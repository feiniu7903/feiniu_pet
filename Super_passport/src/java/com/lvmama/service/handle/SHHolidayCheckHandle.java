package com.lvmama.service.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.service.CheckStockHandle;
import com.lvmama.shholiday.ShholidayClient;
import com.lvmama.shholiday.request.CheckOrderRequest;
import com.lvmama.shholiday.response.CheckOrderResponse;
import com.lvmama.shholiday.vo.order.Contact;
import com.lvmama.shholiday.vo.order.OrderBaseInfo;
import com.lvmama.shholiday.vo.order.OrderFavorInfo;
import com.lvmama.shholiday.vo.order.OrderPassenger;
import com.lvmama.shholiday.vo.order.ProductInfo;
import com.opensymphony.oscache.util.StringUtil;

public class SHHolidayCheckHandle implements CheckStockHandle {

	private MetaProductBranchService metaProductBranchService;
	private MetaTravelCodeService metaTravelCodeService;
	private ShholidayClient shholidayClient;
	private static Log logger = LogFactory.getLog(SHHolidayCheckHandle.class);
	
	@Override
	public List<Item> check(BuyInfo buyinfo, List<Item> list) {
		//游客为空不做验证
		List<OrderPassenger> orderPassengers = initPassengers(buyinfo,list);
		if(orderPassengers==null || orderPassengers.size()<1) return list;
		
		Contact contact = initContact(buyinfo);
		Item item = list.get(0);
		if(contact==null){
			item.setStock(SupplierProductInfo.STOCK.LACK);
			item.setLackReason("预订异常 联系人信息为空");
			return list;
		}
		
		if(checkPassengers(list,orderPassengers)){
			OrderBaseInfo orderBaseInfo = initBaseInfo(list);
			ProductInfo productInfo = initProductInfo(list);
			if(productInfo==null){
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预订异常 查询产品信息异常");
				return list;
			}
			
			List<OrderFavorInfo> orderFavorInfos = initFavorInfos(list);
			CheckOrderRequest corderReq = new CheckOrderRequest(contact,orderPassengers,orderBaseInfo,productInfo,orderFavorInfos);
			CheckOrderResponse response = shholidayClient.execute(corderReq);
			if(response.isSuccess()){
				OrderBaseInfo respOrderBaseInfo = response.getBaseInfo();
				if(respOrderBaseInfo==null){
					item.setStock(SupplierProductInfo.STOCK.LACK);
					item.setLackReason("对不起,当前不可预订");
					logger.error("上航下单检查异常 返回信息为空");
				}else{
					Long respTotalAmount = respOrderBaseInfo.getLongOrderTotalAmount()+respOrderBaseInfo.getLongOrderFavorAmount();
					if(orderBaseInfo.getLongOrderTotalAmount()>0){
						if(orderBaseInfo.getLongOrderTotalAmount().compareTo(respTotalAmount)!=0){
							logger.error("上航下单检查价格异常 lvmama价格=" + orderBaseInfo.getLongOrderTotalAmount() 
									+"　上航价格="+respTotalAmount+"计算方式" +respOrderBaseInfo.getPriceArithmetic());
						}
					}else{
						if(orderBaseInfo.getLongOrderTotalAmount().compareTo(respTotalAmount)!=0){
							item.setStock(SupplierProductInfo.STOCK.LACK);
							item.setLackReason("对不起,当前不可预订");
							logger.info("上航下单检查价格异常 lvmama价格=" + orderBaseInfo.getLongOrderTotalAmount() 
									+"　上航价格="+respTotalAmount+"计算方式" +respOrderBaseInfo.getPriceArithmetic());
						}
					}
				}
			}else {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预订异常 下单数据未通过第三方验证");
				if(StringUtils.isNotEmpty(response.getHeader().getException())){
					item.setLackReason("预订异常 下单数据未通过第三方验证" + response.getHeader().getException());
				}
				logger.info("上航下单检查异常 lvmama价格=" + orderBaseInfo.getLongOrderTotalAmount()+"　上航返回null");
			}
		}
		return list;
	}
	
