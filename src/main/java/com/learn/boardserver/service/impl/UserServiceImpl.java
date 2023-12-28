package com.learn.boardserver.service.impl;

import com.learn.boardserver.dto.UserDTO;
import com.learn.boardserver.mapper.UserProfileMapper;
import com.learn.boardserver.service.UserService;
import com.learn.boardserver.exception.DuplicateIdException;
import com.learn.boardserver.utils.SHA256Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private UserProfileMapper userProfileMapper;

    @Autowired
    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(UserDTO userDTO) {
        boolean dupleIdResult = isDuplicatedId(userDTO.getUserId());
        if (dupleIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }

        userDTO.setCreateTime(new Date());
        userDTO.setPassword(SHA256Util.encryptionSHA256(userDTO.getPassword()));

        int insertCount = userProfileMapper.register(userDTO);

        if(insertCount != 1) {
            log.error("insertMember ERROR! {}", userDTO);
            throw new RuntimeException("insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptPassword = SHA256Util.encryptionSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String userId) {
        return userProfileMapper.idCheck(userId) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptPassword = SHA256Util.encryptionSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);

        if(memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptionSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteId(String userId, String password) {
        String cryptPassword = SHA256Util.encryptionSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(userId, cryptPassword);

        if(memberInfo != null) {
            userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
