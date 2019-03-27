/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.enrolment.modules.sys.dao;

import cn.net.dckj.enrolment.common.persistence.CrudDao;
import cn.net.dckj.enrolment.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.enrolment.modules.sys.entity.Dict;

import java.util.List;

/**
 * 字典DAO接口
 * @author ZYM
 * @version 2019-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
