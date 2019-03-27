/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.wx.entity.SysAuditWx;

/**
 * 微信消息审核管理DAO接口
 * @author hqj
 * @version 2018-10-31
 */
@MyBatisDao
public interface SysAuditWxDao extends CrudDao<SysAuditWx> {
	
}