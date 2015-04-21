package com.lvmama.back.web.ord.sale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SaleServiceRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

/**
 * 订单售后服务类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class OrdSaleQueryAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserUserProxy userUserProxy;
	/**
	 * 售后管理服务
	 */
	private OrderService orderServiceProxy;
	private VstOrdOrderService vstOrdOrderService;
	private VstDistributorService vstDistributorService;
	private OrdSaleServiceDeal ordSaleServiceDeal;
	private List<OrdSaleService> ordSaleServiceList;
	private List<OrdSaleService> selectedOrdSaleServiceList;
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
	private PermUserService permUserService;
	private String operator = "";
	private OrdSaleServiceService ordSaleServiceService;
	
	public VstDistributorService getVstDistributorService() {
		return vstDistributorService;
	}
	public void setVstDistributorService(VstDistributorService vstDistributorService) {
		this.vstDistributorService = vstDistributorService;
	}
	/**
	 * 初始化查询参数.
	 */
	public void doBefore() throws Exception {
		serachSaleMap.put("saleStatus", "NORMAL");
		serachSaleMap.put("sysCode", Constant.COMPLAINT_SYS_CODE.SUPER.name());
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
		CompositeQuery compositeQuery=new CompositeQuery();
		SaleServiceRelate saleServiceRelate = new SaleServiceRelate();
		if(serachSaleMap!=null){
			String ordOrderId = serachSaleMap.get("ordOrderId");
			if(StringUtils.isNotEmpty(ordOrderId)){
				ordOrderId = ordOrderId.trim();
				saleServiceRelate.setSaleServiceOrderId(Long.valueOf(ordOrderId));
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("userName"))){
				saleServiceRelate.setSaleServiceUserName(serachSaleMap.get("userName").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("moblieNumber"))){
				saleServiceRelate.setSaleServiceMobile(serachSaleMap.get("moblieNumber").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("opearUserName"))){
				saleServiceRelate.setSaleServiceApplyName(serachSaleMap.get("opearUserName").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("orderType"))){
				saleServiceRelate.setSaleServiceOrderType(serachSaleMap.get("orderType").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("saleStatus"))){
				saleServiceRelate.setSaleStatus(serachSaleMap.get("saleStatus").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("takenOperator"))){
				saleServiceRelate.setTakenOperator(serachSaleMap.get("takenOperator").trim());
			}
			if(!StringUtil.isEmptyString(serachSaleMap.get("sysCode"))){
				saleServiceRelate.setSysCode(serachSaleMap.get("sysCode").trim());
			}
			String takenTime = serachSaleMap.get("takenTime");
			if(!StringUtil.isEmptyString(takenTime)){
				if(takenTime.equalsIgnoreCase("24")) {
					saleServiceRelate.setTakenTimeEnd(DateUtil.DsDay_Hour(new Date(), -24));
				} else if(takenTime.equalsIgnoreCase("48")){
					saleServiceRelate.setTakenTimeEnd(DateUtil.DsDay_Hour(new Date(), -48));
				} else {
					saleServiceRelate.setTakenTimeBegin(DateUtil.DsDay_Hour(new Date(), -48));
				}
			}
		}
		if(!StringUtil.isEmptyString(serviceType)){
			saleServiceRelate.setSaleServiceType(serviceType.trim());
		}
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if(saleBeginDate!=null){
			saleServiceRelate.setSaleServiceCreateTimeStart(saleBeginDate);
		}else{
			saleServiceRelate.setSaleServiceCreateTimeStart(DateUtil.dsDay_Date(new Date(), -7));
		}
		SimpleDateFormat  dateEndFormat=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");			
		if(saleEndDate!=null){
			saleServiceRelate.setSaleServiceCreateTimeEnd(saleEndDate);
		}else{
			saleServiceRelate.setSaleServiceCreateTimeEnd(new Date());
		}
		PageIndex pageIndex = new PageIndex();
		pageIndex.setEndIndex(50);
		List<SortTypeEnum> sortList=new ArrayList<SortTypeEnum> (); 
		sortList.add(SortTypeEnum.SALE_SERVICE_CREATE_TIME_DESC);
		compositeQuery.setTypeList(sortList);
		compositeQuery.setPageIndex(pageIndex);
		Map<String, Object> param = saleServiceRelate.getUserParam(saleServiceRelate);
		if(param != null) {
			saleServiceRelate.setUserIdList(saleServiceRelate.getUserList(userUserProxy.getUsers(param)));
		}
		compositeQuery.setSaleServiceRelate(saleServiceRelate);
		initialPageInfo(this.getOrderServiceProxy().queryOrdSaleServiceCount(compositeQuery),compositeQuery);
		ordSaleServiceList=this.getOrderServiceProxy().queryOrdSaleService(compositeQuery);
		List<String> userNoList=new ArrayList<String>();
		for (OrdSaleService sale : ordSaleServiceList) {
			Map<String, Long> map = new HashMap<String, Long>();
			map.put("orderId", sale.getOrderId());
			sale.setHasRefund(getOrdRefundMentService().findOrdRefundByParamCount(map).longValue() > 0l);
			System.out.println("sale.getSysCode() = "+sale.getSysCode());
			if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(sale.getSysCode())) {
				VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(sale.getOrderId());
				if(vstOrdOrderVo!=null) {
					UserUser userUser=userUserProxy.getUserUserByUserNo(vstOrdOrderVo.getUserId());
					long refundedAmount = orderServiceProxy.getRefundAmountByOrderId(sale.getOrderId(), sale.getSysCode());
					//设置订单来源渠道
//					if(vstOrdOrderVo.getDistributorId()!=null) {
//						vstOrdOrderVo.setDistributorName(vstDistributorService.getDistributorName(vstOrdOrderVo.getDistributorId()));	
//					}
					OrdOrder order = new OrdOrder().setProp(vstOrdOrderVo, userUser, refundedAmount);
					sale.setOrdOrder(order);
					userNoList.add(order.getUserId());
				}
			} else {
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(sale.getOrderId());
				sale.setOrdOrder(order);
				userNoList.add(order.getUserId());
			}
		}
		if(!userNoList.isEmpty()){
			List<UserUser> users=userUserProxy.getUsersListByUserNoList(userNoList);
			Map<String,UserUser> map=new HashMap<String, UserUser>();
			for(UserUser uu:users){
				map.put(uu.getUserId(), uu);
			}
			for (OrdSaleService sale : ordSaleServiceList) {
				OrdOrder o = sale.getOrdOrder();
				UserUser uu = map.get(o.getUserId());
				if(o != null && uu!=null) {
					o.setUserName(uu.getUserName());
					o.setMobileNumber(uu.getMobileNumber());
				}
			}
		}
	}
	
	public void doDeliver(Window win, Set<Listitem> set) throws Exception {
		selectedOrdSaleServiceList = this.getSelectItemList(set);
		if (selectedOrdSaleServiceList.size() == 0) {
			alert("请至少选择一个订单项进行分单！");
			return;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selectedOrdSaleServiceList", selectedOrdSaleServiceList);
		String url = "/ord/sale/editoperator.zul";
		Window window = (Window) Executions.createComponents(url, win, params);
		window.setWidth("300px");
		window.setMaximizable(true);
		window.setClosable(true);
		window.doModal();
	}
	
	private List<OrdSaleService> getSelectItemList(Set<Listitem> set) {
		List<OrdSaleService> list = new ArrayList<OrdSaleService>();
		if (set != null && set.size() > 0) {
			for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
				Listitem listitem = (Listitem) iter.next();
				OrdSaleService oss = (OrdSaleService) listitem.getValue();
				if(StringUtil.isEmptyString(oss.getTakenOperator())) {
					list.add(oss);
				}
			}
		}
		return list;
	}
	
	public void doSubmit() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", operator);
		PermUser user = permUserService.getPermUserByParams(params);
		if(user == null){
			alert("输入的用户不存在或者不可用");
			return;
		}
		List<Long> saleServiceIds = new ArrayList<Long>();
		for (OrdSaleService oss : selectedOrdSaleServiceList) {
			saleServiceIds.add(oss.getSaleServiceId());
		}
		params.put("takenOperator", operator);
		params.put("saleServiceIds", saleServiceIds);
		int rows = ordSaleServiceService.takeOrdSaleServiceByIds(params, this.getSessionUserName());
		if(rows > 0){
			alert("分配成功");
		}
		this.refreshParent("search");
		this.closeWindow();
	}
	
	public Map<String, String> getSerachSaleMap() {
		return serachSaleMap;
	}
	public void setSerachSaleMap(Map<String, String> serachSaleMap) {
		this.serachSaleMap = serachSaleMap;
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
	public void initServiceType(){
		serviceType = Executions.getCurrent().getParameter("serviceType");
	}

	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}
	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}
	public OrderService getOrderServiceProxy() {
		return (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	}
	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy.getBean("ordRefundMentService");
	}	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public OrdSaleServiceDeal getOrdSaleServiceDeal() {
		return ordSaleServiceDeal;
	}
	public void setOrdSaleServiceDeal(OrdSaleServiceDeal ordSaleServiceDeal) {
		this.ordSaleServiceDeal = ordSaleServiceDeal;
	}
	public List<CodeItem> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
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
	public String getPermId() {
		return permId;
	}
	public void setPermId(String permId) {
		this.permId = permId;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	public List<OrdSaleService> getSelectedOrdSaleServiceList() {
		return selectedOrdSaleServiceList;
	}
	public void setSelectedOrdSaleServiceList(
			List<OrdSaleService> selectedOrdSaleServiceList) {
		this.selectedOrdSaleServiceList = selectedOrdSaleServiceList;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setOrdSaleServiceService(OrdSaleServiceService ordSaleServiceService) {
		this.ordSaleServiceService = ordSaleServiceService;
	}
	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}
}
