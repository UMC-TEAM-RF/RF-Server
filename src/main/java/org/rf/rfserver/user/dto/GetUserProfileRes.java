package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserProfileRes {
    private String nickName;
    private String imageFilePath;
}
