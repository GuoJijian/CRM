package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ContactsMapper;
import com.guojijian.crm.workbench.pojo.Contacts;
import com.guojijian.crm.workbench.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsMapper contactsMapper;

    public int saveContacts(Contacts contacts) {
        return contactsMapper.insert(contacts);
    }

    public List<Contacts> queryContactsForPartInfoByName(String name) {
        return contactsMapper.selectContactsForPartInfoByName(name);
    }

    public String queryContactsNameById(String id) {
        return contactsMapper.selectContactsNameById(id);
    }
}
