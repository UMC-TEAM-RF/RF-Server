package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.user.enums.Mbti;

import java.util.List;

@Getter
@AllArgsConstructor
public class PatchUserReq {
    private String nickName = null;
    private String password = null;
    private List<Language> interestingLanguages = null;
    private String introduce = null;
    private Mbti mbti = null;
}
