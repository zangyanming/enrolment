/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import com.cd.ums.common.persistence.ChartData;
import com.cd.ums.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.SysLogSysmsg;
import com.cd.ums.modules.mq.dao.SysLogSysmsgDao;

/**
 * 应用系统消息日志管理Service
 * @author hqj
 * @version 2018-10-13
 */
@Service
@Transactional(readOnly = true)
public class SysLogSysmsgService extends CrudService<SysLogSysmsgDao, SysLogSysmsg> {

	public SysLogSysmsg get(String id) {
		return super.get(id);
	}
	
	public List<SysLogSysmsg> findList(SysLogSysmsg sysLogSysmsg) {
		return super.findList(sysLogSysmsg);
	}
	
	public Page<SysLogSysmsg> findPage(Page<SysLogSysmsg> page, SysLogSysmsg sysLogSysmsg) {

		// 设置默认时间范围，默认当前月
		if (sysLogSysmsg.getBeginCreateDate() == null){
			sysLogSysmsg.setBeginCreateDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (sysLogSysmsg.getEndCreateDate() == null){
			sysLogSysmsg.setEndCreateDate(DateUtils.addMonths(sysLogSysmsg.getBeginCreateDate(), 1));
		}

		return super.findPage(page, sysLogSysmsg);
	}
	
	@Transactional(readOnly = false)
	public void save(SysLogSysmsg sysLogSysmsg) {
		super.save(sysLogSysmsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysLogSysmsg sysLogSysmsg) {
		super.delete(sysLogSysmsg);
	}

	public List<ChartData> findLogChartByUmsSys(ChartData chartData) {
		return dao.findLogChartByUmsSys(chartData);
	}

	public List<ChartData> findLogChartByMonth(ChartData chartData) {
		return dao.findLogChartByMonth(chartData);
	}
}
