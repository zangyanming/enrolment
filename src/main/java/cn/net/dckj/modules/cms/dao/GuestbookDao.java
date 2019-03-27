/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.modules.cms.dao;

import cn.net.dckj.common.persistence.CrudDao;
import cn.net.dckj.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.modules.cms.entity.Guestbook;

/**
 * 留言DAO接口
 * @author ZYM
 * @version 2018-8-23
 */
@MyBatisDao
public interface GuestbookDao extends CrudDao<Guestbook> {

}
