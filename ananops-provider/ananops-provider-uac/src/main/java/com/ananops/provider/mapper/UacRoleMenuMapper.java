package com.ananops.provider.mapper;

import com.ananops.core.mybatis.MyMapper;
import com.ananops.provider.model.domain.UacRoleMenu;
import com.ananops.provider.model.vo.role.MenuCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Uac role menu mapper.
 *
 * @author ananops.net @gmail.com
 */
@Mapper
@Component
public interface UacRoleMenuMapper extends MyMapper<UacRoleMenu> {
	/**
	 * Count child menu num list.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<MenuCountVo> countChildMenuNum(Long roleId);

	/**
	 * Add role menu list int.
	 *
	 * @param addUacRoleMenuList the add uac role menu list
	 *
	 * @return the int
	 */
	int addRoleMenuList(List<UacRoleMenu> addUacRoleMenuList);


	/**
	 * Delete by role id list int.
	 *
	 * @param roleIdList the role id list
	 *
	 * @return the int
	 */
	int deleteByRoleIdList(@Param("roleIdList") List<Long> roleIdList);
}