package org.rf.rfserver.Constant;

public enum Interest {
    MUSIC(0), KPOP(1), SPORT(2), SPORT_GAME(3),
    LANGUAGE_EXCHANGE(4), LANGUAGE(5), COUNTRY(6), FRIENDSHIP(7),
    FOOD(8), COOKING(9), HOT_PLACE(10), CAFE(11), STUDY(12), MAJOR(13), GRADE(14), READING(15);

    public int id;

    Interest(int id) {
        this.id = id;
    }
}
