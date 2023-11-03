package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.PartyJoinApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyJoinApplicationRepository extends JpaRepository<PartyJoinApplication, Long> {
    @Query("SELECT pja FROM PartyJoinApplication pja JOIN FETCH pja.user WHERE pja.party.id = :partyId")
    List<PartyJoinApplication> findPartyJoinApplicationByParty(@Param("partyId") Long partyId);
}
