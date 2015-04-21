package com.lvmama.comm.pet.service.lvmamacard;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardIn;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOut;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOutDetails;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.vo.lvmamacard.LvmamaCardStatistics;

public interface LvmamacardService {

	/**
	 * 通过入库记录得到查总量
	 * @param param
	 * @return
	 * @author nixianjun 2013-12-6
	 */
	Long getCardCountByParamForInStorege(Map param);
	/**
	 * 根据参数获取入库数据量
	 * 
	 * @param param
	 * @return
	 * @author nixianjun 2013-11-22
	 */
	long countByParamForInStorege(Map param);

	/**
	 * 根据参数获取入库数据list
	 * 
	 * @param param
	 * @return
	 * @author nixianjun 2013-11-22
	 */
	List<StoredCardIn> queryByParamForInStorege(Map param);

	/**
	 * 得到最大的入库号 根据面值分类
	 * 
	 * @param amount2
	 * @return
	 * @author nixianjun 2013-11-25
	 */
	String getIncodeForInStorege(Integer amount2);

	/**
	 * 入库数据添加
	 * 
	 * @param storedCardIn
	 * @author nixianjun 2013-11-25
	 */
	void insertStoredCardInForInStorege(StoredCardIn storedCardIn);

	/**
	 * 批量插入储值卡
	 * 
	 * @param list
	 * @author nixianjun 2013-11-25
	 */
	void batchinsertLvmamaStoredCardForLvmamaStoredCard(
			List<LvmamaStoredCard> list);

	String getLastCardNoByAmountForLvmamaStoredCard(Integer amount3);

	void insertLvmamaStoredCard(LvmamaStoredCard lvmamastoredcard);

	/* 出库 */
	/**
	 * 出库分页查询
	 * 
	 * @param param
	 * @return
	 */
	public List<StoredCardOut> queryByParamForOutStorege(
			Map<String, Object> param);

	/**
	 * 出库新增
	 * 
	 * @param storedCardOut
	 */
	public void insertStoredCardOutForOutStorege(StoredCardOut storedCardOut);

	/**
	 * 出库修改
	 * 
	 * @param storedCardOut
	 */
	public void updateStoredCardOutForOutStorege(StoredCardOut storedCardOut);

	/**
	 * 出库分页查询count
	 * 
	 * @param param
	 * @return
	 */
	public Long countByParamForOutStorege(Map<String, Object> param);

	/**
	 * 出库明细查询
	 * 
	 * @param param
	 * @return
	 */
	public List<StoredCardOutDetails> queryByParamForOutStoregeDetails(
			Map<String, Object> param);

	/**
	 * 出库明细批量新增
	 * 
	 * @param list
	 */
	public void insertOutStoregeDetails(List<StoredCardOutDetails> list);

	/**
	 * 出库明细修改
	 * 
	 * @param details
	 */
	public void updateOutStoregeDetails(StoredCardOutDetails details);

	/**
	 * 出库单删除
	 * @param storedCardOut
	 */
	public void deleteOutStorege(StoredCardOut storedCardOut);
	
	/**
	 * 出库明细删除
	 * 
	 * @param outCode
	 */
	public void deleteOutStoregeDetails(String outCode);
	
	/**
	 * 查询最大出库单号
	 * @return
	 */
	public String getOutCodeForOutStorege();

	/**
	 * 通过参数查询储值卡,获取数量
	 * @param param
	 * @return
	 * @author nixianjun 2013-11-26
	 */
	long countByParamForLvmamaStoredCard(Map param);

	/**
	 * 通过参数查询储值卡，获取数据
	 * @param param
	 * @return
	 * @author nixianjun 2013-11-26
	 */
	List<LvmamaStoredCard> queryByParamForLvmamaStoredCard(Map param);

	LvmamaStoredCard getOneStoreCardByCardNo(String cardNo);

	void insertStoredCardInAndbatchInsertStoredCard(Integer amount,
			Integer count, Date validDate);

