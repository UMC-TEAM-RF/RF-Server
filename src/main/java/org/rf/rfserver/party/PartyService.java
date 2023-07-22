package org.rf.rfserver.party;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.DeletePartyRes;
import org.rf.rfserver.party.dto.GetPartyRes;
import org.rf.rfserver.party.dto.PostPartyReq;
import org.rf.rfserver.party.dto.PostPartyRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyInterestRepository partyInterestRepository;

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
                PartyInterest partyInterest = partyInterestRepository.save(new PartyInterest(party, interest));
                partyInterest.getParty().getInterests().add(partyInterest);
            }
            return new PostPartyRes(party.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPartyRes getGroup(Long groupId) throws BaseException {
        Party party = partyRepository.findById(groupId)
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
                .interests(party.getInterests())
                //.rule(party.getRule())
                //.tags(party.getTags())
                .build();
    }

    public DeletePartyRes deleteParty(Long groupId) throws BaseException {
        Party party = partyRepository.findById(groupId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        for (PartyInterest partyInterest: party.getInterests() ) {
            partyInterestRepository.delete(partyInterest);
        }
        partyRepository.delete(party);
        return DeletePartyRes.builder()
                .id(groupId)
                .build();
    }

}