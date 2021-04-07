package com.example.atlas.mapper;

import com.example.atlas.model.Crop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CropMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Crop record);

    int insertSelective(Crop record);

    Crop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Crop record);

    int updateByPrimaryKey(Crop record);

    Crop selectByCropName(String name);

    int selectMaxId();

    List<Crop> getAllCropData();
}
