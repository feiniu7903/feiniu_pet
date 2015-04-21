package com.lvmama.pet.sup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sup.dao.MetaSettlementDAO;
import com.lvmama.pet.sup.dao.SupSettlementTargetDAO;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

public class SettlementTargetServiceImpl implements SettlementTargetService{
	
	private Logger logger = Logger.getLogger(SettlementTargetServiceImpl.class);
	
	private MetaSettlementDAO metaSettlementDAO;
	private SupSettlementTargetDAO supSettlementTargetDAO;
	private SupSupplierDAO supSupplierDAO;
	private ComLogService comLogService;

	@Override
	public SupSettlementTarget getSupSettlementTargetBySupplierId(
			Long supplierId) {
		return this.supSettlementTargetDAO.getSupSettlementTargetBySupplierId(supplierId);
	}
	
	public Long addSettlementTarget(SupSettlementTarget supSettlementTarget,String operatorName) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("supplierId", supSettlementTarget.getSupplierId());
		long count=selectRowCount(map);
		SupSupplier ss=supSupplierDAO.selectByPrimaryKey(supSettlementTarget.getSupplierId());		
		supSettlementTarget.setName(ss.getSupplierName()+" 结算对象"+StringUtils.leftPad(String.valueOf(count+1), 2,'0'));
		if (Constant.SETTLEMENT_TARGET_TYPE.COMPANY.name().equals(
				supSettlementTarget.getType())) {
			supSettlementTarget
					.setPaymentType(Constant.SETTLEMENT_PAYMENT_TYPE.TRANSFER
							.name());
		}
		
