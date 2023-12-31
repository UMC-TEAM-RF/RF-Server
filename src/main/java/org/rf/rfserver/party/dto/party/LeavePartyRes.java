package org.rf.rfserver.party.dto.party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class LeavePartyRes {
    private Long userId;
    private Long partyId;
}
