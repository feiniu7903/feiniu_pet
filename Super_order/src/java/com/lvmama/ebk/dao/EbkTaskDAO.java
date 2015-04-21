package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.pet.vo.Page;

public class EbkTaskDAO extends BaseIbatisDAO {

    public int countByExample(Map<String, Object> example) {
        Integer count = (Integer)  super.queryForObject("EBK_TASK.countByExample", example);
        return count.intValue();
    }

    public int deleteByPrimaryKey(Long ebkTaskId) {
        int rows = super.delete("EBK_TASK.deleteByPrimaryKey", ebkTaskId);
        return rows;
    }

    public Long insert(EbkTask record) {
    	return (Long)super.insert("EBK_TASK.insert", record);
    }

    @SuppressWarnings("unchecked")
	public List<EbkTask> selectByExample(Map<String, Object> example) {
    	return super.queryForList("EBK_TASK.selectByExample", example);
    }

    public EbkTask selectByPrimaryKey(Long ebkTaskId) {
        EbkTask record = (EbkTask) super.queryForObject("EBK_TASK.selectByPrimaryKey", ebkTaskId);
        return record;
    }
    public List<EbkTask> selectRpairByPrimaryKey() {
    	List<EbkTask> record = super.queryForList("EBK_TASK.selectRpairByPrimaryKey");
        return record;
    }
    
    @SuppressWarnings("unchecked")
	public List<EbkTask> selectByOrdItemMetaId(Long ordItemMetaId) {
    	return super.queryForList("EBK_TASK.selectByOrdItemMetaId", ordItemMetaId);
    }

    public int updateByPrimaryKey(EbkTask record) {
        int rows = super.update("EBK_TASK.updateByPrimaryKeySelective", record);
        return rows;
    }
    public int repairUpdateByPrimaryKeySelective(EbkTask record) {
        int rows = super.update("EBK_TASK.repairUpdateByPrimaryKeySelective", record);
        return rows;
    }
    
	public Integer selectCountByExample(Map<String, Object> example) {
		return (Integer)super.queryForObject("EBK_TASK.selectCountByExample", example);
	}

	public EbkTask selectByEbkCertificateId(Long ebkCertificateId) {
		return (EbkTask) super.queryForObject("EBK_TASK.selectByEbkCertificateId", ebkCertificateId);
	}
	
	@SuppressWarnings("unchecked")
	public Page<EbkTask> queryEbkTaskList(Long currentPage, Long pageSize, Map<String, Object> parameterObject) {
		Map<String, Object> para = new HashMap<String, Object>();
    	Page<EbkTask> page = new Page<EbkTask>();
		//查询总数
    	para.putAll(parameterObject);
    	Integer totalResultSize = queryEbkTaskCount(para);
		//分页查询
		if(totalResultSize > 0) {
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage);
			page.setTotalResultSize(totalResultSize);
			para.put("start", page.getStartRows());
			para.put("end", page.getEndRows());
			List<EbkTask> items = para.get("orderBy")!=null
								  ?this.queryForList("EBK_TASK.queryEbkTaskPageListOrderBy", para)
								  :this.queryForList("EBK_TASK.queryEbkTaskPageList", para);
			page.setItems(items);
		}
		return page;
	}
	
	public Integer queryEbkTaskCount(Map<String, Object> parameterObject) {
		return (Integer) super.queryForObject("EBK_TASK.queryEbkTaskCount",parameterObject);
	}

}