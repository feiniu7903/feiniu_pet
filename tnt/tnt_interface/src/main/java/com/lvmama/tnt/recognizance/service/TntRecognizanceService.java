package com.lvmama.tnt.recognizance.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.recognizance.po.TntRecognizance;
import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;

public interface TntRecognizanceService {

	/**
	 * 设置保证金，第一次设置时，插入记录，以后更新
	 * 
	 * @param userId
	 * @param limits
	 * @return 返回设置金额
	 */
	public long set(Long userId, Long limits, String reason);

	/**
	 * 设置保证金，第一次设置时，插入记录，以后更新
	 */
	public long set(TntRecognizanceChange t);

	/**
	 * 查询保证金账户
	 * 
	 * @param t
	 * @return
	 */
	public List<TntRecognizance> pageQuery(Page<TntRecognizance> page);

	/**
	 * 保证金操作明细列表
	 * 
	 * @return
	 */
	public List<TntRecognizanceChange> pageQueryDetail(
			Page<TntRecognizanceChange> page);

	public int count(TntRecognizance t);

	public int count(TntRecognizanceChange t);

	/**
	 * 根据userId 查询保证金账户
	 * 
	 * @param userId
	 * @return
	 */
	public TntRecognizance getByUserId(Long userId);

	/**
	 * 充值
	 * 
	 * @param t
	 * @return 返回充值金额
	 */
	public ResultGod<TntRecognizanceChange> recharge(TntRecognizanceChange t);

	/**
	 * 扣款
	 * 
	 * @param t
	 * @return 返回扣款金额
	 */
	public ResultGod<TntRecognizanceChange> debit(TntRecognizanceChange t);

	public String getUserNameByRecognizanceId(Long recognizanceId);

	/**
	 * 审核通过
	 * 
	 * @param changeId
	 * @return
	 */
	public ResultGod<String> agree(Long changeId);

	/**
	 * 审核不通过
	 * 
	 * @param changeId
	 * @param reason
	 * @return
	 */
	public ResultGod<String> reject(Long changeId, String reason);

	/**
	 * 财务审核
	 * 
	 * @param t
	 * @return
	 */
	public ResultGod<String> approve(TntRecognizanceChange t);

	/**
	 * 修改
	 * 
	 * @param t
	 * @return
	 */
	public ResultGod<String> edit(TntRecognizanceChange t);

	/**
	 * 提交确认，再次审核
	 * 
	 * @param changeId
	 * @return
	 */
	public ResultGod<String> confirm(Long changeId);

	/**
	 * 作废
	 */
	public ResultGod<String> cancel(Long changeId, String reason);

	public TntRecognizanceChange getChangeById(Long changeId);

	public TntRecognizanceChange getWithUser(TntRecognizanceChange t);

	public List<TntRecognizanceChange> findWithUserPage(
			Page<TntRecognizanceChange> page);

	public int withUserCount(TntRecognizanceChange entity);

}
