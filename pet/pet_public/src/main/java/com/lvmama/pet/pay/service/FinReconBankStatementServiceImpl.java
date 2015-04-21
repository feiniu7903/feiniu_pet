package com.lvmama.pet.pay.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.service.pay.FinReconBankStatementService;
import com.lvmama.pet.pay.dao.FinReconBankStatementDAO;

@Service
public class FinReconBankStatementServiceImpl implements FinReconBankStatementService {
	protected final Log log = LogFactory.getLog(this.getClass().getName());

	FinReconBankStatementDAO finReconBankStatementDAO;
	/**
	 * 保存银行交易明细
	 * @author ZHANG Nan
	 * @param finReconBankStatement 银行交易明细对象
	 * @return 主键
	 */
	@Override
	public Long insert(FinReconBankStatement finReconBankStatement) {
		return finReconBankStatementDAO.insert(finReconBankStatement);
		
	}
	/**
     * 更新银行源交易数据
     * @author ZHANG Nan
     * @param finReconBankStatement
     */
	public int update(FinReconBankStatement finReconBankStatement){
		return finReconBankStatementDAO.update("FIN_RECON_BANK_STATEMENT.update", finReconBankStatement);
	}
	/**
	 * 保存银行交易明细集合
	 * @author ZHANG Nan
	 * @param finReconBankStatementList 银行交易明细集合
	 * @return 主键集合
	 */
	@Override
	public List<Long> insert(List<FinReconBankStatement> finReconBankStatementList) {
		List<Long> reconBankStatementIdList=new ArrayList<Long>();
		for (FinReconBankStatement finReconBankStatement : finReconBankStatementList) {
			Long reconBankStatementId=insert(finReconBankStatement);
			reconBankStatementIdList.add(reconBankStatementId);
		}
		return reconBankStatementIdList;
	}
	
    /**
     * 根据查询条件获取银行交易明细集合
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 银行交易明细集合
     */
	@Override
	public List<FinReconBankStatement> selectByParamMap(Map<String, Object> paramMap) {
		return finReconBankStatementDAO.selectByParamMap(paramMap);
	}
	/**
	 * 根据商户订单号+交易流水号+交易类型+网关获取银行对账明细对象
	 * @author ZHANG Nan
	 * @param bankPaymentTradeNo 商户订单号
	 * @param bankGatewayTradeNo 交易流水号
	 * @param transactionType 交易类型
	 * @param gateway 网关
	 * @return 银行对账明细对象
	 */
	public FinReconBankStatement selectFinReconBankStatement(String bankPaymentTradeNo,String bankGatewayTradeNo,String transactionType,String gateway) {
		Assert.notNull("bankPaymentTradeNo","bankPaymentTradeNo can't be empty!");
		Assert.notNull("bankGatewayTradeNo","bankGatewayTradeNo can't be empty!");
		Assert.notNull("transactionType","transactionType can't be empty!");
		Assert.notNull("gateway","gateway can't be empty!");
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("bankPaymentTradeNo", bankPaymentTradeNo);
		paramMap.put("bankGatewayTradeNo", bankGatewayTradeNo);
		paramMap.put("transactionType", transactionType);
		paramMap.put("gateway", gateway);
		List<FinReconBankStatement> finReconBankStatementList=selectByParamMap(paramMap);
		if(finReconBankStatementList!=null && finReconBankStatementList.size()>0){
			return finReconBankStatementList.get(0);
		}
		return null;
	}
    /**
     * 根据网关集合+对账单日期删除数据
     * @author ZHANG Nan
     * @param gatewayIN 网关集合
     * @param downloadTime 对账单日期
     * @return 影响行数
     */
	public int deleteOldData(String gatewayIN,Date downloadTime) {
		Assert.notNull(gatewayIN, "gatewayIN can't be empty");
		Assert.notNull(downloadTime, "downloadTime can't be empty");
		return finReconBankStatementDAO.deleteOldData(gatewayIN, downloadTime);
	}
	
	
	public FinReconBankStatementDAO getFinReconBankStatementDAO() {
		return finReconBankStatementDAO;
	}

	public void setFinReconBankStatementDAO(FinReconBankStatementDAO finReconBankStatementDAO) {
		this.finReconBankStatementDAO = finReconBankStatementDAO;
	}
}
