package com.lvmama.clutter.web.html5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.service.IClientUserService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.vo.Constant;

/**
 * 活动处理
 * @author jswangqian
 *
 */
@Results({ 
	@Result(name = "myconpon", location = "/WEB-INF/pages/user/myconpon.html", type="freemarker")
})
@Namespace("/mobile/promotionAction")
public class PromotionAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	
	//活动来源类型
	//String MOBILE_ACTIVITY_12580 = "mobile_activity_12580";// 12580活动
	//	MOBILE_ACTIVITY_12580_LOGIN = "mobile_activity_12580_login";// 12580登录活动
	//	MOBILE_ACTIVITY_12580_REG = "mobile_activity_12580_reg";// 12580注册活动
	//	MOBILE_ACTIVITY_10000_LOGIN = "mobile_activity_10000_login";// 12580登录活动
	//	MOBILE_ACTIVITY_10000_REG = "mobile_activity_10000_reg";// 12580注册活动
	private String activityChannel = ""; // losc代码
	
	//活动类型
	private String promotionActionType;//活动类型----优惠券，返回奖金等(目前只有优惠券)-----
	
	//优惠券服务
	private MarkCouponService markCouponService;
	
	//user 服务
	protected IClientUserService mobileUserService;

	@Action("promotionAction")
	public String PromotionAction(){
		
		// 处理相关活动。
		if(!StringUtils.isEmpty(activityChannel)) {
			// 12580活动 10000活动  农行活动  华尔街活动  驴妈妈营销活动
			if(activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_12580_LOGIN) 
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_12580_REG)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_10000_LOGIN) 
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_10000_REG)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_NONGHANG_LOGIN)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_NONGHANG_REG)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_HUAERJIE_LOGIN)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_HUAERJIE_REG)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_LVMAMA_LOGIN)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_LVMAMA_REG)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_DISHINI_LOGIN)
					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_DISHINI_REG)){
				// 相关逻辑 - 赠送优惠券  3891,3892,3893,3894,3895,3896
				boolean b = sendConpon4Wap();
				if(b){ // 如果赠送成功
					getRequest().setAttribute(ClutterConstant.HAS_SEND_CONPON, "true");
					//ServletUtil.addCookie(getResponse(), ClutterConstant.MOBILE_ACTIVITY_LOSC, "", 0, false);// 清楚相关cookies
				}
			}
		}
		// 可用优惠券
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUser().getId());
		params.put("state", "USED");
		List<MobileUserCoupon> usedList = mobileUserService.getCoupon(params);
		// 已用优惠券
		params.put("state", "NOT_USED");
		List<MobileUserCoupon> unusedList = mobileUserService.getCoupon(params);
		getRequest().setAttribute("usedList", usedList);
		getRequest().setAttribute("unusedList", unusedList);
		return "myconpon";
	}
	@Action("promotionActionAjax")
	public void PromotionActionajax(){
		JSONObject jsonObj = new JSONObject();
		boolean b =false;
		try {
			// 处理相关活动。
			if(!StringUtils.isEmpty(activityChannel)) {
				// 12580活动 10000活动  农行活动  华尔街活动  驴妈妈营销活动
				if(activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_DISHINI_LOGIN) || activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_DISHINI_REG)){
					b = sendConpon4Wap();
				}
			}
		} catch (Exception e) {
			jsonObj.put("status", -1);//报错
		}
		if(b==true){
			jsonObj.put("status", 0);//成功
		}else{
			jsonObj.put("status", 1);//失败
		}
		this.sendAjaxResult(jsonObj.toString());
	}
	public boolean sendConpon4Wap() {
		try {
			// 12580活动活动有效期 2014-01-31 23:59:59
			if(activityChannel.indexOf("mobile_activity_12580") != -1 
					&& !ClientUtils.isValidateDate("", "2014-01-31 23:59:59", "yyyy-MM-dd HH:mm:ss")) {
				return false;
			}
			//农行活动有效期2014-03-31 23:59:59
			if(activityChannel.indexOf("mobile_activity_nonghang") !=-1 
					&& !ClientUtils.isValidateDate("", "2014-03-31 23:59:59", "yyyy-MM-dd HH:mm:ss")){
				return false;
			}
			
			// 是否已经赠送过 - 如果没有赠送过
			UserUser user = getUser();
			if(null == user) {
				log.info("sendConpon4Wap user is null...");
				return false;
			}
			// 默认批次号
			String strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.id");
			
			// 如果是电信的(批次号)
			if(activityChannel.indexOf("mobile_activity_10000") != -1) {
				strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.10000.id");
			}
			
			//农行批次号
			if(activityChannel.indexOf("mobile_activity_nonghang") != -1) {
				strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.nonghang.id");
			}
			
			//华尔街批次号
			if(activityChannel.indexOf("mobile_activity_huaerjie") != -1) {
				strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.huaerjie.id");
			}
			
			//驴妈妈营销活动批次号
			if(activityChannel.indexOf("mobile_activity_lvmama") != -1) {
				strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.lvmama.id");
			}
			
			//驴妈妈迪士尼活动批次号
			if(activityChannel.indexOf("mobile_activity_dishini") != -1) {
				strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.dishini.id");
			}
			
			if(StringUtils.isEmpty(strCouponIds)) {
				log.info("strCouponIds is null...");
				return false;
			}
			// 如果没有赠送
			boolean b = false; // 判断是否送过优惠券
			String[] strcouponIdArr = strCouponIds.trim().split(",");
			for(int i = 0 ;i < strcouponIdArr.length;i++) {
				long couponId = Long.valueOf(strcouponIdArr[i]);
				if(!isGiveConpon4Activity(couponId,user)) {
					MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
					markCouponService.bindingUserAndCouponCode(user,markCouponCode.getCouponCode());
					b = true;
				}
			}
			return b;
		}catch(Exception e){
			log.info("sendConpon4Wap error....");
			e.printStackTrace();
		}
	    return false;
	}
	public boolean isGiveConpon4Activity(Long couponId,UserUser u){
		try{
			List<MarkCouponUserInfo> list = markCouponService.queryMobileUserCouponInfoByUserId(u.getId());
			if(null != list && list.size() > 0){
				String couponid = couponId+"";
				for(int i =0;i < list.size();i++) {
					MarkCouponUserInfo mui = list.get(i);
					if(null != mui.getMarkCoupon() && couponid.equals(String.valueOf(mui.getMarkCoupon().getCouponId()))) {
						// 如果已经赠送过优惠券
						return true;
					}
				}
			}
		}catch(Exception e){
			log.info("isGiveConpon4Activity exceptiou .....");
			e.printStackTrace();
		}
		return false;
	}
	public String getActivityChannel() {
		return activityChannel;
	}
	public void setActivityChannel(String activityChannel) {
		this.activityChannel = activityChannel;
	}
	public MarkCouponService getMarkCouponService() {
		return markCouponService;
	}
	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
	public String getPromotionActionType() {
		return promotionActionType;
	}
	public void setPromotionActionType(String promotionActionType) {
		this.promotionActionType = promotionActionType;
	}
	public IClientUserService getMobileUserService() {
		return mobileUserService;
	}
	public void setMobileUserService(IClientUserService mobileUserService) {
		this.mobileUserService = mobileUserService;
	}
}
