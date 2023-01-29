package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.CustomerRemark;

import java.util.List;

public interface CustomerRemarkService {
    /**
     * 批量添加客户备注
     */
    int createCustomerRemarks(List<CustomerRemark> customerRemarkList);
}
