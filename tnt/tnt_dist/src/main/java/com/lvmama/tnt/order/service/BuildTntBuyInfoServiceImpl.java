package com.lvmama.tnt.order.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.service.prod.ProductServiceProxy;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.order.vo.TntProdAssemblyPoint;
import com.lvmama.tnt.order.vo.TntProductRelation;
import com.lvmama.tnt.prod.service.TntProdProductService;
import com.lvmama.tnt.prod.vo.TntProdProduct;

@Repository("buildTntBuyInfoService")
public class BuildTntBuyInfoServiceImpl extends AbstractBuildTntBuyInfoService {

	@Autowired
	private PageService pageService;

	@Autowired
	private ProductServiceProxy productServiceProxy;

	@Autowired
	private TntProdProductService tntProdProductService;

	@Autowired
	private IReceiverUserService receiverUserService;

	@Autowired
	private PlaceCityService placeCityService;

	@Autowired
	private ProdProductBranchService prodProductBranchService;

	@Override
	protected String buildMainProdBranch(TntBuyInfo info) {
		Long prodBranchId = info.getBranchId();
		Date visitDate = info.getVisitDate();
		ProdProductBranch prodProductBranch = pageService
				.getProdBranchByProdBranchId(prodBranchId);
		TntProdProduct t = null;
		String error = null;
		if (prodProductBranch != null) {
			ProdProductBranch mainProdBranch = null;
			boolean isAperiodic = prodProductBranch.getProdProduct()
					.IsAperiodic();
			if (isAperiodic) {
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(prodBranchId,
								prodProductBranch.getValidEndTime());
				if (mainProdBranch != null) {
					info.setValidBeginTime(mainProdBranch.getValidBeginTime());
					info.setValidEndTime(mainProdBranch.getValidEndTime());
				}
			} else {
				if (null == visitDate) {
					Date date = prodProductBranchService
							.selectNearBranchTimePriceByBranchId(prodBranchId);
					if (null != date)
						info.setVisitTime(DateUtil.formatDate(date,
								"yyyy-MM-dd"));
				}
				// 现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(prodBranchId,
								visitDate);
			}
			if (mainProdBranch == null) {
				error = "产品类别不存在";
			} else {
				t = reserve(mainProdBranch);
				info.setMainProdBranch(t);
				info.setIsPayToLvmama(mainProdBranch.getProdProduct()
						.isPaymentToLvmama());
				if (info.getProductId() == null)
					info.setProductId(mainProdBranch.getProductId());
			}
		} else {
			error = "类别不存在";
		}
		return error;
	}

	@Override
	protected Map<String, Object> getData(Long productId) {
		Map<String, Object> data = pageService.getProdCProductInfo(productId,
				false);
		return data;
	}

	@Override
	protected String buildBuyNum(TntBuyInfo info) {
		Map<String, Object> data = info.getData();
		List<ProdProductBranch> branchList = (List<ProdProductBranch>) data
				.get("prodProductBranchList");
		String error = null;
		if (branchList.isEmpty()) {
			error = "类别列表为空";
		} else {
			boolean isAperiodic = info.getMainProdBranch().isAperiodic();
			if (isAperiodic) {
				// 目的地、搜索、频道跳转来的连接可能没有放置数量，取最小预定量填充
				if (info.getBuyNum().isEmpty()) {
					Map<String, Integer> numMap = new HashMap<String, Integer>();
					for (ProdProductBranch b : branchList) {
						Integer num = b.getMinimum() < 1 ? 1 : Integer
								.parseInt(b.getMinimum().toString());
						numMap.put("product_" + b.getProdBranchId(), num);
					}
					info.setBuyNum(numMap);
				}
			}
		}
		return error;
	}

	protected String buildEcontract(TntBuyInfo info) {
		Map<String, Object> data = info.getData();
		ProdCProduct prodCProduct = (ProdCProduct) data.get("prodCProduct");
		String error = null;
		Long productId = info.getProductId();
		if (!prodCProduct.getProdProduct().isSellable()) {
			error = "当前产品" + productId + " 已经过了上下线时间";
		} else if (!prodCProduct.getProdProduct().isOnLine()) {
			error = "当前产品" + productId + "未上线online="
					+ prodCProduct.getProdProduct().getOnLine();
		} /*
		 * update by gaoxin 2014/05/07 else if
		 * (!productServiceProxy.isSellProductByChannel(productId,
		 * info.getChannel())) { error = "当前产品" + productId + "不能在" +
		 * info.getChannel() + "渠道销售"; }
		 */else if (!pageService.checkDateCanSale(info.getProductId(),
				info.getVisitDate())) {
			error = "当前产品" + productId + "游玩时间"
					+ DateUtil.getDateTime("yyyy-MM-dd", info.getVisitDate())
					+ "有时间限制";
		}
		if (error == null) {
			String mainProductEContract = "false";
			if (null != prodCProduct.getProdRoute()) {
				mainProductEContract = prodCProduct.getProdRoute()
						.isEContract() ? "true" : "false";
			}
			info.setIsEContract(mainProductEContract);
		}
		return error;
	}

	@Override
	protected String buildRelatedProductList(TntBuyInfo info) {
		TntProdProduct mainProdBranch = info.getMainProdBranch();
		List<ProdProductBranch> relatedProductList = null;
		if (ProductUtil.hasQueryBranchs(mainProdBranch.getProductType())) {
			relatedProductList = productServiceProxy.getProdBranchList(
					mainProdBranch.getProductId(),
					mainProdBranch.getBranchId(), info.getVisitDate());
		} else {
			relatedProductList = Collections.emptyList();
		}
		/**
		 * 门票,购买数量逻辑
		 */
		if (mainProdBranch.isTicket()) {
			List<ProdProductBranch> relatedProductList2 = new ArrayList<ProdProductBranch>();
			relatedProductList2.addAll(relatedProductList);
			ProdProductBranch pb = new ProdProductBranch();
			pb.setProdBranchId(mainProdBranch.getBranchId());
			pb.setMinimum(mainProdBranch.getMinimum());
			relatedProductList2.add(pb);
			if (null != relatedProductList2 && relatedProductList2.size() > 0) {
				for (ProdProductBranch product : relatedProductList2) {
					String skey = "product_" + product.getProdBranchId();
					if (null == info.getBuyNum().get(skey)) {
						info.getBuyNum().put(skey,
								product.getMinimum().intValue());
					}
				}
			}
		}
		if (relatedProductList != null && !relatedProductList.isEmpty()) {
			List<TntProdProduct> list = new ArrayList<TntProdProduct>();
			for (ProdProductBranch t : relatedProductList) {
				list.add(reserve(t));
			}
			info.setRelatedProductList(list);
		}
		return null;
	}

	private TntProdProduct reserve(ProdProductBranch t) {
		TntProdProduct p = null;
		if (t != null) {
			p = new TntProdProduct();
			p.setBranchId(t.getProdBranchId());
			p.setBranchName(t.getBranchName());
			p.setBranchStatus(t.getOnline());
			p.setSellPrice(t.getSellPrice());
			p.setMaximum(t.getMaximum());
			p.setMinimum(t.getMinimum());
			p.setStock(t.getStock());
			p.setBranchType(t.getBranchType());
			p.setAdultQuantity(t.getAdultQuantity());
			p.setChildQuantity(t.getChildQuantity());
			p.setDescription(t.getDescription());
			p.setInvalidDateMemo(t.getInvalidDateMemo());
			p.setValidBeginTime(t.getValidBeginTime());
			p.setValidEndTime(t.getValidEndTime());
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
				p.setSubProductType(pp.getSubProductType());
			}
		}
		return p;
	}

	protected String buildNeedResourceConfirm(TntBuyInfo info) {
		TntProdProduct mainProdBranch = info.getMainProdBranch();
		String isNeedResourceConfirm = null;
		// 不定期默认为不需要资源审核
		if (mainProdBranch.isAperiodic()) {
			isNeedResourceConfirm = TntConstant.TRUE_FALSE.FALSE.name();
		} else {
			List<Date> visitDates = new ArrayList<Date>();
			visitDates.add(info.getVisitDate());
			for (int i = 0; i < visitDates.size(); i++) {
				boolean resourceConfirm = pageService.isResourceConfirm(
						info.getBranchId(), visitDates.get(i));
				isNeedResourceConfirm = BooleanUtils
						.toStringTrueFalse(resourceConfirm);
				if (resourceConfirm) {
					break;
				}
			}
		}
		info.setIsNeedResourceConfirm(isNeedResourceConfirm);
		return null;
	}

	@Override
	protected String buildProductRelation(TntBuyInfo info) {
		TntProdProduct mainProdBranch = info.getMainProdBranch();
		List<ProdProductRelation> relateList = null;
		// 不定期暂时不做附加产品
		if (!mainProdBranch.isAperiodic()) {
			// 关联的附加商品.
			relateList = this.productServiceProxy.getRelatProduct(
					mainProdBranch.getProductId(), info.getVisitDate());
			Map<String, List<TntProductRelation>> additionalProduct = initAditionalProduct(relateList);
			info.setAdditionalProduct(additionalProduct);
		}
		return null;
	}

	private Map<String, List<TntProductRelation>> initAditionalProduct(
			List<ProdProductRelation> list) {
		Map<String, List<TntProductRelation>> additionalProduct = new HashMap<String, List<TntProductRelation>>();
		for (ProdProductRelation ppr : list) {
			// 前台不可见的产品直接过滤掉
			if (!ppr.getBranch().hasVisible()) {
				continue;
			}
			// 如果是酒店相关的产品就需要判断能否加床
			// 如果不能加床就过滤产品
			String subProductTypeStr = ppr.getSubProductTypeStrTwo();

			List<TntProductRelation> relates = additionalProduct
					.get(subProductTypeStr);
			if (relates != null) {
				relates.add(reserve(ppr));
			} else {
				relates = new ArrayList<TntProductRelation>();
				relates.add(reserve(ppr));
				additionalProduct.put(subProductTypeStr, relates);
			}
		}
		return additionalProduct;
	}

	private TntProductRelation reserve(ProdProductRelation t) {
		TntProductRelation tpr = new TntProductRelation();
		tpr.setProdBranchId(t.getProdBranchId());
		tpr.setProductId(t.getProductId());
		tpr.setRelationId(t.getRelationId());
		tpr.setRelatProductId(t.getRelatProductId());
		tpr.setSaleNumType(t.getSaleNumType());
		ProdProduct prod = t.getRelationProduct();
		if (prod != null) {
			tpr.setProductName(prod.getProductName());
			tpr.setSubProductType(prod.getSubProductType());
			tpr.setPayToLvmama(prod.isPaymentToLvmama());
		}
		ProdProductBranch branch = t.getBranch();
		if (branch != null) {
			tpr.setBranchName(branch.getBranchName());
			tpr.setBranchType(branch.getBranchType());
			tpr.setDescription(branch.getDescription());
			tpr.setMaximum(branch.getMaximum());
			tpr.setMinimum(branch.getMinimum());
			tpr.setSellPrice(branch.getSellPrice());
			tpr.setStock(branch.getStock());
		}
		return tpr;
	}

	@Override
	protected String buildOptionInfo(TntBuyInfo info) {
		Map<String, Object> data = info.getData();
		ProdCProduct prodCProduct = (ProdCProduct) data.get("prodCProduct");
		ProdProduct product = prodCProduct.getProdProduct();
		// 联系人的手机和姓名必填
		List<String> contactInfoOptions = new ArrayList<String>();
		contactInfoOptions.add(Constant.TRAVELLER_INFO_OPTIONS.NAME.getCode());
		contactInfoOptions
				.add(Constant.TRAVELLER_INFO_OPTIONS.MOBILE.getCode());

		List<String> travellerInfoOptions = product
				.getTravellerInfoOptionsList();
		// System.out.println(travellerInfoOptions);
		List<String> firstTravellerInfoOptions = product
				.getFirstTravellerInfoOptionsList();

		if (null == travellerInfoOptions) {
			travellerInfoOptions = new ArrayList<String>(0);
		}
		if (null == firstTravellerInfoOptions) {
			firstTravellerInfoOptions = new ArrayList<String>(0);
		}

		if (null != product.getContactInfoOptionsList()) {
			contactInfoOptions.addAll(product.getContactInfoOptionsList());
		}
		info.setFirstTravellerInfoOptions(firstTravellerInfoOptions);
		info.setTravellerInfoOptions(travellerInfoOptions);
		info.setContactInfoOptions(contactInfoOptions);
		return null;
	}

	@Override
	protected String buildTravelerFill(TntBuyInfo info) {
		List<ProdAssemblyPoint> prodAssemblyPointList = productServiceProxy
				.getAssemblyPoints(info.getProductId());
		if (prodAssemblyPointList != null && !prodAssemblyPointList.isEmpty()) {
			List<TntProdAssemblyPoint> list = new ArrayList<TntProdAssemblyPoint>();
			info.setProdAssemblyPointList(list);
		}
		return null;
	}

	@Override
	protected String buildReserveNotice(TntBuyInfo info) {
		TntProdProduct mainProdBranch = info.getMainProdBranch();
		Map<String, String> reserveNoticeMap = new HashMap<String, String>();
		ViewPage viewPage = (ViewPage) info.getData().get("viewPage");
		// 费用说明
		StringBuffer feiyong = new StringBuffer();
		String productType = mainProdBranch.getProductType();
		String subProductType = mainProdBranch.getSubProductType();
		if (productType.equals("HOTEL")) {
			if (mainProdBranch.getDescription() != null) {
				String[] description = mainProdBranch.getDescription().split(
						"\n");
				for (int i = 0; i < description.length; i++) {
					feiyong.append(description[i]);
				}
			}
		} else if (productType.equals("TRAFFIC")
				&& mainProdBranch.getSubProductType().equals("TRAIN")) {
			feiyong.append("卧铺上中下铺位为随机出票，实际出票铺位以铁路局为准，驴妈妈暂收下铺位的价格。出票后根据实际票价退还差价，至实际支付给驴妈妈的银行卡中。<br/>");
			feiyong.append("请凭下单填写的身份证或护照到火车站或全国各代售点窗口取票，凭票进站，部分火车站可以刷身份证直接进站，以具体车站为准。");
		} else {
			if (null != viewPage && null != viewPage.getContents()
					&& null != viewPage.getContents().get("COSTCONTAIN")) {
				ViewContent view = (ViewContent) viewPage.getContents().get(
						"COSTCONTAIN");
				feiyong.append(view.getContentRn());
			}
		}
		// 预订须知
		StringBuffer yuding = new StringBuffer();
		if (productType.equals("TICKET")) {
			if (null != viewPage && null != viewPage.getContents()
					&& null != viewPage.getContents().get("ORDERTOKNOWN")) {
				ViewContent view = (ViewContent) viewPage.getContents().get(
						"ORDERTOKNOWN");
				yuding.append(view.getContentRn());
			}
		}
		// 退款说明
		StringBuffer tuikuan = new StringBuffer();

		if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)
				&& Constant.SUB_PRODUCT_TYPE.TRAIN.name()
						.equals(subProductType)) {
			tuikuan.append("1)产品预订失败退款：如果快铁驴行预订失败，驴妈妈将在确认失败后退款至原支付渠道，由银行将所有支付款项全额退回。 <br/>");
			tuikuan.append("2)产品差价退款：如果快铁驴行订单内含有的车票的实际票价低于您所支付的票价，驴妈妈将在确认实际票价后将差额退款至原支付渠道，由银行将差额款项退回，因出票原因产生的差额或全额退款，将在2小时内返还。<br/>");
			tuikuan.append("3)产品退票退款：若客户自行将快铁驴行订单内含有的车票进行退票，驴妈妈将在确认退票后，将差额部分退回原支付渠道，由银行将差额款项退回，退回时间为20个工作日。 <br/>");
			tuikuan.append("4)用户在火车站或代售窗口改签后，请在原列车班次的前一天17:00之前，致电我司客服（10106060）进行保险改签。游客可在改签提交后的5个工作日致电保险客服热线(太平保险：95589)查询到最终投保记录。如没有及时通知，保险还是原车次生效，一旦出险，后果自行承担。<br/>");
		} else if (null != viewPage && null != viewPage.getContents()
				&& null != viewPage.getContents().get("REFUNDSEXPLANATION")) {
			ViewContent view = (ViewContent) viewPage.getContents().get(
					"REFUNDSEXPLANATION");
			tuikuan.append(view.getContentRn());
		}

		if (!feiyong.toString().equals("")) {
			reserveNoticeMap.put("feiyong", feiyong.toString());
		}
		if (!yuding.toString().equals("")) {
			reserveNoticeMap.put("yuding", yuding.toString());
		}
		if (!tuikuan.toString().equals("")) {
			reserveNoticeMap.put("tuikuan", tuikuan.toString());
		}
		info.setReserveNoticeMap(reserveNoticeMap);
		return null;
	}

}
