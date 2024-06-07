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
            byte[] jsonData = Files.readAllBytes(Paths.get("FinalTest.json"));
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

            double referenceXForImage = Double.MIN_VALUE;
            double referenceYForImage = Double.MIN_VALUE;
            double referenceXForDose = Double.MAX_VALUE;

            // Find the reference points
            for (ExtractedField field : extractedText.getFields()) {
                if ("약품이미지".equals(field.getInferText())) {
                    referenceXForImage = field.getBoundingPoly().getVertices().get(1).getX();
                    referenceYForImage = field.getBoundingPoly().getVertices().get(1).getY();
                }
                if ("투약량".equals(field.getInferText())) {
                    referenceXForDose = field.getBoundingPoly().getVertices().get(0).getX();
                }
            }

            // Filter elements based on the reference points
            for (ExtractedField field : extractedText.getFields()) {
                String text = field.getInferText();
                List<Vertex> vertices = field.getBoundingPoly().getVertices();

                if ((vertices.get(0).getX() > referenceXForImage) && 
                    (vertices.get(0).getX() < referenceXForDose) && 
                    (vertices.get(0).getY() > referenceYForImage)) {

                    // Add medicine names to the queue
                    if (text.contains("정") && !text.contains("일정")) {
                        String medicineName = text.split("정")[0] + "정";
                        if (!itemNameQueue.contains(medicineName)) {
                            itemNameQueue.add(medicineName);
                        }
                    } else if (text.contains("캡슐")) {
                        String medicineName = text.split("캡슐")[0] + "캡슐";
                        if (!itemNameQueue.contains(medicineName)) {
                            itemNameQueue.add(medicineName);
                        }
                    } else if (text.contains("액")) {
                        String medicineName = text.split("액")[0] + "액";
                        if (!itemNameQueue.contains(medicineName)) {
                            itemNameQueue.add(medicineName);
                        }
                    }

                    // Handle texts starting with "[" and potentially ending with "]"
                    if (text.startsWith("[") && !text.contains("앞") && !text.contains("뒤")) {
                        StringBuilder combinedText = new StringBuilder(text);
                        if (!text.endsWith("]")) {
                            double startX = vertices.get(0).getX();
                            double startY = vertices.get(0).getY();
                            boolean foundEndBracket = false;

                            for (ExtractedField nextField : extractedText.getFields()) {
                                String nextText = nextField.getInferText();
                                List<Vertex> nextVertices = nextField.getBoundingPoly().getVertices();
                                if (nextVertices.get(0).getY() == startY && nextVertices.get(0).getX() > startX) {
                                    combinedText.append(" ").append(nextText);
                                    if (nextText.endsWith("]")) {
                                        foundEndBracket = true;
                                        break;
                                    }
                                }
                            }

                            if (foundEndBracket) {
                                String combinedResult = combinedText.toString();
                                combinedResult = combinedResult.substring(1, combinedResult.length() - 1);  // Remove the starting "[" and ending "]"
                                efcyQueue.add(combinedResult);
                            }
                        } else {
                            String effectText = text.substring(1, text.length() - 1);  // Remove the starting "[" and ending "]"
                            efcyQueue.add(effectText);
                        }
                    }
                }
            }

            // 콘솔에 출력
            System.out.println("Medicine Names Queue: " + itemNameQueue);
            System.out.println("Medicine Effects Queue: " + efcyQueue);

            
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
}
