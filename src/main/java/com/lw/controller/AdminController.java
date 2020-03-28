package com.lw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Person;
import com.lw.entity.Subject;
import com.lw.service.AdminService;
import com.lw.util.CommonUtils;
import com.lw.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "group/get")
    public String getGroup(String request_data){
        try{
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject list = adminService.groupList(jo);
            return ResponseUtils.sendRes(0,"成功",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "group/get_now")
    public String getGroupNow(String request_data){
        try{
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject list = adminService.getNowGroup(jo);
            return ResponseUtils.sendRes(0,"成功",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "group/pass")
    public String passGroup(String request_data){
        try {
            JSONObject jo = JSON.parseObject(request_data);
//            String groupId = jo.getString("group_id");
//            String userId = jo.getString("user_id");
            adminService.passGroup(jo);
            return ResponseUtils.sendRes(0,"成功",null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "group/failed")
    public String failedGroup(String request_data){
        try {
            JSONObject jo = JSON.parseObject(request_data);
//            String groupId = jo.getString("group_id");
//            String userId = jo.getString("user_id");
            adminService.failedGroup(jo);
            return ResponseUtils.sendRes(0,"成功",null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "group/update")
    public String updateGroup(String request_data){
        try {
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            adminService.updateGroup(jo);
            return ResponseUtils.sendRes(0,"成功",null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }


    @RequestMapping(value = "subject/get")
    public String getSubjects(){
        try{
            List<Subject> list = adminService.getSubjects();
            return ResponseUtils.sendRes(0,"成功",list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value="news/add")
    public String addNews(String request_data){
        try{
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = adminService.addNews(jo);
            if (!b){
                return ResponseUtils.sendRes(-1,"新建失败","");
            }
            return ResponseUtils.sendRes(0,"成功","");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "news/delete")
    public String deleteNews(String request_data){
        try{
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = adminService.deleteNews(jo);
            if (!b){
                return ResponseUtils.sendRes(-1,"删除失败","");
            }
            return ResponseUtils.sendRes(0,"成功","");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping(value = "subject/add")
    public String addSubject(String request_data){
        try{
            JSONObject jo = JSON.parseObject(request_data);
            adminService.addSubject(jo);
            return ResponseUtils.sendRes(0,"成功","");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping("person/get")
    public String getPerson(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject res = adminService.getPerson(jo);
            return ResponseUtils.sendRes(0,"成功",res);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }

    @RequestMapping("person/pass")
    public String passPerson(String request_data){
        try{
            JSONObject jo = JSON.parseObject(request_data);
            adminService.updateNum(jo);
            return ResponseUtils.sendRes(0,"成功",null);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.sendRes(-1,"系统错误","");
        }
    }
}
