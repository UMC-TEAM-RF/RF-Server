package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFindIDReq {
    private String nickName;
    private String mail;
}
