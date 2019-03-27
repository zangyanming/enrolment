/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cit.enrolment.modules.cms.dao;

import com.cit.enrolment.common.persistence.CrudDao;
import com.cit.enrolment.common.persistence.annotation.MyBatisDao;
import com.cit.enrolment.modules.cms.entity.Link;

import java.util.List;

/**
 * 链接DAO接口
 * @author ZYM
 * @version 2018-8-23
 */
@MyBatisDao
public interface LinkDao extends CrudDao<Link> {
	
	public List<Link> findByIdIn(String[] ids);
//	{
//		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	
	public int updateExpiredWeight(Link link);
//	{
//		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
//	public List<Link> fjindListByEntity();
}
