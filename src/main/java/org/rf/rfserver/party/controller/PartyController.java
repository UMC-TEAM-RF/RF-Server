package org.rf.rfserver.party.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;


import org.rf.rfserver.party.dto.party.*;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationRes;
import org.rf.rfserver.party.service.PartyService;

import org.rf.rfserver.config.PageDto;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<PostPartyRes> createParty(@RequestPart("postPartyReq") PostPartyReq postPartyReq, @RequestPart("file") MultipartFile file) {
        try {
            return new BaseResponse<>(partyService.createParty(postPartyReq,file));
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
    public BaseResponse<PostJoinApplicationRes> joinApply(@RequestBody PostJoinApplicationReq postJoinApplicationReq) {
        try {
            return new BaseResponse<>(partyService.joinApply(postJoinApplicationReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/join/apply/approve")
    public BaseResponse<PostApproveJoinRes> approveJoin(@RequestParam Long partyJoinApplicationId) {
        try {
            return new BaseResponse<>(partyService.approveJoin(partyJoinApplicationId));
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

    @GetMapping("/join/apply/deny")
    public BaseResponse<PostDenyJoinRes> denyJoin(@RequestParam Long partyJoinApplicationId) {
        try {
            return new BaseResponse<>(partyService.denyJoin(partyJoinApplicationId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 모임 조회 (해당 사용자가 차단한 모임 빼고)
    @GetMapping("/user/{userId}/search")
    public BaseResponse<PageDto<List<GetPartyRes>>> getNonBlockedParties(@PathVariable("userId") Long userId, Pageable pageable) {
        try {
            return new BaseResponse<>(partyService.getNonBlockedParties(userId, pageable));
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
    public BaseResponse<PageDto<List<GetPartyRes>>> getUsersParties(@PathVariable("userId") Long userId, Pageable pageable) {
        try {
            return new BaseResponse<>(partyService.getUsersParties(userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
  
     @GetMapping("/toggle/{partyId}")
    public BaseResponse<TogglePartyRecruitmentRes> togglePartyRecruitment(@PathVariable Long partyId) {
        try {
            return new BaseResponse<>(partyService.togglePartyRecruitment(partyId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 사용자 관심사 기반 모임 목록 불러오기
    @GetMapping("/user/{userId}/interests")
    public BaseResponse<PageDto<List<GetInterestPartyRes>>> getPartiesByUserInterests(@PathVariable("userId") Long userId, Pageable pageable) {
        try {
            return new BaseResponse<>(partyService.getPartiesByUserInterests(userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/eject")
    public BaseResponse<EjectUserRes> ejectUser(@RequestBody EjectUserReq ejectUserReq) {
        try {
            return new BaseResponse<>(partyService.ejectUser(ejectUserReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}