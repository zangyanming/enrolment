/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.ChartData;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.SysLogSysmsg;

import java.util.List;

/**
 * 应用系统消息日志管理DAO接口
 *
 * @author hqj
 * @version 2018-10-13
 */
@MyBatisDao
public interface SysLogSysmsgDao extends CrudDao<SysLogSysmsg> {

    List<ChartData> findLogChartByUmsSys(ChartData chartData);

    List<ChartData> findLogChartByMonth(ChartData chartData);
}