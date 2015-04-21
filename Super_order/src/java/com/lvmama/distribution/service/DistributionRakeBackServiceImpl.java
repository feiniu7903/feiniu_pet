package com.lvmama.distribution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;
import com.lvmama.comm.bee.po.distribution.DistributionRakeBack;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.distribution.DistributionRakeBackService;
import com.lvmama.distribution.dao.DistributionProductCategoryDAO;
import com.lvmama.distribution.dao.DistributionRakeBackDAO;
import com.lvmama.distribution.dao.DistributorInfoDAO;
import com.lvmama.order.dao.OrderItemProdDAO;

public class DistributionRakeBackServiceImpl implements
		DistributionRakeBackService {
	private DistributionRakeBackDAO distributionRakeBackDAO;
	private DistributorInfoDAO distributorInfoDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private DistributionProductCategoryDAO distributionProductCategoryDAO;

	public void saveDistributionRakeBack(Long productBranchId,
			Long distributorInfoId, Long rakeBackRate) {
		DistributionRakeBack rake = new DistributionRakeBack();
		rake.setProductBranchId(productBranchId);
		rake.setDistributorInfoId(distributorInfoId);
		rake.setRakeBackRate(rakeBackRate);
		this.distributionRakeBackDAO.insert(rake);
	}
	
	public void save(DistributionRakeBack drb){
		if(drb.getDistributionProdRakebackId()==null){
			this.distributionRakeBackDAO.insert(drb);
		}else{
			this.distributionRakeBackDAO.update(drb);
		}
	}

	public DistributionRakeBack queryDistributionRakeBack(Long productBranchId,
			Long distributorInfoId) {
		return distributionRakeBackDAO.selectByParams(productBranchId,
				distributorInfoId);
	}

	public void updateRakeBack(Long productBranchId, Long distributorInfoId,
			Long rakeBackRate) {
		distributionRakeBackDAO.updateRakeBackRateByParams(productBranchId,
				distributorInfoId, rakeBackRate);
	}
	
	public Float getRakeBackRatebyOrderId(Long orderId) {
		DistributorInfo info = distributorInfoDAO.selectByOrderId(orderId);
		if (null != info) {
			System.out.println("******************distributorInfoId:" + info.getDistributorInfoId());
			Long distributorInfoId = info.getDistributorInfoId();
			Long productBranchId = 0L;
			String productType = "";
			String subProductType = "";
			List<OrdOrderItemProd> ordItemList = orderItemProdDAO.selectByOrderId(orderId);
			for (OrdOrderItemProd ordItem : ordItemList) {
				if ("true".equals(ordItem.getIsDefault())) {
					productBranchId = ordItem.getProdBranchId();
					productType = ordItem.getProductType();
					subProductType = ordItem.getSubProductType();
					break;
				}
			}
			System.out.println("******************productBranchId:" + productBranchId);
			System.out.println("******************productType:" + productType);
			System.out.println("******************subProductType:" + subProductType);
			// 获取手动设置的返佣点
			DistributionRakeBack rakeBack = distributionRakeBackDAO.selectByParams(productBranchId, distributorInfoId);
			if (null != rakeBack) {
				Long rakeBackRate = rakeBack.getRakeBackRate();
				return rakeBackRate!=null?rakeBackRate.floatValue():null;
			} else {
				// 获取统一返佣点
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("distributorInfoId", distributorInfoId);
				params.put("productType", productType);
				if ("TICKET".equals(productType)) {
					params.put("payOnline", "true");
				} else {
					params.put("subProductType", subProductType);
				}
				System.out.println("******************params:" + params.toString());
				List<DistributionProductCategory> cateList = distributionProductCategoryDAO.selectByParams(params);
				if (null != cateList && cateList.size() > 0) {
					return cateList.get(0).getDiscountRateY();
				}
			}
		}
		return null;
	}

	public void setDistributionRakeBackDAO(
			DistributionRakeBackDAO distributionRakeBackDAO) {
		this.distributionRakeBackDAO = distributionRakeBackDAO;
	}
	
	public void setDistributorInfoDAO(DistributorInfoDAO distributorInfoDAO) {
		this.distributorInfoDAO = distributorInfoDAO;
	}
	
	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void setDistributionProductCategoryDAO(
			DistributionProductCategoryDAO distributionProductCategoryDAO) {
		this.distributionProductCategoryDAO = distributionProductCategoryDAO;
	}
	
}
