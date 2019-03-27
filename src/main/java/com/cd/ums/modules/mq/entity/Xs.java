/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 学生管理Entity
 * @author zangyanming
 * @version 2018-10-13
 */
public class Xs extends DataEntity<Xs> {
	
	private static final long serialVersionUID = 1L;
	private String xlh;		// 序列号
	private String xxbm;		// 学校代码
	private String bjid;		// 班级ID
	private String xsid;		// 学生ID
	private String xd;		// 学段
	private String nj;		// 年级
	private String bj;		// 班级
	private String xm;		// 姓名
	private String xb;		// 性别
	private Date csrq;		// 出生日期
	private String sfzh;		// 身份证号码
	private String xszh;		// 学生证号
	private String mz;		// 民族
	private String hjdz;		// 户籍地址
	private String sjhm;		// 手机号码
	private String wxhm;		// 微信号码
	private String email;		// 电子邮箱
	private String qqhm;		// QQ号码
	private String wbhm;		// 微博号码
	
	public Xs() {
		super();
	}

	public Xs(String id){
		super(id);
	}

	@Length(min=1, max=64, message="序列号长度必须介于 1 和 64 之间")
	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	
	@Length(min=1, max=64, message="学校代码长度必须介于 1 和 64 之间")
	public String getXxbm() {
		return xxbm;
	}

	public void setXxbm(String xxbm) {
		this.xxbm = xxbm;
	}
	
	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}
	
	@Length(min=1, max=64, message="学生ID长度必须介于 1 和 64 之间")
	public String getXsid() {
		return xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}
	
	@Length(min=0, max=64, message="学段长度必须介于 0 和 64 之间")
	public String getXd() {
		return xd;
	}

	public void setXd(String xd) {
		this.xd = xd;
	}
	
	@Length(min=0, max=30, message="年级长度必须介于 0 和 30 之间")
	public String getNj() {
		return nj;
	}

	public void setNj(String nj) {
		this.nj = nj;
	}
	
	@Length(min=0, max=100, message="班级长度必须介于 0 和 100 之间")
	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}
	
	@Length(min=1, max=30, message="姓名长度必须介于 1 和 30 之间")
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	@Length(min=0, max=64, message="性别长度必须介于 0 和 64 之间")
	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCsrq() {
		return csrq;
	}

	public void setCsrq(Date csrq) {
		this.csrq = csrq;
	}
	
	@Length(min=0, max=18, message="身份证号码长度必须介于 0 和 18 之间")
	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	
	@Length(min=0, max=50, message="学生证号长度必须介于 0 和 50 之间")
	public String getXszh() {
		return xszh;
	}

	public void setXszh(String xszh) {
		this.xszh = xszh;
	}
	
	@Length(min=0, max=64, message="民族长度必须介于 0 和 64 之间")
	public String getMz() {
		return mz;
	}

	public void setMz(String mz) {
		this.mz = mz;
	}
	
	@Length(min=0, max=200, message="户籍地址长度必须介于 0 和 200 之间")
	public String getHjdz() {
		return hjdz;
	}

	public void setHjdz(String hjdz) {
		this.hjdz = hjdz;
	}
	
	@Length(min=0, max=11, message="手机号码长度必须介于 0 和 11 之间")
	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	
	@Length(min=0, max=50, message="微信号码长度必须介于 0 和 50 之间")
	public String getWxhm() {
		return wxhm;
	}

	public void setWxhm(String wxhm) {
		this.wxhm = wxhm;
	}
	
	@Length(min=0, max=50, message="电子邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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