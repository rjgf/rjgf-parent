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

package com.rjgf.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.log.entity.SysLog;
import com.rjgf.log.mapper.SysLogMapper;
import com.rjgf.log.service.ISysLogService;
import com.rjgf.log.vo.req.SysLogQueryParam;
import com.rjgf.log.vo.resp.SysLogQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;


/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author geekidea
 * @since 2019-10-11
 */
@Slf4j
@Service
public class SysLogServiceImpl extends CommonServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysLogQueryVo getSysLogById(Serializable id) throws Exception {
        return sysLogMapper.getSysLogById(id);
    }

    @Override
    public Page<SysLogQueryVo> getSysLogPage(SysLogQueryParam sysLogQueryParam) throws Exception {
        IPage page = new Page(sysLogQueryParam.getPageNo(),sysLogQueryParam.getPageSize());
        return sysLogMapper.getSysLogPageList(page, sysLogQueryParam);
    }

}
