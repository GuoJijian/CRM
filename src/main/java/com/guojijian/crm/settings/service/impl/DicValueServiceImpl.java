package com.guojijian.crm.settings.service.impl;

import com.guojijian.crm.settings.mapper.DicValueMapper;
import com.guojijian.crm.settings.pojo.DicValue;
import com.guojijian.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    private DicValueMapper dicValueMapper;

    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectAllDicValueByTypeCode(typeCode);
    }

    public String queryValueById(String id) {
        return dicValueMapper.selectValueById(id);
    }
}
