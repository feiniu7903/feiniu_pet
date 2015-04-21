package com.lvmama.pet.prod.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.pet.po.prod.ProdProductHead;
import com.lvmama.comm.pet.service.prod.ProdProductHeadService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.prod.dao.ProdProductRelationDAO;

/**
 * pet系统里面的prod_product_relation表逻辑关系的实现，在相当长的时间段里面，pet系统和super_order系统中都会存在
 * 同一接口的两个实现。存在的原因是很多附加产品还在super的数据库中，没法迁移。
 * 此实现类的声明会叫做petProdProductRelationService，不是惯例的prodProductRelationService，请注意！
 * @author Administrator
 *
 */
public class ProdProductRelationServiceImpl implements ProdProductRelationService {
	/**
	 * 日志输出器
	 */
	private final static Log LOG = LogFactory.getLog(ProdProductRelationServiceImpl.class);
	/**
	 * 产品关联关系的数据库操作类
	 */
	@Autowired
	private ProdProductRelationDAO prodProductRelationDAO;
	/**
	 * 产品头的远程服务
	 */
	@Autowired
	private ProdProductHeadService prodProductHeadService;
	/**
	 * 操作日志逻辑服务
	 */
	@Autowired
	private ComLogService comLogService;

	@Override
	public ResultHandle updateSaleNumType(Long relationId, String saleNumType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProdProductRelation addRelation(Long productId,
			ProdProductBranch branch, String operatorName) {
		throw new UnsupportedOperationException("此方法已经被废弃");
	}
	
	@Override
	public ProdProductRelation addRelation(final Long mainProductId, final Long additionalProductId, final String operatorName) {
		if (null == mainProductId || null == additionalProductId) {
			return null;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", mainProductId);
		param.put("relatProductId", additionalProductId);
		if (prodProductRelationDAO.count(param) > 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("关联关系已经存在，不需要重复添加!");
			}
			return null;
		}
		
		ProdProductHead head = prodProductHeadService.getProdProductHeadByProductId(additionalProductId);
		if (null == head) {
			return null;
		}
		
		ProdProductRelation relation=new ProdProductRelation();
		relation.setProductId(mainProductId);
		relation.setRelatProductId(additionalProductId);
		if (Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(head.getSubProductType())) {
			relation.setSaleNumType(Constant.SALE_NUMBER_TYPE.ANY.name());
		} else {
			relation.setSaleNumType(Constant.SALE_NUMBER_TYPE.OPT.name());
		}
		relation = prodProductRelationDAO.insert(relation);
		
		comLogService.insert("PROD_PRODUCT_RELATION", mainProductId, additionalProductId,operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertProdRelation.name(),
				"添加附加产品", "产品标示[ "+ additionalProductId +" ]", "PROD_PRODUCT");
		
		return relation;
	}

	@Override		
	public ProdProductRelation getProdRelationDetail(Long pk) {	
		ProdProductRelation relation = prodProductRelationDAO.queryProdProductRelationByPK(pk);
		if (null != relation) {
			relation.setRelatedProduct(prodProductHeadService.getRelatedProduct(relation.getRelatProductId()));
		}
		return relation;
	}

	@Override
	public void deleteRelation(Long relationId, String operatorName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProdProductRelation> getRelatProduct(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdProductRelation> getRelationProductsByProductId(
			Long productId, Date visitTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProdProductRelation getProdRelation(Long productId,
			Long relationBranchId) {
		// TODO Auto-generated method stub
		return null;
	}

}
