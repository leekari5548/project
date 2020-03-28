package com.lw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lw.dao.LoginMapper;
import com.lw.entity.Person;
import com.lw.service.LoginService;
import com.lw.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public boolean register(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        String age = jsonObject.getString("age");
        String gender = jsonObject.getString("gender");
        String idCard = jsonObject.getString("id_card");
        String weight = jsonObject.getString("weight");
        String height = jsonObject.getString("height");
        String description = jsonObject.getString("description");
        String nickname = jsonObject.getString("nickname");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String pic = jsonObject.getString("pic");
        String id = CommonUtils.getUUID();
        String createTime = CommonUtils.getDateTime();
        String verify = null;
        if (type.equals("0") || type.equals("1")){
            verify = "0";
        }
        Person person = new Person(id,type,name,
                gender, username,idCard,
                age,password,nickname, pic,
                height,weight,description, verify,
               createTime,null,null);

        int isRegister = loginMapper.register(person);
        if (isRegister!=0){
            return true;
        }
        return false;
    }

    @Override
    public Person checkUser(String username, String password) {
        Person p = loginMapper.checkUser(username, password);
       return p;
    }

    @Override
    public int updateUser(JSONObject jo) {
        String username = jo.getString("username");
        String name = jo.getString("name");
        String idCard = jo.getString("idCard");
        String age = jo.getString("age");
        String description = jo.getString("description");
        String nickname = jo.getString("nickname");
        int i = loginMapper.updateUser(nickname,username,name,idCard,age,description);
        return i;
    }

    @Override
    public int updatePassword(String username, String password) {
        int i = loginMapper.updatePassword(username,password);
        return i;
    }
}
