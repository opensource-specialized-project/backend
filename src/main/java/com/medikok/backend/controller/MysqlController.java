package com.medikok.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;

// API 연결 관련 서비스
import com.medikok.backend.service.ApiService;
// Mysql 연결 관련 서비스
import com.medikok.backend.service.MysqlService;
// 리스트
import java.util.List;
import java.util.ArrayList;

// 약 정보 클래스
import com.medikok.backend.info.DrugInfo;
// 약 정보 엔티티
import com.medikok.backend.entity.DrugInfoEntity;

@RestController
@RequestMapping("/mysql-request")
public class MysqlController {
    private ApiService apiService;
    private MysqlService mysqlService;

    public MysqlController(ApiService apiService, MysqlService mysqlService) {
        this.apiService = apiService;
        this.mysqlService = mysqlService;
    }

    @GetMapping("/drug-info-list/save")
    public List<DrugInfoEntity> saveDrugInfoEntityList() {
        List<DrugInfoEntity> drugInfoEntityList = new ArrayList<DrugInfoEntity>();
        try {
            List<DrugInfo> drugInfoList = apiService.getDataFromDrbEasyDrugInfoService();
            mysqlService.saveDrugInfoEntity(drugInfoList);
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
}
