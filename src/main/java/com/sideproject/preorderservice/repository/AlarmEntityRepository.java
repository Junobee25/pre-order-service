package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.domain.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Long> {

    Page<AlarmEntity> findAllByUserAccount(UserAccount userAccount, Pageable pageable);
}
