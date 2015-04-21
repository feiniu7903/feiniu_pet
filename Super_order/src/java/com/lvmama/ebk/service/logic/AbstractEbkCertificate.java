/**
 * 
 */
package com.lvmama.ebk.service.logic;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ebk.dao.EbkCertificateDAO;
import com.lvmama.ebk.dao.EbkCertificateItemDAO;
import com.lvmama.ebk.dao.EbkFaxTaskDAO;
import com.lvmama.ebk.dao.EbkOrderDataRevDAO;
import com.lvmama.ebk.dao.EbkTaskDAO;
import com.lvmama.order.service.Query;

 
public abstract class AbstractEbkCertificate {
	protected EbkCertificateDAO ebkCertificateDAO;
	protected EbkCertificateItemDAO ebkCertificateItemDAO;
	protected EbkOrderDataRevDAO ebkOrderDataRevDAO;
	protected EbkFaxTaskDAO ebkFaxTaskDAO;
	protected EbkTaskDAO ebkTaskDAO;
	protected EbkTaskService ebkTaskService;
	protected EbkFaxTaskService ebkFaxTaskService;
	abstract void addCertificateSet(OrdOrder ordOrder, OrdOrderItemMeta ooim, Map<Long, SupBCertificateTarget> sbctMap, AbstractEbkCertificateSet set);
	abstract void createSupplierCertificate(OrdOrder ordOrder,OrdOrderItemMeta itemMeta,AbstractEbkCertificateSet set,Map<Long, SupBCertificateTarget> sbctMap);
	abstract void retransmissionCertificate(OrdOrder ordOrder,OrdOrderItemMeta itemMeta,AbstractEbkCertificateSet set,Map<Long, SupBCertificateTarget> sbctMap);
	public void createData(List<EbkOrderDataRev> list) {
		for(EbkOrderDataRev rev:list){
			ebkOrderDataRevDAO.insert(rev);
		}
	}
 
	/**
	 * 要更新的凭证合并到一个容器中
	 * @author: ranlongfei 2013-3-23 下午5:06:12
	 * @param ebkCertificate
	 * @param ebkCertificates
	 * @param ordOrderItemMeta
	 */
	public void ebkCertificateUpdate(EbkCertificate ebkCertificate, List<EbkCertificate> ebkCertificates, OrdOrderItemMeta ordOrderItemMeta) {
		boolean isExists = false;
		EbkCertificate temp = ebkCertificate;
		//循环是否已有凭证，便于合并凭证子项
		for(EbkCertificate ec : ebkCertificates) {
			if(ebkCertificate.getEbkCertificateId().equals(ec.getEbkCertificateId())) {
				temp = ec;
				isExists = true;
				break;
			}
		}
		temp.putOrdOrderItemMeta(ordOrderItemMeta);
		if(!isExists) {
			ebkCertificates.add(temp);
		}
	}
	 
	protected String getItemProd(final OrdOrder order,final Long ordOrderItemProd) {
		OrdOrderItemProd itemProd = (OrdOrderItemProd) CollectionUtils.find(
				order.getOrdOrderItemProds(), new Predicate() {
					@Override
					public boolean evaluate(Object arg0) {
						return ordOrderItemProd
								.equals(((OrdOrderItemProd) arg0)
										.getOrderItemProdId());
					}
				});
		return itemProd.getProductName();
	}
	
	public void newCancel(OrdOrderItemMeta ordOrderItemMeta,Map<String, EbkCertificate> ooimMap, EbkCertificate old){
		this.newCertificate(ordOrderItemMeta, ooimMap, Constant.EBK_CERTIFICATE_TYPE.CANCEL, old);
	}
	public void newChange(OrdOrderItemMeta ordOrderItemMeta,Map<String, EbkCertificate> ooimMap, EbkCertificate old){
		this.newCertificate(ordOrderItemMeta, ooimMap, Constant.EBK_CERTIFICATE_TYPE.CHANGE, old);
	}
	public void newEnquiry(OrdOrderItemMeta ordOrderItemMeta,Map<String, EbkCertificate> ooimMap, EbkCertificate old){
		this.newCertificate(ordOrderItemMeta, ooimMap, Constant.EBK_CERTIFICATE_TYPE.ENQUIRY, old);
	}
	public void newConfirm(OrdOrderItemMeta ordOrderItemMeta,Map<String, EbkCertificate> ooimMap, EbkCertificate old){
		this.newCertificate(ordOrderItemMeta, ooimMap, Constant.EBK_CERTIFICATE_TYPE.CONFIRM, old);
	}
	
