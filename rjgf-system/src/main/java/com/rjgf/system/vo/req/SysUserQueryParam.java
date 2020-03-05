/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
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

package com.rjgf.system.vo.req;

import com.rjgf.common.common.api.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统用户 查询参数对象
 * </pre>
 *
 * @author geekidea
 * @date 2019-10-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserQueryParam对象", description = "系统用户查询参数")
public class SysUserQueryParam extends PageParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "姓名", notes = "用户名字")
    private String realName;

    @ApiModelProperty(name = "角色", notes = "角色编号")
    private Long roleId;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer state;
}
