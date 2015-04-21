package com.lvmama.service.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.model.Guest;
import com.lvmama.jinjiang.service.JinjiangProductService;
import com.lvmama.jinjiang.vo.product.ProductPrice;
import com.lvmama.service.CheckStockHandle;
import com.opensymphony.oscache.util.StringUtil;

public class JinjiangCheckHandle implements CheckStockHandle {

	private static final Log logger =LogFactory.getLog(JinjiangCheckHandle.class);
	
	private MetaProductBranchService metaProductBranchService;
	private JinjiangProductService jinjiangProductService;
	private MetaTravelCodeService metaTravelCodeService;
	private ProdProductBranchService prodProductBranchService;
	private ProdProductService prodProductService;
	
	@Override
	public List<Item> check(BuyInfo buyinfo, List<Item> list) {
		if(CollectionUtils.isEmpty(list)) return list;
		
		List<Guest> orderPassengers = initOrderGuests(buyinfo,list);
		if(orderPassengers==null || orderPassengers.size()<1) return list;
		
		if(!checkPassengers(list,orderPassengers)){
			return list;
		}
		return checkPrice(buyinfo,list);
	}

	private List<Item> checkPrice(BuyInfo buyinfo,List<Item> list) {
		String lineCode=null;
		Long adultNum=0L;
		Long childNum=0L;
		Long adultPrice=0L;
		Long childPrice=0L;
		Long fangChaPrice=0L;
		Long metaProductId=0L;
		Item item1 = list.get(0);
		Date visitTime = null;
		String branchType = null;
		for(Item item:list){
			MetaProductBranch mpb = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
			if(mpb==null || StringUtil.isEmpty(mpb.getProductIdSupplier())){
				continue;
			}
			if(Constant.ROUTE_BRANCH.ADULT.name().equals(mpb.getProductTypeSupplier())){
				adultNum = adultNum + item.getQuantity();
				metaProductId = mpb.getMetaProductId();
			}
			if(Constant.ROUTE_BRANCH.CHILD.name().equals(mpb.getProductTypeSupplier())){
				childNum = childNum + item.getQuantity();
				metaProductId = mpb.getMetaProductId();
			}
			if(lineCode==null){
				lineCode = mpb.getProductIdSupplier();
				visitTime = item.getVisitTime();
				branchType = Constant.ROUTE_BRANCH.ADULT.name();
			}
		}
		String groupCode=getGroupCode(lineCode,visitTime,branchType);
		if(StringUtil.isEmpty(groupCode)){
			item1.setStock(SupplierProductInfo.STOCK.LACK);
			item1.setLackReason("预订异常  查询团异常 ");
			logger.error("查询团异常  lineCode" + lineCode +" visitTime= "+DateUtil.formatDate(visitTime, "yyyy-MM-dd") + " branchType=" + branchType );
			return list;
		}
		
		for(BuyInfo.Item item:buyinfo.getItemList()){
			ProdProductBranch ppb = prodProductBranchService.selectProdProductBranchByPK(item.getProductBranchId());
			if(Constant.ROUTE_BRANCH.CHILD.name().equals(ppb.getBranchType())){
				TimePrice prodTimePrice=prodProductService.getTimePriceByProdId(ppb.getProductId(), ppb.getProdBranchId(), visitTime);
				if(prodTimePrice!=null){
					childPrice = prodTimePrice.getPrice();
				}
			}
			if(Constant.ROUTE_BRANCH.ADULT.name().equals(ppb.getBranchType())){
				TimePrice prodTimePrice=prodProductService.getTimePriceByProdId(ppb.getProductId(), ppb.getProdBranchId(), visitTime);
				if(prodTimePrice!=null){
					adultPrice = prodTimePrice.getPrice();
				}
			}
			if(Constant.ROUTE_BRANCH.FANGCHA.name().equals(ppb.getBranchType())){
				TimePrice prodTimePrice=prodProductService.getTimePriceByProdId(ppb.getProductId(), ppb.getProdBranchId(), visitTime);
				if(prodTimePrice!=null){
					fangChaPrice = prodTimePrice.getPrice();
				}
			}
		}
		
		try{
			List<ProductPrice> productPrices = jinjiangProductService.realTimeGetGroup(groupCode);
			if(productPrices==null || productPrices.size()==0){
				item1.setStock(SupplierProductInfo.STOCK.LACK);
				item1.setLackReason("预订异常  价格有更新 ");
				logger.error("查询产品价格异常  groupCode" + groupCode );
				return list;
			}
			
			
			for(ProductPrice pp:productPrices){
				if(Constant.ROUTE_BRANCH.ADULT.name().equals(pp.getSupplierBranchId())){
					if(pp.getDayStock()<adultNum+childNum){
						item1.setStock(SupplierProductInfo.STOCK.LACK);
						item1.setLackReason("预订异常  库存不足");
						//更新团信息
						jinjiangProductService.updateProductStocked(lineCode,jinjiangProductService.getSycTime(JinjiangClient.keyPrice),new Date());
						//更新价格
						jinjiangProductService.syncRealTimePrice(groupCode, metaProductId);
						return list;
					}
					if(adultPrice>0 && !adultPrice.equals(pp.getSalePrice())){
						priceChangeed(lineCode, item1,groupCode,metaProductId);
						return list;
					}
				}
				if(Constant.ROUTE_BRANCH.CHILD.name().equals(pp.getSupplierBranchId())){
					if(childPrice>0 && !childPrice.equals(pp.getSalePrice())){
						priceChangeed(lineCode, item1,groupCode,metaProductId);
						return list;
					}
				}
				if(Constant.ROUTE_BRANCH.FANGCHA.name().equals(pp.getSupplierBranchId())){
					if(fangChaPrice>0 && !fangChaPrice.equals(pp.getSalePrice())){
						priceChangeed(lineCode, item1,groupCode,metaProductId);
						return list;
					}
				}
			}
			
			
		}catch(Exception e){
			logger.error("锦江下单检查接口异常:" + e);
		}
		return list;
	}

