package org.rf.rfserver.party.dto.partyjoinapply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.constant.Major;
import org.rf.rfserver.constant.Mbti;

@AllArgsConstructor
@Getter
@Builder
public class GetPartyJoinApplicationListRes {
    private Long partyJoinApplicationId;
    private Country country;
    private Mbti mbti;
    private String imageFilePath;
    private Major major;
}
