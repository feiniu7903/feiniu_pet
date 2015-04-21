package com.lvmama.comm.utils;

import java.util.List;
import java.util.regex.Pattern;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.vo.Constant;

public class OrderUitl {
	
	public static boolean checkIsMainProduct(OrdOrderItemProd itemProd){
		if ("true".equals(itemProd.getIsDefault())) {
			return true;
		}
		return false;
	}
	
	public final static boolean validateBuyNum(String code){
		Pattern pattern = Pattern.compile("^[0-9]{1,2}+$");
		if (!pattern.matcher(code).matches()) {
			return false;
		}
		return true;
	}
	public final static boolean hasWaitpaymentChange(OrdOrder orderDetail){
		if(orderDetail.isNormal()&&!orderDetail.isPaymentSucc()&&!orderDetail.hasNeedPrePay()&&orderDetail.isApprovePass()) {
			if(hasResourceApprovePassSendFax(orderDetail)) {
				//最晚取消时间
				if(orderDetail.getLastCancelTime()!=null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 是否是资源审核后发传真的订单，
	 * 如果不存在传真对应也为true
	 * @param order
	 * @return
	 */
	private static boolean hasResourceApprovePassSendFax(OrdOrder order){
		boolean flag=true;
		for(OrdOrderItemMeta item:order.getAllOrdOrderItemMetas()){
			if(item.isNeedSendFax() && !Constant.PRODUCT_TYPE.OTHER.name().equals(item.getProductType())){
				if(order.isNeedResourceConfirm()&&!item.isApproveResourceSendFax()){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}
	
	public static OrdOrderItemMeta getMeta(List<OrdOrderItemMeta> itemList,long orderItemMetaId){
		for(OrdOrderItemMeta itemMeta:itemList){
			if(itemMeta.getOrderItemMetaId().equals(orderItemMetaId)){
				return itemMeta;
			}
		}
		return null;
	}
	
	/**
	 * 判断一个订单是否是上航假期
	 * @param order
	 * @return
	 */
	public static boolean hasShholidayOrder(OrdOrder order){
		return Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.name().equals(order.getSupplierChannel());
	}
	
	/**
	 * 判断一个订单是否是锦江订单
	 * @param order
	 * @return
	 */
	public static boolean isjinjiangOrder(OrdOrder order){
		return Constant.SUPPLIER_CHANNEL.JINJIANG.name().equals(order.getSupplierChannel());
	}
}
