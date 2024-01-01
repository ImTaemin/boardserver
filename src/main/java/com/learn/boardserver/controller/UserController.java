package com.learn.boardserver.controller;

import com.learn.boardserver.aop.LoginCheck;
import com.learn.boardserver.dto.UserDTO;
import com.learn.boardserver.dto.request.UserDeleteId;
import com.learn.boardserver.dto.request.UserLoginRequest;
import com.learn.boardserver.dto.request.UserUpdatePasswordRequest;
import com.learn.boardserver.dto.response.LoginResponse;
import com.learn.boardserver.dto.response.UserInfoResponse;
import com.learn.boardserver.service.impl.UserServiceImpl;
import com.learn.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserServiceImpl userService;
    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
    private static LoginResponse loginResponse;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if(UserDTO.hasNullDataBeforeRegister(userDTO)){
            throw new NullPointerException("회원가입 정보를 확인해주세요");
        }
        userService.register(userDTO);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        LoginResponse loginResponse;
        UserDTO userInfo = userService.login(id, password);

        if(userInfo == null) {
            return HttpStatus.NOT_FOUND;
        }

        loginResponse = LoginResponse.success(userInfo);
        if(userInfo.getStatus() == UserDTO.Status.ADMIN)
            SessionUtil.setLoginAdminId(session, id);
        else
            SessionUtil.setLoginMemberId(session, id);

        responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);

        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String userId = SessionUtil.getLoginMemberId(session);
        if(userId == null) {
            userId = SessionUtil.getLoginAdminId(session);
        }

        UserDTO memberInfo = userService.getUserInfo(userId);

        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                                            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;

        String id = accountId;
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(id, beforePassword, afterPassword);
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = FAIL_RESPONSE;
        }

        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
                                                  HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("deleteId 실패");
            responseEntity = FAIL_RESPONSE;
        }

        return responseEntity;
    }
}
