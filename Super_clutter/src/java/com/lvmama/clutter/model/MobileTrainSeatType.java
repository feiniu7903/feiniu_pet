package com.lvmama.clutter.model;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 火车座位类别信息. 
 *
 */
public class MobileTrainSeatType {
	/**
	 * 座位类别
	 */
	 private String seatType;
	 
	/**
	  * 价格 
	  */
	 private Long price;
	 /**
	  * 产品类别 
	  */
	 private Long prodBranchId;
	 
	 /**
	  * 是否可售
	  */
	 private boolean canSell ;
	 
	 public String getZhSeatType(){ 
	    	return Constant.TRAIN_SEAT_CATALOG.getCnName(seatType);
	    }
	 
	 public float getPriceYuan() {
		 return this.price == null ? 0 : PriceUtil.convertToYuan(this.price);
	 }
	 
	 public String getSeatType() {
			return seatType;
		}

		public void setSeatType(String seatType) {
			this.seatType = seatType;
		}

		public Long getPrice() {
			return price;
		}

		public void setPrice(Long price) {
			this.price = price;
		}

		public Long getProdBranchId() {
			return prodBranchId;
		}

		public void setProdBranchId(Long prodBranchId) {
			this.prodBranchId = prodBranchId;
		}

		public boolean isCanSell() {
			return canSell;
		}

		public void setCanSell(boolean canSell) {
			this.canSell = canSell;
		}


}
