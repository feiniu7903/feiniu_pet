package com.lvmama.pet.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileHotel;
import com.lvmama.comm.pet.po.mobile.MobileHotelDest;
import com.lvmama.comm.pet.po.mobile.MobileHotelLandmark;
import com.lvmama.comm.pet.po.mobile.MobileHotelListVersion;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderLog;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderVisitor;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderRelateLog;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoom;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoomImage;
import com.lvmama.comm.pet.po.mobile.UserCreditCard;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.pet.mobile.dao.MobileHotelDAO;
import com.lvmama.pet.mobile.dao.MobileHotelDestDAO;
import com.lvmama.pet.mobile.dao.MobileHotelLandmarkDAO;
import com.lvmama.pet.mobile.dao.MobileHotelListVersionDAO;
import com.lvmama.pet.mobile.dao.MobileHotelOrderDAO;
import com.lvmama.pet.mobile.dao.MobileHotelOrderLogDAO;
import com.lvmama.pet.mobile.dao.MobileHotelOrderVisitorDAO;
import com.lvmama.pet.mobile.dao.MobileHotelOrderRelateLogDAO;
import com.lvmama.pet.mobile.dao.MobileHotelRoomDAO;
import com.lvmama.pet.mobile.dao.MobileHotelRoomImageDAO;
import com.lvmama.pet.mobile.dao.UserCreditCardDAO;

/**
 * 对接艺龙酒店静态数据接口.
 * @author qinzubo
 *
 */
public class MobileHotelServiceImpl implements MobileHotelService {
	private final static String MOBILE_HOTEL_SERVICE_ELONG = "mobile_hotel_service_elong";
	private final static int MOBILE_HOTEL_SERVICE_ELONG_SECOND = 60 * 60 * 2;
	
	@Autowired
	MobileHotelDAO mobileHotelDAO;
	@Autowired
	MobileHotelDestDAO mobileHotelDestDAO;
	@Autowired
	MobileHotelLandmarkDAO mobileHotelLandmarkDAO;
	@Autowired
	MobileHotelListVersionDAO mobileHotelListVersionDAO;
	@Autowired
	MobileHotelOrderDAO mobileHotelOrderDAO;
	@Autowired
	MobileHotelOrderLogDAO mobileHotelOrderLogDAO;
	@Autowired
	MobileHotelRoomDAO mobileHotelRoomDAO;
	@Autowired
	MobileHotelRoomImageDAO mobileHotelRoomImageDAO;
	@Autowired
	UserCreditCardDAO userCreditCardDAO;
	@Autowired
	MobileHotelOrderVisitorDAO mobileHotelOrderVisitorDAO;
	@Autowired
	MobileHotelOrderRelateLogDAO mobileHotelOrderRelateLogDAO;
	
	@Override
	public List<MobileHotelOrderVisitor> selectByParams(
			MobileHotelOrderVisitor mobileHotelOrderVisitor) {
		return mobileHotelOrderVisitorDAO.selectByParams(mobileHotelOrderVisitor);
	}

	@Override
	public Long getLastOrderIdByUserId(String userId) {
		return mobileHotelOrderDAO.selectByUserId(userId);
	}

	@Override
	public void saveMobileHotelOrderVisitor(
			MobileHotelOrderVisitor mobileHotelOrderVisitor) {
		mobileHotelOrderVisitorDAO.insert(mobileHotelOrderVisitor);
	}

	@Override
	public long saveMobileHotelOrder(MobileHotelOrder mobileHotelOrder) {
		return mobileHotelOrderDAO.insertMobileHotelOrder(mobileHotelOrder);
	}

	@Override
	public int updateMobileHotelOrder(
			MobileHotelOrder mobileHotelOrder) {
		return mobileHotelOrderDAO.updateByPrimaryKey(mobileHotelOrder);
	}
	
	@Override
	public int updateMobileHotelOrder4Job(
			MobileHotelOrder mobileHotelOrder) {
		return mobileHotelOrderDAO.updateByPrimaryKey4Job(mobileHotelOrder);
	}
	
	@Override
	public int deleteMobileHotelOrderByPrimaryKey(
			long lvHotelOrderId) {
		return mobileHotelOrderDAO.deleteByPrimaryKey(lvHotelOrderId);
	}

	@Override
	public List<MobileHotelOrder> queryMobileHotelOrderList(
			Map<String, Object> param) {
		return mobileHotelOrderDAO.getMobileHotelOrderListByPrarms(param);
	}

	@Override
	public Long countMobileHotelOrderList(Map<String, Object> param) {
		return mobileHotelOrderDAO.countMobileHotelOrder(param);
	}
	
