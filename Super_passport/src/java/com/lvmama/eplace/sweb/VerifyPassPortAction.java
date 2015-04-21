package com.lvmama.eplace.sweb;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortInfo;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.vo.Constant;
import com.lvmama.eplace.utils.ChkPassPortUtil;
@Results( {
		@Result(name = "query", location = "/WEB-INF/epalce/queryPassPort.jsp"),
		@Result(name = "passPortInfo",location = "/WEB-INF/epalce/verify_passPort_info.jsp")}
		)
/**
 * 验证通关类(jsp版).
 * 
 * @author huangl
 */
public class VerifyPassPortAction extends BackBaseAction {
	private static final Log log = LogFactory.getLog(VerifyPassPortAction.class);
	private static final long serialVersionUID = 4589584124336568092L;
	private OrderService orderServiceProxy;
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	private EPlaceService eplaceService;
	private boolean isDimension=false;
	private boolean isShow=true;
	private PassCodeService  passCodeService;
	private PassPortInfo passPortInfo;
	/**
	 * 通关验证
	 * 
	 * @param win
	 * @throws Exception
	 */
	@Action(value="/port/doPassPortInfo",interceptorRefs=@InterceptorRef("authority"))
	public String doPassPortInfo() throws Exception {
		OrdOrder ordOrder = null;
		String orderId=this.getRequest().getParameter("orderId");
		String passQuantity=this.getRequest().getParameter("passQuantity");
		try {
			passPortInfo = new PassPortInfo();
			long userId = this.getOperatorLongId();
			UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
			Long targetId = userRelateSupplierProduct.getSupPerformTarget().getTargetId();
			ordOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			passPortInfo = this.getPassPortInfo(targetId, ordOrder);
			passPortInfo.setPerformPassedQuantity(Integer.parseInt(passQuantity));
			showTotalQuantity();
		} catch (Exception e) {
			log.info("port/doPassPortInfo():init passPortInfo fail to ["+orderId+"]!"+e.getMessage());
			log.error(" captured Exception error ref url: " + this.getRequest().getHeader("referer"));
			log.error(" captured Exception error url: " + this.getRequest().getRequestURL().toString());
			Map para =  this.getRequest().getParameterMap();
			String parameter = "";
			for (Iterator iter = para.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				Object obj = para.get(key);
				String value = "";
				if (obj!=null && (obj instanceof String[])) {
					String[] new_name = (String[]) obj;
					for (int i = 0; i < new_name.length; i++) {
						value += i+"|"+new_name[i];
					}
				}
				parameter += ","+key+"="+value;
			}
			log.error(" captured Exception error parameter: " + parameter);
			e.printStackTrace();
		}
		return "passPortInfo";
	}
	
	/**
	 * 展示修改人数.
	 * @param ordOrder
	 */
	public void showTotalQuantity(){
		long child=0;
		long adult=0;
		long quantity=0;
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(passPortInfo.getTargetId()));
		compositeQuery.getMetaPerformRelate().setOrderId(passPortInfo.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		for(OrdOrderItemMeta orderItemMeta:orderItemMetas){
			adult+=orderItemMeta.getTotalAdultQuantity();
			child+=orderItemMeta.getTotalChildQuantity();
			quantity=orderItemMeta.getQuantity();
		}
		this.getRequest().setAttribute("port_adult",adult);
		this.getRequest().setAttribute("port_child",child);
		this.getRequest().setAttribute("port_quantity",quantity);
	}
	
	
	/**
	 * 展示修改人数.
	 * @param ordOrder
	 */
	@Action("/port/updateTotalQuantity")
	public void updateTotalQuantity(){
		String portQuantity=this.getRequest().getParameter("portQuantity");
		String orderId=this.getRequest().getParameter("orderId");
		String targetId=this.getRequest().getParameter("targetId");
		PassPortInfo portInfo=this.initTotalQuantity(portQuantity, orderId,targetId);
		this.sendAjaxMsg(portInfo.getPriceYuan());
	}
	
