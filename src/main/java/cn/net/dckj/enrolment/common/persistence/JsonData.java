package cn.net.dckj.enrolment.common.persistence;

import cn.net.dckj.enrolment.common.mapper.JsonMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by hqj on 2017/5/28.
 */
public class JsonData implements Serializable {
    private boolean success;
    private String message;
    private Object data;
    private int totalCount = 0;

    public boolean isSuccess() {
        return success;
    }

    public JsonData setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonData setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonData setData(Object data) {
        this.data = data;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public JsonData setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public void toJson(HttpServletResponse response) {
        try {
            response.setContentType("application/json;;charset=UTF-8");
            String json = JsonMapper.toJsonString(this);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
