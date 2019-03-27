/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.sys.entity.User;
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
import com.cd.ums.modules.mq.entity.PersonalAddressBook;
import com.cd.ums.modules.mq.service.PersonalAddressBookService;

import java.util.List;

/**
 * 个人通讯录管理Controller
 * @author zangyanming
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/personalAddressBook")
public class PersonalAddressBookController extends BaseController {

	@Autowired
	private PersonalAddressBookService personalAddressBookService;
	
	@ModelAttribute
	public PersonalAddressBook get(@RequestParam(required=false) String id) {
		PersonalAddressBook entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = personalAddressBookService.get(id);
		}
		if (entity == null){
			entity = new PersonalAddressBook();
		}
		return entity;
	}
	
	@RequiresPermissions("mq:personalAddressBook:view")
	@RequestMapping(value = {"list", ""})
	public String list(PersonalAddressBook personalAddressBook, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PersonalAddressBook> page = personalAddressBookService.findPage(new Page<PersonalAddressBook>(request, response), personalAddressBook); 
		model.addAttribute("page", page);
		return "modules/mq/personalAddressBookList";
	}

	@RequiresPermissions("mq:personalAddressBook:view")
	@RequestMapping(value = "form")
	public String form(PersonalAddressBook personalAddressBook, Model model) {
		model.addAttribute("personalAddressBook", personalAddressBook);
		return "modules/mq/personalAddressBookForm";
	}

	@RequiresPermissions("mq:personalAddressBook:edit")
	@RequestMapping(value = "save")
	public String save(PersonalAddressBook personalAddressBook, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, personalAddressBook)){
			return form(personalAddressBook, model);
		}
		personalAddressBookService.save(personalAddressBook);
		addMessage(redirectAttributes, "保存个人通讯录成功");
		return "redirect:"+Global.getAdminPath()+"/mq/personalAddressBook/?repage";
	}
	
	@RequiresPermissions("mq:personalAddressBook:edit")
	@RequestMapping(value = "delete")
	public String delete(PersonalAddressBook personalAddressBook, RedirectAttributes redirectAttributes) {
		personalAddressBookService.delete(personalAddressBook);
		addMessage(redirectAttributes, "删除个人通讯录成功");
		return "redirect:"+Global.getAdminPath()+"/mq/personalAddressBook/?repage";
	}

	//////////////////////////////以下是json調用格式////////////////////////////////////////////
	@ResponseBody
	@RequiresPermissions("mq:personalAddressBook:view")
	@RequestMapping(value = "personalAddressBooks", method= RequestMethod.GET)
	public JsonData getPersonalAddressBooks(PersonalAddressBook personalAddressBook, HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		//personalAddressBook = (PersonalAddressBook) BizUtils.initEntity(personalAddressBook);
		if (personalAddressBook == null) {
			personalAddressBook = new PersonalAddressBook();
		}

		personalAddressBook.setCreateBy(UserUtils.getUser());
		//personalAddressBook = personalAddressBookService.addDataAuthorityCondition(personalAddressBook, "CreateunitCode", false);

		try {
			Page<PersonalAddressBook> page = personalAddressBookService.findPage(new Page<PersonalAddressBook>(), personalAddressBook);
			// 判断登录系统的用户角色
			boolean isAdmin = UserUtils.getUser().isAdmin();
			String officeGrade = UserUtils.getUser().getOffice().getGrade();

			//把单位通讯录、个人通讯录、个人通讯录默认加入
			List personalAddressBookList = page.getList();
			PersonalAddressBook personalAddressBookTemp = new PersonalAddressBook();
			personalAddressBookTemp.setId("0");
			personalAddressBookTemp.setName("单位通讯录");
			personalAddressBookList.add(personalAddressBookTemp);

			personalAddressBookTemp.setId("1");
			personalAddressBookTemp.setName("个人通讯录");
			personalAddressBookList.add(personalAddressBookTemp);

			personalAddressBookTemp.setId("2");
			personalAddressBookTemp.setName("个人通讯录");
			personalAddressBookList.add(personalAddressBookTemp);

			jsonData.setSuccess(true).setData(personalAddressBookList).setTotalCount(personalAddressBookList.size());
		}
		catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:personalAddressBook:view")
	@RequestMapping(value = "personalAddressBooks/{id}", method= RequestMethod.GET)
	public JsonData getPersonalAddressBookById(@RequestParam(required=false) String id) {
		JsonData jsonData = new JsonData();
		PersonalAddressBook entity = personalAddressBookService.get(id);
//		if (entity == null){
//			entity = new PersonalAddressBook();
//		}
		jsonData.setSuccess(true).setMessage("").setData(entity);

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:PersonalAddressBook:edit")
	@RequestMapping(value = "personalAddressBooks", method = RequestMethod.POST)
	public JsonData addPersonalAddressBook(@RequestBody PersonalAddressBook personalAddressBook) {
		JsonData jsonData = new JsonData();
		try {
			personalAddressBookService.save(personalAddressBook);
			jsonData.setSuccess(true).setMessage("保存个人通讯录信息成功");
		}
		catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:PersonalAddressBook:edit")
	@RequestMapping(value = "personalAddressBooks", method = RequestMethod.PUT)
	public JsonData modifyPersonalAddressBook(@RequestBody PersonalAddressBook personalAddressBook) {
		JsonData jsonData = new JsonData();
		try {
			this.addPersonalAddressBook(personalAddressBook);
			jsonData.setSuccess(true).setMessage("操作成功");
		} catch (Exception e) {
			jsonData.setSuccess(false).setMessage(e.getMessage());
		}

		return jsonData;
	}

	@ResponseBody
	@RequiresPermissions("mq:personalAddressBook:edit")
	@RequestMapping(value = "personalAddressBooks/{id}", method= RequestMethod.DELETE)
	public JsonData deletePersonalAddressBookById(PersonalAddressBook personalAddressBook) {
		JsonData jsonData = new JsonData();

		try {
			personalAddressBookService.delete(personalAddressBook);
			jsonData.setSuccess(true).setMessage("刪除个人通讯录信息成功");
		}catch (Exception e){
			jsonData.setSuccess(true).setMessage("刪除个人通讯录信息失败");
		}

		return jsonData;
	}

	public JsonData sendMsg(String ids, String message) {
		JsonData jsonData = new JsonData();
		String phones = "";
		PersonalAddressBook personalAddressBook = new PersonalAddressBook();
		personalAddressBook.setIds(ids.split(","));
		List<PersonalAddressBook> personalABList = personalAddressBookService.findListByIds(personalAddressBook);
		if (personalABList != null && personalABList.size() > 0) {
			String phone = "";
			for (int i = 0; i < personalABList.size(); i++) {
				phone = personalABList.get(i).getTel();
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
	@RequiresPermissions("mq:personalAddressBook:view")
	public JsonData getContacts(PersonalAddressBook personalAddressBook, HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		try {
			/*List<PersonalAddressBook> dataList = personalAddressBookService.findPage(new Page(request,response),personalAddressBook).getList();
			jsonData.setSuccess(true).setMessage("").setData(dataList).setTotalCount(dataList.size());*/
			Page<PersonalAddressBook> page = personalAddressBookService.findPage(new Page(request,response),personalAddressBook);
			jsonData.setSuccess(true).setMessage("").setData(page.getList()).setTotalCount((int) page.getCount());
		}catch (Exception e){
			jsonData.setSuccess(false).setMessage("查询联系人失败");
		}

		return jsonData;
	}
}