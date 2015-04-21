package com.lvmama.comm.bee.service.ebooking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.vo.Constant;


public interface EbkProdJourneyService {
	/**
     * 获取对象列表
     * @param ebkProdJourneyDO
     * @return 对象列表
     */
    public List<EbkProdJourney> findListByTerm(EbkProdJourney ebkProdJourneyDO);
    /**
     * 获取对象列表--默认使用day_number排序
     * @param ebkProdJourneyDO
     * @return 对象列表
     */
    public List<EbkProdJourney> findListOrderDayNumberByDO(EbkProdJourney ebkProdJourneyDO);
    /**
     * 批量新增行程
     * @param ebkProdJourneyDOs
     * @param productId
     * @param mustSaveDayNum
     * @return 
     */
    public void editEbkProdJourneys(List<EbkProdJourney> ebkProdJourneyDOs,Long productId,Long mustSaveDayNum);
    
    /**
     * 插入多行程每条记录
     * @param record
     * @param operatorName
     * @return
     */
    //public Long insert(EbkProdJourney record, String operatorName);
    
    public Long insertEbkProdJourney(EbkProdJourney ebkProdJourneyDO);
    
    public List<EbkProdJourney> getViewJourneyByMultiJourneyId(Long multiJourneyId);
    
    public void insertEbkJourney(EbkProdJourney ebkJourney);
    
    public void editEbkMultiProdJourneys(List<EbkProdJourney> ebkProdJourneyDOs,Long multiJourneyId,Long productId,Long mustSaveDayNum);
    
}
