package com.lvmama.back.web.log;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;

/**
 * 跳转显示日志详情.
 * 
 * @author huangl
 */
public class ViewJumpProductContentAction  extends BaseAction{
	/**
	 * 对象查看的类型.例:订单表ORD_ORDER
	 */
	private String objectType;
	/**
	 * 日志查看对像编号.例：订单编号.
	 */
	private long objectId;
	/**
	 * 父类编号.
	 */
	private long parentId;
	private ComLog comLog=new ComLog();

	public void doBefore(){
			comLog.setParentId(parentId);
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public ComLog getComLog() {
		return comLog;
	}

	public void setComLog(ComLog comLog) {
		this.comLog = comLog;
	}
}
