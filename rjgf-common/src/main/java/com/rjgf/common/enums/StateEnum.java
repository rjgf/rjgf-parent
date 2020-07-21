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

package com.rjgf.common.enums;


import com.rjgf.common.common.enums.BaseEnum;
import com.rjgf.common.common.exception.BusinessException;

/**
 * 启用禁用状态枚举
 *
 * @author xula
 * @date 2019-10-24
 **/
public enum StateEnum implements BaseEnum {
    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private Integer code;
    private String desc;

    StateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * 判断code是否存在
     * @return
     */
    public static boolean isExistCode(Integer code) {
        for (StateEnum s:StateEnum.values()) {
            if (s.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查state值，并返回对立的值
     * @param state
     * @return
     */
    public static Integer checkState(Integer state) {
//        if (!StateEnum.isExistCode(state)) {
//            throw new BusinessException(String.format("传入的state有误，state只能是 {}, {}",
//                    StateEnum.DISABLE.getCode(),StateEnum.ENABLE.getCode()));
//        }
        int oldState = 0;
        if (StateEnum.DISABLE.getCode().equals(state)) {
            oldState = StateEnum.ENABLE.getCode();
        }
        return oldState;
    }
}
