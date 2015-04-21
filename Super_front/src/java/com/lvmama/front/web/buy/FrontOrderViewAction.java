package com.lvmama.front.web.buy;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.vo.PayMergePayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.OnLinePreSalePowerUtil;
import com.lvmama.comm.utils.PaymentUrl;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORDER_VIEW_STATUS;
import com.lvmama.comm.vo.Constant.STORED_CARD_ENUM;
import com.lvmama.comm.vo.PaymentConstant;




/**
 * 前台(网站)订单展示的实现类。
 * 
 * 用户下单完成后，根据订单的不同状态应展示出不同的页面。此类完成了统一的门面入口。 订单不同状态所显示的不同页面包括： <li>1.资源是否需要确认</li>
 * <li>&nbsp;&nbsp;&nbsp;&nbsp;1.1 资源需要确认</li> <li>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.1
 * 资源未确认，则返回资源确认页面</li> <li>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.1.2 资源已确认，则跳到2</li>
 * <li>&nbsp;&nbsp;&nbsp;&nbsp;1.2 资源无需确认，则跳到2</li> <li>2.是否已支付</li> <li>
 * &nbsp;&nbsp;&nbsp;&nbsp;2.1 未支付</li> <li>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1.1
 * 需要电子签约，则返回资源电子签约页面</li> <li>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.1.2
 * 不需要电子签约，则返回在线支付页面</li> <li>&nbsp;&nbsp;&nbsp;&nbsp;2.2 已支付，则返回完成页面</li>
 * 
 * @author Ganyinwen
 * 
 */

@Results({
		@Result(name = "update", location = "/WEB-INF/pages/buy/orderView.ftl", type = "freemarker"),
		@Result(name = "orderView", location = "/WEB-INF/pages/buy/orderView.ftl", type = "freemarker"),
		@Result(name=com.opensymphony.xwork2.Action.ERROR,params={"status", "404"},type="dispatcher"),
		@Result(name = "orderMergeView", location = "/WEB-INF/pages/buy/orderMergeView.ftl", type = "freemarker"),
		@Result(name = "toOrderView", location = "/view/view.do?orderId=${orderId}", type = "redirect"),
		@Result(name = "toUserOrder", location = "/myspace/order_detail.do?orderId=${orderId}", type = "redirect"),
		@Result(name = "jingmozhuce", location = "/WEB-INF/pages/buy/jingmozhuce.ftl", type = "freemarker"),
		@Result(name = "loginUpdateOrder", location = "/udpate/order.do?orderId=${orderId}", type = "redirect"),
		@Result(name = "toSigningView", location = "/WEB-INF/pages/buy/201107/contEcontract.ftl", type = "freemarker"),
		@Result(name = "preSalePowerView", location = "/WEB-INF/pages/buy/201107/preSalePower.ftl", type="freemarker"),
		@Result(name = "payMentComplent", location = "/WEB-INF/pages/buy/201107/complete.ftl", type = "freemarker"),
		@Result(name = "partPayMentComplent", location = "/WEB-INF/pages/buy/201107/partPayComplete.ftl", type = "freemarker"),
		@Result(name = "mergePayComplete", location = "/WEB-INF/pages/buy/201107/mergePayComplete.ftl", type = "freemarker"),
		@Result(name = "toOrderCheckView", location = "/WEB-INF/pages/buy/201107/orderCheck.ftl", type = "freemarker") ,
		@Result(name = "sucConfirm", location = "/WEB-INF/pages/buy/201107/econtractConfirm/sucConfirm.ftl", type = "freemarker"),
		@Result(name = "bookFail", location = "/WEB-INF/pages/buy/bookFail.ftl", type = "freemarker"),
		@Result(name = "failConfirm", location = "/WEB-INF/pages/buy/201107/econtractConfirm/failConfirm.ftl", type = "freemarker")})
public class FrontOrderViewAction extends OrderViewAction {
	private static final long serialVersionUID = 8508096763195791133L;
	private static final Log LOG = LogFactory
			.getLog(FrontOrderViewAction.class);
		
	//yangbin
	//支付宝快登操作
	private static final String ALIPAY_TOKEN_KEY="alipay_token";
	//盛付通联合登陆Token
	private static final String SNDA_TOKEN_KEY="access_token";
	/**
	 * 预授权启动标志
	 */
	private final static String DEFAULT_PREPAY = "Y";
	private boolean hasAlipayLogin=false;
	private boolean isSndaLogin = false;
	private ProdProductService prodProductService;
	
	private boolean isNeedOrderInfo = true;
	//是否签约
	private String isSigning = "N";
	
