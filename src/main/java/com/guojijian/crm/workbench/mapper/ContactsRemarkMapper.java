package com.guojijian.crm.workbench.mapper;

import com.guojijian.crm.workbench.pojo.ContactsRemark;
import java.util.List;

public interface ContactsRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_remark
     *
     * @mbggenerated Fri Jan 06 18:50:04 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_remark
     *
     * @mbggenerated Fri Jan 06 18:50:04 CST 2023
     */
    int insert(ContactsRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_remark
     *
     * @mbggenerated Fri Jan 06 18:50:04 CST 2023
     */
    ContactsRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_remark
     *
     * @mbggenerated Fri Jan 06 18:50:04 CST 2023
     */
    List<ContactsRemark> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_remark
     *
     * @mbggenerated Fri Jan 06 18:50:04 CST 2023
     */
    int updateByPrimaryKey(ContactsRemark record);

    /**
     * 批量添加联系人备注
     * @param contactsRemarkList
     * @return
     */
    int insertContactsRemarks(List<ContactsRemark> contactsRemarkList);
}