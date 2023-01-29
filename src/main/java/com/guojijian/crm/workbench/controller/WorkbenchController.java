package com.guojijian.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkbenchController {
    @RequestMapping("/workbench/toIndex")
    public String toIndex(){
        return "workbench/index";
    }
}
