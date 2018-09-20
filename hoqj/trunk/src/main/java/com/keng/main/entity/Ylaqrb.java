package com.keng.main.entity;

import java.util.Date;

public class Ylaqrb {
    private String ks;

    private String sbry;

    private String flag;

    private String ylaqyh;

    private String yljfyh;

    private String yljf;

    private String ksjy;

    private Date sbrq;

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks == null ? null : ks.trim();
    }

    public String getSbry() {
        return sbry;
    }

    public void setSbry(String sbry) {
        this.sbry = sbry == null ? null : sbry.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getYlaqyh() {
        return ylaqyh;
    }

    public void setYlaqyh(String ylaqyh) {
        this.ylaqyh = ylaqyh == null ? null : ylaqyh.trim();
    }

    public String getYljfyh() {
        return yljfyh;
    }

    public void setYljfyh(String yljfyh) {
        this.yljfyh = yljfyh == null ? null : yljfyh.trim();
    }

    public String getYljf() {
        return yljf;
    }

    public void setYljf(String yljf) {
        this.yljf = yljf == null ? null : yljf.trim();
    }

    public String getKsjy() {
        return ksjy;
    }

    public void setKsjy(String ksjy) {
        this.ksjy = ksjy == null ? null : ksjy.trim();
    }

    public Date getSbrq() {
        return sbrq;
    }

    public void setSbrq(Date sbrq) {
        this.sbrq = sbrq;
    }
}