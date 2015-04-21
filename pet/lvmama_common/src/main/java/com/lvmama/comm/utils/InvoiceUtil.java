/**
 * 
 */
package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.vo.Constant;

/**
 * 发票相关操作工具类
 * @author yangbin
 *
 */
public abstract class InvoiceUtil {
	/**
	 * 根据结算主体(上海景域文化传播有限公司)对订单类型分类:门票,酒店,自由行.
	 */
	public static final String COMPANY_1 = Constant.INVOICE_COMPANY.COMPANY_1.name();
	/**
	 * 根据结算主体(上海驴妈妈国际旅行社有限公司)对订单类型分类:国内游.
	 */
	public static final String COMPANY_2 = Constant.INVOICE_COMPANY.COMPANY_2.name();
	/**
	 * 根据结算主体(兴旅)对订单类型分类:出境游.
	 */
	public static final String COMPANY_3 = Constant.INVOICE_COMPANY.COMPANY_3.name();
	
	/**
	 * 默认分隔符:",".
	 */
	private static final String DEFAULT_SEPARATOR = ",";
	

	/**
	 * 检查一个发票是否不修改到开票
	 * @param ordInvoice
	 * @return true不能修改
	 */
	public static boolean checkChangeBillAble(OrdInvoice ordInvoice){
		if (ordInvoice == null){
			return true;
		}
		
		return ArrayUtils.contains(UN_BILLED, ordInvoice.getStatus());
	}
	
	/**
	 * 能否审核
	 * @param ordInvoice
	 * @return true不能审核
	 */
	public static boolean checkChangeApproveAble(OrdInvoice ordInvoice){
		if (ordInvoice == null){
			return true;
		}
		
		return !ordInvoice.getStatus().equals(Constant.INVOICE_STATUS.UNBILL.name());
	}
	
	
	/**
	 * 能否修改发票号
	 * @param ordInvoice
	 * @return true不可以修改
	 */
	public static boolean checkChangeInvoiceNo(OrdInvoice ordInvoice){
		return ordInvoice == null
				|| (ordInvoice.getStatus().equals(
						Constant.INVOICE_STATUS.CANCEL.name()))
				|| (ordInvoice.getStatus().equals(
						Constant.INVOICE_STATUS.COMPLETE.name())
				||(ordInvoice.getStatus().equals(Constant.INVOICE_STATUS.RED.name())));
	}
	
	public static boolean checkChangeExpressNo(OrdInvoice ordInvoice){
		return ordInvoice==null||!ordInvoice.getStatus().equals(Constant.INVOICE_STATUS.BILLED.name());
	}
	private static String[] cancel_status_list={Constant.INVOICE_STATUS.UNBILL.name(),Constant.INVOICE_STATUS.APPROVE.name()};
	/**
	 * 
	 * @param ordInvoice
	 * @return true不能取消
	 */
	public static boolean checkUserCancelAble(OrdInvoice ordInvoice){
		return ordInvoice==null||!ArrayUtils.contains(cancel_status_list,ordInvoice.getStatus());
	}
	
	/**
	 * 不可以变更到开发票的状态的当前状态
	 */
	private static String UN_BILLED[] = {
			Constant.INVOICE_STATUS.CANCEL.name(),
			Constant.INVOICE_STATUS.COMPLETE.name(),
			Constant.INVOICE_STATUS.BILLED.name() };
	
	private static String company_2_type[] = {
			Constant.SUB_PRODUCT_TYPE.GROUP.name(),
			Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(),
			Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name(),
			Constant.PRODUCT_TYPE.OTHER.name(),
			Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()
	};	
	
	private static String company_1_type[]={
		Constant.PRODUCT_TYPE.TICKET.name(),
		Constant.PRODUCT_TYPE.HOTEL.name(),
		Constant.SUB_PRODUCT_TYPE.FREENESS.name()		
	};
	
	private static String company_3_type[]={
		Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(),
		Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name()
	};
	
	/**
	 * 分公司对应的线路产品全部转到兴旅	
	 */
	private static String company_3_type_route[]={
		Constant.SUB_PRODUCT_TYPE.GROUP.name(),
		Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(),
		Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name(),
		Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()
	};

