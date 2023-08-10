package org.rf.rfserver.user.repository;

import org.rf.rfserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsUserByLoginId(String loginId);
    public Boolean existsUserByNickName(String nickName);
}
