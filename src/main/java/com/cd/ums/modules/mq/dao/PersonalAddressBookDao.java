/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.PersonalAddressBook;

import java.util.List;

/**
 * 个人通讯录管理DAO接口
 * @author zangyanming
 * @version 2018-10-16
 */
@MyBatisDao
public interface PersonalAddressBookDao extends CrudDao<PersonalAddressBook> {

    List<PersonalAddressBook> findListByIds(PersonalAddressBook personalAddressBook);

    List<PersonalAddressBook> getContacts(PersonalAddressBook personalAddressBook);
}