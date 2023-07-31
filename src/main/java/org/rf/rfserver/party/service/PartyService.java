package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.*;
import org.rf.rfserver.party.repository.PartyInterestRepository;
import org.rf.rfserver.party.repository.PartyJoinApplyRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final UserPartyRepository userPartyRepository;
    private final PartyInterestRepository partyInterestRepository;
    private final PartyJoinApplyRepository partyJoinApplyRepository;

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
                    //      .rule(postPartyReq.getRules())
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
                .users(party.getUserParties())
                .schedules(party.getSchedules())
                .interests(party.getInterests())
                //.rule(party.getRule())
                //.tags(party.getTags())
                .build();
    }

    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        for (PartyInterest partyInterest: party.getInterests() ) {
            partyInterestRepository.delete(partyInterest);
        }
        for (UserParty userParty: party.getUserParties()) {
            userPartyRepository.delete(userParty);
        }
        partyRepository.delete(party);
        return DeletePartyRes.builder()
                .id(partyId)
                .build();
    }

    public void deleteUserParty(UserParty userParty) {

    }

    public PostPartyJoinRes joinParty(PostPartyJoinReq postPartyJoinReq) throws BaseException {
        User user = userRepository.findById(postPartyJoinReq.getUserId())
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        Party party = partyRepository.findById(postPartyJoinReq.getPartyId())
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        UserParty userParty = new UserParty(party, user);
        userParty.setParty(party);
        userParty.setUser(user);
        userPartyRepository.save(userParty);
        return new PostPartyJoinRes(userParty.getId());
    }

    public PostPartyJoinApplyRes partyJoinApply(PostPartyJoinApplyReq postPartyJoinApplyReq) throws BaseException {
        User user = userRepository.findById(postPartyJoinApplyReq.getUserId())
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        Party party = partyRepository.findById(postPartyJoinApplyReq.getPartyId())
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        PartyJoinApply partyJoinApply = new PartyJoinApply(user, party);
        partyJoinApplyRepository.save(partyJoinApply);
        return new PostPartyJoinApplyRes(partyJoinApply.getId());
    }

}