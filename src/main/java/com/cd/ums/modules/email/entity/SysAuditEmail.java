/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.entity;

import com.cd.ums.common.persistence.OperationData;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 邮件消息审核管理Entity
 * @author hqj
 * @version 2018-10-30
 */
public class SysAuditEmail extends DataEntity<SysAuditEmail> {
	
	private static final long serialVersionUID = 1L;
	private String sendBy;		// 邮件发送者
	private String receiverIds;		// 邮件接收者ids
	private String receiverEmails;		// 邮件接收者
	private String subject;		// 邮件主题
	private String content;		// 邮件内容
	private String attachName;		// 附件名称
	private String auditBy;		// 审核者
	private Date auditDate;		// 审核时间
	private String auditStatus;		// 审核状态
	private String auditReason;		// 审核原因

	private String auditStatusName; // 审核状态名称
	private List<OperationData> operations; // 操作权限
	
	public SysAuditEmail() {
		super();
	}

	public SysAuditEmail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="邮件发送者长度必须介于 0 和 64 之间")
	public String getSendBy() {
		return sendBy;
	}

	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	
	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}
	
	public String getReceiverEmails() {
		return receiverEmails;
	}

	public void setReceiverEmails(String receiverEmails) {
		this.receiverEmails = receiverEmails;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
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

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
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

	public List<OperationData> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationData> operations) {
		this.operations = operations;
	}
}