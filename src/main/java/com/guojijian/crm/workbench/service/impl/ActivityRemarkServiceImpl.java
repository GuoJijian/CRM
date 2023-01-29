package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ActivityRemarkMapper;
import com.guojijian.crm.workbench.pojo.ActivityRemark;
import com.guojijian.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    public List<ActivityRemark> queryActivityRemarkByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkByActivityId(activityId);
    }

    public int saveActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }

    public int dropActivityRemark(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    public int alterActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateActivityRemark(activityRemark);
    }
}
