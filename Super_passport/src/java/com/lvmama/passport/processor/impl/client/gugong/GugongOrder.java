package com.lvmama.passport.processor.impl.client.gugong;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.passport.utils.Md5;

public class GugongOrder {
	private String intodate="";
	private String createdate="";
	private String paydate="";

	private String name="";
	private String idnumber="";
	private String telphone="";

	private String orderid="";
	private String invoicename="";
	private String unionid="";
	private String sign="";

	/** 免费票人数 */
	private int freeticket = 0;
	/** 全价票人数 */
	private int fullticket = 0;
	/** 半价票人数 */
	private int halfpriceticket = 0;
	/** 学生票人数 */
	private int studentticket = 0;
	/** 组团人数（免费票人数+全价票人数+半价票人数+学生票人数） */
	private int totalnumber=0;

	/** 全价票价格（全价票单价*全价票人数） */
	private float fullprice;
	private String fullpriceStr="";
	/** 半价票价格（半价票单价*半价票人数） */
	private float halfprice;
	private String halfpriceStr="";
	/** 学生票价格（学生票单价*学生票人数） */
	private float studentprice;
	private String studentpriceStr="";
	/** 支付金额 */
	private float payprice;
	private String paypriceStr="";
	/** 总票价（全价票价格+半价票价格+学生票价格） */
	private float totalprice;
	private String totalpriceStr="";

	public GugongOrder(PayPayment payPayment, OrdOrder ordorder,String passCodeSerialNo) {
		init(payPayment, ordorder,passCodeSerialNo);
	}
	public void init(PayPayment payPayment, OrdOrder ord,String passCodeSerialNo) {

		this.createdate = DateUtil.formatDate(ord.getPaymentTime(), "yyyy-MM-dd HH:mm:ss");
		this.intodate = DateUtil.formatDate(ord.getVisitTime(), "yyyy-MM-dd");
		this.orderid=passCodeSerialNo;//申码的申请流水号

		this.name = ord.getTravellerList().get(0).getName(); //
		this.idnumber = ord.getContact().getCertNo().toUpperCase();//身份证号含x的将字母自动转换为大写
		this.telphone = ord.getTravellerList().get(0).getMobile(); //

		this.invoicename = "";
		this.payprice = payPayment.getAmountYuan(); // 订单支付金额
		this.paypriceStr = PriceUtil.formatDecimal(this.payprice);
		this.paydate = DateUtil.formatDate(ord.getPaymentTime(), "yyyy-MM-dd HH:mm:ss"); // 订单支付时间

		this.freeticket = 0;

		calculateOrder(ord);
		this.totalnumber = this.fullticket + this.halfpriceticket + this.studentticket + this.freeticket;

		this.totalprice = this.fullprice + this.halfprice + this.studentprice;
		this.totalpriceStr = PriceUtil.formatDecimal(this.totalprice);
		
		this.unionid = GugongConstant.unionid;
		this.sign = Md5.encode(this.valuesBuiler());

	}

	public void calculateOrder(OrdOrder ord) {
		List<OrdOrderItemMeta> ordItemMetas = ord.getAllOrdOrderItemMetas();
		for (OrdOrderItemMeta itemMeta : ordItemMetas) {
			String productIdSupplier = itemMeta.getProductIdSupplier();
			if (productIdSupplier.equals(GugongConstant.fullprice)) {
				this.fullticket += itemMeta.getQuantity() * itemMeta.getAdultQuantity() * itemMeta.getProductQuantity();
				this.fullprice = PriceUtil.convertToYuan(this.fullticket * itemMeta.getSettlementPrice());
				fullpriceStr = PriceUtil.formatDecimal(this.fullprice);
			}
			if (productIdSupplier.equals(GugongConstant.halfprice)) {
				this.halfpriceticket += itemMeta.getQuantity() * itemMeta.getAdultQuantity() * itemMeta.getProductQuantity();
				this.halfprice = PriceUtil.convertToYuan(this.halfpriceticket * itemMeta.getSettlementPrice());
				halfpriceStr = PriceUtil.formatDecimal(this.halfprice);
			}
			if (productIdSupplier.equals(GugongConstant.studentprice)) {
				this.studentticket += itemMeta.getQuantity() * itemMeta.getAdultQuantity() * itemMeta.getProductQuantity();
				this.studentprice = PriceUtil.convertToYuan(this.studentticket * itemMeta.getSettlementPrice());
				studentpriceStr = PriceUtil.formatDecimal(this.studentprice);
			}
		}
	}

	//串连各字段值，顺序不能变（按照文档指明的顺序），
	public String valuesBuiler() {
		StringBuilder builder = new StringBuilder();
		builder.append(orderid);
		builder.append(idnumber);
		builder.append(intodate);
		builder.append(name);
		builder.append(telphone);
		builder.append(invoicename);
		builder.append(createdate);
		builder.append(freeticket);
		if (StringUtils.isNotBlank(fullpriceStr)) {
		builder.append(fullpriceStr);
		}else{
		builder.append("0.00");
		}
		builder.append(fullticket);
		if (StringUtils.isNotBlank(halfpriceStr)) {
		builder.append(halfpriceStr);
		}else{
		builder.append("0.00");
		}
		builder.append(halfpriceticket);
		if (StringUtils.isNotBlank(studentpriceStr)) {
		builder.append(studentpriceStr);
		}else{
		builder.append("0.00");
		}
		builder.append(studentticket);
		builder.append(totalnumber);
		builder.append(totalpriceStr);
		builder.append(paydate);
		builder.append(paypriceStr);
		builder.append(unionid);
		builder.append(GugongConstant.password);
		return builder.toString();
	}

