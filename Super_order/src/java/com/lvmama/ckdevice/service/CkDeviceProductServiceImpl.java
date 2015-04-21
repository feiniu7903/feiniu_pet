package com.lvmama.ckdevice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ckdevice.dao.DeviceProductDAO;
import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyTips;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceProductService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ViewJourneyDAO;
import com.lvmama.prd.dao.ViewJourneyTipDAO;
import com.lvmama.prd.dao.ViewPageDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

/**
 * 
 * @author gaoxin
 *
 */
public class CkDeviceProductServiceImpl implements CkDeviceProductService {
	private DeviceProductDAO deviceProductDAO;
	private ProdProductDAO prodProductDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	private ViewJourneyDAO viewJourneyDAO;
	private ViewJourneyTipDAO viewJourneyTipDAO;
	private ViewPageDAO viewPageDAO;
	private ComPlaceDAO comPlaceDAO;

	
	/**
	 * 根据条件查询是否存在这个产品
	 */
	@Override
	public Boolean hasProduct(Map<String, Object> params) {
		return deviceProductDAO.hasProduct(params);
	}
	
	@Override
	public Long getDeviceProductCount(Map<String, Object> params) {
		return this.deviceProductDAO.selectDeviceProductInfoCount(params);
	}
	
	@Override
	public List<CkDeviceProduct> getDeviceProductList(Map<String, Object> params) {
		List<CkDeviceProduct> dpList = new ArrayList<CkDeviceProduct>();
		List<ProdProduct> prodProductList = this.deviceProductDAO.selectDeviceProductInfo(params);
		for (ProdProduct product : prodProductList) {
			params.put("product", product);
			// 产品基本信息:类别,时间价格
			CkDeviceProduct deviceProduct = buildDeviceProduct(params);
			// 目的地信息
			deviceProduct = buildFromDestAndToDest(deviceProduct);
			// viewPage信息,产品的一系列文字描述
			deviceProduct = buildViewPage(deviceProduct);
			if (product.isRoute()) {
				// 行程说明
				List<ViewJourney> viewJourneyList = viewJourneyDAO.getViewJourneysByProductId(product.getProductId());
				for (ViewJourney vj : viewJourneyList) {
					List<ViewJourneyTips> journeyTipsList = viewJourneyTipDAO.getViewJourneyTipsByJourneyId(vj.getJourneyId());
					if (journeyTipsList != null && journeyTipsList.size() != 0) {
						vj.setJourneyTipsList(journeyTipsList);
					}
				}
				deviceProduct.setViewJourneyList(viewJourneyList);
			}
			dpList.add(deviceProduct);
		}
		return dpList;
	}
	
	public List<CkDeviceProduct> queryCanPrintDeviceProductInfo(Map<String, Object> params){
		return deviceProductDAO.queryDeviceProductInfo(params);
	}
	
	private CkDeviceProduct buildDeviceProduct(Map<String, Object> params) {
		Date beginDate = (Date) params.get("beginDate");
		Date endDate = (Date) params.get("endDate");
		ProdProduct product = (ProdProduct) params.get("product");
		Long deviceInfoId = (Long) params.get("deviceInfoId");
		Long branchId = (Long) params.get("branchId");
		// 通过类型取不同产品类型的详细信息
		product = this.prodProductDAO.selectProductDetailByProductType(product.getProductId(), product.getProductType(), true);
		CkDeviceProduct deviceProduct = new CkDeviceProduct();
		
		// 取产品类别
		List<ProdProductBranch> branchList = new ArrayList<ProdProductBranch>();
		branchList = buildBranchList(product.getProductId(), deviceInfoId, branchId);
		// 取类别时间价格
		branchList = buildTimePrice(product, branchList, beginDate, endDate);
		product.setProdBranchList(branchList);
		deviceProduct.setProdProduct(product);
		return deviceProduct;
	}

