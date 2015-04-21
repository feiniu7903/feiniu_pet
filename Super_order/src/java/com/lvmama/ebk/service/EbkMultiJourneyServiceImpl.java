package com.lvmama.ebk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.ebk.dao.EbkMultiJourneyDAO;
import com.lvmama.ebk.dao.EbkProdJourneyDAO;
import com.lvmama.ebk.dao.EbkProdTimePriceDAO;

public class EbkMultiJourneyServiceImpl implements EbkMultiJourneyService {

	private EbkMultiJourneyDAO ebkMultiJourneyDAO;
	private EbkProdJourneyDAO ebkProdJourneyDAO;
	private EbkProdTimePriceDAO ebkProdTimePriceDAO;

	@Override
	public Long insert(EbkMultiJourney record) {
		Long id = ebkMultiJourneyDAO.insert(record);
		return id;
	}

	public EbkMultiJourneyDAO getEbkMultiJourneyDAO() {
		return ebkMultiJourneyDAO;
	}

	public void setEbkMultiJourneyDAO(EbkMultiJourneyDAO ebkMultiJourneyDAO) {
		this.ebkMultiJourneyDAO = ebkMultiJourneyDAO;
	}

	public EbkProdJourneyDAO getEbkProdJourneyDAO() {
		return ebkProdJourneyDAO;
	}

	public void setEbkProdJourneyDAO(EbkProdJourneyDAO ebkProdJourneyDAO) {
		this.ebkProdJourneyDAO = ebkProdJourneyDAO;
	}	

	public EbkProdTimePriceDAO getEbkProdTimePriceDAO() {
		return ebkProdTimePriceDAO;
	}

	public void setEbkProdTimePriceDAO(EbkProdTimePriceDAO ebkProdTimePriceDAO) {
		this.ebkProdTimePriceDAO = ebkProdTimePriceDAO;
	}

	@Override
	public List<EbkMultiJourney> queryMultiJourneyByParams(
			Map<String, Object> params) {
		return ebkMultiJourneyDAO.queryMultiJourneyByParams(params);
	}

	@Override
	public void update(EbkMultiJourney record) {
		ebkMultiJourneyDAO.update(record);
	}

	@Override
	public EbkMultiJourney selectByPrimaryKey(Long multiJourneyId) {
		return ebkMultiJourneyDAO.selectByPrimaryKey(multiJourneyId);
	}

	@Override
	public List<EbkMultiJourney> getAllMultiJourneyDetailByProductId(
			Long productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		List<EbkMultiJourney> list = queryMultiJourneyByParams(params);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				EbkMultiJourney vmj = list.get(i);
				List<EbkProdJourney> viewJourneyList = ebkProdJourneyDAO
						.getEbkProdJourneyByMultiJourneyId(vmj
								.getMultiJourneyId());
				vmj.setViewJourneyList(viewJourneyList);
			}
		}
		return list;
	}

	@Override
	public void deleteTimePriceByMultiJourneyId(Long productId,
			Long multiJourneyId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("multiJourneyId", multiJourneyId);
		ebkProdTimePriceDAO.delete(params);
	}
}
