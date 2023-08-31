package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rule implements EnumModel{
    NONE("해당사항 없음")
    ,ACTIVE("활동적인 걸 원해요")
    ,QUICK_RESPONSE("빠른 답변을 원해요")
    ,ATTENDANCE("모임참석이 중요해요")
    ,FOCUS("우리 모임에 집중해주길 원해요")
    ,NO_SWEAR_WORDS("욕설 및 비방글 절대 금지")
    ,NO_LATE_TALK("너무 늦은 시간에는 대화 금지")
    ,CAN_DROP("강퇴를 진행할 수 있어요")
    ,NO_SEXUAL("선정적인 이야기 금지")
    ,POLITE_SPEECH("존댓말로 대화해요")
    ,INFORMAL_SPEECH("반말로 대화해요")
    ,ACTIVE_VACATION("방학 때도 활동할 사람을 원해요")
    ,PURE("건전한 모임 활동을 원해요")
    ,DAILY_TALK("하루에 한 번 대화는 꼭 참여해요")
    ,FREE_MOOD("자유로운 분위기를 원해요")
    ;
    private final String value;
    @Override
    public String getKey() {return name();}
}
