package com.lvmama.pet.fin.service;

import java.util.HashMap;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinBizItem.BIZ_STATUS;
import com.lvmama.comm.pet.service.fin.FinBizItemService;
import com.lvmama.pet.fin.dao.FinBizItemDAO;

/**
 * 财务业务流水单服务实现
 * @author taiqichao
 *
 */
@HessianService("finBizItemService")
@Service("finBizItemService")
public class FinBizItemServiceImpl implements FinBizItemService {
	
	@Autowired
	private FinBizItemDAO finBizItemDAO;

	@Override
	public void insertFinBizItem(FinBizItem finBizItem) {
		finBizItemDAO.insertFinBizItemDO(finBizItem);
	}

	@Override
	public void updateFinBizItem(FinBizItem finBizItem) {
		finBizItemDAO.updateFinBizItemDO(finBizItem);
	}
	
	@Override
	public List<FinBizItem> selectBizItemListForBatch(){
    	return finBizItemDAO.selectBizItemListForBatch();
    }

	@Override
	public void deleteFinBizItem(Long bizItemId) {
		finBizItemDAO.deleteFinBizItemDOByPrimaryKey(bizItemId);
	}

	@Override
	public FinBizItem getFinBizItem(Long bizItemId) {
		return finBizItemDAO.findFinBizItemDOByPrimaryKey(bizItemId);
	}

	/**
     * 根据查询参数计算总行数
     * @author lvhao
     * @param paramMap 查询参数
     * @return 总行数
     */
	@Override
	public Long selectFinBizItemListByParasCount(Map<String, String> paramMap) {
		return finBizItemDAO.selectFinBizItemListByParasCount(paramMap);
	}

	/**
     * 根据查询条件获取对账结果集合
     * @author lvhao
     * @param paramMap 查询条件
     * @return 对账结果集合
     */
	@Override
	public List<FinBizItem> selectFinBizItemListByParas(Map<String, String> paramMap) {
		return finBizItemDAO.selectFinBizItemListByParas(paramMap);
	}
	
	/**
     * 根据查询条件计算  我方交易总额及银行交易总额
     * @author ZHANG Nan
     * @param paramMap 查询条件
     * @return 我方交易总额及银行交易总额
     */
	public Map<String,String> selectTransactionAmountByParamMap(Map<String, String> paramMap){
		Map<String,BigDecimal> resultMap=finBizItemDAO.selectTransactionAmountByParamMap(paramMap);
		Map<String,String> map=new HashMap<String,String>();
		if(resultMap!=null){
			DecimalFormat df =new DecimalFormat("0.00");
			if(resultMap.get("TRANSACTIONAMOUNTSUM")!=null){
				map.put("TRANSACTIONAMOUNTSUM", df.format(resultMap.get("TRANSACTIONAMOUNTSUM").doubleValue()/100));	
			}
			else{
				map.put("TRANSACTIONAMOUNTSUM", "0");
			}
			if(resultMap.get("TRANSACTIONBANKAMOUNTSUM")!=null){
				map.put("TRANSACTIONBANKAMOUNTSUM",df.format(resultMap.get("TRANSACTIONBANKAMOUNTSUM").doubleValue()/100));	
			}
			else{
				map.put("TRANSACTIONBANKAMOUNTSUM", "0");
			}
		}
		return map;
	}

	@Override
	public void batchUpdateFinBizItemStatus(BIZ_STATUS status,List<Long> reconResultIds) {
		if(null==reconResultIds||reconResultIds.size()==0){
			return;
		}
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("bizStatus", status.getCode());
		param.put("reconResultIds", reconResultIds);
		finBizItemDAO.batchUpdateBizStatus(param);
	}

}
