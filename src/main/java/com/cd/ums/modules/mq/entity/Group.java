/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;

import com.cd.ums.common.persistence.DataEntity;

/**
 * 群组管理Entity
 * @author zangyanming
 * @version 2018-10-14
 */
public class Group extends DataEntity<Group> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 群组名称
	private List<String> operations; // 操作权限
	public Group() {
		super();
	}

	public Group(String id){
		super(id);
	}

	@Length(min=1, max=255, message="群组名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}
}