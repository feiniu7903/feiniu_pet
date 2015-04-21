/**
 * 
 */
package com.lvmama.prd.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;

import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductJourney;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.view.ViewProdJourneyProduct;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourney;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.ord.ProductJourneyUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ViewProdJourneyProductGroup;
import com.lvmama.prd.dao.ProdJourneyProductDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductJourneyDAO;

/**
 * 
 * 该类只做行程提取的以及做产品时间价格表使用
 * @author yangbin
 *
 */
public class ProdJourneyLogic {
	
	private ProdProductJourneyDAO prodProductJourneyDAO;
	private ProdJourneyProductDAO prodJourneyProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private ProdProductBranchLogic prodProductBranchLogic;
	private ProductTimePriceLogic productTimePriceLogic;
	private ProdProductDAO prodProductDAO;
	private ComPlaceDAO comPlaceDAO;
	
	public List<TimePrice> getTimePriceList(Long productId,
			List<TimePrice> timeList, Long adult, Long child,
			boolean checkOnline, Long packId) {
		//取出所有的对应的采购类别的时间价格表.
		List<TimePrice> result=new ArrayList<TimePrice>();
		Map<Long,List<ProdJourneyProduct>> journeyMap = new HashMap<Long, List<ProdJourneyProduct>>();
		boolean first=true;
		long times=0L;
		for(TimePrice tp:timeList){
			
			ViewProdProductJourneyDetail vppjd=new ViewProdProductJourneyDetail();
			try{
				//行程段列表
				List<ProdProductJourney> list=prodProductJourneyDAO.selectListByProductId(productId);
				List<ViewProdProductJourney> res=new ArrayList<ViewProdProductJourney>();
				Date date=new Date(tp.getSpecDate().getTime());
				if(CollectionUtils.isNotEmpty(list)){
					for(ProdProductJourney ppj:list){//分别对各行程判断是否可以销售.
						List<ProdJourneyProduct> prodJourneyList=null;
						if(journeyMap.containsKey(ppj.getProdJourenyId())){
							prodJourneyList = journeyMap.get(ppj.getProdJourenyId());
						}else{
							prodJourneyList=getProdJourneyProductByProdJourney(packId,ppj.getProdJourenyId());
							journeyMap.put(ppj.getProdJourenyId(), prodJourneyList);
						}
						List<ProdJourneyProduct> sellableList=new ArrayList<ProdJourneyProduct>();
						List<ProdJourneyProduct> trafficList = new ArrayList<ProdJourneyProduct>();
						//Date current = new Date();
						for(ProdJourneyProduct pjp:prodJourneyList){
							if(checkOnline && first){
								if(!pjp.getProdBranch().hasOnline()||!pjp.getProdBranch().getProdProduct().isOnLine()){
									if(pjp.hasRequire()){
										//throw new JourneyException("必选产品下线不可以下单");
										//如果存在该地方的无法进展，总体直接退出
										return result;
									}
								}
							}
							ViewProdJourneyProduct vpjp=null;
							try{
								if(pjp.getProdBranch().getProdProduct().isHotel()&&StringUtils.equals(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name(), pjp.getProdBranch().getProdProduct().getSubProductType())){
									vpjp=checkSingleRoomSellableAndConver(pjp,ppj, adult,date);
								}else if(pjp.getProdBranch().getProdProduct().isTraffic()){
									vpjp = checkTrafficSellableAndConver(pjp,ppj,adult,child,date);								
								}else{
									vpjp=checkSellableAndConver(pjp,ppj,adult,child,date);
								}							
								vpjp.setProductJourney(ppj);
								if(pjp.getProdBranch().getProdProduct().isTraffic()){
									trafficList.add(vpjp);
								}else{
									sellableList.add(vpjp);
								}
							}catch(SellableException ex){
								if(pjp.hasRequire()){
									throw new JourneyException("必选产品为空不可以下单");
								}
							}
						}
						//times+=new Date().getTime()-current.getTime();
						//转换产品对象方便前台使用.
						if(child>0 && !trafficList.isEmpty()){
							trafficList = groupProdTraffic(trafficList);
							if(ppj.isPolicy(Constant.PRODUCT_TYPE.TRAFFIC.name())&&trafficList.isEmpty()){
								throw new JourneyException("大交通没有组合产品不可以下单");
							}
						}
						if(!trafficList.isEmpty()){
							sellableList.addAll(trafficList);
						}
						
						checkJourneySellable(ppj,prodJourneyList,sellableList);
						
						ViewProdProductJourney vppj=new ViewProdProductJourney();
						BeanUtils.copyProperties(ppj, vppj);
						vppj.setProdJourneyGroup(ProductJourneyUtil.conver(sellableList));
						vppj.setBeginTime(date);
						//vppj.setToPlace(comPlaceDAO.load(vppj.getToPlaceId()));取时间价格不需要该处理
						res.add(vppj);
						
						date=DateUtils.addDays(date, ppj.getMaxTime().getDays().intValue());
					}
				}
				
				
				vppjd.setProductJourneyList(res);
			}catch(JourneyException ex){
				vppjd.raise(ex);
			}
			first=false;
			if(vppjd.isSuccess()){
				result.add(tp);
			}
			
		}
		//System.out.println("xxxxxxxxxxxxxxxxxx:::::::::::::::::::::"+times);
		return result;
	}
	
