package org.rf.rfserver.blockUser.service;


import lombok.RequiredArgsConstructor;
import org.rf.rfserver.blockUser.dto.DeleteBlockUserRes;
import org.rf.rfserver.blockUser.dto.GetBlockUserRes;
import org.rf.rfserver.blockUser.dto.PostBlockUserReq;
import org.rf.rfserver.blockUser.dto.PostBlockUserRes;
import org.rf.rfserver.blockUser.repository.BlockUserRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.BlockUser;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;

@RequiredArgsConstructor
@Service
public class BlockUserService {
    private final BlockUserRepository blockRepository;

    public PostBlockUserRes createBlock(PostBlockUserReq blockReq) throws BaseException {
        BlockUser block = new BlockUser(blockReq.getBlocker(), blockReq.getBlocked());
        try {
            BlockUser blockRes = blockRepository.save(block);
            System.out.println(blockRes);
            return new PostBlockUserRes(block.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBlockUserRes getBlock(Long blockId) throws BaseException {
        try {
            BlockUser block = blockRepository.getReferenceById(blockId);
            System.out.println(block);
            return new GetBlockUserRes(block.getBlocker(), block.getBlocked());
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public DeleteBlockUserRes deleteBlock(Long blockId) throws BaseException{
        try {
            blockRepository.deleteById(blockId);
            return new DeleteBlockUserRes(true);
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
