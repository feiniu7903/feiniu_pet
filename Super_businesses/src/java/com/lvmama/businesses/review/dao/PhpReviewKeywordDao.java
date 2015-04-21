package com.lvmama.businesses.review.dao;

import java.util.Map;


public class PhpReviewKeywordDao extends CommReviewKeywordDao{
 
	private static final long serialVersionUID = 1344564534L;

	public Integer exeScanningPhpcmsComment(Map map) {
		 super.update("reviewproduce.exeScanningPhpcmsComment",map);
		 return (Integer)map.get("count");
	}

	public Integer exeScanningPhpcmsContentAll(Map map) {
		  Integer i=0;
		  for(int j=1;j<=9;j+=1){
			  super.update("reviewproduce.exeScanningPhpcmsC_"+j,map);
			  if ((Integer)map.get("count")==null)
				  i+=0;
			  else
				  i+=(Integer)map.get("count");
		  }
		 return i;
	}
}
