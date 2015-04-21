package com.lvmama.clutter.service.client.v4_0;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileCouponPoint;
import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.MobileProduct;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.service.client.v3_2.ClientProductServiceV321;
import com.lvmama.clutter.service.impl.ClientProductServiceImpl;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.EnumSeckillStatus;
import com.lvmama.clutter.utils.SeckillUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.visa.VisaVO;

public class ClientProductServiceV40 extends ClientProductServiceV321{
	private static final Log log = LogFactory.getLog(ClientProductServiceImpl.class);

	@Override
	public MobileProduct getProduct(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getProduct(params);
	}

	@Override
	public List<MobileTimePrice> timePrice(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.timePrice(params);
	}

	@Override
	public Map<String, Object> getGroupOnList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getGroupOnList(param);
	}

	@Override
	public MobileGroupOn getGroupOnDetail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getGroupOnDetail(param);
	}

	@Override
	public Map<String, Object> getBranches(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getBranches(param);
	}

	@Override
	public List<MobileProductTitle> getPlaceRoutes(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getPlaceRoutes(param);
	}


	public Map<String,Object> getProductItems(Map<String, Object> param) {
			ArgCheckUtils.validataRequiredArgs("branchId","udid",param);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			List<MobileBranch> branchList = new ArrayList<MobileBranch>();
			Long branchId = Long.valueOf(param.get("branchId").toString());
			
			// 秒杀产品校验缓存里的库存
			ProdSeckillRule prodSeckillRule = clientSeckillService
					.getSeckillRuleByBranchId(branchId);
			if (prodSeckillRule != null) {// 是否秒杀产品
				String seckillStatus = SeckillUtils.getSeckillStatus(prodSeckillRule);
				if (EnumSeckillStatus.SECKILL_BEFORE.name().equals(seckillStatus)) {
					throw new LogicException("距离秒杀还有段时间哦，请稍后再抢！ ");
				} else if (EnumSeckillStatus.SECKILL_AFTER.name().equals(seckillStatus)
						|| EnumSeckillStatus.SECKILL_FINISHED.name().equals(
								seckillStatus)) {
					throw new LogicException("秒杀已结束，下次早点来哦！");
				} else {
					long waitPeopleCount = clientSeckillService
							.getWaitPeopleByMemcached(branchId, true, 1L);
					// 缓存是否有库存
					if (waitPeopleCount < 1) {
						throw new LogicException("秒杀已结束，下次早点来哦！");
					}
				}
			}
			
			String udid = param.get("udid").toString();
			Date visitDate = null;
			boolean isTodayOrder = false;
			if(param.get("todayOrder")!=null){
				isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());
				
			}
			// DateUtil.getDateByStr(visitTime, "yyyy-MM-dd")
			ProdProductBranch ppb =  prodProductBranchService.getProductBranchDetailByBranchId(branchId,null, true);
			
			if(null == ppb || ppb.getProdProduct()==null|| !ppb.hasOnline()){
						throw new NotFoundException("产品不可售");
			}
			Boolean isPhysical = Boolean.valueOf(ppb.getProdProduct().getPhysical());
			
			
			
			Map<String,Object> paramters = new HashMap<String,Object>();
			paramters.put("udid", udid);
			
			boolean isShared = super.hasWeiXinShared(paramters, branchId);
			boolean useWeiXinOrder = super.hasWeiXinOrder(udid, branchId);
			
			
			//增加productName逻辑
			ProductSearchInfo productSearchInfo = productSearchInfoService.queryProductSearchInfoByProductId(ppb
					.getProdProduct().getProductId());
			
			String productName = productSearchInfo.getProductName();
			String branchName = ppb.getBranchName();
			String fullProductName = "";
			StringBuffer buffer = new StringBuffer();
			if(!StringUtils.isEmpty(productName)&&!StringUtils.isEmpty(branchName)){
				buffer.append(productName).append("（").append(branchName).append("）");
			}
			fullProductName = buffer.toString();
			resultMap.put("productName", fullProductName);
			resultMap.put("largeImage", productSearchInfo.getLargeImageUrl());
			
			resultMap.put("physical", isPhysical);
			resultMap.put("smallImage", ppb.getProdProduct().getSmallImage());
			Place place = prodProductPlaceService.getToDestByProductId(ppb.getProdProduct().getProductId());
			resultMap.put("placeName", place!=null?place.getName():"");
			resultMap.put("protocolChecked", true);
			/**
			 * 当天预订
			 */
			if(isTodayOrder){
				visitDate = new Date();
			} else {
				Object visitTimeObj = param.get("visitTime");
				if(visitTimeObj!=null){
					String visitTimeStr = (String) visitTimeObj;
					if(!StringUtils.isEmpty(visitTimeStr)){
						visitDate = DateUtil.getDateByStr(visitTimeStr, "yyyy-MM-dd");
					}
				}
				/**
				 * 获得最近一天可预订日期
				 */
				if (visitDate==null){
					List<TimePrice>  timePriceList = null;
				
					timePriceList = prodProductService.getMainProdTimePrice(ppb.getProdProduct().getProductId(), Long.valueOf(branchId.toString()));
					
					if(timePriceList==null||timePriceList.isEmpty()){
						throw new NotFoundException("产品不可售");
					}
					
					Iterator<TimePrice> it = timePriceList.iterator();
					 while(it.hasNext()){
						TimePrice cTimePrice = it.next();
						if(cTimePrice.isSellable(1)){
							visitDate = cTimePrice.getSpecDate();
							break;
						}
					 }
					 if(visitDate==null){
							throw new NotFoundException("产品不可售");
					 }
				}
			}
			
			resultMap.put("couponAble", "true".equals(ppb.getProdProduct().getCouponAble()));
			
			if(ppb.getProdProduct().isPaymentToSupplier()){
				resultMap.put("couponAble",false);
			}
			
			if(isTodayOrder){
				MobileBranch mb = new MobileBranch();
				ProdBranchSearchInfo pbsi = productSearchInfoService.getProdBranchSearchInfoByBranchId(branchId);
				mb.setBranchId(branchId);
				mb.setCanOrderToday(pbsi.todayOrderAble());
				mb.setTodayOrderLastOrderTime(pbsi.getTodayOrderAbleTime());
				this.setTodayOrderTips2(mb,true);


				resultMap.put("cancelStategy", mb.getTodayOrderTips()==null?"":mb.getTodayOrderTips());
				resultMap.put("orderTips", mb.getTodayOrderTips()==null?"":mb.getTodayOrderTips());

				if(!pbsi.canOrderTodayCurrentTime()){
					throw new LogicException("已超过最晚可预订时间");
				}
			} else {
				if(ppb.getProdProduct().isTicket()){
					if(isPhysical){
						resultMap.put("orderTips", "① 此门票为实体票，需选快递方式支付对应费用和填写收件地址。 ");
					} else {
						resultMap.put("orderTips", "① 订单提交成功后，驴妈妈将会发订单确认短信到你的手机，凭短信可顺利入园。 ");
					}
				} else if(ppb.getProdProduct().isRoute()){
					resultMap.put("orderTips", "① 订单提交成功后，驴妈妈将会发订单确认短信到你的手机。 ");
				} 
			}
			
			/**
			 * 处理门票类型的关联产品
			 * 对于每一种类型的产品返回的关联产品和附加产品部一致需要单独处理
			 */
			if(ppb.getProdProduct().isTicket()|| ppb.getProdProduct().isRoute()){
				
				/**
				 * 3.0.1 版本增加的度假线路预定提示。
				 */
				
				
				ViewPage viewPage = viewPageService.selectByProductId(ppb.getProdProduct().getProductId());

				if (viewPage!=null&&viewPage.getPageId()!=null) {

					viewPage = viewPageService.getViewPage(viewPage.getPageId());
					List<ViewContent> contentList = 	viewPage.getContentList();
					for (int i = 0; i < contentList.size(); i++) {
						ViewContent vc = contentList.get(i);
						/**
						 * 费用包含
						 */
						if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(vc.getContentType())){
							resultMap.put("constcontain", vc.getContent()==null?"":ClientUtils.filterOutHTMLTags(vc.getContent()));
						}
						/**
						 * 退款说明
						 */
						if(Constant.VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name().equals(vc.getContentType())){
							resultMap.put("refundsExplanation", vc.getContent()==null?"":ClientUtils.filterOutHTMLTags(vc.getContent()));
						}
						
						/**
						 * 预定须知
						 */
						if(Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(vc.getContentType()) && !StringUtils.isEmpty(vc.getContent())){
							if(null == resultMap.get("orderTips") || StringUtils.isEmpty(resultMap.get("orderTips").toString())) {
								resultMap.put("orderTips", "①"+ClientUtils.filterOutHTMLTags(vc.getContent()));
							} else {
								resultMap.put("orderTips", resultMap.get("orderTips")+ClientUtils.contentTag(resultMap.get("orderTips").toString()) + ClientUtils.filterOutHTMLTags(vc.getContent()));
							}
						}
					}
				}
				
				
				Long productId = ppb.getProdProduct().getProductId();
				List<ProdProductBranch> listBranches=null;
				if(isTodayOrder){
					listBranches = 	prodProductService.getProductBranchByProductId(productId, "false");
				} else {
					listBranches  =this.productServiceProxy.getProdBranchList(productId, null,visitDate);
				}
				
				if(listBranches==null||listBranches.isEmpty()){
					throw new NotFoundException("产品不可售");
				}
				
				for (ProdProductBranch prodProductBranch : listBranches) {
					MobileBranch mb = new MobileBranch();
					ProdProductBranch prodBrancheVisit = null;
					
					TimePrice tp = null;
					if(isTodayOrder){
						/**
						 * 验证是否当天预订同一种门票预订了1次以上
						 * ios 7 不处理这个逻辑
						 */

//						if(isLowerIOS7(param, false)){
//							List<ClientOrderReport> reportList =  comClientService.getTodayOrderByUdid(param.get("udid").toString());
//							if(reportList!=null &&reportList.size()>0){
//								for (ClientOrderReport clientOrderReport : reportList) {
//									OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(clientOrderReport.getOrderId());
//									if(order!=null && order.getMainProduct()!=null){
//										if(!order.isCanceled()&&prodProductBranch.getProdBranchId()==order.getMainProduct().getProdBranchId().longValue()){
//											throw new LogicException("您超过了今日预订门票限额");
//										}
//									}
//								}
//							}
//							
//						}

						prodBrancheVisit = this.prodProductService.getPhoneProdBranchDetailByProdBranchId(prodProductBranch.getProdBranchId(),DateUtil.getDayStart(visitDate),true);
						/**
						 * 验证今日票是否可售
						 */
						tp =  prodProductBranchService.calcCurrentProdTimePric(Long.valueOf(prodProductBranch.getProdBranchId()), DateUtil.getDayStart(visitDate));
						
						
						
					} else {
						prodBrancheVisit = this.prodProductService.getProdBranchDetailByProdBranchId(prodProductBranch.getProdBranchId(), visitDate,true);
						/**
						 * 验证库存
						 */
						if(param.get("visitTime")!=null){
							 tp = prodProductBranchService.getProdTimePrice(prodProductBranch.getProdBranchId(), visitDate);
						}
					
					}
					
					if(param.get("visitTime")!=null||isTodayOrder){
						/**
						 * 判断是否是点击的类别
						 */
						if(branchId.equals(prodProductBranch.getProdBranchId())){
							if(prodBrancheVisit==null||tp==null){
									throw new NotFoundException("产品不可售");
							} 
							
							if(!tp.isSellable(1)){
									throw new LogicException("产品库存不足");
							}
						} 
						
						if(prodBrancheVisit==null||tp==null){
							continue;
						}
						
					}
					
					//转换成mobile 对象
					//处理附加产品
					/**
					 * 如果使用微信立减，默认最多定2个
					 */
					if(isShared&&!useWeiXinOrder){
						prodBrancheVisit.setMaximum(2L);
					}
					
					mb.setMaximum(prodBrancheVisit.getMaximum());
					mb.setShortName(prodBrancheVisit.getBranchName());
					mb.setMinimum(prodBrancheVisit.getMinimum());
					mb.setSellPriceYuan(prodBrancheVisit.getSellPriceYuan());
					mb.setMarketPriceYuan(prodBrancheVisit.getMarketPriceYuan());
					mb.setChildQuantity(prodBrancheVisit.getChildQuantity());
					mb.setAdultQuantity(prodBrancheVisit.getAdultQuantity());
					mb.setBranchId(prodBrancheVisit.getProdBranchId());
					mb.setProductId(prodBrancheVisit.getProductId());
					//mb.setDescription(prodBrancheVisit.getDescription());
					String descriptionWithTag = prodBrancheVisit.getDescription();
					String description = "";
					if(!StringUtils.isEmpty(descriptionWithTag)){
						description = ClientUtils.filterOutHTMLTags(descriptionWithTag);//去掉html标签
						description = ClientUtils.spliteDescByStr(description);// 过滤掉 “详细信息请见”以后的字符串 
					}
					mb.setDescription(description); 
					if(null != prodBrancheVisit.getProdProduct()) {
						mb.setProductType(prodBrancheVisit.getProdProduct().getProductType());
						mb.setSubProductType(prodBrancheVisit.getProdProduct().getSubProductType());
					}
					branchList.add(mb);
				}
			}
			
		

			if (ppb.getProdProduct().isRoute()&&!ppb.getProdProduct().isEContract()){
				resultMap.put("xieyiUrl", "/app/xieyi.html");
				resultMap.put("xieyiName", "驴妈妈委托服务协议");
			}   else if(ppb.getProdProduct().isTicket()){
				resultMap.put("xieyiUrl", "/app/xieyi_ticket.html");
				resultMap.put("xieyiName", "驴妈妈票务预订协议");
			} else {
				resultMap.put("xieyiUrl", "");
				resultMap.put("xieyiName", "");
			}
			ProdEContract pe = prodProductService.getProdEContractByProductId(ppb.getProdProduct().getProductId());
			resultMap.put("isEContract", pe!=null);
			
			resultMap.put("productId", ppb.getProdProduct().getProductId());
			if (ppb.getProdProduct().isHotel()){
				MobileBranch mb = new MobileBranch();
				//转换成mobile 对象
				//处理附加产品
				mb.setMaximum(ppb.getMaximum());
				mb.setShortName(ppb.getBranchName());
				mb.setMinimum(ppb.getMinimum());
				mb.setSellPriceYuan(ppb.getSellPriceYuan());
				mb.setMarketPriceYuan(ppb.getMarketPriceYuan());
				mb.setBranchId(ppb.getProdBranchId());
				mb.setProductId(ppb.getProductId());
				branchList.add(mb);
				
			}

			/**
			 * 对于门票过滤掉附加产品
			 * 当是门票实体票的时候 需要附加产品
			 */
			/**
			 * 之前是为了解决老版本问题。
			 */
