package org.rf.rfserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mail {
    private String code;
    private String mailAddress;
    private String university;
    private Boolean isAuth;

    private static final int CODE_SIZE = 6;

    public String createRandomCode() {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        while (buffer.length() < CODE_SIZE) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }
}
