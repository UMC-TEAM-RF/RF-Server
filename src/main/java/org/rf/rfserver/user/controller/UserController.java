package org.rf.rfserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.mail.dto.PostResetPasswordReq;
import org.rf.rfserver.mail.dto.PostResetPasswordRes;
import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        try {
            return new BaseResponse<>(userService.createUser(postUserReq));
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
    public BaseResponse<PatchUserRes> updateUser(@PathVariable("userId") Long userId, @RequestBody PatchUserReq patchUserReq) {
        try {
            return new BaseResponse<>(userService.updateUser(userId, patchUserReq));
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

    @PostMapping("/resetPassword")
    public BaseResponse<PostResetPasswordRes> resetPassword(@RequestBody PostResetPasswordReq postPasswordReq) {
        try {
            return new BaseResponse<>(userService.resetPassword(postPasswordReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
