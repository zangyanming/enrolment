/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.Bj;
import com.cd.ums.modules.mq.dao.BjDao;

/**
 * 班级管理Service
 * @author zangyanming
 * @version 2018-10-13
 */
@Service
@Transactional(readOnly = true)
public class BjService extends CrudService<BjDao, Bj> {

	public Bj get(String id) {
		return super.get(id);
	}
	
	public List<Bj> findList(Bj bj) {
		return super.findList(bj);
	}
	
	public Page<Bj> findPage(Page<Bj> page, Bj bj) {
		return super.findPage(page, bj);
	}
	
	@Transactional(readOnly = false)
	public void save(Bj bj) {
		super.save(bj);
	}
	
	@Transactional(readOnly = false)
	public void delete(Bj bj) {
		super.delete(bj);
	}
	
}