package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.OnLinePreSalePowerUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

/**
 * 初始化订单对象.</br>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @see com.lvmama.common.com.po.UsrUsers;
 * @see com.lvmama.common.prd.logic.ProductTimePriceLogic;
 * @see com.lvmama.common.spring.SpringBeanProxy;
 * @see com.lvmama.common.utils.DateUtil;
 * @see com.lvmama.common.utils.PriceUtil;
 * @see com.lvmama.comm.vo.Constant;
 */
public class OrdOrder implements Serializable, Comparable {
	private static Logger logger = Logger.getLogger(OrdOrder.class);

	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 7456723495866897889L;
	/**
	 * 订单明细信息.
	 */
	private List<OrdOrderItemProd> ordOrderItemProds = new ArrayList<OrdOrderItemProd>();

	/**
	 * 所有的采购产品项目信息.
	 */
	private List<OrdOrderItemMeta> allOrdOrderItemMetas = new ArrayList<OrdOrderItemMeta>();
	
	/**
	 * 是否当次更新
	 */
	private boolean updateInCurrent = false;
	
	/**
	 * 真实名字.
	 */
	private String realName;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 采购产品的状态.
	 */
	private String resourceConfirmStatus;
	/**
	 * 首次处理时间.
	 */
	private Date dealTime;
	/**
	 * 终审时间.
	 */
	private Date approveTime;
	/**
	 * 用户名.
	 */
	private String userName;
	/**
	 * 手机号.
	 */
	private String mobileNumber;
	/**
	 * 游玩时间.
	 */
	private Date visitTime;
	/**
	 * 游玩人.
	 */
	private List<OrdPerson> personList = new ArrayList<OrdPerson>();
	/**
	 * 取票人列表.
	 */
	private List<OrdPerson> travellerList = new ArrayList<OrdPerson>();
	/**
	 * 团信息扩展表.
	 */
	private OrdOrderRoute orderRoute = new OrdOrderRoute();
	
	/**
	 * 联系人.
	 */
	private OrdPerson contact;
	/**
	 * 下单人.
	 */
	private OrdPerson booker;
	/**
	 * 紧急联系人
	 */
	private OrdPerson emergencyContact;
	/**
	 * 支付等待时间.
	 */
	private Long waitPayment = -1L;
	/**
	 * 订单ID.
	 */
	private Long orderId;
	/**
	 * 原来订单ID.
	 */
	private Long originalOrderId;
	/**
	 * 订单流水号.
	 */
	private String orderNo;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 用户ID.
	 */
	private String userId;
	/**
	 * 订单状态.
	 */
	private String orderStatus = Constant.ORDER_STATUS.NORMAL.name();
	/**
	 * 订单显示状态.
	 */
	private String orderViewStatus;
	 
	/**
	 * 下单渠道.
	 */
	private String channel;
	/**
	 * 支付状态.
	 */
	private String paymentStatus = Constant.PAYMENT_STATUS.UNPAY.name();
	
	/**
	 * 最后支付时间
	 */
	private Date paymentTime;
	
	/**
	 * 支付完成时间
	 */
	private Date paymentFinishTime;
	/**
	 * 支付对象LVMAMA/SUPPLIER.
	 */
	private String paymentTarget;
	/**
	 * 订单金额(分).
	 */
	private Long orderPay;
	/**
	 * 订单金额(元).
	 */
	private float orderPayFloat;
	/**
	 * 应付金额 (元).
	 */
	private Long oughtPay;
	/**
	 * 应付金额金额 (元).
	 */
	private float oughtPayFloat;
	/**
	 * 实付.
	 */
	private Long actualPay;
	/**
	 * 实付金额.
	 */
	private float actualPayFloat;
	/**
	 * 减去奖金支付金额后的实付金额
	 */
	private Long actualPayExcludeBonusPaidAmount;
	/**
	 * 审核状态.
	 */
	private String approveStatus = Constant.ORDER_APPROVE_STATUS.UNVERIFIED
			.name();
	private String infoApproveStatus = Constant.INFO_APPROVE_STATUS.UNVERIFIED.name();
	/**
	 * redail.
	 */
	private String redail;
	/**
	 * 废单原因.
	 */
	private String cancelReason;
	/**
	 * 废单者.
	 */
	private String cancelOperator;
	/**
	 * 废单时间.
	 */
	private Date cancelTime;
	/**
	 * 用户定单备注.
	 */
	private String userMemo;
	/**
	 * 用户备注的反馈.
	 */
	private String userReply;
	/**
	 * 资源需确认.
	 */
	private String resourceConfirm;
	/**
	 * 是否超级自由行.
	 */
	private String selfPack;
	
	/**
	 * 是否是当天预订的订单;当前只有手机下单会使用
	 */
	private String todayOrder;
	
	/**
	 * 当天预订的订单时使用的入园最晚时间
	 */
	private Date latestUseTime;
	/**
	 * 是否履行.
	 */
	private String performStatus = Constant.ORDER_PERFORM_STATUS.UNPERFORMED
			.name();
	/**
	 * 是否领单.
	 */
	private String taken = Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name();
	/**
	 * 领单者.
	 */
	private String takenOperator;
	/**
	 * 订单类型.
	 */
	private String orderType;
	/**
	 * physical.
	 */
	private String physical;
	/**
	 * 是否需要发票.
	 */
	private String needInvoice = "false";
	
	/**
	 * 是否必须使用预授权 true/false
	 */
	private String needPrePay = "false";
	
	/**
	 * 提前预订时间
	 */
	private Date aheadTime;

	/**
	 * 是否需要服务.
	 */
	private String needSaleService = "false";
	/**
	 * 支付类型.
	 */
	private String paymentType;

	/**
	 * 发票地址.
	 */
	private OrdPerson address;
	/**
	 * 用户.
	 */
	private UserUser user;
	/**
	 * 订单是否支持部分支付.
	 */
	private String isOpenPartPay = "false";
	/**
	 * 返现金额.
	 * 
	 * <pre>
	 * 以分为单位
	 * </pre>
	 */
	private Long cashRefund;
	/**
	 * 是否返现.
	 */
	private String isCashRefund = "false";
	/**
	 * 发票.
	 */
	private List<OrdInvoice> invoiceList;
	
	/**
	 * 大交通相关的信息
	 */
	private List<OrdOrderTraffic> orderTrafficList;

	/**
	 * 限制支付渠道
	 */
	private String paymentChannel;
	/**
	 * 限制支付渠道(中文).
	 */
	private String zhPaymentChannel;
	/**
	 * 是否电子签约
	 */
	private String needContract;
	/**
	 * 团号.
	 */
	private String travelGroupCode;
	/**
	 * 订单首次支付金额.
	 */
	private Long payDeposit;
	/**
	 * 所属组织id
	 */
	private Long orgId;
	
	/**
	 * 招行规定:分期支付的最低金额.
	 */
	private final float MIN_CMB_INSTALMENT_PAY_YUAN = 500f;
	/**
	 * 工行规定:分期支付的最低金额.
	 */
	private final float MIN_ICBC_INSTALMENT_PAY_YUAN = 600f;
	/**
	 * 中行规定:分期支付的最低金额.
	 */
	private final float MIN_BOC_INSTALMENT_PAY_YUAN = 1000f;
	
	private String settlementStatus;
	
	/**
	 * 资源不满足原因 add by liuboen
	 */
	private String resourceLackReason;
	

	private OrdOrderItemProd mainProduct;
	
	/**
	 * 已成功退款的金额
	 */
	private Long refundedAmount = 0L;
	
	/**奖金支付金额**/
	private Long bonusPaidAmount=0L;
	
	/**储值卡支付的金额*/
	private Long sumCardAmount=0L;
	
	/**退改策略*/
	private String cancelStrategy;
	
	/**是否为不定期订单*/
	private String isAperiodic = "false";
	
	/**不定期订单有效期*/
	private Date validBeginTime;
	
	private Date validEndTime;
	
	private String canCreatInvoice;
	
	/**
	 * 是否启用预授权支付
	 */
	private String prePaymentAble;
	
	/**
	 * 供应商渠道
	 */
	private String supplierChannel;
    /**
     * 废单重下原因
     */
    private String cancelReorderReason;
//	/**
//	 * 订单团附加表ID.
//	 */
//	private Long orderRouteId;
	
	/**
	 * 订单是否属于秒杀
	 * @author zenglei
	 */
	private Long seckillId;

