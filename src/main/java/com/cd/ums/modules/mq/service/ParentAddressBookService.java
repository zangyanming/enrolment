/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import com.cd.ums.common.persistence.JsonData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.ParentAddressBook;
import com.cd.ums.modules.mq.dao.ParentAddressBookDao;

/**
 * 家长通讯录管理Service
 * @author zangyanming
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class ParentAddressBookService extends CrudService<ParentAddressBookDao, ParentAddressBook> {

	public ParentAddressBook get(String id) {
		return super.get(id);
	}
	
	public List<ParentAddressBook> findList(ParentAddressBook parentAddressBook) {
		return super.findList(parentAddressBook);
	}
	
	public Page<ParentAddressBook> findPage(Page<ParentAddressBook> page, ParentAddressBook parentAddressBook) {
		return super.findPage(page, parentAddressBook);
	}
	
	@Transactional(readOnly = false)
	public void save(ParentAddressBook parentAddressBook) {
		super.save(parentAddressBook);
	}
	
	@Transactional(readOnly = false)
	public void delete(ParentAddressBook parentAddressBook) {
		super.delete(parentAddressBook);
	}

	public List<ParentAddressBook> findListByIds(ParentAddressBook parentAddressBook) {
		return dao.findListByIds(parentAddressBook);
	}

	public List getContacts(ParentAddressBook parentAddressBook) {
		return dao.getContacts(parentAddressBook);
	}
}