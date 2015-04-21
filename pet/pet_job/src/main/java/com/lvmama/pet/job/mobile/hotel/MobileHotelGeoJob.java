package com.lvmama.pet.job.mobile.hotel;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileHotelDest;
import com.lvmama.comm.pet.po.mobile.MobileHotelLandmark;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;

public class MobileHotelGeoJob extends MobileHotelBaseService{
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MobileHotelService mobileHotelService;
	
	/**
	 * 更新geo数据 
	 */
	public void updateHotelGeoStaticData() {
		if (Constant.getInstance().isJobRunnable()) {
		try {
		   logger.info("......updateHotelGeoStaticData...start...10..");
		   List<Element> hotelGeoList = MobileHotelUtils.getHotelGEODatas();
		   if(null != hotelGeoList && hotelGeoList.size() > 0){
			   // 默认最多查询50000条 ，其实够用的了。 
			   List<MobileHotelDest> mhdList = mobileHotelService.queryMobileHotelDestList(new HashMap<String,Object>());
			   // 把HotelGeo 节点转换成map结构 。 
			   Map<String,String> mhdMap = list2Map(mhdList);
			   for(int i = 0; i < hotelGeoList.size();i++ ) {
				   Element hotelGeo  = hotelGeoList.get(i);
				   String cityCode = hotelGeo.attributeValue("CityCode");
				   //logger.info("......updateHotelGeoStaticData....hotelGeo...start..11....=="+cityCode);
				   // 获取每个HotelGeo节点的版本号 
				   String version = getHotelGeoVersion(hotelGeo);
				   // 是否需要更新孩子节点 
				   boolean isUpdateChildNode = true;
				   if(version.equals(mhdMap.get(cityCode))) {
					   isUpdateChildNode = false;
				   }
				   
				   // 处理mobileHotelList逻辑
				   MobileHotelDest mhd = new MobileHotelDest();
				   // 判断dest 是新增还是修改
				   boolean isAdd = true;
				   if(null != mhdMap && null != mhdMap.get(cityCode)) {
					   isAdd = false;
				   }
				   // 
				   String cityName = hotelGeo.attributeValue("CityName").replaceAll("（.*）", "");
				   mhd.setCityCode(hotelGeo.attributeValue("CityCode"));
				   mhd.setCityName(cityName);
				   mhd.setHotelgeoNodeVersion(version); // 判断子节点是否需要更新
				   
				   // 设置placeId 和 拼音 
				   Map<String,Object> param = new HashMap<String,Object>();
				   param.put("name", cityName);
				   Map<String,Object> m = getNameByLocation(param);
				   if(null != m ) {
					   if(null != m.get("id") && !StringUtils.isEmpty(m.get("id").toString())) {
						   mhd.setPlaceId(Long.valueOf(m.get("id").toString()));
					   }
					   if(null != m.get("pinyin") && !StringUtils.isEmpty(m.get("pinyin").toString())) {
						   mhd.setPinyin(m.get("pinyin").toString());
					   } else {
						   mhd.setPinyin(Pinyin4jUtils.makeStringByStringSet(cityName));
					   }
				   } else {
					   mhd.setPinyin(Pinyin4jUtils.makeStringByStringSet(cityName));
				   }
				  
				   mhd.setProvinceId(hotelGeo.attributeValue("ProvinceId"));
				   mhd.setProvinceName(hotelGeo.attributeValue("ProvinceName"));
				 
				   if(isAdd) {
					   mobileHotelService.insertMobileHotelDest(mhd);
					   //logger.info("......updateHotelGeoStaticData....insertMobileHotelDest...success....13.....");
				   } else {
					   mobileHotelService.updateMobileHotelDest(mhd);
					  // logger.info("......updateHotelGeoStaticData....updateMobileHotelDest...success.....15.....");
				   }
				   // 判断是否需要更新子节点 
				   if(isUpdateChildNode) {
					   updateHotelGeoChildNode(hotelGeo,cityCode);
				   }
			   }
		   }
		   logger.info("......updateHotelGeoStaticData...success....17..");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("......updateHotelGeoStaticData....error....18....");
		}
		}
	}
	
	/**
	 * list 结构根据hotelId放在map中. 
	 * @param list
	 * @return map
	 */
	public Map<String,String> list2Map(List<MobileHotelDest> list) {
		Map<String,String> resutlMap = new HashMap<String,String>();
		if(null != list && list.size() > 0 ) {
			for(int i = 0 ;i < list.size();i++) {
				MobileHotelDest mhl = list.get(i);
				resutlMap.put(mhl.getCityCode(), mhl.getHotelgeoNodeVersion());
			}
		}
		return resutlMap;
	}
	
	
	/**
	 * 更新孩子节点 .
	 */
	public void updateHotelGeoChildNode(Element hotelGeoElement,String cityCode) {
		if(StringUtils.isEmpty(cityCode) || null == hotelGeoElement) {
			return ;
		}
		try {
			//logger.info("......updateHotelGeoChildNode....start....20..." + cityCode);
			//根据cityCode删除所有子节点 
			mobileHotelService.deleteMobileHotelLandmarkByCityCode(cityCode);
			// 添加新的节点 
			List<Element> hgElement = hotelGeoElement.elements();// 包括 Districts  CommericalLocations LandmarkLocations 
			if(null != hgElement && hgElement.size() > 0) {
				for(int i = 0; i < hgElement.size();i++) {
					Element elment = hgElement.get(i);
					if(null != elment &&  null != elment.elements() ) {
						String elementName = elment.getName(); // Districts  CommericalLocations LandmarkLocations 
						//logger.info("......updateHotelGeoChildNode..."+elementName+" ...start.......21......" );
						for(int j = 0 ;j < elment.elements().size();j++) {
							Element location  = (Element)elment.elements().get(j); // Location 
							if(null != location ) {
								MobileHotelLandmark mhl = new MobileHotelLandmark();
								mhl.setCityCode(cityCode);
								mhl.setLocationId(location.attributeValue("Id"));
								mhl.setLocationName(location.attributeValue("Name"));
								mhl.setLocationType(elementName);
								mhl.setPlaceId(0l);
								mhl.setPinyin(Pinyin4jUtils.makeStringByStringSet(location.attributeValue("Name")));
								mobileHotelService.insertMobileHotelLandmark(mhl);
							}
						}
					}
				}
			}
			logger.info("......updateHotelGeoChildNode....success.....24....."+ cityCode);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("......updateHotelGeoChildNode....error.....25........");
		}
		
	}
	
	/**
	 * 获取 HotelGeo  节点的版本。三个节点：Districts  CommericalLocations LandmarkLocations 
	 * @param hotelGeoElement
	 * @return
	 */
	public String  getHotelGeoVersion(Element hotelGeoElement) {
		StringBuffer sb = new StringBuffer("");
		if(null == hotelGeoElement) {
			return "";
		}
		List<Element> hgElement = hotelGeoElement.elements();// 包括 Districts  CommericalLocations LandmarkLocations 
		if(null != hgElement && hgElement.size() > 0) {
			for(int i = 0; i < hgElement.size();i++) {
				Element elment = hgElement.get(i);
				if(null != elment &&  null != elment.elements() ) {
					for(int j = 0 ;j < elment.elements().size();j++) {
						Element node = (Element)elment.elements().get(j);
						if(null != node && null != node.attributes()) {
							for(int a = 0; a < node.attributes().size();a++) {
								Attribute att = (Attribute)node.attributes().get(a);
								sb.append(att.getName()).append(att.getValue());
							}
						}
						
					}
				}
				
			}
		}
		
		try {
			return MD5.encode(sb.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	

	
}
