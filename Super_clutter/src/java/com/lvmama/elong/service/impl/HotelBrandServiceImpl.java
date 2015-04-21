package com.lvmama.elong.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.HotelBrand;
import com.lvmama.elong.service.IHotelBrandService;

public class HotelBrandServiceImpl implements IHotelBrandService {
	private final Logger logger = Logger.getLogger(this.getClass());
    private final static String MOBILE_HOTEL_LIST_URL = "http://api.elong.com/xml/v2.0/hotel/brand_cn.xml";
    
    /**
     * 获取酒店brand ，获取全部数据，大概8000多条 
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<HotelBrand> getHotelBrandList(Map<String,Object> params) throws ElongServiceException {
		List<HotelBrand>  hotelBrandList = new ArrayList<HotelBrand>();
		try {
			String xml = HttpsUtil.requestGet(MOBILE_HOTEL_LIST_URL);
			if(!StringUtils.isEmpty(xml)) {
				Document document = XmlUtils.createDocument(xml);
				List<Element> hotelIndexElements = document.getRootElement().elements();
				if(null != hotelIndexElements && hotelIndexElements.size() > 0) {
					for(int i = 0; i < hotelIndexElements.size();i++) {
						Element brandElement = hotelIndexElements.get(i);
						if(null != brandElement) {
							HotelBrand hb = new HotelBrand();
							hb.setBrandId(Integer.valueOf(brandElement.attributeValue("BrandId")));
							hb.setGroupId(Integer.valueOf(brandElement.attributeValue("GroupId")));
							hb.setLetters(brandElement.attributeValue("Letters"));
							//hb.setLettersEn(brandElement.attributeValue("BrandId"));
							hb.setName(brandElement.attributeValue("Name"));
							//hb.setNameEn(brandElement.attributeValue("BrandId"));
							hb.setShortName(brandElement.attributeValue("ShortName"));
							//hb.setShortNameEn(brandElement.attributeValue("BrandId"));
							hotelBrandList.add(hb);
						}
					}
				}
			} else {
				logger.info("excute getHotelBrandList xml is null........ ");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("excute getHotelBrandList method error........ ");
		}
		
		return hotelBrandList;
	}
}
