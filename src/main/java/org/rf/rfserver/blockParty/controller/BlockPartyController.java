package org.rf.rfserver.blockParty.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.blockParty.dto.BlockPartyRes;
import org.rf.rfserver.blockParty.service.BlockPartyService;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/block/party")
public class BlockPartyController {
    private final BlockPartyService blockPartyService;

    // 모임 차단
    @PostMapping("/{userId}/{partyId}")
    public BaseResponse<BlockPartyRes> blockParty(@PathVariable("userId") Long userId, @PathVariable("partyId") Long partyId) {
        try {
            return new BaseResponse<>(blockPartyService.blockAndLeaveParty(userId, partyId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
