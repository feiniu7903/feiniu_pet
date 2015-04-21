package com.lvmama.bee.web.eplace;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortInfo;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

@Results({ @Result(name = "passPortInfo", location = "/WEB-INF/pages/eplace/passport/passPortInfo.jsp"),
	@Result(name = "getOrderInfo", location = "/WEB-INF/pages/eplace/passport/orderInfo.jsp")
})
/**
 * 查询通关类(jsp版).
 * 
 * @author huangl
 */
public class PassPortBusinessAction extends EbkBaseAction {
	private static final long serialVersionUID = 1L;
	private EPlaceService ePlaceService;
	private OrderService orderServiceProxy ;
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate;
	private PassCodeService passCodeService;
	private PerformTargetService performTargetService;
	private ComLogService comLogService;
	private List<OrdOrderItemMeta> orderItemMetas;
	private PassPortInfo passPortInfo;
	private Long targetId;
	private Long orderId;
	private Long[] quantity;
	private Long[] orderItemMetaId;
	private String remark;
	private String passwordCertificate;
	
	/**
	 * 对于支付给景区（未支付）的订单，组织通关信息
	 * @return
	 */
	@Action("/eplace/doPassPortInfo")
	public String getUnPassportOrderDetail(){
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(targetId.toString());
		compositeQuery.getMetaPerformRelate().setOrderId(orderId);
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(10000);
		orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		passPortInfo = this.getSinglePassPortInfo(ordOrder, orderItemMetas);
		return "passPortInfo";
	}
	@Action("/eplace/getOrderInfo")
	public String getOrderInfo(){
		String[] orderItemMetaIds = getRequestParameter("orderItemMetaIds").split(",");
		List<Long> ids = new ArrayList<Long>();
		for(String s : orderItemMetaIds){
			ids.add(Long.parseLong(s));
		}
		List<PerformDetail> performDetails = orderServiceProxy.getOrderPerformDetail(ids);
		//查询游客信息
		OrdPerson person = new OrdPerson();
		person.setObjectType("ORD_ORDER");
		person.setObjectId(performDetails.get(0).getOrderId());
		List<OrdPerson> persons = orderServiceProxy.getOrdPersons(person);
		
		setRequestAttribute("orderItemMetas", performDetails);
		setRequestAttribute("userMemo", performDetails.get(0).getUserMemo());
		setRequestAttribute("ordPersons", persons);
		setRequestAttribute("isAperiodic", performDetails.get(0).getIsAperiodic());
		return "getOrderInfo";
	}
	
