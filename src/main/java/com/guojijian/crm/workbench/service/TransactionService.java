package com.guojijian.crm.workbench.service;

import com.guojijian.crm.commons.pojo.FunnelVN;
import com.guojijian.crm.workbench.pojo.Transaction;

import java.util.List;

public interface TransactionService {
    /**
     * 添加新交易
     */
    int createTransaction(Transaction transaction);

    /**
     * 根据条件分页查询交易
     */
    List<Transaction> queryTranForPageByCondition(Transaction transaction);

    /**
     * 根据ids批量删除交易
     */
    int dropTranByIds(String[] ids);

    /**
     * 根据id查询交易的编辑信息
     */
    Transaction queryTranForEditById(String id);

    /**
     * 修改交易
     */
    int alterTran(Transaction transaction);

    /**
     * 根据id查询交易的详情
     */
    Transaction queryTranForDetailById(String id);

    /**
     * 通过阶段分组查询交易量
     */
    List<FunnelVN> queryTranCountGroupByStage();

    /**
     * 根据交易id修改阶段
     */
    int alterTranStageById(Transaction transaction);
}
