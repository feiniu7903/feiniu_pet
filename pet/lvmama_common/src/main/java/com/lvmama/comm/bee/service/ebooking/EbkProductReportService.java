package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.report.EbkProductVisitorReportVo;

public interface EbkProductReportService {
	/**
	 * 数据管理 >周边跟团游产品>  产品表
	 * 
	 * 2013-8-27
	 * @param param
	 * @return
	 */
	List<EbkProductVisitorReportVo> findOnSaleProductList(Map<String, Object> param);
	int findProductVisitorCount(Map<String, Object> param);
	/**
	 * 数据管理 > 周边跟团游产品 > 收客表
	 * 
	 * 2013-8-27
	 * @param param
	 * @return
	 */
	List<EbkProductVisitorReportVo> findProductSalesList(Map<String, Object> param);
	int findProductSalesCount(Map<String, Object> param);
	/**
	 * 数据管理 > 周边跟团游产品 > 出团游客信息表
	 * 
	 * 2013-8-27
	 * @param param
	 * @return
	 */
	List<EbkProductVisitorReportVo> findProductVisitorList(Map<String, Object> param);
	int findOnSaleProductCount(Map<String, Object> param);
}
