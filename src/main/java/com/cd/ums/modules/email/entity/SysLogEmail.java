/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 邮件日志管理Entity
 * @author hqj
 * @version 2018-10-18
 */
public class SysLogEmail extends DataEntity<SysLogEmail> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 日志标题
	private String remoteAddr;		// 操作IP地址
	private String userAgent;		// 用户代理
	private String requestUri;		// 请求URI
	private String method;		// 操作方式
	private String params;		// 操作提交的数据
	private String sendBy;		// 邮件发送者
	private String receiveBy;		// 邮件接收者
	private Date sendDate;		// 发送时间
	private String attachName;		// 附件名称
	private String success;		// 消息状态
	private String exception;		// 异常信息
	private Date beginSendDate;		// 开始 发送时间
	private Date endSendDate;		// 结束 发送时间
	
	public SysLogEmail() {
		super();
	}

	public SysLogEmail(String id){
		super(id);
	}

	@Length(min=0, max=255, message="日志标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="操作IP地址长度必须介于 0 和 255 之间")
	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	@Length(min=0, max=255, message="用户代理长度必须介于 0 和 255 之间")
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	@Length(min=0, max=255, message="请求URI长度必须介于 0 和 255 之间")
	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	
	@Length(min=0, max=5, message="操作方式长度必须介于 0 和 5 之间")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	@Length(min=0, max=64, message="邮件发送者长度必须介于 0 和 64 之间")
	public String getSendBy() {
		return sendBy;
	}

	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	
	public String getReceiveBy() {
		return receiveBy;
	}

	public void setReceiveBy(String receiveBy) {
		this.receiveBy = receiveBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	
	@Length(min=0, max=1, message="消息状态长度必须介于 0 和 1 之间")
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
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