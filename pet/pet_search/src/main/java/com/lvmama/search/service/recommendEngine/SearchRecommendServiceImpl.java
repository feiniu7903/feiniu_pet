package com.lvmama.search.service.recommendEngine;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.lvmama.comm.search.service.CfrecommendService;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.search.service.SearchService;
import com.lvmama.search.util.PageConfig;
@Service("searchRecommendService")
public class SearchRecommendServiceImpl implements SearchRecommendService{
	private static final String url = "http://127.0.0.1:81/recommendEngine/CFRecommend"; 
	private static HessianProxyFactory factory = new HessianProxyFactory();
	@Resource
	protected SearchService productIdSearchService;
	@Override
	public List<String> cfrecommendRes(String userId, int num) {
		RouteSearchVO routeSearchVO = new RouteSearchVO(null,userId);
		PageConfig<ProductBean> pageConfig = productIdSearchService.search(routeSearchVO);
		return null;
	}
	@Override
	public List<ProductBean> getproductBeanById(String cookieid) {
		String url = "http://10.2.1.136:81/recommendEngine/CFRecommend"; 
		HessianProxyFactory factory = new HessianProxyFactory(); 
		CfrecommendService service; 
		CfrecommendService basic;
		try {
			basic = (CfrecommendService) factory.create(CfrecommendService.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
		List<String> list1 = basic.recommend(cookieid, 5); 
		if(list1==null || list1.size()<1 ){
			return null;
		}
		Object []ids=list1.toArray();
		List<ProductBean> returnList=new ArrayList<ProductBean>();
		if(ids!=null && ids.length>0){
			for(int i=0;i<ids.length;i++){
				String productId=ids[i].toString();
				RouteSearchVO routeSearchVO = new RouteSearchVO(null,productId);
				PageConfig<ProductBean> pageConfig = productIdSearchService.search(routeSearchVO);
				List<ProductBean> list=pageConfig.getItems();
				if(list!=null && list.size()>0){
					returnList.add(list.get(0));
				}
			}
		}
		return returnList;
	} 
	
	public static void main(String[] args) {
		String url = "http://10.2.1.136:81/recommendEngine/CFRecommend"; 
		HessianProxyFactory factory = new HessianProxyFactory(); 
		CfrecommendService service; 
		try { 
		CfrecommendService basic = (CfrecommendService) factory.create(CfrecommendService.class, url); 

		List<String> list = basic.recommend("01e4300f-523d-4bbe-a239-2fa3ca295b07", 5); 
		Object[] aa=list.toArray();
		for(int i=0;i<aa.length;i++){
			String bb=aa[i].toString();
			System.out.println(bb);
		}
		System.out.println(list.size()); 
		// for (String string : products) { 
		// System.out.println(string); 
		// } 
		} catch (MalformedURLException e) { 
		e.printStackTrace(); 
		} 
	}
	

}
