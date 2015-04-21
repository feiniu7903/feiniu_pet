package com.lvmama.passport.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.pet.vo.Page;

/**
 * 通关点
 * 
 * @author chenlinjun
 * 
 */
@SuppressWarnings("unchecked")
public class PassCodeDAO extends BaseIbatisDAO {
	private static final Log log = LogFactory.getLog(PassCodeDAO.class);
	public PassCodeDAO() {
		super();
	}


	/**
	 * 通过主键查询通关点信息
	 * 
	 * @param serialNo
	 * @return
	 */
	public PassCode getPassCodeByCodeId(Long codeId) {
		return (PassCode) super.queryForObject(
				"PASS_CODE.getPassCodeByCodeId", codeId);
	}

	/**
	 * 通过订单查询通关点信息
	 * 
	 * @param serialNo
	 * @return
	 */

	public List<PassCode> getPassCodeByOrderId(Long orderId) {
		return super.queryForList(
				"PASS_CODE.getPassCodeByOrderId", orderId);
	}
	
	/**
	 * 通过订单编号和状态为申码成功查询通关点信息(返回通关码及通关编号等详细信息)
	 * 
	 * @param serialNo
	 * @return
	 */

	public PassCode getPassCodeByOrderIdStatus(Long orderId) {
		return (PassCode)super.queryForObject(
				"PASS_CODE.PassCode_selectByOrderIdStatus", orderId);
	}

	
	/**
	 * 查询通关点信息
	 * 
	 * @param params
	 * @return
	 */

	public List<PassCode> selectPassCodeByParams(Map<String,Object> params) {
		if (params.get("_startRow")==null) {
			params.put("_startRow", 0);
		}
		if (params.get("_endRow")==null) {
			params.put("_endRow", 20);
		}
		return super.queryForList(
				"PASS_CODE.PassCode_selectByParams", params);
	}

	/**
	 * 通过服务商和游玩日期查询通关点信息
	 * 
	 * @param params
	 * @return
	 */

	public List<PassCode> selectCodeByProviderIdAndValidTime(Map<String,Object> params) {
		return super.queryForList(
				"PASS_CODE.selectCodeByProviderIdAndValidTime", params);
	}
	
	/**
	 * 查询离线模式通关信息列表
	 * @param params
	 * @return
	 */
	public List<PassCode> selectVouchersByProviderId(Map<String,Object> params) {
		return super.queryForList(
				"PASS_CODE.selectVouchersByProviderId", params);
	}
	
//------------------------------------------------------------------------------------------------------------------	
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */
	public List<PassCode> selectByParams(Map<String, Object> params) {
		return super.queryForList(
				"PASS_CODE.PassCode_selectByParams", params);
	}
	/**
	 *  查询通关点信息记录数
	 * @param params
	 * @return
	 */
	public Integer selectRowCount(Map<String,Object> params){
		Integer count = 0;
		count = (Integer) super.queryForObject("PASS_CODE.selectRowCount",params);
		return count;
	}
	/**
	 * 通过申请流水号查询通关码信息
	 * 
	 * @param 查询参数
	 */
	public PassCode getCodeBySerialNo(String serialNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serialNo", serialNo);
		return (PassCode) super.queryForObject(
				"PASS_CODE.PassCode_selectBySerialNo", params);
	}
	/**
	 * 通过EventId查询通关码信息
	 * 
	 * @param 查询参数
	 */
	public PassCode getCodeByEventId(Long eventId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("eventId", eventId);
		return (PassCode) super.queryForObject(
				"PASS_CODE.PassCode_selectByEventId", params);
	}
	/**
	 * 通过组合参数查询通关码信息
	 * 
	 * @param 查询参数
	 */
	public PassCode getPassCodeByParams(Map<String, Object> params ) {
		return (PassCode) super.queryForObject(
				"PASS_CODE.PassCode_selectPassCodeByAddCode", params);
	}
	/**
	 * 通过辅助码MD5编号查询通关码信息
	 * 
	 * @param 查询参数
	 */
	public PassCode getCodeByAddCodeMd5(String addCodeMd5) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addCodeMd5", addCodeMd5);
		return (PassCode) super.queryForObject(
				"PASS_CODE.PassCode_selectByAddCodeMd5", params);
	}
	/**
	 * 新增
	 * 
	 * @param PassCode对象
	 * @update clj 2010-9-20
	 */
	public Long addPassCode(PassCode passCode) {
		Long newKey = (Long) super.insert(
				"PASS_CODE.insertSelective", passCode);
		return newKey;
	}

	/**
	 * 修改
	 * 
	 * @update clj 2010-9-20
	 */
	public void updatePassCode(PassCode passCode) {
		super.update(
				"PASS_CODE.updateByPrimaryKeySelective", passCode);
	}

	/**
	 * 修改
	 * 
	 * @update clj 2010-9-20
	 */
	public void updatePassCodeBySerialNo(PassCode passCode) {
		super.update(
				"PASS_CODE.updateBySerialNo", passCode);
	}
	/**
	 * 删除
	 */
	public void deletePassCode(Long codeId) {
		super.delete(
				"PASS_CODE.PassCode_deletePassCode", codeId);
	}

	/**
	 * 根据状态查询申请码请求的队列数据
	 * 
	 * @param 查询参数
	 */
	public List<PassCode> queryApplyCodeListByStuts(String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return super.queryForList(
				"PASS_CODE.PassCode_selectByStatus", params);
	}

	/**
	 * 根据状态查询其他非申请码请求的队列数据
	 * 
	 * @param 查询参数
	 */
	public List<PassCode> queryEventListByStuts(String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return super.queryForList(
				"PASS_CODE.PassCode_selectEventByStatus", params);
	}
	/**
	 * 生成申请流水号
	 * @return
	 */
	public Long getSerialNo() {
		return (Long)super.queryForObject(
				"PASS_CODE.PassCode_getSerialNo");
	}
	/**
	 * 生成辅助码
	 * @return
	 */
	public String getAssistNo() {
		String assistNo="";
		Long no= (Long)super.queryForObject(
				"PASS_CODE.PassCode_getAssistNo");
		String num="000000000000";
		String temp=String.valueOf(no);
		int len=temp.length();
		assistNo=num.substring(len,num.length())+temp;
		log.info("生成辅助码:"+assistNo);
		return assistNo;
	}
	/**
	 * 查询订单是否已经履行
	 * @param params
	 * @return
	 */
	public boolean hasPerform(Map<String, Object> params ){
		Integer count = 0;
		count= (Integer)super.queryForObject(
		"PASS_CODE.selectPerformTotalByObjectId",params);
		return count>0;
	}
	
	/**
	 * 通过时间查询重新申请码记录
	 * @param params
	 * @return
	 */
	public List<PassCode> selectByReapplyTime() {
		return super.queryForList(
				"PASS_CODE.selectByReapplyTime");
	}
	/**
	 * 查询已经支付了但是没有收到支付消息的订单
	 * @return
	 */
	public List<Object> selectReapplyOrder() {
		return super.queryForList(
				"PASS_CODE.selectReapplyOrder");
	}
	/**
	 * 判断订单或订单子指项是否已经申请成功过码
	 * @param params
	 * @return
	 */
	public int successCodeNum(Map<String, Object> params ){
		Integer count = 0;
		count= (Integer)super.queryForObject(
		"PASS_CODE.selectApplyCount",params);
		return count;
	}
	/**
	 * 修改此订单申请码的个数
	 * @param orderId
	 * @param codeTotal
	 */
	public void updateCodeTotalByOrder(Long orderId,long codeTotal){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("codeTotal", codeTotal);
		super.update(
				"PASS_CODE.updateCodeTotalByOrder", params);
		
	}
