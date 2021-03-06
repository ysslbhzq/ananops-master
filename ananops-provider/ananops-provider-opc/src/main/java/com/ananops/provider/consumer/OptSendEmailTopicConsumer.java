/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：OptSendEmailTopicConsumer.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.consumer;

import com.ananops.JacksonUtil;
import com.ananops.core.mq.MqMessage;
import com.ananops.provider.model.dto.PcSendEmailRequest;
import com.ananops.provider.service.OptSendMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * The class Opt send email topic consumer.
 *
 * @author ananops.net@gmail.com
 */
@Slf4j
@Service
public class OptSendEmailTopicConsumer {

	@Resource
	private OptSendMailService optSendMailService;

	/**
	 * Handler send email topic.
	 *
	 * @param body      the body
	 * @param topicName the topic name
	 * @param tags      the tags
	 * @param keys      the keys
	 */
	public void handlerSendEmailTopic(String body, String topicName, String tags, String keys) {
		MqMessage.checkMessage(body, keys, topicName);
		PcSendEmailRequest request;
		try {
			request = JacksonUtil.parseJson(body, PcSendEmailRequest.class);
		} catch (Exception e) {
			log.error("发送短信MQ出现异常={}", e.getMessage(), e);
			throw new IllegalArgumentException("处理MQ信息,JSON转换异常");
		}
		String subject = request.getSubject();
		String text = request.getText();
		Set<String> to = request.getTo();
		optSendMailService.sendTemplateMail(subject, text, to);
	}
}
