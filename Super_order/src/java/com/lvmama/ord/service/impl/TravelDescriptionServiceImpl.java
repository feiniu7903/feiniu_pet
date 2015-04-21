package com.lvmama.ord.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderRouteTravel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.utils.ord.TemplateFillDataUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ord.dao.OrdOrderRouteTravelDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ViewJourneyDAO;
import com.lvmama.prd.dao.ViewJourneyPlaceDAO;
import com.lvmama.prd.dao.ViewPageDAO;

public class TravelDescriptionServiceImpl implements TravelDescriptionService {
	
	private static final Logger LOG = Logger.getLogger(TravelDescriptionServiceImpl.class);
	private ViewJourneyDAO viewJourneyDAO;
	private ViewJourneyPlaceDAO viewJourneyPlaceDAO;
	private ViewPageDAO viewPageDAO;
	private ProdProductDAO prodProductDAO;
	private OrdOrderRouteTravelDAO ordOrderRouteTravelDAO;
	
	private ComLogDAO comLogDAO;
	
	public void initOrderTravel(final Long fileId,final Long orderId,final String createUser){
		OrdOrderRouteTravel travel = new  OrdOrderRouteTravel();
		travel.setOrderId(orderId);
		travel.setFileId(fileId);
		travel.setCreateUser(createUser);
		ordOrderRouteTravelDAO.insert(travel);
		ComLog log = new ComLog();
		log.setParentId(orderId);
		log.setParentType("ORD_ECONTRACT");
		log.setObjectType("ORD_ORDER_ROUTE_TRAVEL");
		log.setObjectId(orderId);
		log.setOperatorName(createUser);
		log.setLogType(Constant.COM_LOG_CONTRACT_EVENT.insertOrderContract.name());
		log.setLogName("生成线路订单行程");
		log.setContent("由"+createUser+"下单时生成线路行程");
		comLogDAO.insert(log);
	}
	public Long viewOrderTravel(final Long orderId){
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(null==orderId){
			return null;
		}
		parameters.put("orderId", orderId);
		List<OrdOrderRouteTravel> list = ordOrderRouteTravelDAO.query(parameters);
		Long fileId = null;
		if(null != list && list.size() > 0){
			fileId = list.get(0).getFileId();
		}
		return fileId;
	}
	public String queryContentTravelByOrderId(final Long orderId){
		return  ordOrderRouteTravelDAO.queryContentByOrderId(orderId);
	}
	/**
	 * 取得行程说明
	 * @return
	 */
	public String getTravelDesc(final Long productId, final Long multiJourneyId){
		List<ViewJourney> dataList = getViewJourneysById(productId, multiJourneyId);
		ViewPage viewPage=viewPageDAO.selectByPrimaryKey(productId);
		ProdProduct prod = prodProductDAO.selectProductDetailByPrimaryKey(productId);
		if(viewPage != null && prod.isRoute()) {
			ProdRoute pr = (ProdRoute)prod;
			//多行程
			if(pr.hasMultiJourney()) {
				Map<String, Object> contents = viewPage.getContents();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("multiJourneyId", multiJourneyId);
				params.put("contentType", Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
				contents.put(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name(), viewPageDAO.getViewContentByMultiJourneyId(params));
				
				params.put("contentType", Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
				contents.put(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name(), viewPageDAO.getViewContentByMultiJourneyId(params));
				viewPage.setContents(contents);
			}
		}
		return TemplateFillDataUtil.serializeMap(dataList,viewPage);
	}
	public List<ViewJourneyPlace> getSelectedProdTarget(final Long journeyId) {
		return viewJourneyPlaceDAO.selectByJourneyId(journeyId);
	}
	public List<ViewJourney> getViewJourneysById(final Long productId, final Long multiJourneyId) {
		List<ViewJourney> journeyList = null;
		ProdProduct prod = prodProductDAO.selectProductDetailByPrimaryKey(productId);
		if(prod.isRoute()) {
			ProdRoute pr = (ProdRoute)prod;
			//多行程
			if(pr.hasMultiJourney()) {
				if(multiJourneyId != null) {
					journeyList = viewJourneyDAO.getViewJourneyByMultiJourneyId(multiJourneyId);
				}
			} else {
				journeyList = viewJourneyDAO.getViewJourneysByProductId(productId);
			}
		}
		if(journeyList != null) {
			for(int i=0;(null!=journeyList && i<journeyList.size());i++){
				ViewJourney viewJourney =journeyList.get(i);
				String placeDesc="";
				List<ViewJourneyPlace> places=getSelectedProdTarget(viewJourney.getJourneyId());
				for(int j=0;j<places.size();j++){
					if (j==0) placeDesc = places.get(j).getPlaceName();
					else placeDesc += "," + places.get(j).getPlaceName();
				}
				viewJourney.setPlaceDesc(placeDesc);
			}
		}
		return journeyList;
	}
	public ViewJourneyDAO getViewJourneyDAO() {
		return viewJourneyDAO;
	}
	public void setViewJourneyDAO(ViewJourneyDAO viewJourneyDAO) {
		this.viewJourneyDAO = viewJourneyDAO;
	}
	public ViewJourneyPlaceDAO getViewJourneyPlaceDAO() {
		return viewJourneyPlaceDAO;
	}
	public void setViewJourneyPlaceDAO(ViewJourneyPlaceDAO viewJourneyPlaceDAO) {
		this.viewJourneyPlaceDAO = viewJourneyPlaceDAO;
	}
	public ViewPageDAO getViewPageDAO() {
		return viewPageDAO;
	}
	public void setViewPageDAO(ViewPageDAO viewPageDAO) {
		this.viewPageDAO = viewPageDAO;
	}
	public OrdOrderRouteTravelDAO getOrdOrderRouteTravelDAO() {
		return ordOrderRouteTravelDAO;
	}
	public void setOrdOrderRouteTravelDAO(
			OrdOrderRouteTravelDAO ordOrderRouteTravelDAO) {
		this.ordOrderRouteTravelDAO = ordOrderRouteTravelDAO;
	}
	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}
}
