package org.rf.rfserver.user.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.user.dto.GetUserRes;
import org.rf.rfserver.user.dto.PostUserReq;
import org.rf.rfserver.user.dto.PostUserRes;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.DATABASE_ERROR;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        User user = new User();
        user.createUser(postUserReq.getUserId(), postUserReq.getPassword()
                , postUserReq.getEntrance(), postUserReq.getUniversity()
                , postUserReq.getNickName(), postUserReq.getCountry()
                , postUserReq.getInterestingLanguage(), postUserReq.getIntroduce(), postUserReq.getMbti());
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
            return new GetUserRes(user.getNickName(), user.getUniversity(), user.getInterestingLanguage()
                    , user.getIntroduce(), user.getCountry(), user.getMbti(), user.getEntrance());
        } catch (Exception e) {
            throw  new BaseException(DATABASE_ERROR);
        }
    }
}
