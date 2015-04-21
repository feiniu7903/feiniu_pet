package com.lvmama.order.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderRefundmentDAO;

/**
 * OrdRefundmentDAOImpl.
 *
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see static com.lvmama.UtilityTool#isValid(object)
 * @see com.lvmama.ord.po.OrdRefundment
 * @see com.lvmama.order.dao.OrderRefundmentDAO
 */
public class OrderRefundmentDAOImpl extends BaseIbatisDAO implements
		OrderRefundmentDAO {

	/**
	 * 根据订单ID和状态查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *            订单ID
	 * @param status
	 *            状态
	 * @param gatewayTradeNo
	 *            网关的交易号
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryOrdRefundmentByOrderIdAndStatus(
			final Long orderId, final String status, final String gatewayTradeNo) {
		final Map<String, String> map = new HashMap<String, String>();
		if (UtilityTool.isValid(orderId)) {
			map.put("orderId", orderId.toString());
		}
		map.put("status", status);
		map.put("gatewayTradeNo", gatewayTradeNo);
		map.put("sysCode", Constant.COMPLAINT_SYS_CODE.SUPER.name());
		return super.queryForList(
				"ORD_REFUNDMENT.queryOrdRefundmentByOrderIdAndStatus", map);
	}
	
	/**
	 * 根据订单ID和状态查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *            订单ID
	 * @param status
	 *            状态
	 * @param gatewayTradeNo
	 *            网关的交易号
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryVstOrdRefundmentByOrderIdAndStatus(final Long orderId, final String status, final String gatewayTradeNo) {
		final Map<String, String> map = new HashMap<String, String>();
		if (UtilityTool.isValid(orderId)) {
			map.put("orderId", orderId.toString());
		}
		map.put("status", status);
		map.put("gatewayTradeNo", gatewayTradeNo);
		map.put("sysCode", Constant.COMPLAINT_SYS_CODE.VST.name());
		return super.queryForList(
				"ORD_REFUNDMENT.queryOrdRefundmentByOrderIdAndStatus", map);
	}

	/**
	 * 根据 批次号查询{@link OrdRefundment}.
	 *
	 * @param refundmentBatchId
	 *            批次号
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryOrdRefundmentByRefundmentBatchId(
			final Long refundmentBatchId) {
		return super.queryForList(
				"ORD_REFUNDMENT.queryOrdRefundmentByRefundmentBatchId",
				refundmentBatchId);
	}

	/**
	 * 根据refundmentId更新status.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            status
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusById(final Long refundmentId,
			final String status) {
		final Map<String, String> map = new HashMap<String, String>();
		if (UtilityTool.isValid(refundmentId)) {
			map.put("refundmentId", refundmentId.toString());
		}
		map.put("status", status);
		return (super.update(
				"ORD_REFUNDMENT.updateOrdRefundmentStatusById", map) == 1) ? true
				: false;
	}

	/**
	 * 更加refundmentId更新status和refundTime.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            新status
	 * @param refundTime
	 *            新refundTime
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusAndRefundTimeById(
			final Long refundmentId, final String status, final Date refundTime) {
		final Map<String, Object> map = new HashMap<String, Object>();
		if (UtilityTool.isValid(refundmentId)) {
			map.put("refundmentId", refundmentId.toString());
		}
		map.put("status", status);
		map.put("refundTime", refundTime);
		return (super.update(
				"ORD_REFUNDMENT.updateOrdRefundmentStatusAndRefundTimeById",
				map) == 1) ? true : false;
	}






	// 支付需要的接口-------------------------------------------------------
	/**
	 * 保存新的{@link OrdRefundmentItem}.
	 *
	 * @param paramMap
	 *            退款单ID + 订单子子项ID
	 * @return 新{@link OrdRefundmentItem}的ID
	 */
	@Override
	public Long insertOrdRefundmentItem(Map<String, Long> paramMap){
		return Long.valueOf(super.insert(
				"ORD_REFUNDMENT.insertOrdRefundmentItem", paramMap).toString());
	}

	/**
	 * 删除 OrdRefundmentItem}.
	 *
	 * @param refundmentId
	 *            退款单ID
	 * @return 删除的记录数
	 */
	@Override
	public int deleteOrdRefundmentItemByRefundmentId(Long refundmentId){
		return super.delete("ORD_REFUNDMENT.deleteOrdRefundmentItemByRefundmentId", refundmentId);
	}

	/**
	 * 保存新的{@link OrdRefundment}.
	 *
	 * @param ordRefundment
	 *            ordRefundment
	 * @return 新{@link OrdRefundment}的ID
	 */
	@Override
	public Long saveOrdRefundment(final OrdRefundment ordRefundment) {
		return Long.valueOf(super.insert(
				"ORD_REFUNDMENT.save", ordRefundment).toString());
	}

	/**
	 * 自动生成退款单明细
	 * @param orderId 订单号
	 * @param refundmentId 退款单号
	 * @param type 退款单明细类型
	 * @param amount 金额
	 */
	public void saveOrdRefundmentItemByOrderId(final Long orderId,final Long refundmentId,final String type,final Long amount){
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("refundmentId", refundmentId);
		map.put("type", type);
		map.put("amount", amount);
		super.insert("ORD_REFUNDMENT.saveOrdRefundmentItemByOrderId",map);
	}
	
	public void saveOrdRefunementItem(OrdRefundMentItem item){
		super.insert("ORD_REFUNDMENT_ITEM.insert",item);
	}
	/**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	@Override
	public OrdRefundment queryOrdRefundmentById(final Long refundmentId) {
		Object obj = super.queryForObject(
				"ORD_REFUNDMENT.queryOrdRefundmentById", refundmentId);

		if(obj != null)
			return (OrdRefundment) obj;
		else
			return null;
	}
	
	/**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	@Override
	public OrdRefundment queryAbroadHotelOrdRefundmentById(final Long refundmentId) {
		Object obj = super.queryForObject(
				"ORD_REFUNDMENT.queryAbroadHotelOrdRefundmentById", refundmentId);

		if(obj != null)
			return (OrdRefundment) obj;
		else
			return null;
	}

	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *
	 * @return {@link List<OrdRefundment>}
	 */
	@Override
	public List<OrdRefundment> findValidOrdRefundmentByOrderId(Long orderId)
	{
		Object obj = super.queryForList(
				"ORD_REFUNDMENT.findValidOrdRefundmentByOrderId", orderId);

		if(obj != null)
			return (List<OrdRefundment>) obj;
		else
			return null;
	}
	
	/**
	 * 根据订单号和状态查询退款单
	 * @param orderId
	 * @param status
	 * @return
	 */
	public List<OrdRefundment> findOrderRefundmentByOrderIdStatus(Long orderId,String status){
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("status", status);
		return super.queryForList(
				"ORD_REFUNDMENT.findOrderRefundmentByOrderIdStatus", map);
	}
	
	public List<OrdRefundMentItem> findOrderRefundMentItemByOrderItemMetaId(Long orderItemMetaId){
		return super.queryForList(
				"ORD_REFUNDMENT.findOrderRefundMentItemByOrderItemMetaId", orderItemMetaId);
	}
	/**
	 * 根据orderId查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *
	 * @return {@link List<OrdRefundment>}
	 */
	@Override
	public List<OrdRefundment> findRefundOrdRefundmentByOrderId(Long orderId)
	{
		Object obj = super.queryForList(
				"ORD_REFUNDMENT.findRefundOrdRefundmentByOrderId", orderId);

		if(obj != null)
			return (List<OrdRefundment>) obj;
		else
			return null;
	}
	

	/**
	 * 根据refundmentEventId查询{@link OrdRefundment}.
	 *
	 * @param refundmentEventId
	 *
	 * @return {@link OrdRefundment}
	 
	@Override
	public OrdRefundment findOrdRefundmentByOrdRefundmentEventId(Long refundmentEventId)
	{
		Object obj = super.queryForObject(
				"ORD_REFUNDMENT.findOrdRefundmentByOrdRefundmentEventId", refundmentEventId);

		if(obj != null)
			return (OrdRefundment) obj;
		else
			return null;
	}
	 */
	/**
	 * 更新ordRefundment.
	 *
	 * @param ordRefundment
	 *
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundment(OrdRefundment ordRefundment){
		int row = super.update("ORD_REFUNDMENT.updateOrdRefundmentByPrimaryKey", ordRefundment);

		if(row == 1)
			return true;
		else
			return false;
	}

	/**
	 * 按refundmentId更新status和memo.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            新status
	 * @param memo
	 *            备注
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	@Override
	public boolean updateOrdRefundmentStatusAndMemoById(Long refundmentId,
			String status, String memo, String operatorName) {
		Map paramMap = new HashMap();
		paramMap.put("refundmentId", refundmentId);
		paramMap.put("status", status);
		paramMap.put("operatorName", operatorName);
		if(memo != null && !"".equalsIgnoreCase(memo.trim()))
			paramMap.put("memo", memo);

		int row = super.update("ORD_REFUNDMENT.updateOrdRefundmentStatusAndMemoById", paramMap);

		if(row == 1)
			return true;
		else
			return false;
	}

	/**
	 * 根据订单ID查询{@link OrdRefundment}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return {@link OrdRefundment}列表
	 */
	@Override
	public List<OrdRefundment> queryOrdRefundmentByOrderId(final Long orderId) {
		return super.queryForList(
				"ORD_REFUNDMENT.queryOrdRefundmentByOrderId", orderId);
	}
	
	/**
	 * 查询订单的已退款总金额
	 * @param orderId
	 * @return
	 */
	public Long findOrdRefundmentAmountByOrderId(Long orderId){
		return (Long)super.queryForObject("ORD_REFUNDMENT.findOrdRefundmentAmountByOrderId",orderId);
	}
	
	/**
	 * 根据orderId删除退款单
	 * @param orderId
	 * @return
	 */
	public boolean deleteRefundment(Long refundmentId){
		try{
			Map map = new HashMap();
			map.put("refundmentId", refundmentId);
			super.delete("ORD_REFUNDMENT.deleteOrdRefundmentByOrderId", map);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 根据refundmentId删除退款单明细 
	 * @param refundmentId
	 * @return
	 */
	public boolean deleteRefundmentItem(Long refundmentId){
		try{
			super.delete("ORD_REFUNDMENT.deleteOrdRefundmentItemByRefundmentId", refundmentId);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean updateRefundment(OrdRefundment ordRefundment) {
		try {	
			Map map = new HashMap();
			map.put("refundmentId", ordRefundment.getRefundmentId());
			map.put("amount", ordRefundment.getAmount());
			map.put("memo", ordRefundment.getMemo());
			map.put("operatorName", ordRefundment.getOperatorName());
			map.put("penaltyAmount", ordRefundment.getPenaltyAmount());
			map.put("refundStatus", ordRefundment.getStatus());
			super.insert("ORD_REFUNDMENT.updateRefundment", map);
			
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<OrdRefundMentItem> queryOrdRefundmentItemById(Long refundmentId) {
		return super.queryForList("ORD_REFUNDMENT.queryOrdRefundmentItemByRefundmentId",refundmentId);
	}

	/**
	 * @deprecated
	 */
	@Override
	public Long queryRefundAmountSum(Long orderId) {
		return (Long)super.queryForObject("ORD_REFUNDMENT.queryRefundAmountSum",orderId);
	}
	@Override
	public Long queryRefundAmountSumByOrderIdAndSysCode(Long orderId, String sysCode) {
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("sysCode", sysCode);
		return (Long)super.queryForObject("ORD_REFUNDMENT.queryRefundAmountSumByOrderIdAndSysCode",map);
	}
}
