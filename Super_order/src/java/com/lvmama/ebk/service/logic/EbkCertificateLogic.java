package com.lvmama.ebk.service.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;
import com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder;
import com.lvmama.comm.vst.service.EbkSuperClientService;
import com.lvmama.ebk.dao.EbkCertificateDAO;
import com.lvmama.ebk.dao.EbkCertificateItemDAO;
import com.lvmama.ebk.dao.EbkOrderDataRevDAO;
import com.lvmama.order.dao.OrderItemMetaAperiodicDAO;
import com.lvmama.passport.dao.PassCodeDAO;
import com.lvmama.passport.dao.PassPortCodeDAO;

/**
 * 生成凭证：
 * <br>1,生成凭证，凭证类型有：询位，确认，变更，取消
 * <br>2,修改凭证：订单信息变更，凭证状态修改
 * <br>3,查找凭证：按订单号，按传真或EBK任务的条件
 * <br>询位：创建，合并，作废
 * <br>确认：创建，修改，作废
 * <br>酒店：下单，支付，取消（还可能修改），修改（游玩人基本信息、结算单、总价、用户特殊要求、废单重下）
 * <br>门票：下单，审核，支付，取消（还可能修改），修改（游玩人基本信息、结算单、总价、用户特殊要求、废单重下）
 * <br>线路：下单，审核（所有），支付，取消（还可能修改），修改（游玩人基本信息、结算单、总价、用户特殊要求、废单重下）
 * <br>检查项：供应商凭证对象，订单状态，已经存在的凭证，传真策略，支付状态，资源审核状态，信息审核状态
 * @author ranlongfei 2013-3-15
 * @version
 */
public class EbkCertificateLogic {

	private EbkTaskService ebkTaskService;
	
	private EbkCertificateDAO ebkCertificateDAO;
	private EbkCertificateItemDAO ebkCertificateItemDAO;
	private EbkOrderDataRevDAO ebkOrderDataRevDAO;
	
	private EbkCertificateTicketLogic ebkCertificateTicketLogic;
	private EbkCertificateHotelLogic ebkCertificateHotelLogic;
	private EbkCertificateRouteLogic ebkCertificateRouteLogic;
	private EbkTaskLogic ebkTaskLogic;

	private PassCodeDAO passCodeDAO;
	private PassPortCodeDAO passPortCodeDAO;
	private OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO;
	
