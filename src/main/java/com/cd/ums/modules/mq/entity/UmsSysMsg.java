package com.cd.ums.modules.mq.entity;

/**
 * Created by hqj on 2018/10/13.
 */
public class UmsSysMsg {
    private String name;		   // 请求系统名称
    private String sysCode;		   // 请求系统编码
    private String sysKey;		   // 请求系统密钥
    private String destSysCode;    // 目标系统编码
    private Object data;           // 发送数据
    private Boolean audit;         // 是否审核，f不审核，t审核

    // 系统内部群发消息使用
    private String receiverIds;    // 通过申请的目标系统ids
    private String content;        // 群发系统消息内容

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getDestSysCode() {
        return destSysCode;
    }

    public void setDestSysCode(String destSysCode) {
        this.destSysCode = destSysCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(String receiverIds) {
        this.receiverIds = receiverIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
    }
}
