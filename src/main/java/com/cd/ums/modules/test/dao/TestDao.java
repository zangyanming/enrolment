/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.test.dao;

import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.test.entity.Test;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.test.entity.Test;

/**
 * 测试DAO接口
 * @author ZYM
 * @version 2018-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {
	
}
