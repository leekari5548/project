package com.lw.dao;

import com.lw.entity.PersonGroup;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PersonGroupMapper {

    int insertPersonGroup(@Param("id") String id,@Param("username") String username,@Param("groupId") String groupId,@Param("status") String status);

//    @Update("")
    int updatePerson(@Param("status") String status,@Param("username") String username);

//    @Select("")
    PersonGroup getPersonGroup(String username);

//    @Delete("")
    int delPersonGroup(String id);
}
