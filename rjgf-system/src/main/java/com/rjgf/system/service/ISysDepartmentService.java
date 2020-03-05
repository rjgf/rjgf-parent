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

package com.rjgf.system.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.common.api.req.PageRequest;
import com.rjgf.common.service.CommonService;
import com.rjgf.system.entity.SysDepartment;
import com.rjgf.system.vo.req.SysDepartmentQueryParam;
import com.rjgf.system.vo.resp.SysDepartmentQueryVo;
import com.rjgf.system.vo.resp.SysDepartmentTreeVo;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 部门 服务类
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
public interface ISysDepartmentService extends CommonService<SysDepartment> {

    /**
     * 保存
     *
     * @param sysDepartment
     * @return
     * @throws Exception
     */
    boolean save(SysDepartment sysDepartment);

    /**
     * 修改
     *
     * @param sysDepartment
     * @return
     * @throws Exception
     */
    boolean update(SysDepartment sysDepartment) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean delete(Long id) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysDepartmentQueryVo getSysDepartmentById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysDepartmentQueryParam
     * @return
     * @throws Exception
     */
    IPage<SysDepartmentQueryVo> getSysDepartmentPageList(SysDepartmentQueryParam sysDepartmentQueryParam) throws Exception;

    /**
     * 根据id校验部门是否存在并且已启用
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean isEnableSysDepartment(Long id) throws Exception;

    /**
     * 获取所有可用的部门列表
     * @return
     */
    List<SysDepartment> getAllDepartmentList();

    /**
     * 获取所有可用的部门树形列表
     * @return
     */
    List<SysDepartmentTreeVo> getAllDepartmentTree();

}
