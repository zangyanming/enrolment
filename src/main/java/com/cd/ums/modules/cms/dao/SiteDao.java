/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.cms.dao;

import com.cd.ums.modules.cms.entity.Site;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.cms.entity.Site;

/**
 * 站点DAO接口
 * @author ZYM
 * @version 2018-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
