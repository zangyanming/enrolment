/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.modules.sys.dao;

import cn.net.dckj.common.persistence.TreeDao;
import cn.net.dckj.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.modules.sys.entity.Office;

import java.util.List;

/**
 * 机构DAO接口
 * @author ZYM
 * @version 2019-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

    public List<Office> findOfficePersonList(Office office);
}
