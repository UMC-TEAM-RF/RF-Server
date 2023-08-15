package org.rf.rfserver.sign.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.sign.dto.CreateAccessTokenReq;
import org.rf.rfserver.sign.dto.CreateAccessTokenRes;
import org.rf.rfserver.sign.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenRes> createNewAccessToken(@RequestBody CreateAccessTokenReq request) throws BaseException {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenRes(newAccessToken));
//        return new BaseResponse<>(new CreateAccessTokenRes(newAccessToken));
    }
}