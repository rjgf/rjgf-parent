package com.rjgf.system.vo.req.sysrole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/7/10
 * @time: 15:53
 */
@Data
@Accessors(chain = true)
@ApiModel("添加角色用户")
public class SysRoleUsersParam {

    @ApiModelProperty(value = "角色编号",required = true)
    private Long roleId;

    @ApiModelProperty(value = "用户id列表")
    @NotEmpty(message = "用户id集合不能为空")
    @Size(max = 1000, message = "用户id集合超过上限")
    private List<Long> userIds;
}
