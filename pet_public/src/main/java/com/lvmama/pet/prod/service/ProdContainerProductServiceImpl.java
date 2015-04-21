package com.lvmama.pet.prod.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.po.prod.ProdContainerProduct;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.prod.ProductProductPlace;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.pet.vo.ContainerPlaceBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ContainerProductVO;
import com.lvmama.pet.prod.dao.ProdContainerDAO;
import com.lvmama.pet.prod.dao.ProdContainerFromPlaceDAO;
import com.lvmama.pet.prod.dao.ProdContainerProductDAO;
import com.lvmama.pet.prod.dao.ProdProductTagDAO;
import com.lvmama.pet.prod.dao.ProductProductPlaceDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;
import com.lvmama.pet.search.dao.ProductSearchInfoDAO;

public class ProdContainerProductServiceImpl implements
		ProdContainerProductService {
	@Autowired
	private ProdContainerProductDAO prodContainerProductDAO;
	@Autowired
	private ProductSearchInfoDAO productSearchInfoDAO;
	@Autowired
	private ProductProductPlaceDAO productProductPlaceDAO;
	@Autowired
	private ProdContainerDAO prodContainerDAO;
	@Autowired
	private ProdProductTagDAO prodProductTagDAO;
	@Autowired
	private ProdContainerFromPlaceDAO prodContainerFromPlaceDAO;
	@Autowired
	private ComLogDAO comLogDAO;
	

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public void deleteInvalidContainerProducts() {
		prodContainerProductDAO.deleteInvalidContainerProducts();
	}

	public void deleteContainerProductsByTagId(Long containerId, Long tagId) {
		prodContainerProductDAO.deleteContainerProductsByTagId(containerId, tagId);
	}

	public ContainerProductVO createContainerProductVO(Long productId) {
		ContainerProductVO containerProductVO = new ContainerProductVO();
		if(null != productId){
			ProductSearchInfo productSearchInfo = productSearchInfoDAO.queryProductSearchInfoByProductId(productId);
			//无效的产品不需要放入容器
			if(null == productSearchInfo || "N".equals(productSearchInfo.getIsValid())) {
				return containerProductVO;
			}
			
			//酒店或其他类产品不放入容器
			if(null == productSearchInfo || !(isTicketProduct(productSearchInfo.getProductType()) || isRouteProduct(productSearchInfo.getProductType()))){
				return containerProductVO;
			}
			
			//如果没有标的上下级关系信息则放弃处理
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("productId", productId);
			List<ProductProductPlace> productProductPlaceList = productProductPlaceDAO.query(param);
			if(null == productProductPlaceList || productProductPlaceList.isEmpty()) {
				return containerProductVO;
			}
			
			//封装属性
			containerProductVO.setProductId(productId);
	        containerProductVO.setSeq(productSearchInfo.getSeq());
	        containerProductVO.setProductType(productSearchInfo.getProductType());
	        containerProductVO.setSubProductType(productSearchInfo.getSubProductType());
	        containerProductVO.setIsValid(productSearchInfo.getIsValid());
	        containerProductVO.setOnline(isOnline(productSearchInfo.getIsValid()));
	        containerProductVO.setChannel(productSearchInfo.getChannel());
	        String containerCode = null;
	        if (Constant.PRODUCT_TYPE.TICKET.name().equals(productSearchInfo.getProductType())) {
	            containerCode = "DZMP_RECOMMEND";
	            containerProductVO.setFromPlaceId(null);
	            containerProductVO.setToPlaceIdStr(getToPlaceIdStr(productProductPlaceList));
	        } else if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(productSearchInfo.getSubProductType())) {
	            containerCode = "ZYZZ_RECOMMEND";
	            containerProductVO.setFromPlaceId(null);
	            containerProductVO.setToPlaceIdStr(getToPlaceIdStr(productProductPlaceList));
	        } else if (Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(productSearchInfo.getSubProductType())) {
	            containerCode = "ZBY_RECOMMEND";
	            containerProductVO.setFromPlaceId(getFromPlaceId(productProductPlaceList));
	            containerProductVO.setToPlaceIdStr(getToPlaceIdStr(productProductPlaceList));
	        } else if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(productSearchInfo.getSubProductType()) || Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(productSearchInfo.getSubProductType())) {
	            containerCode = "GNY_RECOMMEND";
	            containerProductVO.setFromPlaceId(getFromPlaceId(productProductPlaceList));
	            containerProductVO.setToPlaceIdStr(getToPlaceIdStr(productProductPlaceList));
	        } else if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(productSearchInfo.getSubProductType()) || Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(productSearchInfo.getSubProductType())) {
	            containerCode = "CJY_RECOMMEND";
	            containerProductVO.setFromPlaceId(getFromPlaceId(productProductPlaceList));
	            containerProductVO.setToPlaceIdStr(getToPlaceIdStr(productSearchInfo.getDepartAreaCode(),productSearchInfo.getRouteStandard()));
	        } else if (Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(productSearchInfo.getSubProductType())) {
	            containerCode = "KXLX";
	            containerProductVO.setFromPlaceId(getFromPlaceId(productProductPlaceList));
	            containerProductVO.setToPlaceIdStr(getToPlaceIdStr(productProductPlaceList));
	        }
	        List<Long> containerIdListBefore = prodContainerProductDAO.selectContainerIdListProductAlreadyExists(productId);
	        containerProductVO.setContainerIdListBefore(containerIdListBefore);
	        
	        List<Long> containerIdListAfter = prodContainerDAO.selectContainerIdListProductBelongsTo(containerCode, containerProductVO.getFromPlaceId(), containerProductVO.getToPlaceId());
	        containerProductVO.setContainerIdListAfter(containerIdListAfter);
	        
	        containerProductVO.setProductTagIds(getTagIdList(productSearchInfo.getTagIds()));
	        
	        return containerProductVO;
		}
		return containerProductVO;
	}
	
	private boolean isOnline(String isValid){
		return isValid.equals("Y")?true:false;
	}
	
	private List<Long> getTagIdList(String ids){
		List<Long> list=new ArrayList<Long>();
		if(ids!=null&&!"".equals(ids)){
			String[] array=ids.split(",");
			if(array!=null&&!"".equals(array)){
				for(int i=0;i<array.length;i++){
					if(array[i]!=null&&!"".equals(array[i])){
						list.add(new Long(array[i]));
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 是否是门票类产品
	 * @param productType 产品类型
	 * @return 是否是门票类产品
	 */
	private boolean isTicketProduct(String productType) {
        return Constant.PRODUCT_TYPE.TICKET.name().equals(productType);
    }
	
	/**
	 * 是否是线路类产品
	 * @param productType 产品类型
	 * @return 是否是线路类产品
	 */
	private boolean isRouteProduct(final String productType) {
		return Constant.PRODUCT_TYPE.ROUTE.name().equals(productType);
	}
	
	
	
	
	private Long getFromPlaceId(List<ProductProductPlace> productProductPlaceList) {
        return productProductPlaceList.get(0).getFromPlaceId();
    }
    private String getToPlaceIdStr(List<ProductProductPlace> productProductPlaceList) {
        StringBuffer sb = new StringBuffer();
        for (ProductProductPlace productProductPlace : productProductPlaceList) {
        	sb.append("'" + productProductPlace.getPlaceId() + "',");
        		
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    private String getToPlaceIdStr(String departArea,String routeStandard) {
	    if(!StringUtil.isEmptyString(departArea)){
	    	StringBuffer sb = new StringBuffer("");
		    String[] departAreas = departArea.split(",");
		    if(departAreas!=null&&departAreas.length>0){
		    	for (String area : departAreas) {
		    		if(StringUtils.isNotBlank(area))
		    			area=area.trim();
			        sb.append("'" + area + "',");
			    }
		    }
		    if ("PROD_ROUTE_ROUTE_STANDARD_6".equals(routeStandard)) {// 邮轮游
		        sb.append("'SHIP',");
		    }
		    if ("PROD_ROUTE_ROUTE_STANDARD_5".equals(routeStandard)) {// 海岛游
		        sb.append("'ISLAND',");
		    }
		    sb.deleteCharAt(sb.length() - 1);
		    return sb.toString();
	    }
	    return null;
	    
    }

	public void insertIntoCorrespondingContainers(
			ContainerProductVO containerProductVO, boolean needExistCheck) {
		List<Long> containerIdListAfter = containerProductVO.getContainerIdListAfter();
        if (containerIdListAfter != null&&containerIdListAfter.size()>0) {
            for (Long containerId : containerIdListAfter) {
                if (needExistCheck) {
                    if (!isProductAlreadyExist(containerId, containerProductVO.getProductId())) {
                        prodContainerProductDAO.insertContainerProduct(containerId, containerProductVO.getProductId(), containerProductVO.getSeq());
                    }
                } else {
                    prodContainerProductDAO.insertContainerProduct(containerId, containerProductVO.getProductId(), containerProductVO.getSeq());
                }
            }
        }
	}

	public void deleteContainerProduct(Long containerId, Long productId) {
		prodContainerProductDAO.deleteContainerProduct(containerId, productId);
	}


	private void insertDataByTag(List<Long> productIdList,Long tagId){
		if(productIdList!=null&&productIdList.size()>0){
			for (Long productId : productIdList) {
				if (!isProductAlreadyExist(tagId, productId)) {
					prodContainerProductDAO.insertContainerProduct(tagId, productId, 0L);
	            }
			}
		}
	}
	public void deleteFromCorrespondingContainers(
			ContainerProductVO containerProductVO) {
		List<Long> containerIdListBefore = containerProductVO.getContainerIdListBefore();
        List<Long> containerIdListAfter = containerProductVO.getContainerIdListAfter();
        if (containerIdListBefore != null && containerIdListAfter != null) {
            for (Long containerId : containerIdListBefore) {
                if (!containerIdListAfter.contains(containerId)) {
                    prodContainerProductDAO.deleteContainerProduct(containerId, containerProductVO.getProductId());
                }
            }
        }
		
	}
	public void insertTagContainerProduct(Long productId, Long tagId) {
        if (tagId.equals(Constant.TAG_ID_ON_SALE)) {
            if (!isProductAlreadyExist(Constant.CONTAINER_ID_ON_SALE, productId)) {
                prodContainerProductDAO.insertContainerProduct(Constant.CONTAINER_ID_ON_SALE, productId, 0L);
            }
        }
        if (tagId.equals(Constant.TAG_ID_NEW_ARRIVAL)) {
            if (!isProductAlreadyExist(Constant.CONTAINER_ID_NEW_ARRIVAL, productId)) {
                prodContainerProductDAO.insertContainerProduct(Constant.CONTAINER_ID_NEW_ARRIVAL, productId, 0L);
            }
        }
    }
	private boolean isProductAlreadyExist(Long containerId, Long productId) {
        return prodContainerProductDAO.queryContainerProductCount(containerId, productId) > 0;
    }
	public void deleteNoUsedContainerProducts(List<Long> usedContainerIdList,
			Long productId) {
		prodContainerProductDAO.deleteNoUsedContainerProducts(usedContainerIdList, productId);
	}
	
	
	public int deleteContainer(Long id) {
		return prodContainerDAO.deleteContainer(id);
	}
    public List<ProdContainer> getContainers(String containerCode, Long fromPlaceId, String destId) {
        return prodContainerDAO.selectToPlaces(containerCode, fromPlaceId, destId, false);
    }

    public List<ProdContainer> getContainersMore(String containerCode, Long fromPlaceId) {
        return prodContainerDAO.selectToPlacesMore(containerCode, fromPlaceId, null, null, false);
    }
    
    public void saveContainers(List<ProdContainer> insertedProdContainers, List<ProdContainer> modifiedProdContainers) {
        for (ProdContainer prodContainer : insertedProdContainers) {
            prodContainerDAO.insertContainer(prodContainer);
        }
        for (ProdContainer prodContainer : modifiedProdContainers) {
            prodContainerDAO.updateContainer(prodContainer);
        }
    }

	@Override
	public List<ProdContainerFromPlace> getFromPlaces(String containerCode) {
		return prodContainerFromPlaceDAO.selectFromPlaces(containerCode, true);
	}
	
	public List<ProdContainerFromPlace> getFromPlaces() {
		return prodContainerFromPlaceDAO.selectFromPlaces(null, false);
	}

	public void deleteFromPlace(Long containerFromPlaceId) {
		 prodContainerFromPlaceDAO.deleteFromPlace(containerFromPlaceId);
	}

	@Override
	public void saveFromPlaces(List<ProdContainerFromPlace> insertedFromPlaces,
			List<ProdContainerFromPlace> modifiedFromPlaces) {
		for (ProdContainerFromPlace fromPlace : insertedFromPlaces) {
            prodContainerFromPlaceDAO.insertFromPlace(fromPlace);
        }
        for (ProdContainerFromPlace fromPlace : modifiedFromPlaces) {
            prodContainerFromPlaceDAO.updateFromPlace(fromPlace);
        }
		
	}

	public List<ProdContainer> getContainerNameCodePairs() {
		return prodContainerDAO.getContainerNameCodePairs();
	}

	@Override
	public List<ProdContainer> getFromPlacesByContainerCode(String containerCode) {
		return prodContainerDAO.getFromPlacesByContainerCode(containerCode);
	}

	@Override
	public boolean isFromPlaceEmpty(String containerCode) {
		Long count=prodContainerDAO.isFromPlaceEmpty(containerCode);
		if(count!=null&&count.longValue()>0)
			return false;
		else return true;
	}

	@Override
	public List<ProdContainer> getToPlacesByContainerCodeAndDestId(
			String containerCode, String destId) {
		return prodContainerDAO.getToPlacesByContainerCodeAndDestId(containerCode, destId);
	}

	@Override
	public List<ProdContainer> getToPlacesByContainerCodeAndFromPlaceId(
			String containerCode, String fromPlaceId) {
		return prodContainerDAO.getToPlacesByContainerCodeAndFromPlaceId(containerCode, fromPlaceId);
	}

	@Override
	public Long getContainerProductListCount(Map<String, Object> params) {
		
		return prodContainerProductDAO.getContainerProductListCount(params);
	}

	@Override
	public List<ProdContainerProduct> getContainerProductList(
			Map<String, Object> params) {
		return prodContainerProductDAO.getContainerProductList(params);
	}

	public int updateRecommendSeq(Long containerProductId, int recommendSeq, int oldRecommendSeq,String oprName) {
		ComLog log = new ComLog();
		log.setParentId(null);
		log.setParentType("CONTAINER_PRODUCT");
		log.setObjectType("CONTAINER_PRODUCT");
		log.setObjectId(containerProductId);
		log.setOperatorName(oprName);
		log.setLogType(Constant.COM_LOG_CONTAINER_PRODUCT_EVENT.SET_RECOMMEND_SEQ.name());
		log.setLogName("设置推荐值");
		log.setContent("原值="+oldRecommendSeq+"  新值="+recommendSeq);
		comLogDAO.insert(log);
        return prodContainerProductDAO.setRecommendSeq(containerProductId, recommendSeq);
    }

	@Override
	public void containerProductShowOrHide(Long containerProductId,
			String isValid, String operatorName) {
		ComLog log = new ComLog();
		log.setParentId(null);
		log.setParentType("CONTAINER_PRODUCT");
		log.setObjectType("CONTAINER_PRODUCT");
		log.setObjectId(containerProductId);
		log.setOperatorName(operatorName);
		log.setLogType(Constant.COM_LOG_CONTAINER_PRODUCT_EVENT.RESTORE_RECOMMEND_SEQ.name());
		
		String oldStr="";
		if(isValid.equals("Y")){
			log.setLogName("设置显示");
			oldStr="N";
		}else{
			log.setLogName("设置隐藏");
			oldStr="Y";
		}
		log.setContent("原值="+oldStr+"  新值="+isValid);
		comLogDAO.insert(log);
		prodContainerProductDAO.containerProductShowOrHide(containerProductId, isValid);
	}


	public List<ContainerPlaceBean> getProdContainerToPlacesFromParent(
			String containerCode, Long fromPlaceId, String destId) {
		List<ContainerPlaceBean> tabPlaceList = new ArrayList<ContainerPlaceBean>();
		List<ProdContainer> provincePlaces = prodContainerDAO.selectToPlaces(containerCode, fromPlaceId, destId, true);
		for (ProdContainer provincePlace : provincePlaces) {
			List<ProdContainer> cityPlaces = prodContainerDAO.selectToPlaces(containerCode, fromPlaceId, provincePlace.getToPlaceId(),true);
			ContainerPlaceBean placeBean = new ContainerPlaceBean();
			placeBean.setProdContainer(provincePlace);
			placeBean.setProdContainerList(cityPlaces);
			tabPlaceList.add(placeBean);
		}
		return tabPlaceList;
	}

	public Map<String, Object> buildTabPlaceListData(String containerCode,
			Long fromPlaceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProdContainer> tabPlaceListMore = prodContainerDAO.selectToPlacesMore(containerCode, fromPlaceId, null, null, true);
		map.put("tabPlaceListMore", tabPlaceListMore);
		List<String> zoneNames = prodContainerDAO.selectZoneNames(containerCode, fromPlaceId);
		List<ContainerPlaceBean> tabPlaceListMoreZone = new ArrayList<ContainerPlaceBean>();
		for (String zoneName : zoneNames) {
			List<ProdContainer> zonePlaces = prodContainerDAO.selectToPlacesMore(containerCode, fromPlaceId, null, zoneName, true);
			ContainerPlaceBean placeBean = new ContainerPlaceBean();
			placeBean.setZoneName(zoneName);
			placeBean.setProdContainerList(zonePlaces);
			tabPlaceListMoreZone.add(placeBean);
		}
		map.put("tabPlaceListMoreZone", tabPlaceListMoreZone);
		return map;
	}
	
	public ProdContainer getToPlace(String containerCode, String ipLocationId,String destId) {
		return prodContainerDAO.selectToPlace(containerCode, ipLocationId, destId, true);
	}
    
    /**
     * 设置推荐值
     */
    public int setRecommendSeq(Long containerProductId, int recommendSeq, int oldRecommendSeq, String operatorName) {
    	ComLog log = new ComLog();
		log.setParentId(null);
		log.setParentType("CONTAINER_PRODUCT");
		log.setObjectType("CONTAINER_PRODUCT");
		log.setObjectId(containerProductId);
		log.setOperatorName(operatorName);
		log.setLogType(Constant.COM_LOG_CONTAINER_PRODUCT_EVENT.RESTORE_RECOMMEND_SEQ.name());
		log.setLogName("设置推荐值");
		log.setContent("原值为:" + oldRecommendSeq + " 修改为:" + recommendSeq);
		comLogDAO.insert(log);
        return prodContainerProductDAO.setRecommendSeq(containerProductId, recommendSeq);
    }
    
    public void deleteTagContainerProduct(Long productId, Long tagId) {
        if (tagId.equals(Constant.TAG_ID_ON_SALE)) {
            prodContainerProductDAO.deleteContainerProduct(Constant.CONTAINER_ID_ON_SALE, productId);
        }
        if (tagId.equals(Constant.TAG_ID_NEW_ARRIVAL)) {
            prodContainerProductDAO.deleteContainerProduct(Constant.CONTAINER_ID_NEW_ARRIVAL, productId);
        }
    }
    
    @Override
    public List<Long> queryProductIdList() {
		return productSearchInfoDAO.queryProductIdListByTagId(new HashMap<String, Object>());
	}
    
    @Override
	public void batchInsertTagContainerProduct(Long tagId) {
		if(tagId!=null){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tagId", tagId);
			param.put("valid", "Y");
			List<Long> productIdList = productSearchInfoDAO.queryProductIdListByTagId(param);
			if(tagId.equals(Constant.TAG_ID_ON_SALE)){
				insertDataByTag(productIdList,Constant.CONTAINER_ID_ON_SALE);
			}
			if(tagId.equals(Constant.TAG_ID_NEW_ARRIVAL)){
				insertDataByTag(productIdList,Constant.CONTAINER_ID_NEW_ARRIVAL);
			}
		}
	}    

}
