package org.rf.rfserver.blockUser.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.domain.User;

@Getter
@Setter
@AllArgsConstructor
public class PostBlockUserReq {
    private User blocker;
    private User blocked;
}