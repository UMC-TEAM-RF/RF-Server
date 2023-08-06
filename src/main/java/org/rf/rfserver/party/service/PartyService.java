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
import org.rf.rfserver.party.repository.PartyInterestRepository;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.rf.rfserver.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserPartyRepository userPartyRepository;
    private final PartyInterestRepository partyInterestRepository;
    private final PartyJoinApplicationRepository partyJoinApplicationRepository;

    public PostPartyRes createParty(PostPartyReq postPartyReq) throws BaseException {
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
                    .build());
            for (Interest interest : postPartyReq.getInterests()) {
                PartyInterest partyInterest = partyInterestRepository.save(new PartyInterest(interest, party));
                partyInterest.getParty().getInterests().add(partyInterest);
            }
            return new PostPartyRes(party.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPartyRes getParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
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
                .users(party.getUserParties())
                .schedules(party.getSchedules())
                .interests(party.getInterests())
                //.rule(party.getRule())
                //.tags(party.getTags())
                .build();
    }

    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
        deletePartyInterests(party.getInterests());
        deleteUserParty(party.getUserParties());
        partyRepository.delete(party);
        return new DeletePartyRes(partyId);
    }

    Party findPartyById(Long partyId) throws BaseException {
        return partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(INVALID_PARTY));
    }

    public void deletePartyInterests(List<PartyInterest> partyInterests) { //party를 삭제할때 partyInterest 테이블에서 연관 데이터삭제
        for (PartyInterest partyInterest : partyInterests) {
            partyInterestRepository.delete(partyInterest);
        }
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