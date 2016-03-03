package com.leetai.purchasingagent.modle;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable {
	private Integer sId;

	private Double sPrice;

	// private Integer sUserId;
	private User sUser;

	private String sAddress;

	// private Integer pId;
	private Publish publish;
	
	private Date sAddTime;

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

	// public Integer getsUserId() {
	// return sUserId;
	// }
	//
	// public void setsUserId(Integer sUserId) {
	// this.sUserId = sUserId;
	// }
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

//	public Integer getpId() {
//		return pId;
//	}
//
//	public void setpId(Integer pId) {
//		this.pId = pId;
//	}

	public Date getsAddTime() {
		return sAddTime;
	}

	public Publish getPublish() {
		return publish;
	}

	public void setPublish(Publish publish) {
		this.publish = publish;
	}

	public void setsAddTime(Date sAddTime) {
		this.sAddTime = sAddTime;
	}
}