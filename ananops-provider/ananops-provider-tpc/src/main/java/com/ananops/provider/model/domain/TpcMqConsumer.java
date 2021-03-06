/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumer.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.model.domain;

import com.ananops.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Tpc mq consumer.
 *
 * @author ananops.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "an_tpc_mq_consumer")
@Alias(value = "tpcMqConsumer")
public class TpcMqConsumer extends BaseEntity {

	private static final long serialVersionUID = 9104188440392558541L;

	/**
	 * 微服务名称
	 */
	@Column(name = "application_name")
	private String applicationName;

	/**
	 * 城市编码
	 */
	@Column(name = "consumer_code")
	private String consumerCode;

	/**
	 * 区域编码
	 */
	@Column(name = "consumer_name")
	private String consumerName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

}