//			if(null != param.get("appVersion") && !StringUtils.isEmpty(param.get("appVersion").toString())) {
//				Long appVersion = Long.valueOf(param.get("appVersion").toString());
//				if(appVersion<312L){
//					isPhysical = false;
//				}
//			}
			if(!ppb.getProdProduct().isTicket()||isPhysical){
			
			/**
			 * 处理产品的附加产品。
			 */
			List<ProdProductRelation> relateList = productServiceProxy.getRelatProduct( 
					 ppb.getProdProduct().getProductId(), visitDate);
			
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
				String descriptionWithTag = prodBrancheVisit.getDescription();
				String description = "";
				if(!StringUtils.isEmpty(descriptionWithTag)){
					description = ClientUtils.filterOutHTMLTags(descriptionWithTag);//去掉html标签
					description = ClientUtils.spliteDescByStr(description);// 过滤掉 “详细信息请见”以后的字符串 
				}
				mb.setDescription(description); 
				mb.setFullName(prodBrancheVisit.getFullName());
				mb.setBranchType(prodBrancheVisit.getBranchType());
				// qin20130905 add附加产品类型
				if(null != prodBrancheVisit && null != prodBrancheVisit.getProdProduct()) {
					mb.setProductType(prodBrancheVisit.getProdProduct().getProductType());
					mb.setSubProductType(prodBrancheVisit.getProdProduct().getSubProductType());
					if(isPhysical&&!Constant.SUB_PRODUCT_TYPE.EXPRESS.name().equals(prodBrancheVisit.getProdProduct().getSubProductType())){
						continue;
					}
					/**
					 * 目的地自由行 默认不让选择保险
					 */
					if(ppb.getProdProduct().isFreeness()&&Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(prodBrancheVisit.getProdProduct().getSubProductType())){
						continue;
					}
				}
				
				branchList.add(mb);
			}
			}
			/**
			 * 是否需要门票需要输入身份证以及自由行需要填写游玩人
			 * 这里的逻辑需要调整 根据travellerInfo 来判断联系人 游玩人 是否需要填写 下个版本修改。
			 */

			     // v3.1 qin 线路也要身份证  第一游玩人的必填信息    CARD_NUMBER身份证 MOBILE手机号  NAME用户名pinyi
			   List<String> list = 	ppb.getProdProduct().getContactInfoOptionsList();
			   resultMap.put("needIdCard", list!=null&&(list.isEmpty()||list.contains("CARD_NUMBER"))); 
			   
			   
			   /**
			    * 处理门票需要游玩人的情况
			    */
				 if((ppb.getProdProduct().isTicket()||ppb.getProdProduct().isFreeness())&&ppb.getProdProduct().isOnlyFirstTravellerInfoOptionNotEmpty()){
					   if(ppb.getProdProduct().getFirstTravellerInfoOptionsList().contains("CARD_NUMBER")){
						   resultMap.put("needIdCard", true);  
					   }
				 }    
				/**
				 * 目的地自由行过滤紧急联系人
				 */
			if(Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(ppb.getProdProduct().getSubProductType())){
				resultMap.put("noEmergencyContact", true);
			}
			resultMap.put("nearDate",DateUtil.formatDate(visitDate, "yyyy-MM-dd"));
			
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
			resultMap.put("personList", getLatestPersonList(null == param.get("userNo")?"":param.get("userNo").toString(),ppb.getProdProduct().getProductType()));
			resultMap.put("datas", branchList);
			
			// 积分兑换优惠券 
			MobileCouponPoint mpoint = new MobileCouponPoint();
			if(Boolean.valueOf(resultMap.get("couponAble").toString())) {  // 如果支持优惠券 
				//resultMap.put("couponPointInfo", getCouponByPoint(ppb.getProdProduct().getSubProductType(),String.valueOf(param.get("userNo")),resultMap));
				getCouponByPoint(ppb.getProdProduct().getSubProductType(),String.valueOf(param.get("userNo")),mpoint);
			} 
			resultMap.put("couponPoint", mpoint);
			
			// 优惠活动. 
			resultMap.put("couponList", new ArrayList<Map<String,Object>>());
			// 是否支持多顶多惠,早定早恵
			resultMap.put("hasBusinessCoupon", false);
		
			
			try {
				// 如果是支付给驴妈妈
				if(ppb.getProdProduct().isPaymentToLvmama()) {
					couponActivities(resultMap,"true".equals(ppb.getProdProduct().getIsAperiodic()),branchId,ppb.getValidEndTime(),visitDate,isTodayOrder,useWeiXinOrder,isShared);
				}
				
				ProductSearchInfo psi = this.productSearchInfoService.queryProductSearchInfoByProductId(ppb.getProdProduct().getProductId());
				resultMap.put("hasBusinessCoupon", ClientUtils.hasBusinessCoupon(psi));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//获取自助巴士班的上车地点
			if(null!=ppb&&null!=ppb.getProdProduct()){
				ProdProduct prodProduct = ppb.getProdProduct();
				String subProductType = prodProduct.getSubProductType();
				if("SELFHELP_BUS".equals(subProductType)){
					Long productId = ppb.getProdProduct().getProductId();
					List<ProdAssemblyPoint> prodAssemblyPointList = prodProductService.queryAssembly(productId);
					resultMap.put("prodAssemblyPoint", prodAssemblyPointList);
				}
			}
			
			
			return resultMap;
		}

			/**
			 *  获取优惠信息 和 优惠券 . 
			 * @param resultMap   
			 * @param isAperiodic   是否定期产品  
			 * @param userNo
			 * @param brancId
			 * @param validEndTime
			 * @param visitDate
			 * 
			 */
			public void couponActivities(Map<String,Object> resultMap,boolean isAperiodic,Long brancId,Date validEndTime,Date visitDate,boolean isTodayOrder,boolean useWeiXinOrder,boolean isShared){
				// 不定期产品用最后一天有效期做校验
				ProdProductBranch mainProdBranch= null;
				if(isAperiodic) {
					mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(brancId,validEndTime);
				} else {
					//现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
					if(isTodayOrder){
						mainProdBranch = prodProductService.getPhoneProdBranchDetailByProdBranchId(brancId, DateUtil.getDayStart(visitDate),true);
					} else {
						mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(brancId,visitDate);
					}
				}
				
				// 如果找不到相关产品 
				if(null == mainProdBranch || null == mainProdBranch.getProdProduct()) {
					return ;
				}
				// 如果是支付给驴妈妈  且 可以参加活动 
				if ("true".equals(mainProdBranch.getProdProduct().getPayToLvmama()) 
						&& "true".equals(mainProdBranch.getProdProduct().getCouponActivity())) {
					//关联的附加商品.
					List<ProdProductRelation> relateList = null;
					//不定期暂时不做附加产品
					if(!mainProdBranch.getProdProduct().IsAperiodic()) {
						//关联的附加商品.
						relateList = this.productServiceProxy.getRelatProduct(mainProdBranch.getProductId(),visitDate);
					}
					
					// 查询可以使用优惠活动的附加(关联)产品
					List<Long> idsList = new ArrayList<Long>();
					List<String> subProductTypes = new ArrayList<String>();
					idsList.add(mainProdBranch.getProductId());
					subProductTypes.add(mainProdBranch.getProdProduct().getSubProductType());
					if(!mainProdBranch.getProdProduct().IsAperiodic()) {
						for (ProdProductRelation relateProduct: relateList) {
							if(idsList.contains(relateProduct.getRelatProductId().toString())){
								idsList.add(relateProduct.getRelatProductId());
								subProductTypes.add(relateProduct.getRelationProduct().getSubProductType());
							}
						}
					}
					
					//超级自由行不使用优惠券
					if(!mainProdBranch.getProdProduct().hasSelfPack()){
				    	Map<String,Object> map = new HashMap<String,Object>();
				    	map.put("productIds", idsList);
				    	map.put("subProductTypes", subProductTypes);
				    	map.put("withCode", "false");//只取优惠活动
				    	List<MarkCoupon> orderCouponList = markCouponService.selectAllCanUseAndProductCanUseMarkCoupon(map);

				    	List<Map<String,Object>> couponList = new ArrayList<Map<String,Object>>();
				    	if(null != orderCouponList && orderCouponList.size() > 0) {
				    		for(int i = 0;i < orderCouponList.size();i++) {
				    			MarkCoupon mc = orderCouponList.get(i);
				    			Map<String,Object> m = new HashMap<String,Object>();
				    		
				    			
				    			
				    			
				    			
				    			
				    			// couponId
				    			MarkCouponCode  markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(mc.getCouponId());
				    			m.put("code", markCouponCode.getCouponCode());
				    			m.put("desc", mc.getFavorTypeDescription());
				    			m.put("title", "优惠活动");
				    			// 优惠金额 
				    			m.put("amountYuan", ClientUtils.getFavorTypeAmount(mc));
				    			m.put("wxChecked", false);
				    			Long weixinActivityId = Long.valueOf(ClutterConstant.getProperty("weixinActiviyCouponId"));
				    			if(weixinActivityId.intValue()==mc.getCouponId().intValue()){
				    				if("N".equals(mainProdBranch.getWeixinLijian())){
					    				continue;
					    			} else {
					    				if(!isShared){
						    				continue;
						    			} 
					    				
					    				if(useWeiXinOrder&&weixinActivityId!=null){
						    				continue;
						    			} 
					    			}
				    				m.put("amountYuan", "分享到朋友圈每张立减10元");
				    				m.put("wxChecked", true);
				    			}
				    			couponList.add(m);
				    		}
				    	}
				    	resultMap.put("couponList", couponList);
					}
				}
		}
			

	@Override
	public void getCouponByPoint(String subProductType, String userNo,
			MobileCouponPoint mpoint) {
		// TODO Auto-generated method stub
		super.getCouponByPoint(subProductType, userNo, mpoint);
	}

	@Override
	public MobileProductRoute getRouteDetail(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getRouteDetail(params);
	}

	@Override
	public Map<String, Object> getRouteDetail4Wap(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getRouteDetail4Wap(params);
	}

	@Override
	public List<MobilePersonItem> getLatestPersonList(String userNo,
			String productType) {
		// TODO Auto-generated method stub
		return super.getLatestPersonList(userNo, productType);
	}

	@Override
	public List<ViewJourney> getViewJourneyList(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getViewJourneyList(param);
	}

	@Override
	public Map<String, Object> checkStock(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.checkStock(param);
	}

	@Override
	public List<VisaVO> getVisaList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getVisaList(params);
	}

}
