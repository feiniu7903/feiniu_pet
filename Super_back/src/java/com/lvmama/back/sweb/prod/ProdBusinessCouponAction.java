package com.lvmama.back.sweb.prod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.BeeProdProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SeckillUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SeckillRuleVO;

/**
 *产品优惠
 */
@Results({
	@Result(name = "salesProdBusinesCoupon", location = "/WEB-INF/pages/back/prod/sales_prod_business_coupon.jsp"),
	@Result(name = "metaProdBusinesCoupon", location = "/WEB-INF/pages/back/meta/meta_prod_business_coupon.jsp")
	})
public class ProdBusinessCouponAction  extends BaseAction{

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 5334090489153474296L;
	
	/**
	 * 新增或修改策略对象
	 */
	private BusinessCoupon businessCoupon;
	/**
	 * 秒杀规则对象
	 */
	private ProdSeckillRule prodSeckillRule;
	/**
	 * 类别区别表示
	 * 用于区别特卖会(秒杀SALE等) 
	 */
	private String couponTypeMark;
	private String isSaleChannel ="N";//是否为特卖会渠道（原团购渠道）
	private Long businessCouponId;
	
	//传入2个对象需要getset方法
	private Long businessCoupon_aheadNum;
	private Long branchIdRadio;
	/**
	 * 绑定产品Id
	 */
	private Long productId;
	/**
	 * 产品类别Id,多个Id以","分隔
	 */
	private String branchIdCodes;
	/**
     * 优惠策略远程服务
     */
	private BusinessCouponService businessCouponService;
	private MetaProductBranchService metaProductBranchService;
	private ComLogService comLogService;
	/**
	 * 秒杀规则远程服务
	 */
	private ProdSeckillRuleService prodSeckillRuleService;
	/**
	 * 产品服务远程接口
	 */
	private ProdProductBranchService prodProductBranchService;
	private ProductHeadQueryService productServiceProxy;
	private List<SeckillRuleVO> seckillRuleVOs = new ArrayList<SeckillRuleVO>();
	private List<Long> seckillRuleIds = new ArrayList<Long>();//用于标识原有的规则ID
	/**
	 * 多个子类型选中checkbox展示(CodeItem:code,name名称,checked)
	 */
	private List<CodeItem> allBranchCodeItemList = new ArrayList<CodeItem>();
	private Float amountYuan;
	private	Float amount;
	private String amountMode = "";
	/**
	 * 以"元"为单位的Y值
	 */	
	private Float argumentYYuan;
	private Float argumentZYuan;
	private String metaType;
	private String isAperiodic;
	private MetaProductService metaProductService;
	private ProdProductService prodProductService;
	/**
	 * Tag Service
	 */
	private ProdProductTagService prodProductTagService;
	private Date beforBeginDate;
	private Date beforEndDate;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	private TopicMessageProducer productMessageProducer;
	/**
	 * 打开新增/编辑优惠页面
	 * @return
	 */
	@Action(value="/prod/addOrEditProdBusinessCoupon")
	public String addOrEditProdBusinessCoupon() {
		
		if (null != businessCouponId) {
			businessCoupon = businessCouponService.selectByPK(businessCouponId);
		}else{
			businessCoupon = new BusinessCoupon();
		}
		if("meta".equalsIgnoreCase(metaType)) {
			MetaProduct metaProduct = metaProductService.getMetaProduct(this.productId);
			isAperiodic = metaProduct.IsAperiodic()+"";
		} else {
			ProdProduct prodProduct = prodProductService.getProdProduct(this.productId);
			isAperiodic = prodProduct.IsAperiodic()+"";
		}
		businessCoupon.setMetaType(metaType);
		if("AMOUNT".equals(businessCoupon.getFavorUnit())){
			amountMode = "amountYuan";
		}else{
			amountMode = "amount";
		}
		this.allBranchCodeItemList = branchList2CodeItemList(this.productId);
		//根据id查找产品所属渠,是否特卖会渠道(TUANGOU)
		List<ProdProductChannel> ProdProductChannelList = prodProductService.getProductChannelByProductId(this.productId);
		for (ProdProductChannel prodProductChannel : ProdProductChannelList) {
			if(Constant.CHANNEL.TUANGOU.getCode().equals(prodProductChannel.getProductChannel())){
				isSaleChannel="Y";
			}
		}
		
		if("SALES".equalsIgnoreCase(metaType)){
			return "salesProdBusinesCoupon";
		}else if("META".equalsIgnoreCase(metaType)){
			return "metaProdBusinesCoupon";
		}else{
			return null;
		}

	}
	
