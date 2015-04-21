package com.lvmama.ebk.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-09-24
 */
public class EbkProdJourneyDAO extends BaseIbatisDAO {

	/**
	 * 插入数据
	 * 
	 * @param ebkProdJourneyDO
	 * @return 插入数据的主键
	 */
	public Long insertEbkProdJourneyDO(EbkProdJourney ebkProdJourneyDO) {
		Object JOURNEY_ID = super.insert("EBK_PROD_JOURNEY.insert",
				ebkProdJourneyDO);
		return (Long) JOURNEY_ID;
	}

	/**
	 * 统计记录数
	 * 
	 * @param ebkProdJourneyDO
	 * @return 查出的记录数
	 */
	public Integer countEbkProdJourneyDOByTerm(EbkProdJourney ebkProdJourneyDO) {
		Integer count = (Integer) super.queryForObject(
				"EBK_PROD_JOURNEY.countByDOTerm", ebkProdJourneyDO);
		return count;
	}

	/**
	 * 更新记录
	 * 
	 * @param ebkProdJourneyDO
	 * @return 受影响的行数
	 */
	public Integer updateEbkProdJourneyDO(EbkProdJourney ebkProdJourneyDO) {
		int result = super.update("EBK_PROD_JOURNEY.update", ebkProdJourneyDO);
		return result;
	}

	/**
	 * 获取对象列表
	 * 
	 * @param ebkProdJourneyDO
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<EbkProdJourney> findListByTerm(EbkProdJourney ebkProdJourneyDO) {
		List<EbkProdJourney> list = super.queryForList(
				"EBK_PROD_JOURNEY.findListByDO", ebkProdJourneyDO);
		return list;
	}

	/**
	 * 获取对象列表--默认使用day_number排序
	 * 
	 * @param ebkProdJourneyDO
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<EbkProdJourney> findListOrderDayNumberByDO(
			EbkProdJourney ebkProdJourneyDO) {
		List<EbkProdJourney> list = super
				.queryForList("EBK_PROD_JOURNEY.findListOrderDayNumberByDO",
						ebkProdJourneyDO);
		return list;
	}

	/**
	 * 根据主键获取ebkProdJourneyDO
	 * 
	 * @param journeyId
	 * @return ebkProdJourneyDO
	 */
	public EbkProdJourney findEbkProdJourneyDOByPrimaryKey(Long journeyId) {
		EbkProdJourney ebkProdJourneyDO = (EbkProdJourney) super
				.queryForObject("EBK_PROD_JOURNEY.findByPrimaryKey", journeyId);
		return ebkProdJourneyDO;
	}

	/**
	 * 删除记录
	 * 
	 * @param journeyId
	 * @return 受影响的行数
	 */
	public Integer deleteEbkProdJourneyDOByPrimaryKey(Long journeyId) {
		Integer rows = (Integer) super.delete(
				"EBK_PROD_JOURNEY.deleteByPrimaryKey", journeyId);
		return rows;
	}

	public List<EbkProdJourney> getEbkProdJourneyByMultiJourneyId(
			Long multiJourneyId) {
		List<EbkProdJourney> ebkJList = super.queryForList(
				"EBK_PROD_JOURNEY.getEbkProdJourneyByMultiJourneyId",
				multiJourneyId);
		/*
		 * if(ebkJList != null) { for (int i = 0; i < ebkJList.size(); i++) {
		 * EbkProdJourney ebkJourney = ebkJList.get(i); long journeyId =
		 * ebkJourney.getJourneyId(); List<Place> comPlaceList =
		 * super.queryForList("COM_PLACE.getComPlace", journeyId);
		 * ebkJourney.setPlaceList(comPlaceList); } }
		 */
		return ebkJList;
	}

}