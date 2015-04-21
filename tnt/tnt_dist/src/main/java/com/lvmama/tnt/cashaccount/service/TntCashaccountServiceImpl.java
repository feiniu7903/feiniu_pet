package com.lvmama.tnt.cashaccount.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.cashaccount.mapper.TntCashAccountMapper;
import com.lvmama.tnt.cashaccount.mapper.TntCashCommissionMapper;
import com.lvmama.tnt.cashaccount.mapper.TntCashFreezeQueueMapper;
import com.lvmama.tnt.cashaccount.mapper.TntCashMoneyDrawMapper;
import com.lvmama.tnt.cashaccount.mapper.TntCashPayMapper;
import com.lvmama.tnt.cashaccount.mapper.TntCashRechargeMapper;
import com.lvmama.tnt.cashaccount.mapper.TntCashRefundmentMapper;
import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.cashaccount.po.TntCashCommission;
import com.lvmama.tnt.cashaccount.po.TntCashFreezeQueue;
import com.lvmama.tnt.cashaccount.po.TntCashMoneyDraw;
import com.lvmama.tnt.cashaccount.po.TntCashPay;
import com.lvmama.tnt.cashaccount.po.TntCashRecharge;
import com.lvmama.tnt.cashaccount.po.TntCashRefundment;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.mapper.TntOrderMapper;
import com.lvmama.tnt.order.po.TntOrder;

@Repository("tntCashaccountService")
public class TntCashaccountServiceImpl implements TntCashaccountService {

	@Autowired
	TntCashAccountMapper tntCashAccountMapper;

	@Autowired
	TntCashFreezeQueueMapper tntCashFreezeQueueMapper;

	@Autowired
	TntCashMoneyDrawMapper tntCashMoneyDrawMapper;

	@Autowired
	TntCashPayMapper tntCashPayMapper;

	@Autowired
	TntCashRechargeMapper tntCashRechargeMapper;

	@Autowired
	TntCashRefundmentMapper tntCashRefundmentMapper;

	@Autowired
	TntCashCommissionMapper tntCashCommissionMapper;

	@Autowired
	public TntOrderMapper tntOrderMapper;

	public Long addRecharge(TntCashRecharge tntCashRecharge) {
		if (tntCashRecharge == null) {
			return 0L;
		}
		TntCashAccount cashUser = tntCashAccountMapper.getById(tntCashRecharge
				.getCashAccountId());
		if (cashUser == null) {
			return 0L;
		}
		tntCashRechargeMapper.insert(tntCashRecharge);
		return tntCashRecharge.getCashRechargeId();
	}

