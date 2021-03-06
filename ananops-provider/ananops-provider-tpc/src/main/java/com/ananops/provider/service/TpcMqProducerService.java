/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerService.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.service;

import com.ananops.core.support.IService;
import com.ananops.provider.model.domain.TpcMqProducer;
import com.ananops.provider.model.vo.TpcMqProducerVo;
import com.ananops.provider.model.vo.TpcMqPublishVo;

import java.util.List;

/**
 * The interface Tpc mq producer service.
 *
 * @author ananops.net @gmail.com
 */
public interface TpcMqProducerService extends IService<TpcMqProducer> {
	/**
	 * List producer vo with page list.
	 *
	 * @param mdcMqProducer the mdc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqProducerVo> listProducerVoWithPage(TpcMqProducer mdcMqProducer);

	/**
	 * 查询发布者列表.
	 *
	 * @param mdcMqProducer the mdc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqPublishVo> listPublishVoWithPage(TpcMqProducer mdcMqProducer);

	/**
	 * 根据生产者ID删除生产者.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteProducerById(Long id);

	/**
	 * 根据pid更新生产者状态为在线.
	 *
	 * @param producerGroup the producer group
	 */
	void updateOnLineStatusByPid(String producerGroup);

	/**
	 * 根据pid更新生产者状态为离线.
	 *
	 * @param producerGroup the producer group
	 */
	void updateOffLineStatusByPid(String producerGroup);
}
