package org.rf.rfserver.mail.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.mail.dto.PostCheckReq;
import org.rf.rfserver.mail.dto.PostCheckRes;
import org.rf.rfserver.mail.dto.PostSendReq;
import org.rf.rfserver.mail.dto.PostSendRes;
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
}