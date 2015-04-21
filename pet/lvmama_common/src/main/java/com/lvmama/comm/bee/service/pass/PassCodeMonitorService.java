package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;

/**
 * @author ShiHui
 */
public interface PassCodeMonitorService {
	/**
	 * 通过通关码编号查询到通关点信息.
	 */
	public PassCode getPassCodeByCodeId(Long codeId);
	/**
	 * 为订单的每一个履行对象申请码
	 */
	//public void applyCodeForPerform(OrdOrder order);
	
	public PassEvent destroyCode(Long codeId);
	
	/**
	 *  为订单对象申请码
	 */
	public List<PassCode> applyCodeForOrder(OrdOrder order,Map<Long,List<SupPerformTarget>> supPerformTargetList);
	
	/**
	 * 为单独的一个码重新申请
	 * @param codeId
	 * @return
	 */
	public Long reApplyCode(Long codeId);
	
	/**
	 * 为同一个码重新申码
	 * @param codeId
	 */
	public void reApplyCodeUseSameSerialNo(Long codeId);
	/**
	 * 废码处理
	 * 
	 * @param orderId
	 */
	public List<PassEvent> destroyCode(OrdOrder order) ;
	/**
	 * 更新内容
	 * @param order
	 */
	public List<PassEvent> updateContent(OrdOrder order);
	
	/**
	 * 修改人数
	 * @param order
	 */
	public List<PassEvent> updatePerson(OrdOrder order);
	
	/**
	 * 更新订单联系人信息
	 * @param order
	 */
	public List<PassEvent> updateContact(OrdOrder order);
	/**
	 * 重发短信
	 * @param order
	 * @return
	 */
	public List<PassEvent> resend(OrdOrder order);
	/**
	 * 重发短信
	 * @param codeId
	 * @return
	 */
	public PassEvent resend(Long codeId);
	
	/**
	 * 通过时间查询重新申请码记录
	 * @param 
	 * @return
	 */
	public List<PassCode> selectByReapplyTime();
	/**
	 * 查询已经支付了但是没有收到支付消息的订单
	 * @return
	 */
	public List<Object> selectReapplyOrder();
	

	/**
	 * 判断订单或订单子指项是否已经申请成功过码
	 * @param params
	 * @return
	 */
	public boolean hasSuccessCode(Long orderId,long codeTotal);
	/**
	 * 订单是否已经做过申请
	 * @param orderId
	 * @return
	 */
	public boolean hasApply(Long orderId);
	
	/**
	 * 更新码的状态
	 * @param passCode
	 * @param data
	 * @param isPart 是否是部分履行
	 */
	public void updatePassPortCode(PassCode passCode, Passport data, boolean isPart);
}
