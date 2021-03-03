package com.example.atlas.mapper;

import com.example.atlas.model.Pest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pest record);

    int insertSelective(Pest record);

    Pest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pest record);

    int updateByPrimaryKey(Pest record);
}