	private EbkSuperClientService ebkSuperClientService;
	/**
	 * 生成凭证
	 * 
	 * @param ordOrder
	 * @param sbctMap
	 * @param ebkCertificateEvent
	 * @param userMemoStatus
	 * @param orderItemMetaIdList
	 * @return
	 */
	public List<EbkCertificate> createSupplierCertificate(OrdOrder ordOrder,
			Map<Long, SupBCertificateTarget> sbctMap,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList) {
		List<EbkCertificate> result = new ArrayList<EbkCertificate>();
		AbstractEbkCertificateSet set = new AbstractEbkCertificateSet();
		set.setSupBCertificateTargetMap(sbctMap);
		set.setEbkCertificateEvent(ebkCertificateEvent);
		set.setUserMemoStatus(userMemoStatus);
		set.setOrderItemMetaIdList(orderItemMetaIdList);
		for(OrdOrderItemMeta ooim : ordOrder.getAllOrdOrderItemMetas()) {
			if(!"true".equals(ooim.getVirtual()) && ooim.getProductName().indexOf("虚拟")==-1
					&& (ooim.hasSupplier()||ooim.isNeedSendFax())){
				//订单子项分类
				if(ooim.isTicketProductType()){
					ebkCertificateTicketLogic.addCertificateSet(ordOrder, ooim, sbctMap, set);
				}else if(ooim.isRouteProductType()){
					ebkCertificateRouteLogic.addCertificateSet(ordOrder, ooim, sbctMap, set);
				}else if(ooim.isHotelProductType()){
					ebkCertificateHotelLogic.addCertificateSet(ordOrder, ooim, sbctMap, set);
				}
			}
		}

		List<EbkCertificate> createTaskCertList = new ArrayList<EbkCertificate>();
		if(set.hasNewEbkCert()){
			List<EbkCertificate> insertCertificate = this.insertCertificate(ordOrder,set);
			result.addAll(insertCertificate);
			createTaskCertList.addAll(insertCertificate);
		}
		if(set.hasNewCancelChangeEbkCert()){
			List<EbkCertificate> insertCertificate = this.insertCertificate(ordOrder,set);
			result.addAll(insertCertificate);
			createTaskCertList.addAll(insertCertificate);
			List<EbkCertificate> notValid=new ArrayList<EbkCertificate>();
			for(String key:set.getNewCancelChangeEbkCert().keySet()){
				EbkCertificate ebkCertificate = set.getNewCancelChangeEbkCert().get(key);
				notValid.add(ebkCertificateDAO.selectByPrimaryKey(ebkCertificate.getOldCertificateId()));
			}
			updateEbkCertValid(notValid);
		}
		if(set.hasUpdateEbkCert()) {
			this.updateCertificate(ordOrder,set );
			createTaskCertList.addAll(set.getUpdateEbkCert());
			result.addAll(set.getUpdateEbkCert());
		}
		if(set.hasCancelEbkCert()) {
			this.cancelCertificate(set);
			result.addAll(set.getCancelEbkCert());
		}
		if(set.hasNotValidEbkCert()) {
			this.updateEbkCertValid(set.getNotValidEbkCert());
			result.addAll(set.getNotValidEbkCert());
		}
		if(set.hasNotValidEbkCertItem()) {
			this.updateEbkCertItemValid(set.getCancelEbkCertificateItems());
		}
		if(set.hasMergeEbkCert()){
			this.updateMergeEbkCertItem(ordOrder,set.getMergeEbkCert());
			result.addAll(set.getMergeEbkCert());
		}
		
		createEbkFaxAndTask(ordOrder, createTaskCertList, set);
		return result;
	}
	/**
	 * 生成任务
	 * 
	 * @param cert
	 * @param ordOrder
	 * @param ebkCertificateEvent
	 * @param operator
	 * @return
	 */
	public boolean createCertificateEbkFaxTask(EbkCertificate cert, OrdOrder ordOrder, String ebkCertificateEvent, String operator) {
		OrdOrderItemMeta ooim = this.getOrdOrderItemMeta(ordOrder.getAllOrdOrderItemMetas());
		if(!this.hasCreateEbkFaxTask(cert, ordOrder, ooim )) {
			return false;
		}
		AbstractEbkCertificateSet set = new AbstractEbkCertificateSet();
		set.setEbkCertificateEvent(ebkCertificateEvent);
		cert.setMemo("手工转为传真处理");
		cert.setConfirmChannel(Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.CHANGE_FAX.name());
		this.createEbkFaxTask(cert, set , ordOrder);
		EbkTask ebkTask = this.ebkTaskService.selectByEbkCertificateId(cert.getEbkCertificateId());
		ebkTask.setConfirmTime(new Date());
		ebkTask.setConfirmUser(operator);
		this.ebkTaskService.update(ebkTask);
		return true;
	}
	/**
	 * 生成相关任务：1新增的凭证，2部分修改的凭证
	 * 
	 * @author: ranlongfei 2013-4-26 下午8:38:39
	 * @param ordOrder
	 * @param set
	 */
	private void createEbkFaxAndTask(OrdOrder ordOrder, List<EbkCertificate> ebkCertificates, AbstractEbkCertificateSet set) {
		for(EbkCertificate ebkCertificate : ebkCertificates) {
			//创建任务
			createTask(ebkCertificate, set, ordOrder);
		}
	}  
	/**
	 * 生成任务（EBK与传真）
	 * 
	 * @author: ranlongfei 2013-3-21 下午2:44:49
	 * @param ebkCertificate
	 * @param ooitm 
	 */
	private void createTask(EbkCertificate ebkCertificate, AbstractEbkCertificateSet set, OrdOrder ordOrder) {
		OrdOrderItemMeta ooim = null;
		if(!ebkCertificate.hasCreateTask()){
			ooim = ebkCertificate.getEbkTaskOrdOrderItemMeta();
			if(ooim != null && ooim.hasSupplier()) {
				boolean createTaskFlag=true;
				if(ebkCertificate.isHotel() && ordOrder.hasNeedPrePay() && !ordOrder.isPaymentSucc()){
					createTaskFlag	= false;
				}else if(ordOrder.isTicket() || ooim.isTicketProductType()){
					createTaskFlag	= false;
				}
				if(createTaskFlag){
					EbkTask ebkTask = ebkTaskLogic.craeteEbkTask(ebkCertificate, ordOrder, ooim);
					ebkCertificate.setCreateTask("true");
					ebkCertificateDAO.updateByPrimaryKey(ebkCertificate);
					/*
					 *  凭证及任务创建完成后，向VST系统中间表插入老系统订单任务信息
					 *  2014-02-24
					 *  LIULIANG 
					 */
					ebkTask.setConfirmTime(new Date());
					ebkTask.setEbkCertificate(ebkCertificate);
					if("HOTEL".equals(ebkCertificate.getProductType())){
						ebkSuperClientService.addEbkSuperTask(ebkTask);
					}
				}
			}
			ooim = ebkCertificate.getFaxOrdOrderItemMeta();
			if(ooim != null && ooim.isNeedSendFax()) {
				if(hasCreateEbkFaxTask(ebkCertificate, ordOrder, ooim)) {
					createEbkFaxTask(ebkCertificate, set, ordOrder);
				}
			}
		}else {
			if(ebkCertificate.hasEbkCertificateTypeChange() && ebkCertificate.hasCertificateStatusCreate()){
				if(set.hasOrderModifySettlementPrice()){
					ebkTaskLogic.updateEbkFaxTask(ebkCertificate, ordOrder, set);
				}else if(set.hasOrderModifyPerson()){
					ebkTaskLogic.updateEbkFaxTask(ebkCertificate, ordOrder, set);
				}
			}
		}
	}
	/**
	 * 
	 * @author: ranlongfei 2013-4-22 下午2:00:22
	 * @param ebkCertificate
	 * @param set
	 * @param ordOrder
	 */
	private void createEbkFaxTask(EbkCertificate ebkCertificate, AbstractEbkCertificateSet set, OrdOrder ordOrder) {
		//创建传真任务
		ebkTaskLogic.craeteFaxTask(ebkCertificate, ordOrder,set);
		ebkCertificate.setCreateTask("true");
		ebkCertificateDAO.updateByPrimaryKey(ebkCertificate);
	}

