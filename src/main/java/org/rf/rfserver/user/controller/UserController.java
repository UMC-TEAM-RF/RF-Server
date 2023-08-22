package org.rf.rfserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.mail.dto.PostResetPasswordReq;
import org.rf.rfserver.mail.dto.PostResetPasswordRes;
import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.rf.rfserver.config.BaseResponseStatus.SUCCESS;

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

    // 아이디 찾기
    @PostMapping("/findId")
    public BaseResponse<PostResetPasswordRes> findId(@RequestBody PostResetPasswordReq postPasswordReq) {
        try {
            return new BaseResponse<>(userService.findId(postPasswordReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
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


    // 좋아요 누르기
    @PostMapping("/{giveUserId}/pressLike/{receiveUserId}")
    public BaseResponse<Void> pressLike(@PathVariable("giveUserId") Long giveUserId, @PathVariable("receiveUserId") Long receiveUserId) {
        try {
            userService.pressLike(giveUserId, receiveUserId);
            return new BaseResponse<Void>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<Void>(e.getStatus());
        }
    }

    // 좋아요 취소
    @PostMapping("/{giveUserId}/cancelLike/{receiveUserId}")
    public BaseResponse<Void> cancelLike(@PathVariable("giveUserId") Long giveUserId, @PathVariable("receiveUserId") Long receiveUserId) {
        try {
            userService.cancelLike(giveUserId, receiveUserId);
            return new BaseResponse<Void>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<Void>(e.getStatus());
        }
    }

    // 싫어요 누르기
    @PostMapping("/{giveUserId}/pressHate/{receiveUserId}")
    public BaseResponse<Void> pressHate(@PathVariable("giveUserId") Long giveUserId, @PathVariable("receiveUserId") Long receiveUserId) {
        try {
            userService.pressHate(giveUserId, receiveUserId);
            return new BaseResponse<Void>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<Void>(e.getStatus());
        }
    }

    // 싫어요 취소
    @PostMapping("/{giveUserId}/cancelHate/{receiveUserId}")
    public BaseResponse<Void> cancelHate(@PathVariable("giveUserId") Long giveUserId, @PathVariable("receiveUserId") Long receiveUserId) {
        try {
            userService.cancelHate(giveUserId, receiveUserId);
            return new BaseResponse<Void>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<Void>(e.getStatus());
        }
    }
}
