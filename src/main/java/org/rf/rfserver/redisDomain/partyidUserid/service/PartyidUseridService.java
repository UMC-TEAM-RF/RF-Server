package org.rf.rfserver.redisDomain.partyidUserid.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.redisDomain.partyidUserid.PartyidUserid;
import org.rf.rfserver.redisDomain.partyidUserid.repository.PartyidUseridRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PartyidUseridService {
    private final PartyidUseridRepository partyidUseridRepository;
    public Boolean setPartyidUserid(Long partyId, Long userId) {
        PartyidUserid partyidUserid = partyidUseridRepository.findById(partyId)
                .orElse(new PartyidUserid(partyId, new HashSet<>()));
        partyidUserid.getUserIds().add(userId);
        partyidUseridRepository.save(partyidUserid);
        return true;
    }
    public Set<Long> getUserids(Long partyId){
        PartyidUserid partyidUserid = partyidUseridRepository.findById(partyId)
                .orElseThrow();
        return partyidUserid.getUserIds();
    }
    @Transactional
    public Boolean deleteUseridFromPartyid(Long partyId, Long userId) {
        PartyidUserid partyidUserid = partyidUseridRepository.findById(partyId)
                .orElseThrow();
        Set<Long> userIds = partyidUserid.getUserIds();
        userIds.remove(userId);
        if(userIds.size() > 0) {
            partyidUserid.setUserIds(userIds);
            partyidUseridRepository.save(partyidUserid);
        } else
            partyidUseridRepository.deleteById(partyId);
        return true;
    }
}
