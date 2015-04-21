package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ViewJourneyDAO;
import com.lvmama.prd.dao.ViewJourneyPlaceDAO;
import com.lvmama.prd.dao.ViewJourneyTipDAO;
import com.lvmama.prd.dao.ViewMultiJourneyDAO;

public class ViewPageJourneyServiceImpl implements ViewPageJourneyService {
	private ViewJourneyDAO viewJourneyDAO ;
	private ViewJourneyPlaceDAO viewJourneyPlaceDAO;
	private ComLogDAO comLogDAO;
	private ViewJourneyTipDAO viewJourneyTipDAO;
	private ViewMultiJourneyDAO viewMultiJourneyDAO;
	 
	public ViewJourney loadViewJourney(Long journeyId) {
		return viewJourneyDAO.selectByPrimaryKey(journeyId);
	}

	public void insertViewJourney(ViewJourney viewJourney,String operatorName) {
		if(viewJourney.getMultiJourneyId() == null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", viewJourney.getProductId());
			List<ViewMultiJourney> list = viewMultiJourneyDAO.queryMultiJourneyByParams(params);
			if(list != null && list.size() > 0) {
				viewJourney.setMultiJourneyId(list.get(0).getMultiJourneyId());
			} else {
				ViewMultiJourney vmj = new ViewMultiJourney();
				vmj.setProductId(viewJourney.getProductId());
				Long multiJourneyId = viewMultiJourneyDAO.insert(vmj);
				viewJourney.setMultiJourneyId(multiJourneyId);
			}
		}
		viewJourneyDAO.insert(viewJourney);
		
		/**zx 20120307 add log*/
		comLogDAO.insert("PROD_PRODUCT_JOURNEY", viewJourney.getProductId(),null, operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertProductJourney.name(), null,"创建了标题为[ "+viewJourney.getTitle()+" ]的行程", "PROD_PRODUCT");
		/***/
		
		if(viewJourney.getProdTargetId()==null||viewJourney.getProdTargetId().isEmpty()){
			return;
		}
		for(int i=0;i<viewJourney.getProdTargetId().size();i++){
			ViewJourneyPlace record=new ViewJourneyPlace();
			record.setJourneyId(viewJourney.getJourneyId());
			record.setPlaceId(new Long((String)viewJourney.getProdTargetId().get(i)));
			viewJourneyPlaceDAO.insert(record);
		}
	}
	
	public void insertMultiViewJourney(ViewJourney viewJourney,String operatorName) {
		viewJourneyDAO.insert(viewJourney);		
		comLogDAO.insert("PROD_PRODUCT_JOURNEY", viewJourney.getProductId(),null, operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertProductJourney.name(), null,"创建了标题为[ "+viewJourney.getTitle()+" ]的行程", "PROD_PRODUCT");		
		if(viewJourney.getProdTargetId()==null||viewJourney.getProdTargetId().isEmpty()){
			return;
		}
		for(int i=0;i<viewJourney.getProdTargetId().size();i++){
			ViewJourneyPlace record=new ViewJourneyPlace();
			record.setJourneyId(viewJourney.getJourneyId());
			record.setPlaceId(new Long((String)viewJourney.getProdTargetId().get(i)));
			viewJourneyPlaceDAO.insert(record);
		}
	}

	public void updateViewJourney(ViewJourney viewJourney,String operatorName) {
		
		ViewJourney oldViewJourney = loadViewJourney(viewJourney.getJourneyId());
		
		viewJourneyDAO.updateByPrimaryKey(viewJourney);
		
		/**zx 20120307 add log*/
		comLogDAO.insert("PROD_PRODUCT_JOURNEY", viewJourney.getProductId(),viewJourney.getJourneyId(), operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.editProductJourney.name(), null,"更新了标题为[ "+oldViewJourney.getTitle()+" ]的行程", "PROD_PRODUCT");
		/***/
		
		//delete already exists
		viewJourneyPlaceDAO.deleteByJourneyid(viewJourney.getJourneyId());
		//insert new records
		if(viewJourney.getProdTargetId() != null){
			for(int i=0;i<viewJourney.getProdTargetId().size();i++){
				ViewJourneyPlace record=new ViewJourneyPlace();
				record.setJourneyId(viewJourney.getJourneyId());
				record.setPlaceId(new Long((String)viewJourney.getProdTargetId().get(i)));
				viewJourneyPlaceDAO.insert(record);
			}
		}
	}

	public void deleteViewJourney(Long journeyId,String operatorName) {
		
		ViewJourney preDeleteViewJourney = loadViewJourney(journeyId);
		
		this.viewJourneyPlaceDAO.deleteByJourneyid(journeyId);
		this.viewJourneyTipDAO.deleteByJourneyId(journeyId);
		this.viewJourneyDAO.deleteByPrimaryKey(journeyId);
		
		/**zx 20120307 add log*/
		comLogDAO.insert("PROD_PRODUCT_JOURNEY", preDeleteViewJourney.getProductId(),journeyId, operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.deleteProductJourney.name(), null,"删除了标题为[ "+preDeleteViewJourney.getTitle()+" ]的行程", "PROD_PRODUCT");
		/***/
	}

	public List<CodeItem> getProdTarget(Long pageId) {
		return viewJourneyPlaceDAO.getProdTarget(pageId);
	}

	public List getSelectedProdTarget(Long journeyId) {
		return viewJourneyPlaceDAO.selectByJourneyId(journeyId);
	}

	public List<ViewJourney> getViewJourneysByProductId(Long productId) {
		List<ViewJourney> journeyList= viewJourneyDAO.getViewJourneysByProductId(productId);
		for(int i=0;i<journeyList.size();i++){
			ViewJourney viewJourney =journeyList.get(i);
			String placeDesc="";
			List<ViewJourneyPlace> places=this.getSelectedProdTarget(viewJourney.getJourneyId());
			for(int j=0;j<places.size();j++){
				if (j==0) placeDesc = places.get(j).getPlaceName();
				else placeDesc += "," + places.get(j).getPlaceName();
			}
			viewJourney.setPlaceDesc(placeDesc);
		}
		return journeyList;
	}
	
	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}

	public void setViewJourneyPlaceDAO(ViewJourneyPlaceDAO viewJourneyPlaceDAO) {
		this.viewJourneyPlaceDAO = viewJourneyPlaceDAO;
	}
	
	public void setViewJourneyTipDAO(ViewJourneyTipDAO viewJourneyTipDAO) {
		this.viewJourneyTipDAO = viewJourneyTipDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public List<ViewJourney> getViewJourneyByMultiJourneyId(Long multiJourneyId) {
		List<ViewJourney> journeyList= viewJourneyDAO.getViewJourneyByMultiJourneyId(multiJourneyId);
		for(int i=0;i<journeyList.size();i++){
			ViewJourney viewJourney =journeyList.get(i);
			String placeDesc="";
			List<ViewJourneyPlace> places=this.getSelectedProdTarget(viewJourney.getJourneyId());
			for(int j=0;j<places.size();j++){
				if (j==0) placeDesc = places.get(j).getPlaceName();
				else placeDesc += "," + places.get(j).getPlaceName();
			}
			viewJourney.setPlaceDesc(placeDesc);
		}
		return journeyList;
	}

	public void setViewMultiJourneyDAO(ViewMultiJourneyDAO viewMultiJourneyDAO) {
		this.viewMultiJourneyDAO = viewMultiJourneyDAO;
	}

}
