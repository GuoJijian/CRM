package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.ContactsRemark;

import java.util.List;

public interface ContactsRemarkService {
    /**
     * 批量添加联系人备注
     */
    int createContactsRemarks(List<ContactsRemark> contactsRemarkList);
}
