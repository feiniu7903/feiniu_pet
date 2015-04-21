package com.lvmama.ord.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEContractLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.RouteProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.XmlObjectUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ECONTRACT_TEMPLATE;
import com.lvmama.ord.dao.OrdEContractDAO;
import com.lvmama.ord.dao.OrdEContractLogDAO;
import com.lvmama.ord.dao.OrdEcontractSignLogDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdEContractDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductItemDAO;
import com.lvmama.prd.dao.ProdProductPlaceDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.dao.RouteProductDAO;
import com.lvmama.prd.dao.ViewJourneyDAO;
import com.lvmama.prd.dao.ViewPageDAO;

public class CommonOrderContractLogic {
	private static final Log LOG = LogFactory.getLog(CommonOrderContractLogic.class);
	private static Map<String,String> classMap=new HashMap<String,String>();
	private static final String PROD_ECONTRACT="prodEContract_";
	private ProdProductDAO prodProductDAO;
	private RouteProductDAO routeProductDAO;
	private ProdEContractDAO prodEContractDAO;
	private ProdProductItemDAO prodProductItemDAO;
	private ViewJourneyDAO  viewJourneyDAO;
	private ProdProductPlaceDAO prodProductPlaceDAO;
	private OrdEContractDAO ordEContractDAO;
	private OrdEContractLogDAO ordEContractLogDAO;
	private ViewPageDAO viewPageDAO;
	private ComLogDAO comLogDAO;
	private OrdEcontractSignLogDAO ordEcontractSignLogDAO;
	private MetaProductDAO metaProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private ProdTimePriceDAO prodTimePriceDAO;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * 优惠金额
	 */
	private FavorOrderService favorOrderService;
	/**
	 * 优惠策略
	 */
	private BusinessCouponService businessCouponService;
	static{
		
//		classMap.put("SH_ECONTRACT"       , "shOrderContractLogic");    
//  		classMap.put("BJ_ECONTRACT"       , "bjOrderContractLogic");
//  		classMap.put("BJ_ABROAD_ECONTRACT"       , "bjOrderContractLogic");
//  		classMap.put("GZ_ECONTRACT"       , "gzOrderContractLogic");
//  		classMap.put("SC_ECONTRACT"       , "scOrderContractLogic");
//  		classMap.put("SH_ABROAD_ECONTRACT"   , "abroadOrderContractLogic");
//  		classMap.put("SP_ECONTRACT", "spContractLogic");
  		classMap.put("BJ_ONEDAY_ECONTRACT", "bjOneOrderContractLogic");
  		//TODO 修改LOGIC
  		classMap.put(ECONTRACT_TEMPLATE.GROUP_ECONTRACT.name(), "abroadOrderContractLogic");
  		classMap.put(ECONTRACT_TEMPLATE.GROUP_ABROAD_ECONTRACT.name(), "abroadOrderContractLogic");
  		classMap.put(ECONTRACT_TEMPLATE.PRE_PAY_ECONTRACT.name(), "abroadOrderContractLogic");
  		classMap.put(ECONTRACT_TEMPLATE.TRAVELPRO_PRESELL_ECONTRACT.name(), "abroadOrderContractLogic");
	}
	/**
	 * 取得PDF中的填充数据
	 * @param order
	 * @return
	 */
	public  Map<String, Object> getDataStore(final OrdOrder order,final ProdEContract prodEContract) {
		
 		 Map<String,Object> dataStore = new HashMap<String, Object>();
 		dataStore.put("orderId", order.getOrderId()); 
 		dataStore.put("filialeName", order.getFilialeName());
		dataStore.put("template", prodEContract.getEContractTemplate());
 		OrdOrderItemProd orderItemProd =order.getMainProduct();
 		ProdProduct mainProduct =prodProductDAO.selectProductDetailByPrimaryKey(orderItemProd.getProductId());
 		if(null==mainProduct){
 			LOG.error("根据订单号  "+order.getOrderId()+"  没有找到主产品");
 		}
 		OrdEContract ordContract = ordEContractDAO.queryByOrderId(order.getOrderId());
 		//旅游产品预售协议
 	 	if(ECONTRACT_TEMPLATE.TRAVELPRO_PRESELL_ECONTRACT.name().equals(prodEContract.getEContractTemplate())){
 	 		dataStore.put("TravelProductName", mainProduct.getProductName());
 	 	}
 		createCompanyInfo(order,dataStore);
 		//取得订单游玩人
 		createTouristInfo(order,dataStore,ordContract,prodEContract.getEContractTemplate());
 	
		//取得模板基本信息
		createTemplateBaseInfo(order,mainProduct,prodEContract,dataStore);
		
		//取得签约信息
		createSubscriberInfo(order,prodEContract,dataStore);
		
		ProdRoute	  prodRoute=prodProductDAO.getProdRouteById(mainProduct.getProductId());
		
		if (!ECONTRACT_TEMPLATE.PRE_PAY_ECONTRACT.name().equals(prodEContract.getEContractTemplate())) {
	 		//最小成团人数
	 		Long initialNum = prodRoute.getInitialNum();
	 		dataStore.put("initialNum", initialNum);	/**最少成团人数*/
		
		    //取得保险信息
			createPolicyInfo(order,mainProduct,dataStore);
	
			//取得优惠金额
			createYouhui(order,dataStore);
			
			//取得订单费用信息
			createTourFeeInfo(order,mainProduct,dataStore);
			
			//取得产品信息
			createProductInfo(order,dataStore);
	
//			createProductDescription(mainProduct,dataStore,orderItemProd.getMultiJourneyId());
			
			//取得产品提供方信息
			createProviderInfo(order,prodEContract,dataStore);
			
			if (ECONTRACT_TEMPLATE.BJ_ONEDAY_ECONTRACT.name().equals(prodEContract.getEContractTemplate())) {
				//取得行程信息
				createSchedulerInfo(order,mainProduct,prodRoute,dataStore);
	 		}
 		}
		
		//调用子类实例进行创建
		OrderContractLogic orderContract=newInstance(prodEContract.getEContractTemplate());
		if(null!=orderContract){
			dataStore.put("contractNo", initContractNo(order,ordContract));						/**合同编号*/
			dataStore.put("template", prodEContract.getEContractTemplate());
			orderContract.continueData(order, mainProduct, dataStore);
		}
		//除去空值
		removeNullValue(dataStore);
		return dataStore;
	}
	private void createCompanyInfo(OrdOrder order, Map<String, Object> dataStore){
		//旅游局投诉信息 , 分公司信息
 		String complaints = "上海市旅游局 地址:上海市大沽路 100 号,邮编:200003 上海市旅游质量监督所 投诉电话:021-64393615";
 		String companyName ="上海驴妈妈兴旅国际旅行社有限公司";
 		String companyAddress ="金沙江路1759号圣诺亚大厦B座7F";
 		String companyNum = "L-SH-CJ00056";
 		
		if("BJ_FILIALE".equals(order.getFilialeName())){
			complaints = "北京市旅游发展委办公室 地址:北京市朝阳区建国门外大街 28 号(北 京旅游大厦 1304 室),邮编:100022 投诉电话:010-12301";
			companyName ="上海驴妈妈兴旅国际旅行社有限公司北京分社";
	 		companyAddress ="北京市朝阳区左家庄曙光西里甲6号院时间国际大厦A座2707-09室";
	 		companyNum = "L-SH-CJ00056-BJF-CY0057";
		}else if("GZ_FILIALE".equals(order.getFilialeName())){
			complaints = "广州市旅游局 地址:广州市东风西路 140 号东方金融大厦 13-15 楼, 邮编:510170 广州市旅游质量监督管理所 投诉电话:020-86666666";
			companyName ="上海驴妈妈兴旅国际旅行社有限公司广州分公司";
	 		companyAddress ="广州市越秀区小北路185-189号广州鹏源发展大厦1804/1805室";
	 		companyNum = "L-SH-CJ00056-YXFS001";
		}else if("CD_FILIALE".equals(order.getFilialeName())){
			complaints = "成都市旅游局 地址:成都市锦城大道 366 号 2 号楼 19 楼,邮编::610041 成都市旅游投诉电话:028-96927";
			companyName ="上海驴妈妈兴旅国际旅行社有限公司成都分社";
	 		companyAddress ="成都市一环路西一段2号高升大厦506室";
	 		companyNum = "L-SH-CJ00056-A-fs-001";
		}else if("SY_FILIALE".equals(order.getFilialeName())){
			complaints = "三亚市旅游局 地址:文明路 145 市政府第二办公大楼 7 楼, 邮编:572000 投诉电话:028-96927";
		}
		dataStore.put("complaints", complaints);
		dataStore.put("companyName", companyName);
		dataStore.put("companyAddress", companyAddress);
		dataStore.put("companyNum", companyNum);
		dataStore.put("companyTel", "10106060");
		dataStore.put("companyTel1", "10106060*3");
	}
	private void createYouhui(OrdOrder order, Map<String, Object> dataStore) {
		//优惠券列表
		List<OrdOrderAmountItem> listAmountItem=orderServiceProxy.queryOrdOrderAmountItem(new Long(order.getOrderId()), "ALL");
		//优惠策略(业务端)
		Map<String,Object> businessCouponparam = new HashMap<String,Object>();
		businessCouponparam.put("objectId", order.getOrderId());
		businessCouponparam.put("objectType", "ORD_ORDER_ITEM_PROD");
		List<MarkCouponUsage> markCouponUsageList=favorOrderService.selectByParam(businessCouponparam);
		
		businessCouponparam.clear();
		Float earlyCouponAmount = 0f;
		Float moreCouponAmount = 0f;
		
		if(markCouponUsageList!=null && markCouponUsageList.size()>0){
			List<Long> businessCouponIds =new ArrayList<Long>();
			for(MarkCouponUsage markCouponUsage:markCouponUsageList){
				businessCouponIds.add(markCouponUsage.getCouponCodeId());
			}
			businessCouponparam.put("businessCouponIds", businessCouponIds);
			List<BusinessCoupon> businessCouponList=businessCouponService.selectByIDs(businessCouponparam);
			Long tempEarlyAmount=0l;
			Long tempMoreAmount=0l;
			if(businessCouponList != null && businessCouponList.size() > 0){
				for(BusinessCoupon businessCoupon :businessCouponList){
					for(MarkCouponUsage markCouponUsage:markCouponUsageList){
						if(businessCoupon.getBusinessCouponId().compareTo(markCouponUsage.getCouponCodeId())==0){
							if("EARLY".equals(businessCoupon.getCouponType())){
								//1.早订早惠
								tempEarlyAmount+=markCouponUsage.getAmount();
							}else if ("MORE".equals(businessCoupon.getCouponType())){
								//2.多买多惠
								tempMoreAmount+=markCouponUsage.getAmount();
							}
						}
					}
				}
			}
			earlyCouponAmount=PriceUtil.convertToYuan(tempEarlyAmount);
			moreCouponAmount=PriceUtil.convertToYuan(tempMoreAmount);
			
		}
		float _sum = 0.0f;
		if (null != listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isCouponItem()) {
					BigDecimal b1 = new BigDecimal(Float.toString(_sum));
					BigDecimal b2 = new BigDecimal(Float.toString(PriceUtil.convertToYuan(item.getItemAmount())));
					_sum = b1.add(b2).floatValue();
					
				}
			}
		}
		
		if(earlyCouponAmount != 0){
			_sum += earlyCouponAmount;
		}
		
		if(moreCouponAmount != 0){
			_sum += moreCouponAmount;
		}
		
		dataStore.put("discountAmount", _sum);
		
	}
	public String initContractNo(final OrdOrder order,OrdEContract ordContract){
		return EcontractUtil.initContractNo(null!=ordContract?ordContract.getEcontractNo():null,order.getOrderId(),order.getVisitTime());
	}
	/**
	* 从身份证获取出生日期
	* @param cardNumber 已经校验合法的身份证号
	* @return Strig YYYY-MM-DD 出生日期
	*/
	public static String getBirthDateFromCard(String cardNumber){
	String card = cardNumber.trim();
	    String year;
	    String month;
	    String day;
	    if (card.length()==18){ //处理18位身份证
	        year=card.substring(6,10);
	        month=card.substring(10,12);
	        day=card.substring(12,14);
	    }else{ //处理非18位身份证
	    year=card.substring(6,8);
	        month=card.substring(8,10);
	        day=card.substring(10,12);
	    year="19"+year;        
	    }
	    if (month.length()==1){
	        month="0"+month; //补足两位
	    }
	    if (day.length()==1){
	        day="0"+day; //补足两位
	    }
	    return year+"-"+month+"-"+day;
	}
	/**
	 * 取得游客信息
	 */
	private void createTouristInfo(final OrdOrder order,Map<String,Object> dataStore,OrdEContract ordContract ,String templateName){
		//1.取得订单游玩人
	    Long adults=0L; //成人数量
	    Long childs=0L; //儿童数量
	    Long orderPersonCount=0L;//人总数
	    Long travellerCount=0L;
		List<OrdPerson> ordPersonList=order.getPersonList();
		String allTraveller="";
		String allTravellerName="";
		String healthState = "";
		if(ordContract!=null){
			healthState="健康良好";
		}
		String firstTravellerMobile = "";
	    for(int i=0;i<ordPersonList.size();i++){
	    	OrdPerson ordPerson= ordPersonList.get(i);
	    	if("TRAVELLER".equals(ordPerson.getPersonType())){
	    		if(StringUtil.isEmptyString(firstTravellerMobile)){
	    			firstTravellerMobile = ordPerson.getMobile();
	    		}
	    		dataStore.put("traveller", ordPerson.getName());									/**订单游玩人*/
	    		String certType=ordPerson.getCertType();
	    		String birthday =Constant.E_CONTRACT_DEFAULT_VALUE;
				if ("ID_CARD".equals(certType)) {
					if(StringUtil.isNotEmptyString(ordPerson.getCertNo())){
						birthday = getBirthDateFromCard(ordPerson.getCertNo());
					}
					certType = "身份证";
				} else{ 
					if(ordPerson.getBrithday()!=null){
						birthday = DateUtil.formatDate(ordPerson.getBrithday(), "yyyy-MM-dd");
		    		}
					if ("HUZHAO".equals(certType)) {
						certType = "护照";
					} else if ("OTHER".equals(certType)) {
						certType = "其它证件";
					} else {
						certType = "其它";
					}
				}
	    		allTravellerName+=ordPerson.getName()+" ";
	    		orderPersonCount+=1;
	    		allTraveller +="<p class=\"textIndent24\">"+
	                    "（"+orderPersonCount+"）姓名：<em class=\"all-line all-line2\">"+ordPerson.getName()+"</em>出生日期：<em class=\"all-line all-line4\">"+birthday+"</em></p>"+
	                    "<p class=\"textIndent24\">证件类型：<em class=\"all-line all-line4\">"+certType+"</em>证件号：<em class=\"all-line all-line2\">"+ordPerson.getCertNo()+"</em></p>";
	    		if(!ECONTRACT_TEMPLATE.BJ_ONEDAY_ECONTRACT.name().equals(templateName)){
	    			allTraveller += "<p class=\"textIndent24\">健康或疾病状况： <em class=\"all-line all-line2\">"+healthState+"</em></p>";
	    		}
	    	}
	    	if("CONTACT".equals(ordPerson.getPersonType())){
	    		dataStore.put("contactPerson", ordPerson.getName());									/**联系人姓名*/
	    		dataStore.put("contactPersonMobile", ordPerson.getMobile());							/**联系人电话*/
	    		dataStore.put("contactCertNo", ordPerson.getCertNo());
	    	}
	    }
	    for(OrdOrderItemProd item: order.getOrdOrderItemProds()){
	    	if(Constant.PRODUCT_TYPE.ROUTE.name().equals(item.getProductType())){
	    		Long quantity=item.getQuantity();
	    		travellerCount+=quantity;
	    	}
	    }
	    
	    dataStore.put("allTraveller", allTraveller);
	    dataStore.put("firstTravellerMobile", firstTravellerMobile);
	    dataStore.put("allTravellerName", StringUtil.isNotEmptyString(allTravellerName)? allTravellerName : Constant.E_CONTRACT_DEFAULT_VALUE);
		dataStore.put("adultCount", adults>0?adults:Constant.E_CONTRACT_DEFAULT_VALUE);									/**成人数*/
		dataStore.put("childCount", childs>0?childs:Constant.E_CONTRACT_DEFAULT_VALUE);									/**儿童数*/
		dataStore.put("orderPersonCount", orderPersonCount);										/**总人数*/
		dataStore.put("travellerCount", travellerCount);											/**游客总人数*/
	}
	/**
	 * 取得旅游费用信息
	 */
	private void createTourFeeInfo(final OrdOrder order,final ProdProduct mainProduct,Map<String,Object> dataStore){
		
		List<OrdOrderItemProd> prods  = order.getOrdOrderItemProds();
		String travelExpensesDetail = "";
		float travelExpenses=0f;
		for(OrdOrderItemProd prod : prods){ 
			if (!Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(prod.getSubProductType())) {
				TimePrice tp = prodTimePriceDAO.getProdTimePrice(prod.getProductId(), prod.getProdBranchId(), order.getVisitTime());
				ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(prod.getProdBranchId());
				float p = PriceUtil.convertToYuan(tp.getPrice());
				travelExpenses+= p;
				if(branch.getAdultQuantity() > 0 ){
					travelExpensesDetail+="成人<em class=\"all-line all-line1\">"+p  / branch.getAdultQuantity()+"</em>元/人（"+branch.getBranchName()+"）；";
				}if(branch.getChildQuantity() > 0 ){
					travelExpensesDetail+="儿童<em class=\"all-line all-line1\">"+p / branch.getChildQuantity()+"</em>元/人（"+branch.getBranchName()+"）；";
				}
			}
		}
		dataStore.put("travelExpensesDetail", StringUtil.isNotEmptyString(travelExpensesDetail) ? travelExpensesDetail: Constant.E_CONTRACT_DEFAULT_VALUE);	
		//4.费用说明(包含，不包含)
		String description="",feeStr="",nofeeStr="",orderToKnowStr="",actionToKnow="",refundNotice="",serviceGuarantee="",playNotice="";//增加游玩提示
		//添加字段推荐项目，购物说明字段
		String recommendProject="",shoppingExplain="";
		dataStore.put("oughtPay", order.getOughtPayYuan());										/**旅游总费用 应付*/
		dataStore.put("actualPay", order.getActualPayYuan());	
		Float discountAmount =Float.parseFloat(dataStore.get("discountAmount").toString());
		Object policyMoneyCount = dataStore.get("policyMoneyCount");
		Float policyMoneyCountFloat = 0f;
		if(null!=policyMoneyCount){
			String str = String.valueOf(policyMoneyCount);
			if(!Constant.E_CONTRACT_DEFAULT_VALUE.equalsIgnoreCase(str)){
				policyMoneyCountFloat=Float.valueOf(str);	
			}
		}
		dataStore.put("travelExpenses", order.getOughtPayFloat() - policyMoneyCountFloat);		
		ViewPage viewPage = viewPageDAO.getViewPageByProductId(mainProduct.getProductId());
		if(null==viewPage){
			return;
		}
		Map<String,Object> constents = viewPage.getContents();
		boolean isMultiJourney = false;
		//多行程取对应的行程的费用说明
		if(mainProduct.isRoute()) {
			ProdRoute pr = (ProdRoute) mainProduct;
			isMultiJourney = pr.hasMultiJourney();
		}
		if(isMultiJourney) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("multiJourneyId", order.getMainProduct().getMultiJourneyId());
			params.put("contentType", Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
			ViewContent ccVc = viewPageDAO.getViewContentByMultiJourneyId(params);
			if(ccVc != null) {
				feeStr = ccVc.getContent();
			}
			
			params.put("contentType", Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
			ViewContent ncVc = viewPageDAO.getViewContentByMultiJourneyId(params);
			if(ncVc != null) {
				nofeeStr = ncVc.getContent();
			}		
		} else {
			//费用包含
			if(null != constents.get("COSTCONTAIN")){
				ViewContent content =(ViewContent) constents.get("COSTCONTAIN");
				feeStr = content.getContent();
			}
			//费用不包含
			if(null != constents.get("NOCOSTCONTAIN")){
				ViewContent content =(ViewContent) constents.get("NOCOSTCONTAIN");
				nofeeStr = content.getContent();
			}
		}
		//推荐项目
		if(null != constents.get("RECOMMENDPROJECT")){
			ViewContent content =(ViewContent) constents.get("RECOMMENDPROJECT");
			recommendProject = content.getContent();
		}
		//购物说明
		if(null != constents.get("SHOPPINGEXPLAIN")){
			ViewContent content =(ViewContent) constents.get("SHOPPINGEXPLAIN");
			shoppingExplain = content.getContent();
		}
		
		//行前须知
		if(null != constents.get("ACITONTOKNOW")){
			ViewContent content =(ViewContent) constents.get("ACITONTOKNOW");
			actionToKnow = content.getContent();
		}
		//预定须知
		if(null != constents.get("ORDERTOKNOWN")){
			ViewContent content =(ViewContent) constents.get("ORDERTOKNOWN");
			orderToKnowStr = content.getContent();
		}
		//退款说明
		if(null != constents.get("REFUNDSEXPLANATION")){
			ViewContent content =(ViewContent) constents.get("REFUNDSEXPLANATION");
			refundNotice = content.getContent();
		}
		//游玩提示
		if(null != constents.get("PLAYPOINTOUT")){
			ViewContent content =(ViewContent) constents.get("PLAYPOINTOUT");
			playNotice = content.getContent();
		}
		//服务保障
		if(null != constents.get("SERVICEGUARANTEE")){
			ViewContent content =(ViewContent) constents.get("SERVICEGUARANTEE");
			serviceGuarantee = content.getContent();
		}
		if(StringUtil.isNotEmptyString(feeStr)){
			description += "<p class=\"font14\">费用包含</p><p class=\"textIndent24\">"+feeStr.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(nofeeStr)){
			description += "<p class=\"font14\">费用不包含</p><p class=\"textIndent24\">"+nofeeStr.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(recommendProject)){
			description += "<p class=\"font14\">推荐项目</p><p class=\"textIndent24\">"+recommendProject.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(shoppingExplain)){
			description += "<p class=\"font14\">购物说明</p><p class=\"textIndent24\">"+shoppingExplain.replace("\n", ",<br/>")+"</p>";
		}
		
		String description_1="";
		if(StringUtil.isNotEmptyString(actionToKnow)){
			description_1 += "<p class=\"font14\">行前须知</p><p class=\"textIndent24\">"+actionToKnow.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(orderToKnowStr)){
			description_1 += "<p class=\"font14\">预订须知</p><p class=\"textIndent24\">"+orderToKnowStr.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(refundNotice)){
			description_1 += "<p class=\"font14\">退款说明</p><p class=\"textIndent24\">"+refundNotice.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(serviceGuarantee)){
			description_1 += "<p class=\"font14\">旅游服务保障</p><p class=\"textIndent24\">"+serviceGuarantee.replace("\n", ",<br/>")+"</p>";
		}
		if(StringUtil.isNotEmptyString(playNotice)){
			description_1 += "<p class=\"font14\">游玩提示</p><p class=\"textIndent24\">"+playNotice.replace("\n", ",<br/>")+"</p>";
		}
		dataStore.put("description", description);		
		dataStore.put("description_1",StringUtil.isNotEmptyString(description_1)?description_1:Constant.E_CONTRACT_DEFAULT_VALUE);	
	}
	
	/**
	 * 取得旅游行程信息
	 */
	private void createSchedulerInfo(final OrdOrder order,final ProdProduct mainProduct,ProdRoute prodRoute, Map<String,Object> dataStore){
	    RouteProduct  routeProduct=routeProductDAO.getProductByProductId(mainProduct.getProductId());
		if(null==routeProduct){
			LOG.error("根据主产品号   "+mainProduct.getProductId()+"  没有找到线路产品 RouteProduct 信息");
		}
		if(null==prodRoute){
			LOG.error("根据主产品号   "+mainProduct.getProductId()+"  没有找到线路产品 ProdRoute 信息");
		}
		//团号
		dataStore.put("groupNo", mainProduct.getBizcode()+"_"+(null!=order.getZhVisitTime()?order.getZhVisitTime():""));
		//出发地,目的地
		String fromAddress="",toAddress="";
		Long days = 0L,initialNum=0L;
		if(null!=routeProduct&&null!=routeProduct.getFrom()){
			fromAddress=routeProduct.getFrom().getName();
		}
		if(null!=routeProduct&&null!=routeProduct.getTo()){
			toAddress=routeProduct.getTo().getName();
		}
		dataStore.put("fromAddress", fromAddress);													/**出发地*/
		dataStore.put("toAddress", toAddress);														/**目的地*/
		dataStore.put("visitTime", order.getZhVisitTime());											/**出发时间*/
		dataStore.put("productName", mainProduct.getProductName());									/**旅游路线*/
		if(null!=prodRoute){
			days = prodRoute.getDays();
			initialNum=prodRoute.getInitialNum();
		}

		String tourDays="";
		if(null!=days&&days>0){
			if(days>1)
				tourDays=days+"天";
			else
				tourDays="1天";	
		}
		dataStore.put("days", tourDays);																/**行程天数*/
		if(null!=order.getZhVisitTime()&&null!=prodRoute&&null!=prodRoute.getDays()){
			java.util.Date visitTime=order.getVisitTime();
			java.util.Calendar calendar=java.util.Calendar.getInstance();
			calendar.setTime(visitTime);
			calendar.set(java.util.Calendar.DATE, (int) (calendar.get(java.util.Calendar.DATE)+prodRoute.getDays()-1));/**返回时间=出发时间+游玩天数-1**/
			dataStore.put("returnTime", DateUtil.getFormatDate(calendar.getTime(), "yyyy-MM-dd"));	/**返回时间*/
		}
		dataStore.put("initialNum", initialNum);										/**最少成团人数*/
		
	}
	/**
	 * 取得保险信息
	 */
	private void createPolicyInfo(final OrdOrder order,final ProdProduct mainProduct, Map<String,Object> dataStore){
		//1.保险单价
		Float policyPrice=0f;
		//2.保险总人数
		Long policyPersoncount=0L;
		if(null!=dataStore.get("orderPersonCount")&&!dataStore.get("orderPersonCount").equals(Constant.E_CONTRACT_DEFAULT_VALUE)){
			policyPersoncount=(Long)dataStore.get("orderPersonCount");
		}
		//3.保险总额
		Float policyMoneyCount=0f;
		//4.保险产品名称
		String policyName="";
		//5.是否同意保险
		String isPolicy="不同意";
		//6.保险描述
		String policyDescription="";
		List<OrdOrderItemProd> itemProds=order.getOrdOrderItemProds();
		for(int i=0;i<itemProds.size();i++){
			if(Constant.PRODUCT_TYPE.OTHER.name().equals(itemProds.get(i).getProductType()) && Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(itemProds.get(i).getSubProductType())){
				OrdOrderItemProd policyProduct=itemProds.get(i);
				policyPrice=policyProduct.getPriceYuan();
				policyName+=policyProduct.getProductName()+" ";
				isPolicy="同意";
				policyMoneyCount+=policyProduct.getQuantity()*policyProduct.getPriceYuan();
				ProdProduct pp=prodProductDAO.selectProductDetailByPrimaryKey(policyProduct.getProductId());
				policyDescription+=policyProduct.getProductName()+" **保险说明**:\r\n"+pp.getDescription()+"\r\n";
			}
		}
		dataStore.put("policyUnitPrice", policyPrice>0 && "同意".equals(isPolicy)?policyPrice:Constant.E_CONTRACT_DEFAULT_VALUE);										/**保险单价*/
		if(policyPrice>0){
			dataStore.put("oughtPay", order.getOughtPayYuan()-policyMoneyCount);
		}
		dataStore.put("policyPersonCount",policyPersoncount>0 && "同意".equals(isPolicy)?policyPersoncount:Constant.E_CONTRACT_DEFAULT_VALUE);							/**总人数*/
		dataStore.put("policyMoneyCount", policyMoneyCount>0 && "同意".equals(isPolicy)?policyMoneyCount:Constant.E_CONTRACT_DEFAULT_VALUE);							/**保险总额*/
		dataStore.put("isPolicy", isPolicy);			
		dataStore.put("policyNo",  "同意".equals(isPolicy)? "详见保单":Constant.E_CONTRACT_DEFAULT_VALUE);		
		/**是否同意保险*/
		dataStore.put("policyName", ""!=policyName && "同意".equals(isPolicy)?policyName:Constant.E_CONTRACT_DEFAULT_VALUE);											/**保险产品名称*/
		dataStore.put("policyDescription",""!=policyDescription && "同意".equals(isPolicy)?policyDescription:Constant.E_CONTRACT_DEFAULT_VALUE);						/**保险描述**/
		dataStore.put("policyPerson",""!=policyDescription && "同意".equals(isPolicy)?dataStore.get("allTravellerName"):Constant.E_CONTRACT_DEFAULT_VALUE);						/**保险人**/
	}
	
	private void createProductInfo(final OrdOrder order, Map<String,Object> dataStore){ 
		List<OrdOrderItemProd> ordOrderItemProds = order.getOrdOrderItemProds(); 
		StringBuffer sb = new StringBuffer("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" >"); 
		final String TD = "<td>";
		final String _TD = "</td>";

		sb.append("<th width=\"34%\">产品名称</th><th width=\"33%\">数量</th><th width=\"33%\">单价</th>"); 
		for(OrdOrderItemProd item:ordOrderItemProds){ 
		sb.append("<tr>"); 
		sb.append(TD).append(item.getProductName().replaceAll("<", "＜")
				.replaceAll(">", "＞").replaceAll("&", "＆")
				.replaceAll("\'", "＇").replaceAll("\"", "“")).append(_TD); 
		sb.append(TD).append(item.getQuantity()).append(_TD); 
		TimePrice tp = prodTimePriceDAO.getProdTimePrice(item.getProductId(), item.getProdBranchId(), order.getVisitTime());
		sb.append(TD).append(PriceUtil.convertToYuan(tp.getPrice())).append(_TD); 
		//sb.append(TD).append((item.getPrice()/100)*item.getQuantity()).append(_TD); 
		sb.append("</tr>"); 
		} 
		sb.append("</table>"); 
		dataStore.put("allProductItem", sb.toString()); 
		}
	/**	 * 取得合同模板基本信息
	 */
	private void createTemplateBaseInfo(final OrdOrder order,final ProdProduct mainProduct,final ProdEContract prodEContract, Map<String,Object> dataStore){
		//1.取得合同模板中的数据
		//分割字符
		String SUB_AREA_SIGN=",";
		 String travelFormalities = "OTHERS";
		 String otherTravelFormalities = "/";
		 String guideService;
		 String groupType;
		 travelFormalities=prodEContract.getTravelFormalities();
		 if(null!=travelFormalities){
			 String[] array=travelFormalities.split(SUB_AREA_SIGN);
			 for(int i=0;i<array.length;i++){
				 dataStore.put(PROD_ECONTRACT+"travelFormalities_"+array[i], Constant.E_CONTRACT_SELECTED_SIGN); 		/**旅行手续*/
			 }
		 }
		 otherTravelFormalities=prodEContract.getOtherTravelFormalities();
		 dataStore.put(PROD_ECONTRACT+"otherTravelFormalities", otherTravelFormalities); 			/**其它手续*/
		 guideService=prodEContract.getGuideService(); 
		 if(null!=guideService){
			 String[] array=guideService.split(SUB_AREA_SIGN);
			 for(int i=0;i<array.length;i++){
				 dataStore.put(PROD_ECONTRACT+"guideService_"+array[i], Constant.E_CONTRACT_SELECTED_SIGN); 			/**导游服务*/
			 }
		 }
		 
		 //组团方式
		 groupType=prodEContract.getGroupType();
		 if(null!=groupType){
			 String[] array=groupType.split(SUB_AREA_SIGN);
			 for(int i=0;i<array.length;i++){
				 dataStore.put(PROD_ECONTRACT+"groupType_"+array[i], Constant.E_CONTRACT_SELECTED_SIGN); 			/**导游服务*/
			 }
		 }
		 dataStore.put(PROD_ECONTRACT+"agency", prodEContract.getAgency());						/**被委托方**/
		 dataStore.put(PROD_ECONTRACT+"margin", StringUtil.isNotEmptyString(prodEContract.getMargin())? prodEContract.getMargin() : Constant.E_CONTRACT_DEFAULT_VALUE);						/**保证金**/
		 dataStore.put(PROD_ECONTRACT+"agencyAddress", prodEContract.getAgencyAddress());		/**地接社名称/地址/联系人/联系方式/电话**/
		 dataStore.put(PROD_ECONTRACT+"pathway", prodEContract.getPathway());									/**途径地点*/
		 dataStore.put(PROD_ECONTRACT+"complement", prodEContract.getComplement());							/**其他补充条款*/
		 dataStore.put("eContractTemplate", prodEContract.getEContractTemplate());
	}
	
	/**
	 * 产品提供方
	 */
	private void createProviderInfo(final OrdOrder order,final ProdEContract prodEContract, Map<String,Object> dataStore){
		String provider= Constant.E_CONTRACT_DEFAULT_VALUE;
		String provider_1= Constant.E_CONTRACT_DEFAULT_VALUE;
		String provider_2= Constant.E_CONTRACT_DEFAULT_VALUE;
		if(!"BYONESELF".equals(prodEContract.getGroupType())){
			provider=prodEContract.getAgency();
			OrdOrderItemMeta ooim = order.getMainProduct().getOrdOrderItemMetas().get(0);
			if(ooim != null && ooim.getSupplier()!=null ){
				provider_1 = ooim.getSupplier().getAddress();
				provider_2 = ooim.getSupplier().getLegalPerson();
			}
		}
		dataStore.put("provider", provider);
		dataStore.put("provider_1", provider_1);
		dataStore.put("provider_2", provider_2);
	}
	/**
	 * 签约信息
	 */
	private void createSubscriberInfo(final OrdOrder order,final ProdEContract prodEContract, Map<String,Object> dataStore){
		//甲方签字
		String signer="";
		//甲方代表
		String mainSinger="";
		//联系电话
		String contactTelephone="";
		if(null!=dataStore.get("contactPerson")){
			signer=(String) dataStore.get("contactPerson");
		}
		if(null!=dataStore.get("contactPerson")){
			mainSinger=(String) dataStore.get("contactPerson");
		}
		if(null!=dataStore.get("contactPersonMobile")){
			contactTelephone=(String) dataStore.get("contactPersonMobile");
		}
		dataStore.put("signer", signer);
		dataStore.put("mainSinger", mainSinger);
		if(null==order.getCreateTime()){
			order.setCreateTime(new Date());
		}
		dataStore.put("signDate", DateUtil.getFormatDate(order.getCreateTime(), "yyyy-MM-dd"));	//日    期
		dataStore.put("signDate_year", DateUtil.getFormatDate(order.getCreateTime(), "yyyy"));	//日    期
		dataStore.put("signDate_month", DateUtil.getFormatDate(order.getCreateTime(), "MM"));	//日    期
		dataStore.put("signDate_day", DateUtil.getFormatDate(order.getCreateTime(), "dd"));	//日    期
		dataStore.put("contactTelephone", contactTelephone);
	}
	/**
	 * 
	 */
	private void createProductDescription(final ProdProduct mainProduct,Map<String,Object> dataStore,Long multiJourneyId){
		String tourDescritpion="";//长线，短线说明 
		String productDescription="";//费用说明
		String productName="";//旅游线路
		StringBuffer tourScheduler=new StringBuffer();//行程计划
		StringBuffer tourPlace=new StringBuffer();//途径地址
	//	tourDescritpion=ReadRouteDescUtil.readRouteDesc(mainProduct.getProductType(), mainProduct.getSubProductType());
		productDescription=mainProduct.getDescription();
		productName=mainProduct.getProductName();
		List<ViewJourney> list=null;
		if(mainProduct.isRoute()) {
			ProdRoute pr = (ProdRoute) mainProduct;
			if(pr.hasMultiJourney()) {
				if(multiJourneyId != null) {
					list = viewJourneyDAO.getViewJourneyByMultiJourneyId(multiJourneyId);
				}
			} else {
				list = viewJourneyDAO.getViewJourneysByProductId(mainProduct.getProductId());
			}
		}
		if(list != null) {
			for(ViewJourney viewJourney:list){
				Long seq=viewJourney.getSeq();
				String title=viewJourney.getTitle();
				String dinner=viewJourney.getDinner();
				String hotel=viewJourney.getHotel();
				tourScheduler.append(" 第"+seq+"天      "+title+"\r\n");
				tourScheduler.append(" 用餐："+(null!=dinner?dinner:Constant.E_CONTRACT_DEFAULT_VALUE)+"\r\n");
				tourScheduler.append(" 住宿："+(null!=hotel?hotel:Constant.E_CONTRACT_DEFAULT_VALUE)+"\r\n");
				tourScheduler.append(" 交通："+(null!=viewJourney.getTrafficDesc()?viewJourney.getTrafficDesc():Constant.E_CONTRACT_DEFAULT_VALUE)+"\r\n");
				tourScheduler.append(" 内容："+(null!=viewJourney.getContent()?viewJourney.getContent():Constant.E_CONTRACT_DEFAULT_VALUE)+"\r\n");
				tourScheduler.append(" 游玩景点:"+(null!=viewJourney.getPlaceDesc()?viewJourney.getPlaceDesc():Constant.E_CONTRACT_DEFAULT_VALUE)+"\r\n");
				tourScheduler.append("\r\n");
			}
		}
		List<ProdProductPlace> placeList=prodProductPlaceDAO.selectByProductId(mainProduct.getProductId());
		tourPlace.append("途经地点：\r\n");
		for(ProdProductPlace place:placeList){
			String placeName=place.getPlaceName();
			String isFrom =place.getFrom();
			if("true".equals(isFrom)){
				isFrom="(为出发地)";
			}else{
				isFrom="";
			}
			String isTo=place.getTo();
			if("true".equals(isTo)){
				isTo="(为目的地)";
			}else{
				isTo="";
			}
			tourPlace.append(placeName+isFrom+isTo+"    ");
		}
		tourPlace.append("\r\n");
		dataStore.put("productDescription", null!=productDescription?productDescription:Constant.E_CONTRACT_DEFAULT_FILL);
		dataStore.put("productName", null!=productName?productName:Constant.E_CONTRACT_DEFAULT_FILL);
		dataStore.put("tourScheduler", null!=tourScheduler?tourScheduler:Constant.E_CONTRACT_DEFAULT_FILL);
		dataStore.put("tourDescritpion", null!=tourDescritpion?tourDescritpion:Constant.E_CONTRACT_DEFAULT_FILL);
		dataStore.put("tourPlace", null!=tourPlace?tourPlace.toString():Constant.E_CONTRACT_DEFAULT_FILL);
	}
	/**
	 * 取得各个合同的不同部分属性值
	 * @param templateName
	 * @return
	 */
	private   OrderContractLogic newInstance(String templateName){
		OrderContractLogic logic;
		try {
			logic = (OrderContractLogic)SpringBeanProxy.getBean( classMap.get(templateName));
			return logic;
		} catch (Exception e) {
			LOG.error("合同管理 导出PDF 根据"+templateName+"初始化类失败:"+e.getMessage());
		}
		return null;
	}
	/**
	 * 去掉空值
	 * @param dataStore
	 */
	private void removeNullValue(Map<String,Object> dataStore){
		Iterator<String> iterator=dataStore.keySet().iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			Object object=dataStore.get(key);
			if(null==object){
				dataStore.put(key, Constant.E_CONTRACT_DEFAULT_VALUE);
			}else if(object instanceof String && (((String)object)).matches("^.*[<>&\'\"].*$") && EcontractUtil.isReplaceVal(key)){
				dataStore.put(
						key,
						((String) object).replaceAll("<", "＜")
								.replaceAll(">", "＞").replaceAll("&", "＆")
								.replaceAll("\'", "＇").replaceAll("\"", "“"));
			}
		}
	}
	/**
	 * 取得合同信息
	 * @param orderId
	 * @return
	 */
	public Long getContractContent(final Long orderId){
		OrdEContract ordEContract = ordEContractDAO.queryByOrderId(orderId);
		if(null==ordEContract){
			LOG.error("根据订单号 "+orderId +" 没有找到电子合同记录！");
			return null;
		}
		return ordEContract.getContentFileId();
	}	
	public String getContractComplement(final Long orderId,final String complementTemplate){
		OrdEContract contract = ordEContractDAO.queryByOrderId(orderId);
		String complementXml = contract.getComplementXml();
		Map<String, Object> complementData = (Map<String, Object>) XmlObjectUtil.xml2Bean(complementXml, java.util.HashMap.class);
		String complement=null;
		try {
			complement = StringUtil.composeMessage(complementTemplate,complementData);
		} catch (Exception e) {
			LOG.error("根据订单号  " + orderId + " 取订单的补充条款出错\r\n" + e.getMessage());
		}
		return complement;
	}
	/**
	 * @param order
	 * @return
	 */
	public String getComplementDataXml(final OrdOrder order,final ProdEContract prodContract){
		//1.取供应商名称
		String supplierName="";
		//2.取补充条款
		if("AGENCY".equals(prodContract.getGroupType())){
			supplierName ="本订单订购产品由"+prodContract.getAgency()+"提供。";
		}
		//创建LIST
		Map<String,String> map = new HashMap<String,String>();
		String template = EcontractUtil.getContractComplementTemplate(order);
		map.put("template", template);
		map.put("supplierName",supplierName );
		map.put("addition", null!=prodContract.getComplement()?prodContract.getComplement():"");
		//3.创建XML
		return XmlObjectUtil.bean2Xml(map);
	} 
	/**
	 * 获取订单合同信息
	 * @param orderId
	 * @return
	 */
	public OrdEContract createOrderContract(final OrdOrder order,final ProdEContract prodContract,final Long contentFileId, final Long fixedFileId,final String userName) {
		Long orderId = order.getOrderId();
		String complementXml = getComplementDataXml(order,prodContract);
		OrdEContract ordEContract = new OrdEContract();
		ordEContract.setEcontractId(new UUIDGenerator().generate().toString());
		ordEContract.setOrderId(orderId);
		ordEContract.setEcontractNo(EcontractUtil.initContractNo(null,order.getOrderId(),order.getVisitTime()));
		ordEContract.setEcontractStatus(Constant.ECONTRACT_STATUS.UNCONFIRMED.name());
		ordEContract.setTemplateName(prodContract.getEContractTemplate());
		ordEContract.setContentFileId(contentFileId);
		ordEContract.setFixedFileId(fixedFileId);
		ordEContract.setComplementXml(complementXml);
		ComLog log = new ComLog();
		log.setParentId(orderId);
		log.setParentType("ORD_ECONTRACT");
		log.setObjectType("ORD_ECONTRACT");
		log.setObjectId(orderId);
		log.setOperatorName(userName);
		log.setLogType(Constant.COM_LOG_CONTRACT_EVENT.insertContract.name());
		log.setLogName("生成电子合同");
		log.setContent("由系统根据订单中的信息生成电子合同 ，合同编号为:"+ordEContract.getEcontractNo());
		try{
			ordEContractDAO.insert(ordEContract);
			comLogDAO.insert(log);
		}catch(Exception e){
			LOG.warn("根据订单 "+orderId+" 生成电子合同失败:"+e);
			ordEContract= null;
		}
		return ordEContract;
	}
	public OrdEContract updateOrderContract(final OrdEContract contract,final OrdOrder order,final String complementXml,final Long contentFileId,final Long fixedFileId,final String operater){
		String contractNo = contract.getEcontractNo();
		OrdEContractLog history = new OrdEContractLog();
		BeanUtils.copyProperties(contract, history);
		if(!StringUtil.isEmptyString(contractNo) && order.isEContractConfirmed() ){
			contractNo = EcontractUtil.initContractNo(contract.getEcontractNo(),order.getOrderId(),order.getVisitTime());
		}
		if(null!=complementXml){
			contract.setComplementXml(complementXml);
		}
		contract.setContentFileId(contentFileId);
		contract.setFixedFileId(fixedFileId);
		contract.setContractVersion(1+contract.getContractVersion());
		contract.setEcontractNo(contractNo);
		String logName = "更新合同内容";
		String logContent = "更新合同内容,旧版本号为 "+(null!=history.getContractVersion()?history.getContractVersion():"无")+"  新版本号为"+contract.getContractVersion();
		
		if(!StringUtil.isEmptyString(contractNo)&&order.isEContractConfirmed()){
			logName +="后发送合同";
			logContent +=" 旧合同编码为"+history.getEcontractNo()+" 新合同编码为"+contractNo;
		}
		try{
			ordEContractDAO.update(contract);
			ordEContractLogDAO.insert(history);
			comLogDAO.insert(
					"ORD_ECONTRACT",
					order.getOrderId(),
					order.getOrderId(),
					operater,
					Constant.COM_LOG_CONTRACT_EVENT.updateContractContent.name(),
					logName,
					logContent,"ORD_ECONTRACT");
		}catch(Exception e){
			LOG.warn("根据订单 "+order.getOrderId()+" 更新电子合同失败:\r\n"+e);
			return null;
		}
		return contract;
	}
	public ProdProductDAO getProdProductDAO() {
		return prodProductDAO;
	}
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}
	public RouteProductDAO getRouteProductDAO() {
		return routeProductDAO;
	}
	public void setRouteProductDAO(RouteProductDAO routeProductDAO) {
		this.routeProductDAO = routeProductDAO;
	}
	public ProdEContractDAO getProdEContractDAO() {
		return prodEContractDAO;
	}
	public void setProdEContractDAO(ProdEContractDAO prodEContractDAO) {
		this.prodEContractDAO = prodEContractDAO;
	}
	public ProdProductItemDAO getProdProductItemDAO() {
		return prodProductItemDAO;
	}
	public void setProdProductItemDAO(ProdProductItemDAO prodProductItemDAO) {
		this.prodProductItemDAO = prodProductItemDAO;
	}

	public ViewJourneyDAO getViewJourneyDAO() {
		return viewJourneyDAO;
	}

	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}

	public ProdProductPlaceDAO getProdProductPlaceDAO() {
		return prodProductPlaceDAO;
	}

	public void setProdProductPlaceDAO(ProdProductPlaceDAO prodProductPlaceDAO) {
		this.prodProductPlaceDAO = prodProductPlaceDAO;
	}
	public OrdEContractDAO getOrdEContractDAO() {
		return ordEContractDAO;
	}
	public void setOrdEContractDAO(OrdEContractDAO ordEContractDAO) {
		this.ordEContractDAO = ordEContractDAO;
	}
	public ViewPageDAO getViewPageDAO() {
		return viewPageDAO;
	}
	public void setViewPageDAO(ViewPageDAO viewPageDAO) {
		this.viewPageDAO = viewPageDAO;
	}
	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	public OrdEcontractSignLogDAO getOrdEcontractSignLogDAO() {
		return ordEcontractSignLogDAO;
	}
	public void setOrdEcontractSignLogDAO(
			OrdEcontractSignLogDAO ordEcontractSignLogDAO) {
		this.ordEcontractSignLogDAO = ordEcontractSignLogDAO;
	}
	public MetaProductDAO getMetaProductDAO() {
		return metaProductDAO;
	}
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
	public void setOrdEContractLogDAO(OrdEContractLogDAO ordEContractLogDAO) {
		this.ordEContractLogDAO = ordEContractLogDAO;
	}
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}
	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}
	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}
 
}
