/**
 * 
 */
package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.prod.LineInfo;
import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.LineStopVersion;
import com.lvmama.comm.bee.po.prod.LineStops;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.bee.po.prod.ProdTrainFetchInfo;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.bee.vo.train.SeatInfo;
import com.lvmama.comm.bee.vo.train.TrainParamInfo;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.train.LineStopsComparator;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.StationInfo;
import com.lvmama.prd.dao.LineInfoDAO;
import com.lvmama.prd.dao.LineStationDAO;
import com.lvmama.prd.dao.LineStationStationDAO;
import com.lvmama.prd.dao.LineStopVersionDAO;
import com.lvmama.prd.dao.LineStopsDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductRelationDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.dao.ProdTrainFetchInfoDAO;

/**
 * @author yangbin
 *
 */
public class ProdTrainServiceImpl implements ProdTrainService{

	private LineStationDAO lineStationDAO;
	private LineInfoDAO lineInfoDAO;
	private LineStopsDAO lineStopsDAO;
	private LineStopVersionDAO lineStopVersionDAO;
	private MetaProductDAO metaProductDAO;
	private MetaProductService metaProductService;
	private ProdProductService prodProductService;
	private ProdProductRelationDAO prodProductRelationDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private LineStationStationDAO lineStationStationDAO;
	private MetaProductBranchDAO metaProductBranchDAO;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	private ProdTimePriceDAO prodTimePriceDAO;
	private ProdTrainFetchInfoDAO prodTrainFetchInfoDAO;
	private static final String TICKET_TYPE_ADULT = "ADULT";
	private static final String TICKET_TYPE_CHILD = "CHILD";
	private static final int TRAIN_STATUS_NORMAL = 0;
	private static final int TRAIN_STATUS_ADD = 1;
	private static final int TRAIN_STATUS_UPDATE = 2;
	private static final String REQUEST_TYPE = "ALL";
	
	@Override
	public LineStationStation getStationStationById(Long stationStationId) {
		return lineStationStationDAO.selectByPrimaryKey(stationStationId);
	}

	@Override
	public LineStationStation getStationStationDetailById(Long stationStationId) {
		LineStationStation ss = getStationStationById(stationStationId);
		if(ss!=null){
			ss.setDepartureStation(lineStationDAO.selectByPrimaryKey(ss.getDepartureStationId()));
			ss.setArrivalStation(lineStationDAO.selectByPrimaryKey(ss.getArrivalStationId()));
		}
		return ss;
	}
//	private LineStation getStationAndCreate(Station station){
//		LineStation ls = lineStationDAO.selectByNameAndPinyin(station.getName(), station.getPinyin());
//		if(ls==null){
//			ls = new LineStation();
//			ls.setStationName(station.getName());
//			ls.setStationPinyin(station.getPinyin());
//			ls.setStationPy(station.getPinyinHead());
//			ls.setStationId(lineStationDAO.insert(ls));
//		}
//		return ls;
//	}
	@Override
	public ResultHandleT<Long> updateTrainLineInfo(LineInfo lineInfo, Date specDate,TrainParamInfo trainParamInfo) {
		ResultHandleT<Long> result = new ResultHandleT<Long>();
		if(prodInsuranceBranch==null){
			prodInsuranceBranch = prodProductBranchDAO.selectByPrimaryKey(trainParamInfo.getProdInsuranceId());
		}
		ProdProduct product=null;
		if(lineInfo.getLineInfoId() == null){
			//首先添加Line_Info表
			lineInfoDAO.insert(lineInfo);
			//然后添加Meta_Product_Traffic表
			Long productId = addMetaProductTraffic(lineInfo, trainParamInfo);
			result.setReturnContent(productId);
			//最后添加Prod_Product、Prod_Product_Traffic表
			product = addProdTraffic(lineInfo, trainParamInfo);
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("productId", product.getProductId());
			param.put("payToLvmama", "true");
			param.put("payToSupplier", "false");
		    this.prodProductDAO.updatePaymentTarget(param);		
		}else{
			lineInfoDAO.updateByPrimaryKey(lineInfo);
			//然后修改Meta_Product_Traffic表
			Long productId = addMetaProductTraffic(lineInfo, trainParamInfo);
			result.setReturnContent(productId);
			//最后修改Prod_Product、Prod_Product_Traffic表
			product = addProdTraffic(lineInfo, trainParamInfo);
		}
		addRelation(product.getProductId());
		return result;
	}
	
	private ProdProductBranch prodInsuranceBranch=null;
	
	private void addRelation(final Long productId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("prodBranchId", prodInsuranceBranch.getProdBranchId());
		
		List<ProdProductRelation> list = prodProductRelationDAO.selectProdRelationByParam(map);
		if(CollectionUtils.isEmpty(list)){
			ProdProductRelation relation=new ProdProductRelation();
			relation.setProductId(productId);
			relation.setRelatProductId(prodInsuranceBranch.getProductId());
			relation.setProdBranchId(prodInsuranceBranch.getProdBranchId());
			relation.setSaleNumType(Constant.SALE_NUMBER_TYPE.ALL.name());//必买
			prodProductRelationDAO.insert(relation);
		}
	}
		
