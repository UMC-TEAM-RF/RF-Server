package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCheckReq {
    private String mail;
    private String code;
}