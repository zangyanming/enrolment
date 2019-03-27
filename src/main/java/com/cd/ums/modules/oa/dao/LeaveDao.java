/**
 * There are <a href="https://github.com/cd/ums">Ums</a> code generation
 */
package com.cd.ums.modules.oa.dao;

import com.cd.ums.modules.oa.entity.Leave;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.oa.entity.Leave;

/**
 * 请假DAO接口
 * @author liuj
 * @version 2018-8-23
 */
@MyBatisDao
public interface LeaveDao extends CrudDao<Leave> {
	
	/**
	 * 更新流程实例ID
	 * @param leave
	 * @return
	 */
	public int updateProcessInstanceId(Leave leave);
	
	/**
	 * 更新实际开始结束时间
	 * @param leave
	 * @return
	 */
	public int updateRealityTime(Leave leave);
	
}
