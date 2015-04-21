/**
 * 
 */
package com.lvmama.ebk.service.logic;

import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;

/**
 * @author yangbin
 *
 */
public class EbkCertificateRouteLogic extends AbstractEbkCertificate {
	@Override
	public void addCertificateSet(OrdOrder ordOrder, OrdOrderItemMeta ooim, Map<Long, SupBCertificateTarget> sbctMap, AbstractEbkCertificateSet set) {
		if(set.hasRetransmission()){
			retransmissionCertificate(ordOrder, ooim, set, sbctMap);
		}else if(ordOrder.isPayToSupplier()){
			if(set.hasOrderApprove()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}else if(set.hasOrderCancel()
					|| set.hasOrderModifyPerson()
					|| set.hasOrderModifySettlementPrice()
					||set.hasOrderMemoChange()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			} 
		}else if(ordOrder.isPayToLvmama()){
			//如果是不定期订单，只在支付完成，或者取消订单的时候创建凭证
			if(ordOrder.IsAperiodic()){
				if(set.hasOrderPayment()||set.hasOrderCancel()||set.isUpdateAperiodicOrder()){
					createSupplierCertificate(ordOrder,ooim,set, sbctMap);
				}
			} 
			//询位凭证生成：线路订单、订单子子项product_type=‘ROUTE’、需要资源确认
			else if(set.hasOrderCreate() && ooim.isNeedResourceConfirm() && !ooim.isApproveResourceAmple()) {
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}
			//1.确认单	线路订单、订单子子项product_type=‘ROUTE’、无需资源确认、用户支付
			else if(set.hasOrderPayment() && ordOrder.isPaymentSucc()) {
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}
			else if (set.hasOrderApprove() && (ordOrder.hasNeedPrePay() || ordOrder.isPaymentSucc())) {
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}
			//取消，变更单产生
			else if(set.hasOrderCancel()
					|| set.hasOrderModifyPerson()
					|| set.hasOrderModifySettlementPrice()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}else if(set.hasOrderMemoChange()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			}
		} 
	}
	@Override
	public void createSupplierCertificate(OrdOrder ordOrder,OrdOrderItemMeta ooim,AbstractEbkCertificateSet set,Map<Long, SupBCertificateTarget> sbctMap) {
		if(!set.hasOrderCancel() && ordOrder.isCanceled()){
			return;
		}
		EbkCertificate ec = null;
		//支付给驴妈妈、资源审核通过、支付完成 生成凭证，并且生成传真任务。
		//支付给供应商、生成凭证。
		//合并逻辑：支付方式相同、供应商、凭证对象、产品类型、发送策略、游玩时间。
		//查询是否已生成凭证
		ec = this.ebkCertificateDAO.selectNearbyEbkCertificateByOrderItemMetaId(ooim.getOrderItemMetaId());
		//如果修改
		if(set.hasOrderMemoChange() || set.hasOrderModifySettlementPrice()) {
			if(!hasContainChangedOrdOrderItemMeta(ooim, set, ec)){
				return;
			}
		}
		if(set.hasOrderCancel() && ordOrder.isCanceled()){
			orderCancel(ordOrder, ooim, set, ec);
		}else if(ec == null){
			if(set.hasOrderModifySettlementPrice()){
				return;
			}
			createEbkCert(ordOrder, ooim, set);
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
			if(set.hasOrderModifySettlementPrice()  && (ec.hasCertificateStatusReject()||!ooim.getBcertificateTarget().hasShowSettlePriceFlag())) {
				return;
			}
			EbkFaxTask task = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
			if(ec.hasEbkCertificateTypeEnquiry()){//存在询位单
				if(ordOrder.isPaymentSucc() && ordOrder.isApprovePass()){
					super.newConfirm(ooim,set.getNewEbkCert(),ec);
				}else{
					//修改凭证数据
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}
			}else if(ec.hasEbkCertificateTypeConfirm()){
				if(task != null && task.hasFaxSended()){
					super.newChange(ooim,set.getNewEbkCert(),ec);
				}else if(!ec.hasCertificateStatusCreate() && ordOrder.isPaymentSucc()){
					super.newChange(ooim,set.getNewEbkCert(),ec);
				}else{
					//修改凭证数据
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}
			}else if(ec.hasEbkCertificateTypeChange()){
				if(ooim.isNeedSendFax()){
					if(task.hasFaxSended()){
						super.newChange(ooim,set.getNewEbkCert(),ec);
					}else{
						//修改凭证数据
						ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
					}
				}else if(ooim.hasSupplier()){//
					if(!ec.hasCertificateStatusCreate()){
						super.newChange(ooim,set.getNewEbkCert(),ec);
					}else{
						//修改凭证数据
						ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
					}
				}
			}else{
				set.putNotValidEbkCert(ec);
			}
		}
	}
	private void createEbkCert(OrdOrder ordOrder, OrdOrderItemMeta ooim,
			AbstractEbkCertificateSet set) {
		if(ooim.hasSupplier()){//ebk的处理方式
			if(ordOrder.isApprovePass() && ordOrder.isPaymentSucc()){
				//确认单
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}else if((!ordOrder.isApprovePass() && !ordOrder.hasNeedPrePay()) 
					|| (ordOrder.hasNeedPrePay() && ordOrder.isPaymentSucc())){
				//寻位单
				super.newEnquiry(ooim,set.getNewEbkCert(),null);
			}
		}else if(ooim.isNeedSendFax()){
			if(ooim.isApproveResourceSendFax()&&ordOrder.isApprovePass()){
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}else if(ooim.isPayedSendFax()&&ordOrder.isApprovePass()&&ordOrder.isPaymentSucc()){
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}
		}
	}
	private void orderCancel(OrdOrder ordOrder, OrdOrderItemMeta ooim,
			AbstractEbkCertificateSet set, EbkCertificate ec) {
		 
		if(ec != null && ordOrder.isCanceled()){
			EbkFaxTask faxTask = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
			EbkTask ebkTask = ebkTaskDAO.selectByEbkCertificateId(ec.getEbkCertificateId());
			//传真不产生询位单
			if(faxTask != null){
				//此if逻辑内部未发送不产生取消单，
				if(faxTask.hasFaxSended()){
					this.newCancel(ooim,set.getNewEbkCert(),ec);
				}else if(ec.hasEbkCertificateTypeChange()){
					if(!faxTask.hasFaxSended()){
						set.putNotValidEbkCert(ec);
					}
					this.newCancel(ooim,set.getNewEbkCert(),ec);
				}else{
					set.putNotValidEbkCert(ec);
				}
			}else if(ebkTask!=null){
				if (ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusAccept()){
					this.newCancel(ooim, set.getNewEbkCert(),ec);
				}else if(ec.hasEbkCertificateTypeChange()) {
					if(ec.hasCertificateStatusCreate()) {
						set.putNotValidEbkCert(ec);
					}
					this.newCancel(ooim, set.getNewEbkCert(),ec);
				} else if((ec.hasEbkCertificateTypeEnquiry() || ec.hasEbkCertificateTypeConfirm()) 
						&& ec.hasCertificateStatusCreate()){
					set.putCancelEbkCert(ec);
				}else {
					set.putNotValidEbkCert(ec);
				}
			}else{
				set.putNotValidEbkCert(ec);
			}
		}
	}

