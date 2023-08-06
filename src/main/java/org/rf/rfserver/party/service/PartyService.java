package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.party.DeletePartyRes;
import org.rf.rfserver.party.dto.party.GetPartyRes;
import org.rf.rfserver.party.dto.party.PostPartyReq;
import org.rf.rfserver.party.dto.party.PostPartyRes;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinReq;
import org.rf.rfserver.party.dto.partyjoin.PostApproveJoinRes;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinReq;
import org.rf.rfserver.party.dto.partyjoin.PostDenyJoinRes;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationRes;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.dto.GetUserProfileRes;
import org.rf.rfserver.user.dto.GetUserRes;
import org.rf.rfserver.user.repository.UserRepository;
import org.rf.rfserver.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserService userService;
    private final UserPartyRepository userPartyRepository;
    private final PartyJoinApplicationRepository partyJoinApplicationRepository;

    public PostPartyRes createParty(PostPartyReq postPartyReq) throws BaseException {
        try {
            Party party = partyRepository.save(Party.builder()
                    .name(postPartyReq.getName())
                    .content(postPartyReq.getContent())
                    .location(postPartyReq.getLocation())
                    .language(postPartyReq.getLanguage())
                    .imageFilePath(postPartyReq.getImageFilePath())
                    .interests(postPartyReq.getInterests())
                    .preferAges(postPartyReq.getPreferAges())
                    .createdDate(LocalDateTime.now())
                    .memberCount(postPartyReq.getMemberCount())
                    .nativeCount(postPartyReq.getNativeCount())
                    .ownerId(postPartyReq.getOwnerId())
                    .build());
            return new PostPartyRes(party.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
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
                .createdDate(party.getCreatedDate())
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
        User user = userService.findUserById(postJoinApplyReq.getUserId());
        userService.isExceededPartyCount(user);
        Party party = findPartyById(postJoinApplyReq.getPartyId());
        isJoinedUser(user, party);

        PartyJoinApplication partyJoinApplication = new PartyJoinApplication(user, party);
        partyJoinApplicationRepository.save(partyJoinApplication);
        return new PostJoinApplicationRes(partyJoinApplication.getId());
    }

    public void isJoinedUser(User user, Party party) throws BaseException {
        for (UserParty userParty : party.getUserParties() ) {
            if(userParty.getUser() == user) {
                throw new BaseException(INVALID_PARTY);
            }
        }
    }

    public PostApproveJoinRes approveJoin(PostApproveJoinReq postApproveJoinReq) throws BaseException {
        User user = userService.findUserById(postApproveJoinReq.getUserId());
        Party party = findPartyById(postApproveJoinReq.getPartyId());
        UserParty userParty = new UserParty(party, user);
        userParty.setParty(party);
        userParty.setUser(user);
        userPartyRepository.save(userParty);
        deletePartyJoinApplication(postApproveJoinReq.getPartyJoinApplicationId());
        return new PostApproveJoinRes(postApproveJoinReq.getPartyJoinApplicationId());
    }

    public PostDenyJoinRes denyJoin(PostDenyJoinReq postDenyJoinReq) throws BaseException {
        deletePartyJoinApplication(postDenyJoinReq.getPartyJoinApplicationId());
        return new PostDenyJoinRes(postDenyJoinReq.getPartyJoinApplicationId());
    }

    public void deletePartyJoinApplication(Long partyJoinApplicationId) throws BaseException {
        partyJoinApplicationRepository.delete(partyJoinApplicationRepository.findById(partyJoinApplicationId)
                .orElseThrow(() -> new BaseException(INVALID_JOIN_APPLICATION)));
    }

}