package com.lvmama.passport.processor.impl.client.gugong;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.PriceUtil;

/*
 * 返加数据示例：
 * 　{
 * 　　 "intodate": "2013-04-23",
 * 　　 "fullprice": "60.00",
 * 　　 "halfprice": "30.00",
 * 　　 "studentprice": "20.00",
 * 　　 "totalnumber": "3500",
 * 　　 "resultcode": "0"
 * }
 */
public class GugongTimePrice {

	// 入院日期
	private String intodate;
	// 实时全价票价格
	private String fullprice;
	// 实时半价票价格
	private String halfprice;
	// 实时学生票价格
	private String studentprice;
	// 实时剩余库存
	private int totalnumber;
	// 返回码
	private int resultcode;

	// 实时查询永乐方时间价格是否成功
	public boolean isSuccess() {
		return resultcode == GugongConstant.successful;
	}

	public String getIntodate() {
		return intodate;
	}

	public void setIntodate(String intodate) {
		this.intodate = intodate;
	}

	public int getTotalnumber() {
		return totalnumber;
	}

	public void setTotalnumber(int totalnumber) {
		this.totalnumber = totalnumber;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public String getSettlementPrice(MetaProductBranch branch, TimePrice metaTimePrice) {
		
		String productTypeSupplier = branch.getProductIdSupplier();
		try {
			return (String) this.getClass().getDeclaredField(productTypeSupplier).get(this);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} 
	}

	public long getMarketPrice(MetaProductBranch branch, TimePrice metaTimePrice) {
		String productTypeSupplier = branch.getProductIdSupplier();
		try {
			String price = (String) this.getClass().getDeclaredField(productTypeSupplier).get(this);
			return PriceUtil.convertToFen(price);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		} 
	}

	public String getLvmamaPrice(MetaProductBranch branch, TimePrice metaTimePrice){
		return getSettlementPrice(branch, metaTimePrice);
	}
	public String getFullprice() {
		return fullprice;
	}

	public void setFullprice(String fullprice) {
		this.fullprice = fullprice;
	}

	public String getHalfprice() {
		return halfprice;
	}

	public void setHalfprice(String halfprice) {
		this.halfprice = halfprice;
	}

	public String getStudentprice() {
		return studentprice;
	}

	public void setStudentprice(String studentprice) {
		this.studentprice = studentprice;
	}

}
