package com.lvmama.prd.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.prod.BeeProdProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourney;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.logic.BonusReturnLogic;
import com.lvmama.prd.dao.LimitSaleTimeDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.dao.ViewMultiJourneyDAO;

public class ProductTimePriceLogic {
	private ProdTimePriceDAO prodTimePriceDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private ComLogDAO comLogDAO;
	private LimitSaleTimeDAO limitSaleTimeDAO;
	private MetaProductBranchDAO metaProductBranchDAO;
	private ProdJourneyLogic prodJourneyLogic;
	private MetaProductDAO metaProductDAO; 
	private ViewMultiJourneyDAO viewMultiJourneyDAO;
	private BeeProdProductService beeProdProductService;
	private FavorService favorService;
	private static final Log log = LogFactory.getLog(ProductTimePriceLogic.class);
    private boolean isDistribution = false;
    
    private BonusReturnLogic  bonusReturnLogic;
    

    /**
	 * 游玩日期是否有销售日期限制
	 * @param productId
	 * @param choseDate
	 * @return 有日期限制返回限制日期，没有限制返回null
	 */
	public LimitSaleTime getLimitSaleTime(Long productId, Date choseDate) {
		ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
		// 非不定期产品才需要做这个校验
		if (product != null && !product.IsAperiodic()) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			List<LimitSaleTime> list = limitSaleTimeDAO.queryLimitSaleTimeByproductId(productId);
			if (list != null) {
				if (list.size() > 0) {
					for (LimitSaleTime lst : list) {
						if (StringUtils.equals(lst.getLimitType(), Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())) {
							Date date = new Date();
							try {
								Date begin = DateUtil.defineDate(date, lst.getLimitHourStart());
								Date end = DateUtil.defineDate(date, lst.getLimitHourEnd());
								if (date.getTime() < begin.getTime()
										|| date.getTime() > end.getTime()) {
									return lst;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							if (sf.format(choseDate).equals(sf.format(lst.getLimitVisitTime()))) {
								if (new Date().before(lst.getLimitSaleTime())) {
									return lst;
								}
							}

						}
					}
				}
			}
		}
		return null;
	}
 	
	/**
	 * 查询销售产品默认类别时间价格表
	 * @param productId
	 * @param maxdays
	 * @return
	 */
	public List<TimePrice> getTimePriceList(Long productId, Integer maxdays){
		ProdProductBranch prodBranch = prodProductBranchDAO.selectDefaultBranchByProductId(productId);
		return this.getTimePriceList(productId, prodBranch.getProdBranchId(), maxdays);
	}
	
	
	
	/**
	 * 查询指定销售产品的最近可用的时间价格表
	 * @param prodBranchId
	 * @param maxdays
	 * @return
	 */
	public List<TimePrice> getTimePriceList(Long productId, Long prodBranchId, Integer maxdays,Long packId) {
		return getTimePrice(productId, prodBranchId, maxdays,packId,null,null);
	}
	/**
	 * 查询指定销售产品的最近可用的时间价格表
	 * @param prodBranchId
	 * @param maxdays
	 * @return
	 */
	public List<TimePrice> getTimePriceList(Long productId, Long prodBranchId, Integer maxdays) {
		return getTimePrice(productId, prodBranchId, maxdays,null,null,null);
	}
	
	/**
	 * 查询指定销售产品的最近可用的时间价格表
	 * @param prodBranchId
	 * @param maxdays
	 * @return
	 */
	public List<TimePrice> getTimePriceList(Long productId, Long prodBranchId, Integer maxdays,Date beginDay) {
		return getTimePrice(productId, prodBranchId, maxdays,null,beginDay,null);
	}
	
	/**
	 * @author zenglei
	 *	
	 * 查询指定销售产品　指定时间范围内的时间从格表
	 * @param prodBranchId
	 * @return
	 */
	public List<TimePrice> getTimePriceListByTime(Long productId, Long prodBranchId, Integer maxdays,Date beginDay,Date endDay){
		return getTimePrice(productId, prodBranchId, null, null, beginDay ,endDay);
	}
	public Date selectNearBranchTimePriceByBranchId(final Long prodBranchId){
        //todo troy
        if(isDistribution) {
            return prodTimePriceDAO.selectNearBranchTimePriceByBranchIdFromTemp(prodBranchId);
        }else{
            return prodTimePriceDAO.selectNearBranchTimePriceByBranchId(prodBranchId);
        }
	}
	
	private List<TimePrice> getTimePrice(Long productId, Long prodBranchId, Integer maxdays,Long packId,Date beginDay,Date endDay) {
		ProdProduct product=prodProductDAO.selectProductDetailByPrimaryKey(productId);
		List<TimePrice> ptpList = new ArrayList<TimePrice>();
		if(product==null){
			return ptpList;
		}
		Date nearDate;
		if(beginDay==null){
			nearDate = selectNearBranchTimePriceByBranchId(prodBranchId);
		}else{
            //todo troy
            if (isDistribution) {
                nearDate = prodTimePriceDAO.selectNearBranchTimePriceByBranchIdAndDayFromTemp(prodBranchId, beginDay);
            } else {
                nearDate = prodTimePriceDAO.selectNearBranchTimePriceByBranchIdAndDay(prodBranchId, beginDay);
            }

		}
		
		if(nearDate == null){
			return ptpList;
		}
		if(product.hasSelfPack()){
			Date day=DateUtils.addDays(DateUtil.getDayStart(new Date()), 2);
			if(nearDate.before(day)){
				nearDate=day;//超级自由行，时间取两天后开始
			}
		}
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("prodBranchId", prodBranchId);
		map.put("beginDate", nearDate);
		if(endDay == null){
			map.put("endDate", DateUtils.addDays(nearDate, maxdays-1));
		}else{
			map.put("endDate", endDay);
		}
		// add by gaoxin for 519
		if (isDistribution) {
			ptpList = prodTimePriceDAO.selectProdTimePriceByParamsFromTemp(map);
		}else{
			ptpList = prodTimePriceDAO.selectProdTimePriceByParams(map);
		}
		//取出所有的对应的采购类别的时间价格表.
		List<TimePrice> result=new ArrayList<TimePrice>();
		
		
		//如果是自主打包的产品单独处理
		if(product.hasSelfPack()){
//			for(TimePrice tp:ptpList){
//				ViewProdProductJourneyDetail vppj=prodJourneyLogic.getProductJourneyFromProductId(productId, tp.getSpecDate(), 2L, 0L,true,packId);
//				if(vppj.isSuccess()){
//					result.add(tp);
//				}
//			}//按要求改成不做不可售处理
			result = prodJourneyLogic.getTimePriceList(productId, ptpList, 2L, 0L, true, packId);
		}else{		
			ptpList = recalcTimePrice(ptpList);
			//把时间价格转成map(日期,TimePrice)
			//Map<Date, TimePrice> ptpMap = getTimePriceMap(ptpList);
			//销售类别打包采购类别项
			Map<Long, ProdProductBranchItem> itemMap = this.getProdProductBranchItemByPordBranchId(prodBranchId);
			List<MetaProductBranch> metaProductBranchs = this.metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
			List<ProdProductBranch> prodProductBranchs = prodProductBranchDAO.getProductBranchByProductId(productId,null);
			boolean calcLastTime = false;
			final Map<Long,List<TimePrice>> timePriceMap=new HashMap<Long, List<TimePrice>>();
			final Map<Long,MetaProductTicket> metaProductTicketMap=new HashMap<Long, MetaProductTicket>();
			boolean todayOrderAble =  false;
			if(prodProductBranchs!=null && prodProductBranchs.size()>0 && "true".equals(prodProductBranchs.get(0).getTodayOrderAble())){
				todayOrderAble=true;
				calcLastTime = true;
			}
			for(MetaProductBranch metaBranch:metaProductBranchs){
				map.put("metaBranchId", metaBranch.getMetaBranchId());
				timePriceMap.put(metaBranch.getMetaBranchId(), metaTimePriceDAO.getMetaTimePriceByMetaBranchId(map));
				if(todayOrderAble){
					metaProductTicketMap.put(metaBranch.getMetaProductId(), (MetaProductTicket)metaProductDAO.getMetaProduct(metaBranch.getMetaProductId(),Constant.PRODUCT_TYPE.TICKET.name()));
				}
			}
			TimePriceCallback callback = new TimePriceCallback() {
				@Override
				public TimePrice getTimePrice(final Long metaBranchId,final Date date) {
					
					return (TimePrice)CollectionUtils.find(timePriceMap.get(metaBranchId), new Predicate() {
						@Override
						public boolean evaluate(Object arg0) {
							String d=DateFormatUtils.format(date, "yyyy-MM-dd");
							String d2=DateFormatUtils.format(((TimePrice)arg0).getSpecDate(), "yyyy-MM-dd");
							return StringUtils.equals(d, d2);
						}
					}); 
				}
			};
			for(TimePrice tp:ptpList){
				TimePrice okTimePrice=calcTimePrice(tp, itemMap, metaProductBranchs, callback,calcLastTime,metaProductTicketMap,todayOrderAble, product.isAnniveraryProduct());
				if(okTimePrice!=null){
					//支付给驴妈妈 最晚换票前多少小时数可售、最短换票间隔小时数  
					okTimePrice.setPayToLvmama(product.getPayToLvmama());
					if(todayOrderAble){
						okTimePrice.setTodayOrderAble("true");
					}
					result.add(okTimePrice);
				}
			}
		}
		return result;
	}
	
	public List<TimePrice> selectProdTimePriceByParams(Map<String,Object> param){
		return prodTimePriceDAO.selectProdTimePriceByParams(param);
	}

    public Date selectNearBranchTimePriceByBranchId(Long prodBranchId, boolean isDistribution) {
        try{
            this.isDistribution = isDistribution;
            return selectNearBranchTimePriceByBranchId(prodBranchId);
        } finally {
            this.isDistribution = false;
        }
    }

    public List<TimePrice> getTimePriceList(Long productId, Long prodBranchId, Integer maxdays,Date beginDay, boolean isDistribution) {
        try{
            this.isDistribution = isDistribution;
            return getTimePrice(productId, prodBranchId, maxdays, null, beginDay, null);
        } finally {
            this.isDistribution = false;
        }
    }


    /**
	 * 取出指定的采购类别的时间价格表，回调使用
	 * @author yangbin
	 *
	 */
	interface TimePriceCallback{
		/**
		 * 取出时间价格表
		 * @return
		 */
		TimePrice getTimePrice(final Long metaBranchId,final Date specDate);
	}
	
	/**
	 * 把取到的时间价格表重新计算有效的－－把当前不可售的排除
	 * @param ptpList
	 * @return
	 */
	private List<TimePrice> recalcTimePrice(List<TimePrice> ptpList) {
		List<TimePrice> list = new ArrayList<TimePrice>();
		Date date = new Date();// 当前时间
		for (TimePrice timePrice : ptpList) {
			Long aheadHour = timePrice.getAheadHour();
			// 最晚能下订的时间
			Date lastAheadTime = timePrice.getSpecDate();
			if (aheadHour != null && aheadHour!=0) {
				lastAheadTime = DateUtils.addMinutes(timePrice.getSpecDate(), -aheadHour.intValue());
			}
			if (date.before(lastAheadTime)) {
				list.add(timePrice);
			}
		}
		return list;
	}
	
    /**
     * 计算一个销售类别的一个指定日期的时间价格表
     * @param ptp
     * @param itemMap
     * @param metaBranchs
     * @param callback
     * @param calcLastTime
     * @param metaProductTicketMap
     * @param todayOrderAble
     * @param isAnniversaryProduct   是否是周年产品
     * @return
     */
	private TimePrice calcTimePrice(TimePrice ptp,Map<Long,ProdProductBranchItem> itemMap,List<MetaProductBranch> metaBranchs,TimePriceCallback callback,boolean calcLastTime,Map<Long,MetaProductTicket> metaProductTicketMap,boolean todayOrderAble,final boolean isAnniversaryProduct){
		if(ptp==null){
			return null;
		}
		
		long offerPrice = 0L;
		
		for (MetaProductBranch metaProductBranch : metaBranchs) {
			ProdProductBranchItem productBranchItem = itemMap.get(metaProductBranch.getMetaBranchId());
			TimePrice metaTimePrice = callback.getTimePrice(metaProductBranch.getMetaBranchId(),ptp.getSpecDate());
			if(metaTimePrice==null){
				return null;
			}
			//针对过了自动清库存时间的
			if(metaTimePrice.getZeroStockHour()!=null&&metaTimePrice.getDayStock()>0){
				Date tmp=DateUtils.addHours(metaTimePrice.getSpecDate(), -metaTimePrice.getZeroStockHour().intValue());
				if(tmp.before(new Date())){
					metaTimePrice.setDayStock(0L);
				}
			}
			
			if(metaProductBranch.isTotalDecrease()) {//总量递减
				if (metaProductBranch.getTotalStock()==null) {
					ptp.updateDayStock(0, metaProductBranch.isTotalDecrease());
					ptp.updateOverSale(metaTimePrice.getOverSale(), 0);//修改
				}else{
					if(metaProductBranch.getTotalStock() == -1) {
						ptp.updateDayStock(-1, metaProductBranch.isTotalDecrease());
					} else {
						ptp.updateDayStock(metaProductBranch.getTotalStock()/productBranchItem.getQuantity(), metaProductBranch.isTotalDecrease());
					}
					ptp.updateOverSale(metaTimePrice.getOverSale(), metaProductBranch.getTotalStock());//修改
				}
			}else{
				if(metaTimePrice.getDayStock()>0){
					ptp.updateDayStock(metaTimePrice.getDayStock()/productBranchItem.getQuantity(), metaProductBranch.isTotalDecrease());
					ptp.updateOverSale(metaTimePrice.getOverSale(), metaTimePrice.getDayStock()/productBranchItem.getQuantity());//修改
				} else {
					ptp.updateDayStock(metaTimePrice.getDayStock(), metaProductBranch.isTotalDecrease());
					ptp.updateOverSale(metaTimePrice.getOverSale(), metaTimePrice.getDayStock());//修改
				}
			}
			ptp.updateResourceConfirm(metaTimePrice.getResourceConfirm());//修改
			ptp.plusMarketPrice(metaTimePrice.getMarketPrice()*productBranchItem.getQuantity());
			ptp.plusSettlementPrice(metaTimePrice.getSettlementPrice()*productBranchItem.getQuantity());
			if(calcLastTime){
				ptp.updateLatesUseDate(metaTimePrice.getLatestUseTimeDate());
				ptp.updateEarliestUseTimeDate(metaTimePrice.getEarliestUseTimeDate());
			}
			if(todayOrderAble){
				MetaProductTicket metaProductTicket  = metaProductTicketMap.get(metaProductBranch.getMetaProductId());
				if(metaProductTicket!=null){
					ptp.updateLastReserveTime(metaTimePrice.getLatestUseTimeDate(), metaProductTicket.getLastPassTime(), metaProductTicket.getLastTicketTime());	
				}
				
			}
			/**
			 * 周年产品的底价销售规则
			 * 1.非周年庆产品不进行底价销售
			 * 2.底价销售价格高于实际售价的保持原售价
			 * 3.底价销售是采购价加1%的利润
			 */
			if (ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR) &&
					isAnniversaryProduct) {
				offerPrice += productBranchItem.getQuantity() * ((metaTimePrice.getSettlementPrice() +  (metaTimePrice.getSettlementPrice() % 10000 > 0 ? (metaTimePrice.getSettlementPrice() / 10000 + 1) * 100 : metaTimePrice.getSettlementPrice() / 10000 * 100)) );
			} 
		}
		if (ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR) &&
				isAnniversaryProduct && offerPrice > 0l && offerPrice < ptp.getPrice()) {
			ptp.setPrice(offerPrice);
		}
		
