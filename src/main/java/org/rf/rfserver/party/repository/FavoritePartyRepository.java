package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.FavoriteParty;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritePartyRepository extends JpaRepository<FavoriteParty, Long> {
    Optional<FavoriteParty> findByUserAndParty(User user, Party party);
}
