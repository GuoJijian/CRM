package com.guojijian.crm.workbench.service;

import com.guojijian.crm.workbench.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    /**
     * 根据条件查询所有的线索
     */
    List<Clue> queryConditionClue(Clue clue);

    /**
     * 添加新线索
     */
    int saveClue(Clue clue);

    /**
     * 根据id批量删除线索
     */
    int dropCluesById(String[] ids);

    /**
     * 根据id查询线索
     */
    Clue queryClueById(String id);

    /**
     * 根据id修改线索
     */
    int alterClueById(Clue clue);

    /**
     * 根据id查询线索的详细信息
     */
    Clue queryClueDetailById(String id);

    /**
     * 根据id查询线索部分信息
     */
    Clue queryClueInfoById(String id);

    /**
     * 根据id查询线索的转换信息
     */
    Clue queryClueForConvertById(String id);

    /**
     *保存转换操作
     */
    void saveConvert(Map<String,Object> map);

    /**
     * 根据id删除线索
     */
    int dropClueById(String id);
}
