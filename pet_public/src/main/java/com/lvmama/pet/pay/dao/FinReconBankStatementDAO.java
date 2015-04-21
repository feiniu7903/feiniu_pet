package com.lvmama.pet.pay.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.po.pay.PayPayment;
public class FinReconBankStatementDAO extends BaseIbatisDAO {
	/**
	 * 保存银行交易明细
	 * @author ZHANG Nan
	 * @param finReconBankStatement 银行交易明细对象
	 * @return 主键
	 */
    public Long insert(FinReconBankStatement finReconBankStatement) {
    	return (Long) super.insert("FIN_RECON_BANK_STATEMENT.insert", finReconBankStatement);
    }
    /**
     * 更新银行源交易数据
     * @author ZHANG Nan
     * @param finReconBankStatement
     */
	public void update(FinReconBankStatement finReconBankStatement){
		super.update("FIN_RECON_BANK_STATEMENT.update", finReconBankStatement);
	}
    /**
     * 根据查询条件获取银行交易明细集合
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 银行交易明细集合
     */
    @SuppressWarnings("unchecked")
	public List<FinReconBankStatement> selectByParamMap(Map<String,Object> paramMap) {
    	return super.queryForListForReport("FIN_RECON_BANK_STATEMENT.selectByParamMap", paramMap);
    }
    /**
     * 根据网关集合+对账单日期删除数据
     * @author ZHANG Nan
     * @param gatewayIN 网关集合
     * @param downloadTime 对账单日期
     * @return 影响行数
     */
    public int deleteOldData(String gatewayIN,Date downloadTime) {
    	Map<String,Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("gatewayIN", gatewayIN);
    	paramMap.put("downloadTime", downloadTime);
    	return super.delete("FIN_RECON_BANK_STATEMENT.deleteOldData", paramMap);
    }
    
}