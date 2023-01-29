package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    /**
     * 根据线索id和市场活动id批量添加关联
     */
    int createClueActivityRelationByClueIdAndActivityId(List<ClueActivityRelation> clueActivityRelations);

    /**
     * 根据线索id和市场活动id删除关联
     */
    int dropClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation);

    /**
     * 根据线索id删除所有的线索活动关联关系
     */
    int dropClueActivityRelationsByClueId(String clueId);
}
