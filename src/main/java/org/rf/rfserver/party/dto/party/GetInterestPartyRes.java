package org.rf.rfserver.party.dto.party;

import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.constant.Interest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetInterestPartyRes {
    private Long id;
    private String name;
    private String content;
    private String imageFilePath;
    private LocalDateTime createdDate;
    private Integer memberCount;
    private Long ownerId;
    private List<Interest> interests;
    private Integer currentMemberCount;

    @Builder
    public GetInterestPartyRes(Long id, String name, String content, String imageFilePath,
                       LocalDateTime createdDate, Integer memberCount, Long ownerId, List<Interest> interests,
                               Integer currentMemberCount) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imageFilePath = imageFilePath;
        this.createdDate = createdDate;
        this.memberCount = memberCount;
        this.ownerId = ownerId;
        this.interests = interests;
        this.currentMemberCount = currentMemberCount;
    }
}
