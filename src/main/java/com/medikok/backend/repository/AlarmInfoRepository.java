package com.medikok.backend.repository;

import com.medikok.backend.entity.AlarmInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmInfoRepository extends JpaRepository<AlarmInfoEntity, Long> {
    @Override
    List<AlarmInfoEntity> findAll();
}