	public void newCertificate(OrdOrderItemMeta ordOrderItemMeta,Map<String, EbkCertificate> ooimMap,Constant.EBK_CERTIFICATE_TYPE certificate_TYPE, EbkCertificate old){
		//判断是否可以合并生成凭证，例superId_targetId_productType_visitTime
				//					 门票:superId_targetId_productType_faxStrategy_visitTime
				SupBCertificateTarget sbc = ordOrderItemMeta.getBcertificateTarget();
				String key = initKey(ordOrderItemMeta, sbc);
				EbkCertificate ebkCertificate = ooimMap.get(key);
				if(ebkCertificate==null){
					ebkCertificate = new EbkCertificate();
				}
				
				ebkCertificate.setEbkCertificateType(certificate_TYPE.name());
				if(old != null) {
					ebkCertificate.setOldCertificateId(old.getEbkCertificateId());
				}
				ebkCertificate.putOrdOrderItemMeta(ordOrderItemMeta);
				ooimMap.put(key,ebkCertificate);
	}
	 
	/**
	 * 检查凭证的规则Key
	 * 
	 * @author: ranlongfei 2013-3-23 下午5:49:33
	 * @param ordOrderItemMeta
	 * @param sbc
	 * @return
	 */
	public String initKey(OrdOrderItemMeta ordOrderItemMeta,
			SupBCertificateTarget sbc) {
		String key;
		if(ordOrderItemMeta.isTicketProductType()){
			key = ordOrderItemMeta.getSupplierId()+"_"+sbc.getTargetId()+"_"+ordOrderItemMeta.getProductType()+"_"+ordOrderItemMeta.getIsResourceSendFax()+"_"+sbc.getFaxStrategy()+"_"+ordOrderItemMeta.getStrVisitTime();
		}else {
			key = ordOrderItemMeta.getSupplierId()+"_"+sbc.getTargetId()+"_"+ordOrderItemMeta.getProductType()+"_"+ordOrderItemMeta.getIsResourceSendFax()+"_"+ordOrderItemMeta.getStrVisitTime();
			if(ordOrderItemMeta.isHotelProductType()){
				key+="_"+ordOrderItemMeta.getSubProductType();//如果是酒店，按子类型来区分
			}
		}
		return key;
	}

	/**
	 * 变更的子子项列表是否包括当前子子项
	 * @author: ranlongfei 2013-5-10 下午7:19:52
	 * @param ooim
	 * @param set
	 * @param ec
	 */
	protected boolean hasContainChangedOrdOrderItemMeta(OrdOrderItemMeta ooim, AbstractEbkCertificateSet set, EbkCertificate ec) {
		if(ec == null) {
			return true;
		}
		if(StringUtils.isEmpty(set.getOrderItemMetaIdList())) {
			return true;
		}
		if(("," + set.getOrderItemMetaIdList() + ",").contains("," + ooim.getOrderItemMetaId() + ",")) {
			// 包括，通过
			return true;
		}
		List<EbkCertificateItem> itemList = this.ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(ec.getEbkCertificateId());
		for(EbkCertificateItem i : itemList) {
			if(("," + set.getOrderItemMetaIdList() + ",").contains("," + i.getOrderItemMetaId() + ",")){
				//包括，通过
				return true;
			}
		}
		return false;
	}
	public void setEbkCertificateDAO(EbkCertificateDAO ebkCertificateDAO) {
		this.ebkCertificateDAO = ebkCertificateDAO;
	}

	public void setEbkCertificateItemDAO(EbkCertificateItemDAO ebkCertificateItemDAO) {
		this.ebkCertificateItemDAO = ebkCertificateItemDAO;
	}

	public void setEbkOrderDataRevDAO(EbkOrderDataRevDAO ebkOrderDataRevDAO) {
		this.ebkOrderDataRevDAO = ebkOrderDataRevDAO;
	}

	public void setEbkFaxTaskDAO(EbkFaxTaskDAO ebkFaxTaskDAO) {
		this.ebkFaxTaskDAO = ebkFaxTaskDAO;
	}

	public void setEbkTaskDAO(EbkTaskDAO ebkTaskDAO) {
		this.ebkTaskDAO = ebkTaskDAO;
	}


	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}
	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}
}
