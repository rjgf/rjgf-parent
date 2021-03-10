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

package com.rjgf.log.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统日志 查询结果对象
 * </p>
 *
 * @author xula
 * @date 2019-10-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLogQueryVo对象", description = "系统日志查询参数")
public class SysLogQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "操作内容")
    private String title;

    @ApiModelProperty(value = "创建人ID")
    private Long createId;

    @ApiModelProperty(value = "操作时间")
    private Date createTime;

    @ApiModelProperty(value = "访问链接")
    private String reqUrl;

    @ApiModelProperty(value = "用户访问的ip地址")
    private String reqIp;

    @ApiModelProperty(value = "操作状态 0:失败 1:成功")
    private Integer state;

    @ApiModelProperty(value = "用户名")
    private String userName;
}