package com.lw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lw.dao.LoginMapper;
import com.lw.dao.MessageMapper;
import com.lw.dao.ReviewMapper;
import com.lw.dao.SubjectMapper;
import com.lw.entity.Message;
import com.lw.entity.Person;
import com.lw.entity.Review;
import com.lw.entity.Subject;
import com.lw.service.MessageService;
import com.lw.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public JSONObject getMessage(JSONObject jo) {
        Integer pageNum = jo.getInteger("page_num");
        Integer pageSize = jo.getInteger("page_size");
        String beginDate = jo.getString("begin_time");
        String endDate = jo.getString("end_time");
        String subject = jo.getString("subject");
        String search = jo.getString("search");
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum,pageSize);

        List<Message> list = messageMapper.getMessage(search,subject,beginDate,endDate);
        PageInfo<Message> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
//        System.err.println(total);
        JSONObject res = new JSONObject();
        res.put("list",list);
        res.put("total",total);
//        System.err.println(page);
        return res;
    }

    @Override
    public boolean replyMessage(JSONObject jo) {
        String username = jo.getString("username");
        String toUsername = jo.getString("to_username");
        String messageId = jo.getString("message_id");
        String content = jo.getString("content");

        Review review = new Review();
        review.setCreateTime(CommonUtils.getDateTime());
        review.setUsername(username);
        review.setToUsername(toUsername);
        review.setMessageId(messageId);
        review.setContent(content);

        review.setRead(false);
        Person toPerson = loginMapper.checkUser(toUsername,null);
        Person person = loginMapper.checkUser(username,null);

        review.setToNickname(toPerson.getNickname());
        review.setNickname(person.getNickname());
        review.setId(CommonUtils.getUUID());
        int i = reviewMapper.insertReview(review);
        if (i==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean addMessage(JSONObject jo) {
        String title = jo.getString("title");
        String content = jo.getString("content");
        String username = jo.getString("username");
        String subject = jo.getString("subject");
        Person p = loginMapper.checkUser(username,null);
        Message message = new Message();
        message.setContent(content);
        message.setId(CommonUtils.getUUID());
        message.setTitle(title);
        message.setUsername(username);
        message.setNickname(p.getNickname());
        message.setStatus("0");
        message.setSubject(subject);
        List<Subject> subjects = subjectMapper.get(subject);
        message.setSubjectName(subjects.get(0).getSubject());
        message.setCreateTime(CommonUtils.getDateTime());
        int b = messageMapper.insert(message);
        return b == 1;
    }

    @Override
    public List<Review> getReviewByMessageId(JSONObject jo) {
        String messageId = jo.getString("message_id");
        List<Review> list = reviewMapper.getReviewByMessageId(messageId);
        return list;
    }

    @Override
    public boolean deleteMessage(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        int u = messageMapper.updateMessageStatus(id,"-1");
        int i = reviewMapper.deleteReviewByMessageId(id);

        if (u == 1){
            return true;
        }
        return false;
    }
}
