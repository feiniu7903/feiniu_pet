package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 
 * 订单的采购产品信息(订单子子项信息).
 *
 */
public class OrdOrderItemMeta implements Serializable {
	private static final long serialVersionUID = -4197135240085719037L;

	/**
	 * 所有采购产品的时间信息数据
	 */
	private List<OrdOrderItemMetaTime> allOrdOrderItemMetaTime = new ArrayList<OrdOrderItemMetaTime>();
	
	private OrdOrderItemProd relateOrdOrderItemProd  = new OrdOrderItemProd();
	//主键ID.
	private Long orderItemMetaId;
	//订单ID.
	private Long orderId;
	//订单对象.
	private OrdOrder ordOrder;
	//订单的销售产品ID(订单子项ID).
	private Long orderItemId;
	//履行状态.
	private String performStatus;
	//资源确认状态.
	private String resourceStatus = "UNCONFIRMED";
	//预付款状态.
	private String prePayStatus;
	//结算状态.
	private String settlementStatus;
	//结算状态.
	private String settlementStatusStr;
	//结算时间.
	private Date settlementTime;
	//采购产品ID.
	private Long metaProductId;
	//采购产品分支ID.
	private Long metaBranchId;
	//采购产品名称.
	private String productName;
	//采购产品类型.
	private String productType;
	//供应商ID.
	private Long supplierId;
	//供应商名称.
	private String supplierName;
	//供应商.
	private SupSupplier supplier;
	//门市价.
	private Long marketPrice;
	//结算价.
	private Long settlementPrice;  
	//结算总价
	private Long totalSettlementPrice;  
	//实际结算价.
	private Long actualSettlementPrice; 
	private Long suggestionPrice;
	//传真备注.
	private String faxMemo;
	//资源是否需确认.
	private String resourceConfirm;
	//游玩时间.
	private Date visitTime;
	//该采购产品在打包的销售产品中占有的数量（对于单件销售商品来说）
	private Long productQuantity;
	//组成该采购产品的成人数量（对于单件采购产品来说）
	private Long adultQuantity;
	//组成该采购产品的儿童数量（对于单件采购产品来说）
	private Long childQuantity;
	//电子通关码的有效天数.
	private Long validDays;
	//传真发送状态.
	private String faxSendStatus;
	//是否被领("true"/"false").
	private String taken;
	//产品子类型.
	private String subProductType;
	//是否有退款申请("true"/"false").
	private String refund;
	//打包数量,购买数（即订单子项中的数量）.
	private Long quantity;
	//支付对象.
	private String paymentTarget;
	//售价（根据比例算出）.
	private Long sellPrice;
	//供应商的产品ID.
	private String productIdSupplier;
	//供应商的产品类型或名称.
	private String productTypeSupplier;
	//发送传真("true"/"false").
	private String sendFax;
	//是否资源确认后发送传真
	private String isResourceSendFax;
	// 资源审核不通过原因 (仅当资源审核不通过时)
	private String resourceLackReason;
	//库存是否减少了(是否影响库存),取值为true、false. true代表该订单影响库存，订单被取消时，如果STOCK_REDUCED字段为true，则修改为false.
	private String stockReduced = "false";
	//保留时间
	private Date retentionTime;
	//凭证状态.
	private String passStatus;
	//采购产品.
	private MetaProduct metaProduct;
	//选中订单子项,默认不选中.
	private String checkItem="false";
	//订单子子项履行信息.
	private OrdPerform ordPerform;
	//采购产品类别类型. 取值为COM_CODE表中SET_CODE值为TICKET_BRANCH、ROUTE_BRANCH、OTHER_BRANCH、SELF_PACK_BRANCH.
	private String branchType;
	//分拆销售收入后的实收金额
	private Long payedAmount;
	//酒店入住晚数（单房型是每晚）
	private Long nights;
	
	// 退款明细中的金额类型
	private String amountType;
	// 退款明细中的金额
	private Long amountValue;
	// 退款单id
	private Long refundmentId;
	// 备注
	private String memo;
	
	private Long refundmentItemId;
	// 实际损失
	private Long actualLoss;
	//是否生成供应商系统审核任务(true/flase)
	private String supplierFlag;
	/**
 	 * 是否虚拟产品 true/false
 	 */
 	private String virtual;
 	
