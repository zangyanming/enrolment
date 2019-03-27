/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.Contact;
import com.cd.ums.modules.mq.dao.ContactDao;

/**
 * 联系人管理Service
 *
 * @author zangyanming
 * @version 2018-10-14
 */
@Service
@Transactional(readOnly = true)
public class ContactService extends CrudService<ContactDao, Contact> {

    public Contact get(String id) {
        return super.get(id);
    }

    public List<Contact> findList(Contact contact) {
        return super.findList(contact);
    }

    public Page<Contact> findPage(Page<Contact> page, Contact contact) {
        return super.findPage(page, contact);
    }

    @Transactional(readOnly = false)
    public void save(Contact contact) {
        super.save(contact);
    }

    @Transactional(readOnly = false)
    public void delete(Contact contact) {
        super.delete(contact);
    }

    public List<Contact> findListByIds(Contact contact) {
        return dao.findListByIds(contact);
    }

    @Transactional(readOnly = false)
    public void deleteAllByGroupId(Contact contact) {
            dao.delteAllByGroupId(contact);
    }
}