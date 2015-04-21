package com.lvmama.front.web.callback;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.google.zxing.BarcodeFormat;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkCouponUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.UserCouponDTO;
import com.lvmama.front.web.BaseAction;

/**
 * 随视微信回调接口
 * 
 * @author dingming
 * 
 */
@Namespace(value="/callback")
public class WeChatCallbackAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory
			.getLog(WeChatCallbackAction.class);
	private static  List<Long> couponIdList;
	
	static{
		couponIdList=new ArrayList<Long>();
		couponIdList.add(4305L);
		couponIdList.add(4306L);
		couponIdList.add(4307L);
		couponIdList.add(4308L);
		couponIdList.add(4310L);
	}

	/**
	 * 用户服务类
	 */

	protected UserUserProxy userUserProxy;
	private MarkCouponService markCouponService;
	private MarkCouponUserService markCouponUserService;
	private String uid;
	private String openid;
	private String subscribe;//是否关注驴妈妈企业微信号 0:否 1:是
	private int flag;
	private String mbwechatId;
	private String mbSubscribe;//是否关注驴妈妈企业微信号 0:否 1:是  无线用
	/**
	 * 驴妈妈用户id
	 */
	private String userid;
	/**
	 * 随视微信回调地址,在随视提供的后台获取到
	 */
	private  String REDIRECT_URI="http://call.socialjia.com/WxWeb/5211c4e5650e4?uid=";

	@Action(value = "wechat")
	public void getMessageFromSH(){
		UserUser user = null;
		if (!StringUtil.isEmptyString(uid)) {
			user = userUserProxy.getUserUserByUserNo(uid);
	   }
		if(user!=null&&userUserProxy.getUsersByMobOrNameOrEmailOrCard(openid)==null){
					user.setWechatId(openid);
					user.setSubScribe(subscribe);
					userUserProxy.update(user);
					LOG.info(user.getUserName()+" bind wechat id:"+openid);
					
				if(subscribe!=null&&Integer.valueOf(subscribe)==1){
					boolean res=isCanGiveCoupon(user);
					if(res==true){
						for(Long couponId:couponIdList){
							MarkCouponCode couponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
							markCouponService.bindingUserAndCouponCode(user, couponCode.getCouponCode());
							LOG.info(user.getUserName()+",enjoy coupon id=="+couponCode.getCouponId()+";");
						}
					}
				}
		}
		
		if(user!=null&&user.getWechatId()!=null&&flag==1){
		    //手机客户端调用 传此参数  flag==1
			String callback=getRequest().getParameter("jsoncallback");
			JSONObject JSONObject = new JSONObject();
			JSONObject.put("data", "success");
			sendAjaxResultByJson(callback+"("+JSONObject.toString()+")");
	}
	}
	
	/**
	 * 无线送优惠券接口
	 */
	@Action(value = "giveCoupon")
	public void giveCoupon(){
		if(!StringUtil.isEmptyString(mbwechatId)&& !StringUtil.isEmptyString(mbSubscribe)){
			UserUser user=userUserProxy.getUsersByMobOrNameOrEmailOrCard(mbwechatId);
			JSONObject JSONObject = new JSONObject();
			String flag="fail";
			if(user!=null){
				user.setSubScribe(mbSubscribe);
				userUserProxy.update(user);
				if(Integer.valueOf(mbSubscribe)==1){
					if(isCanGiveCoupon(user)){
						for(Long couponId:couponIdList){  
							MarkCouponCode couponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
							markCouponService.bindingUserAndCouponCode(user, couponCode.getCouponCode());
							LOG.info(user.getUserName()+",enjoy coupon id=="+couponCode.getCouponId()+";");
				      	    }
						flag="success";
				        }else{
				        	flag="used";
				        }
				 }
				JSONObject.put("userName", user.getUserName());
		    }
			JSONObject.put("data", flag);
			sendAjaxResultByJson(JSONObject.toString());
		}
	}

	/**
	 * 产生随视二维码
	 */
	@Action(value="generateCodeImage")
	public void generateCodeImage(){
		if(userid!=null&&!userid.equals("")){
			REDIRECT_URI=URLEncoder.encode((REDIRECT_URI+userid));
			String content="http://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8d97e09f8f2c3323&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=snsapi_base&state=STK";
			try {
				com.lvmama.front.web.callback.QRCode.encode(content, getResponse().getOutputStream(), BarcodeFormat.QR_CODE, 130, 130, null);
			} catch (IOException e) {
				LOG.error(this, e);
			}
		}
	}
	
	public boolean isCanGiveCoupon(UserUser user){
			Long user_Id=user.getId();
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("userId", user_Id);
			List<UserCouponDTO> userCouponDTOs=markCouponUserService.getMySpaceUserCouponData(params);
			Long couponID;
			for(UserCouponDTO userCouponDTO:userCouponDTOs){
				couponID=userCouponDTO.getMarkCoupon().getCouponId();
				if(couponID.equals(3704L)||couponID.equals(3705L)||couponID.equals(3706L)||couponID.equals(3707L)||couponID.equals(3708L)){
					return false;
				}
			}
		return true;
		
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
	
	public void setMarkCouponUserService(MarkCouponUserService markCouponUserService) {
		this.markCouponUserService = markCouponUserService;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMbSubscribe() {
		return mbSubscribe;
	}

	public void setMbSubscribe(String mbSubscribe) {
		this.mbSubscribe = mbSubscribe;
	}

	public String getMbwechatId() {
		return mbwechatId;
	}

	public void setMbwechatId(String mbwechatId) {
		this.mbwechatId = mbwechatId;
	}


}
