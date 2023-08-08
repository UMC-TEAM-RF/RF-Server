package org.rf.rfserver.party.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.config.BaseResponseStatus;
import org.rf.rfserver.constant.*;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.PartyJoinApplication;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.rf.rfserver.user.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.rf.rfserver.config.BaseResponseStatus.*;
import static org.rf.rfserver.constant.University.INHA;

@ExtendWith(MockitoExtension.class)
class PartyServiceTest {
    @InjectMocks
    PartyService partyService;
    @Mock
    PartyRepository partyRepository;
    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PartyJoinApplicationRepository partyJoinApplicationRepository;

    User user = User.builder()
            .loginId("umc1234")
            .password("1234")
            .entrance(2020)
            .university(INHA)
            .nickName("inha")
            .country(Country.KOREA)
            .introduce("나는 항상 배고파")
            .mbti(Mbti.ISFJ)
            .email("hi@naver.com")
            .lifeStyle(LifeStyle.MORNING_HUMAN)
            .build();

    Party party = Party.builder()
            .name("짜장 미친자 모임")
            .content("짜장 같이 먹어요")
            .location("인하대 후문")
            .language(Language.KOREAN)
            .imageFilePath("default")
            .preferAges(PreferAges.EARLY_TWENTIES)
            .memberCount(5)
            .nativeCount(2)
            .ownerId(1L)
            .build();

    @DisplayName("isKorean 성공")
    @Test
    void isKorean_true() throws BaseException {
        Mockito.when(userService.findUserById(1L))
                .thenReturn(user);
        assertThat(partyService.isKorean(userService.findUserById(1L))).isEqualTo(true);
    }

    @DisplayName("isKorean 실패")
    @Test
    void isKorean_false() throws BaseException {
        Mockito.when(userService.findUserById(2L))
                .thenThrow(new BaseException(INVALID_USER));

        BaseException e = assertThrows(BaseException.class,
                () -> partyService.isKorean(2L));
        assertThat(e.getStatus()).isEqualTo(INVALID_USER);
    }

    @Test
    void addOwnerToParty() {
    }

    @Test
    void getUserProfiles() {
    }

    @Test
    void getParty() {
    }

    @Test
    void deleteParty() {
    }

    @Test
    void findPartyById() {
    }

    @Test
    void deleteUserParty() {
    }

    @Test
    void joinApply() {

    }

    @Test
    void isFullParty() {
    }

    @Test
    void testIsKorean() {
    }

    @Test
    void isFullOfKorean() {
    }

    @Test
    void isJoinedUser() {
    }

    @Test
    void approveJoin() {
    }

    @Test
    void denyJoin() {
    }

    @Test
    void deletePartyJoinApplication() {
    }
}