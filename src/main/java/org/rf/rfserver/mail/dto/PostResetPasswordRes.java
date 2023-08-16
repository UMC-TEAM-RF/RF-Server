package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.MailMessage;

@Getter
@AllArgsConstructor
public class PostResetPasswordRes {
    private boolean success;
    private String message;

    public PostResetPasswordRes(boolean success, MailMessage mailMessage) {
        this.success = success;
        this.message = mailMessage.getValue();
    }
}
