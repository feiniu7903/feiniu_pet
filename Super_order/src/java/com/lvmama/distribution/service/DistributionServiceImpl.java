package com.lvmama.distribution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.distribution.DistributionBaiduTuangou;
import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceImage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;
import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.DistributorIp;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.distribution.dao.DistributionBaiduTuangouDAO;
import com.lvmama.distribution.dao.DistributionMessageDao;
import com.lvmama.distribution.dao.DistributionPlaceImageDAO;
import com.lvmama.distribution.dao.DistributionProductCategoryDAO;
import com.lvmama.distribution.dao.DistributorInfoDAO;
import com.lvmama.distribution.dao.DistributorIpDAO;

/**
 * 分销管理业务逻辑
 * 
 * @author lipengcheng
 * 
 */
public class DistributionServiceImpl implements DistributionService {
	private DistributorInfoDAO distributorInfoDAO;
	private DistributorIpDAO distributorIpDAO;
	private DistributionPlaceImageDAO distributionPlaceImageDAO;
	private DistributionProductCategoryDAO distributionProductCategoryDAO;
	private ComPlaceDAO comPlaceDAO;
	private DistributionBaiduTuangouDAO distributionBaiduTuangouDAO;
	private DistributionMessageDao distributionMessageDao;
	/**
	 * 驴妈妈对应的百度的城市名称
	 * @param placeId 目的地或者出发地Id
	 * @return
	 */
	@Override
	public List<String> getBaiduCityNameByPlaceIds(List<Long> placeIds){
		return comPlaceDAO.getBaiduCityNameByPlaceIds(placeIds);
	}

	@Override
	public DistributorInfo selectByDistributorId(Long distributorId) {
		return this.distributorInfoDAO.selectByDistributorId(distributorId);
	}

	/**
	 * 取出全部的分销商
	 */
	public List<DistributorInfo> getAllDistributors() {
		return this.distributorInfoDAO.getAllDistributors();
	}

	/**
	 * 新增分销商
	 */
	public void insertDistributorInfo(DistributorInfo distributor) {
		distributorInfoDAO.insert(distributor);
	}

