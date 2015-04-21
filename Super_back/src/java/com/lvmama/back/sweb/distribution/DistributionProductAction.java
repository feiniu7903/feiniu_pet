package com.lvmama.back.sweb.distribution;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.kenai.jffi.Array;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributionRakeBack;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.distribution.DistributionRakeBackService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.opensymphony.oscache.util.StringUtil;

@Results({ @Result(name = "distributionProductList", location = "/WEB-INF/pages/back/distribution/distributionProd/distributionProductList.jsp"),
	@Result(name = "rakeBackDetail", location = "/WEB-INF/pages/back/distribution/distributionProd/distributorProductRakeBackDetail.jsp"),	
	@Result(name = "cassBackDetail", location = "/WEB-INF/pages/back/distribution/distributionProd/distributorProductCassDetail.jsp"),	
	@Result(name = "distributorSet", location = "/WEB-INF/pages/back/distribution/distributionProd/distributorSet.jsp"),	
	@Result(name = "distributorProductDetail", location = "/WEB-INF/pages/back/distribution/distributionProd/distributorProductDetail.jsp") })
@ParentPackage("json-default")
public class DistributionProductAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3829004554800584394L;
	/** 
	 * 关于分销的服务 
	 * */
	private DistributionService distributionService;
	private DistributionProductService distributionProductService;
	private ProdProductBranchService prodProductBranchService;
	private ComLogService comLogRemoteService;
	private DistributionRakeBackService distributionRakeBackService;
	/** 
	 * 用于页面展示的分销产品集合
	 *  */
	private List<ProdProductBranch> productBranchList;
	private Page<ProdProductBranch> productBranchPage = new Page<ProdProductBranch>();
	/** 
	 * 用于页面展示的分销商信息集合
	 * */
	private List<DistributorInfo> distributorList;
	/**
	 * 操作页面类型：WHITE_LIST 分销、BLACK_LIST 黑名单、RELEASE_LIST 解除黑名单
	 */
	private String operateType;
	
	private Long productBranchId;
	private Long productId;
	private String productName;
	private String filialeName;
	private String[] productTypeList;
	
	private Long distributorInfoId;
	private Long distributionProdRakebackId;
	private List<Long> distributorInfoIds;
	private FILIALE_NAME[] filialeList;
	private String memo;
	private String profit;
	private String distributorName;
	private String rakeBackRate;
	private String rateVolid;
	private TopicMessageProducer productMessageProducer;

	
	@Action(value ="/distribution/distributionProd/distributionProductList")
	public String distributionProductList() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("isAperiodic", "false");
		param.put("prodBranchId", productBranchId);
		param.put("productId", productId);
		if(productName != null) {
			try {
				productName = new String(productName.getBytes("ISO-8859-1"), this.getRequest().getCharacterEncoding());
				param.put("productName", productName);
			} catch (UnsupportedEncodingException e) {
			}
		}
		param.put("filialeName", filialeName);
		if(distributorInfoId != null) {
			param.put("distributorInfoId", distributorInfoId);
//			param.put("volid", "true");
		}
