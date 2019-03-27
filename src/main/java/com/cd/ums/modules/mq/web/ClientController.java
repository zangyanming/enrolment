/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import com.cd.ums.common.beanvalidator.BeanValidators;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.persistence.OperationData;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.utils.DateUtils;
import com.cd.ums.common.utils.SerializeUtil;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.utils.excel.ExportExcel;
import com.cd.ums.common.utils.excel.ImportExcel;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.modules.email.entity.SysAuditEmail;
import com.cd.ums.modules.email.model.MailContent;
import com.cd.ums.modules.email.model.MailMsg;
import com.cd.ums.modules.email.service.SysAuditEmailService;
import com.cd.ums.modules.email.web.MailController;
import com.cd.ums.modules.mq.entity.*;
import com.cd.ums.modules.mq.service.*;
import com.cd.ums.modules.sys.entity.User;
import com.cd.ums.modules.sys.security.SystemAuthorizingRealm;
import com.cd.ums.modules.sys.utils.UserUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;

/**
 * 客户端发送请求地址管理Controller
 *
 * @author hqj
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rest")
public class ClientController extends BaseController {

    // 电子邮件发送
    @Autowired
    private MailController mailController;

    @ResponseBody
    @RequestMapping(value = "email", method = RequestMethod.POST)
    public JsonData sendEmailMessage(/*@RequestBody MailMsg mailMsg,*/ HttpServletRequest request) {
        MailMsg mailMsg = new MailMsg();
        mailMsg.setReceiverIds(request.getParameter("receiverIds"));
        mailMsg.setSubject(request.getParameter("subject"));
        mailMsg.setContent(request.getParameter("content"));
        mailMsg.setAudit(Boolean.parseBoolean(request.getParameter("audit")));
        return mailController.sendMsg(mailMsg, request);
    }

    @Autowired
    private SysAuditEmailService sysAuditEmailService;

    //电子邮件审核
    @ResponseBody
    @RequestMapping(value = "emailMessageAudits", method = RequestMethod.GET)
    public JsonData SysAuditEmail(SysAuditEmail sysAuditEmail, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        /*//增加数据权限控制，只查询本人创建数据
        if (sysAuditSysmsg.getCreateBy() == null) {
            sysAuditSysmsg.setCreateBy(UserUtils.getUser());
        } else {
            if (StringUtils.isBlank(sysAuditSysmsg.getCreateBy().getId())) {
                sysAuditSysmsg.setCreateBy(UserUtils.getUser());
            }
        }*/
        try {
            Page<SysAuditEmail> page = sysAuditEmailService.findPage(new Page<SysAuditEmail>(request, response), sysAuditEmail);
            List<SysAuditEmail> list = page.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String auditStatus = list.get(i).getAuditStatus();
                    if (StringUtils.isBlank(auditStatus) || auditStatus.equals("0")) {
                        list.get(i).setAuditStatusName("未审核");
                        List<OperationData> operations = new ArrayList<OperationData>();
                        OperationData operationData1 = new OperationData();
                        operationData1.setName("pass");
                        operationData1.setTitle("通过");
                        //operationData1.setIcon("squareHandCorrect");
                        operationData1.setIcon("usercheck");
                        operations.add(operationData1);
                        OperationData operationData2 = new OperationData();
                        operationData2.setName("notPass");
                        operationData2.setTitle("不通过");
                        //operationData2.setIcon("squareMultiply");
                        operationData2.setIcon("usertimes");
                        operations.add(operationData2);
                        list.get(i).setOperations(operations);
                    } else if (auditStatus.equals("1")) {
                        list.get(i).setAuditStatusName("通过");
                    } else if (auditStatus.equals("2")) {
                        list.get(i).setAuditStatusName("不通过");
                    }
                }
            }
            jsonData.setSuccess(true).setData(list).setTotalCount((int) page.getCount());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "emailMessageAudits/{id}/audit", method = RequestMethod.POST)
    @Transactional
    public JsonData auditEmailMessageAudit(@PathVariable(value = "id") String id,
                                           @RequestBody SysAuditEmail sysAuditEmail,
                                           HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        try {
            String auditStatus = sysAuditEmail.getAuditStatus();
            SysAuditEmail entity = sysAuditEmailService.get(id);
            entity.setAuditStatus(auditStatus);
            entity.setAuditBy(UserUtils.getUser().getId());
            entity.setAuditDate(new Date());
            sysAuditEmailService.save(entity);
            jsonData.setSuccess(true).setMessage("电子邮件审核成功");
            if (auditStatus.equals("1")) {
                MailContent mailContent = new MailContent();
                mailContent.setSubject(entity.getSubject());
                mailContent.setContent(entity.getContent());
                Map map = SerializeUtil.json2Map(entity.getReceiverEmails());
                mailContent.setReceivemail(map);
                String attachName = entity.getAttachName();
                if (StringUtils.isNotEmpty(attachName)) {
                    mailContent.setAttach(true);
                    mailContent.setAttachName(attachName);
                } else {
                    mailContent.setAttach(false);
                    mailContent.setAttachName("");
                }
                jsonData = mailController.send(mailContent, request);
            }
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("电子邮件审核失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    // 应用系统注册
    @Autowired
    private UmsSystemController umsSystemController;

    @ResponseBody
    @RequestMapping(value = "systems", method = RequestMethod.GET)
    public JsonData UmsSystems(UmsSystem umsSystem, HttpServletRequest request, HttpServletResponse response) {
        return umsSystemController.listJson(umsSystem, request, response);
    }

    @ResponseBody
    @RequestMapping(value = "systems", method = RequestMethod.POST)
    public JsonData AddUmsSystem(@RequestBody UmsSystem umsSystem, Model model) {
        JsonData jsonData = new JsonData();
        String sysName = umsSystem.getName();
        String checkName = umsSystemController.checkName(null, sysName);
        if (checkName.equals("false")) {
            jsonData.setSuccess(false).setMessage("系统名称已存在");
            return jsonData;
        }
        String sysCode = umsSystem.getSysCode();
        String checkSysCode = umsSystemController.checkSysCode(null, sysCode);
        if (checkSysCode.equals("false")) {
            jsonData.setSuccess(false).setMessage("系统编码已存在");
            return jsonData;
        }
        return umsSystemController.saveJson(umsSystem, model);
    }

    @ResponseBody
    @RequestMapping(value = "systems/{id}", method = RequestMethod.GET)
    public JsonData getUmsSystem(@PathVariable(value = "id") String id, Model model) {
        JsonData jsonData = new JsonData();
        UmsSystem entity = umsSystemController.get(id);
        jsonData.setSuccess(true).setData(entity).setTotalCount(1);
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "systems/{id}", method = RequestMethod.PUT)
    public JsonData updateUmsSystem(@PathVariable(value = "id") String id, @RequestBody UmsSystem umsSystem, Model model) {
        JsonData jsonData = new JsonData();
        String sysName = umsSystem.getName();
        String checkName = umsSystemController.checkName(id, sysName);
        if (checkName.equals("false")) {
            jsonData.setSuccess(false).setMessage("系统名称已存在");
            return jsonData;
        }
        String sysCode = umsSystem.getSysCode();
        String checkSysCode = umsSystemController.checkSysCode(id, sysCode);
        if (checkSysCode.equals("false")) {
            jsonData.setSuccess(false).setMessage("系统编码已存在");
            return jsonData;
        }
        return umsSystemController.saveJson(umsSystem, model);
    }

    @ResponseBody
    @RequestMapping(value = "systems/{id}", method = RequestMethod.DELETE)
    public JsonData deleteUmsSystem(@PathVariable(value = "id") String id) {
        UmsSystem umsSystem = new UmsSystem();
        umsSystem.setId(id);
        return umsSystemController.deleteJson(umsSystem);
    }

    @ResponseBody
    @RequestMapping(value = "systems/audit/pass", method = RequestMethod.GET)
    public JsonData PassUmsSystems(UmsSystem umsSystem, HttpServletRequest request, HttpServletResponse response) {
        return umsSystemController.listPassJson(umsSystem, request, response);
    }

    // 群组管理
    @Autowired
    private GroupController groupController;

    @ResponseBody
    @RequestMapping(value = {"groups"}, method = RequestMethod.GET)
    public JsonData getGroups(Group group, HttpServletRequest request, HttpServletResponse response) {
        return groupController.getGroups(group, request, response);
    }

    @ResponseBody
    @RequestMapping(value = {"groups/{id}"}, method = RequestMethod.GET)
    public JsonData getById(@PathVariable(value = "id") String id) {
        return groupController.getGroupById(id);
    }

    @ResponseBody
    @RequestMapping(value = "groups", method = RequestMethod.POST)
    public JsonData addGroup(@RequestBody Group group) {
        JsonData jsonData = new JsonData();
        if (group == null || group.getName().isEmpty()) {
            jsonData.setSuccess(false).setMessage("群组名称不能为空");
            return jsonData;
        }
        return groupController.addGroup(group);
    }

    @ResponseBody
    @RequestMapping(value = "groups/{id}", method = RequestMethod.PUT)
    public JsonData modifyGroup(@PathVariable(value = "id") String id, @RequestBody Group group) {
        JsonData jsonData = new JsonData();

        if (group == null || group.getName().isEmpty()) {
            jsonData.setSuccess(false).setMessage("群组名称不能为空");
            return jsonData;
        }

        return groupController.modifyGroup(group);
    }

    @ResponseBody
    @RequestMapping(value = {"groups/{id}"}, method = RequestMethod.DELETE)
    public JsonData deleteGroupById(@PathVariable(value = "id") String id) {
        Group group = new Group();
        group.setId(id);
        return groupController.deleteGroupById(id, group);
    }

    // 单位通讯录管理
    @Autowired
    private UnitAddressBookController unitAddressBookController;
    //
    // 家长通讯录管理
    @Autowired
    private ParentAddressBookController parentAddressBookController;
    // 个人通讯录管理
    @Autowired
    private PersonalAddressBookController personalAddressBookController;
    // 联系人管理
    @Autowired
    private ContactController contactController;

    //获取联系人
    @ResponseBody
    @RequestMapping(value = {"groups/{groupId}/contacts"}, method = RequestMethod.GET)
    public JsonData getContacts(@PathVariable(value = "groupId") String groupId, HttpServletRequest request, HttpServletResponse response) {
        String unitCode = UserUtils.getUser().getOffice().getCode();
        if (groupId.equals("0")) {//单位通讯录
            UnitAddressBook unitAddressBook = new UnitAddressBook();
            unitAddressBook.setDwbm(unitCode);
            return unitAddressBookController.getContacts(unitAddressBook, request, response);
        }
        if (groupId.equals("1")) {//家长通讯录
            ParentAddressBook parentAddressBook = new ParentAddressBook();
            parentAddressBook.setDwbm(unitCode);
            return parentAddressBookController.getContacts(parentAddressBook, request, response);
        }
        if (groupId.equals("2")) {//个人通讯录
            PersonalAddressBook personalAddressBook = new PersonalAddressBook();
            personalAddressBook.setCreateBy(UserUtils.getUser());
            return personalAddressBookController.getContacts(personalAddressBook, request, response);
        } else {//群组
            Contact contact = new Contact();
            contact.setGroupId(groupId);
            return contactController.getContacts(contact, request, response);
        }
    }

    @ResponseBody
    @RequestMapping(value = {"groups/{groupId}/contacts/{id}"}, method = RequestMethod.GET)
    public JsonData getContactById(@PathVariable(value = "groupId") String groupId, @PathVariable(value = "id") String id) {
        Contact contact = new Contact();
        contact.setGroupId(groupId);
        contact.setId(id);
        if (groupId.equals("0")) {
            return unitAddressBookController.getUnitAddressBookById(id);
        } else if (groupId.equals("1")) {
            return parentAddressBookController.getParentAddressBookById(id);
        } else if (groupId.equals("2")) {
            return personalAddressBookController.getPersonalAddressBookById(id);
        } else {
            return contactController.getContactById(id);
        }
    }

    @ResponseBody
    @RequestMapping(value = "groups/{groupId}/contacts", method = RequestMethod.POST)
    public JsonData addContact(@PathVariable(value = "groupId") String groupId, @RequestBody Contact contact) {
        contact.setGroupId(groupId);
        if (groupId.equals("0")) {
            UnitAddressBook unitAddressBook = new UnitAddressBook();
            unitAddressBook.setName(contact.getName());
            unitAddressBook.setTel(contact.getTel());
            unitAddressBook.setEmail(contact.getEmail());
            unitAddressBook.setQqhm(contact.getQqhm());
            unitAddressBook.setWxhm(contact.getWxhm());
            unitAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
            return unitAddressBookController.addUnitAddressBook(unitAddressBook);
        } else if (groupId.equals("1")) {
            ParentAddressBook parentAddressBook = new ParentAddressBook();
            parentAddressBook.setName(contact.getName());
            parentAddressBook.setTel(contact.getTel());
            parentAddressBook.setEmail(contact.getEmail());
            parentAddressBook.setQqhm(contact.getQqhm());
            parentAddressBook.setWxhm(contact.getWxhm());
            parentAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
            return parentAddressBookController.addParentAddressBook(parentAddressBook);
        } else if (groupId.equals("2")) {
            PersonalAddressBook personalAddressBook = new PersonalAddressBook();
            personalAddressBook.setName(contact.getName());
            personalAddressBook.setTel(contact.getTel());
            personalAddressBook.setEmail(contact.getEmail());
            personalAddressBook.setQqhm(contact.getQqhm());
            personalAddressBook.setWxhm(contact.getWxhm());
            return personalAddressBookController.addPersonalAddressBook(personalAddressBook);
        } else {
            return contactController.addContact(contact);
        }
    }

    @ResponseBody
    @RequestMapping(value = "groups/{groupId}/contacts/{id}", method = RequestMethod.PUT)
    public JsonData modifyContact(@PathVariable(value = "groupId") String groupId, @PathVariable(value = "id") String id, @RequestBody Contact contact) {
        contact.setGroupId(groupId);
        if (groupId.equals("0")) {
            UnitAddressBook unitAddressBook = new UnitAddressBook();
            BeanUtils.copyProperties(contact, unitAddressBook);
            unitAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
            return unitAddressBookController.modifyUnitAddressBook(unitAddressBook);
        } else if (groupId.equals("1")) {
            ParentAddressBook parentAddressBook = new ParentAddressBook();
            BeanUtils.copyProperties(contact, parentAddressBook);
            return parentAddressBookController.modifyParentAddressBook(parentAddressBook);
        } else if (groupId.equals("2")) {
            PersonalAddressBook personalAddressBook = new PersonalAddressBook();
            BeanUtils.copyProperties(contact, personalAddressBook);
            return personalAddressBookController.modifyPersonalAddressBook(personalAddressBook);
        } else {
            return contactController.modifyContact(contact);
        }
    }

    @ResponseBody
    @RequestMapping(value = "groups/{groupId}/contacts/{id}", method = RequestMethod.DELETE)
    public JsonData deleteContactById(@PathVariable(value = "groupId") String groupId, @PathVariable(value = "id") String id) {
        Contact contact = new Contact();
        contact.setGroupId(groupId);
        contact.setId(id);

        if (groupId.equals("0")) {
            UnitAddressBook unitAddressBook = new UnitAddressBook();
            BeanUtils.copyProperties(contact, unitAddressBook);
            unitAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
            return unitAddressBookController.deleteUnitAddressBookById(unitAddressBook);
        } else if (groupId.equals("1")) {
            ParentAddressBook parentAddressBook = new ParentAddressBook();
            BeanUtils.copyProperties(contact, parentAddressBook);
            return parentAddressBookController.deleteParentAddressBookById(parentAddressBook);
        } else if (groupId.equals("2")) {
            PersonalAddressBook personalAddressBook = new PersonalAddressBook();
            BeanUtils.copyProperties(contact, personalAddressBook);
            return personalAddressBookController.deletePersonalAddressBookById(personalAddressBook);
        } else {
            return contactController.deleteContactById(contact);
        }
    }


//
//    @ResponseBody
//    @RequestMapping(value = {"unitAddressBooks"}, method = RequestMethod.GET)
//    public JsonData getUnitAddressBooks(UnitAddressBook unitAddressBook, HttpServletRequest request, HttpServletResponse response) {
//        return unitAddressBookController.getUnitAddressBooks(unitAddressBook, request, response);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"unitAddressBooks/{id}"}, method = RequestMethod.GET)
//    public JsonData getUnitAddressBookById(@RequestParam(required = false) String id) {
//        return unitAddressBookController.getUnitAddressBookById(id);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "unitAddressBooks", method = RequestMethod.POST)
//    public JsonData addUnitAddressBook(@RequestBody UnitAddressBook unitAddressBook) {
//        return unitAddressBookController.addUnitAddressBook(unitAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "unitAddressBooks/{id}", method = RequestMethod.PUT)
//    public JsonData modifyUnitAddressBook(@PathVariable(value = "id") String id, @RequestBody UnitAddressBook unitAddressBook) {
//        return unitAddressBookController.modifyUnitAddressBook(unitAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"unitAddressBooks/{id}"}, method = RequestMethod.DELETE)
//    public JsonData deleteUnitAddressBookById(@PathVariable(value = "id") String id) {
//        UnitAddressBook unitAddressBook = new UnitAddressBook();
//        unitAddressBook.setId(id);
//        return unitAddressBookController.deleteUnitAddressBookById(unitAddressBook);
//    }

//
//    @ResponseBody
//    @RequestMapping(value = {"parentAddressBooks"}, method = RequestMethod.GET)
//    public JsonData getParentAddressBooks(ParentAddressBook parentAddressBook, HttpServletRequest request, HttpServletResponse response) {
//        return parentAddressBookController.getParentAddressBooks(parentAddressBook, request, response);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"parentAddressBooks/{id}"}, method = RequestMethod.GET)
//    public JsonData getParentAddressBookById(@RequestParam(required = false) String id) {
//        return parentAddressBookController.getParentAddressBookById(id);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "parentAddressBooks", method = RequestMethod.POST)
//    public JsonData addParentAddressBook(@RequestBody ParentAddressBook parentAddressBook) {
//        return parentAddressBookController.addParentAddressBook(parentAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "parentAddressBooks/{id}", method = RequestMethod.PUT)
//    public JsonData modifyParentAddressBook(@PathVariable(value = "id") String id, @RequestBody ParentAddressBook parentAddressBook) {
//        return parentAddressBookController.modifyParentAddressBook(parentAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"parentAddressBooks/{id}"}, method = RequestMethod.DELETE)
//    public JsonData deleteParentAddressBookById(@PathVariable(value = "id") String id) {
//        ParentAddressBook parentAddressBook = new ParentAddressBook();
//        parentAddressBook.setId(id);
//        return parentAddressBookController.deleteParentAddressBookById(parentAddressBook);
//    }
//

//
//    @ResponseBody
//    @RequestMapping(value = {"personalAddressBooks"}, method = RequestMethod.GET)
//    public JsonData getPersonalAddressBooks(PersonalAddressBook personalAddressBook, HttpServletRequest request, HttpServletResponse response) {
//        return personalAddressBookController.getPersonalAddressBooks(personalAddressBook, request, response);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"personalAddressBooks/{id}"}, method = RequestMethod.GET)
//    public JsonData getPersonalAddressBookById(@RequestParam(required = false) String id) {
//        return personalAddressBookController.getPersonalAddressBookById(id);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "personalAddressBooks", method = RequestMethod.POST)
//    public JsonData addPersonalAddressBook(@RequestBody PersonalAddressBook personalAddressBook) {
//        return personalAddressBookController.addPersonalAddressBook(personalAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "personalAddressBooks/{id}", method = RequestMethod.PUT)
//    public JsonData modifyPersonalAddressBook(@PathVariable(value = "id") String id, @RequestBody PersonalAddressBook personalAddressBook) {
//        return personalAddressBookController.modifyPersonalAddressBook(personalAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"personalAddressBooks/{id}"}, method = RequestMethod.DELETE)
//    public JsonData deletePersonalAddressBookById(@PathVariable(value = "id") String id) {
//        PersonalAddressBook personalAddressBook = new PersonalAddressBook();
//        personalAddressBook.setId(id);
//        return personalAddressBookController.deletePersonalAddressBookById(personalAddressBook);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"groups/{groupId}/cantacts"}, method = RequestMethod.GET)
//    public JsonData getContactsByGroupId(@PathVariable(value = "groupId") String groupId,HttpServletRequest request, HttpServletResponse response) {
//        if(groupId.equals("0")){
//            return this.getUnitAddressBooks(null, request, response);
//        }else if(groupId.equals("1")){
//            return this.getParentAddressBooks(null, request, response);
//        }
//        else if(groupId.equals("2")){
//            return this.getPersonalAddressBooks(null, request, response);
//        }
//        else{
//            return this.getContactsByGroupId(groupId, request, response);
//        }
//    }

    //短信日志
    @Autowired
    private ShortMessageController shortMessageController;

    @ResponseBody
    @RequestMapping(value = {"shortMessages"}, method = RequestMethod.GET)
    public JsonData getShortMessages(ShortMessage shortMessage, HttpServletRequest request, HttpServletResponse response) {
        return shortMessageController.getShortMessages(shortMessage);
    }

    @ResponseBody
    @RequestMapping(value = {"shortMessages/{id}"}, method = RequestMethod.GET)
    public JsonData getShortMessageById(@RequestParam(required = false) String id) {
        return shortMessageController.getShortMessageById(id);
    }

    //发送短信消息
    @ResponseBody
    @RequestMapping(value = "sms", method = RequestMethod.POST)
    public JsonData sendShortMessage(@RequestBody ShortMessage shortMessage, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        String ids = shortMessage.getReceiverIds();
        Boolean isAudit = shortMessage.getAudit();
        if (ids.isEmpty() || ids.length() <= 0) {
            jsonData.setSuccess(false).setMessage("发送失败,失败原因：电话号码不能为空");
            return jsonData;
        }
        if (isAudit == null) {
            jsonData.setSuccess(false).setMessage("未设置发送消息是否需要审核，请检查页面条件！");
            return jsonData;
        }

        //由于前台只传入了联系人ids，并不知道是哪个群组里的，因此需要从4个群组里分别查找
        UnitAddressBook unitAddressBook = new UnitAddressBook();
        unitAddressBook.setIds(ids.split(","));
        List<UnitAddressBook> unitList = unitAddressBookService.findListByIds(unitAddressBook);
        ParentAddressBook parentAddressBook = new ParentAddressBook();
        parentAddressBook.setIds(ids.split(","));
        List<ParentAddressBook> parentList = parentAddressBookService.findListByIds(parentAddressBook);
        PersonalAddressBook personalAddressBook = new PersonalAddressBook();
        personalAddressBook.setIds(ids.split(","));
        List<PersonalAddressBook> personalList = personalAddressBookService.findListByIds(personalAddressBook);
        Contact contact = new Contact();
        contact.setIds(ids.split(","));
        List<Contact> contactList = contactService.findListByIds(contact);

        String phoneNum = "";
        List<String> phoneList = new ArrayList<String>();
        List<String> receiverNameList = new ArrayList<String>();
        if (!unitList.isEmpty()) {
            for (int i = 0; i < unitList.size(); i++) {
                phoneNum = unitList.get(i).getTel();
                if (!phoneNum.isEmpty()) {
                    phoneList.add(phoneNum);
                    receiverNameList.add(unitList.get(i).getName());
                }
            }
        }
        if (!parentList.isEmpty()) {
            for (int i = 0; i < parentList.size(); i++) {
                phoneNum = parentList.get(i).getTel();
                if (!phoneNum.isEmpty()) {
                    phoneList.add(phoneNum);
                    receiverNameList.add(parentList.get(i).getName());
                }
            }
        }

        if (!personalList.isEmpty()) {
            for (int i = 0; i < personalList.size(); i++) {
                phoneNum = personalList.get(i).getTel();
                if (!phoneNum.isEmpty()) {
                    phoneList.add(phoneNum);
                    receiverNameList.add(personalList.get(i).getName());
                }
            }
        }

        if (!contactList.isEmpty()) {
            for (int i = 0; i < contactList.size(); i++) {
                phoneNum = contactList.get(i).getTel();
                if (!phoneNum.isEmpty()) {
                    phoneList.add(phoneNum);
                    receiverNameList.add(contactList.get(i).getName());
                }
            }
        }

        //DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        // 去重
        List<String> sendPhoneList = new ArrayList<String>(new HashSet<String>(phoneList));
        String phoneNumbers = "";
        phoneNumbers += StringUtils.join(sendPhoneList, ",");

        List<String> receiverNamesList = new ArrayList<String>(new HashSet<String>(receiverNameList));
        String receiverNames = "";
        receiverNames += StringUtils.join(receiverNamesList, ",");

        // 判断发送消息是否需要审核
        if (!isAudit) {
            String ret = this.sendMsg(ids, phoneNumbers, receiverNames, shortMessage.getContent());
            if (ret.equals("00")) {
                jsonData.setSuccess(true).setMessage("发送成功");
            } else {
                jsonData.setSuccess(false).setMessage("发送失败,失败原因：" + ret);
            }
        } else {
            try {
                SysAuditSms sysAuditSms = new SysAuditSms();
                sysAuditSms.setReceiverIds(ids);
                sysAuditSms.setReceiverPhones(phoneNumbers);
                sysAuditSms.setContent(shortMessage.getContent());

                sysAuditSms.setSenderOfficeId(UserUtils.getUser().getOffice().getCode());
                //sysAuditSms.setSenderOfficeName(UserUtils.getUser().getOffice().getName());
                sysAuditSms.setSenderId(UserUtils.getUser().getId());
                //sysAuditSms.setSenderName(UserUtils.getUser().getName());
                //sysAuditSms.setReceiverNames("");

                sysAuditSms.setAuditStatus("0");
                sysAuditSms.setCreateBy(UserUtils.getUser());

                sysAuditSmsService.save(sysAuditSms);
                jsonData.setSuccess(true).setMessage("消息保存成功，等待系统审核！");
            } catch (Exception e) {
                jsonData.setSuccess(false).setMessage(e.getMessage());
            }
        }
        return jsonData;

//        if (groupId.equals("0")) {
//            return unitAddressBookController.sendMsg(ids, message);
//        } else if (groupId.equals("1")) {
//            return parentAddressBookController.sendMsg(ids, message);
//        } else if (groupId.equals("2")) {
//            return personalAddressBookController.sendMsg(ids, message);
//        } else {
//            return contactController.sendMsg(groupId, ids, message);
//        }
    }

    @Autowired
    private SysAuditSmsService sysAuditSmsService;

    //短信消息审核
    @ResponseBody
    @RequestMapping(value = "smsMessageAudits", method = RequestMethod.GET)
    public JsonData SysAuditSms(SysAuditSms sysAuditSms, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        /*//增加数据权限控制，只查询本人创建数据
        if (sysAuditSysmsg.getCreateBy() == null) {
            sysAuditSysmsg.setCreateBy(UserUtils.getUser());
        } else {
            if (StringUtils.isBlank(sysAuditSysmsg.getCreateBy().getId())) {
                sysAuditSysmsg.setCreateBy(UserUtils.getUser());
            }
        }*/
        try {
            Page<SysAuditSms> page = sysAuditSmsService.findPage(new Page<SysAuditSms>(request, response), sysAuditSms);
            List<SysAuditSms> list = page.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String auditStatus = list.get(i).getAuditStatus();
                    if (StringUtils.isBlank(auditStatus) || auditStatus.equals("0")) {
                        list.get(i).setAuditStatusName("未审核");
                        List<OperationData> operations = new ArrayList<OperationData>();
                        OperationData operationData1 = new OperationData();
                        operationData1.setName("pass");
                        operationData1.setTitle("通过");
                        //operationData1.setIcon("squareHandCorrect");
                        operationData1.setIcon("usercheck");
                        operations.add(operationData1);
                        OperationData operationData2 = new OperationData();
                        operationData2.setName("notPass");
                        operationData2.setTitle("不通过");
                        //operationData2.setIcon("squareMultiply");
                        operationData2.setIcon("usertimes");
                        operations.add(operationData2);
                        list.get(i).setOperations(operations);
                    } else if (auditStatus.equals("1")) {
                        list.get(i).setAuditStatusName("通过");
                    } else if (auditStatus.equals("2")) {
                        list.get(i).setAuditStatusName("不通过");
                    }
                }
            }
            jsonData.setSuccess(true).setData(list).setTotalCount((int) page.getCount());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "smsMessageAudits/{id}/audit", method = RequestMethod.POST)
    @Transactional
    public JsonData auditSmsMessageAudit(@PathVariable(value = "id") String id,
                                         @RequestBody SysAuditSms sysAuditSms,
                                         HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        try {
            String auditStatus = sysAuditSms.getAuditStatus();
            SysAuditSms entity = sysAuditSmsService.get(id);
            entity.setAuditStatus(auditStatus);
            entity.setAuditBy(UserUtils.getUser().getId());
            entity.setAuditDate(new Date());
            sysAuditSmsService.save(entity);
            jsonData.setSuccess(true).setMessage("短信消息审核成功");
            if (auditStatus.equals("1")) {
                String ids = entity.getReceiverIds();
                String phoneNumbers = entity.getReceiverPhones();
                String receiverNames = entity.getReceiverNames();
                String content = entity.getContent();
                String ret = this.sendMsg(ids, phoneNumbers, receiverNames, content);
                if (ret.equals("00")) {
                    jsonData.setSuccess(true).setMessage("发送成功");
                } else {
                    jsonData.setSuccess(false).setMessage("发送失败,失败原因：" + ret);
                }
            }
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("短信消息审核失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    @Autowired
    private GroupService groupService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UnitAddressBookService unitAddressBookService;
    @Autowired
    private ParentAddressBookService parentAddressBookService;
    @Autowired
    private PersonalAddressBookService personalAddressBookService;


    /**
     * 导出联系人数据
     *
     * @param groupId
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("mq:contact:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public JsonData exportFile(String groupId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        JsonData jsonData = new JsonData();
        try {
            String fileName = "联系人数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            if (groupId.equals("0")) {//单位
                UnitAddressBook unitAddressBook = new UnitAddressBook();
                unitAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
                Page<UnitAddressBook> page = unitAddressBookService.findPage(new Page<UnitAddressBook>(request, response, -1), unitAddressBook);
                new ExportExcel("联系人数据", Contact.class).setDataList(page.getList()).write(response, fileName).dispose();
            } else if (groupId.equals("1")) {//家长
                ParentAddressBook parentAddressBook = new ParentAddressBook();
                parentAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
                Page<ParentAddressBook> page = parentAddressBookService.findPage(new Page<ParentAddressBook>(request, response, -1), parentAddressBook);
                new ExportExcel("联系人数据", Contact.class).setDataList(page.getList()).write(response, fileName).dispose();
            } else if (groupId.equals("2")) {//个人
                PersonalAddressBook personalAddressBook = new PersonalAddressBook();
                Page<PersonalAddressBook> page = personalAddressBookService.findPage(new Page<PersonalAddressBook>(request, response, -1), personalAddressBook);
                new ExportExcel("联系人数据", Contact.class).setDataList(page.getList()).write(response, fileName).dispose();
            } else {
                Contact contact = new Contact();
                contact.setGroupId(groupId);
                Page<Contact> page = contactService.findPage(new Page<Contact>(request, response, -1), contact);
                new ExportExcel("联系人数据", Contact.class).setDataList(page.getList()).write(response, fileName).dispose();
            }
            jsonData.setSuccess(true).setMessage("导出联系人成功");
        } catch (Exception e) {
            jsonData.setSuccess(true).setMessage("导出联系人失败！失败信息：" + e.getMessage());
        }

        return jsonData;
    }

    /**
     * 导入联系人数据
     *
     * @param upload
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "groups/{groupId}/upload", method = RequestMethod.POST)
    public JsonData importFile(@PathVariable(value = "groupId") String groupId, MultipartFile upload, RedirectAttributes redirectAttributes) {
        JsonData jsonData = new JsonData();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();

            ImportExcel ei = null;
            try {
                ei = new ImportExcel(upload, 1, 0);
            } catch (InvalidFormatException e) {
                jsonData.setSuccess(false).setMessage("联系人导入失败，失败原因：" + e.getMessage());
                return jsonData;
            } catch (IOException e) {
                jsonData.setSuccess(false).setMessage("联系人导入失败，失败原因：" + e.getMessage());
                return jsonData;
            }

            List<Contact> list = ei.getDataList(Contact.class);
            for (Contact contact : list) {
                try {
                    if (groupId.equals(0)) {// 单位通讯录
                        UnitAddressBook unitAddressBook = new UnitAddressBook();
                        BeanUtils.copyProperties(contact, unitAddressBook);
                        unitAddressBook.setDwbm(UserUtils.getUser().getOffice().getCode());
                        UnitAddressBook unitAddressBookTemp = new UnitAddressBook();
                        unitAddressBookTemp.setDwbm(UserUtils.getUser().getOffice().getName());
                        unitAddressBookTemp.setName(unitAddressBook.getName());
                        List unitAddressBookListTemp = unitAddressBookService.findList(unitAddressBookTemp);
                        if (unitAddressBookListTemp == null && unitAddressBookListTemp.size() <= 0) {
                            BeanValidators.validateWithException(validator, unitAddressBook);
                            unitAddressBookService.save(unitAddressBook);
                            successNum++;
                        } else {
                            failureMsg.append("<br/>联系人 " + contact.getName() + " 已存在; ");
                            failureNum++;
                        }
                    } else if (groupId.equals("1")) {// 家长通讯录
                        ParentAddressBook parentAddressBook = new ParentAddressBook();
                        BeanUtils.copyProperties(contact, parentAddressBook);
                        parentAddressBook.setDwbm(UserUtils.getUser().getOffice().getName());
                        ParentAddressBook parentAddressBookTemp = new ParentAddressBook();
                        parentAddressBookTemp.setDwbm(UserUtils.getUser().getOffice().getCode());
                        parentAddressBookTemp.setName(parentAddressBook.getName());
                        List parentAddressBookListTemp = parentAddressBookService.findList(parentAddressBookTemp);
                        if (parentAddressBookListTemp == null && parentAddressBookListTemp.size() <= 0) {
                            BeanValidators.validateWithException(validator, parentAddressBook);
                            parentAddressBookService.save(parentAddressBook);
                            successNum++;
                        } else {
                            failureMsg.append("<br/>联系人 " + contact.getName() + " 已存在; ");
                            failureNum++;
                        }
                    } else if (groupId.equals("2")) {// 个人通讯录
                        PersonalAddressBook personalAddressBook = new PersonalAddressBook();
                        BeanUtils.copyProperties(contact, personalAddressBook);
                        PersonalAddressBook personalAddressBookTemp = new PersonalAddressBook();
                        personalAddressBookTemp.setName(personalAddressBook.getName());
                        List personalAddressBookListTemp = personalAddressBookService.findList(personalAddressBookTemp);
                        if (personalAddressBookListTemp == null && personalAddressBookListTemp.size() <= 0) {
                            BeanValidators.validateWithException(validator, personalAddressBook);
                            personalAddressBookService.save(personalAddressBook);
                            successNum++;
                        } else {
                            failureMsg.append("<br/>联系人 " + contact.getName() + " 已存在; ");
                            failureNum++;
                        }
                    } else {
                        contact.setPersonId(UserUtils.getUser().getId());
                        contact.setGroupId(groupId);
                        Group group = groupService.get(groupId);
                        contact.setGroupName(group.getName());
                        Contact contactTemp = new Contact();
                        contactTemp.setGroupId(groupId);
                        contactTemp.setName(contact.getName());
                        List contactListTemp = contactService.findList(contactTemp);
                        if (contactListTemp == null || contactListTemp.size() <= 0) {
                            BeanValidators.validateWithException(validator, contact);
                            contactService.save(contact);
                            successNum++;
                        } else {
                            failureMsg.append("<br/>联系人 " + contact.getName() + " 已存在; ");
                            failureNum++;
                        }
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>联系人 " + contact.getName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>联系人 " + contact.getName() + " 导入失败：" + ex.getMessage());
                }
            }

            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条联系人，导入信息如下：");
            }
            jsonData.setSuccess(true).setMessage("已成功导入 " + successNum + " 条联系人" + failureMsg);
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("导入联系人失败！失败信息：" + e.getMessage());
        }

        return jsonData;
    }

    /**
     * 下载导入联系人数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "import/template")
    public JsonData importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        JsonData jsonData = new JsonData();
        try {
            String fileName = "联系人数据导入模板.xlsx";
//            List<Contact> list = Lists.newArrayList();
//            Contact contact = new Contact();
//            contact.setPersonId(UserUtils.getUser().getId());
//            list = contactService.findList(contact);
            new ExportExcel("联系人数据", Contact.class, 2).write(response, fileName).dispose();
            jsonData.setSuccess(true).setMessage("");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("导入模板下载失败！失败信息：" + e.getMessage());
        }

        return jsonData;
    }

    //获取当前用户菜单
    @ResponseBody
    @RequestMapping(value = "menus", method = RequestMethod.GET)
    public JsonData getAllMenu() {
        JsonData jsonData = new JsonData();

        List menuList = UserUtils.getMenuList();
        jsonData.setSuccess(true).setData(menuList).setMessage("").setTotalCount(menuList.size());

        return jsonData;
    }

    //获取当前登录用户信息
    @ResponseBody
    @RequestMapping(value = "loginUser", method = RequestMethod.GET)
    public JsonData getLoginUser() {
        JsonData jsonData = new JsonData();
        try {
            User loginUser = UserUtils.getUser();
            if (loginUser != null) {
                jsonData.setSuccess(true).setData(loginUser);
            } else {
                jsonData.setSuccess(false).setData(null);
            }
        }catch (Exception e){
            jsonData.setSuccess(false).setData(null);
        }

        return jsonData;
    }


    // 发送短信消息
    @Autowired
    private ShortMessageService shortMessageService;

    private String sendMsg(String ids, String phones, String receiverNames, String message) {
        String ret = "00";
        if (phones.isEmpty() || message.isEmpty()) {
            ret = "01消息内容或电话号码不能为空";
        }

        String[] phonesSplit = phones.split(",");
        int phonesCnt = phonesSplit.length;
        int i = 0, pos = 0;
        // 每隔50个循环一下
        for (i = 0; i < phonesCnt / 50; i++) {
            String sendPhones = "";
            for (int j = 0; j < 50; j++) {
                sendPhones += phonesSplit[i * 50 + j] + ",";
                pos = i * 50 + j;
            }
            if (sendPhones.length() > 0) {
                sendPhones = sendPhones.substring(0, sendPhones.length() - 1);
            }

            //ret = SmsService.SendMsg(sendPhones, message);
        }

        // 剩余的
        for (int k = 0; k < phonesSplit.length - pos; k++) {
            String sendPhones = "";
            sendPhones += phonesSplit[k] + ",";
            if (sendPhones.length() > 0) {
                sendPhones = sendPhones.substring(0, sendPhones.length() - 1);
            }

            //ret = SmsService.SendMsg(sendPhones, message);
        }

        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setReceiverIds(ids);
        shortMessage.setReceiverNames(receiverNames);
        shortMessage.setContent(message);
        shortMessage.setSenderId(UserUtils.getUser().getId());
        shortMessage.setSenderOfficeId(UserUtils.getUser().getOffice().getCode());
        shortMessage.setSendDate(new Date());
        if (ret.equals("00")) {
            shortMessage.setSuccess("1");
        } else {
            shortMessage.setSuccess("0");
            shortMessage.setException(ret);
        }
        shortMessageService.save(shortMessage);

        return ret;
    }

    @Autowired
    private UmsSysMsgController umsSysMsgController;

    //发送系统消息(系统前端群发使用)
    @ResponseBody
    @RequestMapping(value = "systemMessage", method = RequestMethod.POST)
    public JsonData sendSystemMessage(@RequestBody UmsSysMsg umsSysMsg, HttpServletRequest request) {
        return umsSysMsgController.sendSystemMessage(umsSysMsg, request);
    }

    @Autowired
    private SysAuditSysmsgService sysAuditSysmsgService;

    //应用系统消息审核
    @ResponseBody
    @RequestMapping(value = "systemMessageAudits", method = RequestMethod.GET)
    public JsonData SysAuditSysmsgs(SysAuditSysmsg sysAuditSysmsg, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        /*//增加数据权限控制，只查询本人创建数据
        if (sysAuditSysmsg.getCreateBy() == null) {
            sysAuditSysmsg.setCreateBy(UserUtils.getUser());
        } else {
            if (StringUtils.isBlank(sysAuditSysmsg.getCreateBy().getId())) {
                sysAuditSysmsg.setCreateBy(UserUtils.getUser());
            }
        }*/
        try {
            Page<SysAuditSysmsg> page = sysAuditSysmsgService.findPage(new Page<SysAuditSysmsg>(request, response), sysAuditSysmsg);
            List<SysAuditSysmsg> list = page.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String auditStatus = list.get(i).getAuditStatus();
                    if (StringUtils.isBlank(auditStatus) || auditStatus.equals("0")) {
                        list.get(i).setAuditStatusName("未审核");
                        List<OperationData> operations = new ArrayList<OperationData>();
                        OperationData operationData1 = new OperationData();
                        operationData1.setName("pass");
                        operationData1.setTitle("通过");
                        //operationData1.setIcon("squareHandCorrect");
                        operationData1.setIcon("usercheck");
                        operations.add(operationData1);
                        OperationData operationData2 = new OperationData();
                        operationData2.setName("notPass");
                        operationData2.setTitle("不通过");
                        //operationData2.setIcon("squareMultiply");
                        operationData2.setIcon("usertimes");
                        operations.add(operationData2);
                        list.get(i).setOperations(operations);
                    } else if (auditStatus.equals("1")) {
                        list.get(i).setAuditStatusName("通过");
                    } else if (auditStatus.equals("2")) {
                        list.get(i).setAuditStatusName("不通过");
                    }
                }
            }
            jsonData.setSuccess(true).setData(list).setTotalCount((int) page.getCount());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "systemMessageAudits/{id}/audit", method = RequestMethod.POST)
    @Transactional
    public JsonData auditSystemMessageAudit(@PathVariable(value = "id") String id,
                                            @RequestBody SysAuditSysmsg sysAuditSysmsg,
                                            HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        try {
            String auditStatus = sysAuditSysmsg.getAuditStatus();
            SysAuditSysmsg entity = sysAuditSysmsgService.get(id);
            entity.setAuditStatus(auditStatus);
            entity.setAuditBy(UserUtils.getUser().getId());
            entity.setAuditDate(new Date());
            sysAuditSysmsgService.save(entity);
            jsonData.setSuccess(true).setMessage("应用系统消息审核成功");
            if (auditStatus.equals("1")) {
                UmsSysMsg umsSysMsg = new UmsSysMsg();
                umsSysMsg.setName(entity.getSendName());
                umsSysMsg.setSysCode(entity.getSendCode());
                umsSysMsg.setSysKey(entity.getSendKey());
                umsSysMsg.setReceiverIds(entity.getReceiverIds());
                umsSysMsg.setContent(entity.getContent());
                jsonData = umsSysMsgController.send(umsSysMsg, request);
            }
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("应用系统消息审核失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    // 登出
    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public JsonData logout(HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        try {
            UserUtils.getSubject().logout();
            SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
            if (principal == null) {
                jsonData.setSuccess(true);
            } else {
                jsonData.setSuccess(false);
                jsonData.setMessage("系统登出失败，请重新登出！");
            }
        } catch (Exception e) {
            jsonData.setSuccess(false);
            jsonData.setMessage(e.getMessage());
        }
        return jsonData;
    }

    //发送系统消息信息
    @ResponseBody
    @RequestMapping(value = "newsend", method = RequestMethod.GET)
    public JsonData newSend(UmsSysMsg umsSysMsg, HttpServletRequest request) {
        return umsSysMsgController.sendMsg(umsSysMsg, request);
    }

    //接收系统消息信息
    @ResponseBody
    @RequestMapping(value = "newreceive", method = RequestMethod.GET)
    public JsonData newReceive(UmsSysMsg umsSysMsg, HttpServletRequest request) {
        return umsSysMsgController.receiveMsg(umsSysMsg, request);
    }
}
