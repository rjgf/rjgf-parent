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

package com.rjgf.log.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.service.CommonService;
import com.rjgf.log.entity.SysLog;
import com.rjgf.log.vo.req.SysLogQueryParam;
import com.rjgf.log.vo.resp.SysLogQueryVo;


import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author xula
 * @since 2019-10-11
 */
public interface ISysLogService extends CommonService<SysLog> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysLogQueryVo getSysLogById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysLogQueryParam
     * @return
     */
    Page<SysLogQueryVo> getSysLogPage(SysLogQueryParam sysLogQueryParam) throws Exception;

}
