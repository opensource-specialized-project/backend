package com.medikok.backend.info;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DrugInfo {
    private String resultCode; // 결과코드
    private String resultMsg; // 결과메시지
    private int numOfRows; // 한 페이지 결과 수
    private int pageNo; // 페이지 번호
    private int totalCount; // 전체 결과 수
    private String entpName; // 업체명
    private String itemName; // 제품명
    private String itemSeq; // 품목기준코드
    private List<String> efcyQesitm; // 효능
    private String useMethodQesitm; // 사용법
    private String atpnWarnQesitm; // 사용 전 주의사항
    private String atpnQesitm; // 사용상 주의사항
    private String intrcQesitm; // 상호작용
    private String seQesitm; // 부작용
    private String depositMethodQesitm; // 보관법
    private String openDe; // 공개일자
    private String updateDe; // 수정일자
    private String itemImage; // 낱알이미지

    public String removeQuestionMarks(String s) {
        String pattern = "\\?";
        return s.replaceAll(pattern, " ").trim();

    }

    @JsonProperty("resultCode")
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @JsonProperty("resultMsg")
    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @JsonProperty("numOfRows")
    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    @JsonProperty("pageNo")
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @JsonProperty("totalCount")
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("entpName")
    public String getEntpName() {
        return entpName;
    }

    public void setEntpName(String entpName) {
        this.entpName = entpName;
    }

    @JsonProperty("itemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @JsonProperty("itemSeq")
    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    @JsonProperty("efcyQesitm")
    public List<String> getEfcyQesitm() {
        return efcyQesitm;
    }

    public void setEfcyQesitm(String efcyQesitm) {
        // 문장 사이에 있는 효능만 가져오기
        int startIndex = efcyQesitm.indexOf("이 약은");
        int endIndex = efcyQesitm.indexOf("에 사용합니다");
        String trimmedString = efcyQesitm;
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            trimmedString = efcyQesitm.substring(startIndex + "이 약은".length(), endIndex).trim();
        }

        // 문장 사이에 껴있는 물음표 제거
        trimmedString = removeQuestionMarks(trimmedString);

        // 콤마를 기준으로 문자열 분할
        String[] tokens = trimmedString.split(",");
        List<String> extractedList = new ArrayList<>();
        for (String token : tokens) {
            extractedList.add(token.trim());
        }

        this.efcyQesitm = extractedList;
    }

    @JsonProperty("useMethodQesitm")
    public String getUseMethodQesitm() {
        return useMethodQesitm;
    }

    public void setUseMethodQesitm(String useMethodQesitm) {
        this.useMethodQesitm = removeQuestionMarks(useMethodQesitm);
    }

    @JsonProperty("atpnWarnQesitm")
    public String getAtpnWarnQesitm() {
        return atpnWarnQesitm;
    }

    public void setAtpnWarnQesitm(String atpnWarnQesitm) {
        this.atpnWarnQesitm = removeQuestionMarks(atpnWarnQesitm);
    }

    @JsonProperty("atpnQesitm")
    public String getAtpnQesitm() {
        return atpnQesitm;
    }

    public void setAtpnQesitm(String atpnQesitm) {
        this.atpnQesitm = removeQuestionMarks(atpnQesitm);
    }

    @JsonProperty("intrcQesitm")
    public String getIntrcQesitm() {
        return intrcQesitm;
    }

    public void setIntrcQesitm(String intrcQesitm) {
        this.intrcQesitm = removeQuestionMarks(intrcQesitm);
    }

    @JsonProperty("seQesitm")
    public String getSeQesitm() {
        return seQesitm;
    }

    public void setSeQesitm(String seQesitm) {
        this.seQesitm = removeQuestionMarks(seQesitm);
    }

    @JsonProperty("depositMethodQesitm")
    public String getDepositMethodQesitm() {
        return depositMethodQesitm;
    }

    public void setDepositMethodQesitm(String depositMethodQesitm) {
        this.depositMethodQesitm = removeQuestionMarks(depositMethodQesitm);
    }

    @JsonProperty("openDe")
    public String getOpenDe() {
        return openDe;
    }

    public void setOpenDe(String openDe) {
        this.openDe = openDe;
    }

    @JsonProperty("updateDe")
    public String getUpdateDe() {
        return updateDe;
    }

    public void setUpdateDe(String updateDe) {
        this.updateDe = updateDe;
    }

    @JsonProperty("itemImage")
    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
