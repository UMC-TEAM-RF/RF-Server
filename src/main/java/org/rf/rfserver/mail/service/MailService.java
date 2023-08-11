package org.rf.rfserver.mail.service;


import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.constant.University;
import org.rf.rfserver.constant.UniversityUrl;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.mail.domain.MailRegex;
import org.rf.rfserver.mail.dto.*;
import org.rf.rfserver.mail.domain.MailMessage;
import org.rf.rfserver.mail.exception.InvalidMailException;
import org.rf.rfserver.mail.exception.UnauthorizedException;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final Mail mail = new Mail();

    /*@Value("${spring.mail.from}")
    private String fromEmail;*/

    private final UserRepository userRepository;

    // 이메일 전송
    public PostSendRes sendMail(PostSendReq sendReq) throws BaseException {
        try {
            String mailAddress = sendReq.getMail();
            University university = sendReq.getUniversity();
            checkPossibleMail(mailAddress, university);
            SimpleMailMessage message = new SimpleMailMessage();
            setMessage(message, mailAddress);
            javaMailSender.send(message);
            return new PostSendRes(mail.getMailAddress(), mail.getUniversity());
        } catch (InvalidMailException e) {
            throw new BaseException(INVALID_UNIVERSITY);
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
        MailMessage mailTitle = MailMessage.MAIL_TITLE;
        message.setSubject(mailTitle.getContent());
        saveMailInfo(mailAddress);
        MailMessage mailMessage = MailMessage.MAIL_MESSAGE;
        message.setText(mailMessage.getContent() + mail.getCode());
    }

    private void saveMailInfo(String mailAddress) {
        String code = mail.createRandomCode();
        mail.setCode(code);
        mail.setMailAddress(mailAddress);
        String universityUrl = parseUniversity(mailAddress);
        University university = UniversityUrl.getUniversityByUrl(universityUrl);
        mail.setUniversity(university);
        mail.setIsAuth(false);
    }

    private void checkPossibleMail(String mailAddress, University givenUniversity) {
        String universityUrl = parseUniversity(mailAddress);
        University returnUniversity = UniversityUrl.getUniversityByUrl(universityUrl);
        if (!returnUniversity.equals(givenUniversity)) {
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

    /*
    * 비밀번호 재설정을 위한 이메일 전송
    * */


    // 비밀번호 재설정을 위한 메일 전송
    public void sendMailForPasswordReset(String to, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("[알프] 임시 비밀번호 안내");
        message.setText("안녕하세요, \n\n요청하신 임시 비밀번호입니다 : " + tempPassword + "\n\n임시 비밀번호로 로그인 한 후 새 비밀번호로 변경해 주세요.");

        javaMailSender.send(message);
    }

    // 임시 비밀번호 생성
    public String createTempPassword() {
        Mail mail = new Mail();
        return mail.createRandomCode();
    }
}