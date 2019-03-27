package com.cd.ums.modules.sms;

import com.cd.ums.modules.mq.entity.ShortMessage;
import com.cd.ums.modules.mq.service.ShortMessageService;
import com.cd.ums.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zyanming2002 on 2018/10/16.
 */
public class SendShortMessage {
    @Autowired
    private ShortMessageService shortMessageService;

    public String sendMsg(String ids, String phones, String message) {
        String ret = "00";
        if (phones.isEmpty() || message.isEmpty()) {
            ret = "01消息内容或电话号码不能为空";
        }

        String[] phonesSplit = phones.split(",");
        int phonesCnt = phonesSplit.length;
        int i = 0, pos = 0;
        // 每隔50个循环一下
        for (i = 0; i < phonesCnt / 50; i++) {
            String sendPhones = "";
            for (int j = 0; j < 50; j++) {
                sendPhones += "," + phonesSplit[i * 50 + j];
                pos = i * 50 + j;
            }
            if (sendPhones.length() > 0) {
                sendPhones = sendPhones.substring(1, sendPhones.length() - 1);
            }
            //先不发送
            //ret = SmsService.SendMsg(sendPhones, message);
        }

        // 剩余的
        for(int k = 0; k < phonesSplit.length - pos; k++){
            String sendPhones = "";
            sendPhones += "," + phonesSplit[k];
            if (sendPhones.length() > 0) {
                sendPhones = sendPhones.substring(1, sendPhones.length() - 1);
            }
            //先不发送
            //ret = SmsService.SendMsg(sendPhones, message);
        }

        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setReceiverIds(ids);
        shortMessage.setContent(message);
        shortMessage.setSenderId(UserUtils.getUser().getId());
        shortMessage.setSenderOfficeId(UserUtils.getUser().getOffice().getCode());
        shortMessage.setSendDate(new Date());
        shortMessageService = new ShortMessageService();
        shortMessageService.save(shortMessage);

        return ret;
    }
}

