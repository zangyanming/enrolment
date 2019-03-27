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
import com.cd.ums.modules.mq.entity.PersonalAddressBook;
import com.cd.ums.modules.mq.dao.PersonalAddressBookDao;

/**
 * 个人通讯录管理Service
 * @author zangyanming
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class PersonalAddressBookService extends CrudService<PersonalAddressBookDao, PersonalAddressBook> {

	public PersonalAddressBook get(String id) {
		return super.get(id);
	}
	
	public List<PersonalAddressBook> findList(PersonalAddressBook personalAddressBook) {
		return super.findList(personalAddressBook);
	}
	
	public Page<PersonalAddressBook> findPage(Page<PersonalAddressBook> page, PersonalAddressBook personalAddressBook) {
		return super.findPage(page, personalAddressBook);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonalAddressBook personalAddressBook) {
		super.save(personalAddressBook);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonalAddressBook personalAddressBook) {
		super.delete(personalAddressBook);
	}

	public List<PersonalAddressBook> findListByIds(PersonalAddressBook personalAddressBook) {
		return dao.findListByIds(personalAddressBook);
	}

	public List<PersonalAddressBook> getContacts(PersonalAddressBook personalAddressBook) {
		return dao.getContacts(personalAddressBook);
	}
}