 	/**
 	 * 机票登机时间，不使用visitTime,该属性会出现时分
 	 */
 	private Date goFlightTime;
 	/**
 	 * 机票使用字段
 	 * 回程登机时间
 	 */
 	private Date backFlightTime;
 	
 	/**
 	 * 机票使用字段
 	 * 去程航班
 	 */
 	private String goFlightCode;
 	/**
 	 * 机票回程航班
 	 */
 	private String backFlightCode;
 	/**
 	 * 机票是否是往返
 	 */
 	private String direction;

	/**
	 * 早餐分数
	 */
    private Long breakfastCount;
    /**
     * 履行对象ID
     */
 	private Long performTargetId;
 	/**
 	 * 凭证状态
 	 */
 	private String certificateStatus;
 	/**
 	 * 对应的凭证确认渠道，传真/ebk
 	 */
 	private String confirmChannel;
 	
 	/**
 	 * 凭证类型
 	 */
 	private String ebkCertificateType;
 	
 	private PlaceFlight goPlaceFlight;
 	private PlaceFlight backPlaceFlight;
 	
 	/**
 	 * 使用买断库存量
 	 * @author zuoxiaoshuai
 	 */
 	private Long buyOutQuantity;
 	
 	/**
	 * 分公司名称
	 * @see com.lvmama.comm.vo.Constant.FILIALE_NAME
	 */
 	private String filialeName;
 	
 	
 	public String getZhConfirmChannel() {
    	return Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.getCnName(confirmChannel);
    }
 	public String getCertificateTypeStatus(){
 		if(StringUtil.isEmptyString(ebkCertificateType) && StringUtil.isEmptyString(certificateStatus)){
	    	return "";
 		}
 		if(Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CANCEL.name().equals(this.certificateStatus)) {
 			return Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CANCEL.name();
 		}
 		return ebkCertificateType+"_"+certificateStatus;
    }
 	 public String getZhCertificateTypeStatus(){
     	if(!StringUtil.isEmptyString(ebkCertificateType)&&!StringUtil.isEmptyString(certificateStatus))
     	{
     		return Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.getCnName(getCertificateTypeStatus());
     	}
     	return "";
     }
 	public String getZhEbkCertificateType() {
		return Constant.EBK_CERTIFICATE_TYPE.getCnName(this.ebkCertificateType);
	}
 	private SupBCertificateTarget bcertificateTarget;
 	
 	public String getZhCertificateStatus(){
 		return Constant.EBK_TASK_STATUS.getCnName(this.getCertificateStatus());
    }
	public Long getActualLoss() {
		return actualLoss;
	}

	public Float getActualLossYuan(){
		if(this.actualLoss == null){
			return null;
		}
		return PriceUtil.convertToYuan(actualLoss);
	}
	public void setActualLoss(Long actualLoss) {
		this.actualLoss = actualLoss;
	}
	
	public void setActualLossYuan(String actualLoss){
		if(actualLoss!=null){
			this.actualLoss = PriceUtil.convertToFen(actualLoss);
		}
	}

	// 用来判断实际损失是否显示
	public String getFlag() {
		if(null != amountType){
			if(amountType.equals("SUPPLIER_BEAR")){
				return "false";
			}else{
				return "true";
			}
		}else{
			return "true";
		}
	}

	public String getCheckFlag() {
		if(null != refundmentItemId){
			return "true";
		} else {
			return "false";
		}
	}
	
	public Long getRefundmentItemId() {
		return refundmentItemId;
	}

