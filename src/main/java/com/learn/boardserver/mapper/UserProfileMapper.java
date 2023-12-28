package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(String userId);

    int insertUserProfile(@Param("id") String id, @Param("password") String password,
                          @Param("name") String name, @Param("phone") String phone,
                          @Param("address") String address, @Param("createTime") String createTime,
                          @Param("updateTime") String updateTime);

    int deleteUserProfile(String userId);

    UserDTO findByIdAndPassword(String userId, String password);

    int idCheck(String userId);

    int updatePassword(UserDTO user);

    int register(UserDTO UserDTO);

}
