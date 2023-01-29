package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    /**
     * 获取所有活动
     */
    List<Activity> queryAllActivity();

    /**
     * 添加活动
     */
    int saveActivity(Activity activity);

    /**
     * 根据条件分页查询
     */
    List<Activity> queryActivityByConditionForPage(Map<String, Object> map);

    /**
     * 根据id批量删除
     */
    int deleteActivityByIds(String[] ids);

    /**
     * 根据id查询活动
     */
    Activity queryActivityById(String id);

    /**
     * 根据id修改活动
     */
    int alterActivityById(Activity activity);

    /**
     * 根据id进行批量查询
     */
    List<Activity> queryActivityByIds(String[] ids);

    /**
     * 批量插入活动
     */
    int saveActivitys(List<Activity> activityList);

    /**
     * 通过id查询市场活动的详情
     */
    Activity queryActivityDetailById(String id);

    /**
     * 根据线索id查询所有关联的市场活动
     */
    List<Activity> queryActivityDetailByClueId(String clueId);

    /**
     * 根据活动名称模糊查询未关联的市场活动
     */
    List<Activity> queryActivityNoBundByName(String name,String clueId);

    /**
     * 根据活动名称模糊查询关联的市场活动
     */
    List<Activity> queryActivityForBundByName(Map<String,Object> map);

    /**
     * 根据活动名称模糊查询市场活动的部分信息
     */
    List<Activity> queryActivityForPartInfoByName(String name);

    /**
     * 根据id查询市场活动名称
     */
    String queryActivityNameById(String id);
}