	public void setRefundmentItemId(Long refundmentItemId) {
		this.refundmentItemId = refundmentItemId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public Long getAmountValue() {
		return amountValue;
	}

	public Float getAmountValueYuan(){
		if(this.amountValue == null){
			return null;
		}
		return PriceUtil.convertToYuan(amountValue);
	}
	public String getAmountValueYuanStr() { 
		if(this.amountValue == null){
			return null;
		}
		return String.valueOf(PriceUtil.convertToYuan(amountValue));
	}
	public void setAmountValueYuanStr(String amountValueYuanStr) { 
		if(null != amountValueYuanStr){ 
			this.amountValue = PriceUtil.convertToFen(amountValueYuanStr);
		} 
	}
	public void setAmountValue(Long amountValue) {
		this.amountValue = amountValue;
	}
	
	public void setAmountValueYuan(String amountValue){
		if(amountValue!=null){
			this.amountValue = PriceUtil.convertToFen(amountValue);
		}
	}

	public boolean isPerformed() {
		return Constant.ORDER_PERFORM_STATUS.PERFORMED.name().equals(performStatus)
			|| Constant.ORDER_PERFORM_STATUS.AUTOPERFORMED.name().equals(performStatus);
	}
	
	public boolean isNeedSendFax() {
		return "true".equalsIgnoreCase(sendFax);
	}
	
	public boolean isNeedResourceConfirm() {
		return "true".equals(resourceConfirm);
	}
	
	public boolean isHaveStockReduced() {
		return "true".equals(stockReduced);
	}
	
	public String getStrVisitTime(){
		if(this.visitTime!=null){
			return DateFormatUtils.format(this.visitTime, "yyyy-MM-dd HH:mm");
		}
		return "";
	}
	
	public String getZhBranchType() {
		return CodeSet.getInstance().getCodeName(productType+"_BRANCH", branchType);
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
	}

	public long getTotalAdultQuantity() {
		return adultQuantity*productQuantity*quantity;
	}
	public long getTotalChildQuantity() {
		return childQuantity*productQuantity*quantity;
	}
	
	public long getTotalChildProductQuantity() {
		return childQuantity*productQuantity;
	}
	public long getTotalAdultProductQuantity() {
		return adultQuantity*productQuantity;
	}
	public long getTotalQuantity(){
		return this.getTotalAdultQuantity()+this.getTotalChildQuantity();
	}
	
	public String getZhResourceStatus() {
		return Constant.ORDER_RESOURCE_STATUS.getCnName(resourceStatus);
	}
	
	/**
	 * 获取货币单位为元的汇总价.
	 * @return	返回货币单位为元的汇总价.
	 */
	public float getSettlementPriceToYuan(){
		return PriceUtil.convertToYuan(this.getSettlementPriceLong());
	}
	/**
	 * 获取货币单位为元的结算价.
	 * @return 返回货币单位为元的结算价.
	 */
	public float getSettlementPriceYuan(){
		return PriceUtil.convertToYuan(settlementPrice);
	}
	/**
	 * 获取货币单位为分的汇总价.
	 * @return	返回货币单位为分的汇总价.
	 */
	public long getSettlementPriceLong() {
		return this.settlementPrice * this.productQuantity * this.quantity;
	}
	
	/**
	 * 结算总价.
	 * @return
	 */
	public float getSettlementPriceToYuanHotel(){
		Long settlementPriceTime=0L;
		if (!this.allOrdOrderItemMetaTime.isEmpty()) {
			for(OrdOrderItemMetaTime item :allOrdOrderItemMetaTime) {
				settlementPriceTime+=(item.getSettlementPrice()*item.getQuatity());
			}
			return PriceUtil.convertToYuan(settlementPriceTime);
		} else {
			return PriceUtil.convertToYuan(settlementPrice*quantity*productQuantity);
		}
	}
	
	/**
	 * 酒店单房型结算价 直接取
	 * @return
	 */
	public float getHotelSingelRoomSettlementPriceToYuan(){
		return PriceUtil.convertToYuan(this.getActualSettlementPrice());
	}
	
	/**
	 * 结算均价
	 * @return
	 */
	public float getAvgSingelRoomSettlementPriceToYuan(){
		return PriceUtil.convertToYuan(this.getActualSettlementPrice()/quantity);
	}
	public boolean isOtherProductType() {
		return Constant.PRODUCT_TYPE.OTHER.name().equals(productType);
	}
	public boolean isTicketProductType() {
		return Constant.PRODUCT_TYPE.TICKET.name().equals(productType);
	}
	public boolean isRouteProductType() {
		return Constant.PRODUCT_TYPE.ROUTE.name().equals(productType);
	}
	public boolean isHotelProductType() {
		return Constant.PRODUCT_TYPE.HOTEL.name().equals(productType);
	}
	public boolean isTrafficProductType() {
		return Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType);
	}
	/**
	 * 附加性质的采购产品：如房差、加床费
	 * 
	 * @author: ranlongfei 2013-3-22 上午11:08:48
	 * @return
	 */
	public boolean isAdditionBranchMeta() {
		if(Constant.PRODUCT_TYPE.HOTEL.name().equals(this.productType)) {
			if(Constant.HOTEL_BRANCH.EXTRABED.name().equals(this.branchType)) {
				return true;
			}
		}
		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(this.productType)) {
			if(Constant.ROUTE_BRANCH.FANGCHA.name().equals(this.branchType)) {
				return true;
			}
		}
		if(Constant.PRODUCT_TYPE.HOTEL_FOREIGN.name().equals(this.productType)) {
			if(Constant.HOTEL_BRANCH.EXTRABED.name().equals(this.branchType)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 销售均价
	 * @return
	 */
	public float getAvgSingelRoomSellPriceToYuan(){
		return PriceUtil.convertToYuan(this.sellPrice/quantity);
	}
	
	public float getSellPriceToYuan(){
		return PriceUtil.convertToYuan(this.getSellPrice());
	}

	
	
	/**
	 * 酒店相关产品的入住和游玩日期
	 * @return
	 */
	public String getDateRange() {
		List<Date> dateList = new ArrayList<Date>();
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(this.productType)) {
			for (OrdOrderItemMetaTime itemTime : this.allOrdOrderItemMetaTime) {
				dateList.add(itemTime.getVisitTime());
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String checkInDate = sdf.format(this.visitTime);
		String checkDate = checkInDate;
		java.util.Collections.sort(dateList);
		if (!dateList.isEmpty()) {
			java.util.Collections.sort(dateList);
			Calendar c = Calendar.getInstance();
			c.setTime((Date)dateList.get(dateList.size() - 1));
			c.add(Calendar.DATE, 1);
			checkDate += "至" + sdf.format(c.getTime());
		}
		return checkDate;
	}

	/**
	 * 获得酒店产品的入住房间数量和晚数
	 * @return
	 */
	
	public String getHotelQuantity(){
		if(!this.allOrdOrderItemMetaTime.isEmpty()){
			return String.valueOf(this.allOrdOrderItemMetaTime.get(0).getQuatity())+"间/"+String.valueOf(this.allOrdOrderItemMetaTime.size())+"晚";
		}
		return String.valueOf(this.getQuantity());
	}
	
	public String getSettmentPriceList(){
		StringBuffer list = new StringBuffer();
		if (!this.allOrdOrderItemMetaTime.isEmpty()) {
			list.append("(");

		for (Iterator<OrdOrderItemMetaTime> it = allOrdOrderItemMetaTime.iterator(); it.hasNext();) {
			list.append(PriceUtil.convertToYuan(it.next().getSettlementPrice()));
			if (it.hasNext()) {
				list.append(",");
			}
		}
			list.append(")");
		} else {
			list.append("-");
		}
		
		return list.toString();
	}
	
	public String getProdSellPriceList(){
		StringBuffer list = new StringBuffer();
		if (!this.relateOrdOrderItemProd.getAllOrdOrderItemProdTime().isEmpty()) {
			list.append("(");

		for (Iterator<OrdOrderItemProdTime> it = this.relateOrdOrderItemProd.getAllOrdOrderItemProdTime().iterator(); it.hasNext();) {
			list.append(PriceUtil.convertToYuan(it.next().getPrice()));
			if (it.hasNext()) {
				list.append(",");
			}
		}
			list.append(")");
		} else {
			list.append(this.getSettlementPriceToYuan());
		}
		
		return list.toString();
	}

	/**
	 * 判断资源情况.
	 * 
     * @return  
	 */
	public boolean isApproveResourceAmple() {
		return Constant.ORDER_RESOURCE_STATUS.AMPLE.name().equalsIgnoreCase(
				this.resourceStatus);
	}
	public OrdOrderItemProd getRelateOrdOrderItemProd() {
		return relateOrdOrderItemProd;
	}

	public void setRelateOrdOrderItemProd(OrdOrderItemProd relateOrdOrderItemProd) {
		this.relateOrdOrderItemProd = relateOrdOrderItemProd;
	}
	
	public String getVisitTimeDay() {
		return DateUtil.getDateTime("yyyy-MM-dd", visitTime);
	}
	
	public Date getRetentionTime() {
		return retentionTime;
	}
	
	public void setRetentionTime(Date retentionTime) {
		this.retentionTime = retentionTime;
	}
	public boolean isStudent(){
		return "STUDENT".equalsIgnoreCase(this.branchType);
	}
	 
	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	/**
	 * 是否是资源审核通过后发送传真.
	 * @return 是则返回true,不是则返回false.
	 */
	public boolean isApproveResourceSendFax() {
		return "true".equals(this.sendFax) && "true".equalsIgnoreCase(isResourceSendFax);
	}
	/**
	 * 是否是订单支付后发送传真.
	 * @return 是则返回true,不是则返回false.
	 */
	public boolean isPayedSendFax() {
		return "true".equals(this.sendFax) && !"true".equalsIgnoreCase(isResourceSendFax);
	}
	
	/**
	 * 入住时间.
	 * @return
	 */
	public Date getBeginDate() {
		return this.getVisitTime();
	}
	/**
	 * 离店时间.
	 * @return
	 */
	public Date getEndDate() {	
		return DateUtil.dsDay_Date(this.getVisitTime(), this.getStayDays());
	}
	/**
	 * 入住天数.
	 * 入住天数:分三种情况,
	 * 第一种是单房间,取订单子子项关联的时间价格固化表数据;
	 * 第二种是酒店套餐,取晚数数据;前两种关联的销售产品是酒店类型,
	 * 第三种是线路自由行产品打包酒店采购产品,则晚数为1.
	 * @return
	 */
	public int getStayDays() {
		if (!this.allOrdOrderItemMetaTime.isEmpty()) {
			return this.allOrdOrderItemMetaTime.size();
		}	
		//System.out.println("this.nights:" + this.nights);
		return this.nights == null ? 1 : this.nights.intValue();
	}
	/**
	 * 获得酒店产品的入住房间数量
	 * @return
	 */
	public Long getRoomQuantity(){
		if(!this.allOrdOrderItemMetaTime.isEmpty()){
			return this.allOrdOrderItemMetaTime.get(0).getQuatity();
		}
		return this.getQuantity();
	}
	/**
	 * 酒店产品单房间类型的每日时间价格列表信息.
	 * @return
	 */
	public List<OrdOrderItemMetaTime> getAllOrdOrderItemMetaTimeFax() {
		if (!this.allOrdOrderItemMetaTime.isEmpty()) {
			Collections.sort(this.allOrdOrderItemMetaTime);
			return this.allOrdOrderItemMetaTime;
		}	else {
			OrdOrderItemMetaTime ooimt = new OrdOrderItemMetaTime();
			ooimt.setVisitTime(this.visitTime);
			//ooimt.setSettlementPrice(this.getSettlementPriceLong());
			ooimt.setSettlementPrice(this.getSettlementPrice());
			ooimt.setQuatity(this.getQuantity());
			List<OrdOrderItemMetaTime> list = new ArrayList<OrdOrderItemMetaTime>();
			list.add(ooimt);
			return list;
		}
	}
	public String getIsResourceSendFax() {
		return isResourceSendFax;
	}

	public void setIsResourceSendFax(String isResourceSendFax) {
		this.isResourceSendFax = isResourceSendFax;
	}

	public String getResourceLackReason() {
		return resourceLackReason;
	}
	public String getBranchType() {
		return branchType;
	}

	public void setResourceLackReason(String resourceLackReason) {
		this.resourceLackReason = resourceLackReason;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}
	
	 
	/**
	 * getMetaProduct.
	 *
	 * @return 采购产品
	 */
	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	/**
	 * setMetaProduct.
	 *
	 * @param metaProduct
	 *            采购产品
	 */
	public void setMetaProduct(final MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getPerformStatus() {
		return performStatus;
	}

	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getPrePayStatus() {
		return prePayStatus;
	}

	public void setPrePayStatus(String prePayStatus) {
		this.prePayStatus = prePayStatus;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Long getSuggestionPrice() {
		return suggestionPrice;
	}

	public void setSuggestionPrice(Long suggestionPrice) {
		this.suggestionPrice = suggestionPrice;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getResourceConfirm() {
		return resourceConfirm;
	}

	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

	public String getFaxMemo() {
		return faxMemo;
	}

	public void setFaxMemo(String faxMemo) {
		this.faxMemo = faxMemo;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Long getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public Long getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	public String getFaxSendStatus() {
		return faxSendStatus;
	}

	public void setFaxSendStatus(String faxSendStatus) {
		this.faxSendStatus = faxSendStatus;
	}

	public Long getValidDays() {
		return validDays;
	}

	public void setValidDays(Long validDays) {
		this.validDays = validDays;
	}

	public String getTaken() {
		return taken;
	}

	public void setTaken(String taken) {
		this.taken = taken;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public Long getActualSettlementPrice() {
		return actualSettlementPrice;
	}
	public float getActualSettlementPriceYuan() {
		if(null == actualSettlementPrice){
			return 0f;
		}
		return PriceUtil.convertToYuan(actualSettlementPrice);
	}

	public void setActualSettlementPrice(Long actualSettlementPrice) {
		this.actualSettlementPrice = actualSettlementPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getSendFax() {
		return sendFax;
	}

	public void setSendFax(String sendFax) {
		this.sendFax = sendFax;
	}

	public SupSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SupSupplier supplier) {
		this.supplier = supplier;
	}
	public String getProductIdSupplier() {
		return productIdSupplier;
	}
	
	public void setProductIdSupplier(String productIdSupplier) {
		this.productIdSupplier = productIdSupplier;
	}

	public String getProductTypeSupplier() {
		return productTypeSupplier;
	}

	public void setProductTypeSupplier(String productTypeSupplier) {
		this.productTypeSupplier = productTypeSupplier;
	}

	public String getStockReduced() {
		return stockReduced;
	}

	public void setStockReduced(String stockReduced) {
		this.stockReduced = stockReduced;
	}
	/**
	 * getPassStatus.
	 *
	 * @return 凭证状态
	 */
	public String getPassStatus() {
		return passStatus;
	}

	/**
	 * setPassStatus.
	 *
	 * @param passStatus
	 *            凭证状态
	 */
	public void setPassStatus(final String passStatus) {
		this.passStatus = passStatus;
	}
	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	/**
	 * getOrdPerform.
	 *
	 * @return 采购产品订单子项履行信息
	 */
	public OrdPerform getOrdPerform() {
		return ordPerform;
	}

	/**
	 * setOrdPerform.
	 *
	 * @param ordPerform
	 *            采购产品订单子项履行信息
	 */
	public void setOrdPerform(final OrdPerform ordPerform) {
		this.ordPerform = ordPerform;
	}
	public List<OrdOrderItemMetaTime> getAllOrdOrderItemMetaTime() {
		return allOrdOrderItemMetaTime;
	}

	public void setAllOrdOrderItemMetaTime(
			List<OrdOrderItemMetaTime> allOrdOrderItemMetaTime) {
		this.allOrdOrderItemMetaTime = allOrdOrderItemMetaTime;
	}

	/**
	 * @return the nights
	 */
	public Long getNights() {
		return nights;
	}

	/**
	 * @param nights the nights to set
	 */
	public void setNights(Long nights) {
		this.nights = nights;
	}

	public Long getBreakfastCount() {
		return breakfastCount;
	}

	public void setBreakfastCount(Long breakfastCount) {
		this.breakfastCount = breakfastCount;
	}

	public Long getTotalSettlementPrice() {
		return totalSettlementPrice;
	}
	public float getTotalSettlementPriceYuan() {
		if(null == totalSettlementPrice){
			return 0f;
		}
		return PriceUtil.convertToYuan(totalSettlementPrice);
	}

	public void setTotalSettlementPrice(Long totalSettlementPrice) {
		this.totalSettlementPrice = totalSettlementPrice;
	}	

	public Long getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(Long payedAmount) {
		this.payedAmount = payedAmount;
	}

	public String getSupplierFlag() {
		return supplierFlag;
	}

	public void setSupplierFlag(String supplierFlag) {
		this.supplierFlag = supplierFlag;
	}

	public String getVirtual() {
		return virtual;
	}

	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}

	public Date getBackFlightTime() {
		return backFlightTime;
	}

	public void setBackFlightTime(Date backFlightTime) {
		this.backFlightTime = backFlightTime;
	}

	public String getGoFlightCode() {
		return goFlightCode;
	}

	public void setGoFlightCode(String goFlightCode) {
		this.goFlightCode = goFlightCode;
	}

	public String getBackFlightCode() {
		return backFlightCode;
	}

	public void setBackFlightCode(String backFlightCode) {
		this.backFlightCode = backFlightCode;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getGoFlightTime() {
		return goFlightTime;
	}

	public void setGoFlightTime(Date goFlightTime) {
		this.goFlightTime = goFlightTime;
	}
	
	public String getGoFlightTimeStr(){
		return DateUtil.formatDate(goFlightTime, "yyyy-MM-dd HH:mm");
	}
	
	public String getBackFlightTimeStr(){
		return DateUtil.formatDate(backFlightTime, "yyyy-MM-dd HH:mm");
	}
	
	public String getDirectionStr(){
		if(Constant.TRAFFIC_DIRECTION.SINGLE.name().equals(this.direction)){
			return "单程";
		}else if(Constant.TRAFFIC_DIRECTION.ROUND.name().equals(this.direction)){
			return "往返";
		}
		return null;
	}
	/**
	 * 通过ebk_task审核订单
	 * @return
	 */
	public boolean hasSupplier(){
		return "true".equalsIgnoreCase(supplierFlag);
	}
	public PlaceFlight getGoPlaceFlight() {
		return goPlaceFlight;
	}

	public void setGoPlaceFlight(PlaceFlight goPlaceFlight) {
		this.goPlaceFlight = goPlaceFlight;
	}

	public PlaceFlight getBackPlaceFlight() {
		return backPlaceFlight;
	}

	public void setBackPlaceFlight(PlaceFlight backPlaceFlight) {
		this.backPlaceFlight = backPlaceFlight;
	}

	public Long getPerformTargetId() {
		return performTargetId;
	}

	public void setPerformTargetId(Long performTargetId) {
		this.performTargetId = performTargetId;
	}

	public String getSettlementStatusStr() {
		return settlementStatusStr;
	}

	public void setSettlementStatusStr(String settlementStatusStr) {
		this.settlementStatusStr = settlementStatusStr;
	}

	public String getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public String getConfirmChannel() {
		return confirmChannel;
	}

	public void setConfirmChannel(String confirmChannel) {
		this.confirmChannel = confirmChannel;
	}
	public String getEbkCertificateType() {
		return ebkCertificateType;
	}
	public void setEbkCertificateType(String ebkCertificateType) {
		this.ebkCertificateType = ebkCertificateType;
	}
	public SupBCertificateTarget getBcertificateTarget() {
		return bcertificateTarget;
	}
	public void setBcertificateTarget(SupBCertificateTarget bcertificateTarget) {
		this.bcertificateTarget = bcertificateTarget;
	}
	
	public String getShortProductName(){
		if(StringUtils.isNotEmpty(productName)) {
			int start=productName.lastIndexOf("(");
			if(start != -1){
				return productName.substring(0,start);
			}
		}
		return "";
	}
	public String getZhBranchName(){
		if(StringUtils.isNotEmpty(productName)) {
			int start=productName.lastIndexOf("(");
			String sub=productName.substring(start+1);
			int count=StringUtils.countMatches(sub, ")");
			while(count-->1){
				start=productName.lastIndexOf("(",start-1);
			}
			if(start != -1){
				return productName.substring(start+1,productName.length()-1);
			}
		}
		return "";
	}
	public Long getBuyOutQuantity() {
		return buyOutQuantity == null ? 0L : buyOutQuantity;
	}
	public void setBuyOutQuantity(Long buyOutQuantity) {
		this.buyOutQuantity = buyOutQuantity;
	}
	
	public String getFilialeName() {
		return filialeName;
	}
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
}