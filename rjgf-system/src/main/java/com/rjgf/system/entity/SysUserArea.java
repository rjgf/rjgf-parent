package com.rjgf.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.rjgf.common.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  用户-管辖区域
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserArea对象", description = "用户-管辖区域")
public class SysUserArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "区域编号")
    private Integer areaId;

    @ApiModelProperty(value = "创建时间")
    @Null(message = "创建时间不用传")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人编号")
    @Null(message = "创建人编号")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "备注")
    private String remark;
}
