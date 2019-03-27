/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.cms.dao;

import cn.net.dckj.enrolment.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.enrolment.modules.cms.entity.Article;
import cn.net.dckj.enrolment.modules.cms.entity.Category;
import cn.net.dckj.enrolment.common.persistence.CrudDao;

import java.util.List;

/**
 * 文章DAO接口
 * @author ZYM
 * @version 2018-8-23
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {
	
	public List<Article> findByIdIn(String[] ids);
//	{
//		return find("from Article where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	
	public int updateHitsAddOne(String id);
//	{
//		return update("update Article set hits=hits+1 where id = :p1", new Parameter(id));
//	}
	
	public int updateExpiredWeight(Article article);
	
	public List<Category> findStats(Category category);
//	{
//		return update("update Article set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
	
}
