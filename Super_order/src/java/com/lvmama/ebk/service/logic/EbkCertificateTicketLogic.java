package com.lvmama.ebk.service.logic;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.vo.Constant;

public class EbkCertificateTicketLogic extends AbstractEbkCertificate{

	@Override
	public void addCertificateSet(OrdOrder ordOrder, OrdOrderItemMeta ooim, Map<Long, SupBCertificateTarget> sbctMap, AbstractEbkCertificateSet set) {
		//1.	门票订单、订单子子项product_type=‘TICKET’、支付给驴妈妈、需资源确、资源审核通过
		// NO
		//3.	门票订单、订单子子项product_type=‘TICKET’ 、支付给驴妈妈、不需资源确、支付后
		if(set.hasRetransmission()){
			retransmissionCertificate(ordOrder, ooim, set, sbctMap);
		}else {
			if(ordOrder.IsAperiodic()){
				if(set.hasOrderPayment()||set.hasOrderCancel()||set.isUpdateAperiodicOrder()){
					createSupplierCertificate(ordOrder,ooim,set, sbctMap);
				}
			}else if(ordOrder.isPayToLvmama() && (set.hasOrderApprove() || set.hasOrderPayment())) {
				createSupplierCertificate(ordOrder,ooim,set, sbctMap);
			}else if(set.hasOrderCancel() || set.hasOrderModifyPerson()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}else if(set.hasOrderModifySettlementPrice()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}else if(set.hasOrderMemoChange()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}else if(set.hasOrderApprove() && ordOrder.isPayToSupplier() && ordOrder.isApprovePass()) {
				createSupplierCertificate(ordOrder,ooim,set, sbctMap);
			}
		}
	}
	
	@Override
	public void createSupplierCertificate(OrdOrder ordOrder,OrdOrderItemMeta ooim,AbstractEbkCertificateSet set,Map<Long, SupBCertificateTarget> sbctMap) {
		if(!set.hasOrderCancel() && ordOrder.isCanceled()){
			return;
		}
		if(ooim.isApproveResourceSendFax() && !ordOrder.isApprovePass()){
			return;
		}
		if(ooim.isPayedSendFax()  && !(ordOrder.isApprovePass() && ordOrder.isPaymentSucc())){
			return;
		}
		EbkCertificate ec = null;
		//支付给驴妈妈、资源审核通过、支付完成 生成凭证，并且生成传真任务。
		//支付给供应商、生成凭证。
		//合并逻辑：支付方式相同、供应商、凭证对象、产品类型、发送策略、游玩时间。
		//查询是否已生成凭证
		ec = this.ebkCertificateDAO.selectNearbyEbkCertificateByOrderItemMetaId(ooim.getOrderItemMetaId());
		if(set.hasOrderMemoChange() || set.hasOrderModifySettlementPrice()) {
			if(!hasContainChangedOrdOrderItemMeta(ooim, set, ec)){
				return;
			}
		}
		if(set.hasOrderCancel() && ordOrder.isCanceled()){
			orderCancel(ordOrder, ooim, set, ec);
		}else if(ec == null){
			//如果修改
			if(set.hasOrderModifySettlementPrice()){
				return;
			}
			EbkCertificate	mergeEbkCertificate = getMergeEbkCert(ooim, ordOrder);
			//订单合并策略查询是否合并（立即发送不合并）
			if(mergeEbkCertificate != null){
				ebkCertificateUpdate(mergeEbkCertificate, set.getMergeEbkCert(), ooim);
			}else {				
				//确认单
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}
			return;
		}else if(ec != null && ec.getSupplierId().longValue()!= ooim.getSupplierId().longValue()){
			super.newConfirm(ooim,set.getNewEbkCert(),null);
			 if(ec.hasCertificateStatusCreate()){
				 set.putNotValidEbkCert(ec);
			 }
		}else if(set.isUpdateAperiodicOrder()){
			//修改凭证数据
			if(!hasContainChangedOrdOrderItemMeta(ooim, set, ec)){
				return;
			}
			ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
		}else {
			EbkFaxTask task = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
			if(task != null){
				if(set.hasOrderModifySettlementPrice()&&!ooim.getBcertificateTarget().hasShowSettlePriceFlag()){
					//该状态不处理
				}else if(!task.hasFaxSended() && ooim.isNeedSendFax() && ooim.getBcertificateTarget().hasShowSettlePriceFlag() && set.hasOrderModifySettlementPrice()){
					//修改凭证数据
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}else if(task.hasFaxSended() && !ooim.getBcertificateTarget().hasShowSettlePriceFlag() && set.hasOrderModifySettlementPrice()){
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}else if(task != null && task.hasFaxSended()){
					//变更单
					this.newChange(ooim,set.getNewEbkCert(),ec);
				}else {
					//修改凭证数据
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}
			}else {
				//修改凭证数据
				ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
			}
		}
	}

