package com.medikok.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

import org.springframework.web.bind.annotation.GetMapping;


import com.asprise.ocr.Ocr;

@RestController
@RequestMapping("/test-request")
public class TestController {
    @GetMapping("/ocr") 
    public void testOcr() {
        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
        String s = ocr.recognize(new File[] {new File("test.png")},
        Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT); // PLAINTEXT | XML | PDF | RTF
        System.out.println("Result: " + s);
        ocr.stopEngine();
    }
}
