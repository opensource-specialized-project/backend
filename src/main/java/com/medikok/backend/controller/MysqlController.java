package com.medikok.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;

// API 연결 관련 서비스
import com.medikok.backend.service.ApiService;
// Mysql 연결 관련 서비스
import com.medikok.backend.service.MysqlService;
import com.medikok.backend.shared.DrugInfo;
// import com.medikok.backend.shared.AlarmInfo;

// 리스트
import java.util.List;
import java.util.ArrayList;

// 약 정보 엔티티
import com.medikok.backend.entity.DrugInfoEntity;
// 알람 정보 엔티티
import com.medikok.backend.entity.AlarmInfoEntity;

@RestController
@RequestMapping("/mysql-request")
public class MysqlController {
    private ApiService apiService;
    private MysqlService mysqlService;

    public MysqlController(ApiService apiService, MysqlService mysqlService) {
        this.apiService = apiService;
        this.mysqlService = mysqlService;
    }

    @GetMapping("/drug-info-list/save_all")
    public List<DrugInfoEntity> saveDrugInfoEntityList() {
        List<DrugInfoEntity> drugInfoEntityList = new ArrayList<DrugInfoEntity>();
        try {
            List<DrugInfo> drugInfoList = apiService.getDataFromDrbEasyDrugInfoService();
            mysqlService.saveAllDrugInfoEntity(drugInfoList);
            drugInfoEntityList = mysqlService.findAllDrugInfoEntity();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return drugInfoEntityList;
    }

    @GetMapping("/drug-info-list/get")
    public List<DrugInfoEntity> getDrugInfoEntityList() {
        List<DrugInfoEntity> drugInfoEntityList = new ArrayList<DrugInfoEntity>();
        try {
            drugInfoEntityList = mysqlService.findAllDrugInfoEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drugInfoEntityList;
    }

    @GetMapping("/alarm-info-list/get")
    public List<AlarmInfoEntity> getAlarmInfoListGet() {
        List<AlarmInfoEntity> alarmInfoEntityList = new ArrayList<AlarmInfoEntity>(); 
        try {
            alarmInfoEntityList = mysqlService.findAllAlarmInfoEntity();
        } catch (Exception e) {
           
            e.printStackTrace();
        }
        return alarmInfoEntityList;
    }

    // @PostMapping("/alarm-info-list/create")
    // public AlarmInfoEntity createAlarmInfoEntity(AlarmInfo alarmInfo) {
    //     AlarmInfoEntity alarmInfoEntity = new AlarmInfoEntity();
    //     try {
    //         List<AlarmInfoEntity> alarmInfoEntityList = mysqlService.findAllAlarmInfoEntity();
    //         mysqlService.saveAlarmInfoEntity(alarmInfo);
    //     } catch (Exception E) {
    //     }
    // }
}
