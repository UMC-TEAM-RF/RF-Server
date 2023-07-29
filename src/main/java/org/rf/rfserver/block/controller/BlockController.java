package org.rf.rfserver.block.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.block.dto.DeleteBlockRes;
import org.rf.rfserver.block.dto.GetBlockRes;
import org.rf.rfserver.block.dto.PostBlockReq;
import org.rf.rfserver.block.dto.PostBlockRes;
import org.rf.rfserver.block.service.BlockService;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockController {
    private final BlockService blockService;
    @PostMapping("")
    public BaseResponse<PostBlockRes> createBlock(@RequestBody PostBlockReq blockReq) {
        try {
            return new BaseResponse<>(blockService.createBlock(blockReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{blockId}")
    public BaseResponse<GetBlockRes> getBlock(@PathVariable("blockId") Long blockId) {
        try {
            return new BaseResponse<>(blockService.getBlock(blockId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/{blockId}")
    public BaseResponse<DeleteBlockRes> deleteBlock(@PathVariable("blockId") Long blockId) {
        try {
            return new BaseResponse<>(blockService.deleteBlock(blockId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
