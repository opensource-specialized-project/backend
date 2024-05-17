package com.medikok.backend.shared;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class AlarmInfo {
    private LocalDateTime alarmTime; // 알람 시간
    private boolean[] alarmDays; // 알람이 울리는 요일
    private String alarmLabel; // 알람 레이블
    private int volumeLevel; // 음량 설정
    private int alarmType; // 알람 유형
    private boolean isActive; // 알람 활성화
    private boolean isRepeating; // 알람 반복
    private List<String> drugItemNameList; // 약 정보

    public AlarmInfo(
        LocalDateTime alarmTime, 
        boolean[] alarmDays, 
        String alarmLabel, 
        int volumeLevel,
        int alarmType,
        boolean isActive,
        boolean isRepeating,
        List<String> drugItemNameList
        ) {
            this.alarmTime = alarmTime;
            this.alarmDays = alarmDays;
            this.alarmLabel = alarmLabel;
            this.volumeLevel = volumeLevel;
            this.alarmType = alarmType;
            this.isActive = isActive;
            this.isRepeating = isRepeating;
            this.drugItemNameList = drugItemNameList;
    }
    // 알람 시간 getter, setter
    public LocalDateTime getAlarmTime() {
        return this.alarmTime;
    }
    public int getAlarmHour() { // 시각 가져오기
        return this.alarmTime.getHour();
    }
    public int getAlarmMinute() { // 분 가져오기
        return this.alarmTime.getMinute();
    }

    // 알람 시간 setter
    public void setAlarmTime(LocalDateTime alarmTime) { 
        this.alarmTime = alarmTime;
    }
    public void setAlarmHour(int hour) { // 시각 설정 
        this.alarmTime = this.alarmTime.withHour(hour);
    }
    public void setAlarmMinute(int minute) { // 분 설정
        this.alarmTime = this.alarmTime.withMinute(minute);
    }

    // 알람 울리는 요일 getter, setter
    public boolean[] getAlarmDays() { // 알람이 울리는 요일 가져오기
        return this.alarmDays;
    }

    public void setAlarmDays(boolean[] alarmDays) { // 알람이 울리는 요일 세팅하기
        this.alarmDays = alarmDays;
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

    // 반복 여부 getter, setter
    public boolean getIsRepeating() {
        return this.isRepeating;
    }

    public void setIsRepeating(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setRepeating(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }
    public List<String> getDrugItemNameList() {
        return drugItemNameList;
    }
    public void setDrugItemNameList(List<String> drugItemNameList) {
        this.drugItemNameList = drugItemNameList;
    }
}
