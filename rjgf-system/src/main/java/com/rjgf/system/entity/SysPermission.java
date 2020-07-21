/*
 * Copyright 2019-2029 xula(https://github.com/xula)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rjgf.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rjgf.common.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * <pre>
 * 系统权限
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysPermission对象", description = "系统权限")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "权限名称",required = true)
    private String name;

    @ApiModelProperty(value = "父id",required = true)
    private Long parentId;

    @ApiModelProperty(value = "上级父类的集合",required = true)
    private String parentIds;

    @ApiModelProperty(value = "路径",required = true)
    private String path;

    @ApiModelProperty(value = "唯一编码")
    @NotBlank(message = "唯一编码不能为空")
    private String code;

    @ApiModelProperty(value = "图标",required = true)
    private String icon;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "类型，1：菜单，2：按钮",required = true)
    @NotNull(message = "类型，1：菜单，2：按钮不能为空")
    private Integer type;

    @ApiModelProperty(value = "层级，1：第一级，2：第二级，N：第N级")
    private Integer level;

    @ApiModelProperty(value = "状态，0：禁用，1：启用")
    private Integer state;

    @ApiModelProperty(value = "排序",required = true)
    private Integer sort;
}