	public ViewProdProductJourneyDetail getProductJourneyFromProductId(
			Long productId, Date visitTime, Long adult, Long child,boolean checkOnline,Long packId) {
		ViewProdProductJourneyDetail vppjd=new ViewProdProductJourneyDetail();
		try{
			//行程段列表
			List<ProdProductJourney> list=prodProductJourneyDAO.selectListByProductId(productId);
			List<ViewProdProductJourney> res=new ArrayList<ViewProdProductJourney>();
			Date date=visitTime;
			if(CollectionUtils.isNotEmpty(list)){
				for(ProdProductJourney ppj:list){//分别对各行程判断是否可以销售.
					List<ProdJourneyProduct> prodJourneyList=getProdJourneyProductByProdJourney(packId,ppj.getProdJourenyId());
					
					List<ProdJourneyProduct> sellableList=new ArrayList<ProdJourneyProduct>();
					List<ProdJourneyProduct> trafficList = new ArrayList<ProdJourneyProduct>();
					for(ProdJourneyProduct pjp:prodJourneyList){
						if(checkOnline){
							if(!pjp.getProdBranch().hasOnline()||!pjp.getProdBranch().getProdProduct().isOnLine()){
								if(pjp.hasRequire()){
									throw new JourneyException("必选产品下线不可以下单");
								}
							}
						}
						ViewProdJourneyProduct vpjp=null;
						try{
							if(pjp.getProdBranch().getProdProduct().isHotel()&&StringUtils.equals(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name(), pjp.getProdBranch().getProdProduct().getSubProductType())){
								vpjp=checkSingleRoomSellableAndConver(pjp,ppj, adult,date);
							}else if(pjp.getProdBranch().getProdProduct().isTraffic()){
								vpjp = checkTrafficSellableAndConver(pjp,ppj,adult,child,date);								
							}else{
								vpjp=checkSellableAndConver(pjp,ppj,adult,child,date);
							}							
							vpjp.setProductJourney(ppj);
							if(pjp.getProdBranch().getProdProduct().isTraffic()){
								trafficList.add(vpjp);
							}else{
								sellableList.add(vpjp);
							}
						}catch(SellableException ex){
							if(pjp.hasRequire()){
								throw new JourneyException("必选产品为空不可以下单");
							}
						}
					}
					
					//转换产品对象方便前台使用.
					if(child>0 && !trafficList.isEmpty()){
						trafficList = groupProdTraffic(trafficList);
						if(ppj.isPolicy(Constant.PRODUCT_TYPE.TRAFFIC.name())&&trafficList.isEmpty()){
							throw new JourneyException("大交通没有组合产品不可以下单");
						}
					}
					if(!trafficList.isEmpty()){
						sellableList.addAll(trafficList);
					}
					
					checkJourneySellable(ppj,prodJourneyList,sellableList);
					
					ViewProdProductJourney vppj=new ViewProdProductJourney();
					BeanUtils.copyProperties(ppj, vppj);
					vppj.setProdJourneyGroup(ProductJourneyUtil.conver(sellableList));
					vppj.setBeginTime(date);
					vppj.setToPlace(comPlaceDAO.load(vppj.getToPlaceId()));
					res.add(vppj);
					
					date=DateUtils.addDays(date, ppj.getMaxTime().getDays().intValue());
				}
			}
			
			
			vppjd.setProductJourneyList(res);
		}catch(JourneyException ex){
			vppjd.raise(ex);
		}

		return vppjd;
	}
	
