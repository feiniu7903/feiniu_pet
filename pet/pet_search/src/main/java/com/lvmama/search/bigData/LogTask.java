package com.lvmama.search.bigData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.lvmama.search.util.LuceneCommonDic;
/*
 * 线程类来调用hbasedata
 */
public class LogTask implements Callable {

	List<String> placeidList=null;
	String keyword=null;
	public LogTask(){			
	}
	public LogTask(String keyword, List<String> placeidList) {
		this.placeidList=placeidList;
		this.keyword=keyword;
	}
	@Override
	public Object call() throws Exception {
		
		Map<String, String> returnMap=null;
		try {
			returnMap = HbaseData.queryProductScore(
					LuceneCommonDic.hbaseDefaultTable, keyword, LuceneCommonDic.hbaseDefaultFamily, placeidList);
		} catch (Exception e) {
			return null;
		}
		
		return returnMap;
	}

}
