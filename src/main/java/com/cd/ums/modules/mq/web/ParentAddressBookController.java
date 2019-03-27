/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.sys.utils.UserUtils;
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
import com.cd.ums.modules.mq.entity.ParentAddressBook;
import com.cd.ums.modules.mq.service.ParentAddressBookService;

import java.util.ArrayList;
import java.util.List;

/**
 * 家长通讯录管理Controller
 * @author zangyanming
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/parentAddressBook")
public class ParentAddressBookController extends BaseController {

	@Autowired
	private ParentAddressBookService parentAddressBookService;
	
	@ModelAttribute
	public ParentAddressBook get(@RequestParam(required=false) String id) {
		ParentAddressBook entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = parentAddressBookService.get(id);
		}
		if (entity == null){
			entity = new ParentAddressBook();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:parentAddressBook:view")
	@RequestMapping(value = {"list", ""})
	public String list(ParentAddressBook parentAddressBook, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ParentAddressBook> page = parentAddressBookService.findPage(new Page<ParentAddressBook>(request, response), parentAddressBook); 
		model.addAttribute("page", page);
		return "modules/mq/parentAddressBookList";
	}

	@RequiresPermissions("mq:parentAddressBook:view")
	@RequestMapping(value = "form")
	public String form(ParentAddressBook parentAddressBook, Model model) {
		model.addAttribute("parentAddressBook", parentAddressBook);
		return "modules/mq/parentAddressBookForm";
	}

	@RequiresPermissions("mq:parentAddressBook:edit")
	@RequestMapping(value = "save")
	public String save(ParentAddressBook parentAddressBook, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parentAddressBook)){
			return form(parentAddressBook, model);
		}
		parentAddressBookService.save(parentAddressBook);
		addMessage(redirectAttributes, "保存家长通讯录成功");
		return "redirect:"+Global.getAdminPath()+"/mq/parentAddressBook/?repage";
	}
	
	@RequiresPermissions("mq:parentAddressBook:edit")
	@RequestMapping(value = "delete")
	public String delete(ParentAddressBook parentAddressBook, RedirectAttributes redirectAttributes) {
		parentAddressBookService.delete(parentAddressBook);
		addMessage(redirectAttributes, "删除家长通讯录成功");
		return "redirect:"+Global.getAdminPath()+"/mq/parentAddressBook/?repage";
	}

	//////////////////////////////以下是json調用格式////////////////////////////////////////////
	@ResponseBody
	@RequiresPermissions("mq:parentAddressBook:view")
	@RequestMapping(value = "parentAddressBooks", method= RequestMethod.GET)
	public JsonData getParentAddressBooks(ParentAddressBook parentAddressBook, HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		//parentAddressBook = (ParentAddressBook) BizUtils.initEntity(parentAddressBook);
		if (parentAddressBook == null) {
			parentAddressBook = new ParentAddressBook();
		}

		parentAddressBook.setCreateBy(UserUtils.getUser());
		//parentAddressBook = parentAddressBookService.addDataAuthorityCondition(parentAddressBook, "CreateunitCode", false);

		try {
			Page<ParentAddressBook> page = parentAddressBookService.findPage(new Page<ParentAddressBook>(request, response), parentAddressBook);
			// 判断登录系统的用户角色
			boolean isAdmin = UserUtils.getUser().isAdmin();
			String officeGrade = UserUtils.getUser().getOffice().getGrade();

			//把单位通讯录、家长通讯录、个人通讯录默认加入
			List parentAddressBookList = page.getList();
			ParentAddressBook parentAddressBookTemp = new ParentAddressBook();
			parentAddressBookTemp.setId("0");
			parentAddressBookTemp.setName("单位通讯录");
			parentAddressBookList.add(parentAddressBookTemp);

			parentAddressBookTemp.setId("1");
			parentAddressBookTemp.setName("家长通讯录");
			parentAddressBookList.add(parentAddressBookTemp);

			parentAddressBookTemp.setId("2");
			parentAddressBookTemp.setName("个人通讯录");
			parentAddressBookList.add(parentAddressBookTemp);

			jsonData.setSuccess(true).setData(parentAddressBookList).setTotalCount(parentAddressBookList.size());
		}
		catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:parentAddressBook:view")
	@RequestMapping(value = "parentAddressBooks/{id}", method= RequestMethod.GET)
	public JsonData getParentAddressBookById(@RequestParam(required=false) String id) {
		JsonData jsonData = new JsonData();
		ParentAddressBook entity = parentAddressBookService.get(id);
//		if (entity == null){
//			entity = new ParentAddressBook();
//		}
		jsonData.setSuccess(true).setMessage("").setData(entity);

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:ParentAddressBook:edit")
	@RequestMapping(value = "parentAddressBooks", method = RequestMethod.POST)
	public JsonData addParentAddressBook(@RequestBody ParentAddressBook parentAddressBook) {
		JsonData jsonData = new JsonData();
		try {
			parentAddressBookService.save(parentAddressBook);
			jsonData.setSuccess(true).setMessage("保存家长通讯录信息成功");
		}
		catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:ParentAddressBook:edit")
	@RequestMapping(value = "parentAddressBooks", method = RequestMethod.PUT)
	public JsonData modifyParentAddressBook(@RequestBody ParentAddressBook parentAddressBook) {
		JsonData jsonData = new JsonData();
		try {
			this.addParentAddressBook(parentAddressBook);
			jsonData.setSuccess(true).setMessage("操作成功");
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:parentAddressBook:edit")
	@RequestMapping(value = "parentAddressBooks/{id}", method= RequestMethod.DELETE)
	public JsonData deleteParentAddressBookById(ParentAddressBook parentAddressBook) {
		JsonData jsonData = new JsonData();
		try {
			parentAddressBookService.delete(parentAddressBook);
			jsonData.setSuccess(true).setMessage("刪除家长通讯录信息失败");
		}catch (Exception e){
			jsonData.setSuccess(true).setMessage("刪除家长通讯录信息成功");
		}

		return jsonData;
	}

	@ResponseBody
	public JsonData sendMsg(String ids, String message) {
		JsonData jsonData = new JsonData();
		String phones = "";
		ParentAddressBook parentAddressBook = new ParentAddressBook();
		parentAddressBook.setIds(ids.split(","));
		List<ParentAddressBook> parentABList = parentAddressBookService.findListByIds(parentAddressBook);
		if (parentABList != null && parentABList.size() > 0) {
			String phone = "";
			for (int i = 0; i < parentABList.size(); i++) {
				phone = parentABList.get(i).getTel();
				if (!phone.isEmpty()) {
					phones += phone + ",";
				}
			}

			if (phones.length() > 10) {
				phones = phones.substring(0, phones.length() - 1);
			}
		}
		//发送短信消息

		jsonData.setSuccess(true).setMessage("发送消息成功");

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:parentAddressBook:view")
	public JsonData getContacts(ParentAddressBook parentAddressBook, HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		try {
			/*List<ParentAddressBook> dataList = parentAddressBookService.findPage(new Page(request,response),parentAddressBook).getList();
			jsonData.setSuccess(true).setMessage("").setData(dataList).setTotalCount(dataList.size());*/
			Page<ParentAddressBook> page = parentAddressBookService.findPage(new Page(request,response),parentAddressBook);
			jsonData.setSuccess(true).setMessage("").setData(page.getList()).setTotalCount((int) page.getCount());
		}catch (Exception e){
			jsonData.setSuccess(false).setMessage("查询联系人失败");
		}

		return jsonData;
	}
}