package com.example.atlas.mapper;

import com.example.atlas.model.UserTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTable record);

    int insertSelective(UserTable record);

    UserTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTable record);

    int updateByPrimaryKey(UserTable record);

    List<UserTable> selectByUserName(@Param("userName") String userName, @Param("roleId")String roleId);

    List<UserTable> selectByRoleId(@Param("roleId") String roleId, @Param("username") String username);
}
