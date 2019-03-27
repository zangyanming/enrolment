/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.common.utils.excel.fieldtype;

import cn.net.dckj.enrolment.common.utils.Collections3;
import cn.net.dckj.enrolment.common.utils.SpringContextHolder;
import cn.net.dckj.enrolment.common.utils.StringUtils;
import cn.net.dckj.enrolment.modules.sys.entity.Role;
import cn.net.dckj.enrolment.modules.sys.service.SystemService;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 字段类型转换
 * @author ZYM
 * @version 2018-5-29
 */
public class RoleListType {

	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
	
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Role> roleList = Lists.newArrayList();
		List<Role> allRoleList = systemService.findAllRole();
		for (String s : StringUtils.split(val, ",")){
			for (Role e : allRoleList){
				if (StringUtils.trimToEmpty(s).equals(e.getName())){
					roleList.add(e);
				}
			}
		}
		return roleList.size()>0?roleList:null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			@SuppressWarnings("unchecked")
			List<Role> roleList = (List<Role>)val;
			return Collections3.extractToString(roleList, "name", ", ");
		}
		return "";
	}
	
}
