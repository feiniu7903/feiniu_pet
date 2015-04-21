package com.lvmama.comm.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.sensitiveW.SensitiveWord;
import com.lvmama.comm.pet.po.sup.FinAccountingEntity;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupContractChange;
import com.lvmama.comm.pet.po.sup.SupContractFs;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sup.FinAccountingEntityService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

public class LogObject {

	/**
	 * 修改供应商
	 * @param ss
	 * @param preUpdateSupSupplier
	 * @param operatorName
	 * @param comLogService
	 */
	public static void updateSupSupplierLogStr(SupSupplier ss,SupSupplier preUpdateSupSupplier,String operatorName,ComLogService comLogService,PlaceCityService placeCityService){
		ComCity comCity = placeCityService.selectCityByPrimaryKey(ss.getCityId());
		if(null!=comCity){
			ss.setCityName(comCity.getCityName());
		}
		comCity=placeCityService.selectCityByPrimaryKey(preUpdateSupSupplier.getCityId());
		if(null!=comCity){
			preUpdateSupSupplier.setCityName(comCity.getCityName());
		}
		
		LogHelper<SupSupplier> helper=LogHelper.makeLog(preUpdateSupSupplier, ss);		
		helper.log("supplierName", "供应商名称");
		//供应商类型		
		helper.log("supplierType", "供应商类型","getZhSupplierType");
		//所在城市		
		helper.log("cityName", "所在城市");
		//地址
		helper.log("address", "地址");		
		//邮编		
		helper.log("postcode", "邮编");
		//固定电话		
		helper.log("telephone", "固定电话");
		//网址		
		helper.log("webSite", "网址");
		//传真		
		helper.log("fax", "传真");
		//手机		
		helper.log("legalPerson", "法人代表");
		helper.log("travelLicense", "旅行社许可证号");
		helper.log("bosshead", "负责人");
		helper.log("parentId", "上级供应商ID");
		helper.log("assessPoints", "考核分");
		helper.log("foregiftsAlert", "押金回收时间");

		String str = helper.toResult();
		
		if(!"".equals(str)){
			comLogService.insert("SUP_SUPPLIER",null, ss.getSupplierId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.updateSupSupplier.name(),
				"更新供应商信息", str, null);
		}
	}
	
	
	public static void addSupplierLog(SupSupplier supplier, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_SUPPLIER",null, supplier.getSupplierId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupSupplier.name(),
				"创建供应商",MessageFormat.format("创建了名称为[ {0} ]的供应商", (supplier.getSupplierName()==null ?"":supplier.getSupplierName())), null);
	}
	
	/**
	 * 创建合同
	 * @param contract
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addSupContractLog(SupContract contract, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_CONTRACT",null, contract.getContractId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupContract.name(),
				"创建合同",MessageFormat.format("创建了名称为[ {0} ]的合同", (contract.getContractName()==null ?"":contract.getContractName())), null);
	}
	
	/**
	 * 修改合同
	 * @param ss
	 * @param preUpdateSupSupplier
	 * @param operatorName
	 * @param comLogService
	 */
	public static void updateSupContractLog(SupContract contract,SupContract oldContract,String operatorName,ComLogService comLogService){
		
		//采购经理处理
		PermUserService permUserService=SpringBeanProxy.getBean(PermUserService.class, "permUserService");
		if(null!=permUserService){
			if(contract.getManagerId() != null) {
				contract.setManagerName(permUserService.getPermUserByUserId(contract.getManagerId()).getRealName());
			}
			if(oldContract.getManagerId() != null) {
				oldContract.setManagerName(permUserService.getPermUserByUserId(oldContract.getManagerId()).getRealName());
			}
		}
		
		//甲方处理
		FinAccountingEntityService finAccountingEntityService=SpringBeanProxy.getBean(FinAccountingEntityService.class,"finAccountingEntityService");
		if(null!=finAccountingEntityService){
			//组装map
			Map<String, String> finAccountingEntityData=new HashMap<String, String>();
			List<FinAccountingEntity> entityList=finAccountingEntityService.selectEntityList();
			for (FinAccountingEntity finAccountingEntity : entityList) {
				finAccountingEntityData.put(String.valueOf(finAccountingEntity.getAccountingEntityId()), finAccountingEntity.getName());
			}
			if(StringUtils.isNotBlank(contract.getPartyA())){
				contract.setFinAccountingEntityName(finAccountingEntityData.get(contract.getPartyA()));
			}
			if(StringUtils.isNotBlank(oldContract.getPartyA())){
				oldContract.setFinAccountingEntityName(finAccountingEntityData.get(oldContract.getPartyA()));
			}
		}
		
		LogHelper<SupContract> helper=LogHelper.makeLog(oldContract, contract);
		helper.log("contractName", "合同名称");
		helper.log("contractNo", "合同编号");
		helper.log("signDate", "签定日期");
		helper.log("summary", "概要");
		helper.log("beginDate", "有效期开始日期");
		helper.log("orgId", "组织id");
		helper.log("endDate", "有效期结束日期");		
		helper.log("supplierId", "供应商id");
		helper.log("contractStatus", "合同状态","getZhContractStatus");
		helper.log("contractType", "合同类型","getZhContractType");
		helper.log("createTime", "创建时间");
		helper.log("arranger", "经办人");
		helper.log("finAccountingEntityName", "甲方");
		helper.log("managerName", "采购经理");
		helper.log("operateName", "操作人姓名");
		String str = helper.toResult();
		
		if(!"".equals(str)){
			comLogService.insert("SUP_CONTRACT",null,contract.getContractId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.updateSupContract.name(),
				"更新合同信息", str, null);
		}
	}
	
	/**
	 * 上传合同扫描件
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addSupContractFsLog(SupContractFs contractFs, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_CONTRACT_FS",contractFs.getContractId(), contractFs.getSupContractFsId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSuPSettlementTarget.name(),
				"上传合同",MessageFormat.format("上传了名称为[ {0} ]的合同扫描件",contractFs.getFsName()), "SUP_CONTRACT");
	}
	
	/**
	 * 创建结算对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addSupSettlementTargetLog(SupSettlementTarget target, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_SETTLEMENT_TARGET",target.getSupplierId(), target.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSuPSettlementTarget.name(),
				"创建结算对象",MessageFormat.format("创建了名称为[ {0} ]的结算对象", (target.getName()==null ?"":target.getName())), "SUP_SUPPLIER");
	}
	

	
	/**
	 * 创建补充单
	 * @param contractChange
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addSupContractChangeLog(SupContractChange contractChange, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_CONTRACT_CHANGE",contractChange.getContractId(), contractChange.getContractChangeId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupContractChange.name(),
				"创建补充单",MessageFormat.format("创建了id为[ {0} ]的补充单,变更类型：" + Constant.CONTRACT_TYPE.getCnName(contractChange.getChangeType())+",备注信息：" + contractChange.getChangeMemo(), (contractChange.getContractChangeId()==null ?"":contractChange.getContractChangeId())), "SUP_CONTRACT");
		comLogService.insert("SUP_CONTRACT",null,contractChange.getContractId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupContractChange.name(),
				"创建补充单",MessageFormat.format("创建了id为[ {0} ]的补充单,变更类型：" + Constant.CONTRACT_TYPE.getCnName(contractChange.getChangeType())+",备注信息：" + contractChange.getChangeMemo(), (contractChange.getContractChangeId()==null ?"":contractChange.getContractChangeId())), null);
	}
	
	/**
	 * 上传变更单附件图
	 * @param contractChange
	 * @param operatorName
	 * @param comLogService
	 */
	public static void updateSupContractChangeLog(SupContractChange contractChange, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_CONTRACT_CHANGE",contractChange.getContractId(), contractChange.getContractChangeId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.uploadFile.name(),
				"上传补充单附件图",MessageFormat.format("为id为[ {0} ]的补充单上传附件图", (contractChange.getContractChangeId()==null ?"":contractChange.getContractChangeId())), "SUP_CONTRACT");
	}
	
	/**
	 * 修改结算对象
	 * @param target
	 * @param SupSettlementTarget
	 * @param operatorName
	 * @param comLogService
	 */
	public static void updateSettlementTargetLog(SupSettlementTarget target,SupSettlementTarget oldTarget,String operatorName,ComLogService comLogService){
		LogHelper<SupSettlementTarget> helper=LogHelper.makeLog(oldTarget, target);
		helper.log("name", "对象名称");
		helper.log("settlementPeriod", "结算周期");
		helper.log("advancedDays", "提前天数");
		helper.log("bankAccountName", "开户名称");
		helper.log("bankName", "开户银行");
		helper.log("bankAccount", "开户账号");
		helper.log("alipayAccount", "支付宝账号");
		helper.log("alipayName", "支付宝用户名");
		helper.log("type", "类型");
		helper.log("paymentType", "付款方式");
		helper.log("bankLines", "联行号");
		helper.log("memo", "备注");
		String str = "对" + oldTarget.getName() + helper.toResult();
		
		if(!"".equals(str)){
			comLogService.insert("SUP_SETTLEMENT_TARGET",target.getSupplierId(),target.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.updateSuPSettlementTarget.name(),
				"更新结算对象", str, "SUP_SUPPLIER");
		}
	}
	
	/**
	 * 上传文件
	 * @param pid
	 * @param fileName
	 * @param comLogService
	 */
	public static void addUploadFileLog(Long pid, String fileName, String operatorName, ComLogService comLogService){
		comLogService.insert("UPLOAD_FILE",null, pid,operatorName,
				Constant.COM_LOG_ORDER_EVENT.uploadFile.name(),
				"上传文件",MessageFormat.format("上传了名为[ {0} ]的文件", (fileName==null ?"":fileName)), null);
	}
	
	/**
	 * 创建凭证对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addBCertificateTargetLog(SupBCertificateTarget target, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_B_CERTIFICATE_TARGET",target.getSupplierId(), target.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupBCertificateTarget.name(),
				"创建凭证对象",MessageFormat.format("创建了名称为[ {0} ]的凭证对象", (target.getName()==null ?"":target.getName())), "SUP_SUPPLIER");
	}
	
	/**
	 * 修改凭证对象
	 * @param target
	 * @param oldTarget
	 * @param operatorName
	 * @param comLogService
	 */
	public static void updateBCertificateTargetLog(SupBCertificateTarget target,SupBCertificateTarget oldTarget,String operatorName,ComLogService comLogService){
		LogHelper<SupBCertificateTarget> helper=LogHelper.makeLog(oldTarget, target);
		helper.log("name", "对象名称");
		helper.log("bcertificate", "B凭证方式");
		helper.log("faxNo", "传真号码");
		helper.log("faxTemplate", "使用模版");
		helper.log("faxStrategy", "传真策略");
		helper.log("memo", "备注");
		String str = "对" + oldTarget.getName() + helper.toResult();
		
		if(!"".equals(str)){
			comLogService.insert("SUP_B_CERTIFICATE_TARGET",target.getSupplierId(),target.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.updateSupBCertificateTarget.name(),
				"更新凭证对象", str, "SUP_SUPPLIER");
		}
	}
	
	/**
	 * 创建关联凭证对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addMetaBCertificateLog(MetaBCertificate metaBCertificate, String metaProductName, String operatorName, ComLogService comLogService){
		comLogService.insert("META_B_CERTIFICATE",metaBCertificate.getMetaProductId(), metaBCertificate.getMetaProductId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertMetaBCertificate.name(),
				"创建关联的凭证对象", LogViewUtil.logNewStrByColumnName("凭证对象", metaProductName+" 关系绑定"), "META_PRODUCT");
	}
	
	/**
	 * 删除关联的凭证对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void deleteMetaBCertificateLog(MetaBCertificate metaBCertificate, String operatorName, ComLogService comLogService){
		comLogService.insert("META_B_CERTIFICATE",metaBCertificate.getMetaProductId(), metaBCertificate.getMetaProductId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteMetaBCertificate.name(),
				"删除关联的凭证对象", LogViewUtil.logDeleteStr(operatorName), "META_PRODUCT");
	}
	
	/**
	 * 创建履行对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addSupPerformTargetLog(SupPerformTarget performTarget, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_PERFORM_TARGET",performTarget.getSupplierId(), performTarget.getTargetId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertSupPerformTarget.name(),
				"创建履行对象",MessageFormat.format("创建了名称为[ {0} ]的履行对象", (performTarget.getName()==null ?"":performTarget.getName())), "SUP_SUPPLIER");
	}
	
	/**
	 * 修改履行对象
	 * @param target
	 * @param oldTarget
	 * @param operatorName
	 * @param comLogService
	 */
	public static void updateSupPerformTargetLog(SupPerformTarget target,SupPerformTarget oldTarget,String operatorName,ComLogService comLogService){
		LogHelper<SupPerformTarget> helper=LogHelper.makeLog(oldTarget, target);
		helper.log("name", "对象名称");
		helper.log("certificateType", "履行方式");
		helper.log("openTime", "履行开始时间");
		helper.log("closeTime", "履行结束时间");
		helper.log("memo", "备注");
		String str = "对" + oldTarget.getName() + helper.toResult();
		
		if(!"".equals(str)){
			comLogService.insert("SUP_PERFORM_TARGET",target.getSupplierId(), target.getTargetId(),operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateSupPerformTarget.name(),
					"编辑履行对象", str, "SUP_SUPPLIER");
		}
	}
	
	/**
	 * 创建关联履行对象
	 * @param metaPerform
	 * @param performTargetName
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addMetaBCertificateLog(MetaPerform metaPerform, String performTargetName, String operatorName, ComLogService comLogService){
		comLogService.insert("META_PERFORM",metaPerform.getMetaProductId(),metaPerform.getProductPerformId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertMetaPerform.name(),
				"添加关联的履行对象", LogViewUtil.logNewStrByColumnName("履行对象", performTargetName+" 关系绑定"), "META_PRODUCT");
	}
	
	/**
	 * 删除关联的履行对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void deleteMetaBCertificateLog(MetaPerform metaPerform, String operatorName, ComLogService comLogService){
		comLogService.insert("META_PERFORM",metaPerform.getMetaProductId(),metaPerform.getProductPerformId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteMetaPerform.name(),
				"删除关联的履行对象",LogViewUtil.logDeleteStr(operatorName), "META_PRODUCT");
	}
	
	/**
	 * 创建关联结算对象
	 * @param metaSettlement
	 * @param settlementTargetName
	 * @param operatorName
	 * @param comLogService
	 */
	public static void addMetaSettlementLog(MetaSettlement metaSettlement, String settlementTargetName, String operatorName, ComLogService comLogService){
		comLogService.insert("META_SETTLEMENT",metaSettlement.getMetaProductId(),metaSettlement.getSettlementId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertMetaSettlement.name(),
				"添加关联的结算对象", LogViewUtil.logNewStrByColumnName("结算对象", settlementTargetName+" 关系绑定"), "META_PRODUCT");
	}
	
	/**
	 * 删除关联的结算对象
	 * @param target
	 * @param operatorName
	 * @param comLogService
	 */
	public static void deleteMetaSettlementLog(MetaSettlement metaSettlement, String operatorName, ComLogService comLogService){
		comLogService.insert("META_SETTLEMENT",metaSettlement.getMetaProductId(),metaSettlement.getSettlementId(),operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteMetaPerform.name(),
				"删除关联的结算对象",LogViewUtil.logDeleteStr(operatorName), "META_PRODUCT");
	}
	
	public static String makeSupplierAptitudeLog(LogHelper<SupSupplierAptitude> log){
		log.log("aptitudeAudit","资质审核状态");
		log.log("aptitudeEndTime","资质到期时间");
		log.log("businessLicenceFile",Constant.SUP_APTITUDE_TYPE.BUSINESS_LICENCE.getCnName());
		log.log("organizationFile",Constant.SUP_APTITUDE_TYPE.ORGANIZATION.getCnName());
		log.log("operationPermissionFile",Constant.SUP_APTITUDE_TYPE.OPERATION_PERMISSION.getCnName());
		log.log("insuranceFile",Constant.SUP_APTITUDE_TYPE.INSURANCE.getCnName());
		log.log("taxRegistrationFile",Constant.SUP_APTITUDE_TYPE.TAX_REGISTRATION.getCnName());
		return log.toResult();
	}
	
	public static String makeUpdateComContactLog(ComContact old,ComContact newE){
		LogHelper<ComContact> log=new LogHelper<ComContact>(ComContact.class,old,newE);
		log.log("name","姓名");
		log.log("sex","性别");
		log.log("title","职务");		
		log.log("telephone","电话");
		log.log("mobilephone","手机");
		log.log("memo","说明");
		log.log("address","地址");
		log.log("email","Email");
		log.log("otherContact","其他联系方式");
		return log.toResult();
	}
	
	public static void addContractRejectedLog(Long contractId, String content, String operatorName, ComLogService comLogService){
		comLogService.insert("SUP_CONTRACT",null, contractId,operatorName,Constant.COM_LOG_ORDER_EVENT.rejectContract.name(),"合同审核驳回","错误信息："+content, null);
	}
	
	public static void addSensitiveWordLog(SensitiveWord sensitiveWord, String operatorName, ComLogService comLogService){
		comLogService.insert("SENSITIVE_WORD",0l, sensitiveWord.getSensitiveId(),operatorName,
				Constant.COM_LOG_SENSITIVE_WORD.ADD_SENSITIVE_WORD.name(),
				"添加敏感词",MessageFormat.format("添加敏感词[ {0} ]", sensitiveWord.getContent()), "SENSITIVE_WORD");
	}
	
	public static void updateSensitiveWordLog(SensitiveWord sensitiveWord,SensitiveWord oldSensitiveWord,String operatorName,ComLogService comLogService){
		comLogService.insert("SENSITIVE_WORD",0l,sensitiveWord.getSensitiveId(),operatorName,
			Constant.COM_LOG_SENSITIVE_WORD.EDIT_SENSITIVE_WORD.name(),
			"编辑敏感词", "编辑敏感词[ "+oldSensitiveWord.getContent()+" ]为[ " + sensitiveWord.getContent() + " ]", "SENSITIVE_WORD");
	}
	
	public static void deleteSensitiveWordLog(SensitiveWord sensitiveWord, String operatorName,ComLogService comLogService){
		comLogService.insert("SENSITIVE_WORD",0l,sensitiveWord.getSensitiveId(),operatorName,
			Constant.COM_LOG_SENSITIVE_WORD.DELETE_SENSITIVE_WORD.name(),
			"删除敏感词", MessageFormat.format("删除敏感词[ {0} ]", sensitiveWord.getContent()), "SENSITIVE_WORD");
	}
}
