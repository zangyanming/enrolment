package com.cd.ums.modules.wx.model;

import java.util.Map;

/**
 * Created by hqj on 2018-10-27.
 * 微信的消息封装类
 */
public class WxContent {
    private String title;       // 标题
    private String description; // 描述
    private String url;         // 点击图文链接跳转的地址
    private String picUrl;      // 图文链接的图片

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