	/**
	 * 为分销产品构造目的地信息
	 * 
	 * @param deviceProduct
	 * @return
	 */
	private CkDeviceProduct buildFromDestAndToDest(CkDeviceProduct deviceProduct) {
		Long productId = deviceProduct.getProdProduct().getProductId();
		Place fromDest = comPlaceDAO.getFromDestByProductId(productId);
		Place toDest = comPlaceDAO.getToDestByProductId(productId);
		if (fromDest != null) {
			deviceProduct.setFromDest(fromDest);
		}
		if (toDest != null) {
			deviceProduct.setToDest(toDest);
		}
		return deviceProduct;
	}

	/**
	 * 为分销产品构造viewPage信息
	 * 
	 * @param distributionProduct
	 * @return
	 */
	private CkDeviceProduct buildViewPage(CkDeviceProduct distributionProduct) {
		Long productId = distributionProduct.getProdProduct().getProductId();
		ViewPage viewPage = viewPageDAO.getViewPageByProductId(productId);
		distributionProduct.setViewPage(viewPage);
		return distributionProduct;
	}


	/**
	 * 为分销产品构造产品类别基本信息
	 * 
	 * @param productId
	 * @param distributorInfoId
	 * @param branchId
	 * @return
	 */
	private List<ProdProductBranch> buildBranchList(Long productId, Long deviceInfoId, Long branchId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("deviceInfoId", deviceInfoId);
		if(branchId != null){
			param.put("prodBranchId", branchId);
		}
		param.put("volid", "true");
		param.put("start", 0);
		param.put("end", 10000);
		List<ProdProductBranch> branchList = this.deviceProductDAO.selectProdProductBranchInfo(param);
		return branchList;
	}

	/**
	 * 为分销产品构造类别的时间价格
	 * 
	 * @param branchList
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List<ProdProductBranch> buildTimePrice(ProdProduct product, List<ProdProductBranch> branchList, Date beginDate, Date endDate) {
		if (beginDate == null) {
			beginDate = DateUtil.accurateToDay(new Date());
			endDate = DateUtil.accurateToDay(product.getOfflineTime());
		}
		for (ProdProductBranch branch : branchList) {
			Date paramBeginDate = beginDate;
			Date paramEndDate = endDate;
			List<TimePrice> tList = Collections.emptyList();
			Long productId = branch.getProdProduct().getProductId();
			Date date = productTimePriceLogic.selectNearBranchTimePriceByBranchId(branch.getProdBranchId());
			if(date!=null && date.before(paramEndDate)){
				if(paramBeginDate.before(date)){//如果开始日期比指定日期要早。改成数据库当中能拿出来的最早日期
					paramBeginDate = date;
				}
				int maxdays = DateUtil.getDaysBetween(paramBeginDate,paramEndDate);
				if(maxdays>product.getShowSaleDays()){
					maxdays=product.getShowSaleDays();
				}
				tList = productTimePriceLogic.getTimePriceList(productId,branch.getProdBranchId(), maxdays,paramBeginDate);
			}
			branch.setTimePriceList(tList);
		}
		return branchList;
	}
	public void setDeviceProductDAO(DeviceProductDAO deviceProductDAO) {
		this.deviceProductDAO = deviceProductDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}

	public void setViewJourneyTipDAO(ViewJourneyTipDAO viewJourneyTipDAO) {
		this.viewJourneyTipDAO = viewJourneyTipDAO;
	}

	public void setViewPageDAO(ViewPageDAO viewPageDAO) {
		this.viewPageDAO = viewPageDAO;
	}

	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}

	@Override
	public void save(CkDeviceProduct cp) {
		deviceProductDAO.insert(cp);
	}

	@Override
	public void update(CkDeviceProduct cp) {
		deviceProductDAO.update(cp);
	}

	@Override
	public void del(Long deviceProductId) {
		deviceProductDAO.del(deviceProductId);
		
	}

	@Override
	public List<CkDeviceProduct> query(Map<String, Object> params) {
		return deviceProductDAO.query(params);
	}

	
}
