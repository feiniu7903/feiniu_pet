package com.lvmama.passport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.pass.EplaceMetaProduct;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.po.pass.SupplierRelateProduct;
import com.lvmama.comm.bee.po.pass.UserRelateMenu;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.eplace.dao.EplaceSupplierDAO;
import com.lvmama.eplace.dao.PassPortAuthResourcesDAO;
import com.lvmama.eplace.dao.PassPortLogDao;
import com.lvmama.eplace.dao.PassPortUserDAO;
import com.lvmama.eplace.dao.SupplierRelateProductDAO;
import com.lvmama.eplace.dao.UserRelateMenuDAO;
import com.lvmama.eplace.dao.UserRelateSupplierProductDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;

/**
 * 
 * @author luoyinqi
 * 
 */

public class EPlaceServiceImpl implements EPlaceService {
	private EplaceSupplierDAO eplaceSupplierDAO;
	private MetaProductDAO metaProductDAO;
	private SupplierRelateProductDAO supplierRelateProductDAO;
	private PassPortUserDAO passPortUserDAO;
	private PassPortLogDao passPortLogDao;
	private PassPortAuthResourcesDAO passPortAuthResourcesDAO;
	private UserRelateMenuDAO userRelateMenuDAO;
	private UserRelateSupplierProductDAO userRelateSupplierProductDAO;
	private MetaProductBranchDAO metaProductBranchDAO;
	
	public PassPortUser getPassPortUserByUserName(Map map) {
		return this.passPortUserDAO.getPassPortUserByUserName(map);
	}
	@SuppressWarnings("unchecked")
	public List<PassPortAuthResources> findPassPortAuthResourcesByMapIsChecked(Map param){
		List resourceslist=passPortAuthResourcesDAO.selectByParms(param);
		List userRelateList=this.userRelateMenuDAO.findUserRelateMenuByMap(param);
		for (int i = 0; i < resourceslist.size(); i++) {
			PassPortAuthResources p=(PassPortAuthResources)resourceslist.get(i);
			for (int j = 0; j < userRelateList.size(); j++) {
				UserRelateMenu u=(UserRelateMenu)userRelateList.get(j);
				if(u.getResourceId().equals(p.getResourceId())){
					p.setChecked(true);
				}
			}
		}
		return resourceslist;
	}
	@SuppressWarnings("unchecked")
	public List<SupplierRelateProduct> findSupplierRelateProductByMapIsChecked(Map map){
		List productList=supplierRelateProductDAO.findSupplierRelateProductByMap(map);
		List supplierProductList=this.userRelateSupplierProductDAO.findUserRelateSupplierProductByMap(map);
		for (int i = 0; i < productList.size(); i++) {
			SupplierRelateProduct s=(SupplierRelateProduct)productList.get(i);
			for (int j = 0; j < supplierProductList.size(); j++) {
				UserRelateSupplierProduct u=(UserRelateSupplierProduct)supplierProductList.get(j);
				if(u.getMetaProductBranchId().equals(s.getMetaProductBranchId())){
					s.setChecked(true);
				}
			}
		}
		return productList;
	}
	
	public void updatePassPortUserResources(PassPortUser passPortUser,
			List resourcesIds, List productIds) {
		passPortUserDAO.updatePassPortUser(passPortUser);
		Long passportUserId=passPortUser.getPassPortUserId();
		if(passportUserId!=null){
			if(resourcesIds!=null){
				userRelateMenuDAO.deleteUserRelateMenu(passportUserId);
				this.addUserPelateMenuByUserId(resourcesIds, passportUserId);
			}
			if(productIds!=null){
				userRelateSupplierProductDAO.deleteUserRelateSupplierProduct(passportUserId);
				for (int i = 0; i < productIds.size(); i++) {
					SupplierRelateProduct p=(SupplierRelateProduct)productIds.get(i);
					if(p.isChecked()){
						UserRelateSupplierProduct u=new UserRelateSupplierProduct();
						u.setMetaProductId(p.getMetaProductId());
						u.setPassPortUserId(passPortUser.getPassPortUserId());
						u.setMetaProductBranchId(p.getMetaProductBranchId());
						userRelateSupplierProductDAO.addUserRelateSupplierProduct(u);
					}
					
				}
			}
		}
	}
	
