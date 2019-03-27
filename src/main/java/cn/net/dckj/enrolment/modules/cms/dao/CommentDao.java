/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.cms.dao;

import cn.net.dckj.enrolment.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.enrolment.common.persistence.CrudDao;
import cn.net.dckj.enrolment.modules.cms.entity.Comment;

/**
 * 评论DAO接口
 * @author ZYM
 * @version 2018-8-23
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {

}