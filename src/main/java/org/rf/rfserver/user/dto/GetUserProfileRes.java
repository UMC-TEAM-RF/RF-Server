package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.Country;

@AllArgsConstructor
@Getter
public class GetUserProfileRes {
    private String nickName;
    private String imageFilePath;
    private Country country;
}
