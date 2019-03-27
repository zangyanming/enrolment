/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.web;

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
import com.cd.ums.modules.wx.entity.SysAuditWx;
import com.cd.ums.modules.wx.service.SysAuditWxService;

/**
 * 微信消息审核管理Controller
 * @author hqj
 * @version 2018-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/sysAuditWx")
public class SysAuditWxController extends BaseController {

	@Autowired
	private SysAuditWxService sysAuditWxService;
	
	@ModelAttribute
	public SysAuditWx get(@RequestParam(required=false) String id) {
		SysAuditWx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysAuditWxService.get(id);
		}
		if (entity == null){
			entity = new SysAuditWx();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:sysAuditWx:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysAuditWx sysAuditWx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysAuditWx> page = sysAuditWxService.findPage(new Page<SysAuditWx>(request, response), sysAuditWx); 
		model.addAttribute("page", page);
		return "modules/wx/sysAuditWxList";
	}

	@RequiresPermissions("wx:sysAuditWx:view")
	@RequestMapping(value = "form")
	public String form(SysAuditWx sysAuditWx, Model model) {
		model.addAttribute("sysAuditWx", sysAuditWx);
		return "modules/wx/sysAuditWxForm";
	}

	@RequiresPermissions("wx:sysAuditWx:edit")
	@RequestMapping(value = "save")
	public String save(SysAuditWx sysAuditWx, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysAuditWx)){
			return form(sysAuditWx, model);
		}
		sysAuditWxService.save(sysAuditWx);
		addMessage(redirectAttributes, "保存微信消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/wx/sysAuditWx/?repage";
	}
	
	@RequiresPermissions("wx:sysAuditWx:edit")
	@RequestMapping(value = "delete")
	public String delete(SysAuditWx sysAuditWx, RedirectAttributes redirectAttributes) {
		sysAuditWxService.delete(sysAuditWx);
		addMessage(redirectAttributes, "删除微信消息审核信息成功");
		return "redirect:"+Global.getAdminPath()+"/wx/sysAuditWx/?repage";
	}

}