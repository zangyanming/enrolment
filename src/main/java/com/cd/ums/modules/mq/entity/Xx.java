/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;
import com.cd.ums.modules.sys.entity.Office;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 学校信息管理Entity
 * @author zangyanming
 * @version 2018-10-13
 */
public class Xx extends DataEntity<Xx> {
	
	private static final long serialVersionUID = 1L;
	private String xlh;		// 序列号
	private String xxbm;		// 学校编码
	private String xxmc;		// 学校名称
	private String xxlb;		// 学校类别
	private String xxxz;		// 学校性质
	private String xxdz;		// 学校地址
	private Office gsdw;		// 归属单位
	private String gsdwmc;		// 归属名称
	private String lxdh;		// 联系电话
	private String xz;		// 校长
	private String bz;		// 备注
	private Office djdw;		// 登记单位
	private String djdwmc;		// 登记单位名称
	private String gxdw;		// 更新单位
	private String zt;		// 状态
	
	public Xx() {
		super();
	}

	public Xx(String id){
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
	
	@Length(min=0, max=64, message="学校类别长度必须介于 0 和 64 之间")
	public String getXxlb() {
		return xxlb;
	}

	public void setXxlb(String xxlb) {
		this.xxlb = xxlb;
	}
	
	@Length(min=0, max=64, message="学校性质长度必须介于 0 和 64 之间")
	public String getXxxz() {
		return xxxz;
	}

	public void setXxxz(String xxxz) {
		this.xxxz = xxxz;
	}
	
	@Length(min=0, max=100, message="学校地址长度必须介于 0 和 100 之间")
	public String getXxdz() {
		return xxdz;
	}

	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}
	
	public Office getGsdw() {
		return gsdw;
	}

	public void setGsdw(Office gsdw) {
		this.gsdw = gsdw;
	}
	
	@Length(min=0, max=100, message="归属名称长度必须介于 0 和 100 之间")
	public String getGsdwmc() {
		return gsdwmc;
	}

	public void setGsdwmc(String gsdwmc) {
		this.gsdwmc = gsdwmc;
	}
	
	@Length(min=0, max=20, message="联系电话长度必须介于 0 和 20 之间")
	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	
	@Length(min=0, max=50, message="校长长度必须介于 0 和 50 之间")
	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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
	
	@Length(min=0, max=64, message="更新单位长度必须介于 0 和 64 之间")
	public String getGxdw() {
		return gxdw;
	}

	public void setGxdw(String gxdw) {
		this.gxdw = gxdw;
	}
	
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
}