package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ActivityMapper;
import com.guojijian.crm.workbench.pojo.Activity;
import com.guojijian.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    public List<Activity> queryAllActivity() {
        return activityMapper.selectAll();
    }

    public int saveActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    public int alterActivityById(Activity activity) {
        return activityMapper.updateActivityById(activity);
    }

    public List<Activity> queryActivityByIds(String[] ids) {
        return activityMapper.selectActivityByIds(ids);
    }

    public int saveActivitys(List<Activity> activityList) {
        return activityMapper.insertActivitys(activityList);
    }

    public Activity queryActivityDetailById(String id) {
        return activityMapper.selectActivityDetailById(id);
    }

    public List<Activity> queryActivityDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    public List<Activity> queryActivityNoBundByName(String name, String clueId) {
        return activityMapper.selectActivityNoBundByName(name,clueId);
    }

    public List<Activity> queryActivityForBundByName(Map<String, Object> map) {
        return activityMapper.selectActivityForBundByName(map);
    }

    public List<Activity> queryActivityForPartInfoByName(String name) {
        return activityMapper.selectActivityForPartInfoByName(name);
    }

    public String queryActivityNameById(String id) {
        return activityMapper.selectActivityNameById(id);
    }
}