	/**
	 * 按不同公司返回对应的发票产品类型
	 * @param company
	 * @return
	 */
	public static String[] getProductTypeByCompany(String company){
		if(StringUtils.isEmpty(company)){
			return new String[0];
		}
		if(company.equals(COMPANY_1)){
			return company_1_type;
		}else if(company.equals(COMPANY_2)){
			return company_2_type;
		}else if(company.equals(COMPANY_3)){
			return company_3_type;
		}
		return new String[0];
	}
 
	/**
	 * 根据不同结算主体标识返回相应的订单类型集合,订单类型集合以","号分隔拼接成字符串.
	 * @param company 结算主体标识 取值为:COMPANY_1,COMPANY_2,COMPANY_3其中之一.
	 * @return 返回订单类型以","分隔所拼接而成的字符串.
	 */
	public static String getProductTypeStringByCompany(String company) {
		String[] array = getProductTypeByCompany(company);
		StringBuilder sb = new StringBuilder();
		for (String ele : array) {
			sb.append(ele);
			sb.append(DEFAULT_SEPARATOR);
		}
		if (array.length > 0) {
			return sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}
	
 
	
	/**
	 * 按订单类型返回指定的code_type值
	 * @param orderType
	 * @return 不存在返回空值
	 */
	public static Constant.CODE_TYPE getInvoiceDetail(OrdOrder order){
		//分公司的处理
		if(ArrayUtils.contains(filiale_list, order.getFilialeName())&&ArrayUtils.contains(company_3_type_route, order.getOrderType())){
			return Constant.CODE_TYPE.COMPANY_3_CONTENT;
		}else if(ArrayUtils.contains(company_1_type, order.getOrderType())){
			return Constant.CODE_TYPE.COMPANY_1_CONTENT;
		}else if(ArrayUtils.contains(company_2_type, order.getOrderType())){
			return Constant.CODE_TYPE.COMPANY_2_CONTENT;
		}else if(ArrayUtils.contains(company_3_type, order.getOrderType())){
			return Constant.CODE_TYPE.COMPANY_3_CONTENT;
		}else{
			return null;
		}
	}
	
	private static String filiale_list[]={
		Constant.FILIALE_NAME.BJ_FILIALE.name(),
		Constant.FILIALE_NAME.CD_FILIALE.name(),
		Constant.FILIALE_NAME.GZ_FILIALE.name(),
		Constant.FILIALE_NAME.HZ_FILIALE.name()
	};
	
	/**
	 * 按订单的类型取发票的主体
	 * @param orderType
	 * @return
	 */
	public static Constant.INVOICE_COMPANY getInvoiceCompany(String orderType){
		if(ArrayUtils.contains(company_1_type, orderType)){
			return Constant.INVOICE_COMPANY.COMPANY_1;
		}else if(ArrayUtils.contains(company_2_type, orderType)){
			return Constant.INVOICE_COMPANY.COMPANY_2;
		}else if(ArrayUtils.contains(company_3_type, orderType)){
			return Constant.INVOICE_COMPANY.COMPANY_3;
		}
		return null;
	}
	
	public static Constant.INVOICE_COMPANY getInvoiceCompany(OrdOrder order){
		if(ArrayUtils.contains(filiale_list, order.getFilialeName())&&ArrayUtils.contains(company_3_type_route, order.getOrderType())){
			return Constant.INVOICE_COMPANY.COMPANY_3;
		}else{
			return getInvoiceCompany(order.getOrderType());
		}
			
	}
 
	/**
	 * 取发票内容对应的中文类型
	 * @param str
	 * @return
	 */
	public static String getZhInvoiceContent(String str){
		String array[]=StringUtils.split(str,",");
		StringBuffer sb=new StringBuffer();
		if(!ArrayUtils.isEmpty(array)){
			for(String tmp:array){
				sb.append(Constant.INVOICE_CONTENT.getCnName(tmp));
				sb.append(" ");
			}
		}
		return sb.toString().trim();
	}
	
	public static enum INVOICE_CONTENT{
		/**服务费**/
		SERVICE_CHARGE,
		/**旅游费**/
		TRAVEL_CHARGE,
		/**会务费**/
		COMMITTEE_CHARGE,
		/**住宿代理费**/
		REGISTER_PROXY_CHARGE,
		/**门票代理费**/
		TICKED_PROXY_CHARGE,
		/**团费**/
		GROUP_CHARGE,
		/**代理费（门票+酒店）**/
		TICKET_HOTEL_PROXY_CHARGE,
		/**旅游费（门票+酒店）**/
		TICKET_HOTEL_TRAVEL_CHARGE,
		/**门票代理费**/
		TICKET_PROXY_CHARGE,
		/**综合服务费**/
		INTEGRATION_CHARGE,
		/**签证费**/
		VISA_CHARGE
	}
	
	private static enum TYPE{
		C1,//团费、旅游费、会务费(单独成团，且包含会务的行程)
		C2,//代理费（门票+酒店）
		C3,//门票代理费,旅游费,服务费
		C4,//住宿代理费
		C5,//服务费
		C6//团费/旅游费/服务费
	}
	
	private static OrderInvoiceContent _instance=null;
	public static List<CodeItem> getInvoiceContents(String orderType,boolean blank){
		if(_instance==null){
			_instance=new OrderInvoiceContent();
			_instance.init();
		}
		return _instance.getList(orderType);
	}
	static class OrderInvoiceContent{
		//相同的内容的订单类型
		private Map<String,TYPE> orderTypeMap=new HashMap<String, TYPE>();
		
		//类型对应的内容列表
		Map<TYPE,List<CodeItem>> map=new HashMap<TYPE, List<CodeItem>>();
		
		public List<CodeItem> getList(String orderType){
			TYPE type=orderTypeMap.get(orderType);
			if(type==null){
				throw new IllegalArgumentException("订单类型不存在开票内容");
			}
			return map.get(type);
			
		}
		
		/**
		 * 初始化订单类型对应的发票内容
		 */
		synchronized void init(){
			
			
			//出境的发票内容
			initInvoiceContent(TYPE.C1,	INVOICE_CONTENT.GROUP_CHARGE,INVOICE_CONTENT.TRAVEL_CHARGE);
			
			initInvoiceContent(TYPE.C2, INVOICE_CONTENT.TICKET_HOTEL_PROXY_CHARGE,INVOICE_CONTENT.TRAVEL_CHARGE,INVOICE_CONTENT.TICKET_HOTEL_TRAVEL_CHARGE);
			initInvoiceContent(TYPE.C3, INVOICE_CONTENT.TICKET_PROXY_CHARGE,INVOICE_CONTENT.TRAVEL_CHARGE,INVOICE_CONTENT.SERVICE_CHARGE);
				
			initInvoiceContent(TYPE.C4, INVOICE_CONTENT.REGISTER_PROXY_CHARGE);
			//单签证
			initInvoiceContent(TYPE.C5, INVOICE_CONTENT.SERVICE_CHARGE);
			
			initInvoiceContent(TYPE.C6,	INVOICE_CONTENT.GROUP_CHARGE,INVOICE_CONTENT.TRAVEL_CHARGE);
			
			//出境跟团游
			orderTypeMap.put(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),TYPE.C1);
			//出境自由行
			orderTypeMap.put(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(),TYPE.C6);		
			
			orderTypeMap.put(Constant.PRODUCT_TYPE.TICKET.name(), TYPE.C3);
			
			orderTypeMap.put(Constant.PRODUCT_TYPE.HOTEL.name(),TYPE.C4);

			orderTypeMap.put(Constant.SUB_PRODUCT_TYPE.FREENESS.name(), TYPE.C2);
			
//			orderTypeMap.put(Constant.SUB_PRODUCT_TYPE.VISA.name(), TYPE.C5);
			
			for(String str:company_2_type){
				orderTypeMap.put(str,TYPE.C1);
			}	
		}
		
		private void initInvoiceContent(TYPE type,INVOICE_CONTENT...contents){
			List<CodeItem> list=new ArrayList<CodeItem>();
			for(INVOICE_CONTENT c:contents){
				list.add(new CodeItem(c.name(), Constant.INVOICE_CONTENT.getCnName(c.name())));
			}
			map.put(type, list);
		}
	}
}