	public String getCancelStrategy() {
		return cancelStrategy;
	}
	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
	}
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public String getTodayOrder() {
		return todayOrder;
	}
	public void setTodayOrder(String todayOrder) {
		this.todayOrder = todayOrder;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	/**
	 * 分公司名称
	 */
	private String filialeName;
	/**
	 * 电子签约状态
	 * 
	 * @return
	 */
	private String eContractStatus = Constant.ECONTRACT_STATUS.UNCONFIRMED
			.name();
	
	/**
	 * 二次订单跟踪.
	 */
	private OrdOrderTrack  orderTrack = new OrdOrderTrack();
	
	/**
	 * 获取二次订单跟踪.
	 * @return
	 */
	public OrdOrderTrack getOrderTrack() {
		return orderTrack;
	}
	/**
	 * 设置二次订单跟踪.
	 * @param ordOrderTrack
	 */
	public void setOrderTrack(OrdOrderTrack orderTrack) {
		this.orderTrack = orderTrack;
	}

	/**
	 * 合同.
	 */
	private OrdEContract orderEContract = new OrdEContract();
	

	/**
	 * 合同状态.
	 */
	private String contractStatus;
	/**
	 * 合同确认时间.
	 */
    private Date confirmedDate;
	/**
	 * 设置订单合同.
	 * @return
	 */
    
    /**
     * 最晚取消时间
     */
    
    private Date lastCancelTime;
    /**
     * 是否为测试单标识
     */
    private String testOrderFlag;
    
    /**
     * 该订单是否可以点评
     */
    private boolean iscanComment;
    
    public boolean isIscanComment() {
		return iscanComment;
	}
	public void setIscanComment(boolean iscanComment) {
		this.iscanComment = iscanComment;
	}
	/**
     * 设置最晚取消时间
     * @param lastCancelTime
     */
    public void setLastCancelTime(Date lastCancelTime) {
		this.lastCancelTime = lastCancelTime;
	}
	public OrdEContract getOrderEContract() {
		return orderEContract;
	}
	
    /**
     * 获取订单合同.
     * @param ordEContract
     */
    public void setOrderEContract(OrdEContract orderEContract) {
		this.orderEContract = orderEContract;
	}
	/**
	 * 设置订单合同.
	 * @return
	 */
	public String getContractStatus() {
		if(StringUtils.isNotEmpty(orderEContract.getEcontractStatus())){
			return orderEContract.getEcontractStatus();
		}else{
			return contractStatus;
		}
	}
	/**
     * 获取订单合同.
     * @param contractStatus
     */
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
    /**
     * 设置合同确认时间.
     * @return
     */
	public Date getConfirmedDate() {
		return confirmedDate;
	}
    /**
     * 获取合同确认时间.
     * @param confirmedDate
     */
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}
	
	/**
	 * 是否被关注(领单者)
	 */
	private String isCare = "false";

	/**
	 * 加载下单人.
	 * 
	 * @return booker 下单人
	 */

	public OrdPerson getBooker() {
		if (this.booker == null && this.personList != null) {
			for (int i = 0; i < this.personList.size(); i++) {
				OrdPerson person = this.personList.get(i);
				if (Constant.ORD_PERSON_TYPE.BOOKER.name().equals(
						person.getPersonType())) {
					this.booker = person;
				}
			}
		}
		return this.booker;
	}

	/**
	 * 加载用户.
	 * 
	 * @return user 用户
	 */
	public UserUser getUser() {
		return user;
	}

	/**
	 * 设置用户.
	 * 
	 * @param user
	 *            用户
	 */
	public void setUser(UserUser user) {
		this.user = user;
	}

	/**
	 * 加载原来订单ID.
	 * 
	 * @return Long originalOrderId
	 */
	public Long getOriginalOrderId() {
		return originalOrderId;
	}

	/**
	 * 设置原来订单ID.
	 * 
	 * @param originalOrderId
	 */
	public void setOriginalOrderId(Long originalOrderId) {
		this.originalOrderId = originalOrderId;
	}

	private String passport = "false";

	/**
	 * 获取订单ID..
	 * 
	 * @return Long orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID.
	 * 
	 * @param Long
	 *            orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取订单流水号.
	 * 
	 * @return orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 设置订单流水号.
	 * 
	 * @param orderNo
	 *            订单流水号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取创建时间.
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间.
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取用户ID.
	 * 
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID.
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取订单状态.
	 * 
	 * @return orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 设置订单状态.
	 * 
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 获取支付状态.
	 * 
	 * @return paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置支付状态.
	 * 
	 * @param paymentStatus
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	/**
	 * 获取订单显示状态.
	 * 
	 * @return orderViewStatus
	 */
	public String getOrderViewStatus() {
		return orderViewStatus;
	}

	public String getZhOrderViewStatus() {
		return Constant.ORDER_VIEW_STATUS.getCnName(orderViewStatus);
	}
	
	/**
	 * 出团通知书状态（中文）
	 */
	public String getZhGroupWordStatus(){
		if("false".equalsIgnoreCase(this.orderRoute.getGroupWordStatus())){
			return "";
		}
		if (StringUtils.isNotEmpty(this.orderRoute.getGroupWordStatus())) {
			return Constant.GROUP_ADVICE_NOTE.getCnName(orderRoute.getGroupWordStatus());
		} else {
			return "";
		}
     }

	/**
	 * 设置订单显示状态.
	 * 
	 * @param orderViewStatus
	 */
	public void setOrderViewStatus(String orderViewStatus) {
		this.orderViewStatus = orderViewStatus;
	}
	/**
	 * 获取订单金额(分).
	 * @return
	 */
	public Long getOrderPay() {
		return orderPay;
	}
	/**
	 * 设置订单金额(分).
	 * @param orderPay
	 */
	public void setOrderPay(Long orderPay) {
		this.orderPay = orderPay;
	}
	/**
	 * 获取订单金额(元).
	 * @return
	 */
	public float getOrderPayFloat() {
		if (orderPay != null) {
			this.orderPayFloat = PriceUtil.convertToYuan(orderPay.longValue());
		}
		return orderPayFloat;
	}
	/**
	 * 设置订单金额(元).
	 * @param orderPayFloat
	 */
	public void setOrderPayFloat(float orderPayFloat) {
		this.orderPayFloat = orderPayFloat;
	}
	/**
	 * 获取应付金额.
	 * 
	 * @return oughtPay
	 */
	public Long getOughtPay() {
		return oughtPay;
	}

	/**
	 * 设置应付金额.
	 * 
	 * @param oughtPay
	 */
	public void setOughtPay(Long oughtPay) {
		if (oughtPay != null) {
			this.oughtPayFloat = PriceUtil.convertToYuan(oughtPay.longValue());
		}
		this.oughtPay = oughtPay;
	}

	/**
	 * 获取实付金额.
	 * 
	 * @return actualPay
	 */
	public Long getActualPay() {
		return actualPay;
	}

	/**
	 * 设置实付金额.
	 * 
	 * @param actualPay
	 */
	public void setActualPay(Long actualPay) {
		if (actualPay != null) {
			this.actualPayFloat = PriceUtil
					.convertToYuan(actualPay.longValue());
		}
		this.actualPay = actualPay;
	}

	/**
	 * 获取审核状态.
	 * 
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * 设置审核状态.
	 * 
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	/**
	 * 获取支付对象.
	 * 
	 * @return paymentTarget
	 */
	public String getPaymentTarget() {
		return paymentTarget;
	}

	/**
	 * 设置支付对象.
	 * 
	 * @param paymentTarget
	 */
	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	/**
	 * 获取废单原因.
	 * 
	 * @return cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * 设置废单原因.
	 * 
	 * @param cancelReason
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * 获取废单者.
	 * 
	 * @return cancelOperator
	 */
	public String getCancelOperator() {
		return cancelOperator;
	}

	/**
	 * 设置废单者.
	 * 
	 * @param cancelOperator
	 */
	public void setCancelOperator(String cancelOperator) {
		this.cancelOperator = cancelOperator;
	}

	/**
	 * 获取废单时间.
	 * 
	 * @return cancelOperator
	 */
	public Date getCancelTime() {
		return cancelTime;
	}

	/**
	 * 设置废单时间.
	 * 
	 * @param cancelTime
	 */
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	/**
	 * 获取用户定单备注.
	 * 
	 * @return userMemo
	 */
	public String getUserMemo() {
		return userMemo;
	}

	/**
	 * 设置用户定单备注.
	 * 
	 * @param userMemo
	 */
	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}

	/**
	 * 获取用户备注的反馈.
	 * 
	 * @return userReply
	 */
	public String getUserReply() {
		return userReply;
	}

	/**
	 * 设置用户备注的反馈.
	 * 
	 * @param userReply
	 */
	public void setUserReply(String userReply) {
		this.userReply = userReply;
	}

	/**
	 * 获取是否履行状态.
	 * 
	 * @return performStatus
	 */
	public String getPerformStatus() {
		return performStatus;
	}

	/**
	 * 设置履行状态.
	 * 
	 * @param performStatus
	 */
	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}

	/**
	 * 获取是否领单状态.
	 * 
	 * @return taken
	 */
	public String getTaken() {
		return taken;
	}

	/**
	 * 设置领单状态.
	 * 
	 * @param taken
	 */
	public void setTaken(String taken) {
		this.taken = taken;
	}

	/**
	 * 获取领单者.
	 * 
	 * @return takenOperator
	 */
	public String getTakenOperator() {
		return takenOperator;
	}

	/**
	 * 设置领单者.
	 * 
	 * @param takenOperator
	 */
	public void setTakenOperator(String takenOperator) {
		this.takenOperator = takenOperator;
	}

	/**
	 * 获取是否支持部分支付.
	 * 
	 * @return
	 */
	public String getIsOpenPartPay() {
		return isOpenPartPay;
	}

	/**
	 * 设置是否支持部分支付.
	 * 
	 * @param isPartPay
	 */
	public void setIsOpenPartPay(String isOpenPartPay) {
		this.isOpenPartPay = isOpenPartPay;
	}

	/**
	 * 获取团号.
	 * 
	 * @return
	 */
	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	/**
	 * 设置团号.
	 * 
	 * @param travelGroupCode
	 */
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	/**
	 * 获取订单首次支付金额.
	 * 
	 * @return
	 */
	public Long getPayDeposit() {
		return payDeposit;
	}

	/**
	 * 设置订单首次支付金额.
	 * 
	 * @param travelGroupCode
	 */
	public void setPayDeposit(Long payDeposit) {
		this.payDeposit = payDeposit;
	}
	
	/**
	 * 获取订单类型.
	 * 
	 * @return orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * 设置订单类型.
	 * 
	 * @param orderType
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * 判断订单类型是不是境内跟团游,多人成团.
	 * 
	 * @return
	 */
	public boolean isGroupOrderType() {
		return (Constant.SUB_PRODUCT_TYPE.GROUP.name().equalsIgnoreCase(orderType)||
				Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(orderType)||
				Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(orderType));
	}
	
	/**
	 * 判断下单渠道.
	 * 
	 * @return
	 */
	public boolean isFrontendOrder() {
		return Constant.CHANNEL.FRONTEND.name().equalsIgnoreCase(channel);
	}

	/**
	 * 判断是否显示撤销预授权支付.
	 * @return 
	 */
