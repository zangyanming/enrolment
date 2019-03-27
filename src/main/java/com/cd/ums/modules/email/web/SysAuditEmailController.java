/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.web;

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
import com.cd.ums.modules.email.entity.SysAuditEmail;
import com.cd.ums.modules.email.service.SysAuditEmailService;

/**
 * 邮件消息审核管理Controller
 * @author hqj
 * @version 2018-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/email/sysAuditEmail")
public class SysAuditEmailController extends BaseController {

	@Autowired
	private SysAuditEmailService sysAuditEmailService;
	
	@ModelAttribute
	public SysAuditEmail get(@RequestParam(required=false) String id) {
		SysAuditEmail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysAuditEmailService.get(id);
		}
		if (entity == null){
			entity = new SysAuditEmail();
		}
		return entity;
	}
	
	@RequiresPermissions("email:sysAuditEmail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysAuditEmail sysAuditEmail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysAuditEmail> page = sysAuditEmailService.findPage(new Page<SysAuditEmail>(request, response), sysAuditEmail); 
		model.addAttribute("page", page);
		return "modules/email/sysAuditEmailList";
	}

	@RequiresPermissions("email:sysAuditEmail:view")
	@RequestMapping(value = "form")
	public String form(SysAuditEmail sysAuditEmail, Model model) {
		model.addAttribute("sysAuditEmail", sysAuditEmail);
		return "modules/email/sysAuditEmailForm";
	}

	@RequiresPermissions("email:sysAuditEmail:edit")
	@RequestMapping(value = "save")
	public String save(SysAuditEmail sysAuditEmail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysAuditEmail)){
			return form(sysAuditEmail, model);
		}
		sysAuditEmailService.save(sysAuditEmail);
		addMessage(redirectAttributes, "保存邮件消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/email/sysAuditEmail/?repage";
	}
	
	@RequiresPermissions("email:sysAuditEmail:edit")
	@RequestMapping(value = "delete")
	public String delete(SysAuditEmail sysAuditEmail, RedirectAttributes redirectAttributes) {
		sysAuditEmailService.delete(sysAuditEmail);
		addMessage(redirectAttributes, "删除邮件消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/email/sysAuditEmail/?repage";
	}

}