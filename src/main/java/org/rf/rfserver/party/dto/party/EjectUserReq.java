package org.rf.rfserver.party.dto.party;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EjectUserReq {
    private Long ownerId;
    private Long partyId;
    private Long userId;
}
