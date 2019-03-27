package com.cd.ums.common.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hqj on 2018-10-24.
 * 日志图表的数据封装类
 */
public class ChartData implements Serializable {

    private String name; // 名称
    private Integer cnt; // 数量
    private Float money; // 短信费用

    // 查询条件
    private String yearstr;  // 当前年份
    private String monthstr; // 当前月份
    private String officeId; // 单位id
    private String officeName; //单位名称
    private String userId; // 用户ID
    private String userName; // 用户名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date toDate;


    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public String getYearstr() {
        return yearstr;
    }

    public void setYearstr(String yearstr) {
        this.yearstr = yearstr;
    }

    public String getMonthstr() {
        return monthstr;
    }

    public void setMonthstr(String monthstr) {
        this.monthstr = monthstr;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
