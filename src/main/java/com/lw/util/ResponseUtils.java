package com.lw.util;

import com.alibaba.fastjson.JSONObject;

public class ResponseUtils {
    public static String sendRes(Integer code,String message,Object data){
        JSONObject object = new JSONObject();
        object.put("code",code);
        object.put("message",message);
        object.put("data",data);
        return object.toJSONString();
    }
}
