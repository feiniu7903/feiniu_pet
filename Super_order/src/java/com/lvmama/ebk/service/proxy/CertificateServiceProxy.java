package com.lvmama.ebk.service.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.EbkSuperClientService;

public class CertificateServiceProxy implements CertificateService {

	private static final Log log = LogFactory.getLog(CertificateServiceProxy.class);
	private OrderService orderServiceProxy;
	private EbkCertificateService ebkCertificateService;
	private BCertificateTargetService bCertificateTargetService;
	private SupplierService supplierService;
	private ComLogService comLogRemoteService;
	private EbkTaskService ebkTaskService;
	private EbkSuperClientService ebkSuperClientService;

	@Override
	public boolean createCertificateEbkFaxTaskWithCertId(Long ebkCertificateId, String operator) {
		// 手工生成传真任务
		EbkCertificate cert = ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(ebkCertificateId);
		if(cert == null) {
			return false;
		}
		Long orderId = null;
		Set<Long> midList = new HashSet<Long>();
		for(EbkCertificateItem item : cert.getEbkCertificateItemList()) {
			midList.add(item.getOrderItemMetaId());
			orderId = item.getOrderId();
		}
		if(orderId == null || midList.size() < 1) {
			return false;
		}
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		List<OrdOrderItemMeta> mList = new ArrayList<OrdOrderItemMeta>();
		for(OrdOrderItemMeta im : ordOrder.getAllOrdOrderItemMetas()) {
			if(midList.contains(im.getOrderItemMetaId())) {
				mList.add(im);
			}
		}
		if(mList.size() < 1) {
			return false;
		}
		ordOrder.setAllOrdOrderItemMetas(mList);
		boolean result = ebkCertificateService.createCertificateEbkFaxTask(cert, ordOrder, EBK_CHANGE_TO_FAX, operator);
		if(result) {
			for(EbkCertificateItem item : cert.getEbkCertificateItemList()){
				orderServiceProxy.updateCertificateStatusAndTypeOrConfirmChannel(
						item.getOrderItemMetaId(),
						cert.getCertificateStatus(), 
						cert.getEbkCertificateType(),
						Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.CHANGE_FAX.name()
						);
			}
		}
		return result; 
	}
	@Override
	public boolean createCertificateEbkFaxTaskWithMetaId(Long orderItemMetaId, String operator) {
		EbkCertificateItem item = ebkCertificateService.selectEbkCertificateItemByOrderItemMetaId(orderItemMetaId);
		if(item != null) {
			return this.createCertificateEbkFaxTaskWithCertId(item.getEbkCertificateId(), operator);
		}
		return false;
	}
	@Override
	public boolean createSupplierCertificate(Long orderId,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList) {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		return createSupplierCertificate(ordOrder,ebkCertificateEvent,userMemoStatus, orderItemMetaIdList);
	}
	@Override
	public boolean createSupplierCertificate(OrdOrder ordOrder,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList) {
		log.info("createSupplierCertificate with order:" + ordOrder.getOrderId());
		//查询凭证对象
		Map<Long, SupBCertificateTarget> sbct = this.getSupBCertificateTarget(ordOrder);
		//
		createCertAndUpdateOrderMeta(ordOrder,sbct,ebkCertificateEvent,userMemoStatus, orderItemMetaIdList);
		return true; 
	}

