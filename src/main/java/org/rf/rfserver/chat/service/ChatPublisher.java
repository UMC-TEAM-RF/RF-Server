package org.rf.rfserver.chat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatReq;
import org.rf.rfserver.chat.dto.ChatRes;
import org.rf.rfserver.chat.repository.ChatRepository;
import org.rf.rfserver.domain.Chat;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.User;

import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatPublisher {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    @Transactional
    public void sendMessage(ChatReq chatReq) {
        User user = userRepository.findById(chatReq.getUserId())
                .orElseThrow(); // 해당 사용자 없음

        Party party = partyRepository.findById(chatReq.getPartyId())
                .orElseThrow(); // 해당 채팅방 없음
        Chat chat = new Chat(chatReq.getContent(), chatReq.getType(), user, party);
        chatRepository.save(chat);
        ChatRes chatRes = new ChatRes(chat.getId(), chatReq);
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatRes);
    }
}
