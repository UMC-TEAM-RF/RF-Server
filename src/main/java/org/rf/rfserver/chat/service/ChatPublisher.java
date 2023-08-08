package org.rf.rfserver.chat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatDto;
import org.rf.rfserver.chat.repository.ChatRepository;
import org.rf.rfserver.domain.Chat;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.schedule.repository.ScheduleRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.rf.rfserver.constant.MessageType.*;

@RequiredArgsConstructor
@Service
public class ChatPublisher {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final ScheduleRepository scheduleRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    @Transactional
    public void sendMessage(ChatDto chatDto, Long partyId) {
        User user = userRepository.findById(chatDto.getSpeaker().getSpeakerId())
                .orElseThrow(); // 해당 사용자 없음
        Party party = partyRepository.findById(partyId)
                .orElseThrow(); // 해당 채팅방 없음
        Chat chat = new Chat(chatDto.getContent(), chatDto.getType(), user, party);
        if(chatDto.getType() == REPLY) {
            chat.setReplyAtChatId(chatDto.getReplyChatId());
        }
        if(chatDto.getType() == SCHEDULE) {
            DateTimeFormatter scheduleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Schedule schedule = Schedule.builder()
                    .scheduleName(chatDto.getSchedule().getScheduleName())
                    .datetime(LocalDateTime.parse(chatDto.getSchedule().getDateTime(), scheduleFormatter))
                    .location(chatDto.getSchedule().getLocation())
                    .participantCount(chatDto.getSchedule().getParticipantCount())
                    .alert(chatDto.getSchedule().getAlert())
                    .party(chat.getParty())
                    .build();
            Schedule newSchedule = scheduleRepository.save(schedule);
            chat.setSchedule(newSchedule);
            chatDto.getSchedule().setScheduleId(newSchedule.getId());
        }
        Chat newChat = chatRepository.save(chat);
        chatDto.setChatDtoForRes(partyId, newChat.getId(), user.getNickName(), user.getImageUrl(), chat.getCreatedAt());
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatDto);
    }
}
