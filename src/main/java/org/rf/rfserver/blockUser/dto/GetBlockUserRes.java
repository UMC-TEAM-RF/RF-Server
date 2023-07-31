package org.rf.rfserver.blockUser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.domain.User;

@Getter
@AllArgsConstructor
public class GetBlockUserRes {
    private User blocker;
    private User blocked;
}
