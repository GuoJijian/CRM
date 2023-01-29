package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ClueRemarkMapper;
import com.guojijian.crm.workbench.pojo.ClueRemark;
import com.guojijian.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    public List<ClueRemark> queryAllClueRemarkByClueId(String clueId) {
        return clueRemarkMapper.selectAllClueRemarkByClueId(clueId);
    }

    public int saveClueRemark(ClueRemark clueRemark) {
        return clueRemarkMapper.insertClueRemarkByClueId(clueRemark);
    }

    public int dropClueRemarkById(String id) {
        return clueRemarkMapper.deleteClueRemarkById(id);
    }

    public int alterClueRemarkById(ClueRemark clueRemark) {
        return clueRemarkMapper.updateById(clueRemark);
    }

    public List<ClueRemark> queryClueRemarkForConvertByClueId(String clueId) {
        return clueRemarkMapper.selectClueRemarkForConvertByClueId(clueId);
    }

    public int dropClueRemarksByClueId(String clueId) {
        return clueRemarkMapper.deleteClueRemarksByClueId(clueId);
    }
}
