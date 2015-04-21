package com.lvmama.front.web.stored;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.utils.lvmamacard.LvmamaCardUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;
import com.opensymphony.oscache.util.StringUtil;

/**
 * 
 * @author liwenzhan
 *
 */

@ParentPackage("json-default")
@Results( { 
	@Result(type="json",name="checkOrderCode",params={"includeProperties","info.*"})
})

public class AjaxStoredPayAction  extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6170955184441652201L;
	/**
	 * 储值卡卡号.
	 */
	private String cardNo;
	/**
	 * 储值卡密码.
	 */
	private String cardPassword;
	private String verifycode="";
	private String message;
	/**
	 * 卡的流水号.
	 */
	private String serialNo;
	/**
	 * 储值卡查询时候 用到密码 也就卡的后四位。
	 */
	private String cardNoStr;
	/**
	 *  储值卡信息.
	 */
	private StoredCard storedCard;
	/**
	 * 订单SERVICE.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 储值卡支付SERVICE.
	 */
	private StoredCardService storedCardService ;
	/**
	 * 储值卡支付SERVICE.
	 */
	private LvmamacardService lvmamacardService ;
	
	private UserUserProxy userUserProxy;
	
	private String mobileVerifyCode;
	/**
	 * 用户No
	 */
	private String userNo;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMobileVerifyCode() {
		return mobileVerifyCode;
	}

	public void setMobileVerifyCode(String mobileVerifyCode) {
		this.mobileVerifyCode = mobileVerifyCode;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	/**
	 * 判断储值卡（老卡）是不是可以用来支付.
	 * @return
	 */
	@Action(value="/ajax/chackOldCardNo")
	public void isOldCardPay(){
		JSONResult result = new JSONResult();
		try {
			if(UtilityTool.isValid(cardNo)){
				boolean isCode = storedCardService.isCardPay(cardNo);
				if (!isCode) {
					LOG.debug("您的卡号不正确、余额为零或已过期，请重新输入。");
					throw new Exception("您的卡号不正确、余额为零或已过期，请重新输入。");
				}
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 判断储值卡（新卡）是不是可以用来支付.
	 * @return
	 */
	@Action(value="/ajax/chackNewCardNo")
	public void isNewCardPay(){
		JSONResult result = new JSONResult();
		try {
			if(UtilityTool.isValid(cardNo)){
				boolean isCode = true;
				LvmamaStoredCard lvmamaStoredCard = lvmamacardService.getOneStoreCardByCardNo(cardNo);
				if(lvmamaStoredCard!=null){
					if(lvmamaStoredCard.getType().intValue()!=1){
						isCode = false;
					}
					if(!Constant.CARD_STATUS.NOTUSED.name().equalsIgnoreCase(lvmamaStoredCard.getStatus())&&!Constant.CARD_STATUS.USED.name().equalsIgnoreCase(lvmamaStoredCard.getStatus())){
						isCode = false;
					}
					if(!(lvmamaStoredCard.getBalance().longValue()>0)){
						isCode = false;
					}
					if(lvmamaStoredCard.getOverTime()!=null&&DateUtil.isCompareTime(lvmamaStoredCard.getOverTime(),new Date())){
						isCode = false;
					}
				}else{
					isCode = false;
				}
				
				if (!isCode) {
					LOG.debug("您的卡号不正确、余额为零或已过期，请重新输入。");
					throw new Exception("您的卡号不正确、余额为零或已过期，请重新输入。");
				}
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 判断储值卡密码是否正确
	 * @return
	 */
	@Action(value="/ajax/chackCodeNoPassword")
	public void chackCodeNoPassword(){
		JSONResult result = new JSONResult();
		try {
			if(UtilityTool.isValid(cardNo)){
				boolean isCode = true;
				LvmamaStoredCard lvmamaStoredCard = lvmamacardService.getOneStoreCardByCardNo(cardNo);
				if (lvmamaStoredCard==null) {
					LOG.debug("您的卡号密码不正确、请重新输入。");
					throw new Exception("您的卡号密码不正确、请重新输入。");
				}
				if(lvmamaStoredCard.getType().intValue()!=1){
					isCode = false;
				}
				if(!Constant.CARD_STATUS.NOTUSED.name().equalsIgnoreCase(lvmamaStoredCard.getStatus())&&!Constant.CARD_STATUS.USED.name().equalsIgnoreCase(lvmamaStoredCard.getStatus())){
					isCode = false;
				}
				if(!(lvmamaStoredCard.getBalance().longValue()>0)){
					isCode = false;
				}
				if(lvmamaStoredCard.getOverTime()!=null&&DateUtil.isCompareTime(lvmamaStoredCard.getOverTime(),new Date())){
					isCode = false;
				}
				if(!lvmamaStoredCard.getPassword().equals(DESUtils.getInstance().getEncString(cardPassword))){
					isCode = false;
				}
				if (!isCode) {
					LOG.debug("您的卡号密码不正确、请重新输入。");
					throw new Exception("您的卡号密码不正确、请重新输入。");
				}
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 手机校验码校验
	 * @return
	 */
	@Action(value="/ajax/mobileCodeValidate")
	public void mobileCodeValidate() {
		JSONObject result = new JSONObject();
		
		UserUser uu = userUserProxy.getUserUserByUserNo(userNo);
		if(uu==null){
			outputErrorMessage("不存在的用户!");
			return ;
		}
		
		//有手机并且绑定需要校验验证码
		if(StringUtils.isNotBlank(uu.getMobileNumber())&&"Y".equals(uu.getIsMobileChecked())){
			if(StringUtils.isBlank(mobileVerifyCode)){
				outputErrorMessage("请输入手机验证码!");
				return ;
			}
			boolean validateVerifyCodeSuccess = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, mobileVerifyCode, uu.getMobileNumber());
			if(!validateVerifyCodeSuccess){
				outputErrorMessage("您输入的手机验证码不正确!");
				return ;
			}
		}else {
			outputErrorMessage("您输入的手机验证码不正确!");
			return ;
		}
		callback(result);
	}
	
	private void outputErrorMessage(String msg) {
		JSONObject result = new JSONObject();
		result.put("code", 1);
		result.put("msg", msg);
		callback(result);
	}
	
	private void callback(JSONObject json) {
		HttpServletResponse res = this.getResponse();
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try {
			if (!json.has("code")) {
				json.put("code", 0);
			}
			json.put("success", json.getInt("code") == 0);
			if (!json.getBoolean("success")) {
				if (!json.containsKey("msg")
						|| StringUtils.isEmpty(json.getString("msg"))) {
					json.put("msg", "错误未定义");
				}
			}
			String content = "(" + json.toString() + ")";
			res.getOutputStream().write(content.getBytes("UTF-8"));
			res.getOutputStream().flush();
			res.getOutputStream().close();
		} catch (Exception ex) {
			LOG.error(this.getClass(), ex);
		}
	}
	
	/**
	 * 验证码判断.
	 * @return
	 */
	@Action(value="/ajax/chackVerifycode")
	public void isChackVerifycode() {
		JSONResult result = new JSONResult();
		try {
			boolean isVerifycode = false;
			// 取session中的正确验证码
			if (getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE) == null) {
				throw new Exception("提交的验证码与系统的验证码不符！");
			}
			isVerifycode = StringUtils.equals((String) getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE), verifycode);
			LOG.debug("提交的验证码:"
					+ verifycode
					+ ",与系统的验证码:"
					+ (String) getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE)+ "不符!");

			if (!isVerifycode) {
				throw new Exception("提交的验证码与系统的验证码不符！");
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 储值卡查询.
	 * @return
	 * @throws IOException
	 */
	@Action("/ajax/storedSearch")
	public void storedSearch(){
		JSONResult result = new JSONResult();
		try {
			if(!StringUtil.isEmpty(cardNoStr) && !StringUtil.isEmpty(serialNo)){
				if(cardNoStr.length() == 4){
					Map<String, Object> parameter=new HashMap<String,Object>();
					parameter.put("cardNo", cardNoStr);
					parameter.put("serialNo", serialNo);
					List<StoredCard> lst=storedCardService.queryCardListByParam(parameter);
					JSONArray cardUsageArr = new JSONArray();
					if (lst != null && lst.size()>0) {
						storedCard=lst.get(0);
						Map<String, Object> usageparam = buildUsageParameter(storedCard);
						List<StoredCardUsage> list = storedCardService.queryUsageListByCardId(usageparam);
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								JSONObject cardUsage = new JSONObject();
								StoredCardUsage usage = list.get(i);
								String productName="";
								OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(usage.getOrderId());
								if(order!=null&&order.getOrdOrderItemProds()!=null&&order.getOrdOrderItemProds().size()>0){
									for(int t=0;t<order.getOrdOrderItemProds().size();t++){
										productName=productName+"/"+order.getOrdOrderItemProds().get(t).getProductName();
									}
								}
								cardUsage.put("createTime", DateUtil.formatDate(usage.getCreateTime(),"yyyy/MM/dd"));
								cardUsage.put("orderId", usage.getOrderId());
								cardUsage.put("zhUsageType", usage.getZhUsageType());
								cardUsage.put("productName", productName.substring(1, productName.length()));
								cardUsage.put("amountFloat", usage.getAmountFloat());
								cardUsageArr.add(i, cardUsage);
							}
						}
					}else{
						message = "流水号或卡密码不正确 !";
						result.put("message", message);
					}
					JSONObject storedCardObj = new JSONObject();
					storedCardObj.put("serialNo", storedCard.getSerialNo());
					storedCardObj.put("amountFloat", storedCard.getAmountFloat());
					storedCardObj.put("blanceFloat", storedCard.getBalanceFloat());
					storedCardObj.put("overTime", DateUtil.formatDate(storedCard.getOverTime(),"yyyy年MM月dd日"));
					result.put("storedCardObj", storedCardObj);
					result.put("cardUsageArr", cardUsageArr);
				}else{
					message = "流水号或卡密码不正确 !";
					result.put("message", message);
				}
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 驴游天下卡
	 * @return
	 * @throws IOException
	 * @author nixianjun  13.12.16
	 */
	@Action("/ajax/storedSearchForNewCard")
	public void storedSearchForNewCard(){
		JSONResult result = new JSONResult();
		try {
			if(!StringUtil.isEmpty(cardNo) && !StringUtil.isEmpty(cardPassword)){
				if(cardNo.length() == 12&&cardPassword.length()==8){
 					LvmamaStoredCard lvmaStoredCard=lvmamacardService.getOneStoreCardByCardNo(cardNo);
					JSONArray cardUsageArr = new JSONArray();
					if (lvmaStoredCard != null &&DESUtils.getInstance().getEncString(cardPassword).equals(lvmaStoredCard.getPassword())) {
 						Map<String, Object> usageparam = new HashMap<String, Object>();
 						usageparam.put("cardId", lvmaStoredCard.getStoredCardId());
						List<StoredCardUsage> list = storedCardService.queryUsageListByCardId(usageparam);
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								JSONObject cardUsage = new JSONObject();
								StoredCardUsage usage = list.get(i);
								String productName="";
								OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(usage.getOrderId());
								if(order!=null&&order.getOrdOrderItemProds()!=null&&order.getOrdOrderItemProds().size()>0){
									for(int t=0;t<order.getOrdOrderItemProds().size();t++){
										productName=productName+"/"+order.getOrdOrderItemProds().get(t).getProductName();
									}
								}
								cardUsage.put("orderId", usage.getOrderId());
								cardUsage.put("productName", productName.substring(1, productName.length()));
								cardUsage.put("amountFloat", usage.getAmountFloat());
//								cardUsage.put("zhUsageType", usage.getZhUsageType());
								cardUsage.put("orderPay", order.getOrderPayFloat());
								cardUsage.put("createTime", DateUtil.formatDate(usage.getCreateTime(),"yyyy/MM/dd"));
								cardUsageArr.add(i, cardUsage);
							}
						}
					}else{
						message = "卡号或卡密码不正确 !";
						result.put("message", message);
					}
					JSONObject storedCardObj = new JSONObject();
					storedCardObj.put("cardNo", lvmaStoredCard.getCardNo());
					storedCardObj.put("amountFloat", lvmaStoredCard.getAmountFloat());
					storedCardObj.put("usedamountFloat", lvmaStoredCard.getAmountFloat()-lvmaStoredCard.getBalanceFloat());
					storedCardObj.put("blanceFloat", lvmaStoredCard.getBalanceFloat());
					storedCardObj.put("overTime", DateUtil.formatDate(lvmaStoredCard.getOverTime(),"yyyy年MM月dd日"));
					result.put("storedCardObj", storedCardObj);
					result.put("cardUsageArr", cardUsageArr);
				}else{
					message = "卡号或卡密码不正确 !";
					result.put("message", message);
				}
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 驴游天下卡
	 * @return
	 * @throws IOException
	 * @author nixianjun  13.12.16
	 */
	@Action("/ajax/storedSearchForNewCard2")
	public void storedSearchForNewCard2(){
		JSONResult result = new JSONResult();
		try {
			if(!StringUtil.isEmpty(cardNo) && !StringUtil.isEmpty(cardPassword)){
				if(cardNo.length() == 12&&cardPassword.length()==8){
 					LvmamaStoredCard lvmaStoredCard=lvmamacardService.getOneStoreCardByCardNo(cardNo);
 					if (lvmaStoredCard != null &&DESUtils.getInstance().getEncString(cardPassword).equals(lvmaStoredCard.getPassword())) { 
						JSONObject storedCardObj = new JSONObject();
						storedCardObj.put("overTime", DateUtil.formatDate(lvmaStoredCard.getOverTime(),"yyyy MM dd"));
						result.put("storedCardObj", storedCardObj);
						log.info("storedCardObj:"+storedCardObj);
					}else{
						message = "卡号或卡密码不正确 !";
						result.put("message", message);
					}
 				}else{
					message = "卡号或卡密码不正确 !";
					result.put("message", message);
				}
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 驴游天下卡延期
	 * @return
	 * @throws IOException
	 * @author nixianjun  13.12.16
	 */
	@Action("/ajax/storedSearchForNewCardForDeplay")
	public void storedSearchForNewCardForDeplay(){
		JSONResult result = new JSONResult();
		try {
			LvmamaStoredCard lvmamaCard = lvmamacardService
					.getOneStoreCardByCardNo(cardNo);
			if (lvmamaCard == null) {
 				result.put("message", "卡号:" + cardNo + "不存在");
				result.output(getResponse());
				return;
			}
			if (lvmamaCard.getOverTime() == null) {
 				result.put("message", "卡号:" + cardNo + "此卡永久有效");
				result.output(getResponse());
				return;
			}
			if (lvmamaCard.getStatus().equals(
					Constant.CARD_STATUS.FREEZE.getCode())) {
				result.put("message", "卡号:" + lvmamaCard.getCardNo()
						+ "已经冻结，不能延期！");
				result.output(getResponse());
				return;
			}
			if (lvmamaCard.getBalance() < LvmamaCardUtils.FIFTY) {
 				result.put("message", "卡号:" + lvmamaCard.getCardNo()
						+ "金额小于50元，不能延期！");
				result.output(getResponse());
				return;
			}
			if(super.getUser()!=null&&super.getUser().getUserName()!=null){
				lvmamacardService.doDeplay(lvmamaCard,"前台用户："+super.getUser().getUserName());
			}else{
				lvmamacardService.doDeplay(lvmamaCard,"前台匿名用户");
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
			result.put("message", "延期失败");
			result.output(getResponse());
			return;
		}
		result.output(getResponse());
	}
	
	 /**
     * 包装储值卡查询条件.
     * @return
     */
    private Map<String,Object> buildParameter(){
		Map<String,Object> map=new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(this.serialNo)) {
			map.put("serialNo", serialNo);
		}
		if (StringUtils.isNotEmpty(this.cardNoStr)) {
			map.put("cardNo", cardNoStr);
		}
		return map;
	}
    /**
     * 包装储值卡支付记录的查询条件.
     * @return
     */
    private Map<String,Object> buildUsageParameter(StoredCard storedCard){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("cardId", storedCard.getStoredCardId());
		return map;
	}
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCardNoStr() {
		return cardNoStr;
	}

	public void setCardNoStr(String cardNoStr) {
		this.cardNoStr = cardNoStr;
	}
	public StoredCard getStoredCard() {
		return storedCard;
	}
	public void setStoredCard(StoredCard storedCard) {
		this.storedCard = storedCard;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
