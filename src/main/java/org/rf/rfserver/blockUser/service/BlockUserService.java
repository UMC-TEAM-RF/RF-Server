package org.rf.rfserver.blockUser.service;


import lombok.RequiredArgsConstructor;
import org.rf.rfserver.blockUser.dto.*;
import org.rf.rfserver.blockUser.repository.BlockUserRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.BlockParty;
import org.rf.rfserver.domain.BlockUser;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.repository.BlockPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;

@RequiredArgsConstructor
@Service
public class BlockUserService {
    private final BlockUserRepository blockUserRepository;
    private final BlockPartyRepository blockPartyRepository;
    private final UserRepository userRepository;

    public PostBlockUserRes createBlock(PostBlockUserReq blockReq) throws BaseException {
        BlockUser block = new BlockUser(blockReq.getBlocker(), blockReq.getBlocked());
        try {
            BlockUser blockRes = blockUserRepository.save(block);
            System.out.println(blockRes);
            return new PostBlockUserRes(block.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetBlockUserRes getBlock(Long blockId) throws BaseException {
        try {
            BlockUser block = blockUserRepository.getReferenceById(blockId);
            System.out.println(block);
            return new GetBlockUserRes(block.getBlocker(), block.getBlocked());
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public DeleteBlockUserRes deleteBlock(Long blockId) throws BaseException{
        try {
            blockUserRepository.deleteById(blockId);
            return new DeleteBlockUserRes(true);
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 사용자 차단 리스트 조회
     * @param blocker
     * @return List[GetBlockPartyRes]
     * @throws BaseException
     */
    public List<GetBlockUserRes> getUserBlocks(User blocker) throws BaseException{
        try {
            List<BlockUser> blocks = blockUserRepository.getBlockUsersByBlocker(blocker);
            List<GetBlockUserRes> getBlockUsersRes = blocks.stream()
                    .map(e -> new GetBlockUserRes(e.getBlocker(), e.getBlocked()))
                    .collect(Collectors.toList());
            return getBlockUsersRes;
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
