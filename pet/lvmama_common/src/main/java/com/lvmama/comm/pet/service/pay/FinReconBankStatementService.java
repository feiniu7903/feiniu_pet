package com.lvmama.comm.pet.service.pay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.FinReconBankStatement;

public interface FinReconBankStatementService {
	/**
	 * 保存银行交易明细
	 * @author ZHANG Nan
	 * @param finReconBankStatement 银行交易明细对象
	 * @return 主键
	 */
    public Long insert(FinReconBankStatement finReconBankStatement);
    /**
     * 更新银行源交易数据
     * @author ZHANG Nan
     * @param finReconBankStatement
     */
	public int update(FinReconBankStatement finReconBankStatement);
	/**
	 * 保存银行交易明细集合
	 * @author ZHANG Nan
	 * @param finReconBankStatementList 银行交易明细集合
	 * @return 主键集合
	 */
	public List<Long> insert(List<FinReconBankStatement> finReconBankStatementList);
    /**
     * 根据查询条件获取银行交易明细集合
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 银行交易明细集合
     */
	public List<FinReconBankStatement> selectByParamMap(Map<String,Object> paramMap);
	/**
	 * 根据商户订单号+交易流水号+交易类型+网关获取银行对账明细对象
	 * @author ZHANG Nan
	 * @param bankPaymentTradeNo 商户订单号
	 * @param bankGatewayTradeNo 交易流水号
	 * @param transactionType 交易类型
	 * @param gateway 网关
	 * @return 银行对账明细对象
	 */
	public FinReconBankStatement selectFinReconBankStatement(String bankPaymentTradeNo,String bankGatewayTradeNo,String transactionType,String gateway) ;
    /**
     * 根据网关集合+对账单日期删除数据
     * @author ZHANG Nan
     * @param gatewayIN 网关集合
     * @param downloadTime 对账单日期
     * @return 影响行数
     */
	public int deleteOldData(String gatewayIN,Date downloadTime);
}
