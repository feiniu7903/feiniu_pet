/**
 * 
 */
package com.lvmama.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.service.TrainDataSyncService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.bee.vo.train.TrainParamInfo;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.CityStationReqVo;
import com.lvmama.comm.vo.train.product.CityStationRspVo;
import com.lvmama.comm.vo.train.product.LineInfo;
import com.lvmama.comm.vo.train.product.LineReqVo;
import com.lvmama.comm.vo.train.product.LineRspVo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsReqVo;
import com.lvmama.comm.vo.train.product.LineStopsRspVo;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.Station2StationReqvo;
import com.lvmama.comm.vo.train.product.Station2StationRspVo;
import com.lvmama.comm.vo.train.product.StationInfo;
import com.lvmama.comm.vo.train.product.StationReqVo;
import com.lvmama.comm.vo.train.product.StationRspVo;
import com.lvmama.train.PinyinUtil;
import com.lvmama.train.service.TrainClient;
import com.lvmama.train.service.request.CityStationQueryRequest;
import com.lvmama.train.service.request.LineInfoQueryRequest;
import com.lvmama.train.service.request.LineStopsRequest;
import com.lvmama.train.service.request.Station2StationListRequest;
import com.lvmama.train.service.request.StationQueryRequest;
import com.lvmama.train.service.response.CityStationQueryResponse;
import com.lvmama.train.service.response.LineInfoQueryResponse;
import com.lvmama.train.service.response.LineStopsResponse;
import com.lvmama.train.service.response.Station2StationListResponse;
import com.lvmama.train.service.response.StationQueryResponse;

/**
 * @author yangbin
 *
 */
public class TrainDataSyncServiceImpl implements TrainDataSyncService,InitializingBean{

	private ProdTrainService prodTrainService;
	//private TopicMessageProducer productMessageProducer;
	private Long settlmentTargetId;
	private Long performTargetId;
	private Long bcertificateTargetId;
	private Long supplierId;
	private Long prodInsuranceId;
	private String userName;
	private BCertificateTargetService bCertificateTargetService;
	private PerformTargetService performTargetService;
	private SettlementTargetService settlementTargetService;
	private ProdTrainCacheService prodTrainCacheService;
	private PermUserService permUserService;
	private TrainParamInfo trainParamInfo;
	private TrainClient client = new TrainClient();
	private static final int TRAIN_STATUS_ADD = 1;
	private static final int TRAIN_STATUS_UPDATE = 2;
	private static final int TRAIN_STATUS_NORMAL=0;//表示未更新
	private static final String REQUEST_TYPE = "all";
	
//	@Override
//	public boolean syncCity(String departureCity, String arrivalCity, Date visitTime) {
//		String key=departureCity+"-"+arrivalCity+"_"+DateUtil.formatDate(visitTime, "yyyy-MM-dd");
//		try{
//			if(SynchronizedLock.isOnDoingMemCached(key)){
//				return false;
//			}
//			//如果已经存在，就给前台返回过会再查询的操作
//			if(prodTrainService.selectCountFetchInfo(departureCity+"-"+arrivalCity,visitTime)>0){
//				return false;
//			}
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("cityPinyin", departureCity);
//			List<LineStation> departureStationList = prodTrainService.selectLineStationByParam(map);
//			
//			map.put("cityPinyin", arrivalCity);
//			List<LineStation> arrivalStationList = prodTrainService.selectLineStationByParam(map);
//			
//			for(LineStation departure:departureStationList){
//				for(LineStation arrival:arrivalStationList){
//					sync(departure.getStationPinyin(), arrival.getStationPinyin(), visitTime);
//				}
//			}
//			ProdTrainFetchInfo info = new ProdTrainFetchInfo();
//			info.setFetchCatalog("503");
//			info.setFetchKey(departureCity+"-"+arrivalCity);
//			info.setFetchStatus(ProdTrainFetchInfo.STATUS.COMPLETE.name());
//			info.setCreateTime(new Date());
//			info.setVisitTime(visitTime);
//			prodTrainService.addFetch(info);
//			return true;
//		}finally {
//			SynchronizedLock.releaseMemCached(key);
//		}
//	}