	//是否同意在线预售权 (默认为同意)
	private boolean preSalePowered = Boolean.TRUE;
	//联系人邮箱主站
	private String catantMailHost;
	//游玩人的必填信息
	private String travellerInfoOptions;
	
	/*电子合同回执*/
	private String econtractId;
	
	private String signature;
	
	private Long payAmountFen;
	
	/**
	 * 订单支付 等待时间.
	 */
	private String waitPayment;
	/**
	 * 审核通过时间.
	 */
	private String approveTime;
    /**
     * 游玩时间
     */
	private String visitTime;
	
	private String bizType=Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name();
	/**
	 * 对象类型(订单).
	 */
	private String objectType = "ORD_ORDER";
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
	
	/**
	 * 储值卡支付SERVICE.
	 */
	private StoredCardService storedCardService;
	/**
	 * 储值卡卡号.
	 */
	private String cardNo;
	/**
	 * 分期支付页面初始化数据Map.
	 */
	private Map<String,Long> instalmentInfoMap;
	private transient TopicMessageProducer orderMessageProducer;	

	/**
	 * 电子合同服务接口
	 */
	private OrdEContractService ordEContractService;
	
	/**
	 * 订单固化行程接口
	 */
	private TravelDescriptionService travelDescriptionService;
	
	/**
	 * 电子合同内容
	 */
	private String contractContent;
	
	/**
	 * 订单中固化的行程
	 */
	private String orderTravel;

	private Date currentTime = new Date();

	private String paymentParams;
	
	/**
	 * 现金账户专用
	 * 
	 */
	private String paymentParamsCashAccount;
	
	/**
	 * 合并支付时多订单号(逗号","隔开)
	 */
	private String orderIds;

	/**
	 * 储值卡支付记录.
	 */
	private List usageList=new ArrayList();
	
	/**
	 * 合并支付应该总金额
	 */
	private float oughtPayTotalAmount=0;
	
	/**
	 * 合并支付的订单号
	 */
	private String[] orderIdArray;
	/**
	 * 合并支付封装数据
	 */
	private String mergePayData;
	
	/**
	 * 分润账号集
	 */
	private String royaltyParameters;
	
	/**
	 * 首笔订单联系人手机号码
	 */
	private String firstOrderCtMobile;
	/**
	 * 首笔订单
	 */
	private OrdOrder firstOrder;
	/**
	 * 是否需要邮箱验证
	 */
	private boolean needsEmailCheck=false;
	
	/**
	 * 签约时,勾选的同意/不同意事项
	 */
	private String agree3; //本人同意转至组团社指定旅行社出团；
	private String agree4;//本人同意延期出团；
	private String agree5;//本人同意改签其他线路出团。
	private String agree6;//本人同意采用拼团方式出团。 
	
	/**
	 * 临时关闭存款账户支付
	 */
	private Boolean tempCloseCashAccountPay=false;
	
	private LvmamacardService lvmamacardService;
	
	private String isBoundLipinka;	//是否绑定过礼品卡
	private String boundLipinkaUsable;	//绑定过的礼品卡是否可用
	private String hadConsumedLipinka; //已消费过得礼品卡
	private String bindCardNo;	//绑定的礼品卡
	private String bindCardPassword;	//绑定的礼品卡

	public String getBoundLipinkaUsable() {
		return boundLipinkaUsable;
	}

	public void setBoundLipinkaUsable(String boundLipinkaUsable) {
		this.boundLipinkaUsable = boundLipinkaUsable;
	}

	public String getBindCardNo() {
		return bindCardNo;
	}

	public void setBindCardNo(String bindCardNo) {
		this.bindCardNo = bindCardNo;
	}

	public String getBindCardPassword() {
		return bindCardPassword;
	}

	public void setBindCardPassword(String bindCardPassword) {
		this.bindCardPassword = bindCardPassword;
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}

	public String getIsBoundLipinka() {
		return isBoundLipinka;
	}

	public void setIsBoundLipinka(String isBoundLipinka) {
		this.isBoundLipinka = isBoundLipinka;
	}

	public String getHadConsumedLipinka() {
		return hadConsumedLipinka;
	}

	public void setHadConsumedLipinka(String hadConsumedLipinka) {
		this.hadConsumedLipinka = hadConsumedLipinka;
	}
	
