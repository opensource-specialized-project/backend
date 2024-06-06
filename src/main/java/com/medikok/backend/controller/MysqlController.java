package com.medikok.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// API 연결 관련 서비스
import com.medikok.backend.service.ApiService;
// Mysql 연결 관련 서비스
import com.medikok.backend.service.MysqlService;
import com.medikok.backend.shared.AlarmInfo;
import com.medikok.backend.shared.DrugInfo;
// import com.medikok.backend.shared.AlarmInfo;

// 리스트
import java.util.List;
import java.util.ArrayList;

// 약 정보 엔티티
import com.medikok.backend.entity.DrugInfoEntity;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
// 알람 정보 엔티티
import com.medikok.backend.entity.AlarmInfoEntity;
// 이미지 불러오기
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Base64;

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
            mysqlService.saveAllDrugInfoEntityByInfo(drugInfoList);
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

    // 알람 정보 가져오기
    @GetMapping("/alarm-info-list/get")
    public List<AlarmInfoEntity> getAlarmInfoList() {
        List<AlarmInfoEntity> alarmInfoEntityList = new ArrayList<AlarmInfoEntity>(); 
        try {
            alarmInfoEntityList = mysqlService.findAllAlarmInfoEntity();
        } catch (Exception e) {
           
            e.printStackTrace();
        }
        return alarmInfoEntityList;
    }

    // 알람 새로 만들기
    @PostMapping("/alarm-info-list/create") 
    public boolean createAlarmInfoList(AlarmInfo alarmInfo) {
        try {
            mysqlService.saveAlarmInfoEntityByInfo(alarmInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    // 알람 정보 테스트 - 저장
    @GetMapping("alarm-info-list/test/save")
    public List<AlarmInfoEntity> testSaveAlarmInfoList() {
        mysqlService.deleteAllAlarmInfoEntity();
        AlarmInfo alarmInfo = new AlarmInfo(
            LocalDateTime.now(),
            new boolean[]{true, true, true, true, false, false, true}, // 배열 초기화 수정
            "테스트 알람",
            10,
            0,
            true,
            true,
            new ArrayList<>() 
        );
        
        mysqlService.saveAlarmInfoEntityByInfo(alarmInfo);

        List<AlarmInfoEntity> alarmInfoEntityList = new ArrayList<AlarmInfoEntity>();
        try {
            alarmInfoEntityList = mysqlService.findAllAlarmInfoEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alarmInfoEntityList;
    }

    // 알람 정보 테스트 - 수정
    @GetMapping("alarm-info-list/test/modify")
    public AlarmInfoEntity testModifyAlarmInfoEntity() {
        AlarmInfoEntity alarmInfoEntity = null;
        try {
            alarmInfoEntity = mysqlService.getAlarmInfoEntityByAlarmLabel("테스트 알람");
            alarmInfoEntity.setAlarmLabel("수정된 알람");
            mysqlService.saveAlarmInfoEntityByEntity(alarmInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alarmInfoEntity;
    }
    // 불러온 이미지 저장
    public static class ImageRequest {
        private String image;
    
        // 기본 생성자 추가
        public ImageRequest() {
        }
    
        public String getImage() {
            return image;
        }
    
        public void setImage(String image) {
            this.image = image;
        }
    }

    @PostMapping("/saveImage")
    public ResponseEntity<String> saveImage(@RequestBody ImageRequest imageRequest) {
        try {
            // 이미지 처리 로직 추가
            String base64Image = imageRequest.getImage();
            System.out.println("Received image: " + base64Image);
            // 파일로 저장할 경로
            String filePath = "testImage.jpg";

            try {
                // Base64 문자열 디코딩
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                // 파일 객체 생성
                File imageFile = new File(filePath);

                // 바이트 배열을 파일로 쓰기
                FileUtils.writeByteArrayToFile(imageFile, imageBytes);

                System.out.println("Image saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>("Image saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}   
