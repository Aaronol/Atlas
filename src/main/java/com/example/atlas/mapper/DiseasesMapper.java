package com.example.atlas.mapper;

import com.example.atlas.model.Diseases;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiseasesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Diseases record);

    int insertSelective(Diseases record);

    Diseases selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Diseases record);

    int updateByPrimaryKey(Diseases record);
}
