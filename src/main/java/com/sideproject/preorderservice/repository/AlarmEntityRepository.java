package com.sideproject.preorderservice.repository;


import com.sideproject.preorderservice.domain.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Long> {
    Page<AlarmEntity> findAllByFromUserIdIn(List<Long> fromUserId, Pageable pageable);
}
