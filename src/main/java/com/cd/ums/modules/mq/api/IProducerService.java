package com.cd.ums.modules.mq.api;

import com.cd.ums.common.persistence.JsonData;

/**
 * Created by hqj on 2018/10/12.
 */
public interface IProducerService {

    public JsonData sendMessage(String queueName, String msg);
}
