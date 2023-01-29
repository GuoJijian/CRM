package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ContactsRemarkMapper;
import com.guojijian.crm.workbench.pojo.ContactsRemark;
import com.guojijian.crm.workbench.service.ContactsRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactsRemarkService")
public class ContactsRemarkServiceImpl implements ContactsRemarkService {

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    public int createContactsRemarks(List<ContactsRemark> contactsRemarkList) {
        return contactsRemarkMapper.insertContactsRemarks(contactsRemarkList);
    }
}
