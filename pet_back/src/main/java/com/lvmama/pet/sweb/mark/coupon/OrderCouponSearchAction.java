package com.lvmama.pet.sweb.mark.coupon;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrderCouponSearchAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -8322851596383659115L;
	/**
	 * 订单号
	 */
	private Long orderId;
	
	private List<String> markCouponUsageDescs = new ArrayList<String>();
	
	private FavorOrderService favorOrderService;
	
	private MarkCouponService markCouponService;
	
	@Action(value="/mark/coupon/orderCouponSearch",results={@Result(location = "/WEB-INF/pages/back/mark/orderCouponSearch.jsp")})
	public String orderCouponSearch() {	
		if (null != orderId) {
			List<MarkCouponUsage> markCouponUsages = favorOrderService.getMarkCouponUsageByObjectIdAndType(orderId, null);
			for (MarkCouponUsage usage : markCouponUsages) {
				if (Constant.OBJECT_TYPE.ORD_ORDER.name().equals(usage.getObjectType())) {
					MarkCouponCode code = markCouponService.selectMarkCouponCodeByPk(usage.getCouponCodeId());
					if (null != code) {
						markCouponUsageDescs.add("订单类优惠：使用了优惠券号码:" + code.getCouponCode() + "  享受了优惠金额:" + PriceUtil.convertToYuan(usage.getAmount()) + "元");
					} else {
						markCouponUsageDescs.add("订单类优惠：享受了优惠金额:" + PriceUtil.convertToYuan(usage.getAmount()) + "元");
					}
				}
			}
		}
		
		return SUCCESS;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public List<String> getMarkCouponUsageDescs() {
		return markCouponUsageDescs;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}

}
