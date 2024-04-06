package com.medikok.backend.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
// 리스트
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medikok.backend.shared.DrugInfo;

@Service
public class ApiService {

    @Value("${service.api.key}")
    private String serviceKey;

    public List<DrugInfo> getDataFromDrbEasyDrugInfoService() throws IOException, InterruptedException {
        String url = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"
                + "?ServiceKey=" + serviceKey
                + "&pageNo=" + 1
                + "&numOfRows=" + 100
                + "&type=" + "json";
        System.out.println(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());

        List<DrugInfo> drugInfoList = new ArrayList<>();
        JsonNode bodyNode = jsonResponse.get("body");
        if (bodyNode != null) {
            JsonNode itemsNode = bodyNode.get("items");
            if (itemsNode != null && itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    DrugInfo drugInfo = new DrugInfo();

                    JsonNode entpNameNode = itemNode.get("entpName"); // 업체명
                    if (entpNameNode != null) {
                        drugInfo.setEntpName(entpNameNode.asText());
                    }

                    JsonNode itemNameNode = itemNode.get("itemName"); // 제품명
                    if (itemNameNode != null) {
                        drugInfo.setItemName(itemNameNode.asText());
                    }

                    JsonNode efcyQesitmNode = itemNode.get("efcyQesitm"); // 효능
                    if (efcyQesitmNode != null) {
                        drugInfo.setEfcyQesitm(efcyQesitmNode.asText());

                    }

                    JsonNode useMethodQesitmNode = itemNode.get("useMethodQesitm"); // 사용법
                    if (useMethodQesitmNode != null) {
                        drugInfo.setUseMethodQesitm(useMethodQesitmNode.asText());
                    }

                    JsonNode atpnWarnQesitmNode = itemNode.get("atpnWarnQesitm"); // 사용 전 주의사항
                    if (atpnWarnQesitmNode != null) {
                        drugInfo.setAtpnWarnQesitm(atpnWarnQesitmNode.asText());
                    }

                    JsonNode atpnQesitmNode = itemNode.get("atpnQesitm"); // 사용상 주의사항
                    if (atpnQesitmNode != null) {
                        drugInfo.setAtpnQesitm(atpnQesitmNode.asText());
                    }

                    JsonNode intrcQesitmNode = itemNode.get("intrcQesitm"); // 상호작용
                    if (intrcQesitmNode != null) {
                        drugInfo.setIntrcQesitm(intrcQesitmNode.asText());
                    }

                    JsonNode seQesitmNode = itemNode.get("seQesitm"); // 부작용
                    if (seQesitmNode != null) {
                        drugInfo.setSeQesitm(seQesitmNode.asText());
                    }

                    JsonNode depositMethodQesitmNode = itemNode.get("depositMethodQesitm"); // 보관법
                    if (seQesitmNode != null) {
                        drugInfo.setDepositMethodQesitm(depositMethodQesitmNode.asText());
                    }

                    JsonNode itemImageNode = itemNode.get("itemImage"); // 낱알이미지
                    if (itemImageNode != null) {
                        drugInfo.setItemImage(itemImageNode.asText());
                    }
                    drugInfoList.add(drugInfo);
                }
            }
        }
        return drugInfoList;
    }
}
