/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import com.cd.ums.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 联系人管理Entity
 * @author zangyanming
 * @version 2018-10-14
 */
public class Contact extends DataEntity<Contact> {
	
	private static final long serialVersionUID = 1L;
	private String groupId;		// 群组ID
	private String groupName;    // 群组名称
	private String personType;		// 联系人来源类型 1:教师 2:家长 3:学生 4:个人
	private String personId;		// 来源表的ID
	private String name;		// 姓名
	private String qqhm;		// QQ号码
	private String wxhm;		// 微信号码
	private String email;		// 电子邮箱
	private String tel;		// 手机号码
	private String[] ids; // 查询使用
	public Contact() {
		super();
	}

	public Contact(String id){
		super(id);
	}

	@Length(min=0, max=64, message="群组ID长度必须介于 0 和 64 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Length(min=1, max=64, message="联系人来源类型 0:教师 1:家长 2:学生 3:个人长度必须介于 1 和 64 之间")
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	@Length(min=0, max=64, message="来源表的ID长度必须介于 0 和 64 之间")
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@Length(min=1, max=50, message="姓名长度必须介于 1 和 50 之间")
	@ExcelField(title="联系人姓名", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=30, message="手机号码长度必须介于 0 和 30 之间")
	@ExcelField(title="联系人手机", align=2, sort=20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Length(min=0, max=50, message="QQ号码长度必须介于 0 和 50 之间")
	@ExcelField(title="联系人QQ号码", align=2, sort=30)
	public String getQqhm() {
		return qqhm;
	}

	public void setQqhm(String qqhm) {
		this.qqhm = qqhm;
	}
	
	@Length(min=0, max=50, message="微信号码长度必须介于 0 和 50 之间")
	@ExcelField(title="联系人微信号码", align=2, sort=40)
	public String getWxhm() {
		return wxhm;
	}

	public void setWxhm(String wxhm) {
		this.wxhm = wxhm;
	}
	
	@Length(min=0, max=50, message="电子邮箱长度必须介于 0 和 50 之间")
	@ExcelField(title="联系人电子邮箱", align=2, sort=50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
}