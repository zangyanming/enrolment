/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.wx.entity.SysAuditWx;
import com.cd.ums.modules.wx.dao.SysAuditWxDao;

/**
 * 微信消息审核管理Service
 * @author hqj
 * @version 2018-10-31
 */
@Service
@Transactional(readOnly = true)
public class SysAuditWxService extends CrudService<SysAuditWxDao, SysAuditWx> {

	public SysAuditWx get(String id) {
		return super.get(id);
	}
	
	public List<SysAuditWx> findList(SysAuditWx sysAuditWx) {
		return super.findList(sysAuditWx);
	}
	
	public Page<SysAuditWx> findPage(Page<SysAuditWx> page, SysAuditWx sysAuditWx) {
		return super.findPage(page, sysAuditWx);
	}
	
	@Transactional(readOnly = false)
	public void save(SysAuditWx sysAuditWx) {
		super.save(sysAuditWx);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysAuditWx sysAuditWx) {
		super.delete(sysAuditWx);
	}
	
}