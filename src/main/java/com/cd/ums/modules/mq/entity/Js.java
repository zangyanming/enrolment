/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 教师管理Entity
 * @author zangyanming
 * @version 2018-10-13
 */
public class Js extends DataEntity<Js> {
	
	private static final long serialVersionUID = 1L;
	private String xlh;		// 序列号
	private String xxbm;		// 学校编码
	private String xxmc;		// 学校名称
	private String xk;		// 学科
	private String xm;		// 姓名
	private String sfzh;		// 身份证号码
	private String xb;		// 性别
	private String csrq;		// 出生日期
	private String sjhm;		// 手机号码
	private String email;		// 电子邮箱
	private String wxhm;		// 微信号码
	private String qqhm;		// QQ号码
	private String wbhm;		// 微博号码
	
	public Js() {
		super();
	}

	public Js(String id){
		super(id);
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	
	@Length(min=1, max=64, message="学校编码长度必须介于 1 和 64 之间")
	public String getXxbm() {
		return xxbm;
	}

	public void setXxbm(String xxbm) {
		this.xxbm = xxbm;
	}
	
	@Length(min=1, max=200, message="学校名称长度必须介于 1 和 200 之间")
	public String getXxmc() {
		return xxmc;
	}

	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}
	
	@Length(min=0, max=50, message="学科长度必须介于 0 和 50 之间")
	public String getXk() {
		return xk;
	}

	public void setXk(String xk) {
		this.xk = xk;
	}
	
	@Length(min=1, max=50, message="姓名长度必须介于 1 和 50 之间")
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	@Length(min=0, max=18, message="身份证号码长度必须介于 0 和 18 之间")
	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	
	@Length(min=0, max=64, message="性别长度必须介于 0 和 64 之间")
	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}
	
	@Length(min=0, max=100, message="出生日期长度必须介于 0 和 100 之间")
	public String getCsrq() {
		return csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	
	@Length(min=0, max=11, message="手机号码长度必须介于 0 和 11 之间")
	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	
	@Length(min=0, max=50, message="电子邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=50, message="微信号码长度必须介于 0 和 50 之间")
	public String getWxhm() {
		return wxhm;
	}

	public void setWxhm(String wxhm) {
		this.wxhm = wxhm;
	}
	
	@Length(min=0, max=50, message="QQ号码长度必须介于 0 和 50 之间")
	public String getQqhm() {
		return qqhm;
	}

	public void setQqhm(String qqhm) {
		this.qqhm = qqhm;
	}
	
	@Length(min=0, max=50, message="微博号码长度必须介于 0 和 50 之间")
	public String getWbhm() {
		return wbhm;
	}

	public void setWbhm(String wbhm) {
		this.wbhm = wbhm;
	}
	
}