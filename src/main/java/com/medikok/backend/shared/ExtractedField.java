package com.medikok.backend.shared;

public class ExtractedField {
    private String inferText;
    private float inferConfidence;
    private BoundingPoly boundingPoly;

    // 생성자
    public ExtractedField(String inferText, float inferConfidence, BoundingPoly boundingPoly) {
        this.inferText = inferText;
        this.inferConfidence = inferConfidence;
        this.boundingPoly = boundingPoly;
    }

    // Getter 및 Setter 메서드
    public String getInferText() {
        return inferText;
    }

    public void setInferText(String inferText) {
        this.inferText = inferText;
    }

    public float getInferConfidence() {
        return inferConfidence;
    }

    public void setInferConfidence(float inferConfidence) {
        this.inferConfidence = inferConfidence;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    @Override
    public String toString() {
        return "ExtractedField{" +
                "inferText='" + inferText + '\'' +
                ", inferConfidence=" + inferConfidence +
                ", boundingPoly=" + boundingPoly +
                '}';
    }
}