	/**
	 * 通过卡号数组作废卡
	 * @param arrayStr
	 * @param status
	 * @author nixianjun 2013-11-27
	 */
	void batchCancelLvmamaStoredCardByArray(String[] arrayStr, String status);

	/**
	 * 通过批次号作废卡
	 * @param inCode
	 * @param code
	 * @author nixianjun 2013-11-27
	 * @param instatus 
	 */
	void cancelLvmamaStoredCardByInCode(String inCode, String cardstatus, String instatus);

	/**
	 * 通过入库卡 通过并入库
	 * @param inCode
	 * @param code
	 * @param code2
	 * @author nixianjun 2013-11-27
	 */
	void passLvmamaStoredCardByInCode(String inCode, String code, String code2);

	/**
	 * 更改储值卡表
	 * @param map
	 * @author nixianjun 2013-11-28
	 */
	void updateByParamForLvmamaStoredCard(Map map);
 
	public void updateOutStoredCard(Map<String, Object> param);
	
	@SuppressWarnings("unchecked")
	public List<LvmamaStoredCard> queryOutStoredBeginNoAndEndNo(Map<String, Object> param);

	/**
	 * 综合查询入库出库count
	 * @param param
	 * @return
	 * @author nixianjun 2013-12-2
	 */
	long countByParamForInStoreAndOutStore(Map param);
	/**
	 * 综合查询入库出库query
	 * @param param
	 * @return
	 * @author nixianjun 2013-12-2
	 */
	List<LvmamaCardStatistics> queryByParamForInStoreAndOutStore(Map param);

	LvmamaCardStatistics getLvmamaCardStatisticsByInCode(String inCode); 
	
	  List<LvmamaStoredCard> queryOutStoredCardStatusByOutCode(Map<String, Object> param);

	  /**
	   * 更改入库状态
	   * @param param
	   * @author nixianjun 2013-12-5
	   */
	void updateForInStorage(Map param);

	/**
	 * 使用新储值卡 支付订单.
	 *
	 * @param lvmamaStoredCard
	 *            储值卡对象
	 * @param orderId
	 *            订单ID
	 * @param bizType
	 *            业务系统
	 * @param payAmount
	 *            订单金额
	 * @param operatorId
	 *            操作人
	 * @author zhangjie 2013-12-10
	 */
	 Long payFromStoredCard(LvmamaStoredCard lvmamaStoredCard,Long orderId,String bizType, Long payAmount,Long operatorId);
	
	/**
	 * 有新储值卡支付的退款处理
	 * @author zhangjie 2013-12-11
	 */
	 BankReturnInfo refund2CardAccount(Long payRefundmentId,Long paymentId, Long refundAmount,String serialNo,String operatorId);
	
	 /**
	  * 得到目前距离日期过期的驴游天下卡储值卡
	  * @return
	  * @author nixianjun 2013-12-12
	  */
	 List<LvmamaStoredCard> getOverTimeStoredCard(Date date);
	 /**
	  * 卡做延期
	  * @param lvmamaCard
	  * @param userName
	  * @author nixianjun 2013-12-16
	  */
	void doDeplay(LvmamaStoredCard lvmamaCard, String userName);
	/**
	 * 查询出库卡总数总金额
	 * @param param
	 * @return
	 */
	StoredCardOut queryOutStoregeSum(Map<String, Object> param);
	/**
	 * 出库单导出excel查询
	 * @param param
	 * @return
	 */
	List<StoredCardOut> queryOutStoregeExcel(Map<String, Object> param);
	/**
	 * 做解冻
	 * @param lvmamaCard
	 * @param userName
	 * @author nixianjun 2014-1-16
	 */
	void doUnFrozen(LvmamaStoredCard lvmamaCard, String userName);
	
	public List<LvmamaStoredCard> queryUsedLvmamaStoredCardByUserId(Map param);
}
