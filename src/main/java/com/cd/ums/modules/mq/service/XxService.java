/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.Xx;
import com.cd.ums.modules.mq.dao.XxDao;

/**
 * 学校信息管理Service
 * @author zangyanming
 * @version 2018-10-13
 */
@Service
@Transactional(readOnly = true)
public class XxService extends CrudService<XxDao, Xx> {

	public Xx get(String id) {
		return super.get(id);
	}
	
	public List<Xx> findList(Xx xx) {
		return super.findList(xx);
	}
	
	public Page<Xx> findPage(Page<Xx> page, Xx xx) {
		return super.findPage(page, xx);
	}
	
	@Transactional(readOnly = false)
	public void save(Xx xx) {
		super.save(xx);
	}
	
	@Transactional(readOnly = false)
	public void delete(Xx xx) {
		super.delete(xx);
	}
	
}