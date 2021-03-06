/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：HandleSendingMessageJob.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.job.dataflow;

import com.google.common.collect.Lists;
import com.ananops.DateUtil;
import com.ananops.elastic.lite.JobParameter;
import com.ananops.elastic.lite.annotation.ElasticJobConfig;
import com.ananops.elastic.lite.job.AbstractBaseDataflowJob;
import com.ananops.provider.mapper.TpcMqConfirmMapper;
import com.ananops.provider.model.domain.TpcMqMessage;
import com.ananops.provider.model.dto.MessageTaskQueryDto;
import com.ananops.provider.model.enums.JobTaskStatusEnum;
import com.ananops.provider.model.enums.MqSendStatusEnum;
import com.ananops.provider.service.TpcMqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 处理发送中的消息数据.
 *
 * @author ananops.net @gmail.com
 */
@Component
@Slf4j
@ElasticJobConfig(cron = "0/30 * * * * ?", jobParameter = "fetchNum=200")
public class HandleSendingMessageJob extends AbstractBaseDataflowJob<TpcMqMessage> {
	@Resource
	private TpcMqMessageService tpcMqMessageService;
	@Value("${ananops.message.handleTimeout}")
	private int timeOutMinute;
	@Value("${ananops.message.maxSendTimes}")
	private int messageMaxSendTimes;

	@Value("${ananops.message.resendMultiplier}")
	private int messageResendMultiplier;
	@Resource
	private TpcMqConfirmMapper tpcMqConfirmMapper;

	/**
	 * Fetch job data list.
	 *
	 * @param jobParameter the job parameter
	 *
	 * @return the list
	 */
	@Override
	protected List<TpcMqMessage> fetchJobData(JobParameter jobParameter) {//抓取流式数据
		MessageTaskQueryDto query = new MessageTaskQueryDto();
		query.setCreateTimeBefore(DateUtil.getBeforeTime(timeOutMinute));
		query.setMessageStatus(MqSendStatusEnum.SENDING.sendStatus());
		query.setFetchNum(jobParameter.getFetchNum());
		query.setShardingItem(jobParameter.getShardingItem());
		query.setShardingTotalCount(jobParameter.getShardingTotalCount());
		query.setTaskStatus(JobTaskStatusEnum.TASK_CREATE.status());
		return tpcMqMessageService.listMessageForWaitingProcess(query);
	}

	/**
	 * Process job data.
	 *
	 * @param taskList the task list
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	protected void processJobData(List<TpcMqMessage> taskList) {//处理流式数据
		for (TpcMqMessage message : taskList) {//遍历任务列表

			Integer resendTimes = message.getResendTimes();//获取消息的重发次数
			if (resendTimes >= messageMaxSendTimes) {//如果消息的当前重发次数大于最大重发次数，说明消息已经死了
				tpcMqMessageService.setMessageToAlreadyDead(message.getId());//修改消息的状态
				continue;
			}

			int times = (resendTimes == 0 ? 1 : resendTimes) * messageResendMultiplier;//获取消息重发的次数
			long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();//获取当前时间
			long needTime = currentTimeInMillis - times * 60 * 1000;//获取上一次重发到现在的累计时间
			long hasTime = message.getUpdateTime().getTime();//获取消息设置的重发时间
			// 判断是否达到了可以再次发送的时间条件
			if (hasTime > needTime) {//如果还没到达下一次重发的时间
				log.debug("currentTime[" + com.xiaoleilu.hutool.date.DateUtil.formatDateTime(new Date()) + "],[SENDING]消息上次发送时间[" + com.xiaoleilu.hutool.date.DateUtil.formatDateTime(message.getUpdateTime()) + "],必须过了[" + times + "]分钟才可以再发送。");
				continue;
			}
			//如果可以进行新一次重发了
			// 前置状态
			List<Integer> preStatusList = Lists.newArrayList(JobTaskStatusEnum.TASK_CREATE.status());
			// 设置任务状态为执行中
			message.setPreStatusList(preStatusList);
			message.setTaskStatus(JobTaskStatusEnum.TASK_EXETING.status());
			int updateRes = tpcMqMessageService.updateMqMessageTaskStatus(message);//更新mq消息任务的状态
			if (updateRes > 0) {//如果mq消息任务状态更新了
				try {

					// 查询是否全部订阅者都确认了消息 是 则更新消息状态完成, 否则重发消息

					int count = tpcMqConfirmMapper.selectUnConsumedCount(message.getMessageKey());
					int status = JobTaskStatusEnum.TASK_CREATE.status();
					if (count < 1) {
						TpcMqMessage update = new TpcMqMessage();
						update.setMessageStatus(MqSendStatusEnum.FINISH.sendStatus());
						update.setId(message.getId());
						tpcMqMessageService.updateMqMessageStatus(update);
						status = JobTaskStatusEnum.TASK_SUCCESS.status();
					} else {
						tpcMqMessageService.resendMessageByMessageId(message.getId());
					}

					// 前置状态
					preStatusList = Lists.newArrayList(JobTaskStatusEnum.TASK_EXETING.status());
					// 设置任务状态为执行中
					message.setPreStatusList(preStatusList);
					message.setTaskStatus(status);
					tpcMqMessageService.updateMqMessageTaskStatus(message);
				} catch (Exception e) {
					log.error("重发失败 ex={}", e.getMessage(), e);
					// 设置任务状态为执行中
					preStatusList = Lists.newArrayList(JobTaskStatusEnum.TASK_EXETING.status());
					message.setPreStatusList(preStatusList);
					message.setTaskStatus(JobTaskStatusEnum.TASK_SUCCESS.status());
					tpcMqMessageService.updateMqMessageTaskStatus(message);
				}
			}
		}
	}
}
