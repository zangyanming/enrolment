/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.email.web;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.mapper.JsonMapper;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.modules.email.entity.SysAuditEmail;
import com.cd.ums.modules.email.entity.SysLogEmail;
import com.cd.ums.modules.email.model.MailContent;
import com.cd.ums.modules.email.model.MailMsg;
import com.cd.ums.modules.email.service.MailServiceImpl;
import com.cd.ums.modules.email.service.SysAuditEmailService;
import com.cd.ums.modules.email.service.SysLogEmailService;
import com.cd.ums.modules.mq.entity.Contact;
import com.cd.ums.modules.mq.entity.ParentAddressBook;
import com.cd.ums.modules.mq.entity.PersonalAddressBook;
import com.cd.ums.modules.mq.entity.UnitAddressBook;
import com.cd.ums.modules.mq.service.ContactService;
import com.cd.ums.modules.mq.service.ParentAddressBookService;
import com.cd.ums.modules.mq.service.PersonalAddressBookService;
import com.cd.ums.modules.mq.service.UnitAddressBookService;
import com.cd.ums.modules.sys.entity.User;
import com.cd.ums.modules.sys.utils.UserUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端发送请求地址管理Controller
 *
 * @author hqj
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/email/mail")
public class MailController extends BaseController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UnitAddressBookService unitAddressBookService;
    @Autowired
    private ParentAddressBookService parentAddressBookService;
    @Autowired
    private PersonalAddressBookService personalAddressBookService;

    @Autowired
    private SysLogEmailService sysLogEmailService;

    @Autowired
    private SysAuditEmailService sysAuditEmailService;

    @ResponseBody
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public JsonData sendMsg(@RequestBody MailMsg mailMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        MailContent mailContent = new MailContent();

        // 解析请求中的数据
        /*String subject = mpRequest.getParameter("subject");
        String content = mpRequest.getParameter("content");
        String receiverIds = mpRequest.getParameter("receiverIds");*/
        String receiverIds = mailMsg.getReceiverIds();
        String subject = mailMsg.getSubject();
        String content = mailMsg.getContent();
        Boolean isAudit = mailMsg.getAudit();
        if (StringUtils.isEmpty(receiverIds)){
            jsonData.setSuccess(false).setMessage("邮件接收者不能为空！");
            writeLog(mailContent, jsonData, request);
            return jsonData;
        }
        if (StringUtils.isEmpty(subject)) {
            jsonData.setSuccess(false).setMessage("邮件主题不能为空！");
            writeLog(mailContent, jsonData, request);
            return jsonData;
        }
        if (StringUtils.isEmpty(content)) {
            jsonData.setSuccess(false).setMessage("邮件内容不能为空！");
            writeLog(mailContent, jsonData, request);
            return jsonData;
        }
        if (isAudit == null) {
            jsonData.setSuccess(false).setMessage("未设置发送消息是否需要审核，请检查页面条件！");
            writeLog(mailContent, jsonData, request);
            return jsonData;
        }
        // 处理附件
        // 获取内容类型
        String contentType = request.getContentType();
        if (contentType == null || !contentType.startsWith("multipart") ||
                !ServletFileUpload.isMultipartContent(request)) {
            mailContent.setAttach(false);
            mailContent.setAttachName("");
        } else {
            // 获取上传的文件(多个)
            MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mpRequest.getFileMap();
            /*for(Map.Entry<String, MultipartFile> entry : fileMap.entrySet()){
                // 对文件进处理
                System.out.println(entry.getKey() + ":" + entry.getValue().getOriginalFilename());
            }*/
            // 目前只处理单个附件
            if (fileMap.size() > 0) {
                MultipartFile file = fileMap.get("upload");
                if (file == null) {
                    jsonData.setSuccess(false).setMessage("附件上传失败！");
                    writeLog(mailContent, jsonData, request);
                    return jsonData;
                } else {
                    long maxSize = Long.parseLong(Global.getConfig("web.maxUploadSize"));
                    long contentLength = request.getContentLength();
                    if (contentLength > maxSize) {
                        jsonData.setSuccess(false).setMessage("上传文件大小超出文件最大大小！");
                        writeLog(mailContent, jsonData, request);
                        return jsonData;
                    }
                    String path = mpRequest.getSession().getServletContext().getRealPath("userfiles");
                    // 如果不存在目录，则创建
                    File f = new File(path);
                    if(!f.exists()) {
                        f.mkdirs();
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String dateStr = sdf.format(new Date());
                    String originalFileName = file.getOriginalFilename();
                    String fileName = dateStr + "_" + originalFileName;
                    String attachPathName = path + File.separator + fileName;
                    try {
                        File ff = new File(attachPathName);
                        file.transferTo(ff);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mailContent.setAttach(true);
                    mailContent.setAttachName(attachPathName);
                }
            } else {
                mailContent.setAttach(false);
                mailContent.setAttachName("");
            }
        }

        // 查询邮件接收者昵称及邮箱地址
        //由于前台只传入了联系人receiverIds，并不知道是哪个群组里的，因此需要从4个群组里分别查找
        UnitAddressBook unitAddressBook = new UnitAddressBook();
        unitAddressBook.setIds(receiverIds.split(","));
        List<UnitAddressBook> unitList = unitAddressBookService.findListByIds(unitAddressBook);
        ParentAddressBook parentAddressBook = new ParentAddressBook();
        parentAddressBook.setIds(receiverIds.split(","));
        List<ParentAddressBook> parentList = parentAddressBookService.findListByIds(parentAddressBook);
        PersonalAddressBook personalAddressBook = new PersonalAddressBook();
        personalAddressBook.setIds(receiverIds.split(","));
        List<PersonalAddressBook> personalList = personalAddressBookService.findListByIds(personalAddressBook);
        Contact contact = new Contact();
        contact.setIds(receiverIds.split(","));
        List<Contact> contactList = contactService.findListByIds(contact);

        Map<String, String> emailMap = new HashMap<>();
        String emailName = "";
        String emailAddress = "";
        if (!unitList.isEmpty()) {
            for (int i = 0; i < unitList.size(); i++) {
                emailName = unitList.get(i).getName();
                emailAddress = unitList.get(i).getEmail();
                /*if (StringUtils.isNotEmpty(emailName) && StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailName, emailAddress);
                }*/
                if (StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailAddress, emailAddress);
                }
            }
        }
        if (!parentList.isEmpty()) {
            for (int i = 0; i < parentList.size(); i++) {
                emailName = parentList.get(i).getName();
                emailAddress = parentList.get(i).getEmail();
                /*if (StringUtils.isNotEmpty(emailName) && StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailName, emailAddress);
                }*/
                if (StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailAddress, emailAddress);
                }
            }
        }
        if (!personalList.isEmpty()) {
            for (int i = 0; i < personalList.size(); i++) {
                emailName = personalList.get(i).getName();
                emailAddress = personalList.get(i).getEmail();
                /*if (StringUtils.isNotEmpty(emailName) && StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailName, emailAddress);
                }*/
                if (StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailAddress, emailAddress);
                }
            }
        }
        if (!contactList.isEmpty()) {
            for (int i = 0; i < contactList.size(); i++) {
                emailName = contactList.get(i).getName();
                emailAddress = contactList.get(i).getEmail();
                /*if (StringUtils.isNotEmpty(emailName) && StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailName, emailAddress);
                }*/
                if (StringUtils.isNotEmpty(emailAddress)) {
                    emailMap.put(emailAddress, emailAddress);
                }
            }
        }
        mailContent.setReceivemail(emailMap);
        /*// 测试数据
        Map<String, String> map = new HashMap<>();
        map.put("hqj", "150091225@qq.com");
        map.put("zym", "307952767@qq.com");
        mailContent.setReceivemail(map);*/

        mailContent.setSubject(subject);
        mailContent.setContent(content);

        // 判断发送消息是否需要审核
        if (!isAudit) {
            jsonData = send(mailContent, request);
        } else {
            try {
                SysAuditEmail sysAuditEmail = new SysAuditEmail();
                sysAuditEmail.setSubject(mailContent.getSubject());
                sysAuditEmail.setContent(mailContent.getContent());
                sysAuditEmail.setReceiverEmails(JsonMapper.toJsonString(mailContent.getReceivemail()));
                sysAuditEmail.setAttachName(mailContent.getAttachName());

                sysAuditEmail.setSendBy(Global.getConfig("sendMailName"));
                sysAuditEmail.setReceiverIds(mailMsg.getReceiverIds());
                sysAuditEmail.setAuditStatus("0");
                sysAuditEmail.setCreateBy(UserUtils.getUser());

                sysAuditEmailService.save(sysAuditEmail);
                jsonData.setSuccess(true).setMessage("消息保存成功，等待系统审核！");
            } catch (Exception e) {
                jsonData.setSuccess(false).setMessage(e.getMessage());
            }
        }
        return jsonData;
    }

    // 发送邮件
    public JsonData send(MailContent mailContent, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        MailServiceImpl mail = new MailServiceImpl();
        jsonData = mail.sendMail(mailContent);
        writeLog(mailContent, jsonData, request);
        if (jsonData.isSuccess()) {
            jsonData.setData(null);
        }
        return jsonData;
    }

    // 写日志信息
    private void writeLog(MailContent mailContent, JsonData jsonData, HttpServletRequest request) {
        SysLogEmail sysLogEmail = new SysLogEmail();
        sysLogEmail.setTitle("邮件消息发送");
        User user = UserUtils.getUser();
        sysLogEmail.setCreateBy(user);
        sysLogEmail.setSendBy(Global.getConfig("sendMailName"));
        sysLogEmail.setReceiveBy(JsonMapper.toJsonString(mailContent.getReceivemail()));
        if (jsonData.isSuccess()) {
            MimeMessage message = (MimeMessage) jsonData.getData();
            try {
                sysLogEmail.setSendDate(message.getSentDate());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        sysLogEmail.setAttachName(mailContent.getAttachName());
        sysLogEmail.setSuccess(jsonData.isSuccess() ? "1" : "0");
        sysLogEmail.setException(jsonData.isSuccess() ? "" : jsonData.getMessage());

        if (request != null) {
            sysLogEmail.setRemoteAddr(StringUtils.getRemoteAddr(request));
            sysLogEmail.setUserAgent(request.getHeader("user-agent"));
            sysLogEmail.setRequestUri(request.getRequestURI());
            sysLogEmail.setMethod(request.getMethod());
            sysLogEmail.setParams(convertParams(request.getParameterMap()));
        }
        /*String params = JsonMapper.toJsonString(mailMsg);
        sysLogEmail.setParams(params);*/

        sysLogEmailService.save(sysLogEmail);
    }

    private String convertParams(Map paramMap) {
        if (paramMap == null) {
            return "";
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        return params.toString();
    }
}
