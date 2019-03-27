/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cit.enrolment.modules.gen.dao;

import com.cit.enrolment.common.persistence.CrudDao;
import com.cit.enrolment.common.persistence.annotation.MyBatisDao;
import com.cit.enrolment.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 * @author ZYM
 * @version 2018-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
