package com.guojijian.crm.workbench.controller;

import com.guojijian.crm.commons.contants.Contants;
import com.guojijian.crm.commons.pojo.ReturnObject;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.commons.util.UUIDUtils;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.workbench.mapper.ActivityMapper;
import com.guojijian.crm.workbench.pojo.Activity;
import com.guojijian.crm.workbench.pojo.ActivityRemark;
import com.guojijian.crm.workbench.service.ActivityRemarkService;
import com.guojijian.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class ActivityDetailController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/toDetail")
    public String toDetail(String activityId, Model model){
        //收集数据
        Activity activity=activityService.queryActivityDetailById(activityId);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkByActivityId(activityId);
        //封装数据
        model.addAttribute("activity",activity);
        model.addAttribute("activityRemarkList",activityRemarkList);

        return "workbench/activity/detail";
    }

    @RequestMapping("/workbench/activity/saveActivityRemark")
    @ResponseBody
    public Object saveActivityRemark(ActivityRemark activityRemark, HttpSession session){
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activityRemark.setId(UUIDUtils.getUUID());
        activityRemark.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Contants.ACTIVITY_REMARK_EDIT_FLAG_NO);

        //处理业务
        ReturnObject ro=new ReturnObject();
        try{
            int result=activityRemarkService.saveActivityRemark(activityRemark);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setRetObject(activityRemark);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }
        }catch(Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }

        return ro;
    }

    @RequestMapping("/workbench/activity/dropActivityRemarkById")
    @ResponseBody
    public Object dropActivityRemarkById(String id){

        ReturnObject ro=new ReturnObject();
        try {
            //处理业务
            int result = activityRemarkService.dropActivityRemark(id);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请重试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请重试！");
        }
        return ro;
    }

    @RequestMapping("/workbench/activity/alterActivityRemark")
    @ResponseBody
    public Object alterActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        ReturnObject ro=new ReturnObject();
        //封装参数
        activityRemark.setEditDate(DateUtils.dataFormatDataTime(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Contants.ACTIVITY_REMARK_EDIT_FLAG_YES);
        //处理业务
        try{
            int result=activityRemarkService.alterActivityRemark(activityRemark);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setRetObject(activityRemark);
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }
        return ro;
    }
}
