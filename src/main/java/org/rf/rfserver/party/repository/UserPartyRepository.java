package org.rf.rfserver.party.repository;

import org.rf.rfserver.domain.UserParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPartyRepository extends JpaRepository<UserParty, Long> {

    @Modifying
    @Query("DELETE From UserParty up " +
            "WHERE up.id in :userPartyIds")
    void deleteUserParties(List<Long> userPartyIds);
}
