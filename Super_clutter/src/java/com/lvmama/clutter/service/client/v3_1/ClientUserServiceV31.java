package com.lvmama.clutter.service.client.v3_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.service.impl.ClientUserServiceImpl;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.mobile.MobileHotel;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashBonusReturn;
import com.lvmama.comm.pet.po.money.CashPay;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.CashAccountChangeLogVO;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
/**
 * 3.1版本用户相关奖金账号相关接口
 * @author dengcheng
 *
 */
public class ClientUserServiceV31  extends ClientUserServiceImpl{
	private static final Log log = LogFactory.getLog(ClientUserServiceV31.class);
	
	private static String MOBILE_RESEND_MSG = "mobile_resend_msg";
	private static int MOBILE_RESEND_SECOND = 55;

	/**
	 * 短信发送服务器.
	 */
	private TopicMessageProducer orderMessageProducer;
	
	/**
	 * 用户奖金信息. 
	 */
	@Override
	public Map<String, Object> getBonusInfo(Map<String, Object> params) throws Exception {
		
		Long userId = Long.valueOf(params.get("userId").toString());
		String userNo = params.get("userNo").toString();
		// 个人奖金账号信息
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(userId);
		//奖金收入总条数
		Long totalCount= cashAccountService.getBonusReturnCount(userId);;
		//奖金支出总条数
		Long totalRecords=cashAccountService.queryMoneyAccountChangeLogCount(createBonusCompositeQuery(userNo,moneyAccount.getCashAccountId()));
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userNo);
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery.setBonusRefundment("Y");
		//退款的总数据
		Long totalRefund=cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		// 奖金余额
		if(null != moneyAccount) {
			resultMap.put("bonusBalanceYuan", null==moneyAccount.getBonusBalance()?0l:PriceUtil.convertToYuan(moneyAccount.getBonusBalance()));
		} else {
			resultMap.put("bonusBalanceYuan", 0l);
		}
		resultMap.put("incomeCount", null==totalCount?0f:totalCount); // 收入条数
		resultMap.put("paymentCount", null==totalRecords?0f:totalRecords); // 支出条数
		resultMap.put("refundCount", null==totalRefund?0f:totalRefund); // 退款 条数
		resultMap.put("helpUrl", "/app/help_bonus.html"); // 玩转奖金url
		
