package com.lvmama.comm.pet.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.po.prod.ProdContainerProduct;
import com.lvmama.comm.pet.vo.ContainerPlaceBean;
import com.lvmama.comm.vo.ContainerProductVO;

public interface ProdContainerProductService {
	/**
	 * 删除不可用的产品
	 */
	void deleteInvalidContainerProducts();
	/**
	 * 根据containnerId和tagId删除数据
	 * @param containerId
	 * @param tagId
	 */
	void deleteContainerProductsByTagId(Long containerId,Long tagId);
	/**
	 * 查询符合条件的产品id列表
	 * @return
	 */
	List<Long> queryProductIdList();
	/**
	 * 根据productId创建ContainerProductVO
	 * @param productId
	 * @return
	 */
	ContainerProductVO createContainerProductVO(Long productId);
	/**
	 * 根据productId删除不应该存在的容器数据
	 * @param usedContainerIdList
	 * @param productId
	 */
	void deleteNoUsedContainerProducts(List<Long> usedContainerIdList, Long productId);
	void insertIntoCorrespondingContainers(ContainerProductVO containerProductVO, boolean needExistCheck);
	void batchInsertTagContainerProduct(Long tagId);
	/**
	 * 删除容器数据，根据containerId和productId
	 * @param containerId
	 * @param productId
	 * @return
	 */
	void deleteContainerProduct(Long containerId, Long productId);
	
	void deleteFromCorrespondingContainers(ContainerProductVO containerProductVO);
	
	void insertTagContainerProduct(Long productId, Long tagId);
	/**
	 * 根据容器id删除
	 * @param id
	 * @return
	 */
	int deleteContainer(Long id);
    List<ProdContainer> getContainers(String containerCode, Long fromPlaceId, String destId);
    List<ProdContainer> getContainersMore(String containerCode, Long fromPlaceId);
    void saveContainers(List<ProdContainer> insertedProdContainers, List<ProdContainer> modifiedProdContainers);
    
    List<ProdContainerFromPlace> getFromPlaces(String containerCode);
    
    /**
     * 获取所有的出发地
     * @return
     */
    List<ProdContainerFromPlace> getFromPlaces();
    /**
     * 根据containerFromPlaceId删除
     * @param containerFromPlaceId
     */
    void deleteFromPlace(Long containerFromPlaceId);
    /**
     * 保存出发地
     * @param insertedFromPlaces
     * @param modifiedFromPlaces
     */
    void saveFromPlaces(List<ProdContainerFromPlace> insertedFromPlaces, List<ProdContainerFromPlace> modifiedFromPlaces);
    /**
     * 获取所有的容器名称和代码
     * @return
     */
    List<ProdContainer> getContainerNameCodePairs();
    /**
     * 根据containerCode获取该容器出发地
     * @param containerCode
     * @return
     */
    List<ProdContainer> getFromPlacesByContainerCode(String containerCode);
    
    boolean isFromPlaceEmpty(String containerCode);
    /**
     * 根据containerCode和destId获取容器目的地数据
     * @param containerCode
     * @param destId
     * @return
     */
    List<ProdContainer> getToPlacesByContainerCodeAndDestId(String containerCode, String destId);
    /**
     * 根据containerCode和fromPlaceId获取容器目的地数据
     * @param containerCode
     * @param fromPlaceId
     * @return
     */
    List<ProdContainer> getToPlacesByContainerCodeAndFromPlaceId(String containerCode, String fromPlaceId);
    
    Long getContainerProductListCount(Map<String,Object> params);
    
    List<ProdContainerProduct> getContainerProductList(Map<String,Object> params);
    /**
     * 设置推荐排序值
     * @param containerProductId
     * @param recommendSeq
     * @param oldRecommendSeq
     * @return
     */
    int updateRecommendSeq(Long containerProductId, int recommendSeq, int oldRecommendSeq,String oprName);
    void  containerProductShowOrHide(Long containerProductId, String isValid, String operatorName);
    /**
     * 获取容器目的地通过上级目的地节点
     * @param containerCode
     * @param fromPlaceId
     * @param destId
     * @return
     */
    List<ContainerPlaceBean> getProdContainerToPlacesFromParent(String containerCode, Long fromPlaceId, String destId);
    /**
     * 构建tab页签数据
     * @param containerCode
     * @param fromPlaceId
     * @return
     */
    Map<String, Object> buildTabPlaceListData(String containerCode, Long fromPlaceId);
    
    ProdContainer getToPlace(String containerCode, String ipLocationId,String destId);
    	
	int setRecommendSeq(Long containerProductId, int recommendSeq, int oldRecommendSeq,String operatorName);

	void deleteTagContainerProduct(Long productId, Long tagId);
    
}
