package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.Contacts;

import java.util.List;

public interface ContactsService {
    /**
     * 保存新联系人
     */
    int saveContacts(Contacts contacts);

    /**
     * 根据联系人名称模糊查询联系人的部分信息
     */
    List<Contacts> queryContactsForPartInfoByName(String name);

    /**
     * 根据id查询联系人名称
     */
    String queryContactsNameById(String id);
}
