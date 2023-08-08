package org.rf.rfserver.chat.service;

import lombok.AllArgsConstructor;
import org.rf.rfserver.chat.dto.ChatDto;
import org.rf.rfserver.chat.dto.GetChatsReq;
import org.rf.rfserver.chat.repository.ChatRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Chat;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.rf.rfserver.config.BaseResponseStatus.NO_SUCH_USER;

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    public List<ChatDto> getChats(GetChatsReq getChatsReq) throws BaseException {
        User user = userRepository.findById(getChatsReq.getUserId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        List<Chat> chats = chatRepository.findChatsByIdAfterAndUserPartiesContainUser(getChatsReq.getChatId(), user);
        return chats.stream()
                .map(chat -> new ChatDto(chat))
                .collect(Collectors.toList());
    }

    /**
     * 3일이 지난 채팅들을 데이터베이스에서 제거하는 서비스 메소드
     */
    @Scheduled(cron = "0 0 0/6 * * *")
    public void expireChats() {
        chatRepository.deleteChatsByCreatedAtBefore(LocalDateTime.now().minusDays(3));
    }
}
