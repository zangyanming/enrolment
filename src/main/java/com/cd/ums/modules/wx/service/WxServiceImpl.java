package com.cd.ums.modules.wx.service;

import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.common.utils.HttpClientUtil;
import com.cd.ums.modules.wx.api.IWxService;
import com.cd.ums.modules.wx.model.WxConfig;
import com.cd.ums.modules.wx.model.WxMsg;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hqj on 2018/10/27.
 */
public class WxServiceImpl implements IWxService {

    public static Map accessTokenMap;

    @Override
    public JsonData sendWxMessage(WxMsg wxMsg) {
        JsonData jsonData = new JsonData();
        try {
            WxConfig wxConfig = getWxConfig();
            // 获取access_token
            jsonData = getAccessToken(wxConfig);
            if (!jsonData.isSuccess()) {
                return jsonData;
            }
            Map map = (Map) jsonData.getData();
            String accessToken = (String) map.get("access_token");
            // 获取发送文本消息数据包
            List<String> list = new ArrayList<String>();
            String content = wxMsg.getContent();
            String openIds = wxMsg.getReceiverOpenIds();
            JSONArray jsonArray = new JSONArray(openIds);
            for (int i=0; i <jsonArray.length();i++) {
                list.add((String) jsonArray.get(i));
            }
            String params = createTextData(list, content);
            // 调用客服接口-发消息
            //https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN
            StringBuffer sb = new StringBuffer(wxConfig.getWxServer());
            sb.append("cgi-bin/message/custom/send?");
            sb.append("access_token=");
            sb.append(accessToken);
            URL httpclient = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            // 传入参数
            os.write(params.getBytes("UTF-8"));
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String result = new String(jsonBytes, "UTF-8");
            System.out.println("请求返回结果:" + result);
            os.flush();
            os.close();
            jsonData.setSuccess(true).setMessage("信息发送成功！").setData(new Date());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("信息发送失败！原因：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("信息发送失败！原因：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("信息发送失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    @Override
    public JsonData sendWxMessage(String content) {
        JsonData jsonData = new JsonData();
        try {
            WxConfig wxConfig = getWxConfig();
            // 获取access_token
            jsonData = getAccessToken(wxConfig);
            if (!jsonData.isSuccess()) {
                return jsonData;
            }
            Map map = (Map) jsonData.getData();
            String accessToken = (String) map.get("access_token");
            // 获取发送文本消息数据包
            jsonData = getAllOpenIds(wxConfig);
            if (!jsonData.isSuccess()) {
                return jsonData;
            }
            List<String> list = (List<String>) jsonData.getData();
            String params = createTextData(list, content);
            // 调用客服接口-发消息
            //https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN
            StringBuffer sb = new StringBuffer(wxConfig.getWxServer());
            sb.append("cgi-bin/message/custom/send?");
            sb.append("access_token=");
            sb.append(accessToken);
            URL httpclient = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            // 传入参数
            os.write(params.getBytes("UTF-8"));
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String result = new String(jsonBytes, "UTF-8");
            System.out.println("请求返回结果:" + result);
            os.flush();
            os.close();
            jsonData.setSuccess(true).setMessage("信息发送成功！");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("信息发送失败！原因：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("信息发送失败！原因：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage("信息发送失败！原因：" + e.getMessage());
        }
        return jsonData;
    }

    /**
     * 获取token
     * 成功返回map对象 {"access_token":"ACCESS_TOKEN","expires_in":7200}
     *
     * @param wxConfig
     * @return
     */
    public static JsonData getAccessToken(WxConfig wxConfig) {
        JsonData jsonData = new JsonData();
        // 获取有效的access_token
        try {
            StringBuffer sb = new StringBuffer(wxConfig.getWxServer());
            sb.append("cgi-bin/token?");
            sb.append("grant_type=client_credential");
            sb.append("&appid=");
            sb.append(wxConfig.getWxAppId());
            sb.append("&secret=");
            sb.append(wxConfig.getWxSecret());
            Map<String, Object> result = HttpClientUtil.get(sb.toString(), null);
            if (result.containsKey("errcode")) {
                String errcode = String.valueOf(result.get("errcode"));
                String errmsg = (String) result.get("errmsg");
                jsonData.setSuccess(false).setMessage(errcode + "," + errmsg);
            } else {
                jsonData.setSuccess(true).setMessage("获取access_token成功！").setData(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage(e.getMessage());
        }
        return jsonData;
    }

    /**
     * 获取openIds
     * 成功返回list对象
     *
     * @param wxConfig
     * @return
     */
    public static JsonData getAllOpenIds(WxConfig wxConfig) {
        JsonData jsonData = new JsonData();
        List<String> list = new ArrayList<String>();
        // 获取全部openIds
        try {
            jsonData = getAccessToken(wxConfig);
            if (!jsonData.isSuccess()) {
                return jsonData;
            }
            Map map = (Map) jsonData.getData();
            String accessToken = (String) map.get("access_token");
            StringBuffer sb = new StringBuffer(wxConfig.getWxServer());
            sb.append("cgi-bin/user/get?");
            sb.append("access_token=");
            sb.append(accessToken);
            Map<String, Object> result = HttpClientUtil.get(sb.toString(), null);
            if (result.containsKey("errcode")) {
                String errcode = (String) result.get("errcode");
                String errmsg = (String) result.get("errmsg");
                jsonData.setSuccess(false).setMessage(errcode + "," + errmsg);
                return jsonData;
            } else {
                // 赋值给list
                // result.get("data");
                String lastOpenId = (String) result.get("next_openid");
                if (lastOpenId != null) {
                    getOpenIds(wxConfig, accessToken, lastOpenId, list);
                }
                jsonData.setSuccess(true).setMessage("获取openIds成功！").setData(list).setTotalCount(list.size());
                return jsonData;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.setSuccess(false).setMessage(e.getMessage());
            return jsonData;
        }
    }

    private static void getOpenIds(WxConfig wxConfig, String accessToken, String nextOpenid, List list) {
        StringBuffer sb = new StringBuffer(wxConfig.getWxServer());
        sb.append("cgi-bin/user/get?");
        sb.append("access_token=");
        sb.append(accessToken);
        sb.append("&next_openid=");
        sb.append(nextOpenid);
        Map<String, Object> result = HttpClientUtil.get(sb.toString(), null);
        if (result.containsKey("errcode")) {
            return;
        } else {
            // 赋值给list
            // result.get("data");
            String lastOpenId = (String) result.get("next_openid");
            if (lastOpenId == null) {
                return;
            } else {
                getOpenIds(wxConfig, accessToken, lastOpenId, list);
            }
        }
    }

    /*// 组织图文消息数据
    public static String createSendData(String openId, Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openId);
        jsonObject.put("msgtype", "news");
        JSONObject news = new JSONObject();
        JSONArray articles = new JSONArray();
        JSONObject list = new JSONObject();
        list.put("title", String.valueOf(map.get("title"))); //标题
        list.put("description", String.valueOf(map.get("description"))); //描述
        list.put("url", String.valueOf(map.get("url"))); //点击图文链接跳转的地址
        list.put("picurl", String.valueOf(map.get("picurl"))); //图文链接的图片
        articles.put(list);
        news.put("articles", articles);
        JSONObject text = new JSONObject();
        text.put("test1", String.valueOf(map.get("test1")));
        jsonObject.put("text", text);
        jsonObject.put("news", news);
        return jsonObject.toString();
    }*/


    // 根据OpenID列表群发，组织图文消息(点击跳转到图文消息页面,可以使用素材管理中的永久素材media_id)
    public static String createMPNewsData(List<String> openIds, String mediaId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray touser = new JSONArray();
        for (String openId : openIds) {
            touser.put(openId);
        }
        jsonObject.put("touser", touser);
        JSONObject media = new JSONObject();
        media.put("media_id", mediaId);
        jsonObject.put("mpnews", media);
        jsonObject.put("msgtype", "mpnews");
        return jsonObject.toString();
    }

    // 根据OpenID列表群发，组织文本消息
    public static String createTextData(List<String> openIds, String content) {
        JSONObject jsonObject = new JSONObject();
        JSONArray touser = new JSONArray();
        for (String openId : openIds) {
            touser.put(openId);
        }
        jsonObject.put("touser", touser);
        JSONObject contentObj = new JSONObject();
        contentObj.put("content", content);
        jsonObject.put("text", contentObj);
        jsonObject.put("msgtype", "text");
        return jsonObject.toString();
    }

    /**
     * 读取配置文件内容
     *
     * @return
     */
    private WxConfig getWxConfig() {
        WxConfig config = new WxConfig();
        //String wxAppId = Global.getConfig("wxAppId");
        String wxAppId = "wxf0de0b815f6be3f7";
        config.setWxAppId(wxAppId);

        //String wxSecret = Global.getConfig("wxSecret");
        String wxSecret = "ca4cf3e64fdc0dbd86163de58110bb8d";
        config.setWxSecret(wxSecret);

        //String wxServer = Global.getConfig("wxServer");
        String wxServer = "https://api.weixin.qq.com/";
        config.setWxServer(wxServer);

        return config;
    }

    public static void main(String[] args) throws Exception {
        List<String> openIds = new ArrayList<String>();
        openIds.add("OPENID1");
        openIds.add("OPENID2");
        String mediaId = "123dsdajkasd231jhksad";
        String params = createMPNewsData(openIds, mediaId);
        System.out.println(params);
    }
}
