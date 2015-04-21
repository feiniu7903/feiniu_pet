package com.lvmama.clutter.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 自由行相关信息. 
 * @author qinzubo
 */
public class MobileProductRoute extends MobileProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDest; // 出发地
	private String toDest; // 目的地
	
	// v3.1
	/**
	 * 是否需要签证 
	 */
	private boolean visa;
	
	/**
	 * 游玩天数
	 */
	public String visitDay; 
	
	
	public String getAbsoluteSmallImageUrl() {
		return StringUtils.isEmpty(this.getSmallImage()) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.getSmallImage();
	}

	public String getVisaUrl() {
		return "/html5/visa.htm?productId="+ getProductId();
	}
	public boolean hasSelfPack(){
		return false;
	}


	public String getFromDest() {
		return fromDest;
	}


	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}


	public String getToDest() {
		return toDest;
	}


	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	
	public boolean isVisa() {
		return visa;
	}


	public void setVisa(boolean visa) {
		this.visa = visa;
	}
	
	
	public String getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}

	public String getShareContent(){
		if(this.getMarketPriceYuan()==0){
			return "";
		}
		String content =String.format("我在@驴妈妈旅游网 上看中了旅游线路 “%s”，%s折，仅售%s 。",
				com.lvmama.clutter.utils.ClientUtils.subProductName(this.getProductName()),
				PriceUtil.formatDecimal(this.getSellPriceYuan()/this.getMarketPriceYuan()*10),this.getSellPriceYuan());
		//PriceUtil.formatDecimal(mainProduct.getSellPrice()/mainProduct.getMarketPriceYuan()*100)
		return content;
	}
	
	public String getShareContentTitle(){
		if(this.getMarketPriceYuan()==0){
			return null;
		}
		String contentTitle =String.format(ClutterConstant.SHARE_CONTENT,this.getProductName(),PriceUtil.formatDecimal(this.getSellPriceYuan()/this.getMarketPriceYuan()*10));
		return contentTitle;
	}
	
	public String getWapUrl(){
		return "http://m.lvmama.com/clutter/route/"+getProductId();
	}

}
