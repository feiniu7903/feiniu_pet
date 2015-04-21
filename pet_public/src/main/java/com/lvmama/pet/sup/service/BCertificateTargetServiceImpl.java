package com.lvmama.pet.sup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComFaxTemplate;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.pub.dao.ComFaxTemplateDAO;
import com.lvmama.pet.sup.dao.MetaBCertificateDAO;
import com.lvmama.pet.sup.dao.SupBCertificateTargetDAO;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

public class BCertificateTargetServiceImpl implements BCertificateTargetService {
	private SupBCertificateTargetDAO supBCertificateTargetDAO;
	private MetaBCertificateDAO metaBCertificateDAO;
	private ComFaxTemplateDAO comFaxTemplateDAO;
	private SupSupplierDAO supSupplierDAO;
	private ComLogService comLogService;
	
	public Long addBCertificateTarget(SupBCertificateTarget target,String operatorName) {
		SupSupplier ss=supSupplierDAO.selectByPrimaryKey(target.getSupplierId());
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("supplierId", target.getSupplierId());
		long count=selectRowCount(map);
		target.setName(ss.getSupplierName()+" 凭证对象"+StringUtils.leftPad(String.valueOf(count+1), 2,'0'));
		Long pk=supBCertificateTargetDAO.insert(target);
		
		comLogService.insert("SUP_B_CERTIFICATE_TARGET",target.getSupplierId(), target.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupBCertificateTarget.name(),
				"创建凭证对象", LogViewUtil.logNewStr(operatorName), "SUP_SUPPLIER");
		return pk;
	}
 
	public List<SupBCertificateTarget> findBCertificateTarget(Map param) {
		return supBCertificateTargetDAO.findBCertificateTarget(param);
	}

