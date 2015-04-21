package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.MetaProductBasicMV;
import com.lvmama.report.vo.SupportRankAnalysisVo;

/**
 * 供应商分析
 * @author yangchen
 *
 */
public class MetaProductBasicMVDAO extends BaseIbatisDAO {
	/**供应商分析MetaProductBasicMV集合
	 * @param param p
	 * @return result
	 * */
	public List<MetaProductBasicMV> queryMetaProductBasicMV(final Map param,boolean isForReportExport) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return  super.queryForList(
				"META_PRODUCT_BASIC_MV.queryMetaProductBasicMV", param,isForReportExport);
	}
	/**
	 * 供应商分析总条数
	 * @param param p
	 * @return result
	 */
	public Long countMetaProductBasicMV(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.countMetaProductBasicMV", param);
	}

	/**
	 * 销售总数
	 * @param param p
	 * @return result
	 */
	public Long sumAmountQuantityMetaProductBasicMV(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumAmountQuantityMetaProductBasicMV",
				param);
	}

	/**
	 * 订单总数
	 * @param param p
	 * @return result
	 */
	public Long sumOrderQuantityMetaProductBasicMV(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumOrderQuantityMetaProdProductBasicMV",
				param);
	}

	/**
	 * 销售总额
	 * @param param p
	 * @return result
	 */
	public Long sumAmountProductBasicMV(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumAmountProductBasicMV", param);
	}

	/**
	 * 供应商分析排行Count
	 * @param param p
	 * @return result
	 */
	public Long countSupportRankAnalysis(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.countSupportRankAnalysis", param);
	}

	/**
	 * 供应商分析排行SupportRankAnalysisVo对象
	 * @param param p
	 * @return result
	 */
	public List<SupportRankAnalysisVo> querySupportRankAnalysis(final Map param,boolean isForReportExport) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (List<SupportRankAnalysisVo>) super
				.queryForList(
						"META_PRODUCT_BASIC_MV.querySupportRankAnalysis", param,isForReportExport);
	}

	/**
	 * 供应商排行分析下人数总数
	 * @param param p
	 * @return result
	 */
	public Long sumPersonQuantity(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumPersonQuantity", param);
	}
		/**
		 * 供应商排行分析下房间总数
		 * @param param p
		 * @return result
		 */
	public Long sumRoomQuantity(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumRoomQuantity", param);
	}

	/**
	 * 供应商排行分析下套总数
	 * @param param p
	 * @return result
	 */
	public Long sumSuitQuantity(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumSuitQuantity", param);
	}

	/**
	 * 供应商排行分析下订单总数
	 * @param param p
	 * @return result
	 */
	public Long sumOrderQuantity(final Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "META_PRODUCT_BASIC_MV");
		}
		return (Long) super.queryForObject(
				"META_PRODUCT_BASIC_MV.sumOrderQuantity", param);
	}
}
