package com.guojijian.crm.settings.service;

import com.guojijian.crm.settings.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 账号密码验证
     */
    User queryUserByActAndPwd(Map<String,Object> map);

    /**
     * 获取所有的用户信息
     */
    List<User> queryAllUser();
}
