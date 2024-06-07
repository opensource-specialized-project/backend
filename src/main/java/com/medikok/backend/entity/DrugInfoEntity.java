package com.medikok.backend.entity;

// jakarta
import jakarta.persistence.Table; // 테이블 지정
import jakarta.persistence.Entity; // 데이터베이스 테이블과 매핑되는 클래스
import jakarta.persistence.FetchType;
import jakarta.persistence.Id; // 엔터티의 기본 키를 지정
import jakarta.persistence.Lob;
import jakarta.persistence.GeneratedValue; // 기본 키 값에 대한 생성자 제공
import jakarta.persistence.GenerationType; // 기본 키 값에 대한 생성 타입

import java.util.List;

import com.medikok.backend.shared.DrugInfo;

import java.util.ArrayList;
import java.util.Arrays;

import jakarta.persistence.Basic;
import jakarta.persistence.Column; // 열 지정

@Table(name = "drug_info")
@Entity
public class DrugInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = true) // 제품명
    private String itemName;

    @Column(name = "efcy_qesitm", nullable = true) // 효능
    private String efcyQesitm;

    @Column(name = "use_method_qesitm", nullable = true, columnDefinition = "MEDIUMBLOB") // 사용법
    @Lob
    @Basic(fetch = FetchType.LAZY) // 'mediumblob'에 대한 매핑 전략으로 설정
    private byte[] useMethodQesitm;

    @Column(name = "atpn_warn_qesitm", nullable = true, columnDefinition = "MEDIUMBLOB") // 사용 전 주의사항
    @Lob
    @Basic(fetch = FetchType.LAZY) // 'mediumblob'에 대한 매핑 전략으로 설정
    private byte[] atpnWarnQesitm;

    @Column(name = "atpn_qesitm", nullable = true, columnDefinition = "MEDIUMBLOB") // 사용 상 주의사항
    @Lob
    @Basic(fetch = FetchType.LAZY) // 'mediumblob'에 대한 매핑 전략으로 설정
    private byte[] atpnQesitm;

    @Column(name = "intrc_qesitm", nullable = true, columnDefinition = "MEDIUMBLOB") // 상호작용
    @Lob
    @Basic(fetch = FetchType.LAZY) // 'mediumblob'에 대한 매핑 전략으로 설정
    private byte[] intrcQesitm;

    @Column(name = "se_qesitm", nullable = true, columnDefinition = "MEDIUMBLOB") // 부작용
    @Lob
    @Basic(fetch = FetchType.LAZY) // 'mediumblob'에 대한 매핑 전략으로 설정
    private byte[] seQesitm;

    @Column(name = "deposit_method_qesitm", nullable = true, columnDefinition = "MEDIUMBLOB") // 보관법
    @Lob
    @Basic(fetch = FetchType.LAZY) // 'mediumblob'에 대한 매핑 전략으로 설정
    private byte[] depositMethodQesitm;

    @Column(name = "item_image", nullable = true, columnDefinition = "MEDIUMBLOB") // 이미지
    private String itemImage;

    public DrugInfoEntity() {
        this.useMethodQesitm = new byte[0];
        this.atpnWarnQesitm = new byte[0];
        this.atpnQesitm = new byte[0];
        this.intrcQesitm = new byte[0];
        this.seQesitm = new byte[0];
        this.depositMethodQesitm = new byte[0];
    }
    
    public DrugInfoEntity(String itemName, String efcyQesitm) {
        this();
        setItemName(itemName);
        setEfcyQesitm(efcyQesitm);
    }

    // DrugInfo를 통한 DrugInfoEntity set
    public void setDrugInfoEntity(DrugInfo drugInfo) {
        setItemName(drugInfo.getItemName()); // 제품명 set
        setEfcyQesitm(drugInfo.getEfcyQesitm()); // 효능 set
        setUseMethodQesitm(drugInfo.getUseMethodQesitm()); // 사용법 set
        setAtpnWarnQesitm(drugInfo.getAtpnWarnQesitm()); // 사용 전 주의사항 set
        setAtpnQesitm(drugInfo.getAtpnQesitm()); // 사용상 주의사항 set
        setIntrcQesitm(drugInfo.getIntrcQesitm()); // 상호작용 set
        setSeQesitm(drugInfo.getSeQesitm()); // 부작용 set
        setDepositMethodQesitm(drugInfo.getDepositMethodQesitm()); // 보관법 set
        setItemImage(drugInfo.getItemImage()); // 이미지 set
    }

    // 제품명 getter, setter
    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // 효능 getter, setter
    public List<String> getEfcyQesitm() {
        if (this.efcyQesitm != null && !this.efcyQesitm.isEmpty()) {
            return Arrays.asList(this.efcyQesitm.split(",")); // ,로 구분하여 효능들을 리스트 형식으로 반환
        } else {
            return new ArrayList<>(); // 효능이 없다면 빈 리스트로 반환
        }
    }

    public void setEfcyQesitm(List<String> efcyQesitm) {
        this.efcyQesitm = String.join(",", efcyQesitm); // 리스트 요소들을 ,로 묶어 문자열 형식으로 저장
    }   
    
    public void setEfcyQesitm(String efcyQesitm) {
        this.efcyQesitm = efcyQesitm;
    }

    // 사용법 getter, setter
    public String getUseMethodQesitm() {
        return new String(this.useMethodQesitm);
    }

    public void setUseMethodQesitm(String useMethodQesitm) {
        this.useMethodQesitm = useMethodQesitm.getBytes();
    }

    // 사용 전 주의사항 getter, setter
    public String getAtpnWarnQesitm() {
        return new String(this.atpnWarnQesitm);
    }

    public void setAtpnWarnQesitm(String atpnWarnQesitm) {
        this.atpnWarnQesitm = atpnWarnQesitm.getBytes();
    }

    // 사용상 주의사항 getter, setter
    public String getAtpnQesitm() {
        return new String(this.atpnQesitm);
    }

    public void setAtpnQesitm(String atpnQesitm) {
        this.atpnQesitm = atpnQesitm.getBytes();
    }

    // 상호작용 getter, setter
    public String getIntrcQesitm() {
        return new String(this.intrcQesitm);
    }

    public void setIntrcQesitm(String intrcQesitm) {
        this.intrcQesitm = intrcQesitm.getBytes();
    }

    // 부작용 getter, setter
    public String getSeQesitm() {
        return new String(this.seQesitm);
    }
    
    public void setSeQesitm(String seQesitm) {
        this.seQesitm = seQesitm.getBytes();
    }

    // 보관법 getter, setter
    public String getDepositMethodQesitm() {
        return new String(this.depositMethodQesitm);
    }

    public void setDepositMethodQesitm(String depositMethodQesitm) {
        this.depositMethodQesitm = depositMethodQesitm.getBytes();
    }

    // 이미지 getter, setter
    public String getItemImage() {
        return this.itemImage;
    }
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

}