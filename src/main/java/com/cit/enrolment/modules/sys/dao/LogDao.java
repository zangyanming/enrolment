/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cit.enrolment.modules.sys.dao;

import com.cit.enrolment.common.persistence.CrudDao;
import com.cit.enrolment.common.persistence.annotation.MyBatisDao;
import com.cit.enrolment.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author ZYM
 * @version 2019-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
