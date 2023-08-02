package org.rf.rfserver.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.user.dto.*;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        User user = new User(postUserReq.getLoginId(), postUserReq.getPassword()
                , postUserReq.getEntrance(), postUserReq.getUniversity()
                , postUserReq.getNickName(), postUserReq.getCountry()
                , postUserReq.getInterestingLanguage(), postUserReq.getIntroduce(), postUserReq.getMbti());
        try {
            User userRes = userRepository.save(user);
            System.out.println(userRes);
            return new PostUserRes(user.getId());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUser(Long userId) throws BaseException{
        try {
            User user = userRepository.getReferenceById(userId);
            System.out.println(user);
            return new GetUserRes(user.getNickName(), user.getUniversity(), user.getInterestingLanguage()
                    , user.getIntroduce(), user.getCountry(), user.getMbti(), user.getEntrance());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PatchUserRes updateUser(Long userId, PatchUserReq patchUserReq) throws BaseException{
        try {
            User user = userRepository.getReferenceById(userId);
            System.out.println("origin : " + user);
            user.updateUser(patchUserReq.getNickName(), patchUserReq.getPassword()
                    , patchUserReq.getInterestingLanguage(), patchUserReq.getIntroduce(), patchUserReq.getMbti());
            System.out.println("updated : " + user);
            return new PatchUserRes(true);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
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
}
