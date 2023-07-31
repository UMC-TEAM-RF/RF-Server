package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPartyRepository extends JpaRepository<UserParty, Long> {
    Optional<UserParty> findByUserAndParty(User user, Party party);
}
