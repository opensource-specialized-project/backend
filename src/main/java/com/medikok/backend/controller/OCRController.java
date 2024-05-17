package com.medikok.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.asprise.ocr.Ocr;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.medikok.backend.entity.DrugInfoEntity;
import com.medikok.backend.service.MysqlService;

@RestController
public class OCRController {
    // private MysqlService mysqlService;

    // public OCRController(MysqlService mysqlService) {
    //     this.mysqlService = mysqlService;
    // }

    @GetMapping("/ocr")
    public void performOCR() {
        try {
            Ocr.setUp(); // one time setup
            Ocr ocr = new Ocr(); // create a new OCR engine
            ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
            String s = ocr.recognize(new File[] {new File("test.png")},
            Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT); // PLAINTEXT | XML | PDF | RTF
            System.out.println("Result: " + s);
            ocr.stopEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
