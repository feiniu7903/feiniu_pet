package com.lvmama.distribution.model.qunar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;


public class PriceBuilder {

	public static String buildSiglePriceDetail(DistributionProduct product, ProdProductBranch branch,List<ProdProductBranch> fangchaBranchList) {
		PriceDetail priceDetail = new PriceDetail();
		
		Summary summary = new Summary();
		ProdProduct prodProduct = product.getProdProduct();
		summary.setResourceid(branch.getProdProduct().getProductId()+"-"+branch.getProdBranchId().toString());// prodId - branchId
		summary.setPfunction(ProductBuilder.pfunction(prodProduct.getSubProductType()));
		
		//是否为 套餐 打包的成人数+儿童数 大于1时 为true
		Long adultQuantity = branch.getAdultQuantity();
		Long childQuantity = branch.getChildQuantity();
		String istaocan = String.valueOf((adultQuantity+childQuantity)>1L);
		summary.setIstaocan(istaocan);
		
		priceDetail.setSummary(summary);
		
		Package_ package_ = new Package_();
		List<Team> tealList = new ArrayList<Team>();
		Date onlineTime = prodProduct.getOnlineTime();
		Date offlineTime= prodProduct.getOfflineTime();
		Date now = new Date();
		boolean isOnsale = prodProduct.isOnLine() && branch.hasOnline() && now.after(onlineTime) && now.before(offlineTime);

		for (TimePrice price : branch.getTimePriceList()){
			Team team = new Team();
			team.setIspackage(Boolean.valueOf(istaocan));
			team.setTakeoffdate(DateUtil.formatDate(price.getSpecDate(), "yyyy-MM-dd"));
			team.setMarketprice(String.valueOf(PriceUtil.convertToYuan(price.getMarketPrice())));//市场价
			//如果销售类别类型为成人,取销售价为成人”销售价“
			team.setAdultprice(Constant.ROUTE_BRANCH.ADULT.getCode().equals(branch.getBranchType())?String.valueOf(PriceUtil.convertToYuan(PriceUtil.convertToFen(price.getSellPriceFloat()))):"");
			//如果销售类别中的 儿童数据大于0， 说明价格中包含儿童价
			team.setContainchildprice(String.valueOf(branch.getChildQuantity()>0));
			team.setChildprice("");
			team.setChildpricedesc("");
			//如果总库存不为空，取总库存，否则取每日库存
			team.setAvailablecount(price.getTotalDayStock()!=null?price.getTotalDayStock().toString():Long.toString(price.getDayStock()));
			team.setMaxbuycount(branch.getMaximum().toString());
			team.setMinbuycount(branch.getMinimum().toString());
			team.setPricedesc("");//起价说明
			team.setRoomnum("");//床位数
			team.setPricename("");
			team.setQunarprice(String.valueOf(PriceUtil.convertToYuan(PriceUtil.convertToFen(price.getSellPriceFloat()))));
			String roomsendprice = "";
			if(fangchaBranchList!=null&&fangchaBranchList.size()>0){
				ProdProductBranch fangcha = fangchaBranchList.get(0);//房差销售类别
				List<TimePrice> timePriceList = fangcha.getTimePriceList();
				for (TimePrice timePrice : timePriceList) {
					String primaryBranchDate = team.getTakeoffdate();
					String fangchaDate = DateUtil.formatDate(timePrice.getSpecDate(), "yyyy-MM-dd");
					if(primaryBranchDate.equals(fangchaDate)){
						roomsendprice = String.valueOf(PriceUtil.convertToYuan(PriceUtil.convertToFen(timePrice.getSellPriceFloat())));//单房差
						break;
					}
				}
			}
			team.setRoomsendprice(roomsendprice);//单房差
			String status = isOnsale ? "on sale" : "offline";
			team.setStatus(status);
			tealList.add(team);
		}
		package_.setId("");
		package_.setStatus("");
		package_.setTeams(tealList);		
		priceDetail.setPackage_(package_);
		return priceDetail.toString();
	}
	
}