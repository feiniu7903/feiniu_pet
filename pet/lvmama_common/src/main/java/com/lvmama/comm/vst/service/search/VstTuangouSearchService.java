package com.lvmama.comm.vst.service.search;

import java.util.List;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;

/**
 * 团购产品搜索
 * 
 * @author gairyoung
 * 
 */
@RemoteService("tuangouSearchService")
public interface VstTuangouSearchService {

	/**
	 * 按照关键词查询团购的产品信息
	 *  
	 * 团购查询结果不限制产品类型,但是会根据传入的productType影响排序
	 * 
	 * @param keyword
	 *            关键词
	 * @param productType
	 *            产品类型
	 * @param size
	 *            返回的团购产品数量
	 * @return
	 */
	public List<ProductBean> search(String keyword, String productType, int size);

	/**
	 * 使用SearchVO查询团购产品信息
	 * 
	 * 团购查询结果不限制产品类型,但是会根据传入的productType影响排序
	 * 
	 * @param sv
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public List<ProductBean> search(SearchVO sv, String productType);

}
