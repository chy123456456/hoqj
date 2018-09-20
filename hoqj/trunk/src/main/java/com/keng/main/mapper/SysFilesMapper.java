package com.keng.main.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.keng.main.entity.SysFiles;

public interface SysFilesMapper {
    int deleteByPrimaryKey(Integer fId);

    int insert(SysFiles record);

    int insertSelective(SysFiles record);

    SysFiles selectByPrimaryKey(Integer fId);

    int updateByPrimaryKeySelective(SysFiles record);

    int updateByPrimaryKey(SysFiles record);

    int deleteByGId(Integer gId);

    List<SysFiles> selectByGId(Integer gId);

    /**
     * @param fId 文件编号
     * @param gId 组编号
     */
    void updateByGId(@Param("fId") Integer fId, @Param("gId") Integer gId);

    /**
     * @param gId 组编号
     * @param fileMode 文件类型
     * @param fId 文件编号
     */
    void updateFileStatus(@Param("gId") Integer gId, @Param("fileMode") String fileMode, @Param("fId") Integer fId);

    /**
     * 删除文件
     * 
     * @param fId 文件编号
     * @return 影响记录条数
     */
    int deleteFile(@Param("fId") Integer fId);

    int updateGid(@Param("oldGid") Integer oldGid, @Param("newGid") Integer newGid);

    public SysFiles selectEffectivByPK(Integer fId);

    int updFileSort(Map<String, Object> inputMap);
}