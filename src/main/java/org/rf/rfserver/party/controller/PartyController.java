package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;

import org.rf.rfserver.party.dto.party.DeletePartyRes;
import org.rf.rfserver.party.dto.party.GetPartyRes;
import org.rf.rfserver.party.dto.party.PostPartyReq;
import org.rf.rfserver.party.dto.party.PostPartyRes;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinReq;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinReq;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationRes;
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
    public BaseResponse<PostJoinApplicationRes> joinApply(@RequestBody PostJoinApplicationReq postJoinApplicationReqt) {
        try {
            return new BaseResponse<>(partyService.joinApply(postJoinApplicationReqt));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/join/apply/approve")
    public BaseResponse<PostApproveJoinRes> approveJoin(@RequestBody PostApproveJoinReq postApproveJoinReq) {
        try {
            return new BaseResponse<>(partyService.approveJoin(postApproveJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/join/apply/deny")
    public BaseResponse<PostDenyJoinRes> denyJoin(@RequestBody PostDenyJoinReq postDenyJoinReq) {
        try {
            return new BaseResponse<>(partyService.denyJoin(postDenyJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}