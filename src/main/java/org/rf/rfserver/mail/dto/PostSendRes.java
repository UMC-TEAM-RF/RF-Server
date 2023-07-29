package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.domain.Mail;

@Getter
@AllArgsConstructor
public class PostSendRes {
    private Mail mail;
}
