package org.rf.rfserver.chat.repository;

import org.rf.rfserver.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
