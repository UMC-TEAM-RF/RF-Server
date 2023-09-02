package org.rf.rfserver.party.dto.favoriteparty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoritePartyReq {
    Long userId;
    Long partyId;
    Boolean isFavorite;
}
