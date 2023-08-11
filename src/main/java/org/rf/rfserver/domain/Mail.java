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

    public String createRandomCode() {
        int CODE_SIZE = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        while (buffer.length() < CODE_SIZE) {
            int index = random.nextInt(characters.length());
            buffer.append(characters.charAt(index));
        }
        return buffer.toString();
    }

}
