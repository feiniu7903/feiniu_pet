/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.vo.CashAccountVO;
/**
 * CashAccountDAO,持久层类 用于CashAccount 表的CRUD.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */
public class CashAccountDAO extends BaseIbatisDAO{	
	/**
	 * 持久化对象
	 * @return
	 */
	public Long insert(CashAccount cashAccount) {
		return (Long)super.insert("CASH_ACCOUNT.insert", cashAccount);
	}
	/**
	 * 查询现金账户.
	 *
	 * @param userId
	 *            用户ID
	 * @return 现金账户
	 */
	public CashAccountVO queryMoneyAccount(final Long userId) {
		CashAccountVO cashAccountVO = (CashAccountVO) super.queryForObject(
				"CASH_ACCOUNT.queryMoneyAccount", userId);
		 if(cashAccountVO==null){
			 cashAccountVO = new CashAccountVO(false);
		 }
		 return cashAccountVO;
	}
	
	/**
	 * 查询现金账户.
	 *
	 * @param userNo
	 *            用户No
	 * @return 现金账户
	 */
	public CashAccountVO queryMoneyAccount(final String userNo) {
		 CashAccountVO cashAccountVO =(CashAccountVO) super.queryForObject(
				"CASH_ACCOUNT.queryMoneyAccountByUserNo", userNo);
		 if(cashAccountVO==null){
			 cashAccountVO = new CashAccountVO();
		 }
		 return cashAccountVO;
	}
	/**
	 * 创建现金账户.
	 *
	 * @param userId
	 *            用户ID
	 */
	public Long createMoneyAccount(final Long userId) {
		CashAccount cashAccount=new CashAccount();
		cashAccount.setUserId(userId);
		return (Long) super.insert("CASH_ACCOUNT.createMoneyAccount",
				cashAccount);
	}

	/**
	 * 保护.
	 *
	 * <pre>
	 * 防止相同业务ID重复提交
	 * </pre>
	 *
	 * @param businessId
	 *            业务ID
	 * @param comeFrom
	 *            来源
	 */
	public void protect(final String businessId, final String comeFrom) {
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("businessId", businessId);
		map.put("comeFrom", comeFrom);
		super.insert("CASH_ACCOUNT.protect", map);
	}
	/**
	 * 根据businessId和comeFrom查询记录数.
	 * @param businessId
	 * @param comeFrom
	 * @return
	 */
	public Long selectProtectCount(final String businessId, final String comeFrom) {
		final Map<String, String> map = new Hashtable<String, String>();
		map.put("businessId", businessId);
		map.put("comeFrom", comeFrom);
		return (Long) super.queryForObject(
				"CASH_ACCOUNT.selectProtectCount", map);
	}
	
	/**
	 * 操作充值余额.
	 *
	 * @param userId
	 *            用户ID
	 * @param amount
	 *            正整数代表向充值余额里充值，负整数代表使用充值余额付款，以分为单位
	 */
	public void updateReChargeBalance(final Long cashAcountId, final Long amount) {
		final Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("cashAccountId", cashAcountId);
		map.put("amount", amount);
		super.insert(
				"CASH_ACCOUNT.updateReChargeBalance", map);
	}

	/**
	 * 锁定.
	 *
	 * <pre>
	 * 锁定该现金账户，避免多处同时修改现金账户余额
	 * </pre>
	 *
	 * @param userId
	 *            用户ID
	 */
	public void lock(final Long userId) {
		super.queryForObject("CASH_ACCOUNT.lock", userId);
	}

	/**
	 * 操作订单退款余额.
	 *
	 * @param userId
	 *            用户ID
	 * @param amount
	 *            正整数代表退款到订单退款余额，负整数代表使用订单退款余额付款，以分为单位
	 */
	public void updateRefundBalance(final Long cashAccoutId, final Long amount) {
		final Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("cashAcountId", cashAccoutId);
		map.put("amount", amount);
		super.insert("CASH_ACCOUNT.manipulateRefundBalance",
				map);
	}

