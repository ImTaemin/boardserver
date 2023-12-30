package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(String userId);

    int register(UserDTO UserDTO);

    int updatePassword(UserDTO user);

    int deleteUserProfile(String userId);

    UserDTO findByIdAndPassword(String userId, String password);

    int idCheck(String userId);

}
