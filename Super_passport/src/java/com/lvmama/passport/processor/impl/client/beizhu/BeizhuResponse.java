package com.lvmama.passport.processor.impl.client.beizhu;

import org.apache.commons.lang.StringUtils;

/**
 * <table>
 * <tr>
 * <td>名称</td>
 * <td>类型</td>
 * <td>描述</td>
 * </tr>
 * <tr>
 * <td>res</td>
 * <td>number</td>
 * <td>返回结果值（数字）0表示成功 其它都说明有错误</td>
 * </tr>
 * <tr>
 * <tr>
 * <td>state</td>
 * <td>number</td>
 * <td>订单状态：UNUSED：未用，USED：已用，CANCEL：已退</td>
 * </tr>
 * <td>order_id</td>
 * <td>number</td>
 * <td>贝竹一证通订单号（如果不成功则没有订单）</td> </tr>
 * <tr>
 * <td>other_id</td>
 * <td>number</td>
 * <td>合作网站的订单号</td>
 * </tr>
 * </table>
 * 
 * @author zhangkexing
 */
public class BeizhuResponse {
	private String res; // 返回结果值（数字）0表示成功,其它对照常量表
	private String order_id; // 贝竹一证通订单号
	private String other_id; // 合作网站(驴妈妈)的订单号
	private int num; // 可用张数（如果客人买了3张，已经使用2张，可用为1张）
	private String state;//贝竹方的订单状态
	public boolean operateOrderSuccess() {
		return StringUtils.equals(res, "0") && StringUtils.isNotEmpty(order_id);
	}

	public boolean used() {
		return StringUtils.equals(state, BeizhuConstant.BEIZHU_ORDER_STATUS.USED.name());
	}
	
	public boolean unused() {
		return StringUtils.equals(state, BeizhuConstant.BEIZHU_ORDER_STATUS.UNUSED.name());
	}

	public boolean canceled() {
		return StringUtils.equals(state, BeizhuConstant.BEIZHU_ORDER_STATUS.CANCEL.name());
	}
	
	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOther_id() {
		return other_id;
	}

	public void setOther_id(String other_id) {
		this.other_id = other_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeizhuResponse [res=");
		builder.append(res);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", other_id=");
		builder.append(other_id);
		builder.append(", num=");
		builder.append(num);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}

}
