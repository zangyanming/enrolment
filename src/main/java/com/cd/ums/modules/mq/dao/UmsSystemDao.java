/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.UmsSystem;

import java.util.List;

/**
 * 系统消息申请管理DAO接口
 * @author hqj
 * @version 2018-10-07
 */
@MyBatisDao
public interface UmsSystemDao extends CrudDao<UmsSystem> {

    List<UmsSystem> findByName(UmsSystem entity);

    List<UmsSystem> findBySysCode(UmsSystem entity);
}