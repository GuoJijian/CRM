package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ClueActivityRelationMapper;
import com.guojijian.crm.workbench.pojo.ClueActivityRelation;
import com.guojijian.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    public int createClueActivityRelationByClueIdAndActivityId(List<ClueActivityRelation> clueActivityRelations) {
        return clueActivityRelationMapper.insertClueActivityRelationByClueIdAndActivityId(clueActivityRelations);
    }

    public int dropClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdAndActivityId(clueActivityRelation);
    }

    public int dropClueActivityRelationsByClueId(String clueId) {
        return clueActivityRelationMapper.deleteClueActivityRelationsByClueId(clueId);
    }
}
