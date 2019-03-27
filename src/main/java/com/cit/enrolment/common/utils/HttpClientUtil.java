package com.cit.enrolment.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by martin on 2017/7/2.
 */
public class HttpClientUtil {
    private static List<NameValuePair> generateParams(Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString()));
            }
        }

        return nvps;
    }

    private static Map formatResult(String result) {
        Map rtn;

        if (result.indexOf("吉林省司法厅后台管理系统 登录") > 0) {
            rtn = new HashMap<String, Object>();

            rtn.put("success", false);
            rtn.put("msg", "login");
        } else {
            rtn = SerializeUtil.json2Map(result);
        }

        return rtn;
    }

    public static Map<String, String> post(String url, Map<String, Object> params) {
        Map<String, String> rtn = new HashMap<>();
        Map<String, Object> map = post(url, params, null);
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            rtn.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return rtn;
    }

    public static Map<String, Object> post1(String url, Map<String, Object> params) {
        return post(url, params, null);
    }

    public static Map<String, String> postData(String url, Map<String, Object> params, String sessionId) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String result = null;

        try {
            if (sessionId != null && !sessionId.isEmpty()) {

                httpPost.addHeader("Cookie", "jeesite.session.id=" + sessionId);
                //httpPost.addHeader("Content-Type","application/json; charset=UTF-8");
            }
            StringEntity entity = new StringEntity(SerializeUtil.serializeJson(params), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            // do something useful with the response body
            result = EntityUtils.toString(httpEntity, "UTF-8");

            EntityUtils.consume(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return formatResult(result);
    }

    public static Map<String, Object> post(String url, Map<String, Object> params, String sessionId) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String result = null;

        try {
            if (sessionId != null && !sessionId.isEmpty()) {
                httpPost.addHeader("Cookie", "ums.session.id=" + sessionId);
            }
            StringEntity entity = new StringEntity(SerializeUtil.serializeJson(params), "utf-8");//解决中文乱码问题
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            // do something useful with the response body
            result = EntityUtils.toString(httpEntity, "UTF-8");

            EntityUtils.consume(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return formatResult(result);
    }

    public static Map<String, Object> et(String url) {
        return get(url, null);
    }

    public static Map<String, Object> get(String url, String sessionId) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = null;

        try {
            if (sessionId != null && !sessionId.isEmpty()) {
                httpGet.addHeader("Cookie", "ums.session.id=" + sessionId);
            }

            response = httpclient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            // do something useful with the response body
            result = EntityUtils.toString(httpEntity, "UTF-8");
            // and ensure it is fully consumed
            EntityUtils.consume(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return formatResult(result);
    }
}
