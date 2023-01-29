package com.guojijian.crm.workbench.mapper;

import com.guojijian.crm.workbench.pojo.ClueRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Mon Jan 02 10:52:02 CST 2023
     */
    int insert(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Mon Jan 02 10:52:02 CST 2023
     */
    ClueRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Mon Jan 02 10:52:02 CST 2023
     */
    List<ClueRemark> selectAll();

    /**
     * 根据线索id查询所有的线索备注
     * @return
     */
    List<ClueRemark> selectAllClueRemarkByClueId(String clueId);

    /**
     * 添加线索备注
     */
    int insertClueRemarkByClueId(ClueRemark clueRemark);

    /**
     * 根据d备注i删除备注
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Mon Jan 02 10:52:02 CST 2023
     */
    int deleteClueRemarkById(String id);


    /**
     * 根据id更改备注
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Mon Jan 02 10:52:02 CST 2023
     */
    int updateById(ClueRemark clueRemark);

    /**
     * 根据线索id查询备注的转换信息
     */
    List<ClueRemark> selectClueRemarkForConvertByClueId(String clueId);

    /**
     * 根据线索id批量删除备注
     */
    int deleteClueRemarksByClueId(String clueId);
}