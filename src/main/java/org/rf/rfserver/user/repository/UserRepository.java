package org.rf.rfserver.user.repository;

import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;
import org.rf.rfserver.user.dto.GetUserProfileRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsUserByLoginId(String loginId);
    public Boolean existsUserByNickName(String nickName);
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginId(String loginId);

    @Query("SELECT NEW org.rf.rfserver.user.dto.GetUserProfileRes(u.nickName, u.imageFilePath, u.country) " +
            "From User u " +
            "WHERE u.id in :userIds")
    List<GetUserProfileRes> getUserProfilesByUserParties(@Param("userIds") List<Long> userIds);
}
