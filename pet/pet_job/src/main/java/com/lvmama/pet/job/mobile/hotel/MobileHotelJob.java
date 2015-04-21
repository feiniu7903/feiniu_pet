package com.lvmama.pet.job.mobile.hotel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileHotel;
import com.lvmama.comm.pet.po.mobile.MobileHotelListVersion;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoom;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoomImage;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.comm.vo.Constant;
/**
 * 
 * @author qinzubo
 *
 */
public class MobileHotelJob extends MobileHotelBaseService{
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MobileHotelService mobileHotelService ;
	
	int step = 15000;
	
	/**
	 * 更新酒店相关静态数据的job-单个线程执行. 
	 */
	public void updateHotelStaticData() {
		if (Constant.getInstance().isJobRunnable()) {
			// 删除不符合条件的记录 
			try{
				int rows = mobileHotelService.deleteMobileHotelVerionNotInHotelMobile();
				logger.info("...MobileHotelJob deleteMobileHotelVerionNotInHotelMobile....rows==" + rows);
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("...MobileHotelJob deleteMobileHotelVerionNotInHotelMobile....error==");
			}
			
			// 默认每次更新4天的。 
			int days = 4;
            if("1".equals(getWeekDay(new Date()))) {
				// 修复前10天的数据
				days = 10;
			}

			// 如果是下周一  , 
 			if(System.currentTimeMillis() > this.getDateTime("2014-01-10") && System.currentTimeMillis() < this.getDateTime("2014-01-11")) {
 				// 修复前200天的数据
 				days = 200;
 			}
			
			logger.info("...MobileHotelJob updateHotelStaticData  start...10....days==" + days);
			try {
				// elong列表数据 
				List<Element> listElement = MobileHotelUtils.getHotelList();
				if(null != listElement && listElement.size() > 0) {
					
					// 修复静态数据
					addOrUpdateHotelInfos4Bugs();
					
					logger.info("...get elong hotelList xml data successs.....11......"+listElement.size());
					Long count = mobileHotelService.countMobileHotelListVersionList(getDefaultMap());
					//数据库中是否有记录 ， 有的话需要比较 
					boolean needQuery = true;
					if(count < 1) {
						needQuery = false;
					}
					// 当前日期
					for(int i = 0; i < listElement.size() ;i++) {
						Element element = listElement.get(i);
						// 只更新最近4天的 ,
						if(isNearest2Days(element.attributeValue("UpdatedTime"),days)) {
							logger.info("... has update hotelList size ......999..... " + i);
							return ;
						}
						String hotelId = element.attributeValue("HotelId");
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("hotelId", hotelId);
						boolean isAdd = true; // 是否新增 
						boolean needUpdate = true;
						MobileHotelListVersion mhl = new MobileHotelListVersion();
						if(needQuery) {
							mhl = mobileHotelService.selectMobileHotelListVersionByHotelId(hotelId);
							// 需要更新 ，如果记录已经存在 
							if(null != mhl) {
								isAdd = false;
								if(!mhl.getUpdatedTime().equals(element.attributeValue("UpdatedTime"))) {
									needUpdate = true; // 如果时间不同，则需要更新 
								} else {
									needUpdate = false; // 如果过时间相同 则不需要更新 
								}
							} 
						}
						
						if(isAdd) {
							// 如果是数据库原来没有的数据 ,新增
							addOrUpdateMobileHotelListVersion(element,new MobileHotelListVersion(),false);
						} else {
							if(needUpdate) {
								addOrUpdateMobileHotelListVersion(element,mhl,true);
							}
						}
					}
				}
				logger.info("...MobileHotelJob updateHotelStaticData  success...18.... ");
			}catch(Exception e){
				e.printStackTrace();
				logger.error("...MobileHotelJob updateHotelStaticData  error...19.... ");
			}
		}
	}
	
