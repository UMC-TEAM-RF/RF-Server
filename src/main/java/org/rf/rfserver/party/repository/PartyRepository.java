package org.rf.rfserver.party.repository;

import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.domain.Party;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long>, JpaSpecificationExecutor<Party> {
   @Query("SELECT p FROM Party p WHERE p.id NOT IN (SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = ?1)")
   Page<Party> findNonBlockedPartiesByUserId(Long userId, Pageable pageable);

   // 사용자 관심사 기반 모임 목록 불러오기 (가입한 모임, 차단한 모임 제외)
   @Query("SELECT p FROM Party p JOIN p.interests i " +
           "WHERE i IN :userInterests AND p.id NOT IN (" +
           "SELECT up.party.id FROM UserParty up WHERE up.user.id = :userId" +
           ") AND p.id NOT IN (" +
           "SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = :userId" +
           ")")
   Page<Party> findInterestParties(List<Interest> userInterests, Long userId, Pageable pageable);

}
