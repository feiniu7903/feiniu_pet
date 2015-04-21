/**
 * 
 */
package com.lvmama.comm.bee.po.op;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.vo.FincConstant;

/**
 * 旅游团PO
 * 
 * @author yangbin
 * 
 */
public class OpTravelGroup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8613393696815691044L;
	
	private Long travelGroupId;
	private String travelGroupCode;
	private Long productId;
	private String productName;
	private Long sellPrice;
	private Long settlementPrice;
	private Date visitTime;
	private Long initialGroupNum;// 计划人数
	private Long paySuccessNum;
	private Long payPartNum;
	private Long payNotNum;
	private Long initialNum;// 最小成团人数
	private Date makeTime;
	private String travelGroupStatus;
	private Date createTime;
	private String groupWordAble;//能否发出团通知书
	private List<OpTravelGroup> sameProductGroup;
	private Long days;
	private String memo;
	private Date backTime;
	private String settlementStatus;
	private Double actIncoming;		//团收入
	private Double actAllowance;	//活动折让
	private Double actProfit;	//团利润
	private Long actAdult;	//成人数
	private Long actChild;	//儿童数
	
	/**
	 * 所属组id
	 */
	private Long orgId;
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getTravelGroupId() {
		return travelGroupId;
	}

	public void setTravelGroupId(Long travelGroupId) {
		this.travelGroupId = travelGroupId;
	}

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public long getPaySuccessNum() {
		return paySuccessNum;
	}

	public void setPaySuccessNum(long paySuccessNum) {
		this.paySuccessNum = paySuccessNum;
	}

	public long getPayPartNum() {
		return payPartNum;
	}

	public void setPayPartNum(long payPartNum) {
		this.payPartNum = payPartNum;
	}

	public long getPayNotNum() {
		return payNotNum;
	}

	public void setPayNotNum(long payNotNum) {
		this.payNotNum = payNotNum;
	}

	public Date getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(Date makeTime) {
		this.makeTime = makeTime;
	}

	public String getTravelGroupStatus() {
		return travelGroupStatus;
	}

	public void setTravelGroupStatus(String travelGroupStatus) {
		this.travelGroupStatus = travelGroupStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getInitialGroupNum() {
		return initialGroupNum;
	}

	public void setInitialGroupNum(long initialGroupNum) {
		this.initialGroupNum = initialGroupNum;
	}

	public long getInitialNum() {
		return initialNum;
	}

	public void setInitialNum(long initialNum) {
		this.initialNum = initialNum;
	}

	public long getRemain() {
		if (initialGroupNum < 1) {
			return initialGroupNum;
		} else {
			return initialGroupNum - paySuccessNum - payPartNum - payNotNum;
		}
	}

	public String getRemainStr() {
		if (initialGroupNum < 0) {
			return "不限";
		} else if (initialGroupNum == 0) {
			return "0";
		} else {
			long c = getRemain();
			return String.valueOf(c);
		}

	}

	public String getSellPriceStr() {
		return String.valueOf(sellPrice / 100);
	}

	public String getSettlementPriceStr() {
		return String.valueOf(settlementPrice / 100);
	}

	public String getTravelGroupStatusStr()	{
		int pos=ArrayUtils.indexOf(travel_status_map, travelGroupStatus);
		try{		
			return travel_status_map[pos+1];
		}catch(Exception ex){
			return "未定义";
		}
	}
	
	

	


	/**
	 * @return the groupWordAble
	 */
	public String getGroupWordAble() {
		return groupWordAble;
	}

	/**
	 * @param groupWordAble the groupWordAble to set
	 */
	public void setGroupWordAble(String groupWordAble) {
		this.groupWordAble = groupWordAble;
	}

	/**
	 * 判断可发送出团通知书状态是否是true.
	 * @return true
	 */
	public boolean isGroupWordAbled(){
		return StringUtils.equals(groupWordAble, "true");
	}

	



	/**
	 * @return the sameProductGroup
	 */
	public List<OpTravelGroup> getSameProductGroup() {
		return sameProductGroup;
	}

	/**
	 * @param sameProductGroup the sameProductGroup to set
	 */
	public void setSameProductGroup(List<OpTravelGroup> sameProductGroup) {
		this.sameProductGroup = sameProductGroup;
	}





	/**
	 * @return the days
	 */
	public Long getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Long days) {
		this.days = days;
	}





	private static String travel_status_map[] = { "CONFIRM", "已成团", "CANCEL",
			"取消成团", "END", "封团", "NORMAL", "未成团" };

	public String getMemo() {
		return memo;
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

	public String getSettlementStatus() {
		return settlementStatus;
	}
	public String getSettlementStatusName(){
		if(FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(settlementStatus)){
			return "未做成本";
		}else if(FincConstant.GROUP_SETTLEMENT_STATUS_COSTED.equals(settlementStatus)){
			return "已做成本";
		}else if(FincConstant.GROUP_SETTLEMENT_STATUS_CONFIRMED.equals(settlementStatus)){
			return "确认成本";
		}else if(FincConstant.GROUP_SETTLEMENT_STATUS_CHECKED.equals(settlementStatus)){
			return "已核算";
		}
		return null;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public long getVisitTimeNum(){
		return visitTime.getTime();
	}

	public Double getActIncoming() {
		return actIncoming;
	}

	public void setActIncoming(Double actIncoming) {
		this.actIncoming = actIncoming;
	}

	public Double getActAllowance() {
		return actAllowance;
	}

	public void setActAllowance(Double actAllowance) {
		this.actAllowance = actAllowance;
	}

	public Double getActProfit() {
		return actProfit;
	}

	public void setActProfit(Double actProfit) {
		this.actProfit = actProfit;
	}

	public Long getActAdult() {
		return actAdult;
	}

	public void setActAdult(Long actAdult) {
		this.actAdult = actAdult;
	}

	public Long getActChild() {
		return actChild;
	}

	public void setActChild(Long actChild) {
		this.actChild = actChild;
	}

}
