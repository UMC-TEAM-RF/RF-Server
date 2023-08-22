package org.rf.rfserver.party.repository;

import org.rf.rfserver.constant.Interest;
import org.rf.rfserver.constant.PreferAges;
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

   // 사용자 관심사 기반 [단체 모임] 목록 불러오기 (가입한 모임, 차단한 모임 제외 / 같은 대학교 유저가 생성한 모임만 조회 가능)
   @Query("SELECT p FROM Party p " +
           "JOIN User u ON p.ownerId = u.id " +
           "JOIN p.interests i " +
           "WHERE u.university IN " +
           "(SELECT owner.university FROM User owner WHERE owner.id = :userId) " +
           "AND p.memberCount >= 3 " +
           "AND i IN :userInterests " +
           "AND p.id NOT IN (" +
           "SELECT up.party.id FROM UserParty up WHERE up.user.id = :userId" +
           ") " +
           "AND p.id NOT IN (" +
           "SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = :userId" +
           ")")
   Page<Party> recommendGroupParties(List<Interest> userInterests, Long userId, Pageable pageable);

   // 모임 검색 + 필터링
   @Query("SELECT p FROM Party p " +
           "JOIN User u ON p.ownerId = u.id " +
           "JOIN FETCH p.interests i " +
           "WHERE u.university IN " +
           "(SELECT owner.university FROM User owner WHERE owner.id = :userId) " +
           "AND (:name IS NULL OR (:name IS NOT NULL AND p.name LIKE %:name%)) " +
           "AND (:isRecruiting IS NULL OR p.isRecruiting = :isRecruiting) " +
           "AND (:preferAges IS NULL OR p.preferAges = :preferAges) " +
           "AND (" +
           "(:partyMembersOption = 1 AND p.memberCount = 2) " +
           "OR (:partyMembersOption = 2 AND p.memberCount >= 3) " +
           "OR :partyMembersOption IS NULL" +
           ") " +
           "AND (:interestsSize IS NULL OR i IN :interests) " +
           "AND p.id NOT IN (" +
           "SELECT up.party.id FROM UserParty up WHERE up.user.id = :userId" +
           ") " +
           "AND p.id NOT IN (" +
           "SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = :userId" +
           ")"
   )
   Page<Party> searchPartyByFilter(
           @Param("userId") Long userId,
           @Param("name") String name,
           @Param("isRecruiting") Boolean isRecruiting,
           @Param("preferAges") PreferAges preferAges,
           @Param("partyMembersOption") Integer partyMembersOption,
           @Param("interestsSize") Integer interestsSize,
           @Param("interests") List<Interest> interests,
           Pageable pageable);

   // 사용자 관심사 기반 [1:1(개인)] 모임 목록 불러오기 (가입한 모임, 차단한 모임 제외 / 같은 대학교 유저가 생성한 모임만 조회 가능)
   @Query("SELECT p FROM Party p " +
           "JOIN User u ON p.ownerId = u.id " +
           "JOIN p.interests i " +
           "WHERE u.university IN " +
           "(SELECT owner.university FROM User owner WHERE owner.id = :userId) " +
           "AND p.memberCount = 2 " +
           "AND i IN :userInterests " +
           "AND p.id NOT IN (" +
           "SELECT up.party.id FROM UserParty up WHERE up.user.id = :userId" +
           ") " +
           "AND p.id NOT IN (" +
           "SELECT bp.blockedParty.id FROM BlockParty bp WHERE bp.blockerUser.id = :userId" +
           ")")
   Page<Party> recommendPersonalParties(List<Interest> userInterests, Long userId, Pageable pageable);
}