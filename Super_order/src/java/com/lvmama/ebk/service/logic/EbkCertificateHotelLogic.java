package com.lvmama.ebk.service.logic;

import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.vo.Constant;

public class EbkCertificateHotelLogic extends AbstractEbkCertificate{
	private Logger logger = Logger.getLogger(EbkCertificateHotelLogic.class);
	
	@Override
	public void addCertificateSet(OrdOrder ordOrder, OrdOrderItemMeta ooim, Map<Long, SupBCertificateTarget> sbctMap, AbstractEbkCertificateSet set) {
		if(set.hasRetransmission()){
			retransmissionCertificate(ordOrder, ooim, set, sbctMap);
		}else if(ordOrder.isPayToSupplier()){
			logger.error("hotel paytosupplier");
		}else if(ordOrder.isPayToLvmama()) {
			//如果是不定期订单，只在支付完成，或者取消订单的时候创建凭证
			if(ordOrder.IsAperiodic()){
				if(set.hasOrderPayment()||set.hasOrderCancel()||set.isUpdateAperiodicOrder()){
					createSupplierCertificate(ordOrder, ooim, set, sbctMap);
				}
			} 
			//处理强制预授权支付的,产品确认单
			else if(set.hasOrderPayment() && ordOrder.hasNeedPrePay() && ordOrder.isPaymentSucc()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			//确认单产生
			}else if(set.hasOrderCreate() && !ordOrder.hasNeedPrePay()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			//支付后发传真，生成任务
			}else if(set.hasOrderPayment() && ooim.isPayedSendFax() && ordOrder.isPaymentSucc()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			//审核后发传真
			}else if(set.hasOrderApprove() && ooim.isApproveResourceSendFax() && ordOrder.isApprovePass()){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			// 客服在审核了订单，生成新凭证
			}else if(set.hasOrderApprove() && ordOrder.isApprovePass() 
					&&( Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CONFIRM_REJECT.name().equals(ooim.getCertificateTypeStatus())
						|| Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.CONFIRM_CREATE.name().equals(ooim.getCertificateTypeStatus())
						)
					){
				createSupplierCertificate(ordOrder, ooim, set, sbctMap);
			//取消单，变更单产生
			}else if(set.hasOrderCancel() 
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
		//查询是否已生成凭证
		EbkCertificate ec = this.ebkCertificateDAO.selectNearbyEbkCertificateByOrderItemMetaId(ooim.getOrderItemMetaId());
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
			//确认单
			super.newConfirm(ooim,set.getNewEbkCert(),null);
		//修改订单子子项才会产生
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
		}else{
			if(set.hasOrderModifySettlementPrice() && (ec.hasCertificateStatusReject()||!ooim.getBcertificateTarget().hasShowSettlePriceFlag())) {
				return;
			}
			EbkFaxTask faxTask = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
			EbkTask ebkTask = ebkTaskDAO.selectByEbkCertificateId(ec.getEbkCertificateId());
			if(faxTask != null){
				if(!faxTask.hasFaxSended()){
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}else if(set.hasOrderModifySettlementPrice()){
					SupBCertificateTarget s = set.getSupBCertificateTargetBySupplierId(ec.getTargetId());
					if(s.hasShowSettlePriceFlag() && ec.hasEbkCertificateTypeConfirm() && faxTask.hasFaxSended()){
						this.newChange(ooim,set.getNewEbkCert(),ec);
					}else if(s.hasShowSettlePriceFlag() && ec.hasEbkCertificateTypeChange() && faxTask.hasFaxSended()){
						this.newChange(ooim,set.getNewEbkCert(),ec);
					}else {
						ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
					}
				}else if(ec.hasEbkCertificateTypeChange() && (!ec.hasCertificateStatusCreate() || faxTask.hasFaxSended())){
					this.newChange(ooim,set.getNewEbkCert(),ec);
				}else if(ec.hasEbkCertificateTypeChange() && ec.hasCertificateStatusCreate()){
					//修改凭证数据
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}else if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusReject()){
					//确认单
					super.newConfirm(ooim,set.getNewEbkCert(),null);
				}else {
					this.newChange(ooim,set.getNewEbkCert(),ec);
				}
			}else if (ebkTask != null) {
				if(set.hasOrderModifySettlementPrice()){
					if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusReject()){
						super.newConfirm(ooim,set.getNewEbkCert(),null);
					}else if(ec.hasEbkCertificateTypeConfirm() && !ec.hasCertificateStatusCreate()){
						this.newChange(ooim,set.getNewEbkCert(),ec);
					}else if(ec.hasEbkCertificateTypeChange() && !ec.hasCertificateStatusCreate()){
						this.newChange(ooim,set.getNewEbkCert(),ec);
					}else {
						ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
					}
				}else if(ec.hasEbkCertificateTypeChange() && !ec.hasCertificateStatusCreate()){
					this.newChange(ooim,set.getNewEbkCert(),ec);
				}else if(ec.hasEbkCertificateTypeChange() && ec.hasCertificateStatusCreate()){
					//修改凭证数据
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}else if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusCreate()){
					ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
				}else if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusReject()){
					//确认单
					super.newConfirm(ooim,set.getNewEbkCert(),null);
				}else {
					this.newChange(ooim,set.getNewEbkCert(),ec);
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
			EbkFaxTask faxTask = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
			EbkTask ebkTask = ebkTaskDAO.selectByEbkCertificateId(ec.getEbkCertificateId());
			if(faxTask != null || ebkTask != null){
				if(faxTask!=null){
					if(ec.hasEbkCertificateTypeConfirm() && !faxTask.hasFaxSended()){
						set.putNotValidEbkCert(ec);
					}else if(ec.hasEbkCertificateTypeConfirm() && faxTask.hasFaxSended()){
						this.newCancel(ooim,set.getNewEbkCert(),ec);
					}else if(ec.hasEbkCertificateTypeChange() && !faxTask.hasFaxSended()){
						set.putNotValidEbkCert(ec);
						this.newCancel(ooim,set.getNewEbkCert(),ec);
					}else if(ec.hasEbkCertificateTypeChange() && faxTask.hasFaxSended()) {
						this.newCancel(ooim,set.getNewEbkCert(),ec);
					}else{
						set.putNotValidEbkCert(ec);
					}
				}
				if(ebkTask != null){
					if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusReject()){
						//this.newCancel(ooim,set.getNewEbkCert(),ec);
					//确认单，已处理，产生取消凭证
					}else if(ec.hasEbkCertificateTypeConfirm() && !ec.hasCertificateStatusCreate()){
						//取消
						this.newCancel(ooim,set.getNewEbkCert(),ec);
					} else if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusCreate()) {
						//客人取消
						set.putCancelEbkCert(ec);
					//变更单取消
					}else if(ec.hasEbkCertificateTypeChange()){
						if(ec.hasCertificateStatusCreate()) {
							set.putNotValidEbkCert(ec);
						}
						//取消
						this.newCancel(ooim,set.getNewEbkCert(),ec);
					}else {
						set.putNotValidEbkCert(ec);
					}
				}
			}else {
				set.putNotValidEbkCert(ec);
			}
		}
	}

	@Override
	public void retransmissionCertificate(OrdOrder ordOrder,
			OrdOrderItemMeta ooim, AbstractEbkCertificateSet set,
			Map<Long, SupBCertificateTarget> sbctMap) {
		if(ordOrder.isCanceled()) {
			return;
		}
		EbkCertificate ec = this.ebkCertificateDAO.selectNearbyEbkCertificateByOrderItemMetaId(ooim.getOrderItemMetaId());
		if(ec == null){
			//确认单
			super.newConfirm(ooim,set.getNewEbkCert(),null);
			return;
		} 
		EbkFaxTask task = ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ec.getEbkCertificateId());
		EbkTask ebkTask = ebkTaskDAO.selectByEbkCertificateId(ec.getEbkCertificateId());
		if((task != null && task.hasFaxSended()) || (ebkTask != null && !ec.hasCertificateStatusCreate())){
			if(ec.hasEbkCertificateTypeConfirm() && ec.hasCertificateStatusReject()){
				this.newConfirm(ooim, set.getNewEbkCert(), ec);
				return;
			}
			this.newChange(ooim,set.getNewEbkCert(),ec);
			return;
		}
		ebkCertificateUpdate(ec, set.getUpdateEbkCert(), ooim);
	}
	 
}
