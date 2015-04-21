package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHotel;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.vo.place.ScenicVo;

public interface PlacePageService {
	
	/**
	 * 获取特推产品：先取后台推荐的产品，若无推荐则调取各分类产品的第一款
	 * 
	 * @return
	 */
	List<ProductSearchInfo> specialRecommendation(Long currentPlaceId,Long firtMatchFromDestId,String pageChannel,int currentPage,int prdSize);
	
	/**
	 * 目的地酒店以及景点查询 并且包含其下的产品
	 * 
	 * @param id
	 *            目的地
	 * @param placeSize
	 *            设置返回目的地的数量
	 * @param stage
	 *            2.景区类型 3.酒店
	 * @param isTicket
	 *            2 酒店 1 门票 3 自由行 4 国内游
	 * @param pageSize
	 *            景点或酒店获取数
	 * @param prdSize
	 *            每个景点/酒店下产品获取数量
	 * @param currentPage
	 *            当前页面码
	 * @return
	 */
	public Map<String, Object> getPlaceAndPrd(Long id, String placeStage, String isTicket, int placeSize, int pageSize, int prdSize, int currentPage) ;
	/**
	 * 国内自由行(含交通) 和 省份级的各地到
	 */
	public Map<String,Object> createDest2DestProduct(String[] subProductType,Place place,Long fromDestId);
	/**
	 * 获取当前目的地显示数据
	 * @param place
	 */
	public Map<String, Object> getAboutPlaceContent(Place place);
	/**
	 * 国内取：
	 * 'GROUP','GROUP_LONG','SELFHELP_BUS';省级取：'GROUP','SELFHELP_BUS';大洲级：'GROUP_FOREIGN
	 * ' ;
	 * @param place
	 * @return
	 */
	public String[] getSubProductTypeForSurrounding(Place place);
	/**
	 * 查询路线取自由行（含交通）：长途自由行;出境取（出境跟团游、短途跟团游和自助巴士班）;省份取:GROUP_LONG,
	 * FREENESS_LONG;跟团游：短途跟团游，自助巴士班'GROUP','SELFHELP_BUS'
	 * 
	 * @param place
	 * @return
	 */
	public String[] getSubProductTypeForDest2Dest(Place place);
	/**
	 * 出境跟团游： 大洲级跟团游：
	 * 
	 * @param place
	 * @return
	 */
	public String[] getSubProductTypeForDest2destGroup(Place place);
	/**
	 * 获取place图片
	 * @param place
	 * @return
	 */
	public String getPlacePhotoImg(Place place);
	
	
	/**
	 * 获取place图片大图
	 * @param place
	 * @return
	 */
	String getPlacePhotoLargeImg(Place place);
	

	
	
	/**
	 * 设置目的地页面当季推荐标签页的缓存数据
	 * @param firtMatchFromDestId 出发地标识
	 * @param place 目的地
	 * @return
	 */
	Map<String, Object> getDestInfoForRecommendProducts(Long fromPlaceId, Place place,Long fromDestId);	
	
	
	/**
	 * 设置目的地页面门票标签页的缓存数据
	 * @param placeId 目的地
	 * @return
	 * <p>获取该目的地下根据seq值排序并含有门票的10个景点，每个景点再获取1个门票产品的信息</p>
	 */
	Map<String, Object> getDestInfoForTicketProducts(Long placeId,int prdSize);
	
	/**
	 * 设置目的地页面自由行标签页的缓存数据
	 * @param placeId 目的地
	 * @return
	 * <p>获取该目的地下的seq值靠前的2条超级自由行和10条普通自由行产品</p>
	 */
	Map<String, Object> getDestInfoForFreenessProducts(Long placeId,boolean isRecommend);
	
	/**
	 * 设置目的地页面酒店标签页的缓存数据
	 * @param placeId 目的地
	 * @return
	 * <p>获取该目的地下的seq值靠前的并含有产品的10个酒店，并获取每个酒店的房型</p>
	 */
	Map<String, Object> getDestInfoForHotelProducts(Long placeId,int prdSize);
	
	/**
	 * 设置目的地页面跟团游标签页的缓存数据
	 * @param fromPlaceId 出发地
	 * @param placeId 目的地
	 * @param subProductTypes 产品子类型列表
	 * @return
	 */
	Map<String, Object> getDestInfoForSurroundProducts(Long fromPlaceId, Long placeId, String[] subProductType,int prdSize);
	
	/**
	 * 设置酒店目的地页面的缓存数据
	 * @param hotelPlace
	 * @return
	 */
	Map<String,Object>  getHotelPageInfo(Place hotelPlace) ;
	/**
	 * 通过placePhoto参数获取图片list
	 * @param placePhoto
	 * @return
	 * @author:nixianjun 2013-7-10
	 */
	List<PlacePhoto> getPlacePhotoListByPlacePhoto(PlacePhoto placePhoto);

	ScenicVo getScenicPageMainInfo(Place place);
	
	/**
	 * 度假酒店详情页
	 * @param place
	 * @return
	 */
	Map<String, Object> getHolidayHotelPageInfo(Place place);

	/**
	 * 根据景点筛选出周边的有产品的景点
	 * @param placeId 景点标识
	 * @param stage 景点类型
	 * @param limit 数据集
	 * @return s
	 */
	List<PlaceSearchInfo> getVicinityByPlace(Long placeId, String subject, Long stage, Long limit);
	/**
	 * 获取酒店信息
	 * @param placeId
	 * @return
	 */
	PlaceHotel searchPlaceHotel(Long placeId);

	List<ProductSearchInfo> getFreeNessAndHotelSuit(Place scenicPlace);

	List<ProductSearchInfo> getGroupAndBus(Place scenicPlace);
	
}
