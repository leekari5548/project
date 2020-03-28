package com.lw.dao;

import com.lw.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Mapper
public interface LoginMapper {

    int register(Person person);

    Person checkUser(@Param("username") String username, @Param("password") String password);

    int updateUser(@Param("nickname") String nickname,
                   @Param("username") String username,
                   @Param("name") String name,
                   @Param("idCard") String idCard,
                   @Param("age") String age,
                   @Param("description") String description);

    int updatePassword(@Param("username") String username, @Param("password") String password);

    int updateToAdmin(@Param("groupId") String groupId, @Param("groupName") String groupName,
                      @Param("type") String type, @Param("username") String username);

    List<Person> getPersonByType(@Param("type") String type, @Param("groupId") String groupId);

    int updateNum(@Param("username") String username, @Param("num") String num,@Param("groupId") String groupId,@Param("groupName") String groupName);

}
