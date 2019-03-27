/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.dao;

import com.cd.ums.common.persistence.ChartData;
import com.cd.ums.common.persistence.CrudDao;
import com.cd.ums.common.persistence.annotation.MyBatisDao;
import com.cd.ums.modules.mq.entity.ShortMessage;

import java.util.List;

/**
 * 短信消息管理DAO接口
 * @author zangyanming
 * @version 2018-10-20
 */
@MyBatisDao
public interface ShortMessageDao extends CrudDao<ShortMessage> {

    List<ChartData> findLogChartByMonth(ChartData chartData);

    List<ChartData> findLogChartByMessageType(ChartData chartData);
}