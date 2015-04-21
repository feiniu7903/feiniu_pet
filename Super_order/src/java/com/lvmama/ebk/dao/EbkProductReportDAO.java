package com.lvmama.ebk.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.vo.report.EbkProductVisitorReportVo;

public class EbkProductReportDAO extends BaseIbatisDAO {
	/**
	 * 数据管理 >周边跟团游产品>  产品表
	 * 
	 * 2013-8-27
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EbkProductVisitorReportVo> findOnSaleProductList(Map<String, Object> param){
		return this.queryForList("EBK_PRODUCT_REPORT.findOnSaleProductList", param);
	}
	public int findOnSaleProductCount(Map<String, Object> param) {
        Integer count = (Integer)  super.queryForObject("EBK_PRODUCT_REPORT.findOnSaleProductCount", param);
        return count.intValue();
    }
	/**
	 * 数据管理 > 周边跟团游产品 > 收客表
	 * 
	 * 2013-8-27
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EbkProductVisitorReportVo> findProductSalesList(Map<String, Object> param){
		return this.queryForList("EBK_PRODUCT_REPORT.findProductSalesList", param);
	}
	public int findProductSalesCount(Map<String, Object> param) {
		Integer count = (Integer)  super.queryForObject("EBK_PRODUCT_REPORT.findProductSalesCount", param);
		return count.intValue();
	}
	/**
	 * 数据管理 > 周边跟团游产品 > 出团游客信息表
	 * 
	 * 2013-8-27
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EbkProductVisitorReportVo> findProductVisitorList(Map<String, Object> param){
		return this.queryForList("EBK_PRODUCT_REPORT.findProductVisitorList", param);
	}
	public int findProductVisitorCount(Map<String, Object> param) {
		Integer count = (Integer)  super.queryForObject("EBK_PRODUCT_REPORT.findProductVisitorCount", param);
		return count.intValue();
	}
}