package org.rf.rfserver.chat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.apns.dto.PushDto;
import org.rf.rfserver.apns.service.ApnsService;
import org.rf.rfserver.chat.dto.ChatDto;
import org.rf.rfserver.chat.repository.ChatRepository;
import org.rf.rfserver.constant.PushNotificationType;
import org.rf.rfserver.device.service.DeviceTokenService;
import org.rf.rfserver.domain.Chat;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Schedule;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.redisDomain.partyidUserid.service.PartyidUseridService;
import org.rf.rfserver.schedule.repository.ScheduleRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.rf.rfserver.constant.MessageType.*;

@RequiredArgsConstructor
@Service
public class ChatPublisher {
    private final ChatRepository chatRepository;
    private final PartyRepository partyRepository;
    private final ScheduleRepository scheduleRepository;
    private final PartyidUseridService partyidUseridService;
    private final DeviceTokenService deviceTokenService;
    private final ApnsService apnsService;
    private final RedisTemplate<String, Object> redisTemplate; // redis 를 사용하기 위한 redisTemplate
    private final ChannelTopic channelTopic;

    /**
     * 송신한 클라언트의 요청 소켓을 받아 해당 채팅의 반환 채팅 정보를 가공해 redis 서비스를 이용해 보내는 메소드
     * @param chatDto
     * @param partyId
     */
    @Transactional
    public void sendMessage(ChatDto chatDto, Long partyId) {
        Chat chat = new Chat(chatDto.getContent(), chatDto.getType(), chatDto.getSpeaker().getUserId(), partyId);
        // 초대, 강퇴의 경우 행위를 당하는 사용자의 정보를 메세지에 담아야 함
        if(chatDto.getType() == INVITE || chatDto.getType() == KICK_OUT) {
            chat.setVictim(chatDto.getVictim().getUserId());
            if(chatDto.getType() == KICK_OUT) {/** 사용자 강퇴 API 서비스와 연결 */}
            if(chatDto.getType() == INVITE) {/** 사용자 초대 API 서비스와 연결 */}
        }
        if(chatDto.getType() == LEAVE) {/** 사용자 나감 API 서비스와 연결 */}
        // 답장의 경우 답장을 할 채팅의 정보를 메세지에 담아야 함
        if(chatDto.getType() == REPLY) {
            chat.setReplyAtChatId(chatDto.getReplyChatId());
        }
        // 스케쥴 메세지의 경우 일정을 생성하여 정보를 메세지에 담아야 함
        if(chatDto.getType() == SCHEDULE) {
            Party party = partyRepository.findById(partyId)
                    .orElseThrow();
            DateTimeFormatter scheduleFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Schedule schedule = Schedule.builder()
                    .scheduleName(chatDto.getSchedule().getScheduleName())
                    .datetime(LocalDateTime.parse(chatDto.getSchedule().getDateTime(), scheduleFormatter))
                    .location(chatDto.getSchedule().getLocation())
                    .participantCount(chatDto.getSchedule().getParticipantCount())
                    .alert(chatDto.getSchedule().getAlert())
                    .party(party)
                    .build();
            Schedule newSchedule = scheduleRepository.save(schedule);
            chat.setSchedule(newSchedule);
            chatDto.getSchedule().setScheduleId(newSchedule.getId());
        }
        Chat newChat = chatRepository.save(chat);
        chatDto.setChatDtoForRes(newChat.getId(), chat.getCreatedAt());
        if(chatDto.getType() == TEXT || chatDto.getType() == IMAGE || chatDto.getType() == SCHEDULE || chatDto.getType() == REPLY) {
            Set<Long> userIds = partyidUseridService.getUserids(partyId);
            for (Long userId : userIds) {
                if(userId == chatDto.getSpeaker().getUserId()) continue;
                String deviceToken = deviceTokenService.getDeviceTokenByUserId(userId);
                if(deviceToken == null) continue;
                if(chatDto.getType() == TEXT || chatDto.getType() == REPLY)
                    apnsService.sendPush(new PushDto(PushNotificationType.CHAT, userId, chatDto.getSpeaker().getUserName(), chatDto.getPartyName(), chat.getContent(),partyId));
                if(chatDto.getType() == IMAGE)
                    apnsService.sendPush(new PushDto(PushNotificationType.CHAT, userId, chatDto.getSpeaker().getUserName(), chatDto.getPartyName(), "이미지를 전송했습니다.",partyId));
                if(chatDto.getType() == SCHEDULE)
                    apnsService.sendPush(new PushDto(PushNotificationType.CHAT, userId, chatDto.getSpeaker().getUserName(), chatDto.getPartyName(), "일정이 생성되었습니다.",partyId));
            }
        }
        // redis를 이용하여 chatroom 토픽에 대한 메세지를 메세지
        redisTemplate.convertAndSend("chat", chatDto); // redis의 "chat" topic을 이용하여 보냄
    }
}
