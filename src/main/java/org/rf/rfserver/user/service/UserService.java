package org.rf.rfserver.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.s3.S3Uploader;
import org.rf.rfserver.constant.Country;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.domain.UserParty;


import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    public PostUserRes createUser(PostUserReq postUserReq, MultipartFile file) throws BaseException {
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
            if(file != null) {
                String imageFilePath = s3Uploader.fileUpload(file, "userImage");
                user.updateImageUrl(imageFilePath);
            }
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
                    , patchUserReq.getInterestingLanguages()
                    , patchUserReq.getIntroduce()
                    , patchUserReq.getMbti()
                    , patchUserReq.getLifeStyle()
            );
            if(file != null){
                String imageFilePath = s3Uploader.fileUpload(file, "userImage");
                user.updateImageUrl(imageFilePath);
            }
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

