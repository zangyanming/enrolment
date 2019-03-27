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
import com.cd.ums.modules.mq.entity.Xx;
import com.cd.ums.modules.mq.service.XxService;

/**
 * 学校信息管理Controller
 * @author zangyanming
 * @version 2018-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/xx")
public class XxController extends BaseController {

	@Autowired
	private XxService xxService;
	
	@ModelAttribute
	public Xx get(@RequestParam(required=false) String id) {
		Xx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xxService.get(id);
		}
		if (entity == null){
			entity = new Xx();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:xx:view")
	@RequestMapping(value = {"list", ""})
	public String list(Xx xx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Xx> page = xxService.findPage(new Page<Xx>(request, response), xx); 
		model.addAttribute("page", page);
		return "modules/mq/xxList";
	}

	@RequiresPermissions("mq:xx:view")
	@RequestMapping(value = "form")
	public String form(Xx xx, Model model) {
		model.addAttribute("xx", xx);
		return "modules/mq/xxForm";
	}

	@RequiresPermissions("mq:xx:edit")
	@RequestMapping(value = "save")
	public String save(Xx xx, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, xx)){
			return form(xx, model);
		}
		xxService.save(xx);
		addMessage(redirectAttributes, "保存学校成功");
		return "redirect:"+Global.getAdminPath()+"/mq/xx/?repage";
	}
	
	@RequiresPermissions("mq:xx:edit")
	@RequestMapping(value = "delete")
	public String delete(Xx xx, RedirectAttributes redirectAttributes) {
		xxService.delete(xx);
		addMessage(redirectAttributes, "删除学校成功");
		return "redirect:"+Global.getAdminPath()+"/mq/xx/?repage";
	}

}