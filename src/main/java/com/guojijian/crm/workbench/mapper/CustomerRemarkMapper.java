package com.guojijian.crm.workbench.mapper;

import com.guojijian.crm.workbench.pojo.CustomerRemark;
import java.util.List;

public interface CustomerRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Fri Jan 06 18:31:06 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Fri Jan 06 18:31:06 CST 2023
     */
    int insert(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Fri Jan 06 18:31:06 CST 2023
     */
    CustomerRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Fri Jan 06 18:31:06 CST 2023
     */
    List<CustomerRemark> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Fri Jan 06 18:31:06 CST 2023
     */
    int updateByPrimaryKey(CustomerRemark record);

    /**
     * 批量添加客户备注信息
     */
    int insertCustomerRemarks(List<CustomerRemark> customerRemarkList);
}