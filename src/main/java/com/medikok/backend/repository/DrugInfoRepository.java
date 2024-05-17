package com.medikok.backend.repository;

import com.medikok.backend.entity.DrugInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DrugInfoRepository extends JpaRepository<DrugInfoEntity, Long> {
    @Override
    List<DrugInfoEntity> findAll();
    
}
 