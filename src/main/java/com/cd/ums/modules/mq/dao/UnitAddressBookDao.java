/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.UnitAddressBook;

import java.util.List;

/**
 * 单位通讯录管理DAO接口
 *
 * @author zangyanming
 * @version 2018-10-16
 */
@MyBatisDao
public interface UnitAddressBookDao extends CrudDao<UnitAddressBook> {

    List<UnitAddressBook> findListByIds(UnitAddressBook unitAddressBook);

    List<UnitAddressBook> getContacts(UnitAddressBook unitAddressBook);
}