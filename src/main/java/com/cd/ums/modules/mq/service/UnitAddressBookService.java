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
import com.cd.ums.modules.mq.entity.UnitAddressBook;
import com.cd.ums.modules.mq.dao.UnitAddressBookDao;

/**
 * 单位通讯录管理Service
 * @author zangyanming
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class UnitAddressBookService extends CrudService<UnitAddressBookDao, UnitAddressBook> {

	public UnitAddressBook get(String id) {
		return super.get(id);
	}
	
	public List<UnitAddressBook> findList(UnitAddressBook unitAddressBook) {
		return super.findList(unitAddressBook);
	}
	
	public Page<UnitAddressBook> findPage(Page<UnitAddressBook> page, UnitAddressBook unitAddressBook) {
		return super.findPage(page, unitAddressBook);
	}
	
	@Transactional(readOnly = false)
	public void save(UnitAddressBook unitAddressBook) {
		super.save(unitAddressBook);
	}
	
	@Transactional(readOnly = false)
	public void delete(UnitAddressBook unitAddressBook) {
		super.delete(unitAddressBook);
	}

	public List<UnitAddressBook> findListByIds(UnitAddressBook unitAddressBook) {
		return dao.findListByIds(unitAddressBook);
	}

	public List getContacts(UnitAddressBook unitAddressBook) {
		return dao.getContacts(unitAddressBook);
	}
}