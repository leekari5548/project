package com.lw.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lw.entity.Group;
import com.lw.entity.Match;
import com.lw.entity.Subject;

import java.util.List;

public interface AdminService {

    List<Subject> getSubjects();

    boolean addNews(JSONObject jsonObject);

    boolean deleteNews(JSONObject jsonObject);

    JSONObject groupList(JSONObject jsonObject);

    void passGroup(JSONObject jsonObject);

    void failedGroup(JSONObject jsonObject);

    void addSubject(JSONObject jsonObject);

    JSONObject getNowGroup(JSONObject jsonObject);

    void updateGroup(JSONObject jsonObject);

    JSONObject getPerson(JSONObject jsonObject);

    void updateNum(JSONObject jsonObject);

    void changeStatus(JSONObject jsonObject);

//    void getGroupPerson(JSO);

    String updateMatch(JSONObject jsonObject);

    boolean matchGet(JSONObject jsonObject);

    void insertMatch(JSONObject jsonObject);

    void insertSubject(JSONObject jsonObject);

    void removeSubject(JSONObject jsonObject);

    JSONObject getCanSelect();

    JSONArray getMatches();
}
