/**
 * 
 */
package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public abstract class ProductUtil {
	/**
	 * 门票 
	 */
	public final  static String PRODUCT_TYPE_TICKET="TICKET";
	/**
	 * 酒店
	 */
	public final  static String PRODUCT_TYPE_HOTEL="HOTEL";
	
	public final  static String SUB_PRODUCT_TYPE_FREENESS="FREENESS";
	public final static  String SUB_PRODUCT_TYPE_FREENESS_LONG="FREENESS_LONG";
	public final static  String SUB_PRODUCT_TYPE_SELFHELP_BUS="SELFHELP_BUS";
	
	
	public final static  String SUB_PRODUCT_TYPE_GROUP="GROUP";
	public final static  String SUB_PRODUCT_TYPE_GROUP_LONG="GROUP_LONG";
	
	
	public final static  String SUB_PRODUCT_TYPE_GROUP_FOREIGN="GROUP_FOREIGN";
	public final static  String SUB_PRODUCT_TYPE_FREENESS_FOREIGN="FREENESS_FOREIGN";
	
	/**
	 * 是否是酒店类型产品
	 * @param productType
	 * @param subProdcutType
	 * @return
	 */
	public static boolean isHotel(String productType ,String subProdcutType){
		
		return PRODUCT_TYPE_HOTEL.equals(productType);
	}
	/**
	 * 是否是门票类型产品
	 * @param productType
	 * @param subProdcutType
	 * @return
	 */
	public static boolean isTicket(String productType ,String subProdcutType){
		
		return PRODUCT_TYPE_TICKET.equals(productType);
	}
	/**
	 * 是否是跟团游
	 * @param productType
	 * @param subProdcutType
	 * @return
	 */
	public static boolean isGroup(String productType ,String subProdcutType){
		return SUB_PRODUCT_TYPE_GROUP.equals(subProdcutType)
		||SUB_PRODUCT_TYPE_GROUP_LONG.equals(subProdcutType);
	}
	/**
	 * 是否是自由行
	 * @param productType
	 * @param subProductType
	 * @return
	 */
	public static boolean isFreeness(String productType ,String subProductType){
		
		return SUB_PRODUCT_TYPE_FREENESS.equals(subProductType)
		||SUB_PRODUCT_TYPE_FREENESS_LONG.equals(subProductType)
		||SUB_PRODUCT_TYPE_SELFHELP_BUS.equals(subProductType);
	}
	/**
	 * 是否是出境游
	 * @param productType
	 * @param subProductType
	 * @return
	 */
	public static boolean isForeign(String productType ,String subProductType){
		return SUB_PRODUCT_TYPE_GROUP_FOREIGN.equals(subProductType)
		||SUB_PRODUCT_TYPE_FREENESS_FOREIGN.equals(subProductType);
	}
	public static String  getProductTypeDispName(String productType ,String subProductType){
		 
		if(isTicket(productType,subProductType)){
			return "门票";
		}
		if(isGroup(productType,subProductType)){
			return "国内游";
		}
		if(isFreeness(productType,subProductType)){
			return "自由行";
		}
		if(isForeign(productType,subProductType)){
			return "出境游";
		}
		if(isHotel(productType,subProductType)){
			return "国内酒店";
		}
		return "";
	}


	
	/**
	 * 取产品主类型对应的类别子类set code.
	 * @param productType
	 * @return
	 */
	public static String getBranchSetByType(String productType){
		return productType+"_BRANCH";
	}
	
	/**
	 * 取其他的子产品类型
	 * @return
	 */
	public static List<CodeItem> getOtherSubTypeList(){
		return getOtherSubTypeList(false);
	}
	public static List<CodeItem> getOtherSubTypeList(boolean containctVisa){
		List<CodeItem> list=new ArrayList<CodeItem>();
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.INSURANCE.name(),"保险"));
		if(containctVisa){
			list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.VISA.name(),"签证"));
		}
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.OWNEXPENSE.name(),"自费产品"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.TIP.name(),"小费"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.TAX.name(),"税金"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.EXPRESS.name(),"快递费"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.OTHER.name(),"其它"));
		return list;
	} 
	
	/**
	 * 酒店类型产品.
	 * @return
	 */
	public static List<CodeItem> getHotelSubTypeList(){
		List<CodeItem> list=new ArrayList<CodeItem>();
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name(),"单房间"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name(),"酒店套餐"));
		return list;
	}
	
	/**
	 * 读取产品子类型.
	 * @param productType
	 * @param blank
	 * @return
	 */
	public static List<CodeItem> getSubProductTypeList(String productType,boolean blank){
		List<CodeItem> list=null;
		if(Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)){
			list=getHotelSubTypeList();
		}else if(Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)){
			list=getRouteSubTypeList();
		}else if(Constant.PRODUCT_TYPE.OTHER.name().equals(productType)){
			list=getOtherSubTypeList();
		}else if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)){
			list=getTrafficSubTypeList();
		}
		if(list==null){
			list=new ArrayList<CodeItem>();
		}
		if(blank){
			list.add(0,new CodeItem("","请选择"));
		}
		return list;
	}
	
	private static final String[] metaSubProductType={"自由行",Constant.SUB_PRODUCT_TYPE.FREENESS.name(),"境内跟团游",Constant.SUB_PRODUCT_TYPE.GROUP.name(),"境外跟团游",Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),"境外自由行",Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()};

	/**
	 * 根据产品子类型的code码,得到产品子类型的CodeItem对象.
	 * @param subProductType 产品子类型.
	 * @return 返回CodeItem对象.
	 */
	public static CodeItem getSubProdTypeByCode(String subProductType) {
		for (CodeItem e : getOtherSubTypeList(true)) {
			if (e.getCode().equals(subProductType)) {
				return e;
			}
		}
		for (CodeItem e : getHotelSubTypeList()) {
			if (e.getCode().equals(subProductType)) {
				return e;
			}
		}
		for (CodeItem e : getTicketSubTypeList()) {
			if (e.getCode().equals(subProductType)) {
				return e;
			}
		}
		for (CodeItem e : getRouteSubTypeList()) {
			if (e.getCode().equals(subProductType)) {
				return e;
			}
		}
		for (CodeItem e : getTrafficSubTypeList()) {
			if (e.getCode().equals(subProductType)) {
				return e;
			}
		}
		/*
		if (Constant.SUB_PRODUCT_TYPE.FANGCHA.name().equals(subProductType)) {
			return new CodeItem(Constant.SUB_PRODUCT_TYPE.FANGCHA.name(),"房差");
		}*/
		throw new IllegalArgumentException("无效的产品子类型:[" + subProductType + "]");
	}
	

	public static List<CodeItem> getMetaRouteSubTypeList(){
		List<CodeItem> list=new ArrayList<CodeItem>();
		for(int i=0;i<metaSubProductType.length;i+=2){
			list.add(new CodeItem(metaSubProductType[i+1],metaSubProductType[i]));
		}
		
		return list;
	}	
	
	private static String[] subRouteProductTypes={"短途跟团游",Constant.SUB_PRODUCT_TYPE.GROUP.name(),"长途跟团游",Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(),"出境跟团游",Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),"目的地自由行",Constant.SUB_PRODUCT_TYPE.FREENESS.name(),"长途自由行",Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(),"出境自由行",Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(),"自助巴士班",Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name()};
	public static List<CodeItem> getRouteSubTypeList(){
		return getList(subRouteProductTypes);
	}
	/**
	 * 境外游名称（下拉列表）
	 * 按照不同的类型进行不同的选择
	 */
	//线路  //其他
	private static String[] regionNamesXLQT={"澳洲",Constant.REGION_NAMES.AOZHOU.name(),"东南亚",Constant.REGION_NAMES.DONGNANYA.name(),"东南亚海岛",Constant.REGION_NAMES.DONGNANYAHAIDAO.name(),"港澳",Constant.REGION_NAMES.GANGAO.name(),"美洲",Constant.REGION_NAMES.MEIZHOU.name(),"南亚",Constant.REGION_NAMES.NANYA.name(),"欧洲",Constant.REGION_NAMES.OUZHOU.name(),"日韩",Constant.REGION_NAMES.RIHAN.name(),"中东非",Constant.REGION_NAMES.ZHONGDONGFEI.name(),"签证",Constant.REGION_NAMES.QIANZHEN.name(),"邮轮",Constant.REGION_NAMES.YOULUN.name(),"特色产品",Constant.REGION_NAMES.TESECHANPIN.name()};
	public static List<CodeItem> getregionNamesXLQT(){
		return getList(regionNamesXLQT);
	}
	//门票  //酒店
	private static String[] regionNamesMPJD={"澳洲",Constant.REGION_NAMES.AOZHOU.name(),"东南亚",Constant.REGION_NAMES.DONGNANYA.name(),"东南亚海岛",Constant.REGION_NAMES.DONGNANYAHAIDAO.name(),"港澳",Constant.REGION_NAMES.GANGAO.name(),"美洲",Constant.REGION_NAMES.MEIZHOU.name(),"南亚",Constant.REGION_NAMES.NANYA.name(),"欧洲",Constant.REGION_NAMES.OUZHOU.name(),"日韩",Constant.REGION_NAMES.RIHAN.name(),"中东非",Constant.REGION_NAMES.ZHONGDONGFEI.name(),"特色产品",Constant.REGION_NAMES.TESECHANPIN.name()};
	public static List<CodeItem> getregionNamesMPJD(){
		return getList(regionNamesMPJD);
	}
	
	private static String[] subRouteProductTypes2={"目的地自由行",Constant.SUB_PRODUCT_TYPE.FREENESS.name(),"长途自由行",Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(),"出境自由行",Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()};
	/**
	 * 自主打包子类型.
	 * @return
	 */
	public static List<CodeItem> getRouteSPSubTypeList(){
		return getList(subRouteProductTypes2);
	}
	
	private static String[] subTicketProductTypes={"单门票",Constant.SUB_PRODUCT_TYPE.SINGLE.name(),"通票",Constant.SUB_PRODUCT_TYPE.WHOLE.name(),"联票",Constant.SUB_PRODUCT_TYPE.UNION.name(),"套票",Constant.SUB_PRODUCT_TYPE.SUIT.name()};
	public static List<CodeItem> getTicketSubTypeList(){
		return getList(subTicketProductTypes);
	}
	
	private static List<CodeItem> getList(String subProductTypes[]){
		List<CodeItem> list=new ArrayList<CodeItem>();
		for(int i=0;i<subProductTypes.length;i+=2){
			list.add(new CodeItem(subProductTypes[i+1], subProductTypes[i]));
		}
		return list;
	}
	
	final static String types[]={Constant.PRODUCT_TYPE.ROUTE.name(),Constant.PRODUCT_TYPE.TICKET.name()};
	
	/**
	 * 判断一个产品是否需要读取附加的类别还是全部的类别.
	 * @param productType
	 * @return
	 */
	public static boolean hasQueryBranchs(String productType){
		return (ArrayUtils.contains(types, productType));
	}
	
	public static boolean isAdultTicket(ProdProductBranch branch){
		return StringUtils.equals(branch.getBranchType(), Ticket.ADULT.name());
	}
	public static boolean isChildTicet(ProdProductBranch branch){
		return StringUtils.equals(branch.getBranchType(), Ticket.CHILD.name());
	}
	public static boolean isAdultRoute(ProdProductBranch branch){
		return StringUtils.equals(branch.getBranchType(), Route.ADULT.name());
	}
	/**
	 * 
	 * @param subProductType
	 * @return
	 */
	@Deprecated
	public static boolean isShow6Month(String subProductType,boolean additional){
		if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(subProductType)
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(subProductType)
				|| Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(subProductType)
				|| additional) {
			return true;
		}
		return false;
	}
	
	public enum Route{
		ADULT,
		CHILD,
		FANGCHA
	}
	
	public enum Ticket{
		ADULT,//成人票
		CHILD,//儿童票
		SUIT //套票
	}
	
	public enum Hotel{
		EXTRABED;//加床费
	}
	
	public static List<CodeItem> getTrafficSubTypeList(){
		List<CodeItem> list=new ArrayList<CodeItem>();
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.FLIGHT.name(),"机票"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.BUS.name(),"巴士"));
		list.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.TRAIN.name(),"火车票"));
		return list;
	}
	
	public static List<CodeItem> getDirectionTypeList(){
		List<CodeItem> list=new ArrayList<CodeItem>();
		list.add(new CodeItem(Constant.TRAFFIC_DIRECTION.SINGLE.name(),"单程"));
		list.add(new CodeItem(Constant.TRAFFIC_DIRECTION.ROUND.name(),"往返"));
		return list;
	}
	
	public static List<CodeItem> getTrafficBranchList(){
		List<CodeItem> list=new ArrayList<CodeItem>();
		list.add(new CodeItem(Constant.TRAFFIC_BRANCH_1.FIRST.name(),"头等舱"));
		list.add(new CodeItem(Constant.TRAFFIC_BRANCH_1.ECONOMY.name(),"经济舱"));
		return list;
	}
	
	/**
	 * 读取火车票类别类型列表
	 * @param blank
	 * @return
	 */
	public static List<CodeItem> getTrainBranchTypeList(boolean blank){
			List<CodeItem> list = new ArrayList<CodeItem>();
			if(blank){
				list.add(new CodeItem("","请选择"));
			}
			for(Constant.TRAIN_SEAT_CATALOG sc:Constant.TRAIN_SEAT_CATALOG.values()){
				list.add(new CodeItem(sc.getAttr1(),sc.getCnName()));
			}
			return list;
	}
	
}