	/**
	 * 返回页面 功能：根据订单属性的不同状态，转到“核对订单”、“产品签约”、“订单支付”或“用户订单”页面。
	 * 
	 * @return
	 */
	@Action("/view/view")
	@Override
	public String execute() throws Exception {
		if (this.isAccessAllowed()) {
			orderInfo();
		} else {
			return ERROR;
		}
		order=orderServiceProxy.queryOrdOrderByOrderId(orderId);

		
		//由于驴妈妈账户被盗严重  对于广州长隆供应商的产品临时关闭存款账户支付功能(485=广州长隆供应商ID)
		List<OrdOrderItemMeta> ordOrderItemMetaList=order.getAllOrdOrderItemMetas();
		if(ordOrderItemMetaList!=null && ordOrderItemMetaList.size()>0){
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
				if(ordOrderItemMeta.getSupplierId()!=null && 
						(485==ordOrderItemMeta.getSupplierId().longValue()
						||2261==ordOrderItemMeta.getSupplierId().longValue()
						||4462==ordOrderItemMeta.getSupplierId().longValue()
						||4367==ordOrderItemMeta.getSupplierId().longValue()
						||1340==ordOrderItemMeta.getSupplierId().longValue()
						||6134==ordOrderItemMeta.getSupplierId().longValue()
						)){
					tempCloseCashAccountPay=true;
				}
			}
		}
		
		
		if(order!=null && order.getIsShHolidayOrder()){
			if(Constant.ORDER_STATUS.CANCEL.getCode().equalsIgnoreCase(order.getOrderStatus())){
				return "bookFail";
			}
		}
		
