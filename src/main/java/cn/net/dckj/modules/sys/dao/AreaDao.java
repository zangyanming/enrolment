/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.modules.sys.dao;

import cn.net.dckj.common.persistence.TreeDao;
import cn.net.dckj.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author ZYM
 * @version 2019-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
