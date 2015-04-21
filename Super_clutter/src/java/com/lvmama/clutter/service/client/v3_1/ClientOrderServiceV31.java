package com.lvmama.clutter.service.client.v3_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.coupon.CouponValidateInfo;
import com.lvmama.clutter.model.coupon.MobilelValidateBusinessCouponInfo;
import com.lvmama.clutter.service.impl.ClientOrderServiceImpl;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.pet.po.businessCoupon.ValidateBusinessCouponInfo;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

public class ClientOrderServiceV31 extends ClientOrderServiceImpl{
	
	private static final Log log = LogFactory.getLog(ClientOrderServiceImpl.class);
	
	@Override
	public Map<String,Object> commitOrder(Map<String,Object> param) {
		return super.commitOrder(param);
	}

	public Map<String,Object> validateTravellerInfo(Map<String,Object> param){
		ArgCheckUtils.validataRequiredArgs("firstChannel","secondChannel","branchItem","visitTime","udid",param);

		String branchItem = param.get("branchItem").toString();
		String visitTime = param.get("visitTime").toString();
		String leaveTime = param.get("leaveTime")==null?null:param.get("leaveTime").toString();
		
		boolean isTodayOrder  = false;
		
		if(param.get("todayOrder")!=null){
			isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());
		}
		
		String[] branchArray = branchItem.split("_");

		if(branchArray.length==0){
			throw new RuntimeException("类别项构建错误!");
		}
	
		
		BuyInfo createOrderBuyInfo = new BuyInfo();
		
		Map<String,Object> result  = this.setOrderItems(branchArray, isTodayOrder, visitTime, leaveTime, createOrderBuyInfo,null);
		if(result!=null && (Boolean)result.get("noEmergencyContact")==true&&(Integer)result.get("travellerNumber")==0){
			/**
			 * 如果不需要填写紧急联系人和游玩人 直接提交订单。
			 */
			return this.commitOrder(param);
		}
		result.remove("mainPoduct");
		return result;
		
	}
	
	public Map<String,Object> validateCoupon(Map<String,Object> param){
		ArgCheckUtils.validataRequiredArgs("firstChannel","secondChannel","branchItem","visitTime","udid","validateCouponCode",param);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String branchItem = param.get("branchItem").toString();
		String visitTime = param.get("visitTime").toString();
		String leaveTime = param.get("leaveTime")==null?null:param.get("leaveTime").toString();
		
		boolean validateCouponCode = Boolean.valueOf(param.get("validateCouponCode").toString().toUpperCase());

		if(validateCouponCode==true&& param.get("couponCode")==null){
			throw new LogicException("必须输入优惠券");
		}
//		String udid = param.get("udid").toString();
//		String firstChannel = param.get("firstChannel").toString();
//		String secondChannel = param.get("secondChannel").toString();
		String couponCode = param.get("couponCode")==null?null:param.get("couponCode").toString().toUpperCase();
		
		boolean isTodayOrder  = false;
		if(param.get("todayOrder")!=null){
			isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());
		}
		
		
		
		String[] branchArray = branchItem.split("_");

		if(branchArray.length==0){
			throw new RuntimeException("类别项构建错误!");
		}
	
	
		BuyInfo createOrderBuyInfo = new BuyInfo();
		
	    this.setOrderItems(branchArray, isTodayOrder, visitTime, leaveTime, createOrderBuyInfo,couponCode);
		createOrderBuyInfo.setTodayOrder(isTodayOrder);
		
		FavorResult fr =  favorService.calculateFavorResultByBuyInfo(createOrderBuyInfo);
		createOrderBuyInfo.setFavorResult(fr);

		PriceInfo priceInfo = orderServiceProxy.countPrice(createOrderBuyInfo);
		
		if(validateCouponCode){
			this.throwValidateCouponInfo(fr, priceInfo);
			CouponValidateInfo cvi = new CouponValidateInfo();
			cvi.setKey(priceInfo.getInfo().getKey());
			cvi.setValid(priceInfo.getInfo().isValid());
			cvi.setValue(priceInfo.getInfo().getValue());
			cvi.setReturnCouponCode(couponCode);
			cvi.setYouhuiAmountYuan(PriceUtil.getLongPriceYuan(priceInfo.getInfo().getYouhuiAmount()));
			resultMap.put("validateInfo",cvi);
			resultMap.put("oughtPay", priceInfo.getOughtPay());
			return resultMap;
		}

		List<MobilelValidateBusinessCouponInfo> list = new ArrayList<MobilelValidateBusinessCouponInfo>();
		if(priceInfo.getValidateBusinessCouponInfoList()!=null && !priceInfo.getValidateBusinessCouponInfoList().isEmpty()){
			for (ValidateBusinessCouponInfo  vbci : priceInfo.getValidateBusinessCouponInfoList()) {
				MobilelValidateBusinessCouponInfo mvbci = new MobilelValidateBusinessCouponInfo();
				mvbci.setAmountYuan(vbci.getAmountYuan());
				ProdProductBranch ppb = prodProductBranchService.selectProdProductBranchByPK(vbci.getProductBranchId());
				if(ppb!=null){
					vbci.setDisplayInfo(ppb.getBranchName()+vbci.getDisplayInfo());
				}
				mvbci.setTitle(Constant.BUSINESS_COUPON_TYPE.getCnName(vbci.getCouponType()));
				String pattern = "(<font.*?>)";
				String strReplace = vbci.getDisplayInfo().replaceFirst(pattern, "<font color='#D51077' font-style='normal' font-weight='blod'>");
				mvbci.setDesc(strReplace);// 过滤html代码
				list.add(mvbci);
			}
		}
		resultMap.put("businessCouponInfoList", list);
		resultMap.put("oughtPay", priceInfo.getOughtPay());
		return resultMap;

	}

//	public static void main(String[] args) {
//		String str = "订购每满1份，已立减<font style='color: #FF6600;font-style: normal;font-weight:bold;'>20.0</font>元。";
//		//String str="rr<font>";
//		String pattern = "(</font.*?>";
//		String strReplace = str.replaceFirst(pattern, "<font color='#FF6600' font-style='normal' font-weight='blod'>");
//		Pattern p = Pattern.compile(pattern);  
//		Matcher m = p.matcher(str);  
//		
//
//		while(m.find()){
//			//String strReplace = str.replaceFirst(pattern, m.group(0)+" ###");
//			//System.out.println(strReplace);	
//			System.out.println(m.group(0)); 
//		}
//		
//
//	}
//	
	@Override
	public void addfeedBack(String content, String email, String userId,
			String firstChannel) {
		// TODO Auto-generated method stub
		super.addfeedBack(content, email, userId, firstChannel);
	}
	
	@Override
	public String commitComment(UserUser users, CommonCmtCommentVO comment,
			List<ClientCmtLatitude> cmtLatitudeList) {
		// TODO Auto-generated method stub
		return super.commitComment(users, comment, cmtLatitudeList);
	}

	
}
