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
import com.cd.ums.modules.mq.entity.Xs;
import com.cd.ums.modules.mq.service.XsService;

/**
 * 学生管理Controller
 * @author zangyanming
 * @version 2018-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/xs")
public class XsController extends BaseController {

	@Autowired
	private XsService xsService;
	
	@ModelAttribute
	public Xs get(@RequestParam(required=false) String id) {
		Xs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xsService.get(id);
		}
		if (entity == null){
			entity = new Xs();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:xs:view")
	@RequestMapping(value = {"list", ""})
	public String list(Xs xs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Xs> page = xsService.findPage(new Page<Xs>(request, response), xs); 
		model.addAttribute("page", page);
		return "modules/mq/xsList";
	}

	@RequiresPermissions("mq:xs:view")
	@RequestMapping(value = "form")
	public String form(Xs xs, Model model) {
		model.addAttribute("xs", xs);
		return "modules/mq/xsForm";
	}

	@RequiresPermissions("mq:xs:edit")
	@RequestMapping(value = "save")
	public String save(Xs xs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, xs)){
			return form(xs, model);
		}
		xsService.save(xs);
		addMessage(redirectAttributes, "保存学生成功");
		return "redirect:"+Global.getAdminPath()+"/mq/xs/?repage";
	}
	
	@RequiresPermissions("mq:xs:edit")
	@RequestMapping(value = "delete")
	public String delete(Xs xs, RedirectAttributes redirectAttributes) {
		xsService.delete(xs);
		addMessage(redirectAttributes, "删除学生成功");
		return "redirect:"+Global.getAdminPath()+"/mq/xs/?repage";
	}

}