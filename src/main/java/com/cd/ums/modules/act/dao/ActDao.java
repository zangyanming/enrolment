/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.act.dao;

import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.act.entity.Act;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author cd
 * @version 2019-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
