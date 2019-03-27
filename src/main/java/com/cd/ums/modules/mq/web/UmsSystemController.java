/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.utils.IdGen;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.modules.mq.entity.UmsSystem;
import com.cd.ums.modules.mq.service.UmsSystemService;
import com.cd.ums.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统消息申请管理Controller
 *
 * @author hqj
 * @version 2018-10-07
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/umsSystem")
public class UmsSystemController extends BaseController {

    @Autowired
    private UmsSystemService umsSystemService;

    @ModelAttribute
    public UmsSystem get(@RequestParam(required = false) String id) {
        UmsSystem entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = umsSystemService.get(id);
        }
        if (entity == null) {
            entity = new UmsSystem();
        }
        return entity;
    }

    @RequiresPermissions("mq:umsSystem:view")
    @RequestMapping(value = {"list", ""})
    public String list(UmsSystem umsSystem, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UmsSystem> page = umsSystemService.findPage(new Page<UmsSystem>(request, response), umsSystem);
        model.addAttribute("page", page);
        return "modules/mq/umsSystemList";
    }

    @RequiresPermissions("mq:umsSystem:view")
    @RequestMapping(value = "form")
    public String form(UmsSystem umsSystem, Model model, String op) {
        model.addAttribute("umsSystem", umsSystem);
        if (StringUtils.isNotEmpty(op)) {
            if (op.equals("1")) { // 新增
                return "modules/mq/umsSystemFormAdd";
            } else if (op.equals("2")) { // 编辑
                return "modules/mq/umsSystemFormEdit";
            } else if (op.equals("3")) { // 审核
                return "modules/mq/umsSystemFormCheck";
            } else { // 查看
                return "modules/mq/umsSystemFormView";
            }
        } else { // 查看
            return "modules/mq/umsSystemFormView";
        }
    }

    @RequiresPermissions("mq:umsSystem:edit")
    @RequestMapping(value = "save")
    public String save(UmsSystem umsSystem, Model model, RedirectAttributes redirectAttributes, String op) {
        if (!beanValidator(model, umsSystem)) {
            return form(umsSystem, model, op);
        }
        String msg = "";
        if (StringUtils.isNotEmpty(op)) {
            if (op.equals("1")) { // 新增
                umsSystem.setAuditStatus("1");
                umsSystem.setAuditBy(UserUtils.getUser().getName());
                umsSystem.setAuditDate(new Date());
                umsSystem.setAuditReason("");
                umsSystem.setSysKey(IdGen.uuid());
                msg = "新增应用系统申请信息成功";
            } else if (op.equals("2")) { // 编辑
                String id = umsSystem.getId();
                UmsSystem oldUmsSystem = umsSystemService.get(id);
                if (!oldUmsSystem.getAuditStatus().equals(umsSystem.getAuditStatus())) {
                    umsSystem.setAuditBy(UserUtils.getUser().getName());
                    umsSystem.setAuditDate(new Date());
                    if (umsSystem.getAuditStatus().equals("1")) {
                        umsSystem.setSysKey(IdGen.uuid());
                    }
                }
                msg = "编辑应用系统申请信息成功";
            } else if (op.equals("3")) { // 审核
                umsSystem.setAuditBy(UserUtils.getUser().getName());
                umsSystem.setAuditDate(new Date());
                if (umsSystem.getAuditStatus().equals("1")) {
                    umsSystem.setSysKey(IdGen.uuid());
                }
                msg = "审核应用系统申请信息成功";
            }
        }
        umsSystemService.save(umsSystem);
        addMessage(redirectAttributes, msg);
        return "redirect:" + Global.getAdminPath() + "/mq/umsSystem/?repage";
    }

    @RequiresPermissions("mq:umsSystem:edit")
    @RequestMapping(value = "delete")
    public String delete(UmsSystem umsSystem, RedirectAttributes redirectAttributes) {
        umsSystemService.delete(umsSystem);
        addMessage(redirectAttributes, "删除应用系统申请信息成功");
        return "redirect:" + Global.getAdminPath() + "/mq/umsSystem/?repage";
    }

    /**
     * 验证系统名称是否有效
     *
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String id, String name) {
        if (name != null) {
            UmsSystem umsSystem = new UmsSystem();
            umsSystem.setId(id);
            umsSystem.setName(name);
            List list = umsSystemService.findByName(umsSystem);
            if (list != null && list.size() > 0) {
                return "false";
            }
        }
        return "true";
    }

    /**
     * 验证系统编码是否有效
     *
     * @param sysCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkSysCode")
    public String checkSysCode(String id, String sysCode) {
        if (sysCode != null) {
            UmsSystem umsSystem = new UmsSystem();
            umsSystem.setId(id);
            umsSystem.setSysCode(sysCode);
            List list = umsSystemService.findBySysCode(umsSystem);
            if (list != null && list.size() > 0) {
                return "false";
            }
        }
        return "true";
    }

    @RequiresPermissions("mq:umsSystem:view")
    //@LogEvent(name = "查询应用系统申请信息")
    @ResponseBody
    @RequestMapping(value = {"list/json", "json"})
    public JsonData listJson(UmsSystem umsSystem, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        //增加数据权限控制，只查询本人创建数据
        if (umsSystem.getCreateBy() == null) {
            umsSystem.setCreateBy(UserUtils.getUser());
        } else {
            if (StringUtils.isBlank(umsSystem.getCreateBy().getId())) {
                umsSystem.setCreateBy(UserUtils.getUser());
            }
        }
        try {
            Page<UmsSystem> page = umsSystemService.findPage(new Page<UmsSystem>(request, response), umsSystem);
            List<UmsSystem> list = page.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String auditStatus = list.get(i).getAuditStatus();
                    if (StringUtils.isBlank(auditStatus) || auditStatus.equals("0")) {
                        list.get(i).setAuditStatusName("申请");
                        List<String> operations = new ArrayList<>();
                        operations.add("edit");
                        operations.add("delete");
                        list.get(i).setOperations(operations);
                    } else if (auditStatus.equals("1")) {
                        list.get(i).setAuditStatusName("通过");
                    } else if (auditStatus.equals("2")) {
                        list.get(i).setAuditStatusName("不通过");
                    } else if (auditStatus.equals("3")) {
                        list.get(i).setAuditStatusName("停用");
                    } else if (auditStatus.equals("4")) {
                        list.get(i).setAuditStatusName("超期");
                    }
                }
            }
            jsonData.setSuccess(true).setData(list).setTotalCount((int) page.getCount());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @RequiresPermissions("mq:umsSystem:view")
    //@LogEvent(name = "查询应用系统通过申请的信息")
    @ResponseBody
    @RequestMapping(value = "list/pass/json")
    public JsonData listPassJson(UmsSystem umsSystem, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        try {
            umsSystem.setStatus("1");
            Page<UmsSystem> page = umsSystemService.findPage(new Page<UmsSystem>(request, response), umsSystem);
            jsonData.setSuccess(true).setData(page.getList()).setTotalCount((int) page.getCount());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @RequiresPermissions("mq:umsSystem:edit")
    @ResponseBody
    @RequestMapping(value = "save/json")
    public JsonData saveJson(@RequestBody UmsSystem umsSystem, Model model) {
        JsonData jsonData = new JsonData();
        if (!beanValidator(model, umsSystem)) {
            jsonData.setSuccess(false).setMessage("数据验证失败!");
            return jsonData;
        }
        try {
            umsSystemService.save(umsSystem);
            jsonData.setSuccess(true).setMessage("保存应用系统申请信息成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("保存应用系统申请信息失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    @RequiresPermissions("mq:umsSystem:edit")
    //@LogEvent(name = "删除应用系统申请信息")
    @ResponseBody
    @RequestMapping(value = "delete/json")
    public JsonData deleteJson(UmsSystem umsSystem) {
        JsonData jsonData = new JsonData();
        try {
            umsSystemService.delete(umsSystem);
            jsonData.setSuccess(true).setMessage("删除应用系统申请信息成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("删除应用系统申请信息失败！原因：" + e.getMessage());
        }
        return jsonData;
    }
}