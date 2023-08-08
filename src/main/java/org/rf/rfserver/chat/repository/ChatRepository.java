package org.rf.rfserver.chat.repository;

import jakarta.transaction.Transactional;
import org.rf.rfserver.domain.Chat;
import org.rf.rfserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN FETCH UserParty up ON c.party = up.party WHERE c.id > :chatId AND up.user = :user")
    List<Chat> findChatsByIdAfterAndUserPartiesContainUser(Long chatId, User user);
}
