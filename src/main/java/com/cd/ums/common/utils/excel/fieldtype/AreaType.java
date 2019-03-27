/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.common.utils.excel.fieldtype;

import com.cd.ums.modules.sys.entity.Area;
import com.cd.ums.modules.sys.utils.UserUtils;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.modules.sys.entity.Area;
import com.cd.ums.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author ZYM
 * @version 2018-03-10
 */
public class AreaType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Area e : UserUtils.getAreaList()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}
