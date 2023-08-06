package org.rf.rfserver.blockParty.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.blockParty.dto.BlockPartyRes;
import org.rf.rfserver.blockParty.repository.BlockPartyRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.BlockParty;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlockPartyService {
    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final UserPartyRepository userPartyRepository;
    private final BlockPartyRepository blockPartyRepository;


    // 모임 차단
    public BlockPartyRes blockAndLeaveParty(Long userId, Long partyId) throws BaseException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        Party party = partyRepository.findById(partyId).orElseThrow(() -> new BaseException(PARTY_NOT_FOUND));

        // 사용자가 참여한 모임 조회
        UserParty userParty = userPartyRepository.findByUserAndParty(user, party)
                .orElseThrow(() -> new BaseException(NOT_JOINED_PARTY));

        // 모임 차단
        BlockParty blockParty = new BlockParty(user, party);

        // 연관관계 편의 메소드를 사용해 양방향 관계 해제
        user.removeUserParty(userParty);
        party.removeUserParty(userParty);

        // 사용자와 모임 연결 제거 (모임에서 나가기)
        userPartyRepository.delete(userParty);

        blockPartyRepository.save(blockParty);

        return BlockPartyRes.builder()
                .userId(userId)
                .partyId(partyId)
                .build();
    }
}
