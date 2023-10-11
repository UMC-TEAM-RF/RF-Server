package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.PartyJoinApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyJoinApplicationRepository extends JpaRepository<PartyJoinApplication, Long> {
    List<PartyJoinApplication> findPartyJoinApplicationByParty(Party party);
}
