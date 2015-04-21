package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceDTO;
import com.lvmama.comm.pet.po.fin.FinGroupCostDTO;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Repository
@SuppressWarnings("unchecked")
public class FinGLInterfaceDAO extends BaseIbatisDAO {

	/**
	 * 生成对账数据
	 * 
	 * @return 结果集合
	 */
	public void insert(FinGLInterface finGLInterface) {
		super.insert("FIN_GL_INTERFACE.insert", finGLInterface);
	}
	
	/**
	 * 查询对账数据
	 * 
	 * @return 结果集合
	 */
	public List<FinGLInterface> searchRecord(Map<String, Object> map) {
		return super.queryForList("FIN_GL_INTERFACE.selectByBatch",map);
	}

	public Page<FinGLInterface> queryForPage(Map<String, Object> map){
		return super.queryForPage("FIN_GL_INTERFACE.selectByBatch", map);
	}
	/**
	 * 更新对账数据
	 * 
	 * @param 批次号
	 * */
	public void updateStatus(Map<String, Object> map) {
		super.update("FIN_GL_INTERFACE.updateStatus", map);
	}
	
	/**
	 * 更新对账数据
	 * 
	 * @param 状态
	 * */
	public void updateStatusNull(Map<String, Object> map) {
		super.update("FIN_GL_INTERFACE.updateStatusNull", map);
	}

	/**
	 * 查询对账数据
	 * 
	 * @return 总数
	 */
	public Long selectRowCount(Map<String, Object> map) {
		Long count = 0L;
		count = (Long) super.queryForObject("FIN_GL_INTERFACE.selectRowCount",
				map);
		return count;
	}

	/**
	 * 查询对账数据
	 * 
	 * @return 结果集合
	 */
	public List<FinGLInterface> searchRecordByCondition(Map<String, Object> map) {
		if (map.get("_startRow") == null
				|| !StringUtil.isNumber(map.get("_startRow").toString())) {
			map.put("_startRow", 0);
		}
		if (map.get("_endRow") == null
				|| !StringUtil.isNumber(map.get("_endRow").toString())) {
			map.put("_endRow", 20);
		}
		return super.queryForList("FIN_GL_INTERFACE.selectByCondition", map,Boolean.TRUE);
	}

	/**
	 * 获得预付款结果集参数
	 * 
	 * @return 结果集合
	 */
	public List<FinGLInterfaceDTO> selectPerPaymentParam(Long orderId) {
		return super.queryForList("FIN_GL_INTERFACE.selectPerPaymentParam", orderId);
	}

	/**
	 * 更新对账数据
	 * 
	 * @param 对账接口信息
	 * */
	public void update(FinGLInterface obj) {
		super.update("FIN_GL_INTERFACE.update", obj);
	}
	
	/**
	 * 根据订单ID查询退款对账信息
	 * @param orderId
	 * @return
	 */
	public List<FinGLInterfaceDTO> selectRefundedPordByAccountId(final Long accountId){
		return super.queryForList("FIN_GL_INTERFACE.selectRefundedPordByAccountId", accountId);
	}
	/**
	 * 根据订单ID查询补偿对账信息
	 * @param accountId
	 * @return
	 */
	public List<FinGLInterfaceDTO> selectRefundedCompensationByAccountId(final Long accountId){
		return super.queryForList("FIN_GL_INTERFACE.selectRefundedCompensationByAccountId", accountId);
	}
	
	/**
	 * 查询订单信息
	 * @param orderId 订单ID
	 * @return
	 */
	public Map<String,Object> selectOrderInfo(Long orderId){
		return (Map<String, Object>) super.queryForObject("FIN_GL_INTERFACE.selectOrderInfo", orderId);
	}
	
	public Long selectPaidNoForCancePrePay(Long orderId){
		return (Long) super.queryForObject("FIN_GL_INTERFACE.selectPaidNoForCancePrePay", orderId);
	}
	
	public Map<String,Object> selectPenaltyAmountByAccountId(final Long reconResultId){
		return super.queryForMap("FIN_GL_INTERFACE.selectOrdOrderItemProdByOrderId", reconResultId, null);
	}


