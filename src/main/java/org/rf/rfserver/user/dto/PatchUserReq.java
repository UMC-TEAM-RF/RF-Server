package org.rf.rfserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.Language;
import org.rf.rfserver.constant.LifeStyle;
import org.rf.rfserver.constant.Mbti;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserReq {
    private String nickName = null;
    private String password = null;
    private String imageFilePath = null;
    private List<Language> interestingLanguages = null;
    private String introduce = null;
    private Mbti mbti = null;
    private LifeStyle lifeStyle = null;
}
