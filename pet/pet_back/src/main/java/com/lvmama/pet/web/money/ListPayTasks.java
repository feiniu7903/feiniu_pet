package com.lvmama.pet.web.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.A;
import org.zkoss.zul.Paging;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.view.PaginationVO;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_CASH_EVENT;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;
import com.lvmama.pet.web.BaseAction;

public class ListPayTasks extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComLogService petComLogService;

	private List<Map<String,Object>> playMoneyList;

	private CashAccountService cashAccountService;
	private TopicMessageProducer resourceMessageProducer;
	private PayPaymentRefundmentService payPaymentRefundmentService;
	private UserUserProxy userUserProxy = (UserUserProxy)SpringBeanProxy.getBean("userUserProxy");
	CompositeQuery compositeQuery = new CompositeQuery();
	private String userId;
	private String orderId;
	public void loadDataList(){
		//只显示等待打款的
		
		PaginationVO<CashMoneyDraw> paginationVO = new PaginationVO<CashMoneyDraw>();
		if(StringUtils.isNotBlank(userId)){
			paginationVO.addQueryParam("userNo",userId);
		}
		Paging paging=(Paging)this.getComponent().getFellow("_paging");
		paginationVO.setActivePage(paging.getActivePage());
		paginationVO.setPageSize(paging.getPageSize());
		//分页查询只调用一次，减少与RPC调用次数
		//Long rowCount = orderMoneyAccountService.queryFincMoneyDrawCount(compositeQuery);
		PaginationVO<CashMoneyDraw> paginationResult = cashAccountService
				.queryFincMoneyDraw(paginationVO);
		initialPageInfo(paginationResult.getTotalRows(), compositeQuery);
		playMoneyList = new ArrayList<Map<String, Object>>();
		if (paginationResult.getTotalRows() > 0) {
			for (CashMoneyDraw cashMoneyDraw : paginationResult.getResultList()) {
				Map<String,Object> obj=new HashMap<String, Object>();
				paginationResult.getResultList();
				CashAccount cashAccount = cashAccountService
						.queryCashAccountByPk(cashMoneyDraw.getCashAccountId());
				UserUser user = userUserProxy.getUserUserByPk(cashAccount
						.getUserId());
				obj.put("fincMoneyDraw", cashMoneyDraw);
				obj.put("user", user);
				obj.put("sid", cashAccount.getCashAccountId());
				playMoneyList.add(obj);
			}
		}
		
	}
	
	/**
	 * 打款
	 * @param a
	 */
	public void play(final A a){
		ZkMessage.showQuestion("您确定需要打款吗?", 
				new ZkMsgCallBack()	{
					public void execute() {
						Long moneyDrawId  = (Long)a.getAttribute("moneyDrawId");
						if(moneyDrawId==null||moneyDrawId<1){
							alert("参数不存在");
							return;
						}
						CashMoneyDraw cashMoneyDraw = cashAccountService.getFincMoneyDrawByPK(moneyDrawId);
						if(cashMoneyDraw == null){
							alert("提款记录不存在");
							return;
						}
						if(!Constant.FINC_CASH_STATUS.UnApplyPayCash.name().equalsIgnoreCase(cashMoneyDraw.getPayStatus())){
							alert("当前的提现操作状态 不可以打款");
							return;
						}
						cashAccountService.updateCashMoneyDrawPayStatusByPK(cashMoneyDraw,Constant.FINC_CASH_STATUS.ApplyPayCash.name(),getSessionUserName());
						CashAccount cashAccount = cashAccountService.queryCashAccountByPk(cashMoneyDraw.getCashAccountId());
						RefundmentToBankInfo refundInfo = new RefundmentToBankInfo();
						refundInfo.setRefundAmount(cashMoneyDraw.getDrawAmount());
						refundInfo.setBizType(Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name());
						refundInfo.setObjectId(cashMoneyDraw.getMoneyDrawId());
						refundInfo.setObjectType(Constant.PAYMENT_OBJECT_TYPE.CASH_MONEY_DRAW.name());
						refundInfo.setRefundType(Constant.REFUND_TYPE.CASH_ACCOUNT_WITHDRAW.name());
						refundInfo.setUserId(cashAccount.getUserId());
						refundInfo.setOperator(getSessionUserName());
						//支付宝批量付款到银行
						if(cashMoneyDraw.isFromAlipayToBank()){
							refundInfo.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY_BPTB.name());	
						}
						//支付宝批量付款到支付宝帐号
						else{
							refundInfo.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY_BATCH.name());
						}
						
						boolean success=payPaymentRefundmentService.createRefundment(null,refundInfo);
						if (success) {
							Message msg = MessageFactory.newPaymentRefundmentMessage(cashMoneyDraw.getMoneyDrawId());
							msg.setAddition(Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name());
							resourceMessageProducer.sendMsg(msg);
							alert("提交打款成功");
							refreshComponent("search");
						}else{
							alert("提交打款失败");
						}

						insertLog("CASH_MONEY_DRAW", null, moneyDrawId, ListPayTasks.this.getSessionUserName(),
								COM_LOG_CASH_EVENT.refundToBACK.name(), "提现出款",null);
					}
				}, new ZkMsgCallBack() {
					public void execute() {}
				}
			
		);
		
	}
	
	private void insertLog(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content) {
		
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);

		if (content != null)
			log.setContent(content);
		petComLogService.addComLog(log);
	}

	public List<Map<String, Object>> getPlayMoneyList() {
		return playMoneyList;
	}


	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPlayMoneyList(List<Map<String, Object>> playMoneyList) {
		this.playMoneyList = playMoneyList;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setPetComLogService(ComLogService petComLogService) {
		this.petComLogService = petComLogService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}
	
}
