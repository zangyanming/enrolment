/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.modules.act.dao;

import cn.net.dckj.common.persistence.CrudDao;
import cn.net.dckj.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author cd
 * @version 2019-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
