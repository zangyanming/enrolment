/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.email.entity.SysAuditEmail;

/**
 * 邮件消息审核管理DAO接口
 * @author hqj
 * @version 2018-10-30
 */
@MyBatisDao
public interface SysAuditEmailDao extends CrudDao<SysAuditEmail> {
	
}