/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.UmsSystem;
import com.cd.ums.modules.mq.dao.UmsSystemDao;

/**
 * 系统消息申请管理Service
 * @author hqj
 * @version 2018-10-07
 */
@Service
@Transactional(readOnly = true)
public class UmsSystemService extends CrudService<UmsSystemDao, UmsSystem> {

	public UmsSystem get(String id) {
		return super.get(id);
	}
	
	public List<UmsSystem> findList(UmsSystem umsSystem) {
		return super.findList(umsSystem);
	}
	
	public Page<UmsSystem> findPage(Page<UmsSystem> page, UmsSystem umsSystem) {
		return super.findPage(page, umsSystem);
	}
	
	@Transactional(readOnly = false)
	public void save(UmsSystem umsSystem) {
		super.save(umsSystem);
	}
	
	@Transactional(readOnly = false)
	public void delete(UmsSystem umsSystem) {
		super.delete(umsSystem);
	}

	public List<UmsSystem> findByName(UmsSystem umsSystem) {
		return this.dao.findByName(umsSystem);
	}

	public List<UmsSystem> findBySysCode(UmsSystem umsSystem) {
		return this.dao.findBySysCode(umsSystem);
	}
}