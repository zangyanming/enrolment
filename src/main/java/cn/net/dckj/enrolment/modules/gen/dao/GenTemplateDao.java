/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.gen.dao;

import cn.net.dckj.enrolment.common.persistence.CrudDao;
import cn.net.dckj.enrolment.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.enrolment.modules.gen.entity.GenTemplate;

/**
 * 代码模板DAO接口
 * @author ZYM
 * @version 2018-10-15
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