	public String getJSON() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		
		builder.append("\"orderid\"".concat(":\"").concat(orderid).concat("\","));
		builder.append("\"idnumber\"".concat(":\"").concat(idnumber).concat("\","));
		builder.append("\"intodate\"".concat(":\"").concat(intodate).concat("\","));
		builder.append("\"name\"".concat(":\"").concat(name).concat("\","));
		builder.append("\"telphone\"".concat(":\"").concat(telphone).concat("\","));
		builder.append("\"invoicename\"".concat(":\"").concat(invoicename).concat("\","));
		builder.append("\"createdate\"".concat(":\"").concat(createdate).concat("\","));
		builder.append("\"freeticket\"".concat(":\"").concat(String.valueOf(freeticket)).concat("\","));
		if (StringUtils.isNotBlank(fullpriceStr)) {
		builder.append("\"fullprice\"".concat(":\"").concat(fullpriceStr).concat("\","));
		}else{
		builder.append("\"fullprice\"".concat(":\"").concat("0.00").concat("\","));	
		}
		builder.append("\"fullticket\"".concat(":\"").concat(String.valueOf(fullticket).concat("\",")));
		if (StringUtils.isNotBlank(halfpriceStr)) {
		builder.append("\"halfprice\"".concat(":\"").concat(halfpriceStr).concat("\","));
		}else{
		builder.append("\"halfprice\"".concat(":\"").concat("0.00").concat("\","));
		}
		builder.append("\"halfpriceticket\"".concat(":\"").concat(String.valueOf(halfpriceticket).concat("\",")));
		if (StringUtils.isNotBlank(studentpriceStr)) {
		builder.append("\"studentprice\"".concat(":\"").concat(studentpriceStr).concat("\","));
		}else{
		builder.append("\"studentprice\"".concat(":\"").concat("0.00").concat("\","));
		}
		builder.append("\"studentticket\"".concat(":\"").concat(String.valueOf(studentticket).concat("\",")));
		builder.append("\"totalnumber\"".concat(":\"").concat(String.valueOf(totalnumber).concat("\",")));
		builder.append("\"totalprice\"".concat(":\"").concat(totalpriceStr).concat("\","));
		builder.append("\"paydate\"".concat(":\"").concat(paydate).concat("\","));
		builder.append("\"payprice\"".concat(":\"").concat(paypriceStr).concat("\","));
		builder.append("\"unionid\"".concat(":\"").concat(unionid).concat("\","));
		builder.append("\"sign\"".concat(":\"").concat(sign).concat("\""));
		
		builder.append("}");
		return builder.toString();
	}

	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdnumber() {
		return idnumber;
	}
	
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	
	public String getTelphone() {
		return telphone;
	}
	
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	public String getOrderid() {
		return orderid;
	}
	
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public String getInvoicename() {
		return invoicename;
	}
	
	public void setInvoicename(String invoicename) {
		this.invoicename = invoicename;
	}
	
	public String getUnionid() {
		return unionid;
	}
	
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public int getFreeticket() {
		return freeticket;
	}
	
	public void setFreeticket(int freeticket) {
		this.freeticket = freeticket;
	}
	
	public int getFullticket() {
		return fullticket;
	}
	
	public void setFullticket(int fullticket) {
		this.fullticket = fullticket;
	}
	
	public int getHalfpriceticket() {
		return halfpriceticket;
	}
	
	public void setHalfpriceticket(int halfpriceticket) {
		this.halfpriceticket = halfpriceticket;
	}
	
	public int getStudentticket() {
		return studentticket;
	}
	
	public void setStudentticket(int studentticket) {
		this.studentticket = studentticket;
	}
	
	public int getTotalnumber() {
		return totalnumber;
	}
	
	public void setTotalnumber(int totalnumber) {
		this.totalnumber = totalnumber;
	}
	
	public float getFullprice() {
		return fullprice;
	}
	
	public void setFullprice(float fullprice) {
		this.fullprice = fullprice;
	}
	
	public float getHalfprice() {
		return halfprice;
	}
	
	public void setHalfprice(float halfprice) {
		this.halfprice = halfprice;
	}
	
	public float getStudentprice() {
		return studentprice;
	}
	
	public void setStudentprice(float studentprice) {
		this.studentprice = studentprice;
	}
	
	public float getPayprice() {
		return payprice;
	}
	
	public void setPayprice(float payprice) {
		this.payprice = payprice;
	}
	
	public float getTotalprice() {
		return totalprice;
	}
	
	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}
	
	public String getIntodate() {
		return intodate;
	}
	
	public void setIntodate(String intodate) {
		this.intodate = intodate;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	public String getPaydate() {
		return paydate;
	}
	
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
}
