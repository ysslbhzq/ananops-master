/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：TpcMqSubscribeVo.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.model.vo;

import com.ananops.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The class Mdc mq subscribe vo.
 *
 * @author ananops.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqSubscribeVo extends BaseVo {
	private static final long serialVersionUID = 8833595265432073801L;

	/**
	 * 主题编码
	 */
	private String topicCode;
	/**
	 * 主题名称
	 */
	private String topicName;

	/**
	 * 微服务名称
	 */
	private String applicationName;

	/**
	 * 城市编码
	 */
	private String consumerCode;

	/**
	 * 区域编码
	 */
	private String consumerName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 订阅Tag列表
	 */
	private List<TpcMqTagVo> tagVoList;
}
