package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
