package com.medikok.backend.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocr")
public class OCRController {
    public static void main(String[] args) {
        String apiURL = "https://13zzspooc5.apigw.ntruss.com/custom/v1/31024/3ee28955498450db332f2b64c9223287b700fe7ca12bb794e813c11bb1c52678/general";
        String secretKey = "a1BCQlljTHBoUXJuQWpJbHB1eldNVVV6Rk9Oc1FPUWM=";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            json.put("lang", "ko");
            JSONObject image = new JSONObject();
            image.put("format", "jpg");

            // Read image file and convert to byte array
            byte[] imageData = readImageFileToByteArray("Please.jpg");
            if (imageData != null) {
                image.put("data", imageData);
                image.put("name", "demo");
                JSONArray images = new JSONArray();
                images.put(image);
                json.put("images", images);
                String postParams = json.toString();

                // Send POST request
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.write(postParams.getBytes(StandardCharsets.UTF_8));
                wr.flush();
                wr.close();

                // Get response
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
                }

                // Read response
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                // Save response to JSON file
                saveResponseToJsonFile(response.toString(), "please.json");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readImageFileToByteArray(String fileName) {
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveResponseToJsonFile(String jsonResponse, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8))) {
            writer.write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}