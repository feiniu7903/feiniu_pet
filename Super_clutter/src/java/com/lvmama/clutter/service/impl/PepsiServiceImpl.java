package com.lvmama.clutter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import com.lvmama.clutter.service.PepsiService;
import com.lvmama.clutter.utils.ClutterConstant;
//import com.lvmama.clutter.utils.HttpClient;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;

/**
 * 百事优惠券服务接口实现
 * 
 * @author chenlinjun,qiuguobin
 * 
 */
public class PepsiServiceImpl implements PepsiService {
	private static final Log log = LogFactory.getLog(PepsiServiceImpl.class);
	private MarkCouponService markCouponService;
	private UserUserProxy userUserProxy;

	/**
	 * 百事兑换优惠券接口
	 * 
	 * @param mobile
	 *            手机号
	 * @param serialNo
	 *            百事流水号
	 * @param email
	 *            邮箱
	 * @return 0:兑换失败,1:兑换成功
	 */
	public int exchangeCoupon(String mobile, String serialNo, String email) {
		log.info("Pepsi exchangeCoupon mobile:" + mobile + " serialNo:" + serialNo + " email:" + email);
		int flag = 0; // 0:兑换失败,1:兑换成功
		// 注册
		JSONObject jsonObj= register(mobile, serialNo, email);
		String userId = "";
		boolean serialNoExists = false;
		try {
			userId = jsonObj.getString("userId");
			serialNo = jsonObj.getString("serialNo");
			serialNoExists = jsonObj.getBoolean("serialNoExists");
		} catch (JSONException e) {
			log.error(" Parse Json Exception ", e);
		}

		// 生成优惠券号
		String couponNo = "";
		// 活动号
		String activityId = ClutterConstant.getActivityId();
		if (!serialNoExists && !StringUtil.isEmptyString(userId)) {// 流水号不存在则创建一个优惠券号
			MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(new Long(activityId));
			if(markCouponCode != null){
				couponNo = markCouponCode.getCouponCode();
			}
			if(bindCouponToUser(activityId, couponNo, userId)){
				flag = 1;
			}
		}
		return flag;
	}
	
	private boolean bindCouponToUser(String activityIda, String couponCode, String userId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("couponCode", couponCode);
		param.put("isValid", "true");
		List<MarkCouponCode> list = markCouponService.selectMarkCouponCodeByParam(param);
		if (list.size()>0) {
			UserUser user = userUserProxy.getUserUserByUserNo(userId);
			MarkCouponCode code = list.get(0);
			Long id = markCouponService.bindingUserAndCouponCode(user, code.getCouponCode());
			if (id>0) {
				return true;
			}
		}
		return false;
	}

	private JSONObject register(String mobile, String serialNo, String email) {
		String url = ClutterConstant.getURL();

		Map<String, String> data = new HashMap<String, String>();
		data.put("mobile", mobile);
		data.put("serialNo", serialNo);
		data.put("email", email);
		// 构造HttpClient的实例
		//HttpClient httpClient = new HttpClient(data, url);
		//String userInfo = httpClient.sendRequest();
		String userInfo = HttpsUtil.requestPostForm(url, data);
		log.info("Pepsi exchangeCoupon json data:" + userInfo);
		// 取json数据
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(userInfo);
		} catch (JSONException e) {
			log.error(" Parse Json Exception ", e);
		}
		return jsonObj;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

}
