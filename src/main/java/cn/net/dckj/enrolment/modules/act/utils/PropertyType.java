/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.act.utils;

import java.util.Date;

/**
 * 属性数据类型
 * @author ZYM
 * @version 2018-11-03
 */
public enum PropertyType {
	
	S(String.class), 
	I(Integer.class), 
	L(Long.class), 
	F(Float.class), 
	N(Double.class),
	D(Date.class), 
	SD(java.sql.Date.class), 
	B(Boolean.class);

	private Class<?> clazz;

	private PropertyType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getValue() {
		return clazz;
	}
}