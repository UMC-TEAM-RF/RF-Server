package org.rf.rfserver.party.repository;

import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.domain.Party;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long>, JpaSpecificationExecutor<Party> {
   // 모임 불러오기 (차단 제외 / 같은 대학교 유저가 생성한 모임만 조회 가능)
   @Query("SELECT p FROM Party p " +
           "JOIN User u ON p.ownerId = u.id " +
           "WHERE u.university IN " +
           "(SELECT owner.university FROM User owner WHERE owner.id = :userId) " +
           "AND p.id NOT IN " +
           "(SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = :userId)")
   Page<Party> findNonBlockedPartiesByUserId(@Param("userId") Long userId, Pageable pageable);

   // 사용자 관심사 기반 모임 목록 불러오기 (가입한 모임, 차단한 모임 제외 / 같은 대학교 유저가 생성한 모임만 조회 가능)
   @Query("SELECT p FROM Party p " +
           "JOIN User u ON p.ownerId = u.id JOIN p.interests i " +
           "WHERE u.university IN " +
           "(SELECT owner.university FROM User owner WHERE owner.id = :userId) " +
           "AND i IN :userInterests " +
           "AND p.id NOT IN (" +
           "SELECT up.party.id FROM UserParty up WHERE up.user.id = :userId" +
           ") " +
           "AND p.id NOT IN (" +
           "SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = :userId" +
           ")")
   Page<Party> findInterestParties(List<Interest> userInterests, Long userId, Pageable pageable);
}
