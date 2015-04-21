package com.lvmama.comm.bee.service.distribution;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionBaiduTuangou;
import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceImage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;
import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.DistributorIp;
import com.lvmama.comm.pet.po.place.Place;

public interface DistributionService {
	List<DistributorInfo> selectWhiteListByProductIdAndProductBranchId(Long productId, Long branchId);

	/**
	 * 取得全部的分销商信息
	 * 
	 * @return
	 */
	List<DistributorInfo> getAllDistributors();

	/**
	 * 新增分销商信息
	 * 
	 * @param distributor
	 */
	void insertDistributorInfo(DistributorInfo distributor);

	/**
	 * 更新分销商信息
	 * 
	 * @param distributor
	 */
	void updateDistributorInfo(DistributorInfo distributor);

	/**
	 * 通过分销商ID取得分销商所分配的IP
	 * 
	 * @return
	 */
	List<DistributorIp> getDistributorIpByDistributorInfoId(Long distributorId);

	/**
	 * 通过IP信息ID更新IP
	 * 
	 * @param ip
	 * @param distributorIpId
	 */
	void updateDistributorIpByDistributorIpId(DistributorIp distributorIp);

	/**
	 * 通过IP信息ID删除IP
	 * 
	 * @param distributorIpId
	 */
	void deleteDistributorIpByDistributorIpId(Long distributorIpId);
	
	/**
	 * 删除分销返点
	 */
	public void deleteDistributorProductCategory(Long distributionProductCategoryId);

	/**
	 * 新增IP
	 */
	void insertDistributorIp(DistributorIp distributorIp);
	
	/**
	 * 按条件查询是否存在分销返佣点 
	 */
	public Long selectPistributionProductCategoryConditionByCount(Map<String,Object> params);

	DistributorInfo selectByDistributorCode(String distributorCode);

	DistributorInfo selectByDistributorId(Long distributorId);

	/**
	 * 根据渠道查询分销商
	 * 
	 * @param distributorChannel
	 * @return
	 */
	DistributorInfo selectByDistributorChannel(String distributorChannel);

	List<DistributionPlaceImage> selectPlaceImageByName(String placeName);

	List<String> selectImageByPlaceId(long placeId);

	List<String> selectByDistributorInfoId(Long distributorInfoId);

	List<DistributionPlaceProduct> selectAllRouteProduct(Map<String, Object> param);

	Integer selectAllRouteProductCount();

	List<DistributionPlaceImage> selectPlaceCityByName(Map<String, Object> param);

	List<DistributionPlaceImage> selectPlaceImageByName(Map<String, Object> param);

	List<DistributionPlaceProduct> selectProductByPlaceId(Long placeId);

	DistributionPlaceImage selectSightByName(String placeName);

	Long getDistributionProductPlaceCount(Map<String, Object> params);

	List<Place> getDistributionProductPlace(Map<String, Object> params);

	/**
	 * 查询所有分销类型
	 * 
	 * @return
	 */
	public List<DistributionProductCategory> selectAllDistributionProdCategory();
	
	
	/**
	 * 根据分销id查询所有分销返佣点 
	 */
	public List<DistributionProductCategory> selectDistributionProductCategory(Long distributorInfoId);
	
	
	/**
	 *  新增分销返佣点
	 */
	public void insertDistributionProductCategory(DistributionProductCategory distributionProductCategory);

	/**
	 * 驴妈妈对应的百度的城市名称
	 * 
	 * @param placeId
	 *            目的地或者出发地Id
	 * @return
	 */
	public List<String> getBaiduCityNameByPlaceIds(List<Long> placeIds);

	/**
	 * 插入百度团购分销产品
	 */
	public void insertBaiduTuangouProduct(DistributionBaiduTuangou baiduTuangouProduct);

	/**
	 * 修改百度团购分销产品
	 */
	public void updateBaiduTuangouProduct(DistributionBaiduTuangou baiduTuangouProduct);

	/**
	 * 分页查询百度团购分销产品
	 */
	public List<DistributionBaiduTuangou> selectBaiduTuangouProducts(Long startRow, Long endRow);

	/**
	 * 获取百度团购分销产品总数
	 */
	public Long getDistributionBaiduTuangouTotalCount();

	/**
	 * 删除所有百度团购分销产品
	 */
	public void deleteAllBaiduTuangouProducts();

	/**
	 * 根据产品Id查询百度团购产品
	 */
	public DistributionBaiduTuangou selectBaiduTuangouProductByProductId(Long productId);

	/**
	 * 根据产品Id删除百度团购产品
	 */
	public void deleteBaiduTuangouProductByProductId(Long productId);
	/**
	 * 根据分销商查询分销产品类别
	 * 
	 * @author: ranlongfei 2013-6-25
	 * @param distributorId
	 * @param volid 
	 * @return
	 */
	List<DistributorInfo> selectByProductBranchIdAndVolid(Long distributorId, String volid);
	/**
	 * 查询各自页面的分销商列表数据
	 * 
	 * @author: ranlongfei 2013-6-25
	 * @param productBranchId
	 * @param type
	 * @return
	 */
	List<DistributorInfo> selectDistributorListByType(Long productBranchId, String operateType);
	
	/**
	 * 条件查询分销商列表（分页）
	 */
	public List<DistributorInfo> selectDistributorByParams(Map<String, Object> parameterObject);
	
	/**
	 * 条件查询分销商总数
	 */
	public Integer selectDistributorByParamsCount(Map<String, Object> parameterObject);
	
	/**
	 * 通过分销商IP的id取得分销商IP 
	 */
	public DistributorIp getDistributorIpByDistributorIpId(Long distributorIpId);
	
	/**
	 *	保存分销推送消息
	 * @param distributionMessage
	 */
	public void saveOrUpdateDistributionMessage(DistributionMessage distributionMessage);


	/**
	 * 根据消息参数获取对应的消息列表
	 * @param distributionMessage
	 * @return
	 */
	public List<DistributionMessage> getMessagesByMsgParams(DistributionMessage distributionMessage);
	
	/**
	 * 根据消息参数获取对应单条的消息
	 * @param distributionMessage
	 * @return
	 */
	public DistributionMessage getMessageByMsgParams(DistributionMessage distributionMessage);
}
