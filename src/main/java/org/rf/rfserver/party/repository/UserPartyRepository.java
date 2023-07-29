package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPartyRepository extends JpaRepository<UserParty, Long> {
}
