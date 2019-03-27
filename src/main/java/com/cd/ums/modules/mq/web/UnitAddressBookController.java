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
import com.cd.ums.modules.mq.entity.UnitAddressBook;
import com.cd.ums.modules.mq.service.UnitAddressBookService;

import java.util.ArrayList;
import java.util.List;

/**
 * 单位通讯录管理Controller
 *
 * @author zangyanming
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/unitAddressBook")
public class UnitAddressBookController extends BaseController {

    @Autowired
    private UnitAddressBookService unitAddressBookService;

    @ModelAttribute
    public UnitAddressBook get(@RequestParam(required = false) String id) {
        UnitAddressBook entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = unitAddressBookService.get(id);
        }
        if (entity == null) {
            entity = new UnitAddressBook();
        }
        return entity;
    }

    @RequiresPermissions("mq:unitAddressBook:view")
    @RequestMapping(value = {"list", ""})
    public String list(UnitAddressBook unitAddressBook, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UnitAddressBook> page = unitAddressBookService.findPage(new Page<UnitAddressBook>(request, response), unitAddressBook);
        model.addAttribute("page", page);
        return "modules/mq/unitAddressBookList";
    }

    @RequiresPermissions("mq:unitAddressBook:view")
    @RequestMapping(value = "form")
    public String form(UnitAddressBook unitAddressBook, Model model) {
        model.addAttribute("unitAddressBook", unitAddressBook);
        return "modules/mq/unitAddressBookForm";
    }

    @RequiresPermissions("mq:unitAddressBook:edit")
    @RequestMapping(value = "save")
    public String save(UnitAddressBook unitAddressBook, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, unitAddressBook)) {
            return form(unitAddressBook, model);
        }
        unitAddressBookService.save(unitAddressBook);
        addMessage(redirectAttributes, "保存单位通讯录成功");
        return "redirect:" + Global.getAdminPath() + "/mq/unitAddressBook/?repage";
    }

    @RequiresPermissions("mq:unitAddressBook:edit")
    @RequestMapping(value = "delete")
    public String delete(UnitAddressBook unitAddressBook, RedirectAttributes redirectAttributes) {
        unitAddressBookService.delete(unitAddressBook);
        addMessage(redirectAttributes, "删除单位通讯录成功");
        return "redirect:" + Global.getAdminPath() + "/mq/unitAddressBook/?repage";
    }

    //////////////////////////////以下是json調用格式////////////////////////////////////////////
    @ResponseBody
    @RequiresPermissions("mq:unitAddressBook:view")
    @RequestMapping(value = "unitAddressBooks", method = RequestMethod.GET)
    public JsonData getUnitAddressBooks(UnitAddressBook unitAddressBook, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        if (unitAddressBook == null) {
            unitAddressBook = new UnitAddressBook();
        }

        //
        //unitAddressBook.setCreateBy(UserUtils.getUser());
        unitAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());

        try {
            Page<UnitAddressBook> page = unitAddressBookService.findPage(new Page(request, response), unitAddressBook);
            // 判断登录系统的用户角色
            boolean isAdmin = UserUtils.getUser().isAdmin();
            String officeGrade = UserUtils.getUser().getOffice().getGrade();

            //把单位通讯录、单位通讯录、单位通讯录默认加入
            List unitAddressBookList = page.getList();
            UnitAddressBook unitAddressBookTemp = new UnitAddressBook();
            unitAddressBookTemp.setId("0");
            unitAddressBookTemp.setName("单位通讯录");
            unitAddressBookList.add(unitAddressBookTemp);

            unitAddressBookTemp.setId("1");
            unitAddressBookTemp.setName("单位通讯录");
            unitAddressBookList.add(unitAddressBookTemp);

            unitAddressBookTemp.setId("2");
            unitAddressBookTemp.setName("单位通讯录");
            unitAddressBookList.add(unitAddressBookTemp);

            jsonData.setSuccess(true).setData(unitAddressBookList).setTotalCount(unitAddressBookList.size());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:unitAddressBook:view")
    @RequestMapping(value = "unitAddressBooks/{id}", method = RequestMethod.GET)
    public JsonData getUnitAddressBookById(@RequestParam(required = false) String id) {
        JsonData jsonData = new JsonData();
        UnitAddressBook entity = unitAddressBookService.get(id);
//        if (entity == null) {
//            entity = new UnitAddressBook();
//        }

        jsonData.setSuccess(true).setMessage("").setData(entity);
        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:UnitAddressBook:edit")
    @RequestMapping(value = "unitAddressBooks", method = RequestMethod.POST)
    public JsonData addUnitAddressBook(@RequestBody UnitAddressBook unitAddressBook) {
        JsonData jsonData = new JsonData();
        try {
            unitAddressBookService.save(unitAddressBook);
            jsonData.setSuccess(true).setMessage("保存单位通讯录信息成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }

        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:UnitAddressBook:edit")
    @RequestMapping(value = "unitAddressBooks", method = RequestMethod.PUT)
    public JsonData modifyUnitAddressBook(@RequestBody UnitAddressBook unitAddressBook) {
        JsonData jsonData = new JsonData();
        try {
            this.addUnitAddressBook(unitAddressBook);
            jsonData.setSuccess(true).setMessage("操作成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }

        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:unitAddressBook:edit")
    @RequestMapping(value = "unitAddressBooks/{id}", method = RequestMethod.DELETE)
    public JsonData deleteUnitAddressBookById(UnitAddressBook unitAddressBook) {
        JsonData jsonData = new JsonData();
        try {
            unitAddressBookService.delete(unitAddressBook);
            jsonData.setSuccess(true).setMessage("刪除单位通讯录信息成功");
        } catch (Exception e) {
            jsonData.setSuccess(true).setMessage("刪除单位通讯录信息失败");
        }

        return jsonData;
    }

    @ResponseBody
    public JsonData sendMsg(String ids, String message) {
        JsonData jsonData = new JsonData();
        String phones = "";
        UnitAddressBook unitAddressBook = new UnitAddressBook();
        unitAddressBook.setIds(ids.split(","));
        List<UnitAddressBook> unitABList = unitAddressBookService.findListByIds(unitAddressBook);
        if (unitABList != null && unitABList.size() > 0) {
            String phone = "";
            for (int i = 0; i < unitABList.size(); i++) {
                phone = unitABList.get(i).getTel();
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
    @RequiresPermissions("mq:unitAddressBook:view")
    public JsonData getContacts(UnitAddressBook unitAddressBook, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        try {
            /*List<UnitAddressBook> dataList = unitAddressBookService.findPage(new Page(request,response),unitAddressBook).getList();
            jsonData.setSuccess(true).setMessage("").setData(dataList).setTotalCount(dataList.size());*/
            Page<UnitAddressBook> page = unitAddressBookService.findPage(new Page(request,response),unitAddressBook);
            jsonData.setSuccess(true).setMessage("").setData(page.getList()).setTotalCount((int) page.getCount());
        }catch (Exception e){
            jsonData.setSuccess(false).setMessage("查询联系人失败");
        }

        return jsonData;
    }
}