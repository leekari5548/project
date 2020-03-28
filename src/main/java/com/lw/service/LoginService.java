package com.lw.service;

import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Person;

public interface LoginService {

    boolean register(JSONObject jsonObject);

    Person checkUser(String username, String password);

    int updateUser(JSONObject jo);

    int updatePassword(String username, String password);
}
