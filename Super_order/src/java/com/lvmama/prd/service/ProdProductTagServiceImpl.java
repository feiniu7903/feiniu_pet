package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductTagDAO;
import com.lvmama.prd.dao.ProdTagDAO;
import com.lvmama.prd.dao.ProdTagGroupDAO;

public class ProdProductTagServiceImpl implements ProdProductTagService {
	private ProdProductDAO prodProductDAO;
	private ProdTagGroupDAO prodTagGroupDAO;
	private ProdTagDAO prodTagDAO;
	private ProdProductTagDAO prodProductTagDAO;
	
	/** 产品 和 标签 添加时 处理 关联数据的 flag:1 */
	private int EXECUTE_FLAG_ADD = 1;

	/** 产品 和 标签 强制 添加时 处理 关联数据的 flag:2 
	 *  标签组是单选的，同一产品只能绑定一个组内标签
	 *  (特殊业务需求强制某产品可以绑多个该组标签) */
	private int EXECUTE_FLAG_ADD_FORCE = 2;

	/** 新增关联 产品 */
	public List<ProdProductTag> addProgProductTags(Long tagGroupId, List<ProdProductTag> productTags, List<ProdProductTag> alreadyAddProductTags) {
		return doProgProductTags(tagGroupId, productTags, alreadyAddProductTags, EXECUTE_FLAG_ADD);
	}

	/** 删除重复的 产品 标签关联 新增关联 */
	public List<ProdProductTag> addProductTagAndDeleteConflictsProductTag(long tagGroupId, List<ProdProductTag> prodProductTags) {
		return doProgProductTags(tagGroupId, prodProductTags, null, EXECUTE_FLAG_ADD_FORCE);
	}

	 
	/**
	 * 公用方法，处理 产品 和 标签的关联数据
	 * @param tagGroupId 标签组
	 * @param List<ProdProductTag> productTags 添加的新标签 项
	 * @param List<ProdProductTag> alreadyAddProductTags 部分已有的标签
	 * @param int executeFlag
	 * @return
	 */
	private List<ProdProductTag> doProgProductTags(Long tagGroupId, List<ProdProductTag> productTags, List<ProdProductTag> alreadyAddProductTags, int executeFlag) {
		ProdTagGroup prodTagGroup = this.prodTagGroupDAO.selectByPrimaryKey(tagGroupId);
		Map<Long, Long> dataMap = new HashMap<Long, Long>();
		//productTags新增的标签
		for (int i = 0; i < productTags.size(); i++) {
			ProdProductTag productTag = productTags.get(i);
			
			//系统中已有的标签
			List<ProdProductTag> productTagList = null;
			
			//标签组是单选的就同一时间下面只能有一个标签
			if (prodTagGroup.isSingleOption()) {
				productTagList = this.prodProductTagDAO.selectProductTagByProductIdAndTagGroupId(productTag.getProductId(), tagGroupId);

			} else {
				productTagList = prodProductTagDAO.selectProductTagByProductIdAndTagId(productTag);
			}
			
			if (executeFlag == EXECUTE_FLAG_ADD) {
				if (productTagList != null && !productTagList.isEmpty()) {// 重复添加
					ProdProduct prodProduct = prodProductDAO.selectByPrimaryKey(productTag.getProductId());
					productTag.setProductName(prodProduct.getProductName());
					prodProduct.setProductType(prodProduct.getProductType());
					// 查询的是页面选择的tag 用于显示
					
					ProdTag tempTag = prodTagDAO.selectByPrimaryKey(productTagList.get(0).getTagId());
					
//					ProdProductTag tempProdtag=prodProductTagDAO.selectByTagId(productTagList.get(0).getTagId()).get(0);
					
					productTag.setTagName(tempTag.getTagName());
//					productTag.setBeginTime(tempProdtag.getBeginTime());
//					productTag.setEndTime(tempProdtag.getEndTime());
					alreadyAddProductTags.add(productTag);
				} else {
					// 没有重复关联，则添加关联(产品与标签关系)
					prodProductTagDAO.insertSelective(productTag);
				}
			} else if (executeFlag == EXECUTE_FLAG_ADD_FORCE) {
				if (productTagList != null) {
					for (int j = 0; j < productTagList.size(); j++) {
						ProdProductTag pt = productTagList.get(j);
						prodProductTagDAO.deleteByPrimaryKey(pt);//删除已有的标签
					}
				}
				//新增标签
				prodProductTagDAO.insertSelective(productTag);
			}
			dataMap.put(productTag.getProductId(), productTag.getTagId());
		}
		//TODO 张振华再添加
//		containerProductService.insertTagContainerProduct(dataMap);
		
		return alreadyAddProductTags;
	}

	 public void deleteProdProductTagTimeOut(){
		 prodProductTagDAO.deleteProdProductTagTimeOut();
	 }
	 
	 public int deleteByTagId(Long tagId) {
		if(tagId != null){
			return prodProductTagDAO.deleteByTagId(tagId);
		}else{
			return 0;
		}
	 }
	    
	/**
	 * 通过产品类型、产品名称、标签名查询产品
	 */
	public List<ProdProduct> queryProductByTagAndProductType(Map<String, Object> params) {
		return prodProductDAO.queryProductByTagAndProductType(params);
	}

	public Integer queryProductByTagAndProductTypeCount(Map<String, Object> params) {
		return prodProductDAO.queryProductByTagAndProductTypeCount(params);
	};

