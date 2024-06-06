package com.medikok.backend.shared;

import java.util.ArrayList;
import java.util.List;

public class ExtractedText {
    private List<ExtractedField> fields;

    // 기본 생성자
    public ExtractedText() {
        this.fields = new ArrayList<>();
    }

    // JSON 데이터로부터 데이터를 설정하는 메서드
    public void setFieldsFromJson(List<ExtractedField> fields) {
        this.fields = fields;
    }

    // Getter 및 Setter 메서드
    public List<ExtractedField> getFields() {
        return fields;
    }

    public void setFields(List<ExtractedField> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "ExtractedText{" +
                "fields=" + fields +
                '}';
    }
}
