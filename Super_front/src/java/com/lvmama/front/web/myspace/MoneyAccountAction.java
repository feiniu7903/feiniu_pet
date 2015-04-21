package com.lvmama.front.web.myspace;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashBonusReturn;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.money.CashPay;
import com.lvmama.comm.pet.po.money.CashRecharge;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.CashAccountChangeLogVO;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

@Results({
	@Result(name = "store_phone_set", location = "/WEB-INF/pages/myspace/sub/account/store_phone_set.ftl", type = "freemarker")
	,@Result(name = "store_phone_change", location = "/WEB-INF/pages/myspace/sub/account/store_phone_change.ftl", type = "freemarker")
	,@Result(name = "store", location = "/WEB-INF/pages/myspace/sub/account/store.ftl", type = "freemarker")
	,@Result(name = "store_add", location = "/WEB-INF/pages/myspace/sub/account/store_add.ftl", type = "freemarker")
	,@Result(name = "store_del", location = "/WEB-INF/pages/myspace/sub/account/store_new_del.ftl", type = "freemarker")
	,@Result(name = "store_password_set", location = "/WEB-INF/pages/myspace/sub/account/store_password_set.ftl", type = "freemarker")
	,@Result(name = "store_password_change", location = "/WEB-INF/pages/myspace/sub/account/store_password_change.ftl", type = "freemarker")
	,@Result(name = "store_password_back", location = "/WEB-INF/pages/myspace/sub/account/store_password_back.ftl", type = "freemarker")
	,@Result(name = "store_del2", location = "/WEB-INF/pages/myspace/sub/account/store_del2.ftl", type = "freemarker")
	,@Result(name = "store_phone_change_success", location = "/WEB-INF/pages/myspace/sub/account/store_phone_change_success.ftl", type = "freemarker")
	,@Result(name = "store_phone_change_fail", location = "/WEB-INF/pages/myspace/sub/account/store_phone_change_fail.ftl", type = "freemarker")
	,@Result(name = "bonus", location = "/WEB-INF/pages/myspace/sub/account/money.ftl", type = "freemarker")
})
public class MoneyAccountAction extends SpaceBaseAction{
	
	private static final long serialVersionUID = -7497786256450963047L;
	
	private CashAccountService cashAccountService ;
	private CashAccountVO moneyAccount;
	private List<CashAccountChangeLogVO> tansList;
	private List<CashBonusReturn> bonusReturnList;
	@SuppressWarnings("rawtypes")
	private Page pageConfig;
	private long currentPage = 1;
	private long pageSize = 10;
	private Map<String,String> moneyInit;
	
	/**
	 * 格式化后的手机号.
	 */
	private String mobileFormat;
	
	/**
	 * 现金账户提现短信验证码
	 */
	private String cashAccountVerifyCode;
	
	/**
	 * 订单号.
	 */
	private Long orderId;
	private OrderService orderServiceProxy;
	private float payAmount;
	private String store_set_type;
	private Long bank;
	private String bankName;
	private String account;
	private String accountName;
	private String mobileNumber;
	private String verifycode;
	private List<CashMoneyDraw> moneyDrawList;
	private UserUserProxy userUserProxy;
	private Long rechargeAmount;
	private String rechargeUrl;
	private String errorMessage;
	private Boolean isDefault = true;
	//奖金支出总条数
	Long totalRecords=0L;
	//奖金收入总条数
	Long totalCount=0L;
	/**
	 * 奖金退款条数
	 */
	Long totalRefund=0L;
	//奖金tab类型
	String bonusTabType;
	
	private OrdOrder firstOrder;//首笔订单
	private String firstOrderCtMobile;//首笔订单联系人手机号码
	private String mobileVerifycode;//手机验证码
	private String emailVerifycode;//邮箱验证码
	private boolean rechargeAble=true;//是否可以充值
	
