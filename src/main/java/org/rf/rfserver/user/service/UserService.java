package org.rf.rfserver.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.mail.dto.PostResetPasswordReq;
import org.rf.rfserver.mail.dto.PostResetPasswordRes;
import org.rf.rfserver.mail.service.MailService;

import org.rf.rfserver.constant.Country;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;

import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;
import static org.rf.rfserver.constant.MailMessage.FIND_ID;
import static org.rf.rfserver.constant.MailMessage.RESET_PASSWORD;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        User user = User.builder()
                .loginId(postUserReq.getLoginId())
                .password(postUserReq.getPassword())
                .entrance(postUserReq.getEntrance())
                .university(postUserReq.getUniversity())
                .nickName(postUserReq.getNickName())
                .country(postUserReq.getCountry())
                .interestingLanguages(postUserReq.getInterestingLanguages())
                .introduce(postUserReq.getIntroduce())
                .mbti(postUserReq.getMbti())
                .email(postUserReq.getEmail())
                .interestCountries(postUserReq.getInterestCountries())
                .userInterests(postUserReq.getInterests())
                .lifeStyle(postUserReq.getLifeStyle())
                .build();
        try {
            userRepository.save(user);
            return new PostUserRes(user.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUser(Long userId) throws BaseException{
        try {
            User user = userRepository.getReferenceById(userId);
            return new GetUserRes(
                    user.getNickName()
                    , user.getUniversity()
                    , user.getInterestingLanguages()
                    , user.getIntroduce()
                    , user.getCountry()
                    , user.getMbti()
                    , user.getEntrance()
                    , user.getEmail()
                    , user.getInterestCountries()
                    , user.getUserInterests()
                    , user.getLifeStyle()
            );
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PatchUserRes updateUser(Long userId, PatchUserReq patchUserReq) throws BaseException{
        try {
            User user = userRepository.getReferenceById(userId);
            user.updateUser(
                    patchUserReq.getNickName()
                    , patchUserReq.getPassword()
                    , patchUserReq.getInterestingLanguages()
                    , patchUserReq.getIntroduce()
                    , patchUserReq.getMbti()
                    , patchUserReq.getLifeStyle()
            );
            return new PatchUserRes(true);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void isExceededPartyCount(User user) throws BaseException {
        if (user.isMoreThanFiveParties()) {
            throw new BaseException(EXCEEDED_PARTY_COUNT);
        }
    }

    public DeleteUserRes deleteUser(Long userId) throws BaseException{
        try {
            userRepository.deleteById(userId);
            return new DeleteUserRes(true);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public User findUserById(Long userId) throws BaseException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(INVALID_USER));
    }

    public GetUserIdCheckRes checkId(String loginId) throws BaseException {
        try {
            Boolean judge = !userRepository.existsUserByLoginId(loginId);
            return new GetUserIdCheckRes(judge);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetNicknameCheckRes checkNickname(String nickName) throws BaseException {
        try {
            Boolean judge = !userRepository.existsUserByNickName(nickName);
            return new GetNicknameCheckRes(judge);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

  // 아이디 찾기
    public PostResetPasswordRes findId(PostResetPasswordReq postPasswordReq) throws BaseException {
        // 데이터베이스에서 사용자 찾기
        User user = userRepository.findByEmail(postPasswordReq.getMail())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        String userId = user.getLoginId();

        // 이메일 전송
        mailService.sendMailForFindId(postPasswordReq.getMail(), userId);

        return new PostResetPasswordRes(true, FIND_ID);
    }

    // 비밀번호 재설정
    public PostResetPasswordRes resetPassword(PostResetPasswordReq postPasswordReq) throws BaseException {
        // 아이디로 사용자 찾기
        User user = userRepository.findByLoginId(postPasswordReq.getLoginId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));


        // 입력된 이메일이 저장된 이메일과 일치하는지 확인
        if (!user.getEmail().equals(postPasswordReq.getMail())) {
            throw new BaseException(NOT_USER_MAIL);
        }

        // 임시 비밀번호 생성
        String tempPassword = mailService.createTempPassword();

        // 사용자 비밀번호 업데이트 및 저장
        user.setPassword(tempPassword);
        userRepository.save(user);

        // 이메일 전송
        mailService.sendMailForPasswordReset(postPasswordReq.getMail(), tempPassword);

        return new PostResetPasswordRes(true, RESET_PASSWORD);
    }

      public boolean isKorean(User user) {
        if (user.getCountry() == Country.KOREA) {
            return true;
        }
        return false;
    }

    public List<GetUserProfileRes> getUserProfiles(List<UserParty> userParties) {
        List<Long> userIds = new ArrayList<>();
        for (UserParty userParty: userParties) {
            userIds.add(userParty.getUser().getId());
        }
        return userRepository.getUserProfilesByUserParties(userIds);
    }
}

