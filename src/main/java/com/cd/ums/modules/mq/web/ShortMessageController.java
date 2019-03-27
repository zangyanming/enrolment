/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cd.ums.common.persistence.ChartData;
import com.cd.ums.common.persistence.JsonData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.modules.mq.entity.ShortMessage;
import com.cd.ums.modules.mq.service.ShortMessageService;

import java.util.Calendar;
import java.util.List;

/**
 * 短信消息管理Controller
 * @author zangyanming
 * @version 2018-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/shortMessage")
public class ShortMessageController extends BaseController {

	@Autowired
	private ShortMessageService shortMessageService;
	
	@ModelAttribute
	public ShortMessage get(@RequestParam(required=false) String id) {
		ShortMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shortMessageService.get(id);
		}
		if (entity == null){
			entity = new ShortMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:shortMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShortMessage shortMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShortMessage> page = shortMessageService.findPage(new Page<ShortMessage>(request, response), shortMessage); 
		model.addAttribute("page", page);
		return "modules/mq/shortMessageList";
	}

	@RequiresPermissions("mq:shortMessage:view")
	@RequestMapping(value = "form")
	public String form(ShortMessage shortMessage, Model model) {
		model.addAttribute("shortMessage", shortMessage);
		return "modules/mq/shortMessageForm";
	}

	@RequiresPermissions("mq:shortMessage:edit")
	@RequestMapping(value = "save")
	public String save(ShortMessage shortMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shortMessage)){
			return form(shortMessage, model);
		}
		shortMessageService.save(shortMessage);
		addMessage(redirectAttributes, "保存短信消息成功");
		return "redirect:"+Global.getAdminPath()+"/mq/shortMessage/?repage";
	}
	
	@RequiresPermissions("mq:shortMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(ShortMessage shortMessage, RedirectAttributes redirectAttributes) {
		shortMessageService.delete(shortMessage);
		addMessage(redirectAttributes, "删除短信消息成功");
		return "redirect:"+Global.getAdminPath()+"/mq/shortMessage/?repage";
	}

	//////////////////////////////以下是json調用格式////////////////////////////////////////////
	@ResponseBody
	@RequiresPermissions("mq:shortMessage:view")
	@RequestMapping(value = "shortMessages", method= RequestMethod.GET)
	public JsonData getShortMessages(ShortMessage shortMessage) {
		JsonData jsonData = new JsonData();
		if (shortMessage == null) {
			shortMessage = new ShortMessage();
		}
		try {
			Page<ShortMessage> page = shortMessageService.findPage(new Page(), shortMessage);
			jsonData.setSuccess(true).setData(page.getList()).setTotalCount((int)page.getCount());
		}
		catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:shortMessage:view")
	@RequestMapping(value = "shortMessages/{id}", method= RequestMethod.GET)
	public JsonData getShortMessageById(@RequestParam(required=false) String id) {
		JsonData jsonData = new JsonData();
		ShortMessage entity = shortMessageService.get(id);
		if (entity == null){
			entity = new ShortMessage();
		}
		jsonData.setSuccess(true).setMessage("").setData(entity);

		return jsonData;
	}

	@ResponseBody
	@RequestMapping(value = "logChartByMonth")
	public JsonData logChartByMonth(ChartData chartData) {
		JsonData jsonData = new JsonData();
		try {
			// 默认查询当年有数据月份（限定如果无年份，按当前年份查询）
			if (chartData == null || StringUtils.isBlank(chartData.getYearstr())) {
				int y = Calendar.getInstance().get(Calendar.YEAR);
				chartData.setYearstr(String.valueOf(y));
			}
			List<ChartData> list = shortMessageService.findLogChartByMonth(chartData);
			jsonData.setSuccess(true).setData(list).setTotalCount(list.size());
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}
		return jsonData;
	}

	@ResponseBody
	@RequestMapping(value = "logChartByMessageType")
	public JsonData logChartByMessageType(ChartData chartData) {
		JsonData jsonData = new JsonData();
		try {
			List<ChartData> list = shortMessageService.findLogChartByMessageType(chartData);
			jsonData.setSuccess(true).setData(list).setTotalCount(list.size());
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}
		return jsonData;
	}
}