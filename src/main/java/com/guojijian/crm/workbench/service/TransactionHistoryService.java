package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.TransactionHistory;

import java.util.List;

public interface TransactionHistoryService {
    /**
     * 添加新交易历史记录
     */
    int createTranHistory(TransactionHistory transactionHistory);

    /**
     * 根据交易id删除关联的交易历史记录
     */
    int dropTranHistoryByTranId(String tranId);

    /**
     * 根据交易id查询关联的交易历史记录
     */
    List<TransactionHistory> queryTranHistoryByTranId(String tranId);
}
