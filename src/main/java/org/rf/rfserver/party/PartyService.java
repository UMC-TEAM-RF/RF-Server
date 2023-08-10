package org.rf.rfserver.party;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.dto.DeletePartyRes;
import org.rf.rfserver.party.dto.GetPartyRes;
import org.rf.rfserver.party.dto.PostPartyReq;
import org.rf.rfserver.party.dto.PostPartyRes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import static org.rf.rfserver.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
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
                    .memberCount(postPartyReq.getMemberCount())
                    .nativeCount(postPartyReq.getNativeCount())
                    .ownerId(postPartyReq.getOwnerId())
                    .rules(postPartyReq.getRules())
                    .interests(postPartyReq.getInterests())
                    .build();
          
            //file 비어있는지 체크
            if(file != null){
                String imageFilePath = s3Uploader.fileUpload(file, "partyImage");
                party.updateImageUrl(imageFilePath);
            } else { //파일이 들어오지 않으면 ImageUrl에 null 할당
                party.updateImageUrl(null);
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
                .memberCount(party.getMemberCount())
                .nativeCount(party.getNativeCount())
                .ownerId(party.getOwnerId())
                .rules(party.getRules())
                .interests(party.getInterests())
                .schedules(party.getSchedules())
                .users(party.getUsers())
                .build();
    }

    public DeletePartyRes deleteParty(Long partyId) throws BaseException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(REQUEST_ERROR));
        partyRepository.delete(party);
        return DeletePartyRes.builder()
                .id(partyId)
                .build();
    }

}