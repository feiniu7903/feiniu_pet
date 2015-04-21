package com.lvmama.tnt.prod.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.prod.vo.TntProdProduct;

@Repository("tntProdProductService")
public class TntProdProductServiceImpl extends
		InterfaceVersionService<TntProdProduct, ProdProductBranch> implements
		TntProdProductService {

	@Autowired
	private ProdProductBranchService prodProductBranchService;

	@Autowired
	private ProductHeadQueryService productServiceProxy;

	@Autowired
	private PageService pageService;

	@Override
	public TntProdProduct reserve(ProdProductBranch t) {
		TntProdProduct p = null;
		if (t != null) {
			p = new TntProdProduct();
			p.setBranchId(t.getProdBranchId());
			p.setBranchName(t.getBranchName());
			p.setBranchStatus(t.getOnline());
			ProdProduct pp = t.getProdProduct();
			if (pp != null) {
				p.setProductId(pp.getProductId());
				p.setProductName(pp.getProductName());
				p.setProductStatus(pp.getOnLine());
				p.setProductType(pp.getProductType());
				p.setPlaceName(pp.getPlaceName());
				p.setPlaceId(pp.getPlaceId());
				p.setPayType(pp.isPaymentToLvmama() ? TntConstant.PRODUCT_PAY_TYPE.TOLVMAMA
						.getValue() : TntConstant.PRODUCT_PAY_TYPE.TOSUPPLIER
						.getValue());
				p.setIsAperiodic(pp.getIsAperiodic());
			}
		}
		return p;
	}

	@Override
	public ProdProductBranch translate(TntProdProduct t) {
		ProdProductBranch p = null;
		if (t != null) {
			p = new ProdProductBranch();
			p.setBranchName(t.getBranchName());
			p.setProdBranchId(t.getBranchId());
			p.setProductId(t.getProductId());
			ProdProduct pp = new ProdProduct();
			pp.setProductName(t.getProductName());
			pp.setProductType(t.getProductType());
			pp.setPlaceId(t.getPlaceId());
			pp.setPlaceName(t.getPlaceName());
			pp.setIsAperiodic(t.getIsAperiodic());
			String payType = t.getPayType();
			if (payType != null && !payType.isEmpty()) {
				if (TntConstant.PRODUCT_PAY_TYPE.isPayToLvmama(payType)) {
					pp.setPayToLvmama("true");
					pp.setPayToSupplier("false");
				} else if (TntConstant.PRODUCT_PAY_TYPE
						.isPayToSupplier(payType)) {
					pp.setPayToLvmama("false");
					pp.setPayToSupplier("true");
				}
			} else {
				pp.setPayToLvmama(null);
			}
			p.setProdProduct(pp);
		}
		return p;
	}

	@Override
	public List<ProdProductBranch> selectList(Map<String, Object> map) {
		List<ProdProductBranch> list = prodProductBranchService
				.selectB2BProd(map);
		return list;
	}

	@Override
	protected Map<String, Object> toMap(ProdProductBranch t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productType", t.getProdProduct().getProductType());
		if (t.getProdBranchId() != null) {
			map.put("prodBranchId", t.getProdBranchId());
		}
		if (t.getBranchName() != null) {
			map.put("branchName", t.getBranchName());
		}
		if (t.getProductId() != null) {
			map.put("productId", t.getProductId());
		}
		ProdProduct pp = t.getProdProduct();
		if (pp != null) {
			if (pp.getProductName() != null)
				map.put("productName", pp.getProductName());
			if (pp.getPayToLvmama() != null)
				map.put("paytolvmama", pp.getPayToLvmama());
		}
		return map;
	}

	@Override
	public long count(Map<String, Object> map) {
		return prodProductBranchService.selectB2BProdCount(map);
	}

	@Override
	public TntProdProduct getByBranchId(Long branchId) {
		TntProdProduct t = new TntProdProduct();
		t.setBranchId(branchId);
		t = get(t);
		return t;
	}

	@Override
	public ProdProductBranch selectOne(Map<String, Object> map) {
		return prodProductBranchService.selectB2BProdByParam(map);
	}

	@Override
	public TntProdProduct getByBranchIdSure(Long branchId) {
		ProdProductBranch t = prodProductBranchService
				.selectB2BProdByBranchId(branchId);
		return reserve(t);
	}

	@Override
	public List<TntProdProduct> getProdBranchList(Long productId,
			Long removeBranchId, Date visitTime) {
		List<ProdProductBranch> list = productServiceProxy.getProdBranchList(
				productId, removeBranchId, visitTime);
		return reserve(list);
	}

	@Override
	public TntProdProduct getProdBranchDetailByProdBranchId(TntBuyInfo buyInfo) {
		Long prodBranchId = buyInfo.getBranchId();
		Date visitDate = buyInfo.getVisitDate();
		ProdProductBranch prodProductBranch = pageService
				.getProdBranchByProdBranchId(prodBranchId);
		TntProdProduct t = null;
		if (prodProductBranch != null) {
			ProdProductBranch mainProdBranch = null;
			boolean isAperiodic = prodProductBranch.getProdProduct()
					.IsAperiodic();
			if (isAperiodic) {
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(prodBranchId,
								prodProductBranch.getValidEndTime());
			} else {
				if (null == visitDate) {
					Date date = prodProductBranchService
							.selectNearBranchTimePriceByBranchId(prodBranchId);
					if (null != date)
						buyInfo.setVisitTime(DateUtil.formatDate(date,
								"yyyy-MM-dd"));
				}
				// 现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(prodBranchId,
								visitDate);
			}
			t = reserve(mainProdBranch);
		}
		return t;
	}
}
