package com.lvmama.comm.bee.service.market;

import com.lvmama.comm.pet.po.mark.MarkActivityBlacklist;
import java.util.List;
import java.util.Map;

/**
 * @author shihui
 */
public interface MarkActivityBlacklistService {

	/**
	 * 根据关键字查找
	 * */
    List<MarkActivityBlacklist> getMarkActivityBlacklist(Map<String, Object> paramMap);
    
    Long getMarkActivityBlacklistCount(Map<String, Object> paramMap);
    
    Long saveMarkActivityBlacklist(MarkActivityBlacklist record);
    
    void updateMarkActivityBlacklist(MarkActivityBlacklist record);
    
    void deleteMarkActivityBlacklist(Long blackId);
    
    MarkActivityBlacklist selectByPrimaryKey(Long actId);
    
    Long checkIsExisted(Map<String, Object> paramMap);
}
