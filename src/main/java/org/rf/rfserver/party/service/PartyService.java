package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.blockParty.dto.BlockPartyRes;
import org.rf.rfserver.blockParty.repository.BlockPartyRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.*;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final UserPartyRepository userPartyRepository;
    private final BlockPartyRepository blockPartyRepository;

    public Party createParty(PostPartyReq postPartyReq) throws BaseException {
        try {
            Party party = partyRepository.save(Party.builder()
                    .name(postPartyReq.getName())
                    .content(postPartyReq.getContent())
                    .location(postPartyReq.getLocation())
                    .language(postPartyReq.getLanguage())
                    .imageFilePath(postPartyReq.getImageFilePath())
                    .preferAges(postPartyReq.getPreferAges())
                    .createdDate(LocalDateTime.now())
                    .memberCount(postPartyReq.getMemberCount())
                    .nativeCount(postPartyReq.getNativeCount())
                    .ownerId(postPartyReq.getOwnerId())
                    //      .rule(postPartyReq.getRules())
                    .build());
            /*for (Interest interest : postPartyReq.getInterests()) {
                PartyInterest partyInterest = partyInterestRepository.save(new PartyInterest(interest, party));
                partyInterest.getParty().getInterests().add(partyInterest);
            }*/
            return party;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPartyRes getParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        return GetPartyRes.builder()
                .id(party.getId())
                .name(party.getName())
                .content(party.getContent())
                .location(party.getLocation())
                .language(party.getLanguage())
                .imageFilePath(party.getImageFilePath())
                .preferAges(party.getPreferAges())
                .createdDate(party.getCreatedDate())
                .memberCount(party.getMemberCount())
                .nativeCount(party.getNativeCount())
                .ownerId(party.getOwnerId())
                .users(party.getUsers())
                .schedules(party.getSchedules())
/*
                .interests(party.getInterests())
*/
                //.rule(party.getRule())
                //.tags(party.getTags())
                .build();
    }

    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        /*for (PartyInterest partyInterest: party.getInterests() ) {
            partyInterestRepository.delete(partyInterest);
        }*/
        partyRepository.delete(party);
        return DeletePartyRes.builder()
                .id(partyId)
                .build();
    }

    // 사용자가 모임 생성
    public Party userCreateParty(Long userId, UserCreatePartyReq userCreatePartyReq) throws BaseException {
        PostPartyReq postPartyReq = new PostPartyReq(
                userCreatePartyReq.getName(),
                userCreatePartyReq.getContent(),
                userCreatePartyReq.getLocation(),
                userCreatePartyReq.getLanguage(),
                userCreatePartyReq.getImageFilePath(),
                userCreatePartyReq.getPreferAges(),
                userCreatePartyReq.getMemberCount(),
                userCreatePartyReq.getNativeCount(),
                userCreatePartyReq.getOwnerId()
/*
                userCreatePartyReq.getInterests()
*/
        );
        // 모임 생성
        Party party = createParty(postPartyReq);
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 사용자에게 생성한 모임 추가
        UserParty userParty = new UserParty(party, user);

        // 연관관계 편의 메소드를 사용해 양방향 관계 설정
        user.addUserParty(userParty);
        party.addUserParty(userParty);

        userPartyRepository.save(userParty);

        return party;
    }

    // 모임 들어가기
    public JoinPartyRes joinParty(Long userId, Long partyId) throws BaseException {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 모임 조회
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(PARTY_NOT_FOUND));

        // 이미 모임에 참여한 사용자인지 확인
        UserParty userParty = userPartyRepository.findByUserAndParty(user, party)
                .orElse(null);
        if (userParty != null) {
            throw new BaseException(ALREADY_JOINED_PARTY);
        }

        // 사용자에게 선택한 모임 추가
        userParty = new UserParty(party, user);

        // 연관관계 편의 메소드를 사용해 양방향 관계 설정
        user.addUserParty(userParty);
        party.addUserParty(userParty);

        userPartyRepository.save(userParty);

        return JoinPartyRes.builder()
                .userId(userId)
                .partyId(partyId)
                .build();
    }

    // 모임 나가기
    public LeavePartyRes leaveParty(Long userId, Long partyId) throws BaseException {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 모임 조회
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(PARTY_NOT_FOUND));

        // 사용자가 해당 모임에 있는지 확인
        UserParty userParty = userPartyRepository.findByUserAndParty(user, party)
                .orElseThrow(() -> new BaseException(ALREADY_LEFT_PARTY));

        // 연관관계 편의 메소드를 사용해 양방향 관계 해제
        user.removeUserParty(userParty);
        party.removeUserParty(userParty);

        // 사용자와 모임 연결 제거
        userPartyRepository.delete(userParty);

        return LeavePartyRes.builder()
                .userId(userId)
                .partyId(partyId)
                .build();
    }

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

    // 모임 조회 (차단한 거 빼고)
    public List<GetPartyRes> getNonBlockedParties(Long userId) throws BaseException {
        userRepository.findById(userId).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        List<Party> nonBlockedParties = partyRepository.findNonBlockedPartiesByUserId(userId);

        return nonBlockedParties.stream().map(party -> GetPartyRes.builder()
                        .id(party.getId())
                        .name(party.getName())
                        .content(party.getContent())
                        .location(party.getLocation())
                        .language(party.getLanguage())
                        .imageFilePath(party.getImageFilePath())
                        .preferAges(party.getPreferAges())
                        .createdDate(party.getCreatedDate())
                        .memberCount(party.getMemberCount())
                        .nativeCount(party.getNativeCount())
                        .ownerId(party.getOwnerId())
                        .users(party.getUsers())
                        .schedules(party.getSchedules())
                        .build())
                .collect(Collectors.toList());
    }
}