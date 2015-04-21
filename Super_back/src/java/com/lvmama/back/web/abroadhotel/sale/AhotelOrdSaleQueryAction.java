package com.lvmama.back.web.abroadhotel.sale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 订单售后服务类.
 *
 * @author liwenzhan
 *
 */
@SuppressWarnings("unused")
public class AhotelOrdSaleQueryAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserUserProxy userUserProxy;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	private List<AhotelOrdSaleService> ordSaleServiceList;
	List<CodeItem> channelList;
	private String serviceType;
	private String serviceTypeName;
	/**
	 * 售后服务综合查询封装.
	 */
	private Map<String,String> serachSaleMap=new HashMap<String,String>();
	private Date saleBeginDate;
	private Date saleEndDate;
	private String permId;
	private Long ordOrderId;
	private Map<String, Object> searchRefundMentMap = new HashMap<String, Object>();
	/**
	 * 初始化查询参数.
	 */
	public void doBefore() throws Exception {
		serachSaleMap.put("saleStatus", "NORMAL");
		initServiceType();
	}
	public void transitD(){
		channelList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.SERVICE_TYPE.name());
	}
	
	
	/**
	 * 综合查询所有售后服务查询,包含(常规售后,紧急入园,投诉)查询.
	 * @return
	 * @throws ParseException 
	 */
	public void queryOrderSale() throws ParseException{
		if(searchRefundMentMap!=null){
			searchRefundMentMap.clear();
		}
		if(serachSaleMap!=null){
			if(ordOrderId != null){
				searchRefundMentMap.put("orderId",ordOrderId);
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("opearUserName"))){
				searchRefundMentMap.put("operatorName",serachSaleMap.get("opearUserName").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("saleStatus"))){
				searchRefundMentMap.put("status",serachSaleMap.get("saleStatus").trim());
			}
		}
		if(!StringUtil.isEmptyString(serviceType)){
			searchRefundMentMap.put("serviceType",serviceType.trim());
		}
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if(saleBeginDate!=null){
			searchRefundMentMap.put("saleBeginDate", saleBeginDate);
		}else{
			searchRefundMentMap.put("saleBeginDate", DateUtil.dsDay_Date(new Date(), -7));
		}
		SimpleDateFormat  dateEndFormat=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");			
		if(saleEndDate!=null){
			searchRefundMentMap.put("saleEndDate",saleEndDate);
		}else{
			searchRefundMentMap.put("saleEndDate", new Date());
		}
		
		Map map=initialPageInfoByMap(abroadhotelOrderService.findAhotelOrdSaleServiceByParamCount(searchRefundMentMap),searchRefundMentMap);
		int skipResults=0;
		int maxResults=10;
		if(map.get("skipResults")!=null){ 
			skipResults=Integer.parseInt(map.get("skipResults").toString());
		}
		if(map.get("maxResults")!=null){
			maxResults=Integer.parseInt(map.get("maxResults").toString());
		}
		searchRefundMentMap.put("skipResults",skipResults);
		searchRefundMentMap.put("maxResults",maxResults);
		ordSaleServiceList=abroadhotelOrderService.findAhotelOrdSaleServiceByParam(searchRefundMentMap,skipResults,maxResults);
		for(AhotelOrdSaleService ordSaleService : ordSaleServiceList){
			if(ordSaleService.getOrdOrder() != null){
				String userId = ordSaleService.getOrdOrder().getUserId();
				if(userId != null && !userId.equals("")){
					UserUser user = userUserProxy.getUserUserByUserNo(ordSaleService.getOrdOrder().getUserId());
					ordSaleService.getOrdOrder().setUserId(user.getUserName());
				}
			}
		}
	}
	
	public void initServiceType(){
		serviceType = Executions.getCurrent().getParameter("serviceType");
	}
	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
	}
	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}
	public List<AhotelOrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}
	public void setOrdSaleServiceList(List<AhotelOrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}
	public List<CodeItem> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceTypeName() {
		return Constant.SERVICE_TYPE.getCnName(this.serviceType);
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public Map<String, String> getSerachSaleMap() {
		return serachSaleMap;
	}
	public void setSerachSaleMap(Map<String, String> serachSaleMap) {
		this.serachSaleMap = serachSaleMap;
	}
	public Date getSaleBeginDate() {
		return saleBeginDate;
	}
	public void setSaleBeginDate(Date saleBeginDate) {
		this.saleBeginDate = saleBeginDate;
	}
	public Date getSaleEndDate() {
		return saleEndDate;
	}
	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}
	public Map<String, Object> getSearchRefundMentMap() {
		return searchRefundMentMap;
	}
	public void setSearchRefundMentMap(Map<String, Object> searchRefundMentMap) {
		this.searchRefundMentMap = searchRefundMentMap;
	}
	public String getPermId() {
		return permId;
	}
	public void setPermId(String permId) {
		this.permId = permId;
	}
	
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public Long getOrdOrderId() {
		return ordOrderId;
	}
	public void setOrdOrderId(Long ordOrderId) {
		this.ordOrderId = ordOrderId;
	}
}
