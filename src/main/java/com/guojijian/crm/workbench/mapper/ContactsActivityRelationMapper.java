package com.guojijian.crm.workbench.mapper;

import com.guojijian.crm.workbench.pojo.ContactsActivityRelation;
import java.util.List;

public interface ContactsActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Fri Jan 06 19:10:19 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Fri Jan 06 19:10:19 CST 2023
     */
    int insert(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Fri Jan 06 19:10:19 CST 2023
     */
    ContactsActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Fri Jan 06 19:10:19 CST 2023
     */
    List<ContactsActivityRelation> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Fri Jan 06 19:10:19 CST 2023
     */
    int updateByPrimaryKey(ContactsActivityRelation record);

    /**
     * 批量添加联系人与活动的关联
     */
    int insertContactsActivityRelations(List<ContactsActivityRelation> contactsActivityRelationList);
}