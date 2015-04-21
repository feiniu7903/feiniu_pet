package com.lvmama.comm.bee.service.ebooking;

/**
 * ebk产品时间价格、库存维护接口
 * @author shangzhengyuan
 *
 */
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.EbkCalendarModel;

public interface EbkProdTimePriceService {
	
	/**
	 * 根据查询条件查询 EBK产品时间价格表
	 * @param params
	 * @return
	 */
	List<EbkProdTimePrice> query(final Map<String,Object> params);
	
	/**
	 *  根据查询条件查询 EBK产品对应的线上产品时间价格表
	 * @param params
	 * @return
	 */
	public List<EbkProdTimePrice> queryMetaAndProdTimePrice(Map<String, Object> params) ;
	
	/**
	 *  根据查询条件查询 EBK产品对应的线上产品时间价格表（共享库存）
	 * @param params
	 * @return
	 */
	public List<EbkProdTimePrice> queryVirtualMetaAndProdTimePrice(Map<String, Object> params) ;
	
	/**
	 * 根据查询条件查询 EBK产品时间价格表
	 * @param productId
	 * @param prodBranchId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public EbkCalendarModel selectEbkProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime);
	/**
	 *  根据查询条件查询 EBK产品对应的线上产品时间价格表
	 * @param params
	 * @return
	 */
	public EbkCalendarModel selectProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime);
	/**
	 *  根据查询条件查询 EBK产品对应的线上产品时间价格表（共享库存）
	 * @param params
	 * @return
	 */
	public EbkCalendarModel selectVirtualProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime);
	
	/**
	 * 批量插入 EBK产品时间价格、库存信息
	 * @param ebkProdTimePrice
	 * @return
	 */
	void insertBatch(final List<EbkProdTimePrice> ebkProdTimePrices);
	
	/**
	 * 批量更新 EBK产品时间价格、库存信息
	 * @param ebkProdTimePrice
	 * @return
	 */
	void updateBatch(final List<EbkProdTimePrice> ebkProdTimePrices);
	
	/**
	 * 插入一条 EBK产品时间价格、库存信息
	 * @param ebkProdTimePrice
	 * @return
	 */
	EbkProdTimePrice insert(final EbkProdTimePrice ebkProdTimePrice);
	
	/**
	 * 更新 EBK产品时间价格、库存信息
	 * @param ebkProdTimePrice
	 * @return
	 */
	int update(final EbkProdTimePrice ebkProdTimePrice);
	
	/**
	 * 更新 EBK产品时间价格、库存信息
	 * @param ebkProdTimePrice
	 * @return
	 */
	int update(final Map<String,Object> params);
	
	/**
	 * 删除 EBK产品时间价格、库存信息
	 * @param ebkProdTimePriceId
	 * @return
	 */
	int delete(final Long ebkProdTimePriceId);
	/**
	 * 根据 条件删除 EBK产品时间价格、库存信息
	 * @param ebkProdTimePriceId
	 * @return
	 */
	int delete(final Map<String,Object> params);
	 /**
     * 统计记录数
     * @param ebkProdTimePriceDO
     * @return 查出的记录数
     */
    public Integer countEbkProdTimePriceDOByExample(EbkProdTimePrice ebkProdTimePriceDO);

    /**
     * 获取对象列表
     * @param ebkProdTimePriceDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdTimePrice> findListByTerm(EbkProdTimePrice ebkProdTimePriceDO) ;
    /**
     * 查询时间价格按照时间升序排序
     * @param ebkProdTimePriceDO
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<EbkProdTimePrice> findListByTermOrderByDateASC(EbkProdTimePrice ebkProdTimePriceDO) ;
    

    /**
     * 根据主键获取ebkProdTimePriceDO
     * @param timePriceId
     * @return ebkProdTimePriceDO
     */
    public EbkProdTimePrice findEbkProdTimePriceDOByPrimaryKey(Long timePriceId);
    
    /**
     * 查询super的时间价格
     * @param prodBranchId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<TimePrice> selectProdTimePriceByParams(Long prodBranchId,Date beginDate,Date endDate);

	EbkCalendarModel selectRelationProdTimePriceByProdBranchId(Long ebkProdBranchId, Date beginDate, Date endDate);

	public List<TimePrice> selectMetaTimePriceByParams(Long metaBranchId,Date beginDate,Date endDate);
}
