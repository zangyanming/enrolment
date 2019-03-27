/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.SysAuditSysmsg;
import com.cd.ums.modules.mq.dao.SysAuditSysmsgDao;

/**
 * 应用系统消息审核管理Service
 * @author hqj
 * @version 2018-10-30
 */
@Service
@Transactional(readOnly = true)
public class SysAuditSysmsgService extends CrudService<SysAuditSysmsgDao, SysAuditSysmsg> {

	public SysAuditSysmsg get(String id) {
		return super.get(id);
	}
	
	public List<SysAuditSysmsg> findList(SysAuditSysmsg sysAuditSysmsg) {
		return super.findList(sysAuditSysmsg);
	}
	
	public Page<SysAuditSysmsg> findPage(Page<SysAuditSysmsg> page, SysAuditSysmsg sysAuditSysmsg) {
		return super.findPage(page, sysAuditSysmsg);
	}
	
	@Transactional(readOnly = false)
	public void save(SysAuditSysmsg sysAuditSysmsg) {
		super.save(sysAuditSysmsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysAuditSysmsg sysAuditSysmsg) {
		super.delete(sysAuditSysmsg);
	}
	
}