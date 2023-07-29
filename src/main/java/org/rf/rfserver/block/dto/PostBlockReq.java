package org.rf.rfserver.block.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.domain.User;

@Getter
@Setter
@AllArgsConstructor
public class PostBlockReq {
    private User blocker;
    private User blocked;
}