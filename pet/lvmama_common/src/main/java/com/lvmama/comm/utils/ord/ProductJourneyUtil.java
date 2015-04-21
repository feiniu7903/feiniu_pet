/**
 * 
 */
package com.lvmama.comm.utils.ord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;

/**
 * @author yangbin
 *
 */
public class ProductJourneyUtil {

	/**
	 * 转换一个行程产品列表到按产品类型的Map
	 * @param products 传入的ProdJourneyProduct需要先初始化其ProdProductBranch及ProdProduct
	 * @return
	 */
	public static Map<String,List<ProdJourneyProduct>> conver(List<ProdJourneyProduct> products){
		//按产品类别分类产品
		Map<String,List<ProdJourneyProduct>> map=new HashMap<String, List<ProdJourneyProduct>>();
		for(ProdJourneyProduct pp:products){
			final String type=pp.getProdBranch().getProdProduct().getProductType();
			List<ProdJourneyProduct> list=null;
			if(map.containsKey(type)){
				list=map.get(type);
			}else{
				list=new ArrayList<ProdJourneyProduct>();
			}
			
			list.add(pp);
			map.put(type, list);
		}
		return map;
	}
	
	
	/**
	 * 转换ProdJourneyProduct为Prodproduct
	 * 排放的格式为.
	 * product产品
	 * 		productBranch列表
	 * @param list
	 * @return
	 */
	public static List<ProdProduct> converToProduct(List<ProdJourneyProduct> list){
		List<ProdProduct> productList=null;
		if(CollectionUtils.isNotEmpty(list)){
			Map<Long,ProdProduct> map=new HashMap<Long, ProdProduct>();
			for(ProdJourneyProduct pjp:list){
				if(!map.containsKey(pjp.getProdBranch().getProductId())){
					ProdProduct pp=pjp.getProdBranch().getProdProduct();
					pp.setProdJourneyProductList(new ArrayList<ProdJourneyProduct>());
					map.put(pjp.getProdBranch().getProductId(), pjp.getProdBranch().getProdProduct());
				}
			}
			
			for(ProdJourneyProduct pjp:list){
				ProdProduct pp=map.get(pjp.getProdBranch().getProductId());
				pp.getProdJourneyProductList().add(pjp);
			}
			
			productList=new ArrayList<ProdProduct>();
			for(Long key:map.keySet()){
				productList.add(map.get(key));
			}
			
			//对取出来的数据排序,有默认产品的排第一个.
			Collections.sort(productList, new ProductComparator());
		}
		return productList;
	}
	
	static class ProductComparator implements Comparator<ProdProduct>{

		@Override
		public int compare(ProdProduct o1, ProdProduct o2) {
			Predicate predicate=new ProductPredicate(); 
			ProdJourneyProduct defaultProduct=(ProdJourneyProduct)CollectionUtils.find(o1.getProdJourneyProductList(), predicate);
			if(defaultProduct!=null){
				return -1;
			}
			defaultProduct=(ProdJourneyProduct)CollectionUtils.find(o2.getProdJourneyProductList(), predicate);
			if(defaultProduct!=null){
				return 1;
			}
			return 0;
		}
		
	}
	
	static class ProductPredicate implements Predicate{

		@Override
		public boolean evaluate(Object arg0) {
			return ((ProdJourneyProduct)arg0).hasDefaultProduct();
		}
		
	}
}
