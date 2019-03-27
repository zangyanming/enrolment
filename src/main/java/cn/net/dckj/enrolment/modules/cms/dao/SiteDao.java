/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.cms.dao;

import cn.net.dckj.enrolment.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.enrolment.modules.cms.entity.Site;
import cn.net.dckj.enrolment.common.persistence.CrudDao;

/**
 * 站点DAO接口
 * @author ZYM
 * @version 2018-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