	private Long addMetaProductTraffic(LineInfo lineInfo, TrainParamInfo trainParamInfo) {
		// TODO Auto-generated method stub
		Long productId;
		MetaProductTraffic mpt = metaProductService.getTrainMetaProduct(lineInfo.getFullName());
		if(mpt == null){
			mpt = new MetaProductTraffic();
			mpt.setBizCode(lineInfo.getFullName());
			mpt.setCurrencyType(Constant.FIN_CURRENCY.CNY.name());
			mpt.setDays(lineInfo.getTakeDays());
			mpt.setIsResourceSendFax("false");
			mpt.setLineInfoId(lineInfo.getLineInfoId());
			mpt.setPayToLvmama("true");
			mpt.setPayToSupplier("false");
			mpt.setProductName(lineInfo.getStartStation().getStationName()+"-"+lineInfo.getEndStation().getStationName()+lineInfo.getFullName());
			mpt.setProductType(Constant.PRODUCT_TYPE.TRAFFIC.name());
			mpt.setSubProductType(Constant.SUB_PRODUCT_TYPE.TRAIN.name());
			mpt.setSupplierId(trainParamInfo.getSupplierId());
			mpt.setManagerId(trainParamInfo.getPermUserId());
			mpt.setOrgId(trainParamInfo.getOrgId());
			mpt.setSupplierChannel(Constant.SUPPLIER_CHANNEL.TRAIN.name());
			mpt.setIsAperiodic("false");
			mpt.setValid("Y");//默认先不上线，该功能由站站更新后来更改
			productId=metaProductService.addMetaProduct(mpt, "SYSTEM");
			mpt.setMetaProductId(productId);
		}else{
			mpt.setLineInfoId(lineInfo.getLineInfoId());
			mpt.setCurrencyType(Constant.FIN_CURRENCY.CNY.name());
			mpt.setDays(lineInfo.getTakeDays());
			mpt.setIsResourceSendFax("false");
			mpt.setLineInfoId(lineInfo.getLineInfoId());
			mpt.setProductName(lineInfo.getStartStation().getStationName()+"-"+lineInfo.getEndStation().getStationName()+lineInfo.getFullName());
			mpt.setSupplierId(trainParamInfo.getSupplierId());
			mpt.setManagerId(trainParamInfo.getPermUserId());
			mpt.setOrgId(trainParamInfo.getOrgId());
			mpt.setSupplierChannel(Constant.SUPPLIER_CHANNEL.TRAIN.name());
			mpt.setIsAperiodic("false");
			metaProductService.updateMetaProduct(mpt, "SYSTEM");
			productId = mpt.getMetaProductId();
		}
		return productId;
	}
	
	private ProdProduct addProdTraffic(LineInfo lineInfo, TrainParamInfo trainParamInfo) {
		ProdProduct product;
		ProdTraffic prodTraffic = prodProductService.getTrainProduct(lineInfo.getFullName());
		if(prodTraffic == null){
			prodTraffic = new ProdTraffic();
			prodTraffic.setAdditional("false");
			prodTraffic.setBizcode(lineInfo.getFullName());
			prodTraffic.setCanPayByBonus("N");
			prodTraffic.setCouponAble("false");
			prodTraffic.setCreateTime(new Date());
			prodTraffic.setDays(lineInfo.getTakeDays());
			prodTraffic.setFilialeName(Constant.FILIALE_NAME.SH_FILIALE.name());
			prodTraffic.setOnlineTime(new Date());
			prodTraffic.setOfflineTime(DateUtils.addYears(prodTraffic.getOnlineTime(), 1));
			prodTraffic.setOnLine("false");
			prodTraffic.setProductName(lineInfo.getStartStation().getStationName()+"-"+lineInfo.getEndStation().getStationName()+lineInfo.getFullName());
			prodTraffic.setProductType(Constant.PRODUCT_TYPE.TRAFFIC.name());
			prodTraffic.setSendSms("true");
			prodTraffic.setSubProductType(Constant.SUB_PRODUCT_TYPE.TRAIN.name());
			prodTraffic.setValid("Y");
			prodTraffic.setCouponActivity("false");
			prodTraffic.setIsRefundable("N");
			prodTraffic.setPrePaymentAble("N");
			prodTraffic.setLineInfoId(lineInfo.getLineInfoId());
			prodTraffic.setManagerId(trainParamInfo.getPermUserId());
			prodTraffic.setTravellerInfoOptions("NAME,CARD_NUMBER,F_NAME,F_CARD_NUMBER");
			prodTraffic.setOrgId(trainParamInfo.getOrgId());
			prodTraffic.setIsAperiodic("false");
			product = prodProductService.addProductChannel(prodTraffic, 
					new String[]{Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.BACKEND.name()},"SYSTEM");
		}else{
			prodTraffic.setAdditional("false");
			prodTraffic.setBizcode(lineInfo.getFullName());
			prodTraffic.setCanPayByBonus("N");
			prodTraffic.setCouponAble("false");
//			prodTraffic.setCreateTime(new Date());
			prodTraffic.setDays(lineInfo.getTakeDays());
			prodTraffic.setFilialeName(Constant.FILIALE_NAME.SH_FILIALE.name());
			//prodTraffic.setOfflineTime(DateUtils.addYears(prodTraffic.getOnlineTime(), 1));
			prodTraffic.setProductName(lineInfo.getStartStation().getStationName()+"-"+lineInfo.getEndStation().getStationName()+lineInfo.getFullName());
			prodTraffic.setCouponActivity("false");
			prodTraffic.setIsRefundable("N");
			prodTraffic.setPrePaymentAble("N");
			prodTraffic.setLineInfoId(lineInfo.getLineInfoId());
			prodTraffic.setManagerId(trainParamInfo.getPermUserId());
			prodTraffic.setTravellerInfoOptions("NAME,CARD_NUMBER,F_NAME,F_CARD_NUMBER");
			prodTraffic.setOrgId(trainParamInfo.getOrgId());
			prodTraffic.setIsAperiodic("false");
			prodProductService.updateByPrimaryKey(prodTraffic, "SYSTEM");
			product = prodTraffic;
		}
		return product;
	}

	private ProdTrainCache initCache(ProdProduct product,ProdProductBranch branch, LineInfo lineInfo,LineStationStation lss,long price,Date visitTime){
		ProdTrainCache cache = new ProdTrainCache();
		cache.setArrivalTime(lss.getArrivalTime());
		cache.setCategory(lineInfo.getCategory());
		cache.setDepartureTime(lss.getDepartureTime());
		cache.setLineInfoId(lineInfo.getLineInfoId());
		cache.setLineName(lss.getLineName());
		cache.setPrice(price);
		cache.setProdBranchId(branch.getProdBranchId());
		cache.setProductId(product.getProductId());
		cache.setProductName(product.getProductName());
		cache.setSeatType(branch.getBerth());
		cache.setDepartureCity(lss.getDepartureStation().getCityPinyin());
		cache.setArrivalCity(lss.getArrivalStation().getCityPinyin());
		
		cache.setDepartureStation(lss.getDepartureStation().getStationPinyin());
		cache.setArrivalStation(lss.getArrivalStation().getStationPinyin());
		cache.setDepartureStationName(lss.getDepartureStation().getStationName());
		cache.setArrivalStationName(lss.getArrivalStation().getStationName());
		cache.setTakenTime(lss.getTakenTime());
		cache.setStationStationId(lss.getStationStationId());
//		cache.setTimePriceId(timePrice.getTimePriceId());
		cache.setVisitTime(visitTime);
		cache.setProdBranchName(branch.getBranchName());
		cache.setSoldout("false");
		LineStation station = lineStationIdMap.get(lineInfo.getStartStationId());
		if(station!=null){
			cache.setStartStationName(station.getStationName());
		}
		station = lineStationIdMap.get(lineInfo.getEndStationId());
		if(station!=null){
			cache.setEndStationName(station.getStationName());
		}
		return cache;
	}
	
