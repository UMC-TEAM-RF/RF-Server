package org.rf.rfserver.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.mail.dto.PostResetPasswordReq;
import org.rf.rfserver.mail.dto.PostResetPasswordRes;
import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.dto.sign.LoginReq;
import org.rf.rfserver.user.dto.sign.LoginRes;
import org.rf.rfserver.user.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<PostUserRes> createUser(@RequestPart("postUserReq") PostUserReq postUserReq, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            return new BaseResponse<>(userService.createUser(postUserReq, file));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") Long userId) {
        try {
            return new BaseResponse<>(userService.getUser(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/{userId}")
    public BaseResponse<PatchUserRes> updateUser(@PathVariable("userId") Long userId, @RequestPart PatchUserReq patchUserReq, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            return new BaseResponse<>(userService.updateUser(userId, patchUserReq, file));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/{userId}")
    public BaseResponse<DeleteUserRes> deleteUser(@PathVariable("userId") Long userId) {
        try {
            return new BaseResponse<>(userService.deleteUser(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/idCheck/{loginId}")
    public BaseResponse<GetUserIdCheckRes> checkId(@PathVariable("loginId") String loginId) {
        try {
            return new BaseResponse<>(userService.checkId(loginId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/nicknameCheck/{nickName}")
    public BaseResponse<GetNicknameCheckRes> checkNickname(@PathVariable("nickName") String nickName) {
        try {
            return new BaseResponse<>(userService.checkNickname(nickName));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@RequestBody LoginReq loginReq) {
        try {
            return new BaseResponse<>(userService.login(loginReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 아이디 찾기
    @PostMapping("/findId")
    public BaseResponse<PostResetPasswordRes> findId(@RequestBody PostResetPasswordReq postPasswordReq) {
        try {
            return new BaseResponse<>(userService.findId(postPasswordReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    // 비밀번호 재설정
    @PostMapping("/resetPassword")
    public BaseResponse<PostResetPasswordRes> resetPassword(@RequestBody PostResetPasswordReq postPasswordReq) {
        try {
            return new BaseResponse<>(userService.resetPassword(postPasswordReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
