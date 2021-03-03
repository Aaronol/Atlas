package com.example.atlas.mapper;

import com.example.atlas.model.CropToPest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CropToPestMapper {
    int insert(CropToPest record);

    int insertSelective(CropToPest record);
}