	/**
	 * 生成一个指定的key,用来做绑定使用
	 * @param pjp
	 * @return
	 */
	private String generMapKey(ProdJourneyProduct pjp){
		StringBuffer sb=new StringBuffer();
		sb.append(pjp.getProdBranch().getProdProduct().getProductId());
		sb.append("-");
		sb.append(pjp.getProdBranch().getBerth());
		return sb.toString();
	}
	
	/**
	 * 合组交通产品中的成人与儿童
	 * @param list
	 * @return
	 */
	private List<ProdJourneyProduct> groupProdTraffic(List<ProdJourneyProduct> list){
		Map<String,List<ProdJourneyProduct>> map=new HashMap<String, List<ProdJourneyProduct>>();
		for(ProdJourneyProduct pjp : list){
			String key=generMapKey(pjp);
			List<ProdJourneyProduct> value=null;
			if(map.containsKey(key)){
				value=map.get(key);
			}else{
				value=new ArrayList<ProdJourneyProduct>();
				map.put(key, value);
			}
			value.add(pjp);			
		}
		
		List<ProdJourneyProduct> result = new ArrayList<ProdJourneyProduct>();
		for(String key:map.keySet()){
			List<ProdJourneyProduct> pjpList = map.get(key);
			if(pjpList.size()!=2){//如果一个舱位的机票数不等于2，直接去掉该机票
				continue;
			}
			ViewProdJourneyProductGroup group=new ViewProdJourneyProductGroup();
			group.setProdJourneyProductList(pjpList);
			result.add(group);
		}
		return result;
	}
	
	/**
	 * 读取行程当中的所有的产品，并且初始化其对应的prodBranch,prodProduct对象.
	 * 套餐中成人分类是必选儿童不是必选
	 * @param prodJourneyId
	 * @param packId
	 * @return
	 */
	private List<ProdJourneyProduct> getProdJourneyProductByProdJourney(Long packId,Long prodJourneyId){
		List<ProdJourneyProduct> prodJourneyList = null;
		if(packId!=null && packId>0l){
			prodJourneyList = prodJourneyProductDAO.selectJourneyProductListByJourneyIdAndPackId(packId,prodJourneyId);
			for(ProdJourneyProduct packPP:prodJourneyList){
				//套餐中大人产品是必选
				ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(packPP.getProdBranchId());
				if(branch.getBranchType().contains("ADULT")){
					packPP.setRequire("true");
				}
			}
		}else{
			prodJourneyList = prodJourneyProductDAO.selectJourneyProductListByJourneyId(prodJourneyId);
		}
		List<ProdProductBranch> prodBranchList=prodProductBranchDAO.selectListByProdJourney(prodJourneyId);
		List<ProdProduct> productList = new ArrayList<ProdProduct>();
		for(Constant.PRODUCT_TYPE pt:TYPES){
			productList.addAll(prodProductDAO.selectListByProdJourney(prodJourneyId,pt));
		}
		
		Map<Long,ProdProduct> map=new HashMap<Long, ProdProduct>();
		for(ProdProduct pp:productList){
			map.put(pp.getProductId(), pp);
		}
		//填充
		for(ProdProductBranch ppb:prodBranchList){
			ppb.setProdProduct(map.get(ppb.getProductId()));
		}
		
		Map<Long,ProdProductBranch> branchMap=new HashMap<Long, ProdProductBranch>();
		for(ProdProductBranch branch:prodBranchList){
			branchMap.put(branch.getProdBranchId(), branch);
		}
		
		for(ProdJourneyProduct pjp:prodJourneyList){
			pjp.setProdBranch(branchMap.get(pjp.getProdBranchId()));
		}
		return prodJourneyList;
	}
	
	

	/**
	 * 检查一个行程段是否可销售，如果某个行程段中的组不同销售该产品就整体不能销售
	 * @param ppj 行程
	 * @param prodJourneyList 行程打包的产品列表
	 * @param sellableList	  行程可售的产品列表
	 * @return
	 */
	private boolean checkJourneySellable(final ProdProductJourney ppj,
			final List<ProdJourneyProduct> prodJourneyList,
			final List<ProdJourneyProduct> sellableList)
			throws JourneyException {
		for(final Constant.PRODUCT_TYPE type:TYPES){
			
			boolean flag=ppj.isPolicy(type.name());
			if(flag){//组必选的时候判断下面的逻辑.		
				//首先判断该组是否打包了产品
				Object obj=CollectionUtils.find(prodJourneyList, new Predicate() {
					
					@Override
					public boolean evaluate(Object arg0) {
						ProdJourneyProduct pjp=(ProdJourneyProduct)arg0;
						return type.name().equals(pjp.getProdBranch().getProdProduct().getProductType());
					}
				});
				if(obj!=null){
						
					//如果行程当中存在产品就判断是否可销售
					obj=CollectionUtils.find(sellableList, new Predicate() {
						
						@Override
						public boolean evaluate(Object arg0) {
							ProdJourneyProduct pjp=(ProdJourneyProduct)arg0;
							return type.name().equals(pjp.getProdBranch().getProdProduct().getProductType());
						}
					});
					
					if(obj==null){
						throw new JourneyException("行程当中存在不可销售类型");
					}
				}
			}
		}
		
		return true;
	}
	
