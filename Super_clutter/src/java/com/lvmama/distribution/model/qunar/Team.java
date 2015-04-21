package com.lvmama.distribution.model.qunar;

// <team takeoffdate="{团期，如：2012-07-12,必填}"
// marketprice="{市场价，正整数,必填}----驴妈妈售价"
// adultprice="{成人价，正整数,必填}"
// containchildprice="{是否包含儿童价，true/false}"
// childprice="{儿童价，整数,必填}"
// childpricedesc="{儿童价说明}"
// roomnum="{床位数：1/2/3/4/5/6,默认2}"
// roomsendprice="{单房差，正整数}"
// availablecount="{可用库存，正整数,必填}"
// minbuycount="{一次最少购买数，正整数，默认为1}"
// maxbuycount="{一次最多购买数，大于一次最少购买数，默认为9}"
// pricedesc="{起价说明}"
// status="{团期状态,on sale/offline}" />
public class Team {
//	private String team = "team";
	private String takeoffdate;
	private String marketprice;
	private String adultprice;
	private String containchildprice;
	private String childprice;
	private String childpricedesc;
	private String roomnum;
	private String roomsendprice;
	private String availablecount;
	private String minbuycount;
	private String maxbuycount;
	private String pricedesc;
	private String status;
	/**
	 * 套餐
	 */
	private boolean ispackage;
	private String pricename;
	private String qunarprice;
	
	public String getTakeoffdate() {
		return takeoffdate;
	}

	public void setTakeoffdate(String takeoffdate) {
		this.takeoffdate = takeoffdate;
	}

	public String getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(String marketprice) {
		this.marketprice = marketprice;
	}

	public String getAdultprice() {
		return adultprice;
	}

	public void setAdultprice(String adultprice) {
		this.adultprice = adultprice;
	}

	public String getContainchildprice() {
		return containchildprice;
	}

	public void setContainchildprice(String containchildprice) {
		this.containchildprice = containchildprice;
	}

	public String getChildprice() {
		return childprice;
	}

	public void setChildprice(String childprice) {
		this.childprice = childprice;
	}

	public String getChildpricedesc() {
		return childpricedesc;
	}

	public void setChildpricedesc(String childpricedesc) {
		this.childpricedesc = childpricedesc;
	}

	public String getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(String roomnum) {
		this.roomnum = roomnum;
	}

	public String getRoomsendprice() {
		return roomsendprice;
	}

	public void setRoomsendprice(String roomsendprice) {
		this.roomsendprice = roomsendprice;
	}

	public String getAvailablecount() {
		return availablecount;
	}

	public void setAvailablecount(String availablecount) {
		this.availablecount = availablecount;
	}

	public String getMinbuycount() {
		return minbuycount;
	}

	public void setMinbuycount(String minbuycount) {
		this.minbuycount = minbuycount;
	}

	public String getMaxbuycount() {
		return maxbuycount;
	}

	public void setMaxbuycount(String maxbuycount) {
		this.maxbuycount = maxbuycount;
	}

	public String getPricedesc() {
		return pricedesc;
	}

	public void setPricedesc(String pricedesc) {
		this.pricedesc = pricedesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuffer builder = new StringBuffer();
		builder.append("<team");
		builder.append(" takeoffdate=\"");
		builder.append(takeoffdate);
		builder.append("\" marketprice=\"");
		builder.append(marketprice);
		if(this.isIspackage()){
			builder.append("\" pricename=\"");
			builder.append(pricename);
			builder.append("\" qunarprice=\"");
			builder.append(qunarprice);
		}else{
			builder.append("\" adultprice=\"");
			builder.append(adultprice);
			builder.append("\" containchildprice=\"");
			builder.append(containchildprice);
			builder.append("\" childprice=\"");
			builder.append(childprice);
			builder.append("\" childpricedesc=\"");
			builder.append(childpricedesc);
			builder.append("\" roomnum=\"");
			builder.append(roomnum);
			builder.append("\" roomsendprice=\"");
			builder.append(roomsendprice);
		}
		builder.append("\" availablecount=\"");
		builder.append(availablecount);
		builder.append("\" minbuycount=\"");
		builder.append(minbuycount);
		builder.append("\" maxbuycount=\"");
		builder.append(maxbuycount);
		builder.append("\" pricedesc=\"");
		builder.append(pricedesc);
		builder.append("\" status=\"");
		builder.append(status);
		builder.append("\"/>");
		return builder.toString();
	}

	/**
	 * 是否是套餐
	 * @return
	 */
	public boolean isIspackage() {
		return ispackage;
	}

	public void setIspackage(boolean ispackage) {
		this.ispackage = ispackage;
	}

	public String getPricename() {
		return pricename;
	}

	public void setPricename(String pricename) {
		this.pricename = pricename;
	}

	public String getQunarprice() {
		return qunarprice;
	}

	public void setQunarprice(String qunarprice) {
		this.qunarprice = qunarprice;
	}

}