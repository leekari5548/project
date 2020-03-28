package com.lw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Group;
import com.lw.entity.News;
import com.lw.service.AdminService;
import com.lw.service.UserService;
import com.lw.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @RequestMapping("/news/get")
    public String getAllNews(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject resJson = userService.getNews(jo);
//            System.err.println(list);
            return ResponseUtils.sendRes(0,"成功",resJson);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }


    @RequestMapping("/review/delete")
    public String deleteReview(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = userService.deleteReview(jo);
//            System.err.println(list);
            if (!b){
                return ResponseUtils.sendRes(-1,"删除失败","");
            }
            return ResponseUtils.sendRes(0,"成功",null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }


    @RequestMapping(value = "review/get")
    public String getReview(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject resJson = userService.getReviewByUsername(jo);
//            System.err.println(list);
            return ResponseUtils.sendRes(0,"成功",resJson);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "review/get_mine")
    public String getReviewAboutMe(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject resJson = userService.getReviewByToUsername(jo);
//            System.err.println(list);
            return ResponseUtils.sendRes(0,"成功",resJson);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }


    @RequestMapping(value = "group/add")
    public String addGroup(String request_data){
        try {
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = userService.addGroup(jo);
            if (!b){
                return ResponseUtils.sendRes(-1,"系统错误","");
            }
            return ResponseUtils.sendRes(0,"success","");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "/user/group/get")
    public String getGroup(String request_data){
        try {
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            List<Group> groups = userService.getGroup(jo);
            return ResponseUtils.sendRes(0,"success",groups);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }
}