/**
 * 批量修改
 * @param passCodes
 * @return
 */
	public int updateBatch(final List<PassCode> passCodes) {
		int count=0;
		try {
			if (passCodes != null) {
				count=(Integer)super.execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (PassCode passCode : passCodes) {
							executor.update("PASS_CODE.updateBatchStauts", passCode);
						}
						int flag=executor.executeBatch();
						return flag;
					}
				});
			}
		} catch (Exception e) {
			log.error("批量更新异常", e);
		}
		return count;
	}


	public Integer getSyncUpdatePassCodeCount(Map<String, Object> queryOption) {
		return super.update("PASS_CODE.getSyncUpdatePassCount", queryOption);
	}


	public Integer getUpdatePassCodeByCodeId(Map<String, Object> queryOption) {
		return super.update("PASS_CODE.getUpdatePassCountByCodeId", queryOption);
	}


	public Integer getUpdatePassCodeBySerId(Map<String, Object> queryOption) {
		return super.update("PASS_CODE.getUpdatePassCountBySerId", queryOption);
	}
	
	public Page<PassCode> selectAutoPerform(Map parameterObject){
		return super.queryForPage("PASS_CODE.selectAutoPerform",parameterObject);
	}
	
////////////////////////

	/**
	 * 根据条件查询
	 */
	public List<PassCode> queryPassCodeByParam(Map<String,Object> params) {
		return super.queryForList("PASS_CODE.queryPassCodeByParam", params);
	}
	public List<PassCode> getPassCodeByAddCode(String addCode) {
		return super.queryForList("PASS_CODE.selectPassCodeByAddCode", addCode);
	}
	public List<PassCode> getPassCodeByOrderIdStatusList(Long orderId) {
		return super.queryForList(
				"PASS_CODE.PassCode_selectByOrderIdStatus", orderId);
	}
	public List<PassCode> getPassCodeByOrderIdAndTargetIdList(Long orderId,Long targetId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", orderId);
		params.put("targetId", targetId);
		return super.queryForList(
				"PASS_CODE.PassCode_selectByOrderIdAndTargetId", params);
	}


	public boolean checkCodeHasExisting(String codeType,String code) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("code", code);
		params.put("codeType", codeType);
		return super.queryForObject("PASS_CODE.checkCodeHasExisting", params)!=null;
	}


	public boolean destoryCode(String outStance,String destoryStatus) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("serialNo", outStance);
		params.put("status", destoryStatus);
		return super.update("PASS_CODE.destoryCode", params)>0;
	}

	public List<String> getAddCodesByEBK(Map params) {
		return super.queryForList("PASS_CODE.getAddCodesByEBK", params,true);
	}


	public boolean addCodeExisting(String addCode) {
		return super.queryForObject("PASS_CODE.selectPassCodeByAddCode",addCode)!=null;
	}


	public Object addCodeIsInTarget(Map<String, Object> param) {
		return super.queryForObject("PASS_CODE.addCodeIsInTarget",param);
	}
	
	public List<PassCode> selectPassCodesByOrderId(Long orderId) {
		return super.queryForList("PASS_CODE.selectPassCodesByOrderId", orderId);
	}
	
	public Integer selectRowCountByOrderId(Map<String,Object> params){
		Integer count = 0;
		count = (Integer) super.queryForObject("PASS_CODE.selectRowCountByOrderId",params);
		return count;
	}
	
	public List<PassCode> getPassCodeBysupplierId(Map<String,Object> params) {
		return super.queryForList(
				"PASS_CODE.PassCode_selectBySupplierId", params);
	}
	
	public List<PassCode> selectPassCodeListByOrderIdAndStatus(Map<String,Object> params) {
		return super.queryForList(
				"PASS_CODE.selectPassCodeListByOrderIdAndStatus", params);
	}
}

