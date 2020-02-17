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

package com.rjgf.system.convert;

import com.rjgf.system.entity.SysArea;
import com.rjgf.system.vo.resp.SysAreaTreeVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 省市区转换器
 *
 * @author geekidea
 * @date 2019-10-05
 **/
@Mapper
public interface SysAreaConvert {

    SysAreaConvert INSTANCE = Mappers.getMapper(SysAreaConvert.class);

    /**
     * sysAreas列表转换成SysAreaTreeVo列表
     *
     * @param sysAreas
     * @return
     */
    List<SysAreaTreeVo> listToTreeVoList(List<SysArea> sysAreas);
}
