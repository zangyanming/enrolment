/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.service;

import java.util.List;

import com.cd.ums.common.persistence.ChartData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.email.entity.SysLogEmail;
import com.cd.ums.modules.email.dao.SysLogEmailDao;

/**
 * 邮件日志管理Service
 * @author hqj
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class SysLogEmailService extends CrudService<SysLogEmailDao, SysLogEmail> {

	public SysLogEmail get(String id) {
		return super.get(id);
	}
	
	public List<SysLogEmail> findList(SysLogEmail sysLogEmail) {
		return super.findList(sysLogEmail);
	}
	
	public Page<SysLogEmail> findPage(Page<SysLogEmail> page, SysLogEmail sysLogEmail) {
		return super.findPage(page, sysLogEmail);
	}
	
	@Transactional(readOnly = false)
	public void save(SysLogEmail sysLogEmail) {
		super.save(sysLogEmail);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysLogEmail sysLogEmail) {
		super.delete(sysLogEmail);
	}

	public List<ChartData> findLogChartByMonth(ChartData chartData) {
		return dao.findLogChartByMonth(chartData);
	}
}