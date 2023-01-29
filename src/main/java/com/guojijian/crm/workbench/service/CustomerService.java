package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.Customer;

import java.util.List;

public interface CustomerService {
    /**
     * 添加新客户
     */
    int saveCustomer(Customer customer);

    /**
     * 根据字符串模糊查询客户名称
     */
    List<String> queryCustomerNameByStr(String name);

    /**
     * 根据客户名称查询客户的id
     */
    String queryCustomerIdByName(String name);
}
