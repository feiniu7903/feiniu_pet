package com.lvmama.clutter.service.client.v5_1_0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobilePayment;
import com.lvmama.clutter.service.client.v5_0.ClientUserServiceV50;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.vo.Constant;

/**    
 * @Title: ClientUserServiceV510.java
 * @Package com.lvmama.clutter.service.client.v5_1_0
 * @Description: TODO
 * @author jiangzhihu
 * @date 2014-4-23 下午1:25:13
 * @version V1.0.0
 */
public class ClientUserServiceV510 extends ClientUserServiceV50 {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("orderId", param);
		MobileOrder mo = super.getMobileOrderByOrderId(
				Long.valueOf(param.get("orderId").toString()),
				String.valueOf(param.get("userNo")));
		boolean isTrain = isTrain(mo.getMainProductType(),
				mo.getMainSubProductType());
		// 如果不是火车票
		if (!isTrain) {
			super.initBonus(mo);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", param.get("userId"));
		List<UserCooperationUser> list = userCooperationUserService
				.getCooperationUsers(parameters);
		String channel = "";
		if (list != null && !list.isEmpty()) {
			UserCooperationUser cu = list.get(0);
			channel = cu.getCooperation();
		}
		MobilePayment mpalipay = new MobilePayment(
				Constant.PAYMENT_GATEWAY.ALIPAY_APP.name(), Constant
						.getInstance().getAliPayAppUrl());
		MobilePayment mpwap = new MobilePayment(
				Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name(), Constant
						.getInstance().getAliPayWapUrl());
		MobilePayment mpupompTest = new MobilePayment("UPOMP_OTHER", Constant
				.getInstance().getUpompPayUrl());
		MobilePayment mpupomp = new MobilePayment(
				Constant.PAYMENT_GATEWAY.UPOMP.name(), Constant.getInstance()
						.getUpompPayUrl());
		MobilePayment tenpayWap = new MobilePayment("TENPAY_WAP", Constant
				.getInstance().getTenpayWapUrl());
		
		//微信支付
		MobilePayment weixinPay = null;
		boolean isAndroid = (Boolean) param.get("isAndroid");
		if(isAndroid){//android
			logger.info("isAndroid="+isAndroid);
			weixinPay = new MobilePayment("WEIXIN_APP", Constant
					.getInstance().getWeixinAndroidUrl());
		}
		boolean isIphone = (Boolean) param.get("isIphone");
		if(isIphone){//iphone
			logger.info("isAndroid="+isAndroid);
			weixinPay = new MobilePayment("WEIXIN_APP", Constant
					.getInstance().getWeixinIphoneUrl());
		}
		
		
		mo.getPaymentChannels().add(mpalipay);
		mo.getPaymentChannels().add(mpwap);
		if(null!=weixinPay){
			mo.getPaymentChannels().add(weixinPay);
		}
		mo.getPaymentChannels().add(mpupompTest);
		mo.getPaymentChannels().add(tenpayWap);
		mo.getPaymentChannels().add(mpupomp);
		// isTrain 如果是火车票 - 屏蔽银联支付 ,isWP8 屏蔽银联支付
		if ("ALIPAY".equals(channel) || "CLIENT_ANONYMOUS".equals(channel)
				|| isTrain || this.isHopeChannel(param, Constant.MOBILE_PLATFORM.WP8.name())) {
			mo.getPaymentChannels().remove(mpupomp);
			mo.getPaymentChannels().remove(mpupompTest);
			if ("ALIPAY".equals(channel)) {
				mo.getPaymentChannels().remove(tenpayWap);
			}
		}
		return mo;
	}

}
