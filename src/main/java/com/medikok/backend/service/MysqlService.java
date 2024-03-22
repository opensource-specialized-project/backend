package com.medikok.backend.service;
import com.medikok.backend.repository.DrugInfoRepository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

// 약 정보 클래스
import com.medikok.backend.info.DrugInfo;
// 약 정보 엔티티
import com.medikok.backend.entity.DrugInfoEntity;

@Service
public class MysqlService {
    DrugInfoRepository drugInfoRepository;
    public MysqlService(DrugInfoRepository drugInfoRepository) {
        this.drugInfoRepository = drugInfoRepository;
    }
    public List<DrugInfoEntity> findAllDrugInfoEntity() {
        return drugInfoRepository.findAll();
    }
    public boolean saveDrugInfoEntity(List<DrugInfo> drugInfoList) {
        try {
            List<DrugInfoEntity> drugInfoEntityList = new ArrayList<DrugInfoEntity>();
            for (DrugInfo drugInfo : drugInfoList) {
                DrugInfoEntity drugInfoEntity = new DrugInfoEntity();
                drugInfoEntity.setDrugInfoEntity(drugInfo);
                
                drugInfoEntityList.add(drugInfoEntity);
            }
            drugInfoRepository.saveAll(drugInfoEntityList);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    } 
}
