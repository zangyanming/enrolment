/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.modules.gen.dao;

import cn.net.dckj.common.persistence.CrudDao;
import cn.net.dckj.common.persistence.annotation.MyBatisDao;
import cn.net.dckj.modules.gen.entity.GenTable;
import cn.net.dckj.modules.gen.entity.GenTableColumn;

import java.util.List;

/**
 * 业务表字段DAO接口
 * @author ZYM
 * @version 2018-10-15
 */
@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
