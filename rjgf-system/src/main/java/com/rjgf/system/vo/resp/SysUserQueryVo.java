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

package com.rjgf.system.vo.resp;

import com.rjgf.system.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 系统用户 查询结果对象
 * </pre>
 *
 * @author geekidea
 * @date 2019-10-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserQueryVo对象", description = "系统用户查询参数")
public class SysUserQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户名字")
    private String realName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "性别，0：女，1：男，默认1")
    private Integer gender;

    @ApiModelProperty(value = "头像")
    private String head;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "remark")
    private String remark;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer state;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "角色列表")
    private List<String> sysRoles;
}