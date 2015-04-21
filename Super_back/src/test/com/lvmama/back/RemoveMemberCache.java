package com.lvmama.back;

import com.lvmama.comm.utils.MemcachedUtil;


public class RemoveMemberCache {

	public static void main(String [] age){
		//Object o =  MemCachedUtil.get("PROD_C_PRODUCT_INFO_2266");
		//System.out.println(o);
		//System.out.println( MemCachedUtil.remove("PROD_C_PRODUCT_INFO_61903"));
		//boolean bool = MemCachedUtil.remove("PROD_C_PRODUCT_INFO_63686");
		//placeDest_key: 
		//placeDest_key: 
		//placeDest_key: 
		//placeDest_key: 
		//placeDest_key:
		MemcachedUtil.getInstance().remove("placeAction_navigation_placeDest_3547");
		MemcachedUtil.getInstance().remove("placeAction_navigation_placeDest_104804");
		boolean bool = MemcachedUtil.getInstance().remove("placeAction_navigation_placeDest_299");
		System.out.println(bool);
		//bool = MemCachedUtil.remove("PROD_C_PRODUCT_INFO_61903");
		//System.out.println(bool);
		//bool = MemCachedUtil.remove("PROD_C_PRODUCT_INFO_61906");
		//System.out.println(bool);
		
	}
}