	@Override
	public void retransmissionCertificate(OrdOrder ordOrder,
			OrdOrderItemMeta ooim, AbstractEbkCertificateSet set,
			Map<Long, SupBCertificateTarget> sbctMap) {
		EbkCertificate ec = this.ebkCertificateDAO.selectNearbyEbkCertificateByOrderItemMetaId(ooim.getOrderItemMetaId());
		if(ec != null){
			if(ec.hasEbkCertificateTypeEnquiry() && ec.hasCertificateStatusReject()){
				this.newEnquiry(ooim, set.getNewEbkCert(), ec);
			} else if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusReject()){
				this.newConfirm(ooim, set.getNewEbkCert(), ec);
			} else if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusAccept()) {
				this.newChange(ooim,set.getNewEbkCert(),ec);
			} else if(ec.hasEbkCertificateTypeChange() && !ec.hasCertificateStatusCreate()) {
				this.newChange(ooim,set.getNewEbkCert(),ec);
			} else {
				ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
			}
			return;
		}
		if(ooim.hasSupplier()){//ebk的处理方式
			if(ordOrder.isApprovePass() && ordOrder.isPaymentSucc()){
				//确认单
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}else if((!ordOrder.isApprovePass() && !ordOrder.hasNeedPrePay()) || (ordOrder.hasNeedPrePay() && ordOrder.isPaymentSucc())){
				//寻位单
				super.newEnquiry(ooim,set.getNewEbkCert(),null);
			}
			return;
		}
		if(ooim.isNeedSendFax()){
			if(ooim.isApproveResourceSendFax()&&ordOrder.isApprovePass()){
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}else if(ooim.isPayedSendFax()&&ordOrder.isApprovePass()&&ordOrder.isPaymentSucc()){
				super.newConfirm(ooim,set.getNewEbkCert(),null);
			}
			return;
		}
	}

}
