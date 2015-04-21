package com.lvmama.ebk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;
import com.lvmama.ebk.dao.EbkCertificateDAO;
import com.lvmama.ebk.dao.EbkCertificateItemDAO;
import com.lvmama.ebk.dao.EbkOrderDataRevDAO;
import com.lvmama.ebk.dao.EbkTaskDAO;
import com.lvmama.ebk.service.logic.EbkCertificateLogic;

public class EbkCertificateServiceImpl implements EbkCertificateService {
	
	private EbkCertificateDAO ebkCertificateDAO;
	private EbkCertificateItemDAO ebkCertificateItemDAO;
	private EbkOrderDataRevDAO ebkOrderDataRevDAO;
	private EbkTaskDAO ebkTaskDAO;
	private EbkCertificateLogic ebkCertificateLogic;
 
	@Override
	public List<EbkCertificate> createSupplierCertificate(OrdOrder ordOrder,
			Map<Long, SupBCertificateTarget> sbctMap,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList) {
		return ebkCertificateLogic.createSupplierCertificate(ordOrder, sbctMap, ebkCertificateEvent, userMemoStatus, orderItemMetaIdList);
	}

	@Override
	public boolean createCertificateEbkFaxTask(EbkCertificate cert, OrdOrder ordOrder, String ebkCertificateEvent, String operator) {
		return this.ebkCertificateLogic.createCertificateEbkFaxTask(cert, ordOrder, ebkCertificateEvent, operator);
	}
	
	@Override
	public Page<EbkTask> queryEbkTaskPageListSQL(Long currentPage, Long pageSize, Map<String, Object> parameterObject) {
		Page<EbkTask> ebkTaskPage = ebkTaskDAO.queryEbkTaskList(currentPage, pageSize, parameterObject);
		for(EbkTask ebkTask : ebkTaskPage.getItems()){
			EbkCertificate cert = ebkCertificateDAO.selectNotValidByPrimaryKey(ebkTask.getEbkCertificateId());
			parameterObject.put("ebkCertificateId", cert.getEbkCertificateId());
			cert.setEbkCertificateItemList(ebkCertificateItemDAO.queryEbkCertificateItemList(parameterObject));
			parameterObject.remove("ebkCertificateId");
			ebkTask.setEbkCertificate(cert);
		}
		return ebkTaskPage;
	}
	
	@Override
	public EbkCertificate selectByPrimaryKey(Long ebkCertificateId) {
		return this.ebkCertificateDAO.selectByPrimaryKey(ebkCertificateId);
	}
	@Override
	public List<EbkCertificateItem> selectEbkCertificateItemByEbkCertificateId(Long ebkCertificateId) {
		return this.ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(ebkCertificateId);
	}
	public int update(EbkCertificate ebkCertificate){
		 int i=this.ebkCertificateDAO.updateByPrimaryKeySelective(ebkCertificate);
		 return i;
	}
	
	public EbkCertificate selectEbkCertificateDetailByPrimaryKeyAndValid(final Long key){
		EbkCertificate entity = ebkCertificateDAO.selectNotValidByPrimaryKey(key);
		return initData(entity);
	}
	
