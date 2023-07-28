package org.rf.rfserver.mail.service;


import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.mail.dto.PostCheckReq;
import org.rf.rfserver.mail.dto.PostCheckRes;
import org.rf.rfserver.mail.dto.PostSendReq;
import org.rf.rfserver.mail.dto.PostSendRes;
import org.rf.rfserver.mail.exception.InvalidMailException;
import org.rf.rfserver.mail.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MailService {
    @Autowired
    private final JavaMailSender javaMailSender;
    private final Mail mail = new Mail();


    // 이메일 전송
    public PostSendRes sendMail(PostSendReq sendReq) throws BaseException {
        try {
            String mailAddress = sendReq.getMail();
            String university = sendReq.getUniversity();
            checkPossibleMail(mailAddress, university);
            SimpleMailMessage message = new SimpleMailMessage();
            setMessage(message, mailAddress);
            javaMailSender.send(message);
            return new PostSendRes(mail);
        } catch (Exception e) {
            throw new BaseException(INVALID_MAIL);
        }
    }

    // 사용자에게 받은 인증 코드 확인
    public PostCheckRes checkCode(PostCheckReq checkReq) throws BaseException {
        try {
            checkRequest(checkReq, mail);
            mail.setIsAuth(true);
            return new PostCheckRes(true);
        } catch (Exception e) {
            throw new BaseException(INVALID_CODE);
        }
    }

    private String parseUniversity(String mailAddress) {
        MailRegex mailParse = MailRegex.MAIL_PARSE;
        String regex = mailParse.getRegex();
        List<String> splitMail = List.of(mailAddress.split(regex));
        return splitMail.get(1);
    }

    // 이메일 메시지의 세부 정보를 설정
    private void setMessage(SimpleMailMessage message, String mailAddress) {
        message.setTo(mailAddress);
        org.rf.rfserver.domain.MailMessage mailTitle = org.rf.rfserver.domain.MailMessage.MAIL_TITLE;
        message.setSubject(mailTitle.getContent());
        saveMailInfo(mailAddress);
        org.rf.rfserver.domain.MailMessage mailMessage = MailMessage.MAIL_MESSAGE;
        message.setText(mailMessage.getContent() + mail.getCode());
    }

    private void saveMailInfo(String mailAddress) {
        String code = mail.createRandomCode();
        mail.setCode(code);
        mail.setMailAddress(mailAddress);
        String university = parseUniversity(mailAddress);
        String koreanUniversity = universityNameMap.getOrDefault(university, university);
        mail.setUniversity(koreanUniversity);
        mail.setIsAuth(false);
    }

    private void checkPossibleMail(String mailAddress, String userUniversity) {
        String university = parseUniversity(mailAddress);
        String koreanUniversity = universityNameMap.getOrDefault(university, university);
        if (!koreanUniversity.equals(userUniversity)) {
            throw new InvalidMailException(INVALID_UNIVERSITY);
        }
        if (!isUniversityMail(mailAddress)) {
            throw new InvalidMailException(INVALID_MAIL);
        }
    }

    private void checkRequest(PostCheckReq mailRequest, Mail mail) {
        checkValidCode(mailRequest, mail.getCode());
        checkValidRequestMail(mailRequest, mail.getMailAddress());
    }

    private void checkValidCode(PostCheckReq mailRequest, String code) {
        if (!mailRequest.getCode().equals(code)) {
            throw new UnauthorizedException(INVALID_CODE);
        }
    }

    private void checkValidRequestMail(PostCheckReq mailRequest, String mailAddress) {
        if (!mailRequest.getMail().equals(mailAddress)) {
            throw new InvalidMailException(INVALID_MAIL);
        }
    }

    private boolean isUniversityMail(String mailAddress) {
        MailRegex university = MailRegex.UNIVERSITY_MAIL;
        return Pattern.matches(university.getRegex(), mailAddress);
    }

    private final Map<String, String> universityNameMap = new HashMap<>(); {
        // 대학교 이름과 해당 변환 이름을 매핑
        universityNameMap.put("inha.edu", "인하대학교");
        universityNameMap.put("hanyang.ac.kr", "한양대학교");
        universityNameMap.put("tukorea.ac.kr", "한국공학대학교");
        universityNameMap.put("catholic.ac.kr", "가톨릭대학교");
        // 다른 대학교에 대한 매핑도 필요하면 여기에 추가로 작성 예정
    }
}