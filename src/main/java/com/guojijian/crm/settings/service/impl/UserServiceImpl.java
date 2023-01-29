package com.guojijian.crm.settings.service.impl;

import com.guojijian.crm.settings.mapper.UserMapper;
import com.guojijian.crm.settings.pojo.User;
import com.guojijian.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserByActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByActAndPwd(map);
    }

    public List<User> queryAllUser() {
        return userMapper.selectAll();
    }
}
