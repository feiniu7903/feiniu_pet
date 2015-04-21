package com.lvmama.ord.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEContractComment;
import com.lvmama.comm.bee.po.ord.OrdEContractLog;
import com.lvmama.comm.bee.po.ord.OrdEcontractBackUpFile;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ord.dao.OrdEContractCommentDAO;
import com.lvmama.ord.dao.OrdEContractDAO;
import com.lvmama.ord.dao.OrdEContractLogDAO;
import com.lvmama.ord.dao.OrdEcontractBackUpFileDAO;
import com.lvmama.ord.dao.OrdEcontractSignLogDAO;
import com.lvmama.ord.logic.CommonOrderContractLogic;

class OrdEContractServiceImpl implements OrdEContractService {
	private static final Logger LOG = Logger.getLogger(OrdEContractServiceImpl.class);
	private OrdEContractDAO ordEContractDAO;
	private OrdEContractCommentDAO ordEContractCommentDAO;
	private OrdEContractLogDAO ordEContractLogDAO;
	private OrdEcontractSignLogDAO ordEcontractSignLogDAO;
	private OrdEcontractBackUpFileDAO ordEcontractBackUpFileDAO;
	private CommonOrderContractLogic orderContractLogic;
	private ComLogDAO comLogDAO;
	@Override
	public OrdEContractComment insertOrdEContractComment(OrdEContractComment obj) {
		return ordEContractCommentDAO.insert(obj);
	}

	/**
     * 根据订单电子合同编号查询备注列表.
     */
	@Override
	public List<OrdEContractComment> queryEContractCommentByEContractId(String eContractId) {
		return ordEContractCommentDAO.queryByEContractId(eContractId);
	}

	/**
	 * 根据订单ID查询备注列表.
	 */
	@Override
	public OrdEContractComment queryEContractCommentByOrderId(final Long orderId) {
		return ordEContractCommentDAO.queryByOrderId(orderId);
	}
	
	@Override
	public OrdEContractLog insertEContractLog(OrdEContractLog object) {
		return ordEContractLogDAO.insert(object);
	}
	
	@Override
	public OrdEcontractSignLog insertEcontractSignLog(OrdEcontractSignLog object) {
		return ordEcontractSignLogDAO.insert(object);
	}
	
	@Override
	public List<OrdEcontractSignLog> queryEcontractSignLog(Map<String, Object> parameters) {
		return ordEcontractSignLogDAO.query(parameters);
	}
	
	@Override
	public OrdEcontractBackUpFile insertEcontractBackUpFile(OrdEcontractBackUpFile object) {
		return ordEcontractBackUpFileDAO.insert(object);
	}
	
	@Override
	public List<OrdEcontractBackUpFile> queryEcontractBackUpFile(Map<String, Object> parameters) {
		return ordEcontractBackUpFileDAO.query(parameters);
	}
	@Override
	public int updateEcontractBackUpFile(OrdEcontractBackUpFile object) {
		return ordEcontractBackUpFileDAO.update(object);
	}

	@Override
	public OrdEContract createOrderContract(final OrdOrder order,final ProdEContract prodContract,final Long contentFileId, final Long fixedFileId,final String userName) {
		return orderContractLogic.createOrderContract(order,prodContract,contentFileId,fixedFileId,userName);
	}
	@Override
	public OrdEContract updateOrderContract(final OrdEContract contract,final OrdOrder order,final String complementXml,final Long contentFileId,final Long fixedFileId,final String operater){
		return orderContractLogic.updateOrderContract(contract,order,complementXml,contentFileId,fixedFileId,operater);
	}
	@Override
	public Map<String, Object> getDataStore(OrdOrder order,final ProdEContract prodEContract) {
		return orderContractLogic.getDataStore(order,prodEContract);
	}

	@Override
	public Long getContractContent(Long orderId) {
		return orderContractLogic.getContractContent(orderId);
	}