		//首先判断是否是20130718后预授权功能的订单
		//是则按销售产品中定义的预授权支付标志运行
		if(DEFAULT_PREPAY.equals(order.getPrePaymentAble())){
			preSalePowered = true;
			return goVier();
		//如果是老订单，则按原来的逻辑走
		}else{
			preSalePowered = false;
			return goVier();
		} 
 	}
	
	private String goVier(){
		String processStatus = OnLinePreSalePowerUtil.orderProcessStatus(order,preSalePowered,"Y".equals(isSigning));
		if(ORDER_VIEW_STATUS.UNSIGNED.name().equals(processStatus)){
			if(order.isNeedEContract()){
				ProdEContract prodEContract = prodProductService.getProdEContractByProductId(order.getMainProduct().getProductId());
				getRequest().setAttribute("contractTemplate", prodEContract.getEContractTemplate());
			}
			return "toSigningView";
		}else if(ORDER_VIEW_STATUS.SIGNED.name().equals(processStatus)){
			 return signContract();
		}else if(ORDER_VIEW_STATUS.PREPAYED.name().equals(processStatus)){
			this.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PRE_PAY.name());
			validatePayment();
			if(order.hasNeedPrePay()){
				this.setWaitPayment(String.valueOf(DateUtil.getMinBetween(order.getCreateTime(), order.getAheadTime())));
			}
			return "preSalePowerView";
		}else if(ORDER_VIEW_STATUS.UNVERIFIED.name().equals(processStatus)){
			return "toOrderCheckView";
		}else if(ORDER_VIEW_STATUS.UNPAY.name().equals(processStatus) || ORDER_VIEW_STATUS.PARTPAY.name().equals(processStatus)){
			this.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			validatePayment();
			this.setApproveTime(DateUtil.getDateTime("yyyyMMddHHmmss",order.getApproveTime()));
			if(!order.IsAperiodic()) {
				this.setVisitTime(DateUtil.getDateTime("yyyyMMddHHmmss",order.getVisitTime()));
			}
			this.setWaitPayment(order.getWaitPayment().toString());
			PaymentUrl pu=new PaymentUrl(order.getOrderId(),Constant.OBJECT_TYPE.ORD_ORDER.name(),
					Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name(),order.getOughtPayFenLong(),Constant.PAYMENT_TYPE.ORDER.name(),0L,order.getUserId());
			paymentParams=pu.getPaymentUrl("");
			paymentParamsCashAccount=pu.getPaymentUrlWithBonus("");
			//
			if(this.getUser()!=null) {
				//判断是否绑定过礼品卡
				Map param = new HashMap<String, Object>();
				param.put("userId", this.getUser().getId());
				param.put("FORREPORT", false);
				List<LvmamaStoredCard> lvmamaStoredCardList = lvmamacardService.queryByParamForLvmamaStoredCard(param);
				if(lvmamaStoredCardList!=null && lvmamaStoredCardList.size()>0) {
					isBoundLipinka = "1";
					LvmamaStoredCard lvmamaStoredCardMaxBalance = null;
					Long maxBalance = 0L;
					for(LvmamaStoredCard lvmamaStoredCard:lvmamaStoredCardList) {
						if(Constant.CARD_STATUS.USED.getCode().equals(lvmamaStoredCard.getStatus()) || Constant.CARD_STATUS.NOTUSED.getCode().equals(lvmamaStoredCard.getStatus())) {	//已使用和未使用的卡可以使用
							if(lvmamaStoredCard.getBalance()!=null && lvmamaStoredCard.getBalance()>maxBalance) {	//取金额最大的卡
								maxBalance = lvmamaStoredCard.getBalance();
								lvmamaStoredCardMaxBalance = lvmamaStoredCard;
							}
						}
					}
					if(lvmamaStoredCardMaxBalance==null || lvmamaStoredCardMaxBalance.getBalance()==0L) {
						boundLipinkaUsable = "0";
					}else {
						boundLipinkaUsable = "1";
						bindCardNo = lvmamaStoredCardMaxBalance.getCardNo();
						bindCardPassword = lvmamaStoredCardMaxBalance.getPassword();	
					}
				}else {
					boundLipinkaUsable = "0";
					isBoundLipinka = "0";
				}
				//获得消费过的礼品卡
				Map param2 = new HashMap<String, Object>();
				param2.put("userId", ""+this.getUser().getId());
				List<LvmamaStoredCard> usedLvmamaStoredCardList = lvmamacardService.queryUsedLvmamaStoredCardByUserId(param2);
				hadConsumedLipinka = "";
				if(usedLvmamaStoredCardList!=null && usedLvmamaStoredCardList.size()>0) {
					for(LvmamaStoredCard usedLvmamaStoredCard:usedLvmamaStoredCardList) {
						hadConsumedLipinka += usedLvmamaStoredCard.getCardNo()+",";
					}
				}	
			}
			return "orderView";
		}else if(ORDER_VIEW_STATUS.FINISHED.name().equals(processStatus)){
			return toSuccess();
		}else{
			return ERROR;
		}
	}
	/**
	 * 合并支付
	 * @author ZHANG Nan
	 * @return 合并支付页面
	 */
	@Action("/view/viewMergePay")
	public String viewMergePay(){
		if(StringUtils.isNotBlank(orderIds)){
			log.info("merge pay,orderIds="+orderIds);
			List<PayMergePayment> payMergePaymentList=new ArrayList<PayMergePayment>();
			
			orderIdArray=orderIds.split(",");
			for (String tempOrderId : orderIdArray) {
				Long orderId=null;
				try {
					orderId=Long.valueOf(tempOrderId);
				} catch (Exception e) {
					e.printStackTrace();
					return ERROR;
				}
				if(!isAccessAllowed(orderId)){
					return ERROR;
				}
				PayMergePayment payMergePayment=new PayMergePayment();
				payMergePayment.setObjectId(orderId);
				payMergePayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
				payMergePayment.setObjectType(objectType);
				payMergePayment.setBizType(bizType);
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
				if(order==null){
					return ERROR;
				}
				payMergePayment.setAmount(order.getOughtPayFenLong());
				payMergePayment.setApproveTime(DateUtil.getDateTime("yyyyMMddHHmmss",order.getApproveTime()));
				if(!order.IsAperiodic()) {
					payMergePayment.setVisitTime(DateUtil.getDateTime("yyyyMMddHHmmss",order.getVisitTime()));
				}
				payMergePayment.setWaitPayment(order.getWaitPayment().toString());
				payMergePaymentList.add(payMergePayment);
				oughtPayTotalAmount+=order.getOughtPayYuanFloat();
			}
			mergePayData=JSONArray.fromObject(payMergePaymentList).toString();
			bizType=Constant.PAYMENT_BIZ_TYPE.MERGE_PAY.name();
			
			log.info("source: " + mergePayData+PaymentConstant.SIG_PRIVATE_KEY);
			signature = MD5.md5(mergePayData+PaymentConstant.SIG_PRIVATE_KEY);
			log.info("md5: " + signature);
			
			//判断是否有使用支付宝账户登录
			findAlipayToken();
			log.info("Began to merge pay,mergePayData="+mergePayData+",oughtPayTotalAmount="+oughtPayTotalAmount);
			return "orderMergeView";
		}
		return ERROR;
 	}
	private String signContract(){
		saveContactEmail();
		/**
		 * 合同签约
		 */
		signContract(order.getOrderId());
		try{
			return execute();
		}catch(Exception e){
			LOG.warn("签约后返回 exceute 出错 \r\n"+e);
			return ERROR;	
		}
	}
	private void signContract(final Long orderId){
		OrdEcontractSignLog signLog = new OrdEcontractSignLog();
		signLog.setSignMode(Constant.ECONTRACT_SIGN_TYPE.ONLINE_SIGN.getCode());
		signLog.setSignDate(new java.util.Date());
		signLog.setSignUser(getUser().getUserId());
		boolean isSign = orderServiceProxy.updateOrdEContractStatusToConfirmed(orderId);
		if(!isSign){
			LOG.info("前台用户签约，订单"+orderId+" 在订单表中没能修改签约状态");
		}
		isSign = ordEContractService.signContract(orderId, signLog);
		if(!isSign){
			LOG.info("电子合同签约失败 订单号"+orderId);
		}else{
			//通过消息把用户前台勾选的待同意选项,更新至合同中
			orderMessageProducer.sendMsg(MessageFactory.newOrderContactUpdateAgreeItem(order.getOrderId(),"true".equals(agree3),  "true".equals(agree4),  "true".equals(agree5),  true));
		}
	}
	private void validatePayment(){

		instalmentInfoMap = new HashMap<String,Long>();

		/**
		 * 针对支付宝的token检测
		 * @author yangbin
		 */
			if(!order.isPaymentChannelLimit())//非优先通道时才判断是否有token的存在
			{
				findAlipayToken();
			    findSndaToken();
			}
			if(order.isCanInstalment()){//如果该订单时可以分期支付的，初始化分期支付页面数据，默认选择中国银行
				final int INSTALMENT_NUMBER = 3;//期数
				final float FEE_RATE = 0.025F;//手续费率
				long oughtPay = (long)order.getOughtPayYuan();
				long instalmentFee = (long)(oughtPay * FEE_RATE);
				long averagePayment = (long)(oughtPay / INSTALMENT_NUMBER);
				long downPayment = oughtPay - averagePayment * (INSTALMENT_NUMBER -1) + instalmentFee;
				long totalPayment = oughtPay + instalmentFee;
				instalmentInfoMap.put("totalPayment", totalPayment);//总计
				instalmentInfoMap.put("instalmentFee", instalmentFee);//手续费
				instalmentInfoMap.put("averagePayment", averagePayment);//首付之后的单笔支付
				instalmentInfoMap.put("downPayment", downPayment);//首付
			}
			String royaltyParameters=this.getRoyaltyParameters();
			if(StringUtils.isBlank(royaltyParameters)){
				royaltyParameters="";
			}
			String dataStr = String.valueOf(order.getOrderId())+objectType+String.valueOf(order.getOughtPayFenLong())+paymentType+bizType+royaltyParameters+PaymentConstant.SIG_PRIVATE_KEY;
			log.info("source: " + dataStr);
			signature = MD5.md5(dataStr);
			log.info("md5: " + signature);
	}
	/**
	 * 当订单可以分笔支付时 重新获取用户支付的金额并生成签名
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	@Action("/view/reGenerateSignature")
	public void reGenerateSignature() throws IOException{
		String royaltyParameters=this.getRoyaltyParameters();
		if(StringUtils.isBlank(royaltyParameters)){
			royaltyParameters="";
		}
		String dataStr = String.valueOf(orderId)+objectType+String.valueOf(payAmountFen)+paymentType+bizType+royaltyParameters+PaymentConstant.SIG_PRIVATE_KEY;
		signature = MD5.md5(dataStr);
		getResponse().getWriter().write("{newSignature:'"+signature+"'}");
	}

	/**
	 * 设置支付等待时间
	 */
	
