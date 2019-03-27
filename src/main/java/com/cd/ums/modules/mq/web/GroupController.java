/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/cd/ums">Ums</a> All rights reserved.
 */
package com.cd.ums.modules.mq.web;

import com.cd.ums.common.config.Global;
import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.persistence.Page;
import com.cd.ums.common.utils.StringUtils;
import com.cd.ums.common.web.BaseController;
import com.cd.ums.modules.mq.entity.Contact;
import com.cd.ums.modules.mq.entity.Group;
import com.cd.ums.modules.mq.service.ContactService;
import com.cd.ums.modules.mq.service.GroupService;
import com.cd.ums.modules.sys.entity.Menu;
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
import java.util.List;

/**
 * 群组管理Controller
 *
 * @author zangyanming
 * @version 2018-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/mq/group")
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private ContactService contactService;

    @ModelAttribute
    public Group get(@RequestParam(required = false) String id) {
        Group entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = groupService.get(id);
        }
        if (entity == null) {
            entity = new Group();
        }
        return entity;
    }

    @RequiresPermissions("mq:group:view")
    @RequestMapping(value = {"list", ""})
    public String list(Group group, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Group> page = groupService.findPage(new Page<Group>(request, response), group);
        model.addAttribute("page", page);
        return "modules/mq/groupList";
    }

    @RequiresPermissions("mq:group:view")
    @RequestMapping(value = "form")
    public String form(Group group, Model model) {
        model.addAttribute("group", group);
        return "modules/mq/groupForm";
    }

    @RequiresPermissions("mq:group:edit")
    @RequestMapping(value = "save")
    public String save(Group group, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, group)) {
            return form(group, model);
        }
        groupService.save(group);
        addMessage(redirectAttributes, "保存群组成功");
        return "redirect:" + Global.getAdminPath() + "/mq/group/?repage";
    }

    @RequiresPermissions("mq:group:edit")
    @RequestMapping(value = "delete")
    public String delete(Group group, RedirectAttributes redirectAttributes) {
        groupService.delete(group);
        addMessage(redirectAttributes, "删除群组成功");
        return "redirect:" + Global.getAdminPath() + "/mq/group/?repage";
    }


    //////////////////////////////以下是json調用格式////////////////////////////////////////////
    @ResponseBody
    @RequiresPermissions("mq:group:view")
    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public JsonData getGroups(Group group, HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        if (group == null) {
            group = new Group();
        }

        group.setCreateBy(UserUtils.getUser());

        try {
            //Page<Group> page = groupService.findPage(new Page<Group>(request, response), group);
            // 判断登录系统的用户角色
            boolean isAdmin = UserUtils.getUser().isAdmin();
            String officeGrade = UserUtils.getUser().getOffice().getGrade();


            List groupList = groupService.findList(group);
            // 分组操作的权限
            if (groupList != null && groupList.size() > 0) {
                for (int i = 0; i < groupList.size(); i++) {
                    Group groupTemp = new Group();
                    groupTemp = (Group) groupList.get(i);
                    List<String> operations = new ArrayList<>();
                    operations.add("edit");
                    operations.add("delete");
                    groupTemp.setOperations(operations);
                }
            }
            //获取该用户的菜单,将有权限的菜单名称加入到群组中,如果是系统管理员,默认都加入
            //用户权限菜单
            List<Menu> menuList = UserUtils.getMenuList();
            List groupRoleList = new ArrayList<>();
            for (int i = 0; i < menuList.size(); i++) {
                Group groupTemp = new Group();
                if (menuList.get(i).getName().equals("单位通讯录")) {
                    groupTemp.setId("0");
                    groupTemp.setName("单位通讯录");
                    groupRoleList.add(groupTemp);
                }
                if (menuList.get(i).getName().equals("家长通讯录")) {
                    groupTemp = new Group();
                    groupTemp.setId("1");
                    groupTemp.setName("家长通讯录");
                    groupRoleList.add(groupTemp);
                }
                if (menuList.get(i).getName().equals("个人通讯录")) {
                    groupTemp = new Group();
                    groupTemp.setId("2");
                    groupTemp.setName("个人通讯录");
                    groupRoleList.add(groupTemp);
                }
            }

            // 追加群组
            groupRoleList.addAll(groupList);

            jsonData.setSuccess(true).setData(groupRoleList).setTotalCount(groupRoleList.size());
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:group:view")
    @RequestMapping(value = "groups/{id}", method = RequestMethod.GET)
    public JsonData getGroupById(@RequestParam(required = false) String id) {
        JsonData jsonData = new JsonData();
        Group entity = groupService.get(id);
        if (entity == null) {
            entity = new Group();
        }

        jsonData.setSuccess(true).setMessage("操作成功").setData(entity).setTotalCount(1);
        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:group:edit")
    @RequestMapping(value = "groups", method = RequestMethod.POST)
    public JsonData addGroup(@RequestBody Group group) {
        JsonData jsonData = new JsonData();
        group.setId(null);

        Group groupTemp = new Group();
        groupTemp.setName(group.getName());
        groupTemp.setCreateBy(UserUtils.getUser());
        List<Group> groupList = groupService.findList(groupTemp);
        if (groupList.isEmpty()) {
            try {
                groupService.save(group);
                jsonData.setSuccess(true).setMessage("操作成功");
            } catch (Exception e) {
                jsonData.setSuccess(false).setMessage(e.getMessage());
            }
        } else {
            jsonData.setSuccess(false).setMessage("该群组名称已经存在");
        }

        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:Group:edit")
    @RequestMapping(value = "groups", method = RequestMethod.PUT)
    public JsonData modifyGroup(@RequestBody Group group) {
        JsonData jsonData = new JsonData();
        try {
            groupService.save(group);
            jsonData.setSuccess(true).setMessage("操作成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("操作失败" + e.getMessage());
        }
        return jsonData;
    }

    @ResponseBody
    @RequiresPermissions("mq:group:edit")
    @RequestMapping(value = "groups/{id}", method = RequestMethod.DELETE)
    public JsonData deleteGroupById(@RequestParam(required = false) String id, Group group) {
        JsonData jsonData = new JsonData();

        Contact contact = new Contact();
        try {
            //先删除该用户该群组该联系人
            contact.setCreateBy(UserUtils.getUser());
            contact.setGroupId(group.getId());
            contactService.deleteAllByGroupId(contact);
            //删除该群组
            groupService.delete(group);
            jsonData.setSuccess(true).setMessage("操作成功");
        } catch (Exception e) {
            jsonData.setSuccess(false).setMessage("操作失败" + e.getMessage());
        }

        jsonData.setSuccess(true).setMessage("操作成功");

        return jsonData;
    }
}