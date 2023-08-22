package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public enum Banner{
    BANNER1("partyBanner/banner1.jpg"),
    BANNER2("partyBanner/banner2.jpg"),
    BANNER3("partyBanner/banner3.jpg"),
    BANNER4("partyBanner/banner4.jpg"),
    BANNER5("partyBanner/banner5.jpg")
    ;
    private final String url;

    public static Banner getRandomBanner(){
        Random random = new Random();
        return values()[random.nextInt(4)];
    }

}
