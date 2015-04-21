package com.lvmama.comm.pet.service.mobile;

import java.util.List;
import java.util.Map;

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

/**
 * 对接艺龙酒店静态数据接口.
 * @author qinzubo
 *
 */
public interface MobileHotelService {
	
	/**
	 * 根据用户ID，最近的订单号，获取入住人
	 * @param mobileHotelOrderVisitor
	 * @return
	 */
	List<MobileHotelOrderVisitor> selectByParams(MobileHotelOrderVisitor mobileHotelOrderVisitor);
	
	/**
	 * 获取用户最近的订单的订单号号
	 * @param userId
	 * @return
	 */
	Long getLastOrderIdByUserId(String userId);
	
	/**
	 * 保存酒店订单入住人
	 * @param mobileHotelOrderVisitor
	 */
	void saveMobileHotelOrderVisitor(MobileHotelOrderVisitor mobileHotelOrderVisitor);
	
	/**
	 * 保存酒店订单
	 * @param mobileHotelOrder
	 * @return
	 */
	long saveMobileHotelOrder(MobileHotelOrder mobileHotelOrder);
	
	/**
	 * 修改酒店订单
	 * @param mobileHotelOrder
	 * @return
	 */
	int updateMobileHotelOrder(MobileHotelOrder mobileHotelOrder);
	int updateMobileHotelOrder4Job(MobileHotelOrder mobileHotelOrder);
	int deleteMobileHotelOrderByPrimaryKey(long lvHotelOrderId);

	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelOrder> queryMobileHotelOrderList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelOrderList(Map<String,Object> param);
	/************************* 酒店 开始******************************/
	/**
	 * 新增酒店
	 * @param mHotel 
	 * @return  
	 */
	MobileHotel insertMobileHotel(MobileHotel mHotel);
	
	/**
	 * 更新酒店目的地.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int  updateMobileHotel(MobileHotel mHotel);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotel selectMobileHotelByHotelId(String hotelId);
	
	/**
	 * 根据酒店订单查找酒店.
	 * @return  对象
	 */
	MobileHotel selectMobileHotelByOrderId(String hotelId);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotel> queryMobileHotelList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelByHotelId(String hotelId);
	/************************* 酒店结束******************************/
	
	/************************* 酒店目的地 开始******************************/
	/**
	 * 新增酒店目的地 
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelDest insertMobileHotelDest(MobileHotelDest mHotel);
	
	/**
	 * 更新酒店目的地.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int  updateMobileHotelDest(MobileHotelDest mHotel);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelDest selectMobileHotelDestByCityCode(String cityCode);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelDest> queryMobileHotelDestList(Map<String,Object> param);
	

	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelDestList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelDestByCityCode(String cityCode);
	/************************* 酒店目的地 结束******************************/
	
	/************************* 酒店品牌 开始******************************/
	/**
	 * 新增酒店品牌 
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelLandmark insertMobileHotelLandmark(MobileHotelLandmark mHotelLandmark);
	
	/**
	 * 更新酒店品牌.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int   updateMobileHotelLandmark(MobileHotelLandmark mHotelLandmark);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelLandmark selectMobileHotelLandmarkById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelLandmark> queryMobileHotelLandmarkList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelLandmarkList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelLandmarkById(Long id);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelLandmarkByCityCode(String cityCode);
	
	/************************* 酒店品牌 结束******************************/
	
