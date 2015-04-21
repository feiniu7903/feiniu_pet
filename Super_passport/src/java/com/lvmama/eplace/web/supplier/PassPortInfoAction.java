package com.lvmama.eplace.web.supplier;

import java.util.Date;
import java.util.List;

import org.zkoss.zul.Label;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortInfo;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关操作
 * 
 * @author chenlinjun
 * 
 */
public class PassPortInfoAction extends ZkBaseAction {
	private static final long serialVersionUID = -220967933184448187L;
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassPortInfo passPortInfo;
	private Label showTotalQuantity;
	private Label showTotalPrice;
	private List<OrdOrderItemMeta> orderItemMetas;
	private EPlaceService eplaceService;
	private PassCodeService passCodeService;
	
	@Override
	protected void doBefore() throws Exception {
		passPortInfo=(PassPortInfo)this.arg.get("passPortInfo");
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(passPortInfo.getTargetId()));
		compositeQuery.getMetaPerformRelate().setOrderId(passPortInfo.getOrderId());
		orderItemMetas = orderServiceProxy
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
	}
	/**
	 * 展示修改人数.
	 * @param ordOrder
	 */
	public void showTotalQuantity(List<OrdOrderItemMeta> orderItemMetas){
		long child=0;
		long adult=0;
		long totalPrice=0;
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderItemMetas.get(0).getOrderId());
		for(OrdOrderItemMeta orderItemMeta:orderItemMetas){
			adult+=orderItemMeta.getTotalAdultQuantity();
			child+=orderItemMeta.getTotalChildQuantity();
			long price=0;
			for(OrdOrderItemProd ordOrderItemProd:ordOrder.getOrdOrderItemProds()){
				 if(ordOrderItemProd.getOrderItemProdId().equals(orderItemMeta.getOrderItemId())){
					 price=ordOrderItemProd.getPrice();
					 break;
				 }
			}
			totalPrice+=price*orderItemMeta.getQuantity();
		}
		showTotalQuantity.setValue("总人数:"+(child+adult)+"            (成人"+adult+" 儿童"+child+")");
		passPortInfo.setAdult(adult);
		passPortInfo.setChild(child);
		passPortInfo.setPrice(totalPrice);
		passPortInfo.setPriceYuan(String.valueOf((totalPrice/100)));
		this.showTotalPrice.setValue(passPortInfo.getPriceYuan());
	}
	/**
	 * 通关
	 * 
	 * @param passPortInfo
	 */
	public void pass(PassPortInfo passPortInfo) {
		boolean flag=false;
		Long adultQuantity =0l;
		Long childQuantity = 0l;
		long realTotal=passPortInfo.getAdult().intValue()+ passPortInfo.getChild().intValue();
		if(passPortInfo.getTotalMan().intValue()!=realTotal){
			 adultQuantity = passPortInfo.getAdult();
			 childQuantity = passPortInfo.getChild();
		}
		if (passPortInfo.getSerialNo() != null) {
			// 二维码信息更新
			PassPortCode passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(passPortInfo.getCodeId(),
					passPortInfo.getTargetId());
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
			ZkMessage.showInfo("凭证正常通关");
			this.closeWindow();
		}else{
			this.closeWindow();
			ZkMessage.showInfo("该订单已经履行");
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
			flag=orderServiceProxy.insertOrdPerform(targetId, orderId, ORDER, adultQuantity, childQuantity);
		} else {
			Long orderItemMetaId = orderItemMetas.get(0).getOrderItemMetaId();
			flag=orderServiceProxy.insertOrdPerform(targetId, orderItemMetaId, ORDER_ITEM, adultQuantity, childQuantity);
		}
		if(flag){
			for (int i = 0; i < orderItemMetas.size(); i++) {
				OrdOrderItemMeta o=orderItemMetas.get(i);
				PassPortLog passPortLog = new PassPortLog();
				passPortLog.setContent("通过E景通通关预警管理通关");
				passPortLog.setCreateDate(new Date());
				passPortLog.setOrderId(passPortInfo.getOrderId());
				passPortLog.setOrderItemMetaId(o.getOrderItemMetaId());
				passPortLog.setPassPortUserId(this.getUser().getPassPortUserId());
				eplaceService.addPassPortLog(passPortLog);
			}
		}
		return flag;
	}

	public PassPortInfo getPassPortInfo() {
		return passPortInfo;
	}

	public void setPassPortInfo(PassPortInfo passPortInfo) {
		this.passPortInfo = passPortInfo;
	}
	public List<OrdOrderItemMeta> getOrderItemMetas() {
		return orderItemMetas;
	}
	public void setOrderItemMetas(List<OrdOrderItemMeta> orderItemMetas) {
		this.orderItemMetas = orderItemMetas;
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
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
}
