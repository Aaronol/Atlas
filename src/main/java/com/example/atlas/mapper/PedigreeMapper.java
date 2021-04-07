package com.example.atlas.mapper;

import com.example.atlas.model.Pedigree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PedigreeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pedigree record);

    int insertSelective(Pedigree record);

    Pedigree selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pedigree record);

    int updateByPrimaryKey(Pedigree record);

    Pedigree selectByName(String name);

    int selectMaxId();

    List<Pedigree> getAllPedigreeData();
}
