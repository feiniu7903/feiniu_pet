package com.lvmama.search.service.recommendEngine;


	import java.util.List;

	import com.lvmama.comm.search.vo.ProductBean;

	public interface SearchRecommendService  {
		public List<String> cfrecommendRes(String userId,int num);
		public List<ProductBean>getproductBeanById(String cookieid) ;
	}


