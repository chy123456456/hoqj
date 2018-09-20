package com.keng.main.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.keng.main.entity.SysFiles;
import com.keng.main.mapper.SysFilesMapper;
import com.keng.main.service.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService {
    @Autowired
    private SysFilesMapper sysFileMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
    public int addFile(SysFiles... array) {
        /*
         * if (array.length > 0) { Integer relateId = array[0].getRelateId(); if (relateId != null) {
         * fileMapper.deleteByRelateId(relateId); } }
         */
        int count = 0;
        for (SysFiles sysFiles : array) {
            int sort = 1;
            if (sysFiles.getgId() != null) {
                List<SysFiles> list = sysFileMapper.selectByGId(sysFiles.getgId());
                if (list.size() > 0) {
                    sort = list.get(list.size() - 1).getSort();
                    sysFiles.setSort(sort + 1);
                } else {
                    sysFiles.setSort(sort);
                }
            }
            count += sysFileMapper.insertSelective(sysFiles);
        }
        // soMgr.log("添加"+count+"个文件：");
        return count;
    }

    @Override
    public SysFiles getFile(Integer id) {
        return sysFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysFiles> getGroupFile(Integer relateId) {
        return sysFileMapper.selectByGId(relateId);
    }

    @Override
    public int remFile(Integer fId) {
        // soMgr.log("删除文件："+fId);
        return sysFileMapper.deleteFile(fId);
    }

    @Override
    public void updateByGId(Integer fId, Integer gId) {
        // soMgr.log("修改文件：fId:"+fId+"gId:"+gId);
        sysFileMapper.updateByGId(fId, gId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ym.ax12580.services.common.IFileMgrSv#updateFileStatus(java.lang.Integer, java.lang.String,
     * java.lang.Integer)
     */
    @Override
    public void updateFileStatus(Integer gId, String fileMode, Integer fId) {
        sysFileMapper.updateFileStatus(gId, fileMode, fId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ym.tong.services.common.IFileMgrSv#getNotDelFile(java.lang.Integer)
     */
    @Override
    public SysFiles getNotDelFile(Integer image) {
        SysFiles file = this.getFile(image);
        if (file != null && 0 == file.getFileStatus()) {// 删除状态
            return null;
        }
        return file;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ym.tong.services.common.IFileMgrSv#getEffectivFile(java.lang.Integer)
     */
    @Override
    public SysFiles getEffectivFile(Integer fId) {
        return sysFileMapper.selectEffectivByPK(fId);
    }

    @Override
    public int updFileSort(Map<String, Object> inputMap) {
        return sysFileMapper.updFileSort(inputMap);
    }
}
