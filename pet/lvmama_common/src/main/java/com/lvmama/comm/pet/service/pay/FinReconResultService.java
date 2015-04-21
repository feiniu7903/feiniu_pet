package com.lvmama.comm.pet.service.pay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.FinReconResult;

public interface FinReconResultService {
	/**
	 * 保存对账结果
	 * @author ZHANG Nan
	 * @param finReconResult 对账结果对象
	 * @return 主键
	 */
    public Long insert(FinReconResult finReconResult);
    /**
     * 更新对账结果
     * @author ZHANG Nan
     * @param finReconResult 对账结果对象
     * @return
     */
	public int update(FinReconResult finReconResult);
	/**
	 * 保存或更新对账结果(如果主键不为空则更新)
	 * @author ZHANG Nan
	 * @param finReconResult 对账结果对象
	 */
	public void saveOrUpdate(FinReconResult finReconResult);
    /**
	 * 保存对账结果集合
	 * @author ZHANG Nan
	 * @param finReconResult 对账结果对象集合
	 * @return 主键集合
	 */
	public List<Long> insert(List<FinReconResult> finReconResultList);
	/**
	 * 根据主键查询对账结果对象
	 * @author ZHANG Nan
	 * @param reconResultId 主键
	 * @return 对账结果对象
	 */
	public FinReconResult selectByReconResultId(Long reconResultId);
	/**
	 * 根据条件将当前网关+当天日期的支付及充值成功的数据插入到对账结果表中
	 * @author ZHANG Nan
	 * @param gateway 网关
	 * @param reconDate 对账日期(对哪天的帐)
	 * @return 插入主键
	 */
	public boolean copyTransactionDataToReconResult(String gatewayIN,Date reconDate);
	/**
	 * 根据条件将当前网关+当天日期我方成功的交易数据插入到对账结果表中
	 * @author ZHANG Nan
	 * @param gateway 网关
	 * @param reconDate 对账日期(对哪天的帐)
	 * @param defaultReconStatus 默认使用的对账状态(对于存款账户来说默认不需要勾兑直接成功)
	 * @return 是否成功
	 */
	public boolean copyTransactionDataToReconResult(String gatewayIN,Date reconDate,String defaultReconStatus);
	/**
	 * 根据网关+对账日期(对哪天的帐)删除数据
	 * @author ZHANG Nan
	 * @param gateway 网关
	 * @param bankReconTime 对账日期(对哪天的帐)
	 * @return 删除行数
	 */
	public int deleteOldData(String gatewayIN,Date bankReconTime,String reconStatus);
	/**
	 * 统一更新网关+对账日期 未对账的数据
	 * @author ZHANG Nan
	 * @param gatewayIN 需更新的网关
	 * @param bankReconTimeShort 对账日期
	 * @param gateway 网关CODE
	 * @return
	 */
    public int updateNoMatchingReconResult(String gatewayIN,Date bankReconTimeShort,String gateway);
    /**
     * 根据对账流水号+网关交易号+交易类型+网关集合 查询对账结果对象
     * @author ZHANG Nan
     * @param paymentTradeNo 对账流水号
     * @param gatawayTradeNo 网关交易号
     * @param transactionType 交易类型
     * @param gatewayIN 网关集合
     * @return 对账结果对象
     */
    public FinReconResult selectReconResultListByParas(String paymentTradeNo,String gatawayTradeNo,String transactionType,String gatewayIN);
    /**
     * 根据对账流水号+网关交易号+交易类型+网关集合 并且不包含某些主键 查询对账结果对象
     * @author ZHANG Nan
     * @param paymentTradeNo 对账流水号
     * @param gatawayTradeNo 网关交易号
     * @param transactionType 交易类型
     * @param gatewayIN 网关集合
     * @param notInReconResultIds 不包含的主键
     * @return 对账结果对象
     */
    public FinReconResult selectReconResultListByParas(String paymentTradeNo,String gatawayTradeNo,String transactionType,String gatewayIN,String notInReconResultIds);
    public FinReconResult selectReconResultListByParas(String paymentTradeNo,String gatawayTradeNo,String transactionType,String gatewayIN,String notInReconResultIds,String paymentId);
    /**
     * 根据查询参数计算总行数
     * @author ZHANG Nan
     * @param paramMap 查询参数
     * @return 总行数
     */
    public Long selectReconResultListByParasCount(Map<String,String> paramMap);
    /**
     * 根据查询条件获取对账结果集合
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 对账结果集合
     */
    public List<FinReconResult> selectReconResultListByParas(Map<String,String> paramMap);
    
    public List<FinReconResult> selectReconResultListForBatch();
    /**
     * 根据查询条件计算  我方交易总额及银行交易总额
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 我方交易总额及银行交易总额
     */
    public Map<String,String> selectTransactionAmountByParamMap(Map<String, String> paramMap);
    /**
     * 更新记账结果
     * @author Libo Wang
     * @param finReconResult 对账结果对象
     * @return
     */
	public int updateGLStatus(FinReconResult finReconResult);
	/**
	 * 更新所有入账失败的勾兑结果为未记账
	 * @return
	 */
	public int updateFailedReconResultGLStatus();

}
