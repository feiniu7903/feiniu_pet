package com.lvmama.ebk.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;

public class EbkCertificateDAO extends BaseIbatisDAO {

    public int deleteByPrimaryKey(Long ebkCertificateId) {
        EbkCertificate key = new EbkCertificate();
        key.setEbkCertificateId(ebkCertificateId);
        int rows = super.delete("EBK_CERTIFICATE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(EbkCertificate record) {
        Object newKey = super.insert("EBK_CERTIFICATE.insert", record);
        return (Long) newKey;
    }
 
    public EbkCertificate selectByPrimaryKey(Long ebkCertificateId) {
        EbkCertificate key = new EbkCertificate();
        key.setEbkCertificateId(ebkCertificateId);
        EbkCertificate record = (EbkCertificate) super.queryForObject("EBK_CERTIFICATE.selectByPrimaryKey", key);
        return record;
    }
    public EbkCertificate selectNotValidByPrimaryKey(Long ebkCertificateId) {
        EbkCertificate key = new EbkCertificate();
        key.setEbkCertificateId(ebkCertificateId);
        EbkCertificate record = (EbkCertificate) super.queryForObject("EBK_CERTIFICATE.selectNotValidByPrimaryKey", key);
        return record;
    }
    
    @SuppressWarnings("unchecked")
	public List<EbkCertificate> selectEbkCertificateByOrderId(Long orderId){
    	return this.queryForList("EBK_CERTIFICATE.selectEbkCertificateByOrderId", orderId);
    }
    public EbkCertificate selectTicketMergeEbkCertificate(Long supplierId,Long targetId,String faxStrategy,Date visitTime) {
        EbkCertificate key = new EbkCertificate();
        key.setSupplierId(supplierId);
        key.setFaxStrategy(faxStrategy);
        key.setTargetId(targetId);
        key.setVisitTime(visitTime);
        EbkCertificate record = (EbkCertificate) super.queryForObject("EBK_CERTIFICATE.selectTicketMergeEbkCertificate", key);
        return record;
    }
    @SuppressWarnings("unchecked")
    public EbkCertificate selectNearbyEbkCertificateByOrderItemMetaId(Long orerItemMetaId) {
		List<EbkCertificate> list = super.queryForList("EBK_CERTIFICATE.selectEbkCertificateByOrderItemMetaId", orerItemMetaId);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
        return null;
    }
    
    public int updateByPrimaryKeySelective(EbkCertificate record) {
        int rows = super.update("EBK_CERTIFICATE.updateByPrimaryKeySelective", record);
        return rows;
    }
    public int updateUserMemoStatus(Long ebkCertificateId,String updateUserMemoStatus) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("ebkCertificateId", ebkCertificateId);
    	param.put("userMemoStatus", updateUserMemoStatus);
        int rows = super.update("EBK_CERTIFICATE.updateUserMemoStatus", param);
        return rows;
    }
    public int updateByPrimaryKey(EbkCertificate record) {
        int rows = super.update("EBK_CERTIFICATE.updateByPrimaryKey", record);
        return rows;
    }
    
    public int updateEbkCertificateByFaxTaskId(String faxMemo,List<Long> faxTaskIdList) {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("memo", faxMemo);
    	params.put("faxTaskIdList", faxTaskIdList);
    	int rows = super.update("EBK_CERTIFICATE.updateByFaxTaskId", params);
    	return rows;
    }
    
    public int updateChangeInfo(String changeInfo, Long ebkCertificateId) {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("changeInfo", changeInfo);
    	params.put("ebkCertificateId", ebkCertificateId);
    	int rows = super.update("EBK_CERTIFICATE.updateChangeInfo", params);
    	return rows;
    }
}