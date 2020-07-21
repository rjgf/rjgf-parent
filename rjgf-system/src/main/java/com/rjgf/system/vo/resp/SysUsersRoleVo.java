package com.rjgf.system.vo.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/7/20
 * @time: 10:50
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUsersRoleVo对象", description = "用户角色")
public class SysUsersRoleVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "用户编号")
    private String userName;

//    @ApiModelProperty(value = "角色编号")
//    private Long roleId;

    @ApiModelProperty(value = "是否属于当前角色")
    private boolean isBelongCurRole = false;
}
