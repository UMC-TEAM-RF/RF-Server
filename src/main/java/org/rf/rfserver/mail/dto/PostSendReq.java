package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.University;

@Getter
@Setter
@AllArgsConstructor
public class PostSendReq {
    private University university;
    private String mail;
}
