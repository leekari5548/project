package com.lw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Message;
import com.lw.entity.Review;
import com.lw.service.MessageService;
import com.lw.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public String getMessage(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            JSONObject list = messageService.getMessage(jo);
            return ResponseUtils.sendRes(0,"success",list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.sendRes(-1,"系统错误",null);
        }
    }

    @RequestMapping(value = "/reply",method = RequestMethod.POST)
    public String reply(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = messageService.replyMessage(jo);
            if (!b){
                return ResponseUtils.sendRes(-1,"回复失败",null);
            }
            return ResponseUtils.sendRes(0,"success",null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.sendRes(-1,"系统错误",null);
        }
    }

    @RequestMapping(value = "/new")
    public String addMessage(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = messageService.addMessage(jo);
            if (!b){
                return ResponseUtils.sendRes(-1,"新建评论失败",null);
            }
            return ResponseUtils.sendRes(0,"success",null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.sendRes(-1,"系统错误",null);
        }
    }

    @RequestMapping("/delete")
    public String deleteMessage(String request_data){
        try{
            logger.info("delete => "+request_data);
            JSONObject jo = JSON.parseObject(request_data);
            boolean b = messageService.deleteMessage(jo);
            if (!b){
                return ResponseUtils.sendRes(-1,"删除失败",null);
            }
            return ResponseUtils.sendRes(0,"success",null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.sendRes(-1,"系统错误",null);
        }
    }

    @RequestMapping(value = "/reply/get")
    public String getReply(String request_data){
        try{
            logger.info(request_data);
            JSONObject jo = JSON.parseObject(request_data);
            List<Review> list = messageService.getReviewByMessageId(jo);
            return ResponseUtils.sendRes(0,"success",list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtils.sendRes(-1,"系统错误",null);
        }
    }
}
