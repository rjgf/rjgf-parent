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

package com.rjgf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.convert.SysDepartmentConvert;
import com.rjgf.system.entity.SysDepartment;
import com.rjgf.system.mapper.SysDepartmentMapper;
import com.rjgf.system.vo.req.SysDepartmentQueryParam;
import com.rjgf.system.service.ISysDepartmentService;
import com.rjgf.system.vo.resp.SysDepartmentQueryVo;
import com.rjgf.system.vo.resp.SysDepartmentTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * 部门 服务实现类
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
@Slf4j
@Service
public class SysDepartmentServiceImpl extends CommonServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(SysDepartment sysDepartment) {
        if (sysDepartment.getParentId() == null) {
            return super.save(sysDepartment);
        }
        dealSysDepartmentAsAddAndUpdate(sysDepartment);
        return super.save(sysDepartment);
    }


    /**
     * 目前主要再添加或修改的时候加一层数据处理
     * @param sysDepartment
     */
    private void  dealSysDepartmentAsAddAndUpdate(SysDepartment sysDepartment) {
        // 获取父类的谱图
        Long parentId = sysDepartment.getParentId();
        SysDepartment parent = sysDepartmentMapper.selectById(parentId);
        String parentIds = parent.getParentIds();
        if (StringUtils.isEmpty(parentIds)) {
            parentIds = parentId.toString();
        } else {
            parentIds = StringUtils.join(new Object[]{parent.getParentIds(), parentId}, ",");
        }
        sysDepartment.setParentIds(parentIds);
        Integer level = parent.getLevel() + 1;
        sysDepartment.setLevel(level);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(SysDepartment sysDepartment) throws Exception {
        if (sysDepartment.getParentId() == null) {
            return super.updateById(sysDepartment);
        }
        dealSysDepartmentAsAddAndUpdate(sysDepartment);
        return super.updateById(sysDepartment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public SysDepartmentQueryVo getSysDepartmentById(Serializable id) throws Exception {
        SysDepartment sysDepartment = sysDepartmentMapper.selectById(id);
        return SysDepartmentConvert.INSTANCE.sysDepartmentToQueryVo(sysDepartment);
    }

    @Override
    public IPage<SysDepartmentQueryVo> getSysDepartmentPageList(SysDepartmentQueryParam sysDepartmentQueryParam) throws Exception {
        Page page = new Page(sysDepartmentQueryParam.getPageNo(),sysDepartmentQueryParam.getPageSize());
        return sysDepartmentMapper.getSysDepartmentPageList(page,sysDepartmentQueryParam);
    }

    @Override
    public boolean isEnableSysDepartment(Long id) throws Exception {
        SysDepartment sysDepartment = new SysDepartment()
                .setId(id)
                .setState(StateEnum.ENABLE.getCode());
        int count = sysDepartmentMapper.selectCount(new QueryWrapper<>(sysDepartment));
        return count > 0;
    }

    @Override
    public List<SysDepartment> getAllDepartmentList() {
        SysDepartment sysDepartment = new SysDepartment().setState(StateEnum.ENABLE.getCode());
        // 获取所有已启用的部门列表
        return sysDepartmentMapper.selectList(new QueryWrapper(sysDepartment));
    }

    @Override
    public List<SysDepartmentTreeVo> getAllDepartmentTree() {
        SysDepartment sysDepartment = new SysDepartment().setState(StateEnum.ENABLE.getCode());
        List<SysDepartment> sysDepartmentList = super.baseMapper.selectList(Wrappers.lambdaQuery(sysDepartment));
        if (CollectionUtils.isEmpty(sysDepartmentList)) {
            throw new IllegalArgumentException("SysDepartment列表不能为空");
        }
        List<SysDepartmentTreeVo> list = SysDepartmentConvert.INSTANCE.listToTreeVoList(sysDepartmentList);
        List<SysDepartmentTreeVo> treeVos = new ArrayList<>();
        for (SysDepartmentTreeVo treeVo : list) {
            if (treeVo.getParentId() == null || treeVo.getParentId() == -1) {
                treeVos.add(findChildren(treeVo, list));
            }
        }
        return treeVos;
    }

    /**
     * 递归获取树形结果列表
     *
     * @param tree
     * @param list
     * @return
     */
    public SysDepartmentTreeVo findChildren(SysDepartmentTreeVo tree, List<SysDepartmentTreeVo> list) {
        for (SysDepartmentTreeVo vo : list) {
            if (tree.getId().equals(vo.getParentId())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList<>());
                }
                tree.getChildren().add(findChildren(vo, list));
            }
        }
        return tree;
    }


}
