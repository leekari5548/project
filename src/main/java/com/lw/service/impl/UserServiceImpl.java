package com.lw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lw.dao.GroupMapper;
import com.lw.dao.NewsMapper;
import com.lw.dao.ReviewMapper;
import com.lw.entity.Group;
import com.lw.entity.News;
import com.lw.entity.Review;
import com.lw.service.UserService;
import com.lw.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public JSONObject getNews(JSONObject jo) {
        String search = jo.getString("search");
        Integer pageSize = jo.getInteger("page_size");
        Integer pageNum = jo.getInteger("page_num");
        String groupId = jo.getString("group_id");
        PageHelper.startPage(pageNum,pageSize);
        List<News> list = newsMapper.getNews(search,groupId);
        PageInfo<News> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",total);
        jsonObject.put("list",list);
        return jsonObject;
    }

    @Override
    public boolean deleteReview(JSONObject jo) {
        String id = jo.getString("id");
        int d = reviewMapper.deleteReview(id);
        if (d == 1) {
            return true;
        }
        return false;
    }

    @Override
    public JSONObject getReviewByUsername(JSONObject jo) {
        String username = jo.getString("username");
        Integer pageNum = jo.getInteger("page_num");
        Integer pageSize = jo.getInteger("page_size");
        PageHelper.startPage(pageNum,pageSize);
        List<Review> list = reviewMapper.getReviewByUsername(username,null);
        PageInfo<Review> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        JSONObject res = new JSONObject();
        res.put("total",total);
        res.put("list",list);
        return res;
    }

    @Override
    public JSONObject getReviewByToUsername(JSONObject jo) {
        String username = jo.getString("to_username");
        Integer pageNum = jo.getInteger("page_num");
        Integer pageSize = jo.getInteger("page_size");
        PageHelper.startPage(pageNum,pageSize);
        List<Review> list = reviewMapper.getReviewByUsername(null,username);
        PageInfo<Review> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        JSONObject res = new JSONObject();
        res.put("total",total);
        res.put("list",list);
        return res;
    }

    @Override
    public boolean addGroup(JSONObject jsonObject) {
        String contact = jsonObject.getString("contact");
        String name = jsonObject.getString("name");
        String logo = jsonObject.getString("logo");
        String description = jsonObject.getString("description");
        String creator = jsonObject.getString("creator");
        Group group = new Group();
        group.setId(CommonUtils.getUUID());
        group.setName(name);
        group.setCreateTime(CommonUtils.getDateTime());
        group.setCreator(creator);
        group.setDescription(description);
        group.setContact(contact);
        group.setLogo(logo);
        group.setStatus("0");
        int i = groupMapper.insertGroup(group);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public List<Group> getGroup(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        return groupMapper.getGroup(null,username);
    }
}