	//行程当中的出现的组
	final Constant.PRODUCT_TYPE TYPES[]={Constant.PRODUCT_TYPE.HOTEL,Constant.PRODUCT_TYPE.ROUTE,Constant.PRODUCT_TYPE.TICKET,Constant.PRODUCT_TYPE.TRAFFIC};
	
	private static final long MAX_STOCK=999999;//定义最大值的常量，做取小值来用.
	private static final long UNLIMITED_STOCK=-1L;
	
	
	
	/**
	 * 按指定的时间价格表检查.
	 * @param pjp
	 * @param timePrice 需要检查的时间价格表.
	 * @param adult
	 * @param child
	 * @return
	 */
	private boolean checkSellable(ProdJourneyProduct pjp,TimePrice timePrice,long adult,long child){
		if (ProductUtil.isAdultTicket(pjp.getProdBranch())
				|| ProductUtil.isAdultRoute(pjp.getProdBranch())) {
			if(adult==0){
				return false;
			}
			if(timePrice.getDayStock()!=UNLIMITED_STOCK&&!timePrice.isOverSaleAble()&&timePrice.getDayStock()*pjp.getProdBranch().getAdultQuantity()<adult){
				return false;
			}
		}else if(ProductUtil.isChildTicet(pjp.getProdBranch())){
			if(child==0){//如果成人数为0就不计算
				return false;
			}
			if(timePrice.getDayStock()!=UNLIMITED_STOCK&&!timePrice.isOverSaleAble()&&timePrice.getDayStock()*pjp.getProdBranch().getChildQuantity()<child){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 大交通检查,只检查一天的产品
	 * @param pjp
	 * @param ppj
	 * @param adult
	 * @param child
	 * @param date
	 * @return
	 * @throws SellableException
	 */
	private ViewProdJourneyProduct checkTrafficSellableAndConver(ProdJourneyProduct pjp,ProdProductJourney ppj,long adult,long child,Date date)throws SellableException{
		ViewProdJourneyProduct vpjp=new ViewProdJourneyProduct();
		final TimePrice price = productTimePriceLogic.calcProdTimePrice(pjp.getProdBranchId(),date);
		if(price==null||!checkSellable(pjp, price, adult, child)){
			throw new SellableException(); 
		}
		pjp.getProdBranch().setSellPrice(price.getPrice());
		pjp.getProdBranch().setMarketPrice(price.getMarketPrice());
		pjp.getProdBranch().setStock(price.isOverSaleAble()?-1L:price.getDayStock());
		
		ViewProdJourneyProduct.Time time=new ViewProdJourneyProduct.Time(date);
		time.setSellPrice(price.getPrice());
		BeanUtils.copyProperties(pjp, vpjp);
		
		vpjp.getTimeInfos().add(time);
		return vpjp;
	}
	
	/**
	 * 检查一个行程当中的打包的产品是否可以使用.
	 * 1.门票检查其类别的类型，如果是成人票判断成人数，是儿童票判断儿童数;
	 * 当中只要某一天可销售代表可销售
	 * @return
	 */
	private ViewProdJourneyProduct checkSellableAndConver(ProdJourneyProduct pjp,ProdProductJourney ppj,long adult,long child,Date date)throws SellableException{		
		ViewProdJourneyProduct vpjp=new ViewProdJourneyProduct();
		List<ViewProdJourneyProduct.Time> timeList=new ArrayList<ViewProdJourneyProduct.Time>();
		ViewProdJourneyProduct.Time time=null;

		boolean firstSetPrice=true;
		for(int i=0;i<ppj.getMaxTime().getDays();i++){//检查每一天能否可售
			Date visitTime=DateUtils.addDays(date, i);
			final TimePrice price = productTimePriceLogic.calcProdTimePrice(pjp.getProdBranchId(),visitTime);			
			if (price == null) {
				continue;
			}					
			if(checkSellable(pjp, price, adult, child)){				
				time=new ViewProdJourneyProduct.Time(visitTime);
				time.setSellPrice(price.getPrice());
				time.setStock(price.getDayStock());
				timeList.add(time);	
				
				if(firstSetPrice){
					firstSetPrice=false;
					pjp.getProdBranch().setSellPrice(price.getPrice());
					pjp.getProdBranch().setMarketPrice(price.getMarketPrice());
					pjp.getProdBranch().setStock(price.isOverSaleAble()?-1L:price.getDayStock());
				}
			}
		}
		if(timeList.isEmpty()){
			throw new SellableException();
		}
		
		//取出行程当中所有行程当中能买的时间价格表.
		BeanUtils.copyProperties(pjp, vpjp);	
		vpjp.setTimeInfos(timeList);
		return vpjp;
	}
	
	/**
	 * 不能销售时的异常抛出.
	 * @author yangbin
	 *
	 */
	static class SellableException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	/**
	 * 判断一个酒店是否能在一个行程当中销售.
	 * @param pjp
	 * @param ppj
	 * @param adult
	 * @param child
	 * @return
	 */
	private ViewProdJourneyProduct checkSingleRoomSellableAndConver(ProdJourneyProduct pjp,ProdProductJourney ppj,long adult,Date date)throws SellableException{
		ProdProductBranch branch=pjp.getProdBranch();
		branch=prodProductBranchLogic.fill(branch, date);
		if(branch==null){
			throw new SellableException();
		}
		pjp.setProdBranch(branch);
		long stock=pjp.getProdBranch().getStock();
		if(stock==UNLIMITED_STOCK){
			stock=MAX_STOCK;
		}
		if(stock!=MAX_STOCK&&pjp.getProdBranch().getStock()*pjp.getProdBranch().getAdultQuantity()<adult){
			throw new SellableException();
		}
		long sellPrice=pjp.getProdBranch().getSellPrice();
		long marketPrice=pjp.getProdBranch().getMarketPrice();
		//检查是否有库存，并且取出最小值存入到branch当中
		for(int i=1;i<ppj.getMaxTime().getNights();i++){
			Date visitTime=DateUtils.addDays(date, i);
			final TimePrice price = productTimePriceLogic.calcProdTimePrice(pjp.getProdBranchId(),visitTime);
			if (price == null) {
				throw new SellableException();
			}			
			if(price.getDayStock()==UNLIMITED_STOCK){
				price.setDayStock(MAX_STOCK);
			}
			if (price.getDayStock() != MAX_STOCK
					&& price.getDayStock()
							* pjp.getProdBranch().getAdultQuantity() < adult) {
				throw new SellableException();
			} else {
				long tmp = price.getDayStock();
				sellPrice+=price.getPrice();
				marketPrice+=price.getMarketPrice();
				stock = Math.min(tmp, stock);
			}
		}
		
		//该段行程当中的一个酒店的总单价
		pjp.getProdBranch().setSellPrice(sellPrice);
		pjp.getProdBranch().setMarketPrice(marketPrice);
		//恢复原样值.
		if(stock==MAX_STOCK){
			pjp.getProdBranch().setStock(UNLIMITED_STOCK);
		}else{
			pjp.getProdBranch().setStock(stock);
		}
		ViewProdJourneyProduct vpjp=new ViewProdJourneyProduct();
		BeanUtils.copyProperties(pjp, vpjp);
		return vpjp;
	}

	/**
	 * @param prodProductJourneyDAO the prodProductJourneyDAO to set
	 */
	public void setProdProductJourneyDAO(ProdProductJourneyDAO prodProductJourneyDAO) {
		this.prodProductJourneyDAO = prodProductJourneyDAO;
	}

	/**
	 * @param prodJourneyProductDAO the prodJourneyProductDAO to set
	 */
	public void setProdJourneyProductDAO(ProdJourneyProductDAO prodJourneyProductDAO) {
		this.prodJourneyProductDAO = prodJourneyProductDAO;
	}

	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	/**
	 * @param prodProductBranchLogic the prodProductBranchLogic to set
	 */
	public void setProdProductBranchLogic(
			ProdProductBranchLogic prodProductBranchLogic) {
		this.prodProductBranchLogic = prodProductBranchLogic;
	}

	/**
	 * @param productTimePriceLogic the productTimePriceLogic to set
	 */
	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}



	/**
	 * @param comPlaceDAO the comPlaceDAO to set
	 */
	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}

	
	/**
	 * @param prodProductDAO the prodProductDAO to set
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}
	
	
}
