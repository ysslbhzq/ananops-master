/*
 * Copyright (c) 2018. ananops.net All Rights Reserved.
 * 项目名称：ananops快速搭建企业级分布式微服务平台
 * 类名称：MdcAddressMapper.java
 * 创建人：刘兆明
 * 联系方式：ananops.net@gmail.com
 * 开源地址: https://github.com/ananops
 * 博客地址: http://blog.ananops.net
 * 项目官网: http://ananops.net
 */

package com.ananops.provider.mapper;

import com.ananops.core.mybatis.MyMapper;
import com.ananops.provider.model.domain.MdcAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Mdc address mapper.
 *
 * @author ananops.net@gmail.com
 */
@Mapper
@Component
public interface MdcAddressMapper extends MyMapper<MdcAddress> {
	/**
	 * Select mdc address list.
	 *
	 * @param address the address
	 *
	 * @return the list
	 */
	List<MdcAddress> selectMdcAddress(MdcAddress address);

	/**
	 * Select address by pid list.
	 *
	 * @param pid the pid
	 *
	 * @return the list
	 */
	List<MdcAddress> selectAddressByPid(Long pid);

}