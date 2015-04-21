package com.lvmama.tnt.recognizance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.recognizance.mapper.TntRecognizanceChangeMapper;
import com.lvmama.tnt.recognizance.mapper.TntRecognizanceMapper;
import com.lvmama.tnt.recognizance.po.TntRecognizance;
import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;

@Repository("tntRecognizanceService")
public class TntRecognizanceServiceImpl implements TntRecognizanceService {

	@Autowired
	private TntRecognizanceMapper tntRecognizanceMapper;

	@Autowired
	private TntRecognizanceChangeMapper tntRecognizanceChangeMapper;

	@Override
	@Transactional
	public long set(Long userId, Long limits, String reason) {
		if (userId != null && limits != null) {
			// 判断是否设置过保证金
			TntRecognizance exist = getByUserId(userId);
			TntRecognizance t = new TntRecognizance();
			t.setUserId(userId);
			t.setLimits(limits);
			int i = 0;
			if (exist != null) {
				t.setRecognizanceId(exist.getRecognizanceId());
				i = tntRecognizanceMapper.update(t);
			} else {
				t.setBalance(0l);
				i = tntRecognizanceMapper.insert(t);
			}
			if (i > 0) {
				TntRecognizanceChange trc = new TntRecognizanceChange();
				trc.setRecognizanceId(t.getRecognizanceId());
				trc.setUserId(userId);
				trc.setReason(reason);
				trc.setAmount(limits);
				trc.setType(TntRecognizance.TYPE.SETLIMIT.getValue());
				tntRecognizanceChangeMapper.insert(trc);
			}
			return limits;
		}
		return 0;
	}

	@Override
	public List<TntRecognizance> pageQuery(Page<TntRecognizance> page) {
		if (page != null && page.getParam() != null)
			return tntRecognizanceMapper.findPage(page);
		return null;
	}

	@Override
	public List<TntRecognizanceChange> pageQueryDetail(
			Page<TntRecognizanceChange> page) {
		if (page != null && page.getParam() != null) {
			return tntRecognizanceChangeMapper.findPage(page);
		}
		return null;
	}

	@Override
	public int count(TntRecognizance t) {
		if (t != null) {
			return tntRecognizanceMapper.count(t);
		}
		return 0;
	}

	@Override
	public int count(TntRecognizanceChange t) {
		if (t != null) {
			return tntRecognizanceChangeMapper.count(t);
		}
		return 0;
	}

