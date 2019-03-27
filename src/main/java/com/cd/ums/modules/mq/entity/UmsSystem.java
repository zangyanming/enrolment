/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 系统消息申请管理Entity
 * @author hqj
 * @version 2018-10-07
 */
public class UmsSystem extends DataEntity<UmsSystem> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 系统名称
	private String applicationUnit;		// 申请单位
	private String applicant;		// 申请人
	private String email;		// 申请人邮箱
	private String mobile;		// 申请人手机
	private String phone;		// 申请人电话
	private String auditBy;		// 审核人
	private Date auditDate;		// 审核时间
	private String auditStatus;		// 审核状态
	private String auditReason;		// 审核原因
	private Date validDate;		// 有效时间
	private String sysCode;		// 系统编码
	private String sysKey;		// 密钥
	private String queueName;		// 队列名称
	private String topicName;		// 话题名称

	private String auditStatusName;		// 审核状态名称
	private List<String> operations; // 操作权限

	private String[] ids;  // 查询使用
	private String status; // 审核状态，原审核状态有默认初始值，查询不可用

	private String eqName;    // 查询系统名称
	private String eqSysCode; // 查询系统编码
	
	public UmsSystem() {
		super();
		this.auditStatus = AUDIT_STATUS_APPLY;
	}

	public UmsSystem(String id){
		super(id);
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=1, max=100, message="系统名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=100, message="申请单位长度必须介于 1 和 100 之间")
	public String getApplicationUnit() {
		return applicationUnit;
	}

	public void setApplicationUnit(String applicationUnit) {
		this.applicationUnit = applicationUnit;
	}
	
	@Length(min=1, max=100, message="申请人长度必须介于 1 和 100 之间")
	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	@Length(min=1, max=200, message="申请人邮箱长度必须介于 1 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=1, max=11, message="申请人手机长度必须介于 1 和 11 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=20, message="申请人电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=64, message="审核人长度必须介于 0 和 64 之间")
	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	@Length(min=1, max=1, message="审核状态长度必须介于 1 和 1 之间")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=200, message="审核原因长度必须介于 0 和 200 之间")
	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
	@Length(min=0, max=100, message="系统编码长度必须介于 0 和 100 之间")
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	@Length(min=0, max=100, message="密钥长度必须介于 0 和 100 之间")
	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	
	@Length(min=0, max=64, message="队列名称长度必须介于 0 和 64 之间")
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	@Length(min=0, max=64, message="话题名称长度必须介于 0 和 64 之间")
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getEqName() {
		return eqName;
	}

	public void setEqName(String eqName) {
		this.eqName = eqName;
	}

	public String getEqSysCode() {
		return eqSysCode;
	}

	public void setEqSysCode(String eqSysCode) {
		this.eqSysCode = eqSysCode;
	}

	/**
	 * 审核标记（0：申请；1：通过；2：不通过；3：停用；9：超期；）
	 */
	public static final String AUDIT_STATUS_APPLY = "0";
	public static final String AUDIT_STATUS_PASS = "1";
	public static final String AUDIT_STATUS_NOT_PASS = "2";
	public static final String AUDIT_STATUS_STOP = "3";
	public static final String AUDIT_STATUS_OVERDUE = "9";
}