	/****************/
	@Override
	public MobileHotelDest insertMobileHotelDest(MobileHotelDest mHotel) {
		return mobileHotelDestDAO.insert(mHotel);
	}

	@Override
	public int updateMobileHotelDest(MobileHotelDest mHotel) {
		return mobileHotelDestDAO.updateByPrimaryKey(mHotel);
	}

	@Override
	public MobileHotelDest selectMobileHotelDestByCityCode(String cityCode) {
		Object obj = getMemecachedInfo("selectMobileHotelDestByCityCode"+cityCode);
		if(null != obj) {
			return (MobileHotelDest)obj;
		} else {
			MobileHotelDest md = mobileHotelDestDAO.selectByPrimaryKey(cityCode);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + "selectMobileHotelDestByCityCode"+cityCode, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public List<MobileHotelDest> queryMobileHotelDestList(
			Map<String, Object> params) {
		return mobileHotelDestDAO.getMobileHotelDestListByPrarms(params);
	}

	@Override
	public Long countMobileHotelDestList(Map<String, Object> param) {
		return mobileHotelDestDAO.countMobileHotelDest(param);
	}

	@Override
	public int deleteMobileHotelDestByCityCode(String cityCode) {
		return mobileHotelDestDAO.deleteByPrimaryKey(cityCode);
	}

	/*************/
	@Override
	public MobileHotelLandmark insertMobileHotelLandmark(
			MobileHotelLandmark mHotelLandmark) {
		return mobileHotelLandmarkDAO.insert(mHotelLandmark);
	}

	@Override
	public int updateMobileHotelLandmark(MobileHotelLandmark mHotelLandmark) {
		return mobileHotelLandmarkDAO.updateByPrimaryKey(mHotelLandmark);
	}

	@Override
	public MobileHotelLandmark selectMobileHotelLandmarkById(Long id) {
		Object obj = getMemecachedInfo("selectMobileHotelLandmarkById"+id);
		if(null != obj) {
			return (MobileHotelLandmark)obj;
		} else {
			MobileHotelLandmark md = mobileHotelLandmarkDAO.selectByPrimaryKey(id);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + "selectMobileHotelLandmarkById"+id, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public List<MobileHotelLandmark> queryMobileHotelLandmarkList(
			Map<String, Object> param) {
		return mobileHotelLandmarkDAO.getMobileHotelLandmarkListByPrarms(param);
	}

	@Override
	public Long countMobileHotelLandmarkList(Map<String, Object> param) {
		return mobileHotelLandmarkDAO.countMobileHotelLandmark(param);
	}

	@Override
	public int deleteMobileHotelLandmarkById(Long id) {
		return mobileHotelLandmarkDAO.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteMobileHotelLandmarkByCityCode(String cityCode) {
		return mobileHotelLandmarkDAO.deleteByPrimaryCityCode(cityCode);
	}


	/**********/
	@Override
	public MobileHotelListVersion insertMobileHotelListVersion(
			MobileHotelListVersion mHotelVersion) {
		// TODO Auto-generated method stub
		return mobileHotelListVersionDAO.insert(mHotelVersion);
	}

	@Override
	public int updateMobileHotelListVersion(
			MobileHotelListVersion mHotelVersion) {
		// TODO Auto-generated method stub
		return mobileHotelListVersionDAO.updateByPrimaryKey(mHotelVersion);
	}

	@Override
	public MobileHotelListVersion selectMobileHotelListVersionByHotelId(
			String hotelId) {
		return mobileHotelListVersionDAO.selectByPrimaryKey(hotelId);
	}

	@Override
	public List<MobileHotelListVersion> queryMobileHotelListVersionList(
			Map<String, Object> param) {
		return mobileHotelListVersionDAO.getMobileHotelListVersionListByPrarms(param);
	}

	@Override
	public Long countMobileHotelListVersionList(Map<String, Object> param) {
		return mobileHotelListVersionDAO.countMobileHotelListVersion(param);
	}

	@Override
	public int deleteMobileHotelListVersionByCityCode(String cityCode) {
		return mobileHotelListVersionDAO.deleteByPrimaryKey(cityCode);
	}

	/**********/
	/************/
	@Override
	public MobileHotelRoom insertMobileHotelRoom(MobileHotelRoom mHotelRoom) {
		return mobileHotelRoomDAO.insert(mHotelRoom);
	}

	@Override
	public int updateMobileHotelRoom(MobileHotelRoom mHotelRoom) {
		return mobileHotelRoomDAO.updateByPrimaryKey(mHotelRoom);
	}

	@Override
	public MobileHotelRoom selectMobileHotelRoomById(Long id) {
		return mobileHotelRoomDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<MobileHotelRoom> queryMobileHotelRoomList(
			Map<String, Object> param) {
		String key ="queryMobileHotelRoomList" + getMemcacheKeyByParams(param);
		Object obj = getMemecachedInfo(key);
		if(null != obj) {
			return (List<MobileHotelRoom>)obj;
		} else {
			List<MobileHotelRoom> md = mobileHotelRoomDAO.getMobileHotelRoomListByPrarms(param);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + key, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
		
	}

	@Override
	public Long countMobileHotelRoomList(Map<String, Object> param) {
		String key ="countMobileHotelRoomList" + getMemcacheKeyByParams(param);
		Object obj = getMemecachedInfo(key);
		if(null != obj) {
			return (Long)obj;
		} else {
			Long md = mobileHotelRoomDAO.countMobileHotelRoom(param);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + key, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public int deleteMobileHotelRoomById(Long id) {
		return mobileHotelRoomDAO.deleteByPrimaryKey(id);
	}

	@Override
	public MobileHotelRoomImage insertMobileHotelRoomImage(
			MobileHotelRoomImage mHotelRoomImg) {
		return mobileHotelRoomImageDAO.insert(mHotelRoomImg);
	}

	@Override
	public int updateMobileHotelRoomImage(MobileHotelRoomImage mHotelRoomImg) {
		return mobileHotelRoomImageDAO.updateByPrimaryKey(mHotelRoomImg);
	}

	@Override
	public MobileHotelRoomImage selectMobileHotelRoomImageById(Long id) {
		Object obj = getMemecachedInfo("selectMobileHotelRoomImageById"+id);
		if(null != obj) {
			return (MobileHotelRoomImage)obj;
		} else {
			MobileHotelRoomImage md = mobileHotelRoomImageDAO.selectByPrimaryKey(id);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + "selectMobileHotelRoomImageById"+id, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public List<MobileHotelRoomImage> queryMobileHotelRoomImageList(
			Map<String, Object> param) {
		String key ="queryMobileHotelRoomImageList" + getMemcacheKeyByParams(param);
		Object obj = getMemecachedInfo(key);
		if(null != obj) {
			return (List<MobileHotelRoomImage>)obj;
		} else {
			List<MobileHotelRoomImage> md = mobileHotelRoomImageDAO.getMobileHotelRoomImageListByPrarms(param);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + key, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public Long countMobileHotelRoomImageList(Map<String, Object> param) {
		String key ="countMobileHotelRoomImageList" + getMemcacheKeyByParams(param);
		Object obj = getMemecachedInfo(key);
		if(null != obj) {
			return (Long)obj;
		} else {
			Long md = mobileHotelRoomImageDAO.countMobileHotelRoomImage(param);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + key, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public int deleteMobileHotelRoomImageById(Long id) {
		return mobileHotelRoomImageDAO.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteMobileHotelRoomImageByHotelIdAndRoomId(String roomId,
			String hotelId) {
		return mobileHotelRoomImageDAO.deleteMobileHotelRoomImageByHotelIdAndRoomId( roomId, hotelId);
	}
	
    /***** hotel ****/
	@Override
	public MobileHotel insertMobileHotel(MobileHotel mHotel) {
		return mobileHotelDAO.insert(mHotel);
	}

	@Override
	public int updateMobileHotel(MobileHotel mHotel) {
		return mobileHotelDAO.updateByPrimaryKey(mHotel);
	}

	@Override
	public MobileHotel selectMobileHotelByHotelId(String hotelId) {
		Object obj = getMemecachedInfo("selectMobileHotelByHotelId" + hotelId);
		if(null != obj) {
			return (MobileHotel)obj;
		} else {
			MobileHotel md = mobileHotelDAO.selectByPrimaryKey(hotelId);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + "selectMobileHotelByHotelId"+hotelId, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}

	@Override
	public MobileHotel selectMobileHotelByOrderId(String hotelId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("orderId", hotelId);
		List<MobileHotel> mdList = mobileHotelDAO.queryMobileHotelListByOrderId(param);
		if(null != mdList  && mdList.size() > 0) {
			return mdList.get(0);
		}
		return null;
	}
	
	
	@Override
	public List<MobileHotel> queryMobileHotelList(Map<String, Object> param) {
		return mobileHotelDAO.getMobileHotelListByPrarms(param);
	}

	@Override
	public Long countMobileHotelList(Map<String, Object> param) {
		return mobileHotelDAO.countMobileHotel(param);
	}

	@Override
	public int deleteMobileHotelByHotelId(String hotelId) {
		return mobileHotelDAO.deleteByPrimaryKey(hotelId);
	}

	@Override
	public List<MobileHotelRoomImage> queryMobileHotelRoomImageListByHotelIdAndRoomId(
			String roomId, String hotelId) {
		Object obj = getMemecachedInfo("queryMobileHotelRoomImageListByHotelIdAndRoomId"+roomId+hotelId);
		if(null != obj) {
			return (List<MobileHotelRoomImage>)obj;
		} else {
			List<MobileHotelRoomImage> md = mobileHotelRoomImageDAO.queryMobileHotelRoomImageListByHotelIdAndRoomId(roomId,hotelId);
			if (md != null ) {
				MemcachedUtil.getInstance().set( MOBILE_HOTEL_SERVICE_ELONG + "queryMobileHotelRoomImageListByHotelIdAndRoomId"+roomId+hotelId, MOBILE_HOTEL_SERVICE_ELONG_SECOND, md);
			}
			return md;
		}
	}
	
	
	
	
	/**************************  order 日志开始  ***********************/
	@Override
	public MobileHotelOrderLog insertMobileHotelOrderLog(
			MobileHotelOrderLog mHotel) {
		return mobileHotelOrderLogDAO.insert(mHotel);
	}

	@Override
	public int updateMobileHotelOrderLog(MobileHotelOrderLog mHotel) {
		// TODO Auto-generated method stub
		return mobileHotelOrderLogDAO.updateByPrimaryKey(mHotel);
	}

	@Override
	public MobileHotelOrderLog selectMobileHotelOrderLogById(Long id) {
		return mobileHotelOrderLogDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<MobileHotelOrderLog> queryMobileHotelOrderLogList(Map<String, Object> param) {
		return mobileHotelOrderLogDAO.getMobileHotelOrderLogListByPrarms(param);
	}

	@Override
	public Long countMobileHotelOrderLogList(Map<String, Object> param) {
		return mobileHotelOrderLogDAO.countMobileHotelOrderLog(param);
	}

	@Override
	public int deleteMobileHotelOrderLogById(Long id) {
		// TODO Auto-generated method stub
		return mobileHotelOrderLogDAO.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<UserCreditCard> selectUserCreditCardByUserId(String userId) {
		return userCreditCardDAO.selectUserCreditCardByUserId(userId);
	}

	@Override
	public UserCreditCard selectUserCreditCard(UserCreditCard userCreditCard) {
		return userCreditCardDAO.selectUserCreditCard(userCreditCard);
	}

	@Override
	public void saveUserCreditCard(UserCreditCard userCreditCard) {
		userCreditCardDAO.insert(userCreditCard);
	}

	/**
	 * 先从缓存中回去信息.
	 * 
	 * @param params
	 * @return obj
	 */
	public Object getMemecachedInfo(String memcacheKey) {
		Object obj = null;
		// 先从缓存中区
		if (!StringUtils.isEmpty(memcacheKey)) {
			obj = MemcachedUtil.getInstance().get(MOBILE_HOTEL_SERVICE_ELONG + memcacheKey);
		}
		return obj;
	}

	/**
	 * 根据参数获取相应的key .
	 * 
	 * @param params
	 * @return key
	 */
	public String getMemcacheKeyByParams(Map<String, Object> params) {
		String memcacheKey = "";
		// 先从缓存中区
		try {
			if(null != params) {
				memcacheKey = MD5.encode(params.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memcacheKey;
	}

	@Override
	public MobileHotelOrderRelateLog insertMobileHotelOrderRelateLog(
			MobileHotelOrderRelateLog mHotel) {
		return mobileHotelOrderRelateLogDAO.insert(mHotel);
	}

	@Override
	public int updateMobileHotelOrderRelateLog(MobileHotelOrderRelateLog mHotel) {
		// TODO Auto-generated method stub
		return mobileHotelOrderRelateLogDAO.updateByPrimaryKey(mHotel);
	}

	@Override
	public MobileHotelOrderRelateLog selectMobileHotelOrderRelateLogById(Long id) {
		// TODO Auto-generated method stub
		return mobileHotelOrderRelateLogDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<MobileHotelOrderRelateLog> queryMobileHotelOrderRelateLogList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileHotelOrderRelateLogDAO.getMobileHotelOrderRelateLogListByPrarms(param);
	}

	@Override
	public Long countMobileHotelOrderRelateLogList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileHotelOrderRelateLogDAO.countMobileHotelOrderRelateLog(param);
	}

	@Override
	public int deleteMobileHotelOrderRelateLogById(Long id) {
		// TODO Auto-generated method stub
		return mobileHotelOrderRelateLogDAO.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteMobileHotelVerionNotInHotelMobile() {
		 return mobileHotelDAO.deleteMobileHotelVerionNotInHotelMobile();
	}

	
}
