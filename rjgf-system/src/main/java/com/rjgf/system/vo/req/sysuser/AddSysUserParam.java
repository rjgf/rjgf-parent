package com.rjgf.system.vo.req.sysuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/1
 * @time: 12:56
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "添加用户", description = "添加用户参数对象")
public class AddSysUserParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty(value = "性别，0：女，1：男，默认1")
    private Integer gender;

    @ApiModelProperty(value = "头像")
    private String head;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer state;

    @ApiModelProperty(value = "部门id")
    @NotNull(message = "部门id不能为空")
    private Long departmentId;

    @ApiModelProperty(value = "角色id列表")
    @NotEmpty(message = "角色集合不能为空")
    @Size(max = 4, message = "角色集合超过上限")
    private List<Long> roleIds;

    @ApiModelProperty(value = "备注")
    private String remark;
}
