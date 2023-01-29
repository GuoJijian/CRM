package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.workbench.mapper.TransactionRemarkMapper;
import com.guojijian.crm.workbench.pojo.TransactionRemark;
import com.guojijian.crm.workbench.service.TransactionRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("transactionRemarkService")
public class TransactionRemarkServiceImpl implements TransactionRemarkService {

    @Autowired
    private TransactionRemarkMapper transactionRemarkMapper;

    public int insertTransactionRemarks(List<TransactionRemark> trList) {
        return transactionRemarkMapper.insertTransactionRemarks(trList);
    }

    public List<TransactionRemark> queryTranRemarkById(String tranId) {
        return transactionRemarkMapper.selectTranRemarkById(tranId);
    }

    public int alterTranRemark(TransactionRemark transactionRemark) {
        return transactionRemarkMapper.updateTranRemark(transactionRemark);
    }

    public int createTranRemark(TransactionRemark transactionRemark) {
        return transactionRemarkMapper.insertTranRemark(transactionRemark);
    }

    public int dropTranRemarkById(String id) {
        return transactionRemarkMapper.deleteTranRemarkById(id);
    }

    public int dropTranRemarkByTranId(String tranId) {
        return transactionRemarkMapper.deleteTranRemarkByTranId(tranId);
    }
}
