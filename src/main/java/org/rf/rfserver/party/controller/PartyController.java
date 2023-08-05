package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.party.service.PartyService;
import org.rf.rfserver.party.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;

    /*@PostMapping
    public BaseResponse<PostPartyRes> createParty(@RequestBody PostPartyReq postPartyReq) {
        try {
            return new BaseResponse<>(partyService.createParty(postPartyReq));
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }*/

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
    @PostMapping("/create")
    public BaseResponse<Party> userCreateParty(@RequestBody UserCreatePartyReq userCreatePartyReq) {
        try {
            return new BaseResponse<>(partyService.userCreateParty(userCreatePartyReq.getUserId(), userCreatePartyReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 모임 들어가기
    @PostMapping(value = "/join")
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

    // 모임 차단
    @PostMapping("/block/{userId}/{partyId}")
    public BaseResponse<BlockPartyRes> blockParty(@PathVariable("userId") Long userId, @PathVariable("partyId") Long partyId) {
        try {
            return new BaseResponse<>(partyService.blockAndLeaveParty(userId, partyId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 모임 조회 (차단한 모임 빼고)
    /*@GetMapping("/non-blocked")
    public BaseResponse<List<GetPartyRes>> getNonBlockedParties(@RequestParam("userId") Long userId) {
        try {
            return new BaseResponse<>(partyService.getNonBlockedParties(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }*/
    @GetMapping("/non-blocked/{userId}")
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
    @GetMapping("/user/{userId}")
    public BaseResponse<List<GetPartyRes>> getUsersParties(@PathVariable("userId") Long userId) {
        try {
            return new BaseResponse<>(partyService.getUsersParties(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}