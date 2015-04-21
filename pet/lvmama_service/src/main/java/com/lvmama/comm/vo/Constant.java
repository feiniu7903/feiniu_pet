package com.lvmama.comm.vo;
import java.util.Properties;

public class Constant {

	private static Properties properties;
	private static volatile Constant instance=null;
	public static final String LV_SESSION_ID="lvsessionid";
	
	/**
	 * 分公司
	 * @author Libo Wang 2012-6-26
	 * @version
	 */
	public static enum FILIALE_NAME {
		SH_FILIALE("上海总部"),
		BJ_FILIALE("北京分部"),
		GZ_FILIALE("广州分部"),
		CD_FILIALE("成都分部"),
		SY_FILIALE("三亚分部"),
		HS_FILIALE("黄山办事处"),
		HZ_FILIALE("杭州分部");
		
		private String cnName;
		FILIALE_NAME(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(FILIALE_NAME item:FILIALE_NAME.values()){
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
	public static enum ORDER_STATUS {
		/** 正常 */
		NORMAL("正常"),
		/** 取消 */
		CANCEL("取消"),
		/** 完成 （结束） */
		FINISHED("完成"),
		/** 未确认结束 */
		UNCONFIRM("未确认结束");
		
		private String cnName;
		ORDER_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(ORDER_STATUS item:ORDER_STATUS.values()){
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
	public static enum SETTLEMENT_COMPANY {
		/** 上海景域文化传播有限公司 */
		COMPANY_1("上海景域文化传播有限公司"),
		/** 上海景域旅行社有限公司 */
		COMPANY_2("上海驴妈妈国际旅行社有限公司"),
		/**兴旅**/
		COMPANY_3("上海驴妈妈兴旅国际旅行社有限公司");
		
		private String cnName;
		SETTLEMENT_COMPANY(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_COMPANY item:SETTLEMENT_COMPANY.values()){
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
	/**
	 * 订单结算项的状态
	 */
	public static enum SET_SETTLEMENT_ITEM_STATUS {
		NORMAL("正常"),
		CANCEL("取消"),
		NOSETTLEMENT("不结算");
		private String cnName;
		SET_SETTLEMENT_ITEM_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SET_SETTLEMENT_ITEM_STATUS item:SET_SETTLEMENT_ITEM_STATUS.values()){
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
	public static enum SETTLEMENT_STATUS {
		/** 未结算 */
		UNSETTLEMENTED("未结算"),
		/** 已确认 （不使用）*/
		CONFIRMED("已确认"),
		/** 争议单（不使用） */
		DISPUTED("争议单"),
		/** 已结算 */
		SETTLEMENTED("已结算"),
		/** 结算中 */
		SETTLEMENTING("结算中"),
		NOSETTLEMENT("不结算");
		
		private String cnName;
		SETTLEMENT_STATUS(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_STATUS item:SETTLEMENT_STATUS.values()){
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
	public static enum SETTLEMENT_PERIOD {
		/** 每单结算 */
		PERORDER("每单结算"),
		/** 按月结算 */
		PERMONTH("按月结算"),
		/** 按季结算 */
		PERQUARTER("按季结算"),
		/**按周结算*/
		PER_WEEK("按周结算"),
		/**按半月结算*/
		PER_HALF_MONTH("按半月结算");
		
		private String cnName;
		SETTLEMENT_PERIOD(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SETTLEMENT_PERIOD item:SETTLEMENT_PERIOD.values()){
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
	public static enum SUB_PRODUCT_TYPE {
		/**单票，单一门票 */
		SINGLE("单票"),
		/**套票，同一景区多人票组合 */
		SUIT("套票"),
		/**联票，不同景区组合 */
		UNION("联票"),
		/**通票，同一景区所有项目组合 */
		WHOLE("通票"),
		/**境内自由行,单人出发 */
		FREENESS("目的地自由行"),
		/**境内跟团游,多人成团 */
		GROUP("短途跟团游"),
		/**境外自由行,单人出发 */
		FREENESS_FOREIGN("出境自由行"),
		/**境外跟团游,多人成团 */
		GROUP_FOREIGN("出境跟团游"),
		/**保险 */
		INSURANCE("保险"),
		/**单房间 */
		SINGLE_ROOM("单房间 "),
		/**酒店套餐 */
		HOTEL_SUIT("酒店套餐"),
		/**境外酒店 */
		HOTEL_FOREIGN("境外酒店"),
		/**长途跟团游*/
		GROUP_LONG("长途跟团游"),
		/**长途自由行*/
		FREENESS_LONG("长途自由行"),
		/**自助巴士班*/
		SELFHELP_BUS("自助巴士班"),
		/**签证**/
		VISA("签证"),
		/**其它*/
		OTHER("其它"),
		/**自费*/
		OWNEXPENSE("自费"),
		/**港务税*/
		PORTTAX("港务税"),
		/**税金**/
		TAX("税金"),
		/**快递费**/
		EXPRESS("快递费"),
		/** 房差 */
		FANGCHA("房差"),
		/**小费*/
		TIP("小费"),
		/**机票*/
		FLIGHT("机票"),
		/**火车票**/
		TRAIN("火车票"),
		/**巴士*/
		BUS("巴士");
		
		private String cnName;
		SUB_PRODUCT_TYPE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(SUB_PRODUCT_TYPE item:SUB_PRODUCT_TYPE.values()){
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
}

