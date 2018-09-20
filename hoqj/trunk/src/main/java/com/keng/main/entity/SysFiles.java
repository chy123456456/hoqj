package com.keng.main.entity;

import java.util.Date;

public class SysFiles {
    private Integer fId;
    private Integer gId;
    private String  remoteName;
    private String  localName;
    private String  savePath;
    private String  fileMode;
    private String  fileType;
    private Long    fileSize;
    private Integer fileStatus;
    private Date    uploadTime;
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName == null ? null : remoteName.trim();
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName == null ? null : localName.trim();
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath == null ? null : savePath.trim();
    }

    public String getFileMode() {
        return fileMode;
    }

    public void setFileMode(String fileMode) {
        this.fileMode = fileMode == null ? null : fileMode.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "fId=" + fId + ",gId=" + gId;
    }
}