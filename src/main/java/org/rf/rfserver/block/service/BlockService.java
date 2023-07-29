package org.rf.rfserver.block.service;


import lombok.RequiredArgsConstructor;
import org.rf.rfserver.block.dto.DeleteBlockRes;
import org.rf.rfserver.block.dto.GetBlockRes;
import org.rf.rfserver.block.dto.PostBlockReq;
import org.rf.rfserver.block.dto.PostBlockRes;
import org.rf.rfserver.block.repository.BlockRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Block;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;

@RequiredArgsConstructor
@Service
public class BlockService {
    private final BlockRepository blockRepository;

    public PostBlockRes createBlock(PostBlockReq blockReq) throws BaseException {
        Block block = new Block(blockReq.getBlocker(), blockReq.getBlocked());
        try {
            Block blockRes = blockRepository.save(block);
            System.out.println(blockRes);
            return new PostBlockRes(block.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBlockRes getBlock(Long blockId) throws BaseException {
        try {
            Block block = blockRepository.getReferenceById(blockId);
            System.out.println(block);
            return new GetBlockRes(block.getBlocker(), block.getBlocked());
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public DeleteBlockRes deleteBlock(Long blockId) throws BaseException{
        try {
            blockRepository.deleteById(blockId);
            return new DeleteBlockRes(true);
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
