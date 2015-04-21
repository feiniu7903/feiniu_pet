package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.prod.ProdProductTag;

 public interface ProdProductTagService {
	 /**
	  * 删除过了有效期的产品标签
	  * @param params
	  * @return
	  */
	 public void deleteProdProductTagTimeOut();
	 List<ProdProduct> queryProductByTagAndProductType(Map<String,Object> params);
	 List<ProdProductTag> selectProdProductByParams(ProdProductTag prodProductTag);
	 String getConflictsTagMsg(Long tagGroupId, List<ProdProductTag> prodProductTags);
	
	/**
	 * 删除拥有某标签的产品所关联的该标签的时效等信息
	 */
	 void delProductTag(Long productTagId);
	
	/**
	 * 删除拥有某标签的产品集所关联的该标签的时效等信息
	 */
	void delProductTags(List<ProdProductTag> prodProductTags);
	  
	/**
	 * 获取拥有某标签的产品所关联的该标签的时效等信息
	 */
	 List<ProdProductTag> selectByParams(Map<String,Object> params);
	
	/**
	 * 获取拥有某标签的产品所关联的该标签的时效信息的总记录数
	 */
	 Integer getProductTagsTotalCount(Long tagId);

	/**
	 * 通过产品id和标签id删除产品标签
	 * @param productId
	 * @param tagId
	 * @param tagGroupId 
	 */
	 void delProductTagByProductIdAndTagId(Long productId, Long tagId, long tagGroupId);

	 Integer queryProductByTagAndProductTypeCount(Map<String, Object> params);
	 /**
	  * 根据产品id和标签组id查询关联
	  * @param productId
	  * @param tagGroupId
	  * @return
	  */
	 List<ProdProductTag> selectProductTagByProductIdAndTagGroupId(Long productId,Long tagGroupId);
	 
	 /**
		 * 新增关联 产品
		 * @param tagGroupId 标签组
		 * @param placeTags 新增标签
		 * @param alreadyAddProductTags 产品关联的所有标签
		 * @return 返回alreadyAddProductTags
	 */
	 public List<ProdProductTag> addProgProductTags(Long tagGroupId, List<ProdProductTag> productTags, List<ProdProductTag> alreadyAddProductTags);
	 
	 /** 强制添加 产品 关联 */
	 public List<ProdProductTag> addProductTagAndDeleteConflictsProductTag(long tagGroupId, List<ProdProductTag> convertProductTag);
	 
	 /**
	  * 为产品自动打Tag
	  * @param prodTag
	  * @param productId
	  * @param productTags
	  * @return  List<ProdProductTag>
	  */
	 List<ProdProductTag> addSystemProgProductTags(List<ProdProductTag> prodProductTags,List<Long> productIds,String tagName);
	 
	 int deleteByTagId(Long tagId);
}