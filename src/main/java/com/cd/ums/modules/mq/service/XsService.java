/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.Xs;
import com.cd.ums.modules.mq.dao.XsDao;

/**
 * 学生管理Service
 * @author zangyanming
 * @version 2018-10-13
 */
@Service
@Transactional(readOnly = true)
public class XsService extends CrudService<XsDao, Xs> {

	public Xs get(String id) {
		return super.get(id);
	}
	
	public List<Xs> findList(Xs xs) {
		return super.findList(xs);
	}
	
	public Page<Xs> findPage(Page<Xs> page, Xs xs) {
		return super.findPage(page, xs);
	}
	
	@Transactional(readOnly = false)
	public void save(Xs xs) {
		super.save(xs);
	}
	
	@Transactional(readOnly = false)
	public void delete(Xs xs) {
		super.delete(xs);
	}
	
}