	public void addPassPortUserResources(PassPortUser passPortUser,
			List resourcesIds, List productIds) {
		Long passportUserId=passPortUserDAO.addPassPortUser(passPortUser);
		if(passportUserId!=null){
			this.addUserPelateMenuByUserId(resourcesIds, passportUserId);
			for (int i = 0; i < productIds.size(); i++) {
				SupplierRelateProduct p=(SupplierRelateProduct)productIds.get(i);
				if(p.isChecked()){
					UserRelateSupplierProduct u=new UserRelateSupplierProduct();
					u.setMetaProductId(p.getMetaProductId());
					u.setMetaProductBranchId(p.getMetaProductBranchId());
					u.setPassPortUserId(passPortUser.getPassPortUserId());
					userRelateSupplierProductDAO.addUserRelateSupplierProduct(u);
				}
				
			}
		}
	}

	public List<SupplierRelateProduct> findSupplierRelateProductByMap(Map map) {
		return supplierRelateProductDAO.findSupplierRelateProductByMap(map);
	}

	@Override
	public List<UserRelateMenu> findUserRelateMenuByMap(Map param) {
		return userRelateMenuDAO.findUserRelateMenuByMap(param);
	}

	@Override
	public Long findUserRelateMenuByMapCount(Map param) {
		return userRelateMenuDAO.findUserRelateMenuByMapCount(param);
	}

	public List<PassPortAuthResources> findPassPortAuthResourcesByMap(Map param) {
		return passPortAuthResourcesDAO.selectByParms(param);
	}

	public Long findPassPortUserByMapCount(Map param) {
		return passPortUserDAO.findPassPortUserByMapCount(param);
	}

	public PassPortUser getPassPortUserByPk(String passPortUserId) {
		return passPortUserDAO.getPassPortUserByPk(passPortUserId);
	}

	public void updatePassPortUser(PassPortUser passPortUser, List resourcesIds) {
		passPortUserDAO.updatePassPortUser(passPortUser);
		Long passportUserId=passPortUser.getPassPortUserId();
		if(passportUserId!=null&&resourcesIds!=null){
			userRelateMenuDAO.deleteUserRelateMenu(passportUserId);
			this.addUserPelateMenuByUserId(resourcesIds, passportUserId);
		}
	}

	public void addPassPortUser(PassPortUser passPortUser,List resourcesIds) {
		Long passportid=passPortUserDAO.addPassPortUser(passPortUser);
		if(passportid!=null){
			this.addUserPelateMenuByUserId(resourcesIds, passportid);
		}
	}
	
	private void addUserPelateMenuByUserId(List resourcesIds,Long passportUserId){
		Long []parentIds=new Long[resourcesIds.size()];
		for (int i = 0; i < resourcesIds.size(); i++) {
			PassPortAuthResources p=(PassPortAuthResources)resourcesIds.get(i);
			if(p.isChecked()){
				UserRelateMenu u=new UserRelateMenu();
				u.setPassPortUserId(passportUserId);
				u.setResourceId(p.getResourceId());
				parentIds[i]=p.getParentId();
				userRelateMenuDAO.addUserRelateMenu(u);
			}
			
		}
		for (int i = 0; i < parentIds.length; i++) {
			if(parentIds[i]!=null){
				UserRelateMenu u=new UserRelateMenu();
				u.setPassPortUserId(passportUserId);
				u.setResourceId(parentIds[i]);
				userRelateMenuDAO.addUserRelateMenu(u);
				break;
			}
		}
	}
	

