/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.service.impl;

import com.ananops.core.support.BaseService;
import com.ananops.provider.mapper.TpcMqProducerMapper;
import com.ananops.provider.model.domain.TpcMqProducer;
import com.ananops.provider.model.vo.TpcMqProducerVo;
import com.ananops.provider.model.vo.TpcMqPublishVo;
import com.ananops.provider.service.TpcMqProducerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Tpc mq producer service.
 *
 * @author ananops.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqProducerServiceImpl extends BaseService<TpcMqProducer> implements TpcMqProducerService {

	@Resource
	private TpcMqProducerMapper mdcMqProducerMapper;

	@Override
	public List<TpcMqProducerVo> listProducerVoWithPage(TpcMqProducer mdcMqProducer) {
		return mdcMqProducerMapper.listTpcMqProducerVoWithPage(mdcMqProducer);
	}

	@Override
	public List<TpcMqPublishVo> listPublishVoWithPage(TpcMqProducer mdcMqProducer) {
		return mdcMqProducerMapper.listTpcMqPublishVoWithPage(mdcMqProducer);
	}

	@Override
	public int deleteProducerById(Long producerId) {
		// 删除consumer
		mdcMqProducerMapper.deleteByPrimaryKey(producerId);
		// 删除发布关系
		return mdcMqProducerMapper.deletePublishByProducerId(producerId);
	}

	@Override
	public void updateOnLineStatusByPid(final String producerGroup) {
		logger.info("更新生产者pid={}状态为在线", producerGroup);
		this.updateStatus(producerGroup, 10);

	}

	@Override
	public void updateOffLineStatusByPid(final String producerGroup) {
		logger.info("更新生产者pid={}状态为离线", producerGroup);
		this.updateStatus(producerGroup, 20);
	}

	private void updateStatus(final String producerGroup, final int status) {
		TpcMqProducer tpcMqProducer = mdcMqProducerMapper.getByPid(producerGroup);
		if (tpcMqProducer.getStatus() != null && tpcMqProducer.getStatus() != status) {
			TpcMqProducer update = new TpcMqProducer();
			update.setStatus(status);
			update.setId(tpcMqProducer.getId());
			mdcMqProducerMapper.updateByPrimaryKeySelective(update);
		}
	}
}
