package com.lvmama.back.web.abroadhotel.refundMent;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
 
/**
 * 确认的日志记录.
 * 
 */
public class AbroadRefundmentLogsAction extends BaseAction{

	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = -4498270645703033701L;
	
	/**
	 * 日志的列表.
	 */
	private List<ComLog> comLogList;
	/**
	 * 加载comLogService.
	 */
	private ComLogService comLogService;
	
	/**
	 * 退款单ID
	 */
	private Long refundmentId;
	private String logType;
	
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Long getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public void doBefore(){
		comLogList = comLogService.queryByObjectId("ABROADHOTEL_ORD_REFUNDMENT",refundmentId);
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
