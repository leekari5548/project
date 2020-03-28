package com.lw.service;

import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Group;
import com.lw.entity.News;
import com.lw.entity.Review;

import java.util.List;

public interface UserService {

    JSONObject getNews(JSONObject jo);

    boolean deleteReview(JSONObject jo);

    JSONObject getReviewByUsername(JSONObject jo);

    JSONObject getReviewByToUsername(JSONObject jo);

    boolean addGroup(JSONObject jsonObject);

    List<Group> getGroup(JSONObject jsonObject);
}
