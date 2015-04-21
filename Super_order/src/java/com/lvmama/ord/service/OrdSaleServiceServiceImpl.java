package com.lvmama.ord.service;

import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ord.dao.OrdSaleServiceDAO;

public class OrdSaleServiceServiceImpl implements OrdSaleServiceService {

	private OrdSaleServiceDAO ordSaleServiceDao;
	private ComMessageService comMessageService;
	/**
	 * 日志
	 */
	private ComLogDAO comLogDAO;
	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	public void deleteOrdSaleService(String ordSaleId){
		this.ordSaleServiceDao.deleteOrdSaleService(ordSaleId);
	}
	public boolean findOrderItemIsRefund(Long orderItemMetaId){
		return ordSaleServiceDao.findOrderItemIsRefund(orderItemMetaId);	
	}
	public List<OrdSaleService> getOrdSaleServiceAllByParam(Map params) {
		return this.ordSaleServiceDao.getOrdSaleServiceAllByParam(params);
	}

	public boolean updateOrdSaleService(OrdSaleService ordSaleService) {
		boolean flag = this.ordSaleServiceDao.updateOrdSaleService(ordSaleService);
		
		insertLog(ordSaleService.getSaleServiceId(), "SALE_SERVICE", "ordSaleServiceComplete", 
				ordSaleService.getOperatorName(), "售后处理结束", ordSaleService.getOperatorName());
		
		return flag;
	}

	
	private void insertLog(Long objectId,String objectType, String logType,String logName,String content,String operator){
		ComLog log=new ComLog();
		log.setObjectId(objectId);
		log.setObjectType(objectType);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setOperatorName(operator);
		log.setContent(content);
		comLogDAO.insert(log);
	}

	public Long addOrdSaleService(OrdSaleService ordSaleService) {
		Long saleId=ordSaleServiceDao.addOrdSaleService(ordSaleService);
		if(Constant.SERVICE_TYPE.URGENCY.name().equals(ordSaleService.getServiceType())){
			comMessageService.addSystemComMessage(Constant.EVENT_TYPE.URGENCY_PERFORM.name(),"有紧急入园问题需要处理", Constant.SYSTEM_USER);
		}
		return saleId;
	}

	public OrdSaleService getOrdSaleServiceByPk(Long ordSaleId) {
		return this.ordSaleServiceDao.getOrdSaleServiceByPk(ordSaleId);
	}

	public OrdSaleServiceDAO getOrdSaleServiceDao() {
		return ordSaleServiceDao;
	}

	public void setOrdSaleServiceDao(OrdSaleServiceDAO ordSaleServiceDao) {
		this.ordSaleServiceDao = ordSaleServiceDao;
	}
	public ComMessageService getComMessageService() {
		return comMessageService;
	}
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}
	@Override
	public int takeOrdSaleServiceByIds(Map<String, Object> params, String operatorName) {
		int result = this.ordSaleServiceDao.takeOrdSaleServiceByIds(params);

		String takenOperator = (String) params.get("takenOperator");
		List<Long> saleServiceIds = (List<Long>) params.get("saleServiceIds");
		for (Long saleServiceId : saleServiceIds) {
			insertLog(saleServiceId, "SALE_SERVICE", "ordSaleServiceComplete", 
					operatorName, "分配给：" + takenOperator, operatorName);
		}
		
		return result;
	}

}
