package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponseStatus;
import org.rf.rfserver.domain.FavoriteParty;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.dto.favoriteparty.FavoritePartyReq;
import org.rf.rfserver.party.dto.favoriteparty.FavoritePartyRes;
import org.rf.rfserver.party.repository.FavoritePartyRepository;
import org.rf.rfserver.user.service.UserService;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class FavoritePartyService {
    private final FavoritePartyRepository favoritePartyRepository;
    private final UserService userService;
    private final PartyService partyService;

    public FavoritePartyRes favoriteParty(FavoritePartyReq favoritePartyReq) throws BaseException {
        User user = userService.findUserById(favoritePartyReq.getUserId());
        Party party = partyService.findPartyById(favoritePartyReq.getPartyId());
        if(favoritePartyReq.getIsFavorite()) {
            return addFavoriteParty(user, party);
        }
        return deleteFavoriteParty(user, party);
    }

    public FavoritePartyRes addFavoriteParty(User user, Party party) {
        FavoriteParty favoriteParty = new FavoriteParty(user, party);
        favoritePartyRepository.save(favoriteParty);
        return new FavoritePartyRes(true);
    }

    public FavoritePartyRes deleteFavoriteParty(User user, Party party) throws BaseException {
        FavoriteParty favoriteParty = favoritePartyRepository.findByUserAndParty(user, party)
                .orElseThrow(() -> new BaseException(NOT_FAVORITE_PARTY));
        favoriteParty.deleteFavoriteParty();
        favoritePartyRepository.delete(favoriteParty);
        return new FavoritePartyRes(true);
    }
}
