package com.guojijian.crm.workbench.controller;

import com.guojijian.crm.commons.pojo.FunnelVN;
import com.guojijian.crm.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChartController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/workbench/chart/transaction/toTranIndex")
    public String toTranIndex(){
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/queryTranCountGroupByStage")
    @ResponseBody
    public Object queryTranCountGroupByStage(){
        //分组查询交易量
        List<FunnelVN> funnelVNList=transactionService.queryTranCountGroupByStage();
        //获取阶段名
        List<String> stageName=new ArrayList<String>();
        if(funnelVNList!=null && funnelVNList.size()>0){
            for(FunnelVN funnelVN : funnelVNList){
                stageName.add(funnelVN.getName());
            }
        }

        //封装查询的结果
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("funnelVNList",funnelVNList);
        map.put("stageName",stageName);

        return map;
    }
}
