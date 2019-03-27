/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.Contact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 联系人管理DAO接口
 * @author zangyanming
 * @version 2018-10-14
 */
@MyBatisDao
public interface ContactDao extends CrudDao<Contact> {

    List<Contact> findListByIds(Contact contact);

    void delteAllByGroupId(Contact contact);
}