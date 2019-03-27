/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.sys.dao;

import java.util.List;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.sys.entity.Dict;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ZYM
 * @version 2019-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
