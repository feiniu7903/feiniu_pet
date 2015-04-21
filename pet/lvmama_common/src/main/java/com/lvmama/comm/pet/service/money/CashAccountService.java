/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.money;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.MoneyAccountChangeLogRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.view.PaginationVO;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashBonusReturn;
import com.lvmama.comm.pet.po.money.CashDraw;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.po.money.CashPay;
import com.lvmama.comm.pet.po.money.CashRecharge;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pub.ComBank;
import com.lvmama.comm.vo.CashAccountChangeLogVO;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * CashAccount 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface CashAccountService{
	/**
	 * 持久化对象
	 * @param cashAccount
	 * @return
	 */
	public Long insert(CashAccount cashAccount);
	
	/**
	 * 获取财务相关银行列表.
	 * 
	 * @return 财务相关银行列表
	 */
	public List<ComBank> getComBankList();

	
	/**
	 * 申请提现到银行账户.
	 *
	 * @param userId
	 *            用户ID
	 * @param bankName
	 *            银行名称
	 * @param bankAccount
	 *            银行账号
	 * @param bankAccountName
	 *            银行账户姓名
	 * @param kaiHuHang
	 *            开户行
	 * @param province
	 *            省
	 * @param city
	 *            市
	 * @param amount
	 *            提现金额，以分为单位
	 * @param isCompensation
	 *            是否为补偿款
	 * @param flag
	 *            对公对私标示 1:对公2:对私
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表申请提现到银行账户成功，<code>false</code> 代表申请提现到银行账户失败
	 */
	boolean applyDraw2Bank(Long userId, String bankName, String bankAccount,
			String bankAccountName, String kaiHuHang, String province,
			String city, Long amount, boolean isCompensation,String flag, String operatorName,String isSuperback);
	
	/**
	 * 申请提现到支付宝账户.
	 * 
	 * @param userId
	 *            用户ID
	 * @param aliPayAccount
	 *            支付宝账号
	 * @param aliPayAccountName
	 *            支付宝账户姓名
	 * @param amount
	 *            提现金额，以分为单位
	 * @param isCompensation
	 *            是否为补偿款
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表申请提现到支付宝账户成功，<code>false</code>代表申请提现到支付宝账户失败
	 */
	public boolean applyDraw2AliPay(final Long userId,
			final String aliPayAccount, final String aliPayAccountName,
			final Long amount, final boolean isCompensation,
			final String operatorName,String isSuperback);
	
	/**
	 * 更新提现单审核状态.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * @param auditStatus
	 *            审核状态（拒绝/通过）
	 * @param memo
	 *            审核原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表更新提现单审核状态成功，<code>false</code> 代表更新提现单审核状态失败
	 */
	public boolean updateCashMoneyDrawAuditStatus(final Long moneyDrawId,
			final String auditStatus, final String memo,
			final String operatorName);
	
	/**
	 * 查询提现记录计数.
	 * 
	 * @param compositeQuery
	 *            综合查询条件
	 * @return 查询提现记录计数
	 */
	public Long queryMoneyDrawCount(final CompositeQuery compositeQuery);
	
	/**
	 * 查询提现记录.
	 * 
	 * @param compositeQuery
	 *            综合查询条件
	 * @return 提现记录列表
	 */
	public List<CashMoneyDraw> queryMoneyDraw(final CompositeQuery compositeQuery);
	
	/**
	 * 查询现金账户.
	 * 
	 * @param userId
	 *            用户ID
	 * @return 现金账户
	 */
	public CashAccountVO queryMoneyAccountByUserId(final Long userId);
	
	/**
	 * 查询现金账户.
	 * 
	 * @param userNo
	 *            用户ID
	 * @return 现金账户
	 */
	public CashAccountVO queryMoneyAccountByUserNo(final String userNo);
	
	/**
	 * 查询现金账户
	 * @param id
	 * @return
	 */
	public CashAccount queryCashAccountByPk(final Long id);
	
	/**
	 * 查询现金账户
	 * @param id
	 * @return
	 */
	public CashAccount queryCashAccountByUserId(final Long id);
	/**
	 * 查询现金账户，如果不存在则创建账户
	 * @param id
	 * @return
	 */
	public CashAccount queryOrCreateCashAccountByUserId(final Long id);
	
	/**
	 * 创建现金账户，仅为新注册用户使用
	 * @param id
	 * @return
	 */
	public Long createCashAccountByUserId(Long id);

	/**
	 * 查询现金账户
	 * @param userNo
	 * @return
	 */
	public CashAccount queryCashAccountByUserNo(final String userNo);
	
	/**
	 * 对账.
	 * 
	 * <pre>
	 * 所有现金账户总收入 - 所有现金账户总支出
	 * 不为0时抛出运行期异常
	 * </pre>
	 * 
	 * @return <pre>
	 * 0L代表平衡
	 * </pre>
	 */
	public Long balance();
	
	/**
	 * 查询现金账户变更日志计数.
	 * 
	 * <pre>
	 * 使用{@link MoneyAccountChangeLogRelate}和{@link PageIndex}
	 * </pre>
	 * 
	 * @see #queryMoneyAccountChangeLog(CompositeQuery)
	 * @param compositeQuery
	 *            综合查询条件
	 * @return 现金账户变更日志计数
	 */
	public Long queryMoneyAccountChangeLogCount(
			final CompositeQuery compositeQuery);
	
	/**
	 * 查询现金账户变更日志.
	 * 
	 * <pre>
	 * 使用{@link MoneyAccountChangeLogRelate}和{@link PageIndex}
	 * </pre>
	 * 
	 * @see #queryMoneyAccountChangeLogCount(CompositeQuery)
	 * @param compositeQuery
	 *            综合查询条件
	 * @return 现金账户变更日志列表
	 */
	public List<CashAccountChangeLogVO> queryMoneyAccountChangeLog(
			final CompositeQuery compositeQuery);
	
	/**
	 * 分页查询打款历史
	 * @param paginationVO 分页查询对象
	 * @return
	 */
	public PaginationVO<Map<String, Object>> queryMoneyDrawHistory(PaginationVO<Map<String, Object>> paginationVO);
	
	/**
	 * 分页查询打款任务记录
	 * @param compositeQuery
	 * @return
	 */
	PaginationVO<CashMoneyDraw> queryFincMoneyDraw(PaginationVO<CashMoneyDraw> paginationVO);
	
	/**
	 * 提现单已处理完毕.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * @param memo
	 *            原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表提现单已处理完毕成功，<code>false</code> 代表提现单已处理完毕失败
	 */
	public boolean setDoneToFincMoneyDrawPayStatus(final Long moneyDrawId,
			final String memo, final String operatorName);
	
	/**
	 * 拒绝提现单打款.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * @param memo
	 *            原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表拒绝提现单打款成功，<code>false</code> 代表拒绝提现单打款失败
	 */
	public boolean rejectedFincMoneyDrawPayStatus(final Long moneyDrawId,
			final String memo, final String operatorName);
	
	/**
	 * 检查手机号码是否绑定.
	 * 
	 * @param userId
	 *            用户ID
	 * @return true代表手机号码已经绑定，false代表手机号码未绑定
	 */
	public boolean checkMobileNumber(final Long userId);
	
	/**
	 * 
	 * @param userId
	 * 	 用户ID
	 * @param orderId
	 * @param refundmentAmount
	 * @param payRefundmentId
	 * @return
	 */
	BankReturnInfo refund2CashAccount(RefundmentToBankInfo info);
	
	/**
	 * 首次设置存款账户支付密码/或存款账户忘记支付密码重新设置.
	 * @param userId 用户ID
	 * @param inputPassword 用户输入的支付密码
	 * @return <code>true</code> 代表设置成功,<code>false</code>代表设置失败
	 */
	boolean initPaymentPassword(Long userId, String inputPassword);
	
	/**
	 * 更改存款账户支付密码.
	 * @param userId 用户ID
	 * @param oldInputPassword 用户输入的旧支付密码
	 * @param newInputPassword 用户输入的新支付密码
	 * @return <code>true</code> 代表更改成功,<code>false</code>代表更改失败
	 */
	boolean changePaymentPassword(Long userId, String oldInputPassword, String newInputPassword);
	
	/**
	 * 校验存款账户支付秘密.
	 * @param userId 用户ID
	 * @param password 支付密码
	 * @return <code>true</code> 为校验成功 </br>
	 * 		   <code>false</code> 为校验失败</br>
	 */
	boolean validateMoneyAccountPaymentPassword(Long userId, String password);
	
	/**
	 * 接收到打款应答后更新打款单、新建现金账户打款记录、现金账户支付记录.
	 *
	 * @param cashMoneyDraw 打款单
	 *
	 * @param cashDraw 现金账户打款记录
	 *
	 * @return 操作是否成功
	 */
	public boolean withDrawMoney(CashMoneyDraw cashMoneyDraw, CashDraw cashDraw);
	
	/**
	 * 更新充值任务.
	 *
	 * @param cashRecharge
	 *            充值任务
	 *
	 * @return <code>true</code>代表更新充值任务成功，<code>false</code>代表更新充值任务失败
	 */
	boolean updateCashRecharge(CashRecharge cashRecharge);
	
	Long insertCashRecharge(CashRecharge cashRecharge);

	public boolean rechargeToCashAccount(Long cashRechargeId,Long reChargeAmount);
	
	/** TODO
	 * 支付成功后的回调接口.
	 *
	 * @param payment
	 *            新生成的payment对象，用于封装传递OrdPayment中的各个成员变量
	 * @return <code>true</code>代表回调成功，<code>false</code>代表回调失败
	 */
	boolean successPaymentCallback(PayPayment payment);

	/**
	 * 通过支付网关交易号serial查询充值任务.
	 *
	 * @param serial
	 *            支付网关交易号
	 *
	 * @return CashRecharge 充值任务
	 */
	CashRecharge findCashRechargeBySerial(String serial);
	
	public CashRecharge findCashRechargeById(Long cashRechargeId);

	
	/**
	 * 0元支付
	 * @param orderId
	 * @return
	 */
	boolean pay0Yuan(Long orderId);

	/**
	 * 绑定手机号码.
	 * 
	 * @param userId
	 *            用户ID
	 * @param mobileNumber
	 *            手机号码
	 * @param updateFlag
	 *            true代表强制更新已经存在的手机号码，false代表不强制更新已经存在的手机号码
	 * @return true代表绑定成功，false代表绑定失败
	 */
	boolean bindMobileNumber(Long userId, String mobileNumber,
			boolean updateFlag);
	
	
	/**
	 * 新建打款单.
	 *
	 * @param cashMoneyDraw 打款单
	 * 
	 * @return 打款单ID
	 */
	Long insertCashMoneyDraw(CashMoneyDraw cashMoneyDraw);
	
	/**
	 * 查询打款单.
	 *
	 * @param moneyDrawId 打款单ID
	 * 
	 * @return 打款单
	 */
	CashMoneyDraw queryCashMoneyDraw(Long moneyDrawId);
	
	/**
	 * 更新打款任务.
	 *
	 * @param cashDraw 打款任务
	 * 
	 * @return 更新打款任务是否成功
	 */
	boolean updateCashDrawByPrimaryKey(CashDraw cashDraw);
	
	/**
	 * 根据alipay2bankFile查询{@link fincCashDraw}.
	 *
	 * @param alipay2bankFile 上传给支付宝的打款文件名
	 * 
	 * @return {@link cashDraw}
	 */
	CashDraw findCashDrawByAlipay2bankFile(String alipay2bankFile);
	
	/**
	 * 根据流水号查询{@link cashDraw}.
	 *
	 * @param serial 流水号
	 * 
	 * @return {@link cashDraw}
	 */
	CashDraw findCashDrawBySerial(String serial);
	
	/**
	 * 接收到打款回调后新建交易记录、更新打款单、现金账户打款记录、现金账户支付记录.
	 * @param serialNo
	 * @param tradeNo
	 * @param isSuccess
	 * @param payStatus
	 * @param memo
	 * @return
	 */
	boolean callbackForDrawMoneyHandle(String serialNo,String tradeNo,boolean isSuccess,String payStatus, String memo);

	/**
	 * 根据提现单ID查询{@link cashDraw}.
	 * 
	 * @param moneyDrawId
	 *            提现单ID
	 * 
	 * @return {@link cashDraw}
	 */
	CashDraw findCashDrawByMoneyDrawId(Long moneyDrawId);
	
	/**
	 * /**
	 * 支付.
	 * <pre>
	 * 先使用现金账户充值余额支付，现金账户充值余额不足时再使用现金账户订单退款余额支付
	 * 支付金额不足时抛出运行期异常
	 * @param orderId
	 * @param userId
	 * @param payAmount
	 * @param serial
	 * @return 
	 */
	public CashPay payFromMoneyAccount(Long orderId,Long userId,Long payAmount,String serial);
	
	/**
	 * 使用现金账户支付，优先使用奖金余额，奖金余额不足时再使用充值余额，充值余额不足时再使用订单退款余额支付
	 * @param userId 用户ID
	 * @param orderId 订单ID
	 * @param payAmount 支付金额
	 * @param bonusPay 奖金可支付金额(目前只有网站前台支付可以使用奖金，电话支付不可以使用奖金，传入0即可)
	 * @param bizType 业务系统
	 * @return 支付记录ID集合
	 */
	public List<Long> payFromCashAccount(Long userId,Long orderId,String bizType,Long payAmount,Long bonusPay);
	
	/**
	 * 使用奖金支付
	 * @author taiqichao
	 * @param userId 用户ID
	 * @param orderId 订单ID
	 * @param bizType 业务系统
	 * @param payAmount 支付金额(分)
	 * @return 支付记录ID
	 */
	public Long payFromBonus(Long userId,Long orderId,String bizType,Long payAmount);
	
	/**
	 * 使用奖金支付（新老奖金账户混合使用方式）
	 * @author taiqichao
	 * @param userId 用户ID
	 * @param orderId 订单ID
	 * @param bizType 业务系统
	 * @param oldPayAmount 老奖金账户支付金额(分)
	 * @param newPayAmount 新奖金账户支付金额(分)
	 * @return 支付记录ID集合(集合大小为2)
	 */
	public List<Long> payFromBonus(Long userId,Long orderId,String bizType,Long oldPayAmount,Long newPayAmount);
	
	/**
	 * 点评奖金返现
	 * @param orderAndComment
	 */
	public void returnBonusForOrderComment(OrderAndComment orderAndComment);
	
	/**
	 * PC端奖金红包返现
	 * @param returnAmount 返现金额(分)
	 * @param userId 用户id
	 * @param activityId 活动id(可以定义成常量，用于区分活动)
	 */
	public void returnBonusForPCActivity(Long returnAmount, final Long userId, String activityId);
	
	/**
	 * 分页查询奖金返现信息
	 * @param userId
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public List<CashBonusReturn> queryBonusReturn(final Long userId,final Integer beginIndex, final Integer endIndex);
	
	/**
	 * 获取奖金返现条数
	 * @param userId
	 * @return
	 */
	public Long getBonusReturnCount(final Long userId);

	/**
	 * 读取一个对象
	 * @param moneyDrawId
	 * @return
	 */
	CashMoneyDraw getFincMoneyDrawByPK(Long moneyDrawId);
	
	/**
	 * 更改CashMoneyDraw对象
	 * @param cashMoneyDraw
	 * @param status
	 * @param operatorName
	 */
	void updateCashMoneyDrawPayStatusByPK(CashMoneyDraw cashMoneyDraw,String status,String operatorName);
	
	/**
	 * 手动成功/失败
	 * @param manualHandleFlag
	 * @param cashMoneyDrawId
	 * @param userName
	 * @return
	 */
	public boolean manualHandle(boolean manualHandleFlag, Long cashMoneyDrawId,
			String userName);
	/**
	 * 根据businessId和comeFrom查询记录数.
	 * @param businessId
	 * @param comeFrom
	 * @return
	 */
	public Long selectProtectCount(String businessId,String comeFrom);
	
	/**
	 * 手动返现
	 * @param orderAndComment
	 */
	public void returnOrderManualAdjust(OrderAndComment orderAndComment);
	
	/**
	 * 提现银行卡账户名申请提现次数
	 * @param bankAccountName 提现银行卡账户名
	 * @return 申请提现次数
	 */
	public Long queryMoneyDrowCountByBankAccount(String bankAccountName);
	
	
	
	/**
	 * 更新用户最后支付校验时间
	 * @param userId
	 * @param lastPayValidateTime
	 */
	public void updateLastPayValidateTime(final Long userId,Date lastPayValidateTime);
	
	/**
	 * 冻结或解冻现金账户
	 * */
	public void changeCashAccountValidByParams(final Long userId, String valid, String memo, String operatorName);
	
	public boolean isMobileRegistrable(final String mobile);
	
	/**
	 * 移动端-艺龙酒店返现
	 * @param orderAndComment
	 */
	public void returnBonus4ElongOrder(MobileHotelOrder mobileHotelOrder,final Long userId);
	
	
	/**
	 * 移动端-活动返现
	 * @param returnAmount  返现金额 
	 * @param userNo  用户编号 
	 * @param businessId  唯一号,最大长度不能大于50 ，此号必须全局唯一 ，为了防止同一个业务返现多次 （比如 一个订单返现一次，订单号就是全局唯一的）。
	 */
	public boolean returnBonus4ClientActivity(Long returnAmount, final String userNo, String businessId);
	
	
	/**
	 * 判断用户是否可用使用奖金支付(如果用户在一年内使用奖金消费过30次，或一个月内使用奖金消费过10次，将无法使用奖金)
	 * @author taiqichao
	 * @param userId 用户id
	 * @return 是否可以使用奖金
	 */
	public boolean canUseBonusPay(Long userId);
	
	
}
