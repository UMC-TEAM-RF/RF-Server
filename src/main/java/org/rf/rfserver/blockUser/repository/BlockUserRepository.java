package org.rf.rfserver.blockUser.repository;

import org.rf.rfserver.domain.BlockUser;
import org.rf.rfserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {
    void deleteByBlockerAndBlocked(User blocker, User blocked);
}
