package org.rf.rfserver.party;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.DeletePartyRes;
import org.rf.rfserver.party.dto.GetPartyRes;
import org.rf.rfserver.party.dto.PostPartyReq;
import org.rf.rfserver.party.dto.PostPartyRes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyInterestRepository partyInterestRepository;

    private final S3Uploader s3Uploader;

    public PostPartyRes createParty(PostPartyReq postPartyReq, MultipartFile file) throws BaseException {
        try {
            Party party = Party.builder()
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
                    .build();
//            for (Interest interest : postPartyReq.getInterests()) {
//                PartyInterest partyInterest = partyInterestRepository.save(new PartyInterest(interest, party));
//                partyInterest.getParty().getInterests().add(partyInterest);
//            }

            //file 비어있는지 체크
            if(file != null){
                String imageFilePath = s3Uploader.fileUpload(file, "partyImage");
                party.updateImageUrl(imageFilePath);
            }
            partyRepository.save(party);

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
                .users(party.getUsers())
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
        partyRepository.delete(party);
        return DeletePartyRes.builder()
                .id(partyId)
                .build();
    }

}