package com.lvmama.back.sweb.prod;

import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.utils.SeckillUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SeckillRuleVO;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/prod/prod_business_coupon_list.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/prod_business_coupon_list_auditing_show.jsp")
})
public class ProdBusinessCouponListAction extends ProductAction{
	/**
	 * 日志
	 */
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 优惠策略远程服务
     */
	private BusinessCouponService businessCouponService;
	/**秒杀规则远程服务*/
	private ProdSeckillRuleService prodSeckillRuleService;
	/**
	 * 日志远程服务
	 */
	protected ComLogService comLogService;
	/**
	 * 优惠绑定类型(PROD"产品"/BRANCH"产品类别")
	 */
	private String couponTarget;
	
	/**
	 * 优惠类型(EARLY"早定早惠"/MORE"多订多惠"SALE"特卖会");取值为 com.lvmama.comm.vo.BUSINESS_COUPON_TYPE中定义的值其中之一
	 */
	private String couponType;
	/**
	 * 优惠状态
	 */
	private String valid;
	/**
	 * 新增或修改策略对象
	 */
	private BusinessCoupon businessCoupon;
	
	/**
	 * 优惠策略主键
	 */
	private Long businessCouponId;
	/**
	 * 产品类型：销售产品\采购产品
	 */
	private String metaType;
	/**
	 * 产品服务远程接口
	 */
	private ProdProductBranchService prodProductBranchService;
	private MetaProductBranchService metaProductBranchService;
	/**
	 * 销售产品Id
	 */
	protected Long metaProductId;
	/**
	 * 优惠绑定条件
	 */
	private Map<Object,Object> couponTargetMap=new HashMap<Object,Object>();
	private MetaProductService metaProductService;
	private MetaProduct metaProduct;
	/**
	 * 类别区别表示
	 * 用于区别特卖会(秒杀SALE等) 
	 */
	private String couponTypeMark;
	/**
	 * Tag Service
	 */
	private ProdProductTagService prodProductTagService;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
 
	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	public void setMetaProduct(MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

	public ProdBusinessCouponListAction() {
		super();
		setMenuType("coupon");
	}
    @Action(value="/prod/queryProdBusinessCouponListAuditingShow")
    public String queryProdBusinessCouponListAuditingShow(){
        String  resultName = this.goEdit();
        if(resultName.equals(PRODUCT_EXCEPTION_PAGE)){
            return resultName;
        }else{
            return "auditingShow";
        }
    }
	/**
	 * 根据查询条件查询符合条件的优惠券(活动)结果集.
	 * @return
	 */
	@Action(value="/prod/queryProdBusinessCouponList")
	@Override
	public String goEdit() {
		if("SALES".equals(metaType) && !doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		if("META".equals(metaType) && productId == null){
			return PRODUCT_EXCEPTION_PAGE;
		}
		
	if(couponType!=null&&couponType.equalsIgnoreCase(Constant.BUSINESS_COUPON_TYPE.SALE.name())){//特卖会查询
			Map<String,Object> searchConds = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(metaType)) {
				searchConds.put("metaType", metaType);
			}
			if (StringUtils.isNotBlank(couponType)) {
				searchConds.put("couponType", couponType);
			}
			if (StringUtils.isNotBlank(couponTarget)) {
				searchConds.put("branchId", couponTarget);
			}
			Integer totalRowCount = prodSeckillRuleService.selectSeckillRuleRowCount(searchConds);
			pagination = super.initPagination();
			pagination.setTotalRecords(totalRowCount);
			searchConds.put("_startRow", pagination.getFirstRow());
			searchConds.put("_endRow", pagination.getLastRow());
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
			//优惠类型为SALE(特卖会的所有)
			searchConds.put("productId", productId);//查询该产品的特卖会
			List<BusinessCoupon> businessCouponList = businessCouponService.selectByParam(searchConds);
			//取出产品下的所有类别
			List<ProdProductBranch> productBranchList = prodProductBranchService.getProductBranchByProductId(productId, "false");
			for(ProdProductBranch prodProductBranch:productBranchList){
				//优惠绑定条件--设置初始值
				couponTargetMap.put(prodProductBranch.getProdBranchId(), prodProductBranch.getBranchName());
			}
			couponTypeMark =Constant.BUSINESS_COUPON_TYPE.SALE.name();
			List<BusinessCoupon> newBusinessCouponList = new ArrayList<BusinessCoupon>();
			//查询页面显示字段VO
			for (BusinessCoupon businessCoupon : businessCouponList) {
				List<SeckillRuleVO> skrVOList = new ArrayList<SeckillRuleVO>();
				//根据BranchId取到对应的BranchName
				setCurrentBindBranchNames(productBranchList,null,businessCoupon);
				//查出秒杀规则表中的字段
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("businessCouponId",businessCoupon.getBusinessCouponId());
				param.put("branchId", businessCoupon.getBranchId());
				List<ProdSeckillRule> prodSkrList = prodSeckillRuleService.selectByParam(param);
				for (ProdSeckillRule prodSeckillRule : prodSkrList) {
					SeckillRuleVO skrvo = new SeckillRuleVO();
					//秒杀规则部分字段
					skrvo.setId(prodSeckillRule.getId());
					skrvo.setBusinessCouponId(businessCoupon.getBusinessCouponId());
					skrvo.setStartTime(prodSeckillRule.getStartTime());
					skrvo.setEndTime(prodSeckillRule.getEndTime());
					skrvo.setReducePrice(prodSeckillRule.getReducePrice());
					skrvo.setCreateTime(prodSeckillRule.getCreateTime());
					skrvo.setIpBuyLimit(prodSeckillRule.getIpBuyLimit());
					skrvo.setUserBuyLimit(prodSeckillRule.getUserBuyLimit());
					skrvo.setPayValidTime(prodSeckillRule.getPayValidTime());
					skrVOList.add(skrvo);
				}
				//多条规则，按开始时间排序
				SeckillUtils<SeckillRuleVO> skUtil= new SeckillUtils<SeckillRuleVO>();
				if(skrVOList.size()>1){
					skUtil.sortByMethod(skrVOList, "getStartTime", false);
				}
				businessCoupon.setSeckillRuleVOs(skrVOList);
				newBusinessCouponList.add(businessCoupon);
			}
			pagination.setRecords(newBusinessCouponList);
			return goAfter();
			
		}else{//多订多惠，早定早惠查询,默认查询
			Map<String,Object> searchConds = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(metaType)) {
				searchConds.put("metaType", metaType);
			}
			if (StringUtils.isNotBlank(couponType)) {
				searchConds.put("couponType", couponType);
			}
			if (StringUtils.isNotBlank(couponTarget)) {
				searchConds.put("branchId", couponTarget);
			}
			if (StringUtils.isNotBlank(valid)) {
				if("notStart".equals(valid)){//订单状态--未开始
					searchConds.put("notStart", valid);
				}else if("process".equals(valid)){//订单状态--进行中
					searchConds.put("process", valid);
				}else if("over".equals(valid)){//订单状态--已结束
					searchConds.put("over", valid);
				}else if("false".equals(valid)){//订单状态--关闭
					searchConds.put("valid", valid);
				}
			}
			searchConds.put("productId", productId);
			Integer totalRowCount = businessCouponService.selectBusinessCouponRowCount(searchConds);
			pagination = super.initPagination();
			pagination.setTotalRecords(totalRowCount);
			searchConds.put("_startRow", pagination.getFirstRow());
			searchConds.put("_endRow", pagination.getLastRow());
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
			List<BusinessCoupon> businessCouponList = businessCouponService.selectByParam(searchConds);
			List<BusinessCoupon> newBusinessCouponList = new ArrayList<BusinessCoupon>();
			//优惠绑定
			//1.采购产品
			if("META".equals(metaType)){
				//销售产品用于页面显示信息
				metaProduct=metaProductService.getMetaProduct(productId);
				//取出产品下的所有类别
				List<MetaProductBranch> metaBranchList = metaProductBranchService.selectBranchListByProductId(productId, "false");
				for(MetaProductBranch metaProductBranch:metaBranchList){
					//优惠绑定条件--设置初始值(销售产品)
					couponTargetMap.put(metaProductBranch.getMetaBranchId(), metaProductBranch.getBranchName());
				}
				//根据BranchId取到对应的BranchName
				for(BusinessCoupon businessCoupon:businessCouponList){
					setCurrentBindBranchNames(null,metaBranchList,businessCoupon);
					newBusinessCouponList.add(businessCoupon);
				}
			}else if("SALES".equals(metaType)){//2.销售产品
				//取出产品下的所有类别
				List<ProdProductBranch> productBranchList = prodProductBranchService.getProductBranchByProductId(productId, "false");
				for(ProdProductBranch prodProductBranch:productBranchList){
					//优惠绑定条件--设置初始值(采购产品)
					couponTargetMap.put(prodProductBranch.getProdBranchId(), prodProductBranch.getBranchName());
				}
				//根据BranchId取到对应的BranchName
				for(BusinessCoupon businessCoupon:businessCouponList){
					setCurrentBindBranchNames(productBranchList,null,businessCoupon);
					newBusinessCouponList.add(businessCoupon);
				}
			}
			pagination.setRecords(newBusinessCouponList);
			return goAfter();
		
		}
	}
	
