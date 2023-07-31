package org.rf.rfserver.blockUser.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.blockUser.dto.DeleteBlockUserRes;
import org.rf.rfserver.blockUser.dto.GetBlockUserRes;
import org.rf.rfserver.blockUser.dto.PostBlockUserReq;
import org.rf.rfserver.blockUser.dto.PostBlockUserRes;
import org.rf.rfserver.blockUser.service.BlockUserService;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockUserController {
    private final BlockUserService blockService;
    @PostMapping("")
    public BaseResponse<PostBlockUserRes> createBlock(@RequestBody PostBlockUserReq blockReq) {
        try {
            return new BaseResponse<>(blockService.createBlock(blockReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{blockId}")
    public BaseResponse<GetBlockUserRes> getBlock(@PathVariable("blockId") Long blockId) {
        try {
            return new BaseResponse<>(blockService.getBlock(blockId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/{blockId}")
    public BaseResponse<DeleteBlockUserRes> deleteBlock(@PathVariable("blockId") Long blockId) {
        try {
            return new BaseResponse<>(blockService.deleteBlock(blockId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