	private void priceChangeed(String lineCode, Item item1, String groupCode, Long metaProductId) throws Exception {
		item1.setStock(SupplierProductInfo.STOCK.LACK);
		item1.setLackReason("预订异常  价格有更新");
		//更新团信息
		jinjiangProductService.updateProductStocked(lineCode,jinjiangProductService.getSycTime(JinjiangClient.keyPrice),new Date());
		//更新价格
		jinjiangProductService.syncRealTimePrice(groupCode, metaProductId);
	}
	
	private String getGroupCode(String lineCode, Date validBeginTime,String branchType) {
		MetaTravelCode metaTravelCode = metaTravelCodeService.selectBySuppAndDateAndChannelAndBranch(lineCode, validBeginTime
				,Constant.SUPPLIER_CHANNEL.JINJIANG.getCode(),branchType);
		if(metaTravelCode!=null){
			return metaTravelCode.getTravelCodeId();
		}
		return null;
	}

	private List<Guest> initOrderGuests(BuyInfo buyinfo, List<Item> list) {
		List<Guest> orderPassengers = new ArrayList<Guest>();
		for(Person per:buyinfo.getPersonList()){
			if(StringUtils.equals(per.getPersonType(),Constant.ORD_PERSON_TYPE.TRAVELLER.name())){
				Guest orderPrassenger = new Guest();
				orderPrassenger.setBirthday(per.getBrithday());
				orderPrassenger.setCertificationNumber(per.getCertNo());
				orderPrassenger.setCertificationType(per.getCertType());
				orderPrassenger.setMobile(per.getMobile());
				orderPrassenger.setName(per.getName());
				orderPrassenger.setSex(per.getGender());
				orderPrassenger.setPriceCategory("");
				orderPrassenger.setOtherContactInfo("");
				orderPassengers.add(orderPrassenger);
			}
		}
		if(orderPassengers==null || orderPassengers.size()<1) return null;
		return orderPassengers;
	}
	
private boolean checkPassengers(List<Item> list,List<Guest> orderPassengers) {
		
		Item item0 = list.get(0);
		Long needAdultNum=0l;
		Long needChildrenNum=0l;
		Long infantNum=0l;
		Long realAdultNum = 0l;
		Long realChildrenNum = 0l;
		
		for(Guest pa : orderPassengers){
			if(pa.getBirthday()==null){
				item0.setStock(SupplierProductInfo.STOCK.LACK);
				item0.setLackReason("预订异常 请完善游客信息");
				return false;
			}
			
			if("ADULT".equals(pa.getCategory())){
				realAdultNum++;
			}
			if("CHILD".equals(pa.getCategory())){
				realChildrenNum++;
			}
		}
		
		
		for (Item item : list) {
			try {
				MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
				if(Constant.ROUTE_BRANCH.ADULT.getCode().equals(metaBranch.getProductTypeSupplier())){
					needAdultNum = needAdultNum+item.getQuantity();
				}
				if(Constant.ROUTE_BRANCH.CHILD.getCode().equals(metaBranch.getProductTypeSupplier())){
					needChildrenNum = needChildrenNum+item.getQuantity();
				}
				if(Constant.ROUTE_BRANCH.FANGCHA.getCode().equals(metaBranch.getProductTypeSupplier())){
					infantNum = infantNum+item.getQuantity();
				}
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预定异常");
				e.printStackTrace();
			}
		}
		
		
		if(infantNum>realAdultNum+realChildrenNum){
			item0.setStock(SupplierProductInfo.STOCK.LACK);
			item0.setLackReason("预定异常 游客数量少于房差数");
			return false;
		}
		if(!needAdultNum.equals(realAdultNum) || !needChildrenNum.equals(realChildrenNum)){
			item0.setStock(SupplierProductInfo.STOCK.LACK);
			item0.setLackReason("对不起，您填写的游客证件或出生年月与您所选类别不一致，请检查后准确填写");
			return false;
		}
		return true;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setJinjiangProductService(
			JinjiangProductService jinjiangProductService) {
		this.jinjiangProductService = jinjiangProductService;
	}

	public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
		this.metaTravelCodeService = metaTravelCodeService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	
	
}
