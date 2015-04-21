package com.lvmama.pet.pay.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.FinReconResult;
public class FinReconResultDAO extends BaseIbatisDAO {

    public Long insert(FinReconResult finReconResult) {
    	return (Long) super.insert("FIN_RECON_RESULT.insert", finReconResult);
    }
	
	public int update(FinReconResult finReconResult){
		return super.update("FIN_RECON_RESULT.update", finReconResult);
	}
	
	public int updateGLStatus(FinReconResult finReconResult){
		return super.update("FIN_RECON_RESULT.updateGLStatus", finReconResult);
	}
    
	public FinReconResult selectByReconResultId(Long reconResultId){
		return (FinReconResult) super.queryForObject("FIN_RECON_RESULT.selectByReconResultId", reconResultId);
	}
    public int deleteOldData(String gatewayIN,Date bankReconTime,String reconStatus) {
    	Map<String,Object> paramMap=new HashMap<String,Object>();
    	paramMap.put("gatewayIN", gatewayIN);
    	paramMap.put("bankReconTime", bankReconTime);
    	paramMap.put("reconStatus", reconStatus);
    	return super.delete("FIN_RECON_RESULT.deleteOldData", paramMap);
    }
    public int updateNoMatchingReconResult(Map<String,Object> paramMap){
    	return super.update("FIN_RECON_RESULT.updateNoMatchingReconResult",paramMap);
    }
    
    
    public Long selectReconResultListByParasCount(Map<String,String> paramMap){
    	return (Long) super.queryForObject("FIN_RECON_RESULT.selectReconResultListByParasCount",paramMap);
    }
    @SuppressWarnings("unchecked")
	public List<FinReconResult> selectReconResultListByParas(Map<String,String> paramMap){
    	return super.queryForListForReport("FIN_RECON_RESULT.selectReconResultListByParas",paramMap);
    }
    @SuppressWarnings("unchecked")
	public List<FinReconResult> selectReconResultListForBatch(){
    	return super.queryForList("FIN_RECON_RESULT.selectReconResultListForBatch",null);
    }
	@SuppressWarnings("unchecked")
	public Map<String,BigDecimal> selectTransactionAmountByParamMap(Map<String, String> paramMap){
		return (Map<String,BigDecimal>) super.queryForObject("FIN_RECON_RESULT.selectTransactionAmountByParamMap", paramMap);
	}
	
    /**
     * 更新所有入账失败的勾兑结果为未记账
     * @return 成功更新条数
     */
    public int updateFailedReconResultGLStatus(){
    	super.update("FIN_COST_GL_INTERFACE.updateOrdOrderItemMetaGLStatusNull");
    	super.update("FIN_BIZ_ITEM.updateFailedFinBizItemGLStatus");
    	return super.update("FIN_RECON_RESULT.updateFailedReconResultGLStatus");
    }
}