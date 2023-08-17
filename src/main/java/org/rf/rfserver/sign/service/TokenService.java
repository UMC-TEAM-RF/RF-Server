package org.rf.rfserver.sign.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.jwt.TokenProvider;
import org.rf.rfserver.constant.RfRule;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static org.rf.rfserver.config.BaseResponseStatus.INVALID_TOKEN;
import static org.rf.rfserver.constant.RfRule.*;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) throws BaseException {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new BaseException(INVALID_TOKEN);
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findUserById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(ACCESS_TOKEN_EXPIRATION));
    }

    public void setDeviceToken(Long userId, String deviceToken) {}
}
