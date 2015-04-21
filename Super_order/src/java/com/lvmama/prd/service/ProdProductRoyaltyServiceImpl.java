package com.lvmama.prd.service;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProductRoyalty;
import com.lvmama.comm.bee.po.prod.ProdProductRoyalty.ROYALTY_TYPE;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.prd.dao.ProdProductRoyaltyDAO;

/**
 * 产品分润服务实现类
 * 
 * @author taiqichao
 *
 */
public class ProdProductRoyaltyServiceImpl implements ProdProductRoyaltyService {
	
	private ProdProductRoyaltyDAO prodProductRoyaltyDAO;

	public List<ProdProductRoyalty> getProdProductRoyaltys(Long productId) {
		return prodProductRoyaltyDAO.selectAllByProductId(productId);
	}

	public ProdProductRoyaltyDAO getProdProductRoyaltyDAO() {
		return prodProductRoyaltyDAO;
	}

	public void setProdProductRoyaltyDAO(ProdProductRoyaltyDAO prodProductRoyaltyDAO) {
		this.prodProductRoyaltyDAO = prodProductRoyaltyDAO;
	}

	public String getRoyaltysParamers(Long productId,OrdOrder order) {
		
		List<ProdProductRoyalty> royaltys=this.getProdProductRoyaltys(productId);
		
		if(null!=royaltys&&royaltys.size()>0){
			//收款方Email1^金额1^备注1|收款方Email2^金额2^备注2
			StringBuilder paramersBuilder=new StringBuilder();
			for (ProdProductRoyalty prodProductRoyalty : royaltys) {
				//按照订单金额支付分润
				if(ROYALTY_TYPE.ORDER_PAY_AMOUNT.name().equals(prodProductRoyalty.getRoyaltyType())){
					//分润金额=分润百分比*应付总金额
					Float amount=prodProductRoyalty.getPercentage()*order.getOughtPayYuanFloat();
					paramersBuilder.append(prodProductRoyalty.getPayeeAccount())
					.append("^").append(amount).append("^").append("lvmama");
					paramersBuilder.append("|");
				}
			}
			return paramersBuilder.toString();
		}
		return null;
	}

	public List<Long> getRoyaltyProductIds() {
		return prodProductRoyaltyDAO.selectAllProdProductIds();
	}

}
