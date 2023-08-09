package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.Party;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long>, JpaSpecificationExecutor<Party> {
   @Query("SELECT p FROM Party p WHERE p.id NOT IN (SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = ?1)")
   List<Party> findNonBlockedPartiesByUserId(Long userId, Pageable pageable);
}
