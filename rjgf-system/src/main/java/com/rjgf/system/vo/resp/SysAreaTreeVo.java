package com.rjgf.system.vo.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rjgf.common.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 省市区
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysAreaQueryVo对象", description = "省市区")
public class SysAreaTreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer areaId;

    @ApiModelProperty(value = "省市县")
    private String name;

    @ApiModelProperty(value = "父级")
    private Integer parentId;

    @ApiModelProperty(value = "谱图 记录层级")
    private String path;

    @ApiModelProperty(value = "子集")
    private List<SysAreaTreeVo> children;
}
