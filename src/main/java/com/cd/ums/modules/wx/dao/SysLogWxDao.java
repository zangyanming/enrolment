/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.wx.entity.SysLogWx;

/**
 * 微信消息日志管理DAO接口
 * @author hqj
 * @version 2018-11-01
 */
@MyBatisDao
public interface SysLogWxDao extends CrudDao<SysLogWx> {
	
}