	public void updateBCertificateTarget(SupBCertificateTarget target,String operatorName) {
		StringBuffer strBuf=new StringBuffer();
		SupBCertificateTarget oldtarget = this.getBCertificateTargetByTargetId(target.getTargetId());
		if(!LogViewUtil.logIsEmptyStr(target.getName()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getName()))){
			strBuf.append(LogViewUtil.logEditStr("对象名称", oldtarget.getName(), target.getName()));
		}
		if(!LogViewUtil.logIsEmptyStr(target.getViewBcertificate()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getViewBcertificate()))){
			strBuf.append(LogViewUtil.logEditStr("B凭证方式", oldtarget.getViewBcertificate(), target.getViewBcertificate()));
		}
		if(!LogViewUtil.logIsEmptyStr(target.getFaxNo()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getFaxNo()))){
			strBuf.append(LogViewUtil.logEditStr("传真号码", oldtarget.getFaxNo(), target.getFaxNo()));
		}
		if(!LogViewUtil.logIsEmptyStr(target.getFaxTemplate()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getFaxTemplate()))){
			strBuf.append(LogViewUtil.logEditStr("使用模版", oldtarget.getZhfaxTemplate(), target.getZhfaxTemplate()));
		}
		if(!LogViewUtil.logIsEmptyStr(target.getFaxStrategy()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getFaxStrategy()))){
			strBuf.append(LogViewUtil.logEditStr("传真策略", oldtarget.getZhFaxStrategy(), target.getZhFaxStrategy()));
		}
		if(!LogViewUtil.logIsEmptyStr(target.getMemo()).equals(LogViewUtil.logIsEmptyStr(oldtarget.getMemo()))){
			strBuf.append(LogViewUtil.logEditStr("备注", oldtarget.getMemo(), target.getMemo()));
		}
		
		supBCertificateTargetDAO.updateByPrimaryKey(target);
		if(!"".equals(strBuf.toString())){
		comLogService.insert("SUP_B_CERTIFICATE_TARGET",target.getSupplierId(), target.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.updateSupBCertificateTarget.name(),
				"编辑凭证对象", "对" + oldtarget.getName() +strBuf.toString(), "SUP_SUPPLIER");
		}
	}
	
	public SupBCertificateTarget getBCertificateTargetByTargetId(Long targetId) {
		return supBCertificateTargetDAO.selectByPrimaryKey(targetId);
	}
	 
	/**
	 * 获取传真模板列表
	 */
	public List<ComFaxTemplate> getFaxTemplateList() {
		return this.comFaxTemplateDAO.selectComFaxTemplateList();
	}
	
	public ComFaxTemplate getFaxTemplate(String templateId){
		return this.comFaxTemplateDAO.selectByTemplateId(templateId);
	}

	public void setSupBCertificateTargetDAO(SupBCertificateTargetDAO supBCertificateTargetDAO) {
		this.supBCertificateTargetDAO = supBCertificateTargetDAO;
	}
 
	public void setComFaxTemplateDAO(ComFaxTemplateDAO comFaxTemplateDAO) {
		this.comFaxTemplateDAO = comFaxTemplateDAO;
	}

	public void markIsValid(Map params) {
		supBCertificateTargetDAO.markIsValid(params);
	}

 	public Integer selectRowCount(Map searchConds) {
 		return supBCertificateTargetDAO.selectRowCount(searchConds);
	}
	public List<SupBCertificateTarget> selectSuperMetaBCertificateByMetaProductId(Long metaProductId){
		return this.supBCertificateTargetDAO.selectMetaBCertificateByMetaProductId(metaProductId,Constant.PRODUCT_BIZ_TYPE.BEE.name());
	}
	public void deleteMetaRelation(MetaBCertificate metaBCertificate,String operatorName) {
		metaBCertificateDAO.deleteByMetaProductIdAndTargetId(metaBCertificate);
		
		comLogService.insert("META_B_CERTIFICATE",metaBCertificate.getMetaProductId(), metaBCertificate.getMetaProductId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteMetaBCertificate.name(),
				"创建关联的凭证对象", LogViewUtil.logDeleteStr(operatorName), "META_PRODUCT");
	}

	public void setMetaBCertificateDAO(MetaBCertificateDAO metaBCertificateDAO) {
		this.metaBCertificateDAO = metaBCertificateDAO;
	}

	@Override
	public Integer selectByMetaProductId(Long metaProductId){
		return metaBCertificateDAO.selectSuperByMetaProductId(metaProductId);
	}
	@Override
	public void insertSuperMetaBCertificate(MetaBCertificate metaBCertificate,String operatorName) {
		metaBCertificate.setBizType(Constant.PRODUCT_BIZ_TYPE.BEE.name());
		metaBCertificateDAO.insert(metaBCertificate);
		
		SupBCertificateTarget metaProduct=this.supBCertificateTargetDAO.selectByPrimaryKey(metaBCertificate.getTargetId());
		comLogService.insert("META_B_CERTIFICATE",metaBCertificate.getMetaProductId(), metaBCertificate.getMetaProductId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertMetaBCertificate.name(),
				"创建关联的凭证对象", LogViewUtil.logNewStrByColumnName("凭证对象", metaProduct.getName()+" 关系绑定"), "META_PRODUCT");
	}
	
	
	public MetaBCertificate findMetaBCertificateByByMetaProductId(Long metaProductId){
		MetaBCertificate metaBCertificate=new MetaBCertificate();
		metaBCertificate.setBizType(Constant.PRODUCT_BIZ_TYPE.BEE.name());
		metaBCertificate.setMetaProductId(metaProductId);
		List<MetaBCertificate> list=metaBCertificateDAO.findByMetaParams(metaBCertificate);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	

	@Override
	public SupBCertificateTarget getSuperMetaBCertificateByMetaProductId(
			Long metaProductId) {
		return this.supBCertificateTargetDAO.getMetaBCertificateByMetaProductId(metaProductId, Constant.PRODUCT_BIZ_TYPE.BEE.name());
	}

	public void setSupSupplierDAO(SupSupplierDAO supSupplierDAO) {
		this.supSupplierDAO = supSupplierDAO;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
