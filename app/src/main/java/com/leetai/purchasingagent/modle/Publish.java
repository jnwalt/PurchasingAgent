package com.leetai.purchasingagent.modle;

import java.io.Serializable;
import java.util.Date;

public class Publish implements Serializable {
    private Integer pId;

    private String pTitle;

    private String pDescription;

    private String pType;

    private Double pPrice;

    private String pImg;

   // private Integer pUserId;
private User pUser;
    private String pAddress;

    private Date pAddTime;

    private Integer pFlag;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle == null ? null : pTitle.trim();
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription == null ? null : pDescription.trim();
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType == null ? null : pType.trim();
    }

    public Double getpPrice() {
        return pPrice;
    }

    public void setpPrice(Double pPrice) {
        this.pPrice = pPrice;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg == null ? null : pImg.trim();
    }

//    public Integer getpUserId() {
//        return pUserId;
//    }
//
//    public void setpUserId(Integer pUserId) {
//        this.pUserId = pUserId;
//    }

    public String getpAddress() {
        return pAddress;
    }

    public User getpUser() {
		return pUser;
	}

	public void setpUser(User pUser) {
		this.pUser = pUser;
	}

	public void setpAddress(String pAddress) {
        this.pAddress = pAddress == null ? null : pAddress.trim();
    }

    public Date getpAddTime() {
        return pAddTime;
    }

    public void setpAddTime(Date pAddTime) {
        this.pAddTime = pAddTime;
    }

    public Integer getpFlag() {
        return pFlag;
    }

    public void setpFlag(Integer pFlag) {
        this.pFlag = pFlag;
    }
}