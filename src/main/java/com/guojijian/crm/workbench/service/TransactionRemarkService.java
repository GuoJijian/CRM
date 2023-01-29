package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.Transaction;
import com.guojijian.crm.workbench.pojo.TransactionRemark;

import java.util.List;

public interface TransactionRemarkService {
    /**
     * 批量添加交易备注
     */
    int insertTransactionRemarks(List<TransactionRemark> trList);

    /**
     * 根据交易id查询备注
     */
    List<TransactionRemark> queryTranRemarkById(String tranId);

    /**
     * 根据id修改备注
     */
    int alterTranRemark(TransactionRemark transactionRemark);

    /**
     * 添加备注
     */
    int createTranRemark(TransactionRemark transactionRemark);

    /**
     * 根据id删除备注
     */
    int dropTranRemarkById(String id);

    /**
     * 根据交易id删除相关联的备注
     */
    int dropTranRemarkByTranId(String tranId);
}