	/**
	 * 初始化修改数量-改变价格.
	 * @param portQuantity
	 * @param orderId
	 * @return
	 */
	private PassPortInfo initTotalQuantity(String portQuantity,String orderId,String targetId){
		passPortInfo=new PassPortInfo();
		long child=0;
		long adult=0;
		long totalPrice=0;
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(targetId);
		compositeQuery.getMetaPerformRelate().setOrderId(Long.valueOf(orderId));
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderItemMetas.get(0).getOrderId());
		for(OrdOrderItemMeta orderItemMeta:orderItemMetas){
			adult+=orderItemMeta.getTotalAdultProductQuantity()*Long.valueOf(portQuantity);
			child+=orderItemMeta.getTotalChildProductQuantity()*Long.valueOf(portQuantity);
			long price=0;
			for(OrdOrderItemProd ordOrderItemProd:ordOrder.getOrdOrderItemProds()){
				 if(ordOrderItemProd.getOrderItemProdId().equals(orderItemMeta.getOrderItemId())){
					 price=ordOrderItemProd.getPrice();
					 break;
				 }
			}
			totalPrice+=price*Long.valueOf(portQuantity);
		}
		passPortInfo.setAdult(adult);
		passPortInfo.setChild(child);
		passPortInfo.setTotalMan(child + adult);
		passPortInfo.setPrice(totalPrice);
		passPortInfo.setPriceYuan(String.valueOf((totalPrice/100)));
		return passPortInfo;
	}
	
	
	
	/**
	 * 通关
	 * 
	 * @param passPortInfo
	 */
	@SuppressWarnings("unused")
	@Action("/port/passInfo")
	public void passInfo() {
		boolean flag=false;
		boolean isPassport=false;
		Long adultQuantity =0l;
		Long childQuantity = 0l;
		String portQuantity=this.getRequest().getParameter("portQuantity");
		String oldQuantity=this.getRequest().getParameter("oldQuantity");
		String orderId=this.getRequest().getParameter("orderId");
		String targetId=this.getRequest().getParameter("targetId");
		try {
			ChkPassPortUtil chkPassPortUtil=new ChkPassPortUtil();
			String msg=chkPassPortUtil.doCheck(Long.valueOf(orderId),Long.valueOf(this.getOperatorLongId()));
			if(msg!=null&&!"".equals(msg)){
				this.sendAjaxMsg(msg);
				return;
			}
			PassPortInfo passPortInfo=this.initTotalQuantity(portQuantity, orderId,targetId);
			passPortInfo.setOrderId(Long.valueOf(orderId));
			passPortInfo.setTargetId(Long.valueOf(targetId));
			if(!portQuantity.equals(oldQuantity)){
				adultQuantity = passPortInfo.getAdult();
				childQuantity = passPortInfo.getChild();
			}
			UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(this.getOperatorLongId());
			if (Constant.CCERT_TYPE.DIMENSION.name().equals(
					userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
				isPassport = true;
			}
			if (isPassport) {
				// 二维码信息更新
				PassCode passCode=this.passCodeService.getPassCodeByOrderIdStatus(Long.valueOf(orderId));
				PassPortCode passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(passCode.getCodeId(),
						Long.valueOf(targetId));
				passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.USED.name());
				passPortCode.setUsedTime(new Date());
				// 更新通关点信息
				passCodeService.updatePassPortCode(passPortCode);
				flag=this.addPerform(passPortInfo.getOrderId(), passPortInfo.getTargetId(), adultQuantity,
						childQuantity);
				
			} else {
				flag=this.addPerform(passPortInfo.getOrderId(), passPortInfo.getTargetId(), adultQuantity,
						childQuantity);
			}
			if(flag){
				this.sendAjaxMsg("凭证正常通关");
			}else{
				this.sendAjaxMsg("该订单已经履行");
			}
		} catch (Exception e) {
			log.info("port/passInfo():passport fail to ["+orderId+"]!"+e.getMessage());
			this.sendAjaxMsg("订单通关失败!");
		}
	}
	
	/**
	 * 添加履行信息
	 * 
	 * @param orderId
	 * @param targetId
	 * @param adultQuantity
	 * @param childQuantity
	 */
	private boolean addPerform(Long orderId, Long targetId, Long adultQuantity, Long childQuantity) {
		boolean flag=false;
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setOrderId(orderId);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		int size = orderItemMetas.size();
		if (size > 1) {
			log.info("Eplace pass by "+ORDER+":orderId="+orderId+",targetId="+targetId+",adultQuantity:"+adultQuantity+",childQuantity:"+childQuantity);
			flag=orderServiceProxy.insertOrdPerform(targetId, orderId, ORDER, adultQuantity, childQuantity);
		} else {
			Long orderItemMetaId = orderItemMetas.get(0).getOrderItemMetaId();
			log.info("Eplace pass by "+ORDER_ITEM+":ORDER_ITEM="+orderItemMetaId+",targetId="+targetId+",adultQuantity:"+adultQuantity+",childQuantity:"+childQuantity);
			flag=orderServiceProxy.insertOrdPerform(targetId, orderItemMetaId, ORDER_ITEM, adultQuantity, childQuantity);
		}
		if(flag){
			for (int i = 0; i < orderItemMetas.size(); i++) {
				OrdOrderItemMeta o=orderItemMetas.get(i);
				PassPortLog passPortLog = new PassPortLog();
				passPortLog.setContent("通过E景通通关菜单操作通关");
				passPortLog.setCreateDate(new Date());
				passPortLog.setOrderId(passPortInfo.getOrderId());
				passPortLog.setOrderItemMetaId(o.getOrderItemMetaId());
				passPortLog.setPassPortUserId(this.getOperatorLongId());
				eplaceService.addPassPortLog(passPortLog);
			}
		}
		return flag;
	}


	/**
	 * 组织通关信息
	 * 
	 * @param targetId
	 * @return
	 */
	private PassPortInfo getPassPortInfo(Long targetId, OrdOrder ordOrder) {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
		compositeQuery.getMetaPerformRelate().setOrderId(ordOrder.getOrderId());
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		long adult = 0;
		long child = 0;
		long totalChildProductQuantity=0;
		long totalAdultProductQuantity=0;
		for (int j = 0; j <  orderItemMetas.size(); j++) {
				OrdOrderItemMeta itemMeta = orderItemMetas.get(j);
				// 成人数
				adult = adult + itemMeta.getTotalAdultQuantity();
				// 儿童数
				child = child + itemMeta.getTotalChildQuantity();
				totalAdultProductQuantity=totalAdultProductQuantity+itemMeta.getTotalAdultProductQuantity();
				totalChildProductQuantity=totalChildProductQuantity+itemMeta.getTotalChildProductQuantity();
		}
		PassPortInfo passPortInfo = new PassPortInfo();
		passPortInfo.setChild(child);
		passPortInfo.setAdult(adult);
		passPortInfo.setTotalMan(child + adult);
		passPortInfo.setTotalAdultProductQuantity(totalAdultProductQuantity);
		passPortInfo.setTotalChildProductQuantity(totalChildProductQuantity);
		if (ordOrder.isPayToLvmama()) {
			passPortInfo.setPrice(0);
			passPortInfo.setPriceYuan("-");
		} else {
			passPortInfo.setPrice(ordOrder.getOughtPayYuan());
			passPortInfo.setPriceYuan(String.valueOf(ordOrder.getOughtPayYuan()));
		}
		passPortInfo.setVisitTime(DateFormatUtils.format(ordOrder.getVisitTime(), "yyyy-MM-dd"));
		passPortInfo.setOrderId(ordOrder.getOrderId());
		passPortInfo.setMobile(ordOrder.getContact().getMobile());
		passPortInfo.setName(ordOrder.getContact().getName());
		passPortInfo.setTargetId(targetId);
		passPortInfo.setPersonList(ordOrder.getPersonList());
		passPortInfo.setPayChannel(ordOrder.getPaymentTarget());
		return passPortInfo;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
 
	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public boolean isDimension() {
		return isDimension;
	}
	public void setDimension(boolean isDimension) {
		this.isDimension = isDimension;
	}
	public boolean isShow() {
		return isShow;
	}
	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	public PassCodeService getPassCodeService() {
		return passCodeService;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}


	public PassPortInfo getPassPortInfo() {
		return passPortInfo;
	}


	public void setPassPortInfo(PassPortInfo passPortInfo) {
		this.passPortInfo = passPortInfo;
	}
}