	/**
	 * 操作账户奖金余额.
	 *
	 * @param cashAccoutId
	 *            用户ID
	 * @param amount
	 *            正整数代表向奖金余额里充值，负整数代表使用奖金余额，以分为单位
	 */
	public void updateBonusBalance(final Long cashAccoutId, final Long amount) {
		final Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("cashAcountId", cashAccoutId);
		map.put("amount", amount);
		super.insert("CASH_ACCOUNT.updateBonusBalance",map);
	}
	
	/**
	 * 操作新奖金余额.
	 *
	 * @param cashAccoutId
	 *            用户ID
	 * @param amount
	 *            正整数代表向奖金余额里充值，负整数代表使用奖金余额，以分为单位
	 */
	public void updateNewBonusBalance(final Long cashAccoutId, final Long amount) {
		final Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("cashAcountId", cashAccoutId);
		map.put("amount", amount);
		super.insert("CASH_ACCOUNT.updateNewBonusBalance",map);
	}
	

	/**
	 * 对账.
	 *
	 * <pre>
	 * 所有现金账户总收入 - 所有现金账户总支出
	 * </pre>
	 *
	 * @return <pre>
	 * 正整数代表总收入多于总支出的钱，0L代表平衡，负整数代表总支出多于总收入的钱，以分为单位
	 * </pre>
	 */
	public Long balance() {
		return (Long) super.queryForObject(
				"CASH_ACCOUNT.balance");
	}

	/**
	 * 更新手机号码.
	 * 
	 * @param userId
	 *            用户ID
	 * @param mobileNumber
	 *            手机号码
	 */
	public void updateMobileNumber(final Long userId,
			final String mobileNumber) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		map.put("userId", userId);
		map.put("mobileNumber", mobileNumber);
		super.update("CASH_ACCOUNT.updateMobileNumber", map);
	}
	/**
	 * 更新支付密码.
	 * @param userId 用户ID
	 * @param paymentPassword 支付密码
	 */
	public void updatePaymentPassword(final Long userId,final String paymentPassword) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		map.put("userId", userId);
		map.put("paymentPassword", paymentPassword);
		super.update("CASH_ACCOUNT.updatePaymentPassword", map);
	}
	
	
	/**
	 * 更新用户最后支付校验时间
	 * @param userId 用户ID
	 * @param lastPayValidateTime 最后更新时间
	 */
	public void updateLastPayValidateTime(final Long userId,final Date lastPayValidateTime) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		map.put("userId", userId);
		map.put("lastPayValidateTime", lastPayValidateTime);
		super.update("CASH_ACCOUNT.updateLastPayValidateTime", map);
	}
	
	/**
	 * 根据主键id查询
	 */
	public CashAccount getCashAccountById(Long id) {
		return (CashAccount)super.queryForObject("CASH_ACCOUNT.getCashAccountById", id);
	}
	/**
	 * 根据用户id查询
	 */
	public CashAccount getCashAccountByUserId(Long id) {
		return (CashAccount)super.queryForObject("CASH_ACCOUNT.getCashAccountByUserId", id);
	}
	/**
	 * 根据用户号查询
	 */
	public CashAccount getCashAccountByUserNo(String userNo) {
		return (CashAccount)super.queryForObject("CASH_ACCOUNT.getCashAccountByUserNo", userNo);
	}
	/**
	 * 根据条件查询
	 */
	@SuppressWarnings("unchecked")
	public List<CashAccount> queryCashAccountByParam(Map<String,Object> params) {
		return super.queryForList("CASH_ACCOUNT.queryCashAccountByParam", params);
	}
	
	/**
	 * 冻结现金账户
	 * */
	public void changeCashAccountValidByParams(Map<String, Object> map) {
		super.update("CASH_ACCOUNT.changeCashAccountValidByParams", map);
	}
}
