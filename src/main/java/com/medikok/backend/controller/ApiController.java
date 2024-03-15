package com.medikok.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.medikok.backend.service.ApiService;

import java.util.ArrayList;
// 리스트
import java.util.List;
// 약 정보 클래스
import com.medikok.backend.info.DrugInfo;

@RestController
@RequestMapping("/api-request")
public class ApiController {
    private ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/drug-info-list")
    public List<DrugInfo> getDrugInfoList() {
        List<DrugInfo> drugInfoList = new ArrayList<DrugInfo>();
        try {
            drugInfoList = apiService.getDataFromDrbEasyDrugInfoService();
            for (DrugInfo drugInfo : drugInfoList) {
                System.out.println("이름: " + drugInfo.getItemName());
                System.out.println("효능: " + drugInfo.getEfcyQesitm());
                System.out.println("사용법: " + drugInfo.getUseMethodQesitm());
                System.out.println("사용하기 전 주의사항: " + drugInfo.getAtpnWarnQesitm());
                System.out.println("사용 상 주의사항: " + drugInfo.getAtpnQesitm());
                System.out.println("상호작용: " + drugInfo.getIntrcQesitm());
                System.out.println("부작용: " + drugInfo.getSeQesitm());
                System.out.println("보관법: " + drugInfo.getDepositMethodQesitm());
                System.out.println("낱알 이미지: " + drugInfo.getItemImage());
                System.out.println("--------------------------------------------");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
        return drugInfoList;
    }
}