	/**
	 * 消费tab
	 */
	@Override
	@Action("/myspace/account/store")
	public String execute() throws Exception {
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());//5ad32f1a1f4b4345011f4b8284fb02bf
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserId(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.setPayFrom(CashPay.PayFrom.MONEY.toString());
		compositeQuery.setBonusRefundment("N");
		tansList = queryForTansList(compositeQuery);
		if(tansList!=null && tansList.size()>0){
			for(CashAccountChangeLogVO item:tansList){
				if(StringUtils.isNotEmpty(item.getOrderId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getOrderId()));
					if(order!=null){
						item.setProductName(order.getMainProduct().getProductName());
					}
				}
			}
		}
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		
		return "store";
	}
	/**
	 * 充值tab
	 * @return
	 */
	@Action("/myspace/account/viewcharge")
	public String viewCharge(){
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.RECHARGE);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.setPayFrom(CashPay.PayFrom.MONEY.toString());
		compositeQuery.setBonusRefundment("N");
		tansList = queryForTansList(compositeQuery);
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		return "store";
	}
	/**
	 * 退款tab
	 * @return
	 */
	@Action("/myspace/account/viewrefund")
	public String viewRefund(){
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.setPayFrom(CashPay.PayFrom.MONEY.toString());
		compositeQuery.setBonusRefundment("N");
		tansList = queryForTansList(compositeQuery);
		if(tansList!=null && tansList.size()>0){
			for(CashAccountChangeLogVO item:tansList){
				if(StringUtils.isNotEmpty(item.getOrderId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getOrderId()));
					if(order!=null){
						item.setProductName(order.getMainProduct().getProductName());
					}
				}
			}
		}
		
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		
		return "store";
	}
	/**
	 * 提现tab
	 * @return
	 */
	@Action("/myspace/account/viewdraw")
	public String viewdraw(){
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		CompositeQuery cq = new CompositeQuery(Constant.MoneyAccountChangeType.DRAW);
		cq.setBonusRefundment("N");
		cq.setPayFrom(CashPay.PayFrom.MONEY.toString());
		initMoneyInit(this.getUserId(),cq);

		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyDrawRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.getMoneyDrawRelate().setUserNo(this.getUserId());
		
		Long totalRecords = cashAccountService.queryMoneyDrawCount(compositeQuery);
		pageConfig = Page.page(totalRecords, pageSize, currentPage);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		compositeQuery.setPageIndex(pageIndex);
		
		moneyDrawList = cashAccountService.queryMoneyDraw(compositeQuery);
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		return "store";
	}
	
	/**
	 * 提取公用部分
	 * @return
	 */
	private CompositeQuery createBonusCompositeQuery() {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserId(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery.setBonusRefundment("Y");
		return compositeQuery;
	}
	
	/**
	 * 奖金收入tab
	 * @return
	 */
	@Action("/myspace/account/bonusreturn")
	public String bonusReturn(){
		bonusTabType="bonusreturn";
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(getUser().getId());
		
		//支出的总数据
		totalRecords = cashAccountService.queryMoneyAccountChangeLogCount(createBonusCompositeQuery());
		//收入的总数据
		totalCount=cashAccountService.getBonusReturnCount(this.getUser().getId());
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery.setBonusRefundment("Y");
		//退款的总数据
		totalRefund=cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		
		pageConfig = Page.page(totalCount, pageSize, currentPage);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		bonusReturnList = cashAccountService.queryBonusReturn(this.getUser().getId(),pageIndex.getBeginIndex(),pageIndex.getEndIndex());
		if(bonusReturnList!=null && bonusReturnList.size()>0){
			for(CashBonusReturn item:bonusReturnList){
				if((Constant.BonusOperation.ORDER_AND_COMMENT.name().equalsIgnoreCase(item.getComeFrom())
						||Constant.BonusOperation.ORDER_MANUAL_ADJUST.name().equalsIgnoreCase(item.getComeFrom())) 
						&&StringUtils.isNotEmpty(item.getBusinessId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getBusinessId()));
					if(order!=null){
						item.setProductName(order.getMainProduct().getProductName());
					}
				}else{
					item.setBusinessId("");
					item.setProductName("");
				}
			}
		}
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		return "bonus";
	}
	
	/**
	 * 若该用户没有绑定手机,且用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）,若不满足以上条件,不能充值
	 */
	private void canRecharge(CashAccountVO moneyAccount) {
		Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(getUser().getUserNo());
		if(StringUtils.isBlank(getUser().getMobileNumber())&&!(moneyAccount.getTotalMoney()==0&&orderCounts==0)){
			rechargeAble=false;
		}
	}
	
	/**
	 * 奖金支出tab
	 */
	@Action("/myspace/account/bonuspayment")
	public String bonusPayment() {
		bonusTabType="bonuspayment";
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());//5ad32f1a1f4b4345011f4b8284fb02bf
		
		totalCount=cashAccountService.getBonusReturnCount(this.getUser().getId());
		CompositeQuery compositeQuery = createBonusCompositeQuery();
		totalRecords = cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		
		CompositeQuery compositeQuery1 = new CompositeQuery();
		compositeQuery1.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery1.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery1.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery1.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery1.setBonusRefundment("Y");
		totalRefund=cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery1);
		
		pageConfig = Page.page(totalRecords, pageSize, currentPage);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		compositeQuery.setPageIndex(pageIndex);
		tansList = cashAccountService.queryMoneyAccountChangeLog(compositeQuery);
		if(tansList!=null && tansList.size()>0){
			for(CashAccountChangeLogVO item:tansList){
				if(StringUtils.isNotEmpty(item.getOrderId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getOrderId()));
					if(order!=null){
						item.setProductName(order.getMainProduct().getProductName());
					}
				}
			}
		}
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		return "bonus";
	}
	
	
	/**
	 * 奖金退款tab
	 * @return
	 */
	@Action("/myspace/account/bonusrefund")
	public String bonusRefund(){
		bonusTabType="bonusrefund";
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		
		totalRecords = cashAccountService.queryMoneyAccountChangeLogCount(createBonusCompositeQuery());
		totalCount=cashAccountService.getBonusReturnCount(this.getUser().getId());

		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery.setBonusRefundment("Y");
		
		totalRefund=cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		
		tansList = queryForTansList(compositeQuery);
		if(tansList!=null && tansList.size()>0){
			for(CashAccountChangeLogVO item:tansList){
				if(StringUtils.isNotEmpty(item.getOrderId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getOrderId()));
					if(order!=null){
						item.setProductName(order.getMainProduct().getProductName());
					}
				}
			}
		}
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		
		
		return "bonus";
	}
	
	/**
	 * 账户充值
	 * @return
	 * @throws Exception
	 */
	@Action("/myspace/account/store_add")
	public String storeAdd()throws Exception {
		
		UserUser user = getUser();
		if (null == user) {
			return ERROR;
		}
		
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		//判断是否可以充值
		canRecharge(moneyAccount);
		if(!rechargeAble){
			return returnContactCustomServicePage();
		}
		
		return "store_add";
	}
	
	/**
	 * 账户充值
	 * @return
	 * @throws Exception
	 */
	@Action("/myspace/account/store_recharge")
	public void storeRecharge()throws Exception {
		
		JSONResult result = new JSONResult();
		
		UserUser user = getUser();
		
		if(null==user){
			getResponse().setStatus(403);
			return;
		}
		
		//若该用户没有绑定手机,且用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）,若不满足以上条件,不能充值
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
		Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(user.getUserNo());
		if(StringUtils.isBlank(getUser().getMobileNumber())&&!(moneyAccount.getTotalMoney()==0&&orderCounts==0)){
			result.put("code","-1");
			result.put("msg","您的账户存在安全问题，为了保证您的资金安全，请联系客服");
			result.output(getResponse());
			return;
		}
		
		//判断是否可以充值
		canRecharge(moneyAccount);
		if(!rechargeAble){
			result.put("code","-1");
			result.put("msg","您的账户存在安全问题，为了保证您的资金安全，请联系客服");
			result.output(getResponse());
			return;
		}
		
		
		//未绑定手机用户需先绑定手机
		if(StringUtils.isBlank(user.getMobileNumber())){
			
			if(StringUtils.isBlank(this.getMobileNumber())){
				result.put("code","-1");
				result.put("msg","请输入手机号码");
				result.output(getResponse());
				return;
			}
			
			if(StringUtils.isBlank(this.getVerifycode())){
				result.put("code","-1");
				result.put("msg","请输入手机验证码");
				result.output(getResponse());
				return;
			}
			
			if(!userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, getMobileNumber())){
				result.put("code","-1");
				result.put("msg","该手机号码已经存在");
				result.output(getResponse());
				return;
			}
			
			//校验手机验证码是否的正确
			if (!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, this.getVerifycode(), this.getMobileNumber()) ) {
				result.put("code","-1");
				result.put("msg","手机验证码输入错误");
				result.output(getResponse());
				return;
			} 
			
			//绑定手机
			String oldStatus=user.getIsMobileChecked();
			user.setMobileNumber(this.getMobileNumber());
			user.setIsMobileChecked("Y");
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);
			//如果用户验证过的手机注销过，则不再发送手机验证的奖励积分
			if (!"F".equalsIgnoreCase(oldStatus)) {
				userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
			}
			collectModifyUserInfoAction(user,"bindMobile", this.getMobileNumber());	
		}
		
		//生成充值记录
		CashRecharge cashRecharge = new CashRecharge();
		cashRecharge.setCashAccountId(moneyAccount.getCashAccountId());
		cashRecharge.setAmount(rechargeAmount);
		cashRecharge.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
		cashRecharge.setCreateTime(new Date());
		Long cashRechargeId = cashAccountService.insertCashRecharge(cashRecharge);
		String objectType = "CASH_RECHARGE";
		String paymentType = Constant.PAYMENT_OPERATE_TYPE.PAY.name();
		String bizType = Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name();
		String waitPayment = "1";
		String approveTime = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		String visitTime = approveTime;
		String dataStr = cashRechargeId+objectType+rechargeAmount+paymentType+bizType+PaymentConstant.SIG_PRIVATE_KEY;
		log.info("source: " + dataStr);
		String signature = MD5.md5(dataStr);
		log.info("md5: " + signature);
	    rechargeUrl = "objectId="+cashRechargeId+"&amount="+rechargeAmount+"&objectType="+objectType+"&paymentType="+paymentType+"&bizType="+bizType+"&waitPayment="+waitPayment+"&approveTime=" +approveTime + "&visitTime=" + visitTime + "&signature="+signature;
	    result.put("code","0");
	    result.put("rechargeUrl", rechargeUrl);
	    result.output(getResponse());
	}
	
	/**
	 * 校验手机号码.
	 */
	@Action("/myspace/account/validateMobileNumber")
	public void validateMobileNumber(){
		JSONResult result = new JSONResult();
		try{
			moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
			if(StringUtils.isNotEmpty(moneyAccount.getMobileNumber()) && StringUtils.isNotEmpty(mobileNumber) && moneyAccount.getMobileNumber().equals(mobileNumber)){
				result.put("validateSuccess", true);
			}else {
				LOG.info("mobileNumber: " + mobileNumber);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 提交提现
	 * @return
	 */
	@Action("/myspace/account/submitdraw")
	public String submitDraw(){
		UserUser user = getUser();
		if (null == user) {
			return ERROR;
		}
		
		//没有手机，也没有邮箱
		if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getMobileNumber())) {
			return returnContactCustomServicePage();
		}
		
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
		if("N".equals(moneyAccount.getValid())){
			return this.applydraw();
		}
		
		if(moneyAccount.getMaxDrawMoney()==0){
			return this.applydraw();
		}
		
		if(StringUtils.isBlank(this.getFirstOrderCtMobile())){
			errorMessage = "首笔订单联系人手机号码不能为空!";
			return this.applydraw();
		}
		
		//校验首笔订单联系人手机号码
		this.firstOrder=orderServiceProxy.queryUserFirstOrder(getUser().getUserNo());
		
		if(!firstOrder.getContact().getMobile().equals(this.getFirstOrderCtMobile().trim())){
			errorMessage = "首笔订单联系人手机号码填写错误!";
			return this.applydraw();
		}
		
		//绑定手机的，校验手机验证码
		if(StringUtils.isNotBlank(user.getMobileNumber())){
			if(!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, this.getMobileVerifycode(), user.getMobileNumber())){
				errorMessage = "手机验证码输入错误!";
				return this.applydraw();
			}
			//绑定手机
			String oldStatus=user.getIsMobileChecked();
			user.setIsMobileChecked("Y");
			userUserProxy.update(user);
			putSession(Constant.SESSION_FRONT_USER, user);
			//如果用户验证过的手机注销过，则不再发送手机验证的奖励积分
			if (!"F".equalsIgnoreCase(oldStatus)) {
				userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
			}
			collectModifyUserInfoAction(user,"bindMobile", this.getMobileNumber());	
		}
		
		//没有绑定手机的，绑定邮箱的校验邮箱验证码
		if(StringUtils.isBlank(user.getMobileNumber())&&StringUtils.isNotBlank(user.getEmail())){
			if(!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.EMAIL, this.getEmailVerifycode(), user.getEmail())){
				errorMessage = "邮箱验证码输入错误!";
				return this.applydraw();
			}
			
			//判断邮箱是否激活
			String oldStatus=user.getIsEmailChecked();
			if(!"Y".equals(oldStatus)){
				user.setIsEmailChecked("Y");
				userUserProxy.update(user);
				putSession(Constant.SESSION_FRONT_USER, user);
				deleteEMVCookie(this.getRequest(), this.getResponse());
				//如果用户验证过的邮箱注销过，则不再发送邮箱验证的奖励积分
				if (!"F".equalsIgnoreCase(oldStatus)) {
					userUserProxy.addUserPoint(user.getId(),"POINT_FOR_EMAIL_AUTHENTICATION", null, null);
				}
				collectModifyUserInfoAction(user,"EMAIL_AUTHENTICATE", "-->"+user.getEmail());	
			}
			
		}
		
		if(StringUtils.isBlank(bankName)){
			errorMessage = "请选择银行";
			return this.applydraw();
		}
		
		if(StringUtils.isBlank(accountName)){
			errorMessage = "收款户名不能为空";
			return this.applydraw();
		}
		
		if(StringUtils.isBlank(account)){
			errorMessage = "银行卡号不能为空!";
			return this.applydraw();
		}
		
		try {
			cashAccountService.applyDraw2Bank(this.getUser().getId(), bankName, account,accountName, "", "", "",(new Float(payAmount).longValue()), false,"2",this.getUser().getUserName(),"false");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "store_del2";
	}
	
	
	/**
	 * 删除EMAIL 验证标志位COOKIE
	 * @param request
	 * @param response
	 */
	protected void deleteEMVCookie(HttpServletRequest request, HttpServletResponse response){
		// see if the user sent us a valid TGC
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
                  if (StringUtils.equals(
						cookies[i].getName(), "EMV")) {
					cookies[i].setMaxAge(0);
					cookies[i].setValue(null);
					cookies[i].setDomain(".lvmama.com");
					cookies[i].setPath("/");
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
	}
	
	/**
	 * 转到申请提现页面
	 * @return
	 */
	@Action("/myspace/account/store_del")
	public String applydraw(){
		
		UserUser user = getUser();
		
		if (null == user) {
			return ERROR;
		}
		
		//没有手机，也没有邮箱，需要联系客服
		if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getMobileNumber())) {
			return returnContactCustomServicePage();
		}
		
		//获取奖金账户的状态
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
		if("N".equals(moneyAccount.getValid())){
			errorMessage="您的账户已被冻结！请联系客服。";
			return "store_del";
		}
		
		//可提现金额判断
		if(moneyAccount.getMaxDrawMoney()==0){
			errorMessage="可提现金额为0，您不能申请提现!";
			return "store_del";
		}
		
		//查询用户首笔订单信息
		this.firstOrder=orderServiceProxy.queryUserFirstOrder(getUser().getUserNo());
		
		return "store_del";
	}
	
	
	
	/**
	 * 手机号绑定
	 * @return
	 */
	@Action("/myspace/account/store_phone_set")
	public String bindingMobileIndex(){
		UserUser user = getUser();
		if (null != user
				&& StringUtils.isNotBlank(user.getMobileNumber())
				&& "Y".equalsIgnoreCase(user.getIsMobileChecked())
				&& null != cashAccountService.queryMoneyAccountByUserId(this.getUser().getId())) {
			
			//userClient.sendAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, user, "SMS_FOR_CASH_ACCOUNT_TO_ACCOUNT_MOBILE");
			
			return "store_phone_set";
		} else {
			return ERROR;
		}
	}
	
	/**
	 * 手机号修改
	 * @return
	 */
	@Action(value="/myspace/account/store_phone_change")
	public String changeMobileIndex(){
		UserUser user = getUser();
		moneyAccount = cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());
		if (null != user && null != moneyAccount) {
			return "store_phone_change";
		} else {
			return ERROR;
		}
	}	
	
	/**
	 * 手机号修改结果页面
	 * @return
	 */
	@Action(value="/myspace/account/store_phone_change_success")
	public String changeMobile(){
		UserUser user = getUser();
		String authOldMobileCode = getRequest().getParameter("authOldMobileCode");
		String authenticationCode = getRequest().getParameter("authenticationCode");
		String mobile = getRequest().getParameter("mobile");
		moneyAccount =cashAccountService.queryMoneyAccountByUserId(this.getUser().getId());		
		mobile = mobile == null ? null : mobile.replaceAll(" ", "");
		
		if (StringUtils.isBlank(authOldMobileCode) 
				|| StringUtils.isBlank(authenticationCode)
				|| StringUtils.isBlank(mobile)) {
			return "store_phone_change_fail";
		}
		
		if (null != user 
				&& null != moneyAccount
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authOldMobileCode, moneyAccount.getMobileNumber())
				&& userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile)
				&& cashAccountService.bindMobileNumber(user.getId(), mobile, true)) {
			return "store_phone_change_success";
			} else {
				return "store_phone_change_fail";
			}
	}	
	
	
	private List<CashAccountChangeLogVO> queryForTansList(CompositeQuery compositeQuery){
		Long totalRecords = cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		pageConfig = Page.page(totalRecords, pageSize, currentPage);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		compositeQuery.setPageIndex(pageIndex);
		initMoneyInit(this.getUserId(),compositeQuery);
		return cashAccountService.queryMoneyAccountChangeLog(compositeQuery);
	}
	private void initMoneyInit(String userId,final CompositeQuery paramCQ){
		moneyInit=new HashMap<String,String>();
		CashAccount cashAccount=cashAccountService.queryOrCreateCashAccountByUserId(this.getUser().getId());
		moneyInit.put("mobileNumber", cashAccount.getMobileNumber());
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(this.getUserId());
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.setPayFrom(paramCQ.getPayFrom());
		moneyInit.put(Constant.MoneyAccountChangeType.PAY.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.setBonusRefundment(paramCQ.getBonusRefundment());
		moneyInit.put(Constant.MoneyAccountChangeType.REFUND.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
				
		compositeQuery.getMoneyDrawRelate().setUserNo(this.getUserId());
		compositeQuery.getMoneyDrawRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.DRAW);
		moneyInit.put(Constant.MoneyAccountChangeType.DRAW.name(), cashAccountService.queryMoneyDrawCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.RECHARGE);
		moneyInit.put(Constant.MoneyAccountChangeType.RECHARGE.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.ALL);
		moneyInit.put(Constant.MoneyAccountChangeType.ALL.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		moneyInit.put("changeType",paramCQ.getMoneyAccountChangeLogRelate().getMoneyAccountChangeType().getCode());
	}
	
	public CashAccountVO getMoneyAccount() {
		return moneyAccount;
	}

	public void setMoneyAccount(CashAccountVO moneyAccount) {
		this.moneyAccount = moneyAccount;
	}

	@SuppressWarnings("rawtypes")
	public Page getPageConfig() {
		return pageConfig;
	}

	@SuppressWarnings("rawtypes")
	public void setPageConfig(Page pageConfig) {
		this.pageConfig = pageConfig;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public List<CashAccountChangeLogVO> getTansList() {
		return tansList;
	}

	public Map<String, String> getMoneyInit() {
		return moneyInit;
	}


	public String getMobileFormat() {
		return mobileFormat;
	}

	public void setMobileFormat(String mobileFormat) {
		this.mobileFormat = mobileFormat;
	}

	public float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(float payAmount) {
		this.payAmount = payAmount;
	}

	public String getStore_set_type() {
		return store_set_type;
	}

	public void setStore_set_type(String store_set_type) {
		this.store_set_type = store_set_type;
	}

	public Long getBank() {
		return bank;
	}

	public void setBank(Long bank) {
		this.bank = bank;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public List<CashMoneyDraw> getMoneyDrawList() {
		return moneyDrawList;
	}
	public void setMoneyDrawList(List<CashMoneyDraw> moneyDrawList) {
		this.moneyDrawList = moneyDrawList;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
	public Long getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Long rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getRechargeUrl() {
		return rechargeUrl;
	}
	public void setRechargeUrl(String rechargeUrl) {
		this.rechargeUrl = rechargeUrl;
	}
	public String getCashAccountVerifyCode() {
		return cashAccountVerifyCode;
	}
	public void setCashAccountVerifyCode(String cashAccountVerifyCode) {
		this.cashAccountVerifyCode = cashAccountVerifyCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public List<CashBonusReturn> getBonusReturnList() {
		return bonusReturnList;
	}
	public void setBonusReturnList(List<CashBonusReturn> bonusReturnList) {
		this.bonusReturnList = bonusReturnList;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getTotalRefund() {
		return totalRefund;
	}
	public void setTotalRefund(Long totalRefund) {
		this.totalRefund = totalRefund;
	}
	public String getBonusTabType() {
		return bonusTabType;
	}
	public void setBonusTabType(String bonusTabType) {
		this.bonusTabType = bonusTabType;
	}
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	public OrdOrder getFirstOrder() {
		return firstOrder;
	}
	public String getFirstOrderCtMobile() {
		return firstOrderCtMobile;
	}
	public void setFirstOrderCtMobile(String firstOrderCtMobile) {
		this.firstOrderCtMobile = firstOrderCtMobile;
	}
	public String getMobileVerifycode() {
		return mobileVerifycode;
	}
	public void setMobileVerifycode(String mobileVerifycode) {
		this.mobileVerifycode = mobileVerifycode;
	}
	public String getEmailVerifycode() {
		return emailVerifycode;
	}
	public void setEmailVerifycode(String emailVerifycode) {
		this.emailVerifycode = emailVerifycode;
	}
	public boolean isRechargeAble() {
		return rechargeAble;
	}
	public void setRechargeAble(boolean rechargeAble) {
		this.rechargeAble = rechargeAble;
	}
	
}
