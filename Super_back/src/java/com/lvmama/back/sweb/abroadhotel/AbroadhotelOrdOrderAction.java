package com.lvmama.back.sweb.abroadhotel;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.abroad.constants.AbroadHotelConstant;
import com.lvmama.comm.abroad.po.AhotelOrdPayment;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.service.ICancelReservation;
import com.lvmama.comm.abroad.service.IReservationsOrder;
import com.lvmama.comm.abroad.vo.request.ReservationsOrderReq;
import com.lvmama.comm.abroad.vo.response.CancelReservationRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderHotelDetailRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderPersonDetailRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderRoomDetailRes;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.perm.UserOrgAuditPermService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "success", location = "/WEB-INF/pages/back/abroadhotel/ordOrder/orderList.ftl", type="freemarker"),
		@Result(name = "detail", location = "/WEB-INF/pages/back/abroadhotel/ordOrder/orderDetail.ftl", type="freemarker"),
		@Result(name = "json", location = "/WEB-INF/pages/back/abroadhotel/common/json.ftl", type="freemarker"),
		@Result(name = "payinfo", location = "/WEB-INF/pages/back/abroadhotel/ordOrder/orderPayInfo.ftl", type="freemarker"),
		@Result(name = "reset", location = "/WEB-INF/pages/back/abroadhotel/ordOrder/orderReset.ftl", type="freemarker")
	})
public class AbroadhotelOrdOrderAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8840801424336950796L;
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	private final static float SALE_RATE=1.13f;
	/**
	 * 订单服务接口
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	private OrderService orderServiceProxy;
	private ComLogService comLogService;
	private UserUserProxy userUserProxy;
	private IReservationsOrder reservationsOrder;
	private ICancelReservation cancelReservation;
	private List<ComLog> comLogs;
	private UserOrgAuditPermService userOrgAuditPermService;
	private PermUserService permUserService;
	/**查询请求信息*/
	private ReservationsOrderReq reservationsOrderReq;
	/**响应信息*/
	private ReservationsOrderRes reservationsOrderRes;
	/**订单信息*/
	private ReservationsOrder reservationsOrderDetail;
	/**酒店详情*/
	private List<ReservationsOrderHotelDetailRes> reservationsOrderHotelDetailRes;
	/**客人详情*/
	private List<ReservationsOrderPersonDetailRes> reservationsOrderPersonDetailRes;
	/**房间详情*/
	private List<ReservationsOrderRoomDetailRes> reservationsOrderRoomDetailRes;
	/**废单原因 */
	private List<CodeItem> cancelReasons;
	/** 我的审核任务和历史订单详细信息 */
	private OrdOrder orderDetail;
	/**是否超出最晚取消时间，true超出，false未超出*/
	private boolean outOfCancelTime;
	/**取消原因*/
	private String cancelReason;
	/**json字符串*/
	private String jsonString;
	/**是否初始状态*/
	private String isInit;
	/***/
	private int count;
	/***/
	private long firstPrice=0L;
	/***/
	private String percentageCharged;
	/***/
	private String percentageOf;
	/***/
	private String remarks;
	/***/
	private String sortStr;
	/***/
	private String voucher;
	
	private long refundMoney;
	
	private List<AhotelOrdPayment> ahotelOrdPaymentList;
	
	/**
	 * 支付信息详情
	 * @return
	 */
	@Action("/abroadhotel/ordOrder/paymentInfo")
	public String getPaymentInfo(){
		ahotelOrdPaymentList=abroadhotelOrderService.queryAhotelOrdPaymentByOrderId(Long.parseLong(reservationsOrderReq.getOrderNo().replace(",", "")));
		reservationsOrderDetail=reservationsOrder.queryForOrderList(reservationsOrderReq, null).getReservationsOrders().get(0);
		return "payinfo";
	}
	
	/**
	 * 查看订单详情
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action("/abroadhotel/ordOrder/detail")
	public String detail(){
		try{
			reservationsOrderDetail=reservationsOrder.queryForOrderList(reservationsOrderReq, null).getReservationsOrders().get(0);
			reservationsOrderHotelDetailRes=reservationsOrder.queryForHotelDetailByOrderId(reservationsOrderDetail.getId());
			//当前订单只对应一个酒店
			if(reservationsOrderHotelDetailRes!=null && reservationsOrderHotelDetailRes.size()>0){
				ReservationsOrderHotelDetailRes orderHotelDetail=reservationsOrderHotelDetailRes.get(0);
				reservationsOrderRoomDetailRes=reservationsOrder.queryForRoomDetailByAbroadhotelId(orderHotelDetail.getId());
				count=reservationsOrderRoomDetailRes.size();
				reservationsOrderPersonDetailRes=reservationsOrder.queryForPersonDetailByAbroadhotelId(orderHotelDetail.getId());
				for(ReservationsOrderPersonDetailRes personRes:reservationsOrderPersonDetailRes){
					personRes.setCertType(AbroadHotelConstant.getCnName(personRes.getCertType()));
				}
				Date cancelDate=reservationsOrder.getLastCancelTimeForBack(reservationsOrderDetail.getId());
				//重置最晚取消时间
				reservationsOrderDetail.setLastCancelTime(cancelDate);
				//最晚取消时间早于支付等待时间，设置支付等待时间为最晚取消时间
				if(reservationsOrderDetail.getWaitPayment()==null || reservationsOrderDetail.getWaitPayment().compareTo(DateUtils.addHours(cancelDate, -1))>0){
					reservationsOrderDetail.setWaitPayment(DateUtils.addHours(cancelDate, -1));
				}
				//id转姓名
				if(StringUtils.isNotEmpty(reservationsOrderDetail.getCancelOperator()) && !reservationsOrderDetail.getCancelOperator().equals("SYSTEM")){
					try{
						//判断是否用户取消
						if(reservationsOrderDetail.getCancelOperator().equals(reservationsOrderDetail.getUserId())){
							UserUser userUser=userUserProxy.getUserUserByUserNo(reservationsOrderDetail.getCancelOperator());
							if(userUser!=null)
							{
								reservationsOrderDetail.setCancelOperator(userUser.getUserName());
							}
						}else{
							PermUser permUser=userOrgAuditPermService.getPermUserById(Long.parseLong(reservationsOrderDetail.getCancelOperator()));
							if(permUser!=null)
							{
								reservationsOrderDetail.setCancelOperator(permUser.getUserName());
							}
						}
					}catch(Exception e){
						log.error("获取用户："+reservationsOrderDetail.getCancelOperator()+" 错误",e);
					}
					
				}
				//用户id转用户名
				if(StringUtils.isNotEmpty(reservationsOrderDetail.getUserId())){
					UserUser userUser=userUserProxy.getUserUserByUserNo(reservationsOrderDetail.getUserId());
					if(userUser!=null)
					{
						reservationsOrderDetail.setUserId(userUser.getUserName());
					}
				}
				//订单状态
				if(StringUtils.isNotEmpty(reservationsOrderDetail.getOrderStatus())){
					reservationsOrderDetail.setOrderStatusZH(getOrderZH(reservationsOrderDetail.getOrderStatus()));
				}
				
				//退款金额
				AhotelOrdRefundment ahotelOrdRefundment=abroadhotelOrderService.findHasRefundedOrderRefundmentByOrderId(Long.parseLong(reservationsOrderDetail.getOrderNo()));
				if(ahotelOrdRefundment!=null){
					refundMoney=ahotelOrdRefundment.getAmount();
				}
				
				//获取取消规则及第一晚价格，单位分
				try{
					voucher=orderHotelDetail.getVoucher();
					if(StringUtils.isNotEmpty(voucher)){
						Map voucherMap=xml2Map(voucher);
						Map cancellationPolicy=(Map)((Map)voucherMap.get("GetReservationDetails")).get("CancellationPolicy");
						percentageCharged=cancellationPolicy.get("PercentageCharged").toString();
						percentageOf=cancellationPolicy.get("PercentageOf").toString();
						remarks=cancellationPolicy.get("Remarks").toString();
						if(percentageOf.trim().equalsIgnoreCase("F")){
							List days=(List)((Map)voucherMap.get("GetReservationDetails")).get("Days");
							Collections.sort(days,new Comparator() {
								@Override
								public int compare(Object o1, Object o2) {
									Map dayMap1=(Map)o1;
									Map dayMap2=(Map)o2;
									Date day1=DateUtil.toDate(((Map)dayMap1.get("Day")).get("Date").toString(), "yyyy-MM-dd");
									Date day2=DateUtil.toDate(((Map)dayMap2.get("Day")).get("Date").toString(), "yyyy-MM-dd");
									return day1.compareTo(day2);
								}
							});
							List rooms=(List)((Map)(((Map)days.get(0)).get("Day"))).get("Rooms");
							for(Object room:rooms){
								Map item=(Map) ((Map)room).get("Room");
								String price=item.get("Price").toString();
								String boardPrice=item.get("BoardPrice").toString();
								Long lPrice=conver(price);
								Long lBoardPrice=conver(boardPrice);
								Long quantity=Long.parseLong(item.get("Quantity").toString());
								firstPrice=(long) (firstPrice+(lPrice+lBoardPrice)*quantity*100*SALE_RATE);
	//							转为10元整数倍
								long mod=firstPrice%1000;
								if(mod!=0){
									firstPrice=firstPrice+1000-mod;
								}
							}
						}else{
							firstPrice=reservationsOrderDetail.getActualPay();
						}
					}
				}catch(Exception e){
					log.error("取消策略",e);
					firstPrice=reservationsOrderDetail.getActualPay();
				}
				comLogs = comLogService.queryByParentId(Constant.COM_LOG_OBJECT_TYPE.ABROADHOTEL_ORD_ORDER.name(), Long.parseLong(reservationsOrderDetail.getOrderNo()));
				cancelReasons = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.ORD_CANCEL_REASON.name());
			}
		}catch(Exception e){
			log.error("订单明细信息",e);
		}
		return "detail";
	}
	/**
	 * 取消订单
	 * @return
	 */
	@Action("/abroadhotel/ordOrder/cancelOrder")
	public String cancelOrder(){
		CancelReservationRes cancelReservationRes=cancelReservation.cancelReservation(reservationsOrderReq.getOrderNo(), getOperatorId(), cancelReason);
		JSON json=JSONSerializer.toJSON(cancelReservationRes);
		if(cancelReservationRes==null || StringUtils.isEmpty(cancelReservationRes.getErrorID())){
			comLogService.insert(Constant.COM_LOG_OBJECT_TYPE.ABROADHOTEL_ORD_ORDER.name(), Long.parseLong(reservationsOrderReq.getOrderNo()),
					Long.parseLong(reservationsOrderReq.getOrderNo()), getOperatorName(),
					Constant.EVENT_TYPE.ORDER_CANCEL.name(),
					"取消订单", "后台取消订单", "ORD_ORDER");
		}
		jsonString=json.toString();
		return "json";
	}
	/**
	 * 订单列表
	 * @return
	 */
	@Action("/abroadhotel/ordOrder/list")
	public String list(){
		if(StringUtils.isNotEmpty(isInit) && isInit.equals("true")){
			try{
				//姓名转取消人id（需要对系统取消做特殊处理）
				String saveCancelOperator=reservationsOrderReq.getCancelOperator();
				reservationsOrderReq.setCancelOperator(userIdTran(saveCancelOperator));
				
				this.initPagination();
				if(reservationsOrderReq.getCheckInTo()!=null){
					reservationsOrderReq.setCheckInTo(DateUtils.parseDate(DateFormatUtils.format(reservationsOrderReq.getCheckInTo(), "yyMMdd")+"235959", new String[]{"yyMMddHHmmss"}));
				}
				if(reservationsOrderReq.getCancelledTo()!=null){
					reservationsOrderReq.setCancelledTo(DateUtils.parseDate(DateFormatUtils.format(reservationsOrderReq.getCancelledTo(), "yyMMdd")+"235959", new String[]{"yyMMddHHmmss"}));
				}
				if(reservationsOrderReq.getPaymentTimeTo()!=null){
					reservationsOrderReq.setPaymentTimeTo(DateUtils.parseDate(DateFormatUtils.format(reservationsOrderReq.getPaymentTimeTo(), "yyMMdd")+"235959", new String[]{"yyMMddHHmmss"}));
				}
				if(reservationsOrderReq.getResMadeTo()!=null){
					reservationsOrderReq.setResMadeTo(DateUtils.parseDate(DateFormatUtils.format(reservationsOrderReq.getResMadeTo(), "yyMMdd")+"235959", new String[]{"yyMMddHHmmss"}));
				}
				
				int count=reservationsOrder.selectCountInComplex(reservationsOrderReq,null);
				this.getPagination().setTotalRecords(count);
				if(this.getPagination()!=null){
					reservationsOrderRes=reservationsOrder.queryForOrderList(reservationsOrderReq, null,(this.getPagination().getPage()-1)*this.getPagination().getPerPageRecord(), this.getPagination().getPerPageRecord(), sortStr);
				}else{
					reservationsOrderRes=reservationsOrder.queryForOrderList(reservationsOrderReq, null, 0, 10, sortStr);
				}
				reservationsOrderReq.setCancelOperator(saveCancelOperator);
				if(reservationsOrderRes!=null && reservationsOrderRes.getReservationsOrders()!=null){
					for(ReservationsOrder order:reservationsOrderRes.getReservationsOrders()){
						if(StringUtils.isNotEmpty(order.getOrderStatus())){
							order.setOrderStatusZH(getOrderZH(order.getOrderStatus()));
						}
						if(StringUtils.isNotEmpty(order.getCancelOperator()) && StringUtils.isNotEmpty(order.getUserId())){
							//判断是否用户取消
							if(order.getCancelOperator().equals(order.getUserId())){
								UserUser userUser=userUserProxy.getUserUserByUserNo(order.getCancelOperator());
								if(userUser!=null)
								{
									order.setCancelOperator(userUser.getUserName());
								}
							}else if(!order.getCancelOperator().equals("SYSTEM")){
								PermUser permUser=userOrgAuditPermService.getPermUserById(Long.parseLong(order.getCancelOperator()));
								if(permUser!=null)
								{
									order.setCancelOperator(permUser.getUserName());
								}
							}
						}
					}
				}
			}catch(Exception e){
				log.error("订单列表",e);
			}
		}
		return SUCCESS;
	}
	
	@Action("/abroadhotel/ordOrder/resetOrderStatus")
	public String resetOrderStatus(){
		try{
			reservationsOrderDetail=reservationsOrder.queryForOrderList(reservationsOrderReq, null).getReservationsOrders().get(0);
			//取消人id转姓名
			if(StringUtils.isNotEmpty(reservationsOrderDetail.getCancelOperator()) && !reservationsOrderDetail.getCancelOperator().equals("SYSTEM")){
				try{
					PermUser permUser=userOrgAuditPermService.getPermUserById(Long.parseLong(reservationsOrderDetail.getCancelOperator()));
					if(permUser!=null)
					{
						reservationsOrderDetail.setCancelOperator(permUser.getUserName());
					}
				}catch(Exception e){
					log.error("获取用户："+reservationsOrderDetail.getCancelOperator()+" 错误",e);
				}
				
			}
		}catch(Exception e){
			log.error("订单明细信息",e);
		}
		return "reset";
	}
	/**
	 * 修改订单状态
	 * @return
	 */
	@Action("/abroadhotel/ordOrder/doResetOrderStatus")
	public String doResetOrderStatus(){
		boolean flag=false;
		try{
			flag=reservationsOrder.resetOrderStatus(reservationsOrderReq.getOrderNo(), reservationsOrderReq.getOrderStatus(), reservationsOrderReq.getPaymentStatus(), null, getOperatorId(), cancelReason);
			comLogService.insert(Constant.COM_LOG_OBJECT_TYPE.ABROADHOTEL_ORD_ORDER.name(), Long.parseLong(reservationsOrderReq.getOrderNo()),
					Long.parseLong(reservationsOrderReq.getOrderNo()), getOperatorName(),
					Constant.EVENT_TYPE.ORDER_CANCEL.name(),
					"重置订单状态", "后台重置订单状态为:"+ AbroadHotelConstant.getCnName(reservationsOrderReq.getOrderStatus())+",支付状态为:" 
					+AbroadHotelConstant.getCnName(reservationsOrderReq.getPaymentStatus()) +"，重置操作"+ (flag?"成功":"失败") , "ORD_ORDER");
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}
		JSONObject json=new JSONObject();
		if(flag){
			json.put("flag", flag);
			json.put("message", "重置成功");
		}else{
			json.put("flag", flag);
			json.put("message", "重置失败");
		}
		jsonString=json.toString();
		return "json";
	}

	private String getOrderZH(String orderStatus){
		if(StringUtils.isNotEmpty(orderStatus)){
			if(orderStatus.equals(AbroadHotelConstant.ORD_ORDER_ORDER_STATUS_CONFIRMED)){
				return " 正常";
			}else if(orderStatus.equals(AbroadHotelConstant.ORD_ORDER_ORDER_STATUS_SUCCESS)){
				return "完成";
			}else if(orderStatus.equals(AbroadHotelConstant.ORD_ORDER_ORDER_STATUS_WAITCANCEL)){
				return "取消";
			}else if(orderStatus.equals(AbroadHotelConstant.ORD_ORDER_ORDER_STATUS_CANCELFAILED)){
				return "取消失败";
			}else if(orderStatus.equals(AbroadHotelConstant.ORD_ORDER_ORDER_STATUS_CANCEL)){
				return "取消";
			}
		}
		return orderStatus;
	}
	/**private method*******************************************************/
	@SuppressWarnings("rawtypes")
	private Map xml2Map(String xml){
		Map map=new HashMap();
		SAXBuilder sb = new SAXBuilder();
		StringReader read = new StringReader(xml);
		try{
			InputSource source = new InputSource(read);
			Document doc = sb.build(source);
			Element root = doc.getRootElement();
			map=loop2Element(root,map);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(read!=null){
				read.close();
				read=null;
			}
		}
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map loop2Element(Element element,Map initMap){
		if(element==null){
			return null;
		}else{
			if(element.getName().equalsIgnoreCase("Days") || element.getName().equalsIgnoreCase("Rooms")){
				List tempList=new ArrayList();
				List ls=element.getChildren();
				if(ls!=null && ls.size()>0){
					Iterator i = ls.iterator();
					while(i.hasNext()){
						Element ine=(Element) i.next();
						Map tempMap=new HashMap();
						tempMap=loop2Element(ine,tempMap);
						tempList.add(tempMap);
					}
				}
				initMap.put(element.getName(), tempList);
			}else{
				List ls=element.getChildren();
				Map innerMap=new HashMap();
				if(ls!=null && ls.size()>0){
					Iterator i = ls.iterator();
					while(i.hasNext()){
						Element ine=(Element) i.next();
						innerMap=loop2Element(ine,innerMap);
					}
					initMap.put(element.getName(), innerMap);
				}else{
					initMap.put(element.getName(), element.getTextTrim());
				}
			}
		}
		return initMap;
	}
	
	private long conver(String money){
		if(money==null || money.trim().length()==0){
			return 0L;
		}
		String tempMoney=money.replace(".", "").replace(",", ".");
		double dbMoney=Double.parseDouble(tempMoney);
		BigDecimal bd=new BigDecimal(dbMoney);
		bd=bd.setScale(0, BigDecimal.ROUND_UP);
		return bd.longValue();
	}
	
	/**
	 * 姓名转取消人id（需要对系统取消做特殊处理）
	 * @param cancelOperator
	 * @return
	 */
	private String userIdTran(String cancelOperator){
		String newUserIds="";
		if(StringUtils.isNotEmpty(cancelOperator)){
			List<PermUser> lstUser=permUserService.findPermUser(cancelOperator);
			if(lstUser==null || lstUser.size()==0)
			{
				if(!cancelOperator.equals("SYSTEM")){
					newUserIds=cancelOperator;
				}else{
					newUserIds="'SYSTEM'";
				}
			}else{
				String userId="";
				for(PermUser pu:lstUser){
					userId=userId+"'"+pu.getUserId()+"',";
				}
				userId=userId.substring(0,userId.length()-1);
				if(cancelOperator.equals("SYSTEM")){
					userId+=",'SYSTEM'";
				}
				newUserIds=userId;
			}
		}
		return newUserIds;
	}
	/**private method*******************************************************/
	public long getFirstPrice() {
		return firstPrice;
	}
	public void setFirstPrice(long firstPrice) {
		this.firstPrice = firstPrice;
	}
	public String getPercentageCharged() {
		return percentageCharged;
	}
	public void setPercentageCharged(String percentageCharged) {
		this.percentageCharged = percentageCharged;
	}
	public String getPercentageOf() {
		return percentageOf;
	}
	public void setPercentageOf(String percentageOf) {
		this.percentageOf = percentageOf;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public UserOrgAuditPermService getUserOrgAuditPermService() {
		return userOrgAuditPermService;
	}
	public void setUserOrgAuditPermService(
			UserOrgAuditPermService userOrgAuditPermService) {
		this.userOrgAuditPermService = userOrgAuditPermService;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public String getSortStr() {
		return sortStr;
	}
	public void setSortStr(String sortStr) {
		this.sortStr = sortStr;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}

	public List<AhotelOrdPayment> getAhotelOrdPaymentList() {
		return ahotelOrdPaymentList;
	}

	public void setAhotelOrdPaymentList(List<AhotelOrdPayment> ahotelOrdPaymentList) {
		this.ahotelOrdPaymentList = ahotelOrdPaymentList;
	}

	public long getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(long refundMoney) {
		this.refundMoney = refundMoney;
	}
	public ReservationsOrderReq getReservationsOrderReq() {
		return reservationsOrderReq;
	}
	public void setReservationsOrderReq(ReservationsOrderReq reservationsOrderReq) {
		this.reservationsOrderReq = reservationsOrderReq;
	}
	public ReservationsOrderRes getReservationsOrderRes() {
		return reservationsOrderRes;
	}
	public void setReservationsOrderRes(ReservationsOrderRes reservationsOrderRes) {
		this.reservationsOrderRes = reservationsOrderRes;
	}
	public void setAbroadhotelReservationsOrderService(IReservationsOrder reservationsOrder) {
		this.reservationsOrder = reservationsOrder;
	}
	public String getIsInit() {
		return isInit;
	}
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	public ReservationsOrder getReservationsOrderDetail() {
		return reservationsOrderDetail;
	}
	public void setReservationsOrderDetail(ReservationsOrder reservationsOrderDetail) {
		this.reservationsOrderDetail = reservationsOrderDetail;
	}
	public List<ReservationsOrderHotelDetailRes> getReservationsOrderHotelDetailRes() {
		return reservationsOrderHotelDetailRes;
	}
	public void setReservationsOrderHotelDetailRes(
			List<ReservationsOrderHotelDetailRes> reservationsOrderHotelDetailRes) {
		this.reservationsOrderHotelDetailRes = reservationsOrderHotelDetailRes;
	}
	public List<ReservationsOrderPersonDetailRes> getReservationsOrderPersonDetailRes() {
		return reservationsOrderPersonDetailRes;
	}
	public void setReservationsOrderPersonDetailRes(
			List<ReservationsOrderPersonDetailRes> reservationsOrderPersonDetailRes) {
		this.reservationsOrderPersonDetailRes = reservationsOrderPersonDetailRes;
	}
	public List<ReservationsOrderRoomDetailRes> getReservationsOrderRoomDetailRes() {
		return reservationsOrderRoomDetailRes;
	}
	public void setReservationsOrderRoomDetailRes(
			List<ReservationsOrderRoomDetailRes> reservationsOrderRoomDetailRes) {
		this.reservationsOrderRoomDetailRes = reservationsOrderRoomDetailRes;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<CodeItem> getCancelReasons() {
		return cancelReasons;
	}
	public void setCancelReasons(List<CodeItem> cancelReasons) {
		this.cancelReasons = cancelReasons;
	}
	public OrdOrder getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public List<ComLog> getComLogs() {
		return comLogs;
	}
	public void setComLogs(List<ComLog> comLogs) {
		this.comLogs = comLogs;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public boolean isOutOfCancelTime() {
		return outOfCancelTime;
	}
	public void setOutOfCancelTime(boolean outOfCancelTime) {
		this.outOfCancelTime = outOfCancelTime;
	}

	public void setAbroadhotelCancelReservationService(ICancelReservation cancelReservation) {
		this.cancelReservation = cancelReservation;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
