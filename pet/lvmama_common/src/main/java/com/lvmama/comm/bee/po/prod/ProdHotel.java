package com.lvmama.comm.bee.po.prod;

import com.lvmama.comm.vo.Constant;

public class ProdHotel extends ProdProduct {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4923696252904185710L;
	/**
	 * 默认入住天数:一天.
	 */
	private static final int DEFAULT_STAY_DAYS = 1;
	
	private Long hotelId;

	private Integer days;// 入住天数 
	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getProductType() {
		return Constant.PRODUCT_TYPE.HOTEL.name();
	}

	/**
	 * 获取入住天数,如果数据库中取不到有效值,则取默认值:一天.
	 * @return 返回入住天数.
	 */
	public Integer getDays() {
		if (days == null || days == 0) {
			return ProdHotel.DEFAULT_STAY_DAYS;
		}
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}