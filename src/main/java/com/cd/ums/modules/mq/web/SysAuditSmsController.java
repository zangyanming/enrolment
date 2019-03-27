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
import com.cd.ums.modules.mq.entity.SysAuditSms;
import com.cd.ums.modules.mq.service.SysAuditSmsService;

/**
 * 短信消息审核管理Controller
 * @author hqj
 * @version 2018-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/sysAuditSms")
public class SysAuditSmsController extends BaseController {

	@Autowired
	private SysAuditSmsService sysAuditSmsService;
	
	@ModelAttribute
	public SysAuditSms get(@RequestParam(required=false) String id) {
		SysAuditSms entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysAuditSmsService.get(id);
		}
		if (entity == null){
			entity = new SysAuditSms();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:sysAuditSms:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysAuditSms sysAuditSms, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysAuditSms> page = sysAuditSmsService.findPage(new Page<SysAuditSms>(request, response), sysAuditSms); 
		model.addAttribute("page", page);
		return "modules/mq/sysAuditSmsList";
	}

	@RequiresPermissions("mq:sysAuditSms:view")
	@RequestMapping(value = "form")
	public String form(SysAuditSms sysAuditSms, Model model) {
		model.addAttribute("sysAuditSms", sysAuditSms);
		return "modules/mq/sysAuditSmsForm";
	}

	@RequiresPermissions("mq:sysAuditSms:edit")
	@RequestMapping(value = "save")
	public String save(SysAuditSms sysAuditSms, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysAuditSms)){
			return form(sysAuditSms, model);
		}
		sysAuditSmsService.save(sysAuditSms);
		addMessage(redirectAttributes, "保存短信消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/mq/sysAuditSms/?repage";
	}
	
	@RequiresPermissions("mq:sysAuditSms:edit")
	@RequestMapping(value = "delete")
	public String delete(SysAuditSms sysAuditSms, RedirectAttributes redirectAttributes) {
		sysAuditSmsService.delete(sysAuditSms);
		addMessage(redirectAttributes, "删除短信消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/mq/sysAuditSms/?repage";
	}

}