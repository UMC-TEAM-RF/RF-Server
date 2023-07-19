package org.rf.rfserver.party;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.party.dto.DeletePartyRes;
import org.rf.rfserver.party.dto.GetPartyRes;
import org.rf.rfserver.party.dto.PostPartyReq;
import org.rf.rfserver.party.dto.PostPartyRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;

    public PostPartyRes createParty(PostPartyReq postPartyReq) throws BaseException {
        try {
            return new PostPartyRes(
                    partyRepository.save(Party.builder()
                            .name(postPartyReq.getName())
                            .content(postPartyReq.getContent())
                            .location(postPartyReq.getLocation())
                            .language(postPartyReq.getLanguage())
                            .imageFilePath(postPartyReq.getImageFilePath())
                            .createdDate(LocalDateTime.now())
                            .memberCount(postPartyReq.getMemberCount())
                            .nativeCount(postPartyReq.getNativeCount())
                            .ownerId(postPartyReq.getOwnerId())
//                            .rule(postPartyReq.getRules())
//                            .partyPartyInterests(postPartyReq.getGroupGroupInterests())
                            .build()).getId()
            );
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
//                .rule(party.getRule())
//                .groupGroupInterests(party.getPartyPartyInterests())
//                .tags(party.getTags())
                .schedules(party.getSchedules())
                .build();
    }

    public DeletePartyRes deleteParty(Long groupId) throws BaseException {
        Party group = partyRepository.findById(groupId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        partyRepository.delete(group);
        return DeletePartyRes.builder()
                .id(groupId)
                .build();
    }

}