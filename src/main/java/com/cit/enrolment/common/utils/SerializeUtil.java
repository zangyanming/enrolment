package com.cit.enrolment.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;


public class SerializeUtil {
    public static Map<String, Object> json2Map(String json) {
        Gson gson = new Gson();

        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());

        if (map == null) {
            map = new HashMap<String,Object>();
        }

        return map;
    }

    public static String serializeJson(Object object) {

        Gson mapper = new Gson();
        try {
            return mapper.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
