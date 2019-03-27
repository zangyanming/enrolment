/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.wx.web;

import com.cd.ums.common.mapper.JsonMapper;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.web.BaseController;
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
import com.cd.ums.modules.wx.entity.SysAuditWx;
import com.cd.ums.modules.wx.entity.SysLogWx;
import com.cd.ums.modules.wx.model.WxMsg;
import com.cd.ums.modules.wx.service.SysAuditWxService;
import com.cd.ums.modules.wx.service.SysLogWxService;
import com.cd.ums.modules.wx.service.WxServiceImpl;
import org.activiti.engine.impl.util.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 客户端发送请求地址管理Controller
 *
 * @author hqj
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wx")
public class WxController extends BaseController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UnitAddressBookService unitAddressBookService;
    @Autowired
    private ParentAddressBookService parentAddressBookService;
    @Autowired
    private PersonalAddressBookService personalAddressBookService;

    @Autowired
    private SysLogWxService sysLogWxService;

    @Autowired
    private SysAuditWxService sysAuditWxService;

    @ResponseBody
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public JsonData sendMsg(@RequestBody WxMsg wxMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();

        // 解析请求中的数据
        String receiverIds = wxMsg.getReceiverIds();
        String content = wxMsg.getContent();
        Boolean isAudit = wxMsg.getAudit();
        if (StringUtils.isEmpty(receiverIds)) {
            jsonData.setSuccess(false).setMessage("微信接收者不能为空");
            writeLog(wxMsg, jsonData, request);
            return jsonData;
        }
        if (StringUtils.isEmpty(content)) {
            jsonData.setSuccess(false).setMessage("微信消息不能为空");
            writeLog(wxMsg, jsonData, request);
            return jsonData;
        }
        if (isAudit == null) {
            jsonData.setSuccess(false).setMessage("未设置发送消息是否需要审核，请检查页面条件！");
            writeLog(wxMsg, jsonData, request);
            return jsonData;
        }

        // 查询微信消息接收者微信号（openId）
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

        JSONArray openIds = new JSONArray();
        String openId = "";
        if (!unitList.isEmpty()) {
            for (int i = 0; i < unitList.size(); i++) {
                openId = unitList.get(i).getWxhm();
                if (StringUtils.isNotEmpty(openId)) {
                    openIds.put(openId);
                }
            }
        }
        if (!parentList.isEmpty()) {
            for (int i = 0; i < parentList.size(); i++) {
                openId = parentList.get(i).getWxhm();
                if (StringUtils.isNotEmpty(openId)) {
                    openIds.put(openId);
                }
            }
        }
        if (!personalList.isEmpty()) {
            for (int i = 0; i < personalList.size(); i++) {
                openId = personalList.get(i).getWxhm();
                if (StringUtils.isNotEmpty(openId)) {
                    openIds.put(openId);
                }
            }
        }
        if (!contactList.isEmpty()) {
            for (int i = 0; i < contactList.size(); i++) {
                openId = contactList.get(i).getWxhm();
                if (StringUtils.isNotEmpty(openId)) {
                    openIds.put(openId);
                }
            }
        }
        wxMsg.setReceiverOpenIds(openIds.toString());

        // 判断发送消息是否需要审核
        if (!isAudit) {
            jsonData = send(wxMsg, request);
        } else {
            try {
                SysAuditWx sysAuditWx = new SysAuditWx();
                sysAuditWx.setReceiverIds(receiverIds);
                sysAuditWx.setReceiverOpenids(openIds.toString());
                sysAuditWx.setContent(content);

                sysAuditWx.setSenderOfficeId(UserUtils.getUser().getOffice().getCode());
                //sysAuditWx.setSenderOfficeName(UserUtils.getUser().getOffice().getName());
                sysAuditWx.setSenderId(UserUtils.getUser().getId());
                //sysAuditWx.setSenderName(UserUtils.getUser().getName());
                //sysAuditWx.setReceiverNames("");

                sysAuditWx.setAuditStatus("0");
                sysAuditWx.setCreateBy(UserUtils.getUser());

                sysAuditWxService.save(sysAuditWx);
                jsonData.setSuccess(true).setMessage("消息保存成功，等待系统审核！");
            } catch (Exception e) {
                jsonData.setSuccess(false).setMessage(e.getMessage());
            }
        }
        return jsonData;
    }

    // 发送微信文本消息
    public JsonData send(WxMsg wxMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        WxServiceImpl wxService = new WxServiceImpl();
        jsonData = wxService.sendWxMessage(wxMsg);
        writeLog(wxMsg, jsonData, request);
        if (jsonData.isSuccess()) {
            jsonData.setData(null);
        }
        return jsonData;
    }

    // 写日志信息
    private void writeLog(WxMsg wxMsg, JsonData jsonData, HttpServletRequest request) {
        SysLogWx sysLogWx = new SysLogWx();
        sysLogWx.setTitle("微信消息发送");
        User user = UserUtils.getUser();
        sysLogWx.setCreateBy(user);
        sysLogWx.setSendOfficeId(user.getOffice().getCode());
        sysLogWx.setSendBy(user.getId());

        sysLogWx.setReceiveBy(wxMsg.getReceiverOpenIds());
        if (jsonData.isSuccess()) {
            sysLogWx.setSendDate((Date) jsonData.getData());
        }
        sysLogWx.setSuccess(jsonData.isSuccess() ? "1" : "0");
        sysLogWx.setException(jsonData.isSuccess() ? "" : jsonData.getMessage());

        if (request != null) {
            sysLogWx.setRemoteAddr(StringUtils.getRemoteAddr(request));
            sysLogWx.setUserAgent(request.getHeader("user-agent"));
            sysLogWx.setRequestUri(request.getRequestURI());
            sysLogWx.setMethod(request.getMethod());
            //sysLogWx.setParams(convertParams(request.getParameterMap()));
        }
        String params = JsonMapper.toJsonString(wxMsg);
        sysLogWx.setParams(params);

        sysLogWxService.save(sysLogWx);
    }

    /*private String convertParams(Map paramMap) {
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
    }*/
}
