/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.service;

import java.util.List;

import com.cd.ums.common.persistence.ChartData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.service.CrudService;
import com.cd.ums.modules.mq.entity.ShortMessage;
import com.cd.ums.modules.mq.dao.ShortMessageDao;

/**
 * 短信消息管理Service
 * @author zangyanming
 * @version 2018-10-20
 */
@Service
@Transactional(readOnly = true)
public class ShortMessageService extends CrudService<ShortMessageDao, ShortMessage> {

	public ShortMessage get(String id) {
		return super.get(id);
	}
	
	public List<ShortMessage> findList(ShortMessage shortMessage) {
		return super.findList(shortMessage);
	}
	
	public Page<ShortMessage> findPage(Page<ShortMessage> page, ShortMessage shortMessage) {
		return super.findPage(page, shortMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(ShortMessage shortMessage) {
		super.save(shortMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShortMessage shortMessage) {
		super.delete(shortMessage);
	}

	public List<ChartData> findLogChartByMonth(ChartData chartData) {
		return dao.findLogChartByMonth(chartData);
	}

	public List<ChartData> findLogChartByMessageType(ChartData chartData) {
		return dao.findLogChartByMessageType(chartData);
	}
}