package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.apns.dto.PushDto;
import org.rf.rfserver.apns.service.ApnsService;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.Banner;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.PushNotificationType;
import org.rf.rfserver.constant.PreferAges;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.favoriteparty.FavoritePartyReq;
import org.rf.rfserver.party.dto.favoriteparty.FavoritePartyRes;
import org.rf.rfserver.party.dto.party.*;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationRes;
import org.rf.rfserver.party.repository.FavoritePartyRepository;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.redisDomain.partyidUserid.service.PartyidUseridService;
import org.rf.rfserver.user.dto.GetUserProfileRes;
import org.rf.rfserver.user.repository.UserRepository;
import org.rf.rfserver.user.service.UserService;

import org.rf.rfserver.config.PageDto;

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
    private final PartyidUseridService partyidUseridService;
    private final ApnsService apnsService;
    private final FavoritePartyRepository favoritePartyRepository;


    public PostPartyRes createParty(PostPartyReq postPartyReq, MultipartFile file) throws BaseException {
        try {
            User user = userService.findUserById(postPartyReq.getOwnerId());
            Party party = Party.builder()
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
                    .build();
            addOwnerToParty(user, party);

            //file 비어있는지 체크
            String imageFilePath;
            if(file != null){
                imageFilePath = s3Uploader.fileUpload(file, "partyImage");
                party.updateImageUrl(imageFilePath);
            }
            if (file == null){
                imageFilePath = s3Uploader.getImageFilePath(Banner.getRandomBanner().getUrl());
                party.updateImageUrl(imageFilePath);
            }
            partyRepository.save(party);
            return new PostPartyRes(party.getId(), postPartyReq, party.getImageFilePath());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void addOwnerToParty(User user, Party party) throws BaseException {
        makeUserParty(user, party);
        if (userService.isKorean(user)) {
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
                .isRecruiting(party.getIsRecruiting())
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

    public PatchPartyRes updateParty(Long partyId, PatchPartyReq patchPartyReq) throws BaseException {
        Party party = findPartyById(partyId);
        party.updateParty(patchPartyReq);
        return new PatchPartyRes(true);
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
        joinValidation(party, user);
        PartyJoinApplication partyJoinApplication = new PartyJoinApplication(user, party);
        partyJoinApplicationRepository.save(partyJoinApplication);
        sendJoinApplyPush(party.getOwnerId(), user.getNickName(), party.getId(), party.getName());
        return new PostJoinApplicationRes(partyJoinApplication.getId());
    }


    public void joinValidation(Party party, User user) throws BaseException {
        isRecruiting(party);
        isFullParty(party);
        if (userService.isKorean(user)) {
            if (isFullOfKorean(party)) {
                throw new BaseException(FULL_OF_KOREAN);
            }
        }
        userService.isExceededPartyCount(user);
        isJoinedUser(user, party);
    }


    public Boolean isFullParty(Party party) {
        if (party.getUsers().size() >= party.getMemberCount()) {
            return true;
        }
        return false;
    }

    public Boolean isFullOfKorean(Party party) {
        if(party.getNativeCount() <= party.getCurrentNativeCount()) {
            return true;
        }
       return false;
    }

    public void isJoinedUser(User user, Party party) throws BaseException {
        for (UserParty userParty : party.getUsers()) {
            if (userParty.getUser() == user) {
                throw new BaseException(INVALID_JOIN_APPLICATION);
            }
        }
    }

    public PostApproveJoinRes approveJoin(Long partyJoinApplicationId) throws BaseException {
        PartyJoinApplication partyJoinApplication = partyJoinApplicationRepository.findById(partyJoinApplicationId)
                .orElseThrow(() -> new BaseException(INVALID_APPLICATION));
        User user = userService.findUserById(partyJoinApplication.getUser().getId());
        Party party = findPartyById(partyJoinApplication.getParty().getId());
        joinValidation(party, user);
        makeUserParty(user, party);
        if (userService.isKorean(user)) {
            party.plusCurrentNativeCount();
        }
        if (isFullParty(party)) {
            party.changeRecruitmentState(false);
        }
        deletePartyJoinApplication(partyJoinApplicationId);
        sendApproveJoinPush(user.getId(), user.getNickName(), party.getId(), party.getName());
        return new PostApproveJoinRes(partyJoinApplicationId);
    }

    public void makeUserParty(User user, Party party) {
        UserParty userParty = new UserParty(party, user);
        userPartyRepository.save(userParty);
        partyidUseridService.setPartyidUserid(party.getId(), user.getId());
    }

    public PostDenyJoinRes denyJoin(Long partyJoinApplicationId) throws BaseException {
        PartyJoinApplication partyJoinApplication = partyJoinApplicationRepository.findById(partyJoinApplicationId)
                .orElseThrow();
        sendDenyJoinPush(partyJoinApplication.getUser().getId(), partyJoinApplication.getUser().getNickName(), partyJoinApplication.getParty().getId(), partyJoinApplication.getParty().getName());
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

        // redis partyIduserId 데이터베이스에서 userId 제거
        partyidUseridService.deleteUseridFromPartyid(partyId, userId);

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
                        .userProfiles(userService.getUserProfiles(party.getUsers()))
                        .schedules(party.getSchedules())
                        .build())
                .collect(Collectors.toList()));
    }

    /**
     * 클라이언트가 속한 그룹 리스트를 조회 서비스
     *
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
                        .userProfiles(userService.getUserProfiles(userParty.getParty().getUsers()))
                        .schedules(userParty.getParty().getSchedules())
                        .build())
                .collect(Collectors.toList()));
    }

    public TogglePartyRecruitmentRes togglePartyRecruitment(Long partyId) throws BaseException {
        Party party = findPartyById(partyId);
        if (party.getIsRecruiting()) {
            party.changeRecruitmentState(false);
        } else if (!party.getIsRecruiting()) {
            party.changeRecruitmentState(true);
        }
        return new TogglePartyRecruitmentRes(party.getIsRecruiting());
    }


    public void isRecruiting(Party party) throws BaseException {
        if (!party.getIsRecruiting()) {
            throw new BaseException(NOT_RECRUITING);
        }
    }

    // 사용자 관심사 기반 [단체 모임] 목록 불러오기 (가입한 모임, 차단한 모임 제외 / 같은 대학교 유저가 생성한 모임만 조회 가능)
    public PageDto<List<GetInterestPartyRes>> recommendGroupParties(Long userId, Pageable pageable) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        List<Interest> interests = user.getUserInterests();
        Page<Party> parties = partyRepository.recommendGroupParties(interests, userId, pageable);

        return new PageDto<>(parties.getNumber(), parties.getTotalPages(), parties.stream()
                .map(party -> GetInterestPartyRes.builder()
                        .id(party.getId())
                        .name(party.getName())
                        .content(party.getContent())
                        .imageFilePath(party.getImageFilePath())
                        .memberCount(party.getMemberCount())
                        .ownerId(party.getOwnerId())
                        .interests(party.getInterests())
                        .currentMemberCount(party.getUsers().size())
                        .build())
                .collect(Collectors.toList()));
    }

    public void sendJoinApplyPush(Long userId, String userName, Long partyId, String partyName) {
        apnsService.sendPush(new PushDto(PushNotificationType.APPLY, userId, PushNotificationType.APPLY.getValue(), partyName, userName + "친구가 가입을 원해요.", partyId));
    }

    public void sendApproveJoinPush(Long userId, String userName, Long partyId, String partyName) {
        apnsService.sendPush(new PushDto(PushNotificationType.APPROVE, userId, PushNotificationType.APPROVE.getValue(), partyName, userName + "친구, 가입을 환영해요.", partyId));
    }
    public void sendDenyJoinPush(Long userId, String userName, Long partyId, String partyName) {
        apnsService.sendPush(new PushDto(PushNotificationType.DENY, userId, PushNotificationType.DENY.getValue(), partyName, userName + "친구, 다음 기회에 만나요.", partyId));
    }
    // 모임 검색 + 필터링
    public PageDto<List<GetPartyRes>> searchPartyByFilter(
            Long userId, String name, Boolean isRecruiting, PreferAges preferAges,
            Integer partyMembersOption, List<Interest> interests,
            Pageable pageable) throws BaseException {

        Page<Party> parties = partyRepository.searchPartyByFilter(
                userId, name, isRecruiting, preferAges,
                partyMembersOption, interests == null ? null : interests.size(), interests,
                pageable);

        return new PageDto<>(parties.getNumber(), parties.getTotalPages(), parties.stream()
                .map(party -> GetPartyRes.builder()
                        .id(party.getId())
                        .name(party.getName())
                        .content(party.getContent())
                        .location(party.getLocation())
                        .isRecruiting(party.getIsRecruiting())
                        .language(party.getLanguage())
                        .imageFilePath(party.getImageFilePath())
                        .preferAges(party.getPreferAges())
                        .memberCount(party.getMemberCount())
                        .nativeCount(party.getNativeCount())
                        .ownerId(party.getOwnerId())
                        .schedules(party.getSchedules())
                        .interests(party.getInterests())
                        .userProfiles(userService.getUserProfiles(party.getUsers()))
                        .schedules(party.getSchedules())
                        .interests(party.getInterests())
                        .rules(party.getRules())
                        .build())
                .collect(Collectors.toList()));
    }

    // 사용자 관심사 기반 [1:1(개인)] 모임 목록 불러오기 (가입한 모임, 차단한 모임 제외 / 같은 대학교 유저가 생성한 모임만 조회 가능)
    public PageDto<List<GetInterestPartyRes>> recommendPersonalParties(Long userId, Pageable pageable) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        List<Interest> interests = user.getUserInterests();
        Page<Party> parties = partyRepository.recommendPersonalParties(interests, userId, pageable);

        return new PageDto<>(parties.getNumber(), parties.getTotalPages(), parties.stream()
                .map(party -> GetInterestPartyRes.builder()
                        .id(party.getId())
                        .name(party.getName())
                        .content(party.getContent())
                        .imageFilePath(party.getImageFilePath())
                        .memberCount(party.getMemberCount())
                        .ownerId(party.getOwnerId())
                        .interests(party.getInterests())
                        .currentMemberCount(party.getUsers().size())
                        .build())
                .collect(Collectors.toList()));
    }

    public EjectUserRes ejectUser(EjectUserReq ejectUserReq) throws BaseException {
        isOwner(ejectUserReq.getOwnerId(), ejectUserReq.getPartyId());
        leaveParty(ejectUserReq.getUserId(), ejectUserReq.getPartyId());
        // redis partyIduserId 데이터베이스에서 userId 제거
        partyidUseridService.deleteUseridFromPartyid(ejectUserReq.getPartyId(), ejectUserReq.getUserId());
        return new EjectUserRes(true);
    }

    public void isOwner(Long ownerId, Long partyId) throws BaseException {
        if(findPartyById(partyId).getOwnerId() != ownerId) {
            throw new BaseException(NOT_OWNER);
        }
    }
}