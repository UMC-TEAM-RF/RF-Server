package org.rf.rfserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResetPasswordRes {
    private boolean success;
    private String message;
}
