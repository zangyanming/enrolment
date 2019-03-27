/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/cd/ums">Ums</a> All rights reserved.
 */
package cn.net.dckj.modules.sys.service;

import cn.net.dckj.common.persistence.Page;
import cn.net.dckj.common.service.CrudService;
import cn.net.dckj.common.utils.DateUtils;
import cn.net.dckj.modules.sys.dao.LogDao;
import cn.net.dckj.modules.sys.entity.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