	public List<PassPortUser> findPassPortUserByMap(Map param) {
		return passPortUserDAO.findPassPortUserByMap(param);
	}

	public List<MetaProduct> findMetaProduct(Map param) {
		return this.metaProductDAO.findMetaProduct(param);
	}
	public List<MetaProductBranch> selectMetaProductBranchBySupplierId(Long supplierId) {
		return metaProductBranchDAO.selectMetaProductBranchBySupplierId(supplierId);
	}

	@Override
	public List<EplaceMetaProduct> findEplaceMetaProductByMap(Map param,List<Long> ids) {
		List<EplaceMetaProduct> ePlaceList = new ArrayList<EplaceMetaProduct>();
		Long targetId = (Long)param.get("targetId");
		Long supplierId = Long.valueOf(param.get("supplierId").toString());
		List<MetaProductBranch> list = null;
		//通过有无履行对象ID可以判定是否是通过履行对象ID进行查询
		if(targetId!=null && targetId!=0){			
			list = metaProductBranchDAO.selectMetaBranchByProductIds(ids);
		}else{
			list = metaProductBranchDAO.selectMetaProductBranchBySupplierId(supplierId);
		}
		List<Long> supplyList = this.supplierRelateProductDAO
				.findSupplierRelateProductIds(Long.valueOf(param.get(
						"eplaceSupplierId").toString()));
		for (MetaProductBranch metaProductBranch : list) {
			EplaceMetaProduct eplaceMetaProduct = new EplaceMetaProduct();
			eplaceMetaProduct.setMetaProductId(metaProductBranch.getMetaProductId());
			eplaceMetaProduct.setMetaBranchId(metaProductBranch.getMetaBranchId());
			eplaceMetaProduct.setBranchName(metaProductBranch.getBranchName());
			if (supplyList.contains(metaProductBranch.getMetaBranchId())) {
				eplaceMetaProduct.setIschecked(true);
			}
			ePlaceList.add(eplaceMetaProduct);
		}
		return ePlaceList;
	}
		
	public EplaceSupplier getEplaceSupplierByPk(Long eplaceSupplierId) {
		return eplaceSupplierDAO.getEplaceSupplierByPk(eplaceSupplierId);
	}

	public void closeEplaceSupplier(EplaceSupplier eplaceSupplier) {
		eplaceSupplierDAO.updateEplaceSupplier(eplaceSupplier);
	}

	public void updateEplaceSupplier(EplaceSupplier eplaceSupplier,List ordItemIds) {
		eplaceSupplierDAO.updateEplaceSupplier(eplaceSupplier);
		// 先删除，后增加.
		this.supplierRelateProductDAO.deleteSupplierRelateProduct(eplaceSupplier.getEplaceSupplierId());
		if (ordItemIds != null) {
			for (int i = 0; i < ordItemIds.size(); i++) {
				EplaceMetaProduct eplaceMetaProduct = (EplaceMetaProduct) ordItemIds.get(i);
				if (eplaceMetaProduct.isIschecked()) {
					SupplierRelateProduct supplyProduct = new SupplierRelateProduct();
					supplyProduct.setMetaProductId(eplaceMetaProduct.getMetaProductId());
					supplyProduct.setEplaceSupplierId(eplaceSupplier.getEplaceSupplierId());
					supplyProduct.setMetaProductBranchId(eplaceMetaProduct.getMetaBranchId());
					supplierRelateProductDAO.addSupplierRelateProduct(supplyProduct);
				}
			}
		}
	}

	public void addEplaceSupplier(EplaceSupplier eplaceSupplier, List ordItemIds) {
		Long supplyId = eplaceSupplierDAO.addEplaceSupplier(eplaceSupplier);
		if (supplyId != null) {
			for (int i = 0; i < ordItemIds.size(); i++) {
				SupplierRelateProduct supplyProduct = new SupplierRelateProduct();
				supplyProduct.setMetaProductBranchId(Long.valueOf(ordItemIds.get(i).toString()));
				supplyProduct.setEplaceSupplierId(supplyId);
				supplierRelateProductDAO.addSupplierRelateProduct(supplyProduct);
			}
		}
	}
  
