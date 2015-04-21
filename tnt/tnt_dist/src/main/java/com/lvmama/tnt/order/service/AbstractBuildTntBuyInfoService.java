package com.lvmama.tnt.order.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.order.vo.TntProductRelation;
import com.lvmama.tnt.prod.service.TntProdPolicyService;
import com.lvmama.tnt.prod.vo.TntProdProduct;

public abstract class AbstractBuildTntBuyInfoService implements
		BuildTntBuyInfoService {

	@Autowired
	private TntProdPolicyService tntProdPolicyService;

	@Autowired
	private ProdProductService prodProductService;

	private static final Log LOG = LogFactory
			.getLog(AbstractBuildTntBuyInfoService.class);

	protected abstract String buildMainProdBranch(TntBuyInfo info);

	protected abstract Map<String, Object> getData(Long productId);

	protected abstract String buildBuyNum(TntBuyInfo info);

	protected abstract String buildEcontract(TntBuyInfo info);

	protected abstract String buildRelatedProductList(TntBuyInfo info);

	protected abstract String buildNeedResourceConfirm(TntBuyInfo info);

	protected abstract String buildProductRelation(TntBuyInfo info);

	protected abstract String buildReserveNotice(TntBuyInfo info);

	// 初始化游玩人和联系人的必填信息
	protected abstract String buildOptionInfo(TntBuyInfo info);

	// 游玩人信息
	protected abstract String buildTravelerFill(TntBuyInfo info);

	protected String buildData(TntBuyInfo info) {
		Long productId = info.getProductId();
		String error = null;
		Map<String, Object> data = getData(productId);
		info.setData(data);
		if (data.size() == 0) {
			error = "查询数据为空";
		} else {
			error = buildBuyNum(info);
			if (error == null) {
				error = buildEcontract(info);
			}
		}
		return error;
	}

	@Override
	public ResultGod<TntBuyInfo> build(TntBuyInfo buyInfo) {
		ResultGod<TntBuyInfo> message = new ResultGod<TntBuyInfo>(true, "系统忙！");
		String error = null;
		if (null == buyInfo || buyInfo.getBranchId() == null) {
			error = "提交的buyInfo数据信息不全!跳转回主站.";
		} else {
			error = buildMainProdBranch(buyInfo);
			if (error == null) {
				error = buildData(buyInfo);
				if (error == null) {
					if ("您对订单的特殊要求".equals(buyInfo.getUserMemo())) {
						buyInfo.setUserMemo(null);
					}
					// 关联产品
					error = buildRelatedProductList(buyInfo);
					if (error == null) {
						// 是否资源审核
						error = buildNeedResourceConfirm(buyInfo);
						if (error == null) {
							// 关联的附加商品.
							error = buildProductRelation(buyInfo);
							if (error == null) {
								// 人员的必填信息
								error = buildOptionInfo(buyInfo);
								if (error == null) {
									// 游玩人信息
									error = buildTravelerFill(buyInfo);
									if (error == null) {
										// 说明语句
										error = buildReserveNotice(buyInfo);
										if (error == null) {
											// 计算分销价格
											if (buyInfo.getIsPayToLvmama()) {
												calculateSellPrice(buyInfo);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (error != null) {
			LOG.error(error);
			message.setErrorText(error);
			message.setSuccess(false);
		}
		message.setResult(buyInfo);
		return message;
	}

	private void calculateSellPrice(TntProdProduct prod, Date visitDate,
			Long userId) {
		Long branchId = prod.getBranchId();
		TimePrice timePrice = prodProductService.calcProdTimePrice(branchId,
				visitDate);
		if (timePrice != null) {
			boolean payToLvmama = prod.isPayToLvmama();
			Long price = tntProdPolicyService.calculatePrice(branchId, userId,
					prod.getSellPrice(), timePrice.getSettlementPrice(),
					payToLvmama);
			prod.setSellPrice(price);
		}
	}

	private void calculateSellPrice(TntProductRelation prod, Date visitDate,
			Long userId) {
		Long branchId = prod.getProdBranchId();
		TimePrice timePrice = prodProductService.calcProdTimePrice(branchId,
				visitDate);
		if (timePrice != null) {
			boolean payToLvmama = prod.isPayToLvmama();
			Long price = tntProdPolicyService.calculatePrice(branchId, userId,
					timePrice.getPrice(), timePrice.getSettlementPrice(),
					payToLvmama);
			prod.setSellPrice(price);
		}
	}

	protected void calculateSellPrice(TntBuyInfo buyInfo) {
		TntProdProduct prod = buyInfo.getMainProdBranch();
		if (buyInfo.getDistributor() != null && prod != null) {
			Long userId = TntUtil.parserLong(buyInfo.getDistributor());
			Date visitDate = buyInfo.getVisitDate();
			if (visitDate == null)
				visitDate = buyInfo.getValidEndTime();
			if (visitDate == null)
				visitDate = buyInfo.getValidBeginTime();
			calculateSellPrice(buyInfo.getMainProdBranch(), visitDate, userId);
			List<TntProdProduct> relatedProds = buyInfo.getRelatedProductList();
			if (relatedProds != null && !relatedProds.isEmpty()) {
				for (TntProdProduct t : relatedProds) {
					calculateSellPrice(t, visitDate, userId);
				}
			}
			calculateAddRelation(buyInfo.getAdditionalProduct(), visitDate,
					userId);
		}
	}

	protected void calculateAddRelation(
			Map<String, List<TntProductRelation>> addRelMap, Date visitDate,
			Long userId) {
		if (addRelMap != null && !addRelMap.isEmpty()) {
			Iterator<Map.Entry<String, List<TntProductRelation>>> iter = addRelMap
					.entrySet().iterator();
			while (iter.hasNext()) {
				List<TntProductRelation> list = iter.next().getValue();
				if (list != null && !list.isEmpty()) {
					for (TntProductRelation t : list) {
						calculateSellPrice(t, visitDate, userId);
					}
				}
			}
		}
	}
}