	@Action("/eplace/checkActivateOrderDateSpace")
	public void checkActivateOrderDateSpace() {
		Map<String, Object> resultMap = orderItemMetaAperiodicService.checkPasswordCertificate(orderId, this.getCurrentSupplierId(), null, DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		resultMap.put("success", resultMap.get("message") != null ? false : true);
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	@Action("/eplace/passInfo")
	public void passInfo() {
		JSONObject json=new JSONObject();
		boolean flag=false;
		boolean returnFlag=true;
		boolean isPassport=false;
		OrdOrder ordOrder = null;
		if(orderId==null || targetId==null){
			returnFlag=false;
			json.put("returnFlag", returnFlag);
			json.put("message", "订单通关失败，您没有权限");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		try {
			ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			CompositeQuery compositeQuery = new CompositeQuery();
			compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
			compositeQuery.getMetaPerformRelate().setOrderId(ordOrder.getOrderId());
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(10000);
			List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			String msg=this.doCheck(ordOrder,orderItemMetas,targetId);
			if(msg!=null&&!"".equals(msg)){
				returnFlag=false;
				json.put("returnFlag", returnFlag);
				json.put("message", msg);
				JSONOutput.writeJSON(getResponse(), json);
				return;
			}
			if(orderItemMetaId == null || orderItemMetaId.length < 1) {
				orderItemMetaId = new Long[orderItemMetas.size()];
				quantity = new Long[orderItemMetas.size()];
				for(int i = 0; i < orderItemMetaId.length; i++) {
					quantity[i] = orderItemMetas.get(i).getQuantity();
					orderItemMetaId[i] = orderItemMetas.get(i).getOrderItemMetaId();
				}
			}
			//通关处理
			flag=this.addItemMetaPerform(ordOrder, orderItemMetas, orderItemMetaId, quantity, targetId, remark);
			
			if(flag){
				for(OrdOrderItemMeta itemMeta:orderItemMetas){
					List<SupPerformTarget> supPerformTarget=performTargetService.findSuperSupPerformTargetByMetaProductId(itemMeta.getMetaProductId());	
					if (Constant.CCERT_TYPE.DIMENSION.name().equals(
							supPerformTarget.get(0).getCertificateType())) {
						isPassport = true;
						break;
					}
				}
				if (isPassport) {
					// 二维码信息更新
					List<PassCode> passCodeLst=passCodeService.getPassCodeByOrderIdAndTargetIdList(ordOrder.getOrderId(), targetId);
					if(CollectionUtils.isNotEmpty(passCodeLst)){
						for(PassCode passCode:passCodeLst){
							PassPortCode tempPassPortCode=new PassPortCode();
							tempPassPortCode.setCodeId(passCode.getCodeId());
							tempPassPortCode.setTargetId(targetId);
							List<PassPortCode> passPortCodeList=passCodeService.queryPassPortCodeByParam(tempPassPortCode);
							for(PassPortCode passPortCode:passPortCodeList){
								passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.USED.name());
								passPortCode.setUsedTime(new Timestamp(new Date().getTime()));
								// 更新通关点信息
								passCodeService.updatePassPortCode(passPortCode);
							}
						}
					}
				} 
				//如果是不定期订单，需要更新订单的游玩时间以及密码券的激活状态
				if(ordOrder.IsAperiodic()){
					List<Long> orderItemMetaIds = new ArrayList<Long>();
					for (OrdOrderItemMeta meta : orderItemMetas) {
						orderItemMetaIds.add(meta.getOrderItemMetaId());
					}
					ResultHandle handle = orderItemMetaAperiodicService.updateAperiodicOrderUseStatus(orderId,"","",DateUtil.formatDate(new Date(),"yyyy-MM-dd"),Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED.name(),this.getSessionUser().getUserName(),orderItemMetaIds);
					if(handle.isFail()) {
						throw new Exception(handle.getMsg());
					}
				}
			}
			if(flag){
				returnFlag=true;
				json.put("message", "凭证正常通关");
				
			}else{
				returnFlag=false;
				json.put("message", "该订单已经履行");
			}
		} catch (Exception e) {
			log.info("port/passInfo():passport fail to ["+orderId+"]!"+e.getMessage());
			returnFlag=false;
			json.put("message", "订单通关失败!");
		}
		json.put("returnFlag", returnFlag);
		JSONOutput.writeJSON(getResponse(), json);
	}
	/**
	 * 通关验证
	 * 
	 * @param win
	 * @throws Exception
	 */
	private String doCheck(OrdOrder ordOrder,List<OrdOrderItemMeta> orderItemMetas,Long targetId) throws Exception {
		String msg="";
		//是否是二维码
		boolean flag = false;
		for(OrdOrderItemMeta itemMeta:orderItemMetas){
			List<SupPerformTarget> supPerformTarget=performTargetService.findSuperSupPerformTargetByMetaProductId(itemMeta.getMetaProductId());	
			if (Constant.CCERT_TYPE.DIMENSION.name().equals(
					supPerformTarget.get(0).getCertificateType())) {
				flag = true;
				break;
			}
		}
		if (flag) {
			//是二维码
			List<PassCode> passCodeLst=passCodeService.getPassCodeByOrderIdAndTargetIdList(ordOrder.getOrderId(), targetId);
			if(CollectionUtils.isNotEmpty(passCodeLst)){
				msg =  this.valid(passCodeLst.get(0).getCodeId(), targetId);
				if (msg == null) {
					if(ordOrder==null||ordOrder.isCanceled()){
						msg="订单:" + orderId + "不存在或者已经被取消";
						return msg;
					}
				}else{
					return msg;
				}
			}else{
				msg="此凭证不存在";
				return msg;
			}
		} else {
			try{
				if(ordOrder==null||ordOrder.isCanceled()){
					msg="订单:" + orderId + "不存在或者已经被取消";
					return msg;
				}
				/* 去掉订单未到游玩时间不能通关的限制
				Long visitTime=ordOrder.getVisitTime().getTime();
				long toDay =DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd").getTime();
				if(toDay<visitTime&&ordOrder.isPayToLvmama()){
					msg="订单:" + orderId + "未到游玩日期";
					return msg;
				}*/
				boolean isPerform=false;
				//兼容旧数据-rxq
				if(orderItemMetas.size()>1){
					List<OrdPerform> ordPerformList=orderServiceProxy.queryOrdPerformByOrderId(orderId);
					if(CollectionUtils.isNotEmpty(ordPerformList)){
						isPerform=true;
					}
				}
				for(OrdOrderItemMeta itemMeta:orderItemMetas)
				{
					OrdPerform ordPerform=orderServiceProxy.queryOrdPerformByOrderItemMetaId(itemMeta.getOrderItemMetaId());
					if(ordPerform!=null){
						isPerform=true;
					}
				}
				if(isPerform){
					msg="订单:" + orderId + "已经通关过,不能重复通关";
					return msg;
				} 
			}catch(Exception e){
				msg="订单:" + orderId + "不存在或者已经被取消";
				return msg;
			}
		}
		return msg;
	}
	/**
	 * 二维码验证
	 * 
	 * @param codeId
	 * @param targetId
	 * @return
	 */
	private String valid(Long codeId, Long targetId) {
		PassPortCode tempPassPortCode=new PassPortCode();
		tempPassPortCode.setCodeId(codeId);
		tempPassPortCode.setTargetId(targetId);
		List<PassPortCode> passPortCodeList=passCodeService.queryPassPortCodeByParam(tempPassPortCode);
		String status = "0";
		String msg = null;
		if (CollectionUtils.isNotEmpty(passPortCodeList)) {
			PassPortCode passPortCode=passPortCodeList.get(0);
			long toDay = DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			long validTime = passPortCode.getValidTime().getTime();
			long invalidTime = passPortCode.getInvalidTime().getTime();
			boolean isValid = (toDay >= validTime && toDay <= invalidTime) ? true : false;
			boolean validateInvalidDate = passPortCode.validateInvalidDate();
			status = passPortCode.getStatus().trim();
			// 码没有被使用
			if (Constant.PASSCODE_USE_STATUS.UNUSED.name().equals(status) && isValid && validateInvalidDate) {
				msg = null;
			} else if (Constant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
				msg = "凭证已经作废";
			} else if (!isValid) {
				if (toDay < validTime) {
//					msg = "凭证还未到游玩日期";
				} else {
					msg = "凭证已经过期";
				}
			} else if(!validateInvalidDate) {
				msg = "今日不可游玩";
			} else if (Constant.PASSCODE_USE_STATUS.USED.name().equals(status)) {

				msg = "凭证在此景点已经使用过，不能重复使用";
			}
		} else {
			msg = "凭证在不能在此景点使用";
		}
		return msg;
	}
	/**
	 * 添加履行信息
	 * @param ordOrder
	 * @param orderItemMetas
	 * @param orderItemMetaId
	 * @param portQuantity
	 * @param targetId
	 * @param remark
	 * @return
	 */
	private boolean addItemMetaPerform(OrdOrder ordOrder,List<OrdOrderItemMeta> orderItemMetas, Long[] orderItemMetaId, Long[] portQuantity, Long targetId,String remark) {
		log.info("Eplace pass by "+Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name()+":ORDER_ID="+ordOrder.getOrderId()+", targetId="+targetId+
				", orderItemMetas.size="+ orderItemMetas.size() +", portQuantity="+portQuantity);
		for(OrdOrderItemMeta orderItemMeta:orderItemMetas){
			Long quantity = findQuantity(orderItemMetaId, portQuantity, orderItemMeta.getOrderItemMetaId());
			boolean result = orderServiceProxy.insertOrdPerform(targetId, orderItemMeta.getOrderItemMetaId(), Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name(), quantity * orderItemMeta.getTotalAdultProductQuantity(), quantity * orderItemMeta.getTotalChildProductQuantity(), remark);
			if(!result) {
				return false;
			}
			comLogService.insert(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name(), ordOrder.getOrderId(), orderItemMeta.getOrderItemMetaId(), this.getSessionUser().getUserName(),
					Constant.EBookingBizType.EPLACE.name(), "通过E景通通关菜单操作通关", "通过E景通通关菜单操作通关", Constant.OBJECT_TYPE.ORD_ORDER.name());
		}
		return true;
	}
	/**
	 * 
	 * @author: ranlongfei 2013-2-27 下午10:02:01
	 * @param orderItemMetaId
	 * @param portQuantity
	 * @param id
	 */
	private Long findQuantity(Long[] orderItemMetaId, Long[] portQuantity, Long id) {
		for(int i = 0; i < orderItemMetaId.length; i++) {
			if(id.equals(orderItemMetaId[i])) {
				return portQuantity[i];
			}
		}
		return 0L;
	}
	
	/**
	 *  组织通关信息(景区支付)<br/>
	 *  <font color=red>打包的订单子子项唯一</font>
	 * @param ordOrder
	 * @param orderItemMetas
	 * @return
	 */
	private PassPortInfo getSinglePassPortInfo(OrdOrder ordOrder,List<OrdOrderItemMeta> orderItemMetas) {
		PassPortInfo passPortInfo = new PassPortInfo();
		if (ordOrder.isPayToLvmama()) {
			passPortInfo.setPrice(0);
			passPortInfo.setPriceYuan("-");
		} else {
			Long price = 0L;
			for(OrdOrderItemMeta oi : orderItemMetas) {
				OrdOrderItemProd itemProd=orderServiceProxy.queryOrdOrderItemProdById(oi.getOrderItemId());
				price += itemProd.getPrice()*itemProd.getQuantity();
			}
			passPortInfo.setPrice(PriceUtil.convertToYuan(price));
			passPortInfo.setPriceYuan(String.valueOf(passPortInfo.getPrice()));
		}
		passPortInfo.setVisitTime(DateFormatUtils.format(ordOrder.getVisitTime(), "yyyy-MM-dd HH:mm"));
		passPortInfo.setOrderId(ordOrder.getOrderId());
		passPortInfo.setMobile(ordOrder.getContact().getMobile());
		passPortInfo.setName(ordOrder.getContact().getName());
		passPortInfo.setTargetId(targetId);
		passPortInfo.setPayChannel(ordOrder.getPaymentTarget());
		passPortInfo.setPersonList(ordOrder.getPersonList());
		return passPortInfo;
	}
	
	public String initPageStr(){
		StringBuffer strBuf=new StringBuffer();
		return strBuf.toString();
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public CompositeQuery getCompositeQuery() {
		return compositeQuery;
	}

	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}

	public PerformDetailRelate getPerformDetailRelate() {
		return performDetailRelate;
	}

	public void setPerformDetailRelate(PerformDetailRelate performDetailRelate) {
		this.performDetailRelate = performDetailRelate;
	}

	public EPlaceService getePlaceService() {
		return ePlaceService;
	}
	public void setePlaceService(EPlaceService ePlaceService) {
		this.ePlaceService = ePlaceService;
	}
	public PassPortInfo getPassPortInfo() {
		return passPortInfo;
	}
	public void setPassPortInfo(PassPortInfo passPortInfo) {
		this.passPortInfo = passPortInfo;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long[] getQuantity() {
		return quantity;
	}
	public void setQuantity(Long[] quantity) {
		this.quantity = quantity;
	}
	public Long[] getOrderItemMetaId() {
		return orderItemMetaId;
	}
	public void setOrderItemMetaId(Long[] orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public List<OrdOrderItemMeta> getOrderItemMetas() {
		return orderItemMetas;
	}
	public void setOrderItemMetas(List<OrdOrderItemMeta> orderItemMetas) {
		this.orderItemMetas = orderItemMetas;
	}
	public OrderItemMetaAperiodicService getOrderItemMetaAperiodicService() {
		return orderItemMetaAperiodicService;
	}
	public void setOrderItemMetaAperiodicService(
			OrderItemMetaAperiodicService orderItemMetaAperiodicService) {
		this.orderItemMetaAperiodicService = orderItemMetaAperiodicService;
	}

	public String getPasswordCertificate() {
		return passwordCertificate;
	}
	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}
}
