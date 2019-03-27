/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 家长管理Entity
 * @author zangyanming
 * @version 2018-10-13
 */
public class Jz extends DataEntity<Jz> {
	
	private static final long serialVersionUID = 1L;
	private String xlh;		// 序列号
	private String xm;		// 姓名
	private String sfzh;		// 身份证号
	private String zz;		// 住址
	private String yxsgx;		// 与学生关系
	private String sjhm;		// 手机号码
	private String email;		// 电子邮箱
	private String wxhm;		// 微博号码
	private String qqhm;		// QQ号码
	private String wbhm;		// 微博号码
	private String xsxlh;		// 学生序列号
	private String xssfzh;		// 学生身份证号
	
	public Jz() {
		super();
	}

	public Jz(String id){
		super(id);
	}

	@Length(min=1, max=64, message="序列号长度必须介于 1 和 64 之间")
	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	
	@Length(min=1, max=60, message="姓名长度必须介于 1 和 60 之间")
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	@Length(min=0, max=18, message="身份证号长度必须介于 0 和 18 之间")
	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	
	@Length(min=0, max=200, message="住址长度必须介于 0 和 200 之间")
	public String getZz() {
		return zz;
	}

	public void setZz(String zz) {
		this.zz = zz;
	}
	
	@Length(min=0, max=200, message="与学生关系长度必须介于 0 和 200 之间")
	public String getYxsgx() {
		return yxsgx;
	}

	public void setYxsgx(String yxsgx) {
		this.yxsgx = yxsgx;
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
	
	@Length(min=0, max=50, message="微博号码长度必须介于 0 和 50 之间")
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
	
	public String getXsxlh() {
		return xsxlh;
	}

	public void setXsxlh(String xsxlh) {
		this.xsxlh = xsxlh;
	}
	
	@Length(min=1, max=18, message="学生身份证号长度必须介于 1 和 18 之间")
	public String getXssfzh() {
		return xssfzh;
	}

	public void setXssfzh(String xssfzh) {
		this.xssfzh = xssfzh;
	}
	
}