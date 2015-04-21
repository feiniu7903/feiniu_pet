package com.lvmama.back.web.ord.refundMent;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
 
/**
 * 日志记录.
 * 
 * 
 * @author 张文君
 * @see com.lvmama.common.com.po.ComLog;
 * @see com.lvmama.common.ord.po.OrdSettlement;
 * @see com.lvmama.common.ord.po.OrdSubSettlement;
 * @see com.lvmama.back.service.ComLogService;
 * @see com.lvmama.common.ord.service.OrderService;
 * @see com.lvmama.common.ord.service.po.CompositeQuery;
 */
public class OrdRefundmentItemLogsAction extends BaseAction{

	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = -4498270645703033701L;
	
	/**
	 * 日志的列表.
	 */
	private List<ComLog> comLogList;
	/**
	 * 加载订单服务.
	 */
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 加载comLogService.
	 */
	private ComLogService comLogService;
	
	/**
	 * 退款单明细ID
	 */
	private Long refundmentItemId;

	public Long getRefundmentItemId() {
		return refundmentItemId;
	}

	public void setRefundmentItemId(Long refundmentItemId) {
		this.refundmentItemId = refundmentItemId;
	}

	public void doBefore(){
		comLogList = comLogService.queryByObjectId("ORD_REFUNDMENT_ITEM", refundmentItemId);
	}
	
	/**
	 * 获取日志列表数据.
	 * 
	 * @return comLogList
	 *          日志列表数据
	 */
	public List<ComLog> getComLogList() {
		return comLogList;
	}
	/**
	 * 设置日志列表数据.
	 * 
	 * @param comLogList
	 *           日志列表数据
	 */
	public void setComLogList(List<ComLog> comLogList) {
		this.comLogList = comLogList;
	}
	
	public ComLogService getComLogService() {
		return comLogService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