	@Action(value="/prod/checkSaleChannel")
	public void checkSaleChannel() {
		//根据id查找产品所属渠,是否特卖会渠道(TUANGOU)
		List<ProdProductChannel> ProdProductChannelList = prodProductService.getProductChannelByProductId(this.productId);
		boolean isSaleChannel = false;
		boolean isLvtuChannel = false;
		for (ProdProductChannel prodProductChannel : ProdProductChannelList) {
			if(Constant.CHANNEL.TUANGOU.getCode().equals(prodProductChannel.getProductChannel())){
				isSaleChannel = true;
			} else if (Constant.CHANNEL.LVTU_TEAM_BUYING.getCode().equals(prodProductChannel.getProductChannel())) {
				isLvtuChannel = true;
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		if (isSaleChannel && isLvtuChannel) {
			param.put("success", false);
			param.put("errorMessage", "该产品设置了“驴途团购”渠道，不能设置秒杀活动，请修改渠道后重新设置");
		} else {
			param.put("success", true);
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	 
	/**
	 * 修改秒杀规则
	 * @return
	 * @author liudong
	 */
	@Action(value="/prod/addOrEditProdSaleRule")
	public String addOrEditProdSaleRule() {
		if (null != businessCouponId) {
			businessCoupon = businessCouponService.selectByPK(businessCouponId);
			//获取规则
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("businessCouponId", businessCouponId);
			List<ProdSeckillRule> prodSeckillRules = prodSeckillRuleService.selectByParam(params);
			List<SeckillRuleVO> skrVOList = new ArrayList<SeckillRuleVO>();
			for (ProdSeckillRule prodSeckillRule : prodSeckillRules) {
				SeckillRuleVO skrvo = new SeckillRuleVO();
				BeanUtils.copyProperties(prodSeckillRule,skrvo);
				skrVOList.add(skrvo);
			}
			businessCoupon.setSeckillRuleVOs(skrVOList);
		}else{
			businessCoupon = new BusinessCoupon();
		}
		if("meta".equalsIgnoreCase(metaType)) {
			MetaProduct metaProduct = metaProductService.getMetaProduct(this.productId);
			isAperiodic = metaProduct.IsAperiodic()+"";
		} else {
			ProdProduct prodProduct = prodProductService.getProdProduct(this.productId);
			isAperiodic = prodProduct.IsAperiodic()+"";
		}
		businessCoupon.setMetaType(metaType);
		if("AMOUNT".equals(businessCoupon.getFavorUnit())){
			amountMode = "amountYuan";
		}else{
			amountMode = "amount";
		}
		this.allBranchCodeItemList = branchList2CodeItemList(this.productId);
		
		if("SALES".equalsIgnoreCase(metaType)){
			return "salesProdBusinesCoupon";
		}else if("META".equalsIgnoreCase(metaType)){
			return "metaProdBusinesCoupon";
		}else{
			return null;
		}

	}
	
	
	/**
	 * 新增或修改优惠策略，根据传入的businessCoupon是否存在businessCouponId来决定是新增还是更新
	 */
	@Action(value="/prod/saveProdBusinessCoupon")
	public void saveProdBusinessCoupon() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null == businessCoupon) {
			param.put("success", false);
			param.put("errorMessage", "操作失败");
		} else {
			//特卖会
			if(Constant.BUSINESS_COUPON_TYPE.SALE.name().equals(couponTypeMark)){
				//特卖会只能是销售产品
				if("SALES".equalsIgnoreCase(businessCoupon.getMetaType())){
					//如果已经选过，则改了
					Map<String,Object> params = new HashMap<String,Object>();
					List<ProdSeckillRule> prodSeckillRules=null;
					if(null == businessCoupon.getBusinessCouponId()){
						if(isOnlySaleType(businessCoupon.getSaleType(),Long.valueOf(branchIdCodes))&&isOnlyBranch(businessCoupon.getProductId(),Long.valueOf(branchIdCodes))){
							//特卖会规则与产品(类别)绑定
							BusinessCoupon saleCoupon = getBusinessCouponBySALE(businessCoupon);
							saleCoupon.setValid("true");//是否有效
							List<Long> businessCouponIds=null;
							//查出数据库中所有的时间段,进行对比校验
							params.put("branchId", branchIdCodes);
							params.put("valid", "yes");//秒杀规则是否结束(END_TIME>sysdate)
							prodSeckillRules = prodSeckillRuleService.selectByParam(params);
							boolean isRightTime =isRightTime(prodSeckillRules);
							if(isRightTime){
								businessCouponIds = businessCouponService.saveBusinessCouponAndBusinessCoupon(saleCoupon, saleCoupon.getProductId(),branchIdCodes);
								for(SeckillRuleVO skvo:seckillRuleVOs){
									//秒杀此处businessCouponIds只有一个
									setProdSeckillRuleByParams(businessCouponIds.get(0),skvo,prodSeckillRules,null);
									prodSeckillRuleService.insertProdSeckillRule(prodSeckillRule);
								}
								param.put("success", true);
								param.put("successMessage", "新增特卖会成功!");
								String OperateName = super.getOperatorName();
								for(Long businessCouponId : businessCouponIds){
									this.saveComLog("PROD_COUPON_BUSINESS_BIND", businessCouponId, OperateName, Constant.COUPON_ACTION.COUPON_ADD.name(),
											"新增或修改优惠", "新增或修改优惠名称为：" + (String)this.businessCoupon.getCouponName());
								}
							}else{
								param.put("success", false);
								param.put("errorMessage", "优惠时段规则无效!");
							}	
						}else{
							param.put("success", false);
							param.put("errorMessage", "类别"+branchIdCodes+"不满足规则！");
						}
					}else{//更新修改特卖会规则绑定产品(类别)
						if(isOnlySaleType(businessCoupon.getSaleType(),businessCoupon.getBranchId())){
							//查出数据库中所有的时间段,进行对比校验
							params.put("branchId", businessCoupon.getBranchId());
							params.put("valid", "yes");//秒杀规则是否结束(END_TIME>sysdate)
							prodSeckillRules = prodSeckillRuleService.selectByParam(params);
							List<ProdSeckillRule> newprodSeckillRules = updateProdSeckillRule(prodSeckillRules);//更新规则
							boolean isUpdateRightTime = isRightTime(newprodSeckillRules);
							if(isUpdateRightTime){
								//更新优惠表
								businessCouponService.updateBusinessCouponAndBusinessCouponProduct(businessCoupon);
								//更新规则表
								for(SeckillRuleVO skvo:seckillRuleVOs){
									if(skvo.getId()!=null){//修改的规则
										BuildupdateProdSeckillRule(skvo);
										prodSeckillRuleService.updateByPk(prodSeckillRule);
									}else{//新增规则
										setProdSeckillRuleByParams(businessCoupon.getBusinessCouponId(),skvo,prodSeckillRules,businessCoupon.getBranchId());
										prodSeckillRuleService.insertProdSeckillRule(prodSeckillRule);
									}
								}
								param.put("success", true);
								param.put("successMessage", "新增或修改特卖会成功!");
								String OperateName = super.getOperatorName();
								this.saveComLog("PROD_COUPON_BUSINESS_BIND",this.businessCoupon.getBusinessCouponId() , OperateName, Constant.COUPON_ACTION.COUPON_ADD.name(),
										"新增或修改优惠", "新增或修改优惠名称为：" + (String)this.businessCoupon.getCouponName());
							}else{
								param.put("success", false);
								param.put("errorMessage", "修改规则无效!");
							}
						}else{
							param.put("success", false);
							param.put("errorMessage", "类别"+businessCoupon.getBranchId()+"已存在其它特卖类型，请先删除！");
						}
								
					}
				}
			}else{//早定早惠,多定多惠
				if("AMOUNT".equals(businessCoupon.getFavorUnit())){
					//销售产品：AMOUNT_MORE_QUANTITY_WHOLE状态下，Y是份; 其他状态是元
					if ("SALES".equalsIgnoreCase(businessCoupon.getMetaType())&&(!("AMOUNT_MORE_QUANTITY_WHOLE".equalsIgnoreCase(businessCoupon.getFavorType())))
							&& null == businessCoupon.getArgumentY()
							&& null != this.argumentYYuan) {
						businessCoupon.setArgumentY(PriceUtil.convertToFen(argumentYYuan));
					}
					//销售产品：AMOUNT_MORE_QUANTITY_WHOLE状态下存Z元
					if ("SALES".equalsIgnoreCase(businessCoupon.getMetaType())&&("AMOUNT_MORE_QUANTITY_WHOLE".equalsIgnoreCase(businessCoupon.getFavorType()))
							&& null == businessCoupon.getArgumentZ()
							&& null != this.argumentZYuan) {
						businessCoupon.setArgumentZ(PriceUtil.convertToFen(argumentZYuan));
					}
					
					//采购产品：Z是元
					if ("META".equalsIgnoreCase(businessCoupon.getMetaType())
							&& null == businessCoupon.getArgumentZ()
							&& null != this.argumentZYuan) {
						businessCoupon.setArgumentZ(PriceUtil.convertToFen(argumentZYuan));
					}
				}
				if (null == businessCoupon.getBusinessCouponId()) {
					//保存优惠策略和绑定的产品
					businessCoupon.setValid("false");
					List<Long> businessCouponIds = businessCouponService.saveBusinessCouponAndBusinessCoupon(businessCoupon, businessCoupon.getProductId(),branchIdCodes);
					param.put("success", true);
					param.put("successMessage", "新增优惠成功!");
					
					String OperateName = super.getOperatorName();
					for(Long businessCouponId : businessCouponIds){
						this.saveComLog("PROD_COUPON_BUSINESS_BIND", businessCouponId, OperateName, Constant.COUPON_ACTION.COUPON_ADD.name(),
								"新增或修改优惠", "新增或修改优惠名称为：" + (String)this.businessCoupon.getCouponName());
					}
					
				} else {
					//更新优惠策略和绑定的产品
					businessCouponService.updateBusinessCouponAndBusinessCouponProduct(businessCoupon);
					param.put("success", true);
					param.put("successMessage", "修改优惠成功!");
					String OperateName = super.getOperatorName();
					this.saveComLog("PROD_COUPON_BUSINESS_BIND",this.businessCoupon.getBusinessCouponId() , OperateName, Constant.COUPON_ACTION.COUPON_ADD.name(),
							"新增或修改优惠", "新增或修改优惠名称为：" + (String)this.businessCoupon.getCouponName());
					
					//系统自动添加早/多Tag==========Begin==========
					//--只适用于销售产品并且下单有效期有变化的产品
					if(Constant.BUSINESS_COUPON_META_TYPE.SALES.getCode().equals(businessCoupon.getMetaType())&&(beforBeginDate.compareTo(businessCoupon.getBeginTime())!=0||beforEndDate.compareTo(businessCoupon.getEndTime())!=0)){
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
				}
				//优惠券更新后通知修改最低价
				if ("true".equals(businessCoupon.getValid())) {
					if(businessCoupon.getProductId()!=null){
						List<ProdProductBranch> prodProductBranchs = prodProductService.getProductBranchByProductId(businessCoupon.getProductId(), null);
						for (ProdProductBranch prodProductBranch : prodProductBranchs) {
							productMessageProducer.sendMsg(MessageFactory.newProductSellPriceMessage(prodProductBranch.getProdBranchId()));
						}
					}
				}
			}
			
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	
	public ProdSeckillRule getProdSeckillRule() {
		return prodSeckillRule;
	}

	public void setProdSeckillRule(ProdSeckillRule prodSeckillRule) {
		this.prodSeckillRule = prodSeckillRule;
	}
	/**是否唯一特卖类型,用于控制一个branchId出现多个特卖类型*/
	private boolean isOnlySaleType(String saleType,Long branchId){
		//查询优惠活动表该branchId特卖类型
		boolean result = true;
		Map<String, Object> paramx = new HashMap<String, Object>();
		paramx.put("branchId", branchId);
		paramx.put("couponType", Constant.BUSINESS_COUPON_TYPE.SALE.name());
		List<BusinessCoupon>  BusinessCouponx = businessCouponService.selectByIDs(paramx);
		if(BusinessCouponx!=null&&BusinessCouponx.size()>0){
			for (BusinessCoupon businessCouponx : BusinessCouponx) {
				if(!businessCouponx.getSaleType().equals(saleType)){
					result=false;
					break;
				}
			}
		}
		return result;
	}
	/**是否为单产品单类型作为秒杀,用于控制一个productId出现多个branchId*/
	private boolean isOnlyBranch(Long productId,Long branchId){
		boolean result = true;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("productId", productId);
		List<ProdSeckillRule> prodSeckillRules = prodSeckillRuleService.selectByParam(param);
		if(prodSeckillRules!=null&&prodSeckillRules.size()>0){
			for (ProdSeckillRule prodSeckillRule : prodSeckillRules) {
				if(!prodSeckillRule.getBranchId().equals(branchId)){
					result=false;
					break;
				}
			}
		}
		return result;
	}
	/**判断是否时段是否满足规则*/
	private boolean isRightTime(List<ProdSeckillRule> prodSeckillRules){
		boolean isRightTime=false;
		for(SeckillRuleVO skvo:seckillRuleVOs){
			ProdSeckillRule seckill = new ProdSeckillRule();
			seckill.setStartTime(skvo.getStartTime());
			seckill.setEndTime(skvo.getEndTime());
			prodSeckillRules.add(seckill);
			if(prodSeckillRules!=null&&!prodSeckillRules.isEmpty()){
				isRightTime = comparaTime(prodSeckillRules,branchIdCodes, seckill);
				if(isRightTime==false)
					break;
			}else{
				isRightTime = true;
			}
			
		}
		return isRightTime;
	}
	/**更新规则
	 * @return
	 */
	private List<ProdSeckillRule> updateProdSeckillRule(List<ProdSeckillRule> prodSeckillRules){
		List<Long> idList = new ArrayList<Long>();
		List<ProdSeckillRule> newprodSeckillRules= new ArrayList<ProdSeckillRule>();//需要移除的对象
		//移除List中ID对应的规则
		boolean flag= false;//是否移除该对象
		for (ProdSeckillRule prodSeckillRule : prodSeckillRules) {
			for (SeckillRuleVO seckillRuleVO : seckillRuleVOs) {
				if(seckillRuleVO.getId()!=null){//如果是修改过的规则
					idList.add(seckillRuleVO.getId());
					if(prodSeckillRule.getId().equals(seckillRuleVO.getId())){
						flag = true;//移除
						break;
					}
				}
			}
			if(!flag){
				newprodSeckillRules.add(prodSeckillRule);
			}
		}
		for (int i = 0; i < seckillRuleIds.size(); i++) {
			if(!idList.contains(seckillRuleIds.get(i)))//规则被移除则从规则表中删除
				prodSeckillRuleService.deleteByPk(seckillRuleIds.get(i));
		}
		return newprodSeckillRules;
	}

	/**
	 *封装特卖会规则需要的优惠策略对象
	 */
	private BusinessCoupon getBusinessCouponBySALE(BusinessCoupon businessCoupon){
		BusinessCoupon bCoupon = new BusinessCoupon();
		bCoupon.setProductId(businessCoupon.getProductId());
		bCoupon.setCouponName(businessCoupon.getCouponName());
		bCoupon.setCouponType(businessCoupon.getCouponType());
		bCoupon.setSaleType(businessCoupon.getSaleType());
		bCoupon.setMetaType(businessCoupon.getMetaType());
		return bCoupon;
	}
	/**
	 *查出数据库中所有的时间段,进行对比校验,
	 *@return
	 */
	private boolean comparaTime(List<ProdSeckillRule> prodSeckillRules,String branchId,ProdSeckillRule prodSeckillRule){
		//只有一条则不作比较
		if(prodSeckillRules.size()==1){
			return true;
		}
		boolean result = false;	
		SeckillUtils<ProdSeckillRule> skUtil= new SeckillUtils<ProdSeckillRule>();
		//将要插入的prodSeckillRule加入到list中进行排序
		skUtil.sortByMethod(prodSeckillRules, "getStartTime", false);
		ProdSeckillRule pskr=null;
		ProdSeckillRule prevPskr=null;
		ProdSeckillRule nextPskr=null;
		//获取插入的对象和它的前一个和后一个对象
		for (int i = 0; i < prodSeckillRules.size(); i++) {
			if(prodSeckillRule==prodSeckillRules.get(i)){
				pskr = prodSeckillRules.get(i);
				if(i==0){
					nextPskr = prodSeckillRules.get(i+1);
				}else if(i==(prodSeckillRules.size()-1)){
					prevPskr = prodSeckillRules.get(i-1);
				}else{
					prevPskr = prodSeckillRules.get(i-1);
					nextPskr = prodSeckillRules.get(i+1);
				}
			}	
		}
		//比对时间
		if(prevPskr!=null&&nextPskr==null){
			if(SeckillUtils.compare2Time(prevPskr.getEndTime(), pskr.getStartTime())){
				result=true;
			}
		}else if(prevPskr==null&&nextPskr!=null){
			if(SeckillUtils.compare2Time(pskr.getEndTime(), nextPskr.getStartTime())){
				result=true;
			}
		}else{
			if(SeckillUtils.compare2Time(prevPskr.getEndTime(), pskr.getStartTime())&&SeckillUtils.compare2Time(pskr.getEndTime(),nextPskr.getStartTime())){
				result=true;
			}
		}
		
		
		return result;
	}
	/**设置更新特卖规则字段*/
	private void BuildupdateProdSeckillRule(SeckillRuleVO skvo){
		prodSeckillRule.setId(skvo.getId());
		prodSeckillRule.setBusinessCouponId(businessCoupon.getBusinessCouponId());
		Long creator = super.getSessionUser().getUserId();
		prodSeckillRule.setCreator(creator);
		prodSeckillRule.setStartTime(skvo.getStartTime());
		prodSeckillRule.setEndTime(skvo.getEndTime());
		prodSeckillRule.setAmount(skvo.getAmount());
		prodSeckillRule.setReducePrice(PriceUtil.convertToFen(Long.toString(skvo.getReducePrice())));//存入分
		prodSeckillRule.setLocalStock(skvo.getAmount());
	}
	/**
	 *设置特卖规则部分字段
	 */
	private void setProdSeckillRuleByParams(Long businessCouponId,SeckillRuleVO skvo,List<ProdSeckillRule> prodSeckillRules,Long branchId){
		prodSeckillRule.setBusinessCouponId(businessCouponId);
		Long creator = super.getSessionUser().getUserId();
		prodSeckillRule.setCreator(creator);
		prodSeckillRule.setProductId(businessCoupon.getProductId());
		if(branchId!=null){
			prodSeckillRule.setBranchId(branchId);
		}else{
			prodSeckillRule.setBranchId(Long.valueOf(branchIdCodes));
		}
		prodSeckillRule.setStartTime(skvo.getStartTime());
		prodSeckillRule.setEndTime(skvo.getEndTime());
		prodSeckillRule.setAmount(skvo.getAmount());
		prodSeckillRule.setReducePrice(PriceUtil.convertToFen(Long.toString(skvo.getReducePrice())));//存入分
		prodSeckillRule.setLocalStock(skvo.getAmount());
		/*//同一类别都存一天的时间价格
		if(prodSeckillRules!=null&&prodSeckillRules.size()>0&&prodSeckillRules.get(0).getTimePrice()!=null){
			prodSeckillRule.setTimePrice(prodSeckillRules.get(0).getTimePrice());
			prodSeckillRule.setTimePriceDate(prodSeckillRules.get(0).getTimePriceDate());
		}else{
			Date nearCanSaleDay = productServiceProxy.selectNearBranchTimePriceByBranchId(Long.valueOf(branchIdCodes),new Date());
			TimePrice timeprice = prodProductBranchService.getProdTimePrice(Long.valueOf(branchIdCodes), nearCanSaleDay);
			prodSeckillRule.setTimePrice(timeprice.getPrice());
			prodSeckillRule.setTimePriceDate(nearCanSaleDay);
		}*/
		
		
	}
	/**
	 * 将产品子类型列表转换为CodeItem列表 ，以提供页面显示.
	 * @param subProductTypeList 产品子类型列表.
	 * @return CodeItem列表.
	 */
	private List<CodeItem> branchList2CodeItemList(Long productId) {
		List<CodeItem> result = new ArrayList<CodeItem>();
		
		if("SALES".equalsIgnoreCase(metaType)){
			List<ProdProductBranch> branchList = prodProductBranchService.getProductBranchByProductId(productId, "false");
			for (ProdProductBranch branch: branchList) {
				CodeItem ci = new CodeItem();
				ci.setName(branch.getBranchName());
				ci.setCode(branch.getProdBranchId().toString());
				ci.setChecked("false");
				result.add(ci);
			} 
		}else if("META".equalsIgnoreCase(metaType)){
			List<MetaProductBranch> branchList = metaProductBranchService.selectBranchListByProductId(productId, "false");
			for (MetaProductBranch branch: branchList) {
				CodeItem ci = new CodeItem();
				ci.setName(branch.getBranchName());
				ci.setCode(branch.getMetaBranchId().toString());
				ci.setChecked("false");
				result.add(ci);
			} 
		}
		
		return result;
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
	private void saveComLog(final String objectType, final Long objectId,
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
	
	
	public Long getBusinessCouponId() {
		return businessCouponId;
	}

	public void setBusinessCouponId(Long businessCouponId) {
		this.businessCouponId = businessCouponId;
	}

	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}
	
	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
	public BusinessCoupon getBusinessCoupon() {
		return businessCoupon;
	}

	public void setBusinessCoupon(BusinessCoupon businessCoupon) {
		this.businessCoupon = businessCoupon;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getBranchIdCodes() {
		return branchIdCodes;
	}

	public void setBranchIdCodes(String branchIdCodes) {
		this.branchIdCodes = branchIdCodes;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public List<CodeItem> getAllBranchCodeItemList() {
		return allBranchCodeItemList;
	}

	public void setAllBranchCodeItemList(List<CodeItem> allBranchCodeItemList) {
		this.allBranchCodeItemList = allBranchCodeItemList;
	}

	public Float getAmountYuan() {
		return amountYuan;
	}

	public void setAmountYuan(Float amountYuan) {
		this.amountYuan = amountYuan;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getAmountMode() {
		return amountMode;
	}

	public void setAmountMode(String amountMode) {
		this.amountMode = amountMode;
	}

	public void setArgumentYYuan(Float argumentYYuan) {
		this.argumentYYuan = argumentYYuan;
	}
	
	public Float getArgumentZYuan() {
		return argumentZYuan;
	}

	public void setArgumentZYuan(Float argumentZYuan) {
		this.argumentZYuan = argumentZYuan;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public String getMetaType() {
		return metaType;
	}

	public void setMetaType(String metaType) {
		this.metaType = metaType;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}
	public void setBeforBeginDate(Date beforBeginDate) {
		this.beforBeginDate = beforBeginDate;
	}

	@SuppressWarnings("deprecation")
	public void setBeforEndDate(Date beforEndDate) {
		if(beforEndDate!=null){
			beforEndDate.setHours(23);
			beforEndDate.setMinutes(59);
			beforEndDate.setSeconds(59);
		}
		this.beforEndDate = beforEndDate;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}
	public String getCouponTypeMark() {
		return couponTypeMark;
	}

	public void setCouponTypeMark(String couponTypeMark) {
		this.couponTypeMark = couponTypeMark;
	}

	//
	public Long getBusinessCoupon_aheadNum() {
		return businessCoupon_aheadNum;
	}

	public void setBusinessCoupon_aheadNum(Long businessCoupon_aheadNum) {
		this.businessCoupon_aheadNum = businessCoupon_aheadNum;
	}

	public Long getBranchIdRadio() {
		return branchIdRadio;
	}

	public void setBranchIdRadio(Long branchIdRadio) {
		this.branchIdRadio = branchIdRadio;
	}
	public List<SeckillRuleVO> getSeckillRuleVOs() {
		return seckillRuleVOs;
	}

	public void setSeckillRuleVOs(List<SeckillRuleVO> seckillRuleVOs) {
		this.seckillRuleVOs = seckillRuleVOs;
	}
	
	public List<Long> getSeckillRuleIds() {
		return seckillRuleIds;
	}

	public void setSeckillRuleIds(List<Long> seckillRuleIds) {
		this.seckillRuleIds = seckillRuleIds;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public String getIsSaleChannel() {
		return isSaleChannel;
	}

	public void setIsSaleChannel(String isSaleChannel) {
		this.isSaleChannel = isSaleChannel;
	}
	
}