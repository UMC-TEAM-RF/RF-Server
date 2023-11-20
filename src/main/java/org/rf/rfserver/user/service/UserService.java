package org.rf.rfserver.user.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.jwt.TokenProvider;
import org.rf.rfserver.device.service.DeviceTokenService;
import org.rf.rfserver.mail.dto.PostResetPasswordReq;
import org.rf.rfserver.mail.dto.PostResetPasswordRes;
import org.rf.rfserver.mail.service.MailService;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;

import org.rf.rfserver.sign.service.RefreshTokenService;
import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.dto.sign.LoginReq;
import org.rf.rfserver.user.dto.sign.LoginRes;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;
import static org.rf.rfserver.constant.RfRule.*;
import static org.rf.rfserver.constant.MailMessage.FIND_ID;
import static org.rf.rfserver.constant.MailMessage.RESET_PASSWORD;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;
    private final S3Uploader s3Uploader;
    private final DeviceTokenService deviceTokenService;

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        isDuplicatedLoginId(postUserReq.getLoginId());
        User user = User.builder()
                .loginId(postUserReq.getLoginId())
                .password(bCryptPasswordEncoder.encode(postUserReq.getPassword()))
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
                .major(postUserReq.getMajor())
                .build();
        try {
            String imageFilePath = s3Uploader.getImageFilePath("userDefault/defaultImage.jpg");
            user.updateImageUrl(imageFilePath);
            userRepository.save(user);
            System.out.println(imageFilePath);
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
                    , user.getImageFilePath()
            );
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PatchUserRes updateUser(Long userId, PatchUserReq patchUserReq, MultipartFile file) throws BaseException{
        try {
            User user = userRepository.getReferenceById(userId);
            user.updateUser(
                    patchUserReq.getNickName()
                    , patchUserReq.getPassword()
                    , patchUserReq.getImageFilePath()
                    , patchUserReq.getInterestingLanguages()
                    , patchUserReq.getIntroduce()
                    , patchUserReq.getMbti()
                    , patchUserReq.getLifeStyle()
                    , patchUserReq.getMajor()
            );
            //사용자가 새로운 이미지로 바꾸려고 할 때
            if(file != null){
                String preImageFilePath = user.getImageFilePath();
                if(preImageFilePath != "default"){
                    String fileKey = s3Uploader.changeFileKeyPath(preImageFilePath);
                    s3Uploader.deleteFile(fileKey);
                }
                String imageFilePath = s3Uploader.fileUpload(file, "userImage");
                user.updateImageUrl(imageFilePath);
            }
            return new PatchUserRes(true);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void isExceededPartyCount(User user) throws BaseException {
        if (user.isMoreThanLimitedPartyNumber()) {
            throw new BaseException(EXCEEDED_PARTY_COUNT);
        }
    }

    public DeleteUserRes deleteUser(Long userId) throws BaseException{
        try {
            //유저의 프로필 이미지를 S3에서 삭제
            User user = userRepository.getReferenceById(userId);
            String imageFilePath = user.getImageFilePath();
            String fileKey = s3Uploader.changeFileKeyPath(imageFilePath);
            s3Uploader.deleteFile(fileKey);
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

    public LoginRes login(LoginReq loginReq) throws BaseException {
        User user = userRepository.findByLoginId(loginReq.getLoginId())
                .filter(it -> bCryptPasswordEncoder.matches(loginReq.getPassword(), it.getPassword()))	// 암호화된 비밀번호와 비교하도록 수정
                .orElseThrow(() -> new BaseException(INVALID_LOGIN_IR_OR_PASSWORD));
        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(ACCESS_TOKEN_EXPIRATION));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(REFRESH_TOKEN_EXPIRATION));
        refreshTokenService.saveRefreshToken(user.getId(), refreshToken);
        user.setDeviceToken(loginReq.getDeviceToken());
        deviceTokenService.setDeviceToken(user.getId(), loginReq.getDeviceToken());
        return new LoginRes(accessToken, refreshToken, user.getNickName(), user.getUniversity(), user.getInterestingLanguages(),
                user.getIntroduce(), user.getCountry(), user.getMbti(), user.getEntrance(), user.getEmail(), user.getInterestCountries(),
                user.getUserInterests(), user.getLifeStyle(), user.getImageFilePath(), user.getId());
    }

    public void isDuplicatedLoginId(String loginId) throws BaseException {
        if(userRepository.existsUserByLoginId(loginId)) {
            throw new BaseException(DUPLICATED_LOGIN_ID);
        }
    }

    public void pressLike(Long giveUserId, Long receiveUserId) throws BaseException {
        User giveUser = userRepository.findById(giveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        User receiveUser = userRepository.findById(receiveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        receiveUser.increaseLike();
        userRepository.save(receiveUser);
    }

    public void cancelLike(Long giveUserId, Long receiveUserId) throws BaseException {
        User giveUser = userRepository.findById(giveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        User receiveUser = userRepository.findById(receiveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        receiveUser.decreaseLike();
        userRepository.save(receiveUser);
    }

    public void pressHate(Long giveUserId, Long receiveUserId) throws BaseException {
        User giveUser = userRepository.findById(giveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        User receiveUser = userRepository.findById(receiveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        receiveUser.increaseHate();
        userRepository.save(receiveUser);
    }

    public void cancelHate(Long giveUserId, Long receiveUserId) throws BaseException {
        User giveUser = userRepository.findById(giveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        User receiveUser = userRepository.findById(receiveUserId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        receiveUser.decreaseHate();
        userRepository.save(receiveUser);
    }

    // 알프 점수 계산
    public GetRFScoreRes getRFScore(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        int love = user.getLove();
        int hate = user.getHate();
        int report = user.getReport();
        int score = user.getScore();

        int change = love - hate - 5 * report;
        score += change;

        // score가 최대 점수 100보다 높은 경우
        if(score > 100) {
            return new GetRFScoreRes(100);
        }
        // score가 최저 점수 0보다 낮은 경우
        else if(score < 0) {
            return new GetRFScoreRes(0);
        }
        else {
            return new GetRFScoreRes(score);
        }
    }
}

