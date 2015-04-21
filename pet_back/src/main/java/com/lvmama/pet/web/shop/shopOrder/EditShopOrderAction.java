package com.lvmama.pet.web.shop.shopOrder;

import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.vo.ShopOrderVO;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;

/**
 * 编辑订单
 * @author ganyingwen
 *
 */
public class EditShopOrderAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1231234543354651L;
	/**
	 * 订单服务
	 */
	private ShopOrderService shopOrderService;
	/**
	 * 订单明细
	 */
	private ShopOrder shopOrder;
	/**
	 * 订单ID
	 */
	private Long orderId;

	@Override
	public void doBefore() {
		if (shopOrder == null) {
			shopOrder = (ShopOrderVO) shopOrderService.queryShopOrderByKey(orderId);
		}
	}
	
	/**
	 * 更新
	 */
	public void update() {
		ZkMessage.showQuestion("确定保存?", new ZkMsgCallBack() {
			public void execute() {
				shopOrderService.updata(shopOrder);
				refreshParent("search");
				getComponent().detach();
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	public void setShopOrderService(final ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public ShopOrder getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(final ShopOrder shopOrder) {
		this.shopOrder = shopOrder;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
