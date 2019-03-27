/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import com.cd.ums.common.mapper.JsonMapper;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.modules.mq.entity.SysAuditSysmsg;
import com.cd.ums.modules.mq.entity.SysLogSysmsg;
import com.cd.ums.modules.mq.entity.UmsSysMsg;
import com.cd.ums.modules.mq.entity.UmsSystem;
import com.cd.ums.modules.mq.service.*;
import com.cd.ums.modules.sys.entity.User;
import com.cd.ums.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理系统消息管理Controller
 *
 * @author hqj
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/umssysmsg")
public class UmsSysMsgController extends BaseController {

    @Autowired
    private UmsSystemService umsSystemService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private SysLogSysmsgService sysLogSysmsgService;

    @Autowired
    private SysAuditSysmsgService sysAuditSysmsgService;

    //@LogEvent(name = "发送系统消息信息")
    @ResponseBody
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public JsonData sendMsg(@RequestBody UmsSysMsg umsSysMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        if (StringUtils.isBlank(umsSysMsg.getSysCode()) ||
                StringUtils.isBlank(umsSysMsg.getName()) ||
                StringUtils.isBlank(umsSysMsg.getSysKey()) ||
                StringUtils.isBlank(umsSysMsg.getDestSysCode()) ||
                umsSysMsg.getData() == null) {
            jsonData.setSuccess(false).setMessage("参数不符合规范，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 查询请求系统
        UmsSystem queryCondition = new UmsSystem();
        queryCondition.setEqSysCode(umsSysMsg.getSysCode());
        queryCondition.setEqName(umsSysMsg.getName());
        List<UmsSystem> list = umsSystemService.findList(queryCondition);
        if (list == null || list.size() < 1) {
            jsonData.setSuccess(false).setMessage("请求系统名称或编码无效，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        UmsSystem umsSystem = list.get(0);
        // 校验请求系统审核状态
        if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_APPLY)) {
            jsonData.setSuccess(false).setMessage("请求系统处于申请状态，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_NOT_PASS)) {
            jsonData.setSuccess(false).setMessage("请求系统申请未通过，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_STOP)) {
            jsonData.setSuccess(false).setMessage("请求系统已经停用，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_OVERDUE)) {
            jsonData.setSuccess(false).setMessage("请求系统已经过期，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 校验请求系统密钥
        String sysKey = umsSystem.getSysKey();
        if (!sysKey.equals(umsSysMsg.getSysKey())) {
            jsonData.setSuccess(false).setMessage("请求系统密钥无效，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 查询目标系统
        queryCondition = new UmsSystem();
        queryCondition.setEqSysCode(umsSysMsg.getDestSysCode());
        List<UmsSystem> destList = umsSystemService.findList(queryCondition);
        if (destList == null || destList.size() < 1) {
            jsonData.setSuccess(false).setMessage("目标系统编码无效，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        UmsSystem destUmsSystem = destList.get(0);
        // 校验目标系统审核状态
        if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_APPLY)) {
            jsonData.setSuccess(false).setMessage("目标系统处于申请状态，无权限接收消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        } else if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_NOT_PASS)) {
            jsonData.setSuccess(false).setMessage("目标系统申请未通过，无权限接收消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        } else if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_STOP)) {
            jsonData.setSuccess(false).setMessage("目标系统已经停用，无权限接收消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        } else if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_OVERDUE)) {
            jsonData.setSuccess(false).setMessage("目标系统已经过期，无权限接收消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        }
        // 处理双向管道
        /*// 创建queue名称（请求系统编码-目标系统编码-请求系统密钥）
        String queueName = umsSysMsg.getSysCode() + "-" + umsSysMsg.getDestSysCode() + "-" + sysKey;*/
        // 处理单一管道
        // 创建queue名称（目标系统编码-目标系统密钥）
        String queueName = umsSysMsg.getDestSysCode() + "-" + destUmsSystem.getSysKey();
        String msg = (String) umsSysMsg.getData();
        jsonData = producerService.sendMessage(queueName, msg);
        writeLog("1", umsSysMsg, jsonData, destUmsSystem.getName(), request);
        return jsonData;
    }

    //@LogEvent(name = "接收系统消息信息")
    @ResponseBody
    @RequestMapping(value = "receive", method = RequestMethod.POST)
    public JsonData receiveMsg(@RequestBody UmsSysMsg umsSysMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        // 处理双向管道,保留destSysCode参数，处理单一管道，不需要destSysCode参数
        if (umsSysMsg.getSysCode() == null || umsSysMsg.getName() == null ||
                umsSysMsg.getSysKey() == null /*|| umsSysMsg.getDestSysCode() == null*/) {
            jsonData.setSuccess(false).setMessage("参数不符合规范，请检查传递参数！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 查询请求系统
        UmsSystem queryCondition = new UmsSystem();
        queryCondition.setEqSysCode(umsSysMsg.getSysCode());
        queryCondition.setEqName(umsSysMsg.getName());
        List<UmsSystem> list = umsSystemService.findList(queryCondition);
        if (list == null || list.size() < 1) {
            jsonData.setSuccess(false).setMessage("请求系统名称或编码无效，请检查传递参数！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        UmsSystem umsSystem = list.get(0);
        // 校验请求系统审核状态
        if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_APPLY)) {
            jsonData.setSuccess(false).setMessage("请求系统处于申请状态，无权限接收消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_NOT_PASS)) {
            jsonData.setSuccess(false).setMessage("请求系统申请未通过，无权限接收消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_STOP)) {
            jsonData.setSuccess(false).setMessage("请求系统已经停用，无权限接收消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_OVERDUE)) {
            jsonData.setSuccess(false).setMessage("请求系统已经过期，无权限接收消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 校验请求系统密钥
        String sysKey = umsSystem.getSysKey();
        if (!sysKey.equals(umsSysMsg.getSysKey())) {
            jsonData.setSuccess(false).setMessage("请求系统密钥无效，无权限接收消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 处理双向管道
        /*// 查询目标系统
        queryCondition = new UmsSystem();
        queryCondition.setEqSysCode(umsSysMsg.getDestSysCode());
        List<UmsSystem> destList = umsSystemService.findList(queryCondition);
        if (destList == null || destList.size() < 1) {
            jsonData.setSuccess(false).setMessage("目标系统编码无效，请检查传递参数！");
            writeLog("2", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        UmsSystem destUmsSystem = destList.get(0);
        // 校验目标系统审核状态
        if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_APPLY)) {
            jsonData.setSuccess(false).setMessage("目标系统处于申请状态，无权限发送消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        } else if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_NOT_PASS)) {
            jsonData.setSuccess(false).setMessage("目标系统申请未通过，无权限发送消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        } else if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_STOP)) {
            jsonData.setSuccess(false).setMessage("目标系统已经停用，无权限发送消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        } else if (destUmsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_OVERDUE)) {
            jsonData.setSuccess(false).setMessage("目标系统已经过期，无权限发送消息，请联系管理员！");
            writeLog("2", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            return jsonData;
        }
        // 获取目标系统密钥
        String destSysKey = destUmsSystem.getSysKey();
        // 创建queue名称（目标系统编码-请求系统编码-目标系统密钥）
        String queueName = umsSysMsg.getDestSysCode() + "-" + umsSysMsg.getSysCode() + "-" + destSysKey;*/
        // 处理单一管道
        // 创建queue名称（请求系统编码-请求系统密钥）
        String queueName = umsSysMsg.getSysCode() + "-" + umsSysMsg.getSysKey();
        jsonData = consumerService.getAllMessage(queueName);
        // 处理双向管道
        /*writeLog("2", umsSysMsg, jsonData, destUmsSystem.getName(), request);*/
        // 处理单一管道
        writeLog("2", umsSysMsg, jsonData, "", request);
        return jsonData;
    }

    @ResponseBody
    @RequestMapping(value = "sendGroup", method = RequestMethod.POST)
    public JsonData sendSystemMessage(@RequestBody UmsSysMsg umsSysMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        String ids = umsSysMsg.getReceiverIds();
        String content = umsSysMsg.getContent();
        umsSysMsg.setData(content); // 记录日志时可能使用
        Boolean isAudit = umsSysMsg.getAudit();
        if (StringUtils.isBlank(umsSysMsg.getSysCode()) ||
                StringUtils.isBlank(umsSysMsg.getSysKey()) ||
                StringUtils.isBlank(ids) || StringUtils.isBlank(content)) {
            jsonData.setSuccess(false).setMessage("参数不符合规范，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        if (isAudit == null) {
            jsonData.setSuccess(false).setMessage("未设置发送消息是否需要审核，请检查页面条件！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 查询请求系统
        UmsSystem queryCondition = new UmsSystem();
        queryCondition.setEqSysCode(umsSysMsg.getSysCode());
        List<UmsSystem> list = umsSystemService.findList(queryCondition);
        if (list == null || list.size() < 1) {
            jsonData.setSuccess(false).setMessage("请求系统编码无效，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        UmsSystem umsSystem = list.get(0);
        umsSysMsg.setName(umsSystem.getName());
        // 校验请求系统审核状态
        if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_APPLY)) {
            jsonData.setSuccess(false).setMessage("请求系统处于申请状态，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_NOT_PASS)) {
            jsonData.setSuccess(false).setMessage("请求系统申请未通过，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_STOP)) {
            jsonData.setSuccess(false).setMessage("请求系统已经停用，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        } else if (umsSystem.getAuditStatus().equals(UmsSystem.AUDIT_STATUS_OVERDUE)) {
            jsonData.setSuccess(false).setMessage("请求系统已经过期，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 校验请求系统密钥
        String sysKey = umsSystem.getSysKey();
        if (!sysKey.equals(umsSysMsg.getSysKey())) {
            jsonData.setSuccess(false).setMessage("请求系统密钥无效，无权限发送消息，请联系管理员！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 查询目标系统
        queryCondition = new UmsSystem();
        queryCondition.setIds(ids.split(","));
        List<UmsSystem> destList = umsSystemService.findList(queryCondition);
        if (destList == null || destList.size() < 1) {
            jsonData.setSuccess(false).setMessage("目标系统无效，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        // 判断发送消息是否需要审核
        if (!isAudit) {
            jsonData = send(umsSysMsg, request);
        } else {
            try {
                SysAuditSysmsg sysAuditSysmsg = new SysAuditSysmsg();
                sysAuditSysmsg.setSendName(umsSysMsg.getName());
                sysAuditSysmsg.setSendCode(umsSysMsg.getSysCode());
                sysAuditSysmsg.setSendKey(umsSysMsg.getSysKey());
                sysAuditSysmsg.setReceiverIds(umsSysMsg.getReceiverIds());
                sysAuditSysmsg.setContent(umsSysMsg.getContent());

                String receiverNames = "";
                String receiverCodes = "";
                for (int i = 0; i < destList.size(); i++) {
                    UmsSystem destUmsSystem = destList.get(i);
                    if (receiverNames.equals("")) {
                        receiverNames += destUmsSystem.getName();
                    } else {
                        receiverNames += "," + destUmsSystem.getName();
                    }
                    if (receiverCodes.equals("")) {
                        receiverCodes += destUmsSystem.getSysCode();
                    } else {
                        receiverCodes += "," + destUmsSystem.getSysCode();
                    }
                }
                sysAuditSysmsg.setReceiverNames(receiverNames);
                sysAuditSysmsg.setReceiverCodes(receiverCodes);

                sysAuditSysmsg.setAuditStatus("0");
                sysAuditSysmsg.setCreateBy(UserUtils.getUser());

                sysAuditSysmsgService.save(sysAuditSysmsg);
                jsonData.setSuccess(true).setMessage("消息保存成功，等待系统审核！");
            } catch (Exception e) {
                jsonData.setSuccess(false).setMessage(e.getMessage());
            }
        }
        return jsonData;
    }

    // 发送消息
    public JsonData send(UmsSysMsg umsSysMsg, HttpServletRequest request) {
        JsonData jsonData = new JsonData();
        String ids = umsSysMsg.getReceiverIds();
        String content = umsSysMsg.getContent();
        umsSysMsg.setData(content); // 记录日志时可能使用
        // 查询目标系统
        UmsSystem queryCondition = new UmsSystem();
        queryCondition.setIds(ids.split(","));
        List<UmsSystem> destList = umsSystemService.findList(queryCondition);
        if (destList == null || destList.size() < 1) {
            jsonData.setSuccess(false).setMessage("目标系统无效，请检查传递参数！");
            writeLog("1", umsSysMsg, jsonData, "", request);
            return jsonData;
        }
        StringBuilder failureMsg = new StringBuilder();
        for (int i = 0; i < destList.size(); i++) {
            UmsSystem destUmsSystem = destList.get(i);
            // 创建queue名称（目标系统编码-目标系统密钥）
            String queueName = destUmsSystem.getSysCode() + "-" + destUmsSystem.getSysKey();
            jsonData = producerService.sendMessage(queueName, content);
            writeLog("1", umsSysMsg, jsonData, destUmsSystem.getName(), request);
            if (!jsonData.isSuccess()) {
                failureMsg.append("<br/>发送给" + destUmsSystem.getName() + jsonData.getMessage());
            }
        }
        if (StringUtils.isBlank(failureMsg.toString())) {
            jsonData.setSuccess(true).setMessage("消息发送成功！");
        } else {
            jsonData.setSuccess(true).setMessage("消息发送完毕！" + failureMsg.toString());
        }
        return jsonData;
    }

    // 写日志信息
    private void writeLog(String type, UmsSysMsg umsSysMsg, JsonData jsonData,
                          String sysName, HttpServletRequest request) {
        SysLogSysmsg sysLogSysmsg = new SysLogSysmsg();
        sysLogSysmsg.setType(type);
        User user = new User();
        user.setName(umsSysMsg.getName());
        sysLogSysmsg.setCreateBy(user);
        if (type.equals("1")) {
            sysLogSysmsg.setTitle("消息发送");
            sysLogSysmsg.setSendBy(umsSysMsg.getName());
            sysLogSysmsg.setReceiveBy(sysName);
        } else if (type.equals("2")) {
            sysLogSysmsg.setTitle("消息接收");
            sysLogSysmsg.setSendBy(sysName);
            sysLogSysmsg.setReceiveBy(umsSysMsg.getName());
        }
        sysLogSysmsg.setSuccess(jsonData.isSuccess() ? "1" : "0");
        sysLogSysmsg.setMsgCount(jsonData.getTotalCount());
        sysLogSysmsg.setException(jsonData.isSuccess() ? "" : jsonData.getMessage());

        if (request != null) {
            sysLogSysmsg.setRemoteAddr(StringUtils.getRemoteAddr(request));
            sysLogSysmsg.setUserAgent(request.getHeader("user-agent"));
            sysLogSysmsg.setRequestUri(request.getRequestURI());
            sysLogSysmsg.setMethod(request.getMethod());
            //sysLogSysmsg.setParams(request.getParameterMap());
        }
        String params = JsonMapper.toJsonString(umsSysMsg);
        sysLogSysmsg.setParams(params);

        sysLogSysmsgService.save(sysLogSysmsg);
    }

    public static void main(String[] args) throws Exception {
    }
}