	@Override
	public boolean syncStationInfo(String requestType, String requestDate, String stationName) {
		try {
			StationReqVo vo = new StationReqVo();
			vo.setRequestType(requestType);
			vo.setRequestDate(requestDate);
			vo.setStationName(stationName);
			
			StationQueryRequest request = new StationQueryRequest(vo);
			StationQueryResponse response = client.execute(request);
			if(response.isSuccess()){
				List<StationInfo> stationInfos = ((StationRspVo)response.getRsp().getVo()).getStationInfos();
				return this.insertStationInfos(stationInfos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean insertStationInfos(List<StationInfo> stationInfos){
		return prodTrainService.insertStationInfos(stationInfos);
	}

	@Override
	public boolean syncCityStationInfo(String requestType, String requestDate, String cityName){
		// TODO Auto-generated method stub
		CityStationReqVo vo = new CityStationReqVo();
		vo.setRequestType(requestType);
		vo.setRequestDate(requestDate);
		vo.setCityName(cityName);
		
		CityStationQueryRequest request = new CityStationQueryRequest(vo);
		CityStationQueryResponse response = client.execute(request);
		if(response.isSuccess()){
			List<CityStationInfo> cityStationInfos = ((CityStationRspVo)response.getRsp().getVo()).getCityStationInfos();
			if(!cityStationInfos.isEmpty()){
				for(CityStationInfo cityStation : cityStationInfos){
					//添加城市拼音信息
					cityStation.setCity_pinyin(PinyinUtil.getMixPinyin(cityStation.getCity_name()));
					//如果是新增或者更新,保存城市车站信息
					if(cityStation.getStatus() == TRAIN_STATUS_ADD || cityStation.getStatus() == TRAIN_STATUS_UPDATE){
						List<String> stationNames = cityStation.getStation_list(); 
						Map<String, LineStation> map = prodTrainService.getLineStationByNames(stationNames);
						for(String stationName : stationNames){
							LineStation lineStation = map.get(stationName);
							//如果没有该Station信息，则先去同步
							if(lineStation == null){
								boolean isSuccess = syncStationInfo(REQUEST_TYPE, requestDate, stationName);
								if(!isSuccess){
									cityStationInfos.remove(cityStation);
								}
							}
						}
					}
				}
			}
			return insertCityStationInfos(cityStationInfos);
		}
		return false;
	}
	
	@Override
	public boolean insertCityStationInfos(List<CityStationInfo> cityStationInfos){
		return prodTrainService.insertCityStationInfos(cityStationInfos);
	}

	@Override
	public boolean syncLineInfo(String requestType, String requestDate, String trainId) {
		// TODO Auto-generated method stub
		LineReqVo vo = new LineReqVo();
		vo.setRequestType(requestType);
		vo.setRequestDate(requestDate);
		vo.setTrainId(trainId);
		
		LineInfoQueryRequest request = new LineInfoQueryRequest(vo);
		LineInfoQueryResponse response = client.execute(request);
		if(response.isSuccess()){
			List<LineInfo> lineInfos = ((LineRspVo)response.getRsp().getVo()).getLineInfos();
			if(!lineInfos.isEmpty()){
				for(LineInfo line : lineInfos){
					if(line.getStatus() == TRAIN_STATUS_ADD || line.getStatus() == TRAIN_STATUS_UPDATE||line.getStatus() == TRAIN_STATUS_NORMAL){
						List<String> stationNames = new ArrayList<String>();
						stationNames.add(line.getOrigin_station());
						stationNames.add(line.getTerminal_station());
						
						Map<String, LineStation> map = prodTrainService.getLineStationByNames(stationNames);
						for(String stationName : stationNames){
							LineStation lineStation = map.get(stationName);
							//如果没有该Station信息，则先去同步
							if(lineStation == null){
								boolean isSuccess = syncStationInfo(REQUEST_TYPE, requestDate, stationName);
								if(!isSuccess){
									lineInfos.remove(line);
								}
							}
						}
					}
				}
			}
			return this.insertLineInfos(lineInfos, requestDate, trainParamInfo);
		}
		return false;
	}
	
	private boolean insertLineInfos(List<LineInfo> lineInfos, String requestDate, TrainParamInfo trainParamInfo) {
		// TODO Auto-generated method stub
		List<Long> metaProductIds = prodTrainService.insertLineInfos(lineInfos, requestDate, trainParamInfo);
		//新添加的数据，添加采购打包关系
		if(metaProductIds != null && metaProductIds.size() > 0){
			for(Long metaProductId : metaProductIds){
				if(metaProductId != null && metaProductId != 0){
					addMetaTarget(metaProductId);
				}
			}
		}
		return true;
	}

	@Override
	public boolean insertLineInfos(List<LineInfo> lineInfos, String requestDate){
		return insertLineInfos(lineInfos, requestDate, trainParamInfo);
	}
	
	private void addMetaTarget(final Long metaProductId){
		Integer count=bCertificateTargetService.selectByMetaProductId(metaProductId);
		if(count==null||count==0){
			MetaBCertificate bcertificate= new MetaBCertificate();
			bcertificate.setTargetId(bcertificateTargetId);
			bcertificate.setMetaProductId(metaProductId);
			bCertificateTargetService.insertSuperMetaBCertificate(bcertificate, "SYSTEM");
		}
		count = settlementTargetService.selectByMetaProductId(metaProductId);
		if(count==null||count==0){
			MetaSettlement settlement = new MetaSettlement();
			settlement.setMetaProductId(metaProductId);
			settlement.setTargetId(settlmentTargetId);
			settlementTargetService.addMetaRelation(settlement, "SYSTEM");
		}
		count = performTargetService.selectByMetaProductId(metaProductId);
		if(count==null||count==0){
			MetaPerform perform = new MetaPerform();
			perform.setMetaProductId(metaProductId);
			perform.setTargetId(performTargetId);
			performTargetService.addMetaRelation(perform, "SYSTEM");		
		}
	}

	@Override
	public boolean syncLineStopsInfo(String requestType, String requestDate, String trainId) {
		// TODO Auto-generated method stub
		LineStopsReqVo vo = new LineStopsReqVo();
		vo.setRequestType(requestType);
		vo.setRequestDate(requestDate);
		vo.setTrainId(trainId);
		
		LineStopsRequest request = new LineStopsRequest(vo);
		LineStopsResponse response = client.execute(request);
		if(response.isSuccess()){
			List<LineStopsInfo> lineStopsInfos = ((LineStopsRspVo)response.getRsp().getVo()).getLineStopsInfos();
			if(!lineStopsInfos.isEmpty()){
				for(LineStopsInfo lineStop : lineStopsInfos){
					com.lvmama.comm.bee.po.prod.LineInfo lineInfo = prodTrainService.selectLineInfoByLineName(lineStop.getTrain_id());
					if(lineInfo == null){
						boolean isSuccess = syncLineInfo(REQUEST_TYPE, requestDate, lineStop.getTrain_id());
						if(!isSuccess){
							lineStopsInfos.remove(lineStop);
							continue;
						} 
					}
					List<String> stationNames = new ArrayList<String>();
					for(LineStopsStationInfo station : lineStop.getPark_station()){
						stationNames.add(station.getStation_name());
					}
					Map<String, LineStation> map = prodTrainService.getLineStationByNames(stationNames);
					for(String stationName : stationNames){
						LineStation lineStation = map.get(stationName);
						//如果没有该Station信息，则先去同步
						if(lineStation == null){
							boolean isSuccess = syncStationInfo(REQUEST_TYPE, requestDate, stationName);
							if(!isSuccess){
								lineStopsInfos.remove(lineStop);
							}
						}
					}
				}
			}
			return this.insertLineStopInfos(lineStopsInfos, requestDate);
		}
		return false;
	}
	
	@Override
	public boolean insertLineStopInfos(List<LineStopsInfo> lineStopsInfos, String requestDate){
		return prodTrainService.insertLineStopInfo(lineStopsInfos, requestDate);
	}

	@Override
	public boolean syncTicketPriceInfo(String requestType, String requestDate, String departSation, 
			String arriveStation, String trainId) {
		// TODO Auto-generated method stub
		Station2StationReqvo vo = new Station2StationReqvo();
		vo.setRequestType(requestType);
		vo.setRequestDate(requestDate);
		vo.setDepartStation(departSation);
		vo.setArriveStation(arriveStation);
		vo.setTrainId(trainId);
		
		Station2StationListRequest request = new Station2StationListRequest(vo);
		Station2StationListResponse response = client.execute(request);
		if(response.isSuccess()){
			List<Station2StationInfo> station2StationInfos = ((Station2StationRspVo)response.getRsp().getVo()).getStation2StationInfos();
			if(CollectionUtils.isNotEmpty(station2StationInfos)){
				final int SPACE=500;
				if(station2StationInfos.size()<=SPACE){
					addLineInfo(station2StationInfos, requestDate);
					return this.insertTicketPriceInfos(station2StationInfos, requestDate);
				}else{
					int total=station2StationInfos.size();
					int fromIndex=0;
					int toIndex=SPACE;
					while(true){
						if(toIndex>=total){
							toIndex=total;
						}
						List<Station2StationInfo> subList = station2StationInfos.subList(fromIndex, toIndex);
						addLineInfo(subList, requestDate);
						this.insertTicketPriceInfos(subList, requestDate);
						fromIndex = toIndex;
						toIndex+=SPACE;
						if(fromIndex>=total){
							break;
						}
					}
					return true;
				}
			}
			
		}
		return false;
	}
	
	public boolean insertTicketPriceInfos(List<Station2StationInfo> station2StationInfos, String requestDate){
		if(station2StationInfos == null) return true;
		
		Date visitTime = DateUtil.toDate(requestDate, "yyyy-MM-dd");

		if(!station2StationInfos.isEmpty()){
			Date date = new Date();
			System.out.println("begin selectTicket");
			List<Map<String,Object>> caches = prodTrainService.insertTicketPriceInfos(station2StationInfos, requestDate);
			System.out.println("middle selectTicket:"+(new Date().getTime()-date.getTime()));
			for(Map<String, Object> cc : caches){
				handleCached(cc,visitTime,false);
			}
			System.out.println("end selectTicket:"+(new Date().getTime()-date.getTime()));
		}
		return true;
	}
	
	private void addLineInfo(List<Station2StationInfo> list, String requestDate){
		boolean flag=false;
		for(Station2StationInfo ssi:list){
			if(prodTrainService.selectLineInfoByLineName(ssi.getTrain_id()) == null){
				syncLineInfo(REQUEST_TYPE, requestDate, ssi.getTrain_id());
				flag=true;
			}
			if(prodTrainService.getLineStationByName(ssi.getDepart_station()) == null){
				syncStationInfo(REQUEST_TYPE, requestDate, ssi.getDepart_station());
				flag=true;
			}
			if(prodTrainService.getLineStationByName(ssi.getArrive_station()) == null){
				syncStationInfo(REQUEST_TYPE, requestDate, ssi.getArrive_station());
				flag=true;
			}
		}
		if(flag){
			prodTrainService.initTrainCacheMap(false);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleCached(Map<String,Object> map,Date date,boolean haveCachePool){
		List<ProdTrainCache> list = (List<ProdTrainCache>)map.get("TRAIN_ADD_CACHE");
		if(CollectionUtils.isNotEmpty(list)){
			try{
				prodTrainCacheService.updateCache(list);
			}catch(Exception ex){
				ex.printStackTrace();
			}
//			if(!haveCachePool){
//				for(ProdTrainCache cache:list){
//					TrainCacheManager.getInstance().set(TrainKey.newTrainKey(cache), cache);
//				}
//			}
		}
		List<Long> soldoutList = (List<Long>)map.get("TRAIN_SOLDOUT_CACHE");
		if(CollectionUtils.isNotEmpty(soldoutList)){
			try{
				prodTrainCacheService.markSoldout(soldoutList,date);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	public void setSettlmentTargetId(Long settlmentTargetId) {
		this.settlmentTargetId = settlmentTargetId;
	}

	public void setPerformTargetId(Long performTargetId) {
		this.performTargetId = performTargetId;
	}

	public void setBcertificateTargetId(Long bcertificateTargetId) {
		this.bcertificateTargetId = bcertificateTargetId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setProdInsuranceId(Long prodInsuranceId) {
		this.prodInsuranceId = prodInsuranceId;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
		trainParamInfo = new TrainParamInfo();
		/*
		PermUser user = permUserService.getPermUserByUserName(userName);
		trainParamInfo.setBcertificateTargetId(bcertificateTargetId);
		trainParamInfo.setOrgId(user.getDepartmentId());
		trainParamInfo.setPerformTargetId(performTargetId);
		trainParamInfo.setPermUserId(user.getUserId());
		trainParamInfo.setProdInsuranceId(prodInsuranceId);
		trainParamInfo.setSettlmentTargetId(settlmentTargetId);
		trainParamInfo.setSupplierId(supplierId);*/
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}
	
}
