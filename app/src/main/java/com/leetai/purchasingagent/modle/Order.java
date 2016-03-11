package com.leetai.purchasingagent.modle;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	private Integer psId;

	// private Integer sId;
	private Bid bid;

	private Date psAddTime;

	private Integer psStatus;

	private String psExpressNo;

	public Integer getPsId() {
		return psId;
	}

	public void setPsId(Integer psId) {
		this.psId = psId;
	}

	// public Integer getsId() {
	// return sId;
	// }
	//
	// public void setsId(Integer sId) {
	// this.sId = sId;
	// }
	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public Date getPsAddTime() {
		return psAddTime;
	}

	public void setPsAddTime(Date psAddTime) {
		this.psAddTime = psAddTime;
	}

	public Integer getPsStatus() {
		return psStatus;
	}

	public void setPsStatus(Integer psStatus) {
		this.psStatus = psStatus;
	}

	public String getPsExpressNo() {
		return psExpressNo;
	}

	public void setPsExpressNo(String psExpressNo) {
		this.psExpressNo = psExpressNo == null ? null : psExpressNo.trim();
	}
}