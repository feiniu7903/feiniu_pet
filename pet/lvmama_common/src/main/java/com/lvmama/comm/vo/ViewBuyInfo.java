package com.lvmama.comm.vo;

import com.lvmama.comm.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewBuyInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5118032649083322646L;
	private String userId;
	private Long productId;
	private Long prodBranchId;
	private Long packId;
	private Date visitDate;
	private String visitTime;
	private String userMemo;
	private String paymentTarget;
	private String num;
	private List<CouponInfo> couponList = new ArrayList<CouponInfo>();
	private Integer days;
	private String leaveTime;
	private String productType;
	private String subProductType;
	private String paymentChannel;
	private String channel;
	private List<TimeInfo> timeInfo = new ArrayList<TimeInfo>();
	private String selfPack="false";
	private String content;
	private Integer adult;
	private Integer child;
	private Date validBeginTime;
	private Date validEndTime;
	
	private String localCheck;
	
	private Long seckillId;
	private Long seckillBranchId;
	private Long waitPayment;
	private String seckillToken;
	/**
	 * 是否为不定期产品
	 * */
	private String isAperiodic = "false";
	/**
	 * 是否需要发票
	 * */
	private String needInvoice = "false";
    private Float cashValue;
	private Map<String, Integer> buyNum = new HashMap<String, Integer>();
	
	//不定期类别有效期结束日期
	private Map<String, String> validTime = new HashMap<String, String>();
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	
	public Long getPackId() {
		return packId;
	}
	public void setPackId(Long packId) {
		this.packId = packId;
	}
	public Map<String, Integer> getBuyNum() {
		return buyNum;
	}
	
	public void setProductid(Long productId) {
		this.setProductId(productId);
	}
	
	public void setVisitTime(String visitTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.visitTime = visitTime.trim();		
		try{
			if(StringUtils.isNotEmpty(this.visitTime)){
				this.visitDate = sdf.parse(this.visitTime);	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getVisitTime() {
		return visitTime;
	}
	
	public Map<Long, Long> getOrdItemProdList() {
		Map<Long, Long> map = new HashMap<Long, Long>();
		Set<String> keySet = buyNum.keySet();
		for (String string : keySet) {
			if (string.matches("^product_[0-9]+$")) {
				Long productId = Long.valueOf(string.substring(8));
				map.put(productId, Long.valueOf(buyNum.get(string)));
			}
		}
		return map;
	}
	
	/**
	 * 针对自由行的数据解析.
	 * key对应的是journeyProductId.
	 * 
	 * 酒店的数据格式是:hotel_类别ID_行程打包项ID_数量_入住时间_离店时间.
	 * 大交通的数数据格式是:traffic_类别及行程打包ID列表_时间.
	 * 其他的产品格式是:product_类别ID_行程打包项ID_数量_时间
	 * @return
	 */
	public Map<Long,TimeInfo> getOrdItemProdMap(){
		Map<Long,TimeInfo> map=new HashMap<Long, TimeInfo>();
		if(StringUtils.isNotEmpty(content)){
			String array[]=content.split(";");
			for(String str:array){
				String fields[]=str.split("_");
				if(str.startsWith("traffic_")){
					String[] jpIds=fields[1].split(",");
					for(int i=0;i<jpIds.length;i++){
						TimeInfo ti = new TimeInfo();
						ti.setProductType(fields[0]);
						String[] jp_branch=jpIds[i].split("-");
						if(jp_branch[0].startsWith("ADULT")){//表示成人
							ti.setQuantity(adult.longValue());//直接取成人数
						}else if(jp_branch[0].startsWith("CHILD")){
							ti.setQuantity(child.longValue());
						}
						ti.setProdBranchId(NumberUtils.toLong(jp_branch[1]));
						ti.setJourneyProductId(NumberUtils.toLong(jp_branch[2]));
						ti.setVisitTime(fields[2]);
						if(ti.checkedSuccess()){
							map.put(ti.getKey(), ti);
						}
					}
				}else{
					TimeInfo ti=new TimeInfo();
					ti.setProductType(fields[0]);
					ti.setProdBranchId(NumberUtils.toLong(fields[1]));
					if(str.startsWith("addition_")){
						ti.setQuantity(NumberUtils.toLong(fields[2]));
						ti.setVisitTime(visitTime);
					}else{
						ti.setJourneyProductId(NumberUtils.toLong(fields[2]));
						ti.setQuantity(NumberUtils.toLong(fields[3]));
						ti.setVisitTime(fields[4]);
						
						if(str.startsWith("hotel_")){//如果是酒店，计算的时间当中会存在一个离店时间
							ti.setLeaveTime(fields[5]);
						}
					}
					if(ti.checkedSuccess()){
						map.put(ti.getKey(), ti);
					}
				}
			}
		}
		return map;
	}
	
	//相关酒店产品列表
	public Map<Long, Long> getOrdItemHotelList() {
		Map<Long, Long> map = new HashMap<Long, Long>();
		Set<String> keySet = buyNum.keySet();
		for (String string : keySet) {
			if (string.matches("^hotel_[0-9]+$")) {
				Long productId = Long.valueOf(string.substring(6));
				map.put(productId, Long.valueOf(buyNum.get(string)));
			}
		}
		return map;
	}
	
	public String getUserMemo() {
		return (userMemo==null)?"":userMemo;
	}
	public void setUserMemo(String userMemo) {
		this.userMemo = userMemo;
	}
	public String getPaymentTarget() {
		return paymentTarget;
	}
	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}
	public String getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}
	public List<CouponInfo> getCouponList() {
		return couponList;
	}
	
	public String getNum() {
		return num;
	}
	
	public Long getNumLong() {
		return new Long(num);
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	public void setDays(Integer days) {
		if(days != null) {
			leaveTime = leaveTime == null ? getSpecifyDate(days) : leaveTime;
		}
		this.days = days;
	}
	public void setBuyNum(Map<String, Integer> buyNum) {
		this.buyNum = buyNum;
	}
	public String getLeaveTime() {
		if(this.days != null) {
			leaveTime = leaveTime == null ? getSpecifyDate(this.days) : leaveTime;
		}
		return leaveTime;
	}
	public Integer getDays() {
		return days;
	}

	public String getSpecifyDate(int day) {
		if(this.visitDate != null) {
			Date date=DateUtils.addDays(visitDate, day);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date.getTime());
		}
		return null;
	}
	
	public Integer getBuyNumValue(String key){
		Integer value = this.buyNum.get(key);
		if(value!=null){
			return value;
		}
		return 0;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public List<TimeInfo> getTimeInfo() {
		return timeInfo;
	}
	public void setTimeInfo(List<TimeInfo> timeInfo) {
		this.timeInfo = timeInfo;
	}
	public String getPaymentChannel() {
		for (CouponInfo coupon: couponList) {
			if (coupon!=null&&"true".equals(coupon.getChecked())) {
				return  coupon.getPaymentChannel();
			}
		}
		return paymentChannel;
	}
	
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public String getChannel() {
		if(this.channel != null && org.apache.commons.lang3.StringUtils.isNotEmpty(this.channel)){
			return this.channel;
		}
		return "FRONTEND";
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * @return the prodBranchId
	 */
	public Long getProdBranchId() {
		return prodBranchId;
	}
	/**
	 * @param prodBranchId the prodBranchId to set
	 */
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @param selfPack the selfPack to set
	 */
	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}
	
	
	
	/**
	 * @return the selfPack
	 */
	public String getSelfPack() {
		return selfPack;
	}
	public boolean hasSelfPack(){
		return StringUtils.equals(selfPack, "true");
	}
	/**
	 * @return the adult
	 */
	public Integer getAdult() {
		return adult==null?0:adult;
	}
	/**
	 * @param adult the adult to set
	 */
	public void setAdult(Integer adult) {
		this.adult = adult;
	}
	/**
	 * @return the child
	 */
	public Integer getChild() {
		return child==null?0:child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(Integer child) {
		this.child = child;
	}
	
	public int getTotalQuantity(){	
		return getAdult()+getChild();
	}
	public String getIsAperiodic() {
		return isAperiodic;
	}
	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}
	public boolean IsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?true:false;
	}
	public Map<String, String> getValidTime() {
		return validTime;
	}
	public void setValidTime(Map<String, String> validTime) {
		this.validTime = validTime;
	}
	
	/**
	 * 不定期产品
	 * 传入类别id,取对应的有效期结束日期
	 * */
	public Date getValidTimeValue(Long key){
		String validTime = this.validTime.get("product_" + key);
		if(validTime != null) {
			return DateUtil.getDayStart(DateUtil.toDate(validTime, "yyyy-MM-dd"));
		}
		return null;
	}
	public Date getValidBeginTime() {
		return validBeginTime;
	}
	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}
	public Date getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	public String getLocalCheck() {
		return localCheck;
	}
	public void setLocalCheck(String localCheck) {
		this.localCheck = localCheck;
	}

	public Long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}
	public Long getSeckillBranchId() {
		return seckillBranchId;
	}
	public void setSeckillBranchId(Long seckillBranchId) {
		this.seckillBranchId = seckillBranchId;
	}
	public Long getWaitPayment() {
		return waitPayment;
	}
	public void setWaitPayment(Long waitPayment) {
		this.waitPayment = waitPayment;
	}
	public String getSeckillToken() {
		return seckillToken;
	}
	public void setSeckillToken(String seckillToken) {
		this.seckillToken = seckillToken;
	}

  public Float getCashValue() {
      return cashValue;
  }

  public void setCashValue(Float cashValue) {
      this.cashValue = cashValue;
  }
}
