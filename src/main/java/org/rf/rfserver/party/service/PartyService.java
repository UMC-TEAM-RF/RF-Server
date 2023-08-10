package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.party.DeletePartyRes;
import org.rf.rfserver.party.dto.party.GetPartyRes;
import org.rf.rfserver.party.dto.party.PostPartyReq;
import org.rf.rfserver.party.dto.party.PostPartyRes;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationRes;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.dto.GetUserProfileRes;
import org.rf.rfserver.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserService userService;
    private final UserPartyRepository userPartyRepository;
    private final PartyJoinApplicationRepository partyJoinApplicationRepository;
    private final S3Uploader s3Uploader;

    public PostPartyRes createParty(PostPartyReq postPartyReq, MultipartFile file) throws BaseException {
        try {
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
                    .build());
            addOwnerToParty(party.getOwnerId(), party);
            if(file != null){
                String imageFilePath = s3Uploader.fileUpload(file, "partyImage");
                party.updateImageUrl(imageFilePath);
            }
            return new PostPartyRes(party.getId(), postPartyReq);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void addOwnerToParty(Long ownerId, Party party) throws BaseException {
        User user = userService.findUserById(ownerId);
        UserParty userParty = new UserParty(party, user);
        userPartyRepository.save(userParty);
        userParty.setUser(user);
        userParty.setParty(party);
        if(userService.isKorean(user)) {
            party.plusCurrentNativeCount();
        }
    }

    public List<GetUserProfileRes> getUserProfiles(List<UserParty> userParties) {
        List<GetUserProfileRes> userProfiles = new ArrayList<>();
        for (UserParty userParty : userParties) {
            User user = userParty.getUser();
            userProfiles.add(new GetUserProfileRes(user.getNickName(), user.getImageFilePath(), user.getCountry().name()));
        }
        return userProfiles;
    }

    public GetPartyRes getParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
        List<GetUserProfileRes> userProfiles = getUserProfiles(party.getUserParties());
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
                .rules(party.getRules())
                .build();
    }

    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
        deleteUserParty(party.getUserParties());
        partyRepository.delete(party);
        return new DeletePartyRes(partyId);
    }

    Party findPartyById(Long partyId) throws BaseException {
        return partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
    }

    public void deleteUserParty(List<UserParty> userParties) {
        for (UserParty userParty : userParties) {
            userParty.getUser().getUserParties().remove(userParty);
            userPartyRepository.delete(userParty);
        }
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
        if (party.getUserParties().size() >= party.getMemberCount()) {
            throw new BaseException(EXCEEDED_PARTY_USER_COUNT);
        }
    }

    public void isFullOfKorean(Party party) throws BaseException {
        if(party.getNativeCount() <= party.getCurrentNativeCount()) {
            throw new BaseException(FULL_OF_KOREAN);
        }
    }


    public void isJoinedUser(User user, Party party) throws BaseException {
        for (UserParty userParty : party.getUserParties() ) {
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
        userParty.setParty(party);
        userParty.setUser(user);
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

}