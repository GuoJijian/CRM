package com.guojijian.crm.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.crm.commons.contants.Contants;
import com.guojijian.crm.commons.pojo.ReturnObject;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.commons.util.HSSFUtils;
import com.guojijian.crm.commons.util.UUIDUtils;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.settings.service.UserService;
import com.guojijian.crm.workbench.pojo.Activity;
import com.guojijian.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
@Transactional
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/toIndex")
    public String queryActivityByConditionForPage(Model model,Integer pageNum,Integer pageSize){
        //获取所有的用户，保存至域对象
        List<User> userList=userService.queryAllUser();
        model.addAttribute("userList",userList);
        //获取分页后的活动列表，保存至域对象
        if(pageNum == null || pageSize==0){
            pageNum=1;
        }
        if(pageSize == null || pageSize==0){
            pageSize=10;
        }
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveActivity")
    @ResponseBody
    public Object saveActivity(Activity activity, HttpSession session){
        //获取session域对象中的用户
        User user=(User) session.getAttribute(Contants.SESSION_USER);

        //为活动对象赋值
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject ro=new ReturnObject();
        try{
            //持久化新活动
            int result=activityService.saveActivity(activity);

            //判断创建功能是否成功
            if(result>0){
                ro.setCode("1");
            }else{
                ro.setCode("0");
                ro.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ro.setCode("0");
            ro.setMessage("系统繁忙，请稍后再试！");
        }

        return ro;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage")
    @ResponseBody
    public Object queryActivityByConditionForPage(Integer pageNum,Integer pageSize,String name,String owner,String startDate,String endDate){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        PageHelper.startPage(pageNum,pageSize);
        List<Activity> activityList=activityService.queryActivityByConditionForPage(map);
        PageInfo<Activity> page=new PageInfo<Activity>(activityList,5);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("activityList",activityList);
        resultMap.put("page",page);
        return resultMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds")
    @ResponseBody
    public Object deleteActivityByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try{
            int result=activityService.deleteActivityByIds(ids);
            if(result!=ids.length){
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }
        }catch(Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("事务繁忙，请稍后再试！");
        }

        return ro;
    }

    @RequestMapping("/workbench/activity/queryActivityById")
    @ResponseBody
    public Object queryActivityById(String id){
        Activity activity=activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/alterActivityById")
    @ResponseBody
    public Object alterActivityById(Activity activity,HttpSession session){
        //获取参数
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setEditDate(DateUtils.dataFormatDataTime(new Date()));
        activity.setEditBy(user.getId());
        //处理数据
        ReturnObject ro=new ReturnObject();
        try{
            int result=activityService.alterActivityById(activity);
            if(result<0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请稍后再试！");
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }
        }catch(Exception e){
            e.printStackTrace();
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("网络繁忙，请稍后再试！");
        }

        return ro;
    }

    @RequestMapping("/workbench/activity/exportActivityDownload")
    public void exportActivityDownload(HttpServletResponse response){
        //获取所有活动列表
        List<Activity> activityList=activityService.queryAllActivity();

        //创建一个excel表，将activityList的内容放入excel表中
        //表头
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet("市场活动列表");
        HSSFRow row=sheet.createRow(0);
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("活动名");
        cell=row.createCell(3);
        cell.setCellValue("开始时间");
        cell=row.createCell(4);
        cell.setCellValue("结束时间");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("编辑时间");
        cell=row.createCell(10);
        cell.setCellValue("编辑者");

        //表数据
        //判断市场活动集合是否为空
        if(activityList!=null && activityList.size()>0){
            Activity activity;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                row=sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateDate());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditDate());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        //下载excel
        //设置响应类型
        response.setContentType("application/octet-stream");
        //浏览器接收到相应信息之后，默认情况下，直接在显示窗口中打开；即使打不开，也会调用应用程序打开；
        //只有实在打不开，才会激活文件下载窗口
        //设置响应头信息，使浏览器接收到响应信息之后，直接激活文件下载窗口
        response.addHeader("Content-Disposition","attachment;filename=activityTable.xls");
        OutputStream out = null;
        try{
            //获取输出流
            out=response.getOutputStream();
            wb.write(out);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/workbench/activity/optionActivityDownload")
    public void optionActivityDownload(String[] ids,HttpServletResponse response){
        OutputStream out=null;
        try {
            out=response.getOutputStream();
            //设置响应类型
            response.setContentType("application/octet-stream");
            //设置响应头信息
            response.addHeader("Content-Disposition","attachment;filename=activityTable.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取数据
        List<Activity> activityList=activityService.queryActivityByIds(ids);
        //判断是否查询成功
        if(activityList!=null && activityList.size()==ids.length){
            //创建excel文件
            HSSFWorkbook wb=new HSSFWorkbook();
            HSSFSheet sheet=wb.createSheet("市场活动列表");
            HSSFRow row=sheet.createRow(0);
            //表头
            HSSFCell cell=row.createCell(0);
            cell.setCellValue("ID");
            cell=row.createCell(1);
            cell.setCellValue("所有者");
            cell=row.createCell(2);
            cell.setCellValue("名称");
            cell=row.createCell(3);
            cell.setCellValue("开始时间");
            cell=row.createCell(4);
            cell.setCellValue("结束时间");
            cell=row.createCell(5);
            cell.setCellValue("成本");
            cell=row.createCell(6);
            cell.setCellValue("描述");
            cell=row.createCell(7);
            cell.setCellValue("创建时间");
            cell=row.createCell(8);
            cell.setCellValue("创建者");
            cell=row.createCell(9);
            cell.setCellValue("编辑时间");
            cell=row.createCell(10);
            cell.setCellValue("编辑者");

            Activity activity=null;
            //表内数据
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                row=sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateDate());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditDate());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
            try {
                wb.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequestMapping("/workbench/activity/uploadActivity")
    @ResponseBody
    public Object uploadActivity(MultipartFile activityFile,HttpSession session){
        ReturnObject ro=new ReturnObject();

        User user=(User)session.getAttribute(Contants.SESSION_USER);
        try {
            InputStream is=activityFile.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(is);
            HSSFSheet sheet=wb.getSheetAt(0);

            List<Activity> activityList=new ArrayList<Activity>();
            Activity activity=null;
            for(int i=0;i<sheet.getLastRowNum();i++){
                HSSFRow row=sheet.getRow(i+1);
                activity=new Activity();
                for(int j=0;j<row.getLastCellNum();j++){
                    HSSFCell cell=row.getCell(j);
                    activity.setId(UUIDUtils.getUUID());
                    activity.setOwner(user.getId());
                    activity.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
                    activity.setCreateBy(user.getId());
                    if(j==0){
                        activity.setName(HSSFUtils.getCellValue(cell));
                    }else if(j==1){
                        activity.setStartDate(HSSFUtils.getCellValue(cell));
                    }else if(j==2){
                        activity.setEndDate(HSSFUtils.getCellValue(cell));
                    }else if(j==3){
                        activity.setCost(HSSFUtils.getCellValue(cell));
                    }else if(j==4){
                        activity.setDescription(HSSFUtils.getCellValue(cell));
                    }
                }
                activityList.add(activity);
            }

            //批量插入市场活动
            int result=activityService.saveActivitys(activityList);
            if(result==sheet.getLastRowNum()){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setMessage("插入"+sheet.getLastRowNum()+"条记录");
            }else{
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("网络繁忙，请重试！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ro;
    }
}
