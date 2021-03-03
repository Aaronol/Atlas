package com.example.atlas.mapper;

import com.example.atlas.model.CropToDiseases;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CropToDiseasesMapper {
    int insert(CropToDiseases record);

    int insertSelective(CropToDiseases record);
}
