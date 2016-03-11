package com.leetai.purchasingagent.modle;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable {
    private Integer sId;

    private Double sPrice;

    private User sUser;

    private String sAddress;

    private Publish publish;

    private Date sAddTime;

    private String sMemo;

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Double getsPrice() {
        return sPrice;
    }

    public void setsPrice(Double sPrice) {
        this.sPrice = sPrice;
    }
 
	public User getsUser() {
		return sUser;
	}

	public void setsUser(User sUser) {
		this.sUser = sUser;
	}

	public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress == null ? null : sAddress.trim();
    }
 
    public Publish getPublish() {
		return publish;
	}

	public void setPublish(Publish publish) {
		this.publish = publish;
	}

	public Date getsAddTime() {
        return sAddTime;
    }

    public void setsAddTime(Date sAddTime) {
        this.sAddTime = sAddTime;
    }

    public String getsMemo() {
        return sMemo;
    }

    public void setsMemo(String sMemo) {
        this.sMemo = sMemo == null ? null : sMemo.trim();
    }
}