//	public boolean  isViewCancelPre() {
//		return Constant.PAYMENT_STATUS.PREPAYED.name().equals(this.paymentStatus) && 
//		Constant.ORDER_STATUS.NORMAL.name().equalsIgnoreCase(orderStatus);
//	}
	
	/**
	 * 判断未支付.
	 * @return
	 */
	public boolean isUnpay() {
		return Constant.PAYMENT_STATUS.UNPAY.name().equals(this.paymentStatus);
	}
	/**
	 * 判断预授权支付.
	 * @return
	 */
//	public boolean  isPrepayed() {
//		return Constant.PAYMENT_STATUS.PREPAYED.name().equals(this.paymentStatus);
//	}
	/**
	 * 判断是否部分支付.
	 * @return
	 */
	public boolean isPartPay() {
		return Constant.PAYMENT_STATUS.PARTPAY.name().equals(this.paymentStatus);
	}
	/**
	 * 判断支付状态,是否全额支付.
	 * 
	 * @return
	 */
	public boolean isPaymentSucc() {
		return Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(paymentStatus);
	}
	
	/**
	 * 判断支付状态,是否全额支付.
	 * 
	 * @return
	 */
	public boolean isPaySucc() {
		return Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(paymentStatus);
	}
	
	/**
	 * 判断支付状态,是否预授权支付.
	 * @return
	 */
//	public boolean isPrePaymentSucc(){
//		return Constant.PAYMENT_STATUS.PREPAYED.name().equalsIgnoreCase(paymentStatus);
//	}
	
	/**
	 * 判断支付状态,是预授权支付或者已付.
	 * @return
	 */
//	public boolean isPreAndPaySucc() {
//		return Constant.PAYMENT_STATUS.PREPAYED.name().equalsIgnoreCase(paymentStatus) ||
//		Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(paymentStatus);
//	}

	/**
	 * 判断信息是否通过.
	 * 
	 * @return
	 */
	public boolean isApproveInfoPass() {
		return Constant.INFO_APPROVE_STATUS.INFOPASS.name().equalsIgnoreCase(
				infoApproveStatus);
	}

	/**
	 * 判断资源情况.
	 * 
	 * @return
	 */
	public boolean isApproveResourceAmple() {
		return Constant.ORDER_RESOURCE_STATUS.AMPLE.name().equalsIgnoreCase(
				resourceConfirmStatus);
	}

	/**
	 * 判断审核.
	 * 
	 * @return
	 */
	public boolean isApprovePass() {
		return Constant.ORDER_APPROVE_STATUS.VERIFIED.name().equalsIgnoreCase(
				approveStatus);
	}

	/**
	 * 判断审核不通过.
	 * 
	 * @return
	 */
	public boolean isApproveFail() {
		return Constant.ORDER_APPROVE_STATUS.RESOURCEFAIL.name().equalsIgnoreCase(
				approveStatus);
	}
	
	/**
	 * 判断取消.
	 * 
	 * @return
	 */
	public boolean isCanceled() {
		return Constant.ORDER_STATUS.CANCEL.name()
				.equalsIgnoreCase(orderStatus);
	}

	/**
	 * 判断支付对象.
	 * 
	 * @return
	 */
	public boolean isPayToLvmama() {
		return Constant.PAYMENT_TARGET.TOLVMAMA.name().equalsIgnoreCase(paymentTarget);
	}
	
	public boolean isPayToSupplier() {
		return Constant.PAYMENT_TARGET.TOSUPPLIER.name().equalsIgnoreCase(paymentTarget);
	}

	/**
	 * 判断资源是否需要确认.
	 * 
	 * @return
	 */
	public boolean isNeedResourceConfirm() {
		return "true".equalsIgnoreCase(resourceConfirm);
	}
	
	/**
	 * 判断是否可以使用分期支付.
	 * @return
	 */
	public boolean isCanInstalment(){
		return isCanCMBInstalment() || isCanICBCInstalment() || isCanBOCInstalment();
	}
	/**
	 * 是否满足招行分期支付条件
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isCanCMBInstalment(){
		return oughtPayFloat >= MIN_CMB_INSTALMENT_PAY_YUAN && Constant.PAYMENT_STATUS.UNPAY.name().equalsIgnoreCase(paymentStatus);
	}
	/**
	 * 是否满足工行分期支付条件
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isCanICBCInstalment(){
		return oughtPayFloat >= MIN_ICBC_INSTALMENT_PAY_YUAN && Constant.PAYMENT_STATUS.UNPAY.name().equalsIgnoreCase(paymentStatus);
	}
	/**
	 * 是否满足中行分期支付条件
	 * @author ZHANG Nan
	 * @return
	 */
	public boolean isCanBOCInstalment(){
		return oughtPayFloat >= MIN_BOC_INSTALMENT_PAY_YUAN && Constant.PAYMENT_STATUS.UNPAY.name().equalsIgnoreCase(paymentStatus);
	}
	
	/**
	 * 获取资源需确认.
	 * 
	 * @return resourceConfirm
	 */
	public String getResourceConfirm() {
		return resourceConfirm;
	}

	/**
	 * 设置资源需确认.
	 * 
	 * @param resourceConfirm
	 */
	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

	/**i
	 * 获取订单明细，为空则执行加载
	 * 
	 * @return
	 */
	public List<OrdOrderItemProd> getOrdOrderItemProds() {
		return ordOrderItemProds;
	}

	/**
	 * 获取此订单所有的采购项目列表
	 * 
	 * @return
	 */
	public List<OrdOrderItemMeta> getAllOrdOrderItemMetas() {
		return allOrdOrderItemMetas;
	}

	/**
	 * 重设订单的默认废单等待时间
	 */
	public void reCalcWaitPayment() {
		if(!hasSelfPack()&&!hasTodayOrder()){//非超级自由行才处理重新设置的逻辑
			if (Constant.ORDER_TYPE.TICKET.name().equals(orderType)) {
				if(this.waitPayment==null||this.waitPayment ==0L){
					this.waitPayment = Constant.WAIT_PAYMENT.PW_2HOUR.getValue();
				}
				
			}
			if (Constant.ORDER_TYPE.FREENESS_LONG.name().equals(orderType)
					|| Constant.ORDER_TYPE.GROUP_LONG.name().equals(orderType)) {
				this.waitPayment = Constant.WAIT_PAYMENT.PW_12HOUR.getValue();
			}else if(Constant.ORDER_TYPE.GROUP_FOREIGN.name().equals(orderType)
					|| Constant.ORDER_TYPE.FREENESS_FOREIGN.name()
					.equals(orderType)){
				this.waitPayment = Constant.WAIT_PAYMENT.PW_24HOUR.getValue();
			}
			if(Constant.ORDER_TYPE.HOTEL.name().equals(orderType)){
				//供应商为港捷旅订房集团中心，资源无需确认的产品支付等待时间为30分钟
				if (this.hasGangjie() && !isNeedResourceConfirm()){
					this.waitPayment = Constant.WAIT_PAYMENT.PW_HALF_HOUR.getValue();
				}else{
					this.waitPayment = Constant.WAIT_PAYMENT.PW_4HOUR.getValue();
				}
			}else if(Constant.ORDER_TYPE.TRAIN.name().equals(orderType)){//火车票定半小时
				this.waitPayment = Constant.WAIT_PAYMENT.PW_HALF_HOUR.getValue();
			}
			if(this.getIsJinjiangOrder()){
				Long waitTime = DateUtil.getMinBetween(new Date(), getOrderAheadDate());
				this.waitPayment = this.waitPayment>waitTime?waitTime:this.waitPayment;
			}
		}
	}
	
	/**
	 * 构造默认最小支付等待时间
	 */
	public void compareWaitPayment(){
		Date waitPaymentTime = DateUtil.DsDay_Minute(this.approveTime, this.waitPayment.intValue());
		Date lastCancelTime = getLastCancelTime();
		if( lastCancelTime!=null && waitPaymentTime.after(lastCancelTime)){
			waitPayment = (lastCancelTime.getTime()-approveTime.getTime())/(1000*60)+1;
		}
	}
	/**
	 * 必须用预授权的起始时间
	 * @return
	 */
	public Date getNeedPrePayBeginTime(){
		if(lastCancelTime==null){
			return null;
		}else{
			return DateUtils.addHours(lastCancelTime, -1);
		}
	}

	/**
	 * 获取支付等待时间.
	 * 
	 * @return waitPayment
	 */
	public Long getWaitPayment() {
		return waitPayment;
	}

	/**
	 * 设置支付等待时间.
	 * 
	 * @param waitPayment
	 */
	public void setWaitPayment(Long waitPayment) {
		this.waitPayment = waitPayment;
	}

	/**
	 * 获取redail.
	 * 
	 * @return redail
	 */
	public String getRedail() {
		return redail;
	}

	/**
	 * 设置redail.
	 * 
	 * @param redail
	 */
	public void setRedail(String redail) {
		this.redail = redail;
	}

	/**
	 * 获取订单团附加表ID.
	 * @return
	 */

	public Long getOrderRouteId() {
//		if (StringUtils.isNotEmpty(this.orderRoute.getOrderId().name())) {
		return orderRoute.getOrderRouteId();
//		} else {
//			return -1L;
//		}
	}

