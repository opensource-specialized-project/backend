package com.medikok.backend.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.medikok.backend.shared.ExtractedField;
import com.medikok.backend.shared.ExtractedText;
import com.medikok.backend.entity.DrugInfoEntity;
import com.medikok.backend.repository.AlarmInfoRepository;
import com.medikok.backend.repository.DrugInfoRepository;
import com.medikok.backend.shared.BoundingPoly;
import com.medikok.backend.shared.DrugInfo;
import com.medikok.backend.shared.Vertex;

@RestController
@RequestMapping("/extract-infer-text")
public class ExtractInferTextController {
    DrugInfoRepository drugInfoRepository;
    AlarmInfoRepository alarmInfoRepository;

    public ExtractInferTextController(DrugInfoRepository drugInfoRepository) {
        this.drugInfoRepository = drugInfoRepository;
    }

    public Queue<String> itemNameQueue = new ArrayDeque<>();
    public Queue<String> efcyQueue = new ArrayDeque<>();
    
    @GetMapping("/print-textdata")
    public List<DrugInfoEntity> printTextdata() {
        try {
            // 파일에서 JSON 전체 내용 읽기 (UTF-8로 인코딩된 파일)
            byte[] jsonData = Files.readAllBytes(Paths.get("please.json"));
            String content = new String(jsonData, StandardCharsets.UTF_8);
            
            // JSON 전체 내용을 JSONObject로 파싱
            JSONObject jsonObject = new JSONObject(content);
            
            // "images" 키에 해당하는 JSON 배열 추출
            JSONArray imagesArray = jsonObject.getJSONArray("images");
            
            // ExtractedText 객체를 생성하여 JSON 데이터 설정
            ExtractedText extractedText = new ExtractedText();

            List<ExtractedField> extractedFields = new ArrayList<>();
            
            // 각 객체의 "fields" 키에 해당하는 배열에서 데이터를 추출
            for (int i = 0; i < imagesArray.length(); i++) {
                JSONObject imageObject = imagesArray.getJSONObject(i);
                JSONArray fieldsArray = imageObject.getJSONArray("fields");

                for (int j = 0; j < fieldsArray.length(); j++) {
                    JSONObject fieldObject = fieldsArray.getJSONObject(j);
                    
                    String inferText = fieldObject.getString("inferText");
                    float inferConfidence = (float) fieldObject.getDouble("inferConfidence");

                    JSONArray verticesArray = fieldObject.getJSONObject("boundingPoly").getJSONArray("vertices");
                    List<Vertex> vertices = new ArrayList<>();
                    for (int k = 0; k < verticesArray.length(); k++) {
                        JSONObject vertexObject = verticesArray.getJSONObject(k);
                        float x = (float) vertexObject.getDouble("x");
                        float y = (float) vertexObject.getDouble("y");
                        vertices.add(new Vertex(x, y));
                    }

                    BoundingPoly boundingPoly = new BoundingPoly(vertices);
                    ExtractedField extractedField = new ExtractedField(inferText, inferConfidence, boundingPoly);
                    extractedFields.add(extractedField);
                }
            }

            extractedText.setFieldsFromJson(extractedFields);
            
            // 콘솔에 출력
            for (ExtractedField field : extractedText.getFields()) {
                String inferText =  field.getInferText();
                if (isDrugName(inferText)) {
                    itemNameQueue.add(inferText);
                }
                if (isDrugEfcy(inferText)) {
                    efcyQueue.add(inferText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DrugInfoEntity> drugInfoEntityList = new ArrayList<DrugInfoEntity>();
        while (!itemNameQueue.isEmpty()) {
            String itemName = itemNameQueue.poll();
            String efcy = efcyQueue.poll();
            DrugInfoEntity drugInfoEntity = new DrugInfoEntity(itemName, efcy);
            drugInfoEntityList.add(drugInfoEntity);            
        }
        drugInfoRepository.saveAll(drugInfoEntityList);

        return drugInfoEntityList;
    }

       
    private boolean isDrugName(String drugName) {
        // "액", "캡슐", "밀리그램" 중 하나가 포함되어 있는지 확인
        boolean containsLiquid = drugName.contains("액");
        boolean containsCapsule = drugName.contains("캡슐");
        boolean containsMilligram = drugName.contains("밀리그램");
        
        // 세 가지 중 하나라도 포함되어 있으면 true 반환
        return containsLiquid || containsCapsule || containsMilligram;
    }
    
    
    private boolean isDrugEfcy(String drugEfcy) {
        // "[" 또는 "]"가 포함되어 있는지 확인
        boolean containsLeftBracket = drugEfcy.contains("[");
        boolean containsRightBracket = drugEfcy.contains("]");
        
        // 둘 중 하나라도 포함되어 있으면 true 반환
        return containsLeftBracket || containsRightBracket;
    }
}
