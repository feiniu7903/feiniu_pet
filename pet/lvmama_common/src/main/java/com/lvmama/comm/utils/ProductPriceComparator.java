package com.lvmama.comm.utils;

import java.util.Comparator;

import com.lvmama.comm.pet.po.search.ProductSearchInfo;

public class ProductPriceComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		ProductSearchInfo p0=(ProductSearchInfo)o1;  
		ProductSearchInfo p1=(ProductSearchInfo)o2;  
		int flag=p0.getSellPrice().compareTo(p1.getSellPrice());
		return flag;
	}


}