	@Override
	public void save() {
	} 
	
	/**
	 * 设置当前绑定产品的名称，根据BranchId转换成BranchName
	 * @param productBranchList
	 * @param mataBranchList
	 * @param businessCoupon
	 */
	private void setCurrentBindBranchNames(List<ProdProductBranch> productBranchList,List<MetaProductBranch> mataBranchList,BusinessCoupon businessCoupon){
		//销售产品
		if(productBranchList!=null){
			for(ProdProductBranch prodProductBranch:productBranchList){
				if(prodProductBranch.getProdBranchId().compareTo(businessCoupon.getBranchId())==0){
					businessCoupon.setCurrentBindBranchNames(prodProductBranch.getBranchName());
				}
			}
		}
		//采购产品
		if(mataBranchList!=null){
			for(MetaProductBranch metaProductBranch:mataBranchList){
				if(metaProductBranch.getMetaBranchId().compareTo(businessCoupon.getBranchId())==0){
					businessCoupon.setCurrentBindBranchNames(metaProductBranch.getBranchName());
				}
			}
		}
	}
	/**
	 * 删除特卖活动
	 */
	@Action(value="/prod/delProdSaleRule")
	public void delProdSaleRule(){
		Map<String, Object> param = new HashMap<String, Object>();
		if(null == businessCouponId){
			param.put("success", false);
			param.put("errorMessage", "找不到相应的优惠批次");
		}else{
			//删除优惠活动表
			businessCouponService.deleteFromBusinessCoupon(businessCouponId);
			//删除秒杀规则表
			prodSeckillRuleService.deleteFromProdseckillrule(businessCouponId);
			param.put("success", true);
			param.put("successMessage", "删除成功");
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	/**
	 * 取反优惠批次的有效状态，即原本有效的更新为无效，无效的变为有效
	 */
	@Action(value="/prod/editValidStatus")
	public void editBusinessCouponValidStatus() {
		Map<String, Object> param = new HashMap<String, Object>();
		BusinessCoupon businessCoupon = null == businessCouponId ? null : businessCouponService.selectByPK(businessCouponId);
		
		if (null == businessCoupon) {
			param.put("success", false);
			param.put("errorMessage", "找不到相应的优惠批次");
		} else {
			if ("true".equals(businessCoupon.getValid())) {
				businessCoupon.setValid("false");
			} else {
				businessCoupon.setValid("true");
			}
			businessCouponService.updateByPrimaryKey(businessCoupon);
			
			//系统自动添加早/多Tag==========Begin==========
			if(Constant.BUSINESS_COUPON_META_TYPE.SALES.getCode().equals(businessCoupon.getMetaType())){
				log.info("Tag Early/More ProductId:"+businessCoupon.getProductId());
				List<Long> productIds=new ArrayList<Long>();
				productIds.add(businessCoupon.getProductId());
				List<ProdProductTag> prodProductTags=null;
				//早订早慧
				if(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode().equals(businessCoupon.getCouponType())){
					ProdProductTag prodProductTag=businessCouponService.checkProductTag(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode(), businessCoupon.getProductId());
					if(prodProductTag!=null){
						prodProductTags=new ArrayList<ProdProductTag>();
						prodProductTags.add(prodProductTag);
					}
					prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.EARLY.getCnName());
				//多订多惠	
				}else if(Constant.BUSINESS_COUPON_TYPE.MORE.getCode().equals(businessCoupon.getCouponType())){
					ProdProductTag prodProductTag=businessCouponService.checkProductTag(Constant.BUSINESS_COUPON_TYPE.MORE.getCode(), businessCoupon.getProductId());
					if(prodProductTag!=null){
						prodProductTags=new ArrayList<ProdProductTag>();
						prodProductTags.add(prodProductTag);
					}
					prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.MORE.getCnName());
				}
				//通知更新PRODUCT_SEARCH_INFO
				comSearchInfoUpdateService.productUpdated(businessCoupon.getProductId());
			}
			//系统自动添加早/多Tag==========End==========
			param.put("success", true);
			param.put("successMessage", "优惠开启或者关闭修改成功");
			
			if ("true".equals(businessCoupon.getValid())) {
				this.saveComLog("PROD_COUPON_BUSINESS_BIND",businessCoupon.getBusinessCouponId() , getOperatorNameAndCheck(), "优惠开启",
						"优惠开启", "优惠开启名称为：" + (String)businessCoupon.getCouponName());
			} else {
				this.saveComLog("PROD_COUPON_BUSINESS_BIND",businessCoupon.getBusinessCouponId() , getOperatorNameAndCheck(), "优惠关闭",
						"优惠关闭", "优惠关闭名称为：" + (String)businessCoupon.getCouponName());
			}

			//优惠券更新后通知修改最低价

			if(businessCoupon.getProductId()!=null){
				List<ProdProductBranch> prodProductBranchs = prodProductService.getProductBranchByProductId(businessCoupon.getProductId(), null);
				for (ProdProductBranch prodProductBranch : prodProductBranchs) {
					productMessageProducer.sendMsg(MessageFactory.newProductSellPriceMessage(prodProductBranch.getProdBranchId()));
				}
			}
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	
	/**
	 * 保存操作日志.
	 * @param objectType 表名.
	 * @param objectId  objectId
	 * @param operatorName operatorName
	 * @param logType logType
	 * @param logName logName
	 * @param content content
	 */
	protected void saveComLog(final String objectType, final Long objectId,
			final String operatorName, final String logType,
			final String logName, final String content) {
		final ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogService.addComLog(log);
	}
	
	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}

	public BusinessCoupon getBusinessCoupon() {
		return businessCoupon;
	}

	public void setBusinessCoupon(BusinessCoupon businessCoupon) {
		this.businessCoupon = businessCoupon;
	}
	
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getCouponTarget() {
		return couponTarget;
	}
	public void setCouponTarget(String couponTarget) {
		this.couponTarget = couponTarget;
	}

	public Long getBusinessCouponId() {
		return businessCouponId;
	}

	public void setBusinessCouponId(Long businessCouponId) {
		this.businessCouponId = businessCouponId;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getMetaType() {
		return metaType;
	}

	public void setMetaType(String metaType) {
		this.metaType = metaType;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public Long getMetaProductId() {
		return this.productId;
	}

	public void setMetaProductId() {
		this.metaProductId = this.productId;
	}

	public String getCouponTypeMark() {
		return couponTypeMark;
	}

	public void setCouponTypeMark(String couponTypeMark) {
		this.couponTypeMark = couponTypeMark;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public Map<Object, Object> getCouponTargetMap() {
		return couponTargetMap;
	}

	public void setCouponTargetMap(Map<Object, Object> couponTargetMap) {
		this.couponTargetMap = couponTargetMap;
	}
	
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
	
}
