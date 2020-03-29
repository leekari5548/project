package com.lw.dao;

import com.lw.entity.Match;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMapper {

    int insertMatch(Match match);

    List<Match> getMatch();
}
