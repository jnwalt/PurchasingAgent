package com.leetai.purchasingagent.modle;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
 

	private Bid bid;
 
	
	
	
	 private Integer psId;

	    private Integer pId;

	   // private Integer sId;

	    private Integer pUserId;

	    private Integer sUserId;

	    private Date psAddTime;

	    private Integer psStatus;

	    private String psExpressNo;

		public Bid getBid() {
			return bid;
		}

		public void setBid(Bid bid) {
			this.bid = bid;
		}

		public Integer getPsId() {
			return psId;
		}

		public void setPsId(Integer psId) {
			this.psId = psId;
		}

		public Integer getpId() {
			return pId;
		}

		public void setpId(Integer pId) {
			this.pId = pId;
		}

		public Integer getpUserId() {
			return pUserId;
		}

		public void setpUserId(Integer pUserId) {
			this.pUserId = pUserId;
		}

		public Integer getsUserId() {
			return sUserId;
		}

		public void setsUserId(Integer sUserId) {
			this.sUserId = sUserId;
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
			this.psExpressNo = psExpressNo;
		}

	     

}