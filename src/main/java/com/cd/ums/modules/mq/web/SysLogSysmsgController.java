/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cd.ums.common.persistence.ChartData;
import com.cd.ums.common.persistence.JsonData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.modules.mq.entity.SysLogSysmsg;
import com.cd.ums.modules.mq.service.SysLogSysmsgService;

import java.util.Calendar;
import java.util.List;

/**
 * 应用系统消息日志管理Controller
 * @author hqj
 * @version 2018-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/sysLogSysmsg")
public class SysLogSysmsgController extends BaseController {

	@Autowired
	private SysLogSysmsgService sysLogSysmsgService;
	
	@ModelAttribute
	public SysLogSysmsg get(@RequestParam(required=false) String id) {
		SysLogSysmsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysLogSysmsgService.get(id);
		}
		if (entity == null){
			entity = new SysLogSysmsg();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:sysLogSysmsg:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysLogSysmsg sysLogSysmsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysLogSysmsg> page = sysLogSysmsgService.findPage(new Page<SysLogSysmsg>(request, response), sysLogSysmsg); 
		model.addAttribute("page", page);
		return "modules/mq/sysLogSysmsgList";
	}

	@RequiresPermissions("mq:sysLogSysmsg:view")
	@RequestMapping(value = "form")
	public String form(SysLogSysmsg sysLogSysmsg, Model model) {
		model.addAttribute("sysLogSysmsg", sysLogSysmsg);
		return "modules/mq/sysLogSysmsgForm";
	}

	@ResponseBody
	@RequestMapping(value = "logChartByName")
	public JsonData logChartByName(ChartData chartData) {
		JsonData jsonData = new JsonData();
		try {
			List<ChartData> list = sysLogSysmsgService.findLogChartByUmsSys(chartData);
			jsonData.setSuccess(true).setData(list).setTotalCount(list.size());
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}
		return jsonData;
	}

	@ResponseBody
	@RequestMapping(value = "logChartByMonth")
	public JsonData logChartByMonth(ChartData chartData) {
		JsonData jsonData = new JsonData();
		try {
			// 默认查询当年有数据月份（限定如果无年份，按当前年份查询）
			if (chartData == null || StringUtils.isBlank(chartData.getYearstr())) {
				int y = Calendar.getInstance().get(Calendar.YEAR);
				chartData.setYearstr(String.valueOf(y));
			}
			List<ChartData> list = sysLogSysmsgService.findLogChartByMonth(chartData);
			jsonData.setSuccess(true).setData(list).setTotalCount(list.size());
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}
		return jsonData;
	}

	/*@RequiresPermissions("mq:sysLogSysmsg:edit")
	@RequestMapping(value = "save")
	public String save(SysLogSysmsg sysLogSysmsg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysLogSysmsg)){
			return form(sysLogSysmsg, model);
		}
		sysLogSysmsgService.save(sysLogSysmsg);
		addMessage(redirectAttributes, "保存应用系统消息日志成功");
		return "redirect:"+Global.getAdminPath()+"/mq/sysLogSysmsg/?repage";
	}
	
	@RequiresPermissions("mq:sysLogSysmsg:edit")
	@RequestMapping(value = "delete")
	public String delete(SysLogSysmsg sysLogSysmsg, RedirectAttributes redirectAttributes) {
		sysLogSysmsgService.delete(sysLogSysmsg);
		addMessage(redirectAttributes, "删除应用系统消息日志成功");
		return "redirect:"+Global.getAdminPath()+"/mq/sysLogSysmsg/?repage";
	}*/
}