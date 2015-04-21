package com.lvmama.shholiday.notify;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.shholiday.vo.order.OrderStatus;

public class TourOrderModifyNotifyRQHandle extends AbstractShholidayOrderNotify {

	private static final Log logger =LogFactory.getLog(TourOrderStatusNotifyRQHandle.class);
	
	private OrdRefundMentService ordRefundMentService;
	private OrdSaleServiceService ordSaleServiceService;
	public TourOrderModifyNotifyRQHandle(){
		super("OrderNotifyRQ","OTA_TourOrderModifyNotifyRS");
		ordRefundMentService = SpringBeanProxy.getBean(OrdRefundMentService.class, "ordRefundMentService");
		ordSaleServiceService = SpringBeanProxy.getBean(OrdSaleServiceService.class, "ordSaleServiceService");
	}
	
	@Override
	protected void handleOther(Element body) {
		orderPackageNo = body.elementText("OrderPackageNo");
		OrdOrderSHHoliday sh = getEntity();
		if (sh == null) {
			setError("60051", "订单不存在", null);
			return;
		}
		Element status = body.element("BookModifyStatus");
		Element bookModifyAmount = body.element("BookModifyAmount");
		orderStatus =XmlUtils.toBean(OrderStatus.class, status);
		Long refAmount=0L;
		if(bookModifyAmount.elementText("ModifyAmount")!=null){
			if("".equals(bookModifyAmount.elementText("ModifyAmount"))){
				refAmount=0L;
			}else{
				refAmount = PriceUtil.convertToFen(bookModifyAmount.elementText("ModifyAmount"));
			}
		}
		addParam("orderPackageNo",orderPackageNo);
		String key="shholiday_refund_"+getSerialNo();
		try{
			if(SynchronizedLock.isOnDoingMemCached(key)){
				return;
			}
			saveSHHolidayInfo(new OrdOrderSHHoliday(sh.getObjectId(),Constant.ORD_SHHOLIDAY_OBJECT_TYPE.ORD_REFUNDED.getCode(),""+refAmount));
			refunded(sh, refAmount);
		}finally{
			SynchronizedLock.releaseMemCached(key);
		}
	}

	private void refunded(OrdOrderSHHoliday sh, Long refAmount) {
		try{
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(sh.getObjectId());
			if(refAmount==0L){ 
				refAmount=order.getActualPay(); 
			}
			Long penaltyAmount = order.getActualPay()-refAmount;
			
			
			if(penaltyAmount<0){
				logger.error("上航订单退改通知  退款金额大于实际支付金额  退款金额=" + penaltyAmount);
				
			}else{	
				orderServiceProxy.updateNeedSaleService("true", sh.getObjectId(), "SYSTEM");
				
				for(OrdOrderItemMeta item:order.getAllOrdOrderItemMetas()){
					if(item.getSupplierId().toString().equals(WebServiceConstant.getProperties("shholiday.supplierId"))){
						item.setAmountValue(0L);
						item.setActualLoss(0L);
						if(penaltyAmount<=0){
							break;
						}
						if(item.getTotalSettlementPrice()>=penaltyAmount){
							item.setAmountValue(penaltyAmount);
							item.setActualLoss(penaltyAmount);
						}else{
							item.setAmountValue(item.getTotalSettlementPrice());
							item.setActualLoss(item.getTotalSettlementPrice());
						}
						penaltyAmount = penaltyAmount - item.getTotalSettlementPrice();
					}
				}
				ordRefundMentService.applyRefund(sh.getObjectId(),saveSaleService(sh.getObjectId()), order.getAllOrdOrderItemMetas(), 
						 refAmount, Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.toString(), "UNVERIFIED", 
							"申请退款", "STSTEM", 0L);				 
				if(order!=null && order.isCancelAble()){
					boolean flag = orderServiceProxy.cancelOrder(sh.getObjectId(), "上航假期通知废单", "SYSTEM");
//					if(flag){
//				         orderServiceProxy.autoCreateOrderFullRefund(order,"SYSTEM", "资源未审核废单，自动退款");
//				    }
				}
			}
			 
		}catch(Exception e){
			logger.error("上航订单退改通知  操作订单异常 orderId=" + sh.getObjectId());
		}
	}

	private Long saveSaleService(Long orderId){
		OrdSaleService ordSevice=new OrdSaleService();
		ordSevice.setCreateTime(new Date());
		ordSevice.setOperatorName("SYSTEM");
		ordSevice.setOrderId(orderId);
		ordSevice.setApplyContent("申请退款");
		ordSevice.setServiceType("NORMAL");
		ordSevice.setStatus("NORMAL");
		return ordSaleServiceService.addOrdSaleService(ordSevice);
	}
	private OrderStatus orderStatus;
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

}
