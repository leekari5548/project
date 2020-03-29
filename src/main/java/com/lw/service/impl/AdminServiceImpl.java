package com.lw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lw.dao.*;
import com.lw.entity.*;
import com.lw.service.AdminService;
import com.lw.util.CommonUtils;
import com.lw.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        List<Person> list = loginMapper.getPersonByType(type,groupId,null);
        PageInfo<Person> personPageInfo = new PageInfo<>(list);
        JSONObject jo = new JSONObject();
        jo.put("total",personPageInfo.getTotal());
        jo.put("list",list);
        return jo;
    }


    @Override
    public void updateNum(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String groupId = jsonObject.getString("group_id");
        String groupName = jsonObject.getString("group_name");
        String num = jsonObject.getString("num");
        String status = jsonObject.getString("status");
        Person person = loginMapper.checkUser(username,null);
        if (("").equals(person.getNum())||person.getNum()==null){
            person.setStatus("替补球员");
            person.setNum(num);
            person.setGroupName(groupName);
            person.setGroupId(groupId);
        }
        if (("").equals(num)||num==null) {
            person.setStatus(null);
            person.setGroupId(null);
            person.setGroupName(null);
            person.setNum(null);
        }
        loginMapper.updateNum(person);
    }

    @Override
    public void changeStatus(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String status = jsonObject.getString("status");
        loginMapper.updateStatus(username,status);
    }

    @Override
    public String updateMatch(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String match = jsonObject.getString("match");
        if (match!=null&& !("").equals(match)){
            List<Person> player = loginMapper.getPersonByType("1",id,"正式球员");
            if (player.size() < 11){
                return ResponseUtils.sendRes(-1,"上场的球员不足11人",null);
            }
            List<Person> trainer = loginMapper.getPersonByType("0",id,null);
            if (trainer.size() < 1){
                return ResponseUtils.sendRes(-1,"教练不足1人",null);
            }
        }
        groupMapper.updateMatch(match,id);
        return ResponseUtils.sendRes(0,"success",null);
    }

    @Override
    public boolean matchGet(JSONObject jsonObject) {
        String id = jsonObject.getString("group_id");
        int i = groupMapper.selectMatch(id);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Autowired
    private MatchMapper matchMapper;

    @Override
    public void insertMatch(JSONObject jsonObject) {
        String group1 = jsonObject.getString("group1");
        String group2 = jsonObject.getString("group2");
        Date date = jsonObject.getDate("start_time");
        Match match = new Match();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12,90);
        Date end = calendar.getTime();
        match.setId(CommonUtils.getUUID());
        match.setGroup1(group1);
        match.setGroup2(group2);
        match.setStartTime(CommonUtils.getDateTime(date));
        match.setEndTime(CommonUtils.getDateTime(end));
        match.setCreateTime(CommonUtils.getDateTime());
        matchMapper.insertMatch(match);
    }

    @Override
    public void insertSubject(JSONObject jsonObject) {
        String subject = jsonObject.getString("subject");
        Subject subject1 = new Subject();
        subject1.setCreateTime(CommonUtils.getDateTime());
        subject1.setSubject(subject);
        subject1.setId(CommonUtils.getUUID());
        subjectMapper.insertSubject(subject1);
    }

    @Override
    public void removeSubject(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        subjectMapper.remove(id);
    }

    @Override
    public JSONObject getCanSelect() {
        List<Group> list = groupMapper.listGroupMatch();
        JSONObject res = new JSONObject();
        res.put("list",list);
        return res;
    }

    @Override
    public JSONArray getMatches() {
        List<Match> list = matchMapper.getMatch();
        JSONArray ja = new JSONArray();
        for (Match m : list){
            JSONObject jo = (JSONObject) JSONObject.toJSON(m);
            String group1Name = groupMapper.getGroupById(m.getGroup1()).getName();
            String group2Name = groupMapper.getGroupById(m.getGroup2()).getName();
            jo.put("group1Name",group1Name);
            jo.put("group2Name",group2Name);
            ja.add(jo);
        }
        return ja;
    }
}
