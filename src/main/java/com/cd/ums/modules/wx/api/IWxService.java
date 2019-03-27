package com.cd.ums.modules.wx.api;

import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.wx.model.WxMsg;

/**
 * @author hqj
 */
public interface IWxService {

    JsonData sendWxMessage(WxMsg wxMsg);

    JsonData sendWxMessage(String content);
}