/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.SysAuditSms;

/**
 * 短信消息审核管理DAO接口
 * @author hqj
 * @version 2018-10-31
 */
@MyBatisDao
public interface SysAuditSmsDao extends CrudDao<SysAuditSms> {
	
}