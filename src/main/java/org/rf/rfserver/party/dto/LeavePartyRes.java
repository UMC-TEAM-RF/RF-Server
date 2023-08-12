package org.rf.rfserver.party.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LeavePartyRes {
    private Long userId;
    private Long partyId;
}
