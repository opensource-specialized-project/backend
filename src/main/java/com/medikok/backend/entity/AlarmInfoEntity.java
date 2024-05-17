package com.medikok.backend.entity;

// jakarta
import jakarta.persistence.Table; // 테이블 지정
import jakarta.persistence.Entity; // 데이터베이스 테이블과 매핑되는 클래스
import jakarta.persistence.Id; // 엔터티의 기본 키를 지정

import jakarta.persistence.GeneratedValue; // 기본 키 값에 대한 생성자 제공
import jakarta.persistence.GenerationType; // 기본 키 값에 대한 생성 타입

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.medikok.backend.shared.AlarmInfo;
import com.medikok.backend.shared.DrugInfo;

import jakarta.persistence.Column; // 열 지정


@Table(name = "alarm_info")
@Entity
public class AlarmInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람 시간
    @Column(name="alarm_time", nullable=false)
    private LocalDateTime alarmTime;

    // 알람이 울리는 요일
    @Column(name="alarm_days", nullable=true)
    private String alarmDays;

    // 알람 레이블
    @Column(name="alarm_label", nullable=false)
    private String alarmLabel;

    // 음량 설정
    @Column(name="volume_level", nullable=false)
    private int volumeLevel;

    // 알람 유형
    // 0: 소리, 1: 진동
    @Column(name="alarm_type", nullable=false)
    private int alarmType;

    // 알람 활성화 여부
    @Column(name="is_active", nullable=false)
    private boolean isActive;

    // 알람 반복 설정
    @Column(name="is_repeating", nullable=true)
    private boolean isRepeating;

    // 약 아이템 정보
    @Column(name = "drug_item_names", nullable = true, columnDefinition = "MEDIUMBLOB") // 이미지
    private String drugItemNames; // 약 정보

    public AlarmInfoEntity() {

    }  
    public AlarmInfoEntity(AlarmInfo alarmInfo) {
        setAlarmTime(alarmInfo.getAlarmTime());
        setAlarmDays(alarmInfo.getAlarmDays());
        setAlarmLabel(alarmInfo.getAlarmLabel());
        setVolumeLevel(alarmInfo.getVolumeLevel());
        setAlarmType(alarmInfo.getAlarmType());
        setIsActive(alarmInfo.getIsActive());
        setIsRepeating(alarmInfo.getIsRepeating());
        setDrugItemNames(alarmInfo.getDrugItemNameList());
    }
    // 알람 시간 getter, setter
    public LocalDateTime getAlarmTime() {
        return this.alarmTime;
    }
    public int getAlarmHour() { // 시각 설정
        return this.alarmTime.getHour();
    }
    public int getAlarmMinute() { // 분 설정
        return this.alarmTime.getMinute();
    }
    public void setAlarmTime(LocalDateTime alarmTime) { // 알람 시간 설정하기
        this.alarmTime = alarmTime;
    }
    public void setAlarmHour(int hour) { // 시각 설정 
        this.alarmTime = this.alarmTime.withHour(hour);
    }
    public void setAlarmMinute(int minute) { // 분 설정
        this.alarmTime = this.alarmTime.withMinute(minute);
    }

    // 알람 울리는 요일 getter, setter
    public boolean[] getAlarmDays() {
        boolean[] alarmDaysArray = new boolean[7];
        if (alarmDays != null && !alarmDays.isEmpty()) {
            String[] alarmDaysStringArray = this.alarmDays.split(",");
            if (alarmDaysStringArray.length == 7) {
                for (int i = 0; i < 7; i++) {
                    alarmDaysArray[i] = alarmDaysStringArray[i].trim().equals("1");
                }
            }
        }
        return alarmDaysArray;
    }
    public void setAlarmDays(boolean[] alarmDaysArray) {
        if (alarmDaysArray != null && alarmDaysArray.length == 7) {
            String[] alarmDaysStringArray = IntStream.range(0, alarmDaysArray.length)
                                                    .mapToObj(i -> alarmDaysArray[i] ? "1" : "0")
                                                    .toArray(String[]::new);
            this.alarmDays = String.join(",", alarmDaysStringArray);
        }
    }
   
    // 알람 레이블 getter, setter
    public String getAlarmLabel() {
        return this.alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    // 볼륨 레벨 getter, setter
    public int getVolumeLevel() {
        return this.volumeLevel;
    }
    public void setVolumeLevel(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    // 알람 유형 getter, setter
    public int getAlarmType() {
        return this.alarmType;
    }
    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    // 알람 활성화 여부 getter, setter
    public boolean getIsActive() {
        return this.isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsRepeating() {
        return this.isRepeating;
    }

    public void setIsRepeating(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }

    // 복용약 정보 getter, setter
    public List<String> getDrugItemNames() {
        String[] drugItemNameArray = drugItemNames.split(",");
        return Arrays.asList(drugItemNameArray);
    }
    
    public void setDrugItemNames (List<String> drugItemNameList) {
        this.drugItemNames = String.join(",", drugItemNameList); 
    }
        
}