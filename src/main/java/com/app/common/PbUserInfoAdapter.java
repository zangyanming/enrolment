package com.app.common;

import com.cit.enrolment.common.config.Global;
import com.cit.enrolment.common.utils.SpringContextHolder;
import com.cit.enrolment.modules.sys.dao.MenuDao;
import com.cit.enrolment.modules.sys.dao.RoleDao;
import com.cit.enrolment.modules.sys.entity.Menu;
import com.cit.enrolment.modules.sys.entity.Office;
import com.cit.enrolment.modules.sys.entity.Role;
import com.cit.enrolment.modules.sys.entity.User;
import com.cit.enrolment.modules.sys.service.SystemService;
import com.cit.enrolment.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jasig.cas.client.UserInfoAdapter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;


public class PbUserInfoAdapter implements UserInfoAdapter {

    private static String DEFAULT_PASSWORD = "123456"; // 系统默认密码

    private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

    public static String loginName = "";
    public static String pwd = "";

    public PbUserInfoAdapter() {
    }

    public static String ssoid = "";

    public String getUserInfoXml() {
        StringBuffer xmlBuffer = new StringBuffer("");

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        // 获取网址的返回结果
        CloseableHttpResponse response = null;
        System.out.println(Global.getConfig("ssoUrl") + "?ssoid=" + ssoid);
        httpGet = new HttpGet(Global.getConfig("ssoUrl") + "?ssoid=" + ssoid);
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpGet);
            // System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取返回结果中的实体
        HttpEntity entity = response.getEntity();
        try {
            // EntityUtils.toString(entity);
            InputStream in = entity.getContent();

            try {
                int l = -1;
                byte[] tmp = new byte[1024];
                while ((l = in.read(tmp)) != -1) {
                    xmlBuffer.append(tmp);
                    // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
                }
            } finally {
                // 关闭低层流。
                in.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return xmlBuffer.toString();
    }

    public void acceptUserInfo(String xml, HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();
        SAXBuilder sb = new SAXBuilder(false);
        Document doc;
        try {
            StringReader reader = new StringReader(xml);
            doc = sb.build(reader);
            Element root = doc.getRootElement();
            userInfo.setYhbm(root.getChildText("yhbm"));
            userInfo.setYhmc(root.getChildText("yhmc"));
            userInfo.setYhlx(root.getChildText("yhlx"));
            userInfo.setSfzh(root.getChildText("sfzh"));
            userInfo.setSjhm(root.getChildText("sjhm"));
            userInfo.setQqhm(root.getChildText("qqhm"));
            userInfo.setWxhm(root.getChildText("wxhm"));
            userInfo.setEmail(root.getChildText("email"));
            userInfo.setDwbm(root.getChildText("dwbm"));
            userInfo.setDwmc(root.getChildText("dwmc"));
            userInfo.setDwlx(root.getChildText("dwlx"));
            userInfo.setYhqx(root.getChildText("yhqx"));
            userInfo.setYhtx(root.getChildText("yhtx"));
            userInfo.setYhfl(root.getChildText("yhfl"));
            userInfo.setGlfw(root.getChildText("glfw"));
            userInfo.setYhxlh(root.getChildText("xlh"));
            //userInfo.setYwbxlh(root.getChildText("ywbxlh"));
            //System.out.println(root.getChildText("ywbxlh"));
            userInfo.setDlip(request.getRemoteAddr());
            //System.out.println("success");
            //request.getSession().setAttribute("USERINFO_KEY", userInfo);

            /**
             *
             * 验证本地数据库是否有这个用户名的用户
             * 如果没有用户，新增用户
             *
             * */
            loginName = userInfo.getYhbm();
            pwd = DEFAULT_PASSWORD;  // 系统默认密码
            User user = systemService.getUserByLoginName(loginName);
            if (user == null) {
                user = new User();
                user.setLoginName(loginName);
                user.setPassword(SystemService.entryptPassword(pwd));
                user.setName(userInfo.getYhmc());
                user.setOffice(new Office(userInfo.getDwbm()));
                user.setEmail(userInfo.getEmail());
                user.setPhone(userInfo.getSjhm());
                user.setMobile(userInfo.getSjhm());
                user.setUserType(userInfo.getYhlx());
                user.setLoginIp(userInfo.getDlip());
                user.setLoginDate(new Date());
                user.setSfzh(userInfo.getSfzh());
                user.setQqhm(userInfo.getQqhm());
                user.setWxhm(userInfo.getWxhm());
                user.setCreateBy(new User("1", "admin"));
                user.setCreateDate(new Date());
                user.setUpdateBy(new User("1", "admin"));
                user.setUpdateDate(new Date());

                // 给定管理员角色
                List<Role> roleList = Lists.newArrayList();
                Role role = roleDao.get("1");
                roleList.add(role);
                user.setRoleList(roleList);
                // 保存用户信息
                systemService.saveUser(user);
                // 清除当前用户缓存
                if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
                    UserUtils.clearCache();
                    //UserUtils.getCacheMap().clear();
                }
            } else {
                // 为防止修改密码，登录修改成默认密码
                systemService.updatePasswordById(user.getId(), loginName, pwd);
            }
            /**
             * 验证平台配置权限
             * 根据平台配置的权限，把本地权限写入到本地数据库user表中
             * 1055-js 教师权限
             * 1055-xs 学生权限
             * 其他都默认管理员权限
             */
            /*if (userInfo.getYhqx().equals("1055-js")) {
                if (baseTeacherService.findByIdcard(userInfo.getSfzh()) == null || baseTeacherService.findByIdcard(userInfo.getSfzh()).size() == 0) {
                    BaseTeacher teacher = new BaseTeacher();
                    teacher.setName(userInfo.getYhmc());
                    teacher.setSchoolcode(userInfo.getDwbm());
                    teacher.setIdcard(userInfo.getSfzh());
                    teacher.setState("0");
                    teacher.setIsNewRecord(true);
                    baseTeacherService.save(teacher);
                    //user.setUserType(userType);
                    user.setTeacherid(Integer.parseInt(teacher.getId()));
                    for (Role r : allRole) {
                        if (r.getId().equals("6f049374098f4236ab109de5f9f4a001")) {
                            roleList.add(r);
                        }
                    }
                    user.setRoleList(roleList);
                }
            } else if (userInfo.getYhqx().equals("1055-xs")) {
                BaseNewstudent baseNewstudent = new BaseNewstudent();
                baseNewstudent.setName(userInfo.getYhmc());
                baseNewstudent.setSchoolcode(userInfo.getDwbm());
                baseNewstudent.setIdcard(userInfo.getSfzh());
                baseNewstudentService.save(baseNewstudent);
                user.setStudentid(Integer.parseInt(baseNewstudent.getId()));
                for (Role r : allRole) {
                    if (r.getId().equals("d79d36f1ce2443c094a5fd03403e77f0")) {
                        roleList.add(r);
                    }
                }
                user.setRoleList(roleList);
            } else {
                for (Role r : allRole) {
                    if (r.getId().equals("dace0cab49a7428584eb57c163f45082")) {
                        roleList.add(r);
                    }
                }
                user.setRoleList(roleList);
            }*/
            // 查询菜单权限
            List<String> list = new ArrayList<String>();
            String yhqx = userInfo.getYhqx();
            //yhqx = "19110101;19110105;19110201;19110203";
            String[] functionIds = yhqx.split(";");
            Map<String, String> map = new HashMap<String, String>();
            for (String functionId : functionIds) {
                list.add(functionId);
                if (functionId.length() == 8) {
                    String key1 = functionId.substring(0, 4);
                    String key2 = functionId.substring(0, 6);
                    map.put(key1, key1);
                    map.put(key2, key2);
                } else if (functionId.length() == 10) {
                    String key1 = functionId.substring(0, 4);
                    String key2 = functionId.substring(0, 6);
                    String key3 = functionId.substring(0, 8);
                    map.put(key1, key1);
                    map.put(key2, key2);
                    map.put(key3, key3);
                }
            }
            for (String mapKey : map.keySet()) {
                list.add(mapKey);
            }
            functionIds = (String[]) list.toArray(new String[list.size()]);
            Menu menu = new Menu();
            menu.setFunctionIds(functionIds);
            List<Menu> menuList = menuDao.findList(menu);
            UserUtils.putCache(UserUtils.CACHE_MENU_LIST, menuList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    public static void main(String args[]) {
        /*String xmlstr ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><userinfo><success>text</success><userid>123123</userid><username>username</username><id_jh>321321</id_jh><id_unit_id>text</id_unit_id><id_unit_name>text</id_unit_name><id_serial_no>text</id_serial_no><id_unit_subid>text</id_unit_subid><id_gmsfhm>text</id_gmsfhm><id_xzqh>text</id_xzqh><accesslevel>text</accesslevel><id_unit_subname>text</id_unit_subname><id_xzqhmc>text</id_xzqhmc><xxmj>text</xxmj><usertype>text</usertype><unitlevel>text</unitlevel><parentunitid>text</parentunitid><userroles>text</userroles><userrights>swdm1|123,23,12;swdm2|as,qwe,a;swdm3|sd,asd,sdd</userrights><usersubrights>text</usersubrights></userinfo>";
        PbUserInfoAdapter xx = new PbUserInfoAdapter();
		HttpServletRequest request =null;
		xx.acceptUserInfo(xmlstr,request);*/
    }

    public static String getLoginName() {
        return loginName;
    }

    public static void setLoginName(String loginName) {
        PbUserInfoAdapter.loginName = loginName;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        PbUserInfoAdapter.pwd = pwd;
    }
}


