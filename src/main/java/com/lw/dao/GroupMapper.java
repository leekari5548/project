package com.lw.dao;

import com.lw.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupMapper {

    int insertGroup(Group group);

    List<Group> getGroup(@Param("status") String status, @Param("creator") String creator);

    int updateGroup(@Param("status") String status, @Param("groupId") String groupId);

    List<Group> getNowGroup();
}
