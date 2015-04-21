package com.lvmama.pet.prod.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.prod.ProdProductHead;
import com.lvmama.comm.pet.service.prod.ProdProductHeadService;
import com.lvmama.comm.pet.vo.product.RelatedProduct;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.prod.dao.ProdInsuranceDAO;
import com.lvmama.pet.prod.dao.ProdProductHeadDAO;

public class ProdProductHeadServiceImpl implements ProdProductHeadService {
	@Autowired
	private ProdProductHeadDAO prodProductHeadDAO;
	@Autowired
	private ProdInsuranceDAO prodInsuranceDAO;

	@Override
	public Long generateProductId() {
		return prodProductHeadDAO.generateProductId();
	}
	
	@Override
	public ProdProductHead getProdProductHeadByProductId(final Long productId) {
		if (null != productId) {
			return prodProductHeadDAO.getProdProductHeadByProductId(productId);
		} else {
			return null;
		}
	}


	@Override
	public ProdProductHead save(final ProdProductHead head) {
		if (null != head) {
			prodProductHeadDAO.save(head);
		}
		return head;
	}

	@Override
	public ProdProductHead update(final ProdProductHead head) {
		if (null != head) {
			prodProductHeadDAO.update(head);
		}
		return head;
	}

	@Override
	public List<ProdProductHead> query(final Map<String, Object> param) {
		return prodProductHeadDAO.query(param);
	}


	@Override
	public RelatedProduct getRelatedProduct(final Long productId) {
		ProdProductHead head = this.getProdProductHeadByProductId(productId);
		if (null != head) {
			if (Constant.PRODUCT_BIZ_TYPE.PET.name().equals(head.getBizType()) 
					&& Constant.PRODUCT_TYPE.OTHER.name().equals(head.getProductType())
					&& Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(head.getSubProductType())) {
				return (RelatedProduct) prodInsuranceDAO.queryProdInsuranceByPK(productId);
			}
		}
		return null;
	}
	
}
