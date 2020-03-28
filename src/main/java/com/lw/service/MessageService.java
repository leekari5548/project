package com.lw.service;

import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Message;
import com.lw.entity.Review;

import java.util.List;

public interface MessageService {

    JSONObject getMessage(JSONObject jo);

    boolean replyMessage(JSONObject jo);

    boolean addMessage(JSONObject jo);

    List<Review> getReviewByMessageId(JSONObject jo);

    boolean deleteMessage(JSONObject jsonObject);
}
