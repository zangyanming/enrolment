/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.Jz;

/**
 * 家长管理DAO接口
 * @author zangyanming
 * @version 2018-10-13
 */
@MyBatisDao
public interface JzDao extends CrudDao<Jz> {
	
}