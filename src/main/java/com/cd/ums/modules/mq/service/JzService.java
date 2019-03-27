/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.Jz;
import com.cd.ums.modules.mq.dao.JzDao;

/**
 * 家长管理Service
 * @author zangyanming
 * @version 2018-10-13
 */
@Service
@Transactional(readOnly = true)
public class JzService extends CrudService<JzDao, Jz> {

	public Jz get(String id) {
		return super.get(id);
	}
	
	public List<Jz> findList(Jz jz) {
		return super.findList(jz);
	}
	
	public Page<Jz> findPage(Page<Jz> page, Jz jz) {
		return super.findPage(page, jz);
	}
	
	@Transactional(readOnly = false)
	public void save(Jz jz) {
		super.save(jz);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jz jz) {
		super.delete(jz);
	}
	
}