	private EbkCertificate initData(EbkCertificate entity){
		if(entity!=null){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("ebkCertificateId", entity.getEbkCertificateId());
			List<EbkCertificateItem> ebkCertificateItemList = ebkCertificateItemDAO.queryEbkCertificateItemList(params);
			entity.setEbkCertificateItemList(ebkCertificateItemList);
			
			Long metaProductID = ebkCertificateItemList.get(0).getMetaProductId();
			entity.setMainMetaProductID(metaProductID);
			String metaProductName = ebkCertificateItemList.get(0).getRealProductName();
			entity.setMainMetaProductName(metaProductName);
			String allUserMemo = "";
			String isResourceSendFax = "";
			for (int i = 0; i < ebkCertificateItemList.size(); i++) {
				EbkCertificateItem ebkCertificateItem = ebkCertificateItemList.get(i);
				ebkCertificateItem.setEbkCertificate(entity);
				if(ebkCertificateItem.getFaxMemo()!=null&&!"".equals(ebkCertificateItem.getFaxMemo()))
				{
					allUserMemo += ebkCertificateItem.getFaxMemo()+"<br>";
				}
				isResourceSendFax = ebkCertificateItem.getIsResourceSendFax();
			}
			entity.setIsResourceSendFax(isResourceSendFax);
			entity.setAllUserMemo(allUserMemo);
			
			List<EbkOrderDataRev> list = ebkOrderDataRevDAO.queryEbkOrderDataRevList(params);
			
			Map<Long,List<EbkOrderDataRev>> map = new HashMap<Long, List<EbkOrderDataRev>>();
			List<EbkOrderDataRev> data = new ArrayList<EbkOrderDataRev>();
			for(EbkOrderDataRev rev:list){
				if(rev.getEbkCertificateItemId()==null){
					data.add(rev);
				}else{
					List<EbkOrderDataRev> revs = null;
					if(map.containsKey(rev.getEbkCertificateItemId())){
						revs = (List<EbkOrderDataRev>)map.get(rev.getEbkCertificateItemId());
					}else{
						revs = new ArrayList<EbkOrderDataRev>();
					}
					revs.add(rev);
					map.put(rev.getEbkCertificateItemId(), revs);
				}
			}
			for(EbkCertificateItem item : entity.getEbkCertificateItemList()){
				item.setEbkOrderDataRevList(map.get(item.getEbkCertificateItemId()));
			}
			entity.setEbkOrderDataRevList(data);
		}
		
		return entity;
	}
	@Override
	public EbkCertificate selectEbkCertificateDetailByPrimaryKey(Long key) {
		EbkCertificate entity = ebkCertificateDAO.selectByPrimaryKey(key);
		return initData(entity);
	}
	public void createData(List<EbkOrderDataRev> list) {
		for(EbkOrderDataRev rev:list){
			if(rev.getDataId() != null && rev.getDataId().longValue() > 0 ){
				ebkOrderDataRevDAO.updateByPrimaryKey(rev);
			}else {
				ebkOrderDataRevDAO.insert(rev);
			}
		}
	}
	@Override
	public EbkCertificateItem selectEbkCertificateItemByOrderItemMetaId(Long orderItemMetaId) {
		return ebkCertificateItemDAO.getEbkCertificateItemByOrderItemMetaId(orderItemMetaId);
	}

	@Override
	public EbkCertificate selectEbkCertDetailAndGetContentByPrimaryKey(Long ebkCerfificatekey) {
		EbkCertificate certificate = selectEbkCertificateDetailByPrimaryKey(ebkCerfificatekey);
		if(certificate != null){
			EbkCertBuilder ebkCertBuilder=EbkCertBuilderFactory.create(certificate);
			certificate.setEbkCertificateData(ebkCertBuilder.getCertContent(certificate));	
		}
		return certificate;
	}

	@Override
	public EbkTask queryEbkTaskByEbkTaskId(Long ebkTaskId) {
		return this.ebkTaskDAO.selectByPrimaryKey(ebkTaskId);
	}

	public List<EbkCertificate> selectEbkCertificateByOrderId(Long orderId){
		return this.ebkCertificateDAO.selectEbkCertificateByOrderId(orderId);
	}
	
	public EbkCertificateDAO getEbkCertificateDAO() {
		return ebkCertificateDAO;
	}

	public void setEbkCertificateDAO(EbkCertificateDAO ebkCertificateDAO) {
		this.ebkCertificateDAO = ebkCertificateDAO;
	}

	public EbkCertificateItemDAO getEbkCertificateItemDAO() {
		return ebkCertificateItemDAO;
	}

	public void setEbkCertificateItemDAO(EbkCertificateItemDAO ebkCertificateItemDAO) {
		this.ebkCertificateItemDAO = ebkCertificateItemDAO;
	}

	public EbkOrderDataRevDAO getEbkOrderDataRevDAO() {
		return ebkOrderDataRevDAO;
	}

	public void setEbkOrderDataRevDAO(EbkOrderDataRevDAO ebkOrderDataRevDAO) {
		this.ebkOrderDataRevDAO = ebkOrderDataRevDAO;
	}

	public EbkTaskDAO getEbkTaskDAO() {
		return ebkTaskDAO;
	}

	public void setEbkTaskDAO(EbkTaskDAO ebkTaskDAO) {
		this.ebkTaskDAO = ebkTaskDAO;
	}
	public EbkCertificateLogic getEbkCertificateLogic() {
		return ebkCertificateLogic;
	}
	public void setEbkCertificateLogic(EbkCertificateLogic ebkCertificateLogic) {
		this.ebkCertificateLogic = ebkCertificateLogic;
	}

	@Override
	public int updateChangeInfo(String changeInfo, Long ebkCertificateId) {
		return ebkCertificateDAO.updateChangeInfo(changeInfo, ebkCertificateId);
	}
}
