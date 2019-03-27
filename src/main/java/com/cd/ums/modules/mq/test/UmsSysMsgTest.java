package com.cd.ums.modules.mq.test;

import com.cd.ums.common.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hqj on 2018/12/28.
 */
public class UmsSysMsgTest {

    public static void main(String[] args) throws Exception {
        try {
            String sessionId = "128befddd87c4236af061050e3e475aa";

            String getSendUrl = "http://localhost:8181/ums/a/rest/newsend?name=1&sysCode=1&sysKey=462f6f0007fb4a42a9fe26d8e9abdba8&destSysCode=2&data=123";
            Map rtn = HttpClientUtil.get(getSendUrl, sessionId);
            System.out.println("----------get send start----------");
            System.out.println(rtn.get("success"));
            System.out.println(rtn.get("message"));
            System.out.println(rtn.get("totalCount"));
            System.out.println("----------get send end----------");

            String getReceiveUrl = "http://localhost:8181/ums/a/rest/newreceive?name=2&sysCode=2&sysKey=8b4290830ef94a7594542aa8fbf403a5";
            rtn = HttpClientUtil.get(getReceiveUrl, sessionId);
            System.out.println("----------get receive start----------");
            System.out.println(rtn.get("success"));
            System.out.println(rtn.get("message"));
            System.out.println(rtn.get("totalCount"));
            if (Double.parseDouble(String.valueOf(rtn.get("totalCount")))>0) {
                System.out.println(((List)rtn.get("data")).get(0));
            }
            System.out.println("----------get receive end----------");

            String postSendUrl = "http://localhost:8181/ums/a/mq/umssysmsg/send";
            // "http://20.1.2.30:7046/ums/a/mq/umssysmsg/send";
            Map params = new HashMap<>();
            // 消息中心
            // xxzx
            // 0d3197a203644f128aa0aea20db3ac01
            // jcpt
            // 来自消息中心的消息信息
            params.put("name", "1");
            params.put("sysCode", "1");
            params.put("sysKey", "462f6f0007fb4a42a9fe26d8e9abdba8");
            params.put("destSysCode", "2");
            params.put("data", "来自1的消息信息");
            rtn = HttpClientUtil.post(postSendUrl, params, sessionId);
            System.out.println("----------post send start----------");
            System.out.println(rtn.get("success"));
            System.out.println(rtn.get("message"));
            System.out.println(rtn.get("totalCount"));
            System.out.println("----------post send end----------");

            String postReceiveUrl = "http://localhost:8181/ums/a/mq/umssysmsg/receive";
            params = new HashMap<>();
            params.put("name", "2");
            params.put("sysCode", "2");
            params.put("sysKey", "8b4290830ef94a7594542aa8fbf403a5");
            rtn = HttpClientUtil.post(postReceiveUrl, params, sessionId);
            System.out.println("----------post receive start----------");
            System.out.println(rtn.get("success"));
            System.out.println(rtn.get("message"));
            System.out.println(rtn.get("totalCount"));
            if (Double.parseDouble(String.valueOf(rtn.get("totalCount")))>0) {
                System.out.println(((List)rtn.get("data")).get(0));
            }
            System.out.println("----------post receive end----------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }
}
