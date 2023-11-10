package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostFindIDCheckRes {
    Boolean judge;
    private String loginId;
}