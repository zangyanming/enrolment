/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.entity;

import com.cd.ums.common.persistence.OperationData;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 微信消息审核管理Entity
 * @author hqj
 * @version 2018-10-31
 */
public class SysAuditWx extends DataEntity<SysAuditWx> {
	
	private static final long serialVersionUID = 1L;
	private String senderOfficeId;		// 微信发送部门id
	private String senderId;		// 微信发送者id
	private String receiverIds;		// 微信接收者ids
	private String receiverNames;		// 微信接收者姓名
	private String receiverOpenids;		// 微信接收者openids
	private String content;		// 微信消息内容
	private String auditBy;		// 审核者
	private Date auditDate;		// 审核时间
	private String auditStatus;		// 审核状态
	private String auditReason;		// 审核原因

	private String auditStatusName; // 审核状态名称
	private List<OperationData> operations; // 操作权限

	private String senderOfficeName; // 微信发送部门名称
	private String senderName;       // 微信发送者姓名
	
	public SysAuditWx() {
		super();
	}

	public SysAuditWx(String id){
		super(id);
	}

	@Length(min=0, max=64, message="微信发送部门id长度必须介于 0 和 64 之间")
	public String getSenderOfficeId() {
		return senderOfficeId;
	}

	public void setSenderOfficeId(String senderOfficeId) {
		this.senderOfficeId = senderOfficeId;
	}
	
	@Length(min=0, max=64, message="微信发送者id长度必须介于 0 和 64 之间")
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
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
	
	public String getReceiverOpenids() {
		return receiverOpenids;
	}

	public void setReceiverOpenids(String receiverOpenids) {
		this.receiverOpenids = receiverOpenids;
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

	public String getSenderOfficeName() {
		return senderOfficeName;
	}

	public void setSenderOfficeName(String senderOfficeName) {
		this.senderOfficeName = senderOfficeName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
}