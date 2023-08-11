package org.rf.rfserver.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.constant.Country;

@Getter
public class GetUserProfileRes {
    private String nickName;
    private String imageFilePath;
    private Country country;

    @Builder
    public GetUserProfileRes(String nickName, String imageFilePath, Country country) {
        this.nickName = nickName;
        this.imageFilePath = imageFilePath;
        this.country = country;
    }
}