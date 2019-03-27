/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.Js;
import com.cd.ums.modules.mq.dao.JsDao;

/**
 * 教师管理Service
 * @author zangyanming
 * @version 2018-10-13
 */
@Service
@Transactional(readOnly = true)
public class JsService extends CrudService<JsDao, Js> {

	public Js get(String id) {
		return super.get(id);
	}
	
	public List<Js> findList(Js js) {
		return super.findList(js);
	}
	
	public Page<Js> findPage(Page<Js> page, Js js) {
		return super.findPage(page, js);
	}
	
	@Transactional(readOnly = false)
	public void save(Js js) {
		super.save(js);
	}
	
	@Transactional(readOnly = false)
	public void delete(Js js) {
		super.delete(js);
	}
	
}