	/**
	 * 通过产品id和标签id删除产品标签
	 */
	public void delProductTagByProductIdAndTagId(Long productId, Long tagId, long tagGroupId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("tagId", tagId);
		params.put("tagGroupId", tagGroupId);
		if(params != null && params.size() > 0){
			prodProductTagDAO.deleteByProductIdAndTagId(params);
		}
	}

	/**
	 * 添加一个产品标签，即对指定产品关联上标签时效信息
	 */
	public void addProductTag(List<ProdProductTag> prodProductTag) {
		for (int i = 0; i < prodProductTag.size(); i++) {
			prodProductTagDAO.insertSelective(prodProductTag.get(i));
		}
	}

	/**
	 * 删除拥有某标签的产品所关联的该标签的时效等信息
	 */
	public void delProductTag(Long productTagId) {
		if(productTagId != null){
			prodProductTagDAO.deleteByProductTagId(productTagId);
		}
	}

	/**
	 * 删除拥有某标签的产品集所关联的该标签的时效等信息，同时删除容器里的标签产品
	 */
	public void delProductTags(List<ProdProductTag> prodProductTags) {
		for (ProdProductTag prodProductTag : prodProductTags) {
			prodProductTag = prodProductTagDAO.selectByPrimaryKey(prodProductTag.getProductTagId());
			if (null != prodProductTag) {
				prodProductTagDAO.deleteProductTags(prodProductTag);
				//TODO 张振华
				//containerProductService.deleteTagContainerProduct(prodProductTag.getProductId(), prodProductTag.getTagId());
			}
		}
	}
    
	/**
	 * 获取拥有某标签的产品所关联的该标签的时效等信息
	 */
	public List<ProdProductTag> selectByParams(Map<String, Object> params) {
		List<ProdProductTag> prodProductTags = prodProductTagDAO.selectByParams(params);
		for (ProdProductTag tag : prodProductTags) {
			ProdProduct prodProduct = prodProductDAO.selectByPrimaryKey(tag.getProductId());
			tag.setProductName(prodProduct.getProductName());
			tag.setProductType(prodProduct.getZhProductType());
		}
		return prodProductTags;
	}

	/**
	 * 获取拥有某标签的产品所关联的该标签的时效信息的总记录数
	 */
	public Integer getProductTagsTotalCount(Long tagId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagId", tagId);
		return prodProductTagDAO.selectRowCount(params);
	}

	public String getConflictsTagMsg(Long tagGroupId, List<ProdProductTag> prodProductTags) {
		String msg = null;
		for (ProdProductTag prodProductTag : prodProductTags) {
			// 判断标签组是单选还是多选
			ProdTagGroup prodTagGroup = this.prodTagGroupDAO.selectByPrimaryKey(tagGroupId);
			if (prodTagGroup.isSingleOption()) {
				// 单选时需要删除同组标签
				List<ProdProductTag> prodTagList = this.prodProductTagDAO.selectProductTagByProductIdAndTagGroupId(prodProductTag.getProductId(), tagGroupId);
				if (prodTagList.size() > 0) {
					msg = "已有同组标签存在，请先删除，再添加";
					break;
				}
			} else {
				// 多选时需要删除，是否已添加
				ProdProductTag tag = new ProdProductTag();
				tag.setProductId(prodProductTag.getProductId());
				tag.setProductTagId(prodProductTag.getTagId());
				List<ProdProductTag> prodTagList = prodProductTagDAO.selectProdProductByParams(prodProductTag);
				if (prodTagList.size() > 0) {
					msg = "该标签已经选择，请先删除，再添加";
					break;
				}
			}
		}
		return msg;
	}
	
	/**
	 * 为产品自动打Tag
	 */
	@Override
	public List<ProdProductTag> addSystemProgProductTags(List<ProdProductTag> prodProductTags, List<Long> productIds, String tagName){
		ProdTag prodTag = prodTagDAO.selectByTagName(tagName);
		List<ProdProductTag> newProdProductTags = null;
		
		if (prodTag != null) {
			// 1.删除产品关联的TAG
			if (productIds != null && productIds.size() > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productIds", productIds);
				params.put("tagId", prodTag.getTagId());
				prodProductTagDAO.deleteByProductIdsAndTagId(params);
			}
			
			// 2.重新绑定相关产品的TAG
			if (prodProductTags != null && prodProductTags.size() > 0) {
				newProdProductTags = new ArrayList<ProdProductTag>();
				for (ProdProductTag productTag : prodProductTags) {
					productTag.setTagId(prodTag.getTagId());
					productTag = prodProductTagDAO.insertSelective(productTag);
					newProdProductTags.add(productTag);
				}
			}
		}
		
		return newProdProductTags;
	}
 
	public List<ProdProductTag> selectProdProductByParams(ProdProductTag prodProductTag) {
		return prodProductTagDAO.selectProdProductByParams(prodProductTag);
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setProdTagGroupDAO(ProdTagGroupDAO prodTagGroupDAO) {
		this.prodTagGroupDAO = prodTagGroupDAO;
	}

	public void setProdTagDAO(ProdTagDAO prodTagDAO) {
		this.prodTagDAO = prodTagDAO;
	}

	public ProdProductTagDAO getProdProductTagDAO() {
		return prodProductTagDAO;
	}

	public void setProdProductTagDAO(ProdProductTagDAO prodProductTagDAO) {
		this.prodProductTagDAO = prodProductTagDAO;
	}

	public List<ProdProductTag> selectProductTagByProductIdAndTagGroupId(Long productId, Long tagGroupId) {
		return this.prodProductTagDAO.selectProductTagByProductIdAndTagGroupId(productId, tagGroupId);
	}

 
}