	/**
	 * 
	 * @author: ranlongfei 2013-4-22 下午2:03:18
	 * @param ebkCertificate
	 * @param ordOrder
	 * @param ooim
	 * @return
	 */
	private boolean hasCreateEbkFaxTask(EbkCertificate ebkCertificate, OrdOrder ordOrder, OrdOrderItemMeta ooim) {
		boolean createTaskFlag=false;
		if(ebkCertificate.hasEbkCertificateTypeEnquiry()) {
			return createTaskFlag;
		}
		//资源审核后生成传真
		if(ebkCertificate.hasEbkCertificateTypeConfirm()){
			if("true".equalsIgnoreCase(ooim.getIsResourceSendFax())&&ordOrder.isApprovePass()){
				createTaskFlag=true;
			}else if(ordOrder.isPaymentSucc()&&ordOrder.isApprovePass()){//支付后生成传真
				createTaskFlag=true;
			}
		}
		if(!ebkCertificate.hasEbkCertificateTypeConfirm() || createTaskFlag){
			createTaskFlag=true;
		}
		return createTaskFlag;
	}
	/**
	 * 插入数据
	 * 
	 * @author: ranlongfei 2013-3-23 下午4:57:44
	 * @param ordOrder
	 * @param sbctMap
	 * @param ooimMap
	 * @return
	 */
	private List<EbkCertificate> insertCertificate(OrdOrder ordOrder,AbstractEbkCertificateSet set){
		Map<String,EbkCertificate> ooimMap = set.getNewEbkCert();
		List<EbkCertificate> result = new ArrayList<EbkCertificate>();
		for (String key : ooimMap.keySet()) {
			EbkCertificate ebkCertificate = ooimMap.get(key);
			List<OrdOrderItemMeta> ordOrderItemMetas = ebkCertificate.getOrdOrderItemMetaList();
			OrdOrderItemMeta ooitm = getOrdOrderItemMeta(ordOrderItemMetas);
			
			ebkCertificate = this.initEbkCertificate(ebkCertificate, ooitm);
			initOrderInfo(ebkCertificate,ordOrder);
			ebkCertificate.setMessageType(set.getEbkCertificateEvent());
			ebkCertificate.setTestOrder(ordOrder.getTestOrderFlag());
			ebkCertificate.setUserMemoStatus(set.getUserMemoStatus());
			ebkCertificate.setOrderType(ordOrder.getOrderType());
			Long ebkCertificateId = ebkCertificateDAO.insert(ebkCertificate);
			ebkCertificate.setEbkCertificateId(ebkCertificateId);
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetas) {
				EbkCertificateItem ebkCertificateItem =  this.initEbkCertificateItem(ordOrderItemMeta, ordOrder);
				ebkCertificateItem.setEbkCertificateId(ebkCertificateId);
				ebkCertificateItemDAO.updateOldItemByOrderItemMetaId(ebkCertificateItem.getOrderItemMetaId());
				Long itemPkId = ebkCertificateItemDAO.insert(ebkCertificateItem);
				ebkCertificateItem.setEbkCertificateItemId(itemPkId);
				ebkCertificateItem.setOrderItemMeta(ordOrderItemMeta);
				ebkCertificate.putEbkCertificateItem(ebkCertificateItem);
			}
			
			//保存凭证数据
			saveOrderDataRev(ebkCertificate,ordOrder,false,false);
			
			//创建任务
			// 已经移走：：：
			result.add(ebkCertificate);
		}
		return result;
	}

	private boolean updateEbkCertValid(List<EbkCertificate> certificates) {
		for(EbkCertificate ec : certificates) {
			ec.setValid("false");
			ec.setVersion(ec.getVersion()+1);
			this.ebkCertificateDAO.updateByPrimaryKeySelective(ec);
		}
		return true;
	}
	private boolean cancelCertificate(AbstractEbkCertificateSet set) {
		List<EbkCertificate> updateEbkCert = set.getCancelEbkCert();
		for(EbkCertificate ec : updateEbkCert) {
			ec.setCertificateStatus(Constant.EBK_TASK_STATUS.CANCEL.name());
			ec.setVersion(ec.getVersion()+1);
			this.ebkCertificateDAO.updateByPrimaryKey(ec);
			//加入以便后面更新
			List<EbkCertificateItem> items = this.ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(ec.getEbkCertificateId());
			ec.setEbkCertificateItemList(items);
		}
		return true;
	}
	private boolean updateEbkCertItemValid(List<EbkCertificateItem> certificates) {
		for(EbkCertificateItem ec : certificates) {
			ec = this.ebkCertificateItemDAO.selectEbkCertificateItemByParam(ec);
			ec.setValid("false");
			ebkCertificateItemDAO.updateByPrimaryKeySelective(ec);
			Long count = ebkCertificateItemDAO.countValidEbkCertificateItem(ec.getEbkCertificateId());
			if(count.longValue() == 0){
				EbkCertificate record = new EbkCertificate();
				record.setEbkCertificateId(ec.getEbkCertificateId());
				record.setValid("false");
				ebkCertificateDAO.updateByPrimaryKeySelective(record);
			}
		}
		return true;
	}
	private boolean updateMergeEbkCertItem(OrdOrder order,List<EbkCertificate> certificates){
		for(EbkCertificate ec : certificates) {
			EbkCertificate entity = this.ebkCertificateDAO.selectByPrimaryKey(ec.getEbkCertificateId());
			if(!entity.isTicket()){
				continue;
			}
			List<OrdOrderItemMeta> list= ec.getOrdOrderItemMetaList();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("ebkCertificateId", ec.getEbkCertificateId());
			boolean update=false;
			for(OrdOrderItemMeta itemMeta:list){
				map.put("orderItemMetaId", itemMeta.getOrderItemMetaId());
				long count = this.ebkCertificateItemDAO.countCertItemByParam(map);
				if(count==0){
					EbkCertificateItem item = this.initEbkCertificateItem(itemMeta, order);
					item.setEbkCertificateId(ec.getEbkCertificateId());
					ebkCertificateItemDAO.updateOldItemByOrderItemMetaId(item.getOrderItemMetaId());
					Long pkId = this.ebkCertificateItemDAO.insert(item);
					item.setEbkCertificateItemId(pkId);
					item.setOrderItemMeta(itemMeta);
					entity.putEbkCertificateItem(item);
					update=true;
				}
			}
			
			if(update){
				entity.setVersion(entity.getVersion()+1);
				this.ebkCertificateDAO.updateByPrimaryKey(entity);
				//保存凭证数据
				saveOrderDataRev(entity,order,false,true);
			}
		}
		return true;
	}
	
	/**
	 * 更新凭证
	 * 
	 * @author: ranlongfei 2013-3-23 下午5:17:27
	 * @param ebkCertificates
	 * @param ordOrder 
	 * @return
	 */
	private boolean updateCertificate(OrdOrder ordOrder,AbstractEbkCertificateSet set ) {
		 List<EbkCertificate> ebkCertificates = set.getUpdateEbkCert();
		 
		 for(EbkCertificate ec : ebkCertificates) {
			List<EbkCertificateItem> items = this.ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(ec.getEbkCertificateId());
			for(OrdOrderItemMeta ooim : ec.getOrdOrderItemMetaList()) {
				EbkCertificateItem item = getMetaEquals(items, ooim); 
				if(item != null) {
						//修改
					this.updateEbkCertificateItem(item, ooim, ordOrder);
					this.ebkCertificateItemDAO.updateByPrimaryKey(item);
					//修改采购
					if(ec.getTargetId().longValue() != ooim.getBcertificateTarget().getTargetId().longValue()){
						ec.setSupplierId(ooim.getSupplierId());
						ec.setTargetId(ooim.getBcertificateTarget().getTargetId());
						ec.setSupplierName(ooim.getSupplier().getSupplierName());
						ec.setTargetName(ooim.getBcertificateTarget().getName());
						ec.setToFax(ooim.getBcertificateTarget().getFaxNo());
						ec.setToName(ooim.getSupplier().getSupplierName());
						ec.setToTel(ooim.getSupplier().getTelephone());
						ec.setMemo(ooim.getMemo());
						ec.setFaxStrategy(ooim.getBcertificateTarget().getFaxStrategy());
					}
				} else {
					//新增,只有需要合并的凭证才会新增
					item = this.initEbkCertificateItem(ooim, ordOrder);
					item.setEbkCertificateId(ec.getEbkCertificateId());
					ebkCertificateItemDAO.updateOldItemByOrderItemMetaId(item.getOrderItemMetaId());
					Long pkId = this.ebkCertificateItemDAO.insert(item);
					item.setEbkCertificateItemId(pkId);
				}
				ec.putEbkCertificateItem(item);
				item.setOrderItemMeta(ooim);
			}
			if(set.hasOrderModifyPerson()){
				this.initOrderInfo(ec, ordOrder);
			}
			
			//不定期订单，需要设置不定期相关信息到凭证
			ec = updateAperiodicEbkCertificateValue(ordOrder,ec);
			
			ec.setUserMemoStatus(set.getUserMemoStatus());
			ec.setVersion(ec.getVersion()+1);
			this.ebkCertificateDAO.updateByPrimaryKey(ec);
			//保存凭证数据
			saveOrderDataRev(ec,ordOrder,true,false);
//			//创建任务
//			createTask(ec, set, ordOrder, this.getOrdOrderItemMeta(ec.getOrdOrderItemMetaList()));
		}
		return true;
	}

	/**
	 * 取一个非附加产品采购产品
	 * @param ordOrderItemMetas
	 * @return
	 */
	private OrdOrderItemMeta getOrdOrderItemMeta(
			List<OrdOrderItemMeta> ordOrderItemMetas) {
		OrdOrderItemMeta ooitm = ordOrderItemMetas.get(0);
		for(OrdOrderItemMeta m : ordOrderItemMetas) {
			if(!m.isAdditionBranchMeta()) {
				ooitm = m;
				break;
			}
		}
		return ooitm;
	}
	
	/**
	 * 子子项是否在凭证子项列表存在
	 * 
	 * @author: ranlongfei 2013-3-23 下午5:29:51
	 * @param item
	 * @param metaList
	 * @return
	 */
	private EbkCertificateItem getMetaEquals(List<EbkCertificateItem> items, OrdOrderItemMeta ooim) {
		for(EbkCertificateItem item : items) {
			if(ooim.getOrderItemMetaId().equals(item.getOrderItemMetaId())) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 更新凭证上与订单当中有关的信息
	 * @param ebkCertificate
	 * @param ordOrder
	 */
	private void initOrderInfo(EbkCertificate ebkCertificate,OrdOrder ordOrder){
		ebkCertificate.setFilialeName(ordOrder.getFilialeName());
		OrdPerson person = ordOrder.getTravellerList().get(0);
		ebkCertificate.setTravellerName(person.getName());
		ebkCertificate.setMobile(person.getMobile());
		
		//如果是不定期产品,需要将不定期订单信息录入凭证中
		ebkCertificate = updateAperiodicEbkCertificateValue(ordOrder,ebkCertificate);
		
	}

	/**
	 * 
	 * @param ebkCertificate
	 * @param order
	 * @param deleteData
	 */
	private void saveOrderDataRev(EbkCertificate ebkCertificate,OrdOrder order,boolean deleteData,boolean updateItems){
		if(deleteData){//暂时先使用全部删除，再做更新相应的值处理
			if(ebkCertificate.isTicket()){
				ebkOrderDataRevDAO.deleteTicketAllByEbkCertificateId(ebkCertificate.getEbkCertificateId(), order.getOrderId());
			}else{
				ebkOrderDataRevDAO.deleteAllByEbkCertificateId(ebkCertificate.getEbkCertificateId());
			}
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(EbkCertDataBuilder.EBK_CERTIFICATE, ebkCertificate);
		params.put(EbkCertDataBuilder.ORD_ORDER, order);
		EbkCertDataBuilder builder = EbkCertBuilderFactory.create(params);
		if(updateItems && ebkCertificate.isTicket()){
			builder.updateOnlyItems();
		}
		builder.makeData();
		
		createData(builder.getDataRevList());
	}

	private void createData(List<EbkOrderDataRev> list) {
		for(EbkOrderDataRev rev:list){
			if(rev.getDataId() != null && rev.getDataId().longValue() > 0 ){
				ebkOrderDataRevDAO.updateByPrimaryKey(rev);
			}else {
				ebkOrderDataRevDAO.insert(rev);
			}
		}
	}
	/**
	 * 初始化凭证表主要信息
	 * 
	 * @author: ranlongfei 2013-3-23 下午5:51:06
	 * @param ordOrderItemMeta
	 * @param sbct
	 * @param ebkCertificateType
	 * @param oldCertificateId
	 * @return
	 */
	private EbkCertificate initEbkCertificate(EbkCertificate e, OrdOrderItemMeta ordOrderItemMeta){
		SupBCertificateTarget target = ordOrderItemMeta.getBcertificateTarget();
		e.setSupplierId(ordOrderItemMeta.getSupplierId());
		e.setSupplierName(ordOrderItemMeta.getSupplier().getSupplierName());
		e.setTargetId(target.getTargetId());
		e.setCertificateStatus(Constant.EBK_TASK_STATUS.CREATE.toString());
		e.setProductType(ordOrderItemMeta.getProductType());
//		e.setEbkCertificateType(ebkCertificateType);
		e.setVisitTime(ordOrderItemMeta.getVisitTime());
		e.setTargetName(target.getName());
		e.setSubProductType(ordOrderItemMeta.getSubProductType());
		//供应商在界面上设置的资源保留时间
		//e.setRetentionTime(ordOrderItemMeta.getRetentionTime());
		e.setToFax(target.getFaxNo());
		e.setToName(ordOrderItemMeta.getSupplier().getSupplierName());
		e.setToTel(ordOrderItemMeta.getSupplier().getTelephone());
		e.setMemo(ordOrderItemMeta.getMemo());
		e.setFaxStrategy(target.getFaxStrategy());
		e.setPaymentTarget(ordOrderItemMeta.getPaymentTarget());
		e.setVersion(1L);
		return e;
	}
	
	/**
	 * 初始化凭证子内容
	 * 
	 * @author: ranlongfei 2013-3-23 下午5:51:03
	 * @param ordOrderItemMeta
	 * @param ordOrder
	 * @return
	 */
	private EbkCertificateItem initEbkCertificateItem(OrdOrderItemMeta ooim, OrdOrder ordOrder){
		EbkCertificateItem e = new EbkCertificateItem();
		e.setCreateTime(new Date());
		e.setIsNew("true");
		this.updateEbkCertificateItem(e, ooim, ordOrder);
		e.setProductName(this.getItemProd(ordOrder, ooim.getOrderItemId()));
		return e;
	}

	private String getItemProd(final OrdOrder order,final Long ordOrderItemProd) {
		OrdOrderItemProd itemProd = (OrdOrderItemProd) CollectionUtils.find(
				order.getOrdOrderItemProds(), new Predicate() {
					@Override
					public boolean evaluate(Object arg0) {
						return ordOrderItemProd
								.equals(((OrdOrderItemProd) arg0)
										.getOrderItemProdId());
					}
				});
		return itemProd.getProductName();
	}
	/**
	 * 修改凭证子内容
	 * 
	 * @author: ranlongfei 2013-3-23 下午5:50:59
	 * @param e
	 * @param ooim
	 * @param ordOrder
	 */
	private void updateEbkCertificateItem(EbkCertificateItem e, OrdOrderItemMeta ooim, OrdOrder ordOrder){
		e.setOrderId(ooim.getOrderId());
		e.setOrderItemMetaId(ooim.getOrderItemMetaId());
		e.setMetaBranchId(ooim.getMetaBranchId());
		e.setFaxMemo(ooim.getFaxMemo());
		e.setOrdLastCancelTime(ordOrder.getLastCancelTime());
		e.setTotalSettlementPrice(ooim.getTotalSettlementPrice());
		e.setSettlementPrice(ooim.getSettlementPrice());
		e.setMetaProductId(ooim.getMetaProductId());
		e.setMetaProductName(ooim.getProductName());
		e.setSupplierNo(ooim.getSupplierName());
		e.setQuantity(ooim.getQuantity()*ooim.getProductQuantity());
		e.setNights(ooim.getNights());
		e.setIsResourceSendFax(ooim.getIsResourceSendFax());
		e.setVisitTime(ooim.getVisitTime());
	}
	

	/**
	 * 不定期订单，设置不定期相关信息到凭证
	 * 
	 * @param ordOrder
	 * @param ebkCertificate
	 * @return
	 */
	private EbkCertificate updateAperiodicEbkCertificateValue(OrdOrder ordOrder,EbkCertificate ebkCertificate ){
		 if(!ordOrder.IsAperiodic()){
			 return ebkCertificate;
		 }
		 ebkCertificate.setIsAperiodic(ordOrder.getIsAperiodic());
		 List<OrdOrderItemMeta> ordOrderItemMetas = ordOrder.getAllOrdOrderItemMetas();
		 for(OrdOrderItemMeta item : ordOrderItemMetas){
			if(!ebkCertificate.getProductType().equalsIgnoreCase(item.getProductType())||ebkCertificate.getSupplierId().longValue()!=item.getSupplierId().longValue()){
				continue;
			}
			//门票凭证信息，需从pass_port_code里面获取，酒店，线路需要从ord_order_item_meta_aperiodic取
			if(Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(item.getProductType())){
				List<PassCode> passCodes = passCodeDAO.getPassCodeByOrderId(item.getOrderId());
				if(passCodes.isEmpty()){
					continue;
				}
				PassCode code = passCodes.get(0);
				PassPortCode portCode = new PassPortCode();
				portCode.setCodeId(code.getCodeId());
				List<PassPortCode> passPortCodes = passPortCodeDAO.queryPassPortCodeByParam(portCode);
				if(passPortCodes.isEmpty()){
					continue;
				}
				portCode = passPortCodes.get(0);
				ebkCertificate.setPasswordCertificate(code.getAddCode());
				ebkCertificate.setUseStatus(Constant.PASSCODE_USE_STATUS.USED.name().equalsIgnoreCase(portCode.getStatus())?Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED.name():Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
				ebkCertificate.setValidBeginTime(ordOrder.getValidBeginTime());
				ebkCertificate.setValidEndTime(ordOrder.getValidEndTime());
			}else{
				OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicDAO.selectOrderAperiodicByOrderItemId(item.getOrderItemMetaId());
				if(aperiodic==null){
					continue;
				}
				ebkCertificate.setPasswordCertificate(aperiodic.getPasswordCertificate());
				if(ordOrder.isPaymentSucc() && ordOrder.isPayToLvmama() && ordOrder.isApprovePass() && !ordOrder.isCanceled()) {
					if(Constant.APERIODIC_ACTIVATION_STATUS.INVALID.name().equalsIgnoreCase(aperiodic.getActivationStatus())
							||Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name().equalsIgnoreCase(aperiodic.getActivationStatus())){
						ebkCertificate.setUseStatus(Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
						ebkCertificate.setCertificateStatus(Constant.EBK_TASK_STATUS.CREATE.name());
						ebkCertificate.setVisitTime(null);
					} else {
						ebkCertificate.setUseStatus(aperiodic.getActivationStatus());
						ebkCertificate.setCertificateStatus(Constant.EBK_TASK_STATUS.ACCEPT.name());
						ebkCertificate.setVisitTime(ordOrder.getVisitTime());
					}
				} else {
					ebkCertificate.setUseStatus(aperiodic.getActivationStatus());
					ebkCertificate.setCertificateStatus(Constant.EBK_TASK_STATUS.CANCEL.name());
					ebkCertificate.setVisitTime(null);
				}
				ebkCertificate.setValidBeginTime(ordOrder.getValidBeginTime());
				ebkCertificate.setValidEndTime(ordOrder.getValidEndTime());
			}
		 }
		 return ebkCertificate;
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

	public EbkCertificateTicketLogic getEbkCertificateTicketLogic() {
		return ebkCertificateTicketLogic;
	}

	public void setEbkCertificateTicketLogic(EbkCertificateTicketLogic ebkCertificateTicketLogic) {
		this.ebkCertificateTicketLogic = ebkCertificateTicketLogic;
	}

	public EbkCertificateHotelLogic getEbkCertificateHotelLogic() {
		return ebkCertificateHotelLogic;
	}

	public void setEbkCertificateHotelLogic(EbkCertificateHotelLogic ebkCertificateHotelLogic) {
		this.ebkCertificateHotelLogic = ebkCertificateHotelLogic;
	}

	public EbkCertificateRouteLogic getEbkCertificateRouteLogic() {
		return ebkCertificateRouteLogic;
	}

	public void setEbkCertificateRouteLogic(EbkCertificateRouteLogic ebkCertificateRouteLogic) {
		this.ebkCertificateRouteLogic = ebkCertificateRouteLogic;
	}

	public EbkTaskLogic getEbkTaskLogic() {
		return ebkTaskLogic;
	}

	public void setEbkTaskLogic(EbkTaskLogic ebkTaskLogic) {
		this.ebkTaskLogic = ebkTaskLogic;
	}

	public PassCodeDAO getPassCodeDAO() {
		return passCodeDAO;
	}

	public void setPassCodeDAO(PassCodeDAO passCodeDAO) {
		this.passCodeDAO = passCodeDAO;
	}

	public PassPortCodeDAO getPassPortCodeDAO() {
		return passPortCodeDAO;
	}

	public void setPassPortCodeDAO(PassPortCodeDAO passPortCodeDAO) {
		this.passPortCodeDAO = passPortCodeDAO;
	}

	public OrderItemMetaAperiodicDAO getOrderItemMetaAperiodicDAO() {
		return orderItemMetaAperiodicDAO;
	}

	public void setOrderItemMetaAperiodicDAO(OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO) {
		this.orderItemMetaAperiodicDAO = orderItemMetaAperiodicDAO;
	}

	public EbkTaskService getEbkTaskService() {
		return ebkTaskService;
	}

	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}
	public EbkSuperClientService getEbkSuperClientService() {
		return ebkSuperClientService;
	}
	public void setEbkSuperClientService(EbkSuperClientService ebkSuperClientService) {
		this.ebkSuperClientService = ebkSuperClientService;
	}
}
