package org.rf.rfserver.block.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rf.rfserver.domain.User;

@Getter
@AllArgsConstructor
public class GetBlockRes {
    private User blocker;
    private User blocked;
}
