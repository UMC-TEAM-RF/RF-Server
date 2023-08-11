package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetPartyUserRes {
    private Long id;
    private String profileImage;
    private String nickName;

    @Builder
    public GetPartyUserRes(Long id, String profileImage, String nickName) {
        this.id = id;
        this.profileImage = profileImage;
        this.nickName = nickName;
    }
}
