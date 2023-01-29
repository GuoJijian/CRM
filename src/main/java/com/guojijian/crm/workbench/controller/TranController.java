package com.guojijian.crm.workbench.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guojijian.crm.commons.contants.Contants;
import com.guojijian.crm.commons.pojo.ReturnObject;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.commons.util.UUIDUtils;
import com.guojijian.crm.settings.pojo.DicValue;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.settings.service.DicValueService;
import com.guojijian.crm.settings.service.UserService;
import com.guojijian.crm.workbench.pojo.*;
import com.guojijian.crm.workbench.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private TransactionRemarkService transactionRemarkService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;
    
    @RequestMapping("/workbench/transaction/toIndex")
    public String toIndex(Model model){
        //获取dic_value
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> tranTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        //将查询结果保存至域对象中
        model.addAttribute("stageList",stageList);
        model.addAttribute("tranTypeList",tranTypeList);
        model.addAttribute("sourceList",sourceList);

        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/queryTranForPageByCondition")
    @ResponseBody
    public Object queryTranForPageByCondition(Integer pageNum, Integer pageSize, Transaction transaction){
        //参数验证
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Transaction> tranList=transactionService.queryTranForPageByCondition(transaction);
        PageInfo<Transaction> page=new PageInfo<Transaction>(tranList,5);
        //将查询到的结果，封装到Map中
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("tranList",tranList);
        map.put("page",page);

        return map;
    }

    @RequestMapping("/workbench/transaction/toSave")
    public String toSave(Model model){
        //获取数据
        List<User> userList=userService.queryAllUser();
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> tranTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        //将数据存入request域对象中
        model.addAttribute("userList",userList);
        model.addAttribute("stageList",stageList);
        model.addAttribute("tranTypeList",tranTypeList);
        model.addAttribute("sourceList",sourceList);

        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/queryCustomerNameByStr")
    @ResponseBody
    public Object queryCustomerNameByStr(String name){
        List<String> customerNameList=customerService.queryCustomerNameByStr(name);
        return customerNameList;
    }

    @RequestMapping("/workbench/transaction/queryPossibilityByStage")
    @ResponseBody
    public Object queryPossibilityByStage(String stage) throws UnsupportedEncodingException {
        ResourceBundle rb=ResourceBundle.getBundle("possibility");
        String possibility=rb.getString(stage);
        return possibility;
    }

    @RequestMapping("/workbench/transaction/queryActivityForPartInfoByName")
    @ResponseBody
    public Object queryActivityForPartInfoByName(String name){
        //根据市场活动名称模糊查询活动部分信息
        List<Activity> activityList=activityService.queryActivityForPartInfoByName(name);

        return activityList;
    }

    @RequestMapping("/workbench/transaction/queryContactsForPartInfoByName")
    @ResponseBody
    public Object queryContactsForPartInfoByName(String fullname){
        //根据联系人名称模糊查询联系人的部分信息
        List<Contacts> contactsList=contactsService.queryContactsForPartInfoByName(fullname);

        return contactsList;
    }

    @RequestMapping("/workbench/transaction/createTran")
    @ResponseBody
    public Object createTran(Transaction transaction, HttpSession session){
        ReturnObject ro=new ReturnObject();
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        transaction.setId(UUIDUtils.getUUID());
        transaction.setCreateBy(user.getId());
        transaction.setCreateDate(DateUtils.dataFormatDataTime(new Date()));

        try {
            //创建交易
            int result=transactionService.createTransaction(transaction);

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

    @RequestMapping("/workbench/transaction/dropTranByIds")
    @ResponseBody
    public Object dropTranByIds(String[] ids){
        ReturnObject ro=new ReturnObject();
        try {
            //批量删除交易
            int result = transactionService.dropTranByIds(ids);
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

    @RequestMapping("/workbench/transaction/toEdit")
    public String toEdit(String id,Model model){
        //查询固定信息
        List<User> userList=userService.queryAllUser();
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> tranTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");

        //将查询的信息封装到request作用域中
        model.addAttribute("id",id);
        model.addAttribute("userList",userList);
        model.addAttribute("stageList",stageList);
        model.addAttribute("tranTypeList",tranTypeList);
        model.addAttribute("sourceList",sourceList);

        return "workbench/transaction/edit";
    }

    @RequestMapping("/workbench/transaction/queryEditInfo")
    @ResponseBody
    public Object queryEditInfo(String id){
        //查询交易的编辑信息
        Transaction transaction=transactionService.queryTranForEditById(id);
        //配置可能性
        ResourceBundle rb=ResourceBundle.getBundle("possibility");
        String stageValue=dicValueService.queryValueById(transaction.getStage());
        String possible=rb.getString(stageValue);
        //查询市场活动源名称
        String activityName=activityService.queryActivityNameById(transaction.getActivityId());
        //查询联系人名称
        String contactsName=contactsService.queryContactsNameById(transaction.getContactsId());

        //将查询结果封装
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("transaction",transaction);
        map.put("possible",possible+"%");
        map.put("activityName",activityName);
        map.put("contactsName",contactsName);


        return map;
    }

    @RequestMapping("/workbench/transaction/alterTran")
    @ResponseBody
    public Object alterTran(Transaction transaction,HttpSession session){
        ReturnObject ro=new ReturnObject();
        //获取参数
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        String customerId=customerService.queryCustomerIdByName(transaction.getCustomerId());
        //封装参数
        if(customerId==null || "".equals(customerId)){
            Customer c=new Customer();
            c.setId(UUIDUtils.getUUID());
            c.setName(transaction.getCustomerId());
            customerService.saveCustomer(c);
            transaction.setCustomerId(c.getId());
        }else{
            transaction.setCustomerId(customerId);
        }
        transaction.setEditBy(user.getId());
        transaction.setEditDate(DateUtils.dataFormatDataTime(new Date()));
        try {
            //修改交易
            int result=transactionService.alterTran(transaction);
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

    @RequestMapping("/workbench/transaction/toDetail")
    public String toDetail(String id,Model model){
        //查询交易的详细信息
        Transaction transaction=transactionService.queryTranForDetailById(id);
        ResourceBundle rb=ResourceBundle.getBundle("possibility");
        String possible=rb.getString(transaction.getStage());
        //将查询结果封装到request域对象中
        model.addAttribute("transaction",transaction);
        model.addAttribute("possible",possible+"%");
        return "workbench/transaction/detail";
    }

    @RequestMapping("/workbench/transaction/queryTranDetailForPartInfo")
    @ResponseBody
    public Object queryTranDetailForPartInfo(String id){
        //查询交易的备注信息
        List<TransactionRemark> tranRemarkList=transactionRemarkService.queryTranRemarkById(id);
        //查询交易的历史记录
        List<TransactionHistory> tranHistoryList=transactionHistoryService.queryTranHistoryByTranId(id);
        //查询阶段
        List<DicValue> dicValueList=dicValueService.queryDicValueByTypeCode("stage");
        //封装查询结果
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("tranRemarkList",tranRemarkList);
        map.put("tranHistoryList",tranHistoryList);
        map.put("dicValueList",dicValueList);

        return map;
    }

    @RequestMapping("/workbench/transaction/alterTranRemark")
    @ResponseBody
    public Object alterTranRemark(TransactionRemark transactionRemark,HttpSession session){
        ReturnObject ro=new ReturnObject();
        //收集参数
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        transactionRemark.setEditBy(user.getId());
        transactionRemark.setEditDate(DateUtils.dataFormatDataTime(new Date()));
        transactionRemark.setEditFlag(Contants.ACTIVITY_REMARK_EDIT_FLAG_YES);
        try {
            //修改交易
            int result = transactionRemarkService.alterTranRemark(transactionRemark);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                transactionRemark.setEditBy(user.getName());
                ro.setRetObject(transactionRemark);
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

    @RequestMapping("/workbench/transaction/createTranRemark")
    @ResponseBody
    public Object createTranRemark(TransactionRemark transactionRemark,HttpSession session){
        ReturnObject ro=new ReturnObject();
        //获取参数
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        //封装参数
        transactionRemark.setId(UUIDUtils.getUUID());
        transactionRemark.setCreateBy(user.getId());
        transactionRemark.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        transactionRemark.setEditFlag(Contants.ACTIVITY_REMARK_EDIT_FLAG_NO);
        try {
            //添加备注
            int result=transactionRemarkService.createTranRemark(transactionRemark);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                transactionRemark.setCreateBy(user.getName());
                ro.setRetObject(transactionRemark);
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

    @RequestMapping("/workbench/transaction/dropTranRemarkById")
    @ResponseBody
    public Object dropTranRemarkById(String id){
        ReturnObject ro=new ReturnObject();
        try {
            //删除备注
            int result=transactionRemarkService.dropTranRemarkById(id);
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

    @RequestMapping("/workbench/transaction/alterTranStageById")
    @ResponseBody
    public Object alterTranStageById(Transaction transaction,HttpSession session){
        ReturnObject ro=new ReturnObject();
        //获取参数
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        transaction.setEditBy(user.getId());
        transaction.setEditDate(DateUtils.dataFormatDataTime(new Date()));

        try {
            //修改交易
            int result=transactionService.alterTranStageById(transaction);
            if(result>0){
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                ro.setRetObject(transaction);
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
}