	/**
	 * 更新酒店列表版本。
	 * @param element
	 * @param mhl
	 * @param isUpdate
	 */
	public void addOrUpdateMobileHotelListVersion(Element element,MobileHotelListVersion mhl,boolean isUpdate) {
		if(null == element || mhl == null ) {
			return;
		}
		mhl.setProducts(element.attributeValue("Products"));
		mhl.setStatus(element.attributeValue("Status"));
		mhl.setUpdatedTime(element.attributeValue("UpdatedTime"));
		// 如果是更新  
		if(isUpdate) {
			mobileHotelService.updateMobileHotelListVersion(mhl);
			//logger.info("...... update mobile_hotel_list_version  success...20.... "+ mhl.getHotelId());
		} else {
			mhl.setHotelId(element.attributeValue("HotelId"));
			mobileHotelService.insertMobileHotelListVersion(mhl);
			//logger.info("...... insert mobile_hotel_list_version  success...21.... "+ mhl.getHotelId());
		}
		
		// 如果酒店可用 
		if("0".equals(element.attributeValue("Status"))) {
			// 更新酒店详情 
			addOrUpdateHotelInfos(element.attributeValue("HotelId"));
		}
		
	}
	
	/**
	 * 更新酒店详情
	 * @param hotelId
	 */
	public void addOrUpdateHotelInfos(String hotelId) {
		if(StringUtils.isEmpty(hotelId) || hotelId.length() < 2 ) {
			logger.error("......... addOrUpdateHotelInfos  hotelId.length lt 2   success...23.... ");
			return ;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		// 酒店节点 
		Element element = MobileHotelUtils.getHotelInfo(hotelId.substring(hotelId.length() - 2,hotelId.length()),hotelId,map);
		if(null != element) {
			// 酒店基本信息 
			try {
				addOrUpdateHotelDetail(element,hotelId,map,0);
				logger.info("......... addOrUpdateHotelInfos  mobile_hotel detail success...25.... ");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("MobileHotelJob addOrUpdateHotelDetail  error...26.... ");
			}
			// 房间 
			try {
				addOrUpdateHotelRooms(element,hotelId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("MobileHotelJob addOrUpdateHotelRooms  error...29.... ");
			}
			
			// 房间图片 
			try {
				addOrUpdateHotelRoomsImages(element,hotelId);
				//logger.info("......... addOrUpdateHotelRoomsImages  success...32.... ");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("MobileHotelJob addOrUpdateHotelRoomsImages  error...32.... ");
			}
			
		}
	}
	
	/**
	 * 更新酒店基本信息. 
	 * @param element 酒店节点 
	 * @param hotelId 酒店id 
	 * @param tag 0 是否需要二次查询 。 
	 */
	public void addOrUpdateHotelDetail(Element element,String hotelId,Map<String,Object> map,int tag) throws Exception {
		MobileHotel mh = mobileHotelService.selectMobileHotelByHotelId(hotelId);
		// 酒店详情节点
		Element elementDetail = MobileHotelUtils.getHotelInfoByType(element,"Detail");
		Element elementReview  = MobileHotelUtils.getHotelInfoByType(element,"Review");
		if(null != elementDetail) {
			boolean isAdd = true;
			if(null == mh) {
				mh = new MobileHotel();
				mh.setHotelId(hotelId);
			} else {
				// 如果酒店详情存在且数据没有变化 
				if(mh.getHotelDetailNodeVersion().equals(map.get("version"))) {
					return;
				} else {
					isAdd = false;
				}
			}
			mh.setAddress(XmlUtils.getChildText(elementDetail,"Address"));
			mh.setBrandid(XmlUtils.getChildText(elementDetail,"BrandId"));
			mh.setBusinesszone(XmlUtils.getChildText(elementDetail,"BusinessZone"));
			mh.setCategory(XmlUtils.getChildText(elementDetail,"Category"));
			mh.setCityid(XmlUtils.getChildText(elementDetail,"CityId"));
			mh.setCreditcards(XmlUtils.getChildText(elementDetail,"CreditCards"));
			mh.setDescription(XmlUtils.getChildText(elementDetail,"Description"));
			mh.setDiningamenities(XmlUtils.getChildText(elementDetail,"DiningAmenities"));
			mh.setDistrict(XmlUtils.getChildText(elementDetail,"District"));
			mh.setEstablismentdate(XmlUtils.getChildText(elementDetail,"EstablishmentDate"));
			mh.setFacilities(XmlUtils.getChildText(elementDetail,"Facilities"));
			mh.setFax(XmlUtils.getChildText(elementDetail,"Fax"));
			mh.setGeneralamenities(XmlUtils.getChildText(elementDetail,"GeneralAmenities"));
			mh.setGooglelat(XmlUtils.getChildText(elementDetail,"GoogleLat"));
			mh.setGooglelon(XmlUtils.getChildText(elementDetail,"GoogleLon"));
			mh.setGroupid(XmlUtils.getChildText(elementDetail,"GroupId"));
			// 待做 。。。
			mh.setHotelDetailNodeVersion(map.get("version").toString());
			mh.setPlaceId(0l); // 
			
			mh.setIntroeditor(MobileHotelUtils.getStringMaxLength(XmlUtils.getChildText(elementDetail,"IntroEditor")));
			mh.setIsapartment(XmlUtils.getChildText(elementDetail,"IsApartment"));
			mh.setIseconomic(XmlUtils.getChildText(elementDetail,"IsEconomic"));
			mh.setName(XmlUtils.getChildText(elementDetail,"Name"));
			mh.setPhone(XmlUtils.getChildText(elementDetail,"Phone"));
			mh.setPostalcode(XmlUtils.getChildText(elementDetail,"PostalCode"));
			mh.setRecreationamenities(XmlUtils.getChildText(elementDetail,"RecreationAmenities"));
			mh.setRenovationdate(XmlUtils.getChildText(elementDetail,"RenovationDate"));
			mh.setStarrate(XmlUtils.getChildText(elementDetail,"StarRate"));
			mh.setSurroundings(XmlUtils.getChildText(elementDetail,"Surroundings"));
			mh.setTraffic(XmlUtils.getChildText(elementDetail,"Traffic"));
			mh.setRoomamenities(XmlUtils.getChildText(elementDetail,"RoomAmenities")); // 房间设施
			
			if(null != elementReview) {
				mh.setScore(elementReview.attributeValue("Score"));
				mh.setGood(str2Long(elementReview.attributeValue("Good")));
				mh.setCount(str2Long(elementReview.attributeValue("Count")));
				mh.setPoor(str2Long(elementReview.attributeValue("Poor")));
			}
			
			if(isAdd) {
				mobileHotelService.insertMobileHotel(mh);
				//logger.info("... insert mobile_hotel  success...40.... " + mh.getHotelId());
			} else {
				mobileHotelService.updateMobileHotel(mh);
				//logger.info("... update mobile_hotel  success...41.... "+ mh.getHotelId());
			}
			
		} else {
			logger.error("... 第一次获取酒店详情update mobile_hotel  error...42...elementDetail is null. "+element.asXML());
			if(0 == tag) {
				try{
					Element element2 = MobileHotelUtils.getHotelInfo(hotelId.substring(hotelId.length() - 2,hotelId.length()),hotelId,map);
					addOrUpdateHotelDetail(element2,hotelId,map,1);
					return;
				}catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				logger.error("... 第二次获取酒店详情update mobile_hotel  error...43...elementDetail is null. ");
			}
		}
	}
	
	/**
	 * 更新酒店房间. 
	 * @param element 酒店节点 
	 * @param hotelId 酒店id 
	 */
	public void addOrUpdateHotelRooms(Element element,String hotelId)  throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hotelId", hotelId);
		List<MobileHotelRoom> mhrList = mobileHotelService.queryMobileHotelRoomList(params);
		// 酒店房间
		Element elementRooms = MobileHotelUtils.getHotelInfoByType(element,"Rooms");
		if(null != elementRooms && null != elementRooms.elements()) {
			int size = elementRooms.elements().size();
			
			//判断数据库中房间是否在elong静态数据中，如果没有 ，则删除 
			/*if(null != mhrList && mhrList.size() > 0 ) {
				for(int i = 0 ;i < mhrList.size();i++) {
					MobileHotelRoom hr = mhrList.get(i);
					boolean flag = true;
					for(int j = 0; j < size ;j++) {
						Element ele = (Element)elementRooms.elements().get(j);
						if(hr.getRoomId().equals(ele.attributeValue("Id"))) {
							flag = false;
							break;
						}
					}
					if(flag) {
						mobileHotelService.deleteMobileHotelRoomById(hr.getRoomId());
					}
				}
			}*/
			
			// 更新数据 
			for(int j = 0; j < size ;j++) {
				Element ele = (Element)elementRooms.elements().get(j);
				boolean isAdd = true; // 是否新增 ；否则更新
				boolean isVersionChange = true;
				String nodeVersion = MobileHotelUtils.md5ElementAttr(ele);
				MobileHotelRoom hr  = new MobileHotelRoom();
				// 判断room是否改变 
				if(null != mhrList && mhrList.size() > 0 ) {
					for(int i = 0 ;i < mhrList.size();i++) {
						hr = mhrList.get(i);
						if(hr.getRoomId().equals(ele.attributeValue("Id"))) {
							// 如果没有改变 则不处理 。 
							if(hr.getRoomNodeVersion().equals(nodeVersion)) {
								isVersionChange = false;
							}
							isAdd =  false;
							break;
						}
					}
				}
				if(!isVersionChange) {
					continue;
				}
				
				hr.setArea(ele.attributeValue("Area"));
				hr.setBedtype(ele.attributeValue("BedType"));
				hr.setBroadnetaccess(str2Short(ele.attributeValue("BroadnetAccess")));
				hr.setBroadnetfee(str2Short(ele.attributeValue("BroadnetFee")));
				hr.setComments(ele.attributeValue("Comments"));
				hr.setDescription(ele.attributeValue("Description"));
				hr.setFloor(ele.attributeValue("Floor"));
				hr.setHotelId(hotelId);
				hr.setName(ele.attributeValue("Name"));
				hr.setRoomId(ele.attributeValue("Id"));
				hr.setRoomNodeVersion(nodeVersion);
				
				if(isAdd) {
					hr.setMobileRoomId(null);
					mobileHotelService.insertMobileHotelRoom(hr);
					//logger.info("... insert mobile_hotel_room  success...51.... " + hr.getHotelId());
				} else {
					mobileHotelService.updateMobileHotelRoom(hr);
					//logger.info("... update mobile_hotel_room  success...52.... " + hr.getHotelId());
				}
				
			}
			
		}
	}
	
	
	/**
	 * 更新酒店房间. 
	 * @param element 酒店节点 
	 * @param hotelId 酒店id 
	 */
	public void addOrUpdateHotelRoomsImages(Element element,String hotelId)  throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hotelId", hotelId);
		List<MobileHotelRoomImage> mImgList = mobileHotelService.queryMobileHotelRoomImageList(params);
		// 酒店房间图片 Images 1
		Element Images = MobileHotelUtils.getHotelInfoByType(element,"Images");
		if(null != Images && null != Images.elements()) {
			int size = Images.elements().size();
			for(int j = 0; j < size ;j++) {
				String roomId = "";
				//Image 2
				Element elementImage = (Element)Images.elements().get(j);
				String imageType = elementImage.attributeValue("Type");
				// Locations 3
				Element elementLocations = MobileHotelUtils.getHotelRoomImagesInfoByType(elementImage,"Locations");
				// RoomId 3
				Element roomIdElement = MobileHotelUtils.getHotelRoomImagesInfoByType(elementImage,"RoomId");
				if(null != roomIdElement) {
					roomId = roomIdElement.getText();
				}
				// 遍历location 4
				if(null != elementLocations && null != elementLocations.elements()) {
					for(int i = 0;i < elementLocations.elements().size();i++ ) {
						Element locationElement = (Element)elementLocations.elements().get(i);
						// 过滤掉有水印 0:无水印 1：有水印
						String wm = locationElement.attributeValue("WaterMark");
						if(!StringUtils.isEmpty(wm) && "1".equals(wm)) {
							continue;
						}
						String imagesVersion = MobileHotelUtils.md5ElementText(locationElement);
						MobileHotelRoomImage mhr = new MobileHotelRoomImage();
						boolean isAdd = true; // 是否新增
						// 根据版本号查找 .
						if(null != mImgList && mImgList.size() > 0) {
							for(int x = 0; x < mImgList.size();x++) {
								mhr = mImgList.get(x);
								// 如果是修改 
								if(mhr.getImageVersion().equals(imagesVersion)) {
									isAdd = false;
									break;
								}
							}
						}
						mhr.setHotelId(hotelId);
						mhr.setImageVersion(imagesVersion);
						mhr.setImgSize(str2Long(locationElement.attributeValue("Size")));
						mhr.setImgType(str2Long(imageType));
						mhr.setImgUrl(locationElement.getText());
						mhr.setWatermark(str2Long(locationElement.attributeValue("WaterMark")));
						// 图片是否属于某一个房间。 
						if(!StringUtils.isEmpty(roomId)) {
							if(roomId.indexOf(",") != -1) {
								String[] roomIds = roomId.split(",");
								for(int a = 0 ; a < roomIds.length;a++) {
									mhr.setRoomId(roomIds[a]);
									addOrUupdateRoomImage(isAdd,mhr);
								}
							}else {
								mhr.setRoomId(roomId);
								addOrUupdateRoomImage(isAdd,mhr);
							}
						} else {
							addOrUupdateRoomImage(isAdd,mhr);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 保存或者更新图片 
	 * @param isAdd
	 * @param mhr
	 */
	public void addOrUupdateRoomImage(boolean isAdd,MobileHotelRoomImage mhr) {
		if(isAdd) {
			mhr.setMobileHotelRoomImageId(null);
			mobileHotelService.insertMobileHotelRoomImage(mhr);
			//logger.info("... insert mobile_hotel_room_image  success...60.... " + mhr.getHotelId());
		} else {
			mobileHotelService.updateMobileHotelRoomImage(mhr);
			//logger.info("... update mobile_hotel_room_image  success...61.... " + mhr.getHotelId());
		}
	}
	
	/**
	 * 更新有问题的酒店详情
	 * @param hotelId
	 */
	public void addOrUpdateHotelInfos4Bugs() {
		if(System.currentTimeMillis() < this.getDateTime("2014-01-22")) {
			// "30201068","20201341","60201077","60201096","70201532" 
			logger.error("MobileHotelJob addOrUpdateHotelInfos4Bugs  start.... ");
			String[] ids = {"10201747"};
			for(String hotelId:ids) {
				Map<String,Object> map = new HashMap<String,Object>();
				// 酒店节点 
				Element element = MobileHotelUtils.getHotelInfo(hotelId.substring(hotelId.length() - 2,hotelId.length()),hotelId,map);
				if(null != element) {
					// 酒店基本信息 
					try {
						addOrUpdateHotelDetail(element,hotelId,map,0);
						logger.info("......... addOrUpdateHotelInfos4Bugs  mobile_hotel detail success...425.... ");
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("addOrUpdateHotelInfos4Bugs addOrUpdateHotelDetail  error...426.... ");
					}
					// 房间 
					try {
						addOrUpdateHotelRooms(element,hotelId);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("MobileHotelJob addOrUpdateHotelRooms  error...429.... ");
					}
					
					// 房间图片 
					try {
						addOrUpdateHotelRoomsImages(element,hotelId);
						//logger.info("......... addOrUpdateHotelRoomsImages  success...32.... ");
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("MobileHotelJob addOrUpdateHotelRoomsImages  error...432.... ");
					}
				}
			}
		}
	}
	
	/**
	 * 字符串转换long类型
	 * @param str 
	 * @return 
	 */
	private Long str2Long(String str) {
		Long lo = null;
		if(StringUtils.isEmpty(str)) {
			return lo;
		}
		try {
			lo = Long.valueOf(str);	
		}catch(Exception e){
			e.printStackTrace();
			lo = null;
		}
		return lo;
	}
	
	
	/**
	 * 字符串转换long类型
	 * @param str 
	 * @return 
	 */
	private Short str2Short(String str) {
		Short sh = null;
		if(StringUtils.isEmpty(str)) {
			return sh;
		}
		try {
			sh = Short.valueOf(str);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return sh;
	}
	
	/**
	 * list 结构根据hotelId放在map中. 
	 * @param list
	 * @return map
	 */
	public Map<String,MobileHotelListVersion> list2Map(List<MobileHotelListVersion> list) {
		Map<String,MobileHotelListVersion> resutlMap = new HashMap<String,MobileHotelListVersion>();
		if(null != list && list.size() > 0 ) {
			for(int i = 0 ;i < list.size();i++) {
				MobileHotelListVersion mhl = list.get(i);
				resutlMap.put(mhl.getHotelId(), mhl);
			}
		}
		return resutlMap;
	}
	
	
	/**
	 * 更新最近2天的数据 
	 * @param updateTime
	 * @param days
	 * @return
	 */
	public boolean isNearest2Days(String updateTime,int days) {
		if(StringUtils.isEmpty(updateTime)) {
			return false;
		}
		Date currentDate = new Date();
		try{
			Date updateDate = DateUtil.toDate(updateTime, "yyyy-MM-dd HH:mm:ss");
			// 如果更新日期加2天还在当前日期前面 ，则返回ture. 
			if(DateUtil.dsDay_Date(updateDate,days).before(currentDate)) {
				return true;
			}
		}catch(Exception e) {
			
		}
		return false;
	}
	
	public Map<String,Object> getDefaultMap() {
		return new HashMap<String,Object>();
	}

	/**
	 * 单个更新字段 》 
	 */
	public void updateHotelStaticData2() {
		try {
			// elong列表数据 
			List<Element> listElement = MobileHotelUtils.getHotelList();
			if(null != listElement && listElement.size() > 0) {
				// 当前日期
				for(int i = 40000; i < listElement.size() ;i++) {
					logger.info("...  success.....55.....hotelId.... "+listElement.size()+"==="+i );
					Element element = listElement.get(i);
					// 只更新最近2天的 ,总记录数7万多 
					String hotelId = element.attributeValue("HotelId");
					MobileHotel mh = mobileHotelService.selectMobileHotelByHotelId(hotelId);
					if(null == mh || !StringUtils.isEmpty(mh.getRoomamenities())) {
						logger.info("...  success.....55.....1.... ");
						continue;
					}
					if(StringUtils.isEmpty(hotelId)) {
						logger.info("...  success.....55.....2.... ");
						continue;
					}
					Element hotelelement = null;
					try {
						logger.info("...  success.....55.....3.... ");
						 hotelelement = MobileHotelUtils.getHotelInfo(hotelId.substring(hotelId.length() - 2,hotelId.length()),hotelId,new HashMap<String,Object>());
						 logger.info("...  success.....55.....4.... ");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(null == hotelelement) {
						logger.info("...  success.....55.....5.... ");
						continue;
					}
					Element elementDetail = MobileHotelUtils.getHotelInfoByType(hotelelement,"Detail");
					if(null == elementDetail) {
						logger.info("...  success.....55.....6.... ");
						continue;
					}
					String a = XmlUtils.getChildText(elementDetail,"RoomAmenities");
					if(StringUtils.isEmpty(a)) {
						continue;
					}
					mh.setRoomamenities(XmlUtils.getChildText(elementDetail,"RoomAmenities")); // 房间设施
					mobileHotelService.updateMobileHotel(mh);
					logger.info("...MobileHotelJob updateHotelStaticData  success.....77.....hotelId.... " +hotelId);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("...MobileHotelJob updateHotelStaticData  error....78.....hotelId.... " );
		}
	}
	
	/**
	 * 多线程更新数据 
	 *//*
	public void updateHotelStaticData() {
		if (Constant.getInstance().isJobRunnable()) {
			List<Element> listElements = initList();
			if(null != listElements && listElements.size() > 0) {
				// 线程数据
				for(int i = 0 ;i <= listElements.size()/step;i++) {
					Thread thread = new Thread(new HotelThread(getNext(i*step,listElements))); 
					logger.info("...Thread.currentThread().getName().....start.... " + Thread.currentThread().getName());
					thread.start();  
				}
			}
		}
	}
	
	private class HotelThread  implements Runnable{
		@SuppressWarnings("unused")
		public HotelThread(){};
		public List<Element> listElements = new ArrayList<Element>();
		public HotelThread(List<Element> listElement) {
			this.listElements = listElement;
		}
		
        public void run() {
        	if(null != listElements) {
    			logger.info("...Thread.currentThread().getName().....run.....start.....11 .. " + Thread.currentThread().getName());
    			updateHotelStaticData3(listElements);
    			logger.info("...Thread.currentThread().getName().....run......success ...12... " + Thread.currentThread().getName());
    		}
        }
    }
	
	*//**
	 * 初始化数据 
	 * @return
	 *//*
	private List<Element> initList() {
		try {
		   return  MobileHotelUtils.getHotelList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Element>();
	}
	
	*//**
	 * 获取下一个8000条数据 
	 * @return
	 *//*
	private synchronized List<Element> getNext(int next,List<Element> listElements){
        if(next>=listElements.size()){
        	return null;
        }
        int from = next;
        next = next + step;
        if(next>=listElements.size()){
        	next = listElements.size();
        }
        return listElements.subList(from, next);
    }
	*/
	
	/**
	 * 更新酒店相关静态数据的job. 
	 */
	/*public void updateHotelStaticData3(List<Element> listElement) {
		logger.info("...MobileHotelJob updateHotelStaticData  start...10.... ");
		try {
			// elong列表数据 
			if(null != listElement && listElement.size() > 0) {
				Long count = mobileHotelService.countMobileHotelListVersionList(getDefaultMap());
				//数据库中是否有记录 ， 有的话需要比较 
				boolean needQuery = true;
				if(count < 1) {
					needQuery = false;
				}
				for(int i = 0; i < listElement.size() ;i++) {
					//logger.info("...thread.....start......" + Thread.currentThread().getName());
					Element element = listElement.get(i);
					String hotelId = element.attributeValue("HotelId");
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("hotelId", hotelId);
					boolean isAdd = true; // 是否新增 
					boolean needUpdate = true;
					MobileHotelListVersion mhl = new MobileHotelListVersion();
					if(needQuery) {
						mhl = mobileHotelService.selectMobileHotelListVersionByHotelId(hotelId);
						// 需要更新 ，如果记录已经存在 
						if(null != mhl) {
							isAdd = false;
							if(!mhl.getUpdatedTime().equals(element.attributeValue("UpdatedTime"))) {
								needUpdate = true; // 如果时间不同，则需要更新 
							} else {
								needUpdate = false; // 如果过时间相同 则不需要更新 
							}
                		} 
					}
					
					if(isAdd) {
						// 如果是数据库原来没有的数据 ,新增
            			addOrUpdateMobileHotelListVersion(element,new MobileHotelListVersion(),false);
					} else {
						if(needUpdate) {
							addOrUpdateMobileHotelListVersion(element,mhl,true);
						}
					}
				}
			}
			logger.info("...MobileHotelJob updateHotelStaticData  success...18.... ");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("...MobileHotelJob updateHotelStaticData  error...19.... ");
		}
	}
	*/

	
	
	
	public static String getWeekDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return "1";
		case Calendar.TUESDAY:
			return "2";
		case Calendar.WEDNESDAY:
			return "3";
		case Calendar.THURSDAY:
			return "4";
		case Calendar.FRIDAY:
			return "5";
		case Calendar.SATURDAY:
			return "6";
		case Calendar.SUNDAY:
			return "7";
		default:
			return "";
		}
	}

	/**
	 * 返回当前时间 毫秒 
	 * @param date
	 * @return
	 */
	public long getDateTime(String date) {
		if(StringUtils.isEmpty(date)) {
			return 0l;
		}
		Date d = parseDate(date,"yyyy-MM-dd");
		return d.getTime();
	}
	
	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			date = (java.util.Date) df.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}
	
	
	/**
	 * 两个字符串时间年月日是否相同 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public boolean dateIsEquals(String date1,String date2) {
		if(StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
			return false;
		}
		try {
			if(date1.substring(0, 10).equals(date2.substring(0, 10))) {
				return true;
			} else {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("..dateIsEquals==.....date1=="+date1 + "==date2==" + date2);
		}
		
		return false;
	}
	
	public void setMobileHotelService(MobileHotelService mobileHotelService) {
		this.mobileHotelService = mobileHotelService;
	}
}
