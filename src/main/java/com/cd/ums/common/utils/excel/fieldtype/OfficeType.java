/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.common.utils.excel.fieldtype;

import com.cd.ums.modules.sys.entity.Office;
import com.cd.ums.modules.sys.utils.UserUtils;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.modules.sys.entity.Office;
import com.cd.ums.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author ZYM
 * @version 2018-03-10
 */
public class OfficeType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Office e : UserUtils.getOfficeList()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Office)val).getName() != null){
			return ((Office)val).getName();
		}
		return "";
	}
}
