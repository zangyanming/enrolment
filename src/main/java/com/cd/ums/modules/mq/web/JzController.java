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
import com.cd.ums.modules.mq.entity.Jz;
import com.cd.ums.modules.mq.service.JzService;

/**
 * 家长管理Controller
 * @author zangyanming
 * @version 2018-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/jz")
public class JzController extends BaseController {

	@Autowired
	private JzService jzService;
	
	@ModelAttribute
	public Jz get(@RequestParam(required=false) String id) {
		Jz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jzService.get(id);
		}
		if (entity == null){
			entity = new Jz();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:jz:view")
	@RequestMapping(value = {"list", ""})
	public String list(Jz jz, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jz> page = jzService.findPage(new Page<Jz>(request, response), jz); 
		model.addAttribute("page", page);
		return "modules/mq/jzList";
	}

	@RequiresPermissions("mq:jz:view")
	@RequestMapping(value = "form")
	public String form(Jz jz, Model model) {
		model.addAttribute("jz", jz);
		return "modules/mq/jzForm";
	}

	@RequiresPermissions("mq:jz:edit")
	@RequestMapping(value = "save")
	public String save(Jz jz, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jz)){
			return form(jz, model);
		}
		jzService.save(jz);
		addMessage(redirectAttributes, "保存家长成功");
		return "redirect:"+Global.getAdminPath()+"/mq/jz/?repage";
	}
	
	@RequiresPermissions("mq:jz:edit")
	@RequestMapping(value = "delete")
	public String delete(Jz jz, RedirectAttributes redirectAttributes) {
		jzService.delete(jz);
		addMessage(redirectAttributes, "删除家长成功");
		return "redirect:"+Global.getAdminPath()+"/mq/jz/?repage";
	}

}