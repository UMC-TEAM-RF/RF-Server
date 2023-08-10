package org.rf.rfserver.party;

import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPartyRepository extends JpaRepository<UserParty, Long> {
    List<UserParty> findByUser(User user);
}
