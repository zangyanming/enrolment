/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.sys.service;

import com.cd.ums.common.persistence.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.common.utils.DateUtils;
import com.cd.ums.modules.sys.dao.LogDao;
import com.cd.ums.modules.sys.entity.Log;

/**
 * 日志Service
 * @author ZYM
 * @version 2019-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {

	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
}
