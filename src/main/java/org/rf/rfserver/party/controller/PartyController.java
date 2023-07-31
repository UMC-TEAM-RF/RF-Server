package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;

import org.rf.rfserver.party.dto.party.DeletePartyRes;
import org.rf.rfserver.party.dto.party.GetPartyRes;
import org.rf.rfserver.party.dto.party.PostPartyReq;
import org.rf.rfserver.party.dto.party.PostPartyRes;
import org.rf.rfserver.party.dto.partyjoin.PostApprovePartyJoinReq;
import org.rf.rfserver.party.dto.partyjoin.PostApprovePartyJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyPartyJoinReq;
import org.rf.rfserver.party.dto.partyjoin.PostDenyPartyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostPartyJoinApplyReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostPartyJoinApplyRes;
import org.rf.rfserver.party.service.PartyService;
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

    @PostMapping("/join/apply")
    public BaseResponse<PostPartyJoinApplyRes> partyJoinApply(@RequestBody PostPartyJoinApplyReq postPartyJoinApplyReq) {
        try {
            return new BaseResponse<>(partyService.partyJoinApply(postPartyJoinApplyReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/join/apply/approve")
    public BaseResponse<PostApprovePartyJoinRes> approveJoinParty(@RequestBody PostApprovePartyJoinReq postApprovePartyJoinReq) {
        try {
            return new BaseResponse<>(partyService.joinParty(postApprovePartyJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @PostMapping("/join/apply/deny")
    public BaseResponse<PostDenyPartyJoinRes> denyJoinParty(@RequestBody PostDenyPartyJoinReq postDenyPartyJoinReq) {
        try {
            return new BaseResponse<>(partyService.denyJoinParty(postDenyPartyJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}