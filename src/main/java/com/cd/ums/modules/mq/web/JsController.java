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
import com.cd.ums.modules.mq.entity.Js;
import com.cd.ums.modules.mq.service.JsService;

/**
 * 教师管理Controller
 * @author zangyanming
 * @version 2018-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/js")
public class JsController extends BaseController {

	@Autowired
	private JsService jsService;
	
	@ModelAttribute
	public Js get(@RequestParam(required=false) String id) {
		Js entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jsService.get(id);
		}
		if (entity == null){
			entity = new Js();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:js:view")
	@RequestMapping(value = {"list", ""})
	public String list(Js js, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Js> page = jsService.findPage(new Page<Js>(request, response), js); 
		model.addAttribute("page", page);
		return "modules/mq/jsList";
	}

	@RequiresPermissions("mq:js:view")
	@RequestMapping(value = "form")
	public String form(Js js, Model model) {
		model.addAttribute("js", js);
		return "modules/mq/jsForm";
	}

	@RequiresPermissions("mq:js:edit")
	@RequestMapping(value = "save")
	public String save(Js js, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, js)){
			return form(js, model);
		}
		jsService.save(js);
		addMessage(redirectAttributes, "保存教师成功");
		return "redirect:"+Global.getAdminPath()+"/mq/js/?repage";
	}
	
	@RequiresPermissions("mq:js:edit")
	@RequestMapping(value = "delete")
	public String delete(Js js, RedirectAttributes redirectAttributes) {
		jsService.delete(js);
		addMessage(redirectAttributes, "删除教师成功");
		return "redirect:"+Global.getAdminPath()+"/mq/js/?repage";
	}

}