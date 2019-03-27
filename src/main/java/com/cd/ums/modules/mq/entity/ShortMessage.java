/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;

import java.beans.Transient;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 短信消息管理Entity
 * @author zangyanming
 * @version 2018-10-20
 */
public class ShortMessage extends DataEntity<ShortMessage> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 内容
	private String senderOfficeId;		// 发送者单位
	private String senderId;		// 发送者
	private String receiverIds;		// 接收者
	private String receiverNames;	// 接收者中文名
	private String ischeck;		// 是否需要审核
	private String checkerId;		// 消息审核人
	private String checkState;		// 审核状态 0:未审核 1:审核通过 2:审核未通过
	private String reason;		// 审核未通过原因
	private Date checkDate;		// 审核时间
	private String checkerOfficeId;		// 审核人单位
	private Date sendDate;		// 发送时间

	private String success; // 0失败 1成功
	private String exception; // 失败原因

	private Boolean audit; // 是否审核，f不审核，t审核

	private String senderOfficeName; // 短信发送部门名称
	private String senderName;       // 短信发送者姓名
	private String checkerName;       // 短信审核者姓名

	private Date beginSendDate;		// 开始 发送时间
	private Date endSendDate;		// 结束 发送时间

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public ShortMessage() {
		super();
	}

	public ShortMessage(String id){
		super(id);
	}

	@Length(min=1, max=1000, message="内容长度必须介于 1 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=255, message="发送者单位长度必须介于 0 和 255 之间")
	public String getSenderOfficeId() {
		return senderOfficeId;
	}

	public void setSenderOfficeId(String senderOfficeId) {
		this.senderOfficeId = senderOfficeId;
	}
	
	@Length(min=0, max=30, message="发送者长度必须介于 0 和 64 之间")
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	@Length(min=0, max=5000, message="接收者长度必须介于 0 和 5000 之间")
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

	@Length(min=0, max=10, message="是否需要审核长度必须介于 0 和 10 之间")
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	@Length(min=0, max=30, message="消息审核人长度必须介于 0 和 30 之间")
	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	
	@Length(min=0, max=255, message="审核状态 0:未审核 1:审核通过 2:审核未通过长度必须介于 0 和 255 之间")
	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	
	@Length(min=0, max=255, message="审核未通过原因长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=64, message="审核人单位长度必须介于 0 和 64 之间")
	public String getCheckerOfficeId() {
		return checkerOfficeId;
	}

	public void setCheckerOfficeId(String checkerOfficeId) {
		this.checkerOfficeId = checkerOfficeId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
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

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Date getBeginSendDate() {
		return beginSendDate;
	}

	public void setBeginSendDate(Date beginSendDate) {
		this.beginSendDate = beginSendDate;
	}

	public Date getEndSendDate() {
		return endSendDate;
	}

	public void setEndSendDate(Date endSendDate) {
		this.endSendDate = endSendDate;
	}
}