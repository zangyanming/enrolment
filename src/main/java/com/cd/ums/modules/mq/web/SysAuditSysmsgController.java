/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.modules.mq.entity.SysAuditSysmsg;
import com.cd.ums.modules.mq.service.SysAuditSysmsgService;

/**
 * 应用系统消息审核管理Controller
 * @author hqj
 * @version 2018-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/sysAuditSysmsg")
public class SysAuditSysmsgController extends BaseController {

	@Autowired
	private SysAuditSysmsgService sysAuditSysmsgService;
	
	@ModelAttribute
	public SysAuditSysmsg get(@RequestParam(required=false) String id) {
		SysAuditSysmsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysAuditSysmsgService.get(id);
		}
		if (entity == null){
			entity = new SysAuditSysmsg();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:sysAuditSysmsg:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysAuditSysmsg sysAuditSysmsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysAuditSysmsg> page = sysAuditSysmsgService.findPage(new Page<SysAuditSysmsg>(request, response), sysAuditSysmsg); 
		model.addAttribute("page", page);
		return "modules/mq/sysAuditSysmsgList";
	}

	@RequiresPermissions("mq:sysAuditSysmsg:view")
	@RequestMapping(value = "form")
	public String form(SysAuditSysmsg sysAuditSysmsg, Model model) {
		model.addAttribute("sysAuditSysmsg", sysAuditSysmsg);
		return "modules/mq/sysAuditSysmsgForm";
	}

	@RequiresPermissions("mq:sysAuditSysmsg:edit")
	@RequestMapping(value = "save")
	public String save(SysAuditSysmsg sysAuditSysmsg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysAuditSysmsg)){
			return form(sysAuditSysmsg, model);
		}
		sysAuditSysmsgService.save(sysAuditSysmsg);
		addMessage(redirectAttributes, "保存应用系统消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/mq/sysAuditSysmsg/?repage";
	}
	
	@RequiresPermissions("mq:sysAuditSysmsg:edit")
	@RequestMapping(value = "delete")
	public String delete(SysAuditSysmsg sysAuditSysmsg, RedirectAttributes redirectAttributes) {
		sysAuditSysmsgService.delete(sysAuditSysmsg);
		addMessage(redirectAttributes, "删除应用系统消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/mq/sysAuditSysmsg/?repage";
	}

}