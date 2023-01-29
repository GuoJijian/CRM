package com.guojijian.crm.settings.service;

import com.guojijian.crm.settings.pojo.DicValue;

import java.util.List;

public interface DicValueService {
    /**
     * 根据类型编码查询dicValue
     */
    List<DicValue> queryDicValueByTypeCode(String typeCode);

    /**
     * 根据id查询value
     */
    String queryValueById(String id);
}
