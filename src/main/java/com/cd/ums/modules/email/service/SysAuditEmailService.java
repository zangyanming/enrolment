/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.email.entity.SysAuditEmail;
import com.cd.ums.modules.email.dao.SysAuditEmailDao;

/**
 * 邮件消息审核管理Service
 * @author hqj
 * @version 2018-10-30
 */
@Service
@Transactional(readOnly = true)
public class SysAuditEmailService extends CrudService<SysAuditEmailDao, SysAuditEmail> {

	public SysAuditEmail get(String id) {
		return super.get(id);
	}
	
	public List<SysAuditEmail> findList(SysAuditEmail sysAuditEmail) {
		return super.findList(sysAuditEmail);
	}
	
	public Page<SysAuditEmail> findPage(Page<SysAuditEmail> page, SysAuditEmail sysAuditEmail) {
		return super.findPage(page, sysAuditEmail);
	}
	
	@Transactional(readOnly = false)
	public void save(SysAuditEmail sysAuditEmail) {
		super.save(sysAuditEmail);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysAuditEmail sysAuditEmail) {
		super.delete(sysAuditEmail);
	}
	
}