package com.lvmama.distribution.model.lv;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.utils.StringUtil;

/**
 * 单个分销产品请求报文体
 * @author lipengcheng
 *
 */
public class ProductParameter {
	
	/** 产品Id*/
	private String productId;
	/** 产品价格：开始时间*/
	private String beginDate="";
	/** 产品价格：结束时间*/
	private String endDate="";
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLoaclSigned() {
		return this.getProductId() + this.getBeginDate() + this.getEndDate();
	}
	
	/**
	 * 分割产品ID数组串
	 * @return
	 */
	public List<Long> getProductIdList() {
		List<Long> idList = new ArrayList<Long>();
		String[] strIds = this.productId.split(",");
		for (String id : strIds) {
			idList.add(Long.valueOf(id));
		}
		return idList;
	}
	
	public String getProductId() {
		return StringUtil.replaceNullStr(productId);
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBeginDate() {
		return StringUtil.replaceNullStr(beginDate);
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return StringUtil.replaceNullStr(endDate);
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
