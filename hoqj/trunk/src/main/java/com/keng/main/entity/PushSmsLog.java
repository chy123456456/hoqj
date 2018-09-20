package com.keng.main.entity;

import java.util.Date;

public class PushSmsLog {
    private Long id;

    private String orderNo;

    private Date payTime;

    private Integer dealpushStatus;

    private Integer transferpushStatus;

    private Date dealpushSendTime;

    private Date dealpushReveTime;

    private Date transferpushSendTime;

    private Date transferpushReveTime;

    private Integer dealSmsStatus;

    private Integer transferSmsStatus;
    
    private String billId;
    
    private Integer pushdCount;

    private Integer pushtCount;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getDealpushStatus() {
        return dealpushStatus;
    }

    public void setDealpushStatus(Integer dealpushStatus) {
        this.dealpushStatus = dealpushStatus;
    }

    public Integer getTransferpushStatus() {
        return transferpushStatus;
    }

    public void setTransferpushStatus(Integer transferpushStatus) {
        this.transferpushStatus = transferpushStatus;
    }

    public Date getDealpushSendTime() {
        return dealpushSendTime;
    }

    public void setDealpushSendTime(Date dealpushSendTime) {
        this.dealpushSendTime = dealpushSendTime;
    }

    public Date getDealpushReveTime() {
        return dealpushReveTime;
    }

    public void setDealpushReveTime(Date dealpushReveTime) {
        this.dealpushReveTime = dealpushReveTime;
    }

    public Date getTransferpushSendTime() {
        return transferpushSendTime;
    }

    public void setTransferpushSendTime(Date transferpushSendTime) {
        this.transferpushSendTime = transferpushSendTime;
    }

    public Date getTransferpushReveTime() {
        return transferpushReveTime;
    }

    public void setTransferpushReveTime(Date transferpushReveTime) {
        this.transferpushReveTime = transferpushReveTime;
    }

    public Integer getDealSmsStatus() {
        return dealSmsStatus;
    }

    public void setDealSmsStatus(Integer dealSmsStatus) {
        this.dealSmsStatus = dealSmsStatus;
    }

    public Integer getTransferSmsStatus() {
        return transferSmsStatus;
    }

    public void setTransferSmsStatus(Integer transferSmsStatus) {
        this.transferSmsStatus = transferSmsStatus;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getPushdCount() {
        return pushdCount;
    }

    public void setPushdCount(Integer pushdCount) {
        this.pushdCount = pushdCount;
    }

    public Integer getPushtCount() {
        return pushtCount;
    }

    public void setPushtCount(Integer pushtCount) {
        this.pushtCount = pushtCount;
    }

    
    
    
}