	@Override
	public TntRecognizance getByUserId(Long userId) {
		if (userId != null) {
			return tntRecognizanceMapper.getByUserId(userId);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultGod<TntRecognizanceChange> recharge(TntRecognizanceChange t) {
		ResultGod<TntRecognizanceChange> result = new ResultGod<TntRecognizanceChange>(
				false, "申请充值失败");
		boolean flag = deal(t, TntRecognizance.TYPE.RECHARGE) > 0;
		result.setSuccess(flag);
		result.setResult(t);
		return result;
	}

	@Override
	@Transactional
	public ResultGod<TntRecognizanceChange> debit(TntRecognizanceChange t) {
		ResultGod<TntRecognizanceChange> result = new ResultGod<TntRecognizanceChange>(
				false, "申请扣款失败");
		TntRecognizance exist = tntRecognizanceMapper.getById(t
				.getRecognizanceId());
		if (exist != null) {
			if (exist.getBalance() < t.getAmount()) {
				result.setErrorText("余额不足，不能申请扣款");
			} else {
				boolean flag = deal(t, TntRecognizance.TYPE.DEBIT) > 0;
				result.setSuccess(flag);
			}
		}
		result.setResult(t);
		return result;
	}

	private long deal(TntRecognizanceChange t, TntRecognizance.TYPE type) {
		if (t != null && type != null && t.getAmount() != null
				&& t.getRecognizanceId() != null) {
			t.setType(type.getValue());
			t.setApproveStatus(TntConstant.RECOGNIZANCE_CHANGE_STATUS.WAITING
					.name());
			return tntRecognizanceChangeMapper.insert(t);
		}
		return 0;
	}

	private long appendBalance(TntRecognizanceChange t) {
		long money = t.getAmount();
		if (TntRecognizance.TYPE.isDebit(t.getType())) {
			money = -money;
		}
		TntRecognizance tr = new TntRecognizance(t.getRecognizanceId());
		tr.setBalance(money);
		if (tntRecognizanceMapper.appendBalance(tr) > 0)
			return money;
		return 0;
	}

	@Override
	public long set(TntRecognizanceChange t) {
		if (t != null) {
			return set(t.getUserId(), t.getAmount(), t.getReason());
		}
		return 0;
	}

	@Override
	public String getUserNameByRecognizanceId(Long recognizanceId) {
		TntRecognizance t = tntRecognizanceMapper.getById(recognizanceId);
		return t != null ? t.getUserName() : "";
	}

	private ResultGod<String> approveDetail(Long changeId,
			String approveStatus, String reason) {
		ResultGod<String> result = new ResultGod<String>(false, "审核失败");
		TntRecognizanceChange t = tntRecognizanceChangeMapper.getById(changeId);
		if (t != null) {
			String currentStatus = t.getApproveStatus();
			if (!TntConstant.RECOGNIZANCE_CHANGE_STATUS
					.canApprove(currentStatus)) {
				result.setErrorText("不能审核状态为" + currentStatus + "的记录");
			} else {
				t.setApproveStatus(approveStatus);
				t.setApproveReason(reason);
				if (tntRecognizanceChangeMapper.approve(t) > 0) {
					if (TntConstant.RECOGNIZANCE_CHANGE_STATUS
							.isAgree(approveStatus)) {
						appendBalance(t);
					}
					result.setSuccess(true);
					result.setResult(getMessage(t));
				}
			}
		}
		return result;
	}

	private String getMessage(TntRecognizanceChange t) {
		StringBuffer sb = new StringBuffer();
		String type = TntRecognizance.TYPE.getDesc(t.getType());
		sb.append(type);
		sb.append("单号");
		sb.append(t.getChangeId());
		sb.append(TntConstant.RECOGNIZANCE_CHANGE_STATUS.getDesc(t
				.getApproveStatus()));
		if (TntConstant.RECOGNIZANCE_CHANGE_STATUS.isReject(t
				.getApproveStatus())) {
			sb.append("，原因：" + t.getApproveReason());
		} else if (t.isDebit()
				&& TntConstant.RECOGNIZANCE_CHANGE_STATUS.isEdited(t
						.getApproveStatus())) {
			sb.append("，原因：" + t.getReason());
		}
		return sb.toString();
	}

	@Override
	public ResultGod<String> agree(Long changeId) {
		return approveDetail(changeId,
				TntConstant.RECOGNIZANCE_CHANGE_STATUS.AGREE.name(), null);
	}

	@Override
	public ResultGod<String> reject(Long changeId, String reason) {
		return approveDetail(changeId,
				TntConstant.RECOGNIZANCE_CHANGE_STATUS.REJECT.name(), reason);
	}

	@Override
	@Transactional
	public ResultGod<String> approve(TntRecognizanceChange t) {
		ResultGod<String> result = new ResultGod<String>(false, "审核失败");
		if (t != null) {
			Long changeId = t.getChangeId();
			String status = t.getApproveStatus();
			if (changeId != null && status != null) {
				if (TntConstant.RECOGNIZANCE_CHANGE_STATUS.isAgree(status)) {
					result = agree(changeId);
				} else {
					String reason = t.getApproveReason();
					result = reject(changeId, reason);
				}
			}
		}
		return result;
	}

	@Override
	public ResultGod<String> edit(TntRecognizanceChange t) {
		ResultGod<String> result = new ResultGod<String>(false, "修改失败");
		if (t == null || t.getChangeId() == null) {
			result.setErrorText("参数错误，不能修改！");
		} else {
			TntRecognizanceChange exist = tntRecognizanceChangeMapper.getById(t
					.getChangeId());
			if (exist == null) {
				result.setErrorText("你修改的记录不存在");
			} else {
				String currentStatus = exist.getApproveStatus();
				if (!TntConstant.RECOGNIZANCE_CHANGE_STATUS
						.canEdit(currentStatus)) {
					result.setErrorText(TntConstant.RECOGNIZANCE_CHANGE_STATUS
							.getDesc(currentStatus) + "的单子不能修改");
				} else {
					t.setApproveStatus(TntConstant.RECOGNIZANCE_CHANGE_STATUS.EDITED
							.name());
					tntRecognizanceChangeMapper.update(t);
					result.setSuccess(true);
					if (result.isSuccess()) {
						exist.setReason(t.getReason());
						exist.setApproveStatus(t.getApproveStatus());
						result.setResult(getMessage(exist));
					}
				}
			}
		}
		return result;
	}

	@Override
	public ResultGod<String> confirm(Long changeId) {
		ResultGod<String> result = new ResultGod<String>(false, "申请确认失败");
		if (changeId == null) {
			result.setErrorText("参数错误，不能申请确认！");
		} else {
			TntRecognizanceChange exist = tntRecognizanceChangeMapper
					.getById(changeId);
			if (exist == null) {
				result.setErrorText("你申请确认的记录不存在");
			} else {
				String currentStatus = exist.getApproveStatus();
				if (!TntConstant.RECOGNIZANCE_CHANGE_STATUS
						.canConfirm(currentStatus)) {
					result.setErrorText(TntConstant.RECOGNIZANCE_CHANGE_STATUS
							.getDesc(currentStatus) + "的单子不能申请确认");
				} else {
					TntRecognizanceChange t = new TntRecognizanceChange(
							changeId);
					t.setApproveStatus(TntConstant.RECOGNIZANCE_CHANGE_STATUS.WAITING
							.name());
					result.setSuccess(tntRecognizanceChangeMapper.update(t) > 0);
					if (result.isSuccess()) {
						exist.setApproveStatus(t.getApproveStatus());
						result.setResult(getMessage(exist));
					}
				}
			}
		}
		return result;
	}

	@Override
	public ResultGod<String> cancel(Long changeId, String reason) {
		ResultGod<String> result = new ResultGod<String>(false, "废除失败");
		if (changeId == null) {
			result.setErrorText("参数错误，不能废除！");
		} else {
			TntRecognizanceChange exist = tntRecognizanceChangeMapper
					.getById(changeId);
			if (exist == null) {
				result.setErrorText("你废除的记录不存在");
			} else {
				String currentStatus = exist.getApproveStatus();
				if (!TntConstant.RECOGNIZANCE_CHANGE_STATUS
						.canEdit(currentStatus)) {
					result.setErrorText(TntConstant.RECOGNIZANCE_CHANGE_STATUS
							.getDesc(currentStatus) + "的单子不能废除");
				} else {
					TntRecognizanceChange t = new TntRecognizanceChange(
							changeId);
					t.setApproveStatus(TntConstant.RECOGNIZANCE_CHANGE_STATUS.CANCEL
							.name());
					t.setApproveReason(reason);
					result.setSuccess(tntRecognizanceChangeMapper.update(t) > 0);
					if (result.isSuccess()) {
						exist.setApproveStatus(t.getApproveStatus());
						result.setResult(getMessage(exist));
					}
				}
			}
		}
		return result;
	}

	@Override
	public TntRecognizanceChange getChangeById(Long changeId) {
		return tntRecognizanceChangeMapper.getById(changeId);
	}

	@Override
	public List<TntRecognizanceChange> findWithUserPage(
			Page<TntRecognizanceChange> page) {
		return tntRecognizanceChangeMapper.findWithUserPage(page);
	}

	@Override
	public int withUserCount(TntRecognizanceChange entity) {
		return tntRecognizanceChangeMapper.withUserCount(entity);
	}

	@Override
	public TntRecognizanceChange getWithUser(TntRecognizanceChange t) {
		return tntRecognizanceChangeMapper.getWithUser(t);
	}
}