	private ProductInfo initProductInfo(List<Item> list) {
		
		String  productIdSupplier = null;
		Date visitTime = null;
		for(Item i : list){
			MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(i.getMetaBranchId());
			if(metaBranch==null) return null;
			if(StringUtils.isNotEmpty(metaBranch.getProductIdSupplier())){
				productIdSupplier = metaBranch.getProductIdSupplier();
				visitTime = i.getVisitTime();
				break;
			}
		}
		
		if(productIdSupplier!=null){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("supplierProductId", productIdSupplier);
			params.put("specDate", DateUtil.accurateToDay(visitTime));
			params.put("supplierChannel", Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.name());
			List<MetaTravelCode> lists = metaTravelCodeService.selectByCondition(params );
			if(lists!=null && lists.size()>0){
				MetaTravelCode metaTravelCode = lists.get(0);
						if(metaTravelCode!=null){
							ProductInfo productInfo = new ProductInfo();
							productInfo.setTakeoffDate(DateUtil.formatDate(visitTime, "yyyyMMdd"));
							if(metaTravelCode!=null){
								productInfo.setTeamUniqueId(metaTravelCode.getTravelCodeId());
								productInfo.setUniqueId(metaTravelCode.getSupplierProductId());
								productInfo.setTeamName(metaTravelCode.getTravelCode());
							}
							return productInfo;
						}
			}
		}
		return null;
	}