	private void orderCancel(OrdOrder ordOrder, OrdOrderItemMeta ooim,
			AbstractEbkCertificateSet set, EbkCertificate ec) {
		if(ec != null && ordOrder.isCanceled()){
			EbkFaxTask task = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
			if(task != null ){
				if(task.hasFaxSended()){
					//取消
					this.newCancel(ooim,set.getNewEbkCert(),ec);
				}else if(ec.hasEbkCertificateTypeConfirm()){
					if(ec.hasSingleOrderCert() && !task.hasFaxSended()){
						initEbkCertificateItem(ordOrder, ooim, set,ec);
					}else {
						initEbkCertificateItem(ordOrder, ooim, set,ec);//如果整个凭证子项都标示为无效，凭证会标示为无效。
					}
				}else if(ec.hasEbkCertificateTypeChange() && !task.hasFaxSended()){
					set.putNotValidEbkCert(ec);
					//取消
					this.newCancel(ooim,set.getNewEbkCert(),ec);
				}else {
					initEbkCertificateItem(ordOrder, ooim, set,ec);
				}
			}else {
				initEbkCertificateItem(ordOrder, ooim, set,ec);
			}
		}
	}

	private void initEbkCertificateItem(OrdOrder ordOrder,
			OrdOrderItemMeta ooim, AbstractEbkCertificateSet set,
			EbkCertificate ebkCertificate) {
		EbkCertificateItem item = new EbkCertificateItem();
		item.setOrderId(ordOrder.getOrderId());
		item.setEbkCertificateId(ebkCertificate.getEbkCertificateId());
		item.setOrderItemMeta(ooim);
		item.setOrderItemMetaId(ooim.getOrderItemMetaId());
		set.putCancelEbkCertificateItems(item);
	}
	/**
	 * 查找需要合并的凭证
	 * 
	 * @author: ranlongfei 2013-4-15 下午4:51:03
	 * @param ooim
	 * @return
	 */
	private EbkCertificate getMergeEbkCert(OrdOrderItemMeta ooim, OrdOrder order) {
		if(StringUtils.isNotEmpty(order.getTestOrderFlag()) && "true".equalsIgnoreCase(order.getTestOrderFlag())) {
			return null;
		}
		if(Constant.FAX_STRATEGY.MANUAL_SEND.name().equals(ooim.getBcertificateTarget().getFaxStrategy())||Constant.FAX_STRATEGY.IMMEDIATELY.name().equals(ooim.getBcertificateTarget().getFaxStrategy())) {
			return null;
		}
		EbkCertificate	mergeEbkCertificate = this.ebkCertificateDAO.selectTicketMergeEbkCertificate(ooim.getSupplierId(),ooim.getBcertificateTarget().getTargetId(),ooim.getBcertificateTarget().getFaxStrategy(),ooim.getVisitTime());
		//订单合并策略查询是否合并（立即发送不合并）
		if(mergeEbkCertificate == null) {
			return null;
		}
		if(StringUtils.isNotEmpty(mergeEbkCertificate.getTestOrder()) && "true".equalsIgnoreCase(mergeEbkCertificate.getTestOrder())) {
			return null;
		}
		if(!mergeEbkCertificate.hasEbkCertificateTypeConfirm()) {
			return null;
		}
		if(!mergeEbkCertificate.hasCertificateStatusCreate()) {
			return null;
		}
		EbkFaxTask task = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(mergeEbkCertificate.getEbkCertificateId());
		if(task != null && task.hasFaxSended()){
			return null;
		}
		return mergeEbkCertificate;
	}
	@Override
	public void retransmissionCertificate(OrdOrder ordOrder,
			OrdOrderItemMeta ooim, AbstractEbkCertificateSet set,
			Map<Long, SupBCertificateTarget> sbctMap) {
		EbkCertificate ebkCertificate = this.ebkCertificateDAO.selectNearbyEbkCertificateByOrderItemMetaId(ooim.getOrderItemMetaId());
		if(ebkCertificate == null){
			EbkCertificate	mergeEbkCertificate = getMergeEbkCert(ooim, ordOrder);
			//订单合并策略查询是否合并（立即发送不合并）
			if(mergeEbkCertificate != null){
				ebkCertificateUpdate(mergeEbkCertificate, set.getMergeEbkCert(), ooim);
			}else {
				//确认单
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}
			return;
		}
		EbkFaxTask task = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ebkCertificate.getEbkCertificateId());
		if(task == null) {
			//确认单
			super.newConfirm(ooim,set.getNewEbkCert(),ebkCertificate);
			return;
		}
		if(task.hasFaxSended()){
			//变更单
			this.newChange(ooim,set.getNewEbkCert(),ebkCertificate);
			return;
		}
		//修改凭证数据
		ebkCertificateUpdate(ebkCertificate, set.getUpdateEbkCert(), ooim);
		return;
	}

	 
}
