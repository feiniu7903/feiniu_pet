package com.lvmama.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.search.service.TuangouSearchService;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;

@HessianService("tuangouSearchService")
@Service("tuangouSearchService")
public class TuangouSearchServiceImpl implements TuangouSearchService {

	protected Log loger = LogFactory.getLog(this.getClass());
	@Resource
	protected NewBaseSearchService newProductSearchService;
	

	@Override
	public List<ProductBean> search(String keyword, String productType, int size) {
		SearchVO sv = new SearchVO();
		sv.setKeyword(keyword);
		List<ProductBean> resList = this.search(sv, productType);
		if (resList != null && resList.size() > 0) {
			return resList.size() <= size ? resList : resList.subList(0, size);
		}else{
			return new ArrayList<ProductBean>();
		}
	}

	@Override
	public List<ProductBean> search(SearchVO sv, String productType) {
		Map<String, String> params = new HashMap<String, String>();	
		params.put(ProductDocument.TO_DEST, sv.getKeyword().trim());
		params.put(ProductDocument.PRODUCT_CHANNEL, "TUANGOU");	
		String type = productType.toUpperCase();
		if (type!=null&&!type.equals("TICKET")&&!type.equals("HOTEL")){
			type = "ROUTE";
		}
		/** 默认优先级别**/
		 String[] typePriority = new String[] { "TICKET", "HOTEL", "ROUTE"};
		 channelPriorityModify(type , typePriority);
		 	
		Query q = QueryUtil.tuanGouRecommedQuery(params);
		List<ProductBean> productList = newProductSearchService.search(q);	
		List<ProductBean> productListSort =  new ArrayList<ProductBean>();
		if (productList!=null&&!productList.isEmpty()) {			
			//按优先级别排序
			for (int i = 0; i < typePriority.length; i++) {
				for (int j = 0; j < productList.size(); j++) {
					if (productList.get(j).getProductType().indexOf(typePriority[i])>=0) {
						productListSort.add(productList.get(j));
					}					
				}				
			}			
		}		
		
		/**算团购倒计时**/		
		if (productListSort.size() > 0 ) {
			for (int i = 0; i < productListSort.size(); i++) {
				productListSort.get(i).setValidTime((String.valueOf(Long.parseLong(productListSort.get(i).getValidTime()) - System.currentTimeMillis())));
			}
		}		
		return productListSort;
	}
	
	/**
	 *  调整补全排序默认优先级   
	 **/	
	private void channelPriorityModify(String nowType, String[] channelPriority) {
		if (!nowType.isEmpty() && !nowType.equals("MAIN")) {
			for (int i = 0; i < channelPriority.length; i++) {
				if (channelPriority[i].indexOf(nowType) >= 0 && i > 0) {
					String topChannel = channelPriority[i];
					for (int j = i - 1; j >=0; j--) {
						channelPriority[j+1] = channelPriority[j];
					}
					channelPriority[0] = topChannel;
					break;
				}
			}
		}
	}

}
