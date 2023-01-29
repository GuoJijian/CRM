package com.guojijian.crm.workbench.mapper;

import com.guojijian.crm.workbench.pojo.TransactionHistory;
import java.util.List;

public interface TransactionHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue Jan 17 09:15:54 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue Jan 17 09:15:54 CST 2023
     */
    TransactionHistory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue Jan 17 09:15:54 CST 2023
     */
    List<TransactionHistory> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue Jan 17 09:15:54 CST 2023
     */
    int updateByPrimaryKey(TransactionHistory record);


    /**
     * 添加新交易历史
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Tue Jan 17 09:15:54 CST 2023
     */
    int insertTranHistory(TransactionHistory transactionHistory);

    /**
     * 根据交易id删除关联的交易历史记录
     */
    int deleteTranHistoryByTranId(String tranId);

    /**
     * 根据交易id查询关联的交易历史记录
     */
    List<TransactionHistory> queryTranHistoryByTranId(String tranId);
}