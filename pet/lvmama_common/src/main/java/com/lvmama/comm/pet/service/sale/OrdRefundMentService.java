package com.lvmama.comm.pet.service.sale;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
/**
 * 
 * @author huangli
 *
 */
public interface OrdRefundMentService {
	/**
	 * 用户通过主健更新退款服务对像.
	 * @param ordrefundment
	 * @return
	 */
	public void updateOrdRefundmentByPK(OrdRefundment ordrefundment);
	/**
	 * 用户点击保存退款时，填写用户退款单，可分次总订单退款及订单子子项退款.
	 * @param record
	 * @param orderId
	 * @return
	 */
	public void saveOrdRefundMent(List list,List orderItemId,String operatorId);
	
	/**
	 * 根据退款单ID取退款单信息.
	 * @param ordRefundId
	 * @return
	 */
	public OrdRefundment findOrdRefundmentById(Long refundmentId);
	/**
	 * 插入退款对像.
	 * 
	 * @param record
	 * @return
	 */
	public Long insert(OrdRefundment record);
	/**
	 * 综合退款售售服务结束的条件的退款记录计数.
	 * 
	 * @param param
	 * @return
	 */
	public Long findOrdRefundByParamCountSaleFinish(Map param);
	/**
	 * 综合退款售售服务结束的条件的退款记录.
	 * 
	 * @param param
	 * @return
	 */
	public List<OrdRefundment> findOrdRefundByParamSaleFinish(Map param,int skipResults,int maxResults);
	/**
	 * 综合查询退款集合计数.
	 * 
	 * @param param
	 * @return
	 */
	public Long findOrdRefundByParamCount(Map param);
	/**
	 * 综合查询退款集合.
	 * 
	 * @param param
	 * @return
	 */
	public List<OrdRefundment> findOrdRefundByParam(Map param,int skipResults,int maxResults);
	/**
	 * 综合查询退款额总数
	 * @param param
	 * @return
	 */
	java.math.BigDecimal findOrdfundByParamSumAmount(Map param);
	
	/**
	 * 统计退款单List总退款金额.
	 * @param ordRefundmentList 退款单List.
	 * @return 总退款金额.
	 */
	float countOrdRefundSumAmount(List<OrdRefundment> ordRefundmentList);
	/**
	 * 查询退款申请
	 * @param param
	 * @return
	 */
	public List<OrdRefundment> queryRefundment(Map param);
	/**
	 * 查询退款申请
	 * @param param
	 * @return
	 */
	public List<OrdRefundment> queryVstRefundment(Map param);
	/**
	 * 查询订单下的销售产品
	 * @param param
	 * @return
	 */
	public List<OrdOrderItemProd> queryProds(Long order);
	/**
	 * 查询退款申请
	 * @param param
	 * @return
	 */
	public Long queryRefundmentCount(Map param);
	/**
	 * 查询退款申请
	 * @param param
	 * @return
	 */
	public Long queryVstRefundmentCount(Map param);
	
	/**
	 * 查询退款申请明细
	 * @param refundMentId 退款单ID
	 * @return
	 */
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaList(Long refundMentId);
	/**
	 * 查询VST退款申请明细
	 * @param refundmentId 退款单ID
	 * @return
	 */
	public List<OrdOrderItemMeta> queryVstOrdOrderItemMetaList(Long refundmentId);
	
	public List<OrdRefundMentItem> queryOrdRefundmentItemsByRefundmentId(Long refundMentId);
	
	public boolean updateRefundStatus(Long refundmentId, String status);
	
	public boolean insertOrdRefundmentItem(Map map);
	public boolean updateOrdRefundmentItem(Map map);
	
	/**
	 * 查询退款单
	 * @param param
	 * @return
	 */
	public List<OrdRefundment> queryRefundmentList(Map param);
	/**
	 * 查询退款单
	 * @param param
	 * @return
	 */
	public List<OrdRefundment> queryVstRefundmentList(Map param);
	
	public Long queryRefundmentListCount(Map param);
	
	public Long queryVstRefundmentListCount(Map param);
	
	public boolean updateOrderStatus(Long orderId, String status);
	
	public void insertLog(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content);

	public List<OrdRefundment> queryRefundmentByOrderId(Map param);
	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	public Constant.APPLY_REFUNDMENT_RESULT applyRefund(Long orderId, Long saleServiceId,
			List<OrdOrderItemMeta> orderItemMetaList, Long amount, String refundType,
			String refundStatus, String reason, String operatorName, Long penaltyAmount);
	
	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	public Constant.APPLY_REFUNDMENT_RESULT applyRefundVst(Long orderId, Long saleServiceId,
			List<VstOrdOrderItem> vstOrdOrderItemsList, Long amount, String refundType,
			String refundStatus, String reason, String operatorName, Long penaltyAmount);

	/**
	 * 根据订单号查询采购产品经理的名称
	 * @param param
	 * @return
	 */
	public List<String> queryManagerNameList(Long orderId);
	
	/**
	 * 根据订单子子项查询退款列表
	 * @param orderItemMetaId
	 * @return
	 */
	public List<OrdRefundMentItem> queryRefundMentItem(final Long orderItemMetaId);
	
	public void refundApproveSuccess(OrdRefundment ordrefundment);
	
	/**
	 * 退款成功后拆分退款金额
	 * @param ordrefundment
	 */
	public void refundAmountSplit(final OrdRefundment ordrefundment);
	/**
	 * 退款成功后拆分退款金额
	 * @param ordrefundment
	 */
	public void vstRefundAmountSplit(final OrdRefundment ordrefundment);
	
}
