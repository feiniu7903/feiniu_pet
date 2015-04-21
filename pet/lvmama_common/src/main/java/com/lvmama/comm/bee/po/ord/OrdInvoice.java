package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdInvoice implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5348325705953108206L;
	@Deprecated
	private String address;
	private Long invoiceId;

	@Deprecated
    private Long orderId;

    private String title;

    private String detail;

    private String memo;

    private Long amount;

    private String invoiceNo;

    private Date billDate;

    private Date createTime;
    
    private String status;
    
    private float amountYuan;
    
    private String deliveryType;
    private String company;
    private String userId;

	private String expressNo;
	/**
	 * 相关订单.
	 */
    @Deprecated
	private OrdOrder order;
	
	private List<OrdInvoiceRelation> invoiceRelationList=Collections.emptyList();
	
	/**
	 * 订单列表
	 */
	private List<OrdOrder> orderList = Collections.emptyList();
	/**
	 * 收货地址
	 */
	private OrdPerson deliveryAddress = new OrdPerson();
	
	/**
	 * 红冲
	 */
	private String redFlag;
	/**
	 * 红冲申请时间
	 */
	private Date redReqTime;
	
	/**
	 * 物流发送状态
	 */
	private String logisticsStatus;
	

	/**
	 * 判断deliveryAddress是否为空,以redeiverID是否存在来判断是否为空
	 * @return
	 */
	public boolean hasNotNullDelivery(){ 
		return deliveryAddress!=null&&StringUtils.isNotEmpty(deliveryAddress.getReceiverId());
	}
	
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	public Long getAmount() {
		return amount;
	}

	public float getAmountYuan() {
		return amount == null ? amountYuan : PriceUtil.convertToYuan(amount);
	}

	public void setAmountYuan(float amountYuan) {
		this.amountYuan = amountYuan;
		this.amount=PriceUtil.convertToFen(amountYuan);
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * getOrder.
	 *
	 * @return 相关订单
	 */
	public OrdOrder getOrder() {
		return order;
	}

	/**
	 * setOrder.
	 *
	 * @param order
	 *            相关订单
	 */
	public void setOrder(final OrdOrder order) {
		this.order = order;
	}
	
	public String getZhDetail() {		
		return InvoiceUtil.getZhInvoiceContent(detail);
	}
	@Deprecated
	public boolean getEditFlag(){
		if (this.status.equals(Constant.INVOICE_STATUS.UNBILL.name())) return true;
		else return false;
	}
	@Deprecated
	public boolean getCancelFlag(){
		if (this.status.equals(Constant.INVOICE_STATUS.BILLED.name())) return true;
		else return false;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getZhStatus(){
		return Constant.INVOICE_STATUS.getCnName(status);
	}
	
	public String getZhDeliveryType(){
		return Constant.DELIVERY_TYPE.getCnName(deliveryType);
	}

	/**
	 * @return the invoiceRelationList
	 */
	public List<OrdInvoiceRelation> getInvoiceRelationList() {
		return invoiceRelationList;
	}

	/**
	 * @param invoiceRelationList the invoiceRelationList to set
	 */
	public void setInvoiceRelationList(List<OrdInvoiceRelation> invoiceRelationList) {
		this.invoiceRelationList = invoiceRelationList;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the deliveryAddress
	 */
	public OrdPerson getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(OrdPerson deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	
	

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

	/**
	 * @return the expressNo
	 */
	public String getExpressNo() {
		return expressNo;
	}

	/**
	 * @param expressNo the expressNo to set
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	/**
	 * 取发票的所有订单号以逗号分开.
	 * @return
	 */
	public String getOrderids(){
		StringBuffer orderids=new StringBuffer();
		if(CollectionUtils.isNotEmpty(invoiceRelationList)){
			for(OrdInvoiceRelation r:invoiceRelationList){
				if(orderids.length()>0){
					orderids.append(",");
				}
				orderids.append(r.getOrderId());
			}
		}
		return orderids.toString();
	}

	public String getRedFlag() {
		return redFlag;
	}

	public void setRedFlag(String redFlag) {
		this.redFlag = redFlag;
	}

	public Date getRedReqTime() {
		return redReqTime;
	}

	public void setRedReqTime(Date redReqTime) {
		this.redReqTime = redReqTime;
	}

	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public List<OrdOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrdOrder> orderList) {
		this.orderList = orderList;
	}
	public String getZhLogisticsStatus(){
		return Constant.INVOICE_LOGISTICS.getCnName(logisticsStatus);
	}
	
}