package org.rf.rfserver.party;

import org.rf.rfserver.domain.PartyInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyInterestRepository extends JpaRepository<PartyInterest, Long> {
}