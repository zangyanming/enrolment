/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.web;

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
import com.cd.ums.modules.email.entity.SysLogEmail;
import com.cd.ums.modules.email.service.SysLogEmailService;

import java.util.Calendar;
import java.util.List;

/**
 * 邮件日志管理Controller
 * @author hqj
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/email/sysLogEmail")
public class SysLogEmailController extends BaseController {

	@Autowired
	private SysLogEmailService sysLogEmailService;
	
	@ModelAttribute
	public SysLogEmail get(@RequestParam(required=false) String id) {
		SysLogEmail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysLogEmailService.get(id);
		}
		if (entity == null){
			entity = new SysLogEmail();
		}
		return entity;
	}
	
	@RequiresPermissions("email:sysLogEmail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysLogEmail sysLogEmail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysLogEmail> page = sysLogEmailService.findPage(new Page<SysLogEmail>(request, response), sysLogEmail); 
		model.addAttribute("page", page);
		return "modules/email/sysLogEmailList";
	}

	@RequiresPermissions("email:sysLogEmail:view")
	@RequestMapping(value = "form")
	public String form(SysLogEmail sysLogEmail, Model model) {
		model.addAttribute("sysLogEmail", sysLogEmail);
		return "modules/email/sysLogEmailForm";
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
			List<ChartData> list = sysLogEmailService.findLogChartByMonth(chartData);
			jsonData.setSuccess(true).setData(list).setTotalCount(list.size());
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}
		return jsonData;
	}

	/*@RequiresPermissions("email:sysLogEmail:edit")
	@RequestMapping(value = "save")
	public String save(SysLogEmail sysLogEmail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysLogEmail)){
			return form(sysLogEmail, model);
		}
		sysLogEmailService.save(sysLogEmail);
		addMessage(redirectAttributes, "保存邮件日志成功");
		return "redirect:"+Global.getAdminPath()+"/email/sysLogEmail/?repage";
	}
	
	@RequiresPermissions("email:sysLogEmail:edit")
	@RequestMapping(value = "delete")
	public String delete(SysLogEmail sysLogEmail, RedirectAttributes redirectAttributes) {
		sysLogEmailService.delete(sysLogEmail);
		addMessage(redirectAttributes, "删除邮件日志成功");
		return "redirect:"+Global.getAdminPath()+"/email/sysLogEmail/?repage";
	}*/
}