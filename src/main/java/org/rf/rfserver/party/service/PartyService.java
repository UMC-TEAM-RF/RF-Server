package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.party.*;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationRes;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.dto.GetUserProfileRes;
import org.rf.rfserver.user.repository.UserRepository;
import org.rf.rfserver.user.service.UserService;

import org.rf.rfserver.config.PageDto;

import org.rf.rfserver.party.dto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserPartyRepository userPartyRepository;
    private final PartyJoinApplicationRepository partyJoinApplicationRepository;
    private final S3Uploader s3Uploader;


    public PostPartyRes createParty(PostPartyReq postPartyReq, MultipartFile file) throws BaseException {
        try {
            User user = userService.findUserById(postPartyReq.getOwnerId());
            Party party = partyRepository.save(Party.builder()
                    .name(postPartyReq.getName())
                    .content(postPartyReq.getContent())
                    .location(postPartyReq.getLocation())
                    .language(postPartyReq.getLanguage())
                    .interests(postPartyReq.getInterests())
                    .preferAges(postPartyReq.getPreferAges())
                    .memberCount(postPartyReq.getMemberCount())
                    .nativeCount(postPartyReq.getNativeCount())
                    .ownerId(postPartyReq.getOwnerId())
                    .rules(postPartyReq.getRules())
                    .interests(postPartyReq.getInterests())
                    .build());
            addOwnerToParty(user, party);
            //file 비어있는지 체크
            if(file != null){
                String imageFilePath = s3Uploader.fileUpload(file, "partyImage");
                party.updateImageUrl(imageFilePath);
            }
            return new PostPartyRes(party.getId(), postPartyReq);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void addOwnerToParty(User user, Party party) {
        UserParty userParty = new UserParty(party, user);
        userPartyRepository.save(userParty);
        if(userService.isKorean(user)) {
            party.plusCurrentNativeCount();
        }
    }

    public GetPartyRes getParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        List<GetUserProfileRes> userProfiles = userService.getUserProfiles(party.getUsers());
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
                .schedules(party.getSchedules())
                .interests(party.getInterests())
                .userProfiles(userProfiles)
                .schedules(party.getSchedules())
                .interests(party.getInterests())
                .rules(party.getRules())
                .build();
    }


    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
        deleteUserParty(party.getUsers());
        partyRepository.delete(party);
        return new DeletePartyRes(partyId);
    }

    Party findPartyById(Long partyId) throws BaseException {
        return partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
    }

    public void deleteUserParty(List<UserParty> userParties) {
        List<Long> userPartyIds = new ArrayList<>();
        for (UserParty userParty : userParties) {
            userParty.getUser().getUserParties().remove(userParty);
        }
        userPartyRepository.deleteUserParties(userPartyIds);
    }

    public PostJoinApplicationRes joinApply(PostJoinApplicationReq postJoinApplyReq) throws BaseException {
        Party party = findPartyById(postJoinApplyReq.getPartyId());
        User user = userService.findUserById(postJoinApplyReq.getUserId());
        joinApplyValidation(party, user);
        PartyJoinApplication partyJoinApplication = new PartyJoinApplication(user, party);
        partyJoinApplicationRepository.save(partyJoinApplication);
        return new PostJoinApplicationRes(partyJoinApplication.getId());
    }

    public void joinApplyValidation(Party party, User user) throws BaseException {
        isFullParty(party);
        if(userService.isKorean(user)) {
            isFullOfKorean(party);
        }
        userService.isExceededPartyCount(user);
        isJoinedUser(user, party);
    }

    public void isFullParty(Party party) throws BaseException {
        if (party.getUsers().size() >= party.getMemberCount()) {
            throw new BaseException(EXCEEDED_PARTY_USER_COUNT);
        }
    }

    public void isFullOfKorean(Party party) throws BaseException {
        if(party.getNativeCount() <= party.getCurrentNativeCount()) {
            throw new BaseException(FULL_OF_KOREAN);
        }
    }


    public void isJoinedUser(User user, Party party) throws BaseException {
        for (UserParty userParty : party.getUsers() ) {
            if(userParty.getUser() == user) {
                throw new BaseException(INVALID_JOIN_APPLICATION);
            }
        }
    }

    public PostApproveJoinRes approveJoin(Long partyJoinApplicationId) throws BaseException {
        PartyJoinApplication partyJoinApplication = partyJoinApplicationRepository.findById(partyJoinApplicationId)
                .orElseThrow(() -> new BaseException(INVALID_APPLICATION));
        User user = userService.findUserById(partyJoinApplication.getUser().getId());
        Party party = findPartyById(partyJoinApplication.getParty().getId());
        makeUserParty(user, party);
        if (userService.isKorean(user)) {
            party.plusCurrentNativeCount();
        }
        deletePartyJoinApplication(partyJoinApplicationId);
        return new PostApproveJoinRes(partyJoinApplicationId);
    }

    public void makeUserParty(User user, Party party) {
        UserParty userParty = new UserParty(party, user);
        userPartyRepository.save(userParty);
    }

    public PostDenyJoinRes denyJoin(Long partyJoinApplicationId) throws BaseException {
        deletePartyJoinApplication(partyJoinApplicationId);
        return new PostDenyJoinRes(partyJoinApplicationId);
    }

    public void deletePartyJoinApplication(Long partyJoinApplicationId) throws BaseException {
        partyJoinApplicationRepository.delete(partyJoinApplicationRepository.findById(partyJoinApplicationId)
                .orElseThrow(() -> new BaseException(INVALID_JOIN_APPLICATION)));
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
                .map(party -> GetPartyRes.builder()
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
                        //.users(party.getUsers())
                        .schedules(party.getSchedules())
                        .build())
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
                .map(userParty -> GetPartyRes.builder()
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
                        //.users(userParty.getParty().getUsers())
                        .schedules(userParty.getParty().getSchedules())
                        .build())
                .collect(Collectors.toList()));
    }
    // 사용자 관심사 기반 모임 목록 불러오기 (가입한 모임, 차단한 모임 제외)
    public PageDto<List<GetInterestPartyRes>> getPartiesByUserInterests(Long userId, Pageable pageable) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        List<Interest> interests = user.getUserInterests();
        Page<Party> parties = partyRepository.findInterestParties(interests, userId, pageable);

        return new PageDto<>(parties.getNumber(), parties.getTotalPages(), parties.stream()
                .map(party -> GetInterestPartyRes.builder()
                        .id(party.getId())
                        .name(party.getName())
                        .content(party.getContent())
                        .imageFilePath(party.getImageFilePath())
                        .memberCount(party.getMemberCount())
                        .ownerId(party.getOwnerId())
                        .build())
                .collect(Collectors.toList()));
    }
}