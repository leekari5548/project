package com.lw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lw.dao.*;
import com.lw.entity.*;
import com.lw.service.AdminService;
import com.lw.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public List<Subject> getSubjects() {
        return subjectMapper.get(null);
    }

    @Override
    public boolean addNews(JSONObject jsonObject) {
        String img = jsonObject.getString("image");
        String creator = jsonObject.getString("creator");
        String content = jsonObject.getString("content");
        String title = jsonObject.getString("title");
        String groupId = jsonObject.getString("group_id");

        News news = new News();
        news.setId(CommonUtils.getUUID());
        news.setContent(content);
        news.setCreateTime(CommonUtils.getDateTime());
        news.setCreator(creator);
        news.setImg(img);
        news.setStatus("0");
        news.setTitle(title);
        news.setGroupId(groupId);

        int i = newsMapper.addNews(news);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteNews(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        int i = newsMapper.updateNewsStatus("-1",id);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject groupList(JSONObject jsonObject) {
        String status = jsonObject.getString("status");
        Integer pageNum = jsonObject.getInteger("page_num");
        Integer pageSize = jsonObject.getInteger("page_size");
        PageHelper.startPage(pageNum,pageSize);
        List<Group> list = groupMapper.getGroup(status,null);
        PageInfo<Group> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        JSONObject jo = new JSONObject();
        jo.put("total",total);
        jo.put("list",list);
        return jo;
    }

    @Override
    public void passGroup(JSONObject jsonObject) {
        String groupId = jsonObject.getString("group_id");
        String userId = jsonObject.getString("user_id");
        String groupName = jsonObject.getString("group_name");
        String status = "1";
        groupMapper.updateGroup(status,groupId);
        loginMapper.updateToAdmin(groupId,groupName,"3",userId);
    }

    @Override
    public void failedGroup(JSONObject jsonObject) {
        String groupId = jsonObject.getString("group_id");
        groupMapper.updateGroup("-1",groupId);
    }

    @Override
    public void addSubject(JSONObject jsonObject) {
        String name = jsonObject.getString("name");

        Subject subject = new Subject();
        subject.setSubject(name);
        subject.setId(CommonUtils.getUUID());
        subject.setCreateTime(CommonUtils.getDateTime());

        subjectMapper.insertSubject(subject);
    }

    @Override
    public JSONObject getNowGroup(JSONObject jsonObject) {
        Integer pageNum = jsonObject.getInteger("page_num");
        Integer pageSize = jsonObject.getInteger("page_size");
        PageHelper.startPage(pageNum,pageSize);
        List<Group> list = groupMapper.getNowGroup();
        PageInfo<Group> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        JSONObject jo = new JSONObject();
        jo.put("total",total);
        jo.put("list",list);
        return jo;
    }

    @Override
    public void updateGroup(JSONObject jsonObject) {
        String status = jsonObject.getString("status");
        String id = jsonObject.getString("id");
        String username = jsonObject.getString("username");
        groupMapper.updateGroup(status,id);
        if (status.equals("3")){
            loginMapper.updateToAdmin("","","2",username);
        }
    }

    @Override
    public JSONObject getPerson(JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        Integer pageNum = jsonObject.getInteger("page_num");
        Integer pageSize = jsonObject.getInteger("page_size");
        String groupId = jsonObject.getString("group_id");
        if (groupId.equals("")){
            groupId = null;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Person> list = loginMapper.getPersonByType(type,groupId);
        PageInfo<Person> personPageInfo = new PageInfo<>(list);
        JSONObject jo = new JSONObject();
        List<Person> newList = new ArrayList<>();
        for (Person person: list){
            PersonGroup personGroup = personGroupMapper.getPersonGroup(person.getUsername());
            if (personGroup.getStatus().equals("0")){
                person.setStatus("替补球员");
            }else {
                person.setStatus("正式球员");
            }
            newList.add(person);
        }
        jo.put("total",personPageInfo.getTotal());
        jo.put("list",newList);
        return jo;
    }

    @Autowired
    private PersonGroupMapper personGroupMapper;
    @Override
    public void updateNum(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String groupId = jsonObject.getString("group_id");
        String groupName = jsonObject.getString("group_name");
        String num = jsonObject.getString("num");
        PersonGroup personGroup = personGroupMapper.getPersonGroup(username);
        if (personGroup!=null){
            if (num.equals("") || num==null){
                personGroupMapper.delPersonGroup(personGroup.getId());
            }
        }
        if (personGroup==null){
            personGroupMapper.insertPersonGroup(CommonUtils.getUUID(),username,groupId,"0");
        }
        loginMapper.updateNum(username,num,groupId,groupName);
    }
}
