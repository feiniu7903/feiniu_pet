package com.lvmama.comm.bee.service.ebooking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;


public interface EbkProdContentService {
	/**
     * 获取对象列表
     * @param ebkProdContentDO
     * @return 对象列表
     */
    public List<EbkProdContent> findListByTerm(EbkProdContent ebkProdContentDO) ;
    
    /**
     * 通过产品ID获取对象列表
     * @param productId
     * @return 对象列表
     */
    public List<EbkProdContent> findListByProductId(Long productId) ;
    
    /**
     * 保存EBK产品特色及推荐,费用说明,其他条款等信息
     * @param ebkProdContents
     * @return 对象列表
     */
    public void saveEbkProdContentDAO(List<EbkProdContent> ebkProdContents) ;
    
    /**
     * 保存EBK产品特色及推荐,费用说明,其他条款等信息
     * @param ebkProdContents
     * @return 对象列表
     */
    public void saveEbkMultiJourneyContentDAO(List<EbkProdContent> ebkProdContents) ;
    
    /**
     * 保存EBK产品发车信息
     * @param ebkProdContents
     * @return 对象列表
     */
    public void saveEbkProdTrafficContentDAO(List<EbkProdContent> ebkProdContents,Long productId,String contentType) ;
    
	public List<EbkProdContent> getEbkContentByMultiJourneyId(Long multiJourneyId, String contentType);
	
	 public List<EbkProdContent> findEbkProdContentDOByIdAndType(Map<String,Object> params);
	 
	 public void insertEbkProdContent(EbkProdContent ebkProdContent);
	 
	 public Long insert(EbkProdContent ebkProdContent);
    
}
