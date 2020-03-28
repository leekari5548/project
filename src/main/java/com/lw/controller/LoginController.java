package com.lw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Person;
import com.lw.service.LoginService;
import com.lw.util.CommonUtils;
import com.lw.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestParam(value = "request_data",required = false) String requestData){
        try {
            logger.info(requestData);
            JSONObject jo = JSON.parseObject(requestData);
            String username = jo.getString("username");
            Person p = loginService.checkUser(username,null);
            if (p!=null){
                return ResponseUtils.sendRes(-1,"您注册的用户名已存在","");
            }
            boolean b = loginService.register(jo);
            if (b){
                return ResponseUtils.sendRes(0,"注册成功","");
            }else {
                return ResponseUtils.sendRes(-1,"系统错误","");
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
//            JSONObject requestData = jo.getJSONObject("request_data");
            String username = jo.getString("username");
            String password = jo.getString("password");
            Person p = loginService.checkUser(username,password);
            if (p == null){
                logger.error("null");
                return ResponseUtils.sendRes(-1,"用户名或密码错误","");
            }
            JSONObject res = new JSONObject();
            res.put("token",username + CommonUtils.getUUID());
            JSONObject personJson = (JSONObject) JSON.toJSON(p);
            res.put("user_info",personJson);
            return ResponseUtils.sendRes(0,"登录成功",res);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "user/update")
    public String update(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            String username = jo.getString("username");
            int i = loginService.updateUser(jo);
            if (i != 1){
                return ResponseUtils.sendRes(-1,"系统错误","");
            }
            Person p = loginService.checkUser(username,null);
            JSONObject res = new JSONObject();
            res.put("token",username + CommonUtils.getUUID());
            JSONObject personJson = (JSONObject) JSON.toJSON(p);
            res.put("user_info",personJson);
            return ResponseUtils.sendRes(0,"用户信息修改成功",res);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "password/update")
    public String updatePwd(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            String username = jo.getString("username");
            String password = jo.getString("password");
            String passwordNew= jo.getString("password_new");
            Person person = loginService.checkUser(username,password);
            if (person == null){
                return ResponseUtils.sendRes(-1,"原密码不正确","");
            }
            if (password.equals(passwordNew)){
                return ResponseUtils.sendRes(-1,"新密码与原密码一致","");
            }
            int i = loginService.updatePassword(username,passwordNew);
            if (i != 1){
                return ResponseUtils.sendRes(-1,"系统错误","");
            }
            return ResponseUtils.sendRes(0,"密码修改成功，请重新登录","");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

}
