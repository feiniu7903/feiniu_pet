package com.lvmama.clutter.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.MobileProduct;
import com.lvmama.clutter.model.MobileProductBranchTrain;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.model.MobileTrainSeatType;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.search.vo.TrainSearchVO;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.visa.VisaVO;

public class ClientTrainProductServiceImpl extends AppServiceImpl implements IClientProductService {

	
	private static final Log logger = LogFactory.getLog(ClientTrainProductServiceImpl.class);
	@Override
	public MobileProduct getProduct(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MobileTimePrice> timePrice(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getGroupOnList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MobileGroupOn getGroupOnDetail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBranches(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> listSeckilles(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MobileGroupOn detailSeckill(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> getStatusById(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<MobileProductTitle> getPlaceRoutes(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private MobileProductBranchTrain buildTrain(ProdProductBranch prodBrancheVisit,boolean isFirst,String seatName,String departureTime){
		MobileProductBranchTrain mb = new MobileProductBranchTrain();
		mb.setMaximum(100L);
		mb.setShortName(prodBrancheVisit.getBranchName());
		mb.setMinimum(0L);
		mb.setSellPriceYuan(prodBrancheVisit.getSellPriceYuan());
		mb.setMarketPriceYuan(prodBrancheVisit.getMarketPriceYuan());
		mb.setChildQuantity(prodBrancheVisit.getChildQuantity());
		mb.setAdultQuantity(prodBrancheVisit.getAdultQuantity());
		mb.setBranchId(prodBrancheVisit.getProdBranchId());
		mb.setProductId(prodBrancheVisit.getProductId());
		mb.setFirst(isFirst);
		mb.setSeatName(seatName);
		mb.setDepartureTime(departureTime);
		return mb;
	}
	
	@Override
	public List<MobileTrainSeatType> getTrainSeatTypes(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("productId","branchId","visitTime",param);
		List<MobileTrainSeatType>  trainSeatList = new ArrayList<MobileTrainSeatType>();
		try {
			Long productId = this.getT(param, "productId", Long.class, true);
			Long branchId = this.getT(param, "branchId", Long.class, true);
			Date visitDate = this.getT(param, "visitTime", Date.class, true);
			ProdTrainCache prodTrainCache =  prodTrainCacheService.get(branchId, visitDate);
			if(null != prodTrainCache) {
				TrainSearchVO vo = new TrainSearchVO();
				vo.setProductId(productId);
				vo.setDeparture(prodTrainCache.getDepartureStation());
				vo.setArrival(prodTrainCache.getArrivalStation());
				vo.setDateDate(visitDate);
				List<ProdTrainCache> prodTrainCacheList = prodTrainCacheService.selectCacheList(vo);
				if(null != prodTrainCacheList && prodTrainCacheList.size() > 0) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("visitTime", param.get("visitTime").toString());
					for(int i = 0; i < prodTrainCacheList.size();i++) {
						ProdTrainCache ptc = prodTrainCacheList.get(i);
						// 不卖卧铺 
						boolean hasPullman = ArrayUtils.contains(ClutterConstant.seat_catalog,ptc.getSeatType());
						if(!hasPullman) {
							MobileTrainSeatType mt = new MobileTrainSeatType();
							mt.setPrice(ptc.getPrice());
							mt.setProdBranchId(ptc.getProdBranchId());
							mt.setSeatType(ptc.getSeatType());
							// 校验是否有票 
							try{
								params.put("branchId", ptc.getProdBranchId());
								// 校验是否可售 
								//checkStockByBranch(params);
								mt.setCanSell(false);
							} catch(Exception e) {
								e.printStackTrace();
								mt.setCanSell(false);
							}
							
							trainSeatList.add(mt);
						}
						
					}
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new LogicException("获取火车票数据失败");
		}
		
		return trainSeatList;
	}

	

	@Override
	public Map<String, Object> getProductItems(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("branchId","visitTime",param);
		
		// 校验是否可售 
		checkStockByBranch(param);
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Long branchId = null;
		Date visitDate = null;
		//String departureStation = null;
		//String departureStation = null;
		try {
			branchId = this.getT(param, "branchId", Long.class, true);
			visitDate = this.getT(param, "visitTime", Date.class, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProdTrainCache mainProdTrainCache = prodTrainCacheService.get(branchId, visitDate);
		
		String departureStation = mainProdTrainCache.getDepartureStation();
		String arrivalStation = mainProdTrainCache.getArrivalStation();
		
		TrainSearchVO tsv = new TrainSearchVO();
		tsv.setProductId(mainProdTrainCache.getProductId());
		tsv.setDeparture(departureStation);
		tsv.setArrival(arrivalStation);
		tsv.setDateDate(visitDate);
		tsv.setLineName(mainProdTrainCache.getLineName());
		
		List<ProdTrainCache> list = prodTrainCacheService.selectCacheList(tsv);
		List<MobileProductBranchTrain> mobileProductBranchTrainList = new ArrayList<MobileProductBranchTrain>();
		if(null != list && !list.isEmpty()) {
			for(int i = 0 ; i < list.size();i++) {
				ProdTrainCache prodTrainCache = list.get(i);
				ProdProductBranch branch=productServiceProxy.getProdBranchDetailByProdBranchId(prodTrainCache.getProdBranchId(), visitDate);
				if(branch!=null && branch.getProdBranchId().equals(branchId)){
					mobileProductBranchTrainList.add(this.buildTrain(branch,true,Constant.TRAIN_SEAT_CATALOG.getCnName(prodTrainCache.getSeatType()),prodTrainCache.getZhDepartureTime()) );
				}
			}
		}
		
		List<MobileBranch> branchList = new ArrayList<MobileBranch>();
		/**
		 * 处理产品的附加产品。
		 */
		List<ProdProductRelation> relateList = productServiceProxy.getRelatProduct( 
				mainProdTrainCache.getProductId(), visitDate);
		
		for (ProdProductRelation prodProductRelation : relateList) {
			MobileBranch mb = new MobileBranch();
			//获得附加产品的默认类别
			ProdProductBranch prodBrancheVisit = this.prodProductService.getProdBranchDetailByProdBranchId(prodProductRelation.getProdBranchId(), visitDate,true);
			if(prodBrancheVisit==null){
				continue;
			}
			mb.setMaximum(prodBrancheVisit.getMaximum());
			mb.setShortName(prodBrancheVisit.getBranchName()+"("+prodBrancheVisit.getProdProduct().getZhSubProductType()+")");
			mb.setMinimum(prodBrancheVisit.getMinimum());
			mb.setSellPriceYuan(prodBrancheVisit.getSellPriceYuan());
			mb.setMarketPriceYuan(prodBrancheVisit.getMarketPriceYuan());
			mb.setBranchId(prodBrancheVisit.getProdBranchId());
			mb.setSaleNumType(prodProductRelation.getSaleNumType());
			mb.setAdditional(true);
			mb.setDescription(ClientUtils.spliteDescByStr(prodBrancheVisit.getDescription())); // 过滤掉 “详细信息请见”以后的字符串 
			mb.setFullName(prodBrancheVisit.getFullName());
			mb.setBranchType(prodBrancheVisit.getBranchType());
			// qin20130905 add附加产品类型
			if(null != prodBrancheVisit && null != prodBrancheVisit.getProdProduct()) {
				mb.setProductType(prodBrancheVisit.getProdProduct().getProductType());
				mb.setSubProductType(prodBrancheVisit.getProdProduct().getSubProductType());
			}
			
			branchList.add(mb);
		}
		resultMap.put("protocolChecked", true);// 是否勾选协议
		resultMap.put("addtionals", branchList);
		resultMap.put("seats", mobileProductBranchTrainList);
		// 初始化 费用包含 - 预定须知  。 
		initContentList(resultMap,mainProdTrainCache.getProductId());
		
		//最近一次订票人员信息 
		resultMap.put("personList", this.getLatestPersonList(null == param.get("userNo")?"":param.get("userNo").toString(),Constant.PRODUCT_TYPE.TRAFFIC.name()));
		
		resultMap.put("xieyiUrl", "/app/xieyi_train.html");
		resultMap.put("xieyiName", "驴妈妈快铁驴行产品服务协议");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if(DateUtils.isSameDay(new Date(), visitDate)){
			resultMap.put("timeHolder","今天");
		} else {
			c.add(Calendar.DATE, 1);
			if  (DateUtils.isSameDay(c.getTime(), visitDate)){
				resultMap.put("timeHolder","明天");
			}  else {
				c.add(Calendar.DATE, 1);
				if  (DateUtils.isSameDay(c.getTime(), visitDate)){
					resultMap.put("timeHolder","后天");
				}  else {
					resultMap.put("timeHolder",DateUtil.formatDate(visitDate, "yyyy-MM-dd"));
				}
			}
		}

		resultMap.put("weekOfDay", DateUtil.getZHDay(visitDate));
		return resultMap;

	}

	/**
	 * 初始化 费用包含 ，退款说明和预定须知等。
	 * @param resultMap
	 * @param productId
	 */
	public void initContentList(Map<String,Object> resultMap,Long productId) {
		resultMap.put("constcontain", "卧铺上中下铺位为随机出票，实际出票铺位以铁路局为准，驴妈妈暂收下铺位的价格。出票后根据实际票价退还差价，至实际支付给驴妈妈的银行卡中。 " +
						"请凭下单填写的身份证或护照到火车站或全国各代售点窗口取票，凭票进站，部分火车站可以刷身份证直接进站，以具体车站为准。");
		resultMap.put("refundsExplanation", "1)产品预订失败退款：如果快铁驴行预订失败，驴妈妈将在确认失败后退款至原支付渠道，由银行将所有支付款项全额退回。  " +
						"2)产品差价退款：如果快铁驴行订单内含有的车票的实际票价低于您所支付的票价，驴妈妈将在确认实际票价后将差额退款至原支付渠道，由银行将差额款项退回，因出票原因产生的差额或全额退款，将在2小时内返还。  " +
						"3)产品退票退款：若客户自行将快铁驴行订单内含有的车票进行退票，驴妈妈将在确认退票后，将差额部分退回原支付渠道，由银行将差额款项退回，退回时间为20个工作日。  " +
						"4)用户在火车站或代售窗口改签后，请在原列车班次的前一天17:00之前，致电我司客服（10106060）进行保险改签。游客可在改签提交后的5个工作日致电保险客服热线(太平保险：95589)查询到最终投保记录。如没有及时通知，保险还是原车次生效，一旦出险，后果自行承担。");
	}
	
	@Override
	public MobileProductRoute getRouteDetail(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getRouteDetail4Wap(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ViewJourney> getViewJourneyList(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 火车票校验库存
	 */
	public Map<String, Object> checkStockByBranch(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("branchId", "visitTime", param);
		logger.info("Check stock begin..");
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try{
			param.put("branchItem", param.get("branchId").toString()+"-1"); // 默认数量为1 
			BuyInfo buyInfo = this.getBuyInfo(param);
			if(buyInfo.getItemList().isEmpty()) {
				throw new LogicException("未选购产品");
			}
			logger.info(buyInfo.getItemList().size());
			if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(buyInfo.getMainProductType())&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(buyInfo.getMainSubProductType())){
				buyInfo.setLocalCheck("false"); // 还没有校验 
				if(TimePriceUtil.hasTrainSoldout()){
					throw new LogicException("不在可售时间范围");
				}
			}
			ResultHandle handle=orderServiceProxy.checkOrderStock(buyInfo);
			if(handle.isFail()){
				throw new LogicException(handle.getMsg());
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new LogicException("未选购产品");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LogicException("当前车票不可售");
		}
		return resultMap;
	}
 
	
	
	/**
	 * 火车票校验库存
	 */
	@Override
	public Map<String, Object> checkStock(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("branchItem", "visitTime", param);
		logger.info("Check stock begin..");
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try{
			BuyInfo buyInfo = this.getBuyInfo(param);
			if(buyInfo.getItemList().isEmpty()) {
				throw new LogicException("未选购产品");
			}
			logger.info(buyInfo.getItemList().size());
			if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(buyInfo.getMainProductType())&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(buyInfo.getMainSubProductType())){
				buyInfo.setLocalCheck("false"); // 还没有校验 
				if(TimePriceUtil.hasTrainSoldout()){
					throw new LogicException("不在可售时间范围");
				}
			}
			//不定期校验有效期
			if(buyInfo.IsAperiodic()) {
				if(buyInfo.getValidBeginTime() != null && buyInfo.getValidEndTime() != null) {
					if(DateUtil.getDayStart(new Date()).after(buyInfo.getValidEndTime())) {
						throw new LogicException("当前商品不可售");
					}
				} else {
					throw new LogicException("当前商品不可售");
				}
			}
			ResultHandle handle=orderServiceProxy.checkOrderStock(buyInfo);
			if(handle.isFail()){
				throw new LogicException(handle.getMsg());
			}
		} catch (IndexOutOfBoundsException e) {
			throw new LogicException("未选购产品");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new LogicException("当前车票不可售");
		}
		return resultMap;
	}

	/**
	 * 获取BuyInfo . 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BuyInfo getBuyInfo(Map<String,Object> param) throws Exception {
		BuyInfo createOrderBuyInfo = new BuyInfo();
		String visitTime = param.get("visitTime").toString();
		// branchList 
		List<MobileBranchItem> branchItemList = this.getBranchList(param.get("branchItem").toString());
		List<Item> itemList = new ArrayList<Item>();
		ProdProduct mainPoduct = null;
		/**
		 * 构建订单项
		 */
		for (MobileBranchItem mobileBranchItem : branchItemList) {
			Item item = new Item();
			//ProdProductBranch branch =  this.prodProductService.getProdBranchDetailByProdBranchId(mobileBranchItem.getBranchId(),  DateUtil.toDate(visitTime, "yyyy-MM-dd"), true);
			ProdProductBranch branch = pageService.getProdBranchByProdBranchId(mobileBranchItem.getBranchId());
			if(branch==null){
				throw new LogicException("产品已下线!");
			}
			
			item.setQuantity(mobileBranchItem.getQuantity().intValue());
			item.setAdultQuantity(branch.getAdultQuantity());
			item.setProductBranchId(branch.getProdBranchId());
			item.setProductId(branch.getProductId());
			item.setProductType(branch.getProdProduct().getProductType());
			item.setSubProductType(branch.getProdProduct().getSubProductType());
			
			//处理只买一个产品非默认产品的情况
			if(branchItemList!=null && branchItemList.size()==1){
				mainPoduct =  branch.getProdProduct();
			}
			//if("true".equals(branch.getDefaultBranch())&&!branch.hasAdditional()){
			if(null == mainPoduct && !branch.hasAdditional()){
				/**
				 * 处理那种录入成其他产品，业务上是附加产品，但是录入的不是附加产品。坑爹的后台验证。。
				 */
				if(branch.getProdProduct()!=null && !Constant.PRODUCT_TYPE.OTHER.name().equals(branch.getProdProduct().getProductType())){
					mainPoduct = branch.getProdProduct();
					if(mainPoduct==null){
						throw new LogicException("产品已下线!");
					}
					item.setIsDefault("true");
				} 
			}
			item.setVisitTime(DateUtil.getDateByStr(visitTime, "yyyy-MM-dd"));
			itemList.add(item);
		}
		
		if(mainPoduct==null){
			throw new LogicException("产品已下线!");
		}
		
		/**
		 * 如果主产品的附加产品选择类型为必选，那么需要验证是否含有附加产品
		 */
		createOrderBuyInfo.setMainProductType(mainPoduct.getProductType());
		createOrderBuyInfo.setMainSubProductType(mainPoduct.getSubProductType());
		createOrderBuyInfo.setItemList(itemList);
		createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		createOrderBuyInfo.setIsAperiodic(mainPoduct.getIsAperiodic());
		createOrderBuyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		return createOrderBuyInfo;
	}
	
	
	
	/**
	 * 解析branchList 
	 * @param branchItem
	 * @return
	 */
	public List<MobileBranchItem> getBranchList(String branchItem) {
		if(StringUtils.isEmpty(branchItem) ) {
			throw new RuntimeException("类别项构建错误!");
		}
		String[] branchArray = branchItem.split("_");
		if(branchArray.length == 0) {
			throw new RuntimeException("类别项构建错误!");
		}
		
		List<MobileBranchItem> branchItemList = new ArrayList<MobileBranchItem>();
		/**
		 * 解析类别对象
		 */
		for (String string : branchArray) {
			String[] itemArray  = string.split("-");
			MobileBranchItem mbi = new MobileBranchItem();
			mbi.setBranchId(Long.valueOf(itemArray[0]));
			mbi.setQuantity(Long.valueOf(itemArray[1]));
			branchItemList.add(mbi);
		}
		
		return branchItemList;
	}
	
	/**
	 * 获取当前用户最近一次的联系人列表.
	 * @param userNo
	 * @return
	 */
	public List<MobilePersonItem> getLatestPersonList(String userNo,String productType) {
		List<MobilePersonItem> vopList = new ArrayList<MobilePersonItem>();
		if(StringUtils.isEmpty(userNo)) {
			return vopList;
		}
		try {
			// 综合查询 
			CompositeQuery compositeQuery = new CompositeQuery();
			// id相关
			OrderIdentity orderIdentity = new OrderIdentity();
			orderIdentity.setUserId(userNo);
			compositeQuery.setOrderIdentity(orderIdentity);
			// 分页相关 
			PageIndex pageIndex = new PageIndex();
			pageIndex.setBeginIndex(0);
			pageIndex.setEndIndex(1);
			compositeQuery.setPageIndex(pageIndex);
			
			// 订单类别相关 
			OrderContent content = new OrderContent();
			
			compositeQuery.setContent(content);
			
			/**
			 * 联系人取最近一次的订单
			 */
			content.setProductType(null);
			List<OrdOrder>  ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
			OrdPerson contactPerson = null;
			MobilePersonItem contactMobilePerson = null; 
			if(ordersList!=null&&ordersList.size()>0){
				OrdOrder ord = ordersList.get(0);
				for (OrdPerson op : ord.getPersonList()) {
					if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(op.getPersonType())){
						contactPerson = op;
						contactMobilePerson = new MobilePersonItem();
						this.copyPersonToMobileItem(contactMobilePerson, contactPerson);
					}
				}
				
			}
			content.setProductType(productType);
			compositeQuery.setContent(content);
			// 订单列表
			ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
			if(null != ordersList && ordersList.size() > 0) {
				OrdOrder ord = ordersList.get(0);
				if(null != ord) {
					// 人员信息 
					for (OrdPerson op : ord.getPersonList()) {
						MobilePersonItem vop = new MobilePersonItem();
						this.copyPersonToMobileItem(vop, op);
						if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(op.getPersonType())){
							if(contactMobilePerson!=null){
								vopList.add(contactMobilePerson);
							} else {
								vopList.add(vop);
							}
						} else {
							vopList.add(vop);
						}
					}
				}
			}
			
			// 如果没有预订过 ,则获取当前登录者信息 
			if(ordersList.size() < 1 ) {
				UserUser user =  userUserProxy.getUserUserByUserNo(userNo);
				if(null != user) {
					MobilePersonItem vop = new MobilePersonItem();
					vop.setPersonMobile(user.getMobileNumber());
					vop.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.getCode());
					vopList.add(vop);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return vopList;
	}
	
	private void copyPersonToMobileItem(MobilePersonItem vop,OrdPerson op){
		if (!StringUtil.isEmptyString(op.getCertNo()) && op.getCertType()!=null && op.getCertType().equals(Constant.CERT_TYPE.ID_CARD.name())) {
			if(op.getCertNo()!=null){
				// v3.1 放开身份证 
				/*if (op.getCertNo().length() == 18) {
					vop.setCertNo("**************" + op.getCertNo().substring(op.getCertNo().length() - 4,op.getCertNo().length()));
				} else if (op.getCertNo().length() == 15) {
					vop.setCertNo("***********" + op.getCertNo().substring(op.getCertNo().length() - 4,op.getCertNo().length()));
				}*/
				vop.setCertNo(op.getCertNo());
			}
			
		}
		vop.setPersonMobile(op.getMobile());
		vop.setPersonName(op.getName());
		vop.setPersonType(op.getPersonType());
		if(op.getBrithday()!=null){
			vop.setBirthday(DateUtil.formatDate(op.getBrithday(), "yyyy-MM-dd"));
		}
		vop.setGender(op.getGender());
		vop.setCertType(op.getCertType());
	}
	
	@Override
	public List<VisaVO> getVisaList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
