package com.lvmama.comm.pet.service.sup;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComFaxTemplate;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;

public interface BCertificateTargetService {
	/**
	 * 添加B端凭证对象
	 * @param SupBCertificateTarget
	 */
	 Long addBCertificateTarget(SupBCertificateTarget target,String operatorName);

	/**
	 * 更新B端凭证对象
	 * @param ProdBCertificate
	 */
	 void updateBCertificateTarget(SupBCertificateTarget target,String operatorName);

	/**
	 * 根据ID获取B端凭证对象
	 * @param targetId
	 * @return 指定的B端凭证对象
	 * 
	 */
	 SupBCertificateTarget getBCertificateTargetByTargetId(Long targetId);

	/**
	 * 根据名称，获取所有的B端凭证对象列表
	 * @param param
	 * @return
	 */
	 List<SupBCertificateTarget> findBCertificateTarget(Map param);
	   
	 List<ComFaxTemplate> getFaxTemplateList();
	 
	 ComFaxTemplate getFaxTemplate(String templateId);
	 
	 /**
	  * 修改是否有效状态
	  * @param targetID
	  * */
	 void markIsValid(Map params);
	 
	 Integer selectRowCount(Map searchConds);
	 List<SupBCertificateTarget> selectSuperMetaBCertificateByMetaProductId(Long metaProductId);
	 SupBCertificateTarget getSuperMetaBCertificateByMetaProductId(Long metaProductId);
	 /**
	  * 删除采购产品B凭证关系
	  * @param metaBCertificate
	  */
	 void deleteMetaRelation(MetaBCertificate metaBCertificate,String operatorName);
	 Integer selectByMetaProductId(Long metaProductId);
	 /**
	  * 添加采购产品B凭证关系
	  * @param metaBCertificate
	  */
	void insertSuperMetaBCertificate(MetaBCertificate metaBCertificate,String operatorName);
	public MetaBCertificate findMetaBCertificateByByMetaProductId(Long metaProductId);
	
}
