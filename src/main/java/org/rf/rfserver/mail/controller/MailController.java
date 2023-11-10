package org.rf.rfserver.mail.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.mail.dto.*;
import org.rf.rfserver.mail.service.MailService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public BaseResponse<PostSendRes> sendMail(@RequestBody PostSendReq sendReq) {
        try {
            return new BaseResponse<>(mailService.sendMail(sendReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/check")
    public BaseResponse<PostCheckRes> auth(@RequestBody PostCheckReq checkReq) {
        try {
            return new BaseResponse<>(mailService.checkCode(checkReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 아이디 찾기
    @PostMapping("/send/findID")
    public BaseResponse<PostFindIDRes> findID(@RequestBody PostFindIDReq findIDReq) {
        try {
            return new BaseResponse<>(mailService.findID(findIDReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 아이디 찾기 (인증코드 확인)
    @PostMapping("/check/findID")
    public BaseResponse<PostFindIDCheckRes> checkFindID(@RequestBody PostCheckReq checkReq) {
        try {
            return new BaseResponse<>(mailService.checkFindID(checkReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}