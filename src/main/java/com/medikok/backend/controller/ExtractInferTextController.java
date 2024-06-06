package com.medikok.backend.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.medikok.backend.shared.ExtractedField;
import com.medikok.backend.shared.ExtractedText;
import com.medikok.backend.shared.BoundingPoly;
import com.medikok.backend.shared.Vertex;

@RestController
@RequestMapping("/extract-infer-text")
public class ExtractInferTextController {

    @GetMapping("/print-textdata")
    public void printTextdata() {
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
                String infertext =  field.getInferText();
                약품명 조건식:
                    큐 추가
                약 효능 조건식:
                    큐 추가
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (약품명의 큐의 길이만큼 반복) {
            DrugEnttiy
            Drug
        }
        }
    }
}