//	/**
//	 * 设置订单团附加表ID.
//	 * @param orderRouteId
//	 */
//	public void setOrderRouteId(Long orderRouteId) {
//		this.orderRouteId = orderRouteId;
//	}

	/**
	 * 获取开票状态.
	 * @return
	 */
	public String getTrafficTicketStatus() {
		if (StringUtils.isNotEmpty(this.orderRoute.getTrafficTicketStatus())) {
			return orderRoute.getTrafficTicketStatus();
		} else {
			return "";
		}
	}


	/**
	 * 获取出团通知书的状态.
	 * @return
	 */
	public String getGroupWordStatus() {
		if (StringUtils.isNotEmpty(this.orderRoute.getGroupWordStatus())) {
			return orderRoute.getGroupWordStatus();
		} else {
			return "";
		}
	}

	

	/**
	 * 获取签证状态.
	 * @return
	 */
	public String getVisaStatus() {
		if (StringUtils.isNotEmpty(this.orderRoute.getVisaStatus())) {
			return orderRoute.getVisaStatus();
		} else {
			return "";
		}
	}

	
	/**
	 * 获取订单明细信息.
	 * 
	 * @return ordOrderItemProds
	 */
	public void setOrdOrderItemProds(List<OrdOrderItemProd> ordOrderItemProds) {
		mainProduct=(OrdOrderItemProd)CollectionUtils.find(ordOrderItemProds, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				OrdOrderItemProd item=(OrdOrderItemProd)arg0;
				return StringUtils.equals("true", item.getIsDefault());
			}
		});
		
		this.ordOrderItemProds = ordOrderItemProds;
		
	}
	
	/**
	 * 从列表当中删除自主打包的主产品.
	 */
	public void removeMainInItemProds(){
		if(mainProduct!=null&&hasSelfPack()){
			this.ordOrderItemProds.remove(mainProduct);
		}
	}

	public void setAllOrdOrderItemMetas(
			List<OrdOrderItemMeta> allOrdOrderItemMetas) {
		this.allOrdOrderItemMetas = allOrdOrderItemMetas;
	}

	public List<OrdPerson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<OrdPerson> personList) {
		this.personList = personList;
	}

	public String getZhPaymentTarget() {
		return Constant.PAYMENT_TARGET.getCnName(this.getPaymentTarget());
	}

	public String getChannel() {
		return channel;
	}
	
	public String getZhChannel(){
		return Constant.CHANNEL.getCnName(channel);
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public float getOughtPayFloat() {
		if (this.oughtPay != null) {
			this.oughtPayFloat = PriceUtil
					.convertToYuan(oughtPay.longValue());
		}
		return oughtPayFloat;
	}

	public void setOughtPayFloat(float oughtPayFloat) {
		this.oughtPayFloat = oughtPayFloat;
	}

	public float getActualPayFloat() {
		return actualPayFloat;
	}

	public void setActualPayFloat(float actualPayFloat) {
		this.actualPayFloat = actualPayFloat;
	}

	public float getActualPayYuan() {
		return PriceUtil.convertToYuan(actualPay);
	}

	public float getCashRefundYuan() {
		if (null == cashRefund) {
			return 0F;
		}
		return PriceUtil.convertToYuan(cashRefund);
	}

	/**
	 * 应付订单的金额.
	 * @return
	 */
	public float getOughtPayYuan() {
		if (oughtPay==null || oughtPay <= 0L) {
			return 0L;
		} else {
			return PriceUtil.convertToYuan(oughtPay);
		}
	}
	
	/**
	 * 应付订单的金额，用于在用户的订单列表显示订单金额.
	 * @return
	 */
	public float getOughtPayYuanFloat() {
		if (this.oughtPay - this.actualPay <= 0) {
			return 0L;
		} else {
			return PriceUtil.convertToYuan(oughtPay - this.actualPay);
		}
	}
	
	/**
	 * 应付订单的金额 分，用于在用户的订单列表显示订单金额.
	 * @return
	 */
	public Long getOughtPayFenLong() {
		if (this.oughtPay - this.actualPay <= 0) {
			return 0L;
		} else {
			return oughtPay - this.actualPay;
		}
	}
	

	public float getPayDepositYuan() {
		return PriceUtil.convertToYuan(payDeposit);
	}
	
	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRealName() {
		return realName;
	}

	public String getResourceConfirmStatus() {
		return resourceConfirmStatus;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public void setResourceConfirmStatus(String resourceConfirmStatus) {
		this.resourceConfirmStatus = resourceConfirmStatus;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	// 获取支付方式
	public String getZhPaymentType() {
		return Constant.PAYMENT_TYPE.getCnName(this.paymentType);
	}

	public int compareTo(Object arg0) {
		if (arg0 instanceof OrdOrder) {
			OrdOrder order = (OrdOrder) arg0;
			if (orderId < order.getOrderId()) {
				return -1;
			} else if (orderId == order.getOrderId()) {
				return 0;
			} else {
				return 1;
			}
		}
		return -1;
	}

	public OrdPerson getContact() {
		return contact;
	}

	public void setContact(OrdPerson contact) {
		this.contact = contact;
	}

	public boolean isPassportOrder() {
		return "true".equalsIgnoreCase(passport);
	}

	public String getZhPaymentStatus() {
		return Constant.PAYMENT_STATUS.getCnName(paymentStatus);
	}

	public String getZhProductChannel() {
		return Constant.CHANNEL.getCnName(channel);
	}

	public String getZhCreateTime() {
		return DateUtil.getFormatDate(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getZhVisitTime() {
		return DateUtil.getFormatDate(visitTime, "yyyy-MM-dd HH:mm");
	}

	public String getZhWaitPayment() {
		Date date = getWaitPaymentDate();
		if(date==null){
			return "";
		}else{
			return DateUtil.getDateTime("yyyy-MM-dd HH:mm", date);
		}
	}
	private Date getWaitPaymentDate(){
		if (this.isApprovePass()&&null!=approveTime) {
			Date waitPayDate = DateUtil.DsDay_Minute(this.approveTime, this.waitPayment.intValue());
			if (lastCancelTime != null && waitPayDate.after(lastCancelTime)) {
				waitPayDate = lastCancelTime;
			}
			return waitPayDate;
		} else {
			return null;
		}
	}
	public String getZhSettlementStatus() {
		return Constant.SETTLEMENT_STATUS.getCnName(settlementStatus);
	}
	public String getZhApproveStatus() {
		return Constant.ORDER_APPROVE_STATUS.getCnName(this.approveStatus);
	}
	public String getZhInfoApproveStatus() {
		return Constant.INFO_APPROVE_STATUS.getCnName(this.infoApproveStatus);
	}

	public Long getCashRefund() {
		return cashRefund;
	}

	public void setCashRefund(final Long cashRefund) {
		this.cashRefund = cashRefund;
	}

	public String getIsCashRefund() {
		return isCashRefund;
	}

	public void setIsCashRefund(final String isCashRefund) {
		this.isCashRefund = isCashRefund;
	}

	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(final List<OrdInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getNeedSaleService() {
		return needSaleService;
	}

	public void setNeedSaleService(String needSaleService) {
		this.needSaleService = needSaleService;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getZhOrderStatus() {
		return Constant.ORDER_STATUS.getCnName(orderStatus);
	}

	public String getZhOrderType() {
		return Constant.ORDER_TYPE.getCnName(orderType);
	}

	public String getZhPerformStatus() {
		return Constant.ORDER_PERFORM_STATUS.getCnName(performStatus);
	}

	public String getZhNeedSaleService() {
		return Constant.TRUE_FALSE.getCnName(needSaleService);
	}

	/**
	 * getTravellerList.
	 * 
	 * @return 取票人列表
	 */
	public List<OrdPerson> getTravellerList() {
		return travellerList;
	}

	/**
	 * setTravellerList.
	 * 
	 * @param travellerList
	 *            取票人列表
	 */
	public void setTravellerList(final List<OrdPerson> travellerList) {
		this.travellerList = travellerList;
	}

	/**
	 * getAddress.
	 * 
	 * @return 发票地址
	 */
	public OrdPerson getAddress() {
		return address;
	}

	/**
	 * setAddress.
	 * 
	 * @param address
	 *            发票地址
	 */
	public void setAddress(final OrdPerson address) {
		this.address = address;
	}

	/**
	 * 是不是支付完成.
	 * @return
	 */
	public boolean isFullyPayed() {
		return (oughtPay == null ? 0 : oughtPay.longValue()) <= (actualPay == null ? 0 : actualPay.longValue());
	}
	
	/**
	 * 是不是支付完成.
	 * @return
	 */
	public boolean isOfflinePay() {
		return !(oughtPay.equals(actualPay));
	}
	
	
	public boolean isOpenPartPay(){
		return "true".equals(this.isOpenPartPay);
	}
	/**
	 * 判断是不是可以分笔支付,定金为0不能分笔支付,再判断isOpenPartPay的状态,.
	 * @return
	 */
	public boolean isSubpenPay(){
		boolean isPay=false;
		if(this.payDeposit>0){
			isPay=this.isOpenPartPay();
		}else{
			isPay=false;
		}
		return isPay;
	}
	
	public boolean isNormal() {
		return Constant.ORDER_STATUS.NORMAL.name().equalsIgnoreCase(
				this.orderStatus);
	}
	
	/**
	 * 可以在线支付的判断.
	 * @return
	 */
	public boolean isCanToPay() {
		if (this.isPayToLvmama()) {
			if (this.isNormal() && !this.isPaymentSucc()) {
				if (this.isApprovePass() && this.isApproveResourceAmple()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 移动端能付支付
	 * 过滤部分支付  - 还原部分支付
	 * @return
	 */
	public boolean mobileCanToPay(){
		if (this.isPayToLvmama()) {
			if (this.isNormal() && !this.isPaymentSucc()) {
				if (this.isApprovePass() && this.isApproveResourceAmple()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 预授权支付的判断.
	 * @return
	 */
	public boolean isCanToPrePay() {
		if (this.isPayToLvmama()) {
			if (this.isNormal() && this.isUnpay() && (this.isUsePrePay() || this.hasNeedPrePay())) {
				if (!this.isApprovePass() && !this.isApproveResourceAmple() && OnLinePreSalePowerUtil.getOnLinePreSalePower(this) && !isPaymentChannelLimit()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 新增预授权属性时追加，判断是否使用预授权标志
	 * @return boolean
	 */
	private boolean isUsePrePay(){
		return "Y".equals(this.getPrePaymentAble());
	}

	public boolean isExpireToPay() {
		if(hasNeedPrePay()){
			return aheadTime.before(new Date());
		}else{
			if (waitPayment == -1) {
				// 减6小时后的游玩时间，在当前系统时间之前就不能支付
				Date visitDate = DateUtil.getDateBeforeHours(visitTime, 6);
				return visitDate.before(new Date());
			}
			//只有在订单审核通过后才可以使用approveTime.
			if(isApprovePass()){
				long time = approveTime.getTime();
				long expireTime = time + (waitPayment * 60 * 1000);
				return System.currentTimeMillis() > expireTime;
			}
		}
		return false;
	}

	public float getMarketAmountYuan() {
		float amount = 0;
		for (OrdOrderItemProd item : ordOrderItemProds) {
			if("true".equals(item.getIsDefault())&&hasSelfPack()){//如果是自主打包的主产品跳过
				continue;
			}
			amount += item.getMarketAmountYuan();
		}
		return amount;
	}

	public float getSaveAmountYuan() {
		return getMarketAmountYuan() - getOughtPayYuan();
	}

	public boolean isShouldSendCert() {
		if (isNormal() && this.isApprovePass()) {
			if (!this.isPassportOrder() && this.isPayToLvmama()
					&& this.isPaymentSucc()) {
				return true;
			} else if (!this.isPassportOrder() && !this.isPayToLvmama()) {
				return true;
			} else if (this.isPassportOrder() && this.isPayToLvmama()
					&& this.isPaymentSucc()) {
				return true;
			} else if (this.isPassportOrder() && !this.isPayToLvmama()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否存在已经结算的子项
	 * 
	 * @return
	 */
	public boolean isHasSettlement() {
		for (OrdOrderItemMeta meta : allOrdOrderItemMetas) {
			if (meta.getSettlementStatus().equals(
					Constant.SETTLEMENT_STATUS.SETTLEMENTED.name()))
				return true;
		}
		return false;
	}
 
	public boolean isOutLimitTime() {
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			if (new Date().after(new Date(this.getVisitTime().getTime() - 1000
					* 60 * prod.getAheadHour())))
				return true;
		}
		return false;
	}

	public OrdOrderItemProd getMainProduct() {
		if(mainProduct!=null){
			return mainProduct;
		}
		
		
		if(CollectionUtils.isEmpty(ordOrderItemProds))
			return null;
		
		//只有一个产品的情况下直接选第一个
		if(this.ordOrderItemProds.size()==1){
			return ordOrderItemProds.get(0);
		}
		
		//有多个产品时从产品当中选择isDefault为true的产品
		for (OrdOrderItemProd itemProd : this.ordOrderItemProds) {
			if ("true".equals(itemProd.getIsDefault())&&!itemProd.isOtherProductType()) {
				return itemProd;
			} 
		}
		
		//有多个产品时从产品当中选择wrappage为true,additional为false的产品
		for (OrdOrderItemProd itemProd : this.ordOrderItemProds) {
			if ("true".equals(itemProd.getWrapPage())
					&& !itemProd.isAdditionalProduct()) {
				return itemProd;
			} 
		}
		
		//有多个产品并且没有找到wrap的值的时候取第一个不是附加产品项
		for(OrdOrderItemProd itemProd:this.ordOrderItemProds){
			if(!itemProd.isAdditionalProduct()&&!itemProd.isOtherProductType()){
				return itemProd;
			}
		}
		
		//没有一个非附加产品的时候就直接取第一个
		return ordOrderItemProds.get(0);
	}

	public Long calcTotalPersonQuantity() {
		Long quantity = 0L;
		for (OrdOrderItemProd itemProd : getOrdOrderItemProds()) {
			/**
			 * 这个地方以前是并的关系，现在改为是或的关系，因为现在的类别当中也有附加的类别，人数不计算在内
			 */
			
			if (itemProd.isOtherProductType() || itemProd.isAdditionalProduct()) {
				continue;
			} else {
				for (OrdOrderItemMeta itemMeta : itemProd
						.getOrdOrderItemMetas()) {
					if (!itemMeta.isOtherProductType()) {
						quantity += itemMeta.getTotalAdultQuantity()
								+ itemMeta.getTotalChildQuantity();
					}
				}
			}
		}
		return quantity;
	}

	public String calcOrderType() {
		String orderType = null;
		if(hasSelfPack()){//超级自由行产品
			OrdOrderItemProd item=getMainProduct();
			orderType=item.getSubProductType();
		}else{
			for (OrdOrderItemProd item : ordOrderItemProds) {
				if (item.getQuantity() > 0) {
					if (Constant.PRODUCT_TYPE.ROUTE.name().equalsIgnoreCase(
							item.getProductType())) {
						if (orderType == null
								|| orderType
										.equals(Constant.PRODUCT_TYPE.OTHER.name()))
							orderType = item.getSubProductType();
					} else if (Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(
							item.getProductType())) {
						if (orderType == null
								|| orderType
										.equals(Constant.PRODUCT_TYPE.OTHER.name()))
							orderType = item.getProductType();
					} else if (Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(
							item.getProductType())) {
						if (orderType == null
								|| orderType
										.equals(Constant.PRODUCT_TYPE.OTHER.name()))
							orderType = item.getProductType();
					} else if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(item.getProductType())){
						if(orderType == null &&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(orderType)){
							orderType =item.getSubProductType();
						}
					}else if (Constant.PRODUCT_TYPE.OTHER.name().equalsIgnoreCase(
							item.getProductType())) {
						orderType = item.getProductType();
					}
				}
			}
		}
		return orderType;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	
	public void setZhPaymentChannel(String zhPaymentChannel) {
		this.zhPaymentChannel = zhPaymentChannel;
	}
	public String getZhPaymentChannel(){
		if(StringUtils.isEmpty(getPaymentChannel())){
			return "";
		}
		return Constant.PAYMENT_GATEWAY.getCnName(this.getPaymentChannel());
	}

	public boolean isPaymentChannelLimit() {
		if (this.paymentChannel != null) {
			return true;
		}
		return false;
	}
	/**
	 * 是否是酒店
	 * 
	 * @return
	 */
	public boolean isHotel() {
		return Constant.PRODUCT_TYPE.HOTEL.name().equals(orderType);
	}

	public boolean isTicket() {
		return Constant.PRODUCT_TYPE.TICKET.name().equals(orderType);
	}

	public boolean isOther() {
		return Constant.PRODUCT_TYPE.OTHER.name().equals(orderType);
	}
	
	public boolean isRoute() {
		return Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(orderType) 
				|| Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(orderType) 
				|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(orderType)
				|| Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(orderType)
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(orderType)
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(orderType)
				|| Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(orderType);
	}
	
	/**
	 * 退改策略
	 * */
	public boolean isAble() {
		return StringUtils.isNotEmpty(cancelStrategy) ? Constant.CANCEL_STRATEGY.ABLE.name().equalsIgnoreCase(cancelStrategy) : false;
	}
	
	public boolean isManual() {
		return StringUtils.isNotEmpty(cancelStrategy) ? Constant.CANCEL_STRATEGY.MANUAL.name().equalsIgnoreCase(cancelStrategy) : false;
	}
	
	public boolean isForbid() {
		return StringUtils.isNotEmpty(cancelStrategy) ? Constant.CANCEL_STRATEGY.FORBID.name().equalsIgnoreCase(cancelStrategy) : false;
	}
	
	/**
	 * 出境跟团游.
	 * @return
	 */
	public boolean isGroupForeign() {
		return Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(orderType);
	}
	
	public boolean isGroupLong() {
		return Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(orderType);
	}
	
	public boolean isFreenessLong() {
		return Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(orderType);
	}
	
	public boolean isFreenessForeign() {
		return Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(orderType);
	}

	public boolean isSelfhelpBus() {
		return Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(orderType);
	}

	public boolean isHotelForeign() {
		return Constant.PRODUCT_TYPE.HOTEL_FOREIGN.name().equals(orderType);
	}	
	public String getNeedContract() {
		return needContract;
	}

	public void setNeedContract(String needContract) {
		this.needContract = needContract;
	}

	/**
	 * 是否需要电子签约
	 * 
	 * @return
	 */
	public boolean isNeedEContract() {
		return Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name().equalsIgnoreCase(
				needContract);
	}

	/**
	 * 电子签约是否已被确认
	 * 
	 * @return
	 */
	public boolean isEContractConfirmed() {
		return Constant.ECONTRACT_STATUS.CONFIRM.name().equalsIgnoreCase(
				eContractStatus);
	}

	public OrdOrderRoute getOrderRoute() {
		return orderRoute;
	}

	public void setOrderRoute(OrdOrderRoute orderRoute) {
		this.orderRoute = orderRoute;
	}
	
	public String getEContractStatus() {
		return eContractStatus;
	}

	public void setEContractStatus(String eContractStatus) {
		this.eContractStatus = eContractStatus;

	}

	public String getZkDisplayOrdOrderItemProdName() {
		String name = "";
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			name += prod.getProductName() + "\r\n";
		}
		return name;
	}

	public String getZkDisplayOrdOrderItemProdQuantity() {
		String quantity = "";
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			quantity += prod.getQuantity() + "\r\n";
		}
		return quantity;
	}

	/**
	 * 取得签约产品, 如果订单中有成人，则取成人。如果没有则取其它不是儿童的，如果只有儿童，则取儿童。
	 * 
	 * @return
	 */
	public OrdOrderItemProd getContentProduct() {
		List<OrdOrderItemProd> contentList = new ArrayList<OrdOrderItemProd>();
		for (OrdOrderItemProd itemProd : this.ordOrderItemProds) {
			if (Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name().equals(
					itemProd.getNeedEContract())) {
				contentList.add(itemProd);
			}
		}
		OrdOrderItemProd itemProd = null;
		OrdOrderItemProd adultProd = null;
		OrdOrderItemProd childProd = null;
		for (OrdOrderItemProd item : contentList) {
			String productName = item.getProductName();
			if (productName.matches(".*[\\(（]成人.*[\\)）].*")) {
				adultProd = item;
				return adultProd;
			} else if (productName.matches(".*[\\(|（]儿童.*[\\)|）].*")) {
				childProd = item;
			} else {
				itemProd = item;
			}
		}
		if (null == adultProd && null == itemProd) {
			itemProd = childProd;
		}
		return itemProd;
	}
	public String getOrderMonitorUrl(){
		return ".../ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId="+orderId;
	}
	public String getIsCare() {
		return isCare;
	}
	public void setIsCare(String isCare) {
		this.isCare = isCare;
	}
	
	/**
	 * 最晚取消时间提示信息
	 */
	
	public String getLastCancelStr() {
		String cancelStr = "";
		Date lastCancelTime = getLastCancelTime();
		if (lastCancelTime != null) {
			if (isCancelAble()) {
				cancelStr = "您可在" + DateUtil.getFormatDate(lastCancelTime, "yyyy年MM月dd日 HH:mm") + "前致电驴妈妈客服中心（1010-6060）对订单进行修改或取消";
			} else {
				cancelStr = "该订单已超过最晚可修改或取消的时间，目前订单无法修改或取消，如有特殊情况请致电驴妈妈客服中心（1010-6060）";
			}
		}else if(isForbid()){
			cancelStr = "该订单不退不改";
		}
		return cancelStr;
	}
	
	/**
	 * 订单能否可以取消,由产品项的最晚取消时间来判断,取最晚时间当中最早的一个来判断
	 * @return
	 */
	public boolean isCancelAble(){		
		boolean cancel_flag=true;
		if(isPaymentSucc()){//支付成功后才使用下面的操作
			if(isAble()) {//可退改才使用下面操作
				Date date=getLastCancelTime();
				if(date!=null){
					//如果大于现在的时间代表还是可以废单
					cancel_flag=date.after(new Date());
				}
			} else if(isForbid()) {//不退改不能取消
				return false;
			}
		}
		return cancel_flag;
	}
	
	/**
	 * 取最晚时间
	 * @return 如果不存在返回空值
	 */
	public Date getLastCancelTime(){
		if(isAble()){
			return lastCancelTime;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 构造订单最晚取消小时数
	 */
	public void makeLastCancelTime(){
		if(!isAble()) {
			lastCancelTime = null;
			return;
		}
		List<Date> dates=new ArrayList<Date>();
		//先把最晚取消时间取出来，如果为空就不取
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			if (prod.isAdditionalProduct()) {// 只支持非附加产品
				continue;
			}
			Date date = prod.getLastCancelTime();
			if (date != null) {
				dates.add(date);
			}
		}
		if(CollectionUtils.isNotEmpty(dates)){
			lastCancelTime = Collections.min(dates);
		}else{
			lastCancelTime = null;
		}
    }
	
	/**
	 * 订单是否有最晚取消时间
	 * 有为true
	 * 无为false
	 * @return
	 */
	public boolean isHasLastCancelTime(){
		return getLastCancelTime()!=null;
	}
	
	
	public String getZhVisaStatus(){
		if(StringUtils.isEmpty(getVisaStatus())){
			return "";
		}
		return Constant.VISA_STATUS.getCnName(getVisaStatus());
	}
	
	
	/**
	 * 是否可以修改人数
	 * 条件：单一销售产品，单一采购产品，单一人类型，支付给供应商，库存充足
	 * @return
	 */
	public boolean isChangeAmount(){
		boolean flag = false;
		if(this.ordOrderItemProds.size()==1 
				&& allOrdOrderItemMetas.size()==1 
				&& allOrdOrderItemMetas.get(0).getProductQuantity().intValue()==1
				&& (allOrdOrderItemMetas.get(0).getAdultQuantity().intValue()==0 || allOrdOrderItemMetas.get(0).getChildQuantity().intValue()==0) 
				&& !this.isPayToLvmama()
				&&!allOrdOrderItemMetas.get(0).isHaveStockReduced()
				){
			flag=true;
		}
		logger.info("isChangeAmount:"+flag);
		return flag;
	}
		
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the filialeName
	 */
	public String getFilialeName() {
		return filialeName;
	}
	/**
	 * @param filialeName the filialeName to set
	 */
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	public Date getLatestUseTime() {
		return latestUseTime;
	}
	public void setLatestUseTime(Date latestUseTime) {
		this.latestUseTime = latestUseTime;
	}
	
	public String getZhFilialeName(){
		return Constant.FILIALE_NAME.getCnName(filialeName);
	}
	
	public String getResourceLackReason() {
		return resourceLackReason;
	}
	public void setResourceLackReason(String resourceLackReason) {
		this.resourceLackReason = resourceLackReason;
	}
	public String getResourceLackReasonStr(){
		if (this.resourceLackReason.equalsIgnoreCase(Constant.ORDER_RESOURCE_LACK_REASON.NO_RESOURCE.name())) {
			return "没有资源";
		}else if (this.resourceLackReason.equalsIgnoreCase(Constant.ORDER_RESOURCE_LACK_REASON.PRICE_CHANGE.name())) {
			return "价格更改";
		}else if (this.resourceLackReason.equalsIgnoreCase(Constant.ORDER_RESOURCE_LACK_REASON.UNABLE_MEET_REQUIREMENTS.name())) {	
			return "游客要求无法满足";
		}
		return this.resourceLackReason;
	}
	public String getSelfPack() {
		return selfPack;
	}
	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}
	
	public boolean hasSelfPack(){
		return StringUtils.equals("true", selfPack);
	}
	
	/**
	 * 获取紧急联系人
	 * @return 紧急联系人
	 */
	public OrdPerson getEmergencyContact() {
		if (this.emergencyContact == null && this.personList != null) {
			for (int i = 0; i < this.personList.size(); i++) {
				OrdPerson person = this.personList.get(i);
				if (Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT .name().equals(
						person.getPersonType())) {
					this.emergencyContact = person;
				}
			}
		}
		return this.emergencyContact;
	}
	
	public OrdPerson getFristTravellerOrdPerson(){
		return this.travellerList.get(0);
	}
	public boolean isNeedSendFax() {
		boolean flag = false;
		if (allOrdOrderItemMetas != null) {
			for (OrdOrderItemMeta ordOrderItemMeta : allOrdOrderItemMetas) {
				if (ordOrderItemMeta.isNeedSendFax()) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	/**
	  * 订单待付金额.
	  * @return 需要支付的金额
	  * <p>The unpay amount equals the order's ought pay subtract the order's actual pay. if unpay amount less than zero, it will return zero.</p>
	  */
	 public String getUnpayAmountFloat(){
	  String unPay = "";
	  if ("NORMAL".equalsIgnoreCase(orderStatus) && oughtPay != null && actualPay != null) {
	   unPay =  (oughtPay-actualPay) < 0 ? "0" : String.valueOf(PriceUtil.convertToYuan(oughtPay-actualPay));
	  }
	  return unPay;
	 }
	public boolean isUpdateInCurrent() {
		return updateInCurrent;
	}
	public void setUpdateInCurrent(boolean updateInCurrent) {
		this.updateInCurrent = updateInCurrent;
	}

	public String getNeedPrePay() {
		return needPrePay;
	}
	public void setNeedPrePay(String needPrePay) {
		this.needPrePay = needPrePay;
	}
	
	public String getIsAperiodic() {
		return isAperiodic;
	}
	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	
	/**
	 * 需要有订单子项的数据
	 * 计算订单的提前预订时间值
	 * @return
	 */
	private Date getOrderAheadDate(){
		List<Date> dates = new ArrayList<Date>();
		for(OrdOrderItemProd itemProd:getOrdOrderItemProds()){
			if(itemProd.getAheadHour()!=null){
				Date date = DateUtils.addMinutes(itemProd.getVisitTime(),-itemProd.getAheadHour().intValue());
				dates.add(date);
			}			
		}
		if(CollectionUtils.isNotEmpty(dates)){
			return Collections.min(dates);
		}else{
			return null;
		}
	}
	
	public void makeOrderNeedPrePay(){
		Date date = getNeedPrePayBeginTime();
		Date createTime = new Date();		
		if (date != null && date.before(createTime)) {// 如果最晚废单时间减去一小时后早于当前的下单时间，订单必须走预授权才可以
			//计算出提前预订时间
			aheadTime = getOrderAheadDate();			
			//qjh
			if(isNeedResourceConfirm()){ 
				needPrePay = "true";
			} else {
				needPrePay = "normalPay";
			}
		}
	}
	
	public boolean hasNeedPrePay(){
		//qjh
		return StringUtils.equalsIgnoreCase(needPrePay, "true") || StringUtils.equalsIgnoreCase(needPrePay, "normalPay");
	}
	public Date getAheadTime() {
		return aheadTime;
	}
	public void setAheadTime(Date aheadTime) {
		this.aheadTime = aheadTime;
	}
		
	public Long getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(Long refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	/**
	 * 订单已经成功退款的金额。单位:元
	 * @return 成功退款的金额，单元为元
	 */
	public float getRefundedAmountYuan() {
		 return PriceUtil.convertToYuan(refundedAmount);
	}
	
	/**
	 * 是否已经存在成功的退款
	 * @return 
	 */
    public boolean isRefundExists() {
    	return 0L != refundedAmount.longValue();
    }
    
    /**
     * 判断一个订单能否做审核
     * @return
     */
    public boolean hasApproveAble(){
    	return !hasNeedPrePay()||isPaymentSucc();
    }
    public boolean getZkApproveAble(){
    	return !hasApproveAble();
    }
    /**
     * 是否分销单
     * @return
     */
    public boolean hasDistribution(){
     return channel.startsWith("DISTRIBUTION");
    }
    /**
     * 是否可以合并支付
     * @author ZHANG Nan
     * @return
     */
    public boolean canMergePay(){
    	//订单正常的、资源审核通过的、未支付的、未过期的、支付给驴妈妈的 、没有限制支付网关的  可以进行合并支付
    	return isNormal() && isApprovePass() && isUnpay() && !isExpireToPay() && isPayToLvmama() && !isPaymentChannelLimit(); 
    }
    
    /**
     * 判断是否是当天预订
     * @return
     */
    public boolean hasTodayOrder(){
    	return StringUtils.equals("true", todayOrder);
    }
    /**
     * 获取奖金支付金额（单位：分）
     * @return
     */
	public Long getBonusPaidAmount() {
		if(bonusPaidAmount ==null) {
			return 0L;
		}
		return bonusPaidAmount;
	}
	/**
	 * 设置奖金支付金额（单位：分）
	 * @param bonusPaidAmount 奖金支付金额（单位：分）
	 */
	public void setBonusPaidAmount(Long bonusPaidAmount) {
		this.bonusPaidAmount = bonusPaidAmount;
	}
    
	/**
	 * 获取奖金支付金额(单位:元)
	 * @return 
	 */
    public float getBonusPaidAmountYuan(){
    	return PriceUtil.convertToYuan(this.getBonusPaidAmount());
    }
	/**
     * 获取减去奖金支付金额后的实付金额
     * @return
     */
	public Long getActualPayExcludeBonusPaidAmount() {
		if (this.actualPay - this.bonusPaidAmount <= 0) {
			return 0L;
		} else {
			return this.actualPay - this.bonusPaidAmount;
		}
	}
	/**
	 * 获取减去奖金支付金额后的实付金额，单位：元
	 * @return
	 */
	public float getActualPayExcludeBonusPaidAmountYuan(){
		return PriceUtil.convertToYuan(getActualPayExcludeBonusPaidAmount());
	}
    
    /**
     * 取订单当中保险的总金额
     * @return
     */
    public float getInsuranceTotalAmount(){
    	long amount=0;
    	for(OrdOrderItemProd itemProd:getOrdOrderItemProds()){
    		if(itemProd.isOtherProductType()&&Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(itemProd.getSubProductType())){
    			amount+=itemProd.getPrice()*itemProd.getQuantity();
    		}
    	}
    	return PriceUtil.convertToYuan(amount);
    }
    
    /**
     * 取订单当中保险的总金额
     * @return
     */
    public String getInsuranceProdNames(){
    	StringBuffer sb=new StringBuffer();
    	for(OrdOrderItemProd itemProd:getOrdOrderItemProds()){
    		if(itemProd.isOtherProductType()&&Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(itemProd.getSubProductType())){
    			sb.append(itemProd.getProductName());
    		}
    	}
    	return sb.toString().trim();
    }
    
    /**
     * 该功能只在报表导出当中使用
     * @return
     */
    public String getItemProdNames(){
    	StringBuffer sb=new StringBuffer();
    	for(OrdOrderItemProd itemProd:getOrdOrderItemProds()){
			sb.append(itemProd.getProductName());
			sb.append(" ");
    	}
    	return sb.toString().trim();
    }
    
    public String getLastWaitPaymentTimeOfString() {
    	Date date = getWaitPaymentDate();
    	if (date!=null) {
    		return DateUtil.formatDate(date, "yyyy年MM月dd日 HH时mm分ss秒");
    	}
    	return "";
    }
    public String getLastWaitPayMentTimeOfMinus(){
    	Date date = getWaitPaymentDate();
    	if(date!=null){
    		Date nowTime = new Date();
    		long minusValue = date.getTime() - nowTime.getTime();
    		if(minusValue <= 0){
    			minusValue = 0;
    		}
    		return minusValue+"";
    	}
    	return "";
    }
    /**
     * 该功能只在报表导出当中使用
     * @return
     */
    public String getItemProdSubProductTypes(){
    	StringBuffer sb=new StringBuffer();
    	for(OrdOrderItemProd itemProd:getOrdOrderItemProds()){
			sb.append(Constant.SUB_PRODUCT_TYPE.getCnName(itemProd.getSubProductType()));
			sb.append(" ");
    	}
    	return sb.toString().trim();
    }
	public String getInfoApproveStatus() {
		return infoApproveStatus;
	}
	public void setInfoApproveStatus(String infoApproveStatus) {
		this.infoApproveStatus = infoApproveStatus;
	} 
	
	// qjh
	// 判断是否存在港捷旅订房集团中心的供应商
	public boolean hasGangjie() {
		boolean flg = false;
		if (this.getOrdOrderItemProds() != null
				&& this.getOrdOrderItemProds().size() > 0) {
			for (OrdOrderItemProd ordOrderItemProd : this
					.getOrdOrderItemProds()) {
				if (ordOrderItemProd != null
						&& ordOrderItemProd.getOrdOrderItemMetas() != null
						&& ordOrderItemProd.getOrdOrderItemMetas().size() > 0) {
					for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd
							.getOrdOrderItemMetas()) {
						if (ordOrderItemMeta.getSupplierId().intValue() == Constant.GANGJIECENTER_ID) {
							flg = true;
						}
					}
				}
			}
		}
		return flg;

	}
	
	public Date getPaymentFinishTime() {
		return paymentFinishTime;
	}
	public void setPaymentFinishTime(Date paymentFinishTime) {
		this.paymentFinishTime = paymentFinishTime;
	}
	
	public String getZhIsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?"不定期":"";
	}
	
	public boolean IsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?true:false;
	}
	public Date getValidBeginTime() {
		return validBeginTime;
	}
	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}
	public Date getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	public String getTestOrderFlag() {
		return testOrderFlag;
	}
	public void setTestOrderFlag(String testOrderFlag) {
		this.testOrderFlag = testOrderFlag;
	}
	public List<OrdOrderTraffic> getOrderTrafficList() {
		return orderTrafficList;
	}
	public void setOrderTrafficList(List<OrdOrderTraffic> orderTrafficList) {
		this.orderTrafficList = orderTrafficList;
	}
	
	public boolean isPaymentToSupplierCancelAble(){
		if(isPayToSupplier()){
			if(lastCancelTime!=null){
				if(new Date().after(lastCancelTime)){
					return false;
				}
			}else if(new Date().after(visitTime)){
				return false;
			}
		}
		return true;
	}
	public Long getSumCardAmount() {
		return sumCardAmount;
	}
	public void setSumCardAmount(Long sumCardAmount) {
		this.sumCardAmount = sumCardAmount;
	}
	public float getSumCardAmountYuan() {
		return PriceUtil.convertToYuan(sumCardAmount);
	}
	
	public String getCanCreatInvoice() {
		if(Constant.ORDER_TYPE.TRAIN.getCode().equals(this.orderType) ||
				Constant.ORDER_TYPE.TICKET.getCode().equals(this.orderType)||
					Constant.ORDER_TYPE.OTHER.getCode().equals(this.orderType)){
			return "false";
		}
		if(this.visitTime==null){
			return "false";
		}
		//游玩日期当天24点以后才可以开发票
		if(!DateUtils.addDays(this.visitTime, 1).before(new Date())){
			return "false";
		}
		//游玩日期90天24点后不可以开发票
		if(DateUtils.addDays(this.visitTime, 91).before(new Date())){
			return "false";
		}
		if(this.getInvoiceList()!=null && !this.getInvoiceList().isEmpty()){
			return "false";
		}
		if(StringUtils.equals("true", this.getNeedInvoice())){
			return "false";
		}
		if(StringUtils.equals("part", this.getNeedInvoice())){
			return "false";
		}
		if(Constant.ORDER_STATUS.CANCEL.getCode().equals(this.getOrderStatus())){
			return "false";
		}
		return canCreatInvoice==null?"true":canCreatInvoice;
	}
	public void setCanCreatInvoice(String canCreatInvoice) {
		this.canCreatInvoice = canCreatInvoice;
	}

	public String getPrePaymentAble() {
		return prePaymentAble;
	}
	public void setPrePaymentAble(String prePaymentAble) {
		this.prePaymentAble = prePaymentAble;
	}
	public String getSupplierChannel() {
		return supplierChannel;
	}
	public void setSupplierChannel(String supplierChannel) {
		this.supplierChannel = supplierChannel;
	}
	public boolean getIsJinjiangOrder(){
		return Constant.SUPPLIER_CHANNEL.JINJIANG.getCode().equalsIgnoreCase(this.getSupplierChannel());
	}
	public boolean getIsShHolidayOrder(){
		return Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.getCode().equalsIgnoreCase(this.getSupplierChannel());
	}
	public boolean getHasSupplierChannelOrder(){
		if(StringUtils.isNotEmpty(supplierChannel)&&!Constant.SUPPLIER_CHANNEL.EBK.getCode().equals(supplierChannel)){
			return true;
		}
		if(Constant.PAYMENT_GATEWAY_DIST_MANUAL.DISTRIBUTOR_B2B
						.name().equalsIgnoreCase(this.getChannel())){
			return true;
		}
		return false;
	}
	
	public String getZhSupplierChannelOrder(){
		return Constant.SUPPLIER_CHANNEL.getCnName(supplierChannel);
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public OrdOrder setProp(VstOrdOrderVo vstOrdOrder, UserUser userUser, long refundedAmount) {
      	if(vstOrdOrder!=null){
      		if(userUser!=null){
      			this.setUserId(userUser.getUserId());
      			this.setRealName(userUser.getRealName());
      			this.setGender(userUser.getGender());
      			this.setUserName(userUser.getUserName());
      			this.setMobileNumber(userUser.getMobileNumber());
      			this.setUser(userUser);
      		}
      		this.setVisitTime(vstOrdOrder.getVisitTime());
      		this.setOrderId(vstOrdOrder.getOrderId());
  			this.setCreateTime(vstOrdOrder.getCreateTime());
  			this.setUserId(vstOrdOrder.getUserId());
  			this.setOrderStatus(vstOrdOrder.getOrderStatus());
  			this.setPaymentStatus(vstOrdOrder.getPaymentStatus());
  			this.setPaymentTime(vstOrdOrder.getPaymentTime());
  			this.setActualPay(vstOrdOrder.getActualAmount());
  			this.setOughtPay(vstOrdOrder.getOughtAmount());
  			//新系统没有approveStatus字段，resourceStatus与其对应 TODO
  			if("AMPLE".equalsIgnoreCase(vstOrdOrder.getResourceStatus())) {	//资源满足
  				this.setApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
  			}else if("LOCK".equalsIgnoreCase(vstOrdOrder.getResourceStatus())) {	//资源不满足
  				this.setApproveStatus(Constant.ORDER_APPROVE_STATUS.RESOURCEFAIL.name());
  			}else if("UNVERIFIED".equalsIgnoreCase(vstOrdOrder.getResourceStatus())) {	//未审核
  				this.setApproveStatus(Constant.ORDER_APPROVE_STATUS.UNVERIFIED.name());
  			}
  			this.setRefundedAmount(refundedAmount);
  			
  			//this.setOrderType(vstOrdOrder.get);
  			//支付等待时间
  			if(vstOrdOrder.getWaitPaymentTime()!=null ) {
  	  			Calendar calendar = Calendar.getInstance();
  	  			calendar.setTime(vstOrdOrder.getWaitPaymentTime());	
  	  			int waitPaymentTimeMinute = calendar.get(Calendar.MINUTE);
  	  			this.setWaitPayment(waitPaymentTimeMinute+0L);
  			}
  			this.setLastCancelTime(vstOrdOrder.getLastCancelTime());
  			this.setApproveTime(vstOrdOrder.getApproveTime());
  			//订单来源渠道
//  			System.out.println("ordorder == vstOrdOrder.getDistributorName()="+vstOrdOrder.getDistributorName());
//  			this.setChannel(vstOrdOrder.getDistributorName()); 
  			this.setUserMemo(vstOrdOrder.getRemark());
  			this.setBonusPaidAmount(0L);	//TODO heyuxing
  			//
  			List<VstOrdOrderItem> vstOrdOrderItems = vstOrdOrder.getVstOrdOrderItems();
  			if(vstOrdOrderItems!=null) {
  				for(VstOrdOrderItem vstOrdOrderItem : vstOrdOrderItems) {
  					OrdOrderItemMeta ordOrderItemMeta = new OrdOrderItemMeta();
  					ordOrderItemMeta.setMetaProductId(vstOrdOrderItem.getProductId());
  					ordOrderItemMeta.setOrderItemMetaId(vstOrdOrderItem.getOrderItemId());
  					ordOrderItemMeta.setProductName(vstOrdOrderItem.getProductName());
  					ordOrderItemMeta.setSettlementStatusStr(Constant.SETTLEMENT_STATUS.getCnName(vstOrdOrderItem.getSettlementStatus()));
  					ordOrderItemMeta.setActualSettlementPrice(vstOrdOrderItem.getActualSettlementPrice());
  					ordOrderItemMeta.setProductQuantity(1L);	//新系统没有打包概念，默认为1
  					ordOrderItemMeta.setQuantity(vstOrdOrderItem.getQuantity());
  					//TODO check 
  					if(vstOrdOrderItem.getAmountType()!=null && vstOrdOrderItem.getAmountType().equals(Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.name())){
  	  					ordOrderItemMeta.setAmountType(Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.name());	
  					}else {
  	  					ordOrderItemMeta.setAmountType(Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.name());
  					}
  					ordOrderItemMeta.setActualLoss(vstOrdOrderItem.getActualLoss());
  					if(refundedAmount>0) {
  						ordOrderItemMeta.setAmountValueYuan(PriceUtil.convertToYuan(refundedAmount)+"");	// 退款明细中的金额
  					}else {
  	  					if(vstOrdOrderItem.getAmountValue()!=null) {
  	  	  					ordOrderItemMeta.setAmountValueYuan(PriceUtil.convertToYuan(vstOrdOrderItem.getAmountValue())+"");	// 退款明细中的金额
  	  					}else {
  	  						ordOrderItemMeta.setAmountValueYuan(PriceUtil.convertToYuan(0L)+"");
  	  					}
  					}
  					this.allOrdOrderItemMetas.add(ordOrderItemMeta);
  				}
  			}
      	}
      	
		return this;
	}
	public Long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

    public String getCancelReorderReason() {
        return cancelReorderReason;
    }

    public void setCancelReorderReason(String cancelReorderReason) {
        this.cancelReorderReason = cancelReorderReason;
    }
}