package com.lvmama.comm.bee.po.ord;
/**
 * @author shangzhengyuan
 * @description 订单行程固化POJO
 * @time 20120731
 * @version 在线预售权
 */
import java.io.Serializable;
import java.util.Date;

public class OrdOrderRouteTravel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2969545411171951379L;
	
	/**
	 * 行程固化ID
	 */
	private Long routeTravelId;
	/**
	 * 订单编号
	 */
	private Long orderId;
	/**
	 * 行程及产品说明详情xml，现在放到文件系统中，详情过大
	private String routeTravel;
	*/
	/**
	 * 关联的行程xml文件ID
	 */
	private Long fileId;
	/**
	 * 操作人
	 */
	private String createUser;
	/**
	 * 操作时间
	 */
	private Date createDate;
	public Long getRouteTravelId() {
		return routeTravelId;
	}
	public void setRouteTravelId(Long routeTravelId) {
		this.routeTravelId = routeTravelId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
}