	/**
	 * 电子合同签约
	 * @param orderServiceProxy
	 * @param comLogDAO
	 * @param orderId
	 * @param signLog
	 * @return
	 */
	@Override
	public boolean signContract(final Long orderId,final OrdEcontractSignLog signLog){
		OrdEContract ordEContract = ordEContractDAO.queryByOrderId(orderId);
		if(null ==ordEContract){
			LOG.error("根据订单号  "+orderId+"  没有找到电子合同 没有进行签约");
			return Boolean.FALSE;
		}
		
		ordEContract.setEcontractStatus(Constant.ECONTRACT_STATUS.CONFIRM.name());
		ordEContract.setConfirmedDate(new Date());

		signLog.setEcontractNo(ordEContract.getEcontractNo());
		if(StringUtil.isEmptyString(signLog.getSignMode())){
			signLog.setSignMode(Constant.ECONTRACT_SIGN_TYPE.ONLINE_SIGN.getCode());
		}
		ComLog log = new ComLog();
		log.setParentId(orderId);
		log.setParentType("ORD_ECONTRACT");
		log.setObjectType("ORD_ECONTRACT");
		log.setObjectId(orderId);
		log.setOperatorName(signLog.getSignUser());
		log.setLogType(Constant.COM_LOG_CONTRACT_EVENT.signContract.name());
		log.setLogName("修改电子合同签约方式");
		log.setContent("保存签约方式:"+Constant.ECONTRACT_SIGN_TYPE.valueOf(signLog.getSignMode()).getCnName());
		
		try{
			ordEcontractSignLogDAO.insert(signLog);
			if(!Constant.ECONTRACT_STATUS.CONFIRM.name().equals(ordEContract.getEcontractStatus())){
				ordEContractDAO.update(ordEContract);
				log.setLogName("电子合同签约");
				log.setContent("把合同签约状态修改为已签约，签约方式:"+Constant.ECONTRACT_SIGN_TYPE.getCnName(signLog.getSignMode()));
			}
			comLogDAO.insert(log);
			return Boolean.TRUE;
		}catch(Exception e){
			LOG.warn("合同签约报错:\r\n"+e.getMessage());
			return Boolean.FALSE;
		}
	}

	@Override
	public String getContractComplement(final Long orderId,final String complementTemplate){
		return orderContractLogic.getContractComplement(orderId,complementTemplate);
	}

	@Override
	public String initComplementXml(final OrdOrder order,final ProdEContract prodContract){
		return orderContractLogic.getComplementDataXml(order, prodContract);
	}
	@Override
	public OrdEContract queryByOrderId(Long orderId) {
		return ordEContractDAO.queryByOrderId(orderId);
	}
	@Override
	public Long existByOrderId(Long orderId) {
		return ordEContractDAO.existByOrderId(orderId);
	}
	@Override
	public void updateOrdEContract(final OrdEContract ordEContract) {
		ordEContractDAO.update(ordEContract);
	}	
	@Override
	public List<OrdEContract> queryOrdEContract(Map<String, Object> parameters) {
		return ordEContractDAO.query(parameters);
	}
	/**
     * 根据订单电子合同编号查询备注列表.
     */
	@Override
	public List<OrdEContractComment> queryByEContractId(String eContractId) {
		return ordEContractCommentDAO.queryByEContractId(eContractId);
	}
	@Override
	public OrdEContract getOrdEContractByOrderId(Long orderId) {
		if(orderId==null||orderId<0)
			return null;
		return ordEContractDAO.queryByOrderId(orderId);
	}

	@Override
	public OrdEContract getOrdEconractByEContractId(String econtractId) {
		return this.ordEContractDAO.queryByPK(econtractId);
	}

	public OrdEContractDAO getOrdEContractDAO() {
		return ordEContractDAO;
	}

	public void setOrdEContractDAO(OrdEContractDAO ordEContractDAO) {
		this.ordEContractDAO = ordEContractDAO;
	}

	public OrdEContractCommentDAO getOrdEContractCommentDAO() {
		return ordEContractCommentDAO;
	}

	public void setOrdEContractCommentDAO(
			OrdEContractCommentDAO ordEContractCommentDAO) {
		this.ordEContractCommentDAO = ordEContractCommentDAO;
	}

	public OrdEContractLogDAO getOrdEContractLogDAO() {
		return ordEContractLogDAO;
	}

	public void setOrdEContractLogDAO(OrdEContractLogDAO ordEContractLogDAO) {
		this.ordEContractLogDAO = ordEContractLogDAO;
	}

	public OrdEcontractSignLogDAO getOrdEcontractSignLogDAO() {
		return ordEcontractSignLogDAO;
	}

	public void setOrdEcontractSignLogDAO(
			OrdEcontractSignLogDAO ordEcontractSignLogDAO) {
		this.ordEcontractSignLogDAO = ordEcontractSignLogDAO;
	}

	public OrdEcontractBackUpFileDAO getOrdEcontractBackUpFileDAO() {
		return ordEcontractBackUpFileDAO;
	}

	public void setOrdEcontractBackUpFileDAO(
			OrdEcontractBackUpFileDAO ordEcontractBackUpFileDAO) {
		this.ordEcontractBackUpFileDAO = ordEcontractBackUpFileDAO;
	}

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public CommonOrderContractLogic getOrderContractLogic() {
		return orderContractLogic;
	}

	public void setOrderContractLogic(CommonOrderContractLogic orderContractLogic) {
		this.orderContractLogic = orderContractLogic;
	}


}
