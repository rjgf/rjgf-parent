package com.rjgf.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rjgf.common.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserRole对象", description = "系统用户-角色")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色编号")
    @NotBlank(message = "角色编号不能为空")
    private Long roleId;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "创建人编号")
    @Null(message = "创建人编号")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @Null(message = "创建时间不用传")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