		return ptp;
	}
	
	/**
	 * 计算游玩时间的，最晚取消时间
	 * @param prodBranchId 销售产品类别
	 * @param specDate 游玩时间
	 * @return 最晚取消时间/null
	 */
	public Date getProductCancelHour(Long prodBranchId, Date specDate){
		TimePrice timePrice = this.calcProdTimePrice(prodBranchId, specDate);
		if(timePrice!=null&&timePrice.getCancelHour() != null){
			Date curr = timePrice.getSpecDate();
			curr = DateUtil.DsDay_Minute(curr, -timePrice.getCancelHour().intValue());
			return curr;
		}
		return null;
	}
	
	/**
	 * 取出来的时间价格表不做提前预小时检查，主要是为了做售价、结算价、是否超卖的数据查 询
	 * @param prodBranchId
	 * @param specDate
	 * @return
	 */
	public TimePrice getNotCalcAheadTimePrice(final Long prodBranchId,final Date specDate){
		return calcProdTimePrice(prodBranchId, specDate);
	}

    /**
     * 取指定类别的一天的时间价格表 (5.19 临时价格专用接口)
     * @param prodBranchId
     * @param specDate
     * @param isDistribution 是否分销
     * @return
     */
    public TimePrice distCalcProdTimePrice(final Long prodBranchId,final Date specDate,boolean isDistribution) {
        try{
            this.isDistribution = isDistribution;
            return calcProdTimePrice(prodBranchId,specDate);
        } finally {
            this.isDistribution = false;
        }
    }
	/**
	 * 根据产品类别Id 和售价buildTimePrice
	 * @param prodBranchId
	 * @param price
	 * @return
	 */
	public TimePrice buildTimePriceByPriceAndBranchId(final Long prodBranchId,final Long price){
		TimePrice timePrice = prodTimePriceDAO.getPriceByBranchId(prodBranchId, price);
		Date specDate = timePrice.getSpecDate();
		return calcProdTimePrice(prodBranchId,specDate);
	}
	/**
	 * 取指定类别的一天的时间价格表
	 * @param prodBranchId
	 * @param specDate
	 * @return
	 */
	public TimePrice calcProdTimePrice(final Long prodBranchId,final Date specDate){
		List<MetaProductBranch> metaBranchs=metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
		Map<Long,ProdProductBranchItem> itemMap=getProdProductBranchItemByPordBranchId(prodBranchId);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("prodBranchId", prodBranchId);
		params.put("specDate", specDate);
        //todo troy
		List<TimePrice> prodTimePriceList;
        if (isDistribution) {
            prodTimePriceList = prodTimePriceDAO.selectProdTimePriceByParamsFromTemp(params);//此处只会取到一天的时间价格
        } else {
            prodTimePriceList = prodTimePriceDAO.selectProdTimePriceByParams(params);//此处只会取到一天的时间价格
        }
        prodTimePriceList = recalcTimePrice(prodTimePriceList);
		if(CollectionUtils.isEmpty(prodTimePriceList)){
			return null;
		}
		
		TimePrice ptp=prodTimePriceList.get(0);
		final List<TimePrice> metaTimeList = metaTimePriceDAO.selectMetaTimePriceByProdBranchIdAndDate(prodBranchId, specDate);
		if(metaTimeList.isEmpty()){
			return null;
		}
		final Map<Long,TimePrice> timePriceMap = new HashMap<Long, TimePrice>();
		for(TimePrice tp:metaTimeList){
			timePriceMap.put(tp.getMetaBranchId(), tp);
		}
		ProdProduct prodProduct = prodProductDAO.selectProductByProdBranchId(prodBranchId);
		
		ptp=calcTimePrice(ptp, itemMap, metaBranchs, new TimePriceCallback() {			
			@Override
			public TimePrice getTimePrice(Long metaBranchId,final Date date) {
				return timePriceMap.get(metaBranchId);
			}
		},false,null,false,null == prodProduct ? false : prodProduct.isAnniveraryProduct());
		return ptp;
	}
	
	/**
	 * @deprecated 五周年的东西
	 * 取指定类别的一天的时间价格表
	 * @param prodBranchId
	 * @param specDate
	 * @return
	 */	
	public TimePrice calcProdTimePrice(final Long prodBranchId,final Date specDate, final boolean isAnniversary){
		if (isAnniversary) {
			return calcProdTimePrice(prodBranchId, specDate);
		} else {
			List<MetaProductBranch> metaBranchs=metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
			Map<Long,ProdProductBranchItem> itemMap=getProdProductBranchItemByPordBranchId(prodBranchId);
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("prodBranchId", prodBranchId);
			params.put("specDate", specDate);
			List<TimePrice> prodTimePriceList=prodTimePriceDAO.selectProdTimePriceByParams(params);//此处只会取到一天的时间价格
			prodTimePriceList = recalcTimePrice(prodTimePriceList);
			if(CollectionUtils.isEmpty(prodTimePriceList)){
				return null;
			}
			
			TimePrice ptp=prodTimePriceList.get(0);
			ProdProduct prodProduct = prodProductDAO.selectProductByProdBranchId(prodBranchId);
			ptp=calcTimePrice(ptp, itemMap, metaBranchs, new TimePriceCallback() {			
				@Override
				public TimePrice getTimePrice(Long metaBranchId,final Date date) {
					TimePrice tp=metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId,date);
					return tp;
				}
			},false,null,false, false);
			return ptp;			
		}
	}
	
	/**
	 * 取当天的时间价格表(为门票客户端当天可预订用)
	 * @param prodBranchId
	 * @param specDate
	 * @return
	 */
	public TimePrice calcCurrentProdTimePrice(final Long prodBranchId,final Date specDate){
		List<MetaProductBranch> metaBranchs=metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
		Map<Long,ProdProductBranchItem> itemMap=getProdProductBranchItemByPordBranchId(prodBranchId);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("prodBranchId", prodBranchId);
		params.put("specDate", specDate);
		List<TimePrice> prodTimePriceList=prodTimePriceDAO.selectProdTimePriceByParams(params);//此处只会取到一天的时间价格
		if(CollectionUtils.isEmpty(prodTimePriceList)){
			return null;
		}
		
		TimePrice ptp=prodTimePriceList.get(0);
		ProdProduct prodProduct = prodProductDAO.selectProductByProdBranchId(prodBranchId);
		ptp=calcTimePrice(ptp, itemMap, metaBranchs, new TimePriceCallback() {			
			@Override
			public TimePrice getTimePrice(Long metaBranchId,final Date date) {
				TimePrice tp=metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId,date);
				return tp;
			}
		},true,null,false,null == prodProduct ? false : prodProduct.isAnniveraryProduct());
		return ptp;
	}
	
	/**
	 * 修改产品的价格，该操作现在必须在类别操作之后才能处理
	 * @param productId
	 */
	public void updatePriceByProductId(Long productId) {
		ProdProductBranch branch = prodProductBranchDAO
				.getProductDefaultBranchByProductId(productId);
		if (branch != null) {// 如果没有取到值暂时不处理.
			ProdProduct product=prodProductDAO.selectByPrimaryKey(productId);
			if(product==null){
				return;
			}
			Date nearDate = prodTimePriceDAO.selectNearBranchTimePriceByBranchId(branch.getProdBranchId());
			if (nearDate != null) {
				product.setSellPrice(branch.getSellPrice());
				ProdProduct prod = new ProdProduct();
				prod.setSellPrice(branch.getSellPrice());
				prod.setMarketPrice(branch.getMarketPrice());
				prod.setMaxCashRefund(bonusReturnLogic.getProductBonusReturnAmount(product, branch.getProdBranchId(), nearDate));
				prod.setProductId(productId);
				prodProductDAO.updatePriceByProductId(prod);
			}
		}
	}
	/**
	 * 查询最近的可售的时间价格表
	 * @param productId 销售产品ID
	 * @return
	 */
	public TimePrice selectLowestPriceByProductId(long productId){
		//根据销售产品ID 查询默认类型
		ProdProductBranch branch=prodProductBranchDAO.getProductDefaultBranchByProductId(productId);
		if(branch!=null){//如果没有取到值暂时不处理.	
			ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(productId);
			if(product==null){
				return null;
			}
			if(!"true".equals(product.getOnLine()) || !"Y".equals(product.getValid())){
				return null;
			}
			Date nearDate = prodTimePriceDAO.selectNearBranchTimePriceByBranchId(branch.getProdBranchId());
			if(nearDate!=null){
				TimePrice timePrice = prodTimePriceDAO
						.selectLowestPriceByBranchId(branch.getProdBranchId(),
								product.hasSelfPack(), nearDate,
								product.getShowSaleDays());
				return timePrice;
			}
		}
		return null;
	}
	/**
	 * 修改类别的销售价与市场价
	 * @param prodBranchId
	 */
	public void updateBranchPriceByBranchId(Long prodBranchId){		
		ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
		if(branch!=null){//如果没有取到值暂时不处理.	
			ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(branch.getProductId());
			if(product==null){
				return;
			}
			Date nearDate = prodTimePriceDAO.selectNearBranchTimePriceByBranchId(prodBranchId);
			if(nearDate!=null){
				TimePrice timePrice = prodTimePriceDAO
						.selectLowestPriceByBranchId(branch.getProdBranchId(),
								product.hasSelfPack(), nearDate,
								product.getShowSaleDays());
				Long marketPrice = 0L;
				ProdProductBranch ppb = new ProdProductBranch();
				if (timePrice != null) {
					if (!product.hasSelfPack()) {
						marketPrice = metaTimePriceDAO
								.selectMarkPriceForProdByDate(
										branch.getProdBranchId(),
										timePrice.getSpecDate());
					}
					ppb.setSellPrice(timePrice.getPrice());
				} else {
					ppb.setSellPrice(0L);
				}
				//长途跟团 长途自由行 最低价减去优惠价
				if(product.getSubProductType().equalsIgnoreCase(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name())
						||product.getSubProductType().equalsIgnoreCase(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name())){
					ppb.setSellPrice(this.getSellPriceByFavor(prodBranchId));
				}
				if (product.isAnniveraryProduct() && ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)) {
					List<TimePrice> timePrices = getTimePriceList(product.getProductId(), prodBranchId, product.getShowSaleDays());
					long minTimePrice = Long.MAX_VALUE;
					for (TimePrice price : timePrices) {
						if (minTimePrice > price.getPrice()) {
							minTimePrice = price.getPrice();
						}
					}
					if (Long.MAX_VALUE != minTimePrice) {
						ppb.setSellPrice(minTimePrice);
					}
				}
				
				
				ppb.setMarketPrice(marketPrice);
				
				ppb.setProdBranchId(prodBranchId);
				
				prodProductBranchDAO.updatePriceByPK(ppb);
			}
		}
	}
	
	/**
	 * 通过销售产品ID和日期查询市场价
	 * @param productId
	 * @param date
	 * @return
	 */
	public Long selectMarkPriceForProdByDate(Long productId,Date date){
		//TODO 该方法相关的调用方需要修改
		return this.metaTimePriceDAO.selectMarkPriceForProdByDate(productId, date);
	}

	public TimePrice loadTimePriceByPidAndData(Long productId,Date date){
		ProdProductBranch prodProductBranch = prodProductBranchDAO.getProductDefaultBranchByProductId(productId);
		return prodTimePriceDAO.getProdTimePrice(productId,prodProductBranch.getProdBranchId(), date);
	}
	
	private void updatePriceTypeLog(StringBuffer strBuf,TimePrice timePriceBean){
		if(Constant.PRICE_TYPE.FIXED_PRICE.name().equals(timePriceBean.getPriceType())){
			if(null != timePriceBean.getPriceF() && !timePriceBean.getPriceF().equals("0")){
				strBuf.append(LogViewUtil.logNewStrByColumnName("驴妈妈售价", timePriceBean.getPriceF()+""));
			}
		}else if(Constant.PRICE_TYPE.FIXED_ADD_PRICE.name().equals(timePriceBean.getPriceType())){
			strBuf.append(LogViewUtil.logNewStrByColumnName("固定加价", timePriceBean.getFixedAddPriceF()+""));
		}else if(Constant.PRICE_TYPE.RATE_PRICE.name().equals(timePriceBean.getPriceType())){
			strBuf.append(LogViewUtil.logNewStrByColumnName("比例加价", timePriceBean.getRatePrice()+""));
		}
	}

	@SuppressWarnings("unchecked")
	public void saveTimePridceLogStr(TimePrice timePriceBean,String operatorName){
		StringBuffer strBuf = new StringBuffer ();
		Map param = new HashMap();
		param.put("prodBranchId",timePriceBean.getProdBranchId());
		List oldTimeList=this.prodTimePriceDAO.selectProdTimePriceByParams(param);
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		//如果查询oldTimeList没有记录，则是新建时间价格表.否则是修改.
		if (oldTimeList.size()==0) {
			strBuf.append(LogViewUtil.logNewStrByColumnName("时间周期",simpleFormat.format(timePriceBean.getBeginDate())+","+simpleFormat.format(timePriceBean.getEndDate())));
			if(timePriceBean.getMultiJourneyId() != null) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("行程", timePriceBean.getMultiJourneyId().toString()));
			}
			if(timePriceBean.getWeekOpen()!=null){
				String monDay="true".equals(timePriceBean.getMonday())?"星期一":"";
				String tuesday="true".equals(timePriceBean.getTuesday())?"星期二":"";
				String wednesday="true".equals(timePriceBean.getWednesday())?"星期三":"";
				String thursday="true".equals(timePriceBean.getThursday())?"星期四":"";
				String friday="true".equals(timePriceBean.getFriday())?"星期五":"";
				String saturday="true".equals(timePriceBean.getSaturday())?"星期六":"";
				String sunday="true".equals(timePriceBean.getSunday())?"星期天":"";
				strBuf.append(LogViewUtil.logNewStrByColumnName("按星期 ",monDay+" "+tuesday+" "+wednesday+" "+thursday+" "+friday+" "+saturday+" "+sunday+" "));
			}
			updatePriceTypeLog(strBuf,timePriceBean);
			if(!"".equals(strBuf.toString())){
				ComLog log = new ComLog();
				log.setParentId(timePriceBean.getProductId());
				log.setObjectType("PROD_TIME_PRICE");
				log.setObjectId(null);
				log.setOperatorName(operatorName);
				log.setLogType(Constant.COM_LOG_ORDER_EVENT.insertProdTimePrice.name());
				log.setLogName("创建销售时间价格表");
				log.setContent(strBuf.toString());
				this.comLogDAO.insert(log);
			}
		}else{
			TimePrice oldBean =new TimePrice();
			if(oldTimeList.size()>0){
				TimePrice beginTime=(TimePrice)oldTimeList.get(0);
				TimePrice endTime=(TimePrice)oldTimeList.get(oldTimeList.size()-1);
				oldBean.setBeginDate(beginTime.getSpecDate());
				oldBean.setEndDate(endTime.getSpecDate());
				if(beginTime.getPrice()==endTime.getPrice()){
					oldBean.setPriceF(beginTime.getPriceF());
				}
			
				if(timePriceBean.getBeginDate().compareTo(oldBean.getBeginDate())!=0||timePriceBean.getEndDate().compareTo(oldBean.getEndDate())!=0){
					strBuf.append(LogViewUtil.logEditStr("时间周期",simpleFormat.format(timePriceBean.getBeginDate())+","+simpleFormat.format(timePriceBean.getEndDate())));
				}
				if(timePriceBean.getMultiJourneyId() != null && timePriceBean.getMultiJourneyId() != oldBean.getMultiJourneyId()) {
					strBuf.append(LogViewUtil.logEditStr("行程",timePriceBean.getMultiJourneyId().toString()));
				}
			if(!LogViewUtil.logIsEmptyStr(timePriceBean.getWeekOpen()).equals(LogViewUtil.logIsEmptyStr(oldBean.getWeekOpen()+""))){
				String monDay="true".equals(timePriceBean.getMonday())?"星期一":"";
				String tuesday="true".equals(timePriceBean.getTuesday())?"星期二":"";
				String wednesday="true".equals(timePriceBean.getWednesday())?"星期三":"";
				String thursday="true".equals(timePriceBean.getThursday())?"星期四":"";
				String friday="true".equals(timePriceBean.getFriday())?"星期五":"";
				String saturday="true".equals(timePriceBean.getSaturday())?"星期六":"";
				String sunday="true".equals(timePriceBean.getSunday())?"星期天":"";
				strBuf.append(LogViewUtil.logEditStr("按星期 ",monDay+" "+tuesday+" "+wednesday+" "+thursday+" "+friday+" "+saturday+" "+sunday+" "));
			}
			updatePriceTypeLog(strBuf,timePriceBean);
			if(!LogViewUtil.logIsEmptyStr(timePriceBean.getCancelHourStr()+"").equals(LogViewUtil.logIsEmptyStr(oldBean.getCancelHourStr()+""))){
				strBuf.append(LogViewUtil.logEditStr("最晚取消小时数", timePriceBean.getCancelHourFloat()+""));
			}
			if(!LogViewUtil.logIsEmptyStr(timePriceBean.getAheadHourStr()+"").equals(LogViewUtil.logIsEmptyStr(oldBean.getAheadHourStr()+""))){
				strBuf.append(LogViewUtil.logEditStr("提前预订小时数", timePriceBean.getAheadHourFloat()+""));
			}
			
			if(!"".equals(strBuf.toString())){
				ComLog log = new ComLog();
				log.setParentId(timePriceBean.getProdBranchId());
				log.setParentType("PROD_TIME_PRICE");
//				log.setObjectType("PROD_TIME_PRICE");
				log.setObjectId(null);
				log.setOperatorName(operatorName);
				log.setLogType(Constant.COM_LOG_ORDER_EVENT.updateProdTimePrice.name());
				log.setLogName("编辑销售时间价格表");
				log.setContent(strBuf.toString());				
				this.comLogDAO.insert(log);
			}
			}
		}
	}
	
	/**
	 * 针对自主打包的时间价格表做修改操作.
	 * @param bean
	 * @param operatorName
	 */
	public List<Date> saveSelfPackTimePrice(TimePrice bean,Long productId,String operatorName){
		List<Date> list=new ArrayList<Date>();
		if(!bean.isClosed()){			
			Calendar cal =Calendar.getInstance();
			StringBuffer sb=new StringBuffer();
			for(long time = bean.getBeginDate().getTime(); time <= bean.getEndDate().getTime(); time += 86400000) {
				cal.setTimeInMillis(time);
				Date date=cal.getTime();
				if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
					boolean flag=saveSelfPackDayTimePrice(bean,productId,date);
					if(!flag){
						list.add(date);
					}else{
						sb.append(DateUtil.getFormatDate(date, "yyyy-MM-dd"));
						sb.append(",");
					}
				}
			}
			if(sb.length()>0){//保存日志
				sb.setLength(sb.length()-1);
				sb.insert(0, "计算日期:");
				sb.append("时间价格");
				ComLog log = new ComLog();
				log.setParentId(bean.getProdBranchId());
				log.setParentType("PROD_TIME_PRICE");
//				log.setObjectType("PROD_TIME_PRICE");
				log.setObjectId(null);
				log.setOperatorName(operatorName);
				log.setLogType(Constant.COM_LOG_ORDER_EVENT.updateProdTimePrice.name());
				log.setLogName("编辑销售时间价格表");
				log.setContent(sb.toString());				
				this.comLogDAO.insert(log);
			}
		}
		return list;
	}


    /**
     * 保存一天的时间价格.
     * @param bean
     * @param productId
     * @param date
     * @return
     */
	private boolean saveSelfPackDayTimePrice(TimePrice bean,Long productId,Date date){
		ViewProdProductJourneyDetail vppjd=prodJourneyLogic.getProductJourneyFromProductId(productId, date, 2L, 0L,false,null);
		if(vppjd.isSuccess()){			
			long price=0L;
			//计算每段行程当中酒店，门票的最小值
			for(ViewProdProductJourney vppj:vppjd.getProductJourneyList()){
				price += getRequiredOrMinPrice(vppj.getHotelList(), vppj, Constant.PRODUCT_TYPE.HOTEL.name());
				price += getRequiredOrMinPrice(vppj.getTicketList(), vppj, Constant.PRODUCT_TYPE.TICKET.name());
				price += getRequiredOrMinPrice(vppj.getTrafficList(), vppj, Constant.PRODUCT_TYPE.TRAFFIC.name());
				price += getRequiredOrMinPrice(vppj.getRouteList(), vppj, Constant.PRODUCT_TYPE.ROUTE.name());
			}
			if(price == 0) {
				for(ViewProdProductJourney vppj:vppjd.getProductJourneyList()){
					ProdJourneyProduct minHotel=getMin(vppj.getHotelList());
					if(minHotel!=null){
						price+=minHotel.getSellPrice();//该值已经计算过优惠与天数					
					}
					ProdJourneyProduct minTicket=getMin(vppj.getTicketList());
					if(minTicket!=null){
						price+=minTicket.getSellPrice();//门票现在只算一天的价格
							//*vppj.getMaxTime().getDays();
					}
					ProdJourneyProduct minTraffic = getMin(vppj.getTrafficList());
					if(minTraffic!=null){
						price+=minTraffic.getSellPrice();//存在大交通，取一个交通的价格
					}
				}
			}
			if(price==0){//如果取到的价钱为0，该日期不可以销售
				return false;
			}
			TimePrice prodTimePrice = prodTimePriceDAO.getProdTimePrice(productId,bean.getProdBranchId(), date);	
			if (prodTimePrice == null) {
				TimePrice record = new TimePrice();
				record.setPrice(TimePriceUtil.conver0(price));
				record.setSpecDate(date);
				record.setProductId(productId);
				record.setAheadHour(bean.getAheadHour());
				record.setCancelHour(bean.getCancelHour());
				record.setProdBranchId(bean.getProdBranchId());
				prodTimePriceDAO.insert(record);
			} else {
				prodTimePrice.setPrice(TimePriceUtil.conver0(price));
				prodTimePrice.setAheadHour(bean.getAheadHour());
				prodTimePrice.setCancelHour(bean.getCancelHour());
				prodTimePriceDAO.update(prodTimePrice);
			}
		}	
		return vppjd.isSuccess();
	}
	
	private long getRequiredOrMinPrice(List<ProdJourneyProduct> list, ViewProdProductJourney vppj, String productType) {
		long price = 0;
		if(CollectionUtils.isNotEmpty(list)) {
			for (ProdJourneyProduct pjp : list) {
				if(pjp.hasRequire()) {
					price += pjp.getSellPrice();
				}
			}
		}
		if(price == 0) {
			if(vppj.isPolicy(productType)) {
				ProdJourneyProduct min = getMin(list);
				if(min != null){
					price += min.getSellPrice();//该值已经计算过优惠与天数					
				}
			}
		}
		return price;
	}
	
	private ProdJourneyProduct getMin(List<ProdJourneyProduct> list){
		if(list==null){
			return null;
		}
		ProdJourneyProduct pjp=(ProdJourneyProduct)Collections.min(list,new Comparator<ProdJourneyProduct>() {

			@Override
			public int compare(ProdJourneyProduct o1, ProdJourneyProduct o2) {
				if(o1.getSellPrice().equals(o2.getSellPrice())){
					return 0;
				}
				return (o1.getSellPrice()-o2.getSellPrice()<0)?-1:1;
			}

		});
		return pjp;
	}
	
	/**
	 * 修改最晚取消时间及预订小时数
	 * @param bean
	 * @param prodProductId
	 * @param operatorName
	 */
	public void saveTimePriceHour(TimePrice bean,Long prodProductId,String operatorName){
		if(bean.isClosed()){
			deleteTimePrice(bean, operatorName);
		}else{
			Long productId=prodProductId;
			if(productId==null){
				ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(bean.getProdBranchId());
				productId=branch.getProductId();
			}
			List<TimePrice> list = selectIntersection(productId,bean.getProdBranchId(), bean.getBeginDate().getTime(), bean.getEndDate().getTime());
			if(bean.getBeginDate()!=null){
				this.saveTimePridceLogStr(bean,operatorName);
			}
			Calendar cal =Calendar.getInstance();
			//保存时间价格表
			for (TimePrice timePrice : list) {
				Date specDate = timePrice.getSpecDate();
				cal.setTimeInMillis(specDate.getTime());
				if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
					TimePrice prodTimePrice = prodTimePriceDAO.getProdTimePrice(productId,bean.getProdBranchId(), specDate);
					if (prodTimePrice != null) {
						prodTimePrice.setCancelHour(bean.getCancelHour());
						prodTimePrice.setAheadHour(bean.getAheadHour());
						prodTimePriceDAO.update(prodTimePrice);
					}
				}
			}
		}
	}
	
	public void insertTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice) {
		prodTimePriceDAO.insert(TimePriceUtil.calcPrice(prodTimePrice, metaTimePrice));
	}
	
	public void updateTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice) {
		prodTimePriceDAO.update(TimePriceUtil.calcPrice(prodTimePrice, metaTimePrice));
	}
	
	public void updateDynamicTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice) {
		prodTimePriceDAO.updateDynamic(TimePriceUtil.calcPrice(prodTimePrice, metaTimePrice));
	}
	
	public void insertTimePriceNoMultiJourneyFK(TimePrice prodTimePrice, TimePrice metaTimePrice){
		prodTimePriceDAO.insert(TimePriceUtil.calcPrice(prodTimePrice, metaTimePrice));
	}
	
	public void updateTimePriceNoMultiJourneyFk(TimePrice prodTimePrice, TimePrice metaTimePrice){
		prodTimePriceDAO.updateNoMultiJourneyFK(TimePriceUtil.calcPrice(prodTimePrice, metaTimePrice));
	}

    /**
     * 保存指定产品的时间价格表
     * @param bean
     * @param prodProductId
     * @param operatorName
     */
	public void saveTimePrice(TimePrice bean, Long prodProductId,String operatorName) {
		Long productId=prodProductId;
		if(productId==null){
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(bean.getProdBranchId());
			productId=branch.getProductId();
		}
		if (bean.isClosed()) {
			deleteTimePrice(bean, operatorName);
		} else {
			List<TimePrice> list = selectIntersection(productId,bean.getProdBranchId(), bean.getBeginDate().getTime(), bean.getEndDate().getTime(), true);
			if(bean.getBeginDate()!=null){
				this.saveTimePridceLogStr(bean,operatorName);
			}
			Calendar cal =Calendar.getInstance();
			//保存时间价格表
			for (TimePrice timePrice : list) {
				Date specDate = timePrice.getSpecDate();
				cal.setTimeInMillis(specDate.getTime());
				if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
					TimePrice prodTimePrice = prodTimePriceDAO.getProdTimePrice(productId,bean.getProdBranchId(), specDate);
					if (prodTimePrice == null) {
						TimePrice record = new TimePrice();
						record.setPrice(bean.getPrice());
						record.setSpecDate(specDate);
						record.setProductId(productId);
						record.setCancelHour(bean.getCancelHour());
						record.setAheadHour(bean.getAheadHour());
						record.setProdBranchId(bean.getProdBranchId());
						record.setPriceType(bean.getPriceType());
						record.setRatePrice(bean.getRatePrice());
						record.setFixedAddPrice(bean.getFixedAddPrice());						
						record.setMultiJourneyId(bean.getMultiJourneyId());
						prodTimePriceDAO.insert(TimePriceUtil.calcPrice(record, timePrice));
					} else {
						prodTimePrice.setPrice(bean.getPrice());
						prodTimePrice.setCancelHour(bean.getCancelHour());
						prodTimePrice.setAheadHour(bean.getAheadHour());
						
						prodTimePrice.setPriceType(bean.getPriceType());
						prodTimePrice.setRatePrice(bean.getRatePrice());
						prodTimePrice.setFixedAddPrice(bean.getFixedAddPrice());	
						prodTimePrice.setMultiJourneyId(bean.getMultiJourneyId());
						prodTimePriceDAO.update(TimePriceUtil.calcPrice(prodTimePrice, timePrice));
					}
				}
			}
		}
	}
	
	/**
	 * 删除指定时间段内的销售产品时间价格表
	 * @param bean <code>bean.prodBrandId</code>不可以为空
	 * @param operatorName
	 */
	public void deleteTimePrice(TimePrice bean, String operatorName) {
		Assert.notNull(bean.getProdBranchId(),"产品类别不可以为空");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodBranchId", bean.getProdBranchId());
		param.put("beginDate", bean.getBeginDate());
		param.put("endDate", bean.getEndDate());
		prodTimePriceDAO.deleteByBeginDateAndEndDate(param);
		ComLog log = new ComLog();
		log.setParentId(bean.getProdBranchId());
		log.setParentType("PROD_TIME_PRICE");
		log.setObjectId(null);
		log.setOperatorName(operatorName);
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.updateProdTimePrice.name());
		log.setLogName("禁售时间价格表");
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
		log.setContent(LogViewUtil.logDeleteStr(operatorName)+"将该"+simple.format(bean.getBeginDate())+"至"+simple.format(bean.getEndDate())+"销售时间价格禁售;");
		comLogDAO.insert(log);
	}

	/**
	 * 查询某一时间段内指定销售产品的时间价格表的数据，其中含所打包的采购产品的时间价格表的数据
	 * 并且把没有销售时间价格的数据也会读取出来
	 * @param prodProductId
	 * @param prodBranchId
	 * @param beginTime
	 * @param endTime
	 * @return 返回销售产品时间价格表，但当中也会返回没有销售价但存在采购的时间价格表
	 */
	public List<TimePrice> selectProdTimePriceByProductId(Long prodProductId,Long prodBranchId, Long beginTime, Long endTime) {
		Assert.notNull(prodBranchId);
		Long productId=prodProductId;
		if(productId==null){//产品id以后要去掉.
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
			productId=branch.getProductId();
		}
		boolean isMultiJourney = false;
		ProdProduct prod = prodProductDAO.selectProductDetailByPrimaryKey(productId);
		if(prod.isRoute()) {
			ProdRoute pr = (ProdRoute) prod;
			isMultiJourney = pr.hasMultiJourney();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", new Date(beginTime));
		param.put("endDate", new Date(endTime));
		List<TimePrice> prodTimePriceList = prodTimePriceDAO.selectProdTimePriceByParams(param);
		Map<Date, TimePrice> ptpMap = getTimePriceMap(prodTimePriceList);
		List<TimePrice> timePriceList = selectIntersection(productId,prodBranchId, beginTime, endTime);
		for (TimePrice timePrice : timePriceList) {
			TimePrice prodTimePrice = ptpMap.get(timePrice.getSpecDate());
			if (prodTimePrice!=null) {
				timePrice.setPrice(prodTimePrice.getPrice());
				timePrice.setTimePriceId(prodTimePrice.getTimePriceId());
				timePrice.setAheadHour(prodTimePrice.getAheadHour());
				timePrice.setCancelHour(prodTimePrice.getCancelHour());
				timePrice.setFixedAddPrice(prodTimePrice.getFixedAddPrice());
				timePrice.setPriceType(prodTimePrice.getPriceType());
				timePrice.setRatePrice(prodTimePrice.getRatePrice());
				timePrice.setCancelStrategy(prodTimePrice.getCancelStrategy());
				//多行程，在时间价格表上展示每天对应的行程名称，方便查看
				if(isMultiJourney) {
					Long multiJourneyId = prodTimePrice.getMultiJourneyId();
					if(multiJourneyId != null) {
						ViewMultiJourney vmj = viewMultiJourneyDAO.selectByPrimaryKey(multiJourneyId);
						if(vmj != null) {
							timePrice.setMultiJourneyName(vmj.getJourneyName());
						}
					}
				}
			}
		}
		return timePriceList;
	}
	
	/**
	 * 查询指定销售产品的最近可用的时间价格表 
	 * @param prodProductId
	 * @param prodBranchId
	 * @param beginTime
	 * @param endTime
	 * @return 必须是完成可销售的时间价格表,同时包含采购与销售时间价格
	 */
	public List<TimePrice> selectSaleProdTimePriceByProductId(Long prodProductId,Long prodBranchId,Long beginTime, Long endTime){
		Assert.notNull(prodBranchId);
		Long productId=prodProductId;
		if(productId==null){//产品id以后要去掉.
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
			productId=branch.getProductId();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", new Date(beginTime));
		param.put("endDate", new Date(endTime));
		List<TimePrice> prodTimePriceList = prodTimePriceDAO.selectProdTimePriceByParams(param);
		List<TimePrice> result=new ArrayList<TimePrice>();
		if(CollectionUtils.isNotEmpty(prodTimePriceList)){
			Map<Date, TimePrice> ptpMap = getTimePriceMap(prodTimePriceList);
			List<TimePrice> timePriceList = selectIntersection(productId,prodBranchId, beginTime, endTime);
			for (TimePrice timePrice : timePriceList) {
				TimePrice prodTimePrice = ptpMap.get(timePrice.getSpecDate());
				if (prodTimePrice!=null) {
					timePrice.setPrice(prodTimePrice.getPrice());
					timePrice.setTimePriceId(prodTimePrice.getTimePriceId());
					timePrice.setAheadHour(prodTimePrice.getAheadHour());
					timePrice.setCancelHour(prodTimePrice.getCancelHour());
					result.add(timePrice);
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 查询某一时间段内指定销售产品所关联的采购产品时间价格表的交集
	 * @param prodProductId 指定的销售产品
	 * @param beginTime 起始时间
	 * @param endTime 结束时间
	 * @return 时间价格表列表
	 */
	public List<TimePrice> selectIntersection(Long prodProductId,Long prodBranchId, Long beginTime, Long endTime){
		return selectIntersection(prodProductId,prodBranchId,beginTime,endTime, false);
	}
	
	/**
	 * 查询某一时间段内指定销售产品所关联的采购产品时间价格表的交集
	 * @param productId 指定的销售产品
	 * @param beginTime 起始时间
	 * @param endTime 结束时间
	 * @return 时间价格表列表
	 */
	private final Long cancelHourL = 7*31*24*60l;
	
	public List<TimePrice> selectIntersection(Long prodProductId,Long prodBranchId, Long beginTime, Long endTime,boolean calcMetaHour) {
		Long productId=prodProductId;
		if(productId==null){//为空的时候从branch当中取出相应的产品id
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
			productId=branch.getProductId();
		}
		//查找销售类别关联的采购类别
		List<MetaProductBranch> metaProductBranchs = metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
		
		//将metaProductBranchs转换成map<metaBranchId,MetaProductBranch>方便使用
		Map<Long, MetaProductBranch> metaProductBranchMap = new HashMap<Long, MetaProductBranch>();
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			metaProductBranchMap.put(metaProductBranch.getMetaBranchId(), metaProductBranch);
		}
		//查找采购产品列表的时间价格表
		List<Map<Date, TimePrice>> list = getMetaProductTimePrice(beginTime,endTime, metaProductBranchs);
		
		//计算“销售产品类别”所关联的“采购产品类别”时间价格表的交集
		//86400000毫秒等于一天。
		Map<Long, ProdProductBranchItem> itemMap = this.getProdProductBranchItemByPordBranchId(prodBranchId);
		List<TimePrice> timePriceList = new ArrayList<TimePrice>();
		for(long time = beginTime; time <= endTime; time += 86400000) {//加一天
			Date date = new Date(time);
			TimePrice bean = new TimePrice();
			bean.setSpecDate(date);
			boolean intersection = true;
			Long aheadHour = null, cancelHour = null;
			String cancelStrategy = "";
			for (Map<Date, TimePrice> mapx : list) {
				TimePrice metaBranchTimePrice = mapx.get(date);
				//有一天没时间价格
				if (metaBranchTimePrice == null) {
					intersection = false;
					break;
				} 
				
				ProdProductBranchItem item = itemMap.get(metaBranchTimePrice.getMetaBranchId());
				MetaProductBranch metaProductBranch = metaProductBranchMap.get(metaBranchTimePrice.getMetaBranchId());
				
				//计算当天时间价格库存
				bean = this.toDayTimePrice(productId,metaBranchTimePrice, item, metaProductBranch, bean, intersection);
				//取打包的采购类别中最大的aheadHour和cancelHour
				if(calcMetaHour) {
					if(metaBranchTimePrice.getAheadHour() != null) {
						if(aheadHour == null) {
							aheadHour = metaBranchTimePrice.getAheadHour();
						} else {
							if(metaBranchTimePrice.getAheadHour() > aheadHour) {
								aheadHour = metaBranchTimePrice.getAheadHour();
							}
						}
					}
					String stragtegy = metaBranchTimePrice.getCancelStrategy();
					if(StringUtils.isNotEmpty(stragtegy)) {
						if(StringUtils.isEmpty(cancelStrategy)) {
							cancelStrategy = stragtegy;
						} else {
							if(Constant.CANCEL_STRATEGY.FORBID.name().equalsIgnoreCase(cancelStrategy)) {   //不退不改,最高级
							} else if(Constant.CANCEL_STRATEGY.MANUAL.name().equalsIgnoreCase(cancelStrategy)) {  //人工确认退改,次于最高
								if(Constant.CANCEL_STRATEGY.FORBID.name().equalsIgnoreCase(stragtegy)) {
									cancelStrategy = stragtegy;
								}
							} else { //可退改,最低级
								cancelStrategy = stragtegy;
							}
						}
					}
					if(Constant.CANCEL_STRATEGY.ABLE.name().equalsIgnoreCase(stragtegy) && metaBranchTimePrice.getCancelHour() != null) {
						if(cancelHour == null) {
							cancelHour = metaBranchTimePrice.getCancelHour();
						} else {
							if(metaBranchTimePrice.getCancelHour() > cancelHour) {
								cancelHour = metaBranchTimePrice.getCancelHour();
							}
						}
					}
				}
			}
			if (intersection) {
				bean.setAheadHour(aheadHour);
				if(StringUtils.isNotEmpty(cancelStrategy)) {//退改策略不为可退改，默认最晚取消小时数为7个月
					if(!cancelStrategy.equalsIgnoreCase(Constant.CANCEL_STRATEGY.ABLE.name())) {
						cancelHour = cancelHourL;
					}
				}
				bean.setCancelHour(cancelHour);
				bean.setCancelStrategy(cancelStrategy);
				timePriceList.add(bean);
			}
		}
		return timePriceList;
	}

	private List<Map<Date, TimePrice>> getMetaProductTimePrice(Long beginTime,
			Long endTime, List<MetaProductBranch> metaProductBranchs) {
		List<Map<Date, TimePrice>> list = new ArrayList<Map<Date, TimePrice>>();//
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("beginDate", new Date(beginTime));
			param.put("endDate", new Date(endTime));
			param.put("metaBranchId", metaProductBranch.getMetaBranchId());
			List<TimePrice> timePrices = metaTimePriceDAO.getMetaTimePriceByMetaBranchId(param);
			//把时间价格转成map(日期,TimePrice)
			Map<Date, TimePrice> mapx = getTimePriceMap(timePrices);
			list.add(mapx);
		}
		return list;
	}
	/**
	 * 
	 * @param metaBranchTimePrice 打包采购类别时间价格
	 * @param item	打包项
	 * @param metaProductBranch 销售产品类别
	 * @param todayTimePrice 时间价格
	 * @param intersection
	 * @return
	 */
	private TimePrice toDayTimePrice(Long productId,TimePrice metaBranchTimePrice,ProdProductBranchItem item,MetaProductBranch metaProductBranch,TimePrice todayTimePrice,boolean intersection){
			if (metaProductBranch.isTotalDecrease()) {
				Long totalStock = metaProductBranch.getTotalStock();
				
				if(totalStock>0){
					todayTimePrice.updateDayStock(totalStock/item.getQuantity(), metaProductBranch.isTotalDecrease());
				} else {
					todayTimePrice.updateDayStock(totalStock, metaProductBranch.isTotalDecrease());
				}
			}else{
				if(metaBranchTimePrice.getDayStock()>0){
					todayTimePrice.updateDayStock(metaBranchTimePrice.getDayStock()/item.getQuantity(), metaProductBranch.isTotalDecrease());
				} else {
					todayTimePrice.updateDayStock(metaBranchTimePrice.getDayStock(), metaProductBranch.isTotalDecrease());
				}
				
			}
			todayTimePrice.setProductId(productId);
			todayTimePrice.setMetaBranchId(metaBranchTimePrice.getMetaBranchId());
			todayTimePrice.plusMarketPrice(metaBranchTimePrice.getMarketPrice()*item.getQuantity());
			todayTimePrice.plusSettlementPrice(metaBranchTimePrice.getSettlementPrice()*item.getQuantity());
			return todayTimePrice;
	}
	
	/**
	 * 判断销售产品类别打包的采购产品类别是否有无交集
	 * @param prodBranchId
	 * @param beginTime
	 * @param endTime
	 * @param metaBranchId
	 * @return
	 */
	public boolean selectIntersectionMetaProduct(Long prodBranchId, Long beginTime, Long endTime, Long metaBranchId ) {
		List<MetaProductBranch> metaBranchs = metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
		if(CollectionUtils.isEmpty(metaBranchs)){ 
			return true; 
		}
		metaBranchs.add(metaProductBranchDAO.selectBrachByPrimaryKey(metaBranchId));
		Map<Long, MetaProductBranch> productMap = new HashMap<Long, MetaProductBranch>();
		for (MetaProductBranch branch : metaBranchs) {
			productMap.put(branch.getMetaBranchId(), branch);
		}
		List<Map<Date, TimePrice>> list = new ArrayList<Map<Date, TimePrice>>();
		for (MetaProductBranch branch : metaBranchs) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("beginDate", new Date(beginTime));
			param.put("endDate", new Date(endTime));
			param.put("metaBranchId", branch.getMetaBranchId());
			List<TimePrice> timePrices = metaTimePriceDAO.getMetaTimePriceByMetaBranchId(param);
			Map<Date, TimePrice> mapx = getTimePriceMap(timePrices);
			list.add(mapx);
		}
		List<TimePrice> timePriceList = new ArrayList<TimePrice>();
		for(long time=beginTime; time<=endTime; time+=86400000) {
			Date date = new Date(time);
			TimePrice bean = new TimePrice();
			bean.setSpecDate(date);
			bean.setProdBranchId(prodBranchId);
			boolean intersection=true;
			for (Map<Date, TimePrice> mapx : list) {
				TimePrice metaTimePrice = mapx.get(date);
				if (metaTimePrice==null) {
					intersection=false;
					break;
				}
			}
			if (intersection) {
				timePriceList.add(bean);
			}
		}
		return !timePriceList.isEmpty();		
	}
	
	/**
	 * 取销售类别对应的采购类别的时间价格表 
	 * @author yangbin
	 * change:2012-2-15 上午11:51:40
	 * 
	 * @param prodBranchId
	 * @param today
	 * @return
	 */
	public List<Date> getMetaTimePriceDates(Long prodBranchId,Date today){
		List<MetaProductBranch> metaProducts = metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
		List<Date> dates = new ArrayList<Date>();

		for (MetaProductBranch branch : metaProducts) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("beginDate", today);
			param.put("metaBranchId", branch.getMetaBranchId());
			List<TimePrice> timePrices = metaTimePriceDAO.getMetaTimePriceByMetaBranchId(param);
			
			if(!dates.isEmpty()){
				for(int i=0;i<dates.size();i++){
					boolean flag = false;
					for(TimePrice tp : timePrices){
						if(tp.getSpecDate().getTime()==dates.get(i).getTime()){
							flag=true;
							break;
						}
					}
					if(!flag){
						dates.remove(i);
					}
				}
			}else{
				for(TimePrice tp : timePrices){
					dates.add(tp.getSpecDate());
				}
			}
		}
		return dates;
	}
		
	
	/**
	 * 查询不包含采购产品时间价格的销售时间价格
	 * @author yangbin
	 * change:2012-2-15 下午12:46:26
	 * 
	 * @param today
	 * @param productId
	 * @param metaBranchId
	 * @return
	 */
	public List<Date> checkTimePriceContain(Date today, Long prodBranchId, Long metaBranchId) {
		Map<String, Object> paramMeta = new HashMap<String, Object>();
		List<Date> dates = getMetaTimePriceDates(prodBranchId, today);
		List<Date> res = new ArrayList<Date>();
		paramMeta.put("beginDate", today); 
		paramMeta.put("metaBranchId", metaBranchId); 
		List<TimePrice> metaTimePrices = metaTimePriceDAO.getMetaTimePriceByMetaBranchId(paramMeta);
		
		Map<String, Object> prodParam = new HashMap<String, Object>();
		prodParam.put("beginDate", today);
		prodParam.put("prodBranchId", prodBranchId);
		List<TimePrice> prodTimePrices = prodTimePriceDAO.selectProdTimePriceByParams(prodParam);

		for(Date d : dates){
			boolean flag = false;//用来表示新打包的采购产品是否存在于原先打包的采购产品的交集
			for(TimePrice tp : metaTimePrices){
				if(tp.getSpecDate().getTime()==d.getTime()){
					flag=true;
					break;
				}
			}
			if(!flag){
				boolean b = false;
				for(TimePrice tp : prodTimePrices){
					if(tp.getSpecDate().getTime()==d.getTime()){
						b=true;
						break;
					}
				}
				if(b){
					res.add(d);
				}
			}
		}
		return res;
	}
	
	/**
	 * 更新时间价格表的多行程外键
	 * @param timeprice
	 */
	public void updateTimePriceByViewMultijourney(TimePrice timeprice){
		prodTimePriceDAO.update(timeprice);
	}
	
		
	/**
	 * 把时间价格转成map(日期,TimePrice)
	 * @param ptpList
	 * @return
	 */
	private Map<Date, TimePrice> getTimePriceMap(List<TimePrice> ptpList) {
		Map<Date, TimePrice> map = new HashMap<Date, TimePrice>();
		for (Object obj : ptpList) {
			if (obj instanceof TimePrice) {
				TimePrice timePrice = (TimePrice)obj;
				map.put(timePrice.getSpecDate(), timePrice);
			}
		}
		return map;
	}
	
	/**
	 * 查询销售产品类别打包采购产品子项
	 * @param prodBranchId
	 * @return
	 */
	private Map<Long, ProdProductBranchItem> getProdProductBranchItemByPordBranchId(Long prodBranchId) {
		Map<Long, ProdProductBranchItem> map = new HashMap<Long, ProdProductBranchItem>();
		List<ProdProductBranchItem> itemList = prodProductBranchItemDAO.selectBranchItemByProdBranchId(prodBranchId);
		for (ProdProductBranchItem prodProductBranchItem : itemList) {
			map.put(prodProductBranchItem.getMetaBranchId(), prodProductBranchItem);
		}		
		return map;
	}
	
	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public static void main(String[] args) {
		Calendar calendar = DateUtil.getClearCalendar();
		calendar.set(Calendar.DATE, 9);

	}

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
 
	public void setLimitSaleTimeDAO(LimitSaleTimeDAO limitSaleTimeDAO) {
		this.limitSaleTimeDAO = limitSaleTimeDAO;
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	/**
	 * @param prodJourneyLogic the prodJourneyLogic to set
	 */
	public void setProdJourneyLogic(ProdJourneyLogic prodJourneyLogic) {
		this.prodJourneyLogic = prodJourneyLogic;
	}
	

	/**
	 * 重新计算非固定价格的时间价格表
	 * @param prodBranchId
	 * @param timeRange
	 */
	public void updateTimePriceByProdBranchId(final Long prodBranchId,final TimeRange timeRange){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("prodBranchId", prodBranchId);
		params.put("beginDate", timeRange.getStart());
		params.put("endDate", timeRange.getEnd());
		List<TimePrice> prodTimePriceList=prodTimePriceDAO.selectProdTimePriceByParams(params);
		if(CollectionUtils.isNotEmpty(prodTimePriceList)){
			Map<Date,TimePrice> prodTimePriceMap=getTimePriceMap(prodTimePriceList);
			List<TimePrice> timePriceList=selectIntersection(null, prodBranchId, timeRange.getStart().getTime(), timeRange.getEnd().getTime(), true);
			for(TimePrice timePrice:timePriceList){
				Date specDate = timePrice.getSpecDate();
				if(prodTimePriceMap.containsKey(specDate)){
					TimePrice prodTimePrice=prodTimePriceMap.get(specDate);	
					if(prodTimePrice!=null){
						prodTimePriceDAO.update(TimePriceUtil.calcPrice(prodTimePrice, timePrice));
					}
				}
			}
		}
		//不定期产品需保存有效期
		ProdProduct product = prodProductDAO.selectProductByProdBranchId(prodBranchId);
		if(product.IsAperiodic()) {
			calculateValidTime(prodBranchId);
		}
	}
	
	
	
	/**
	 * 更新有效期和计算不可游玩日日期
	 * */
	private void calculateValidTime(Long prodBranchId) {
		ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
		List<ProdProductBranchItem> itemList = prodProductBranchItemDAO.selectBranchItemByProdBranchId(prodBranchId);
		//无打包产品,将有效期清空
		if(itemList.isEmpty()) {
			prodProductBranch.setValidBeginTime(null);
			prodProductBranch.setValidEndTime(null);
			prodProductBranch.setInvalidDate(null);
			prodProductBranch.setInvalidDateMemo(null);
		} else {
			List<Date> validTimeList = prodTimePriceDAO.selectValidBranchTimePriceByBranchId(prodBranchId);
			if(CollectionUtils.isNotEmpty(validTimeList)) {
				Date validBeginTime = validTimeList.get(0);
				Date validEndTime = validTimeList.get(validTimeList.size() - 1);
				prodProductBranch.setValidBeginTime(validBeginTime);
				prodProductBranch.setValidEndTime(validEndTime);
				
				Set<Date> dateSet = new HashSet<Date>();
				dateSet.addAll(validTimeList);
				
				StringBuffer invalidDate = new StringBuffer();
				Date dStart = null,dEnd = null,dTemp = null;
				//自动计算不可售日期
				for(long time = validBeginTime.getTime(); time <= validEndTime.getTime(); time += 86400000) {//加一天
					Date date = new Date(time);
					if(!dateSet.contains(date)) {
						if(dStart == null) {
							dStart = date;
							dTemp = dStart;
						} else {
							int day = DateUtil.getDaysBetween(dTemp, date);
							if(day == 1) {
								dEnd = date;
								dTemp = dEnd;
							} else if(day > 1) {
								invalidDate = appenInvalidDateStr(invalidDate, dStart, dEnd);
								dStart = date;
								dEnd = null;
								dTemp = dStart;
							}
						}
					}
				}
				if(dStart != null) {
					invalidDate = appenInvalidDateStr(invalidDate, dStart, dEnd);
				}
				if(StringUtils.isNotEmpty(invalidDate.toString())) {
					prodProductBranch.setInvalidDate(invalidDate.toString().substring(0, invalidDate.length()-1));
				}
			}
		}
		prodProductBranchDAO.updateByPrimaryKeySelective(prodProductBranch);
	}
	/**
	 * 取得优惠后的最低价格
	 * @param prodBranchId
	 */
	private Long getSellPriceByFavor(long prodBranchId){
		Long lowerPriceByFavor=null;
		List<CalendarModel> cmList;
		cmList = beeProdProductService.getProductCalendarByBranchId(prodBranchId);
		cmList = favorService.fillFavorParamsInfoForCalendar(null,prodBranchId, cmList);
		//遍历时间价格表每一天
		for(CalendarModel calendarModel : cmList){
			TimePrice[][]  timePriceArray = calendarModel.getCalendar();
			for(int i = 0; i < timePriceArray.length; i++){
				for(int j = 0; j < timePriceArray[i].length; j++){
					TimePrice timePrice = timePriceArray[i][j];
					if(timePrice.getSpecDate() != null ){
						Long favorPrice =0L;
						if(timePrice.getFavorJsonParams()!=null && !"".equals(timePrice.getFavorJsonParams().trim())){

							try {
								//*********
								//每一个团的优惠价格 开始
								//*********
								JSONArray array =JSONArray.fromObject(timePrice.getFavorJsonParams());
								for (int k = 0; k < array.size(); k++) {
									Map<String, String> map = (Map<String, String>)array.get(k);
									//价格
									if("1".equals(map.get("index")) &&map.get("param")!=null){
										String[] param = map.get("param").toString().split("\\|");
										if(param.length==2 && param[1]!=null && !"".equals(param[1].trim())){
											favorPrice = PriceUtil.convertToFen(param[1]);
										}
									}
								}
								//*********
								//每一个团的优惠价格 结束
								//*********
							} catch (Exception e) {
								favorPrice=0L;
							}
						}
						Long lowerPrice = PriceUtil.convertToFen(timePrice.getSellPriceFloat());
						if(timePrice.getTimePriceId()>0){
							if(lowerPriceByFavor==null){
								lowerPriceByFavor = lowerPrice-favorPrice;
							}
								
							if(lowerPrice-favorPrice<lowerPriceByFavor){
								lowerPriceByFavor = lowerPrice-favorPrice;
							}
							if(lowerPriceByFavor<0){
								lowerPriceByFavor =0L;
							}
						}
						//System.out.println(lowerPriceByFavor+"-"+timePrice.getFavorJsonParams());
					}
				}
			}
		}
		return lowerPriceByFavor;
	}
	
	private StringBuffer appenInvalidDateStr(StringBuffer invalidDate, Date dStart, Date dEnd) {
		invalidDate.append(DateUtil.formatDate(dStart, "yyMMdd"));
		if(dEnd != null) {
			invalidDate.append("-"+DateUtil.formatDate(dEnd, "yyMMdd"));
		}
		invalidDate.append(",");
		return invalidDate;
	}

	public void setViewMultiJourneyDAO(ViewMultiJourneyDAO viewMultiJourneyDAO) {
		this.viewMultiJourneyDAO = viewMultiJourneyDAO;
	}

	public void setBeeProdProductService(BeeProdProductService beeProdProductService) {
		this.beeProdProductService = beeProdProductService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

	public void setBonusReturnLogic(BonusReturnLogic bonusReturnLogic) {
		this.bonusReturnLogic = bonusReturnLogic;
	}
	
	

}