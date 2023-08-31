package org.rf.rfserver.redisDomain.partyidUserid.repository;

import org.rf.rfserver.redisDomain.partyidUserid.PartyidUserid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyidUseridRepository extends CrudRepository<PartyidUserid, Long> {
    Optional<PartyidUserid> findByPartyId(Long partyId);
}
