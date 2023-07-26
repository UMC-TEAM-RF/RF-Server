package org.rf.rfserver.mail.service;


import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseResponseStatus;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.university.service.UniversityService;
import org.rf.rfserver.mail.dto.PostMailReq;
import org.rf.rfserver.mail.dto.PostMailRes;
import org.rf.rfserver.mail.exception.InvalidMailException;
import org.rf.rfserver.mail.exception.UnauthorizedException;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UniversityService universityService;
    private final UserRepository userRepository;
    @Autowired
    private final JavaMailSender javaMailSender;
    private final Mail mail = new Mail();

    // 이메일 전송
    public PostMailRes sendMail(String mailAddress) {
        checkPossibleMail(mailAddress);
        SimpleMailMessage message = new SimpleMailMessage();
        setMessage(message, mailAddress);
        javaMailSender.send(message);
        return new PostMailRes(mail);
    }

    // 사용자에게 받은 인증코드 확인
    public PostMailRes checkCode(PostMailReq mailRequest) {
        checkRequest(mailRequest, mail);
        mail.setIsAuth(true);
        return new PostMailRes(mail);
    }

    // "ac" 또는 "edu" 로 대학교 이름 추출
    private String parseUniversity(String mailAddress) {
        MailRegex mailParse = MailRegex.MAIL_PARSE;
        String regex = mailParse.getRegex();
        List<String> splitMail = List.of(mailAddress.split(regex));
        int acIndex = splitMail.lastIndexOf("ac");
        int eduIndex = splitMail.lastIndexOf("edu");
        int universityIndex = Math.max(acIndex, eduIndex) - 1;
        return splitMail.get(universityIndex);
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

    private void checkPossibleMail(String mailAddress) {
        if (!isUniversityMail(mailAddress)) {
            throw new InvalidMailException(BaseResponseStatus.INVALID_MAIL);
        }
    }

    private void checkRequest(PostMailReq mailRequest, Mail mail) {
        checkValidCode(mailRequest, mail.getCode());
        checkValidRequestMail(mailRequest, mail.getMailAddress());
    }

    // 인증 코드가 일치하는지 확인
    private void checkValidCode(PostMailReq mailRequest, String code) {
        if (!mailRequest.getCode().equals(code)) {
            throw new UnauthorizedException(BaseResponseStatus.INVALID_CODE);
        }
    }

    // 입력 받은 이메일 주소가 정확한지 확인
    private void checkValidRequestMail(PostMailReq mailRequest, String mailAddress) {
        if (!mailRequest.getMail().equals(mailAddress)) {
            throw new InvalidMailException(BaseResponseStatus.INVALID_MAIL);
        }
    }

    // 이메일 주소가 대학교 이메일 주소인지 확인
    private boolean isUniversityMail(String mailAddress) {
        MailRegex university = MailRegex.UNIVERSITY_MAIL;
        return Pattern.matches(university.getRegex(), mailAddress);
    }

    private final Map<String, String> universityNameMap = new HashMap<>(); {
        // 대학교 이름과 해당 변환 이름을 매핑
        universityNameMap.put("inha", "인하대학교");
        universityNameMap.put("hanyang", "한양대학교");
        universityNameMap.put("tukorea", "한국공항대학교");
        universityNameMap.put("catholic", "가톨릭대학교");
        // 다른 대학교에 대한 매핑도 필요하면 여기에 추가로 작성 예정
    }
}