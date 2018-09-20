package com.keng.main.entity;

import java.util.Date;

public class Zdssrb {
    @Override
	public String toString() {
		return "Zdssrb [zyh=" + zyh + ", ks=" + ks + ", xm=" + xm + ", xb=" + xb + ", nl=" + nl + ", nldw=" + nldw
				+ ", ryrq=" + ryrq + ", zyzd=" + zyzd + ", ssmc=" + ssmc + ", sbr=" + sbr + ", sbsj=" + sbsj + ", bz1="
				+ bz1 + ", bz2=" + bz2 + "]";
	}

	private String zyh;

    private String ks;

    private String xm;

    private String xb;

    private Float nl;

    private String nldw;

    private Date ryrq;

    private String zyzd;

    private String ssmc;

    private String sbr;

    private Date sbsj;

    private String bz1;

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

    public String getSsmc() {
        return ssmc;
    }

    public void setSsmc(String ssmc) {
        this.ssmc = ssmc == null ? null : ssmc.trim();
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