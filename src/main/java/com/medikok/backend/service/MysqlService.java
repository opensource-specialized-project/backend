package com.medikok.backend.service;
import com.medikok.backend.repository.DrugInfoRepository;

// shared
import com.medikok.backend.shared.DrugInfo;
// import com.medikok.backend.shared.AlarmInfo;
import com.medikok.backend.repository.AlarmInfoRepository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

// 약 정보 엔티티
import com.medikok.backend.entity.DrugInfoEntity;
// 알람 정보 엔티티
import com.medikok.backend.entity.AlarmInfoEntity;

@Service
public class MysqlService {
    DrugInfoRepository drugInfoRepository;
    AlarmInfoRepository alarmInfoRepository;

    public MysqlService(DrugInfoRepository drugInfoRepository, AlarmInfoRepository alarmInfoRepository) {
        this.drugInfoRepository = drugInfoRepository;
        this.alarmInfoRepository = alarmInfoRepository;
    }

    public List<DrugInfoEntity> findAllDrugInfoEntity() {
        return drugInfoRepository.findAll();
    }
    
    public boolean saveAllDrugInfoEntity(List<DrugInfo> drugInfoList) {
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

    public List<AlarmInfoEntity> findAllAlarmInfoEntity() {
        return alarmInfoRepository.findAll();
    }

    // public boolean saveAlarmInfoEntity(AlarmInfo alarmInfo) {
    //     AlarmInfoEntity alarmInfoEntity = new AlarmInfoEntity();
    //     try {
    //         alarmInfoRepository.save(alarmInfoEntity);
    //         return true;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }
}