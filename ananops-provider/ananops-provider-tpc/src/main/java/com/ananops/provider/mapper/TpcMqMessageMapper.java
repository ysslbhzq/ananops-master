/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageMapper.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.mapper;

import com.ananops.base.dto.MessageQueryDto;
import com.ananops.core.mybatis.MyMapper;
import com.ananops.provider.model.domain.TpcMqMessage;
import com.ananops.provider.model.dto.MessageTaskQueryDto;
import com.ananops.provider.model.vo.TpcMessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Tpc mq message mapper.
 *
 * @author ananops.net @gmail.com
 */
@Component
@Mapper
public interface TpcMqMessageMapper extends MyMapper<TpcMqMessage> {
	/**
	 * Gets message by message key.
	 *
	 * @param messageKey the message key
	 *
	 * @return the message by message key
	 */
	TpcMqMessage getByMessageKey(@Param("messageKey") String messageKey);

	/**
	 * Delete message by message key int.
	 *
	 * @param messageKey the message key
	 *
	 * @return the int
	 */
	int deleteMessageByMessageKey(@Param("messageKey") String messageKey);

	/**
	 * Update already dead by task id.
	 *
	 * @param messageId the message id
	 *
	 * @return the int
	 */
	int updateAlreadyDeadByMessageId(@Param("messageId") Long messageId);

	/**
	 * Add task exe count by id int.
	 *
	 * @param messageId the message id
	 *
	 * @return the int
	 */
	int addTaskExeCountById(@Param("messageId") Long messageId);

	/**
	 * Query waiting confirm message key list list.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<String> queryWaitingConfirmMessageKeyList(MessageTaskQueryDto query);

	/**
	 * Batch delete message int.
	 *
	 * @param deleteKeyList the delete key list
	 *
	 * @return the int
	 */
	int batchDeleteMessage(@Param("messageKeyList") List<String> deleteKeyList);

	/**
	 * Update mq task message status int.
	 *
	 * @param message the message
	 *
	 * @return the int
	 */
	int updateMqMessageTaskStatus(TpcMqMessage message);

	/**
	 * List message for waiting process list.
	 *
	 * @param query the query
	 *
	 * @return the list
	 */
	List<TpcMqMessage> listMessageForWaitingProcess(MessageTaskQueryDto query);

	/**
	 * List reliable message vo with page list.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVoWithPage(MessageQueryDto messageQueryDto);

	/**
	 * List reliable message vo list.
	 *
	 * @param messageIdList the message id list
	 *
	 * @return the list
	 */
	List<TpcMessageVo> listReliableMessageVo(@Param("messageIdList") List<Long> messageIdList);
}