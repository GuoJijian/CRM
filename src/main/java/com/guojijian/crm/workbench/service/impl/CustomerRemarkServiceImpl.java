package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.CustomerRemarkMapper;
import com.guojijian.crm.workbench.pojo.CustomerRemark;
import com.guojijian.crm.workbench.service.CustomerRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerRemarkService")
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    public int createCustomerRemarks(List<CustomerRemark> customerRemarkList) {
        return customerRemarkMapper.insertCustomerRemarks(customerRemarkList);
    }
}