	/**
	 * 更新分销商信息
	 */
	public void updateDistributorInfo(DistributorInfo distributor) {
		distributorInfoDAO.update(distributor);
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	public List<DistributorInfo> selectByProductBranchIdAndVolid(Long productBranchId, String volid) {
		return distributorInfoDAO.selectByProductIdAndProductBranchIdAndVolid(productBranchId, volid);
	}

	public List<DistributorInfo> selectWhiteListByProductIdAndProductBranchId(Long productId, Long branchId) {
		return this.distributorInfoDAO.selectWhiteListByProductIdAndProductBranchId(productId, branchId);
	}

	/**
	 * 查询各自页面的分销商列表数据
	 * 
	 * @author: ranlongfei 2013-6-25
	 * @param productBranchId
	 * @param type
	 * @return
	 */
	public List<DistributorInfo> selectDistributorListByType(Long productBranchId, String type) {
		if("WHITE_LIST".equals(type)) {
			List<DistributorInfo> resultList = getAllDistributors();
			if(resultList != null && !resultList.isEmpty()) {
				List<DistributorInfo> list = selectByProductBranchIdAndVolid(productBranchId, null);
				resultList.removeAll(list);
				return resultList;
			}
		} else if("BLACK_LIST".equals(type)) {
			return selectByProductBranchIdAndVolid(productBranchId, "true");
		} else if("RELEASE_LIST".equals(type)) {
			return selectByProductBranchIdAndVolid(productBranchId, "false");
		}
		return null;
	}

	/**
	 * 通过分销商ID取得分销商所分配的IP
	 */
	public List<DistributorIp> getDistributorIpByDistributorInfoId(Long distribotorId) {
		return distributorIpDAO.selectByDistributorId(distribotorId);
	}
	
	/**
	 * 通过分销商IP的id取得分销商IP 
	 */
	public DistributorIp getDistributorIpByDistributorIpId(Long distributorIpId){
		return distributorIpDAO.getDistributorIpByDistributorIpId(distributorIpId);
	}

	/**
	 * 通过IP信息ID更新IP
	 * 
	 * @param ip
	 * @param distributorIpId
	 */
	public void updateDistributorIpByDistributorIpId(DistributorIp distributorIp) {
		distributorIpDAO.updateByDistributorIpId(distributorIp);
	}

	/**
	 * 通过IP信息ID删除IP
	 */
	public void deleteDistributorIpByDistributorIpId(Long distributorIpId) {
		distributorIpDAO.deleteByDistributorIpId(distributorIpId);
	}
	
	/**
	 * 删除分销返点
	 */
	public void deleteDistributorProductCategory(Long distributionProductCategoryId){
		distributionProductCategoryDAO.deleteBydistributionProductCategoryId(distributionProductCategoryId);
	}

	/**
	 * 新增IP
	 */
	public void insertDistributorIp(DistributorIp distributorIp) {
		distributorIpDAO.insert(distributorIp);
	}

	@Override
	public DistributorInfo selectByDistributorCode(String distributorCode) {
		return distributorInfoDAO.selectByDistributorCode(distributorCode);
	}

	@Override
	public DistributorInfo selectByDistributorChannel(String distributorChannel) {
		return distributorInfoDAO.selectByDistributorChannel(distributorChannel);
	}

	@Override
	public List<DistributionPlaceImage> selectPlaceImageByName(String placeName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeName", placeName);
		return distributionPlaceImageDAO.selectPlaceImageByName(param);
	}

	@Override
	public List<String> selectImageByPlaceId(long placeId) {
		return distributionPlaceImageDAO.selectImageByPlaceId(placeId);
	}

	@Override
	public List<String> selectByDistributorInfoId(Long distributorInfoId) {
		return distributorIpDAO.selectByDistributorInfoId(distributorInfoId);
	}

	@Override
	public List<DistributionPlaceProduct> selectAllRouteProduct(Map<String, Object> param) {
		return distributionPlaceImageDAO.selectAllRouteProduct(param);
	}

	@Override
	public Integer selectAllRouteProductCount() {
		return distributionPlaceImageDAO.selectAllRouteProductCount();
	}

	@Override
	public List<DistributionPlaceImage> selectPlaceCityByName(Map<String, Object> param) {
		return distributionPlaceImageDAO.selectPlaceCityByName(param);
	}

	@Override
	public List<DistributionPlaceImage> selectPlaceImageByName(Map<String, Object> param) {
		return distributionPlaceImageDAO.selectPlaceImageByName(param);
	}

	@Override
	public List<DistributionPlaceProduct> selectProductByPlaceId(Long placeId) {
		return distributionPlaceImageDAO.selectProductByPlaceId(placeId);
	}

	@Override
	public DistributionPlaceImage selectSightByName(String placeName) {
		return distributionPlaceImageDAO.selectSightByName(placeName);
	}

	@Override
	public Long getDistributionProductPlaceCount(Map<String, Object> params) {
		return this.comPlaceDAO.getDistributionProductPlaceCount(params);
	}

	@Override
	public List<Place> getDistributionProductPlace(Map<String, Object> params) {
		List<Place> placeList = this.comPlaceDAO.getDistributionProductPlace(params);
		Long distributorId = (Long) params.get("distributorId");
		for (Place place : placeList) {
			List<String> featureList = this.comPlaceDAO.getFeaturesForDistribution(place.getPlaceId(), distributorId);
			if (!featureList.isEmpty()) {
				place.setDescription(featureList.get(0));
			}
		}
		return placeList;
	}

	@Override
	public List<DistributionProductCategory> selectAllDistributionProdCategory() {
		return distributionProductCategoryDAO.selectAllDistributionProdCategory();
	}
	
	public List<DistributionProductCategory> selectDistributionProductCategory(Long distributorInfoId){
		return distributionProductCategoryDAO.selectListDistributionProductCategory(distributorInfoId);
	}
	
	/**
	 *  新增分销返佣点
	 */
	public void insertDistributionProductCategory(DistributionProductCategory distributionProductCategory){
		distributionProductCategoryDAO.insert(distributionProductCategory);
	}
	
	/**
	 * 按条件查询是否存在分销返佣点 
	 */
	public Long selectPistributionProductCategoryConditionByCount(Map<String,Object> params){
		return distributionProductCategoryDAO.selectPistributionProductCategoryConditionByCount(params);
	}

	public void setDistributionPlaceImageDAO(DistributionPlaceImageDAO distributionPlaceImageDAO) {
		this.distributionPlaceImageDAO = distributionPlaceImageDAO;
	}

	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}

