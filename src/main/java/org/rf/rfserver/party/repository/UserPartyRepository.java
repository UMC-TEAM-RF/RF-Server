package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserPartyRepository extends JpaRepository<UserParty, Long> {
    Optional<UserParty> findByUserAndParty(User user, Party party);
    Page<UserParty> findUserPartiesByUserId(Long userId, Pageable pageable);
    @Modifying
    @Query("DELETE From UserParty up " +
            "WHERE up.id in :userPartyIds")
    void deleteUserParties(@Param("userPartyIds") List<Long> userPartyIds);
    List<UserParty> findByUser(User user);
}
