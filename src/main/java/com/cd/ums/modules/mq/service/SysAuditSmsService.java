/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.SysAuditSms;
import com.cd.ums.modules.mq.dao.SysAuditSmsDao;

/**
 * 短信消息审核管理Service
 * @author hqj
 * @version 2018-10-31
 */
@Service
@Transactional(readOnly = true)
public class SysAuditSmsService extends CrudService<SysAuditSmsDao, SysAuditSms> {

	public SysAuditSms get(String id) {
		return super.get(id);
	}
	
	public List<SysAuditSms> findList(SysAuditSms sysAuditSms) {
		return super.findList(sysAuditSms);
	}
	
	public Page<SysAuditSms> findPage(Page<SysAuditSms> page, SysAuditSms sysAuditSms) {
		return super.findPage(page, sysAuditSms);
	}
	
	@Transactional(readOnly = false)
	public void save(SysAuditSms sysAuditSms) {
		super.save(sysAuditSms);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysAuditSms sysAuditSms) {
		super.delete(sysAuditSms);
	}
	
}