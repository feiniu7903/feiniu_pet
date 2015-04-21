package com.lvmama.back.sweb.prod;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.prod.BounsReturnScale;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.prod.BounsReturnScaleService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.Constant;

/**
 *
 * @author yuzhibing
 *
 */
public abstract class ProdProductAction<T extends ProdProduct> extends AbstractProductAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6995666402066378231L;
	
	private PermUserService permUserService;
	private ComMessageService comMessageService;
	private BounsReturnScaleService bounsReturnScaleService;
	private TopicMessageProducer productMessageProducer;
	
	protected T product;
	protected String productType;
	protected String[] channel;//渠道
	protected String[] initChannel;//产品渠道，该渠道只管初始化时使用
	protected String[] productTravellerInfoOptions;//其他游玩人信息
	protected String[] firstTravellerInfoOptions;//第一游玩人信息
	protected String[] productContactInfoOptions;
	protected String permUserRealName;//产品经理的名字
	protected Class<T> productClazz;
	protected long cashRefundYuan=0;
	private List<String> fields=new ArrayList<String>();
	private final static String DEFAULT_PREPAY = "Y";
	
	/**
	 * Tag Service
	 */
	private ProdProductTagService prodProductTagService;
	private MarkCouponPointChangeService markCouponPointChangeService;
    private ProdProductRelationService prodProductRelationService;
    private ProdSeckillRuleService prodSeckillRuleService;

	private IOpTravelGroupService opTravelGroupService;
	
	protected void addFields(String...array){
		for(String f:array){
			if(!fields.contains(f)){
				fields.add(f);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public ProdProductAction(Constant.PRODUCT_TYPE pt) {
		super();
		this.productType=pt.name();
		setMenuType("prod");
		Class<?> clazz=getClass();
		Type type=clazz.getGenericSuperclass();
		while(!(type instanceof ParameterizedType)&&!clazz.equals(ProdProduct.class)){
			clazz=clazz.getSuperclass();
			type=clazz.getGenericSuperclass();
		}
		productClazz=(Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
		
		try{
			product=productClazz.newInstance();
			product.setProductType(productType);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		initChannel=new String[]{Constant.CHANNEL.BACKEND.name(),Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.CLIENT.name()};
		addFields("additional","travellerInfoOptions","canPayByBonus");
	}
	
	
	/**
	 * @return the permUserRealName
	 */
	public String getPermUserRealName() {
		return permUserRealName;
	}
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.AbstractProductAction#doBefore()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean doBefore() {
		if(productId==null||productId<1){
			return false;
		}
		product=(T)prodProductService.getProdProduct(productId);
		if(product==null){
			return false;
		}
		return true;
	}

	/**
	 * 初始化编辑的产品信息
	 */
	protected void initEditProduct(){		
		//doBefore();
		List<ProdProductChannel> list=prodProductService.selectChannelByProductId(productId);
		if(CollectionUtils.isNotEmpty(list)){
			initChannel=new String[list.size()];
			int p=0;
			for(ProdProductChannel ppc:list){
				initChannel[p++]=ppc.getProductChannel();
			}
		}else{
			initChannel=null;
		}
		
		if(product.getManagerId()!=null){
			PermUser pu=permUserService.getPermUserByUserId(product.getManagerId());
			if(pu!=null){
				permUserRealName=pu.getRealName();
			}
		}
		
		List<String> travellerInfoOptionsList = product.getTravellerInfoOptionsList();
		if (null != travellerInfoOptionsList && !travellerInfoOptionsList.isEmpty()) {
			productTravellerInfoOptions = new String[travellerInfoOptionsList.size()];
			for (int i = 0 ; i < travellerInfoOptionsList.size() ; i++) {
				productTravellerInfoOptions[i] = travellerInfoOptionsList.get(i);
			}
		}
		
		List<String> firstTravellerInfoOptionsList = product.getFirstTravellerInfoOptionsList();
		if (null != firstTravellerInfoOptionsList && !firstTravellerInfoOptionsList.isEmpty()) {
			firstTravellerInfoOptions = new String[firstTravellerInfoOptionsList.size()];
			for (int i = 0 ; i < firstTravellerInfoOptionsList.size() ; i++) {
				firstTravellerInfoOptions[i] = firstTravellerInfoOptionsList.get(i);
			}
		}			

		List<String> contactInfoOptionsList = product.getContactInfoOptionsList();
		if (null != contactInfoOptionsList && !contactInfoOptionsList.isEmpty()) {
			productContactInfoOptions = new String[contactInfoOptionsList.size()];
			for (int i = 0 ; i < contactInfoOptionsList.size() ; i++) {
				productContactInfoOptions[i] = contactInfoOptionsList.get(i);
			}
		}		
		
		cashRefundYuan=product.getCashRefundY().longValue();
	}

	
	/**
	 * 合并数组
	 * @param array
	 * @return
	 */
	protected String merge(String[] array){
		StringBuffer sb=new StringBuffer();		
		for(String a:array){
			sb.append(a);
			sb.append(",");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}


	/**
	 * 操作保存前的操作,可以抛出异常，相当于保存前的数据检查
	 */
	protected ResultHandle doSaveBefore(){	
		ResultHandle handle=new ResultHandle();
		if(!ArrayUtils.isEmpty(productTravellerInfoOptions)){
			product.setTravellerInfoOptions(merge(productTravellerInfoOptions));
		}
		
		if (!ArrayUtils.isEmpty(this.firstTravellerInfoOptions)) {
			StringBuffer sb=new StringBuffer();		
			for(String a:firstTravellerInfoOptions){
				sb.append("F_").append(a).append(",");
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			product.setTravellerInfoOptions(StringUtils.isNotBlank(product.getTravellerInfoOptions()) ? product.getTravellerInfoOptions() + "," + sb.toString() : sb.toString() ); 
		}		
		
		if (!ArrayUtils.isEmpty(productContactInfoOptions)) {
			StringBuffer sb=new StringBuffer();		
			for(String a:productContactInfoOptions){
				sb.append("C_").append(a).append(",");
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			product.setTravellerInfoOptions(StringUtils.isNotBlank(product.getTravellerInfoOptions()) ? product.getTravellerInfoOptions() + "," + sb.toString() : sb.toString() ); 
		}
		
		if(StringUtil.hasIllegalCharacter(product.getProductName())){
			handle.setMsg("产品名称不可包含'<','>','&'");
			return handle;
		}
		
		if(product.getOnlineTime()!=null&&product.getOfflineTime()!=null){
			if(product.getOfflineTime().before(product.getOnlineTime())){
				handle.setMsg("上线时间要不能晚于下线时间");
				return handle;
			}
		}
		if (product.getProductId() != null && product.getOfflineTime() != null) {
			Date maxTime = this.prodProductService.selectMaxTimePriceByProductId(product.getProductId());
			if (maxTime != null && maxTime.after(product.getOfflineTime())) {
				handle.setMsg("下线日期之后，时间价格表中仍有可售日期，可能会导致用户错误下单，请先修改时间价格表内容。");
				return handle;
			}
		}
		if(ArrayUtils.isEmpty(channel)){
			handle.setMsg("销售渠道不可以为空");
			return handle;
		}
		if (product.getProductId() != null) {
			boolean isLvtuChannel = false;
			for (String ch : channel) {
				if (Constant.CHANNEL.LVTU_TEAM_BUYING.getCode().equals(ch)) {
					isLvtuChannel = true;
					break;
				}
			}
			if (isLvtuChannel) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("productId", product.getProductId());
				List<ProdSeckillRule> srList = prodSeckillRuleService.querySeckillRuleByClient(paramMap);
				if (srList.size() > 0) {
					handle.setMsg("该产品设置了秒杀活动，不能设置为驴途团购渠道");
					return handle;
				}
			}
			
		}
		
		if ("Y".equalsIgnoreCase(product.getIsRefundable())) {
			if (!"Y".equalsIgnoreCase(product.getIsManualBonus())) {
				if (StringUtils.isEmpty(product.getBounsScale())) {
					handle.setMsg("返现比例不能为空");
					return handle;
				}
			}
			if (!"Y".equalsIgnoreCase(product.getIsManualBonus())) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("productType", product.getProductType());
				param.put("productSubType", product.getSubProductType());
				BounsReturnScale bounsReturnScale = bounsReturnScaleService.getBonusScaleByType(param);
				if (bounsReturnScale != null) {
					if (new BigDecimal(product.getBounsScale()).compareTo(new BigDecimal(bounsReturnScale.getScale())) > 0) {
						if (StringUtils.trimToNull(product.getBounsReason()) == null) {
							handle.setMsg("返现比例超过阀值[" + bounsReturnScale.getScale() + "%]，投放原因不能为空");
							return handle;
						}
						if (product.getBounsStart() == null || product.getBounsEnd() == null) {
							handle.setMsg("返现比例超过阀值[" + bounsReturnScale.getScale() + "%]，投放时长不能为空");
							return handle;
						}
						if (product.getBounsLimit() == null) {
							handle.setMsg("返现比例超过阀值[" + bounsReturnScale.getScale() + "%]，投放金额不能为空");
							return handle;
						}
					}
				}
			} else {
				if (StringUtils.isEmpty(product.getBounsReason())) {
					handle.setMsg("数值返现，投放原因不能为空");
					return handle;
				}
			}
		}
		
		product.setPrePaymentAble(DEFAULT_PREPAY);
		return handle;
	}
	/**
	 * 操作保存后的操作
	 */
	protected ResultHandle doSaveAfter(){
		return new ResultHandle();
	}
	
	protected Integer checkTimePrice(Long productId,String isSelftSign){
		return prodProductService.checkTimePriceByProductId(productId,isSelftSign);
	}
	
	@SuppressWarnings("unchecked")
	protected void saveProduct(){
		JSONResult result=new JSONResult(getResponse());
		
		HashMap check = new HashMap();
		check.put("bizCode", product.getBizcode());
		List<ProdProduct> list = prodProductService.selectBizCode(check); 
	 
		if (list.size() > 0) {
			if (product.getProductId() == null) {// 如果是新建
				result.raise("产品编号已经存在!").output();
				return;
			} else if (!list.get(0).getProductId().equals(product.getProductId())) {// 如果是编辑
				result.raise("产品编号已经存在!").output();
				return;
			}
		}
		/**
		 * (前台JS已做验证)去掉此处的提前、最晚预定小时数(20120428)
		 * 
		String msg = checkAheadHour();
		if(!"".equals(msg)){
			result.raise(msg).output();
			return;				
		}*/
		ResultHandle succ=doSaveBefore();
		if(!succ.isSuccess()){
			result.raise(succ).output();
			return;
		}
		
		boolean hasNew = (product.getProductId() == null);
		String isClearProdModel=null;
		if (product.getProductId() == null) {
			product.setOrgId(this.getSessionUserDepartmentId());
			
			String name = product.getSubProductType();
			if(("GROUP".equals(name) || "GROUP_LONG".equals(name) || "GROUP_FOREIGN".equals(name) || "FREENESS".equals(name) || "FREENESS_LONG".equals(name) || "FREENESS_FOREIGN".equals(name) || "SELFHELP_BUS".equals(name) )){
				if("FREENESS_FOREIGN".equals(name) || "GROUP_FOREIGN".equals(name)){
					product.setIsForegin("Y");
					product.setRegionName(product.getRegionName());					
				}else{
					//如果为境内则没有地域  保存为
					product.setRegionName("");
					product.setIsForegin("N");
				}
			}
				
			if("Y"==(product.getIsForegin())){
				product.setIsForegin("Y");
				product.setRegionName(product.getRegionName());
			}
			
			//设置点评返现金额
			if("Y".equals(product.getIsRefundable())){
				if("Y".equals(product.getIsManualBonus())) {
					product.setMaxCashRefund(product.getMaxCashRefund()*100);
				} else {//自动返现模式
					product.setMaxCashRefund(0L);//新增产品的还没有默认类别也没有时间价格表，因此返现金额无法计算
				}
			} else {
				product.setIsManualBonus("N");
				product.setMaxCashRefund(0L);
			}
			
			if("GROUP".equals(name) || "FREENESS".equals(name) || "SELFHELP_BUS".equals(name)){
				product.setPayDeposit(Long.valueOf(0));
			}else{
				product.setPayDeposit(product.getPayDeposit()*100);
			}
			
			//Add at 2013-7-12 by XuSemon
			//所属公司为上海且路线类型为长途跟团游和长途自由行的产品,不需要启用预授权支付
			if(product.getFilialeName().equals(Constant.FILIALE_NAME.SH_FILIALE.getCode())
					&& (product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode())
							|| product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode()))){
				product.setPrePaymentAble("N");
			}else
				product.setPrePaymentAble("Y");
			
			product=(T)prodProductService.addProductChannel(product, channel, getOperatorNameAndCheck());

			comMessageService.addSystemComMessage(Constant.EVENT_TYPE.ADD_PRODUCT.name(), "新增销售产品:" + product.getProductName(), Constant.SYSTEM_USER);
			
			//系统自动添加积分抵用/奖金抵扣/期票Tag==========Begin==========
			List<Long> productIds=new ArrayList<Long>();
			productIds.add(product.getProductId());
			List<ProdProductTag> prodProductTags=null;
			//--积分抵用
			if(!"false".equals(product.getCouponAble())){
				prodProductTags=new ArrayList<ProdProductTag>();
				ProdProductTag prodProductTag =new ProdProductTag();
				prodProductTag.setProductId(product.getProductId());
				prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
				prodProductTags.add(prodProductTag);
				prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.POINT_PAY.getCnName());
			}
			
			//--奖金抵扣
			MarkCouponPointChange markCouponPointChange = markCouponPointChangeService.selectBySubProductType(product.getSubProductType());
			if("Y".equals(product.getCanPayByBonus()) && markCouponPointChange!=null){
				if(prodProductTags==null){
					prodProductTags=new ArrayList<ProdProductTag>();
					ProdProductTag prodProductTag =new ProdProductTag();
					prodProductTag.setProductId(product.getProductId());
					prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
					prodProductTags.add(prodProductTag);
				}
				prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.BONUS_PAY.getCnName());
			}
			//--期票
			if(product.IsAperiodic()){
				if(prodProductTags==null){
					prodProductTags=new ArrayList<ProdProductTag>();
					ProdProductTag prodProductTag =new ProdProductTag();
					prodProductTag.setProductId(product.getProductId());
					prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
					prodProductTags.add(prodProductTag);
				}
				prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.APERIODIC.getCnName());
			}
			
			//系统自动添加积分抵用/奖金抵扣/期票Tag==========End==========

            List<ProdProductRelation> prodProductRelationList = prodProductRelationService.getRelatProduct(product.getProductId());

            if (product.getProductType().equals(Constant.PRODUCT_TYPE.ROUTE.getCode())) {
                result.put("bindingInsurance", "Y");
                //没有绑定到保险
                if (prodProductRelationList == null || prodProductRelationList.size() == 0) {
                    result.put("bindingInsuranceInfo", "没有自动绑定保险产品，请到附加产品中手动操作");
                } else {
                    ProdProductRelation relation = prodProductRelationList.get(0);

                    result.put("bindingInsuranceInfo",
                            "默认绑定保险产品：保险产品ID ：" + relation.getRelationProduct().getProductId() +
                            "，保险产品名称：" + relation.getRelationProduct().getProductName());
                }
            }


        } else {
			//取出数据库当中的产品，来做保存
			T productEntity=(T)prodProductService.getProdProduct(product.getProductId());
			
			//是否修改了团号 add by taiqichao 20140321
			boolean travelGroupCodeChanged=false;
			if(StringUtils.isNotBlank(productEntity.getTravelGroupCode())
					&&StringUtils.isNotBlank(product.getTravelGroupCode())
					&&!productEntity.getTravelGroupCode().equals(product.getTravelGroupCode())){
				travelGroupCodeChanged=true;
			}else if(StringUtils.isBlank(productEntity.getTravelGroupCode())
					&&StringUtils.isNotBlank(product.getTravelGroupCode())){
				travelGroupCodeChanged=true;
			}
			
			//判断产品类型属性是否变化，如果变化则清空“其他属性”中定制属性的数据
			if(product.getProductType().equals("ROUTE") || product.getProductType().equals("HOTEL")){
				if(!productEntity.getSubProductType().equals(product.getSubProductType())){
					isClearProdModel="Y";
				}
			}
			Enumeration<String> params=getRequest().getParameterNames();				
			productEntity=merginProduct(product,productEntity,params);
			
			//设置点评返现金额
			if("Y".equals(productEntity.getIsRefundable())){
				if("Y".equals(productEntity.getIsManualBonus())) {
					productEntity.setMaxCashRefund(productEntity.getMaxCashRefund()*100);
				} else {
					if(productEntity.getSellPrice()!=null) {
						//获取产品最小可返现金额
						Long bonusReturnAmount=prodProductService.getProductBonusReturnAmount(productEntity);
						productEntity.setMaxCashRefund(bonusReturnAmount);
					} else {
						productEntity.setMaxCashRefund(0L);
					}
				}
			} else {
				productEntity.setIsManualBonus("N");
				productEntity.setMaxCashRefund(0L);
			}
			
			productEntity.setBounsEnd(product.getBounsEnd());
			productEntity.setBounsLimit(product.getBounsLimit());
			productEntity.setBounsReason(product.getBounsReason());
			productEntity.setBounsScale(product.getBounsScale());
			productEntity.setBounsStart(product.getBounsStart());
			
			
			/**
			GROUP("短途跟团游"),
			GROUP_LONG("长途跟团游"),
			GROUP_FOREIGN("出境跟团游"),
			FREENESS("目的地自由行"),
			FREENESS_LONG("长途自由行"),
			FREENESS_FOREIGN("出境自由行"),
			SELFHELP_BUS("自助巴士班"),
			 */
			String name = productEntity.getSubProductType();
			if(("GROUP".equals(name) || "GROUP_LONG".equals(name) || "GROUP_FOREIGN".equals(name) || "FREENESS".equals(name) || "FREENESS_LONG".equals(name) || "FREENESS_FOREIGN".equals(name) || "SELFHELP_BUS".equals(name) )){
				if("FREENESS_FOREIGN".equals(name) || "GROUP_FOREIGN".equals(name)){
					productEntity.setIsForegin("Y");
					productEntity.setRegionName(productEntity.getRegionName());					
				}else{
					//如果为境内则没有地域  保存为
					productEntity.setRegionName("");
					productEntity.setIsForegin("N");
				}
			}else{
				if("N".equals(productEntity.getIsForegin())){
					productEntity.setIsForegin(productEntity.getIsForegin());
					productEntity.setRegionName("");
				}else{
					productEntity.setIsForegin(productEntity.getIsForegin());
					productEntity.setRegionName(productEntity.getRegionName());
				}
					
			}
			
			if("GROUP".equals(name) || "FREENESS".equals(name) || "SELFHELP_BUS".equals(name)){
				productEntity.setPayDeposit(Long.valueOf(0));
			}else{
				productEntity.setPayDeposit(productEntity.getPayDeposit()*100);
			}

            if("INSURANCE".equals(name)){
                if(StringUtils.isNotBlank(productEntity.getApplicableSubProductType())){
                    productEntity.setApplicableSubProductType(productEntity.getApplicableSubProductType().replace(" ",""));
                }
            }else{
                productEntity.setApplicableSubProductType("");
                productEntity.setApplicableTravelDaysLimit(null);
                productEntity.setApplicableTravelDaysCaps(null);
            }

			//Add at 2013-7-12 by XuSemon
			//所属公司为上海且路线类型为长途跟团游和长途自由行的产品,不需要启用预授权支付
			if(product.getFilialeName().equals(Constant.FILIALE_NAME.SH_FILIALE.getCode())
					&& (product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode())
							|| product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode()))){
				productEntity.setPrePaymentAble("N");
			}else
				productEntity.setPrePaymentAble("Y");

			prodProductService.updateProdProduct(productEntity, channel,getOperatorNameAndCheck(),isClearProdModel);
			
			//系统自动添加积分抵用/奖金抵扣/期票Tag==========Begin==========
			List<Long> productIds=new ArrayList<Long>();
			productIds.add(product.getProductId());
			List<ProdProductTag> prodProductTags=null;
			//--积分抵用
			if(!"false".equals(product.getCouponAble())){
				prodProductTags=new ArrayList<ProdProductTag>();
				ProdProductTag prodProductTag =new ProdProductTag();
				prodProductTag.setProductId(product.getProductId());
				prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
				prodProductTags.add(prodProductTag);
			}
			prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.POINT_PAY.getCnName());
			
			//--奖金抵扣
			if("Y".equals(product.getCanPayByBonus())){
				if(prodProductTags==null){
					prodProductTags=new ArrayList<ProdProductTag>();
					ProdProductTag prodProductTag =new ProdProductTag();
					prodProductTag.setProductId(product.getProductId());
					prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
					prodProductTags.add(prodProductTag);
				}
			}else{
				if(prodProductTags!=null){
					prodProductTags.clear();
				}
			}
			prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.BONUS_PAY.getCnName());
			//--期票
			if(product.IsAperiodic()){
				if(prodProductTags==null){
					prodProductTags=new ArrayList<ProdProductTag>();
					ProdProductTag prodProductTag =new ProdProductTag();
					prodProductTag.setProductId(product.getProductId());
					prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
					prodProductTags.add(prodProductTag);
				}
			}else{
				if(prodProductTags!=null){
					prodProductTags.clear();
				}
			}
			prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.APERIODIC.getCnName());
			//系统自动添加积分抵用/奖金抵扣/期票Tag==========End==========
			
			try {
				// 发送修改销售产品的消息
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(product.getProductId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			removeProductCache(product.getProductId());
			
			//如果修改了团号前缀重新生成团号 add by taiqichao 20140321
			if(travelGroupCodeChanged&&RouteUtil.hasTravelGroupProduct(product)){
				opTravelGroupService.createTravelGroupByProductId(product.getProductId());
			}
		}
		prodProductService.markProductSensitive(product.getProductId(), hasSensitiveWord);
		
		doSaveAfter();
		result.put("productId", product.getProductId());
		result.put("hasNew", hasNew);
		result.put("isClearRoute", isClearProdModel);
		result.output(getResponse());
	}

	public List<CodeItem> getBooleanList()
	{
		List<CodeItem> codeItems = CodeSet.getInstance().getCodeList("TRUE_FALSE");
		for (CodeItem codeItem : codeItems) {
			codeItem.setCode(codeItem.getCode().toLowerCase());
		}
		return codeItems;
	}
	
	/**
	 * 合并提交过来的数据到一起
	 * @param form
	 * @param entity
	 */
	private T merginProduct(T form,T entity,Enumeration<String> params){		
		return CopyUtil.copy(entity, form, params,"product.",fields);
	}
	
	
	
	
	/**
	 * 渠道列表.
	 * @return
	 */
	public List<CodeItem> getChannelList(){
		List<CodeItem> list=CodeSet.getInstance().getCodeList(Constant.CODE_TYPE.CHANNEL.name());
		if(!ArrayUtils.isEmpty(initChannel)){
			for(CodeItem ci:list){
				if(ArrayUtils.contains(initChannel, ci.getCode())){
					ci.setChecked("true");
				}
			}
		}
		return list;
	}
	
	public List<CodeItem> getFilialeNameList(){
		return CodeSet.getInstance().getCodeListAndBlank(Constant.CODE_TYPE.FILIALE_NAME.name());
	}

	
	

	
	/**
	 * @return the channel
	 */
	public String[] getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String[] channel) {
		this.channel = channel;
	}
	
	
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @param productMessageProducer the productMessageProducer to set
	 */
	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	
	/**
	 * @return the product
	 */
	public T getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(T product) {
		this.product = product;
	}
	
	/**
	 * @param permUserService the permUserService to set
	 */
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	/**
	 * @param comMessageService the comMessageService to set
	 */
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	/**
	 * @return the productTravellerInfoOptions
	 */
	public String[] getProductTravellerInfoOptions() {
		return productTravellerInfoOptions;
	}

	/**
	 * @param productTravellerInfoOptions the productTravellerInfoOptions to set
	 */
	public void setProductTravellerInfoOptions(String[] productTravellerInfoOptions) {
		this.productTravellerInfoOptions = productTravellerInfoOptions;
	}
	
	public String[] getProductContactInfoOptions() {
		return productContactInfoOptions;
	}

	public void setProductContactInfoOptions(String[] productContactInfoOptions) {
		this.productContactInfoOptions = productContactInfoOptions;
	}

	public String[] getFirstTravellerInfoOptions() {
		return firstTravellerInfoOptions;
	}

	public void setFirstTravellerInfoOptions(String[] firstTravellerInfoOptions) {
		this.firstTravellerInfoOptions = firstTravellerInfoOptions;
	}

	/**
	 * @return the cashRefundYuan
	 */
	public long getCashRefundYuan() {
		return cashRefundYuan;
	}

	/**
	 * @param cashRefundYuan the cashRefundYuan to set
	 */
	public void setCashRefundYuan(long cashRefundYuan) {
		this.cashRefundYuan = cashRefundYuan;
	}

	/**
	 * 用户必填项列表
	 * @return
	 */
	public List<CodeItem> getTravellerInfoOptionsList(){
		return CodeSet.getInstance().getCodeList("TRAVELLER_INFO_OPTIONS");
	}
	public List<CodeItem> getContactInfoOptionsList(){
		return CodeSet.getInstance().getCodeList("CONTACT_INFO_OPTIONS");
	}
	protected static final Log logger=LogFactory.getLog(ProdProductAction.class);

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setMarkCouponPointChangeService(
			MarkCouponPointChangeService markCouponPointChangeService) {
		this.markCouponPointChangeService = markCouponPointChangeService;
	}

    public void setProdProductRelationService(ProdProductRelationService prodProductRelationService) {
        this.prodProductRelationService = prodProductRelationService;
    }

    public List<CodeItem> getShowSaleDaysList(){
		List<CodeItem> list = new ArrayList<CodeItem>();
		for(int day:Arrays.asList(1,5,7,10,14,15,20,30,60,90,120,150,180,360)){
			String code=String.valueOf(day);
			CodeItem item = new CodeItem(code,code);
			list.add(item);
		}
		return list;
	}

	public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
		this.opTravelGroupService = opTravelGroupService;
	}
	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	public void setBounsReturnScaleService(
			BounsReturnScaleService bounsReturnScaleService) {
		this.bounsReturnScaleService = bounsReturnScaleService;
	}
}
