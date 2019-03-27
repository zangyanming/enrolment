/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import com.cd.ums.common.beanvalidator.BeanValidators;
import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.utils.DateUtils;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.utils.excel.ExportExcel;
import com.cd.ums.common.utils.excel.ImportExcel;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.modules.mq.entity.Contact;
import com.cd.ums.modules.mq.entity.Group;
import com.cd.ums.modules.mq.service.ContactService;
import com.cd.ums.modules.mq.service.GroupService;
import com.cd.ums.modules.sys.service.SystemService;
import com.cd.ums.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 联系人管理Controller
 *
 * @author zangyanming
 * @version 2018-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/contact")
public class ContactController extends BaseController {

    @Autowired
    private ContactService contactService;
    @Autowired
    GroupService groupService;

    @ModelAttribute
    public Contact get(@RequestParam(required = false) String id) {
        Contact entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = contactService.get(id);
        }
        if (entity == null) {
            entity = new Contact();
        }
        return entity;
    }

    @RequiresPermissions("mq:contact:view")
    @RequestMapping(value = {"list", ""})
    public String list(Contact contact, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Contact> page = contactService.findPage(new Page<Contact>(request, response), contact);
        model.addAttribute("page", page);
        return "modules/mq/contactList";
    }

    @RequiresPermissions("mq:contact:view")
    @RequestMapping(value = "form")
    public String form(Contact contact, Model model) {
        model.addAttribute("contact", contact);
        return "modules/mq/contactForm";
    }

    @RequiresPermissions("mq:contact:edit")
    @RequestMapping(value = "save")
    public String save(Contact contact, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, contact)) {
            return form(contact, model);
        }
        contactService.save(contact);
        addMessage(redirectAttributes, "保存联系人成功");
        return "redirect:" + Global.getAdminPath() + "/mq/contact/?repage";
    }

    @RequiresPermissions("mq:contact:edit")
    @RequestMapping(value = "delete")
    public String delete(Contact contact, RedirectAttributes redirectAttributes) {
        contactService.delete(contact);
        addMessage(redirectAttributes, "删除联系人成功");
        return "redirect:" + Global.getAdminPath() + "/mq/contact/?repage";
    }


    //////////////////////////////以下是json調用格式////////////////////////////////////////////
    @ResponseBody
    @RequiresPermissions("mq:contact:view")
    @RequestMapping(value = "contacts")
    public JsonData getContacts(Contact contact, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        if (contact == null) {
            contact = new Contact();
        }

        try {
            Page<Contact> page = contactService.findPage(new Page<Contact>(request, response), contact);
            jsonData.setSuccess(true).setMessage("").setData(page.getList()).setTotalCount((int) page.getCount());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }

        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:contact:view")
    @RequestMapping(value = "contacts/{id}")
    public JsonData getContactById(@RequestParam(required = false) String id) {
        JsonData jsonData = new JsonData();
        Contact entity = contactService.get(id);
//        if (entity == null) {
//            entity = new Contact();
//        }

        jsonData.setSuccess(true).setMessage("").setData(entity);
        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:Contact:edit")
    @RequestMapping(value = "contacts", method = RequestMethod.POST)
    public JsonData addContact(@RequestBody Contact contact) {
        JsonData jsonData = new JsonData();

        try {
            contactService.save(contact);
            jsonData.setSuccess(true).setMessage("操作成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }

        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:Contact:edit")
    @RequestMapping(value = "contacts", method = RequestMethod.PUT)
    public JsonData modifyContact(@RequestBody Contact contact) {
        JsonData jsonData = new JsonData();
        try {
            contactService.save(contact);
            jsonData.setSuccess(true).setMessage("保存联系人信息成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }

        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:contact:edit")
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.DELETE)
    public JsonData deleteContactById(Contact contact) {
        JsonData jsonData = new JsonData();

        try {
            contactService.delete(contact);
            jsonData.setSuccess(true).setMessage("删除联系人信息成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        jsonData.setSuccess(true).setMessage("刪除信息成功");

        return jsonData;
    }

    public JsonData sendMsg(String groupId, String ids, String message) {
        JsonData jsonData = new JsonData();
        String phones = "";
        Contact contact = new Contact();
        contact.setGroupId(groupId);
        contact.setIds(ids.split(","));
        List<Contact> contactList = contactService.findListByIds(contact);
        if (contactList != null && contactList.size() > 0) {
            String phone = "";
            for (int i = 0; i < contactList.size(); i++) {
                phone = contactList.get(i).getTel();
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
}