/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：OptAttachmentReqDto.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.model.dto.attachment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Opt attachment req dto.
 *
 * @author ananops.net@gmail.com
 */
@Data
@ApiModel(value = "OptAttachmentReqDto")
public class OptAttachmentReqDto implements Serializable {

	private static final long serialVersionUID = -1727131719075160349L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 附件流水号
	 */
	@ApiModelProperty(value = "附件流水号")
	private String serialNo;

	/**
	 * 上传附件的相关业务流水号
	 */
	@ApiModelProperty(value = "上传附件的相关业务流水号")
	private String refNo;

}
