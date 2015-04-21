package com.lvmama.prd.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductRelationDAO;
import com.lvmama.prd.logic.ProdProductBranchLogic;


public class ProdProductRelationServiceImpl implements ProdProductRelationService {
	private ProdProductRelationDAO prodProductRelationDAO;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private ComLogDAO comLogDAO;
	private ProdProductBranchItemDAO prodProductBranchItemDAO; 

	public void setProdProductRelationDAO(
			ProdProductRelationDAO prodProductRelationDAO) {
		this.prodProductRelationDAO = prodProductRelationDAO;
	}

	@Override
	public ResultHandle updateSaleNumType(Long relationId, String saleNumType) {
		ResultHandle handle=new ResultHandle();
		if(prodProductRelationDAO.selectByPrimaryKey(relationId)==null){
			handle.setMsg("操作的产品不存在");
			return handle;
		}
		ProdProductRelation record = new ProdProductRelation();
		record.setRelationId(relationId);
		record.setSaleNumType(saleNumType);
		prodProductRelationDAO.updateByPrimaryKeySelective(record);
		return handle;
	}


	@Override
	public ProdProductRelation addRelation(Long productId,
			ProdProductBranch branch,String operatorName) {
		ProdProductRelation relation=getProdRelation(productId, branch.getProdBranchId());
		Assert.isNull(relation,"类别不可以多次打包在一个产品");
		
		//判断是否已经有关联了采购产品.
		//现在去掉了，原因是现在关联附加产品是与产品在一起，而采购产品又是与类别在一起.
		//关联的销售产品不能包含是附加产品的二维码产品，该判断现在去掉。以后打包的产品只可以是其他产品类型当中的保险，签证等
		
		relation=new ProdProductRelation();
		relation.setProductId(productId);
		relation.setRelatProductId(branch.getProductId());
		relation.setProdBranchId(branch.getProdBranchId());
		
		ProdProduct relationProduct=prodProductDAO.selectByPrimaryKey(branch.getProductId());
		if (StringUtils.equals(relationProduct.getProductType(),
				Constant.PRODUCT_TYPE.OTHER.name())
				&& StringUtils.equals(relationProduct.getSubProductType(),
						Constant.SUB_PRODUCT_TYPE.INSURANCE.name())) {
			relation.setSaleNumType(Constant.SALE_NUMBER_TYPE.ANY.name());
		}else{
			relation.setSaleNumType(Constant.SALE_NUMBER_TYPE.OPT.name());
		}		
		relation.setRelationId(prodProductRelationDAO.insert(relation));
		
		
		comLogDAO.insert("PROD_PRODUCT_RELATION", productId, relation.getRelationId(),operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertProdRelation.name(),
				"添加附加产品", "产品名称[ "+relationProduct.getProductName()+" ]", "PROD_PRODUCT");
		return relation;
	}
	
	@Override
	public ProdProductRelation addRelation(Long mainProductId, Long additionalProductId, String operatorName) {
		throw new UnsupportedOperationException("此方法只在pet系统中实现，super_order中请使用其他方法");
	}
	
	public ProdProductRelation getProdRelation(Long productId,Long relationBranchId){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("prodBranchId", relationBranchId);
		List<ProdProductRelation> list=prodProductRelationDAO.selectProdRelationByParam(map);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public void deleteRelation(Long relationId,String operatorName) {
		ProdProductRelation relation=prodProductRelationDAO.selectByPrimaryKey(relationId);
		Assert.notNull(relation,"附加产品不存在");
		
		ProdProduct relationProduct = prodProductDAO.selectByPrimaryKey(relation.getRelatProductId());
		
		prodProductRelationDAO.deleteByPrimaryKey(relationId);
		
		comLogDAO.insert("PROD_PRODUCT_RELATION", relation.getProductId(), relationId,operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.deleteProdRelation.name(),
				"删除附加产品", MessageFormat.format("附件产品名称为[ {0} ]", relationProduct.getProductName()), "PROD_PRODUCT");
	}
 
	@Override
	public List<ProdProductRelation> getRelatProduct(Long productId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("productId", productId);
		List<ProdProductRelation> list=prodProductRelationDAO.selectProdRelationByParam(map);
		if(CollectionUtils.isNotEmpty(list)){
			//初始化产品信息
			for(ProdProductRelation r:list){				
				initRelation(r);				
			}
		}
		return list;
	}
	
	@Override
	public ProdProductRelation getProdRelationDetail(Long pk) {
		ProdProductRelation r=prodProductRelationDAO.selectByPrimaryKey(pk);
		initRelation(r);
		return r;
	}
	
	private void initRelation(ProdProductRelation r){
		if(r.getProdBranchId()!=null){
			r.setBranch(prodProductBranchDAO.selectByPrimaryKey(r.getProdBranchId()));
		}
		if(r.getRelatProductId()!=null){
			r.setRelationProduct(prodProductDAO.selectProductDetailByPrimaryKey(r.getRelatProductId()));
		}
	}

	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	/**
	 * @param prodProductDAO the prodProductDAO to set
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setProdProductBranchItemDAO(ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}



	@Override
	public List<ProdProductRelation> getRelationProductsByProductId(Long productId, Date visitTime) {
		List<ProdProductRelation> list = new ArrayList<ProdProductRelation>(); 
		Map<String,Object> map=new HashMap<String, Object>(); 
		map.put("productId", productId); 
		List<ProdProductRelation> relatProductlist = prodProductRelationDAO.selectProdRelationByParam(map); 
		for (ProdProductRelation relat : relatProductlist) { 
			if (prodProductBranchItemDAO.countProductItem(relat.getProdBranchId())>0){// 如果产品进行了打包才加入到有效的相关产品列表中 
				ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(relat.getProdBranchId()); 				
				if(branch!=null){ 
					branch=prodProductBranchLogic.fill(branch, visitTime);
					if(branch!=null){
//						ProdProduct relationProduct = prodProductDAO.selectByPrimaryKey(branch.getProductId());
//						TimePrice price = productTimePriceLogic.calcProdTimePrice(branch.getProdBranchId(),visitTime); 
//						if (price != null) { 
//						branch.setSellPrice(price.getPrice()); 
//						branch.setMarketPrice(price.getMarketPrice()); 
//						branch.setStock(price.getDayStock()); 
						relat.setBranch(branch); 
						relat.setRelationProduct(branch.getProdProduct());						
						list.add(relat); 
					}
				}
			}
		} 
		return list;
	}
	private ProdProductBranchLogic prodProductBranchLogic;
	/**
	 * @param prodProductBranchLogic the prodProductBranchLogic to set
	 */
	public void setProdProductBranchLogic(
			ProdProductBranchLogic prodProductBranchLogic) {
		this.prodProductBranchLogic = prodProductBranchLogic;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	
}
