package com.lw.dao;

import com.lw.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> getMessage(@Param("search") String search, @Param("subject") String subject,
                             @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    int insert(Message message);

    int updateMessageStatus(@Param("id") String id, @Param("status") String status);
}
