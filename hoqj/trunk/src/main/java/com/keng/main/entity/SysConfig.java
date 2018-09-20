package com.keng.main.entity;
import java.io.Serializable;

public class SysConfig extends SysConfigKey implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private String            cfgVal;
    private String            remark;
    private String            parentCfg;
    private String            parentKey;
    private Integer           status;
    private Integer           sort;

    public String getCfgVal() {
        return cfgVal;
    }

    public void setCfgVal(String cfgVal) {
        this.cfgVal = cfgVal == null ? null : cfgVal.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getParentCfg() {
        return parentCfg;
    }

    public void setParentCfg(String parentCfg) {
        this.parentCfg = parentCfg == null ? null : parentCfg.trim();
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey == null ? null : parentKey.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SysConfig [id=" + id + ", cfgVal=" + cfgVal + ", remark=" + remark + ", parentCfg=" + parentCfg + ", parentKey=" + parentKey
                + ", status=" + status + ", sort=" + sort + "]";
    }
}