		return resultMap;
	}
	
	
	/**
	 * 奖金 - 收入
	 */
	@Override
	public Map<String, Object> getBonusIncome(Map<String, Object> params) throws Exception {
		Long userId = Long.valueOf(params.get("userId").toString());
		//收入的总数据
		Long totalCount=cashAccountService.getBonusReturnCount(userId);
		
		// 奖金收入列表 
		Page pageConfig =  initPage(params,10,1);
		pageConfig.setTotalResultSize(totalCount);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		List<CashBonusReturn>  bonusReturnList = cashAccountService.queryBonusReturn(userId,pageIndex.getBeginIndex(),pageIndex.getEndIndex());
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(bonusReturnList!=null && bonusReturnList.size()>0){
			for(CashBonusReturn item:bonusReturnList){
				Map<String,Object> m  = new HashMap<String,Object>();
				m.put("createDate", DateUtil.formatDate(item.getCreateDate(), "yyyy年MM月dd日")); // 日期
				m.put("bonusYuan", item.getBonusYuan()); // 金额
				m.put("orderId", null==item.getBusinessId()?"":item.getBusinessId());// 订单号
				m.put("productName","");// 产品名称
				m.put("comeFrom",getZhComeFrom(item.getComeFrom()));// 奖金类型 
				
				if(Constant.CASH_ACTION.ORDER_AND_COMMENT.name().equalsIgnoreCase(item.getComeFrom()) 
						&& StringUtils.isNotEmpty(item.getBusinessId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getBusinessId()));
					if(order!=null){
						m.put("productName",order.getMainProduct().getProductName());// 产品名称
					}
				// 艺龙酒店 返现 
				} else if(Constant.BonusOperation.ELONG_ORDER_REFUND.name().equalsIgnoreCase(item.getComeFrom()) 
						&& StringUtils.isNotEmpty(item.getBusinessId())){
					MobileHotel mh = mobileHotelService.selectMobileHotelByOrderId(item.getBusinessId());
					if(null != mh) {
						m.put("productName",mh.getName());// 产品名称
					}
				}else{
					m.put("orderId","");// 产品名称
					m.put("productName","");// 产品名称
				}
				listMap.add(m);
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", listMap);
		resultMap.put("isLastPage", !pageConfig.hasNext());
		return resultMap;
	}

	/**
	 * 奖金 - 支出
	 */
	@Override
	public Map<String, Object> getBonusPayment(Map<String, Object> params)
			throws Exception {
		Long userId = Long.valueOf(params.get("userId").toString());
		String userNo = params.get("userNo").toString();
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(userId);//5ad32f1a1f4b4345011f4b8284fb02bf
		//奖金支出总条数
		CompositeQuery compositeQuery = createBonusCompositeQuery(userNo,moneyAccount.getCashAccountId());
		Long totalRecords = cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		
		Page pageConfig =  initPage(params,10,1);
		pageConfig.setTotalResultSize(totalRecords);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		compositeQuery.setPageIndex(pageIndex);
		List<CashAccountChangeLogVO> tansList = cashAccountService.queryMoneyAccountChangeLog(compositeQuery);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(tansList!=null && tansList.size()>0){
			for(CashAccountChangeLogVO item:tansList){
				Map<String,Object> m  = new HashMap<String,Object>();
				m.put("createDate", DateUtil.formatDate(item.getCreateTime(), "yyyy年MM月dd日")); // 日期
				m.put("bonusYuan",StringUtils.isEmpty(item.getExpenditure())?0f:Float.parseFloat(item.getExpenditure())); // 金额
				m.put("orderId", null==item.getOrderId()?"":item.getOrderId());// 订单号
				m.put("productName","");// 产品名称
				if(StringUtils.isNotEmpty(item.getOrderId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getOrderId()));
					if(order!=null){
						m.put("productName", order.getMainProduct().getProductName());
					}
				}  
				listMap.add(m);
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", listMap);
		resultMap.put("isLastPage", !pageConfig.hasNext());
		return resultMap;
	}

	/**
	 * 奖金 - 退款
	 */
	@Override
	public Map<String, Object> getBonusRefund(Map<String, Object> params) throws Exception {
		Long userId = Long.valueOf(params.get("userId").toString());
		String userNo = params.get("userNo").toString();
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(userId);

		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userNo);
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(moneyAccount.getCashAccountId());
		compositeQuery.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery.setBonusRefundment("Y");
		
		List<CashAccountChangeLogVO> tansList = queryForTansList(compositeQuery,params,userNo,userId);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(tansList!=null && tansList.size()>0){
			for(CashAccountChangeLogVO item:tansList){
				Map<String,Object> m  = new HashMap<String,Object>();
				m.put("createDate", DateUtil.formatDate(item.getCreateTime(), "yyyy年MM月dd日")); // 日期
				m.put("bonusYuan", StringUtils.isEmpty(item.getIncome())?0f:Float.parseFloat(item.getIncome())); // 金额
				m.put("orderId", null==item.getOrderId()?"":item.getOrderId());// 订单号
				m.put("productName","");// 产品名称
				
				if(StringUtils.isNotEmpty(item.getOrderId())){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(Long.parseLong(item.getOrderId()));
					if(order!=null){
						m.put("productName", order.getMainProduct().getProductName());
					}
				}
				
				listMap.add(m);
			}
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", listMap);
		resultMap.put("isLastPage", null==params.get("isLastPage")?true:params.get("isLastPage"));
		return resultMap;
	}
	
	/**
	 * 提取公用部分
	 * @return
	 */
	private CompositeQuery createBonusCompositeQuery(String userNo,Long accountId) {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserId(userNo);
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(accountId);
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userNo);
		compositeQuery.setPayFrom(CashPay.PayFrom.BONUS.toString());
		compositeQuery.setBonusRefundment("Y");
		return compositeQuery;
	}
	

	private List<CashAccountChangeLogVO> queryForTansList(CompositeQuery compositeQuery,Map<String,Object> params,String userNo,Long uId){
		Long totalRecords = cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery);
		Page pageConfig =  initPage(params,10,1);
		pageConfig.setTotalResultSize(totalRecords);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		compositeQuery.setPageIndex(pageIndex);
		// initMoneyInit(userNo,uId,compositeQuery);
		params.put("isLastPage",  !pageConfig.hasNext());
		return cashAccountService.queryMoneyAccountChangeLog(compositeQuery);
	}
	
	/**
	 * 奖金来源-中文
	 * @param comeFrom
	 * @return
	 */
	public String getZhComeFrom(String comeFrom){
		if(!StringUtils.isEmpty(comeFrom)) {
			return Constant.BonusOperation.getCnName(comeFrom);
		}else{
			return "其它";
		}
	}
	
	private void initMoneyInit(String userId,Long uId,final CompositeQuery paramCQ){
		Map<String,String> moneyInit=new HashMap<String,String>();
		CashAccount cashAccount=cashAccountService.queryOrCreateCashAccountByUserId(uId);
		moneyInit.put("mobileNumber", cashAccount.getMobileNumber());
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userId);
		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.PAY);
		compositeQuery.setPayFrom(paramCQ.getPayFrom());
		moneyInit.put(Constant.MoneyAccountChangeType.PAY.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.REFUND);
		compositeQuery.setBonusRefundment(paramCQ.getBonusRefundment());
		moneyInit.put(Constant.MoneyAccountChangeType.REFUND.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
				
		compositeQuery.getMoneyDrawRelate().setUserNo(userId);
		compositeQuery.getMoneyDrawRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.DRAW);
		moneyInit.put(Constant.MoneyAccountChangeType.DRAW.name(), cashAccountService.queryMoneyDrawCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.RECHARGE);
		moneyInit.put(Constant.MoneyAccountChangeType.RECHARGE.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.ALL);
		moneyInit.put(Constant.MoneyAccountChangeType.ALL.name(), cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery).toString());
		moneyInit.put("changeType",paramCQ.getMoneyAccountChangeLogRelate().getMoneyAccountChangeType().getCode());
	}

	/**
	 * v3.1 重发短信凭证 
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> reSendSmsCert(Map<String, Object> params) throws Exception {
		ArgCheckUtils.validataRequiredArgs("orderId","userNo","mobileNumber", params); // "userId",
		// 每个用户一分钟内只能发送一次 短信凭证 
		Object object = MemcachedUtil.getInstance().get(MOBILE_RESEND_MSG+params.get("userNo"));
		if(null != object) {
			long c_long = com.lvmama.clutter.utils.DateUtil.getCurrentTimeLong(); // 当前日期秒
			long h_long = Long.parseLong(object.toString()); // 历史日期 秒
			if(c_long - h_long < MOBILE_RESEND_SECOND) {
				throw new LogicException(ClutterConstant.MOBILE_SEND_MSG_TIME);
			}
		}
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(params.get("orderId").toString()));
		// 订单不存在 
		if(null == order) {
			throw new LogicException("订单不存在");
		}
		// 订单必须是本人的
		if(!order.getUserId().equals(params.get("userNo").toString())) {
			throw new LogicException("订单不存在");
		}
		
		// 如果订单可重发凭证
		if (order.isShouldSendCert()) {
			String orderHeadId= order.getOrderId().toString();
			if(!(StringUtil.isEmptyString(orderHeadId))){
				// 发送完成后 用户信息保存在memcache中
				MemcachedUtil.getInstance().set(MOBILE_RESEND_MSG+params.get("userNo"), MOBILE_RESEND_SECOND, com.lvmama.clutter.utils.DateUtil.getCurrentTimeLong());
				orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(Long.valueOf(orderHeadId),params.get("mobileNumber").toString()));
			} else {
				throw new LogicException("无效的订单号");
			}
		} else {
			throw new LogicException("该订单不支持可重发凭证");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", "success");
		return map;
	}

	/**
	 * 奖金支付 
	 */
	@Override
	public Map<String, Object> bonusPay(Map<String, Object> params)throws Exception {
		ArgCheckUtils.validataRequiredArgs("orderId", params);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		boolean isFinised = false;
		resultMap.put("payFinished", isFinised);
		JSONObject jo2 = null;
		boolean b = true;
		try {
			//失败 {"error_response":{"code":"ORDER_CANT_NOT_BE_FOUND","msg":"无法找到订单"}}
			//成功{"success_response":{"bonusBalance":"1000","bonusPaidAmount":"0","bonus":"960","actBonus":"0"}}
			Long orderId = Long.valueOf(params.get("orderId").toString());
			String jsons = HttpsUtil.requestGet(ClutterConstant.getSuperFrontUrl()+"/bonusAppPay/doPay.do?orderId="+orderId); 
			JSONObject jo = JSONUtil.getObject(jsons);
			// 因为支付成功和失败返回的属性不一样。 
			Object obj = jo.get("success_response"); // 如果成功。 
			if(null != obj) {
				jo2 = JSONUtil.getObject(obj.toString());
				//订单应付金额(以分为单位)
				if(null != jo2.get("order_ought_pay_amount") ) {
					resultMap.put("orderOughtPayAmount", PriceUtil.convertToYuan(Long.valueOf(jo2.get("order_ought_pay_amount").toString())));
				}else {
					resultMap.put("orderOughtPayAmount", 0f);
				}
			} else {
				Object errorObj = jo.get("error_response"); // 如果失败 ,
				if(null != errorObj) {
					b = false;
					jo2 = JSONUtil.getObject(errorObj.toString());
					if("BONUS_AVAILABLE_IS_ZERO".equals(jo2.get("code"))) { // 已经支付过了。 
						b = true;
						resultMap.put("orderOughtPayAmount", 0f);
					}
				}
			}
			
			try {
				Thread.currentThread().sleep(1000l);//暂停1000毫秒 
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			resultMap.put("payFinished", this.isPayBonusFinished(orderId));
		}catch(Exception e){
			e.printStackTrace();
			if(jo2 == null) {
				jo2 = JSONUtil.getObject("{\"code\":\"ORDER_CANT_NOT_BE_FOUND\",\"msg\":\"奖金支付失败\"}");
			}
		}
		// 如果支付失败 
		if(!b) {
			throw new LogicException(null == jo2.get("msg")?"奖金支付失败":jo2.get("msg").toString());
		}
		return resultMap;
	}
	
	/**
	 * 是否使用奖金支付完成 
	 * @param orderId
	 * @return
	 */
	public boolean isPayBonusFinished(Long orderId) {
		boolean b = false; 
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(null != ordOrder) {
				// 订单金额 减去 奖金支付金额 bonusPaidAmount
				float oughtPay = ordOrder.getOughtPayYuan() - ordOrder.getBonusPaidAmountYuan();
				log.info("....oughtPay="+ ordOrder.getOughtPayYuan()  + "....bonusPay"+ordOrder.getBonusPaidAmountYuan());
				if(oughtPay <= 0) {
					b = true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return b;
		
	}
	/**
	 * 优惠券和账号绑定. 
	 * @param userNo     用户账号 
	 * @param couponCode 优惠券号
	 * @return markCouponRelateUserId 优惠券和账号中间表的id 
	 */
	@Override
	public Map<String, Object> bindingCouponToUser(Map<String, Object> params)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		ArgCheckUtils.validataRequiredArgs("userNo","couponCode", params);
		try {
			// 1,用户账号 和 优惠券编号 
			String userNo = String.valueOf(params.get("userNo"));
			String couponCode = String.valueOf(params.get("couponCode"));
			if("null".equals(userNo) || "null".equals(couponCode)) {
				throw new LogicException("无效的账号或优惠券");
			}
			// 2,用户是否存在
			UserUser user = userUserProxy.getUserUserByUserNo(userNo);
			if(null == user) {
				throw new LogicException("用户不存在");
			}
			
			// 3,优惠券和账号绑定 
			Long id = markCouponService.bindingUserAndCouponCode(user, couponCode);
			if(null == id) {
				throw new LogicException("无效的优惠券");
			}
			map.put("markCouponRelateUserId", id);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new LogicException("优惠券绑定失败");
		}
		return map;
	}

	
	/**
	 * 
	 * 获取待点评的订单 . 
	 */
	@Override
	public Map<String,Object> queryCmtWaitForOrder(Map<String, Object> param) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("count", 0);
		resultMap.put("createTime", "");
		List<MobileOrderCmt> cmtList =  super.queryCommentWaitForOrder(param);
		if(null != cmtList && cmtList.size() > 0) {
			MobileOrderCmt cmt = cmtList.get(0);
			// 查询某个时间段的日期 
			if(null != param.get("createTime")) {
				String createTime = param.get("createTime").toString();
				// 如果最新记录 等于  客户端传递过来的时间createTime
				if(!cmt.getCreateTime().equals(createTime)) {
					resultMap.put("count", cmtList.size());
					resultMap.put("createTime",cmtList.get(0).getCreateTime());
				} else {
					resultMap.put("count", 0);
					resultMap.put("createTime", createTime);
				}
				
			} else {
				if(!StringUtils.isEmpty(cmt.getCreateTime())) {
					resultMap.put("createTime",cmt.getCreateTime());
				}
				resultMap.put("count", cmtList.size());
			}
		}
		
		return  resultMap;
	}
	
	
	/**
	 * 提交点评 . 
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> commitOrderComment(Map<String, Object> param) throws Exception{
		Map<String,Object> m = submitCommitComment(param);
		if(null == m || null == m.get("commentId") || Long.valueOf(m.get("commentId").toString()) < 1) {
			throw new LogicException(" 点评提交失败，请重试！ !");
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("cashRefund", false);
		resultMap.put("info", "点评提交成功！");
		// 支持返现 
		if(null != m && null != m.get("cashRefund") && "Y".equals(m.get("cashRefund").toString())) {
			resultMap.put("cashRefund", true);
			resultMap.put("info", "提交成功，审核通过后即可获得奖金啦！");
		}
		return resultMap;
	}
	
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

}
