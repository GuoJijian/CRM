package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationService {
    /**
     * 批量添加联系人与市场活动的关联
     */
    int createContactsActivityRelations(List<ContactsActivityRelation> contactsActivityRelationList);
}
