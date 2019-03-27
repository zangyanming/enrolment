package com.cd.ums.modules.wx.model;

/**
 * Created by hqj on 2018/10/27.
 */
public class WxConfig {
    private String wxAppId; // 微信AppId
    private String wxSecret = "xxx"; // 微信秘钥
    private String wxServer = "https://api.weixin.qq.com/"; // 微信服务器地址

    //微信支付商户开通后 微信会提供appid和appsecret和商户号partner
    private String partners = "";
    //这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
    private String partnerkey = "";
    //openId 是微信用户针对公众号的标识，授权的部分这里不解释
    //微信支付成功后通知地址 必须要求80端口并且地址不能带参数  需修改
    private String notifyUrl = "/xxx/weixin/getwxpayNotice.do";

    private String rechargeNotifyUrl = "/xxxx/weixin/getwxpayRechargeNotice.do";

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getWxSecret() {
        return wxSecret;
    }

    public void setWxSecret(String wxSecret) {
        this.wxSecret = wxSecret;
    }

    public String getWxServer() {
        return wxServer;
    }

    public void setWxServer(String wxServer) {
        this.wxServer = wxServer;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }

    public String getPartnerkey() {
        return partnerkey;
    }

    public void setPartnerkey(String partnerkey) {
        this.partnerkey = partnerkey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRechargeNotifyUrl() {
        return rechargeNotifyUrl;
    }

    public void setRechargeNotifyUrl(String rechargeNotifyUrl) {
        this.rechargeNotifyUrl = rechargeNotifyUrl;
    }
}