	@Override
	public List<EplaceSupplier> findEplaceSupplierByMap(List<SupSupplier> suppliers) {
		List<Long> supplierIdList = new ArrayList<Long>();
		for (int i = 0; i < suppliers.size(); i++) {
			supplierIdList.add(suppliers.get(i).getSupplierId());
		}
		Map<Long, EplaceSupplier> eplaceMap = new HashMap<Long, EplaceSupplier>();
		List<EplaceSupplier> eplaceSupplierList = eplaceSupplierDAO.findEplaceSupplierBySupplierId(supplierIdList);
		for (int i = 0; i < eplaceSupplierList.size(); i++) {
			eplaceMap.put(eplaceSupplierList.get(i).getSupplierId(), eplaceSupplierList.get(i));
		}
		
		List<EplaceSupplier> eplaceList = new ArrayList<EplaceSupplier>();
		EplaceSupplier eplaceSupplier = null;
		EplaceSupplier oldEplace = null;
		SupSupplier supSupplier = null;
		for (int i = 0; i < suppliers.size(); i++) {
			supSupplier = suppliers.get(i);
			eplaceSupplier = new EplaceSupplier();
			oldEplace = eplaceMap.get(supSupplier.getSupplierId());
			
			if(oldEplace != null){
				eplaceSupplier.setEplaceSupplierId(oldEplace.getEplaceSupplierId());
				eplaceSupplier.setSupplierId(oldEplace.getSupplierId());
				eplaceSupplier.setSupplierName(oldEplace.getSupplierName());
				eplaceSupplier.setStatus(oldEplace.getStatus());
				eplaceSupplier.setProductManager(oldEplace.getProductManager());
				eplaceSupplier.setCustomerVisible(oldEplace.getCustomerVisible());
				eplaceSupplier.setMobile(oldEplace.getMobile());
				eplaceSupplier.setCreateDate(oldEplace.getCreateDate());
			}
			eplaceSupplier.setSupSupplier(supSupplier);
			eplaceSupplier.setSupplierId(supSupplier.getSupplierId());
			eplaceSupplier.setSupplierName(supSupplier.getSupplierName());
			eplaceList.add(eplaceSupplier);
		}
		
		return eplaceList;
	}

	@Override
	public List<PassPortLog> queryPassPortLogByOrderItemMetaId(
			Long orderItemMetaId) {
		List<PassPortLog> list = new ArrayList<PassPortLog>();
		list = passPortLogDao.selectByOrderItemMetaId(orderItemMetaId);
		return list;
	}
	/**
	 * 查询供应商用户对应得采购产品信息
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getMeatProductBySupplierUserId(Long userId){
		 List<UserRelateSupplierProduct> supplierRelateProduct=this.userRelateSupplierProductDAO.getSupplierRelateProductByUserId(userId);
		return supplierRelateProduct;
	}
	
	/**
	 * 通过用户编号查询到集合.展示其详情的所属履行对象.
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getSupplierUserListByTargetId(Long userId){
		return this.userRelateSupplierProductDAO.getSupplierUserListByTargetId(userId);
	}
	
	/**
	 * 查询供应商用户对应得采购产品履行对象id
	 * @param userId
	 * @return
	 */
	public UserRelateSupplierProduct getSupplierUserForTargetId(Long userId){
		UserRelateSupplierProduct userRelateSupplierProduct=this.userRelateSupplierProductDAO.getSupplierUserForTargetId(userId);
		return userRelateSupplierProduct;
	}
	/**
	 * 查询供应商用户对应的履行对象个数
	 * @param userId
	 * @return
	 */
	public long getSupplierUserTargetIdTotal(Long userId) {
		return userRelateSupplierProductDAO.getSupplierUserTargetIdTotal(userId);
	}
	/**
	 * 添加通关日志
	 * @param passPortLog
	 */
	public void addPassPortLog(PassPortLog passPortLog){
		this.passPortLogDao.addPassPortLog(passPortLog);
	}
	public void setEplaceSupplierDAO(EplaceSupplierDAO eplaceSupplierDAO) {
		this.eplaceSupplierDAO = eplaceSupplierDAO;
	}

