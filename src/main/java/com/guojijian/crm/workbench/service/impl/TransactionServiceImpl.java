package com.guojijian.crm.workbench.service.impl;

import com.guojijian.crm.commons.pojo.FunnelVN;
import com.guojijian.crm.commons.util.DateUtils;
import com.guojijian.crm.commons.util.UUIDUtils;
import com.guojijian.crm.workbench.mapper.TransactionMapper;
import com.guojijian.crm.workbench.pojo.Customer;
import com.guojijian.crm.workbench.pojo.Transaction;
import com.guojijian.crm.workbench.pojo.TransactionHistory;
import com.guojijian.crm.workbench.service.CustomerService;
import com.guojijian.crm.workbench.service.TransactionHistoryService;
import com.guojijian.crm.workbench.service.TransactionRemarkService;
import com.guojijian.crm.workbench.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private TransactionRemarkService transactionRemarkService;

    public int createTransaction(Transaction transaction) {
        //判断客户是否存在,不在则创建
        String customerId=customerService.queryCustomerIdByName(transaction.getCustomerId());
        if (customerId==null || "".equals(customerId)) {
            //创建客户
            Customer customer = new Customer();
            customer.setId(UUIDUtils.getUUID());
            customer.setName(transaction.getCustomerId());
            customerService.saveCustomer(customer);
            //为交易设置客户
            transaction.setCustomerId(customer.getId());
        }else{
            transaction.setCustomerId(customerId);
        }
        //创建交易记录
        TransactionHistory th=new TransactionHistory();
        th.setCreateBy(transaction.getCreateBy());
        th.setCreateDate(DateUtils.dataFormatDataTime(new Date()));
        th.setExpectedDate(transaction.getExpectedDate());
        th.setId(UUIDUtils.getUUID());
        th.setMoney(transaction.getMoney());
        th.setStage(transaction.getStage());
        th.setTranId(transaction.getId());
        transactionHistoryService.createTranHistory(th);

        return transactionMapper.insert(transaction);
    }

    public List<Transaction> queryTranForPageByCondition(Transaction transaction) {
        return transactionMapper.selectTranForPageByCondition(transaction);
    }

    public int dropTranByIds(String[] ids) {
        for(String id : ids) {
            //删除关联备注
            transactionRemarkService.dropTranRemarkByTranId(id);
            //删除关联的交易历史记录
            transactionHistoryService.dropTranHistoryByTranId(id);
        }
        return transactionMapper.deleteTranByIds(ids);
    }

    public Transaction queryTranForEditById(String id) {
        return transactionMapper.selectTranForEditById(id);
    }

    public int alterTran(Transaction transaction) {
        return transactionMapper.updateTran(transaction);
    }

    public Transaction queryTranForDetailById(String id) {
        return transactionMapper.selectTranForDetailById(id);
    }

    public List<FunnelVN> queryTranCountGroupByStage() {
        return transactionMapper.selectTranCountGroupByStage();
    }

    public int alterTranStageById(Transaction transaction) {
        return transactionMapper.updateTranStageById(transaction);
    }
}
