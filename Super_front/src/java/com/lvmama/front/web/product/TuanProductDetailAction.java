package com.lvmama.front.web.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponBatchService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ViewBuyInfo;

@ParentPackage("json-default")
@Results( {		
	@Result(name = "distributionCoupon", location = "/WEB-INF/pages/tuangou/tuan_distribution_coupon.ftl", type = "freemarker"),
	@Result(name = "SUCCESS", location = "/WEB-INF/pages/tuangou/tuan_distribution_details.ftl", type = "freemarker"),
	@Result(name = "home", location = "http://www.lvmama.com", type = "redirect")
	
})
/**
 * 团购分销、产品详情页
 * 
 * @author zenglei
 *
 */
public class TuanProductDetailAction extends ProductBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 61457468375648543L;
	
	private List<DistributionTuanCoupon> tuanCouponLists = new ArrayList<DistributionTuanCoupon>();
	private ProdProduct prodProduct;
	private ProdProductBranch productBranch;
	private DistributorTuanInfo channelInfo = new DistributorTuanInfo();
	private String validEndTime; //批次号最晚预约时间
	
	private String isSell = "true";
	private Map<String, Object> data;
	private String errorProductType;
	private String errorSubProductType;
	
	private Long channelId;
	private String channelName;
	private String verifycode;
	
	private Long batchId; //批次ID
	
	/**
	 * 分销商impl接口
	 */
	private DistributionTuanService distributionTuanService;
	/**
	 * 券码impl接口
	 */
	private DistributionTuanCouponService distributionTuanCouponService;
	/**
	 * 券码批次impl接口
	 */
	private DistributionTuanCouponBatchService distributionTuanCouponBatchService;
	
	private ViewBuyInfo buyInfo;
	
	public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 根据单个团购券码查询  产品ID及渠道名
	 * 
	 * @return
	 */
    @Action("/product/distributionDetail")
	public String getProductDetailByTuanCoupon(){
    	if(tuanCouponLists != null && tuanCouponLists.size() > 0){
    		//跟据券码 查询产品及对应渠道方
    		DistributionTuanCoupon distributionTuan = distributionTuanCouponService.queryByCode(tuanCouponLists.get(0).getDistributionCouponCode());
    		if(distributionTuan != null && null != distributionTuan.getDistributionTuanCouponBatch().getProductId() && null != distributionTuan.getDistributionTuanCouponBatch().getOrdBatchCount()){
    			//验证通过  进入产品详情页
    			this.id = distributionTuan.getDistributionTuanCouponBatch().getProductId();
    			this.prodBranchId = distributionTuan.getDistributionTuanCouponBatch().getBranchId();
    			this.channelId = distributionTuan.getDistributionTuanCouponBatch().getDistributorTuanInfoId();
    			this.batchId = distributionTuan.getDistributionTuanCouponBatch().getDistributionBatchId();
    			return this.detail();
    		}else{ //验证未通过
    			return "home";
    		}
		}
		return "home";
	}
	/**
	 * 验证
	 */
    @Action("/product/ajaxValidateTuanCoupon")
    public void ajaxValidateTuanCoupon(){
    	String code = (String) getRequest().getSession().getAttribute(Constant.PAGE_USER_VALIDATE);
    	JSONResult result=new JSONResult(getResponse());
    	JSONObject obj=new JSONObject();
    	
    	//验证验证码
    	if(StringUtils.equalsIgnoreCase(code,verifycode)){
    		if(tuanCouponLists != null && tuanCouponLists.size() > 0){
    			for(int i = 0 ; i < tuanCouponLists.size() ;i++){
    				DistributionTuanCoupon distributionTuan = distributionTuanCouponService.queryByCode(tuanCouponLists.get(i).getDistributionCouponCode().toString());
					if(distributionTuan != null && distributionTuan.getDistributionTuanCouponBatch() != null){
						//　多券码校验时，应做券码有效性与一致性验证
	    				if(this.buyInfo != null && this.buyInfo.getProdBranchId().longValue() != distributionTuan.getDistributionTuanCouponBatch().getBranchId().longValue()){
							obj.put("success",false);
							obj.put("couponcode", tuanCouponLists.get(i).getDistributionCouponCode().toString());
	    					obj.put("msg","券码未绑定该产品");
	    					break;
						}
	    				if(this.batchId != null && !StringUtils.equals(this.batchId.toString(), distributionTuan.getDistributionTuanCouponBatch().getDistributionBatchId().toString())){
	    					obj.put("success",false);
							obj.put("couponcode", tuanCouponLists.get(i).getDistributionCouponCode().toString());
	    					obj.put("msg","不属于同一批次券码");
	    					break;
	    				}
	    				if(distributionTuan.isVolid()){
	    					obj.put("success",true);
	    				}else{
	    					obj.put("success",false);
	    					obj.put("couponcode", tuanCouponLists.get(i).getDistributionCouponCode().toString());
	    					obj.put("msg",Constant.DISTRIBUTION_TUAN_COUPON_STATUS.getCnName(distributionTuan.getStatus()));
	    					break;
	    				}
					}else{
						obj.put("success",false);
						obj.put("couponcode", tuanCouponLists.get(i).getDistributionCouponCode().toString());
    					obj.put("msg","券码不存在");
    					break;
					}
    			}
    		}
    	}else{
    		obj.put("success", false);
    		obj.put("msg", "验证码输入错误");
    	}
    	result.put("info",obj);
    	result.output();
    }
    /**
	 * 产品信息读取
	 * @return
	 */
    @Action("/tuanProduct/details")
	public String detail(){
		if (this.prodBranchId > 0 && this.channelId != null && this.batchId != null) {
			productBranch = pageService.getProdBranchByProdBranchId(this.prodBranchId);
			if(productBranch == null || productBranch.getProdProduct() == null){
				return "home";
			}else{
				this.prodProduct = productBranch.getProdProduct();
				if(!prodProduct.isOnLine()){				
					LOG.info("产品" + productId + "未上线，Sellable="+prodProduct.getOnLine());
					return getErrorProductPage(prodProduct);				
				} 
				if (!prodProduct.isSellable()) {		
					isSell="false";			
				}
			}
			//查询分销商
			this.channelInfo = distributionTuanService.getDistributorTuanById(this.channelId);
			if(this.channelInfo == null){
				log.info("未找到分销商=======channelId:"+this.channelId);
				return "home";
			}
			//查询批次
			DistributionTuanCouponBatch couponBatch = distributionTuanCouponBatchService.find(this.batchId);
			if(couponBatch != null){
				this.validEndTime = simpleDateFormat.format(couponBatch.getValidEndTime());
			}else{
				log.info("未找到批次号======batchID:"+this.batchId);
				return "home";
			}
 			return "SUCCESS";
		}	
		return "home";
	}
	/**
	 * 当删除下线的产品返回不同的搜索结果页
	 * @param errorProduct
	 * @return
	 */
	private String getErrorProductPage(ProdProduct errorProduct) {
		errorSubProductType = errorProduct.getSubProductType();
		errorProductType = errorProduct.getProductType();
		if (Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(errorProductType)) { // 门票
			return "TICKET";
		} else if (Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(errorProductType)) { // 酒店
			return "HOTEL";
		} else if (Constant.PRODUCT_TYPE.ROUTE.name().equalsIgnoreCase(errorProductType)) { // 线路
			if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(errorSubProductType)
					|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(errorSubProductType)) {
				return "ABROAD";
			}
			if (Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equalsIgnoreCase(errorSubProductType) 
					|| Constant.SUB_PRODUCT_TYPE.GROUP.name().equalsIgnoreCase(errorSubProductType)) {
				return "AROUND";
			}
			if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(errorSubProductType) 
					|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(errorSubProductType)) {
				return "DESTROUTE";
			}
			if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equalsIgnoreCase(errorSubProductType)) {
				return "FREETOUR";
			}
		}
		return "home";
	}
	
	@Action("/distribution/yuyue")
	public String yuyue(){
		return "distributionCoupon";
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public String getIsSell() {
		return isSell;
	}
	public void setIsSell(String isSell) {
		this.isSell = isSell;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public String getErrorProductType() {
		return errorProductType;
	}
	public void setErrorProductType(String errorProductType) {
		this.errorProductType = errorProductType;
	}
	public String getErrorSubProductType() {
		return errorSubProductType;
	}
	public void setErrorSubProductType(String errorSubProductType) {
		this.errorSubProductType = errorSubProductType;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
    
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	public List<DistributionTuanCoupon> getTuanCouponLists() {
		return tuanCouponLists;
	}
	public void setTuanCouponLists(List<DistributionTuanCoupon> tuanCouponLists) {
		this.tuanCouponLists = tuanCouponLists;
	}
	public ProdProductBranch getProductBranch() {
		return productBranch;
	}
	public void setProductBranch(ProdProductBranch productBranch) {
		this.productBranch = productBranch;
	}
	public DistributorTuanInfo getChannelInfo() {
		return channelInfo;
	}
	public void setChannelInfo(DistributorTuanInfo channelInfo) {
		this.channelInfo = channelInfo;
	}
	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}
	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}
	public void setDistributionTuanService(
			DistributionTuanService distributionTuanService) {
		this.distributionTuanService = distributionTuanService;
	}
	public void setDistributionTuanCouponService(
			DistributionTuanCouponService distributionTuanCouponService) {
		this.distributionTuanCouponService = distributionTuanCouponService;
	}
	public void setDistributionTuanCouponBatchService(
			DistributionTuanCouponBatchService distributionTuanCouponBatchService) {
		this.distributionTuanCouponBatchService = distributionTuanCouponBatchService;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}
}
