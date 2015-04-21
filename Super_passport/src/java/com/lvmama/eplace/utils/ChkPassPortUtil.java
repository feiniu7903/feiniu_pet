package com.lvmama.eplace.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class ChkPassPortUtil {
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private EPlaceService eplaceService= (EPlaceService) SpringBeanProxy.getBean("eplaceService");
	private PassCodeService  passCodeService= (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	/**
	 * 通关验证
	 * 
	 * @param win
	 * @throws Exception
	 */
	public  String doCheck(Long orderId,Long userId) throws Exception {
		String msg="";
		boolean flag = false;
		OrdOrder ordOrder = null;
		UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
		Long targetId = userRelateSupplierProduct.getSupPerformTarget().getTargetId();
		if (Constant.CCERT_TYPE.DIMENSION.name().equals(
				userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
			flag = true;
		}
		if (flag) {
			// 二维码
			PassCode passCode=passCodeService.getPassCodeByOrderIdStatus(orderId);
			if (passCode != null) {
				msg =  this.valid(passCode.getCodeId(), targetId);
				if (msg == null) {
					String serialNo = passCode.getSerialNo();
					ordOrder = orderServiceProxy.queryOrdOrderBySerialNo(serialNo);
					if(ordOrder==null||ordOrder.isCanceled()){
						msg="订单:" + orderId + "不存在或者已经被取消";
						return msg;
					}
				}else{
					return msg;
				}
			} else {
				msg="此凭证不存在";
				return msg;
			}
		} else {
			try{
				ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				if(ordOrder==null||ordOrder.isCanceled()){
					msg="订单:" + orderId + "不存在或者已经被取消";
					return msg;
				}
				Long visitTime=ordOrder.getVisitTime().getTime();
				long toDay =DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd").getTime();
				/*if(toDay<visitTime&&ordOrder.isPayToLvmama()){
					msg="订单:" + orderId + "未到游玩日期";
					return msg;
				}*/
				CompositeQuery compositeQuery = new CompositeQuery();
				compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
				compositeQuery.getMetaPerformRelate().setOrderId(orderId);
				List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			
				int size=orderItemMetas.size();
				Map<String,Object> params=new HashMap<String,Object>();
				if(size>1){
					params.put("objectId", orderId);
					params.put("objectType","ORD_ORDER");
				}else{
					Long orderItemId=orderItemMetas.get(0).getOrderItemMetaId();
					params.put("objectId", orderItemId);
					params.put("objectType","ORD_ORDER_ITEM_META");
				}
				boolean isPerform=passCodeService.hasPassCodePerform(params);
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
		PassPortCode passPortCode = passCodeService.getPassPortCodeByCodeIdAndPortId(codeId, targetId);
		String status = "0";
		String msg = null;
		if (passPortCode != null) {
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
					//msg = "凭证还未到游玩日期";
				} else {
					msg = "凭证已经过期";
				}
			} else if(!validateInvalidDate){
				msg = "今日不可游玩";
			} else if (Constant.PASSCODE_USE_STATUS.USED.name().equals(status)) {

				msg = "凭证在此景点已经使用过，不能重复使用";
			}
		} else {
			msg = "凭证在不能在此景点使用";
		}
		return msg;
	}
}
