package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.ContactsActivityRelationMapper;
import com.guojijian.crm.workbench.pojo.ContactsActivityRelation;
import com.guojijian.crm.workbench.service.ContactsActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contactsActivityRelationService")
public class ContactsActivityRelationServiceImpl implements ContactsActivityRelationService {

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    public int createContactsActivityRelations(List<ContactsActivityRelation> contactsActivityRelationList) {
        return contactsActivityRelationMapper.insertContactsActivityRelations(contactsActivityRelationList);
    }
}
