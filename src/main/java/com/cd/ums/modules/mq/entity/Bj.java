/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;
import com.cd.ums.modules.sys.entity.Office;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 班级管理Entity
 * @author zangyanming
 * @version 2018-10-13
 */
public class Bj extends DataEntity<Bj> {
	
	private static final long serialVersionUID = 1L;
	private String xlh;		// 主键
	private String xxbm;		// 学校代码
	private String xxmc;		// 学校名称
	private String njcode;		// 年级码
	private String njname;		// 年级名称
	private String bjcode;		// 班级码
	private String bjname;		// 班级名称
	private String allname;		// 班级全名
	private String othername;		// 班级别名
	private String master;		// 班主任
	private String master1;		// 班主任1
	private String bz;		// 班长
	private String xd;		// 学段1小学,2初中,3高中,4九年,5完全中学,6十二年制
	private Office djdw;		// 登记单位
	private String djdwmc;		// 登记单位名称
	private Office gxdw;		// 更新单位
	private String ssjg;		// 所属机构
	private String ssjgmc;		// 所属机构名称

	public Bj() {
		super();
	}

	public Bj(String id){
		super(id);
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	
	@Length(min=1, max=20, message="学校代码长度必须介于 1 和 20 之间")
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
	
	public String getNjcode() {
		return njcode;
	}

	public void setNjcode(String njcode) {
		this.njcode = njcode;
	}
	
	@Length(min=1, max=100, message="年级名称长度必须介于 1 和 100 之间")
	public String getNjname() {
		return njname;
	}

	public void setNjname(String njname) {
		this.njname = njname;
	}
	
	@Length(min=1, max=10, message="班级码长度必须介于 1 和 10 之间")
	public String getBjcode() {
		return bjcode;
	}

	public void setBjcode(String bjcode) {
		this.bjcode = bjcode;
	}
	
	@Length(min=1, max=20, message="班级名称长度必须介于 1 和 20 之间")
	public String getBjname() {
		return bjname;
	}

	public void setBjname(String bjname) {
		this.bjname = bjname;
	}
	
	@Length(min=0, max=50, message="班级全名长度必须介于 0 和 50 之间")
	public String getAllname() {
		return allname;
	}

	public void setAllname(String allname) {
		this.allname = allname;
	}
	
	@Length(min=0, max=50, message="班级别名长度必须介于 0 和 50 之间")
	public String getOthername() {
		return othername;
	}

	public void setOthername(String othername) {
		this.othername = othername;
	}
	
	@Length(min=0, max=50, message="班主任长度必须介于 0 和 50 之间")
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	
	@Length(min=0, max=50, message="班主任1长度必须介于 0 和 50 之间")
	public String getMaster1() {
		return master1;
	}

	public void setMaster1(String master1) {
		this.master1 = master1;
	}
	
	@Length(min=0, max=200, message="班长长度必须介于 0 和 200 之间")
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	@Length(min=0, max=20, message="学段1小学,2初中,3高中,4九年,5完全中学,6十二年制长度必须介于 0 和 20 之间")
	public String getXd() {
		return xd;
	}

	public void setXd(String xd) {
		this.xd = xd;
	}
	
	public Office getDjdw() {
		return djdw;
	}

	public void setDjdw(Office djdw) {
		this.djdw = djdw;
	}
	
	@Length(min=0, max=100, message="登记单位名称长度必须介于 0 和 100 之间")
	public String getDjdwmc() {
		return djdwmc;
	}

	public void setDjdwmc(String djdwmc) {
		this.djdwmc = djdwmc;
	}
	
	public Office getGxdw() {
		return gxdw;
	}

	public void setGxdw(Office gxdw) {
		this.gxdw = gxdw;
	}
	
	@Length(min=0, max=64, message="所属机构长度必须介于 0 和 64 之间")
	public String getSsjg() {
		return ssjg;
	}

	public void setSsjg(String ssjg) {
		this.ssjg = ssjg;
	}

	public String getSsjgmc() {
		return ssjgmc;
	}

	public void setSsjgmc(String ssjgmc) {
		this.ssjgmc = ssjgmc;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}