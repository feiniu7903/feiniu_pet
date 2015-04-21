package com.lvmama.comm.pet.service.fin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.vo.Page;

/**
 * 结算单管理
 * 
 * @author yanggan
 * 
 */
@RemoteService("setSettlementService")
public interface SetSettlementService {

	/**
	 * 查询结算单分页数据
	 * 
	 * @param searchParameter
	 *            查询条件
	 * @return
	 */
	Page<SetSettlement> searchList(Map<String, Object> searchParameter);

	/**
	 * 根据查询条件查询结算单ID
	 * 
	 * @param searchParameter
	 *            查询条件
	 * @return
	 */
	List<Long> searchSettlementIds(Map<String, Object> searchParameter);

	/**
	 * 根据结算单ID查询结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	SetSettlement searchBySettlementId(Long id);

	/**
	 * 根据结算单ID查询结算单信息（包含原始对象信息）
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	SetSettlement searchInitalSettlement(Long id);

	/**
	 * 修改固话结算对象信息
	 * 
	 * @param settlement
	 *            结算单信息
	 */
	void updateInitalInfo(SetSettlement settlement);

	/**
	 * 结算单打款
	 * 
	 * @param settlementId
	 *            结算单号
	 * @param advanceDepositsPayAmount
	 *            预存款打款金额
	 * @param deductionPayAmount
	 *            抵扣款打款金额
	 * @param bankPayAmount
	 *            银行打款金额
	 * @param bankName
	 *            银行名称
	 * @param operatetime
	 *            打款时间
	 * @param serial
	 *            流水号
	 * @param operatorName
	 *            操作人
	 * @return
	 */
	Map<String, Object> toPay(Long settlementId, Long advanceDepositsPayAmount, Long deductionPayAmount, Long bankPayAmount, String bankName, Date operatetime, String serial, String operatorName);

	/**
	 * 结算单结算
	 * 
	 * @param settlementId
	 *            结算单号
	 * @param memo
	 *            备注
	 * @param operatorName
	 *            操作人
	 */
	Map<String, Object>  settle(long settlementId, String memo, String operatorName);

	/**
	 * 查询change的流水记录
	 * 
	 * @param settlementId
	 *            结算单号
	 * @return
	 */
	Page<SetSettlementChange> searchChangeRecord(Map<String,Object> searchParams);
}
