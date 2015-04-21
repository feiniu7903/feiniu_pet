package com.lvmama.passport.processor.impl.client.wuxiloch.model;
/**
 * json 订单参数
 * @author haofeifei
 *
 */
public class Order {
	private Integer goods_id ;//商品编号
	private Integer Spec_id;//sku编号
	private Double Price;//成交价
	
	/**
	 * (如果成交价格为0，这个订单就是景区到付订单，成本价就为景区到付需要支付的金额)
	 */
	private Double Cost;//成本价
	private Integer Num;//购买数量
	private Long exp_time;//购买时间
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public Integer getSpec_id() {
		return Spec_id;
	}
	public void setSpec_id(Integer spec_id) {
		Spec_id = spec_id;
	}
	public Double getPrice() {
		return Price;
	}
	public void setPrice(Double price) {
		Price = price;
	}
	public Long getExp_time() {
		return exp_time;
	}
	public void setExp_time(Long exp_time) {
		this.exp_time = exp_time;
	}
	public Double getCost() {
		return Cost;
	}
	public void setCost(Double cost) {
		Cost = cost;
	}
	public Integer getNum() {
		return Num;
	}
	public void setNum(Integer num) {
		Num = num;
	}
	


}
