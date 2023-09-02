package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.party.dto.favoriteparty.FavoritePartyReq;
import org.rf.rfserver.party.dto.favoriteparty.FavoritePartyRes;
import org.rf.rfserver.party.service.FavoritePartyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/favoriteParty")
public class FavoritePartyController {

    private final FavoritePartyService favoritePartyService;

    @PostMapping("/favorite")
    public BaseResponse<FavoritePartyRes> toggleFavoriteParty(@RequestBody FavoritePartyReq favoritePartyReq) {
        try {
            return new BaseResponse<>(favoritePartyService.favoriteParty(favoritePartyReq));

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
