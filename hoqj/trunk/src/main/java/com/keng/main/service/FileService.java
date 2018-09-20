package com.keng.main.service;
import java.util.List;
import java.util.Map;

import com.keng.main.entity.SysFiles;

public interface FileService {
    public int addFile(SysFiles... array);

    public SysFiles getFile(Integer fId);

    public List<SysFiles> getGroupFile(Integer gId);

    public int remFile(Integer fId);

    /**
     * @param fId 文件编号
     * @param gId 组编号
     */
    public void updateByGId(Integer fId, Integer gId);

    /**
     * @param gId 组编号
     * @param fileMode 文件类型
     * @param fId 文件编号
     */
    public void updateFileStatus(Integer gId, String fileMode, Integer fId);

    /**
     * @param image
     * @return
     */
    public SysFiles getNotDelFile(Integer image);

    public SysFiles getEffectivFile(Integer fId);

    public int updFileSort(Map<String, Object> inputMap);
}
