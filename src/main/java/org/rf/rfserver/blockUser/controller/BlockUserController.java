package org.rf.rfserver.blockUser.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.blockUser.dto.*;
import org.rf.rfserver.blockUser.service.BlockUserService;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 사용자가 차단한 사용자 리스트 조회
     * @param userId
     * @return List[GetBlockUserRes]
     */
    @GetMapping("/user/{userId}")
    public BaseResponse<List<GetBlockUserRes>> getBlocks(@PathVariable("userId") User userId) {
        try {
            return new BaseResponse<>(blockService.getUserBlocks(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
