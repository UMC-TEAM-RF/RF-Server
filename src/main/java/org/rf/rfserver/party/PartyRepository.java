package org.rf.rfserver.party;

import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
