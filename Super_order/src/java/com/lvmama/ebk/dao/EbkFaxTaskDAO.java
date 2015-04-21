package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ebooking.OrdFaxSend;
import com.lvmama.comm.bee.po.ebooking.OrdFaxTask;
import com.lvmama.comm.pet.vo.Page;

public class EbkFaxTaskDAO extends BaseIbatisDAO {
	
	public void updateSendCount(Long ebkFaxTaskId){
		super.update("EBK_FAX_TASK.updateSendCount", ebkFaxTaskId);
	}
	@SuppressWarnings("unchecked")
	public List<EbkFaxTask> selectEbkFaxTaskByParams(Map<String, Object> params) {
    	return super.queryForList("EBK_FAX_TASK.selectEbkFaxTaskByParams", params);
    }
    public List<EbkFaxTask> selectSendFaxTask() {
    	return super.queryForList("EBK_FAX_TASK.selectSendFaxTask");
    }
	public Integer getEbkFaxTaskCountByParams(Map<String, Object> params) {
		return (Integer)super.queryForObject("EBK_FAX_TASK.getEbkFaxTaskCountByParams", params);
	}
	public List<Long> selectOrderIdListByFaxSendId(Long ebkFaxSendId) {
		return super.queryForList("EBK_FAX_TASK.selectOrderIdListByFaxSendId", ebkFaxSendId);
	}
	public List<EbkFaxTask> selectOldEbkFaxTaskListWithTaskId(Long ebkFaxTaskId) {
		return super.queryForList("EBK_FAX_TASK.selectOldEbkFaxTaskListWithTaskId", ebkFaxTaskId);
	}
	public EbkFaxTask selectEbkFaxTaskByEbkCertificateId(Long ebkCertificateId) {
		return (EbkFaxTask) super.queryForObject("EBK_FAX_TASK.selectEbkFaxTaskByEbkCertificateId", ebkCertificateId);
	}
	public EbkFaxTask selectEbkFaxTaskByEbkFaxSendId(Long ebkFaxSendId) {
		return (EbkFaxTask) super.queryForObject("EBK_FAX_TASK.selectEbkFaxTaskByEbkFaxSendId", ebkFaxSendId);
	}
	public EbkFaxTask selectEbkFaxTaskByEbkFaxTaskId(Long ebkFaxTaskId) {
		return (EbkFaxTask) super.queryForObject("EBK_FAX_TASK.selectEbkFaxTaskByEbkFaxTaskId", ebkFaxTaskId);
	}
	
	 public List<OrdFaxTask> selectOrdFaxTaskRpairByPrimaryKey() {
		return super.queryForList("EBK_FAX_TASK.selectOrdFaxTaskRpairByPrimaryKey");
	 }
	 public Page<OrdFaxSend> selectOrdFaxSendRpairAll(Long pageSize,Long currentPage) {
		 Page<OrdFaxSend> page = Page.page(pageSize, currentPage);
		 Long totalResultSize = (Long) super.queryForObject("EBK_FAX_TASK.countOrdFaxSendRpairAll");
		 page.setTotalResultSize(totalResultSize);
		 Map<String, Object> param = new HashMap<String, Object>();
		 param.put("start", page.getStartRows());
		 param.put("end", page.getEndRows());
		 page.setItems(super.queryForList("EBK_FAX_TASK.selectOrdFaxSendRpairAll",param));
		 return page;
	 }
	 public List<OrdFaxSend> selectOrdFaxTaskSend(Long orderId) {
		 return super.queryForList("EBK_FAX_TASK.selectOrdFaxTaskSend",orderId);
	 }
	 
	 public List<OrdFaxSend> selectOrdFaxSendRpairByPrimaryKey(Long faxTaskId) {
		return super.queryForList("EBK_FAX_TASK.selectOrdFaxSendRpairByPrimaryKey",faxTaskId);
	 }
	 public List<OrdFaxTask> selectOrdFaxTaskRpairByFaxSendId(Long faxSendId){
		 return super.queryForList("EBK_FAX_TASK.selectOrdFaxTaskRpairByFaxSendId",faxSendId);
	 }
    public EbkFaxTask getByEbkFaxTaskId(Long ebkFaxTaskId) {
    	return this.selectEbkFaxTaskByEbkFaxTaskId(ebkFaxTaskId);
    }
    
	public EbkFaxTask getByEbkCertificateId(Long ebkCertificateId) {
		Map<String,Object> params=new HashMap<String, Object>();
    	params.put("ebkCertificateId", ebkCertificateId);
    	List<EbkFaxTask> ebkFaxTaskList=this.selectEbkFaxTaskByParams(params);
    	if(ebkFaxTaskList.size()>0){
    		return ebkFaxTaskList.get(0);
    	}
    	return null;
	}
	
    public int deleteByEbkFaxTaskId(Long ebkFaxTaskId) {
        EbkFaxTask key = new EbkFaxTask();
        key.setEbkFaxTaskId(ebkFaxTaskId);
        int rows = super.delete("EBK_FAX_TASK.deleteByPrimaryKey", key);
        return rows;
    }
   

    public Long insertEbkFaxTask(EbkFaxTask record) {
        Object newKey = super.insert("EBK_FAX_TASK.insertEbkFaxTask", record);
        return (Long) newKey;
    }
    
    public Long insertEbkFaxTask2(EbkFaxTask record) {
        Object newKey = super.insert("EBK_FAX_TASK.insertEbkFaxTask2", record);
        return (Long) newKey;
    }
    /**
     * 更新凭证最新更新状态，根据CERTIFICATE_ID查找OLD_CERTIFICATE_ID
     * <br>where EBK_CERTIFICATE_ID=(select a.OLD_CERTIFICATE_ID from EBK_CERTIFICATE a where EBK_CERTIFICATE_ID=#ebkCertificateId#)
     * @author: ranlongfei 2013-5-2 下午7:04:58
     * @param record
     * @return
     */
    public int updateEbkFaxTaskNewStatusWithNewId(EbkFaxTask record) {
    	int rows = super.update("EBK_FAX_TASK.updateEbkFaxTaskNewStatusWithNewId", record);
    	return rows;
    }

    public int updateEbkFaxTask(EbkFaxTask record) {
        int rows = super.update("EBK_FAX_TASK.updateEbkFaxTask", record);
        return rows;
    }
    public int updateOrdFaxTask(Long faxSendId,Long isRpair) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("isRpair", isRpair);
    	param.put("faxSendId", faxSendId);
        int rows = super.update("EBK_FAX_TASK.updateOrdFaxTask", param);
        return rows;
    }
    public int updateEbkSendOrRecvStatus(EbkFaxTask ebkFaxTask) {
   	 int rows = super.update("EBK_FAX_TASK.updateEbkSendOrRecvStatus", ebkFaxTask);
        return rows;
   }

}