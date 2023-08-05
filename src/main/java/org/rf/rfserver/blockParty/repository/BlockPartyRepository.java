package org.rf.rfserver.blockParty.repository;

import org.rf.rfserver.domain.BlockParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockPartyRepository extends JpaRepository<BlockParty, Long> {
}