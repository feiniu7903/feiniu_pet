package com.lvmama.passport.processor.impl.client.newland.model;


public class GoodsRecord {
	private String pgoods_id;
	private String pgoods_name;
	private String apply_amount;
	private String used_amount;
    private String remain_amount;
    private String remain_times;
    private String use_mode;
    private String pGoodsGroup;
    private String applyPassword;
    private PGoodsList pGoodsList;
    private String spareField;
    /**
     * 废码
     * @return
     */
	public String toDestoyCodeXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<PGoodsID>").append(this.pgoods_id).append("</PGoodsID>");
		buf.append("<ApplyAmount>").append(this.apply_amount).append("</ApplyAmount>");
		return buf.toString();
	}
	/**
	 * 验证码请求
	 * @return
	 */
	public String toVerifyReqXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<PGoodsList>");
		for(String str:this.pGoodsList.getPgoodsId()){
			buf.append("<PGoodsID>").append(str).append("</PGoodsID>");
		}
		buf.append("</PGoodsList>");
		buf.append("<PGoodsGroup>").append(this.pGoodsGroup).append("</PGoodsGroup>");
		buf.append("<ApplyAmount>").append(this.apply_amount).append("</ApplyAmount>");
		return buf.toString();
	}
	
	/**
	 * 验证码响应
	 * @return
	 */
	public String toVerifyResXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<PGoodsID>").append(this.pgoods_id).append("</PGoodsID>");
		buf.append("<PGoodsName>").append(this.pgoods_name).append("</PGoodsName>");
		buf.append("<ApplyAmount>").append(this.apply_amount).append("</ApplyAmount>");
		buf.append("<UsedAmount>").append(this.used_amount).append("</UsedAmount>");
		buf.append("<RemainAmount>").append(this.remain_amount).append("</RemainAmount>");
		buf.append("<RemainTimes>").append(this.remain_times).append("</RemainTimes>");
		buf.append("<SpareField>").append(this.spareField).append("</SpareField>");
		buf.append("<UseMode>").append(this.use_mode).append("</UseMode>");
		return buf.toString();
	}
	
    /**
     * 回退响应
     * @return
     */
	public String toRollbackResXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<PGoodsID>").append(this.pgoods_id).append("</PGoodsID>");
		buf.append("<ApplyAmount>").append(this.apply_amount).append("</ApplyAmount>");
		return buf.toString();
	}
	
	public String getPgoods_name() {
		return pgoods_name;
	}
	public void setPgoods_name(String pgoods_name) {
		this.pgoods_name = pgoods_name;
	}
	public String getUse_mode() {
		return use_mode;
	}
	public void setUse_mode(String use_mode) {
		this.use_mode = use_mode;
	}
	public String getPgoods_id() {
		return pgoods_id;	
	}

	public void setPgoods_id(String pgoods_id) {
		this.pgoods_id = pgoods_id;
	}

	public String getApply_amount() {
		return apply_amount;
	}

	public void setApply_amount(String apply_amount) {
		this.apply_amount = apply_amount;
	}

	public String getRemain_amount() {
		return remain_amount;
	}

	public void setRemain_amount(String remain_amount) {
		this.remain_amount = remain_amount;
	}

	public String getRemain_times() {
		return remain_times;
	}

	public void setRemain_times(String remain_times) {
		this.remain_times = remain_times;
	}


	public String getUsed_amount() {
		return used_amount;
	}
	public void setUsed_amount(String used_amount) {
		this.used_amount = used_amount;
	}
	public String getPGoodsGroup() {
		return pGoodsGroup;
	}
	public void setPGoodsGroup(String goodsGroup) {
		pGoodsGroup = goodsGroup;
	}
	public PGoodsList getPGoodsList() {
		return pGoodsList;
	}
	public void setPGoodsList(PGoodsList goodsList) {
		pGoodsList = goodsList;
	}
	public String getApplyPassword() {
		return applyPassword;
	}
	public void setApplyPassword(String applyPassword) {
		this.applyPassword = applyPassword;
	}
	public String getSpareField() {
		return spareField;
	}
	public void setSpareField(String spareField) {
		this.spareField = spareField;
	}

}
