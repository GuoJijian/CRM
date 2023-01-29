package com.guojijian.crm.workbench.mapper;

import com.guojijian.crm.workbench.pojo.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueMapper {
    //根据id删除线索
    int deleteClueById(String id);

    int insert(Clue var1);

    List<Clue> selectAll();

    List<Clue> selectConditionClue(Clue var1);

    int insertClue(Clue var1);

    int deleteCluesById(@Param("ids") String[] var1);

    //通过id查询线索详情
    Clue selectClueForDetailById(String id);

    int updateClueById(Clue var1);

    Clue selectClueDetailById(String var1);

    //根据id查询线索
    Clue selectClueById(String id);

    //根据id查询线索的转换信息
    Clue selectClueForConvertById(String id);
}