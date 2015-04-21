package com.lvmama.search.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 产品ID号搜索
 * @author HZ
 *
 */
@Service("productIdSearchService")
public class ProductIdSearchServiceImpl implements SearchService {
	
	protected Log loger = LogFactory.getLog(this.getClass());
	@Resource
	protected NewBaseSearchService newProductSearchService;
	@Override
	public PageConfig<ProductBean> search(SearchVO sv, SORT... sorts) {
		RouteSearchVO productIdSearchVO = (RouteSearchVO)sv;
		int page = productIdSearchVO.getPage()<=0 ? 1:productIdSearchVO.getPage();
		int pageSize = productIdSearchVO.getPageSize() <=0 ? 10 : productIdSearchVO.getPageSize();
		Map<String, String> params = new HashMap<String, String>();	
		PageConfig<ProductBean> pageConfig = null;
		String productId =  productIdSearchVO.getKeyword();
		if (StringUtils.isNotEmpty(productId)) {
			Query q = QueryUtil.getProductIdQuery(productId);
			pageConfig = newProductSearchService.search(pageSize, page, q);
		}
		return pageConfig;
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
}
