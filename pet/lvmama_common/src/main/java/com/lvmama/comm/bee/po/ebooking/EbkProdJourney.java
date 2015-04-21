package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.vo.Constant;

/**
 * @since 2013-09-24
 */
public class EbkProdJourney implements Serializable {

    private static final long serialVersionUID = 138001284952923133L;

    /**
     * column EBK_PROD_JOURNEY.JOURNEY_ID
     */
    private Long journeyId;

    /**
     * column EBK_PROD_JOURNEY.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_JOURNEY.DAY_NUMBER
     */
    private Long dayNumber;

    /**
     * column EBK_PROD_JOURNEY.TITLE
     */
    private String title;

    /**
     * column EBK_PROD_JOURNEY.CONTENT
     */
    private String content;

    /**
     * column EBK_PROD_JOURNEY.DINNER
     */
    private String dinner;

    /**
     * column EBK_PROD_JOURNEY.HOTEL
     */
    private String hotel;

    /**
     * column EBK_PROD_JOURNEY.TRAFFIC
     */
    private String traffic;
    
    /**
     * column EBK_PROD_JOURNEY.MULTI_JOURNEY_ID
     */
    private Long multiJourneyId;
    
    
	public Long getMultiJourneyId() {
		return multiJourneyId;
	}

	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}

	private List<String> traffics = new ArrayList<String>();
    
    /**
     * 行程描述-图片集合
     */
    private List<ComPicture> comPictureJourneyList=new ArrayList<ComPicture>();

    public EbkProdJourney() {
        super();
    }

    public EbkProdJourney(Long journeyId, Long productId, Long dayNumber, String title, String content, String dinner, String hotel, String traffic) {
        this.journeyId = journeyId;
        this.productId = productId;
        this.dayNumber = dayNumber;
        this.title = title;
        this.content = content;
        this.dinner = dinner;
        this.hotel = hotel;
        this.traffic = traffic;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.JOURNEY_ID
     */
    public Long getJourneyId() {
        return journeyId;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.JOURNEY_ID
     * @param journeyId
     */
    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.DAY_NUMBER
     */
    public Long getDayNumber() {
        return dayNumber;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.DAY_NUMBER
     * @param dayNumber
     */
    public void setDayNumber(Long dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.TITLE
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.CONTENT
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.DINNER
     */
    public String getDinner() {
        return dinner;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.DINNER
     * @param dinner
     */
    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.HOTEL
     */
    public String getHotel() {
        return hotel;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.HOTEL
     * @param hotel
     */
    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    /**
     * getter for Column EBK_PROD_JOURNEY.TRAFFIC
     */
    public String getTraffic() {
        return traffic;
    }
    public String getTrafficZh() {
    	return Constant.TRAFFIC_TYPE.getCnName(traffic);
    }
    public String getTrafficSplitZh() {
    	String value="";
    	if(StringUtils.isNotBlank(traffic)){
    		String traffics[]=traffic.split(",");
    		for (String trafficTemp : traffics) {
    			value+=Constant.TRAFFIC_TYPE.getCnName(trafficTemp)+",";
			}
    	}
    	if(StringUtils.isNotBlank(value) && value.length()>0){
    		value=value.substring(0,value.length()-1);
    	}
        return value;
    }

    /**
     * setter for Column EBK_PROD_JOURNEY.TRAFFIC
     * @param traffic
     */
    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

	public List<ComPicture> getComPictureJourneyList() {
		return comPictureJourneyList;
	}

	public void setComPictureJourneyList(List<ComPicture> comPictureJourneyList) {
		this.comPictureJourneyList = comPictureJourneyList;
	}

	public List<String> getTraffics() {
		if (null != traffic) {
			String[] strs = traffic.split(",");
			for (int i = 0; i < strs.length; i++) {
				traffics.add(strs[i]); 
			}
		}
		return traffics;
	}

	public void setTraffics(List<String> traffics) {
		this.traffics = traffics;
	}
	
	
}