	/**
	 * 初始化缓存数据
	 */
	@Override
	public void initTrainCacheMap(boolean create){
		if(create){
			List<LineStation> list = lineStationDAO.selectAll();
			lineStationMap = new HashMap<String, LineStation>();
			lineStationIdMap = new HashMap<Long, LineStation>();
			for(LineStation ls:list){
				lineStationMap.put(ls.getStationName(), ls);
				lineStationIdMap.put(ls.getStationId(), ls);
			}
			
			List<LineInfo> infos =lineInfoDAO.selectAll();
			lineInfoMap = new HashMap<String, LineInfo>();
			for(LineInfo li:infos){
				//System.out.println(li.getFullName());
				lineInfoMap.put(li.getFullName(), li);
			}
			initMapFlag=true;
			logger.info("init cache success");
		}else{
			if(lineStationMap!=null){
				lineStationMap.clear();
			}
			if(lineInfoMap!=null){
				lineInfoMap.clear();
			}
			if(lineStationIdMap!=null){
				lineStationIdMap.clear();
			}
			initMapFlag=false;
			logger.info("clear cache success");
		}
		
	}
	private Map<Long,LineStation> lineStationIdMap;
	private Map<String,LineInfo> lineInfoMap;
	private Map<String,LineStation> lineStationMap;
	@Override
	public Map<String,Object> updateStation2StationInfo(Station2StationInfo ssi, String requestDate) {
		Date specDate = DateUtil.toDate(requestDate, "yyyy-MM-dd");
		LineStation departStation = lineStationMap.get(ssi.getDepart_station());
		if(departStation == null){ 
			logger.info("车站不存在::::"+ssi.getDepart_station());
			return null;
		}
		LineStation arriveStation = lineStationMap.get(ssi.getArrive_station());
		if(arriveStation == null){
			logger.info("车站不存在::::"+ssi.getArrive_station());
			return null;
		}
		
		LineStationStation lss=lineStationStationDAO.selectByStationKey(departStation.getStationPinyin() + "-" + arriveStation.getStationPinyin(), ssi.getTrain_id());
		LineInfo lineInfo = lineInfoMap.get(ssi.getTrain_id());
		if(lineInfo == null){ 
			logger.info("车次不存在::::"+ssi.getTrain_id());
			return null;
		}
		List<ProdTrainCache> prodTrainCaches = new ArrayList<ProdTrainCache>();
		List<Long> soldoutList = new ArrayList<Long>();
		lss = this.addLineInfo(lss, ssi, departStation, arriveStation, lineInfo);
		
		MetaProduct metaProduct = metaProductService.getTrainMetaProduct(lineInfo.getFullName());
		if(metaProduct == null) {
			logger.info("采购产品不存在："+lineInfo.getFullName());
			return null;
		}
		ProdProduct prodProduct = prodProductService.getTrainProduct(lineInfo.getFullName());
		if(prodProduct == null){ 
			logger.info("销售产品不存在:"+lineInfo.getFullName());
			return null;}
		List<MetaProductBranch> list = metaProductBranchDAO.selectMetaBranchsByStationStation(lss.getStationStationId());
		List<ProdProductBranch> prodList = prodProductBranchDAO.selectProdBranchsByStationStation(lss.getStationStationId());
		Set<Long> ids = new HashSet<Long>();
		Set<Long> prodIds = new HashSet<Long>();
		ssi.arrange();
		Map<String,Long> seatPriceMap = new HashMap<String, Long>();
		for(final SeatInfo si:ssi.getSeats()){
			seatPriceMap.put(si.getCatalog(), si.getPrice());
			MetaProductBranch adultMpb = null;// childMpb = null;
			ProdProductBranch adultPpb = null;// childPpb = null;
			for(MetaProductBranch mpb : list){
				//第一版BranchType中存放的是火车票坐席类型
				//第二版BranchType中存放的是火车票乘客票型，Berth存放的是火车票坐席类型
				if((mpb.getBranchType() !=null && mpb.getBranchType().equals(si.getCatalog())) 
						|| ((mpb.getBerth() != null && mpb.getBerth().equals(si.getCatalog())) 
						&& mpb.getBranchType().equals(String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301.getCode()))))
					adultMpb = mpb;
//				else if((mpb.getBerth() != null && mpb.getBerth().equals(si.getCatalog()))
//						&& mpb.getBranchType().equals(String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302.getCode())))
//					childMpb = mpb;
			}
			for(ProdProductBranch ppb : prodList){
				if((ppb.getBranchType() != null && ppb.getBranchType().equals(si.getCatalog())) 
						|| ((ppb.getBerth() != null && ppb.getBerth().equals(si.getCatalog()))
						&& ppb.getBranchType().equals(String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301.getCode()))))
					adultPpb = ppb;
//				else if((ppb.getBerth() != null && ppb.getBerth().equals(si.getCatalog()))
//						&& ppb.getBranchType().equals(String.valueOf(Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302.getCode())))
//					childPpb = ppb;
			}
			if(si.isExistsFlag() && adultPpb!=null){
				if(ssi.getStatus()==1){
					prodIds.add(adultPpb.getProdBranchId());
				}
				continue;
			}
			//增加成人票子子项
			adultMpb = checkAndSaveMpb(adultMpb, ids, specDate, metaProduct, lss, ssi, si, list, TICKET_TYPE_ADULT);
			//增加儿童票子子项 --目前只卖成人票，所以儿童票注掉
//			childMpb = checkAndSaveMpb(childMpb, ids, specDate, metaProduct, lss, ssi, si, list, TICKET_TYPE_CHILD);
			//增加成人票子项
			checkAndSavePpb(adultPpb, prodIds, specDate, prodProduct, lss, ssi, si, adultMpb, prodList, prodTrainCaches, lineInfo, TICKET_TYPE_ADULT);
			//增加儿童票子项 --目前只卖成人票，所以儿童票注掉
//			checkAndSavePpb(childPpb, prodIds, specDate, prodProduct, lss, ssi, si, childMpb, prodList, prodTrainCaches, lineInfo, TICKET_TYPE_CHILD);
		}
		//删除子子项时间价格表
		for(MetaProductBranch mpb:list){
			if(ssi.getStatus()==TRAIN_STATUS_UPDATE){//如果是更新时需要更采购时间价格中当中已经存在的时间价格
				if(ids.contains(mpb.getMetaBranchId())){
					TimePrice price = metaTimePriceDAO.getMetaTimePriceByIdAndDate(mpb.getMetaBranchId(), specDate);
					if(price!=null){
						if(seatPriceMap.containsKey(mpb.getBerth())){
							long p=seatPriceMap.get(mpb.getBerth());
							if(p!=price.getSettlementPrice()){
								price.setSettlementPrice(p);
								price.setMarketPrice(p);
								metaTimePriceDAO.update(price);
							}
						}
					}
				}
			}
			if(!ids.contains(mpb.getMetaBranchId())){
				TimePrice price = metaTimePriceDAO.getMetaTimePriceByIdAndDate(mpb.getMetaBranchId(), specDate);
				if(price!=null){
					TimePrice timePrice = new TimePrice();
					timePrice.setMetaBranchId(mpb.getMetaBranchId());
					timePrice.setBeginDate(specDate);
					timePrice.setEndDate(specDate);
					timePrice.setDayStock(0);
					metaTimePriceDAO.updateTrainTimePrice(timePrice);
				}
			}
		}
		//删除子项时间价格表，并标识售光
		for(ProdProductBranch ppb:prodList){
			if(ssi.getStatus()==TRAIN_STATUS_UPDATE){//如果是更新时需要更采购时间价格中当中已经存在的时间价格
				if(prodIds.contains(ppb.getProdBranchId())){
					TimePrice price = prodTimePriceDAO.getProdTimePrice(ppb.getProductId(), ppb.getProdBranchId(), specDate);
					if(price!=null){
						if(seatPriceMap.containsKey(ppb.getBerth())){
							long p=seatPriceMap.get(ppb.getBerth());
							if(p!=price.getPrice()){
								price.setPrice(p);
								prodTimePriceDAO.update(price);
							}
						}
					}
				}
			}
			if(!prodIds.contains(ppb.getProdBranchId())){
				TimePrice timePrice = prodTimePriceDAO.getProdTimePrice(ppb.getProductId(), ppb.getProdBranchId(), specDate);
				if(timePrice!=null){
//					prodTimePriceDAO.deleteByPrimaryKey(timePrice.getTimePriceId());
					soldoutList.add(timePrice.getProdBranchId());
				}
			}
		}	
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TRAIN_ADD_CACHE", prodTrainCaches);
		map.put("TRAIN_SOLDOUT_CACHE", soldoutList);
		return map;
	}

	private LineStationStation addLineInfo(LineStationStation lss, Station2StationInfo ssi,
			LineStation departStation, LineStation arriveStation,
			LineInfo lineInfo) {
		if(lss==null){//新添加
			lss = new LineStationStation();
			lss.setLineName(ssi.getTrain_id());
			lss.setDepartureStation(departStation);
			lss.setDepartureStationId(departStation.getStationId());
			lss.setDepartureTime(TimePriceUtil.getLongTime(ssi.getDepart_time()));
			
			lss.setArrivalStationId(arriveStation.getStationId());
			lss.setArrivalStation(arriveStation);
			lss.setArrivalTime(TimePriceUtil.getLongTime(ssi.getArrive_time()));
			
			lss.setDirect(null);
			lss.setStationKey(departStation.getStationPinyin() + "-" + arriveStation.getStationPinyin());
			lss.makeCityKey();
			if(ssi.getCost_time().contains(":")){
				String[] ss = ssi.getCost_time().split(":");
				lss.setTakenTime(Long.parseLong(ss[0]) * 60 + Long.parseLong(ss[1]));
			}
			lss.setLineInfoId(lineInfo.getLineInfoId());
			lss.setStationStationId(lineStationStationDAO.insert(lss));
		}else{
			lss.setLineName(ssi.getTrain_id());
			lss.setDepartureStation(departStation);
			lss.setDepartureStationId(departStation.getStationId());
			lss.setDepartureTime(TimePriceUtil.getLongTime(ssi.getDepart_time()));
			
			lss.setArrivalStationId(arriveStation.getStationId());
			lss.setArrivalStation(arriveStation);
			lss.setArrivalTime(TimePriceUtil.getLongTime(ssi.getArrive_time()));
			
			lss.setDirect(null);
			lss.setStationKey(departStation.getStationPinyin() + "-" + arriveStation.getStationPinyin());
			lss.makeCityKey();
			if(ssi.getCost_time().contains(":")){
				String[] ss = ssi.getCost_time().split(":");
				lss.setTakenTime(Long.parseLong(ss[0]) * 60 + Long.parseLong(ss[1]));
			}
			lss.setLineInfoId(lineInfo.getLineInfoId());
			lineStationStationDAO.updateByPrimaryKey(lss);
		}
		return lss;
	}

	/**
	 * 添加子子项并添加时间价格表
	 * @param mpb 子子项类别
	 * @param ids 
	 * @param specDate 日期
	 * @param metaProduct 子子项类
	 * @param lss 站站信息
	 * @param ssi 
	 * @param si
	 * @param list
	 */
	private MetaProductBranch checkAndSaveMpb(MetaProductBranch mpb, Set<Long> ids, Date specDate, MetaProduct metaProduct, LineStationStation lss,
			Station2StationInfo ssi, SeatInfo si, List<MetaProductBranch> list, String type) {
		// TODO Auto-generated method stub
		if(mpb != null){//类别存在，做数据更新
			mpb = addMetaBranch(mpb, metaProduct, lss, ssi, si, 
					type.equals(TICKET_TYPE_ADULT)?Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301:Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302);
//			TimePrice price = metaTimePriceDAO.getMetaTimePriceByIdAndDate(mpb.getMetaBranchId(), specDate);
//			if(price==null){
//				//添加该库存入库
//				addMetaTimePrice(mpb, specDate, si);
//			}
			ids.add(mpb.getMetaBranchId());
		}else{//类别不存在
			//添加类别
			mpb = addMetaBranch(mpb, metaProduct, lss, ssi, si, 
					type.equals(TICKET_TYPE_ADULT)?Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301:Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302);
			//添加该库存入库
//			addMetaTimePrice(mpb,specDate,si);
			list.add(mpb);
			ids.add(mpb.getMetaBranchId());
		}
		return mpb;
	}

	private void checkAndSavePpb(ProdProductBranch ppb, Set<Long> prodIds, Date specDate, ProdProduct prodProduct, LineStationStation lss,
			Station2StationInfo ssi, SeatInfo si, MetaProductBranch mpb, List<ProdProductBranch> prodList, List<ProdTrainCache> prodTrainCaches,
			LineInfo lineInfo, String type) {
		// TODO Auto-generated method stub
		if(ppb!=null){
			ppb = addProdBranch(ppb, prodProduct,lss,ssi,si,false, 
					type.equals(TICKET_TYPE_ADULT)?Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301:Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302);
			TimePrice timePrice = prodTimePriceDAO.getProdTimePrice(ppb.getProductId(), ppb.getProdBranchId(), specDate);
			if(timePrice==null){
//				timePrice = addProdTimePrice(ppb,specDate,si);
				ProdTrainCache cache=initCache(prodProduct, ppb, lineInfo, lss,si.getPrice(), specDate);
				prodTrainCaches.add(cache);
			}
			prodIds.add(ppb.getProdBranchId());
			
			//测试使用
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("metaBranchId", mpb.getMetaBranchId());
			param.put("prodBranchId", ppb.getProdBranchId());
			List<ProdProductBranchItem> itemList=prodProductBranchItemDAO.selectByParam(param);
			if(itemList.isEmpty()){
				packMeta(ppb,mpb);
			}
			if(!prodProduct.isOnLine()) {
				prodProduct.setOnLine("true");
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("productId", prodProduct.getProductId());
				params.put("onLine", "true");
				params.put("productName", prodProduct.getProductName());
				params.put("onLineStr", prodProduct.getStrOnLine());
				prodProductService.markIsSellable(params, "SYSTEM");
			}
		}else{
			boolean firstBranch=prodProductBranchDAO.selectDefaultBranchByProductId(prodProduct.getProductId())==null;
			ppb = addProdBranch(ppb, prodProduct,lss,ssi,si,firstBranch, 
					type.equals(TICKET_TYPE_ADULT)?Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_301:Constant.TRAIN_TICKET_TYPE.TICKET_TYPE_302);
			if (firstBranch && !prodProduct.isOnLine()) {
				prodProduct.setOnLine("true");
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("productId", prodProduct.getProductId());
				params.put("onLine", "true");
				params.put("productName", prodProduct.getProductName());
				params.put("onLineStr", prodProduct.getStrOnLine());
				prodProductService.markIsSellable(params, "SYSTEM");
			}						
			prodList.add(ppb);						
			prodIds.add(ppb.getProdBranchId());
//			TimePrice timePrice = addProdTimePrice(ppb,specDate,si);
			ProdTrainCache cache=initCache(prodProduct, ppb, lineInfo, lss,si.getPrice(), specDate);
			prodTrainCaches.add(cache);
			packMeta(ppb,mpb);
		}
	}

	private MetaProductBranch addMetaBranch(MetaProductBranch mpb, MetaProduct product,LineStationStation lss,Station2StationInfo ssi,SeatInfo si, 
			Constant.TRAIN_TICKET_TYPE type){
		if(mpb == null){
			mpb = new MetaProductBranch();
			mpb.setAdditional("false");
			mpb.setAdultQuantity(1L);
			mpb.setBranchName(ssi.getDepart_station() + "-" + ssi.getArrive_station() + " " +si.getZhCatalog() + " " +type.getcName());
			mpb.setBranchType(String.valueOf(type.getCode()));  //车票类型，例如301（成人票）
			mpb.setChildQuantity(0L);
			mpb.setCreateTime(new Date());
			mpb.setMetaProductId(product.getMetaProductId());
			mpb.setSendFax("false");
			mpb.setStationStationId(lss.getStationStationId());
			mpb.setTotalDecrease("false");
			mpb.setValid("Y");
			mpb.setVirtual("false");
			mpb.setCheckStockHandle(SupplierProductInfo.HANDLE.TRAIN.name());
			mpb.setBerth(si.getCatalog());  //车票坐席，例如204（二等座）
			metaProductBranchDAO.insert(mpb);
		}else{
			mpb.setAdditional("false");
			mpb.setAdultQuantity(1L);
			mpb.setBranchName(ssi.getDepart_station() + "-" + ssi.getArrive_station() + " " +si.getZhCatalog() + " " +type.getcName());
			mpb.setBranchType(String.valueOf(type.getCode()));  //车票类型，例如301（成人票）
			mpb.setChildQuantity(0L);
			mpb.setCreateTime(new Date());
			mpb.setMetaProductId(product.getMetaProductId());
			mpb.setSendFax("false");
			mpb.setStationStationId(lss.getStationStationId());
			mpb.setTotalDecrease("false");
			mpb.setValid("Y");
			mpb.setVirtual("false");
			mpb.setCheckStockHandle(SupplierProductInfo.HANDLE.TRAIN.name());
			mpb.setBerth(si.getCatalog());  //车票坐席，例如204（二等座）
			metaProductBranchDAO.updateByPrimaryKey(mpb);  
		}
		return mpb;
	}
	
	private ProdProductBranch addProdBranch(ProdProductBranch ppb, ProdProduct product, LineStationStation lss, 
			Station2StationInfo ssi, SeatInfo si, boolean first, Constant.TRAIN_TICKET_TYPE type) {
		if(ppb == null){
			ppb = new ProdProductBranch();
			ppb.setAdditional("false");
			ppb.setAdultQuantity(1L);
			ppb.setBranchName(ssi.getDepart_station() + "-" + ssi.getArrive_station() + " " + si.getZhCatalog() + " " + type.getcName());
			ppb.setBranchType(String.valueOf(type.getCode()));  //车票类型，例如301（成人票）
			ppb.setChildQuantity(0L);
			ppb.setCreateTime(new Date());
			ppb.setProductId(product.getProductId());
			ppb.setStationStationId(lss.getStationStationId());
			ppb.setPriceUnit("张");
			ppb.setMinimum(1L);
			ppb.setMaximum(5L);
			ppb.setValid("Y");
			ppb.setVisible("true");
			ppb.setBerth(si.getCatalog());  //车票坐席，例如204（二等座）
//			if(first){
				ppb.setDefaultBranch("false");
//			}
			prodProductBranchDAO.insert(ppb);
		}else{
			ppb.setAdditional("false");
			ppb.setAdultQuantity(1L);
			ppb.setBranchName(ssi.getDepart_station() + "-" + ssi.getArrive_station() + " " + si.getZhCatalog() + " " + type.getcName());
			ppb.setBranchType(String.valueOf(type.getCode()));  //车票类型，例如301（成人票）
			ppb.setChildQuantity(0L);
			ppb.setCreateTime(new Date());
			ppb.setProductId(product.getProductId());
			ppb.setStationStationId(lss.getStationStationId());
			ppb.setPriceUnit("张");
			ppb.setMinimum(1L);
			ppb.setMaximum(5L);
			ppb.setValid("Y");
			ppb.setVisible("true");
			ppb.setBerth(si.getCatalog());  //车票坐席，例如204（二等座）
//			if(first){
				ppb.setDefaultBranch("false");
//			}
			prodProductBranchDAO.updateByPrimaryKey(ppb);
		}
		return ppb;
	}
	
	private TimePrice addProdTimePrice(ProdProductBranch ppb,Date specDate,long price){
		TimePrice timePrice = new TimePrice();
		timePrice.setProdBranchId(ppb.getProdBranchId());
		timePrice.setProductId(ppb.getProductId());
		timePrice.setPrice(price);
		timePrice.setSpecDate(specDate);
		timePrice.setAheadHour(36*60L);//定义为36小时
		timePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.FORBID.name());
		timePrice.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());
		
		prodTimePriceDAO.insert(timePrice);
		return timePrice;
	}
	/**
	 * 打包
	 * @param ppb
	 * @param mpb
	 */
	private void packMeta(ProdProductBranch ppb,MetaProductBranch mpb){		
		ProdProductBranchItem item = new ProdProductBranchItem();
		item.setCreateTime(new Date());
		item.setMetaProductId(mpb.getMetaProductId());
		item.setMetaBranchId(mpb.getMetaBranchId());
		item.setProdBranchId(ppb.getProdBranchId());
		item.setQuantity(1L);
		prodProductBranchItemDAO.insert(item);
	}
	
	private void addMetaTimePrice(final Long productId,final Long metaBranchId,Date specDate,long price){
		TimePrice metaTimePrice = new TimePrice();
		metaTimePrice.setSpecDate(specDate);
		metaTimePrice.setMetaBranchId(metaBranchId);
		if(productId==null){
			MetaProductBranch metaBranch = metaProductBranchDAO.selectBrachByPrimaryKey(metaBranchId);
			metaTimePrice.setProductId(metaBranch.getMetaProductId());
		}else{
			metaTimePrice.setProductId(productId);
		}
		metaTimePrice.setSettlementPrice(price);
		metaTimePrice.setMarketPrice(price);
		metaTimePrice.setDayStock(-1);
		metaTimePrice.setOverSale("false");
//		metaTimePrice.setZeroStockHour(bean.getZeroStockHour());
		metaTimePrice.setResourceConfirm("false");
		//早餐份数
//		metaTimePrice.setBreakfastCount(bean.getBreakfastCount());
		//建议售价
//		metaTimePrice.setSuggestPrice(bean.getSuggestPrice());
		//总库存数量
		metaTimePrice.setTotalDayStock(-1L);
		metaTimePrice.setAheadHour(36*60L);//定义为36小时
//		metaTimePrice.setCancelHour();
		metaTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());
		metaTimePriceDAO.insert(metaTimePrice);
	}
	
	public void setLineInfoDAO(LineInfoDAO lineInfoDAO) {
		this.lineInfoDAO = lineInfoDAO;
	}

	public void setLineStationDAO(LineStationDAO lineStationDAO) {
		this.lineStationDAO = lineStationDAO;
	}

	public void setLineStopsDAO(LineStopsDAO lineStopsDAO) {
		this.lineStopsDAO = lineStopsDAO;
	}
		
	private static final Log logger = LogFactory.getLog(ProdTrainServiceImpl.class);

	@Override
	public LineInfo selectLineInfoByLineName(
			String lineName) {
		return lineInfoDAO.selectByLineName(lineName);
	}
	
	@Override
	public LineInfo selectLineInfoByFullName(String fullName){
		return lineInfoDAO.selectLineInfoByFullName(fullName);
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setProdProductRelationDAO(
			ProdProductRelationDAO prodProductRelationDAO) {
		this.prodProductRelationDAO = prodProductRelationDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setLineStationStationDAO(LineStationStationDAO lineStationStationDAO) {
		this.lineStationStationDAO = lineStationStationDAO;
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	@Override
	public List<LineStationStation> selectStationStationByLineInfo(
			Long lineInfoId) {
		return lineStationStationDAO.selectByLineInfoId(lineInfoId);
	}

	@Override
	public List<LineStation> selectLineStationByParam(Map<String, Object> param) {
		if(param.get("_endRow")==null){
			param.put("_endRow", 20);
		}
		if(param.containsKey("citySearch")){
			return lineStationDAO.selectStationByParam(param);
		}
		return lineStationDAO.selectLineStationByParam(param);
	}
	
	@Override
	public List<LineStation> selectLineStationAll(Map<String, Object> param) {
		List<LineStation> result = lineStationDAO.selectStationByParam(param);
		return result;
	}

	@Override
	public List<LineStops> selectLineStopsByLineInfoId(Long lineInfoId,Date visitTime) {
		return lineStopsDAO.selectByLineInfoId(lineInfoId,visitTime,false);
	}

	@Override
	public List<LineStation> selectLineStationByLineInfoId(Long lineInfoId) {
		return lineStationDAO.selectLineStationByLineInfoId(lineInfoId);
	}

	@Override
	public com.lvmama.comm.bee.po.prod.LineInfo getLineInfo(Long lineInfoId) {
		return lineInfoDAO.selectByPrimaryKey(lineInfoId);
	}

	@Override
	public LineStation getLineStationByStationPinyin(String pinyin) {
		return lineStationDAO.getLineStationByStationPinyin(pinyin);
	}

	@Override
	public void addFetch(ProdTrainFetchInfo info) {
		prodTrainFetchInfoDAO.insert(info);
	}
	
	public void setProdTrainFetchInfoDAO(ProdTrainFetchInfoDAO prodTrainFetchInfoDAO) {
		this.prodTrainFetchInfoDAO = prodTrainFetchInfoDAO;
	}

	@Override
	public List<ProdTrainFetchInfo> selectFetchInfoList() {
		return prodTrainFetchInfoDAO.selectFetchInfoList();
	}

	@Override
	public void updateFetchInfoStatus(ProdTrainFetchInfo record) {
		ProdTrainFetchInfo info = new ProdTrainFetchInfo();
		info.setProdTrainFetchInfoId(record.getProdTrainFetchInfoId());
		info.setFetchStatus(ProdTrainFetchInfo.STATUS.COMPLETE.name());
		prodTrainFetchInfoDAO.updateByPrimaryKeySelective(info);
	}

	@Override
	public Long selectCountFetchInfo(String fetchKey, Date visitTime) {
		return prodTrainFetchInfoDAO.selectCountFetchInfo(fetchKey,visitTime);
	}

	public void setLineStopVersionDAO(LineStopVersionDAO lineStopVersionDAO) {
		this.lineStopVersionDAO = lineStopVersionDAO;
	}

	@Override
	public Map<String, LineStation> getLineStationByNames( List<String> stationNames) {
		// TODO Auto-generated method stub
		String name = null;
		for(String stationName : stationNames){
			if(name == null)
				name = "'" + stationName + "'";
			else
				name = name + ",'" + stationName + "'";
		}
		return lineStationDAO.getLineStationByNames(name);
	}

	@Override
	public LineStopVersion addLineStopsVersion(LineInfo lineInfo, Date specDate) {
		// TODO Auto-generated method stub
		LineStopVersion version = lineStopVersionDAO.selectByLineInfoIdValidTime(lineInfo.getLineInfoId(), specDate);
		if(version!=null){
			lineStopsDAO.deleteByVersionId(version.getLineStopVersionId());
		}else{
			version = new LineStopVersion();
			version.setStartValidTime(specDate);
			version.setLineInfoId(lineInfo.getLineInfoId());
			version.setCreateTime(new Date());
			lineStopVersionDAO.insert(version);
		}
		return version;
	}

	@Override
	public void addLineStopsInfo(LineStopsStationInfo station,
			LineStation lineStation, LineInfo lineInfo, LineStopVersion version) {
		// TODO Auto-generated method stub
		LineStops ls = new LineStops();
		ls.setArrivalTime(TimePriceUtil.getLongTime(station.getArrive_time()));
		ls.setDepartureTime(TimePriceUtil.getLongTime(station.getDepart_time()));
		
		ls.setLineInfoId(lineInfo.getLineInfoId());
		ls.setStationId(lineStation.getStationId());
		ls.setStopStep((long)station.getStation_no());
		if(station.getCost_time().contains(":")){
			String[] ss = station.getCost_time().split(":");
			ls.setTakeTime(Long.parseLong(ss[0]) * 60 + Long.parseLong(ss[1]));
		}
		ls.setLineStopVersionId(version.getLineStopVersionId());
		lineStopsDAO.insert(ls);
	}
	
	@Override
	public void createTimePrice(ProdProductBranch branch, Date visitTime,long price) {
		List<ProdProductBranchItem> list = prodProductBranchItemDAO.selectBranchItemByProdBranchId(branch.getProdBranchId());
		if(CollectionUtils.isNotEmpty(list)){
			for(ProdProductBranchItem item:list){
				MetaProduct mp = metaProductDAO.getMetaProductByPk(item.getMetaProductId());
				if(mp.isTrain()){
					TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(item.getMetaBranchId(), visitTime);
					if(timePrice==null){
						addMetaTimePrice(item.getMetaProductId(),item.getMetaBranchId(), visitTime, price);
					}
				}
			}
			if(prodTimePriceDAO.getProdTimePrice(branch.getProductId(), branch.getProdBranchId(), visitTime)==null){
				addProdTimePrice(branch, visitTime, price);
			}
		}
	}

	@Override
	public boolean insertStationInfos(List<StationInfo> stationInfos) {
		if(!stationInfos.isEmpty()){
			for(StationInfo station : stationInfos){
				LineStation lineStation = lineStationDAO.selectByNameAndPinyin(station.getStation_name(), null);
				if(station.getStatus() == TRAIN_STATUS_ADD || station.getStatus() == TRAIN_STATUS_UPDATE ||station.getStatus() == TRAIN_STATUS_NORMAL){
					String pinyin = station.getStation_pinyin().trim();
					Integer count = lineStationDAO.selectCountByPinyin(pinyin);
					if(lineStation == null){
						lineStation = new LineStation();
						lineStation.setStationName(station.getStation_name());
						lineStation.setStationPinyin(count > 0 ? pinyin + count : pinyin);
						lineStation.setStationPy(station.getStation_index());
						lineStationDAO.insert(lineStation);
					}else if(lineStation != null){
						lineStation.setStationPinyin(count > 0 ? pinyin + count : pinyin);
						lineStation.setStationPy(station.getStation_index());
						lineStationDAO.updateByPrimaryKey(lineStation);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean insertCityStationInfos(List<CityStationInfo> cityStationInfos) {
		if(!cityStationInfos.isEmpty()){
			for(CityStationInfo cityStation : cityStationInfos){
				//如果是新增或者更新,保存城市车站信息
				if(cityStation.getStatus() == TRAIN_STATUS_ADD || cityStation.getStatus() == TRAIN_STATUS_UPDATE||cityStation.getStatus()==TRAIN_STATUS_NORMAL){
					List<String> stationNames = cityStation.getStation_list(); 
					Map<String, LineStation> map = this.getLineStationByNames(stationNames);
					for(String stationName : stationNames){
						if(map != null){
							LineStation lineStation = map.get(stationName);
							if(lineStation != null){
								lineStation.setCityName(cityStation.getCity_name());
								lineStation.setCityPinyin(cityStation.getCity_pinyin());
								lineStationDAO.updateByPrimaryKey(lineStation);
							}
						}
					}
				}
			}
		}
		return true;
	}
	

	@Override
	public List<Long> insertLineInfos(
			List<com.lvmama.comm.vo.train.product.LineInfo> lineInfos,
			String requestDate, TrainParamInfo trainParamInfo) {
		List<Long> result = null;
		if(!lineInfos.isEmpty()){
			for(com.lvmama.comm.vo.train.product.LineInfo line : lineInfos){
				if(line.getStatus() == TRAIN_STATUS_ADD || line.getStatus() == TRAIN_STATUS_UPDATE ||line.getStatus()==TRAIN_STATUS_NORMAL){
					com.lvmama.comm.bee.po.prod.LineInfo lineInfo = this.selectLineInfoByLineName(line.getTrain_id());
					if(lineInfo == null){
						lineInfo = new com.lvmama.comm.bee.po.prod.LineInfo();
					}
					//获取起始车站信息
					LineStation departStation = this.getLineStationByName(line.getOrigin_station());
					if(departStation == null) {
						logger.info("发车车站不存在："+line.getOrigin_station());
						continue;
					}
					lineInfo.setStartStation(departStation);
					lineInfo.setStartStationId(departStation.getStationId());
					
					//获取到达车站信息
					LineStation arriveStation = this.getLineStationByName(line.getTerminal_station());
					if(arriveStation == null){ 
						logger.info("到达车站不存在："+line.getTerminal_station());
						continue;
					}
					lineInfo.setEndStation(arriveStation);
					lineInfo.setEndStationId(arriveStation.getStationId());
					
					//设置起始时间、到达时间、天数
					lineInfo.setFullName(line.getTrain_id());
					lineInfo.setCategory(String.valueOf(line.getTrain_type()));
					lineInfo.setStartTime(TimePriceUtil.getLongTime(line.getDepart_time()));
					lineInfo.setEndTime(TimePriceUtil.getLongTime(line.getArrive_time()));
					if(line.getCost_time() != null && line.getCost_time().indexOf(":") > 0)
						lineInfo.setTakeDays(Long.parseLong(line.getCost_time().split(":")[0]) / 24);
					
					ResultHandleT<Long> rh = this.updateTrainLineInfo(lineInfo, requestDate == null?null:DateUtil.toDate(requestDate, "yyyy-MM-dd"), trainParamInfo);
					if(rh.isSuccess()){
						if(rh.getReturnContent()!=null){
							if(result == null){
								result = new ArrayList<Long>();
							}
							result.add(rh.getReturnContent());
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public LineStation getLineStationByName(String stationName) {
		// TODO Auto-generated method stub
		return lineStationDAO.selectByNameAndPinyin(stationName, null);
	}

	@Override
	public boolean insertLineStopInfo(List<LineStopsInfo> lineStopsInfos,
			String requestDate) {
		if(!initMapFlag){
			initTrainCacheMap(true);
		}
		if(!lineStopsInfos.isEmpty()){
			Date specDate = DateUtil.toDate(requestDate, "yyyy-MM-dd");
			for(LineStopsInfo lineStop : lineStopsInfos){
				com.lvmama.comm.bee.po.prod.LineInfo lineInfo = this.selectLineInfoByLineName(lineStop.getTrain_id());
				if(lineInfo == null) {
					logger.info("insertLineStopInfo lineInfo don't exists:"+lineStop.getTrain_id());
					continue;
				}
				if(!hasSameLineStops(lineStop, lineInfo, specDate)){
					LineStopVersion version = this.addLineStopsVersion(lineInfo, specDate);
					for(LineStopsStationInfo station : lineStop.getPark_station()){
						LineStation lineStation = lineStationMap.get(station.getStation_name());
						if(lineStation==null){
							logger.info("line station is null:"+station.getStation_name());
						}
						this.addLineStopsInfo(station, lineStation, lineInfo, version);
					}
				}else{
					logger.info("车次："+lineInfo.getFullName()+","+requestDate+",存在相同的车次表不需要添加");
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断一个经停是否与当前有效的经停一置
	 * @param lineStop
	 * @param lineInfo
	 * @param visitTime
	 * @return false,不一置，true表示一置
	 */
	private boolean hasSameLineStops(LineStopsInfo lineStop,com.lvmama.comm.bee.po.prod.LineInfo lineInfo,Date visitTime){
		List<LineStops> list = selectLineStopsByLineInfoId(lineInfo.getLineInfoId(),visitTime);
		if(CollectionUtils.isEmpty(list)){
			return false;
		}
		if(lineStop.getPark_station().size()!=list.size()){
			return false;
		}
		
		for(LineStopsStationInfo lssi:lineStop.getPark_station()){
			LineStation ls=lineStationMap.get(lssi.getStation_name());
			LineStopsComparator sc = new LineStopsComparator(ls.getStationId(), lssi);
			Object obj = CollectionUtils.find(list, sc);
			if(obj == null){
				return false;
			}
		}
		return true;
	}
	

	@Override
	public List<Map<String, Object>> insertTicketPriceInfos(
			List<Station2StationInfo> station2StationInfos, String requestDate) {
		synchronized (this) {
			if(!initMapFlag){
				initTrainCacheMap(true);
			}
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(station2StationInfos == null) return result;
		if(station2StationInfos.isEmpty()) return result;
		for(Station2StationInfo ssi : station2StationInfos){
			if(ssi != null){
//				Date date = new Date();
//				System.out.println("start updateStation");
				Map<String, Object> map = this.updateStation2StationInfo(ssi, requestDate);
//				System.out.println("end updateStation:"+(new Date().getTime()-date.getTime()));
				if(map != null){
					result.add(map);
				}
			}
		}
		return result;
	}
	
	@Override
	public List<LineInfo> selectCheZhan(Map<String, Object> param){
		return this.lineInfoDAO.selectCheZhan(param);
	}
	
	@Override
	public List<LineInfo> selectAllLineInfo(Map<String, Object> param){
		return this.lineInfoDAO.selectAll(param);
	}
	
	@Override
	public List<LineStops> selectCheZhanStops(Map<String, Object> param){
		return this.lineStopsDAO.selectCheZhanStops(param);
	}
	
	@Override
	public List<LineStops> selectZhanZhanStops(Map<String, Object> param){
		return this.lineStopsDAO.selectZhanZhanStops(param);
	}
	
	@Override
	public List<LineStationStation> selectLineStationStationByPinyinKey(Map<String, Object> param){
		return this.lineStationStationDAO.selectLineStationStationByPinyinKey(param);
	}
	
	@Override
	public List<LineStops> selectLineStopsCheci(Map<String, Object> param){
		return this.lineStopsDAO.selectLineStopsCheci(param);
	}
	
	@Override
	public List<LineStation> selectLineStationByChezhan(Map<String, Object> param){
		return this.lineStationDAO.selectLineStationByChezhan(param);
	}
	
	private boolean initMapFlag=false;
}
