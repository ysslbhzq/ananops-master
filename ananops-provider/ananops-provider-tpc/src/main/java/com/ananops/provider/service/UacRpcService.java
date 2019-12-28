/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：UacRpcService.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.service;

import com.github.pagehelper.PageInfo;
import com.ananops.base.dto.MessageQueryDto;
import com.ananops.base.dto.MqMessageVo;
import com.ananops.base.enums.ErrorCodeEnum;
import com.ananops.provider.exceptions.TpcBizException;
import com.ananops.provider.model.service.UacMqMessageFeignApi;
import com.ananops.provider.model.service.UacUserTokenFeignApi;
import com.ananops.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Uac rpc service.
 *
 * @author ananops.net @gmail.com
 */
@Service
@Slf4j
public class UacRpcService {
	@Resource
	private UacUserTokenFeignApi uacUserTokenFeignApi;
	@Resource
	private UacMqMessageFeignApi uacMqMessageFeignApi;

	@Retryable(value = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 2))
	public void batchUpdateTokenOffLine() {
		Wrapper<Integer> wrapper = uacUserTokenFeignApi.updateTokenOffLine();
		if (wrapper == null) {
			log.error("updateTokenOffLine 失败 result is null");
			return;
		}
		Integer result = wrapper.getResult();
		if (result == null || result == 0) {
			log.error("updateTokenOffLine 失败");
		} else {
			log.error("updateTokenOffLine 成功");
		}
	}

	public List<String> queryWaitingConfirmMessageKeyList(List<String> messageKeyList) {
		Wrapper<List<String>> wrapper = uacMqMessageFeignApi.queryMessageKeyList(messageKeyList);
		if (wrapper == null) {
			log.error("queryWaitingConfirmMessageKeyList 失败 result is null");
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		return wrapper.getResult();
	}

	public Wrapper<PageInfo<MqMessageVo>> queryMessageListWithPage(MessageQueryDto messageQueryDto) {
		Wrapper<PageInfo<MqMessageVo>> wrapper = uacMqMessageFeignApi.queryMessageListWithPage(messageQueryDto);
		if (wrapper == null) {
			log.error("查询消息记录 失败 result is null");
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		return wrapper;
	}

}