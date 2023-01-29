package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.TransactionHistoryMapper;
import com.guojijian.crm.workbench.pojo.TransactionHistory;
import com.guojijian.crm.workbench.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("transactionHistoryService")
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    public int createTranHistory(TransactionHistory transactionHistory) {
        return transactionHistoryMapper.insertTranHistory(transactionHistory);
    }

    public int dropTranHistoryByTranId(String tranId) {
        return transactionHistoryMapper.deleteTranHistoryByTranId(tranId);
    }

    public List<TransactionHistory> queryTranHistoryByTranId(String tranId) {
        return transactionHistoryMapper.queryTranHistoryByTranId(tranId);
    }
}
