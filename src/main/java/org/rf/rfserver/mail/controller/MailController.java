package org.rf.rfserver.mail.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.mail.dto.PostMailReq;
import org.rf.rfserver.mail.dto.PostMailRes;
import org.rf.rfserver.mail.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.rf.rfserver.config.BaseResponseStatus.MAIL_CHECK_SUCCESS;
import static org.rf.rfserver.config.BaseResponseStatus.MAIL_SEND_SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<BaseResponse> send(@RequestBody PostMailReq mailRequest) {
        String mail = mailRequest.getMail();
        PostMailRes mailResponse = mailService.sendMail(mail);
        BaseResponse response = BaseResponse.of(MAIL_SEND_SUCCESS, mailResponse);
        return new ResponseEntity<>(response, CREATED);
    }

    @PostMapping("/check")
    public ResponseEntity<BaseResponse> auth(@RequestBody PostMailReq mailRequest) {
        PostMailRes mailResponse = mailService.checkCode(mailRequest);
        return ResponseEntity.ok(BaseResponse.of(MAIL_CHECK_SUCCESS));
    }
}
