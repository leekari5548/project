package com.lw.dao;

import com.lw.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {

    int addNews(News news);

    List<News> getNews(@Param("search") String search, @Param("groupId") String groupId);

    int updateNewsStatus(@Param("status") String status, @Param("id") String id);
}
