package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    /**
     * 通过活动的id查询活动备注
     */
    List<ActivityRemark> queryActivityRemarkByActivityId(String activityId);

    /**
     * 添加新备注
     */
    int saveActivityRemark(ActivityRemark activityRemark);

    /**
     * 根据id删除备注
     */
    int dropActivityRemark(String id);

    /**
     * 修改备注
     */
    int alterActivityRemark(ActivityRemark activityRemark);
}
