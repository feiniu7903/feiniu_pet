package com.lvmama.distribution.service;

import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;
import com.lvmama.comm.bee.po.distribution.DistributionRakeBack;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.dao.DistributionProductCategoryDAO;
import com.lvmama.distribution.dao.DistributionProductDAO;
import com.lvmama.distribution.dao.DistributionRakeBackDAO;
import com.lvmama.prd.dao.*;
import com.lvmama.prd.logic.ProductTimePriceLogic;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class DistributionProductServiceImpl implements DistributionProductService {

	private DistributionService distributionService;
	private DistributionProductDAO distributionProductDAO;
	private ProdProductDAO prodProductDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	private ViewJourneyDAO viewJourneyDAO;
	private ViewJourneyTipDAO viewJourneyTipDAO;
	private ViewPageDAO viewPageDAO;
	private ComPlaceDAO comPlaceDAO;
	private DistributionProductCategoryDAO distributionProductCategoryDAO;
	private TopicMessageProducer productMessageProducer;
	private ProdProductBranchDAO prodProductBranchDAO;
	private DistributionRakeBackDAO distributionRakeBackDAO;
	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ProdTimePriceDAO prodTimePriceDAO;
    private boolean isDistribution = false;

    /**
	 * 查询指定产品类别及分销商信息
	 * 
	 * @param productId
	 * @return
	 */
	@Override
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String type) {
		List<ProdProductBranch> prodProductBranchList = prodProductBranchDAO.getProductBranchByProductId(productId, null, null, null);
		return buildDistributorForProductBranchList(prodProductBranchList, type);
	}
	
	/**
	 * 根据条件查询分销产品信息
	 */
	@Override
	public Long getDistributionProductBranchCount(Map<String, Object> params) {
		return distributionProductDAO.selectByDistributionCount(params);
	}
	/**
	 * 根据条件查询分销产品信息
	 */
	@Override
	public List<ProdProductBranch> getDistributionProductBranchList(Map<String, Object> params, String type) {
		List<ProdProductBranch> productBranchList = distributionProductDAO.selectProdProductBranchByDistribution(params);
		return buildDistributorForProductBranchList(productBranchList, type);
	}

	public List<ProdProductBranch> getProductBranchList(Map<String, Object> params, String type) {
		List<ProdProductBranch> productBranchList = distributionProductDAO.selectProdProductBranchByParams(params);
		return buildDistributorForProductBranchList(productBranchList, type);
	}
	
	/**
	 * 根据条件查询分销产品信息
	 */
	@Override
	public DistributionProduct getDistributionProductByBranchId(Long branchId, Long distributorInfoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("volid", "true");
		params.put("productBranchId", branchId);
		params.put("distributorInfoId", distributorInfoId);
		List<DistributionProduct> result = distributionProductDAO.selectByParams(params);
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}

	/**
	 * 根据产品类别查询所有可分销产品类别
	 * 
	 * @return
	 */
	@Override
	public List<ProdProductBranch> selectProdCanDistributeByProdType(Map<String, Object> params) {
		return prodProductBranchDAO.selectProdCanDistributeByProdType(params);
	}

	/**
	 * 根据条件查询分销商产品数目
	 */
	@Override
	public Long getDistributionProductListCount(Map<String, Object> params) {
		return this.distributionProductDAO.selectByDistributionCount(params);
	}

	/**
	 * 根据分销商查询分销产品类别
	 */
	@Override
	public Boolean selectDistributionProductBranchList(Map<String, Object> params) {
		return distributionProductDAO.getProdBranchIdByCondition(params);
	}

	/**
	 * 查询指定产品类别及分销商信息
	 * 
	 * @param productId
	 * @return
	 */
	@Override
	public List<ProdProductBranch> getProductBranchByProductId(Long productId) {
		return prodProductBranchDAO.getProductBranchByProductId(productId, null, null, null);
	}

	@Override
	public Long getDistributionProductCount(Map<String, Object> params) {
		return this.distributionProductDAO.selectDistributionProductInfoCount(params);
	}
	
	@Override
	public List<DistributionProduct> getDistributionProductList(Map<String, Object> params) {
		List<DistributionProduct> dList = new ArrayList<DistributionProduct>();
		List<ProdProduct> prodProductList = this.distributionProductDAO.selectDistributionProductInfo(params);
		for (ProdProduct product : prodProductList) {
			params.put("product", product);
			// 产品基本信息:类别,时间价格
			DistributionProduct distributionProduct = buildDistributionProduct(params);
			// 目的地信息
			distributionProduct = buildFromDestAndToDest(distributionProduct);
			// viewPage信息,产品的一系列文字描述
			distributionProduct = buildViewPage(distributionProduct);
			if (product.isRoute()) {
				// 行程说明
				List<ViewJourney> viewJourneyList = viewJourneyDAO.getViewJourneysByProductId(product.getProductId());
				for (ViewJourney vj : viewJourneyList) {
					List<ViewJourneyTips> journeyTipsList = viewJourneyTipDAO.getViewJourneyTipsByJourneyId(vj.getJourneyId());
					if (journeyTipsList != null && journeyTipsList.size() != 0) {
						vj.setJourneyTipsList(journeyTipsList);
					}
				}
				distributionProduct.setViewJourneyList(viewJourneyList);
			}
			dList.add(distributionProduct);
		}
		return dList;
	}

	@Override
	public List<DistributionProduct> getDistributionOnline(Map<String, Object> params) {
		Long distributorInfoId = (Long) params.get("distributorInfoId");
		List<DistributionProduct> distributionProductList = new ArrayList<DistributionProduct>();
		List<ProdProduct> prodProductList = this.distributionProductDAO.selectDistributionProductInfo(params);
		for (ProdProduct product : prodProductList) {
			DistributionProduct dProduct = new DistributionProduct();
			// 取产品类别
			List<ProdProductBranch> branchList = buildBranchList(product.getProductId(), distributorInfoId);
			product.setProdBranchList(branchList);
			dProduct.setProdProduct(product);
			distributionProductList.add(dProduct);
		}
		return distributionProductList;
	}

	@Override
	public List<DistributionProduct> getDistributionProductTimePriceList(Map<String, Object> params) {
		List<DistributionProduct> distributionProductList = new ArrayList<DistributionProduct>();
		// 取产品列表
		List<ProdProduct> prodProductList = this.distributionProductDAO.selectDistributionProductInfo(params);
		for (ProdProduct product : prodProductList) {
			params.put("product", product);
			DistributionProduct distributionProduct = buildDistributionProduct(params);
			distributionProduct = buildDistributionProdStock(distributionProduct, params);
			distributionProductList.add(distributionProduct);
		}
		return distributionProductList;
	}
	

	@Override
	public boolean isSellableDistributionProductTimePrice(Map<String,Object> params,TimePrice timeprice){
		boolean productSellable = true;
		Long settlementPrice = timeprice.getSettlementPrice();
		Long sellPrice = timeprice.getPrice();
		Long prodBranchId = timeprice.getProdBranchId();
		Long distributorInfoId = (Long) params.get("distributorInfoId");
		DistributionRakeBack rake = distributionRakeBackDAO.selectByParams(prodBranchId	,distributorInfoId);
		Float discountRate = 0f;
		// 如果获取不到手动返佣点，就采用统一返佣点
		if (null != rake) {
			if(!rake.isRateVolid()){
				return true;
			}
			Long rakeBackRate = rake.getRakeBackRate();
			discountRate = rakeBackRate!=null?rakeBackRate.floatValue():0;
		} else {
			List<DistributionProductCategory> categoryList = distributionProductCategoryDAO.selectByParams(params);
			if (!categoryList.isEmpty()) {
				// 统一返佣点
				DistributionProductCategory category = categoryList.get(0);
				discountRate = category.getDiscountRateY();
			}
		}
		double profit = (sellPrice - settlementPrice);
		if (profit > 0) {
			double profitRate = (profit / sellPrice) * 100.0;
			if (profitRate <= discountRate) {
				productSellable = false;
			}
		} else {
			productSellable = false;
		}
		return productSellable;
	}
	/**
	 * 
	 */
	@Override
	public void saveDistributionProduct(Long distributorId, Long productId, Long branchId) {
		DistributionProduct dp = new DistributionProduct();
		dp.setProductId(productId);
		dp.setProductBranchId(branchId);
		dp.setDistributorInfoId(distributorId);
		dp.setVolid("true");
		this.distributionProductDAO.insert(dp);
	}
	@Override
	public void saveDistributionProduct(List<Long> distributorIds, Long productId, Long branchId) {
		for(Long id : distributorIds){
			DistributionProduct dp = getDistributionInfoProductByBranchId(branchId,id);
			if(dp==null){
				this.saveDistributionProduct(id, productId, branchId);
			}
		}
	}
	@Override
	public void updateDistributionProductVolid(Long branchId, List<Long> distributorIds, String volid) {
		StringBuilder sb = new StringBuilder(10);
		for(Long id : distributorIds){
			DistributionProduct d = new DistributionProduct();
			d.setDistributorInfoId(id);
			d.setProductBranchId(branchId);
			d.setVolid(volid);
			DistributionProduct dbp = getDistributionInfoProductByBranchId(branchId,id);
			if(dbp==null){
				ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(branchId);
				if(branch!=null){
					d.setProductId(branch.getProductId());
					distributionProductDAO.insert(d);
				}
			}else{
				this.distributionProductDAO.updateVolid(d);
			}
			sb.append(id).append(",");
		}
		if("false".equals(volid) && sb.length() > 0){
			productMessageProducer.sendMsg(MessageFactory.newDistribuionProductDeleteMessage(branchId, sb.toString()));
		}
	}

	public DistributionProduct getDistributionInfoProductByBranchId(Long branchId, Long distributorInfoId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productBranchId", branchId);
		params.put("distributorInfoId", distributorInfoId);
		List<DistributionProduct> result = distributionProductDAO.selectByParams(params);
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	
	public List<DistributionProduct> selectAllByParams(Map<String, Object> params){
		return distributionProductDAO.selectAllByParams(params);
	}
	/**
	 * 为类别注入分销商信息
	 * 
	 * @param productBranchList
	 * @param type 
	 * @return
	 */
	private List<ProdProductBranch> buildDistributorForProductBranchList(List<ProdProductBranch> productBranchList, String type) {
		for (ProdProductBranch branch : productBranchList) {
			List<DistributorInfo> whiteResultList = distributionService.selectByProductBranchIdAndVolid(branch.getProdBranchId(), "true");
			List<DistributorInfo> blackResultList = distributionService.selectByProductBranchIdAndVolid(branch.getProdBranchId(), "false");
			branch.setDistributor(this.getDistributor(whiteResultList));
			branch.setBlackDistrbuor(this.getDistributor(blackResultList));
		}
		return productBranchList;
	}

	private String getDistributor(List<DistributorInfo> distributorInfoList) {
		if(distributorInfoList == null) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		if (!distributorInfoList.isEmpty()) {
			for (DistributorInfo distributorInfo : distributorInfoList) {
				str.append(distributorInfo.getDistributorName()).append(",");
			}
		}
		if(StringUtils.isNotEmpty(str)){
			return str.substring(0, str.length()-1);
		}
		return str.toString();
	}
	
	private DistributionProduct buildDistributionProduct(Map<String, Object> params) {
		Date beginDate = (Date) params.get("beginDate");
		Date endDate = (Date) params.get("endDate");
		ProdProduct product = (ProdProduct) params.get("product");
		Long distributorInfoId = (Long) params.get("distributorInfoId");
		Long branchId = (Long) params.get("branchId");
		// 通过类型取不同产品类型的详细信息
		product = this.prodProductDAO.selectProductDetailByProductType(product.getProductId(), product.getProductType(), true);
		DistributionProduct distributionProduct = new DistributionProduct();
		// 取产品类别
		List<ProdProductBranch> branchList = new ArrayList<ProdProductBranch>();
		if (branchId == null) {
			branchList = buildBranchList(product.getProductId(), distributorInfoId);
		} else {
			branchList = buildBranchList(product.getProductId(), distributorInfoId, branchId);
		}
		// 取类别时间价格
		// add by gaoxin for 519
		String key = "DIST_TEMP_TIMEPRICE_519";
		Object ob = MemcachedUtil.getInstance().get(key);
		if(ob != null){
			branchList = buildTimePrice(product, branchList, beginDate, endDate,true);
		}else{
			branchList = buildTimePrice(product, branchList, beginDate, endDate);
		}
		product.setProdBranchList(branchList);
		distributionProduct.setProdProduct(product);
		return distributionProduct;
	}

	/**
	 * 为分销产品构造目的地信息
	 * 
	 * @param distributionProduct
	 * @return
	 */
	private DistributionProduct buildFromDestAndToDest(DistributionProduct distributionProduct) {
		Long productId = distributionProduct.getProdProduct().getProductId();
		Place fromDest = comPlaceDAO.getFromDestByProductId(productId);
		Place toDest = comPlaceDAO.getToDestByProductId(productId);
		if (fromDest != null) {
			distributionProduct.setFromDest(fromDest);
		}
		if (toDest != null) {
			distributionProduct.setToDest(toDest);
		}
		return distributionProduct;
	}

	/**
	 * 为分销产品构造viewPage信息
	 * 
	 * @param distributionProduct
	 * @return
	 */
	private DistributionProduct buildViewPage(DistributionProduct distributionProduct) {
		Long productId = distributionProduct.getProdProduct().getProductId();
		ViewPage viewPage = viewPageDAO.getViewPageByProductId(productId);
		distributionProduct.setViewPage(viewPage);
		return distributionProduct;
	}

    /**
     * 为分销产品构造产品类别基本信息
     * @param productId
     * @param distributorInfoId
     * @return
     */
	private List<ProdProductBranch> buildBranchList(Long productId, Long distributorInfoId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("distributorInfoId", distributorInfoId);
		param.put("volid", "true");
		param.put("start", 0);
		param.put("end", 10000);
		List<ProdProductBranch> branchList = this.distributionProductDAO.selectProdProductBranchByDistribution(param);
		return branchList;
	}

	/**
	 * 为分销产品构造产品类别基本信息
	 * 
	 * @param productId
	 * @param distributorInfoId
	 * @param branchId
	 * @return
	 */
	private List<ProdProductBranch> buildBranchList(Long productId, Long distributorInfoId, Long branchId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("distributorInfoId", distributorInfoId);
		param.put("prodBranchId", branchId);
		param.put("volid", "true");
		param.put("start", 0);
		param.put("end", 10000);
		List<ProdProductBranch> branchList = this.distributionProductDAO.selectProdProductBranchByDistribution(param);
		return branchList;
	}

    /**
     * 为分销产品构造类别的时间价格 (5.19 临时价格专用接口)
     * @param product
     * @param branchList
     * @param beginDate
     * @param endDate
     * @param isDistribution  是否分销
     * @return
     */
    private List<ProdProductBranch> buildTimePrice(ProdProduct product, List<ProdProductBranch> branchList, Date beginDate, Date endDate,boolean isDistribution) {
        try{
            this.isDistribution = isDistribution;
            return buildTimePrice(product, branchList, beginDate, endDate);
        } finally {
            this.isDistribution = false;
        }
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
            //todo troy
			Date date;
            if (isDistribution) {
                date = productTimePriceLogic.selectNearBranchTimePriceByBranchId(branch.getProdBranchId(), isDistribution);
            } else {
                date = productTimePriceLogic.selectNearBranchTimePriceByBranchId(branch.getProdBranchId());
            }
            if(date!=null && date.before(paramEndDate)){
				if(paramBeginDate.before(date)){//如果开始日期比指定日期要早。改成数据库当中能拿出来的最早日期
					paramBeginDate = date;
				}
				int maxdays = DateUtil.getDaysBetween(paramBeginDate,paramEndDate);
				if(maxdays>product.getShowSaleDays()){
					maxdays=product.getShowSaleDays();
				}
                //todo troy
                if (isDistribution) {
                    tList = productTimePriceLogic.getTimePriceList(productId, branch.getProdBranchId(), maxdays, paramBeginDate,isDistribution);
                } else {
                    tList = productTimePriceLogic.getTimePriceList(productId, branch.getProdBranchId(), maxdays, paramBeginDate);
                }
            }
			branch.setTimePriceList(tList);
		}
		return branchList;
	}

	/**
	 * 如果设置了手动返佣点，就取相应返佣点
	 * mod by zhangwengang 2013/10/31
	 * 
	 * @param distributionProduct
	 * @param params
	 * @return
	 */
	private DistributionProduct buildDistributionProdStock(DistributionProduct distributionProduct, Map<String, Object> params) {
		ProdProduct product = distributionProduct.getProdProduct();
		List<ProdProductBranch> prodBranchList = product.getProdBranchList();
		for (ProdProductBranch prodBranch : prodBranchList) {
			String productType = product.getProductType();
			params.put("productType", productType);
			if(Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)){
				params.put("subProductType", product.getSubProductType());
			}
			if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
				boolean payOnline = product.isPaymentToLvmama(); 
				String onlineVal = payOnline ? Constant.TRUE_FALSE.TRUE.getValue() : Constant.TRUE_FALSE.FALSE.getValue();
				params.put("payOnline", onlineVal);
			}
			List<TimePrice> timePriceList = prodBranch.getTimePriceList();
			for (TimePrice price : timePriceList) {
				boolean isSellable = isSellableDistributionProductTimePrice(params, price);
				if(!isSellable){
					price.setDayStock(0l);
				}
			}
			prodBranch.setTimePriceList(timePriceList);
		}
		product.setProdBranchList(prodBranchList);
		distributionProduct.setProdProduct(product);
		return distributionProduct;
	}
	
	@Override
	public void updateDistributionProductVolid(Long productId, String valid) {
		distributionProductDAO.updateVolidByParams(productId, null, null, valid);
	}
	
	public void checkCancel(Long productId, String addition) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("volid", "true");
		params.put("productId", productId);
		// 根据产品id获取分销产品 
		List<DistributionProduct> distributionProductList = distributionProductDAO.selectByParams(params);
		for (DistributionProduct distributionProduct : distributionProductList) {
			boolean cancelFlag = false;
			params.clear();
			params.put("productType", "TICKET");
			params.put("distributorInfoId", distributionProduct.getDistributorInfoId());
			// 获取分销商关联关系
			List<DistributionProductCategory> categoryList = distributionProductCategoryDAO.selectByParams(params);
			if (null != categoryList && categoryList.size() == 1) {
				DistributionProductCategory category = categoryList.get(0);
				// 取消分销条件：1，支付对象为驴妈妈并不支持在线支付    2，支付对象为景区并支持在线支付
				if (Constant.PAYMENT_TARGET.TOSUPPLIER.name().equals(addition) &&
						"true".equals(category.getPayOnline())) {
					cancelFlag = true;
				} else if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(addition) &&
						!"true".equals(category.getPayOnline())) {
					cancelFlag = true;
				}
			}
			if (cancelFlag) {
				distributionProductDAO.updateVolidByParams(productId, null, distributionProduct.getDistributorInfoId(), "false");
			}
		}
	}
	/**
	 * 自动更新去哪儿返现
	 */
	@Override
	public void autoUpdateCommentsCashback(DistributionProduct distributionProduct){
		Long branchId = distributionProduct.getProductBranchId();
		Long distributorId = distributionProduct.getDistributorInfoId();
		ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(branchId);
		if(prodProductBranch==null){
			return;
		}
		ProdProduct prodProduct = prodProductDAO.selectByPrimaryKey(prodProductBranch.getProductId());
		if(prodProduct==null){
			return ;
		}
		DistributionProduct dbProduct = this.getDistributionProductByBranchId(branchId, distributorId);
		if(dbProduct!=null && "false".equalsIgnoreCase(dbProduct.getNeedAutoUpdateCashBack())){
			return ;
		}
		Float cassBack = getCassBack(branchId,distributorId,prodProduct.isPaymentToLvmama());
		Double profitPrice = getMinProfitPrice(branchId,cassBack);
		BigDecimal rate = new BigDecimal(profitPrice);
		Long v = rate.divide(new BigDecimal(2),BigDecimal.ROUND_DOWN).longValue();
		distributionProduct.setCommentsCashback(v);
		updateCommentsCashback(distributionProduct);
		productMessageProducer.sendMsg(MessageFactory.newDistribuionCashBackUpdateMessage(distributionProduct.getProductId()));
	}
	
	private Double getMinProfitPrice(Long branchId,Float cassBack) {
		// 根据销售类别id获取采购类别id
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodBranchId", branchId);
		param.put("beginDate", new Date());
		param.put("endDate", DateUtil.dsDay_Date(new Date(),90));
		List<TimePrice> prodTimePriceList = prodTimePriceDAO.selectProdTimePriceByParams(param);
		Double profitPrice = new Double(0);
		Double sellPrice = new Double(0);
		int i=0;
		if(prodTimePriceList!=null && prodTimePriceList.size()>0){
			for(TimePrice sellTimePrice: prodTimePriceList){
				Double tempSettlementPrice = new Double(0);
				sellPrice =  Double.valueOf(sellTimePrice.getPrice());
				List<ProdProductBranchItem> prodPorductBranchItemList = prodProductBranchItemDAO.selectBranchItemByProdBranchId(branchId);
				for (ProdProductBranchItem prodProductBranchItem : prodPorductBranchItemList) {
					TimePrice metaTimePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(prodProductBranchItem.getMetaBranchId(), sellTimePrice.getSpecDate());
					if(metaTimePrice == null || metaTimePrice.getSettlementPrice()==0){
						break;
					}
					tempSettlementPrice += Double.valueOf(metaTimePrice.getSettlementPrice() * prodProductBranchItem.getQuantity());
				}
				Double tempPrice = sellPrice - tempSettlementPrice*100/(100-cassBack);
				if(tempPrice<0){
					return new Double(0);
				}
				if (tempPrice < profitPrice || i==0) {
					profitPrice = tempPrice;
				}
				i++;
			}
		}
		return profitPrice/100;
	}

	private Float getCassBack(Long branchId, Long distributorId,boolean isPayLvmama) {
		DistributionRakeBack rakeBack = distributionRakeBackDAO.selectByParams(branchId, distributorId);
		if(rakeBack!=null && rakeBack.getRakeBackRate()>=0){
			return rakeBack.getRakeBackRate().floatValue();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productType", "TICKET");
		params.put("payOnline", isPayLvmama+"");
		params.put("distributorInfoId", distributorId);
		// 获取分销商关联关系
		List<DistributionProductCategory> categoryList = distributionProductCategoryDAO.selectByParams(params);
		if(categoryList!=null && categoryList.size()>0){
			return categoryList.get(0).getDiscountRateY();
		}
		return 0f;
	}

	public void updateCommentsCashback(DistributionProduct distributionProduct){
		distributionProductDAO.updateCommentsCashback(distributionProduct);
	}
	public DistributionProductDAO getDistributionProductDAO() {
		return distributionProductDAO;
	}

	public void setDistributionProductDAO(DistributionProductDAO distributionProductDAO) {
		this.distributionProductDAO = distributionProductDAO;
	}

	public ProdProductDAO getProdProductDAO() {
		return prodProductDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public ProductTimePriceLogic getProductTimePriceLogic() {
		return productTimePriceLogic;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public ViewJourneyDAO getViewJourneyDAO() {
		return viewJourneyDAO;
	}

	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}

	public ViewJourneyTipDAO getViewJourneyTipDAO() {
		return viewJourneyTipDAO;
	}

	public void setViewJourneyTipDAO(ViewJourneyTipDAO viewJourneyTipDAO) {
		this.viewJourneyTipDAO = viewJourneyTipDAO;
	}

	public ViewPageDAO getViewPageDAO() {
		return viewPageDAO;
	}

	public void setViewPageDAO(ViewPageDAO viewPageDAO) {
		this.viewPageDAO = viewPageDAO;
	}

	public ComPlaceDAO getComPlaceDAO() {
		return comPlaceDAO;
	}

	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}

	public DistributionProductCategoryDAO getDistributionProductCategoryDAO() {
		return distributionProductCategoryDAO;
	}

	public void setDistributionProductCategoryDAO(DistributionProductCategoryDAO distributionProductCategoryDAO) {
		this.distributionProductCategoryDAO = distributionProductCategoryDAO;
	}

	public TopicMessageProducer getProductMessageProducer() {
		return productMessageProducer;
	}

	public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	public ProdProductBranchDAO getProdProductBranchDAO() {
		return prodProductBranchDAO;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public DistributionRakeBackDAO getDistributionRakeBackDAO() {
		return distributionRakeBackDAO;
	}

	public void setDistributionRakeBackDAO(
			DistributionRakeBackDAO distributionRakeBackDAO) {
		this.distributionRakeBackDAO = distributionRakeBackDAO;
	}

	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}
	
}
