package com.lvmama.tnt.order.service;

import java.util.Map;

import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.order.vo.TntPayPayment;
import com.lvmama.tnt.order.vo.TntPriceInfo;

/**
 * 分销平台创建订单
 * 
 * @author gaoxin
 * 
 */
public interface OrderCreateService {

	/**
	 * 分销平台创建订单接口
	 * 
	 * @param tntBuyInfo
	 * @return
	 * @throws Exception
	 */
	public ResultGod<TntOrder> createOrder(TntBuyInfo tntBuyInfo)
			throws Exception;

	/**
	 * 验证购买数量是否小于最低购买数
	 * 
	 * @param 验证是否存在非法数据
	 */
	public String validateSubmitDate(TntBuyInfo tntBuyInfo);

	public TntPriceInfo countPrice(TntBuyInfo buyInfo);

	public Map<String, Object> checkPrice(TntBuyInfo info);
	
	/**
	 * 检查分销价格
	 * @param info
	 * @return
	 */
	public Map<String, Object> checkDistPrice(TntBuyInfo info);

	public String checkOrderStock(TntBuyInfo info) throws Exception;

	/**
	 * 订单支付
	 * 
	 * @param orderId
	 *            lvmama订单号
	 * @param paymentGateway
	 *            支付渠道
	 * @param operator
	 *            支付人
	 * @return
	 */
	public boolean orderPayment(TntPayPayment tntPayPayment);

	public String buildOrder(TntOrder t);

	public void buildPriceOrder(TntOrder t);

	public Long calculateDistOrderAmount(Long orderId, Long userId);

}
