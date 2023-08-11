package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.PageDto;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.*;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final S3Uploader s3Uploader;

    public Party createParty(PostPartyReq postPartyReq, MultipartFile file) throws BaseException {
        try {
            Party party = partyRepository.save(Party.builder()
                    .name(postPartyReq.getName())
                    .content(postPartyReq.getContent())
                    .location(postPartyReq.getLocation())
                    .language(postPartyReq.getLanguage())
                    .imageFilePath(postPartyReq.getImageFilePath())
                    .preferAges(postPartyReq.getPreferAges())
                    .memberCount(postPartyReq.getMemberCount())
                    .nativeCount(postPartyReq.getNativeCount())
                    .ownerId(postPartyReq.getOwnerId())
                    .rules(postPartyReq.getRules())
                    .interests(postPartyReq.getInterests())
                    .build());

            //file 비어있는지 체크
            if(file != null){
                String imageFilePath = s3Uploader.fileUpload(file, "partyImage");
                party.updateImageUrl(imageFilePath);
            }
            partyRepository.save(party);
            return party;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPartyRes getParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));

        List<GetPartyUserRes> partyUsers = party.getUsers().stream()
                .map(userParty -> GetPartyUserRes.builder()
                        .id(userParty.getUser().getId())
                        .profileImage(userParty.getUser().getProfileImage())
                        .nickName(userParty.getUser().getNickName())
                        .build())
                .collect(Collectors.toList());

        return GetPartyRes.builder()
                .id(party.getId())
                .name(party.getName())
                .content(party.getContent())
                .location(party.getLocation())
                .language(party.getLanguage())
                .imageFilePath(party.getImageFilePath())
                .preferAges(party.getPreferAges())
                .memberCount(party.getMemberCount())
                .nativeCount(party.getNativeCount())
                .ownerId(party.getOwnerId())
                .rules(party.getRules())
                .interests(party.getInterests())
                .schedules(party.getSchedules())
                .users(partyUsers)
                .build();

        /*return GetPartyRes.builder()
                .id(party.getId())
                .name(party.getName())
                .content(party.getContent())
                .location(party.getLocation())
                .language(party.getLanguage())
                .imageFilePath(party.getImageFilePath())
                .preferAges(party.getPreferAges())
                .memberCount(party.getMemberCount())
                .nativeCount(party.getNativeCount())
                .ownerId(party.getOwnerId())
                .users(party.getUsers())
                .schedules(party.getSchedules())
                .interests(party.getInterests())
                .rules(party.getRules())
                .build();*/
    }

    @Transactional
    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        partyRepository.delete(party);
        return DeletePartyRes.builder()
                .id(partyId)
                .build();
    }

    // 사용자가 모임 생성
    public Party userCreateParty(Long userId, PostPartyReq userCreatePartyReq, MultipartFile file) throws BaseException {
        PostPartyReq postPartyReq = new PostPartyReq(
                userCreatePartyReq.getName(),
                userCreatePartyReq.getContent(),
                userCreatePartyReq.getLocation(),
                userCreatePartyReq.getLanguage(),
                userCreatePartyReq.getImageFilePath(),
                userCreatePartyReq.getPreferAges(),
                userCreatePartyReq.getMemberCount(),
                userCreatePartyReq.getNativeCount(),
                userCreatePartyReq.getOwnerId(),
                userCreatePartyReq.getRules(),
                userCreatePartyReq.getInterests()
        );
        // 모임 생성
        Party party = createParty(postPartyReq, file);
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

    // 모임 조회 (차단한 거 빼고)
    public PageDto<List<GetPartyRes>> getNonBlockedParties(Long userId, Pageable pageable) throws BaseException {
        userRepository.findById(userId).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        Page<Party> nonBlockedParties = partyRepository.findNonBlockedPartiesByUserId(userId, pageable);

        return new PageDto<>(nonBlockedParties.getNumber(), nonBlockedParties.getTotalPages(), nonBlockedParties.stream()
                .map(party -> {
                    List<GetPartyUserRes> partyUsers = party.getUsers().stream()
                            .map(userParty -> GetPartyUserRes.builder()
                                    .id(userParty.getUser().getId())
                                    .profileImage(userParty.getUser().getProfileImage())
                                    .nickName(userParty.getUser().getNickName())
                                    .build())
                            .collect(Collectors.toList());

                    return GetPartyRes.builder()
                            .id(party.getId())
                            .name(party.getName())
                            .content(party.getContent())
                            .location(party.getLocation())
                            .language(party.getLanguage())
                            .imageFilePath(party.getImageFilePath())
                            .preferAges(party.getPreferAges())
                            .memberCount(party.getMemberCount())
                            .nativeCount(party.getNativeCount())
                            .ownerId(party.getOwnerId())
                            .rules(party.getRules())
                            .interests(party.getInterests())
                            .schedules(party.getSchedules())
                            .users(partyUsers)
                            .build();
                })
                .collect(Collectors.toList()));
    }

    /**
     * 클라이언트가 속한 그룹 리스트를 조회 서비스
     * @param userId
     * @return List[GetPartyRes]
     * @throws BaseException
     */
    public PageDto<List<GetPartyRes>> getUsersParties(Long userId, Pageable pageable) throws BaseException {
        userRepository.findById(userId).orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        Page<UserParty> usersParties = userPartyRepository.findUserPartiesByUserId(userId, pageable);

        return new PageDto<>(usersParties.getNumber(), usersParties.getTotalPages(), usersParties.stream()
                .map(userParty -> {
                    List<GetPartyUserRes> partyUsers = userParty.getParty().getUsers().stream()
                            .map(userPartyItem -> GetPartyUserRes.builder()
                                    .id(userPartyItem.getUser().getId())
                                    .profileImage(userPartyItem.getUser().getProfileImage())
                                    .nickName(userPartyItem.getUser().getNickName())
                                    .build())
                            .collect(Collectors.toList());

                    return GetPartyRes.builder()
                            .id(userParty.getParty().getId())
                            .name(userParty.getParty().getName())
                            .content(userParty.getParty().getContent())
                            .location(userParty.getParty().getLocation())
                            .language(userParty.getParty().getLanguage())
                            .imageFilePath(userParty.getParty().getImageFilePath())
                            .preferAges(userParty.getParty().getPreferAges())
                            .memberCount(userParty.getParty().getMemberCount())
                            .nativeCount(userParty.getParty().getNativeCount())
                            .ownerId(userParty.getParty().getOwnerId())
                            .rules(userParty.getParty().getRules())
                            .interests(userParty.getParty().getInterests())
                            .schedules(userParty.getParty().getSchedules())
                            .users(partyUsers)
                            .build();
                })
                .collect(Collectors.toList()));
    }
}