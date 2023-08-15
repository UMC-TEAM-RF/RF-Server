package org.rf.rfserver.sign.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.RefreshToken;
import org.rf.rfserver.sign.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.INVALID_TOKEN;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) throws BaseException {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BaseException(INVALID_TOKEN));
    }
}