package org.rf.rfserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.user.dto.PostUserReq;
import org.rf.rfserver.user.dto.PostUserRes;
import org.rf.rfserver.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
