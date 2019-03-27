/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import com.cd.ums.common.persistence.OperationData;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 应用系统消息审核管理Entity
 * @author hqj
 * @version 2018-10-30
 */
public class SysAuditSysmsg extends DataEntity<SysAuditSysmsg> {
	
	private static final long serialVersionUID = 1L;
	private String sendName;		// 请求系统名称
	private String sendCode;		// 请求系统编码
	private String sendKey;		// 请求系统密钥
	private String receiverIds;		// 目标系统ids
	private String receiverNames;		// 目标系统名称
	private String receiverCodes;		// 目标系统编码
	private String content;		// 消息内容
	private String auditBy;		// 审核者
	private Date auditDate;		// 审核时间
	private String auditStatus;		// 审核状态
	private String auditReason;		// 审核原因

	private String auditStatusName;		// 审核状态
	private List<OperationData> operations; // 操作权限
	
	public SysAuditSysmsg() {
		super();
	}

	public SysAuditSysmsg(String id){
		super(id);
	}

	@Length(min=0, max=100, message="请求系统名称长度必须介于 0 和 100 之间")
	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	
	@Length(min=0, max=100, message="请求系统编码长度必须介于 0 和 100 之间")
	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}
	
	@Length(min=0, max=100, message="请求系统密钥长度必须介于 0 和 100 之间")
	public String getSendKey() {
		return sendKey;
	}

	public void setSendKey(String sendKey) {
		this.sendKey = sendKey;
	}
	
	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}
	
	public String getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(String receiverNames) {
		this.receiverNames = receiverNames;
	}
	
	public String getReceiverCodes() {
		return receiverCodes;
	}

	public void setReceiverCodes(String receiverCodes) {
		this.receiverCodes = receiverCodes;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="审核者长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=1, message="审核状态长度必须介于 0 和 1 之间")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public List<OperationData> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationData> operations) {
		this.operations = operations;
	}
}