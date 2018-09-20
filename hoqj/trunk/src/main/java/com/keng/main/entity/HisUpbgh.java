package com.keng.main.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * HIS系统排班挂号表
 * 
 * @author Administrator
 *
 */
public class HisUpbgh implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String deptId;
	private String doctorId;
	private Date date;
	private String ghType;
	private String ghBc;
	private String deptZs;
	private Date beginDate;
	private Date endDate;
	private Integer numPlan;
	private Integer num;
	private Integer numXh;
	private Date opDate;
	private String opName;
	private Date dateSno;
	private BigDecimal yyNum;
	private BigDecimal yyCount;
	private BigDecimal yyXh;
	private BigDecimal yyYg;
	private String sourcesSystemId;
	private String visitNo;
	private String scheduleOccasion;
	private String clinicLabel;
	private String status;
	private String regId;
	private Integer jzStatus;
	private String  bDate;
	private String eDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGhType() {
		return ghType;
	}

	public void setGhType(String ghType) {
		this.ghType = ghType;
	}

	public String getGhBc() {
		return ghBc;
	}

	public void setGhBc(String ghBc) {
		this.ghBc = ghBc;
	}

	public String getDeptZs() {
		return deptZs;
	}

	public void setDeptZs(String deptZs) {
		this.deptZs = deptZs;
	}

	public Integer getNumPlan() {
		return numPlan;
	}

	public void setNumPlan(Integer numPlan) {
		this.numPlan = numPlan;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getNumXh() {
		return numXh;
	}

	public void setNumXh(Integer numXh) {
		this.numXh = numXh;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Date getDateSno() {
		return dateSno;
	}

	public void setDateSno(Date dateSno) {
		this.dateSno = dateSno;
	}

	public BigDecimal getYyNum() {
		return yyNum;
	}

	public void setYyNum(BigDecimal yyNum) {
		this.yyNum = yyNum;
	}

	public BigDecimal getYyCount() {
		return yyCount;
	}

	public void setYyCount(BigDecimal yyCount) {
		this.yyCount = yyCount;
	}

	public BigDecimal getYyXh() {
		return yyXh;
	}

	public void setYyXh(BigDecimal yyXh) {
		this.yyXh = yyXh;
	}

	public BigDecimal getYyYg() {
		return yyYg;
	}

	public void setYyYg(BigDecimal yyYg) {
		this.yyYg = yyYg;
	}

	public String getSourcesSystemId() {
		return sourcesSystemId;
	}

	public void setSourcesSystemId(String sourcesSystemId) {
		this.sourcesSystemId = sourcesSystemId;
	}

	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}

	public String getScheduleOccasion() {
		return scheduleOccasion;
	}

	public void setScheduleOccasion(String scheduleOccasion) {
		this.scheduleOccasion = scheduleOccasion;
	}

	public String getClinicLabel() {
		return clinicLabel;
	}

	public void setClinicLabel(String clinicLabel) {
		this.clinicLabel = clinicLabel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public Integer getJzStatus() {
		return jzStatus;
	}

	public void setJzStatus(Integer jzStatus) {
		this.jzStatus = jzStatus;
	}

	public String getbDate() {
		return bDate;
	}

	public void setbDate(String bDate) {
		this.bDate = bDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	@Override
	public String toString() {
		return "HisUpbgh [id=" + id + ", deptId=" + deptId + ", doctorId=" + doctorId + ", date=" + date + ", ghType="
				+ ghType + ", ghBc=" + ghBc + ", deptZs=" + deptZs + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", numPlan=" + numPlan + ", num=" + num + ", numXh=" + numXh + ", opDate=" + opDate + ", opName="
				+ opName + ", dateSno=" + dateSno + ", yyNum=" + yyNum + ", yyCount=" + yyCount + ", yyXh=" + yyXh
				+ ", yyYg=" + yyYg + ", sourcesSystemId=" + sourcesSystemId + ", visitNo=" + visitNo
				+ ", scheduleOccasion=" + scheduleOccasion + ", clinicLabel=" + clinicLabel + ", status=" + status
				+ ", regId=" + regId + ", jzStatus=" + jzStatus + ", bDate=" + bDate + ", eDate=" + eDate + "]";
	}

}