	/************************* 酒店列表版本号 开始******************************/
	/**
	 * 新增酒店目的地 
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelListVersion insertMobileHotelListVersion(MobileHotelListVersion mHotelVersion);
	
	/**
	 * 更新酒店目的地.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int   updateMobileHotelListVersion(MobileHotelListVersion mHotelVersion);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelListVersion selectMobileHotelListVersionByHotelId(String hotelId);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelListVersion> queryMobileHotelListVersionList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelListVersionList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelListVersionByCityCode(String cityCode);
	/************************* 酒店版本号 结束******************************/
	
	
	
	
	/************************* 酒店房间 开始******************************/
	/**
	 * 新增酒店房间 
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelRoom insertMobileHotelRoom(MobileHotelRoom mHotelRoom);
	
	/**
	 * 更新酒店房间.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int   updateMobileHotelRoom(MobileHotelRoom mHotelRoom);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelRoom selectMobileHotelRoomById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelRoom> queryMobileHotelRoomList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelRoomList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param id
	 * @return 删除的条数
	 */
	int deleteMobileHotelRoomById(Long id);
	/************************* 酒店房间 结束******************************/
	

	/************************* 酒店房间图片 开始******************************/
	/**
	 * 新增酒店房间图片
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelRoomImage insertMobileHotelRoomImage(MobileHotelRoomImage mHotelRoomImg);
	
	/**
	 * 更新酒店房间图片.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int   updateMobileHotelRoomImage(MobileHotelRoomImage mHotelRoomImg);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelRoomImage selectMobileHotelRoomImageById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelRoomImage> queryMobileHotelRoomImageList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelRoomImageList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelRoomImageById(Long id);
	
	/**
	 * 更加roomid和hotelid删除图片 
	 * @param roomId
	 * @param hotelId
	 * @return
	 */
	int deleteMobileHotelRoomImageByHotelIdAndRoomId(String roomId,String hotelId);
	
	/**
	 * 根据酒店id和房间id 查询图片列表 
	 * @param roomId    房间id 
	 * @param hotelId   酒店id
	 * @return
	 */
	List<MobileHotelRoomImage> queryMobileHotelRoomImageListByHotelIdAndRoomId(String roomId,String hotelId);
	
	
	
	/************************* 酒店房间图片结束******************************/
	
	
	
	/****************************  酒店订单日志 开始******************************/
	/**
	 * 新增酒店日志 
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelOrderLog insertMobileHotelOrderLog(MobileHotelOrderLog mHotel);
	
	/**
	 * 更新酒店目的地.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int  updateMobileHotelOrderLog(MobileHotelOrderLog mHotel);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelOrderLog selectMobileHotelOrderLogById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelOrderLog> queryMobileHotelOrderLogList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelOrderLogList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelOrderLogById(Long id);
	
	/**
	 * 根据用户Id查询信用卡
	 * @param userId
	 * @return
	 */
	List<UserCreditCard> selectUserCreditCardByUserId(String userId);
	
	/**
	 * 查询信用卡
	 * @param userCreditCard
	 * @return
	 */
	UserCreditCard selectUserCreditCard(UserCreditCard userCreditCard);
	
	/**
	 * 保存信用卡
	 * @param userCreditCard
	 */
	void saveUserCreditCard(UserCreditCard userCreditCard);
	
	
	/****************************  酒店订单RELATED日志 开始******************************/
	/**
	 * 新增酒店日志 
	 * @param mHotel 
	 * @return  
	 */
	MobileHotelOrderRelateLog insertMobileHotelOrderRelateLog(MobileHotelOrderRelateLog mHotel);
	
	/**
	 * 更新酒店目的地.
	 * @param mHotel  要更新的对象 
	 * @return   更新后的对象
	 */
	public int  updateMobileHotelOrderRelateLog(MobileHotelOrderRelateLog mHotel);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键城市编码
	 * @return  对象
	 */
	MobileHotelOrderRelateLog selectMobileHotelOrderRelateLogById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileHotelOrderRelateLog> queryMobileHotelOrderRelateLogList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileHotelOrderRelateLogList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param cityCode
	 * @return 删除的条数
	 */
	int deleteMobileHotelOrderRelateLogById(Long id);
	
	/**
	 * 删除mobile_hotel_list_version 表中status=0 的记录  在mobile_hotel中不存在 。 
	 */
	int deleteMobileHotelVerionNotInHotelMobile();
	
}
