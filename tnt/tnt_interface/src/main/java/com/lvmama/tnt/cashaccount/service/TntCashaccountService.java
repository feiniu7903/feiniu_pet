package com.lvmama.tnt.cashaccount.service;

import java.util.List;

import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.po.TntCashCommission;
import com.lvmama.tnt.cashaccount.po.TntCashFreezeQueue;
import com.lvmama.tnt.cashaccount.po.TntCashMoneyDraw;
import com.lvmama.tnt.cashaccount.po.TntCashPay;
import com.lvmama.tnt.cashaccount.po.TntCashRecharge;
import com.lvmama.tnt.cashaccount.po.TntCashRefundment;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;

public interface TntCashaccountService {

	long count(TntCashAccount t);

	List<TntCashAccount> pageQuery(Page<TntCashAccount> page);

	/**
	 * 根据用户Id取现金账户
	 * 
	 * @param userId
	 * @return
	 */
	TntCashAccount getAccountByUserId(Long userId);

	/**
	 * 手机是否唯一
	 * 
	 * @param mobile
	 *            手机号
	 * @return 唯一手机返回真，否则返回假
	 */
	boolean isMobileUnique(String mobile);

	int update(TntCashAccount cashAccount);

	public int updatePayMobile(TntCashAccount cashAccount);

	public int updatePayPassword(TntCashAccount cashAccount);

	int insert(TntCashAccount cashAccount);

	public Long addRecharge(TntCashRecharge tntCashRecharge);
	public void updateRecharge(TntCashRecharge tntCashRecharge);
	public void passRecharge(TntCashRecharge tntCashRecharge);
	public void unPassRecharge(TntCashRecharge tntCashRecharge);

	public Long addRefundment(TntCashRefundment tntCashRefundment);

	public Long addCommission(TntCashCommission tntCashCommission);

	public Long addCashMoneyDraw(TntCashMoneyDraw tntCashMoneyDraw);

	public void updateCashMoneyDraw(TntCashMoneyDraw tntCashMoneyDraw);

	public TntCashMoneyDraw findCashMoneyDrawById(Long drawID);

	/**
	 * 提现作废
	 * 
	 * @param drawID
	 */
	public void releaseCashMoneyDraw(Long drawID);

	/**
	 * 添加冻结
	 * 
	 * @param tntCashFreezeQueue
	 */
	public Long addCashFreeze(TntCashFreezeQueue tntCashFreezeQueue);

	/**
	 * 冻结作废
	 * 
	 * @param tntCashFreezeQueue
	 */
	public void releaseFreeze(TntCashFreezeQueue tntCashFreezeQueue);
	
	/**
	 * 申请解冻
	 * 
	 * @param 
	 */
	public void updateFreeze(TntCashFreezeQueue tntCashFreezeQueue);

	public long countCashPay(TntCashPay t);

	public List<TntCashPay> pageQueryCashPay(Page<TntCashPay> page);

	public long countCashRecharge(TntCashRecharge t);

	public List<TntCashRecharge> pageQueryCashRecharge(
			Page<TntCashRecharge> page);

	public long countCashRefundment(TntCashRefundment t);

	public List<TntCashRefundment> pageQueryCashRefundment(
			Page<TntCashRefundment> page);

	public long countCashMoneyDraw(TntCashMoneyDraw t);

	public List<TntCashMoneyDraw> pageQueryCashMoneyDraw(
			Page<TntCashMoneyDraw> page);

	public long countCashCommission(TntCashCommission t);

	public List<TntCashCommission> pageQueryCashCommission(
			Page<TntCashCommission> page);

	long countCashFreeze(TntCashFreezeQueue t);

	List<TntCashFreezeQueue> pageQueryCashFreeze(Page<TntCashFreezeQueue> page);

	/**
	 * 验证支付密码
	 * 
	 * @return
	 */
	public String validatePayPassword(TntCashAccount t);

	/**
	 * 验证账户金额
	 * 
	 * @return
	 */
	public ResultGod<TntCashAccount> validateCashBalance(TntCashAccount t,
			Long money);

	/**
	 * 验证账户金额
	 * 
	 * @return
	 */
	public ResultGod<TntCashAccount> validateCashBalance(Long userId, Long money);

	/**
	 * 支付，status为SUCCESS时成功，FAILURE时失败
	 * 
	 * @param t
	 * @return
	 */
	public boolean pay(TntCashPay t);

}
