/**
 * 
 */
/**
 * @author yangbin
 *
 */
package com.lvmama.shholiday.response;


/**明细类型*/
enum TRIP_DETAILTYPE{
	/**早餐*/
	BR("早餐"),
	/**中餐 */
	LU("中餐") ,
	/**晚餐 */
	SU("晚餐"),
	/**住宿 */
	AC("住宿 "),
	/**其它类型 */
	OTHER("其它类型");
	private String cnName;
	TRIP_DETAILTYPE(String name){
		this.cnName=name;
	}
	public String getCode(){
		return this.name();
	}
	public String getCnName(){
		return this.cnName;
	}
	public static String getCnName(String code){
		for(TRIP_DETAILTYPE item : TRIP_DETAILTYPE.values()){
			if(item.getCode().equals(code))
			{
				return item.getCnName();
			}
		}
		return code;
	}
	public String toString(){
		return this.name();
	}
}

/**明细类型取值*/
enum TRIP_DETAILTYPEVAL{
	/**有 */
	Y("含"),
	/** 无*/
	N("敬请自理");
	private String cnName;
	TRIP_DETAILTYPEVAL(String name){
		this.cnName=name;
	}
	public String getCode(){
		return this.name();
	}
	public String getCnName(){
		return this.cnName;
	}
	public static String getCnName(String code){
		for(TRIP_DETAILTYPEVAL item : TRIP_DETAILTYPEVAL.values()){
			if(item.getCode().equals(code))
			{
				return item.getCnName();
			}
		}
		return code;
	}
	public String toString(){
		return this.name();
	}
}

/**产品提示类型*/
enum NOTICE_TYPE{
	/**产品经理推荐*/
	P_MANAGER_RECOMMAND,
	/**优惠活动 */
	SPECIAL_DISCOUNT,
	/**游客须知 */
	TAKER_NOTICE,
	/**销售须知 */
	SALEINFO,	
	/**预订限制 */
	BOOK_LIMIT,	
	/**出行警示 */
	TRAVEL_WARN,	
	/**抵用券 */
	COUPON,	
	/**费用包含 */
	FEE_CONTAIN,	
	/**费用不包含 */
	FEE_NOT_CONTAIN,	
	/**自费项目 */
	TUIJIAN_L_F_F_X_M,
	/**景点特色*/
	JDTS,
	/**景点游览时间*/
	JDYLSJ,
	/**行程特色*/
	CHANPINTESE,
	/**增值服务*/
	ZZFU,
	/**线路说明*/
	XLSM
}