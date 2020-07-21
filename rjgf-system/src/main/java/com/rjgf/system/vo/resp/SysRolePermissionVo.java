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

package com.rjgf.system.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 系统权限树形列表VO
 *
 * @author xula
 * @date 2019-10-26
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysRolePermissionTreeVo对象", description = "角色系统权限")
public class SysRolePermissionVo implements Serializable {
    private static final long serialVersionUID = 2738804574228359190L;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    Long roleId;

    /**
     * 总的权限树
     */
    @ApiModelProperty(value = "总的权限树")
    List<SysPermissionTreeVo> sysPermissionTreeVoList;

    /**
     * 角色拥有的权限id集合
     */
    @ApiModelProperty(value = "当前角色拥有的权限id集合")
    List<Long> rolePermissionIds;
}