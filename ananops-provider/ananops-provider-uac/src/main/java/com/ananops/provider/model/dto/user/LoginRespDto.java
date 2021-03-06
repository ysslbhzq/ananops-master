package com.ananops.provider.model.dto.user;

import com.ananops.base.dto.LoginAuthDto;
import com.ananops.provider.model.domain.UacRole;
import com.ananops.provider.model.domain.UacUser;
import com.ananops.provider.model.vo.MenuVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The class Login resp dto.
 *
 * @author ananops.net@gmail.com
 */
@Data
@ApiModel(value = "发送短信参数Dto")
public class LoginRespDto implements Serializable {
	private static final long serialVersionUID = -8992761897550131632L;
	@ApiModelProperty(value = "登陆用户信息")
	private UacUser user;
	@ApiModelProperty(value = "菜单集合")
	private List<MenuVo> menuList;
	@ApiModelProperty(value = "用户角色信息集合")
	private List<UacRole> roleList;
}
