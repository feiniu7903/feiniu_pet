package com.lvmama.tnt.search.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.prod.service.TntProdPolicyService;
import com.lvmama.tnt.prod.service.TntProductService;
import com.lvmama.tnt.search.vo.Product;
import com.lvmama.tnt.search.vo.ProductBranch;
import com.lvmama.tnt.user.po.TntChannel;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.service.TntChannelService;
import com.lvmama.vst.api.search.service.VstSearchService;
import com.lvmama.vst.api.search.vo.PageConfigVo;
import com.lvmama.vst.api.search.vo.SearchResultVo;
import com.lvmama.vst.api.vo.ResultHandleT;
/**
 * 分销B2B平台搜索
 * @author gaoxin
 *
 */
@Repository("tntSearchService")
public class SearchServiceImpl implements SearchService {
	
	private static final Log log = LogFactory.getLog(SearchServiceImpl.class);
	@Autowired
	private VstSearchService vstSearchService;
	@Autowired
	private TntProdPolicyService tntProdPolicyService;
	@Autowired
	private TntChannelService tntChannelService;
	@Autowired
	private TntProductService tntProductService;
	@Autowired
	private ProdProductService prodProductService;
	
	@Override
	public ResultHandleT<SearchResultVo> searchTicket(String paramStr,TntUser user) throws Exception {
		ResultHandleT<SearchResultVo> resultT = vstSearchService.searchTicket(paramStr);
		SearchResultVo result = resultT.getReturnContent();
		if(result != null){
			result = buildDistributePriceAndValid(result,user);
			resultT.setReturnContent(result);
		}
		return resultT;
	}

	/**
	 *  build 产品在B2B平台的分销价 和是否在B2B平台售卖
	 * @param result
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	private SearchResultVo buildDistributePriceAndValid(SearchResultVo result,TntUser user) throws Exception{
		List<TntChannel> channels = tntChannelService.query(new TntChannel(TntConstant.CHANNEL_CODE.DISTRIBUTOR_B2B.name()));
		Long channelId = channels.get(0).getChannelId();
		PageConfigVo<Map<String, Object>> pageconfig = result.getPageConfig();
		List<Map<String, Object>> items =  pageconfig.getItems();
		for (Map<String, Object> map : items) {
			Map<String, Object> prodMap = (HashMap<String, Object>) map.get("product");
			if(prodMap != null){
				try {
					List<Object> single =(List<Object>) prodMap.get("SINGLE");
					List<Object> whole =(List<Object>) prodMap.get("WHOLE");
					List<Object> suit =(List<Object>) prodMap.get("SUIT");
					List<Object> union =(List<Object>) prodMap.get("UNION");
					prodMap.put("SINGLE" ,buildProd(single,user,channelId));
					prodMap.put("WHOLE" ,buildProd(whole,user,channelId));
					prodMap.put("SUIT" ,buildProd(suit,user,channelId));
					prodMap.put("UNION" ,buildProd(union,user,channelId));
					map.put("product", prodMap);
				} catch (Exception e) {
					log.error("buildProd Exception:" ,e);
				}
			}
		}
		return result;
	}

	
	private List<Object> buildProd(List<Object> objList, TntUser user, Long channelId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Object> prodList = new ArrayList<Object>();
		if (objList == null) {
			return null;
		}
		for (Object prodObj : objList) {
			if (prodObj instanceof ProductBranchBean) {
				ProductBranchBean productBranch = (ProductBranchBean) prodObj;
				ProductBranch branch = new ProductBranch();
				PropertyUtils.copyProperties(branch, productBranch);
				branch.setTntMarketPrice(productBranch.getMarketPrice());
				branch.setTntSellPrice(productBranch.getSellPrice());
				Long branchId = productBranch.getProdBranchId();
				if (user != null) {
					Long sellPrice = PriceUtil.convertToFen(productBranch.getSellPrice());
					Long distributePrice = sellPrice;
					try {
						TimePrice timPrice = prodProductService.buildTimePriceByPriceAndBranchId(branchId, sellPrice);
						Long settlePrice = timPrice.getSettlementPrice();
						distributePrice = tntProdPolicyService.calculatePrice(branchId, user.getUserId(), sellPrice, settlePrice);
					} catch (Exception e) {
						log.error("buildDistributePrice Exception:", e);
					}
					String distPrice = PriceUtil.convertToYuanInt(distributePrice)+"";
					branch.setTntPrice(distPrice);
				}
				boolean isBlack = tntProductService.isInBlack(branchId, channelId);
				String valid = isBlack ? "Y" : "N";
				branch.setValid(valid);
				prodList.add(branch);
			} else if (prodObj instanceof ProductBean) {
				ProductBean vstProduct = (ProductBean) prodObj;
				Product tntProd = new Product();
				PropertyUtils.copyProperties(tntProd, vstProduct);
				tntProd.setTntMarketPrice(vstProduct.getMarketPrice());
				tntProd.setTntSellPrice(vstProduct.getSellPrice());
				Long productId = vstProduct.getProductId();
				ProdProductBranch prodBranch = prodProductService.getProductDefaultBranchByProductId(productId);
				Long branchId = prodBranch.getProdBranchId();
				tntProd.setProdBranchId(branchId);
				if (user != null) {
					Long sellPrice = PriceUtil.convertToFen(vstProduct.getSellPrice());
					Long distributePrice = sellPrice;
					try {
						TimePrice timPrice = prodProductService.buildTimePriceByPriceAndBranchId(branchId, sellPrice);
						Long settlePrice = timPrice.getSettlementPrice();
						distributePrice = tntProdPolicyService.calculatePrice(branchId, user.getUserId(), sellPrice, settlePrice);
					} catch (Exception e) {
						log.error("buildDistributePrice Exception:", e);
					}
					String distPrice =  PriceUtil.convertToYuanInt(distributePrice)+"";
					tntProd.setTntPrice(distPrice);
				}
				boolean isBlack = tntProductService.isInBlack(branchId, channelId);
				String valid = isBlack ? "N" : "Y";
				tntProd.setValid(valid);
				prodList.add(tntProd);
			}
		}
		return prodList;
	}
}
