package org.rf.rfserver.party.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.config.BaseResponseStatus;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.*;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.PartyJoinApplication;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.dto.partyjoinapply.PostJoinApplicationReq;
import org.rf.rfserver.party.repository.PartyJoinApplicationRepository;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.party.repository.UserPartyRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.rf.rfserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.rf.rfserver.constant.University.INHA;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PartyServiceTest {

    @InjectMocks
    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PartyJoinApplicationRepository partyJoinApplicationRepository;
    @Mock
    private S3Uploader s3Uploader;

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

    @Order(1)
    @Test
    void addOwnerToParty() throws BaseException {
        partyService.addOwnerToParty(1L, party);
        assertThat(party.getUserParties().get(0).getUser().getId()).isEqualTo(1L);
        assertThat(party.getCurrentNativeCount()).isEqualTo(1);
    }


    @Order(2)
    @DisplayName("id로 party찾기 성공")
    @Test
    void findPartyById_success() throws BaseException {
        assertThat(partyService.findPartyById(2L).getId()).isEqualTo(2L);
    }

    @Order(3)
    @DisplayName("id로 party찾기 실패")
    @Test
    void findPartyById_fail() {
        BaseException e = assertThrows(BaseException.class,
                () -> partyService.findPartyById(4L));
        assertThat(e.getStatus()).isEqualTo(BaseResponseStatus.INVALID_PARTY);
    }

    @Order(4)
    @DisplayName("가입 신청 성공")
    @Test
    void joinApplyTest_success() throws BaseException {
        PostJoinApplicationReq postJoinApplicationReq = new PostJoinApplicationReq(2L, 1l);
        partyService.joinApply(postJoinApplicationReq);
        assertThat(partyJoinApplicationRepository.findById(1L).get().getUser().getId()).isEqualTo(2L);
    }

    @Order(5)
    @DisplayName("가입 신청 실패_인원_초과")
    @Test
    void joinApplyTest_1() throws BaseException {
        Party party1 = Party.builder()
                .name("혼자만 있고싶어요")
                .content("오지 마세요")
                .location("인하대 후문")
                .language(Language.KOREAN)
                .imageFilePath("default")
                .preferAges(PreferAges.EARLY_TWENTIES)
                .memberCount(1)
                .nativeCount(2)
                .ownerId(3L)
                .build();
        partyRepository.save(party1);
        partyService.addOwnerToParty(2L, party1);
        PostJoinApplicationReq postJoinApplicationReq = new PostJoinApplicationReq(4L, 6L);
        BaseException e = assertThrows(BaseException.class,
                () ->  partyService.joinApply(postJoinApplicationReq));
        assertThat(e.getStatus()).isEqualTo(BaseResponseStatus.EXCEEDED_PARTY_USER_COUNT);
    }

    @Transactional
    @Order(6)
    @DisplayName("가입 신청 실패_한국인_초과")
    @Test
    void joinApplyTest_2() throws BaseException {
        Party party = Party.builder()
                .name("혼자만 있고싶어요")
                .content("오지 마세요")
                .location("인하대 후문")
                .language(Language.KOREAN)
                .imageFilePath("default")
                .preferAges(PreferAges.EARLY_TWENTIES)
                .memberCount(5)
                .nativeCount(1)
                .ownerId(5L)
                .build();
        partyRepository.save(party);
        partyService.addOwnerToParty(5L, party);

        PostJoinApplicationReq postJoinApplicationReq = new PostJoinApplicationReq(6L, 8L);
        BaseException e = assertThrows(BaseException.class,
                () ->  partyService.joinApply(postJoinApplicationReq));
        assertThat(e.getStatus()).isEqualTo(BaseResponseStatus.FULL_OF_KOREAN);
    }
}