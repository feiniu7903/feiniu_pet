package com.lvmama.comm.pet.service.money;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardBatch;
import com.lvmama.comm.pet.po.money.StoredCardStock;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.vo.Page;

/**
 * 
 * @author liwenzhan
 *
 */
public interface StoredCardService {

	/**
	 * 保存卡信息记录.
	 * @param storedCard
	 * @return
	 */
	Long saveStoredCard(StoredCard storedCard);
	
	/**
	 * 更新卡信息记录.
	 * @param storedCard
	 * @return
	 */
	void updateStoredCard(StoredCard storedCard);
	void updateStoredCard(StoredCard storedCard,String operatorName,String type);
	void updateStoredCard(StoredCard storedCard,String operatorName,String type,String cancelMemo);
	
	 /**
     * 根据卡号取卡的信息.
     * @param cardNo
     * @return
     */
	 StoredCard queryStoredCardByCardNo(final String cardNo,final boolean isChackStatus);
	/**
	 * 根据卡得ID取卡的信息.
	 * @param storedCardId
	 * @return
	 */
	StoredCard  queryStoredCardById(long storedCardId);
	
	/**
	 * 判断整个批次是否可以作废.
	 * 作废：判断卡状态为正常状态，且库存状态为未入库状态，激活状态为未激活才可以作废.
	 * @return
	 */
	boolean isBatchCancel(StoredCardBatch batch);
	
	 /**
	 * 卡数据列表.
	 * @param parameter
	 * @return
	 */
	Page<StoredCard> selectCardByParam(Map<String,Object> parameter,Long pageSize,Long page);
	
	/**
	 * 取Card的记录数.
	 * @param parameter
	 * @return
	 */
	Long selectCardByParamCount(Map<String, Object> parameter);
	

	/**
	 * 保存批次信息记录.
	 * @param storedCardBatch
	 * @return
	 */
     Long saveStoredCardBatch(StoredCardBatch storedCardBatch);
     
     /**
 	 * 根据批次得ID取批次的信息.
 	 * @param storedCardId
 	 * @return
 	 */
 	StoredCardBatch  queryBatchById(long batchId);
 	/**
	 * 根据卡号取卡的信息.
	 * @param storedCardId
	 * @return
	 */
	StoredCard  queryStoredCardByCardNo(String cardNo);
	
     /**
 	 * 批次列表.
 	 * @param parameter
 	 * @return
 	 */
 	Page<StoredCardBatch> selectBatchListByParam(Map<String,Object> parameter,Long pageSize,Long page);
     
 	/**
	 * 取CardBatch的记录数.
	 * @param parameter
	 * @return
	 */
	Long selectBatchCountByParam(Map<String, Object> parameter);

	
	/**
	 * 取相同批次的储值卡的记录List.
	 * @param parameter
	 * @return
	 */
	List<StoredCard> queryCardListByParam(Map<String, Object> parameter);
	
	/**
	 * 根据时间和面额查询CardBatch的记录数.
	 * @param batchNo
	 * @return
	 */
	Long selectBatchCount(String batchNo);
	
	/**
	 * 批次作废.
	 * @param batchId
	 * @param operatorName
	 */
	void batchCancel(StoredCardBatch batch,String operatorName,String cencalMemo);
	
	/**
	 * 批次导出数据处理.
	 * @param parameter
	 * @param operatorName
	 * @return
	 */
	List<StoredCard> doOutputStoredCard(Map<String,Object> parameter,String operatorName);
	/**
	 * 保存库信息.
	 * @param storedCardStock
	 * @return
	 */
	Long saveStoredCardStock(StoredCardStock storedCardStock);

	/**
	 * 保存储值卡消费记录.
	 * @param storedCardUsage
	 * @return
	 */
	Long saveStoredCardUsage(StoredCardUsage storedCardUsage);

	
	/**
     * 根据卡号取该卡号的所有消费纪录.
     * @param cardNo
     * @return
     */
    List<StoredCardUsage> queryUsageListByCardId(Map<String, Object> parameter);
	
	/**
	 * 消费记录.
	 * @param param
	 * @return
	 */
	List<StoredCardUsage> queryUsageByParam(Map<String, Object> param);
	
	
	/**
	 * 根据类型,对象ID,日志类型查询相应的日志.
	 * @param objectId
	 * @param objectType
	 * @param logType
	 * @return
	 */
	List<ComLog> queryComLog(final Long objectId,
			final String objectType);
	
	/**
	 * 统计储值卡,为生成入/出库单做准备.
	 * @param maps
	 * @return
	 */
	List<Map<String,Object>> storedCardStats(Map<String, Object> maps);
	/**
	 * 创建入/出库单.
	 * @param lists
	 * @param maps
	 * @return
	 */
	Long buildCardStock(Map<String, Object> maps);
	/**
	 * 查询入/出库单数目.
	 * @param maps
	 * @return
	 */
	Long countCardStockByParameters(Map<String,Object> maps);
	/**
	 * 统计入/出库单.
	 * @param maps
	 * @return
	 */
	Page<Map> cardStockStats(Map<String,Object> maps,Long pageSize,Long page);
	/**
	 * 查询单个入/出库单.
	 * @param stockId
	 * @return
	 */
	StoredCardStock queryCardStockByStockId(Long stockId);
	/**
	 * 移除cardStatisticsList中索引为index的对象.
	 * @param cardStatisticsList
	 * @param index
	 * @return
	 */
	List<Map<String, Object>> removeCardStatisticsByIndex(List<Map<String, Object>> cardStatisticsList,int index);
	/**
	 * 添加cardStatisticsList中的对象.
	 * @param cardStatisticsList
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> addCardStatistics(List<Map<String, Object>> cardStatisticsList,Map<String, Object> parameters);
	/**
	 * 作废出库单.
	 * @param maps
	 */
	boolean cancleOutStockByParam(Map<String,Object> maps);
	/**
	 * 激活某出库单的所有储值卡.
	 * @param stockId
	 */
	boolean activeCardByParam(Map<String,Object> maps);
	/**
	 * 核实出库单统计数据.
	 * @param maps
	 * @return
	 */
	List<Map<String, Object>> verifyOutStock(Map<String,Object> maps);
	/**
	 * 是否符合根据出库单来激活、作废储值卡.
	 * @param stockId
	 * @return
	 */
	boolean isOK(Long stockId);
	/**
	 * 导出入/出库单中所有储值卡.
	 * @param maps
	 * @return
	 */
	List<StoredCard> storedCardExport(Map<String,Object> maps);	
	
	/**
	 * 判断储值卡是不是可以用来支付.
	 * @param cardNo
	 * @return
	 */
	public boolean isCardPay(String cardNo);

	/**
	 * 使用储值卡 支付订单.
	 *
	 * @param orderId
	 *            订单ID
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表使用储值卡支付订单成功，<code>false</code>
	 *         代表使用储值卡支付订单失败
	 */
	public Long payFromStoredCard(StoredCard card,Long orderId,String bizType ,Long payAmount,Long operatorId);
	
	public BankReturnInfo refund2CardAccount(Long payRefundmentId,Long paymentId, Long refundAmount,String serialNo,String operatorId);
	
	public  StoredCard  queryStoredCardByCardNoAndSerialNo(String cardNo,String serialNo);
	
	/**
	 * 修改出库单
	 * @param cardStock
	 * @param operatorName
	 * @return
	 */
	public boolean updateOutStock(StoredCardStock cardStock, String operatorName);

}