	private OrderBaseInfo initBaseInfo(List<Item> list) {
		OrderBaseInfo baseInfo = new OrderBaseInfo();
		baseInfo.setExternalOrderNo(123l);
		for (Item item : list) {
			try {
				MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCode().equals(metaBranch.getProductTypeSupplier())){
					baseInfo.setAdultSaleProxyPrice(baseInfo.getLongAdultSaleProxyPrice()+item.getSettlementPrice());
					baseInfo.setOrderTotalAmount(baseInfo.getLongOrderTotalAmount()+item.getSettlementPrice()*item.getQuantity());
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCode().equals(metaBranch.getProductTypeSupplier())){
					baseInfo.setChildrenSalePrice(baseInfo.getLongChildrenSalePrice()+item.getSettlementPrice());
					baseInfo.setOrderTotalAmount(baseInfo.getLongOrderTotalAmount()+item.getSettlementPrice()*item.getQuantity());
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCode().equals(metaBranch.getProductTypeSupplier())){
					baseInfo.setInfantSalePrice(baseInfo.getLongInfantSalePrice()+item.getSettlementPrice());
					baseInfo.setOrderTotalAmount(baseInfo.getLongOrderTotalAmount()+item.getSettlementPrice()*item.getQuantity());
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.getCode().equals(metaBranch.getProductTypeSupplier())){
					baseInfo.setBookAccompanyPrice(baseInfo.getLongBookAccompanyPrice()+item.getSettlementPrice());
					baseInfo.setOrderTotalAmount(baseInfo.getLongOrderTotalAmount()+item.getSettlementPrice()*item.getQuantity());
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.getCode().equals(metaBranch.getProductTypeSupplier())){
					baseInfo.setOrderTotalAmount(baseInfo.getLongOrderTotalAmount()+item.getSettlementPrice()*item.getQuantity());
				}
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预定异常");
				e.printStackTrace();
			}
		}
		return baseInfo;
	}





	private List<OrderFavorInfo> initFavorInfos(List<Item> list) {
		// TODO Auto-generated method stub
		return null;
	}





	private boolean checkPassengers(List<Item> list,List<OrderPassenger> orderPassengers) {
		
		Item item0 = list.get(0);
		Long adultNum=0l;
		Long childrenNum=0l;
		Long infantNum=0l;
		
		Long adultNum2 = 0l;
		Long childrenNum2 = 0l;
		Long infantNum2 = 0l;
		
		if(orderPassengers==null || orderPassengers.size()<1){
			item0.setStock(SupplierProductInfo.STOCK.LACK);
			item0.setLackReason("预订异常 游客信息不能为空");
			return false;
		}
		
		for(OrderPassenger pa : orderPassengers){
			if(StringUtil.isEmpty(pa.getPassengerBrithday())){
				item0.setStock(SupplierProductInfo.STOCK.LACK);
				item0.setLackReason("预订异常 请完善游客信息");
				return false;
			}
		}
		
		for (Item item : list) {
			try {
				MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ADULT.getCode().equals(metaBranch.getProductTypeSupplier())){
					adultNum = adultNum+item.getQuantity();
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.ACCOMPANY.getCode().equals(metaBranch.getProductTypeSupplier())){
					adultNum = adultNum+item.getQuantity();
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.CHILDREN.getCode().equals(metaBranch.getProductTypeSupplier())){
					childrenNum = childrenNum+item.getQuantity();
				}
				if(Constant.SH_HOLIDAY_BRANCH_TYPE.INFANT.getCode().equals(metaBranch.getProductTypeSupplier())){
					infantNum = infantNum+item.getQuantity();
				}
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预定异常");
				e.printStackTrace();
			}
		}
		
		for(OrderPassenger pa : orderPassengers){
			if("ADU".equals(pa.getPassengerType())){
				adultNum2++;
			}
			if("CHD".equals(pa.getPassengerType())){
				childrenNum2++;
			}
			if("INF".equals(pa.getPassengerType())){
				infantNum2++;
			}
		}
		if(adultNum!=adultNum2 || childrenNum!=childrenNum2 || infantNum!=infantNum2){
			item0.setStock(SupplierProductInfo.STOCK.LACK);
			item0.setLackReason("对不起，您填写的游客证件或出生年月与您所选类别不一致，请检查后准确填写");
			return false;
		}
		return true;
	}

	private List<OrderPassenger> initPassengers(BuyInfo buyinfo,List<Item> list) {
		
		List<OrderPassenger> orderPassengers = new ArrayList<OrderPassenger>();
		for(Person per:buyinfo.getPersonList()){
			if(StringUtils.equals(per.getPersonType(),Constant.ORD_PERSON_TYPE.TRAVELLER.name())){
				OrderPassenger orderPrassenger = new OrderPassenger();
				orderPrassenger.setAddress(per.getAddress());
				orderPrassenger.setBrithday(per.getBrithday());
				orderPrassenger.setCertNo(per.getCertNo());
				orderPrassenger.setCertType(per.getCertType());
				orderPrassenger.setEmail(per.getEmail());
				orderPrassenger.setMobile(per.getMobile());
				orderPrassenger.setName(per.getName());
				orderPrassenger.setGender(per.getGender());
				orderPrassenger.setFax(per.getFax());
				orderPassengers.add(orderPrassenger);
			}
		}
		if(orderPassengers==null || orderPassengers.size()<1) return null;
		for (Item item : list) {
			MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
			if(Constant.SH_HOLIDAY_BRANCH_TYPE.ROOMDIFFER.getCode().equals(metaBranch.getProductTypeSupplier())){
				if(orderPassengers.size()>=item.getQuantity()){
					for(int i=0;i<item.getQuantity();i++){
						orderPassengers.get(i).setSingleRoom("Y");
					}
				}else{
					item.setStock(SupplierProductInfo.STOCK.LACK);
					item.setLackReason("预定异常 游客数量少于房差数");
				}
			}
		}
		return orderPassengers;
	}

	private Contact initContact(BuyInfo buyinfo) {
		if(buyinfo.getPersonList()==null){
			return null;
		}
		Contact contact = null;
		for(Person p:buyinfo.getPersonList()){
			if(StringUtils.equals(p.getPersonType(),Constant.ORD_PERSON_TYPE.CONTACT.name())){
				contact = new Contact();
				contact.setName(p.getName());
				contact.setAddr(p.getAddress());
				contact.setCertNo(p.getCertNo());
				contact.setCerType(p.getCertType());
				contact.setEmail(p.getEmail());
				contact.setFax(p.getFax());
				contact.setMobile(p.getMobile());
				contact.setTel(p.getTel());
			}
		}
		return contact;
	}





	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}





	public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
		this.metaTravelCodeService = metaTravelCodeService;
	}





	public void setShholidayClient(ShholidayClient shholidayClient) {
		this.shholidayClient = shholidayClient;
	}

	
	
}
