/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.test.dao;

import com.cd.ums.common.persistence.TreeDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.common.persistence.TreeDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author ZYM
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}