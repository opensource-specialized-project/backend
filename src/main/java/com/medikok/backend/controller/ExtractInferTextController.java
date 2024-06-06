package com.medikok.backend.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
@RequestMapping("/extract-infer-text")
public class ExtractInferTextController {
    @GetMapping("/print-textdata")
    public void printTextdata(String[] args) {
        try {
            // 파일에서 JSON 전체 내용 읽기 (UTF-8로 인코딩된 파일)
            byte[] jsonData = Files.readAllBytes(Paths.get("please.json"));
            String content = new String(jsonData, StandardCharsets.UTF_8);
            
            // JSON 전체 내용을 JSONObject로 파싱
            JSONObject jsonObject = new JSONObject(content);
            
            // "images" 키에 해당하는 JSON 배열 추출
            JSONArray imagesArray = jsonObject.getJSONArray("images");
            
            // 각 객체의 "fields" 키에 해당하는 배열에서 "inferText" 키 값을 추출하여 출력
            for (int i = 0; i < imagesArray.length(); i++) {
                JSONObject imageObject = imagesArray.getJSONObject(i);
                JSONArray fieldsArray = imageObject.getJSONArray("fields");
                for (int j = 0; j < fieldsArray.length(); j++) {
                    JSONObject fieldObject = fieldsArray.getJSONObject(j);
                    String inferText = fieldObject.getString("inferText");
                    System.out.println("inferText: " + inferText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}