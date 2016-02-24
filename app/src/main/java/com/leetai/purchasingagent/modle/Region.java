package com.leetai.purchasingagent.modle;

public class Region {
    private Double sysRegionId;

    private String sysRegionCode;

    private String sysRegionName;

    private Double parentId;

    private Double sysRegionLevel;

    private Double sysRegionOrder;

    private String sysRegionNameEn;

    private String sysRegionShortnameEn;

    public Double getSysRegionId() {
        return sysRegionId;
    }

    public void setSysRegionId(Double sysRegionId) {
        this.sysRegionId = sysRegionId;
    }

    public String getSysRegionCode() {
        return sysRegionCode;
    }

    public void setSysRegionCode(String sysRegionCode) {
        this.sysRegionCode = sysRegionCode == null ? null : sysRegionCode.trim();
    }

    public String getSysRegionName() {
        return sysRegionName;
    }

    public void setSysRegionName(String sysRegionName) {
        this.sysRegionName = sysRegionName == null ? null : sysRegionName.trim();
    }

    public Double getParentId() {
        return parentId;
    }

    public void setParentId(Double parentId) {
        this.parentId = parentId;
    }

    public Double getSysRegionLevel() {
        return sysRegionLevel;
    }

    public void setSysRegionLevel(Double sysRegionLevel) {
        this.sysRegionLevel = sysRegionLevel;
    }

    public Double getSysRegionOrder() {
        return sysRegionOrder;
    }

    public void setSysRegionOrder(Double sysRegionOrder) {
        this.sysRegionOrder = sysRegionOrder;
    }

    public String getSysRegionNameEn() {
        return sysRegionNameEn;
    }

    public void setSysRegionNameEn(String sysRegionNameEn) {
        this.sysRegionNameEn = sysRegionNameEn == null ? null : sysRegionNameEn.trim();
    }

    public String getSysRegionShortnameEn() {
        return sysRegionShortnameEn;
    }

    public void setSysRegionShortnameEn(String sysRegionShortnameEn) {
        this.sysRegionShortnameEn = sysRegionShortnameEn == null ? null : sysRegionShortnameEn.trim();
    }
}