package org.rf.rfserver.mail.service;


import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseResponseStatus;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.university.service.UniversityService;
import org.rf.rfserver.mail.dto.PostMailReq;
import org.rf.rfserver.mail.dto.PostMailRes;
import org.rf.rfserver.mail.exception.InvalidMailException;
import org.rf.rfserver.mail.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UniversityService universityService;

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
        // 메일 요청 객체(mailRequest)에서 인증 코드와 이메일 주소를 가져옴
        String code = mailRequest.getCode();
        String mailAddress = mailRequest.getMail();

        // 메일 객체(mail)에서 이전에 저장한 인증 코드와 이메일 주소를 가져옴
        String storedCode = mail.getCode();
        String storedMailAddress = mail.getMailAddress();

        // 사용자가 입력한 인증 코드와 이메일 주소가 저장된 정보와 일치하는지 확인
        if (!code.equals(storedCode) || !mailAddress.equals(storedMailAddress)) {
            // 일치하지 않는 경우, UnauthorizedException을 발생시킴
            throw new UnauthorizedException(BaseResponseStatus.INVALID_CODE);
        }

        // 인증 코드가 올바른 경우, mail 객체의 인증 상태를 true로 설정
        mail.setIsAuth(true);

        // 대학교 정보 조회
        University university = universityService.findUniversityByName(mail.getUniversity());

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
        mail.setUniversity(university);
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

    private void checkValidCode(PostMailReq mailRequest, String code) {
        if (!mailRequest.getCode().equals(code)) {
            throw new UnauthorizedException(BaseResponseStatus.INVALID_CODE);
        }
    }

    private void checkValidRequestMail(PostMailReq mailRequest, String mailAddress) {
        if (!mailRequest.getMail().equals(mailAddress)) {
            throw new InvalidMailException(BaseResponseStatus.INVALID_MAIL);
        }
    }

    private boolean isUniversityMail(String mailAddress) {
        MailRegex university = MailRegex.UNIVERSITY_MAIL;
        return Pattern.matches(university.getRegex(), mailAddress);
    }
}
