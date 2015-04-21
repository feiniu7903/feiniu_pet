package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;

public class EbkCertificateItemDAO extends BaseIbatisDAO {

	public Long countValidEbkCertificateItem(Long ebkCertificateId){
		return (Long) this.queryForObject("EBK_CERTIFICATE_ITEM.countValidEbkCertificateItem",ebkCertificateId);
	}
	@SuppressWarnings("unchecked")
	public List<EbkCertificateItem> queryEbkCertificateItemList(Map<String,Object> params) {
		return super.queryForList("EBK_CERTIFICATE_ITEM.queryEbkCertificateItemList", params);
	}
	
	public EbkCertificateItem selectEbkCertificateItemByParam(EbkCertificateItem eci){
		return (EbkCertificateItem) super.queryForObject("EBK_CERTIFICATE_ITEM.selectEbkCertificateItemByParam", eci);
	}
	public List<EbkCertificateItem> selectEbkCertificateItemByebkCertificateId(Long ebkCertificateId){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("ebkCertificateId", ebkCertificateId);
		return this.queryEbkCertificateItemList(params);
	}
	
	public EbkCertificateItem getEbkCertificateItemByOrderItemMetaId(Long orderItemMetaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderItemMetaId", orderItemMetaId);
		List<EbkCertificateItem> itemList = this.queryEbkCertificateItemList(params);
		if (itemList.size() > 0) {
			return itemList.get(0);
		}
		return null;
	}
 
    public Long insert(EbkCertificateItem record) {
        Object newKey = super.insert("EBK_CERTIFICATE_ITEM.insert", record);
        return (Long) newKey;
    }

    public int updateByPrimaryKeySelective(EbkCertificateItem record) {
        int rows = super.update("EBK_CERTIFICATE_ITEM.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public int updateOldItemByOrderItemMetaId(Long orderItemMetaId) {
    	EbkCertificateItem record = new EbkCertificateItem();
    	record.setOrderItemMetaId(orderItemMetaId);
    	record.setIsNew("false");
    	int rows = super.update("EBK_CERTIFICATE_ITEM.updateIsNewByOrderItemMetaId", record);
    	return rows;
    }

    public int updateByPrimaryKey(EbkCertificateItem record) {
        int rows = super.update("EBK_CERTIFICATE_ITEM.updateByPrimaryKey", record);
        return rows;
    }
    
    public long countCertItemByParam(Map<String,Object> params){
    	return (Long) super.queryForObject("EBK_CERTIFICATE_ITEM.countCertItemByParam",params);
    }
    
}