	public void updateRecharge(TntCashRecharge tntCashRecharge) {
		if (tntCashRecharge == null) {
			return;
		}
		TntCashRecharge rec = tntCashRechargeMapper.getById(tntCashRecharge
				.getCashRechargeId());

		if (rec == null) {
			return;
		}
		TntCashAccount cashUser = tntCashAccountMapper.getById(rec
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		rec.setBillNo(tntCashRecharge.getBillNo());
		rec.setBankName(tntCashRecharge.getBankName());
		rec.setAmount(tntCashRecharge.getAmount());
		rec.setBillTime(tntCashRecharge.getBillTime());
		rec.setBankAccount(tntCashRecharge.getBankAccount());
		rec.setBankAccountName(tntCashRecharge.getBankAccountName());
		rec.setReason(tntCashRecharge.getReason());
		rec.setStatus(tntCashRecharge.getStatus());
		tntCashRechargeMapper.update(rec);
	}

	@Transactional
	public void passRecharge(TntCashRecharge tntCashRecharge) {
		if (tntCashRecharge == null) {
			return;
		}
		TntCashRecharge rec = tntCashRechargeMapper.getById(tntCashRecharge
				.getCashRechargeId());

		if (rec == null) {
			return;
		}

		TntCashAccount cashUser = tntCashAccountMapper.getById(rec
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		rec.setStatus(TntConstant.CASH_RECHARGE_STATUS.PASS_AUDIT.name());
		tntCashRechargeMapper.update(rec);
		cashUser.setBalance(cashUser.getBalance() + rec.getAmount());
		cashUser.setTotalMoney(cashUser.getTotalMoney() + rec.getAmount());
		tntCashAccountMapper.updateBalance(cashUser);
	}

	public void unPassRecharge(TntCashRecharge tntCashRecharge) {
		if (tntCashRecharge == null) {
			return;
		}
		TntCashRecharge rec = tntCashRechargeMapper.getById(tntCashRecharge
				.getCashRechargeId());

		if (rec == null) {
			return;
		}

		TntCashAccount cashUser = tntCashAccountMapper.getById(rec
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		rec.setStatus(TntConstant.CASH_RECHARGE_STATUS.UNPASS_AUDIT.name());
		tntCashRechargeMapper.update(rec);
	}

	@Transactional
	public Long addRefundment(TntCashRefundment tntCashRefundment) {
		if (tntCashRefundment == null) {
			return 0L;
		}
		TntCashAccount cashUser = tntCashAccountMapper
				.getById(tntCashRefundment.getCashAccountId());
		if (cashUser == null) {
			return 0L;
		}
		TntOrder tntOrder = tntOrderMapper.getById(tntCashRefundment
				.getTntOrderId());
		if (tntOrder == null) {
			return 0L;
		}

		tntCashRefundment.setCreateTime(new Date());
		tntCashRefundmentMapper.insert(tntCashRefundment);
		cashUser.setBalance(cashUser.getBalance()
				+ tntCashRefundment.getAmount());
		cashUser.setTotalMoney(cashUser.getTotalMoney()
				+ tntCashRefundment.getAmount());
		tntCashAccountMapper.updateBalance(cashUser);
		tntOrder.setRefundStatus(TntConstant.REFUND_STATUS.FINISHED.name());
		tntOrderMapper.updateStatus(tntOrder);
		return tntCashRefundment.getFincRefundmentId();
	}

	@Transactional
	public Long addCommission(TntCashCommission tntCashCommission) {
		if (tntCashCommission == null) {
			return 0L;
		}
		TntCashAccount cashUser = tntCashAccountMapper
				.getById(tntCashCommission.getCashAccountId());
		if (cashUser == null) {
			return 0L;
		}
		tntCashCommission.setCreateTime(new Date());
		tntCashCommissionMapper.insert(tntCashCommission);
		cashUser.setBalance(cashUser.getBalance()
				+ tntCashCommission.getCommisAmount());
		cashUser.setTotalMoney(cashUser.getTotalMoney()
				+ tntCashCommission.getCommisAmount());
		tntCashAccountMapper.updateBalance(cashUser);
		return tntCashCommission.getCashCommissionId();
	}

	@Transactional
	public Long addCashMoneyDraw(TntCashMoneyDraw tntCashMoneyDraw) {
		if (tntCashMoneyDraw == null) {
			return 0L;
		}
		TntCashAccount cashUser = tntCashAccountMapper.getById(tntCashMoneyDraw
				.getCashAccountId());
		if (cashUser == null) {
			return 0L;
		}
		if (tntCashMoneyDraw.getDrawAmount() > cashUser.getBalance()) {
			return 0L;
		}
		tntCashMoneyDraw.setCreateTime(new Date());
		tntCashMoneyDrawMapper.insert(tntCashMoneyDraw);
		cashUser.setBalance(cashUser.getBalance()
				- tntCashMoneyDraw.getDrawAmount());
		cashUser.setFreezeMoney(cashUser.getFreezeMoney()
				+ tntCashMoneyDraw.getDrawAmount());
		tntCashAccountMapper.updateBalance(cashUser);
		return tntCashMoneyDraw.getMoneyDrawId();
	}

	@Transactional
	public void updateCashMoneyDraw(TntCashMoneyDraw tntCashMoneyDraw) {
		if (tntCashMoneyDraw == null) {
			return;
		}
		TntCashMoneyDraw draw = tntCashMoneyDrawMapper.getById(tntCashMoneyDraw
				.getMoneyDrawId());
		if (draw == null) {
			return;
		}
		TntCashAccount cashUser = tntCashAccountMapper.getById(draw
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		draw.setBillNo(tntCashMoneyDraw.getBillNo());
		draw.setBillTime(tntCashMoneyDraw.getBillTime());
		draw.setMemo(tntCashMoneyDraw.getMemo());
		draw.setAuditStatus(tntCashMoneyDraw.getAuditStatus());
		tntCashMoneyDrawMapper.update(draw);
		cashUser.setFreezeMoney(cashUser.getFreezeMoney()
				- draw.getDrawAmount());
		tntCashAccountMapper.updateBalance(cashUser);
	}

	@Transactional
	public void releaseCashMoneyDraw(Long drawID) {
		TntCashMoneyDraw draw = tntCashMoneyDrawMapper.getById(drawID);
		if (draw == null) {
			return;
		}
		TntCashAccount cashUser = tntCashAccountMapper.getById(draw
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		draw.setAuditStatus(TntConstant.DRAW_AUDIT_STATUS.REJECTED_AUDIT.name());
		tntCashMoneyDrawMapper.update(draw);
		cashUser.setBalance(cashUser.getBalance() + draw.getDrawAmount());
		cashUser.setFreezeMoney(cashUser.getFreezeMoney()
				- draw.getDrawAmount());
		tntCashAccountMapper.updateBalance(cashUser);
	}

	@Transactional
	public Long addCashFreeze(TntCashFreezeQueue tntCashFreezeQueue) {
		if (tntCashFreezeQueue == null) {
			return 0L;
		}
		TntCashAccount cashUser = tntCashAccountMapper
				.getById(tntCashFreezeQueue.getCashAccountId());
		if (cashUser == null) {
			return 0L;
		}
		if (tntCashFreezeQueue.getFreezeAmount() > cashUser.getBalance()) {
			return 0L;
		}
		tntCashFreezeQueue.setStatus(TntConstant.FREEZE_STATUS.FREEZE.name());
		tntCashFreezeQueueMapper.insert(tntCashFreezeQueue);
		cashUser.setBalance(cashUser.getBalance()
				- tntCashFreezeQueue.getFreezeAmount());
		cashUser.setFreezeMoney(cashUser.getFreezeMoney()
				+ tntCashFreezeQueue.getFreezeAmount());
		tntCashAccountMapper.updateBalance(cashUser);
		return tntCashFreezeQueue.getFreezeQueueId();
	}

	@Transactional
	public void releaseFreeze(TntCashFreezeQueue tntCashFreezeQueue) {
		if (tntCashFreezeQueue == null) {
			return;
		}

		TntCashFreezeQueue freeze = tntCashFreezeQueueMapper
				.getById(tntCashFreezeQueue.getFreezeQueueId());
		if (freeze == null) {
			return;
		}

		TntCashAccount cashUser = tntCashAccountMapper.getById(freeze
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		freeze.setStatus(TntConstant.FREEZE_STATUS.RELEASE.name());
		freeze.setReleaseTime(new Date());
		tntCashFreezeQueueMapper.update(freeze);
		cashUser.setBalance(cashUser.getBalance() + freeze.getFreezeAmount());
		cashUser.setFreezeMoney(cashUser.getFreezeMoney()
				- freeze.getFreezeAmount());
		tntCashAccountMapper.updateBalance(cashUser);
	}

	public void updateFreeze(TntCashFreezeQueue tntCashFreezeQueue) {
		if (tntCashFreezeQueue == null) {
			return;
		}
		TntCashFreezeQueue freeze = tntCashFreezeQueueMapper
				.getById(tntCashFreezeQueue.getFreezeQueueId());
		if (freeze == null) {
			return;
		}
		TntCashAccount cashUser = tntCashAccountMapper.getById(freeze
				.getCashAccountId());
		if (cashUser == null) {
			return;
		}
		freeze.setStatus(tntCashFreezeQueue.getStatus());
		tntCashFreezeQueueMapper.update(freeze);
	}

	@Override
	public TntCashAccount getAccountByUserId(Long userId) {
		return tntCashAccountMapper.getByUserId(userId);
	}

	/**
	 * 手机是否唯一
	 * 
	 * @param mobile
	 *            手机号
	 * @return 唯一手机返回真，否则返回假
	 */
	@Override
	public boolean isMobileUnique(final String mobile) {
		if (!StringUtil.validMobileNumber(mobile)) {
			return false;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("mobile", mobile);
		Integer num = (Integer) tntCashAccountMapper.checkUnique(parameters);
		if (num > 0) {
			return false;
		} else {
			return true;
		}
	}

	public TntCashMoneyDraw findCashMoneyDrawById(Long drawID) {
		return tntCashMoneyDrawMapper.getById(drawID);
	}

	@Override
	public int insert(TntCashAccount cashAccount) {
		return tntCashAccountMapper.insert(cashAccount);
	}

	@Override
	public int update(TntCashAccount cashAccount) {
		return tntCashAccountMapper.update(cashAccount);
	}

	@Override
	public long count(TntCashAccount t) {
		return tntCashAccountMapper.count(t);
	}

	@Override
	public List<TntCashAccount> pageQuery(Page<TntCashAccount> page) {
		return tntCashAccountMapper.findPage(page);
	}

	public long countCashPay(TntCashPay t) {
		return tntCashPayMapper.count(t);
	}

	public List<TntCashPay> pageQueryCashPay(Page<TntCashPay> page) {
		return tntCashPayMapper.findPage(page);
	}

	public long countCashRecharge(TntCashRecharge t) {
		return tntCashRechargeMapper.count(t);
	}

	public List<TntCashRecharge> pageQueryCashRecharge(
			Page<TntCashRecharge> page) {
		return tntCashRechargeMapper.findPage(page);
	}

	public long countCashRefundment(TntCashRefundment t) {
		return tntCashRefundmentMapper.count(t);
	}

	public List<TntCashRefundment> pageQueryCashRefundment(
			Page<TntCashRefundment> page) {
		return tntCashRefundmentMapper.findPage(page);
	}

	public long countCashMoneyDraw(TntCashMoneyDraw t) {
		return tntCashMoneyDrawMapper.count(t);
	}

	public List<TntCashMoneyDraw> pageQueryCashMoneyDraw(
			Page<TntCashMoneyDraw> page) {
		return tntCashMoneyDrawMapper.findPage(page);
	}

	public long countCashFreeze(TntCashFreezeQueue t) {
		return tntCashFreezeQueueMapper.count(t);
	}

	public List<TntCashFreezeQueue> pageQueryCashFreeze(
			Page<TntCashFreezeQueue> page) {
		return tntCashFreezeQueueMapper.findPage(page);
	}

	public long countCashCommission(TntCashCommission t) {
		return tntCashCommissionMapper.count(t);
	}

	public List<TntCashCommission> pageQueryCashCommission(
			Page<TntCashCommission> page) {
		return tntCashCommissionMapper.findPage(page);
	}

	@Override
	public int updatePayMobile(TntCashAccount cashAccount) {
		return tntCashAccountMapper.updatePayMobile(cashAccount);
	}

	@Override
	public int updatePayPassword(TntCashAccount cashAccount) {
		return tntCashAccountMapper.updatePayPassword(cashAccount);
	}

	@Override
	public ResultGod<TntCashAccount> validateCashBalance(TntCashAccount t,
			Long money) {
		ResultGod<TntCashAccount> result = new ResultGod<TntCashAccount>();
		String error = null;
		if (t != null && money != null) {
			if (t.getCashAccountId() != null) {
				t = tntCashAccountMapper.getById(t.getCashAccountId());
			} else if (t.getUserId() != null) {
				t = tntCashAccountMapper.getByUserId(t.getUserId());
			}
			if (t != null) {
				result.setResult(t);
				if (t.getBalance() < money) {
					error = "您的账户余额不足";
				}
			} else {
				error = "此现金账户不存在";
			}
		} else {
			error = "验证参数错误";
		}
		result.setSuccess(error == null);
		result.setErrorText(error);
		return result;
	}

	@Override
	public ResultGod<TntCashAccount> validateCashBalance(Long userId, Long money) {
		TntCashAccount t = new TntCashAccount();
		t.setUserId(userId);
		return validateCashBalance(t, money);
	}

	@Override
	public String validatePayPassword(TntCashAccount t) {
		String error = null;
		if (t != null && t.getPaymentPassword() != null) {
			String paymentPassword = t.getPaymentPassword();
			if (t.getCashAccountId() != null) {
				t = tntCashAccountMapper.getById(t.getCashAccountId());
			} else if (t.getUserId() != null) {
				t = tntCashAccountMapper.getByUserId(t.getUserId());
			}
			if (t != null) {
				String currentPassword = t.getPaymentPassword();
				if (currentPassword == null || currentPassword.isEmpty()) {
					error = "此现金账户未设置支付密码";
				} else {
					String password = MD5.code(paymentPassword,
							MD5.KEY_TNT_USER_PASSWORD);
					if (!password.equals(currentPassword)) {
						error = "支付密码错误";
					}
				}
			} else {
				error = "此现金账户不存在";
			}
		} else {
			error = "验证参数错误";
		}
		return error;
	}

	@Override
	public boolean pay(TntCashPay t) {
		String status = t.getStatus();
		if (TntConstant.CASHPAY_STATUS.SUCCESS.name().equals(status)) {
			if (tntCashPayMapper.updateStatus(t) > 0) {
				if (t.getCashAccountId() != null && t.getAmount() != null) {
					TntCashAccount account = new TntCashAccount();
					account.setCashAccountId(t.getCashAccountId());
					account.setBalance(-t.getAmount());
					return tntCashAccountMapper.appendBalance(account) > 0;
				}
			}
		} else {
			if (tntCashPayMapper.updateStatus(t) <= 0) {
				tntCashPayMapper.insert(t);
			}
		}
		return false;
	}
}
