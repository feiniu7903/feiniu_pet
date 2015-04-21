package com.lvmama.clutter.service.client.v3_2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.model.MobilePayment;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.vo.Constant;

public class ClientUserServiceV321  extends ClientUserServiceV32{

	@Override
	public Map<String, Object> getBonusInfo(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusInfo(params);
	}

	@Override
	public Map<String, Object> getBonusIncome(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusIncome(params);
	}

	@Override
	public Map<String, Object> getBonusPayment(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusPayment(params);
	}

	@Override
	public Map<String, Object> getBonusRefund(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusRefund(params);
	}

	@Override
	public String getZhComeFrom(String comeFrom) {
		// TODO Auto-generated method stub
		return super.getZhComeFrom(comeFrom);
	}

	@Override
	public Map<String, Object> reSendSmsCert(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.reSendSmsCert(params);
	}

	@Override
	public Map<String, Object> bonusPay(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.bonusPay(params);
	}

	@Override
	public Map<String, Object> bindingCouponToUser(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.bindingCouponToUser(params);
	}

	@Override
	public Map<String, Object> queryCmtWaitForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCmtWaitForOrder(param);
	}

	@Override
	public Map<String, Object> commitOrderComment(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.commitOrderComment(param);
	}

	@Override
	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		// TODO Auto-generated method stub
		super.setOrderMessageProducer(orderMessageProducer);
	}

	@Override
	public MobileUser getUser(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.getUser(param);
	}

	@Override
	public List<MobileUserCoupon> getCoupon(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getCoupon(param);
	}

	/**
	 * 是否火车票  true 是；false：否
	 * @param productTyper
	 * @param subProductType
	 * @return true 
	 */
	public boolean isTrain(String productTyper,String subProductType) {
		return Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productTyper) && Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(subProductType);
	}
	
	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
				ArgCheckUtils.validataRequiredArgs("orderId", param);
				Map<String,Object> map = super.getOrderViewList(Long.valueOf(param.get("orderId").toString()), String.valueOf(param.get("userNo")), 1L,"");
				List<MobileOrder> orderList = (List<MobileOrder>)map.get("datas");
				if(orderList!=null &&!orderList.isEmpty()){
					// 初始化订单奖金支付信息 . 
					MobileOrder mo  =super.initBonus(orderList.get(0)) ;

					List<OrdOrder> listOrder = (List<OrdOrder>) map.get("ordOrderList");
					if(listOrder!=null && !listOrder.isEmpty()){
						OrdOrder oo = listOrder.get(0);
						if(oo.getOughtPay()!=0){
							mo.setCanToPay(oo.isCanToPay());
						}
					}
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("userId", param.get("userId"));
					List<UserCooperationUser> list = userCooperationUserService.getCooperationUsers(parameters);
					String channel = "";
					if(list!=null&&!list.isEmpty()){
						UserCooperationUser cu = list.get(0);
						channel = cu.getCooperation();
					}
					MobilePayment mpalipay = new MobilePayment(Constant.PAYMENT_GATEWAY.ALIPAY_APP.name(), Constant.getInstance().getAliPayAppUrl());
					MobilePayment mpwap = new MobilePayment(Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name(), Constant.getInstance().getAliPayWapUrl());
					MobilePayment mpupompTest = new MobilePayment("UPOMP_OTHER", Constant.getInstance().getUpompPayUrl());
					MobilePayment mpupomp = new MobilePayment(Constant.PAYMENT_GATEWAY.UPOMP.name(), Constant.getInstance().getUpompPayUrl());
					MobilePayment tenpayWap = new MobilePayment("TENPAY_WAP", Constant.getInstance().getTenpayWapUrl());
					mo.getPaymentChannels().add(mpalipay);
					mo.getPaymentChannels().add(mpwap);
					mo.getPaymentChannels().add(mpupompTest);
					mo.getPaymentChannels().add(tenpayWap);
					mo.getPaymentChannels().add(mpupomp);
					// isTrain 如果是火车票 - 屏蔽银联支付 
					if("ALIPAY".equals(channel)||"CLIENT_ANONYMOUS".equals(channel)){
						mo.getPaymentChannels().remove(mpupomp);
						mo.getPaymentChannels().remove(mpupompTest);
						if("ALIPAY".equals(channel)){
							mo.getPaymentChannels().remove(tenpayWap);
						}
					} 

					return mo;

				}  
				return null;	
	}

	@Override
	public Map<String, Object> getOrderList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getOrderList(param);
	}

	@Override
	protected Map<String, Object> getOrderViewList(Long orderId, String userId,
			Long page,String subProductType) {
		// TODO Auto-generated method stub
		return super.getOrderViewList(orderId, userId, page,subProductType);
	}

	@Override
	public String addContacts(Map<String, String> param) throws Exception {
		// TODO Auto-generated method stub
		return super.addContacts(param);
	}

	@Override
	public String addFavorite(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.addFavorite(params);
	}

	@Override
	public boolean cancelFavorite(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancelFavorite(param);
	}

	@Override
	public Map<String, Object> getFavoriteList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getFavoriteList(param);
	}

	@Override
	public String addContact(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.addContact(param);
	}

	@Override
	public List<MobileReceiver> getContact(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getContact(param);
	}

	@Override
	public List<MobileOrderCmt> queryCommentWaitForOrder(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCommentWaitForOrder(param);
	}

	@Override
	public Map<String, Object> queryCommentForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCommentForOrder(param);
	}

	@Override
	public List<DicCommentLatitude> getCommentLatitudeInfos(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.getCommentLatitudeInfos(param);
	}

	@Override
	public String commitComment(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.commitComment(param);
	}

	@Override
	public Float getSumYouHuiAmount(List<OrdOrderAmountItem> listAmountItem,
			float earlyCouponAmount, float moreCouponAmount) {
		// TODO Auto-generated method stub
		return super.getSumYouHuiAmount(listAmountItem, earlyCouponAmount,
				moreCouponAmount);
	}

}
