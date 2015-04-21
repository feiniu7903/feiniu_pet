package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.dao.ViewJourneyDAO;
import com.lvmama.prd.dao.ViewMultiJourneyDAO;

public class ViewMultiJourneyServiceImpl implements ViewMultiJourneyService {
	
	private ViewMultiJourneyDAO viewMultiJourneyDAO;
	
	private ViewJourneyDAO viewJourneyDAO;
	
	private ComLogDAO comLogDAO;
	
	private ProdTimePriceDAO prodTimePriceDAO;

	public void setViewMultiJourneyDAO(ViewMultiJourneyDAO viewMultiJourneyDAO) {
		this.viewMultiJourneyDAO = viewMultiJourneyDAO;
	}

	@Override
	public Long insert(ViewMultiJourney record, String operatorName) {
		Long id = viewMultiJourneyDAO.insert(record);
		comLogDAO.insert("VIEW_MULTI_JOURNEY",record.getProductId(),id, operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertMultiJourney.name(), "创建行程描述","名称为[ "+record.getJourneyName()+" ]的多行程", "PROD_PRODUCT");
		return id;
	}

	@Override
	public Integer selectRowCount(Map<String, Object> params) {
		return viewMultiJourneyDAO.selectRowCount(params);
	}

	@Override
	public List<ViewMultiJourney> queryMultiJourneyByParams(
			Map<String, Object> params) {
		return viewMultiJourneyDAO.queryMultiJourneyByParams(params);
	}

	@Override
	public void update(ViewMultiJourney record, String operatorName) {
		ViewMultiJourney vmj = viewMultiJourneyDAO.selectByPrimaryKey(record.getMultiJourneyId());
		String content = "";
		if(vmj.getJourneyName() == null || !vmj.getJourneyName().equals(record.getJourneyName())) {
			content += "行程名称由[" + vmj.getJourneyName() + "]修改为[" + record.getJourneyName() + "]";
		}
		if(vmj.getDays() == null || !vmj.getDays().toString().equals(record.getDays().toString())) {
			content += "   行程天数由[" + vmj.getDays() + "]修改为[" + record.getDays() + "]";
		}
		if(vmj.getNights() == null ||!vmj.getNights().toString().equals(record.getNights().toString())) {
			content += "   晚数由[" + vmj.getNights() + "]修改为[" + record.getNights() + "]";
		}
		if(!vmj.getValid().equals(record.getValid())) {
			content += "   [" + vmj.getZhValid() + "]修改为[" + record.getZhValid() + "]";
		}
		if(vmj.getContent() == null || !vmj.getContent().equals(record.getContent())) {
			content += "   内容由[" + vmj.getContent() + "]修改为[" + record.getContent() + "]";
		}
		viewMultiJourneyDAO.update(record);
		comLogDAO.insert("VIEW_MULTI_JOURNEY",record.getProductId(),record.getMultiJourneyId(), operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.updateMultiJourney.name(), "修改行程描述",content, "PROD_PRODUCT");
	}

	@Override
	public ViewMultiJourney selectByPrimaryKey(Long multiJourneyId) {
		return viewMultiJourneyDAO.selectByPrimaryKey(multiJourneyId);
	}

	@Override
	public List<ViewMultiJourney> getAllMultiJourneyDetailByProductId(Long productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		List<ViewMultiJourney> list = queryMultiJourneyByParams(params);
		if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				ViewMultiJourney vmj = list.get(i);
				List<ViewJourney> viewJourneyList = viewJourneyDAO.getViewJourneyByMultiJourneyId(vmj.getMultiJourneyId());
				vmj.setViewJourneyList(viewJourneyList);
			}
		}
		return list;
	}

	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public void deleteTimePriceByMultiJourneyId(Long productId, Long multiJourneyId) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("productId", productId);
		params.put("multiJourneyId", multiJourneyId);
		prodTimePriceDAO.deleteTimePriceByMultiJourneyId(params);
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	@Override
	public List<Long> getBranchIdsByMultiJourneyId(Long productId, Long multiJourneyId) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("productId", productId);
		params.put("multiJourneyId", multiJourneyId);
		return prodTimePriceDAO.getBranchIdsByMultiJourneyId(params);
	}

	@Override
	public int delete(Long multiJourneyId) {
		return viewMultiJourneyDAO.deleteByPrimaryKey(multiJourneyId);
	}

	

}
