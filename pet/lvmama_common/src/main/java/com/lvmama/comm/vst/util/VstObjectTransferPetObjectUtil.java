package com.lvmama.comm.vst.util;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.vo.VstSuppContactVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

public final class VstObjectTransferPetObjectUtil {
	private VstObjectTransferPetObjectUtil(){}
	
	@Deprecated
	public static String SettleCycleTransferSettlementPeriod(final Long settleCycle){
		if(null==settleCycle){
			return null;
		}
		if(7>settleCycle.longValue()){
			return Constant.SETTLEMENT_PERIOD.PERORDER.name();
		}
		if(7==settleCycle.longValue()){
			return Constant.SETTLEMENT_PERIOD.PER_WEEK.name();
		}
		if(15==settleCycle.longValue()){
			return Constant.SETTLEMENT_PERIOD.PER_HALF_MONTH.name();
		}
		if(30==settleCycle.longValue()){
			return Constant.SETTLEMENT_PERIOD.PERMONTH.name();
		}
		if(120==settleCycle.longValue()){
			return Constant.SETTLEMENT_PERIOD.PERQUARTER.name();
		}
		return Constant.SETTLEMENT_PERIOD.PERORDER.name();
	}
	public static Long settlementPeriodTransferSettleCycle(final String settlementPeriod,final Long cycle){
		if(null==settlementPeriod){
			return null;
		}
		if(Constant.SETTLEMENT_PERIOD.PERORDER.name().equalsIgnoreCase(settlementPeriod)){
			return null!=cycle?cycle:1L;
		}
		if(Constant.SETTLEMENT_PERIOD.PER_WEEK.name().equalsIgnoreCase(settlementPeriod)){
			return 7L;
		}
		if(Constant.SETTLEMENT_PERIOD.PER_HALF_MONTH.name().equalsIgnoreCase(settlementPeriod)){
			return 15L;
		}
		if(Constant.SETTLEMENT_PERIOD.PERMONTH.name().equalsIgnoreCase(settlementPeriod)){
			return 30L;
		}
		if(Constant.SETTLEMENT_PERIOD.PERQUARTER.name().equalsIgnoreCase(settlementPeriod)){
			return 120L;
		}
		return null;
	}
	public static SupSettlementTarget VstSuppSupplierSettlementVoTransferSupSettlementTarget(final VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo){
		SupSettlementTarget supSettlementTarget = new SupSettlementTarget();
		supSettlementTarget.setTargetId(vstSuppSupplierSettlementVo.getSettleRuleId());
		supSettlementTarget.setName(vstSuppSupplierSettlementVo.getAccountName());
		supSettlementTarget.setSettlementPeriod(vstSuppSupplierSettlementVo.getSettlePeriod());
		//supSettlementTarget.setSettlementPeriod(SettleCycleTransferSettlementPeriod(vstSuppSupplierSettlementVo.getSettleCycle()));
		//supSettlementTarget.setAdvancedDays(vstSuppSupplierSettlementVo.get);
		supSettlementTarget.setBankAccountName(vstSuppSupplierSettlementVo.getAccountName());
		supSettlementTarget.setBankAccount(vstSuppSupplierSettlementVo.getAccountNo());
		supSettlementTarget.setBankName(vstSuppSupplierSettlementVo.getAccountBank());
		supSettlementTarget.setAlipayName(vstSuppSupplierSettlementVo.getAlipayName());
		supSettlementTarget.setAlipayAccount(vstSuppSupplierSettlementVo.getAlipayNo());
		//supSettlementTarget.getSupplier().setCompanyId(vstSuppSupplierSettlementVo.getLvAccSubject());
		supSettlementTarget.setType(vstSuppSupplierSettlementVo.getRuleType());
		supSettlementTarget.setBankLines(vstSuppSupplierSettlementVo.getUnionBankNo());
		supSettlementTarget.setSupplierId(vstSuppSupplierSettlementVo.getSupplierId());
		return supSettlementTarget;
	}
	public static SupSupplier VstSuppSupplierVoTransferSupSupplier(final VstSuppSupplierVo vstSuppSupplierVo){
		SupSupplier supSupplier = new SupSupplier();
		supSupplier.setSupplierId(vstSuppSupplierVo.getSupplierId());
		supSupplier.setSupplierName(vstSuppSupplierVo.getSupplierName());
		supSupplier.setSupplierType(vstSuppSupplierVo.getSupplierType());
		return supSupplier;
	}
	public static ComContact VstSuppContactVoTransferComContact(final VstSuppContactVo vstSuppContactVo){
		ComContact comContact = new ComContact();
		comContact.setContactId(vstSuppContactVo.getPersonId());
		comContact.setEmail(vstSuppContactVo.getEmail());
		comContact.setAddress(vstSuppContactVo.getAddress());
		comContact.setMemo(vstSuppContactVo.getPersonDesc());
		comContact.setMobilephone(vstSuppContactVo.getMobile());
		comContact.setName(vstSuppContactVo.getName());
		comContact.setSupplierId(vstSuppContactVo.getSupplierId());
		comContact.setTitle(vstSuppContactVo.getJob());
		comContact.setSex(vstSuppContactVo.getSex());
		comContact.setTelephone(vstSuppContactVo.getTel());
		return comContact;
	}
}
