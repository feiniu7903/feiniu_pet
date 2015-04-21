package com.lvmama.finance.group.ibatis.po;

import java.sql.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 团信息
 * 
 * @author yanggan
 *
 */
public class TravelGroup  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long travelGroupId;
	private String travelGroupCode;
	private Long productId;
	private String productName;
	private Long sellPrice;
	private Long settlementPrice;
	private Date visitTime;
	private Long initialGroupNum;
	private Long paySuccessNum;
	private Long payPartNum;
	private Long payNotNum;
	private Long initialNum;
	private Date makeTime;
	private String travelGroupStatus;
	private Date createTime;
	private Long orgId;
	private String groupWordAble;
	private String memo;
	private String settlementStatus;
	private Date backTime;
	private Date checkTime;
	private String checkUser;
	
	private String userName;
	
	private Double actProfitRate;
	private String groupType;
	
	public String getGroupTypeZh(){
		return Constant.GROUP_TYPE.getCnName(groupType);
	}
	
	public String getSettlementStatusZH(){
		if(StringUtil.isEmptyString(settlementStatus)){
			return "";
		}else{
			return Constant.TRAVEL_GROUP_STETTLEMENT_STATUS.getCnName(settlementStatus);
		}
	}
	
	public String getTravelGroupStatusZH(){
		if(StringUtil.isEmptyString(travelGroupStatus)){
			return "";
		}else{
			return Constant.OP_GROUP_STATUS.getCnName(travelGroupStatus);
		}
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public TravelGroup() {
	}

	public Long getTravelGroupId() {
		return this.travelGroupId;
	}

	public void setTravelGroupId(Long travelGroupId) {
		this.travelGroupId = travelGroupId;
	}

	public String getTravelGroupCode() {
		return this.travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getSettlementPrice() {
		return this.settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Date getVisitTime() {
		return this.visitTime;
	}
	public String getVisitTimeStr(){
		return DateUtil.getFormatDate(this.visitTime, "yyyy-MM-dd");
	}
	/**
	 * 回团时间
	 * @return
	 */
	public String getBackTimeStr(){
		return DateUtil.getFormatDate(this.backTime, "yyyy-MM-dd");
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Long getInitialGroupNum() {
		return this.initialGroupNum;
	}

	public void setInitialGroupNum(Long initialGroupNum) {
		this.initialGroupNum = initialGroupNum;
	}

	public Long getPaySuccessNum() {
		return this.paySuccessNum;
	}

	public void setPaySuccessNum(Long paySuccessNum) {
		this.paySuccessNum = paySuccessNum;
	}

	public Long getPayPartNum() {
		return this.payPartNum;
	}

	public void setPayPartNum(Long payPartNum) {
		this.payPartNum = payPartNum;
	}

	public Long getPayNotNum() {
		return this.payNotNum;
	}

	public void setPayNotNum(Long payNotNum) {
		this.payNotNum = payNotNum;
	}

	public Long getInitialNum() {
		return this.initialNum;
	}

	public void setInitialNum(Long initialNum) {
		this.initialNum = initialNum;
	}

	public Date getMakeTime() {
		return this.makeTime;
	}

	public void setMakeTime(Date makeTime) {
		this.makeTime = makeTime;
	}

	public String getTravelGroupStatus() {
		return this.travelGroupStatus;
	}

	public void setTravelGroupStatus(String travelGroupStatus) {
		this.travelGroupStatus = travelGroupStatus;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getGroupWordAble() {
		return this.groupWordAble;
	}

	public void setGroupWordAble(String groupWordAble) {
		this.groupWordAble = groupWordAble;
	}

	public String getMemo() {
		return this.memo;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Double getActProfitRate() {
		return actProfitRate;
	}

	public void setActProfitRate(Double actProfitRate) {
		this.actProfitRate = actProfitRate;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}