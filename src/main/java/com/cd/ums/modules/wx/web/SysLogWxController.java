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
import com.cd.ums.modules.wx.entity.SysLogWx;
import com.cd.ums.modules.wx.service.SysLogWxService;

/**
 * 微信消息日志管理Controller
 * @author hqj
 * @version 2018-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/sysLogWx")
public class SysLogWxController extends BaseController {

	@Autowired
	private SysLogWxService sysLogWxService;
	
	@ModelAttribute
	public SysLogWx get(@RequestParam(required=false) String id) {
		SysLogWx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysLogWxService.get(id);
		}
		if (entity == null){
			entity = new SysLogWx();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:sysLogWx:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysLogWx sysLogWx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysLogWx> page = sysLogWxService.findPage(new Page<SysLogWx>(request, response), sysLogWx); 
		model.addAttribute("page", page);
		return "modules/wx/sysLogWxList";
	}

	@RequiresPermissions("wx:sysLogWx:view")
	@RequestMapping(value = "form")
	public String form(SysLogWx sysLogWx, Model model) {
		model.addAttribute("sysLogWx", sysLogWx);
		return "modules/wx/sysLogWxForm";
	}

	@RequiresPermissions("wx:sysLogWx:edit")
	@RequestMapping(value = "save")
	public String save(SysLogWx sysLogWx, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysLogWx)){
			return form(sysLogWx, model);
		}
		sysLogWxService.save(sysLogWx);
		addMessage(redirectAttributes, "保存微信消息日志信息成功");
		return "redirect:"+Global.getAdminPath()+"/wx/sysLogWx/?repage";
	}
	
	@RequiresPermissions("wx:sysLogWx:edit")
	@RequestMapping(value = "delete")
	public String delete(SysLogWx sysLogWx, RedirectAttributes redirectAttributes) {
		sysLogWxService.delete(sysLogWx);
		addMessage(redirectAttributes, "删除微信消息日志信息成功");
		return "redirect:"+Global.getAdminPath()+"/wx/sysLogWx/?repage";
	}

}