package com.lw.dao;

import com.lw.entity.Subject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {

    List<Subject> get(String id);

    @Insert("<script>insert into `subject`(`id`,`subject`,`createTime`) " +
            "values(#{id},#{subject},#{createTime})</script>")
    void insertSubject(Subject subject);

    @Delete("delete from subject where id = #{id}")
    void remove(String id);
}
