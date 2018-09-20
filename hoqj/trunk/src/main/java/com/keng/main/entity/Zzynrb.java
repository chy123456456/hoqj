package com.keng.main.entity;

import java.util.Date;
/**
 * 重症疑难患者
 * @author Administrator
 *
 */
public class Zzynrb {
	/**
	 * 住院号
	 */
    private String zyh;

    /**
     * 科室
     */
    private String ks;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 性别
     */
    private String xb;

    /**
     * 年龄
     */
    private Float nl;

    /**
     * 年龄单位
     */
    private String nldw;

    /**
     * 入院日期
     */
    private Date ryrq;

    /**
     * 住院诊断
     */
    private String zyzd;

    /**
     * 目前情况
     */
    private String mqqk;

    /**
     * 上报人
     */
    private String sbr;

    /**
     * 上报时间
     */
    private Date sbsj;

    /**
     * 备注1
     */
    private String bz1;

    /**
     * 备注2
     */
    private String bz2;

    public String getZyh() {
        return zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh == null ? null : zyh.trim();
    }

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks == null ? null : ks.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb == null ? null : xb.trim();
    }

    public Float getNl() {
        return nl;
    }

    public void setNl(Float nl) {
        this.nl = nl;
    }

    public String getNldw() {
        return nldw;
    }

    public void setNldw(String nldw) {
        this.nldw = nldw == null ? null : nldw.trim();
    }

    public Date getRyrq() {
        return ryrq;
    }

    public void setRyrq(Date ryrq) {
        this.ryrq = ryrq;
    }

    public String getZyzd() {
        return zyzd;
    }

    public void setZyzd(String zyzd) {
        this.zyzd = zyzd == null ? null : zyzd.trim();
    }

    public String getMqqk() {
        return mqqk;
    }

    public void setMqqk(String mqqk) {
        this.mqqk = mqqk == null ? null : mqqk.trim();
    }

    public String getSbr() {
        return sbr;
    }

    public void setSbr(String sbr) {
        this.sbr = sbr == null ? null : sbr.trim();
    }

    public Date getSbsj() {
        return sbsj;
    }

    public void setSbsj(Date sbsj) {
        this.sbsj = sbsj;
    }

    public String getBz1() {
        return bz1;
    }

    public void setBz1(String bz1) {
        this.bz1 = bz1 == null ? null : bz1.trim();
    }

    public String getBz2() {
        return bz2;
    }

    public void setBz2(String bz2) {
        this.bz2 = bz2 == null ? null : bz2.trim();
    }
}