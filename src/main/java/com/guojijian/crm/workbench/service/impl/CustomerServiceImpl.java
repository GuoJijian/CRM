package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.CustomerMapper;
import com.guojijian.crm.workbench.pojo.Customer;
import com.guojijian.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public int saveCustomer(Customer customer) {
        return customerMapper.insert(customer);
    }

    public List<String> queryCustomerNameByStr(String name) {
        return customerMapper.selectCustomerNameByStr(name);
    }

    public String queryCustomerIdByName(String name) {
        return customerMapper.selectCustomerIdByName(name);
    }
}
