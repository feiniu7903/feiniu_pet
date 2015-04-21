package com.lvmama.ebk.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkProdTimePriceService;
import com.lvmama.comm.bee.vo.EbkCalendarModel;
import com.lvmama.ebk.dao.EbkProdTimePriceDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;

public class EbkProdTimePriceServiceImpl implements EbkProdTimePriceService {
	@Autowired
	private EbkProdTimePriceDAO ebkProdTimePriceDAO;
	@Autowired
	private ProdTimePriceDAO prodTimePriceDAO;
	
	@Autowired
	private MetaTimePriceDAO metaTimePriceDAO;
	
	@Override
	public List<EbkProdTimePrice> query(Map<String, Object> params) {
		return ebkProdTimePriceDAO.findListByParams(params);
	}
	@Override
	public List<EbkProdTimePrice> queryMetaAndProdTimePrice(Map<String, Object> params) {
		return ebkProdTimePriceDAO.queryMetaAndProdTimePrice(params);
	}
	@Override
	public List<EbkProdTimePrice> queryVirtualMetaAndProdTimePrice(Map<String, Object> params) {
		return ebkProdTimePriceDAO.queryVirtualMetaAndProdTimePrice(params);
	}
	
	/**
	 * 根据查询条件查询 EBK产品时间价格表
	 * @param productId
	 * @param prodBranchId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public EbkCalendarModel selectEbkProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", beginTime);
		param.put("endDate", endTime);
		List<EbkProdTimePrice> timePriceList =  query(param);
		EbkCalendarModel calendarModel = new EbkCalendarModel();
		calendarModel.setEbkTimePrice(timePriceList,beginTime);
		return calendarModel;
	}
	/**
	 *  根据查询条件查询 EBK产品对应的线上产品时间价格表
	 * @param params
	 * @return
	 */
	public EbkCalendarModel selectProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", beginTime);
		param.put("endDate", endTime);
		List<EbkProdTimePrice> timePriceList =  queryMetaAndProdTimePrice(param);
		EbkCalendarModel calendarModel = new EbkCalendarModel();
		calendarModel.setEbkTimePrice(timePriceList,beginTime);
		return calendarModel;
	}
	/**
	 *  根据查询条件查询 EBK产品对应的线上产品时间价格表（共享库存）
	 * @param params
	 * @return
	 */
	public EbkCalendarModel selectVirtualProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", beginTime);
		param.put("endDate", endTime);
		List<EbkProdTimePrice> timePriceList =  queryVirtualMetaAndProdTimePrice(param);
		EbkCalendarModel calendarModel = new EbkCalendarModel();
		calendarModel.setEbkTimePrice(timePriceList,beginTime);
		return calendarModel;
	}
	/**
	 *  根据查询条件查询线上产品时间价格表
	 * @param params
	 * @return
	 */
	public EbkCalendarModel selectRelationProdTimePriceByProdBranchId(Long prodBranchId,Date beginTime, Date endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("superProdBranchId", prodBranchId);
		param.put("beginDate", beginTime);
		param.put("endDate", endTime);
		List<EbkProdTimePrice> timePriceList =  ebkProdTimePriceDAO.queryRelationProductTimePrice(param);
		EbkCalendarModel calendarModel = new EbkCalendarModel();
		calendarModel.setEbkTimePrice(timePriceList,beginTime);
		return calendarModel;
	}
	@Override
	public void insertBatch(List<EbkProdTimePrice> ebkProdTimePrices) {
		try {
			ebkProdTimePriceDAO.insertBatch(ebkProdTimePrices);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateBatch(List<EbkProdTimePrice> ebkProdTimePrices) {
		try {
			ebkProdTimePriceDAO.updateBatch(ebkProdTimePrices);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public EbkProdTimePrice insert(EbkProdTimePrice ebkProdTimePrice) {
		Long key = ebkProdTimePriceDAO.insertEbkProdTimePriceDO(ebkProdTimePrice);
		ebkProdTimePrice.setTimePriceId(key);
		return ebkProdTimePrice;
	}

	@Override
	public int update(EbkProdTimePrice ebkProdTimePrice) {
		return ebkProdTimePriceDAO.updateEbkProdTimePriceDO(ebkProdTimePrice);
	}

	@Override
	public int delete(Long ebkProdTimePriceId) {
		return ebkProdTimePriceDAO.deleteEbkProdTimePriceDOByPrimaryKey(ebkProdTimePriceId);
	}

	 /**
     * 统计记录数
     * @param ebkProdTimePriceDO
     * @return 查出的记录数
     */
	@Override
    public Integer countEbkProdTimePriceDOByExample(EbkProdTimePrice ebkProdTimePriceDO) {
       return ebkProdTimePriceDAO.countEbkProdTimePriceDOByExample(ebkProdTimePriceDO);
    }

    /**
     * 获取对象列表
     * @param ebkProdTimePriceDO
     * @return 对象列表
     */
	@Override
    public List<EbkProdTimePrice> findListByTerm(EbkProdTimePrice ebkProdTimePriceDO) {
        return ebkProdTimePriceDAO.findListByTerm(ebkProdTimePriceDO);
    }
    
    /**
     * 查询时间价格按照时间升序排序
     * @param ebkProdTimePriceDO
     * @return
     */
	@Override
	public List<EbkProdTimePrice> findListByTermOrderByDateASC(EbkProdTimePrice ebkProdTimePriceDO) {
        return ebkProdTimePriceDAO.findListByTermOrderByDateASC(ebkProdTimePriceDO);
    }
    

    /**
     * 根据主键获取ebkProdTimePriceDO
     * @param timePriceId
     * @return ebkProdTimePriceDO
     */
	@Override
    public EbkProdTimePrice findEbkProdTimePriceDOByPrimaryKey(Long timePriceId) {
      return ebkProdTimePriceDAO.findEbkProdTimePriceDOByPrimaryKey(timePriceId);
    }

	@Override
	public int update(Map<String, Object> params) {
		return ebkProdTimePriceDAO.update(params);
	}
	@Override
	public int delete(Map<String, Object> params) {
		return ebkProdTimePriceDAO.delete(params);
	}
	@Override
	public List<TimePrice> selectProdTimePriceByParams(Long prodBranchId,Date beginDate,Date endDate){
		Map<String,Object> param =new HashMap<String,Object>();
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return prodTimePriceDAO.selectProdTimePriceByParams(param);
	}
	
	@Override
	public List<TimePrice> selectMetaTimePriceByParams(Long metaBranchId,Date beginDate,Date endDate){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("metaBranchId", metaBranchId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return metaTimePriceDAO.getMetaTimePriceByMetaBranchId(param);
	}
	
	
	
	
}
