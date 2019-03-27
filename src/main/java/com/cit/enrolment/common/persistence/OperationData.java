package com.cit.enrolment.common.persistence;

import java.io.Serializable;

/**
 * Created by hqj on 2017/5/28.
 */
public class OperationData implements Serializable {
    private String name;
    private String title;
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
