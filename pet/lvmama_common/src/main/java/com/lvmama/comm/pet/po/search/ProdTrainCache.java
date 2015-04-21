package com.lvmama.comm.pet.po.search;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;

public class ProdTrainCache implements java.lang.Comparable<ProdTrainCache>, Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6433335364385494725L;

	private Long timePriceId;

    private Long stationStationId;

    private String stationKey;
    
    private String cityKey;

    private Date visitTime;

    private String lineName;

    private String category;

    private String seatType;

    private Long price;

    private Long departureTime;

    private Long arrivalTime;

    private Long productId;

    private String productName;

    private Long prodBranchId;
    
    private String prodBranchName;

    private Long lineInfoId;
    
    private String soldout;
    
    private List<ProdTrainCache> pullmanList;
    private String departureCity;
    private String arrivalCity;
    private String departureStation;
    private String arrivalStation;
    
    /**
     * 出发站中文名
     */
    private String departureStationName;
    /**
     * 到达站中文名
     */
    private String arrivalStationName;
    /**
     * 花费时间
     */
    private Long takenTime;
    /**
     * 是否车次始发站
     */
    private String startStation;
    /**
     * 是否车次终点站
     */
    private String endStation;

    public Long getTimePriceId() {
        return timePriceId;
    }

    public void setTimePriceId(Long timePriceId) {
        this.timePriceId = timePriceId;
    }

    public Long getStationStationId() {
        return stationStationId;
    }

    public void setStationStationId(Long stationStationId) {
    	this.stationStationId = stationStationId;
    }

    public String getStationKey() {
        return stationKey;
    }

    public void setStationKey(String stationKey) {
        this.stationKey = stationKey == null ? null : stationKey.trim();
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = DateUtil.getDayStart(visitTime);
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }
    private static char[] lineCategoryList=new char[]{'G','T','D','T','K'};
    public String getCharCategory(){
//    	if(Constant.TRAIN_CATALOG.CATALOG_101.getAttr1().equals(category)){
//    		return "G";
//    	}else if(Constant.TRAIN_CATALOG.CATALOG_104.getAttr1().equals(category)){
//    		return "T";
//    	}else if(Constant.TRAIN_CATALOG.CATALOG_102.getAttr1().equals(category)){
//    		return "C";
//    	}else if(Constant.TRAIN_CATALOG.CATALOG_105.getAttr1().equals(category)){
//    		return "Z";
//    	}else if(Constant.TRAIN_CATALOG.CATALOG_103.getAttr1().equals(category)){
//    		return "D";
//    	}else if(Constant.TRAIN_CATALOG.CATALOG_106.getAttr1().equals(category)){
//    		return "K";
//    	}else{
//    		return "其他";//其他
//    	}
    	if(StringUtils.isNotEmpty(lineName)){
    		char c = lineName.charAt(0);
    		int pos = ArrayUtils.indexOf(lineCategoryList, c);
    		if(pos!=-1){
    			return String.valueOf(lineCategoryList[pos]);
    		}
    	}
    	return "X";
    }
    
    
    public String getZhSeatType(){ 
    	return Constant.TRAIN_SEAT_CATALOG.getCnName(seatType);
    }
    
    public String getZhDepartureTime(){
    	return TimePriceUtil.formatTime(departureTime);
    }

    public String getZhArrivalTime(){
    	return TimePriceUtil.formatTime(arrivalTime);
    }
    
    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType == null ? null : seatType.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Long departureTime) {
        this.departureTime = departureTime;
    }

    public Long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Long arrivalTime) {
        this.arrivalTime = arrivalTime;
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
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getProdBranchId() {
        return prodBranchId;
    }

    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    public Long getLineInfoId() {
        return lineInfoId;
    }

    public void setLineInfoId(Long lineInfoId) {
        this.lineInfoId = lineInfoId;
    }

	public String getProdBranchName() {
		return prodBranchName;
	}

	public void setProdBranchName(String prodBranchName) {
		this.prodBranchName = prodBranchName;
	}

	public String getSoldout() {
		return soldout;
	}

	public void setSoldout(String soldout) {
		this.soldout = soldout;
	}
	
	public void makeSoldout(){
		//票没售光并且火车票不可以销售受或不在售票时间范围内或售票时间在游玩时间36小时以内
		if(!hasSoldout()&&(!Constant.getInstance().hasTrainSaleEnable()
				|| TimePriceUtil.hasTrainSoldout() 
				|| DateUtils.addHours(visitTime, -36).before(new Date()))){
			this.soldout = "true";
		}
	}

	public String getZhTakeTime(){
		if(this.getTakenTime()!=null){
			long tmp=this.getTakenTime();
			long hour=0;
			if(tmp>=60){
				hour = tmp/60;
				tmp=tmp-hour*60;
			}
			StringBuffer sb = new StringBuffer();
			if(hour>0){
				sb.append(hour);
				sb.append("时");
			}
			if(tmp>0){
				sb.append(tmp);
				sb.append("分");
			}
			return sb.toString();
		}
		return "";
	}

	public float getPriceYuan(){
		return PriceUtil.convertToYuan(price);
	}

	public boolean hasSoldout(){
		return StringUtils.equals("true", soldout);
	}

	@Override
	public int compareTo(ProdTrainCache arg0) {
		if(this.getSeq()==arg0.getSeq()){
			return 0;
		}
		if(this.getSeq()>arg0.getSeq()){
			return 1;
		}else{
			return -1;
		}
	}
	
	public int getSeq(){
		return ArrayUtils.indexOf(SEAT_SEQ, seatType);
	}
	private static final String SEAT_SEQ[]={
		"204",
		"203",
		"201",
		"202",
		"216",
		"215",
		"205",
		"206",
		"207",
		"208",
		"209",
		"210",
		"211",
		"212",
		"213",
		"214"
	};

	public List<ProdTrainCache> getPullmanList() {
		return pullmanList;
	}

	public void setPullmanList(List<ProdTrainCache> pullmanList) {
		this.pullmanList = pullmanList;
	}
	
	public boolean isPullmanTicket(){
		return CollectionUtils.isNotEmpty(pullmanList);
	}

	public String getCityKey() {
		return cityKey;
	}

	public void setCityKey(String cityKey) {
		this.cityKey = cityKey;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}


	public String getArrivalStationName() {
		return arrivalStationName;
	}

	public void setArrivalStationName(String arrivalStationName) {
		this.arrivalStationName = arrivalStationName;
	}

	public Long getTakenTime() {
		return takenTime;
	}

	public void setTakenTime(Long takenTime) {
		this.takenTime = takenTime;
	}

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	public String getDepartureStationName() {
		return departureStationName;
	}

	public void setDepartureStationName(String departureStationName) {
		this.departureStationName = departureStationName;
	}
	
	public void setStartStationName(String name){
		startStation = Boolean.toString(StringUtils.equals(name, departureStationName));
	}
	public void setEndStationName(String name){
		endStation = Boolean.toString(StringUtils.equals(name, arrivalStationName));
	}
}