	/**
	 * 查询订单废单重下相关信息
	 * @param orderId 订单ID
	 * @return
	 */
	public List<FinGLInterfaceDTO> selectCancelToCreateNewOrderInfo(Long orderId){
		return super.queryForList("FIN_GL_INTERFACE.selectCancelToCreateNewOrderInfo", orderId);
	}
	
	
	/**
	 * 更改订单对账状态
	 * 
	 * @param  
	 * */
	public void updateOrdOrder(Long orderId,String glStatus) {
		if (orderId!=null&&StringUtils.isNotEmpty(glStatus)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", orderId);
			param.put("glStatus", glStatus);
			
			super.update("FIN_GL_INTERFACE.updateOrdOrder", param);
		}
	}
	/**
	 * 更改订单子子项对账状态
	 * 
	 * @param  
	 * */
	public void updateOrdOrderItemMetaGLStatus(Long orderItemMetaId,String glStatus) {
		if (orderItemMetaId!=null&&StringUtils.isNotEmpty(glStatus)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderItemMetaId", orderItemMetaId);
			param.put("glStatus", glStatus);
			
			super.update("FIN_COST_GL_INTERFACE.updateOrdOrderItemMetaGLStatus", param);
		}
	}
	/**
	 * 取确认收入所需要的一些参数
	 * 
	 * @param code
	 * @return
	 */
	public List<FinGLInterfaceDTO> selectParamByGlStatus(String glStatus) {
		return super.queryForListForReport("FIN_GL_INTERFACE.selectParamByGlStatus", glStatus);
	}
	
	/**
	 * 去订单所属分公司
	 * 
	 * @param code
	 * @return
	 */
	public String selectFilialeName(Long orderId){
		return (String) super.queryForObject("FIN_GL_INTERFACE.selectFilialeName", orderId);
	}
	
	public FinGLInterfaceDTO selectOrderPayment(Long orderId){
		return (FinGLInterfaceDTO)super.queryForObject("FIN_GL_INTERFACE.selectOrderPayment", orderId);
	}
	
	public FinGLInterfaceDTO selectCancelOrderDate(Long orderId){
		return (FinGLInterfaceDTO)super.queryForObject("FIN_GL_INTERFACE.selectCancelOrderDate",orderId);
	}
	
	/**
	 * 查找有问题的订单号
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryDayCountMisOrder(Map<String, Object> map){
		return super.queryForList("FIN_GL_INTERFACE.queryDayCountMisOrder", map);
	}
	
	/**
	 * 统计系统入账信息
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryDayCount(Map<String, Object> map){
		return super.queryForList("FIN_GL_INTERFACE.queryDayCount", map);
	}
	
	public Page queryOrderAccount(Map<String,Object> map){
		return super.queryForPage("FIN_GL_INTERFACE.queryOrderAccount", map);
	}
	
	public Page selectFinanceOrderMonitorPage(Map<String,Object> map){
		Constant.RECON_GW_TYPE[] values = Constant.RECON_GW_TYPE.values();
		StringBuffer paymentGateWay =new  StringBuffer("'0'");
		final String SINGLE_QUOTATION_MARKS="'";
		final String COMMA_MARK=",";
		for(int i=0;i<values.length;i++){
			paymentGateWay.append(COMMA_MARK).append(SINGLE_QUOTATION_MARKS).append(values[i].getCode()).append(SINGLE_QUOTATION_MARKS);	
		}
		map.put("paymentGateWay", paymentGateWay.toString());
		return super.queryForPage("FIN_GL_INTERFACE.selectFinanceOrderMonitor", map);
	}
	/**
	 * 根据条件删除数据
	 * @return
	 */
	public int delete(Map<String, Object> map){
		return super.delete("FIN_GL_INTERFACE.deleteByParam", map);
	}
		
	public List<FinGroupCostDTO> queryGroupCostParam(Map<String,Object> map){
		return super.queryForList("FIN_COST_GL_INTERFACE.queryGroupCostParam",map);
	}
	public List<FinGroupCostDTO> queryOrderCostParam(final Map<String,Object> map){
		return super.queryForList("FIN_COST_GL_INTERFACE.queryOrderCostParam",map);
	}
	public List<FinGroupCostDTO> queryGroupCode(){
		return super.queryForList("FIN_COST_GL_INTERFACE.queryGroupCode");
	}
	
	public List<FinGLInterfaceDTO> queryOrderId(){
		return super.queryForList("FIN_COST_GL_INTERFACE.queryOrderId");
	}
	
	public FinGroupCostDTO queryMainProductItem(final Long orderId){
		return (FinGroupCostDTO)super.queryForObject("FIN_COST_GL_INTERFACE.queryMainProductItem", orderId);
	}
	public Long selectOrderUnSettleCount(final Long orderId){
		return (Long)super.queryForObject("FIN_COST_GL_INTERFACE.selectOrderUnSettleCount", orderId);
	}
}
