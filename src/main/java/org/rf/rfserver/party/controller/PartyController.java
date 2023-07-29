package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;

import org.rf.rfserver.party.service.PartyService;
import org.rf.rfserver.party.dto.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;

    @PostMapping
    public BaseResponse<PostPartyRes> createParty(@RequestBody PostPartyReq postPartyReq) {
        try {
            return new BaseResponse<>(partyService.createParty(postPartyReq));
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    @GetMapping("/{partyId}")
    public BaseResponse<GetPartyRes> getParty(@PathVariable("partyId") Long partyId ) {
        try {
            return new BaseResponse<>(partyService.getParty(partyId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/{partyId}")
    public BaseResponse<DeletePartyRes> deleteParty(@PathVariable("partyId") Long partyId) {
        try {
            return new BaseResponse<>(partyService.deleteParty(partyId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/join")
    public BaseResponse<PostPartyJoinRes> joinParty(@RequestBody PostPartyJoinReq postPartyJoinReq) {
        try {
            return new BaseResponse<>(partyService.joinParty(postPartyJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/join/apply")
    public BaseResponse<PostPartyJoinApplyRes> partyJoinApply(@RequestBody PostPartyJoinApplyReq postPartyJoinApplyReq) {
        try {
            return new BaseResponse<>(partyService.partyJoinApply(postPartyJoinApplyReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}