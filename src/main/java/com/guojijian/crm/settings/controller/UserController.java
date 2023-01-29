package com.guojijian.crm.settings.controller;

import com.guojijian.crm.commons.contants.Contants;
import com.guojijian.crm.commons.pojo.ReturnObject;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRomPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("login_act",loginAct);
        map.put("login_pwd",loginPwd);
        ReturnObject ro=new ReturnObject();
        //验证账号密码
        User user = userService.queryUserByActAndPwd(map);
        if(user==null){
            //返回0，账号或密码错误
            ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            ro.setMessage("账号或密码错误");
        }else{
            //继续验证用户是否过期，用字符串比较的方式
            if(!(DateUtils.dataFormatDataTime(new Date()).compareTo(user.getExpireTime())>0)){
                //返回0，账号已过期
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("账号已过期");
            }else if("0".equals(user.getLockState())){//继续验证用户是否被锁定
                //返回0，账号已锁定
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("账号已锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){//继续验证用户IP是否合法
                //返回0，用户IP不合法
                ro.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                ro.setMessage("用户IP不合法");
            }else{
                //返回1，账号正确
                ro.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }
            //将用户存入Session域对象中
            session.setAttribute(Contants.SESSION_USER,user);

            //判断是否记住密码
            if("true".equals(isRomPwd)){
                Cookie c1=new Cookie("loginAct",loginAct);
                c1.setMaxAge(60*60*24*10);

                response.addCookie(c1);
                Cookie c2=new Cookie("loginPwd",loginPwd);
                c2.setMaxAge(60*60*24*10);
                response.addCookie(c2);
            }else{
                //不记住密码，则删除Cookie，将对应的Cookie设置时间为0
                Cookie c1=new Cookie("loginAct",loginAct);
                c1.setMaxAge(0);
                response.addCookie(c1);
                Cookie c2=new Cookie("loginPwd",loginPwd);
                c2.setMaxAge(0);
                response.addCookie(c2);
            }

        }
        return ro;
    }

    @RequestMapping("/settings/qx/user/loginOut")
    public String loginOut(HttpSession session,HttpServletResponse response){
        //销毁Session中存储的所有信息
        session.invalidate();
        //销毁Cookie
        Cookie c1=new Cookie("loginAct","0");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2=new Cookie("loginPwd","0");
        c2.setMaxAge(0);
        response.addCookie(c2);
        return "redirect:/settings/qx/user/toLogin";
    }
}
