package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.blockParty.dto.BlockPartyRes;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.party.service.PartyService;
import org.rf.rfserver.party.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;

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

    // 모임 생성
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<Party> createParty(@RequestParam("userId") Long userId, @RequestPart("postPartyReq") PostPartyReq postPartyReq, @RequestPart("file") MultipartFile file) {
        try {
            return new BaseResponse<>(partyService.userCreateParty(userId, postPartyReq, file));
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    // 모임 들어가기
    @PostMapping("/join")
    public BaseResponse<JoinPartyRes> joinParty(@RequestBody JoinPartyReq joinPartyReq) {
        try {
            return new BaseResponse<>(partyService.joinParty(joinPartyReq.getUserId(), joinPartyReq.getPartyId()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 모임 나가기
    @PostMapping("/leave/{userId}/{partyId}")
    public BaseResponse<LeavePartyRes> leaveParty(@PathVariable("userId") Long userId, @PathVariable("partyId") Long partyId) {
        try {
            return new BaseResponse<>(partyService.leaveParty(userId, partyId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/user/{userId}/search")
    public BaseResponse<List<GetPartyRes>> getNonBlockedParties(@PathVariable("userId") Long userId) {
        try {
            return new BaseResponse<>(partyService.getNonBlockedParties(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 클라이언트가 속한 그룹 리스트를 조회 컨트롤러
     * @param userId
     * @return List[GetPartyRes]
     */
    @GetMapping("/user/{userId}/belong")
    public BaseResponse<List<GetPartyRes>> getUsersParties(@PathVariable("userId") Long userId) {
        try {
            return new BaseResponse<>(partyService.getUsersParties(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}