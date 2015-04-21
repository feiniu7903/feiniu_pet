package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.vo.Page;

public interface FinGLService {

	/**
	 * 根据批次号获取对账数据(未发送的数据)
	 * 
	 * @return 结果集合
	 */
	public List<FinGLInterface> queryByBatch(Map<String,Object> map);
	
	public Page<FinGLInterface> queryForPage(Map<String, Object> map);
	
	/**
	 * 更新对账数据
	 * 
	 * @param 批次号
	 * */
	public void updateStatus(Map<String, Object> map);
	
	/**
	 * 更新对账数据
	 * 
	 * @param 状态
	 * */
	public void updateStatusNull(Map<String, Object> map);

	/**
	 * 根据条件查询对账数据
	 * 
	 * @return 结果集合
	 */
	public List<FinGLInterface> query(Map<String, Object> paraMap);

	/**
	 * 根据条件查询对账数据
	 * 
	 * @return 总数
	 */
	public Long selectRowCount(Map<String, Object> paraMap);

	/**
	 * 更新对账数据
	 * 
	 * @param 对账接口信息
	 * */
	public void update(FinGLInterface obj);

	/**
	 * 生成总账数据
	 */
	public void generateGLInterfaceByReconResult(FinBizItem finBizItem);
	
	/**
	 * 生成确认收入，总账数据
	 * 
	 */
	public void generateGLInterfaceByOrderGlStatus();
	/**
	 * 生成对账数据
	 */
	public void generateGLData();
	
	/**
	 * 查找有问题的订单号
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryDayCountMisOrder(Map<String, Object> map);
	
	/**
	 * 统计系统入账信息
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryDayCount(Map<String, Object> map);
	
	public void generateGLDataBeforeCleanOldData();
	
	public int delete(Map<String,Object> deleteParameters);
	
	public Page queryOrderAccount(Map<String,Object> map);
	
	public Page selectFinanceOrderMonitorPage(Map<String,Object> map);
}
