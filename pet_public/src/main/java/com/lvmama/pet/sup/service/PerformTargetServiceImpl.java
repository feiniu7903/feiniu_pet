package com.lvmama.pet.sup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sup.dao.MetaPerformDAO;
import com.lvmama.pet.sup.dao.SupPerformTargetDAO;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

public class PerformTargetServiceImpl implements PerformTargetService {

	private SupPerformTargetDAO supPerformTargetDAO;
	private MetaPerformDAO metaPerformDAO;
	
 	private SupSupplierDAO supSupplierDAO;
 	
 	private ComLogService comLogService;
 	
	public Long addPerformTarget(SupPerformTarget performTarget,String operatorName) {
		SupSupplier ss=supSupplierDAO.selectByPrimaryKey(performTarget.getSupplierId());
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("supplierId", performTarget.getSupplierId());
		long count=selectRowCount(map);
		performTarget.setName(ss.getSupplierName()+" 履行对象"+StringUtils.leftPad(String.valueOf(count+1), 2,'0'));
		Long targetId=this.supPerformTargetDAO.insert(performTarget);
		
		comLogService.insert("SUP_PERFORM_TARGET",performTarget.getSupplierId(), performTarget.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupPerformTarget.name(),
				"创建履行对象",LogViewUtil.logNewStr(operatorName), "SUP_SUPPLIER");
		return targetId;
	}


	public void deletePerformTarget(SupPerformTarget performTarget,String operatorName) {
		Long performTargetId = performTarget.getTargetId();
		this.supPerformTargetDAO.deleteByPrimaryKey(performTargetId);
		comLogService.insert("SUP_PERFORM_TARGET",performTarget.getSupplierId(), performTarget.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteSupPerformTarget.name(),
				"删除履行对象",LogViewUtil.logDeleteStr(operatorName), "SUP_SUPPLIER");
	}

	public SupPerformTarget getSupPerformTarget(Long performTargetId) {
		return (SupPerformTarget) this.supPerformTargetDAO.selectByPrimaryKey(performTargetId);
	}
	
	public void updatePerformTarget(SupPerformTarget performTarget,String operatorName) {
		StringBuffer strBuf=new StringBuffer();
		SupPerformTarget oldperformTarget = this.getSupPerformTarget(performTarget.getTargetId());
		if(!LogViewUtil.logIsEmptyStr(performTarget.getName()).equals(LogViewUtil.logIsEmptyStr(oldperformTarget.getName()))){
			strBuf.append(LogViewUtil.logEditStr("对象名称", oldperformTarget.getName(), performTarget.getName()));
		}
		if(!LogViewUtil.logIsEmptyStr(performTarget.getCertificateType()).equals(LogViewUtil.logIsEmptyStr(oldperformTarget.getCertificateType()))){
			strBuf.append(LogViewUtil.logEditStr("履行方式", oldperformTarget.getZhCertificateType(), performTarget.getZhCertificateType()));
		}
		if(!LogViewUtil.logIsEmptyStr(performTarget.getOpenTime()).equals(LogViewUtil.logIsEmptyStr(oldperformTarget.getOpenTime()))||!LogViewUtil.logIsEmptyStr(performTarget.getCloseTime()).equals(LogViewUtil.logIsEmptyStr(oldperformTarget.getCloseTime()))){
			strBuf.append(LogViewUtil.logEditStr("履行时间", oldperformTarget.getOpenTime()+"~"+oldperformTarget.getCloseTime(), performTarget.getOpenTime()+"~"+performTarget.getCloseTime()));
		}
		if(!LogViewUtil.logIsEmptyStr(performTarget.getMemo()).equals(LogViewUtil.logIsEmptyStr(oldperformTarget.getMemo()))){
			strBuf.append(LogViewUtil.logEditStr("备注", oldperformTarget.getMemo(), performTarget.getMemo()));
		}
		
		this.supPerformTargetDAO.updateByPrimaryKey(performTarget);
		
		if(!"".equals(strBuf.toString())){
			comLogService.insert("SUP_PERFORM_TARGET",performTarget.getSupplierId(), performTarget.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.updateSupPerformTarget.name(),
				"编辑履行对象", "对" + oldperformTarget.getName() +strBuf.toString(), "SUP_SUPPLIER");
		}
	}

	public List<SupPerformTarget> findSupPerformTarget(Map param) {
		return this.supPerformTargetDAO.findSupPerformTarget(param);
	}
	public List<SupPerformTarget> findAllSupPerformTarget(Map<String,Object> param){
		param.put("_startRow", 0);
		param.put("_endRow", 500);
		return this.supPerformTargetDAO.findSupPerformTarget(param);
	}
	 
	public void setSupPerformTargetDAO(SupPerformTargetDAO supPerformTargetDAO) {
		this.supPerformTargetDAO = supPerformTargetDAO;
	}
 
	public void markIsValid(Map params) {
		supPerformTargetDAO.markIsValid(params);
	}

 	public Integer selectRowCount(Map searchConds) {
 		return supPerformTargetDAO.selectRowCount(searchConds);
	}

	@Override
	public List<SupPerformTarget> findSuperSupPerformTargetByMetaProductId(
			Long metaProductId) {
		return this.supPerformTargetDAO.findSupPerformTargetByMetaProductId(metaProductId,Constant.PRODUCT_BIZ_TYPE.BEE.name());
	}
	@Override
	public void deleteMetaRelation(MetaPerform metaPerform,String operatorName) {
		metaPerformDAO.deleteByMetaProductIdAndTargetId(metaPerform);
		
		comLogService.insert("META_PERFORM",metaPerform.getMetaProductId(),metaPerform.getProductPerformId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteMetaPerform.name(),
				"删除关联的履行对象",LogViewUtil.logDeleteStr(operatorName), "META_PRODUCT");
	}
	@Override
	public Integer selectByMetaProductId(Long metaProductId){
		return metaPerformDAO.selectSuperByMetaProductId(metaProductId);
	}
	@Override
	public void addMetaRelation(MetaPerform metaPerform,String operatorName) {
		metaPerformDAO.insertSuperMetaPerform(metaPerform);
		
		SupPerformTarget supPerformTarget=(SupPerformTarget)this.supPerformTargetDAO.selectByPrimaryKey(metaPerform.getTargetId());
		comLogService.insert("META_PERFORM",metaPerform.getMetaProductId(),metaPerform.getProductPerformId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertMetaPerform.name(),
				"添加关联的履行对象", LogViewUtil.logNewStrByColumnName("履行对象", supPerformTarget.getName()+" 关系绑定"), "META_PRODUCT");
	}
	public void setMetaPerformDAO(MetaPerformDAO metaPerformDAO) {
		this.metaPerformDAO = metaPerformDAO;
	}


	public void setSupSupplierDAO(SupSupplierDAO supSupplierDAO) {
		this.supSupplierDAO = supSupplierDAO;
	}


	@Override
	public List<Long> selectMetaRelationByParam(Long targetId, Constant.PRODUCT_BIZ_TYPE type) {
		return metaPerformDAO.selectMetaRelationByParam(targetId);
	}

	@Override
	public List<Long> selectMetaRelationBySupplierId(Long supplierId, Constant.PRODUCT_BIZ_TYPE type) {
		return metaPerformDAO.selectMetaRelationBySupplierId(supplierId,type);
	}


	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	@Override
	public List<MetaPerform> getMetaPerformByMetaProductId(Long metaProductId) {
		return metaPerformDAO.getMetaPerformByMetaProductId(metaProductId);
	}
}
