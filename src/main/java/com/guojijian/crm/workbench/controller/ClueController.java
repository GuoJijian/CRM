package com.guojijian.crm.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.crm.commons.contants.Contants;
import com.guojijian.crm.commons.pojo.ReturnObject;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.commons.util.UUIDUtils;
import com.guojijian.crm.settings.pojo.DicValue;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.settings.service.UserService;
import com.guojijian.crm.settings.service.DicValueService;
import com.guojijian.crm.workbench.pojo.Activity;
import com.guojijian.crm.workbench.pojo.Clue;
import com.guojijian.crm.workbench.pojo.ClueActivityRelation;
import com.guojijian.crm.workbench.pojo.ClueRemark;
import com.guojijian.crm.workbench.service.ActivityService;
import com.guojijian.crm.workbench.service.ClueActivityRelationService;
import com.guojijian.crm.workbench.service.ClueRemarkService;
import com.guojijian.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {

    @Autowired
    private UserService userService;

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/toIndex")
    public String toIndex(Model model){
        //处理业务
        List<User> userList=userService.queryAllUser();
        List<DicValue> appellationList=dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList=dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");

        //将数据保存到域对象中
        model.addAttribute("userList",userList);
        model.addAttribute("appellationList",appellationList);
        model.addAttribute("clueStateList",clueStateList);
        model.addAttribute("sourceList",sourceList);

        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/queryConditionClue")
    @ResponseBody
    public Object queryConditionClue(Integer pageNum,Integer pageSize,Clue clue){
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Clue> clueList=clueService.queryConditionClue(clue);
        PageInfo<Clue> page=new PageInfo<Clue>(clueList,5);
        //生成响应信息
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("clueList",clueList);
        map.put("page",page);

        return map;
    }

    @RequestMapping("/workbench/clue/saveCreateClue")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session){
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        clue.setCreateBy(user.getId());

        ReturnObject ro=new ReturnObject();
        try {
            //保存创建的线索
            int result=clueService.saveClue(clue);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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

    @RequestMapping("/workbench/clue/dropCluesById")
    @ResponseBody
    public Object dropCluesById(String[] ids){
        ReturnObject ro=new ReturnObject();
        try {
            //根据id批量删除线索
            int result=clueService.dropCluesById(ids);
            if(ids!=null && result==ids.length){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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

    @RequestMapping("/workbench/clue/queryClueById")
    @ResponseBody
    public Object queryClueById(String id){
        Clue clue=null;
        try{
            clue=clueService.queryClueById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return clue;
    }

    @RequestMapping("/workbench/clue/alterClueById")
    @ResponseBody
    public Object alterClueById(Clue clue){
        ReturnObject ro=new ReturnObject();
        //调用service层方法，修改线索
        try{
            int result=clueService.alterClueById(clue);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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

    @RequestMapping("/workbench/clue/toDetail")
    public String toDetail(Model model,String id){
        //调用service层方法，查询线索的详情
        Clue clue=clueService.queryClueDetailById(id);
        //保存到域对象中
        model.addAttribute("clue",clue);

        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/queryClueBundData")
    @ResponseBody
    public Object queryClueBundData(String clueId){
        //调用service层，查询线索绑定数据
        List<Activity> activityList=activityService.queryActivityDetailByClueId(clueId);
        List<ClueRemark> clueRemarkList=clueRemarkService.queryAllClueRemarkByClueId(clueId);
        //根据查询结果，封装响应信息
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("activityList",activityList);
        map.put("clueRemarkList",clueRemarkList);

        return map;
    }

    @RequestMapping("/workbench/clue/saveClueRemark")
    @ResponseBody
    public Object saveClueRemark(ClueRemark clueRemark,HttpSession session){
        ReturnObject ro=new ReturnObject();
        //获取参数
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        clueRemark.setId(UUIDUtils.getUUID());
        clueRemark.setCreateBy(user.getId());
        clueRemark.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        clueRemark.setEditFlag(Contants.ACTIVITY_REMARK_EDIT_FLAG_NO);

        try {
            //调用service层，添加线索备注
            int result = clueRemarkService.saveClueRemark(clueRemark);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                clueRemark.setCreateBy(user.getName());
                ro.setRetObject(clueRemark);
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

    @RequestMapping("/workbench/clue/dropClueRemarkById")
    @ResponseBody
    public Object dropClueRemarkById(String id){
        ReturnObject ro=new ReturnObject();
        try {
            //调用service层方法，删除备注
            int result = clueRemarkService.dropClueRemarkById(id);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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

    @RequestMapping("/workbench/clue/alterClueRemarkById")
    @ResponseBody
    public Object alterClueRemarkById(ClueRemark clueRemark,HttpSession session){
        ReturnObject ro=new ReturnObject();
        //收集参数
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        clueRemark.setEditBy(user.getId());
        clueRemark.setEditDate(DateUtils.dataFormatDataTime(new Date()));
        clueRemark.setEditFlag(Contants.ACTIVITY_REMARK_EDIT_FLAG_YES);
        try {
            //修改备注
            int result = clueRemarkService.alterClueRemarkById(clueRemark);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                clueRemark.setEditBy(user.getName());
                ro.setRetObject(clueRemark);
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

    @RequestMapping("/workbench/clue/queryActivityNoBundByName")
    @ResponseBody
    public Object queryActivityNoBundByName(String name,String clueId){
        //模糊查询未绑定的市场活动
        List<Activity> activityList=activityService.queryActivityNoBundByName(name,clueId);

        return activityList;
    }

    @RequestMapping("/workbench/clue/createClueActivityRelationByClueIdAndActivityId")
    @ResponseBody
    public Object createClueActivityRelationByClueIdAndActivityId(String clueId,String[] activityIds){
        ReturnObject ro=new ReturnObject();
        ClueActivityRelation car=null;
        List<ClueActivityRelation> carList=new ArrayList<ClueActivityRelation>();
        //封装参数
        for (String activityId:activityIds) {
            car=new ClueActivityRelation();
            car.setId(UUIDUtils.getUUID());
            car.setClueId(clueId);
            car.setActivityId(activityId);
            carList.add(car);
        }
        try {
            //批量添加线索活动关联
            int result = clueActivityRelationService.createClueActivityRelationByClueIdAndActivityId(carList);
            if(result==activityIds.length){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //查询关联的市场活动
                List<Activity> activityList=activityService.queryActivityDetailByClueId(clueId);
                ro.setRetObject(activityList);
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

    @RequestMapping("/workbench/clue/dropClueActivityRelationByClueIdAndActivityId")
    @ResponseBody
    public Object dropClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation){
        ReturnObject ro=new ReturnObject();
        try {
            //删除关联
            int result=clueActivityRelationService.dropClueActivityRelationByClueIdAndActivityId(clueActivityRelation);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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

    @RequestMapping("/workbench/clue/toConvert")
    public String toConvert(String id,Model model){
        //调用service层方法，查询线索部分信息和dicValue
        Clue clue=clueService.queryClueInfoById(id);
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        //将数据保存到request域对象中
        model.addAttribute("clue",clue);
        model.addAttribute("stageList",stageList);

        return "workbench/clue/convert";
    }

    @RequestMapping("/workbench/clue/queryActivityForBundByName")
    @ResponseBody
    public Object queryActivityForBundByName(String clueId,String activityName){
        //封装参数
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("clueId",clueId);
        map.put("activityName",activityName);
        //模糊查询关联的市场活动
        List<Activity> activityList=activityService.queryActivityForBundByName(map);

        return activityList;
    }

    @RequestMapping("/workbench/clue/saveConvert")
    @ResponseBody
    public Object saveConvert(String clueId,String isCreateTran,String money,String name,String expectedDate,String stage,String activityId,HttpSession session){
        ReturnObject ro=new ReturnObject();
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        Map<String,Object> map=new HashMap<String, Object>();
        map.put(Contants.SESSION_USER,user);
        map.put("clueId",clueId);
        map.put("isCreateTran",isCreateTran);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        try {
            //保存转换
            clueService.saveConvert(map);
            ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }
        return ro;
    }
}
