package org.rf.rfserver.redisDomain.partyidUserid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RedisHash("partyidUserid")
public class PartyidUserid {
    @Id
    private Long partyId;
    private Set<Long> userIds;
}
