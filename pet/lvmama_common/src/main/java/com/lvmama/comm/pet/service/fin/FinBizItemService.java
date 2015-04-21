package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinBizItem.BIZ_STATUS;

/**
 * 财务业务流水单服务
 * @author taiqichao
 *
 */
public interface FinBizItemService {
	
	/**
	 * 新增业务流水单记录
	 * @param finBizItem 业务流水单信息
	 */
	void insertFinBizItem(FinBizItem finBizItem);
	
	/**
	 * 更新业务流水单记录
	 * @param finBizItem 业务流水单信息
	 */
	void updateFinBizItem(FinBizItem finBizItem);
	
	/**
	 * 批量更新业务流水状态
	 * @param status 状态
	 * @param reconResultIds 勾兑id
	 */
	void batchUpdateFinBizItemStatus(BIZ_STATUS status,List<Long> reconResultIds);
	
	/**
	 * 获取业务流水单记录
	 * @param bizItemId 业务流水单ID
	 * @return
	 */
	FinBizItem getFinBizItem(Long bizItemId);
	
	/**
	 * 删除业务流水单
	 * @param bizItemId 业务流水单ID
	 */
	void deleteFinBizItem(Long bizItemId);

	/**
	 * 查询做账流水记录(单次1000条)
	 * @return 业务流水单集合
	 */
	List<FinBizItem> selectBizItemListForBatch();
	
	/**
     * 根据查询参数计算总行数
     * @author lvhao
     * @param paramMap 查询参数
     * @return 总行数
     */
    Long selectFinBizItemListByParasCount(Map<String,String> paramMap);
	
    /**
     * 根据查询条件获取对账结果集合
     * @author lvhao
     * @param paramMap 查询条件
     * @return 对账结果集合
     */
    List<FinBizItem> selectFinBizItemListByParas(Map<String,String> paramMap);
    
    /**
     * 根据查询条件计算  我方交易总额及银行交易总额
     * @author lvhao
     * @param paramMap 查询条件
     * @return 我方交易总额及银行交易总额
     */
    Map<String,String> selectTransactionAmountByParamMap(Map<String, String> paramMap);
}
