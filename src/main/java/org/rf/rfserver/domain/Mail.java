package org.rf.rfserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rf.rfserver.constant.University;

import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mail {
    private String code;
    private String mailAddress;
    private University university;
    private Boolean isAuth;

    private static final int CODE_SIZE = 6;
    private static final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String createRandomCode() {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();

        while (buffer.length() < CODE_SIZE) {
            int index = random.nextInt(characters.length());
            buffer.append(characters.charAt(index));
        }
        return buffer.toString();
    }

}
