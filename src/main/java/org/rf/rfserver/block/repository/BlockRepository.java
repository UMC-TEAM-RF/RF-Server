package org.rf.rfserver.block.repository;

import org.rf.rfserver.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {

}
