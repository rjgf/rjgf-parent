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
@ApiModel(value = "修改用户", description = "修改用户参数对象")
public class UpdateSysUserParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String realName;

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

    @ApiModelProperty(value = "城市配置列表")
    @NotEmpty(message = "城市配置不能为空")
    @Size(max = 4, message = "城市配置超过上限")
    private List<Integer> areaIds;

    @ApiModelProperty(value = "备注")
    private String remark;
}
