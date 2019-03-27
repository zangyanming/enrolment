/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.wx.entity.SysLogWx;
import com.cd.ums.modules.wx.dao.SysLogWxDao;

/**
 * 微信消息日志管理Service
 * @author hqj
 * @version 2018-11-01
 */
@Service
@Transactional(readOnly = true)
public class SysLogWxService extends CrudService<SysLogWxDao, SysLogWx> {

	public SysLogWx get(String id) {
		return super.get(id);
	}
	
	public List<SysLogWx> findList(SysLogWx sysLogWx) {
		return super.findList(sysLogWx);
	}
	
	public Page<SysLogWx> findPage(Page<SysLogWx> page, SysLogWx sysLogWx) {
		return super.findPage(page, sysLogWx);
	}
	
	@Transactional(readOnly = false)
	public void save(SysLogWx sysLogWx) {
		super.save(sysLogWx);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysLogWx sysLogWx) {
		super.delete(sysLogWx);
	}
	
}