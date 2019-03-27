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
import com.cd.ums.modules.mq.entity.Bj;
import com.cd.ums.modules.mq.service.BjService;

/**
 * 班级管理Controller
 * @author zangyanming
 * @version 2018-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/bj")
public class BjController extends BaseController {

	@Autowired
	private BjService bjService;
	
	@ModelAttribute
	public Bj get(@RequestParam(required=false) String id) {
		Bj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bjService.get(id);
		}
		if (entity == null){
			entity = new Bj();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:bj:view")
	@RequestMapping(value = {"list", ""})
	public String list(Bj bj, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Bj> page = bjService.findPage(new Page<Bj>(request, response), bj); 
		model.addAttribute("page", page);
		return "modules/mq/bjList";
	}

	@RequiresPermissions("mq:bj:view")
	@RequestMapping(value = "form")
	public String form(Bj bj, Model model) {
		model.addAttribute("bj", bj);
		return "modules/mq/bjForm";
	}

	@RequiresPermissions("mq:bj:edit")
	@RequestMapping(value = "save")
	public String save(Bj bj, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bj)){
			return form(bj, model);
		}
		bjService.save(bj);
		addMessage(redirectAttributes, "保存班级成功");
		return "redirect:"+Global.getAdminPath()+"/mq/bj/?repage";
	}
	
	@RequiresPermissions("mq:bj:edit")
	@RequestMapping(value = "delete")
	public String delete(Bj bj, RedirectAttributes redirectAttributes) {
		bjService.delete(bj);
		addMessage(redirectAttributes, "删除班级成功");
		return "redirect:"+Global.getAdminPath()+"/mq/bj/?repage";
	}

}