	public void setPassPortLogDao(PassPortLogDao passPortLogDao) {
		this.passPortLogDao = passPortLogDao;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
	public void setSupplierRelateProductDAO(
			SupplierRelateProductDAO supplierRelateProductDAO) {
		this.supplierRelateProductDAO = supplierRelateProductDAO;
	}

	public void setPassPortUserDAO(PassPortUserDAO passPortUserDAO) {
		this.passPortUserDAO = passPortUserDAO;
	}
	public void setPassPortAuthResourcesDAO(
			PassPortAuthResourcesDAO passPortAuthResourcesDAO) {
		this.passPortAuthResourcesDAO = passPortAuthResourcesDAO;
	} 
	public void setUserRelateMenuDAO(UserRelateMenuDAO userRelateMenuDAO) {
		this.userRelateMenuDAO = userRelateMenuDAO;
	}
	public void setUserRelateSupplierProductDAO(
			UserRelateSupplierProductDAO userRelateSupplierProductDAO) {
		this.userRelateSupplierProductDAO = userRelateSupplierProductDAO;
	}
 
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}
	@Override
	public List<MetaProduct> selectMetaProductByIds(List<Long> ids) {
		return metaProductDAO.selectMetaProductByIds(ids);
	}
	@Override
	public List<UserRelateSupplierProduct> getSupplierUserProductByUserId(
			Long userId) {
 		return userRelateSupplierProductDAO.getSupplierUserProductByUserId(userId);
	}

	@Override
	public List<PassPortAuthResources> selectSecondResourceByParentId(Long userId, String category,Long parentId){
		return passPortAuthResourcesDAO.selectSecondResourceByParentId(userId, category, parentId);
	}
	@Override
	public List<String> selectCategoryByAdmin(){
		return passPortAuthResourcesDAO.selectCategoryByAdmin();	
	}
	@Override
	public List<String> selectCategoryByUserId(Long userId) {
		return this.passPortAuthResourcesDAO.selectCategoryByUserId(userId);
	}
	@Override
	public List<PassPortAuthResources> getResourcesByUserAndCategory(
			Long userId, String category) {
		return this.passPortAuthResourcesDAO.getResourcesByUserAndCategory(
				userId, category);
	}

	/**
	 * 查询资源地址
	 * 
	 * @param parms
	 * @return
	 */
	@Override
	public List<PassPortAuthResources> selectByParms(Map parms) {
		return this.passPortAuthResourcesDAO.selectByParms(parms);
	}

	/**
	 * 添加资源
	 * 
	 * @param resource
	 */
	@Override
	public void insertResource(PassPortAuthResources resource) {
		this.passPortAuthResourcesDAO.insertResource(resource);
	}

	/**
	 * 编辑资源
	 * 
	 * @param resource
	 */
	@Override
	public void updateResource(PassPortAuthResources resource) {
		this.passPortAuthResourcesDAO.updateResource(resource);
	}

	/**
	 * 删除资源
	 * 
	 * @param resourceId
	 */
	@Override
	public void deleteResource(Long resourceId) {
		this.passPortAuthResourcesDAO.deleteResource(resourceId);
	}
	@Override
	public List<PassPortAuthResources> selectSecondItems(Long parentId) {
		return passPortAuthResourcesDAO.selectSecondItems(parentId);
	}
	@Override
	public List<MetaProductBranch> selectMetaProductBranchBySupplierType(
			Map<String, Object> param) {
		return metaProductBranchDAO.selectMetaProductBranchBySupplierType(param);
	}
 
}
