package com.cd.ums.modules.sms;

import com.SmsService;

public class MessageTest {

    public static void main(String[] args) throws Exception {
        String ret = SmsService.SendMsg("13756677098,13324317165", "恭喜您中大奖了！详情请咨询13324317165");
        System.out.println("first:" + ret);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ret = SmsService.SendMsg("13756677098,1", "恭喜您中大奖了！详情请咨询13324317165");
        System.out.println("second:" + ret);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ret = SmsService.SendMsg("x,13756677098", "恭喜您中大奖了！详情请咨询13324317165");
        System.out.println("third:" + ret);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ret = SmsService.SendMsg("x,a", "恭喜您中大奖了！详情请咨询13324317165");
        System.out.println("forth:" + ret);
    }
}
