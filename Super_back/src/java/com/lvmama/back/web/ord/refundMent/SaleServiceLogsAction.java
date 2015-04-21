package com.lvmama.back.web.ord.refundMent;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
 
/**
 * 售后服务日志记录.
 * 
 * 
 * @author 张文君
 * @version 
 */
public class SaleServiceLogsAction extends BaseAction{

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
	 * saleServiceId
	 */
	private Long objectId;

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public void doBefore(){
		comLogList = comLogService.queryComLogSaleService(objectId, "SALE_SERVICE", Constant.COM_LOG_SALE_SERVICE.ordSaleServiceComplete);
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