//	public vo
	
	
	/**
	 * 跳转到支付成功的页面,需要处理联系人邮箱的url.
	 * @return
	 */
	private String toSuccess(){
		handleCantactEmail();
		return "payMentComplent";
	}
	
	/**
	 * 支付回调成功页
	 * @return
	 * @throws Exception
	 */
	@Action("/order/toSuccess")
	public String toPaymentSuccess() throws Exception {
		if (this.isAccessAllowed()) {
			   orderInfo();
			if (!order.isUnpay()) {
				// 订单已支付，直接跳转到完成页面
				if (order.isPaymentSucc()) {
					return toSuccess();
				}
//				else if (order.isPrePaymentSucc()) {
//					return "payMentComplent";
//				}
				// 订单部分支付.
				else if (order.isPartPay()) {
					return "partPayMentComplent";
				}				
			} 
			String r = getRequestParameter("r");
			if(StringUtil.isNotEmptyString(r)){
				getRequest().setAttribute("r",  r);
			}
			getRequest().setAttribute("payState", "UNPAY");
			return "orderView";
		} else {
			return ERROR;
		}
	}
	/**
	 * 合并支付回调成功页
	 * @return
	 * @throws Exception
	 */
	@Action("/order/toMergePaySuccess")
	public String toMergePaymentSuccess() throws Exception {
		if(StringUtils.isBlank(orderIds)){
			return ERROR;
		}
		orderIdArray=orderIds.split(",");
		for (String tempOrderId : orderIdArray) {
			Long orderId=null;
			try {
				orderId=Long.valueOf(tempOrderId);
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
			if(!isAccessAllowed(orderId)){
				return ERROR;
			}
			
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(order==null){
				return ERROR;
			}
			
			if (!order.isPaymentSucc()) {
				return ERROR;
			}
		}
		return "mergePayComplete";
	}
	/**
	 * 保存联系人邮箱信息.
	 * 若是签约产品，并且已购选前台签约框，则保存信息。  
	 * @return
	 */
	private void saveContactEmail(){
		if (order.isNeedEContract() && isSigning.equals("Y")){
			// 联系人邮箱
			String contactEmail = this.getRequest().getParameter("contactEmail");
			// 更新订单联系人用户
			Person contactPersion = new Person();
			BeanUtils.copyProperties(order.getContact(), contactPersion);
			contactPersion.setEmail(contactEmail);
			orderServiceProxy.updatePerson(contactPersion, order.getOrderId(),this.getUserId());
			/**
			 * 删除生成合同功能,20120714 szy 在线预售权生成合同提前
			 */
		}
	}
	
	private void findAlipayToken()
	{
		Cookie[] cookies=getRequest().getCookies();
		if(!ArrayUtils.isEmpty(cookies))
		{
			for(Cookie c:cookies)
			{
				if(StringUtils.equals(c.getName(), ALIPAY_TOKEN_KEY))
				{
					if(StringUtils.isNotEmpty(c.getValue()))
					{
						hasAlipayLogin=true;
					}
					break;
				}
			}
		}
	}
	
	/**
	 * 查找盛付通令牌
	 */
	private void findSndaToken(){
		Cookie[] cookies = getRequest().getCookies();
		if(!ArrayUtils.isEmpty(cookies)){
			for(Cookie cookie:cookies){
				if(StringUtils.equals(cookie.getName(), SNDA_TOKEN_KEY)){
					if(StringUtils.isNotEmpty(cookie.getValue())){
						isSndaLogin = true;
					}
					break;
				}
			}
		}
	}

	/**
	 * @deprecated
	 * @return
	 */
	@Action("/re/jingmozhuce")
	public String jingMoZhuCe() {
		order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (this.isLogin()) {
			return "loginUpdateOrder";
		} else if (order != null
				&& order.getUserId().equalsIgnoreCase(
						Constant.DEFUALT_BOOKER_USER_ID)) {
			return "jingmozhuce";
		}
		return "error";

	}

	@Action("/udpate/order")
	public String updateOrderUser() throws Exception {
		if (this.isLogin()) {
			order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (order != null
					&& order.getUserId().equalsIgnoreCase(
							Constant.DEFUALT_BOOKER_USER_ID)) {
				LOG.info("订单号：" + order.getOrderId() + " 修改下单人"
						+ order.getUserId() + " update " + this.getUserId());
				// 修改订单中的userId
				orderServiceProxy.updateUserIdForOrder(order.getOrderId(),
						this.getUserId(), this.getUserId());
				// 修改下单人
				Person bookerPerson = null;
				if (order.getBooker() != null) {
					bookerPerson = new Person();
					BeanUtils.copyProperties(bookerPerson, order.getBooker());
				}
				UserUser users = this.getUser();
				// 如果当前用户没有手机号就不修改
				if (bookerPerson != null && users != null
						&& StringUtils.isNotEmpty(users.getMobileNumber())) {
					bookerPerson.setName(users.getRealName());
					bookerPerson.setMobile(users.getMobileNumber());
					orderServiceProxy.updatePerson(bookerPerson,
							order.getOrderId(), this.getUserId());
				}
			}
		}
		return "toOrderView";
	}
	
	/**
	 * 邮件电子合同回执
	 */
	@Action("/confirm/econtract")
	public String confirmEContract(){
		if (isValidEContract()) {				
			 signContract(orderId);
			return "sucConfirm";
		}else{
			return "failConfirm";
		}
	}
	
	/**
	 * 是否合法签约合同
	 * @return
	 */
	private boolean isValidEContract(){
		if (null == econtractId) {
			LOG.error("电子签约合同ID为null");
			return false;
		}
		OrdEContract ordEContract = ordEContractService.getOrdEconractByEContractId(econtractId);	
		if (null == ordEContract) {
			LOG.error("无法找到ID为" + econtractId + "的电子签约合同");
			return false;
		}
		orderId = ordEContract.getOrderId();
		return true;
	}

	/**
	 * @return 订单内容是否可被当前用户访问
	 */
	private boolean isAccessAllowed() {
		return isAccessAllowed(orderId);
	}
	private boolean isAccessAllowed(Long orderId) {
		if (null == orderId) {
			LOG.error("订单号为null,无法展示订单内容");
			return false;
		}
		order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (null == order) {
			LOG.error("无法找到订单号为" + orderId + "的订单，展示订单内容失败!");
			return false;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("订单号" + orderId + "存在且内容将被展现!");
			}
		}
		if (null == this.getBookerUserId()|| !this.getBookerUserId().equals(order.getUserId())) {
			LOG.info("订单" + order.getOrderId() + "被userId=" + this.getUserId()+ "非法访问");
			return false;
		}
		return true;
	}
	/**
	 * 封装订单所购产品信息
	 */
	private void orderInfo() {
		if (isNeedOrderInfo) {
			boolean existsInsurance=false;
			moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this
					.getUserId());
			List<OrdOrderItemProd> mainOrdProd = order.getOrdOrderItemProds();
			if(order.hasSelfPack()){
				mainOrderList.add(order.getMainProduct());
			}
			for (int i = 0; i < mainOrdProd.size(); i++) {
				OrdOrderItemProd itemProd = mainOrdProd.get(i);
				if ("true".equals(itemProd.getIsDefault())) {
					// 主产品
					mainOrderList.add(itemProd);
					this.id = itemProd.getProductId();
				} else if (!itemProd.isAdditionalProduct()) {
					// 相关产品
					relativeOrderList.add(itemProd);
					if (id != null) {
						this.id = itemProd.getProductId();
					}
				} else {
					// 附加产品
					additionalOrderList.add(itemProd);
				}
				//如果是保险做一次标志修改
				if(Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equalsIgnoreCase(itemProd.getSubProductType())){
					existsInsurance=true;
				}
			}
			if (null != order.getMainProduct() && null != order.getMainProduct().getProductId()) {				
				ProdProduct product = prodProductService.getProdProduct(order.getMainProduct().getProductId());
				if (null != product) {
					travellerInfoOptions = product.getTravellerInfoOptions();
				}
				if(mainOrderList.isEmpty()){
					mainOrderList.add(order.getMainProduct());
					this.id=order.getMainProduct().getProductId();
				}
			}
			
			if(StringUtils.isEmpty(travellerInfoOptions)&&existsInsurance){
				travellerInfoOptions="CARD_NUMBER,NAME,MOBILE";
			}
			// 储值卡支付信息
			if (!StringUtils.isEmpty(cardNo)) {
				StoredCard card = storedCardService.queryStoredCardByCardNo(cardNo, false);
				if (UtilityTool.isValid(card)) {
					List<StoredCardUsage> list = storedCardService.queryUsageListByCardId(buildUsageParameter(card.getStoredCardId()));
					formartList(list, card);
				} else {
					LOG.error("refund failed: Can not find StoredCard with the StoredCard cardNo = "
							+ cardNo);
				}
			}
			
			isNeedOrderInfo = false;
			
			//没有手机需要校验首笔订单信息 
			if(StringUtils.isBlank(getUser().getMobileNumber())){
				firstOrder=orderServiceProxy.queryUserFirstOrder(getUserId());
				this.setNeedsEmailCheck(moneyAccount.isNeedsEmailCheck());
			}
		}
	}
	
	/**
     * 包装储值卡消费记录查询条件.
     * @return
     */
	private Map<String, Object> buildUsageParameter(Long cardId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (cardId > 0) {
			map.put("cardId", cardId);
		}
		if (orderId > 0) {
			map.put("orderId", orderId);
		}
		map.put("usageType", STORED_CARD_ENUM.STORED_PAY.name());
		return map;
	}
	
	/**
	 * 组装数据.
	 * @param usageList
	 * @return
	 */
	private void formartList(List<StoredCardUsage> list, StoredCard card) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				StoredCardUsage usage = list.get(i);
				HashMap map = new HashMap();
				map.put("cardNo", card.getCardNo());
				map.put("amountFloat", card.getAmountFloat());
				map.put("payAmount", usage.getAmountFloat());
				map.put("balanceAmount", card.getBalanceFloat());
				map.put("overTime", UtilityTool.formatDate(card.getOverTime(), "yyyy-MM-dd"));
				usageList.add(map);
			}
		}
	}
	
	
	
	/**
	 * 处理联系人邮箱主站地址
	 */
	private void handleCantactEmail(){
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/mailWWW.properties"));
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
		String email = order.getContact().getEmail();
		if(!StringUtil.isEmptyString(email)&&email.indexOf("@")>0){
			catantMailHost = properties.getProperty(email.substring(email.indexOf("@")));
			if(StringUtils.isEmpty(catantMailHost)){
				catantMailHost = "http://mail."+email.substring(email.indexOf("@")+1);
			}
		}
	}
 
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getIsSigning() {
		return isSigning;
	}

	public void setIsSigning(String isSigning) {
		this.isSigning = isSigning;
	}

	public String getCatantMailHost() {
		return catantMailHost;
	}	
	public String getTravellerInfoOptions() {
		return travellerInfoOptions;
	}

	public boolean isHasAlipayLogin() {
		return hasAlipayLogin;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setEcontractId(String econtractId) {
		this.econtractId = econtractId;
	}

	public boolean isSndaLogin() {
		return isSndaLogin;
	}

	public List getUsageList() {
		return usageList;
	}

	public void setUsageList(List usageList) {
		this.usageList = usageList;
	}

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Map<String, Long> getInstalmentInfoMap() {
		return instalmentInfoMap;
	}

	public void setInstalmentInfoMap(Map<String, Long> instalmentInfoMap) {
		this.instalmentInfoMap = instalmentInfoMap;
	}

	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	public String getOrderTravel() {
		return orderTravel;
	}

	public void setOrderTravel(String orderTravel) {
		this.orderTravel = orderTravel;
	}

	public TravelDescriptionService getTravelDescriptionService() {
		return travelDescriptionService;
	}

	public void setTravelDescriptionService(
			TravelDescriptionService travelDescriptionService) {
		this.travelDescriptionService = travelDescriptionService;
	}

	public boolean isPreSalePowered() {
		return preSalePowered;
	}

	public void setPreSalePowered(boolean preSalePowered) {
		this.preSalePowered = preSalePowered;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getWaitPayment() {
		return waitPayment;
	}

	public void setWaitPayment(String waitPayment) {
		this.waitPayment = waitPayment;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getPaymentParams() {
		return paymentParams;
	}

	public void setPaymentParams(String paymentParams) {
		this.paymentParams = paymentParams;
	}

	public Long getPayAmountFen() {
		return payAmountFen;
	}

	public void setPayAmountFen(Long payAmountFen) {
		this.payAmountFen = payAmountFen;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public float getOughtPayTotalAmount() {
		return oughtPayTotalAmount;
	}
	public void setOughtPayTotalAmount(float oughtPayTotalAmount) {
		this.oughtPayTotalAmount = oughtPayTotalAmount;
	}
	public String[] getOrderIdArray() {
		return orderIdArray;
	}
	public void setOrderIdArray(String[] orderIdArray) {
		this.orderIdArray = orderIdArray;
	}
	public String getMergePayData() {
		return mergePayData;
	}
	public void setMergePayData(String mergePayData) {
		this.mergePayData = mergePayData;
	}


	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public String getPaymentParamsCashAccount() {
		return paymentParamsCashAccount;
	}
	public void setPaymentParamsCashAccount(String paymentParamsCashAccount) {
		this.paymentParamsCashAccount = paymentParamsCashAccount;
	}
	public String getRoyaltyParameters() {
		return royaltyParameters;
	}
	public void setRoyaltyParameters(String royaltyParameters) {
		this.royaltyParameters = royaltyParameters;
		LOG.info("Royaltys productId:"+id+",Royaltys orderId:"+order.getOrderId()+",Royalty parameters:"+royaltyParameters);
	}

	public String getFirstOrderCtMobile() {
		return firstOrderCtMobile;
	}

	public void setFirstOrderCtMobile(String firstOrderCtMobile) {
		this.firstOrderCtMobile = firstOrderCtMobile;
	}

	public OrdOrder getFirstOrder() {
		return firstOrder;
	}

	public void setFirstOrder(OrdOrder firstOrder) {
		this.firstOrder = firstOrder;
	}

	public boolean isNeedsEmailCheck() {
		return needsEmailCheck;
	}

	public void setNeedsEmailCheck(boolean needsEmailCheck) {
		this.needsEmailCheck = needsEmailCheck;
	}

	public void setAgree3(String agree3) {
		this.agree3 = agree3;
	}

	public void setAgree4(String agree4) {
		this.agree4 = agree4;
	}

	public void setAgree5(String agree5) {
		this.agree5 = agree5;
	}

	public void setAgree6(String agree6) {
		this.agree6 = agree6;
	}

	public Boolean getTempCloseCashAccountPay() {
		return tempCloseCashAccountPay;
	}

	public void setTempCloseCashAccountPay(Boolean tempCloseCashAccountPay) {
		this.tempCloseCashAccountPay = tempCloseCashAccountPay;
	}
	
}