	public void setDistributionProductCategoryDAO(DistributionProductCategoryDAO distributionProductCategoryDAO) {
		this.distributionProductCategoryDAO = distributionProductCategoryDAO;
	}

	public void setDistributionBaiduTuangouDAO(DistributionBaiduTuangouDAO distributionBaiduTuangouDAO) {
		this.distributionBaiduTuangouDAO = distributionBaiduTuangouDAO;
	}

	@Override
	public void insertBaiduTuangouProduct(DistributionBaiduTuangou baiduTuangouProduct) {
		distributionBaiduTuangouDAO.insert(baiduTuangouProduct);
	}

	@Override
	public void updateBaiduTuangouProduct(DistributionBaiduTuangou baiduTuangouProduct) {
		distributionBaiduTuangouDAO.updateByPrimaryKey(baiduTuangouProduct);
	}

	@Override
	public List<DistributionBaiduTuangou> selectBaiduTuangouProducts(Long startRow, Long endRow) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startRow", startRow);
		params.put("endRow", endRow);
		return distributionBaiduTuangouDAO.selectBaiduTuangouProducts(params);
	}

	@Override
	public DistributionBaiduTuangou selectBaiduTuangouProductByProductId(Long productId) {
		return distributionBaiduTuangouDAO.selectBaiduTuangouProductByProductId(productId);
	}

	@Override
	public Long getDistributionBaiduTuangouTotalCount() {
		return distributionBaiduTuangouDAO.getDistributionBaiduTuangouTotalCount();
	}
	
	@Override
	public void deleteAllBaiduTuangouProducts() {
		distributionBaiduTuangouDAO.deleteAllBaiduTuangouProducts();
	}
	
	@Override
	public void deleteBaiduTuangouProductByProductId(Long productId) {
		distributionBaiduTuangouDAO.deleteBaiduTuangouProductByProductId(productId);
	}
	
	/**
	 * 条件查询分销商列表（分页）
	 */
	public List<DistributorInfo> selectDistributorByParams(Map<String, Object> parameterObject){
		return distributorInfoDAO.selectDistributorByParams(parameterObject);
	}
	
	/**
	 * 条件查询分销商总数
	 */
	public Integer selectDistributorByParamsCount(Map<String, Object> parameterObject){
		return distributorInfoDAO.queryDistributorInfoCount(parameterObject);
	}

	public void setDistributorInfoDAO(DistributorInfoDAO distributorInfoDAO) {
		this.distributorInfoDAO = distributorInfoDAO;
	}

	public void setDistributorIpDAO(DistributorIpDAO distributorIpDAO) {
		this.distributorIpDAO = distributorIpDAO;
	}

	public void setDistributionMessageDao(
			DistributionMessageDao distributionMessageDao) {
		this.distributionMessageDao = distributionMessageDao;
	}
	
	@Override
	public void saveOrUpdateDistributionMessage(DistributionMessage distributionMessage) {
		boolean flag = false;
		boolean isUnpushed = false;
		if(distributionMessage.getMessageId() != null){
			flag = true;
		}else{
			DistributionMessage oldMessage = distributionMessageDao.queryByMsgParams(distributionMessage);
			if(oldMessage != null && oldMessage.getMessageId() != null){
				isUnpushed = "unpushed".equals(oldMessage.getStatus());
				distributionMessage.setMessageId(oldMessage.getMessageId());
				flag = true;
			}
		}
		if(flag){
			if(!isUnpushed){
				distributionMessageDao.update(distributionMessage);
			}
		}else{
			distributionMessageDao.insert(distributionMessage);
		}
	}

	@Override
	public List<DistributionMessage> getMessagesByMsgParams(DistributionMessage distributionMessage) {
		return distributionMessageDao.queryByParams(distributionMessage);
	}
	@Override
	public DistributionMessage getMessageByMsgParams(DistributionMessage distributionMessage){
		return distributionMessageDao.queryByMsgParams(distributionMessage);
	}

}