//		if("BLACK_LIST".equals(operateType)) {
//			param.put("volid", "false");
//		}
		if(productTypeList != null && productTypeList.length > 0) {
			param.put("productTypeList", productTypeList);
		}
		filialeList = Constant.FILIALE_NAME.values();
		distributorList = distributionService.getAllDistributors();
		
		productBranchPage.setTotalResultSize(distributionProductService.getDistributionProductBranchCount(param));
		productBranchPage.buildUrl(getRequest());
		productBranchPage.setCurrentPage(super.page);
		param.put("start", productBranchPage.getStartRows());
		param.put("end", productBranchPage.getEndRows());
		if(productBranchPage.getTotalResultSize() > 0) {
			productBranchPage.setItems(this.distributionProductService.getProductBranchList(param, operateType));
		}
		return "distributionProductList";
	}
	
	@Action(value ="/distribution/distributionProd/distributorProductDetail")
	public String distributorProductDetail() {
		if (!StringUtil.isEmpty(operateType)) {
			distributorList = distributionService.selectDistributorListByType(
					productBranchId, operateType);
			// 获取返佣点
			for (DistributorInfo info : distributorList) {
				DistributionRakeBack rake = distributionRakeBackService
						.queryDistributionRakeBack(productBranchId,
								info.getDistributorInfoId());
				if (null != rake) {
					info.setRakeBackRate(rake.getRakeBackRate().toString());
				}
			}
		}
		// 根据销售类别id获取开始当前日期到结束日期及销售价格
		Date specDateStart = new Date();
		Date specDateEnd = null;
		List<TimePrice> prodTimePriceList = prodProductBranchService
				.selectProdTimePriceByProdBranchId(productBranchId, specDateStart, null);
		if (null != prodTimePriceList && prodTimePriceList.size() > 0) {
			specDateEnd = prodTimePriceList.get(prodTimePriceList.size() - 1).getSpecDate();
			
		}
		// 根据销售类别id获取采购类别id
		List<List<TimePrice>> settlePriceList = new ArrayList<List<TimePrice>>();
		List<ProdProductBranchItem> prodPorductBranchItemList = prodProductBranchService
				.selectBranchItemByBranchId(productBranchId);
		for (ProdProductBranchItem prodProductBranchItem : prodPorductBranchItemList) {
			Long metaBranchId = prodProductBranchItem.getMetaBranchId();
			List<TimePrice> metaTimePriceList = prodProductBranchService
					.selectMetaTimePriceByMetaBranchId(metaBranchId, specDateStart, specDateEnd);
			if (metaTimePriceList.size() == prodTimePriceList.size()) {
				settlePriceList.add(metaTimePriceList);
			} else {
				settlePriceList.clear();
				break;
			}
		}
		
		// 根据时间价格获取最大价格差
		Double settlementPrice = new Double(0);
		Double profitPrice = new Double(0);
		Double sellPrice = new Double(0);
		if (settlePriceList.size() > 0) {
			for (int i = 0; i < prodTimePriceList.size(); i++) {
				TimePrice prodTimePrice = prodTimePriceList.get(i);
				sellPrice = Double.valueOf(prodTimePrice.getPrice());
				// 有多个采购产品打包的话结算价相加
				Double tempSettlementPrice = new Double(0);
				for (int j = 0; j < settlePriceList.size(); j++) {
					List<TimePrice> tempList  = settlePriceList.get(j);
					Long quantity = prodPorductBranchItemList.get(j).getQuantity();
					tempSettlementPrice += Double.valueOf(tempList.get(i).getSettlementPrice() * quantity);
				}
				Double tempPrice = sellPrice - tempSettlementPrice;
				if (tempPrice > profitPrice) {
					profitPrice = tempPrice;
					settlementPrice = tempSettlementPrice;
				}
			}
			
			// 计算利率
			if (0L != sellPrice) {
				DecimalFormat df = new DecimalFormat("#");
				Double rst = profitPrice / sellPrice * 100;
				profit = df.format(rst);
			} else {
				profit = "0";
			}
		}
		return "distributorProductDetail";
	}
	
	@Action(value ="/distribution/distributionProd/distributorSet")
	public String distributorSet() {
		List<DistributorInfo> allDistributorInfos = distributionService.getAllDistributors();
		List<DistributorInfo> blackDistributorInfos = distributionService.selectByProductBranchIdAndVolid(productBranchId, "false");
		if("WHITE_LIST".equalsIgnoreCase(operateType)){
			List<DistributorInfo> whiteDistributorInfos = distributionService.selectByProductBranchIdAndVolid(productBranchId, "true");
			if(allDistributorInfos!=null && blackDistributorInfos!=null){
				allDistributorInfos.removeAll(blackDistributorInfos);
			}
			if(allDistributorInfos!=null && CollectionUtils.isNotEmpty(whiteDistributorInfos)){
				for(DistributorInfo info:allDistributorInfos){
					for(DistributorInfo whiteInfo:whiteDistributorInfos){
						if(info.getDistributorInfoId().equals(whiteInfo.getDistributorInfoId())){
							DistributionRakeBack distributionRakeBack = distributionRakeBackService.queryDistributionRakeBack(productBranchId,info.getDistributorInfoId());
							if(distributionRakeBack!=null){
								info.setRateVolid(distributionRakeBack.getRateVolid());
								info.setRakeBackRate(distributionRakeBack.getRakeBackRate()+"");
							}
							info.setChecked("true");
						}
					}
					if(Constant.DISTRIBUTOR.QUNA_TICKET.getCode().equalsIgnoreCase(info.getDistributorCode())){
						DistributionProduct distributionProduct = distributionProductService.getDistributionProductByBranchId(productBranchId, info.getDistributorInfoId());
						if(distributionProduct!=null){
							distributionProdRakebackId = distributionProduct.getCommentsCashback();
						}
					}
					
				}
			}
		}else if("BLACK_LIST".equalsIgnoreCase(operateType)){
			if(allDistributorInfos!=null && blackDistributorInfos!=null){
				allDistributorInfos.removeAll(blackDistributorInfos);
			}
			List<DistributorInfo> whiteDistributorInfos = distributionService.selectByProductBranchIdAndVolid(productBranchId, "true");
			if(allDistributorInfos!=null && CollectionUtils.isNotEmpty(whiteDistributorInfos)){
				for(DistributorInfo info:allDistributorInfos){
					for(DistributorInfo whiteInfo:whiteDistributorInfos){
						if(info.getDistributorInfoId().equals(whiteInfo.getDistributorInfoId())){
							DistributionRakeBack distributionRakeBack = distributionRakeBackService.queryDistributionRakeBack(productBranchId,info.getDistributorInfoId());
							if(distributionRakeBack!=null){
								info.setRateVolid(distributionRakeBack.getRateVolid());
								info.setRakeBackRate(distributionRakeBack.getRakeBackRate()+"");
							}
						}
					}
				}
			}
		}else if("RELEASE_LIST".equalsIgnoreCase(operateType)){
			if(CollectionUtils.isNotEmpty(blackDistributorInfos)){
				for(DistributorInfo blackInfo:blackDistributorInfos){
					DistributionRakeBack distributionRakeBack = distributionRakeBackService.queryDistributionRakeBack(productBranchId,blackInfo.getDistributorInfoId());
					if(distributionRakeBack!=null){
						blackInfo.setRateVolid(distributionRakeBack.getRateVolid());
						blackInfo.setRakeBackRate(distributionRakeBack.getRakeBackRate()+"");
					}
					//blackInfo.setChecked("true");
				}
			}
			allDistributorInfos = blackDistributorInfos;
		}
		
		distributorList = allDistributorInfos;
		// 根据销售类别id获取开始当前日期到结束日期及销售价格
		Date specDateStart = new Date();
		Date specDateEnd = null;
		List<TimePrice> prodTimePriceList = prodProductBranchService
				.selectProdTimePriceByProdBranchId(productBranchId, specDateStart, null);
		if (null != prodTimePriceList && prodTimePriceList.size() > 0) {
			specDateEnd = prodTimePriceList.get(prodTimePriceList.size() - 1).getSpecDate();
			
		}
		// 根据销售类别id获取采购类别id
		List<List<TimePrice>> settlePriceList = new ArrayList<List<TimePrice>>();
		List<ProdProductBranchItem> prodPorductBranchItemList = prodProductBranchService
				.selectBranchItemByBranchId(productBranchId);
		for (ProdProductBranchItem prodProductBranchItem : prodPorductBranchItemList) {
			Long metaBranchId = prodProductBranchItem.getMetaBranchId();
			List<TimePrice> metaTimePriceList = prodProductBranchService
					.selectMetaTimePriceByMetaBranchId(metaBranchId, specDateStart, specDateEnd);
			if (metaTimePriceList.size() == prodTimePriceList.size()) {
				settlePriceList.add(metaTimePriceList);
			} else {
				settlePriceList.clear();
				break;
			}
		}
		
		profit = "0";
		return "distributorSet";
	}
	
	@Action(value ="/distribution/distributionProd/rakeBackDetail")
	public String rakeBackDetail() {
		try {
			rateVolid="true";
			distributorName = URLDecoder.decode(distributorName, "UTF-8");
			DistributionRakeBack rake = distributionRakeBackService.queryDistributionRakeBack(productBranchId,distributorInfoId);
			if(rake!=null&&rake.getDistributionProdRakebackId()!=null){
				rateVolid=rake.getRateVolid();
				rakeBackRate = rake.getRakeBackRate()+"";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "rakeBackDetail";
	}
	
	/**
	 * 保存反佣点
	 */
	@Action(value = "/distribution/distributionProd/saveRakeBack")
	public void saveRakeBack() {
		JSONObject json = new JSONObject();
		try {
			DistributionRakeBack rake = distributionRakeBackService
					.queryDistributionRakeBack(productBranchId,distributorInfoId);
			rateVolid = "true".equalsIgnoreCase(rateVolid)?rateVolid:"false";
			if (null != rake) {
				rake.setRakeBackRate(Long.valueOf(rakeBackRate));
				rake.setRateVolid(rateVolid);
			} else {
				rake = new DistributionRakeBack();
				rake.setRakeBackRate(Long.valueOf(rakeBackRate));
				rake.setRateVolid(rateVolid);
				rake.setDistributorInfoId(distributorInfoId);
				rake.setProductBranchId(productBranchId);
			}
			distributionRakeBackService.save(rake);
			DistributorInfo info = distributionService.selectByDistributorId(distributorInfoId);
			if(info!=null && Constant.DISTRIBUTOR.QUNA_TICKET.name().equalsIgnoreCase(info.getDistributorCode())){
				DistributionProduct distributionProduct = new DistributionProduct();
				distributionProduct.setDistributorInfoId(distributorInfoId);
				distributionProduct.setProductBranchId(productBranchId);
				distributionProductService.autoUpdateCommentsCashback(distributionProduct);
				comLogRemoteService.insert("DISTRIBUTOR_PRODUCT", null, distributionProduct.getProductBranchId(), "system", "WHITE_LIST", "修改分销产品", "修改返现系统自动自动修改评论返现值", null);
			}
			json.put("flag", "success");
		} catch (Exception e) {
			json.put("flag", "fail");
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		sendAjaxMsg(json.toString());
	}
	
	@Action(value ="/distribution/distributionProd/cassBackDetail")
	public String cassBackDetail() {
		try {
			rateVolid="true";
			DistributorInfo info = distributionService.selectByDistributorId(distributorInfoId);
			if(info==null || !Constant.DISTRIBUTOR.QUNA_TICKET.getCode().equalsIgnoreCase(info.getDistributorCode())){
				return null;
			}
			distributorName = URLDecoder.decode(distributorName, "UTF-8");
			DistributionProduct rake = distributionProductService.getDistributionProductByBranchId(productBranchId, distributorInfoId);
			if(rake!=null){
				rateVolid=rake.getNeedAutoUpdateCashBack();
				rakeBackRate = rake.getCommentsCashback()==null?"":rake.getCommentsCashback()+"";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "cassBackDetail";
	}
	
	/**
	 * 保存反佣点
	 */
	@Action(value = "/distribution/distributionProd/saveCassBack")
	public void saveCassBack() {
		JSONObject json = new JSONObject();
		try {
			if(rakeBackRate==null || Long.valueOf(rakeBackRate)<0){
				json.put("flag", "fail");
				json.put("msg", "返现值不合法");
			}else{
				DistributionProduct distributionProduct = distributionProductService.getDistributionProductByBranchId(productBranchId, distributorInfoId);
				if(distributionProduct==null)
					throw new Exception("请先绑定该分销商到此产品");
				String oldRateVolid = distributionProduct.getNeedAutoUpdateCashBack();
				rateVolid = "false".equalsIgnoreCase(rateVolid)?rateVolid:"true";
				distributionProduct.setNeedAutoUpdateCashBack(rateVolid);
				if("false".equalsIgnoreCase(rateVolid)){
					distributionProduct.setCommentsCashback(Long.valueOf(rakeBackRate));
				}
				distributionProductService.updateCommentsCashback(distributionProduct);
				productMessageProducer.sendMsg(MessageFactory.newDistribuionCashBackUpdateMessage(distributionProduct.getProductId()));
				distributorInfoIds = new ArrayList<Long>();
				distributorInfoIds.add(distributorInfoId);
				String content = "修改返现值 为:"+ distributionProduct.getCommentsCashback() + " 更新类型:" + ("false".equalsIgnoreCase(rateVolid)?"手动":"自动") +"   "+ memo;
				if(distributionProduct.getCommentsCashback()==null){
					content = "更新类型:" + ("false".equalsIgnoreCase(rateVolid)?"手动":"自动") +"   "+ memo;
				}
				saveLog(productBranchId, distributorInfoIds, content, "WHITE_LIST");
				if("false".equalsIgnoreCase(oldRateVolid) && "true".equalsIgnoreCase(rateVolid)){
					distributionProductService.autoUpdateCommentsCashback(distributionProduct);
					comLogRemoteService.insert("DISTRIBUTOR_PRODUCT", null, distributionProduct.getProductBranchId(), "system", "WHITE_LIST", "", "系统自动更新评论返现值", null);
				}
				json.put("flag", "success");
			}
		} catch (Exception e) {
			json.put("flag", "fail");
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		sendAjaxMsg(json.toString());
	}
	
	
	@Action(value ="/distribution/distributionProd/updateAllCashBack")
	public void UpdteCashBack(){
		sendAjaxMsg("返现更新已开始！");
		DistributorInfo distributorInfo = distributionService.selectByDistributorCode(Constant.DISTRIBUTOR.QUNA_TICKET.getCode());
		if(distributorInfo!=null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("volid", "true");
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			List<DistributionProduct> list = distributionProductService.selectAllByParams(params);
			for(DistributionProduct distributionProduct:list){
				if(!"false".equalsIgnoreCase(distributionProduct.getNeedAutoUpdateCashBack())){
					try {
						distributionProduct.setNeedAutoUpdateCashBack("true");
						distributionProductService.autoUpdateCommentsCashback(distributionProduct);
						comLogRemoteService.insert("DISTRIBUTOR_PRODUCT", null, distributionProduct.getProductBranchId(), "system", "WHITE_LIST", "修改分销产品", "系统自动更新评论返现值", null);
					} catch (Exception e) {
						log.error("AutoUpdateCommentsCashbackJob Exception",e);
					}
				}
			}
		}
	}
	
	
	@Action(value ="/distribution/distributionProd/saveDistributorProduct")
	public void saveDistributorProduct() {
		ResultHandle result = new ResultHandle();
		if(distributorInfoIds == null || distributorInfoIds.isEmpty()) {
			result.setMsg("至少要有一个分销商");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		if("WHITE_LIST".equals(operateType)) {
			ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(productBranchId);
			if(branch == null) {
				result.setMsg("产品类别已经删除");
				this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
				return;
			}
			distributionProductService.saveDistributionProduct(distributorInfoIds, branch.getProductId(), productBranchId);
			initCommentsCashback(distributorInfoIds,productBranchId);
			saveLog(productBranchId, distributorInfoIds, "分销产品", operateType);
		} else if("BLACK_LIST".equals(operateType)) {
			distributionProductService.updateDistributionProductVolid(productBranchId, distributorInfoIds, "false");
			updateCommentsCashback(distributorInfoIds,productBranchId);
			saveLog(productBranchId, distributorInfoIds, "加入黑名单 理由：" + memo, operateType);
		} else if("RELEASE_LIST".equals(operateType)) {
			distributionProductService.updateDistributionProductVolid(productBranchId, distributorInfoIds, "true");
			initCommentsCashback(distributorInfoIds,productBranchId);
			saveLog(productBranchId, distributorInfoIds, "解除黑名单 理由：" + memo, operateType);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	private void updateCommentsCashback(List<Long> distIds,Long branchId){
		for(Long disId:distIds){
			DistributorInfo distributor = distributionService.selectByDistributorId(disId);
			if(distributor!=null && Constant.DISTRIBUTOR.QUNA_TICKET.name().equalsIgnoreCase(distributor.getDistributorCode())){
				DistributionProduct distributionProduct = new DistributionProduct();
				distributionProduct.setDistributorInfoId(disId);
				distributionProduct.setProductBranchId(branchId);
				distributionProduct.setNeedAutoUpdateCashBack("true");
				distributionProduct.setCommentsCashback(0L);
				distributionProductService.updateCommentsCashback(distributionProduct);
			}
		}
	}
	private void initCommentsCashback(List<Long> distIds,Long branchId){
		for(Long disId:distIds){
			DistributorInfo distributor = distributionService.selectByDistributorId(disId);
			if(distributor!=null && Constant.DISTRIBUTOR.QUNA_TICKET.name().equalsIgnoreCase(distributor.getDistributorCode())){
				DistributionProduct distributionProduct = new DistributionProduct();
				distributionProduct.setDistributorInfoId(disId);
				distributionProduct.setProductBranchId(branchId);
				distributionProductService.autoUpdateCommentsCashback(distributionProduct);
				comLogRemoteService.insert("DISTRIBUTOR_PRODUCT", null, distributionProduct.getProductBranchId(), "system", "WHITE_LIST", "修改分销产品", "系统自动初始化评论返现值", null);
			}
		}
	}
	private void saveLog(Long branchId, List<Long> distributorIds, String msg, String type) {
		StringBuilder content = new StringBuilder(msg);
		content.append(" ");
		for(Long id : distributorIds) {
			DistributorInfo d = distributionService.selectByDistributorId(id);
			content.append(d.getDistributorName());
			content.append(",");
		}
		comLogRemoteService.insert("DISTRIBUTOR_PRODUCT", null, branchId, getSessionUser().getUserName(), type, "", content.toString(), null);
	}
	
	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public List<ProdProductBranch> getProductBranchList() {
		return productBranchList;
	}

	public void setProductBranchList(List<ProdProductBranch> productBranchList) {
		this.productBranchList = productBranchList;
	}

	public List<DistributorInfo> getDistributorList() {
		return distributorList;
	}

	public void setDistributorList(List<DistributorInfo> distributorList) {
		this.distributorList = distributorList;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Long getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public Long getProductBranchId() {
		return productBranchId;
	}

	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public List<Long> getDistributorInfoIds() {
		return distributorInfoIds;
	}

	public void setDistributorInfoIds(List<Long> distributorInfoIds) {
		this.distributorInfoIds = distributorInfoIds;
	}

	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public ProdProductBranchService getProdProductBranchService() {
		return prodProductBranchService;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String[] getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(String[] productTypeList) {
		this.productTypeList = productTypeList;
	}

	public FILIALE_NAME[] getFilialeList() {
		return filialeList;
	}

	public void setFilialeList(FILIALE_NAME[] filialeList) {
		this.filialeList = filialeList;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Page<ProdProductBranch> getProductBranchPage() {
		return productBranchPage;
	}

	public void setProductBranchPage(Page<ProdProductBranch> productBranchPage) {
		this.productBranchPage = productBranchPage;
	}

	public DistributionProductService getDistributionProductService() {
		return distributionProductService;
	}

	public void setDistributionProductService(DistributionProductService distributionProductService) {
		this.distributionProductService = distributionProductService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getRakeBackRate() {
		return rakeBackRate;
	}

	public void setRakeBackRate(String rakeBackRate) {
		this.rakeBackRate = rakeBackRate;
	}

	
	public String getRateVolid() {
		return rateVolid;
	}

	public void setRateVolid(String rateVolid) {
		this.rateVolid = rateVolid;
	}

	public DistributionRakeBackService getDistributionRakeBackService() {
		return distributionRakeBackService;
	}

	public void setDistributionRakeBackService(
			DistributionRakeBackService distributionRakeBackService) {
		this.distributionRakeBackService = distributionRakeBackService;
	}

	public Long getDistributionProdRakebackId() {
		return distributionProdRakebackId;
	}

	public void setDistributionProdRakebackId(Long distributionProdRakebackId) {
		this.distributionProdRakebackId = distributionProdRakebackId;
	}

	public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

}