		Long pk=supSettlementTargetDAO.insert(supSettlementTarget);
		comLogService.insert("SUP_SETTLEMENT_TARGET",supSettlementTarget.getSupplierId(), supSettlementTarget.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSuPSettlementTarget.name(),
				"创建结算对象", LogViewUtil.logNewStr(operatorName), "SUP_SUPPLIER");
		return pk;

	}

	public List<SupSettlementTarget> findSupSettlementTarget(Map param) {
		return supSettlementTargetDAO.findSupSettlementTarget(param);
	}

	public SupSettlementTarget getSettlementTargetById(long targetId) {
		return supSettlementTargetDAO.getSettlementTargetById(targetId);
	}

	public void updateSettlementTarget(SupSettlementTarget supSettlementTarget,String operatorName) {
		StringBuffer strBuf=new StringBuffer();
		SupSettlementTarget oldtarget = this.getSettlementTargetById(supSettlementTarget.getTargetId());
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getName()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getName()))){
			strBuf.append(LogViewUtil.logEditStr("对象名称", oldtarget.getName(), supSettlementTarget.getName()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getSettlementPeriod()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getSettlementPeriod()))){
			strBuf.append(LogViewUtil.logEditStr("结算周期", oldtarget.getZhSettlementPeriod(), supSettlementTarget.getZhSettlementPeriod()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getBankAccountName()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getBankAccountName()))){
			strBuf.append(LogViewUtil.logEditStr("开户名称", oldtarget.getBankAccountName(), supSettlementTarget.getBankAccountName()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getBankName()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getBankName()))){
			strBuf.append(LogViewUtil.logEditStr("开户银行", oldtarget.getBankName(), supSettlementTarget.getBankName()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getBankAccount()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getBankAccount()))){
			strBuf.append(LogViewUtil.logEditStr("开户账号", oldtarget.getBankAccount(), supSettlementTarget.getBankAccount()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getAlipayName()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getAlipayName()))){
			strBuf.append(LogViewUtil.logEditStr("支付宝账号", oldtarget.getAlipayName(), supSettlementTarget.getAlipayName()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getAlipayAccount()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getAlipayAccount()))){
			strBuf.append(LogViewUtil.logEditStr("支付宝用户名", oldtarget.getAlipayAccount(), supSettlementTarget.getAlipayAccount()));
		}
		if(!LogViewUtil.logIsEmptyStr(supSettlementTarget.getMemo()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getMemo()))){
			strBuf.append(LogViewUtil.logEditStr("备注", oldtarget.getMemo(), supSettlementTarget.getMemo()));
		}
		if (Constant.SETTLEMENT_TARGET_TYPE.COMPANY.name().equals(
				supSettlementTarget.getType())) {
			supSettlementTarget
					.setPaymentType(Constant.SETTLEMENT_PAYMENT_TYPE.TRANSFER
							.name());
		}
		supSettlementTargetDAO.updateByPrimaryKey(supSettlementTarget);
		if(!"".equals(strBuf.toString())){
			comLogService.insert("SUP_SETTLEMENT_TARGET",supSettlementTarget.getSupplierId(), supSettlementTarget.getTargetId(),operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateSuPSettlementTarget.name(),
					"编辑结算对象", "对" + oldtarget.getName() + strBuf.toString(), "SUP_SUPPLIER");
		}
	}

	@Override
	public void deleteMetaRelation(MetaSettlement metaSettlement,String operatorName) {
		metaSettlementDAO.deleteByMetaProductIdAndTargetId(metaSettlement);
		
		comLogService.insert("META_SETTLEMENT",metaSettlement.getMetaProductId(),metaSettlement.getSettlementId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteMetaPerform.name(),
				"删除关联的结算对象",LogViewUtil.logDeleteStr(operatorName), "META_PRODUCT");
	}
	
	public Integer selectByMetaProductId(Long metaProductId){
		return metaSettlementDAO.selectSuperByMetaProductId(metaProductId);
	}
	
	public void addMetaRelation(MetaSettlement metaSettlement,String operatorName) {
		metaSettlementDAO.insertSuperMetaSettlement(metaSettlement);
		SupSettlementTarget supSettlementTarget=(SupSettlementTarget)this.supSettlementTargetDAO.getSettlementTargetById(metaSettlement.getTargetId());
		comLogService.insert("META_SETTLEMENT",metaSettlement.getMetaProductId(),metaSettlement.getSettlementId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertMetaSettlement.name(),
				"添加关联的结算对象", LogViewUtil.logNewStrByColumnName("结算对象", supSettlementTarget.getName()+" 关系绑定"), "META_PRODUCT");
	}
	public Long selectTargetIdByMetaProductId(Long metaProductId){
		return metaSettlementDAO.selectTargetIdByMetaProductId(metaProductId);
	}
	//getter and setter
	public void setSupSettlementTargetDAO(
			SupSettlementTargetDAO supSettlementTargetDAO) {
		this.supSettlementTargetDAO = supSettlementTargetDAO;
	}

	public void markIsValid(Map params) {
		supSettlementTargetDAO.markIsValid(params);
	}

 	public Integer selectRowCount(Map searchConds) {
 		return supSettlementTargetDAO.selectRowCount(searchConds);
	}

	public List<SupSettlementTarget> getSuperSupSettlementTargetByMetaProductId(Long metaProductId) {
		return supSettlementTargetDAO.getMetaSettlementByMetaProductId(metaProductId,Constant.PRODUCT_BIZ_TYPE.BEE.name());
	}

	public void setMetaSettlementDAO(MetaSettlementDAO metaSettlementDAO) {
		this.metaSettlementDAO = metaSettlementDAO;
	}

	public void setSupSupplierDAO(SupSupplierDAO supSupplierDAO) {
		this.supSupplierDAO = supSupplierDAO;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	@Override
	public List<SupSettlementTarget> selectSupSettlementTargetByName(String search, Integer size) {
		return this.supSettlementTargetDAO.selectSupSettlementTargetByName(search, size);
	}

	@Override
	public MetaSettlement findMetaSettlementByMetaProductId(Long metaProductId) {
		return metaSettlementDAO.findMetaSettlementByMetaProductId(metaProductId);
	}

}