	@Override
	public boolean reCreateSupplierCertificate(Long orderItemMetaId, String operator,String ebkCertificateEvent) {
		log.info("reCreateSupplierCertificate with orderItemMetaId:" + orderItemMetaId);
		// 1,查出老的拒绝的任务
		OrdOrder ordOrder = findOrderAndItemMeta(orderItemMetaId);
		Map<Long, SupBCertificateTarget> sbct = this.getSupBCertificateTarget(ordOrder);
		List<EbkCertificate> result = createCertAndUpdateOrderMeta(ordOrder,sbct,ebkCertificateEvent,null, null);
		for(EbkCertificate e : result) {
			for(EbkCertificateItem i : e.getEbkCertificateItemList()) {
				comLogRemoteService.insert(Constant.COM_LOG_OBJECT_TYPE.EBK_ORDER_TASK.name(),
						ordOrder.getOrderId(), i.getOrderItemMetaId(), operator,
						e.getCertificateTypeStatus(),
						"重发订单", "重发订单", Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name());
			}
		}
		return true; 
	}
	/**
	 * 根据子子项ID查出对应的订单，并根据已经存在的凭证内容再次初始化此子子项列表
	 * @author: ranlongfei 2013-5-2 上午10:34:43
	 * @param orderItemMetaId
	 * @return
	 */
	private OrdOrder findOrderAndItemMeta(Long orderItemMetaId) {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrdOrderItemMetaId(orderItemMetaId);
		
		List<OrdOrderItemMeta> inItems = new ArrayList<OrdOrderItemMeta>();
		EbkCertificateItem item = ebkCertificateService.selectEbkCertificateItemByOrderItemMetaId(orderItemMetaId);
		if(item != null) {
			EbkCertificate cert = ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(item.getEbkCertificateId());
			for(OrdOrderItemMeta iMeta : ordOrder.getAllOrdOrderItemMetas()) {
				for(EbkCertificateItem ie : cert.getEbkCertificateItemList()) {
					if(ie.getOrderItemMetaId().equals(iMeta.getOrderItemMetaId())) {
						inItems.add(iMeta);
					}
				}
			}
		} else {
			inItems = ordOrder.getAllOrdOrderItemMetas();
		}
		ordOrder.setAllOrdOrderItemMetas(inItems);
		return ordOrder;
	}
	
	private List<EbkCertificate> createCertAndUpdateOrderMeta(OrdOrder ordOrder, Map<Long, SupBCertificateTarget> sbct,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList) {
		List<EbkCertificate> result = ebkCertificateService.createSupplierCertificate(ordOrder,sbct,ebkCertificateEvent,userMemoStatus, orderItemMetaIdList);
		for(EbkCertificate cert : result) {
			if(cert == null) {
				continue;
			}
			List<EbkCertificateItem> iList = cert.getEbkCertificateItemList();
			if(iList == null || iList.size() < 1) {
				continue;
			}
			for(EbkCertificateItem item : iList){
				orderServiceProxy.updateCertificateStatusAndTypeOrConfirmChannel(
						item.getOrderItemMetaId(),
						cert.getCertificateStatus(), 
						cert.getEbkCertificateType(),
						""
						);
			}

			// 订单重发时修改VST订单中间表对应的凭证状态
			EbkTask ebkTask = ebkTaskService.selectByEbkCertificateId(cert.getEbkCertificateId());
			if(null!=ebkTask){
				ebkTask.setEbkCertificate(cert);
				ebkSuperClientService.updateEbkSuperTask(ebkTask);
			}
		}
		return result; 
	}
	/**
	 * 
	 * @param ordOrderItemMetas
	 * @return  key targetId
	 * 			value SupBCertificateTarget
	 */
	private Map<Long, SupBCertificateTarget> getSupBCertificateTarget(OrdOrder order){
		Map<Long, SupBCertificateTarget> s = new HashMap<Long, SupBCertificateTarget>();
		SupBCertificateTarget sct = null;
		for (OrdOrderItemMeta ordOrderItemMeta : order.getAllOrdOrderItemMetas()) {
			sct = bCertificateTargetService.getSuperMetaBCertificateByMetaProductId(ordOrderItemMeta.getMetaProductId());
			sct.setSupplier(supplierService.getSupplier(sct.getSupplierId()));
			s.put(sct.getTargetId(), sct);
			ordOrderItemMeta.setBcertificateTarget(sct);
		}
		return s;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}

	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

	public BCertificateTargetService getbCertificateTargetService() {
		return bCertificateTargetService;
	}

	public void setbCertificateTargetService(BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
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
