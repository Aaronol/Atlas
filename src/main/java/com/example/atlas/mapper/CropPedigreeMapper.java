package com.example.atlas.mapper;

import com.example.atlas.model.CropPedigree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CropPedigreeMapper {
    int deleteByPrimaryKey(Integer cropId);

    int insert(CropPedigree record);

    int insertSelective(CropPedigree record);

    List<CropPedigree> selectByPrimaryKey(Integer cropId);

    int updateByPrimaryKeySelective(CropPedigree record);

    int updateByPrimaryKey(CropPedigree record);
}
