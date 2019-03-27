/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.dao;

import com.cd.ums.common.persistence.ChartData;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.email.entity.SysLogEmail;

import java.util.List;

/**
 * 邮件日志管理DAO接口
 * @author hqj
 * @version 2018-10-18
 */
@MyBatisDao
public interface SysLogEmailDao extends CrudDao<SysLogEmail> {

    List<ChartData> findLogChartByMonth(ChartData chartData);
}