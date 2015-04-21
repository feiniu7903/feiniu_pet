/**
 * 
 */
package com.lvmama.pet.search.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.search.vo.TrainSearchVO;
import com.lvmama.pet.search.dao.ProdTrainCacheDAO;

/**
 * @author yangbin
 *
 */
public class ProdTrainCacheServiceImpl implements ProdTrainCacheService {

	private ProdTrainCacheDAO prodTrainCacheDAO;
	private final static Log LOG = LogFactory.getLog(ProdTrainCacheServiceImpl.class);
	@Override
	public void updateCache(List<ProdTrainCache> cacheList) {
		for(ProdTrainCache cache:cacheList){
			try{
				if(prodTrainCacheDAO.selectCount(cache)==0){
					prodTrainCacheDAO.insert(cache);
				}else{
					prodTrainCacheDAO.updatePrice(cache);
				}
			}catch(Exception ex){
				
			}
		}
	}

	@Override
	public void markSoldout(final List<Long> prodBranchIds,Date date) {
		for(Long prodBranchId:prodBranchIds){
			prodTrainCacheDAO.markSoldout(prodBranchId,date);
		}
	}

	public void setProdTrainCacheDAO(ProdTrainCacheDAO prodTrainCacheDAO) {
		this.prodTrainCacheDAO = prodTrainCacheDAO;
	}

	@Override
	public List<ProdTrainCache> selectCacheList(TrainSearchVO trainSearchVO) {
		if(trainSearchVO.isKeyEmpty()||trainSearchVO.getDateDate()==null){
			return Collections.emptyList();
		}
		Map<String,Object> param = new HashMap<String, Object>();
		if(trainSearchVO.getDeparture().equalsIgnoreCase(trainSearchVO.getFromCityPinyin())) {
			param.put("departureCity", trainSearchVO.getDeparture());
		} else {
			param.put("departureStation", trainSearchVO.getDeparture());
		}
		if(trainSearchVO.getArrival().equalsIgnoreCase(trainSearchVO.getToCityPinyin())) {
			param.put("arrivalCity", trainSearchVO.getArrival());
		} else {
			param.put("arrivalStation", trainSearchVO.getArrival());
		}
		param.put("visitTime",trainSearchVO.getDateDate());
		if(StringUtils.isNotEmpty(trainSearchVO.getLineName())){
			param.put("lineName", trainSearchVO.getLineName());
		}
		if(trainSearchVO.getProductId()!=null){
			param.put("productId", trainSearchVO.getProductId());
		}
		return prodTrainCacheDAO.selectByParam(param);
	}

	@Override
	public void removeNotValidTrains(Date date) {
		prodTrainCacheDAO.removeNotValidTrains(date);
	}

	@Override
	public boolean existKey(String stationKey, Date visitTime) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("stationKey", stationKey);
		param.put("visitTime", visitTime);
		return prodTrainCacheDAO.selectCountCache(param)>0;
	}

	@Override
	public ProdTrainCache get(Long prodBranchId, Date visitTime) {
		return prodTrainCacheDAO.selectByBranchIdAndVisitTime(prodBranchId,visitTime);
	}

	@Override
	public void copyDataToNewDay(Date date) {
		Date newDate = DateUtils.addDays(date, 1);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("visitTime", newDate);
		LOG.info("begin copy train data to:"+newDate);
		if(prodTrainCacheDAO.selectCountCache(map)==0){
			prodTrainCacheDAO.copyData(date, newDate);
		}else{
			LOG.warn("prod_train_cache exists date:"+newDate);
		}
	}

	@Override
	public ProdTrainCache getTrainCache(ProdTrainCache cache) {
		// TODO Auto-generated method stub
		return prodTrainCacheDAO.selectByCache(cache);
	}

	@Override
	public long queryCount(Date date) {
		return prodTrainCacheDAO.selectCountByDate(date);
	}

	@Override
	public ProdTrainCache queryLastCacheByDate(Date date) {
		return prodTrainCacheDAO.selectLastCache(date);
	}


}
