package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    /**
     * 根据线索id查询所有的线索备注
     */
    List<ClueRemark> queryAllClueRemarkByClueId(String clueId);

    /**
     * 添加线索备注
     */
    int saveClueRemark(ClueRemark clueRemark);

    /**
     * 根据id删除备注
     */
    int dropClueRemarkById(String id);

    /**
     * 根据id更改备注
     */
    int alterClueRemarkById(ClueRemark clueRemark);

    /**
     * 根据线索id查询备注的转换信息
     */
    List<ClueRemark> queryClueRemarkForConvertByClueId(String clueId);

    /**
     * 根据线索id批量删除线索备注
     */
    int dropClueRemarksByClueId(String clueId);
}
